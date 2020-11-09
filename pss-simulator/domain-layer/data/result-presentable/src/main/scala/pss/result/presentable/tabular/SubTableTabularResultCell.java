package pss.result.presentable.tabular;

public class SubTableTabularResultCell<T> extends TabularResultCell<T> {
    protected final SingleTableResult childSingleTableResult;

    public SubTableTabularResultCell(SingleTableResult childSingleTableResult) {
        super(TabularResultCellType.SUB_TABLE_DATA);
        this.childSingleTableResult = childSingleTableResult;
    }

    public SingleTableResult getChildSingleTableResult() {
        return childSingleTableResult;
    }
}
