package com.zachary_moore.lint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to be passed around to represent all {@link LintRule} to be checked
 */
public class LintRegister {

    /**
     * List to hold all of our {@link LintRule}
     */
    private final List<LintRule> lintRules;

    public LintRegister() {
        lintRules = new ArrayList<>();
    }

    /**
     * Register new LintRule into {@link LintRegister}
     * @param toRegister variable amount of {@link LintRule} to register
     */
    public void register(LintRule ...toRegister) {
        lintRules.addAll(Arrays.asList(toRegister));
    }

    public List<LintRule> getLintRules() {
        return lintRules;
    }
}
