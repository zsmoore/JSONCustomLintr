package com.zachary_moore.lint;

import com.zachary_moore.objects.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LintImplementationShould {

    private LintImplementation<JSONObject> jsonObjectLintImplementation;

    @BeforeEach
    public void setUp() {
        this.jsonObjectLintImplementation = new LintImplementation<JSONObject>() {
            @Override
            public Class<?> getClazz() {
                return JSONObject.class;
            }

            @Override
            public boolean shouldReport(JSONObject jsonObject) {
                return true;
            }
        };
    }

    @Test
    public void throwNoReportSetException() {
        Assertions.assertThrows(LintImplementation.NoReportSetException.class, () -> {
            jsonObjectLintImplementation.report(null);
        });
    }

    @Test
    public void noExceptionThrown() throws LintImplementation.NoReportSetException {
        jsonObjectLintImplementation.setReportMessage("Report Message");
        jsonObjectLintImplementation.report(null);
    }
}
