package pss.report.observed;

import pss.data.valuemap.Value;

public class ObservedReportFromFile {
    protected final int reportId;
    protected final Value value;
    protected final int userId;

    public ObservedReportFromFile(int reportId, Value value, int userId) {
        this.reportId = reportId;
        this.value = value;
        this.userId = userId;
    }

    public int getReportId() {
        return reportId;
    }

    public Value getValue() {
        return value;
    }

    public int getUserId() {
        return userId;
    }
}
