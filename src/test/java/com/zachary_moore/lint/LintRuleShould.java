package com.zachary_moore.lint;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class LintRuleShould {

    @Test
    public void expectLintRuleBuilderExceptionBaseBuild() {
        Assertions.assertThrows(LintRule.Builder.LintRuleBuilderException.class, () -> {
            new LintRule.Builder().build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullImplementation() {
        Assertions.assertThrows(LintRule.Builder.LintRuleBuilderException.class, () -> {
            new LintRule.Builder().setImplementation(null).build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullIssueId() {
        Assertions.assertThrows(LintRule.Builder.LintRuleBuilderException.class, () -> {
            new LintRule.Builder().setIssueId(null).build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullLevel() {
        Assertions.assertThrows(LintRule.Builder.LintRuleBuilderException.class, () -> {
            new LintRule.Builder().setLevel(null).build();
        });
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
