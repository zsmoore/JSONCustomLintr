package com.zachary_moore.runner;

import j2html.tags.Tag;
import com.zachary_moore.report.Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class ReportRunner {

    private final LintRunner lintRunner;

    public ReportRunner(LintRunner lintRunner) {
        this.lintRunner = lintRunner;
    }

    public void report(String outputPath) {
        Report report = new Report();
        Tag reportHTML = report.report(lintRunner.lint());
        if (reportHTML == null) {
            System.exit(0);
        }
        try {
            File outFile = new File(outputPath + "/lint-report.html");
            outFile.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(outFile);
            reportHTML.render(writer);
            Logger.getGlobal().info("Lint Report Written to " + new File(outputPath + "/lint-report.html").getAbsolutePath());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.exit(this.lintRunner.analyzeLintAndGiveExitCode());
    }
}
