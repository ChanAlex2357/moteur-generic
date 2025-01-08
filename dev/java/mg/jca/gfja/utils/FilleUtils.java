package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;
import mg.jca.gfja.constants.AnnotationConstants;

public class FilleUtils {
    // Check if a class has the Fille annotation
    public static Annotation isFille(Class<?> clazz) {
        return clazz.getAnnotation(AnnotationConstants.FILLE_ANNOTATION);
    }

    public static Annotation isFille(Object object) {
        return isFille(object.getClass());
    }
}
