package com.zachary_moore.lint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LintRegister {

    private final List<LintRule> lintRules;

    public LintRegister() {
        lintRules = new ArrayList<>();
    }

    public void register(LintRule ...toRegister) {
        lintRules.addAll(Arrays.asList(toRegister));
    }

    public List<LintRule> getLintRules() {
        return lintRules;
    }
}
