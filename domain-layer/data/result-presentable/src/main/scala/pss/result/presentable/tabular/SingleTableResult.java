package pss.result.presentable.tabular;

import java.util.ArrayList;
import java.util.List;

public class SingleTableResult {
    protected final String heading;
    protected List<TabularResultRow> tabularResultRows;
    protected List<TabularResultColumnSet> tabularResultColumns;

    public SingleTableResult(String heading) {
        this.heading = heading;
        this.tabularResultRows = new ArrayList<>();
        this.tabularResultColumns = new ArrayList<>();
    }

    public List<TabularResultRow> getTabularResultRows() {
        return tabularResultRows;
    }

    public List<TabularResultColumnSet> getTabularResultColumns() {
        return tabularResultColumns;
    }

    public void addRow(TabularResultRow resultRow) {
        tabularResultRows.add(resultRow);
    }

    public void addColumn(TabularResultColumnSet column) {
        tabularResultColumns.add(column);
    }

    public void addRows(List<TabularResultRow> resultRows) {
        tabularResultRows.addAll(resultRows);
    }

    public String getHeading() {
        return heading;
    }
}
