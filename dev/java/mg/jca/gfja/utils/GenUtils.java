package mg.jca.gfja.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import mg.jca.gfja.builder.ClassMapBuilder;
import mg.jca.gfja.mapping.Champ;
import mg.jca.gfja.mapping.ChampType;
import mg.jca.gfja.mapping.ClassMap;

public class GenUtils {
    public <T extends ClassMap> T[] searchEntities(ClassMap classMap , String afterWhere , Connection conn) throws Exception{
        T[] results = null;
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
        String where = buildWhereClause(query, champs);
        if (afterWhere != null) {
            where += afterWhere;
        }
        return query+where; 

    }

    protected String buildWhereClause(String query , Champ[] champs ) {
        String where = " WHERE 1=1 ";
        for (Champ champ : champs) {
            if (champ.isNull()) {
                continue;
            }
            where += buildPreparedWhere(champ);
        }
        return where;
    }
    protected String buildPreparedWhere(Champ champ){
        return buildSimpleWhere("AND ", champ.getName()," = ","? ");
    }
    protected String buildSimpleWhere(String pre ,String condField ,String condition , String value){
        return pre+condField+condition+value;
    }
    @SuppressWarnings("unchecked")
    protected <T extends ClassMap> T[] castResult(ClassMap classMap , Champ[] champs , ResultSet resultSet) throws Exception{
        List<ClassMap> classMaps = new ArrayList<>();
        while (resultSet.next()) {
            classMaps.add(ClassMapBuilder.build(classMap, resultSet, champs));
        }
        return (T[]) classMaps.toArray(new ClassMap[0]);
    }
    public ClassMap[] castResult(ClassMap classMap , ResultSet resultSet) throws Exception{
        return castResult(classMap,classMap.getChamps(),resultSet);
    }

    @SuppressWarnings("unchecked")
    public <T extends ClassMap> T instanceForm(ClassMap classMap,String[] cols,HttpServletRequest request) throws Exception{
        T instance = null;
        instance = (T) classMap.getNewInstance();
        for (String string : cols) {
            Champ champ = classMap.getChamp(string);
            if (champ == null) {
                continue;
            }
            champ.setValue(request.getParameter(string));
        }
        return instance;
    }

    public <T extends ClassMap> T instanceForm(ClassMap classMap,HttpServletRequest request) throws Exception{
        ChampType[] types = classMap.getChampsTypes();
        String[] cols = new String[types.length];
        for (int i = 0; i < cols.length; i++) {
            cols[i] = types[i].getName();
        }
        return instanceForm(classMap, cols, request);
    }
}
