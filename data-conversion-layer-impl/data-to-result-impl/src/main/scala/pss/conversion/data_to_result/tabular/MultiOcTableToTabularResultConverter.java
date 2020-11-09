package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.OcTableToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.Ooi;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class MultiOcTableToTabularResultConverter implements OcTableToResultConvertable<MultiOcTable> {
    protected final Set<Dimension> dimensions;
    protected final MultiPssGroup pssGroup;
    private final SingleElementTabularResultColumnSet columnForOoiValue = ColumnProvider.getOoiValueColumn();
    private MultiElementTabularResultColumnSet columnForOcValue;
    private Map<Dimension, Map<Integer, TabularResultColumn>> columnForIocValueMap;

    public MultiOcTableToTabularResultConverter(Set<Dimension> dimensions, MultiPssGroup pssGroup) {
        this.dimensions = dimensions;
        this.pssGroup = pssGroup;
    }

    @Override
    public SingleTableResult generateOcTableResult(MultiOcTable ocTable) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getIocTableHeading());
        initColumns();
        addColumns(singleTableResult);
        addRows(singleTableResult, ocTable);
        return singleTableResult;
    }

    private String getIocTableHeading() {
        return String.format("Ioc table of Group %d", pssGroup.getPssGroupId());
    }

    private void initColumns() {
        columnForIocValueMap = new HashMap<>();
        List<TabularResultColumn> ocColumns = new ArrayList<>();
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps = localOoiMapper.getOoiToIdMaps();
        for (Dimension dimension : ooiToIdMaps.keySet()) {
            Map<Ooi, Integer> ooiToIdMap = ooiToIdMaps.get(dimension);
            Map<Integer, TabularResultColumn> columnMap = new HashMap<>();
            for (Ooi ooi : ooiToIdMap.keySet()) {
                String ooiInfo = getOoiInfo(ooi, ooiToIdMap.get(ooi));
                TabularResultColumn iocColumn = new TabularResultColumn(String.format("%s:%s", dimension.getName(), ooiInfo));
                columnMap.put(ooiToIdMap.get(ooi), iocColumn);
                ocColumns.add(iocColumn);
            }
            columnForIocValueMap.put(dimension, columnMap);
        }
        columnForOcValue = new MultiElementTabularResultColumnSet(ocColumns);
    }

    private String getOoiInfo(Ooi ooi, int localOoiId) {
        return String.format("%d (%d)", localOoiId, ooi.getId());
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
            for (Integer localOoiId : ocCellMap.keySet()) {
                TabularResultColumn tabularResultColumn = columnForIocValueMap.get(dimension).get(localOoiId);
                cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(ocCellMap.get(localOoiId).getOcCount()));
            }
        }
        return cellMap;
    }
}
