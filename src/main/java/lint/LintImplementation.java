package lint;

public abstract class LintImplementation<T> extends BaseJSONAnalyzer {

    public abstract Class<T> getClazz();

    public abstract boolean shouldReport(T t);
}
