package mg.jca.gfja.builder;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

import mg.jca.gfja.mapping.Champ;
import mg.jca.gfja.mapping.ClassMap;

public class ClassMapBuilder {
    
    public ClassMap getClassMap(ClassMap baseClass, ResultSet resultSet , Champ[] baseChamps) throws Exception {
        ClassMap instance = null;
        try {
            instance = baseClass.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new Exception("Erreur lors de la creation d'une instance de la class "+baseClass.getClass()+" pour recupere une ligne de resultset; Veuillez verifier si la class implemente bien un constructeur vide");
        }
        for (Champ champ : baseChamps) {
            Object value = champ.getValueFromResultSet(resultSet);
            champ.setRo
        }
        return instance;
    }
}
