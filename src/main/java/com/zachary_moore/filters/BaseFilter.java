package com.zachary_moore.filters;

import com.zachary_moore.objects.JSONArray;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Class that all filter methods pass through, this is the meat of our filtering system
 */
class BaseFilter {

    /**
     * Filter a {@link JSONFile} down to given {@link Class}
     * @param file JSONFile to filter
     * @param clazz Class to filter down to
     * @param <T> Type of class to filter JSONFile down to
     * @return {@link ArrayList<T>} of given class type from JSONFile
     */
    <T> List<T> getAllOfType(JSONFile file, Class<T> clazz) {
        Object baseObject = file.getObject();
        if (baseObject instanceof JSONObject) {
            return accumulateType((JSONObject) baseObject, clazz);
        } else if (baseObject instanceof JSONArray){
            return accumulateFromJSONArray((JSONArray) baseObject, clazz);
        }
        throw new RuntimeException("Base Object was neither a JSONObject or JSONArray");
    }

    /**
     * Filter a {@link JSONFile} down to {@link WrappedPrimitive} type.
     * We need this to avoid unchecked issues
     * @param file JSONFile to filter
     * @param clazz Inner generic of {@link WrappedPrimitive} to filter down to
     * @param <T> Type of inner generic to filter JSONFile down to a list of WrappedPrimitives of
     * @return {@link ArrayList} of {@link WrappedPrimitive} of given type
     */
    <T> List<WrappedPrimitive<T>> getAllOfWrappedType(JSONFile file, Class<T> clazz) {
        ArrayList<WrappedPrimitive<T>> wrappedTypeList = new ArrayList<>();
        Object baseObject = file.getObject();
        if (baseObject instanceof JSONObject) {
            wrappedTypeList.addAll(accumulateWrappedTypeFromEntrySet(((JSONObject) baseObject).toMap().entrySet(), clazz));
        } else if (baseObject instanceof JSONArray) {
            wrappedTypeList.addAll(accumulateWrappedTypeFromJSONArray((JSONArray) baseObject, clazz));
        } else {
            throw new RuntimeException("Base Object was neither a JSONObject or JSONArray");
        }
        return wrappedTypeList;
    }

    /**
     * Given a set of entries from a {@link JSONObject} HashMap accumulate each WrappedPrimitive of type T
     * @param entrySet {@link Set<Map.Entry>} from a {@link JSONObject}
     * @param clazz Inner generic of {@link WrappedPrimitive} to filter down to
     * @param <K> Key from {@link JSONObject}
     * @param <V> Value from {@link JSONObject}
     * @param <T> Type of {@link WrappedPrimitive} generic to filter down to
     * @return {@link ArrayList} of {@link WrappedPrimitive} of given type
     */
    @SuppressWarnings("unchecked")
    private <K, V, T> List<WrappedPrimitive<T>> accumulateWrappedTypeFromEntrySet(Set<Map.Entry<K, V>> entrySet, Class<T> clazz) {
        ArrayList<WrappedPrimitive<T>> wrappedTypeList = new ArrayList<>();
        entrySet.forEach(entry -> {
            if (isType(entry.getValue(), clazz)) {
                wrappedTypeList.add((WrappedPrimitive<T>) entry.getValue());
            }

            if (entry.getValue() instanceof JSONObject) {
                wrappedTypeList.addAll(accumulateWrappedTypeFromEntrySet(((JSONObject) entry.getValue()).toMap().entrySet(), clazz));
            } else if (entry.getValue() instanceof JSONArray) {
                wrappedTypeList.addAll(accumulateWrappedTypeFromJSONArray((JSONArray) entry.getValue(), clazz));
            }
        });
        return wrappedTypeList;
    }

    /**
     * Given a {@link JSONArray} accumulate each WrappedPrimitive of type T
     * @param array {@link JSONArray} to filter down
     * @param clazz Inner generic of {@link WrappedPrimitive} to filter down to
     * @param <T> Type of {@link WrappedPrimitive} generic to filter down to
     * @return {@link ArrayList} of {@link WrappedPrimitive} of given type
     */
    @SuppressWarnings("unchecked")
    private <T> List<WrappedPrimitive<T>> accumulateWrappedTypeFromJSONArray(JSONArray array, Class<T> clazz) {
        ArrayList<WrappedPrimitive<T>> wrappedTypeList = new ArrayList<>();
        array.toList().forEach(e -> {
            if (isType(e, clazz)) {
                wrappedTypeList.add((WrappedPrimitive<T>) e);
            }

            if (e instanceof JSONObject) {
                wrappedTypeList.addAll(accumulateWrappedTypeFromEntrySet(((JSONObject) e).toMap().entrySet(), clazz));
            } else if (e instanceof JSONArray) {
                wrappedTypeList.addAll(accumulateWrappedTypeFromJSONArray((JSONArray) e, clazz));
            }
        });
        return wrappedTypeList;
    }

    /**
     * Given a {@link JSONObject} accumulate all of Class type T
     * @param toParse {@link JSONObject} to filter from
     * @param clazz {@link Class} to filter down to
     * @param <T> Type of clazz to filter down to
     * @return {@link ArrayList} of type T filtered from {@link JSONObject}
     */
    private <T> List<T> accumulateType(JSONObject toParse, Class<T> clazz) {
        ArrayList<T> typeList = new ArrayList<>();
        if (isType(toParse, clazz)) {
            typeList.add(clazz.cast(toParse));
        }
        typeList.addAll(accumulateFromEntrySet(toParse.toMap().entrySet(), clazz));
        return typeList;
    }

    /**
     *
     * Given a set of entries from a {@link JSONObject} HashMap accumulate each of type T
     * @param entrySet {@link Set<Map.Entry>} from a {@link JSONObject}
     * @param clazz Inner Class type to filter down to
     * @param <K> Key from {@link JSONObject}
     * @param <V> Value from {@link JSONObject}
     * @param <T> Type of Class to filter down to
     * @return {@link ArrayList} of given type
     */
    private <K, V, T> List<T> accumulateFromEntrySet(Set<Map.Entry<K, V>> entrySet, Class<T> clazz) {
        ArrayList<T> typeList = new ArrayList<>();
        entrySet.forEach(entry -> {
            if (isType(entry.getValue(), clazz)) {
                typeList.add(clazz.cast(entry.getValue()));
            }

            if (entry.getValue() instanceof JSONObject) {
                typeList.addAll(accumulateFromEntrySet(((JSONObject) entry.getValue()).toMap().entrySet(), clazz));
            } else if (entry.getValue() instanceof JSONArray) {
                typeList.addAll(accumulateFromJSONArray((JSONArray) entry.getValue(), clazz));
            }
        });
        return typeList;
    }

    /**
     * Given a {@link JSONArray} accumulate each Object of type T
     * @param jsonArray {@link JSONArray} to filter down
     * @param clazz Inner Class type to filter down to
     * @param <T> Type of Class to filter down to
     * @return {@link ArrayList} of given type
     */
    private <T> List<T> accumulateFromJSONArray(JSONArray jsonArray, Class<T> clazz) {
        ArrayList<T> typeList = new ArrayList<>();
        jsonArray.toList().forEach(e -> {
            if (isType(e, clazz)) {
                typeList.add(clazz.cast(e));
            }

            if (e instanceof JSONObject) {
                typeList.addAll(accumulateFromEntrySet(((JSONObject) e).toMap().entrySet(), clazz));
            } else if (e instanceof JSONArray) {
                typeList.addAll(accumulateFromJSONArray((JSONArray) e, clazz));
            }
        });
        return typeList;
    }

    /**
     * Given Object and class type determine if Object is of type or if Object is {@link WrappedPrimitive} check value
     * @param object Object to type check
     * @param clazz Class to check if Object is type
     * @param <T> Type of Class
     * @return if Object is of type clazz
     */
    private <T> boolean isType(Object object, Class<T> clazz) {
        if (object instanceof WrappedPrimitive) {
            if (clazz.isInstance(object)) {
                return clazz.isInstance(object);
            } else {
                return clazz.isInstance(((WrappedPrimitive) object).getValue());
            }
        }
        return clazz.isInstance(object);
    }
}
