package mg.jca.gfja.utils.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import mg.jca.gfja.constants.PathConstant;

public class PropertiesLoader {
    private static Properties properties;

    protected static void setProperties() throws Exception {
        System.out.println("Setting properties ...");
        Properties  properties = new Properties();
        try (FileInputStream input = new FileInputStream(System.getProperty("user.dir") + java.io.File.separator + PathConstant.RESSOURCE_PATH + java.io.File.separator + PathConstant.PROPERTIES_FILE)) {
            properties.load(input);
            setProperties(properties);
        } catch (IOException e) {
            throw new Exception("Error while loading properties file", e);
        }
        setProperties(properties);
    }
    public static String getProperty(String key) throws Exception {
        if (properties == null) {
            setProperties();
        }
        return getProperties().getProperty(key);
    }
    public static Properties getProperties() {
        return properties;
    }
    protected static void setProperties(Properties properties) {
        PropertiesLoader.properties = properties;
    }
}
