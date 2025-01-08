package mg.jca.gfja.utils;

import java.lang.annotation.Annotation;
import mg.jca.gfja.constants.AnnotationConstants;

public class MereUtils extends EntityUtils{
    // Check if a class has the Mere annotation
    public static Annotation isMere(Class<?> clazz) {
        return clazz.getAnnotation(AnnotationConstants.MERE_ANNOTATION);
    }

    public static Annotation isMere(Object object) {
        return isMere(object.getClass());
    }

}
