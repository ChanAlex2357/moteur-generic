package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import mg.jca.gfja.utils.AttributeUtils;

public class ChampType {
    private Annotation attributeAnnotation;
    private Field field;
    private Class<?> fieldType;
    private Class<?> parentClass;

    public ChampType(Field field){
        Annotation attributeAnnotation = AttributeUtils.isAttribute(field);
        setAttributeAnnotation(attributeAnnotation);
        setField(field);

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

    public Class<?> getParentClass() {
        return parentClass;
    }

    public void setParentClass(Class<?> parentClass) {
        this.parentClass = parentClass;
    }
}
