package learn;

import lint.LintImplementation;
import lint.LintLevel;
import lint.LintRegister;
import lint.LintRule;
import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;
import objects.WrappedPrimitive;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

public class MainTest {

    private JSONFile jsonFile;
    private static Logger log;

    @Before
    public void setup() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test-file.pdsc").getFile());
        jsonFile = new JSONFile(file);
//        log = Logger.getLogger("Logger");
    }

    @Test
    public void testJson() throws Exception {
        LintImplementation<JSONObject> lintImplementation = new LintImplementation<JSONObject>() {

            @Override
            public Class<JSONObject> getClazz() {
                return JSONObject.class;
            }

            @Override
            public boolean shouldReport(JSONObject jsonObject) {
                boolean hasBooleanType = hasKeyAndValueEqualTo(jsonObject, "type", "boolean");

                WrappedPrimitive name = safeGetWrappedPrimitive(jsonObject, "name");
                boolean nameStartsWithHas = false;
                if (name != null && name.getValue() instanceof String) {
                    nameStartsWithHas = ((String) name.getValue()).startsWith("has");

                }
                boolean originatingKeyIsFields = isOriginatingKeyEqualTo(jsonObject, "fields");
                boolean isParentArray = isParentOfType(jsonObject, JSONArray.class);

                return reduceBooleans(hasBooleanType, nameStartsWithHas, originatingKeyIsFields, isParentArray);
            }
        };

        LintRule rule = new LintRule.Builder()
                .setLevel(LintLevel.ERROR)
                .setImplementation(lintImplementation)
                .setIssueId("")
                .build();

        LintRegister register = new LintRegister();
        register.register(rule);
        register.addFile(jsonFile);

        register.lint();
    }
}
