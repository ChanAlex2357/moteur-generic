package mg.jca.gfja.exception.dao;

import java.lang.reflect.Field;

import mg.jca.gfja.exception.SimpleException;

public class NotChampException extends SimpleException{
    
    public NotChampException(Class<?> clazz,Field field){
        super();
        setField(field);
        setClazz(clazz);
    }
    private Field field;
    Class<?> clazz;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    @Override
    public String getBody() {
        return "The field "+this.getField().getName()+" in the class "+this.getClazz().getName()+" is  not an attribute";
    }
    
}
