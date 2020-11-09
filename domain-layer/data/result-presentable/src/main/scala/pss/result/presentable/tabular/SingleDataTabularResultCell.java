package pss.result.presentable.tabular;

public class SingleDataTabularResultCell<T> extends TabularResultCell<T> {
    protected final T data;

    public SingleDataTabularResultCell(T data) {
        super(TabularResultCellType.SINGLE_DATA);
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
