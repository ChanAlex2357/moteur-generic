package mg.jca.gfja.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.exception.dao.NotChampException;
import mg.jca.gfja.exception.dao.ViolationPrimaryKeyException;
import mg.jca.gfja.utils.AttributeUtils;

public class Champ {
    
    
    public Champ( ClassMap source,Field field) throws NotChampException, IllegalArgumentException, IllegalAccessException, ViolationPrimaryKeyException {
        // recuperer le nom du champ
        Annotation attributeAnnotation = AttributeUtils.isAttribute(field);
        setParentObject(source);
        if (attributeAnnotation == null ) {
            throw new NotChampException(this.getParentClass(),field);
        }
        setAttributeAnnotation(attributeAnnotation);
        setField(field);
        setName();
        setPrimarykey();
        setValue();
    }
    
    private void setValue() throws IllegalArgumentException, IllegalAccessException {
        Object value = getValueFromParent();
        this.setValue(value);
    }
        
    protected void setPrimarykey() throws ViolationPrimaryKeyException{
        Champ id = this.getParentObject().getIdChamp();
        
        if (this.getAttributeAnnotation() instanceof Id && id == null) {
            makePrimaryKey();
            this.getParentObject().setIdChamp(this);
        }
        else if (this.getAttributeAnnotation() instanceof Id && id != null) {
            throw new ViolationPrimaryKeyException(id, this);
        }
    }
    protected void makePrimaryKey() {
        this.primarykey = true;
    }

    private Annotation attributeAnnotation;
    private ClassMap parentObject;
    private Class<?> parentClass;
    private Field field;
    private String name;
    private Class<?> fieldType;
    private Object value;
    private boolean primarykey = false;

    public Annotation getAttributeAnnotation() {
        return attributeAnnotation;
    }

    public void setAttributeAnnotation(Annotation attributeAnnotation) {
        this.attributeAnnotation = attributeAnnotation;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
        setFieldType(field.getType());
    }
    
    public void setName(){
        String name  = AttributeUtils.getAttributeName(this.getAttributeAnnotation());
        if (name == null || name  == "" || name.length() == 0) {
            name = field.getName();
        }
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getValueFromParent() throws IllegalArgumentException, IllegalAccessException{
        field.setAccessible(true);
        Object value = field.get(this.getParentObject());
        field.setAccessible(false);
        return value;
    }
    
    private void setValueToParent() throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        this.getField().set(parentObject, this.getValue());
        field.setAccessible(false);
    }
    private void setValue(Object value) {
        this.value = value;
    }
    public void setIdValue(Object value) throws IllegalArgumentException, IllegalAccessException{
        Object idValue = getValueFromParent();
        if (primarykey && idValue == null && value != null) {
            this.setValue(value);
            setValueToParent();
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
        this.parentClass = parentClass;
    }

    public ClassMap getParentObject() {
        return parentObject;
    }

    public void setParentObject(ClassMap parentObject) {
        this.parentObject = parentObject;
        setParentClass(parentObject.getClass());
    }
}
