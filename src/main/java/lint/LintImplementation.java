package lint;

import helpers.ImplementationHelper;
import objects.JSONArray;
import objects.JSONObject;
import objects.WrappedObject;
import objects.WrappedPrimitive;

import java.util.Arrays;
import java.util.List;

public abstract class LintImplementation<T> {

    public abstract Class<T> getClazz();

    public abstract boolean shouldReport(T t);

    protected boolean hasKeyAndValueEqualTo(JSONObject jsonObject, String key, Object toCheck) {
        return ImplementationHelper.hasKeyAndValueEqualTo(jsonObject, key, toCheck);
    }

    protected boolean hasIndexAndValueEqualTo(JSONArray jsonArray, int index, Object toCheck) {
        return ImplementationHelper.hasIndexAndValueEqualTo(jsonArray, index, toCheck);
    }

    public static WrappedPrimitive safeGetWrappedPrimitive(JSONObject jsonObject, String key) {
        return ImplementationHelper.safeGetWrappedPrimitive(jsonObject, key);
    }

    public static JSONObject safeGetJSONObject(JSONObject jsonObject, String key) {
        return ImplementationHelper.safeGetJSONObject(jsonObject, key);
    }

    public static JSONArray safeGetJSONArray(JSONObject jsonObject, String key) {
        return ImplementationHelper.safeGetJSONArray(jsonObject, key);
    }

    public static WrappedPrimitive safeGetWrappedPrimitive(JSONArray jsonArray, int index) {
        return ImplementationHelper.safeGetWrappedPrimitive(jsonArray, index);
    }

    public static JSONObject safeGetJSONObject(JSONArray jsonArray, int index) {
        return ImplementationHelper.safeGetJSONObject(jsonArray, index);
    }

    public static JSONArray safeGetJSONArray(JSONArray jsonArray, int index) {
        return ImplementationHelper.safeGetJSONArray(jsonArray, index);
    }

    protected  <E> boolean isEqualTo(WrappedPrimitive<E> wrappedPrimitive, E toCheck) {
        return ImplementationHelper.isEqualTo(wrappedPrimitive, toCheck);
    }

    protected boolean isOriginatingKeyEqualTo(WrappedObject object, String toCheck) {
        return ImplementationHelper.isOriginatingKeyEqualTo(object, toCheck);
    }

    protected <E> boolean isType(Object object, Class<E> clazz) {
        return ImplementationHelper.isType(object, clazz);
    }

    protected <E> boolean isParentOfType(WrappedObject object, Class<E> clazz) {
        return ImplementationHelper.isParentOfType(object, clazz);
    }

    protected boolean reduceBooleans(Boolean ...booleans) {
        return ImplementationHelper.reduceBooleans(booleans);
    }
}
