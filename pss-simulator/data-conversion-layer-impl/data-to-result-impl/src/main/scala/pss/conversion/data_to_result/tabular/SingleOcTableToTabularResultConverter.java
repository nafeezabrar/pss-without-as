package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.OcTableToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.Ooi;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleOcTableToTabularResultConverter implements OcTableToResultConvertable<SingleOcTable> {
    protected final Dimension dimension;
    protected final SinglePssGroup pssGroup;
    private final SingleElementTabularResultColumnSet columnForValue = ColumnProvider.getOoiValueColumn();
    private MultiElementTabularResultColumnSet columnForOcValue;
    private Map<Integer, TabularResultColumn> columnForOcValueMap;

    public SingleOcTableToTabularResultConverter(Dimension dimension, SinglePssGroup pssGroup) {
        this.dimension = dimension;
        this.pssGroup = pssGroup;
    }

    @Override
    public SingleTableResult generateOcTableResult(SingleOcTable ocTable) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getIocTableHeading());
        initColumns();
        addColumns(singleTableResult);
        addRows(singleTableResult, ocTable);
        return singleTableResult;
    }

    private String getIocTableHeading() {
        return String.format("OC table of Group %d", pssGroup.getPssGroupId());
    }

    private void initColumns() {
        columnForOcValueMap = new HashMap<>();
        List<TabularResultColumn> iocColumns = new ArrayList<>();
        SingleLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        for (Ooi ooi : ooiToIdMap.keySet()) {
            TabularResultColumn iocColumn = new TabularResultColumn(getOoiInfo(ooi, ooiToIdMap.get(ooi)));
            columnForOcValueMap.put(ooiToIdMap.get(ooi), iocColumn);
            iocColumns.add(iocColumn);
        }
        columnForOcValue = new MultiElementTabularResultColumnSet(iocColumns);
    }

    private String getOoiInfo(Ooi ooi, int localOoiId) {
        return String.format("%d (%d)", localOoiId, ooi.getId());
    }

    private void addColumns(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnForValue);
        singleTableResult.addColumn(columnForOcValue);
    }

    private void addRows(SingleTableResult singleTableResult, SingleOcTable ocTable) {
//        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
//        for (Value value : ocRowMap.keySet()) {
//            TabularResultRow resultRow = generateResultRow(value, ocRowMap.get(value).getOcCellMap());
//            singleTableResult.addRow(resultRow);
//
//        }
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

    private Ooi getOoi(int localOoiId) {
        Map<Integer, Ooi> idToOoiMap = pssGroup.getLocalOoiMapper().getIdToOoiMap();
        return idToOoiMap.get(localOoiId);
    }
}
