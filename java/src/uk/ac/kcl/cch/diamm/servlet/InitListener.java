package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.HibernateException;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 22-Nov-2010
 * Time: 16:32:26
 * To change this template use File | Settings | File Templates.
 */
public class InitListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent event) {
        //event.getServletContext().removeAttribute("tetheredManager");
       // event.getServletContext()
        HibernateUtil.closeSession();
    }

    public void contextInitialized(ServletContextEvent event) {
        /*try {
            HibernateUtil.beginTransaction();
            //ImageSearch.verifyImages();
            DIAMMFacetManager m = new DIAMMFacetManager();
            event.getServletContext().setAttribute("tetheredManager", m);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }*/

    }



}
