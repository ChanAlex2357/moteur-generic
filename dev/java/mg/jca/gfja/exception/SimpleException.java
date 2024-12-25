package mg.jca.gfja.exception;

public abstract class SimpleException extends CustomableException{

    @Override
    public String getPre() {
        return "ERREUR : ";
    }


    @Override
    public String getPost() {
        return "";
    }
    
}
