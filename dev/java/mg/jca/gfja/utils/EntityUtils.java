package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;

import mg.jca.gfja.annotations.Entity;
import mg.jca.gfja.constants.AnnotationConstants;

public class EntityUtils {
    // Check if a class has the Entity annotation
    public static Annotation isEntity(Class<?> clazz) {
        return clazz.getAnnotation(AnnotationConstants.ENTITY_ANNOTATION);
    }

    public static Annotation isEntity(Object object) {
        return isEntity(object.getClass());
    }

    /// GET ENTITY NAME
    private static String getEntityName(Annotation entityAnnotation) {
        String name = null;
        if (entityAnnotation instanceof Entity) {
            name = ((Entity) entityAnnotation).name();
        }
        return name;
    }
    public static String getEntityName(Class<?> clazz , Annotation annotation){
        String name = getEntityName(annotation);
        if (name == null || name == "" || name.length() == 0) {
            name = clazz.getSimpleName();            
        }
        return name;
    }
    public static String getEntityName(Object obj , Annotation annotation){
        return getEntityName(obj.getClass(), annotation);
    }
    public static String getEntityName(Object object)
    {
        return getEntityName(object.getClass());
    }
    public static String getEntityName(Class<?> clazz)
    {
        Annotation eAnnotation = isEntity(clazz);
        return getEntityName(clazz, eAnnotation);
    }
}
