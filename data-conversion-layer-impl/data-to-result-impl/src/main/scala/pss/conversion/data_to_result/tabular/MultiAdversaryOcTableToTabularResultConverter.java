package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.AdversaryOcTableToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.Ooi;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class MultiAdversaryOcTableToTabularResultConverter implements AdversaryOcTableToResultConvertable<MultiOcTable> {
    protected final Set<Dimension> dimensions;
    private final SingleElementTabularResultColumnSet columnForOoiValue = ColumnProvider.getOoiValueColumn();
    private MultiElementTabularResultColumnSet columnForOcValue;
    private Map<Dimension, Map<Integer, TabularResultColumn>> columnForOcValueMap;

    public MultiAdversaryOcTableToTabularResultConverter(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public SingleTableResult generateOcTableResult(MultiOcTable ocTable) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getIocTableHeading());
        initColumns(ocTable);
        addColumns(singleTableResult);
        addRows(singleTableResult, ocTable);
        return singleTableResult;
    }

    private String getIocTableHeading() {
        return "";
    }

    private void initColumns(MultiOcTable ocTable) {
        columnForOcValueMap = new HashMap<>();

        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();

        Map<Dimension, Set<Integer>> foundLocalOoiMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            foundLocalOoiMap.put(dimension, new HashSet<>());
        }
        for (Value value : ocRowMap.keySet()) {
            MultiOcRow multiOcRow = ocRowMap.get(value);
            Map<Dimension, Map<Integer, OcCell>> ocCellMap = multiOcRow.getOcCellMap();
            for (Dimension dimension : ocCellMap.keySet()) {
                Map<Integer, OcCell> ocCells = ocCellMap.get(dimension);
                Set<Integer> localOoiIdSet = new HashSet<>();
                for (int localOoiId : ocCells.keySet()) {
                    localOoiIdSet.add(localOoiId);
                }
                foundLocalOoiMap.get(dimension).addAll(localOoiIdSet);
            }
        }
        List<TabularResultColumn> columnList = new ArrayList<>();
        for (Dimension dimension : dimensions) {
            Set<Integer> localOoiIds = foundLocalOoiMap.get(dimension);
            Map<Integer, TabularResultColumn> ocColumns = new HashMap<>();
            for (Integer localOoiId : localOoiIds) {
                TabularResultColumn tabularResultColumn = new TabularResultColumn(String.valueOf(localOoiId));
                ocColumns.put(localOoiId, tabularResultColumn);
                columnList.add(tabularResultColumn);
            }
            columnForOcValueMap.put(dimension, ocColumns);
        }
        columnForOcValue = new MultiElementTabularResultColumnSet(columnList);
    }

    private String getOoiInfo(Ooi ooi, int localOoiId) {
        return localOoiId + " (" + ooi.getId() + ")";
    }

    private void addColumns(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnForOoiValue);
        singleTableResult.addColumn(columnForOcValue);
    }

    private void addRows(SingleTableResult singleTableResult, MultiOcTable ocTable) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        for (Value value : ocRowMap.keySet()) {
            TabularResultRow resultRow = generateResultRow(value, ocRowMap.get(value).getOcCellMap());
            singleTableResult.addRow(resultRow);

        }
    }

    private TabularResultRow generateResultRow(Value value, Map<Dimension, Map<Integer, OcCell>> ocCellMap) {
        TabularResultRow resultRow = new TabularResultRow();

        resultRow.addColumn(columnForOoiValue.getTabularResultColumn(), new SingleDataTabularResultCell(value.getIntValue()));

        Map<TabularResultColumn, TabularResultCell> cellMap = getCellMap(ocCellMap);
        resultRow.addColumns(cellMap);
        return resultRow;
    }

    private Map<TabularResultColumn, TabularResultCell> getCellMap(Map<Dimension, Map<Integer, OcCell>> ocCellMaps) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        for (Dimension dimension : ocCellMaps.keySet()) {
            Map<Integer, OcCell> ocCellMap = ocCellMaps.get(dimension);
            Map<Integer, TabularResultColumn> columnMap = columnForOcValueMap.get(dimension);
            for (Integer localOoiId : ocCellMap.keySet()) {
                TabularResultColumn tabularResultColumn = columnMap.get(localOoiId);
                cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(ocCellMap.get(localOoiId).getOcCount()));
            }
            for (Integer localOoiId : columnMap.keySet()) {
                if (!ocCellMap.containsKey(localOoiId)) {
                    TabularResultColumn tabularResultColumn = columnMap.get(localOoiId);
                    cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(0));
                }
            }
        }
        return cellMap;
    }
}
