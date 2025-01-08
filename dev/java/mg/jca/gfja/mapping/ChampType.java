package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import mg.jca.gfja.utils.AttributeUtils;

public class ChampType {
    private Annotation attributeAnnotation;
    private Field field;
    private Class<?> fieldType;
    private String name;


    public ChampType(Field field){
        Annotation attributeAnnotation = AttributeUtils.isAttribute(field);
        setAttributeAnnotation(attributeAnnotation);
        setField(field);
        setName();

    }

    public void setName(){
        String name  = AttributeUtils.getAttributeName(this.getAttributeAnnotation());
        if (name == null || name  == "" || name.length() == 0) {
            name = getField().getName();
        }
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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
        // System.out.println("SET FIELD : "+this.getField());
    }
    
    public Type getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

}
