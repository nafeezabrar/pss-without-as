package pss.conversion.data_to_result.tabular;

import pss.result.presentable.tabular.SingleElementTabularResultColumnSet;
import pss.result.presentable.tabular.TabularResultColumn;

public class ColumnProvider {
    public static SingleElementTabularResultColumnSet getOoiNameColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ooiNameColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getOoiIdColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ooiIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getLocalOoiColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.localOoiIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getOoiValueColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ooiValueColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getOoiIdNameColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ooiIdNameColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getUserIdColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.userIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getUserNameColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.userNameColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getGroupIdColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.groupIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getReportIdColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.reportIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getUserIdNameColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.userIdNameColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getOoiLocalAndGlobalIdColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ooiWithLocalAndGlobalIdColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getIocTableColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.iocTableColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getOcTableColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.ocTableColumnHeading));
    }

    public static SingleElementTabularResultColumnSet getAdversaryOcTableColumn() {
        return new SingleElementTabularResultColumnSet(new TabularResultColumn(ColumnNameProvider.adversaryOcTableColumnHeading));
    }
}
