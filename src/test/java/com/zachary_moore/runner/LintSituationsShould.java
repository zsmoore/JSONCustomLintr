package com.zachary_moore.runner;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;


public class LintSituationsShould {

    private LintRule.Builder builder;
    private LintRegister lintRegister;

    @BeforeEach
    public void setUp() {
      this.builder = new LintRule.Builder().setIssueId("");
      this.lintRegister = new LintRegister();
    }

    @Test
    public void shouldReportFilePathViolation() throws Exception {
        LintRule lintRule = this.builder.setImplementation(new LintImplementation<JSONFile>() {
            @Override
            public Class<?> getClazz() {
                return JSONFile.class;
            }

            @Override
            public boolean shouldReport(JSONFile jsonFile) {
                File file = new File(jsonFile.getFilePath());
                return file.getParentFile().getName().contains("resources");
            }

            @Override
            public String report(JSONFile jsonFile) {
                return "File can not have parent directory resources";
            }
        }).setLevel(LintLevel.ERROR)
          .build();
        this.lintRegister.register(lintRule);
        LintRunner lintRunner =
                new LintRunner(this.lintRegister,
                               "./src/test/resources/test-2.json",
                               "./src/test/resources/test-file.pdsc");

        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 2);
    }

    @Test
    public void jsonFileShouldHaveChildObject() throws Exception {
        LintRule lintRule = this.builder.setImplementation(new LintImplementation<JSONFile>() {
            @Override
            public Class<?> getClazz() {
                return JSONFile.class;
            }

            @Override
            public boolean shouldReport(JSONFile jsonFile) {
                WrappedObject child = jsonFile.getChild();
                return child instanceof JSONObject;
            }

            @Override
            public String report(JSONFile jsonFile) {
                return "Expect a JSONArray as child";
            }
        }).setLevel(LintLevel.ERROR)
          .build();
        this.lintRegister.register(lintRule);
        LintRunner lintRunner =
                new LintRunner(this.lintRegister,
                        "./src/test/resources/test-2.json",
                        "./src/test/resources/test-file.pdsc");

        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 2);
    }

    @Test
    public void jsonFileShouldDetectFileExtension() throws Exception {
        LintRule lintRule = this.builder.setImplementation(new LintImplementation<JSONFile>() {
            @Override
            public Class<?> getClazz() {
                return JSONFile.class;
            }

            @Override
            public boolean shouldReport(JSONFile jsonFile) {
                return jsonFile.getFileExtension().equals("pdsc");
            }

            @Override
            public String report(JSONFile jsonFile) {
                return "Expect a JSONArray as child";
            }
        }).setLevel(LintLevel.ERROR)
          .build();
        this.lintRegister.register(lintRule);
        LintRunner lintRunner =
                new LintRunner(this.lintRegister,
                        "./src/test/resources/test-2.json",
                        "./src/test/resources/test-file.pdsc");

        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 1);
    }
}
