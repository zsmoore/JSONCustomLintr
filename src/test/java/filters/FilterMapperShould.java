package filters;

import lint.LintImplementation;
import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;
import objects.WrappedPrimitive;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilterMapperShould {

    private JSONFile jsonFile;

    @Before
    public void setUp() throws IOException {
        this.jsonFile = new JSONFile(new File(getClass()
                .getClassLoader()
                .getResource("base-filter-test-file.json")
                .getFile()));
    }

    @Test
    public void filterMapperShouldReturnWrappedIntegers() {
        LintImplementation<WrappedPrimitive<Integer>> wrappedPrimitiveLintImplementation
                = new LintImplementation<WrappedPrimitive<Integer>>() {
            @Override
            public Class<?> getClazz() {
                return Integer.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<Integer> integerWrappedPrimitive) {
                return false;
            }
        };

        List<?> wrappedPrimitives
                = FilterMapper.filter(jsonFile, wrappedPrimitiveLintImplementation);
        assert(wrappedPrimitives.size() == 2);
        assert(wrappedPrimitives.get(0) instanceof WrappedPrimitive);
        assert(((WrappedPrimitive) wrappedPrimitives.get(0)).getValue() instanceof Integer);
    }

    @Test
    public void filterMapperShouldReturnWrappedStrings() {
        LintImplementation<WrappedPrimitive<String>> wrappedPrimitiveLintImplementation
                = new LintImplementation<WrappedPrimitive<String>>() {
            @Override
            public Class<?> getClazz() {
                return String.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<String> stringWrappedPrimitive) {
                return false;
            }
        };

        List<?> wrappedPrimitives
                = FilterMapper.filter(jsonFile, wrappedPrimitiveLintImplementation);
        assert(wrappedPrimitives.size() == 3);
        assert(wrappedPrimitives.get(0) instanceof WrappedPrimitive);
        assert(((WrappedPrimitive) wrappedPrimitives.get(0)).getValue() instanceof String);
    }

    @Test
    public void filterMapperShouldReturnJSONObjects() {
        LintImplementation<JSONObject> jsonObjectLintImplementation
                = new LintImplementation<JSONObject>() {
            @Override
            public Class<?> getClazz() {
                return JSONObject.class;
            }

            @Override
            public boolean shouldReport(JSONObject jsonObject) {
                return false;
            }
        };

        List<?> jsonObjects
                = FilterMapper.filter(jsonFile, jsonObjectLintImplementation);
        assert(jsonObjects.size() == 4);
        assert(jsonObjects.get(0) instanceof JSONObject);
    }

    @Test
    public void filterMapperShouldReturnJSONArrays() {
        LintImplementation<JSONArray> jsonArrayLintImplementation
                = new LintImplementation<JSONArray>() {
            @Override
            public Class<?> getClazz() {
                return JSONArray.class;
            }

            @Override
            public boolean shouldReport(JSONArray jsonArray) {
                return false;
            }
        };

        List<?> jsonArrays
                = FilterMapper.filter(jsonFile, jsonArrayLintImplementation);
        assert(jsonArrays.size() == 1);
        assert(jsonArrays.get(0) instanceof JSONArray);
    }
}
