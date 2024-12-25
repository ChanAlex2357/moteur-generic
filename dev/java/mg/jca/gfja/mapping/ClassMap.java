package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.jca.gfja.exception.dao.InsertionEntityException;
import mg.jca.gfja.exception.dao.NotChampException;
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
        Champ[] champs = getChamps(classMap);
        isnertIntoTable(tableName, champs, conn);
        return classMap;
    }

    public int isnertIntoTable(String tableName , Champ[] champs , Connection conn)throws Exception{
        String query = "Insert into "+tableName;
        String cols = "(";
        String preparedValues = "(";

        for (int i = 0; i < champs.length; i++) {
            Champ champ = champs[i];
            if (champ == null) {continue;}
            String post = "";
            if (i < champs.length -1) {
                post = ",";
            }
            cols += champ.getName() +post;
            preparedValues += "?"+post;
        }
        cols += ")";
        preparedValues += ")";
        query += cols+" values "+preparedValues;
        
        System.out.println("INSERT QUERY : "+query);
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
            Object value = champ.getValue();
            preparedStatement.setObject(num,value);
            num +=1;
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

    public Champ[] getChamps(ClassMap classMap) throws IllegalAccessException {
        List<Champ> champs = new ArrayList<Champ>();
        for (Field field : AttributeUtils.getAttributes(classMap.getClass())) {
            Champ champ;
            try {
                champ = new Champ(classMap,field);
                champs.add(champ);        
            } catch (NotChampException e) {
                // Skip for not a champ
                continue;
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (IllegalAccessException e) {
                throw e;
            }
        }
        return champs.toArray(new Champ[0]);
    }
}
