package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
    
    public Connection getConn(String user, String pass) throws Exception {
        if (user == null) {
            user = getUSER();
        }
        if (pass == null) {
            pass = getPASS();
        }
        if (user == null || pass == null) {
            throw new SQLException("User or password is null");
        }
        return DriverManager.getConnection(getFullUrl(), user, pass);
    }

    public Connection getConn() throws SQLException {
        return getConn(getUSER(), getPASS());
    }

    protected String getFullUrl() throws Exception {
        return getDbUrl() + getDATABASE();
    }

    public String getUSER() throws Exception {
        return PropertiesLoader.getProperty("db.user");
    }

    public String getPASS() throws Exception{
        return PropertiesLoader.getProperty("db.pass");
    }

    public String getDATABASE()throws Exception {
        return PropertiesLoader.getProperty("db.database");
    }
    public String getDbUrl() {
        return PropertiesLoader.getProperty("db.url");
    }
}

