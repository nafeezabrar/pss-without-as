package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.AdversaryOcTableToResultConvertable;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class SingleAdversaryOcTableToTabularResultConverter implements AdversaryOcTableToResultConvertable<SingleOcTable> {
    private final SingleElementTabularResultColumnSet columnForValue = ColumnProvider.getOoiValueColumn();
    private MultiElementTabularResultColumnSet columnForOcValue;
    private Map<Integer, TabularResultColumn> columnForOcValueMap;


    @Override
    public SingleTableResult generateOcTableResult(SingleOcTable ocTable) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getIocTableHeading());
        initColumns(ocTable);
        addColumns(singleTableResult);
        addRows(singleTableResult, ocTable);
        return singleTableResult;
    }

    private String getIocTableHeading() {
        return "";
    }

    private void initColumns(SingleOcTable ocTable) {
        columnForOcValueMap = new HashMap<>();
        List<TabularResultColumn> ocColumns = new ArrayList<>();
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        Set<Integer> localOoiIds = new HashSet<>();
        for (Value value : ocRowMap.keySet()) {
            SingleOcRow singleOcRow = ocRowMap.get(value);
            Map<Integer, OcCell> ocCellMap = singleOcRow.getOcCellMap();
            for (Integer localOoiId : ocCellMap.keySet()) {
                if (!localOoiIds.contains(localOoiId))
                    localOoiIds.add(localOoiId);
            }
        }
        for (Integer localOoiId : localOoiIds) {
            TabularResultColumn ocColumn = new TabularResultColumn(String.valueOf(localOoiId));
            columnForOcValueMap.put(localOoiId, ocColumn);
            ocColumns.add(ocColumn);
        }
        columnForOcValue = new MultiElementTabularResultColumnSet(ocColumns);
    }

    private void addColumns(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnForValue);
        singleTableResult.addColumn(columnForOcValue);
    }

    private void addRows(SingleTableResult singleTableResult, SingleOcTable ocTable) {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        for (Value value : ocRowMap.keySet()) {
            TabularResultRow resultRow = generateResultRow(value, ocRowMap.get(value).getOcCellMap());
            singleTableResult.addRow(resultRow);

        }
    }

    private TabularResultRow generateResultRow(Value value, Map<Integer, OcCell> ocCellMap) {
        TabularResultRow resultRow = new TabularResultRow();
        int intValue = value.getIntValue();
        resultRow.addColumn(columnForValue.getTabularResultColumn(), new SingleDataTabularResultCell(intValue));
        Map<TabularResultColumn, TabularResultCell> cellMap = getCellMap(ocCellMap);
        resultRow.addColumns(cellMap);
        return resultRow;
    }

    private Map<TabularResultColumn, TabularResultCell> getCellMap(Map<Integer, OcCell> ocCellMap) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();

        for (Integer localOoiId : ocCellMap.keySet()) {
            OcCell ocCell = ocCellMap.get(localOoiId);
            TabularResultColumn tabularResultColumn = columnForOcValueMap.get(localOoiId);
            cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(ocCell.getOcCount()));
        }
        return cellMap;
    }
}
