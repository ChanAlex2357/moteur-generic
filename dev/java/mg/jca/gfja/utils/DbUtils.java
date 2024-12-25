package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mg.jca.gfja.utils.loader.PropertiesLoader;

public class DbUtils {
    
    public Connection getConn(String user, String pass) throws Exception {
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

    public Connection getConn() throws Exception {
        return getConn(getUSER(), getPASS());
    }
    public String getUSER() throws Exception {
        return PropertiesLoader.getProperty("db.user");
    }

    public String getPASS() throws Exception{
        return PropertiesLoader.getProperty("db.pass");
    }

    public String getDbUrl() throws Exception {
        return PropertiesLoader.getProperty("db.url");
    }
    public String getDriver() throws Exception {
        return PropertiesLoader.getProperty("db.driver");
    }
}

