package com.zachary_moore.lint;

/**
 * Varying levels for {@link LintRule}
 */
public enum LintLevel {
    /**
     * Will not be reported by LintRunnner
     */
    IGNORE,
    /**
     * Will be reported by LintRunnner but not fail ReportGenerator
     */
    WARNING,
    /**
     * Will be reported by LintRunner and fail ReportGenerator
     */
    ERROR
}
