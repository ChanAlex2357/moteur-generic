package mg.jca.gfja.exception;

public class NotGfjaEntityException extends SimpleException{
    
    
    public NotGfjaEntityException(Object object,String preMessage) {
        setInstance(object);
        setInstanceClass(object.getClass());
        setPreMessage(preMessage);
    }

    private Object instance;
    private Class<?> instanceClass;
    private String preMessage;
    public void setPreMessage(String pre) {
        this.preMessage = pre;
    }

    public Object getInstance() {
        return instance;
    }
    public void setInstance(Object instance) {
        this.instance = instance;
    }
    public Class<?> getInstanceClass() {
        return instanceClass;
    }
    public void setInstanceClass(Class<?> instanceClass) {
        this.instanceClass = instanceClass;
    }

    @Override
    public String getPre() {
        return this.preMessage;
    }

    @Override
    public String getBody() {
        return "the class "+this.getInstanceClass()+" does not have an entity property";
    }
}
