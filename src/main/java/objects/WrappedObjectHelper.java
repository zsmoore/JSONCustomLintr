package objects;

public class WrappedObjectHelper {

    private WrappedObjectHelper(){}

    public static WrappedObject getWrappedObject(String originatingKey,
                                                 Object originalObject,
                                                 WrappedObject parentObject) {
        WrappedObject wrappedObject;
        if (originalObject == null || org.json.JSONObject.NULL.equals(originalObject)) {
            return null;
        } else if (originalObject instanceof org.json.JSONObject) {
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
            wrappedObject = new WrappedPrimitive(originatingKey,
                                                 parentObject,
                                                 originalObject);
        }
        wrappedObject.parseAndReplaceWithWrappers();
        return wrappedObject;
    }
}
