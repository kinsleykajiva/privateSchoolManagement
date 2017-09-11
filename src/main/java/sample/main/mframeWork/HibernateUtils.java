package sample.main.mframeWork;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static sample.main.mUtility.mLocalStrings.DATABASE_SETTINGS;

/**This will handle the Hibernate configurations at run-time*/

public class HibernateUtils {

    private static SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); //hibernate config xml file name
        configuration.setProperty("hibernate.dialect" , "org.hibernate.dialect.SQLiteDialect");
        configuration.setProperty("connection.url" , "jdbc:sqlite:"+DATABASE_SETTINGS);

       // StandardServiceRegistryBuilder  registryBuilder = new StandardServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
       // sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
    }

    public static SessionFactory getSessionFactory () {
        return sessionFactory;
    }
}
