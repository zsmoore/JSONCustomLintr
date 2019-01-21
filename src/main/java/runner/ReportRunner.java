package runner;

import j2html.tags.Tag;
import report.Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportRunner {

    private final LintRunner lintRunner;

    public ReportRunner(LintRunner lintRunner) {
        this.lintRunner = lintRunner;
    }

    public void report(String outputPath) {
        Report report = new Report();
        Tag reportHTML = report.report(lintRunner.lint());
        try {
            FileWriter writer = new FileWriter(new File(outputPath + "/lint-report.html"));
            reportHTML.render(writer);
            System.out.println("Lint Report Written to " + new File(outputPath + "/lint-report.html").getAbsolutePath());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
