package objects;

public interface WrappedObject {

    public String getOriginatingKey();

    public WrappedObject getParentObject();

    public void parseAndReplaceWithWrappers();

    public boolean isPrimitive();

}
