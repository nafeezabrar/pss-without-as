package pss.conversion.data_to_result.tabular;

import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class PssGroupToTabularResultConverter {
    protected final PssType pssType;
    protected final PssGroup pssGroup;
    private List<SingleTableResult> tableResults;
    private SingleElementTabularResultColumnSet ooiIdColumn = ColumnProvider.getOoiIdColumn();
    private SingleElementTabularResultColumnSet ooiNameColumn = ColumnProvider.getOoiNameColumn();
    private SingleElementTabularResultColumnSet localOoiColumn = ColumnProvider.getLocalOoiColumn();


    public PssGroupToTabularResultConverter(PssType pssType, PssGroup pssGroup) {
        this.pssType = pssType;
        this.pssGroup = pssGroup;
    }

    List<SingleTableResult> generateSingleTableResult() throws PssException {
        tableResults = new ArrayList<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        for (Dimension dimension : dimensionSet) {
            SingleTableResult tableResult = new SingleTableResult(generatePssGroupTableHeading(dimension));
            addColumns(tableResult);
            addRows(tableResult, dimension);
            tableResults.add(tableResult);
        }
        return tableResults;
    }

    private String generatePssGroupTableHeading(Dimension dimension) {
        return String.format("Pss Group : %d, dimension = %s", pssGroup.getPssGroupId(), dimension.getName());
    }

    private void addColumns(SingleTableResult tableResult) {
        tableResult.addColumn(ooiIdColumn);
        tableResult.addColumn(ooiNameColumn);
        tableResult.addColumn(localOoiColumn);
    }

    private void addRows(SingleTableResult tableResult, Dimension dimension) throws PssException {
        Map<Ooi, Integer> localOoiMap = getLocalOoiMap(dimension);

        for (Ooi ooi : localOoiMap.keySet()) {
            TabularResultRow row = new TabularResultRow();
            addColumns(row, ooi, localOoiMap.get(ooi));
            tableResult.addRow(row);
        }
    }

    private void addColumns(TabularResultRow row, Ooi ooi, int localOoiId) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        cellMap.put(ooiIdColumn.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooi.getId()));
        cellMap.put(ooiNameColumn.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooi.getName()));
        cellMap.put(localOoiColumn.getTabularResultColumn(), new SingleDataTabularResultCell<>(localOoiId));
        row.addColumns(cellMap);
    }


    private Map<Ooi, Integer> getLocalOoiMap(Dimension dimension) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                SingleLocalOoiMapper singleLocalOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
                return singleLocalOoiMapper.getOoiToIdMap();
            case MULTI:
                MultiLocalOoiMapper multiLocalOoiMapper = (MultiLocalOoiMapper) pssGroup.getLocalOoiMapper();
                Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps = multiLocalOoiMapper.getOoiToIdMaps();
                return ooiToIdMaps.get(dimension);
        }
        throw new PssException(String.format("getLocalOoiMap called from invalid pss type %s", pssDimensionType.toString()));
    }
}
