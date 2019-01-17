package objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

public class WrappedObjectHelperShould {

    @Test
    public void getWrappedObjectReturnsWrappedPrimitive() {
        Integer x = 5;
        Object wrappedObject = WrappedObjectHelper.getWrappedObject(null, null, x);
        assert(wrappedObject instanceof WrappedPrimitive);
        assert(wrappedObject.equals(x));
    }

    @Test
    public void getWrappedObjectReturnsJSONObject() {
        org.json.JSONObject unwrappedObject = new JSONObject("{}");
        assert(WrappedObjectHelper.getWrappedObject(null, null, unwrappedObject) instanceof JSONObject);
    }

    @Test
    public void getWrappedObjectReturnsJSONArray() {
        org.json.JSONArray unwrappedArray = new JSONArray(Collections.singletonList(5));
        assert(WrappedObjectHelper.getWrappedObject(null, null, unwrappedArray) instanceof JSONArray);
    }

    @Test
    public void getWrappedObjectWithWrappedObjectReturnsSameObject() {
        WrappedPrimitive<Integer> wrappedPrimitive = new WrappedPrimitive<>(null, null, 5);
        assert(WrappedObjectHelper.getWrappedObject(null, null, wrappedPrimitive) == wrappedPrimitive);
    }

    @Test
    public void getWrappedObjectReturnsNullWrappedPrimitive() {
        assert(WrappedObjectHelper.getWrappedObject(null, null, null) instanceof WrappedPrimitive);
    }

    @Test
    public void getWrappedObjectPreservesKey() {
        assert(WrappedObjectHelper.getWrappedObject("key", null, null).getOriginatingKey().equals("key"));
    }

    @Test
    public void getWrappedObjectPreservesParent() {
        WrappedPrimitive<Integer> parent = new WrappedPrimitive<>(null, null, 5);
        assert(WrappedObjectHelper.getWrappedObject(null, parent, 5).equals(parent));
    }



}
