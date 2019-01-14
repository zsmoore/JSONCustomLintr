package runner;

import j2html.TagCreator;
import j2html.tags.Tag;
import report.Report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportRunner {

    private  LintRunner lintRunner;

    public ReportRunner(LintRunner lintRunner) {
        this.lintRunner = lintRunner;
    }

    public void report(String outputPath) {
        Report report = new Report();
        Tag reportHTML = report.report(lintRunner.lint());
        try {
            FileWriter writer = new FileWriter(new File(outputPath + "/lint-report.html"));
            TagCreator.html(reportHTML).render(writer);
            System.out.println("Lint Report Written to " + new File(outputPath + "/lint-report.html").getAbsolutePath());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
