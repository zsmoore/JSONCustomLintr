package objects;

import org.junit.Test;

public class WrappedPrimitiveShould {

    private Integer primitive = 5;

    @Test
    public void getOriginatingKey() {
        WrappedPrimitive<Integer> wrappedPrimitive = new WrappedPrimitive<>("Key",
                                                                            null,
                                                                            null);
        assert(wrappedPrimitive.getOriginatingKey().equals("Key"));
    }

    @Test
    public void getParentObject() {
        JSONObject jsonObject = new JSONObject(null, null, new org.json.JSONObject("{}"));
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, jsonObject, null);
        assert(wrappedPrimitive.getParentObject().equals(jsonObject));
    }

    @Test
    public void isPrimitive() {
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, null, null);
        assert(wrappedPrimitive.isPrimitive());
    }

    @Test
    public void getValue() {
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, null, primitive);
        assert(wrappedPrimitive.getValue().equals(primitive));
    }

    @Test
    public void dotEqualsUsesValue() {
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, null, primitive);
        assert(wrappedPrimitive.equals(primitive));
    }

    @Test
    public void checkToStringUsesValue() {
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, null, primitive);
        assert(wrappedPrimitive.toString().equals(primitive.toString()));
    }

    @Test
    public void parseAndReplaceWithWrappersShouldDoNothing() {
        WrappedPrimitive<Integer> wrappedPrimitive =
                new WrappedPrimitive<>(null, null, primitive);
        assert(wrappedPrimitive.getValue().equals(primitive));
    }
}
