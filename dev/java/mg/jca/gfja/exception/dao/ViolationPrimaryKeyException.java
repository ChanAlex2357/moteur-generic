package mg.jca.gfja.exception.dao;

import mg.jca.gfja.exception.SimpleException;
import mg.jca.gfja.mapping.Champ;

public class ViolationPrimaryKeyException extends SimpleException{
    private Champ c1;
    private Champ c2;

    public ViolationPrimaryKeyException(Champ c1 , Champ c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public String getBody() {
        return "Mutltiple declaration of id annotation violate the unique primarykey on "+this.c1.getParentClass()+"("+this.c1.getName()+";"+this.c2.getName()+")";
    }
    
}
