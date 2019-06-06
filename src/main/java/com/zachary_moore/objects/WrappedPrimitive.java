package com.zachary_moore.objects;

/**
 * Object to implement {@link WrappedObject} and encapsulate any non {@link org.json.JSONArray} or {@link org.json.JSONObject}
 * @param <T> Type of original Object this class was created from
 */
public class WrappedPrimitive<T> implements WrappedObject {

    private final String originatingKey;
    private final WrappedObject parentObject;
    private final T value;

    protected WrappedPrimitive(String originatingKey,
                               WrappedObject parentObject,
                               T originalObject) {
        if (originatingKey == null && parentObject != null) {
            this.originatingKey = parentObject.getOriginatingKey();
        } else {
            this.originatingKey = originatingKey;
        }
        this.parentObject = parentObject;
        this.value = originalObject;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WrappedPrimitive) {
            return this == other || this.getValue().equals(((WrappedPrimitive) other).getValue());
        } else {
            return this.getValue().equals(other);
        }
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
