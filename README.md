# Rental-Management-System
A java web application to manage rental apartments with both email, sql, payment, user and admin capabilities. We also have ajax support.
It was tested on Tomcat 9.
All required libraries are available in the libs folder.
To update or create database with hibernate, make sure you check the configurations in /src/java/Hibernate/FactoryProvider.java

```java
public class FactoryProvider {
    static SessionFactory factory=null;
    public static SessionFactory getFactory(){
        try{
            
            Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/RentalProject?useSSL=false"); // Change this either port or Database name{RentalProject}
                settings.put(Environment.USER, "root"); // change this
                settings.put(Environment.PASS, "password");// change this
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                //settings.put(Environment.DATASOURCE, "../Data.sql");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "none"); // Change this to create if you're running for the first time

                configuration.setProperties(settings);
/// Other codes
        }catch(Exception e){
            e.printStackTrace();
        }
        return factory;
    }
}
```
Remember to change the highlighted parts in comments.
Use Netbeans as the IDE and resolve the library paths and java path.

```
