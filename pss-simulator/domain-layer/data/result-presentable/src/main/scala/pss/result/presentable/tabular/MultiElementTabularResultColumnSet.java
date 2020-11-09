package pss.result.presentable.tabular;

import java.util.List;

public class MultiElementTabularResultColumnSet extends TabularResultColumnSet {
    protected final List<TabularResultColumn> tabularResultColumnList;

    public MultiElementTabularResultColumnSet(List<TabularResultColumn> tabularResultColumnList) {
        super(TabularResultColumnsType.MULTI);
        this.tabularResultColumnList = tabularResultColumnList;
    }

    public List<TabularResultColumn> getTabularResultColumnList() {
        return tabularResultColumnList;
    }
}

