package pss.result.adversary;

import pss.data.ooi.combination.OoiCombination;
import pss.report.finalreport.FinalReport;

import java.util.List;

public abstract class AdversaryResult<TOoiCombination extends OoiCombination> {
    protected final int reportedByAdversaryCount;
    protected final int reportedValueCountByAdversary;
    protected final int leakedReportCount;
    protected final int totalDecodedValueCount;
    protected final List<FinalReport> leakedReports;

    protected AdversaryResult(int reportedByAdversaryCount, int reportedValueCountByAdversary, int leakedReportCount, int totalDecodedValueCount, List<FinalReport> leakedReports) {
        this.reportedByAdversaryCount = reportedByAdversaryCount;
        this.reportedValueCountByAdversary = reportedValueCountByAdversary;
        this.leakedReportCount = leakedReportCount;
        this.totalDecodedValueCount = totalDecodedValueCount;
        this.leakedReports = leakedReports;
    }

    public int getReportedByAdversaryCount() {
        return reportedByAdversaryCount;
    }

    public int getLeakedReportCount() {
        return leakedReportCount;
    }

    public int getReportedValueCountByAdversary() {
        return reportedValueCountByAdversary;
    }

    public List<FinalReport> getLeakedReports() {
        return leakedReports;
    }

    public int getTotalDecodedValueCount() {
        return totalDecodedValueCount;
    }
}
