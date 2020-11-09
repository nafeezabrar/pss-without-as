package pss.report.common;


import pss.data.valuemap.Value;

public abstract class Report<TReportData extends ReportData> {
    protected int id;
    protected Value value;
    protected TReportData data;

    public Report(int id, Value value, TReportData data) {
        this.id = id;
        this.value = value;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Value getValue() {
        return value;
    }

    public int getIntValue() {
        return value.getIntValue();
    }

    public TReportData getReportData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("Report{id=%d, value=%s, data=%s}", id, value, data);
    }
}
