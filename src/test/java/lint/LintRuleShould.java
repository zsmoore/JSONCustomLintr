package lint;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class LintRuleShould {

    @Test(expected = LintRule.Builder.LintRuleBuilderException.class)
    public void expectLintRuleBuilderExceptionBaseBuild() throws LintRule.Builder.LintRuleBuilderException {
        new LintRule.Builder().build();
    }

    @Test(expected = LintRule.Builder.LintRuleBuilderException.class)
    public void expectLintRuleBuilderExceptionNullImplementation() throws LintRule.Builder.LintRuleBuilderException {
        new LintRule.Builder().setImplementation(null).build();
    }

    @Test(expected = LintRule.Builder.LintRuleBuilderException.class)
    public void expectLintRuleBuilderExceptionNullIssueId() throws LintRule.Builder.LintRuleBuilderException {
        new LintRule.Builder().setIssueId(null).build();
    }

    @Test(expected = LintRule.Builder.LintRuleBuilderException.class)
    public void expectLintRuleBuilderExceptionNullLevel() throws LintRule.Builder.LintRuleBuilderException {
        new LintRule.Builder().setLevel(null).build();
    }

    @Test
    public void minimalLintRule() throws LintRule.Builder.LintRuleBuilderException {
        LintRule lintRule = new LintRule.Builder()
                .setImplementation(mock(LintImplementation.class))
                .setLevel(LintLevel.IGNORE)
                .setIssueId("")
                .build();
        assert(lintRule != null);
    }

    @Test
    public void fullLintRule() throws LintRule.Builder.LintRuleBuilderException {
        LintRule lintRule = new LintRule.Builder()
                .setImplementation(mock(LintImplementation.class))
                .setLevel(LintLevel.IGNORE)
                .setIssueId("")
                .setIssueDescription("")
                .setIssueExplanation("")
                .build();
        assert(lintRule != null);
    }
}
