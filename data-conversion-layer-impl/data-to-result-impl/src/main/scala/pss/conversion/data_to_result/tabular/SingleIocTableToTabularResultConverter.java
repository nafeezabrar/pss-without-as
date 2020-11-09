package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.IocTableToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.SingleIocRow;
import pss.data.ioc_table.SingleIocTable;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class SingleIocTableToTabularResultConverter implements IocTableToResultConvertable<SingleIocTable> {
    protected final Dimension dimension;
    protected final SinglePssGroup pssGroup;
    private final SingleElementTabularResultColumnSet columnForOoi = ColumnProvider.getOoiLocalAndGlobalIdColumn();
    private MultiElementTabularResultColumnSet columnForIocValue;
    private Map<Integer, TabularResultColumn> columnForIocValueMap;

    public SingleIocTableToTabularResultConverter(Dimension dimension, SinglePssGroup pssGroup) {
        this.dimension = dimension;
        this.pssGroup = pssGroup;
    }

    @Override
    public SingleTableResult generateIocTableResult(SingleIocTable iocTable) throws PssException {
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
        SingleLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        for (Ooi ooi : ooiToIdMap.keySet()) {
            TabularResultColumn iocColumn = new TabularResultColumn(getOoiInfo(ooi, ooiToIdMap.get(ooi)));
            columnForIocValueMap.put(ooiToIdMap.get(ooi), iocColumn);
            iocColumns.add(iocColumn);
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

    private void addRows(SingleTableResult singleTableResult, SingleIocTable iocTable) {
        Map<SingleLocalOoiCombination, SingleIocRow> iocMap = iocTable.getIocMap();
        for (SingleLocalOoiCombination localOoiCombination : iocMap.keySet()) {
            TabularResultRow resultRow = generateResultRow(localOoiCombination, iocMap.get(localOoiCombination).getIocCells());
            singleTableResult.addRow(resultRow);

        }
    }

    private TabularResultRow generateResultRow(SingleLocalOoiCombination localOoiCombination, Set<IocCell> iocCells) {
        TabularResultRow resultRow = new TabularResultRow();
        int localOoiId = localOoiCombination.getLocalOoi();
        Ooi ooi = getOoi(localOoiId);
        resultRow.addColumn(columnForOoi.getTabularResultColumn(), new SingleDataTabularResultCell(getOoiInfo(ooi, localOoiId)));
        Map<TabularResultColumn, TabularResultCell> cellMap = getCellMap(iocCells);
        resultRow.addColumns(cellMap);
        return resultRow;
    }

    private Map<TabularResultColumn, TabularResultCell> getCellMap(Set<IocCell> iocCells) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();

        for (IocCell iocCell : iocCells) {
            int localOoiId = iocCell.getOoiId();
            TabularResultColumn tabularResultColumn = columnForIocValueMap.get(localOoiId);
            cellMap.put(tabularResultColumn, new SingleDataTabularResultCell(iocCell.getIocCount()));
        }
        return cellMap;
    }

    private Ooi getOoi(int localOoiId) {
        Map<Integer, Ooi> idToOoiMap = pssGroup.getLocalOoiMapper().getIdToOoiMap();
        return idToOoiMap.get(localOoiId);
    }

}
