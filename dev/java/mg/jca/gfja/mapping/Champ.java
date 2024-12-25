package mg.jca.gfja.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.utils.AttributeUtils;

public class Champ {
    
    
    public Champ (Field field) {
        // recuperer le nom du champ
        Annotation attributeAnnotation = AttributeUtils.isAttribute(field);
        if (attributeAnnotation == null ) {
            // throw    
        }
        setAttributeAnnotation(attributeAnnotation);
        setField(field);
        setName();
        setPrimarykey();
    }
    
    protected void setPrimarykey(){
        if (this.getAttributeAnnotation() instanceof Id) {
            makePrimaryKey();
        }
    }
    protected void makePrimaryKey() {
        this.primarykey = true;
    }

    private Annotation attributeAnnotation;
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
        return value;
    }

    public void getValueFrom(Object source) throws IllegalArgumentException, IllegalAccessException{
        this.setValue(field.get(source));
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(boolean primarykey) {
        this.primarykey = primarykey;
    }
}
