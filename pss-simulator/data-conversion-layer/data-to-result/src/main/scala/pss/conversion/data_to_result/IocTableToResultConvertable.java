package pss.conversion.data_to_result;

import pss.data.ioc_table.IocTable;
import pss.exception.PssException;
import pss.result.presentable.tabular.SingleTableResult;

public interface IocTableToResultConvertable<TIocTable extends IocTable> {
    SingleTableResult generateIocTableResult(TIocTable iocTable) throws PssException;
}
