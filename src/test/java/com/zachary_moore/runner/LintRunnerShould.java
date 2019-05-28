package com.zachary_moore.runner;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.WrappedPrimitive;
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
        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();
        assert(lintOutput.size() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0() {
        this.lintRunner.lint();
        assert(this.lintRunner.analyzeLintAndGiveExitCode() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus1() throws Exception {
        this.lintRegister.register(builder.setLevel(LintLevel.ERROR).build());
        LintRunner lintRunner = new LintRunner(this.lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0WithWarning() throws Exception {
        lintRegister.register(builder.setLevel(LintLevel.WARNING).build());
        LintRunner lintRunner = new LintRunner(lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 0);
    }

    @Test
    public void lintRunnerShouldGiveExitStatus0WithIgnore() throws Exception {
       this.lintRegister.register(builder.setLevel(LintLevel.IGNORE).build());
        LintRunner lintRunner = new LintRunner(this.lintRegister, "./src/test/resources");
        lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 0);
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
        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();

        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 2);
    }
}
