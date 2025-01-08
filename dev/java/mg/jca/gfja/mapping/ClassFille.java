package mg.jca.gfja.mapping;

import java.lang.annotation.Annotation;
import java.sql.Connection;

import mg.jca.gfja.annotations.Fille;
import mg.jca.gfja.builder.ClassMapBuilder;
import mg.jca.gfja.utils.FilleUtils;
import mg.jca.gfja.utils.GenUtils;

public abstract class ClassFille extends ClassMap {
    private Fille getFilleAnnotation() throws Exception{
        Annotation filleAnnotation = FilleUtils.isFille(this);
        if (filleAnnotation == null) {
            throw new Exception("The class "+this.getClass()+" is not annoted whith fille annotation");
        }
        return (Fille)filleAnnotation;
    }
    public ClassMere getMere(Connection conn) throws Exception {
        // Recupere la configuration
        Fille filleAnnotation = getFilleAnnotation();
        // Cree la condition de recherche
        ClassMere mere = null;
        try {
            ClassMere ref = (ClassMere) ClassMapBuilder.buildInstance(filleAnnotation.classmere());
            ClassMap[] classMaps = new GenUtils().searchEntities(ref,null, conn);
            if (classMaps.length > 0) {
                mere = (ClassMere)classMaps[0];
            }
        } catch (Exception e) {
            throw new Exception("The ClassMere ref "+filleAnnotation.classmere()+" for the ClassFille "+this.getClass()+" cannot be builded :: ",e);            
        }
        return mere;
    }
}
