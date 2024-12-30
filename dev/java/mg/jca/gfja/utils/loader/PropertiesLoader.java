package mg.jca.gfja.utils.loader;

import java.util.Properties;

public abstract class PropertiesLoader {
    private Properties properties;
    public PropertiesLoader() throws Exception{
        setProperties();
    }
    protected abstract void setProperties() throws Exception;
    public String getProperty(String key) {
        return getProperties().getProperty(key);
    }
    public Properties getProperties() {
        return properties;
    }
    protected void setProperties(Properties properties) {
        this.properties = properties;
    }
}
