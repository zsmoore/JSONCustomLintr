package com.zachary_moore.objects;

class WrappedObjectHelper {

    private WrappedObjectHelper(){}

    static WrappedObject getWrappedObject(String originatingKey,
                                          WrappedObject parentObject,
                                          Object originalObject) {
        WrappedObject wrappedObject;
        if (originalObject instanceof org.json.JSONObject) {
            wrappedObject = new JSONObject(originatingKey,
                                           parentObject,
                                           (org.json.JSONObject) originalObject);
        } else if (originalObject instanceof org.json.JSONArray) {
            wrappedObject = new JSONArray(originatingKey,
                                          parentObject,
                                          (org.json.JSONArray) originalObject);
        } else if (originalObject instanceof WrappedObject) {
            return (WrappedObject) originalObject;
        } else {
            wrappedObject = new WrappedPrimitive<>(originatingKey,
                                                   parentObject,
                                                   originalObject);
        }
        return wrappedObject;
    }
}
