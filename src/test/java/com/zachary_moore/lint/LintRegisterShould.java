package com.zachary_moore.lint;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class LintRegisterShould {

    private LintRegister lintRegister;

    @BeforeEach
    public void setUp() {
        this.lintRegister = new LintRegister();
    }

    @Test
    public void addOneLintRule() throws Exception {
        this.lintRegister.register(LintRule.builder()
                .implementation(mock(LintImplementation.class))
                .issueId("")
                .level(LintLevel.IGNORE)
                .build());
        assert(lintRegister.getLintRules().size() == 1);
        assert(lintRegister.getLintRules().get(0).getIssueId().equals(""));
    }

    @Test
    public void addMultipleLintRules() throws Exception {
        this.lintRegister.register(LintRule.builder()
                .implementation(mock(LintImplementation.class))
                .issueId("")
                .level(LintLevel.IGNORE)
                .build(),
                LintRule.builder()
                    .implementation(mock(LintImplementation.class))
                    .issueId("")
                    .level(LintLevel.IGNORE)
                    .build());
        assert(lintRegister.getLintRules().size() == 2);
        assert(lintRegister.getLintRules().get(0).getIssueId().equals(""));
        assert(lintRegister.getLintRules().get(1).getIssueId().equals(""));
    }

}
