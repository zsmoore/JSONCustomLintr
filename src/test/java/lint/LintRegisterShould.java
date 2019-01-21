package lint;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class LintRegisterShould {

    private LintRegister lintRegister;

    @Before
    public void setUp() {
        this.lintRegister = new LintRegister();
    }

    @Test
    public void addOneLintRule() throws Exception {
        this.lintRegister.register(new LintRule.Builder()
                .setImplementation(mock(LintImplementation.class))
                .setIssueId("")
                .setLevel(LintLevel.IGNORE)
                .build());
        assert(lintRegister.getLintRules().size() == 1);
        assert(lintRegister.getLintRules().get(0).getIssueId().equals(""));
    }

    @Test
    public void addMultipleLintRules() throws Exception {
        this.lintRegister.register(new LintRule.Builder()
                .setImplementation(mock(LintImplementation.class))
                .setIssueId("")
                .setLevel(LintLevel.IGNORE)
                .build(),
                new LintRule.Builder()
                    .setImplementation(mock(LintImplementation.class))
                    .setIssueId("")
                    .setLevel(LintLevel.IGNORE)
                    .build());
        assert(lintRegister.getLintRules().size() == 2);
        assert(lintRegister.getLintRules().get(0).getIssueId().equals(""));
        assert(lintRegister.getLintRules().get(1).getIssueId().equals(""));
    }

}
