package com.zachary_moore.runner;

import com.zachary_moore.lint.LintError;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.report.Report;
import j2html.tags.Tag;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ReportGenerator {

    private final Map<LintRule, Map<JSONFile, List<LintError>>> errors;
    private final List<String> invalidFiles;
    private final int exitCode;

    public ReportGenerator(Map<LintRule, Map<JSONFile, List<LintError>>> errors,
        List<String> invalidFiles,
        int exitCode) {
        this.errors = errors;
        this.invalidFiles = invalidFiles;
        this.exitCode = exitCode;
    }

    /**
     * Generate an HTML report for any Lint issues and exit program based off of lint results
     * @param outputPath output path for html report
     */
    public void report(String outputPath) {
        Report report = new Report();
        Tag reportHTML = report.report(this.errors);
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

        System.exit(exitCode);
    }
}
