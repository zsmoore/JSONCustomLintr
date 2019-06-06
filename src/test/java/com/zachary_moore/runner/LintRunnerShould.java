package com.zachary_moore.runner;

import com.zachary_moore.lint.Error;
import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONArray;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LintRunnerShould {

    private LintRunner lintRunner;
    private LintRule.Builder builder;
    private LintRegister lintRegister;

    @BeforeEach
    public void setUp() {
        this.lintRunner = new LintRunner(new LintRegister(),
                                         "./src/test/resources");
        this.builder = new LintRule.Builder()
            .setImplementation(new LintImplementation<WrappedPrimitive<String>>() {
                    @Override
                    public Class<?> getClazz() {
                        return String.class;
                    }

                    @Override
                    public boolean shouldReport(WrappedPrimitive<String> stringWrappedPrimitive) {
                        this.setReportMessage(stringWrappedPrimitive.toString());
                        return true;
                    }
                })
            .setIssueId("");
        this.lintRegister = new LintRegister();
    }

    @Test
    public void testNoLintReport() {
        lintRunner.lint();
        assert(lintRunner.getLintOutput().size() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0() {
        this.lintRunner.lint();
        assert(this.lintRunner.getExitCode() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus1() throws Exception {
        this.lintRegister.register(builder.setLevel(LintLevel.ERROR).build());
        LintRunner lintRunner = new LintRunner(this.lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.getExitCode() == 1);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0WithWarning() throws Exception {
        lintRegister.register(builder.setLevel(LintLevel.WARNING).build());
        LintRunner lintRunner = new LintRunner(lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.getExitCode() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0WithIgnore() throws Exception {
       this.lintRegister.register(builder.setLevel(LintLevel.IGNORE).build());
        LintRunner lintRunner = new LintRunner(this.lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.getExitCode() == 0);
    }

    @Test
    public void lintRunnerShouldTakeMultipleFiles() throws Exception {
        LintRule lintRule = builder.setLevel(LintLevel.ERROR).build();
        this.lintRegister.register(lintRule);
        LintRunner lintRunner =
                new LintRunner(
                        this.lintRegister,
                    "./src/test/resources/test-2.json",
                    "./src/test/resources/test-file.pdsc");
        lintRunner.lint();

        Map<LintRule, Map<JSONFile, List<Error>>> lintOutput = lintRunner.getLintOutput();

        assert(lintRunner.getExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 2);
    }

    @Test
    public void lintRunnerShouldValidErrorForWrappedPrimitive() throws Exception {
        LintRule lintRule = new LintRule.Builder()
            .setImplementation(new LintImplementation<WrappedPrimitive<String>>() {
                @Override
                public Class<?> getClazz() {
                    return String.class;
                }

                @Override
                public boolean shouldReport(WrappedPrimitive<String> stringWrappedPrimitive) {
                    this.setReportMessage(stringWrappedPrimitive.toString());
                    return true;
                }
            })
            .setIssueId("")
            .setLevel(LintLevel.ERROR)
            .build();

        LintRegister lintRegister = new LintRegister();
        lintRegister.register(lintRule);
        LintRunner lintRunner =
            new LintRunner(
                lintRegister,
                "./src/test/resources/test-3.json");
        lintRunner.lint();

        Map<LintRule, Map<JSONFile, List<Error>>> lintOutput = lintRunner.getLintOutput();

        assert(lintRunner.getExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 1);

        List<String> exitingPaths = new ArrayList<>();
        lintOutput.get(lintRule).values().forEach(k -> k.forEach(k1 -> exitingPaths.add(k1.getPath())));
        assert(exitingPaths.size() == 4);

        List<String> validPaths = Arrays.asList(
            "test_1_label",
            "test_2_label",
            "test_3_label.test_3_1_label.test_3_1_1_label.test_3_1_1_1_label",
            "test_3_label.test_3_2_label.test_3_2_1_label");
        assert(exitingPaths.containsAll(validPaths));
    }

    @Test
    public void lintRunnerShouldValidErrorForJSONObject() throws Exception {
        LintRule lintRule = new LintRule.Builder()
            .setImplementation(new LintImplementation<JSONObject>() {
                @Override
                public Class<?> getClazz() {
                    return JSONObject.class;
                }

                @Override
                public boolean shouldReport(JSONObject jsonObject) {
                    this.setReportMessage(jsonObject.toString());
                    return true;
                }
            })
            .setIssueId("")
            .setLevel(LintLevel.ERROR)
            .build();

        LintRegister lintRegister = new LintRegister();
        lintRegister.register(lintRule);
        LintRunner lintRunner =
            new LintRunner(
                lintRegister,
                "./src/test/resources/test-3.json");
        lintRunner.lint();

        Map<LintRule, Map<JSONFile, List<Error>>> lintOutput = lintRunner.getLintOutput();

        assert(lintRunner.getExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 1);

        List<String> exitingPaths = new ArrayList<>();
        lintOutput.get(lintRule).values().forEach(k -> k.forEach(k1 -> exitingPaths.add(k1.getPath())));
        assert(exitingPaths.size() == 5);

        List<String> validPaths = Arrays.asList(
            "",
            "test_3_label",
            "test_3_label.test_3_1_label",
            "test_3_label.test_3_1_label.test_3_1_1_label",
            "test_3_label.test_3_2_label");
        assert(exitingPaths.containsAll(validPaths));
    }

    @Test
    public void lintRunnerShouldValidErrorForJSONArray() throws Exception {
        LintRule lintRule = new LintRule.Builder()
            .setImplementation(new LintImplementation<JSONArray>() {
                @Override
                public Class<?> getClazz() {
                    return JSONArray.class;
                }

                @Override
                public boolean shouldReport(JSONArray jsonArray) {
                    this.setReportMessage(jsonArray.toString());
                    return true;
                }
            })
            .setIssueId("")
            .setLevel(LintLevel.ERROR)
            .build();

        LintRegister lintRegister = new LintRegister();
        lintRegister.register(lintRule);
        LintRunner lintRunner =
            new LintRunner(
                lintRegister,
                "./src/test/resources/test-4.json");
        lintRunner.lint();

        Map<LintRule, Map<JSONFile, List<Error>>> lintOutput = lintRunner.getLintOutput();

        assert(lintRunner.getExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 1);

        List<String> exitingPaths = new ArrayList<>();
        lintOutput.get(lintRule).values().forEach(k -> k.forEach(k1 -> exitingPaths.add(k1.getPath())));
        assert(exitingPaths.size() == 2);

        //TODO: Fix this
        List<String> validPaths = Arrays.asList(
            "test_3_label",
            "test_3_label.test_3_1_label");
        assert(exitingPaths.containsAll(validPaths));
    }

  @Test
  public void lintRunnerShouldValidErrorForJSONObjectWithArray() throws Exception {
    LintRule lintRule = new LintRule.Builder()
        .setImplementation(new LintImplementation<JSONObject>() {
          @Override
          public Class<?> getClazz() {
            return JSONObject.class;
          }

          @Override
          public boolean shouldReport(JSONObject jsonObject) {
            this.setReportMessage(jsonObject.toString());
            return true;
          }
        })
        .setIssueId("")
        .setLevel(LintLevel.ERROR)
        .build();

    LintRegister lintRegister = new LintRegister();
    lintRegister.register(lintRule);
    LintRunner lintRunner =
        new LintRunner(
            lintRegister,
            "./src/test/resources/test-4.json");
    lintRunner.lint();

    Map<LintRule, Map<JSONFile, List<Error>>> lintOutput = lintRunner.getLintOutput();

    assert(lintRunner.getExitCode() == 1);
    assert(lintOutput.get(lintRule).size() == 1);

    List<String> exitingPaths = new ArrayList<>();
    lintOutput.get(lintRule).values().forEach(k -> k.forEach(k1 -> exitingPaths.add(k1.getPath())));
    assert(exitingPaths.size() == 2);

    //TODO: Fix this
    List<String> validPaths = Arrays.asList(
        "test_3_label",
        "test_3_label.test_3_1_label");
    assert(exitingPaths.containsAll(validPaths));
  }
}
