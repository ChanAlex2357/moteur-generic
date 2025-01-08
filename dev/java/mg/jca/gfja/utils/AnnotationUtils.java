package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {
    // Get all fields with a specific annotation
    public static List<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }
        return fields;
    }

    // Get all fields with any of the specified annotations
    public static List<Field> getFieldsWithAnnotations(Class<?> clazz, List<Class<? extends Annotation>> annotations) {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            for (Class<? extends Annotation> annotation : annotations) {
                if (field.isAnnotationPresent(annotation)) {
                    fields.add(field);
                    break;
                }
            }
        }
        return fields;
    }

    public static List<Field> getPrimitiveFields(Class<?> clazz,List<Class<? extends Annotation>> excluded){
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!PrimitiveUtils.isPrimitifType(field)) { break; }
            else if ( isAnnoted(field,excluded)) { break; }
            fields.add(field);
        }
        return fields;
    }
    public static boolean isAnnoted(Field field,List<Class<? extends Annotation>> excluded){
        if (excluded == null) {
            return false;
        }
        for (Class<? extends Annotation> annotation : excluded) {
            if (field.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        return false;
    }
}