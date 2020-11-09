package pss.data.ioc_table;

public class IocCell {
    protected final int ooiId;
    protected int iocCount;

    public IocCell(int ooiId) {
        this.ooiId = ooiId;
        this.iocCount = 0;
    }

    public int getOoiId() {
        return ooiId;
    }

    public int getIocCount() {
        return iocCount;
    }

    public void setIocCount(int iocCount) {
        this.iocCount = iocCount;
    }

    public IocCell cloneIocCell() {
        IocCell clonedIocCell = new IocCell(ooiId);
        clonedIocCell.setIocCount(iocCount);
        return clonedIocCell;
    }
}
