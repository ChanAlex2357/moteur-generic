package mg.jca.gfja.builder;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

import mg.jca.gfja.mapping.Champ;
import mg.jca.gfja.mapping.ClassMap;

public class ClassMapBuilder {
    
    @SuppressWarnings("unchecked")
    public static <T extends ClassMap> T build(T baseClass, ResultSet resultSet, Champ[] baseChamps) throws Exception {
        T classMapInstance;
        try {
            classMapInstance = (T) baseClass.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
            throw new Exception("Erreur lors de la creation d'une instance de la class " + baseClass.getClass() + " pour recupere une ligne de resultset; Veuillez verifier si la class implemente bien un constructeur vide");
        }
        for (Champ champ : baseChamps) {
            Object value = champ.getValueFromResultSet(resultSet);
            champ.setValueToClassMap(classMapInstance, value);
        }
        return classMapInstance;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends ClassMap> T buildInstance(T source) throws Exception {
        return (T) source.getClass().getConstructor().newInstance();
    }

    public static <T extends ClassMap> T buildInstance(Class<T> source) throws Exception {
        return source.getConstructor().newInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T extends ClassMap> T buildInstance(String className) throws Exception {
        return buildInstance((Class<T>) Class.forName(className));
    }
}
