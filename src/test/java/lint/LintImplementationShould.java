package lint;

import objects.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class LintImplementationShould {

    private LintImplementation<JSONObject> jsonObjectLintImplementation;

    @Before
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

    @Test(expected = LintImplementation.NoReportSetException.class)
    public void throwNoReportSetException() throws LintImplementation.NoReportSetException {
        jsonObjectLintImplementation.report(null);
    }

    @Test
    public void noExceptionThrown() throws LintImplementation.NoReportSetException {
        jsonObjectLintImplementation.setReportMessage("Report Message");
        jsonObjectLintImplementation.report(null);
    }
}
