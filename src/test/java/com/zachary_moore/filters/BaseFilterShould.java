package com.zachary_moore.filters;

import com.zachary_moore.objects.JSONArray;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedObject;
import com.zachary_moore.objects.WrappedPrimitive;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BaseFilterShould {

    private JSONFile jsonFile;
    private BaseFilter baseFilter;

    @BeforeEach
    public void setUp() throws IOException {
        this.jsonFile = new JSONFile(new File(getClass()
                .getClassLoader()
                .getResource("base-filter-test-file.json")
                .getFile()));
        this.baseFilter = new BaseFilter();
    }

    @Test
    public void getAllOfWrappedIntegerType() {
        List<WrappedPrimitive<Integer>> allIntegers =
                baseFilter.getAllOfWrappedType(jsonFile, Integer.class);
        assert(allIntegers.size() == 2);
        assert(allIntegers.get(0).equals(5));
        assert(allIntegers.get(1).equals(6));
    }

    @Test
    public void getAllOfWrappedStringType() {
        List<WrappedPrimitive<String>> allStrings =
                baseFilter.getAllOfWrappedType(jsonFile, String.class);
        assert(allStrings.size() == 3);
        assert(getFrequencyInWrappedPrimitiveList(allStrings, "test") == 2);
        assert(getFrequencyInWrappedPrimitiveList(allStrings, "string") == 1);
    }

    @Test
    public void getAllOfJSONObjectType() {
        List<JSONObject> allJSONObjects =
                baseFilter.getAllOfType(jsonFile, JSONObject.class);
        assert(allJSONObjects.size() == 4);
        assert(getFrequencyOfOriginalKey(allJSONObjects, "name") == 1);
        assert(getFrequencyOfOriginalKey(allJSONObjects, "records") == 2);
        assert(getFrequencyOfOriginalKey(allJSONObjects, null) == 1);
    }

    @Test
    public void getAllOfJSONArrayType() {
        List<JSONArray> allJSONArrays =
                baseFilter.getAllOfType(jsonFile, JSONArray.class);
        assert(allJSONArrays.size() == 1);
        assert(allJSONArrays.get(0).length() == 2);
        assert(allJSONArrays.get(0).get(0) instanceof JSONObject);
        assert(allJSONArrays.get(0).get(1) instanceof JSONObject);
    }

    private <T> int getFrequencyInWrappedPrimitiveList(List<WrappedPrimitive<T>> wrappedPrimitives, Object toCheck) {
        int frequency = 0;
        for(WrappedPrimitive<T> wrappedPrimitive : wrappedPrimitives) {
            if (wrappedPrimitive.equals(toCheck)) {
                frequency += 1;
            }
        }
        return frequency;
    }

    private int getFrequencyOfOriginalKey(List<JSONObject> wrappedObjects, String originatingKey) {
        int frequency = 0;
        for (WrappedObject wrappedObject: wrappedObjects) {
            if (wrappedObject.getOriginatingKey() == null && originatingKey == null) {
                frequency += 1;
            } else if (originatingKey != null && originatingKey.equals(wrappedObject.getOriginatingKey())) {
                frequency += 1;
            }
        }
        return frequency;
    }
}
