package mg.jca.gfja.loader;

import java.util.Properties;

import mg.jca.gfja.constants.PathConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RessourceLoader {
    private Properties properties;
    /**
     * Constructeur par défaut qui initialise l'objet Properties.
     */
    public RessourceLoader() {
        properties = new Properties();
    }

    private String getFullPath(String file) {
        return PathConstant.RESSOURCE_PATH +"/"+file;
    }

    /**
     * Charge un fichier properties depuis un chemin donné.
     * 
     * @param filePath Chemin vers le fichier properties.
     * @throws IOException Si une erreur se produit lors du chargement du fichier.
     */
    public void loadProperties(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(getFullPath(filePath))) {
            properties.load(inputStream);
        }
    }

    /**
     * Récupère la valeur d'une propriété donnée.
     * 
     * @param key Clé de la propriété.
     * @return Valeur de la propriété ou null si la clé n'existe pas.
     */
    public String getProperty(String key) {
        return getProperty(key,null);
    }

    /**
     * Récupère la valeur d'une propriété ou une valeur par défaut si la clé n'existe pas.
     * 
     * @param key          Clé de la propriété.
     * @param defaultValue Valeur par défaut à retourner si la clé n'existe pas.
     * @return Valeur de la propriété ou valeur par défaut.
     */
    public String getProperty(String key, String defaultValue) {
        if (this.properties == null) {
            throw new IllegalAccessError();
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Vérifie si une clé donnée existe dans les propriétés.
     * 
     * @param key Clé à vérifier.
     * @return true si la clé existe, false sinon.
     */
    public boolean containsKey(String key) {
        if (this.properties == null) {
            throw new IllegalAccessError();
        }
        return properties.containsKey(key);
    }
}
