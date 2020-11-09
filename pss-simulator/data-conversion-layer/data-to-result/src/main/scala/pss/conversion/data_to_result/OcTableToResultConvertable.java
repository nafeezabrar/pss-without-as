package pss.conversion.data_to_result;

import pss.data.oc_table.OcTable;
import pss.exception.PssException;
import pss.result.presentable.tabular.SingleTableResult;

public interface OcTableToResultConvertable<TOcTable extends OcTable> {
    SingleTableResult generateOcTableResult(TOcTable iocTable) throws PssException;
}
