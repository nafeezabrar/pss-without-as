package pss.reader.report;

import pss.report.anonymized.AnonymizedReport;

import java.util.Set;

public interface AnonymizedReportReadable {
    Set<AnonymizedReport> readAnonymizedReport();
}
