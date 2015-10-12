package uk.ac.kcl.cch.diamm.tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.model.DiammUser;

public class UserDisplayName extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
    	PageContext pageContext = (PageContext) getJspContext();
    	String username = ((HttpServletRequest) pageContext.getRequest()).getRemoteUser();
    	
    	if (username != null) {
    		DiammUserDAO  userDao = DAOFactory.getFactory().getDiammUserDAO();
    		userDao.beginTransaction();
    		DiammUser user = userDao.findByUsername(username);
        	userDao.commitTransaction();
    		pageContext.getOut().print(user.getDisplayName());	
    	}
    }
}
