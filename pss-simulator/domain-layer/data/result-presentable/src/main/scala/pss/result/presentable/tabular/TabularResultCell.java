package pss.result.presentable.tabular;

public abstract class TabularResultCell<T> {

    protected final TabularResultCellType cellType;

    public TabularResultCell(TabularResultCellType cellType) {

        this.cellType = cellType;
    }

    public TabularResultCellType getCellType() {
        return cellType;
    }
}
