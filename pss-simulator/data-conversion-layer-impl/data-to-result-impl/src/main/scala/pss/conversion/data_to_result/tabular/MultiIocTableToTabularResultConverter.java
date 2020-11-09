package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.IocTableToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.MultiIocRow;
import pss.data.ioc_table.MultiOcTable;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class MultiIocTableToTabularResultConverter implements IocTableToResultConvertable<MultiOcTable> {
    protected final Set<Dimension> dimensions;
    protected final MultiPssGroup pssGroup;
    private final SingleElementTabularResultColumnSet columnForOoi = ColumnProvider.getOoiLocalAndGlobalIdColumn();
    private MultiElementTabularResultColumnSet columnForIocValue;
    private Map<Dimension, Map<Integer, TabularResultColumn>> columnForIocValueMap;

    public MultiIocTableToTabularResultConverter(Set<Dimension> dimensions, MultiPssGroup pssGroup) {
        this.dimensions = dimensions;
        this.pssGroup = pssGroup;
    }

    @Override
    public SingleTableResult generateIocTableResult(MultiOcTable iocTable) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getIocTableHeading());
        initColumns();
        addColumns(singleTableResult);
        addRows(singleTableResult, iocTable);
        return singleTableResult;
    }

    private String getIocTableHeading() {
        return String.format("Ioc table of Group %d", pssGroup.getPssGroupId());
    }

    private void initColumns() {
        columnForIocValueMap = new HashMap<>();
        List<TabularResultColumn> iocColumns = new ArrayList<>();
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps = localOoiMapper.getOoiToIdMaps();
        for (Dimension dimension : ooiToIdMaps.keySet()) {
            Map<Ooi, Integer> ooiToIdMap = ooiToIdMaps.get(dimension);
            Map<Integer, TabularResultColumn> columnMap = new HashMap<>();
            for (Ooi ooi : ooiToIdMap.keySet()) {
                String ooiInfo = getOoiInfo(ooi, ooiToIdMap.get(ooi));
                TabularResultColumn iocColumn = new TabularResultColumn(String.format("%s:%s", dimension.getName(), ooiInfo));
                columnMap.put(ooiToIdMap.get(ooi), iocColumn);
                iocColumns.add(iocColumn);
            }
            columnForIocValueMap.put(dimension, columnMap);
        }
        columnForIocValue = new MultiElementTabularResultColumnSet(iocColumns);
    }

    private String getOoiInfo(Ooi ooi, int localOoiId) {
        return String.format("%d (%d)", localOoiId, ooi.getId());
    }

    private void addColumns(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnForOoi);
        singleTableResult.addColumn(columnForIocValue);
    }

    private void addRows(SingleTableResult singleTableResult, MultiOcTable iocTable) {
        Map<MultiLocalOoiCombination, MultiIocRow> iocMap = iocTable.getIocMap();
        for (MultiLocalOoiCombination localOoiCombination : iocMap.keySet()) {
            TabularResultRow resultRow = generateResultRow(localOoiCombination, iocMap.get(localOoiCombination).getIocCellMap());
            singleTableResult.addRow(resultRow);

        }
    }

    private TabularResultRow generateResultRow(MultiLocalOoiCombination localOoiCombination, Map<Dimension, Set<IocCell>> iocCells) {
        TabularResultRow resultRow = new TabularResultRow();
        Map<Dimension, Integer> ooiIdMap = localOoiCombination.getLocalOoiMap();
        String ooiInfo = "";
        for (Dimension dimension : dimensions) {
            int localOoiId = ooiIdMap.get(dimension);
            Ooi ooi = getOoi(localOoiId, dimension);
            ooiInfo += String.format("%s:%s", dimension.getName(), getOoiInfo(ooi, localOoiId));
        }
        resultRow.addColumn(columnForOoi.getTabularResultColumn(), new SingleDataTabularResultCell(ooiInfo));

        Map<TabularResultColumn, TabularResultCell> cellMap = getCellMap(iocCells);
        resultRow.addColumns(cellMap);
        return resultRow;
    }

    private Map<TabularResultColumn, TabularResultCell> getCellMap(Map<Dimension, Set<IocCell>> iocCells) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        for (Dimension dimension : iocCells.keySet()) {
            for (IocCell iocCell : iocCells.get(dimension)) {
                int localOoiId = iocCell.getOoiId();
                TabularResultColumn tabularResultColumn = columnForIocValueMap.get(dimension).get(localOoiId);
                cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(iocCell.getIocCount()));
            }
        }
        return cellMap;
    }

    private Ooi getOoi(int localOoiId, Dimension dimension) {
        Map<Integer, Ooi> idToOoiMap = pssGroup.getLocalOoiMapper().getIdToOoiMaps().get(dimension);
        return idToOoiMap.get(localOoiId);
    }
}
