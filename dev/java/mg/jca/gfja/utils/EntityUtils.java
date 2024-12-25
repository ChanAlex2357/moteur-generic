package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;
import mg.jca.gfja.constants.AnnotationConstants;

public class EntityUtils {
    // Check if a class has the Entity annotation
    public static Annotation isEntity(Class<?> clazz) {
        return clazz.getAnnotation(AnnotationConstants.ENTITY_ANNOTATION);
    }
}
