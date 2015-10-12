package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.dao.DiammUserDTO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateDiammUserDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.DiammUser;
import uk.ac.kcl.cch.diamm.model.UserAccess;
import uk.ac.kcl.cch.util.Password;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UserAccessServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public static DiammUser getCurrentUser(String username) {
        DiammUser user = null;
        if (username != null && username.length() > 0) {
            DiammUserDAO userDao = DAOFactory.getFactory().getDiammUserDAO();
            HibernateUtil.beginTransaction();
            user = userDao.findByUsername(username);
            HibernateUtil.commitTransaction();
        }
        return user;
    }


    protected void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        DIAMMFacetServlet.addMenu(req, "facetmanager");
        if (action.equals("manager")) {
            displayManager(req, resp);
        } else if (action.equals("register")) {
            register(req, resp);
        } else if (action.equals("retrievePassword")) {
            retrievePassword(req, resp);
        } else if (action.equals("changePassword")) {
            changePassword(req, resp);
        } else if (action.equals("editAccount")) {
            editAccount(req, resp);
        } else if (action.equals("logout")) {
            logout(req, resp);
        }
    }

    private void displayManager(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {

        try {
            HibernateUtil.beginTransaction();
            if (request.getParameter("search") != null) {
                Criteria c = HibernateUtil.getSession().createCriteria(DiammUser.class);
                boolean search = false;
                if (request.getParameter("username") != null) {
                    String user = request.getParameter("username");
                    search = true;
                    c.add(Restrictions.ilike("username", '%' + user + '%'));
                } else if (request.getParameter("email") != null) {
                    search = true;
                    c.add(Restrictions.ilike("email",'%' + request.getParameter("email")+'%'));
                }
                if (search) {
                    ArrayList<DiammUser> users = (ArrayList<DiammUser>) c.list();
                    request.setAttribute("results", users);
                }
            } else if (request.getParameter("update") != null) {
                int id=Integer.parseInt(request.getParameter("id"));
                DiammUser user=(DiammUser) HibernateUtil.getSession().load(DiammUser.class,new Integer(id));
                user = updateUserFromRequest(user, request);
                HibernateUtil.getSession().update(user);
                request.setAttribute("message","User updated");
                //UserAccess.commitUser(user);
            }

            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + "usermanager.jsp");
        reqDisp.forward(request, resp);
    }


    private DiammUser updateUserFromRequest(DiammUser user, HttpServletRequest req) {
        if (user != null) {


            if (req.getParameter("displayName") != null) {
                user.setDisplayName(req.getParameter("displayName"));
            }
            if (req.getParameter("username") != null) {
                user.setUsername(req.getParameter("username"));
            }
            if (req.getParameter("email") != null) {
                user.setEmail(req.getParameter("email"));
            }
            if (req.getParameter("affiliation") != null) {
                user.setAffiliation(req.getParameter("affiliation"));
            }
            if (req.getParameter("password") != null) {
                user.setPassword(Password.encrypt(req.getParameter("password")));
            }
        }
        return user;
    }

    private void editAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DiammUserDAO dao = new HibernateDiammUserDAO();
        DiammUser user = getCurrentUser(req.getRemoteUser());
        if (req.getParameter("submit") != null) {
            user = updateUserFromRequest(user, req);
            UserAccess.commitUser(user);
            //req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("editResult", "Details Saved");
        } else {
            req.setAttribute("userDetails", user);
        }
        RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + "myDiamm.jsp");
        reqDisp.forward(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DiammUserDTO diammUserDTO = new DiammUserDTO();

        if (req.getParameter("submit") != null) {
            diammUserDTO.setAgree1(req.getParameter("agree1"));
            diammUserDTO.setAgree2(req.getParameter("agree2"));
            diammUserDTO.setAgree3(req.getParameter("agree3"));
            diammUserDTO.setAgree4(req.getParameter("agree4"));
            diammUserDTO.setUsername(req.getParameter("username"));
            diammUserDTO.setDisplayName(req.getParameter("displayName"));
            diammUserDTO.setEmail(req.getParameter("email"));
            diammUserDTO.setAffiliation(req.getParameter("affiliation"));
            Map<String, String> errorMessages = UserAccess.registerNewUser(diammUserDTO);
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("registerSuccess", errorMessages.isEmpty() ? true : false);
        }

        req.setAttribute("userDetails", diammUserDTO);
        RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + "register.jsp");
        reqDisp.forward(req, resp);
    }

    private void retrievePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DiammUserDTO diammUserDTO = new DiammUserDTO();

        if (req.getParameter("submit") != null) {
            diammUserDTO.setEmail(req.getParameter("email"));
            Map<String, String> errorMessages = UserAccess.retrievePassword(diammUserDTO.getEmail());
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("retrieveSuccess", errorMessages.isEmpty() ? true : false);
        }

        req.setAttribute("userDetails", diammUserDTO);
        RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + "passwordRetrieve.jsp");
        reqDisp.forward(req, resp);
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DiammUserDTO diammUserDTO = new DiammUserDTO();
        diammUserDTO.setUsername(req.getRemoteUser());

        if (req.getParameter("submit") != null) {
            diammUserDTO.setPassword(req.getParameter("currentPassword"));
            diammUserDTO.setNewPassword1(req.getParameter("newPassword1"));
            diammUserDTO.setNewPassword2(req.getParameter("newPassword2"));
            Map<String, String> errorMessages = UserAccess.changePassword(diammUserDTO);
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("changeSuccess", errorMessages.isEmpty() ? true : false);
        }

        req.setAttribute("userDetails", diammUserDTO);
        RequestDispatcher reqDisp = req.getRequestDispatcher(getServletContext().getInitParameter("jspRoot") + "passwordChange.jsp");
        reqDisp.forward(req, resp);
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.getSession().invalidate(); //careful here
        resp.sendRedirect("../index.html");
    }
}