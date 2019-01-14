package lint;

public abstract class LintImplementation<T> extends BaseJSONAnalyzer {

    private String reportMessage;

    public abstract Class<?> getClazz();

    public abstract boolean shouldReport(T t);

    public String report(T t) throws NoReportSetException {
        if (reportMessage == null) {
            throw new NoReportSetException("No Report Message Set When Lint Error Found");
        }
        return reportMessage;
    }

    protected void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public static class NoReportSetException extends Exception {
        private NoReportSetException(String errorMessage) {
            super(errorMessage);
        }
    }
}
