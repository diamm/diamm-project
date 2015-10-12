package uk.ac.kcl.cch.diamm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static SessionFactory factory;

    public static Configuration getInitializedConfiguration() {
        AnnotationConfiguration config = new AnnotationConfiguration();
        /*config.addAnnotatedClass(Alcity.class);
        config.addAnnotatedClass(Alcountry.class);
        config.addAnnotatedClass(ARCHIVE.class);
        config.addAnnotatedClass(Item.class);
        config.addAnnotatedClass(Source.class);

        //Subject Term classes
        config.addAnnotatedClass(Term.class);
        config.addAnnotatedClass(Termrelationship.class);
        config.addAnnotatedClass(Relationshiptype.class);
        config.addAnnotatedClass(Termpanelunit.class);*/

        config.configure("hibernate.cfg.xml").
                buildSessionFactory();
        return config;
    }

   

    public static Session getSession() {
        if (factory == null) {
            Configuration config = HibernateUtil.getInitializedConfiguration();
            factory = config.buildSessionFactory();
        }
        Session hibernateSession = factory.getCurrentSession();

        return hibernateSession;
    }

    public static void closeSession() {
        HibernateUtil.getSession().close();
    }

    public static Session beginTransaction() {
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSession();
        hibernateSession.beginTransaction();
        return hibernateSession;
    }

    public static void commitTransaction() {
        HibernateUtil.getSession().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        HibernateUtil.getSession().getTransaction().rollback();
    }
}