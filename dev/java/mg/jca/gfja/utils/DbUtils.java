package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mg.jca.gfja.utils.loader.ProjectPropertyLoader;

public class DbUtils {
    private static ProjectPropertyLoader projectProperty = null;
    private static String USER = null;
    private static String PASS = null;
    private static String URL = null;
    public DbUtils(String url,String user , String pass){
        this(user, pass);
        setURL(url);
    }
    public DbUtils(String user, String pass) {
        setUSER(user);
        setPASS(pass);
    }
    public DbUtils(){}

    private Connection getConn(String user, String pass)  {
        Connection conn = null;
        try {
            conn = getPureConn(user, pass);
        } catch (Exception e) {
            System.out.println("Error while loading connection ...");
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getPureConn(String user,String pass)throws Exception{
        Connection conn = null;
        if (user == null || pass == null) {
            throw new SQLException("User or password is null");
        }
        conn = DriverManager.getConnection(getUrl(), user, pass);
        return conn;
    }
    public Connection getPureConn()throws Exception{
        return getPureConn(getUSER(), getPASS());
    }

    public Connection getConn()  {
        return getConn(getUSER(), getPASS());
    }
    private String getUSER()  {
        if (DbUtils.USER == null) {
            DbUtils.USER =getProjectProperty().getProperty("db.user");
        }
        return DbUtils.USER;
    }

    private String getPASS() {
        if (DbUtils.PASS == null) {
            DbUtils.PASS = getProjectProperty().getProperty("db.pass");
        }
        return DbUtils.PASS;
    }

    private String getUrl()  {
        if (DbUtils.URL == null) {
            DbUtils.URL = getProjectProperty().getProperty("db.url");

        }
        return DbUtils.URL;
    }

    private static ProjectPropertyLoader getProjectProperty(){
        if (DbUtils.projectProperty ==  null) {
            try {
                setProjectProperty(new ProjectPropertyLoader());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return projectProperty;
    }

    private static void setProjectProperty(ProjectPropertyLoader projectProperty) {
        DbUtils.projectProperty = projectProperty;
    }

    private static void setUSER(String uSER) {
        USER = uSER;
    }

    private static void setPASS(String pASS) {
        PASS = pASS;
    }

    private static void setURL(String uRL) {
        URL = uRL;
    }

    public String str(){
        return "URL:"+this.getUrl()+" | USER:"+this.getUSER()+" | Pass:"+this.getPASS();
    }
}

