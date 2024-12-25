package mg.jca.gfja.exception.dao;

import mg.jca.gfja.exception.NotGfjaEntityException;

public class InsertionEntityException extends NotGfjaEntityException {
    public InsertionEntityException(Object obj){
        super(obj,"Insertion error : ");
    }
}
