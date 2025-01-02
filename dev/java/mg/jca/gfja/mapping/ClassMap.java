package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.builder.ClassMapBuilder;
import mg.jca.gfja.exception.dao.InsertionEntityException;
import mg.jca.gfja.exception.dao.NotChampException;
import mg.jca.gfja.utils.AttributeUtils;
import mg.jca.gfja.utils.EntityUtils;
import mg.jca.gfja.utils.GenUtils;
import mg.jca.gfja.utils.Utilitaire;

public abstract class ClassMap {
    protected Champ[] champs;
    protected Champ idChamp;
    protected String deafaultSeq;
    protected String defaultProcedure;

    protected int longuerClePrimaire;
    public ClassMap() {
        longuerClePrimaire = 6;
        deafaultSeq = "SEQ";
        defaultProcedure =  "GET_SEQ";
    }

    public int insertEntity(Connection conn)throws Exception{
        // Verifier si c'est une entite
        Annotation entityAnnotation = EntityUtils.isEntity(this);
        if (entityAnnotation == null) {
            throw new InsertionEntityException(this);
        }
        String tableName = EntityUtils.getEntityName(this,entityAnnotation);
        Champ[] champs = getChamps();
        controllerPrimaryKey(conn);
        return isnertIntoTable(tableName, champs, conn);
    }
    
    private void controllerPrimaryKey(Connection conn) throws Exception {
        
        try {
            Object pkValue = this.getIdChamp().getValue(); 
            if ( pkValue == null) {
                String pk = this.makePK(conn);
                setId(pk);
            }
        } catch (Exception e) {
            // idChamp null
        }
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
        
        // System.out.println("INSERT QUERY : "+query);
        int inserted = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            mapValues(champs, preparedStatement);
            inserted = preparedStatement.executeUpdate();
        }catch (Exception e) {
            throw new Exception("Erreur lors de l'execution de la requete d'insertion de l'entite '"+tableName+"' : "+e.getMessage());
        }
        return inserted;
    }

    public void mapValues(Champ[] champs , PreparedStatement preparedStatement) throws SQLException {
        mapValues(champs, preparedStatement, true);
    }

    public void mapValues(Champ[] champs , PreparedStatement preparedStatement,boolean acceptNull) throws SQLException {
        int num = 1;
        for (Champ champ : champs) {
            Object value = champ.getValue();
            if (!acceptNull && champ.isNull()) {continue;}
            preparedStatement.setObject(num,value);
            num +=1;
        }
    }

    public abstract void controle(Connection conn) throws Exception; 

    public ClassMap save(Connection conn) throws Exception{
        // Controlle des donnees
        controle(conn);
        // Insertion des donnee
        insertEntity(conn);
        return this;
    }

    public static Champ[] getChamps(ClassMap classMap) throws Exception {
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
    public static Champ[] getChampsNotNull(ClassMap classMap) throws Exception {
        List<Champ> champs = new ArrayList<Champ>();
        for (Field field : AttributeUtils.getAttributes(classMap.getClass())) {
            Champ champ;
            try {
                champ = new Champ(classMap,field);
                if (champ.getValue() != null) {
                    champs.add(champ);        
                }
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

    public String makePK(Connection c) throws Exception {
        Id idAnnotation = (Id) this.getIdChamp().getAttributeAnnotation();
        int maxSeq = Utilitaire.getMaxSeq( idAnnotation.procedure() , c);
        String nombre = Utilitaire.completerInt(longuerClePrimaire, maxSeq);
        return String.valueOf(idAnnotation.seq()) + String.valueOf(nombre);
    }

    public Champ[] getChamps() throws Exception {
        if (this.champs == null) {
            setChamps( ClassMap.getChamps(this));
        }
        return champs;
    }

    public void setChamps(Champ[] champs) {
        this.champs = champs;
    }

    public Champ getIdChamp() throws Exception{
        return idChamp;
    }
    public void setIdChamp(Champ idChamp) {
        this.idChamp = idChamp;
    }

    public String getEntityName() {
        return EntityUtils.getEntityName(this);
    }

    public static ClassMap getById(ClassMap classMap , String id , Connection conn) throws Exception{
        ClassMap ref = classMap.getNewInstance();
        ref.setId(id);
        ClassMap[] results =  new GenUtils().searchEntities(ref,null,conn);
        if (results.length > 0 ) {
            return results[0];
        }
        return null;
    }
    public ClassMap getById(Connection conn) throws Exception{
        return ClassMap.getById(this,this.getId(), conn);
    }

    public String getId() throws Exception{
        return (String)this.getIdChamp().getValue();
    }
    public ClassMap getNewInstance() throws Exception{
        return ClassMapBuilder.buildInstance(this);
    }

    public void setId(String id) throws Exception{
        if (getIdChamp() == null) {
            getChamps();
        }
        Champ idChamp = getIdChamp();
        if (idChamp != null) {
            idChamp.setValue(id);
        }
    }
    public ClassMap[] getAll(Connection conn) throws Exception  {
        ClassMap[] all = new GenUtils().searchEntities( getNewInstance() ,null, conn);
        return all;
    }
}
