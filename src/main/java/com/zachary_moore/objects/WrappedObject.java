package com.zachary_moore.objects;

public interface WrappedObject {

    /**
     * @return Closest key to this JSONRepresentation
     */
    String getOriginatingKey();

    /**
     * @return Object that encapsulates this current object
     */
    WrappedObject getParentObject();

    /**
     * Recursively replace any of this object's children with proper {@link WrappedObject}
     */
    void parseAndReplaceWithWrappers();

    boolean isPrimitive();

}
