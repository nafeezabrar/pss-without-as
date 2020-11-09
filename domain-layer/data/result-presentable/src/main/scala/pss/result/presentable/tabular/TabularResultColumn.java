package pss.result.presentable.tabular;

public class TabularResultColumn {
    private final String columnName;

    public TabularResultColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabularResultColumn column = (TabularResultColumn) o;

        return columnName != null ? columnName.equals(column.columnName) : column.columnName == null;

    }

    @Override
    public int hashCode() {
        return columnName != null ? columnName.hashCode() : 0;
    }
}
