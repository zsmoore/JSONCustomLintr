package com.zachary_moore.lint;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class LintRuleShould {

    @Test
    public void expectLintRuleBuilderExceptionBaseBuild() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            LintRule.builder().build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullImplementation() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            LintRule.builder().implementation(null).build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullIssueId() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            LintRule.builder().issueId(null).build();
        });
    }

    @Test
    public void expectLintRuleBuilderExceptionNullLevel() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            LintRule.builder().level(null).build();
        });
    }

    @Test
    public void minimalLintRule() {
        LintRule lintRule = LintRule.builder()
                .implementation(mock(LintImplementation.class))
                .level(LintLevel.IGNORE)
                .issueId("")
                .build();
        assert(lintRule != null);
    }

    @Test
    public void fullLintRule() {
        LintRule lintRule = LintRule.builder()
                .implementation(mock(LintImplementation.class))
                .level(LintLevel.IGNORE)
                .issueId("")
                .issueDescription("")
                .issueExplanation("")
                .build();
        assert(lintRule != null);
    }
}
