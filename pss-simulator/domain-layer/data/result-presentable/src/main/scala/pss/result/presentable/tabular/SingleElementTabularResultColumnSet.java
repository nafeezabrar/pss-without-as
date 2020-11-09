package pss.result.presentable.tabular;

public class SingleElementTabularResultColumnSet extends TabularResultColumnSet {
    protected final TabularResultColumn tabularResultColumn;

    public SingleElementTabularResultColumnSet(TabularResultColumn tabularResultColumn) {
        super(TabularResultColumnsType.SINGLE);
        this.tabularResultColumn = tabularResultColumn;
    }

    public TabularResultColumn getTabularResultColumn() {
        return tabularResultColumn;
    }
}
