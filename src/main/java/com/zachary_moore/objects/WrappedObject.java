package com.zachary_moore.objects;

public interface WrappedObject {

    String getOriginatingKey();

    WrappedObject getParentObject();

    void parseAndReplaceWithWrappers();

    boolean isPrimitive();

}
