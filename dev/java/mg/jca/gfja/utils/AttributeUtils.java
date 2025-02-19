package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import mg.jca.gfja.annotations.Attribute;
import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.constants.AnnotationConstants;

public class AttributeUtils {
    // Check if a field has the Attribute annotation
    public static Annotation isAttribute(Field field) {
        
        Annotation annotation = field.getAnnotation(AnnotationConstants.ATTRIBUTE_ANNOTATION);
        if (annotation ==null) {
            annotation = isId(field);
        }
        return annotation;
    }

    // Check if a field has the Id annotation
    public static Annotation isId(Field field) {
        return field.getAnnotation(AnnotationConstants.ID_ANNOTATION);
    }

    // Get the field with the Id annotation from a class
    public static Field getId(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AnnotationConstants.ID_ANNOTATION)) {
                return field;
            }
        }
        return null;
    }

    // Get the value of the Id field from an object
    public static Object getId(Object obj) {
        Object id = null;
        Class<?> clazz = obj.getClass();
        if (EntityUtils.isEntity(clazz) != null) {
            Field idField = getId(clazz);
            if (idField != null) {
                try {
                    idField.setAccessible(true);
                    id = idField.get(obj);
                    idField.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    // Get attributes with specified annotations
    public static List<Field> getAttributes(Class<?> clazz) {
        return AnnotationUtils.getPrimitiveFields(clazz,null);
    }

    public static String getAttributeName(Annotation annotation) {
        String name = null;

        if (annotation == null) {
            return name;
        }

        if (annotation instanceof Id) {
            name = ((Id)annotation).name();
        }
        else if (annotation instanceof Attribute) {
            name = ((Attribute) annotation).name();
        }
        return name;
    }
}
