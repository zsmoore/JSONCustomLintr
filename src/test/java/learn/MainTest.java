package learn;

import lint.LintImplementation;
import lint.LintLevel;
import lint.LintRegister;
import lint.LintRule;
import objects.JSONArray;
import objects.JSONObject;
import objects.WrappedPrimitive;
import org.junit.Before;
import org.junit.Test;
import runner.LintRunner;
import runner.ReportRunner;

import java.io.File;
import java.util.logging.Logger;

public class MainTest {

    private File file;
    private static Logger log;

    @Before
    public void setup() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        //file = new File(classLoader.getResource("/../res/").getFile());
        log = Logger.getGlobal();
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

                setReportMessage("This is a bad one:\t" + jsonObject);
                return reduceBooleans(hasBooleanType, nameStartsWithHas, originatingKeyIsFields, isParentArray);
            }
        };

        LintRule rule = new LintRule.Builder()
                .setLevel(LintLevel.ERROR)
                .setImplementation(lintImplementation)
                .setIssueId("hasX Boolean Check")
                .build();

        LintImplementation<WrappedPrimitive<String>> lintImplementation1 = new LintImplementation<WrappedPrimitive<String>>() {
            @Override
            public Class<String> getClazz() {
                return String.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<String> string) {
                return string.getValue().equals("test");
            }

            @Override
            public String report(WrappedPrimitive<String> string) {
                return string.getValue();
            }
        };

        LintRule rule1 = new LintRule.Builder()
                .setLevel(LintLevel.ERROR)
                .setImplementation(lintImplementation1)
                .setIssueId("String Name Test")
                .build();

        LintRegister register = new LintRegister();
        register.register(rule,
                          rule1);

        log.info("Ugh" + System.getProperty("user.dir"));
        LintRunner lintRunner = new LintRunner(register, "./src/test/res");
        ReportRunner reportRunner = new ReportRunner(lintRunner);
        reportRunner.report("build/reports");
    }
}
