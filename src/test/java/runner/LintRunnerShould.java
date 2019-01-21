package runner;

import lint.LintImplementation;
import lint.LintLevel;
import lint.LintRegister;
import lint.LintRule;
import objects.JSONFile;
import objects.WrappedPrimitive;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class LintRunnerShould {

    private LintRunner lintRunner;
    private LintRule.Builder builder;
    private LintRegister lintRegister;

    @Before
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
}
