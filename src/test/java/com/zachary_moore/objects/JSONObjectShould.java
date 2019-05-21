package com.zachary_moore.objects;


import java.util.Map;
import org.junit.jupiter.api.Test;


public class JSONObjectShould {

    @Test
    public void getOriginatingKey() {
        JSONObject jsonObject = new JSONObject("Key", null, null);
        assert(jsonObject.getOriginatingKey().equals("Key"));
    }

    @Test
    public void getParentObject() {
        WrappedPrimitive<Integer> wrappedPrimitive = new WrappedPrimitive<>(null, null, 5);
        JSONObject jsonObject = new JSONObject(null, wrappedPrimitive, null);
        assert(jsonObject.getParentObject().equals(wrappedPrimitive));
    }

    @Test
    public void isPrimitive() {
        JSONObject jsonObject = new JSONObject(null, null, null);
        assert(!jsonObject.isPrimitive());
    }

    @Test
    public void parseAndReplaceWithWrappersReplacesWithWrappedPrimitive() {
        org.json.JSONObject unwrappedObject = new org.json.JSONObject("{\"x\":5}");
        assert(unwrappedObject.getInt("x") == 5);
        JSONObject wrappedObject = new JSONObject(null, null, unwrappedObject);
        wrappedObject.parseAndReplaceWithWrappers();
        assert(wrappedObject.get("x") instanceof WrappedPrimitive);
        assert(wrappedObject.get("x").equals(5));
    }

    @Test
    public void parseAndReplaceWithWrappersReplacesWithJSONObject() {
        org.json.JSONObject unwrappedObject = new org.json.JSONObject("{\"x\":{\"y\":5}}");
        assert(unwrappedObject.get("x") instanceof org.json.JSONObject);
        JSONObject wrappedObject = new JSONObject(null, null, unwrappedObject);
        wrappedObject.parseAndReplaceWithWrappers();
        assert(wrappedObject.get("x") instanceof JSONObject);
    }

    @Test
    public void verifyGetImplicitlyReplacesWithWrapper() {
        org.json.JSONObject unwrappedObject = new org.json.JSONObject("{\"x\":5}");
        assert(unwrappedObject.getInt("x") == 5);
        JSONObject wrappedObject = new JSONObject(null, null, unwrappedObject);
        assert(wrappedObject.get("x") instanceof WrappedPrimitive);
        assert(wrappedObject.get("x").equals(5));
    }

    @Test
    public void verifyToMapImplicitlyReplacesWithWrapper() {
        org.json.JSONObject unwrappedObject = new org.json.JSONObject("{\"x\":5}");
        assert(unwrappedObject.getInt("x") == 5);
        JSONObject wrappedObject = new JSONObject(null, null, unwrappedObject);
        Map<String, Object> wrappedMap = wrappedObject.toMap();
        assert(wrappedMap.get("x") instanceof WrappedPrimitive);
        assert(wrappedMap.get("x").equals(5));
    }
}
