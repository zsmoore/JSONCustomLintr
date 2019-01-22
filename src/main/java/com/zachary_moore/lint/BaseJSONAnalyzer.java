package com.zachary_moore.lint;

import com.zachary_moore.objects.JSONArray;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedObject;
import com.zachary_moore.objects.WrappedPrimitive;

import java.util.Arrays;
import java.util.List;

/**
 * Base class with helper methods to use in {@link LintImplementation}
 */
public abstract class BaseJSONAnalyzer {

    /**
     * Check if JSONObject has a key with a value equal to parameter
     * @param jsonObject {@link JSONObject} to check
     * @param key Key in JSONObject
     * @param toCheck Object to check equality for
     * @return true if toCheck is in JSONObject
     */
    protected boolean hasKeyAndValueEqualTo(JSONObject jsonObject, String key, Object toCheck) {
        if (jsonObject == null) {
            return false;
        }

        Object desiredObject = jsonObject.toMap().get(key);
        return (desiredObject == null && toCheck == null)
                || (desiredObject != null && desiredObject.equals(toCheck));
    }

    /**
     * Check if JSONArray has a value at an index equal to parameter
     * @param jsonArray {@link JSONArray} to check
     * @param index index that might be in JSONArray
     * @param toCheck Object to check equality for
     * @return true if toCheck is in JSONArray
     */
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

    /**
     * Attempt to get a {@link WrappedPrimitive} from a {@link JSONObject}
     * @param jsonObject JSONObject to grab from
     * @param key Key in JSONObject to check
     * @return {@link WrappedPrimitive} if it exists in JSONObject
     */
    protected WrappedPrimitive safeGetWrappedPrimitive(JSONObject jsonObject, String key) {
        Object fromObject = safeGet(jsonObject, key);
        if (fromObject == null || !(fromObject instanceof WrappedPrimitive)) {
            return null;
        }
        return (WrappedPrimitive) fromObject;
    }

    /**
     * Attempt to get a {@link JSONObject} from a {@link JSONObject}
     * @param jsonObject JSONObject to grab from
     * @param key Key in JSONObject to check
     * @return {@link JSONObject} if it exists in JSONObject
     */
    protected  JSONObject safeGetJSONObject(JSONObject jsonObject, String key) {
        Object fromObject = safeGet(jsonObject, key);
        if (fromObject == null || !(fromObject instanceof JSONObject)) {
            return null;
        }
        return (JSONObject) fromObject;
    }

    /**
     * Attempt to get a {@link JSONArray} from a {@link JSONObject}
     * @param jsonObject JSONObject to grab from
     * @param key Key in JSONObject to check
     * @return {@link JSONArray} if it exists in JSONObject
     */
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

    /**
     * Attempt to get a {@link WrappedPrimitive} from a {@link JSONArray}
     * @param array JSONArray to grab from
     * @param index index in JSONArray to check
     * @return {@link WrappedPrimitive} if it exists in JSONArray
     */
    protected WrappedPrimitive safeGetWrappedPrimitive(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof WrappedPrimitive)) {
            return  null;
        }
        return (WrappedPrimitive) fromArray;
    }

    /**
     * Attempt to get a {@link JSONObject} from a {@link JSONArray}
     * @param array JSONArray to grab from
     * @param index index in JSONArray to check
     * @return {@link JSONObject} if it exists in JSONArray
     */
    protected JSONObject safeGetJSONObject(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof JSONObject)) {
            return  null;
        }
        return (JSONObject) fromArray;
    }

    /**
     * Attempt to get a {@link JSONArray} from a {@link JSONArray}
     * @param array JSONArray to grab from
     * @param index index in JSONArray to check
     * @return {@link JSONArray} if it exists in JSONArray
     */
    protected JSONArray safeGetJSONArray(JSONArray array, int index) {
        Object fromArray = safeGetIndex(array, index);
        if (fromArray == null || !(fromArray instanceof JSONArray)) {
            return  null;
        }
        return (JSONArray) fromArray;
    }

    /**
     * Check if {@link WrappedPrimitive} value is equal to other Object
     * @param wrappedPrimitive object to check against
     * @param toCheck object to check for
     * @param <T> Type of wrapped primitive
     * @return true if wrappedPrimitive's value is equal to toCheck
     */
    protected <T> boolean isEqualTo(WrappedPrimitive<T> wrappedPrimitive, T toCheck) {
        return wrappedPrimitive.equals(toCheck);
    }

    /**
     * Safe method to check if {@link WrappedObject#getOriginatingKey()} is equal to parameter
     * @param object WrappedObject to check against
     * @param toCheck Key to check for
     * @return true if Key is originating key in WrappedObject
     */
    protected boolean isOriginatingKeyEqualTo(WrappedObject object, String toCheck) {
        return object != null
                && ((object.getOriginatingKey() == null && toCheck == null)
                || object.getOriginatingKey() != null && object.getOriginatingKey().equals(toCheck));
    }

    /**
     * Safe type check that includes {@link WrappedPrimitive} oddities
     */
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

    /**
     * Safe function to check parent type of {@link WrappedObject}
     */
    protected <T> boolean isParentOfType(WrappedObject object, Class<T> clazz) {
        return (object.getParentObject() == null && clazz == null)
                || isType(object.getParentObject(), clazz);
    }

    /**
     * Given a list of booleans return true if all booleans are true
     * @return true if all input booleans are true
     */
    protected boolean reduceBooleans(Boolean... booleans) {
        return Arrays.stream(booleans).filter(b -> !b).count() == 0;
    }
}
