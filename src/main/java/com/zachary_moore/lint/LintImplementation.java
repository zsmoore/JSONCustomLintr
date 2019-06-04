package com.zachary_moore.lint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


/**
 * Abstract class to represent a Lint implementation
 * @param <T> Type of Class to build a LintRule for
 */
public abstract class LintImplementation<T> extends BaseJSONAnalyzer {

    /**
     * Message reported when a lint issue is found, can be set at runtime or statically
     */
    @Setter(AccessLevel.PROTECTED)
    private String reportMessage;

    /**
     * @return Class to build a Lint Implementation for, if building one for a {@link com.zachary_moore.objects.WrappedPrimitive} must return the inner generic
     */
    public abstract Class<?> getClazz();

    /**
     * Determines whether or not the object passed through is an issue in terms of linting
     * This method should use helpers given in {@link BaseJSONAnalyzer}
     * @param t Object the Lint Implementation is built for
     * @return true if this object violates a lint rule
     */
    public abstract boolean shouldReport(T t);

    /**
     * Reports a lint error, either is set in {@link #shouldReport(Object)} by {@link #setReportMessage(String)} or more statically here
     * @param t Object caught to be violating a lint rule
     * @return Message describing why this object violates the lint rule
     * @throws NoReportSetException if reportMessage is null or this is not Overridden
     */
    public String report(T t) throws NoReportSetException {
        if (reportMessage == null) {
            throw new NoReportSetException("No Report Message Set When Lint Error Found");
        }
        return reportMessage;
    }

    public static class NoReportSetException extends Exception {
        private NoReportSetException(String errorMessage) {
            super(errorMessage);
        }
    }
}
