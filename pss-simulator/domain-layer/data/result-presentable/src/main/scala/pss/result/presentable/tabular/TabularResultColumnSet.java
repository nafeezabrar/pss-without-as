package pss.result.presentable.tabular;

public abstract class TabularResultColumnSet {
    private final TabularResultColumnsType type;

    protected TabularResultColumnSet(TabularResultColumnsType type) {
        this.type = type;
    }

    public TabularResultColumnsType getType() {
        return type;
    }

    public enum TabularResultColumnsType {
        SINGLE,
        MULTI
    }
}
