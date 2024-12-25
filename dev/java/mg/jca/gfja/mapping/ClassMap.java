package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.jca.gfja.exception.dao.InsertionEntityException;
import mg.jca.gfja.utils.AttributeUtils;
import mg.jca.gfja.utils.EntityUtils;

public abstract class ClassMap {
    
    public ClassMap insertEntity(ClassMap classMap,Connection conn)throws Exception{
        // Verifier si c'est une entite
        Annotation entityAnnotation = EntityUtils.isEntity(classMap);
        if (entityAnnotation == null) {
            throw new InsertionEntityException(classMap);
        }
        String tableName = EntityUtils.getEntityName(classMap,entityAnnotation);
        Champ[] champs = getChampWithValue(classMap);
        isnertIntoTable(tableName, champs, conn);
        return classMap;
    }

    public int isnertIntoTable(String tableName , Champ[] champs , Connection conn)throws Exception{
        String query = "Insert into "+tableName;
        String cols = "(";
        String preparedValues = "(";

        for (Champ champ : champs) {
            if (champ == null) {continue;}
            cols += champ.getName() +",";
            preparedValues += "?"+",";
        }
        cols += ")";
        preparedValues += ")";
        query += cols+" values "+preparedValues;
        
        int inserted = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            mapValues(champs, preparedStatement);
            inserted = preparedStatement.executeUpdate();
        }
        return inserted;
    }

    public void mapValues(Champ[] champs , PreparedStatement preparedStatement) throws SQLException {
        int num = 1;
        for (Champ champ : champs) {
            preparedStatement.setObject(num,champ.getValue());
        }
    }

    public abstract void controle(Connection conn) throws Exception; 

    public ClassMap createObject(Connection conn) throws Exception{
        // Controlle des donnees
        controle(conn);
        // Insertion des donnee
        insertEntity(this, conn);
        return this;
    }

    public static ClassMap createObject(ClassMap classMap , Connection conn) throws Exception {
        return classMap.createObject(conn);
    }

    public Champ[] getChamps(ClassMap classMap) {
        List<Champ> champs = new ArrayList<Champ>();
        for (Field field : AttributeUtils.getAttributes(classMap.getClass())) {
            Champ champ  =  new Champ(field);
            champs.add(champ);        
        }
        return champs.toArray(new Champ[0]);
    }
    public Champ[] getChampWithValue(ClassMap classMap) throws IllegalArgumentException, IllegalAccessException {
        List<Champ> champs = new ArrayList<Champ>();
        for (Field field : AttributeUtils.getAttributes(classMap.getClass())) {
            Champ champ  =  new Champ(field);
            champ.getValueFrom(classMap);
            champs.add(champ);        
        }
        return champs.toArray(new Champ[0]);
    }
}
