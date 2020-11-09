package pss.anonymization.ioctable.generation;

import pss.data.ioc_table.IocTable;
import pss.data.ooi.local.collection.LocalOoiCollection;

public interface IocTableGenerator<TIocTable extends IocTable, TLocalOoiCollection extends LocalOoiCollection> {
    TIocTable generateIocTable(TLocalOoiCollection ooiCollection);
}
