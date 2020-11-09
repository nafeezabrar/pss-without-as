package pss.result.presentable.tabular;

import java.util.HashMap;
import java.util.Map;

public class TabularResultRow {
    Map<TabularResultColumn, TabularResultCell> tabularResultCells;

    public TabularResultRow() {
        tabularResultCells = new HashMap<>();
    }

    public TabularResultCell getTabularResultCell(TabularResultColumn column) {
        return tabularResultCells.get(column);
    }

    public void addColumns(Map<TabularResultColumn, TabularResultCell> newColumns) {
        tabularResultCells.putAll(newColumns);
    }

    public void addColumn(TabularResultColumn column, TabularResultCell cell) {
        tabularResultCells.put(column, cell);
    }
}
