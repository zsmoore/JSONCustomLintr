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
     * Will be reported by LintRunnner but not fail ReportRunner
     */
    WARNING,
    /**
     * Will be reported by LintRunner and fail ReportRunner
     */
    ERROR
}
