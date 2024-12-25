package mg.jca.gfja.exception;

public abstract class CustomableException extends Exception{

    @Override
    public String getMessage() {
        return this.getPre() + this.getBody()+this.getPost();
    }

    public abstract String getPre();
    public abstract String getBody();
    public abstract String getPost();
}
