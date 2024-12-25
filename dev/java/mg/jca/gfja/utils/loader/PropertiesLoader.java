package mg.jca.gfja.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties properties = new Properties();

    protected static void setProperties() throws Exception {
        Properties  properties = new Properties();
        try (FileInputStream input = new FileInputStream(System.getProperty("user.dir") + java.io.File.separator + PathConstant.RESSOURCE_PATH + java.io.File.separator + PROPERTIES_FILE)) {
            properties.load(input);
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
