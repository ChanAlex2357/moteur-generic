package mg.jca.gfja.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.annotation.Annotation;

import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.exception.dao.NotCompatibleClassMapException;
import mg.jca.gfja.exception.dao.ViolationPrimaryKeyException;

public class Champ {
    ChampType champType ;
    
    
    public Champ( ClassMap source,Field field) throws Exception {
        // recuperer le nom du champ
        setChampType(new ChampType(field));
        setParentObject(source);
        // if (attributeAnnotation == null ) {
        //     throw new NotChampException(this.getParentClass(),field);
        // }
        setPrimarykey();
        setValue();
    }
    
    private void setValue() throws Exception {
        Object value = getValueFromParent();
        this.setValue(value);
    }
        
    protected void setPrimarykey() throws Exception{
        Champ id = this.getParentObject().getIdChamp();
        
        // System.out.println("ID CHAMPPPPPPP : ");
        if (this.getAttributeAnnotation() instanceof Id && id == null) {
            makePrimaryKey();
            this.getParentObject().setIdChamp(this);
        }
        else if (this.getAttributeAnnotation() instanceof Id && id != null) {
            throw new ViolationPrimaryKeyException(id, this);
        }
        // System.out.println("PRIMARY : "+this.isPrimarykey());
    }
    protected void makePrimaryKey() {
        this.primarykey = true;
    }

    public String isNullString(){
        return this.getName()+" : "+this.isNull();
    }
    
    private ClassMap parentObject;
    private Class<?> parentClass;
    private Object value;
    private boolean primarykey = false;
    private boolean isNull = false;

    public Annotation getAttributeAnnotation() {
        return getChampType().getAttributeAnnotation();
    }

    public Field getField() {
        return getChampType().getField();
    }
    
    


    public Type getFieldType() {
        return this.getChampType().getFieldType();
    }

    public Object getValue() {
        return this.value;
    }

    public Object getValueFromParent() throws IllegalArgumentException, IllegalAccessException{
        setAccessibleField();
        // System.out.println("FIELD :"+this.getField());
        Object value = getField().get(this.getParentObject());
        setUnaccessibleField();
        return value;
    }
    public void setValueToClassMap(ClassMap classMap,Object value) throws NotCompatibleClassMapException, IllegalArgumentException, IllegalAccessException{
        if (!classMap.getClass().equals( this.getParentClass()) ) {
            throw new NotCompatibleClassMapException("Champ.setValueToClassMap pour inserer une valeur de champ dans une classMap", this.getParentObject(),classMap);
        }
        setAccessibleField();
        this.getField().set(classMap,value);
        setUnaccessibleField();
    }
    
    private void setValueToParent() throws IllegalArgumentException, IllegalAccessException {
        setAccessibleField();
        this.getField().set(parentObject, this.getValue());
        setUnaccessibleField();
    }

    private void setAccessibleField(){
        this.getField().setAccessible(true);
    }
    private void setUnaccessibleField(){
        this.getField().setAccessible(true);
    }
    public void setValue(Object value) throws Exception{
        this.value = value;
        controleParentValue();
        switchNullState();
    }
    protected void controleParentValue() throws Exception {
        Object parentValue = getValueFromParent();
        try {
            if (!parentValue.equals(value)) {
                setValueToParent();
                // System.out.println("Update "+this.getName()+" value on "+this.getParentObject());
            }
        } catch (NullPointerException e) { 
            if (value != null) {
                setValueToParent();
            }
        }
    }

    public boolean isPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(boolean primarykey) {
        this.primarykey = primarykey;
    }

    public Class<?> getParentClass() {
        return parentClass;
    }

    public void setParentClass(Class<?> parentClass) {
        if (this.parentClass == null) {
            this.parentClass = this.parentObject.getClass();
        }
        this.parentClass = parentClass;
    }

    public ClassMap getParentObject() {
        return parentObject;
    }

    public void setParentObject(ClassMap parentObject) {
        this.parentObject = parentObject;
        setParentClass(parentObject.getClass());
    }

    public Object getValueFromResultSet(ResultSet resultSet) throws SQLException{
        String colName = this.getName();
        if (this.getFieldType().equals(String.class)) {
            return resultSet.getString(colName);
        }
        else if (this.getFieldType().equals(double.class) || this.getFieldType().equals(Double.class)) {
            return resultSet.getDouble(colName);
        }
        else if (this.getFieldType().equals(Date.class)){
            return resultSet.getDate(colName);
        }
        else if (this.getFieldType().equals(int.class) ||this.getFieldType().equals(Integer.class)){
            return resultSet.getInt(colName);
        }
        return resultSet.getObject(this.getName());
    }

    public boolean isNull() {
        return isNull;
    }

    public String getName(){
        return this.getChampType().getName();
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    private void nullState(){setNull(true);}
    private void notNullState(){setNull(false);}
    private void switchNullState(){
        if (getValue() == null) {
            nullState();
            return;        
        }
        else {
            notNullState();
        }
        try {
            if (getFieldType().equals(String.class) && (getValue() == "")) {
                nullState();
            }
            else if (getFieldType().equals(Integer.class ) || getFieldType().equals(Double.class)) {
                if ((Double)getValue() == 0) {
                    nullState();
                }
            }
            else if (getFieldType().equals( int.class ) || getFieldType().equals(double.class)) {
                if ((Double)getValue() == 0) {
                    nullState();
                }
            }
            else {
                notNullState();
            }
        } catch (Exception e) {
            nullState();
        }

    }

    public ChampType getChampType() {
        return champType;
    }

    public void setChampType(ChampType champType) {
        this.champType = champType;
    }
}
