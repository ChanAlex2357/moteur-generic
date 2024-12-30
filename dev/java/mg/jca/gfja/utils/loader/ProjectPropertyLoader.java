package mg.jca.gfja.utils.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import mg.jca.gfja.constants.PathConstant;

public class ProjectPropertyLoader extends PropertiesLoader{
    public ProjectPropertyLoader() throws Exception {
        super();
    }

    @Override
    protected void setProperties() throws Exception {
        Properties  properties = new Properties();
        try (FileInputStream input = new FileInputStream(System.getProperty("user.dir") + java.io.File.separator + PathConstant.RESSOURCE_PATH + java.io.File.separator + PathConstant.PROPERTIES_FILE)) {
            properties.load(input);
            setProperties(properties);
        } catch (IOException e) {
            throw new Exception("Error while loading properties file", e);
        }
        setProperties(properties);
    }
    
}
