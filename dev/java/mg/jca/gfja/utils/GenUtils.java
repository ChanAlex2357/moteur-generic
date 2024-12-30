package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.jca.gfja.builder.ClassMapBuilder;
import mg.jca.gfja.mapping.Champ;
import mg.jca.gfja.mapping.ClassMap;

public class GenUtils {
    public ClassMap[] searchEntities(ClassMap classMap , String afterWhere , Connection conn) throws Exception{
        ClassMap[] results = null;
        String  table = classMap.getEntityName();
        Champ[] champs = classMap.getChamps();
        /// Executer la requete
        String query = buildSelectQuery(table, champs, afterWhere);
        try (PreparedStatement pStatement = conn.prepareStatement(query)) {
            classMap.mapValues(champs, pStatement);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                // Conversion des resultats en ClassMap
                castResult(classMap, champs, resultSet);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la requete de recherche '"+query+"' : "+e.getMessage());
        }
        return results;
    }
    protected String buildSelectQuery(String table , Champ[] champs , String afterWhere ) {
        String query = "Select * from "+table;
        String where = buildWhereClause(query, champs) + afterWhere;
        return query+where; 

    }
    protected String buildWhereClause(String query , Champ[] champs ) {
        String where = " WHERE 1=1 ";
        for (Champ champ : champs) {
            where += "AND "+champ.getName()+" = ? ";
        }
        return where;
    }
    protected ClassMap[] castResult(ClassMap classMap , Champ[] champs , ResultSet resultSet) throws Exception{
        List<ClassMap> classMaps = new ArrayList<>();
        while (resultSet.next()) {
            classMaps.add(ClassMapBuilder.build(classMap, resultSet, champs));
        }
        return classMaps.toArray(new ClassMap[0]);
    }
    public ClassMap[] castResult(ClassMap classMap , ResultSet resultSet) throws Exception{
        return castResult(classMap,classMap.getChamps(),resultSet);
    }
}
