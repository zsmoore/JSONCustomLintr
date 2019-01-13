package learn;

import filters.Filters;
import helpers.ImplementationHelper;
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
        Filters filter = new Filters();
//        log.info(filter.filterToObjects(jsonFile).toString());
//        log.info(filter.filterToArrays(jsonFile).toString());
//        log.info(filter.filterToStrings(jsonFile).toString());

//        ArrayList<WrappedPrimitive<String>> wrappedStrings = filter.filterToStrings(jsonFile);
//        wrappedStrings.forEach(e-> log.info(e.getOriginatingKey() + "\t" + e.toString()));

//        ArrayList<JSONObject> objects = filter.filterToObjects(jsonFile);
//        objects.forEach(e -> log.info(e.getOriginatingKey()));
//
//        objects.forEach(jsonObject -> {
//            if (jsonObject.getOriginatingKey().equals("fields")
//                    && jsonObject.getParentObject() instanceof JSONArray
//                    && jsonObject.get("type").equals("boolean")
//                    && ((String) jsonObject.get("name")).startsWith("has")) {
//                log.info("CAUGHT");
//            }
//        });

//        jsonFile.getObject().toMap().entrySet().forEach(entry ->
//            log.info(entry.getKey() + "\t" + entry.getValue()));

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
