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
        System.out.println(query);
        try (PreparedStatement pStatement = conn.prepareStatement(query)) {
            classMap.mapValues(champs, pStatement,false);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                // Conversion des resultats en ClassMap
                results=castResult(classMap, champs, resultSet);
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
        String collumns = "";
        for (Champ champ : champs) {
            collumns += champ.getName();
        }
        String where = buildWhereClause(query, champs);
        if (afterWhere != null) {
            where += afterWhere;
        }
        return query+where; 

    }

    protected String buildWhereClause(String query , Champ[] champs ) {
        String where = " WHERE 1=1 ";
        for (Champ champ : champs) {
            if (champ.isNull()) {continue;}
            where += buildPreparedWhere(champ);
        }
        return where;
    }
    protected String buildPreparedWhere(Champ champ){
        return buildSimpleWhere("AND ", champ.getName()," = ","?");
    }
    protected String buildSimpleWhere(String pre ,String condField ,String condition , String value){
        return pre+condField+condition+value;
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
