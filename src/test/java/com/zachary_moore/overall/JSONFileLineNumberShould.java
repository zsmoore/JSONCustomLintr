package com.zachary_moore.overall;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedPrimitive;
import com.zachary_moore.runner.LintRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONFileLineNumberShould {

    private LintRule.Builder builder;
    private LintRegister lintRegister;

    @BeforeEach
    public void setUp() {
        this.builder = new LintRule.Builder().setIssueId("");
        this.lintRegister = new LintRegister();
    }

    @Test
    public void basicLineNumberShouldReturnCorrect() throws Exception {
        LintRule lintRule = this.builder.setImplementation(new LintImplementation<WrappedPrimitive<String>>() {
            @Override
            public Class<?> getClazz() {
                return String.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<String> wrappedPrimitive) {
                if (wrappedPrimitive.equals("test")) {
                    setReportMessage("Error occurs on line " + getLineNumber(wrappedPrimitive.getValue(), wrappedPrimitive));
                    return true;
                }
                return false;
            }
        }).setLevel(LintLevel.ERROR)
          .build();
        this.lintRegister.register(lintRule);
        LintRunner lintRunner =
                new LintRunner(this.lintRegister,
                        "./src/test/resources/test-2.json");

        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = lintRunner.lint();
        assert(lintRunner.analyzeLintAndGiveExitCode() == 1);
        assert(lintOutput.get(lintRule).size() == 1);


        for (Map.Entry<JSONFile, List<String>> entry : lintOutput.get(lintRule).entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (i == 0) {
                    assert(entry.getValue().get(i).equals("Error occurs on line 2"));
                }
            }
        }
    }
}
