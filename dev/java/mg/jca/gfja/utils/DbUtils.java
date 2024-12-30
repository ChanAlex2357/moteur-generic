package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mg.jca.gfja.utils.loader.ProjectPropertyLoader;

public class DbUtils {
    public static ProjectPropertyLoader projectProperty = null;
    public Connection getConn(String user, String pass)  {
        Connection conn = null;
        try {
            if (user == null) {
                user = getUSER();
            }
            if (pass == null) {
                pass = getPASS();
            }
            if (user == null || pass == null) {
                throw new SQLException("User or password is null");
            }
            conn = DriverManager.getConnection(getDbUrl(), user, pass);
        } catch (Exception e) {
            System.out.println("Error while loading connection ...");
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getConn()  {
        return getConn(getUSER(), getPASS());
    }
    public String getUSER()  {
        return projectProperty.getProperty("db.user");
    }

    public String getPASS() {
        return projectProperty.getProperty("db.pass");
    }

    public String getDbUrl()  {
        return projectProperty.getProperty("db.url");
    }
    public String getDriver()  {
        return projectProperty.getProperty("db.driver");
    }

    public static ProjectPropertyLoader getProjectProperty(){
        if (DbUtils.projectProperty ==  null) {
            try {
                DbUtils.projectProperty = new ProjectPropertyLoader();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return projectProperty;
    }

    public static void setProjectProperty(ProjectPropertyLoader projectProperty) {
        DbUtils.projectProperty = projectProperty;
    }
}

