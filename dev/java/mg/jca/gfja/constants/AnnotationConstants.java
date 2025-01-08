package mg.jca.gfja.constants;

import mg.jca.gfja.annotations.Attribute;
import mg.jca.gfja.annotations.Entity;
import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.annotations.Fille;
import mg.jca.gfja.annotations.Mere;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class AnnotationConstants {
    public static final Class<Entity> ENTITY_ANNOTATION = Entity.class;
    public static final Class<Attribute> ATTRIBUTE_ANNOTATION = Attribute.class;
    public static final Class<Id> ID_ANNOTATION = Id.class;
    public static final Class<Fille> FILLE_ANNOTATION = Fille.class;
    public static final Class<Mere> MERE_ANNOTATION = Mere.class;

    public static final List<Class<? extends Annotation>> ATTRIBUTE_ANNOTATIONS = Arrays.asList(
        Attribute.class,
        Id.class
    );
}
