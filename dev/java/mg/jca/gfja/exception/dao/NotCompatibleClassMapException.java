package mg.jca.gfja.exception.dao;

import mg.jca.gfja.exception.CustomableException;
import mg.jca.gfja.mapping.ClassMap;

public class NotCompatibleClassMapException extends CustomableException {
    private ClassMap classMap1;
    private ClassMap classMap2;
    private String operation;
    public NotCompatibleClassMapException(String operation,ClassMap map1 , ClassMap map2) {
        super();
        setClassMap1(map1);
        setClassMap2(map2);
        setOperation(operation);
    }
    
    @Override
    public String getBody() {
        return " ; Les deux class map ne sont pas de la meme instance";
    }
    @Override
    public String getPre() {
        return "Impossible de realiser l'operation : "+this.getOperation();
    }

    @Override
    public String getPost() {
        return " ["+this.getClassMap1().getClass()+" =! "+this.getClassMap2().getClass()+"]";
    }

    public ClassMap getClassMap1() {
        return classMap1;
    }

    public void setClassMap1(ClassMap classMap1) {
        this.classMap1 = classMap1;
    }

    public ClassMap getClassMap2() {
        return classMap2;
    }

    public void setClassMap2(ClassMap classMap2) {
        this.classMap2 = classMap2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}   
