/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hibernate;

import entities.*;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
/**
 *
 * @author venom
 */
public class FactoryProvider {
    static SessionFactory factory=null;
    public static SessionFactory getFactory(){
        try{
            
            Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/RentalProject?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "*123*VYBZ");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                //settings.put(Environment.DATASOURCE, "../Data.sql");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "none");

                configuration.setProperties(settings);
                
                configuration.addAnnotatedClass(User.class)   
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Agent.class)
                    .addAnnotatedClass(Bill.class)
                    .addAnnotatedClass(House.class)
                    .addAnnotatedClass(HouseOwner.class)
                    .addAnnotatedClass(HousePhotos.class)
                    .addAnnotatedClass(Tenants.class);;

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                System.out.println("Hibernate Java Config serviceRegistry created");
                factory = configuration.buildSessionFactory(serviceRegistry);
                return factory;
                
        /*StandardServiceRegistry standardRegistry
        = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        // Here we tell Hibernate that we annotated our User class
        MetadataSources sources = new MetadataSources( standardRegistry );
        sources.addAnnotatedClass( User.class )
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Agent.class)
                    .addAnnotatedClass(Bill.class)
                    .addAnnotatedClass(House.class)
                    .addAnnotatedClass(HouseOwner.class)
                    .addAnnotatedClass(HousePhotos.class)
                    .addAnnotatedClass(Tenants.class);
        Metadata metadata = sources.buildMetadata();
        if(factory==null){
            factory = metadata.buildSessionFactory();
            /*factory=new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Agent.class)
                    .addAnnotatedClass(Bill.class)
                    .addAnnotatedClass(House.class)
                    .addAnnotatedClass(HouseOwner.class)
                    .addAnnotatedClass(HousePhotos.class)
                    .addAnnotatedClass(Tenants.class).configure("hibernate.cfg.xml").buildSessionFactory();
        }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return factory;
    }
}
