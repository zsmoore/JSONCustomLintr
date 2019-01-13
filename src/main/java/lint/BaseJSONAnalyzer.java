package lint;

import objects.JSONArray;
import objects.JSONObject;
import objects.WrappedObject;
import objects.WrappedPrimitive;

import java.util.Arrays;
import java.util.List;

public abstract class BaseJSONAnalyzer {

    protected boolean hasKeyAndValueEqualTo(JSONObject jsonObject, String key, Object toCheck) {
        if (jsonObject == null) {
            return false;
        }

        Object desiredObject = jsonObject.toMap().get(key);
        return (desiredObject == null && toCheck == null)
                || (desiredObject != null && desiredObject.equals(toCheck));
    }

    protected boolean hasIndexAndValueEqualTo(JSONArray jsonArray, int index, Object toCheck) {
        if (jsonArray == null) {
            return false;
        }

        List<Object> arrayList = jsonArray.toList();
        if (index < 0 || index > arrayList.size()) {
            return false;
        }

        Object desiredObject = arrayList.get(index);
        return (desiredObject == null && toCheck == null)
                || desiredObject != null && desiredObject.equals(toCheck);
    }

    private Object safeGet(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null) {
            return null;
        }

        if (jsonObject.toMap().containsKey(key)) {
            return jsonObject.toMap().get(key);
        } else {
            return null;
        }
    }

    protected WrappedPrimitive safeGetWrappedPrimitive(JSONObject jsonObject, String key) {
        Object fromObject = safeGet(jsonObject, key);
        if (fromObject == null || !(fromObject instanceof WrappedPrimitive)) {
            return null;
        }
        return (WrappedPrimitive) fromObject;
    }

    protected  JSONObject safeGetJSONObject(JSONObject jsonObject, String key) {
        Object fromObject = safeGet(jsonObject, key);
        if (fromObject == null || !(fromObject instanceof JSONObject)) {
            return null;
        }
        return (JSONObject) fromObject;
    }

    protected JSONArray safeGetJSONArray(JSONObject jsonObject, String key) {
        Object fromObject = safeGet(jsonObject, key);
        if (fromObject == null || !(fromObject instanceof JSONArray)) {
            return null;
        }
        return (JSONArray) fromObject;
    }

    private Object safeGetIndex(JSONArray array, int index) {
        if (array == null || index < 0) {
            return null;
        }

        List<Object> arrayList = array.toList();
        if (index > arrayList.size()) {
            return null;
        }

        return arrayList.get(index);
    }

    protected WrappedPrimitive safeGetWrappedPrimitive(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof WrappedPrimitive)) {
            return  null;
        }
        return (WrappedPrimitive) fromArray;
    }

    protected JSONObject safeGetJSONObject(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof JSONObject)) {
            return  null;
        }
        return (JSONObject) fromArray;
    }

    protected JSONArray safeGetJSONArray(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof JSONArray)) {
            return  null;
        }
        return (JSONArray) fromArray;
    }

    protected <T> boolean isEqualTo(WrappedPrimitive<T> wrappedPrimitive, T toCheck) {
        return wrappedPrimitive.equals(toCheck);
    }

    protected boolean isOriginatingKeyEqualTo(WrappedObject object, String toCheck) {
        return object != null
                && ((object.getOriginatingKey() == null && toCheck == null)
                || object.getOriginatingKey() != null && object.getOriginatingKey().equals(toCheck));
    }

    protected <T> boolean isType(Object object, Class<T> clazz) {
        if (object instanceof WrappedPrimitive) {
            if (clazz.isInstance(object)) {
                return clazz.isInstance(object);
            } else {
                return clazz.isInstance(((WrappedPrimitive) object).getValue());
            }
        }
        return clazz.isInstance(object);
    }

    protected <T> boolean isParentOfType(WrappedObject object, Class<T> clazz) {
        return (object.getParentObject() == null && clazz == null)
                || isType(object.getParentObject(), clazz);
    }

    protected boolean reduceBooleans(Boolean... booleans) {
        return Arrays.stream(booleans).filter(b -> !b).count() == 0;
    }
}
