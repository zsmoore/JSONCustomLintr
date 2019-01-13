package objects;

/**
 * Created by zsmoore on 1/12/19.
 */
public class WrappedPrimitive implements WrappedObject {

    private final String originatingKey;
    private final WrappedObject parentObject;
    private final Object value;

    public WrappedPrimitive(String originatingKey,
                            WrappedObject parentObject,
                            Object originalObject) {
        if (originatingKey == null) {
            this.originatingKey = parentObject.getOriginatingKey();
        } else {
            this.originatingKey = originatingKey;
        }
        this.parentObject = parentObject;
        this.value = originalObject;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getOriginatingKey() {
        return originatingKey;
    }

    @Override
    public WrappedObject getParentObject() {
        return parentObject;
    }

    @Override
    public boolean isPrimitive(){
        return true;
    }

    @Override
    public void parseAndReplaceWithWrappers() {}

    @Override
    public String toString() {
        return value.toString();
    }
}
