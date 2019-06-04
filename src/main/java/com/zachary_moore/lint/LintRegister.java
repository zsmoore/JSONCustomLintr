package com.zachary_moore.lint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


/**
 * Class to be passed around to represent all {@link LintRule} to be checked
 */

@Getter
@NoArgsConstructor
public class LintRegister {

    /**
     * List to hold all of our {@link LintRule}
     */
    private final List<LintRule> lintRules = new ArrayList<>();

    /**
     * Register new LintRule into {@link LintRegister}
     * @param toRegister variable amount of {@link LintRule} to register
     */
    public void register(LintRule ...toRegister) {
        lintRules.addAll(Arrays.asList(toRegister));
    }
}
