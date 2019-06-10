package com.zachary_moore.objects;

import org.apache.commons.lang3.StringUtils;


public class WrappedObjectHelper {

    private WrappedObjectHelper(){}

    /**
     * Take in JSONRepresentation and wrap in proper {@link WrappedObject}
     * @return proper {@link WrappedObject} based on originalObject
     */
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

    public static String getPath(WrappedObject element) {
        if (element.getParentObject() == null)
            return "";

        if (element.getParentObject() instanceof JSONArray) {
            return String.join(".", getPath(element.getParentObject()), getArrayIndex(element) +  "");

        }

        String path = getPath(element.getParentObject());
        if (StringUtils.isNotBlank(path))
            path += ".";

        return path + element.getOriginatingKey();
    }

    private static int getArrayIndex(WrappedObject element) {
        return ((JSONArray)element.getParentObject()).toList().indexOf(element);
    }

}
