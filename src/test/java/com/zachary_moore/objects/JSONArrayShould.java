package com.zachary_moore.objects;

import org.junit.Test;

import java.util.Collections;

public class JSONArrayShould {

    @Test
    public void getOriginatingKey() {
        JSONArray jsonArray = new JSONArray("Key", null, null);
        assert(jsonArray.getOriginatingKey().equals("Key"));
    }

    @Test
    public void getParentObject() {
        JSONObject jsonObject = new JSONObject(null, null, new org.json.JSONObject("{}"));
        JSONArray jsonArray = new JSONArray(null, jsonObject, null);
        assert(jsonArray.getParentObject().equals(jsonObject));
    }

    @Test
    public void isPrimitive() {
        JSONArray jsonArray = new JSONArray(null, null, null);
        assert(!jsonArray.isPrimitive());
    }

    @Test
    public void parseAndReplaceWithWrappersReplacesWithWrappedPrimitive() {
        org.json.JSONArray unwrappedArray = new org.json.JSONArray(Collections.singletonList(5));
        assert(unwrappedArray.get(0) instanceof Integer);
        JSONArray wrappedArray = new JSONArray(null, null, unwrappedArray);
        wrappedArray.parseAndReplaceWithWrappers();
        assert(wrappedArray.get(0) instanceof WrappedPrimitive);
        assert(wrappedArray.get(0).equals(unwrappedArray.get(0)));
    }

    @Test
    public void parseAndReplaceWithWrappersReplacesWithJSONObject() {
        org.json.JSONArray unwrappedArray = new org.json.JSONArray(Collections.singletonList(new org.json.JSONObject("{}")));
        assert(unwrappedArray.get(0) instanceof org.json.JSONObject);
        JSONArray wrappedArray = new JSONArray(null, null, unwrappedArray);
        wrappedArray.parseAndReplaceWithWrappers();
        assert(wrappedArray.get(0) instanceof JSONObject);
    }

    @Test
    public void verifyGetImplicitlyReplacesWithWrapper() {
        org.json.JSONArray unwrappedArray = new org.json.JSONArray(Collections.singletonList(5));
        assert(unwrappedArray.get(0) instanceof Integer);
        JSONArray wrappedArray = new JSONArray(null, null, unwrappedArray);
        assert(wrappedArray.get(0) instanceof WrappedPrimitive);
        assert(wrappedArray.get(0).equals(unwrappedArray.get(0)));
    }

    @Test
    public void verifyToListImplicitlyReplacesWithWrapper() {
        org.json.JSONArray unwrappedArray = new org.json.JSONArray(Collections.singletonList(5));
        assert(unwrappedArray.get(0) instanceof Integer);
        JSONArray wrappedArray = new JSONArray(null, null, unwrappedArray);
        assert(wrappedArray.toList().get(0) instanceof WrappedPrimitive);
        assert(wrappedArray.toList().get(0).equals(unwrappedArray.get(0)));
    }

    @Test
    public void verifyToJSONObjectReturnsWrappedObject() {
        org.json.JSONArray unwrappedArray = new org.json.JSONArray(Collections.singletonList("KEY"));
        JSONArray jsonArray = new JSONArray(null, null, unwrappedArray);
        assert (jsonArray.toJSONObject(unwrappedArray) instanceof JSONObject);
    }
}


