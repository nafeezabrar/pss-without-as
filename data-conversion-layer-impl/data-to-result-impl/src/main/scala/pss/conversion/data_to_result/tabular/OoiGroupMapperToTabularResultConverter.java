package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.OoiGroupMapperToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pss_type.PssType.PssDimensionType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class OoiGroupMapperToTabularResultConverter implements OoiGroupMapperToResultConvertable {

    private Map<Dimension, SingleElementTabularResultColumnSet> columnsForOoi;
    private SingleElementTabularResultColumnSet columnForOoiCombinationValue = ColumnProvider.getOoiValueColumn();

    @Override
    public TabularResult generateOoiGroupMapperResult(PssType pssType, PssVariables pssVariables, OoiGroupMapper ooiGroupMapper) throws PssException {
        initColumns(pssType);
        TabularResult tabularResult = new TabularResult("Ooi Group Mapper Output");
        List<SingleTableResult> singleTableResults = generateSingleTableResult(pssType, ooiGroupMapper, pssVariables);
        tabularResult.addTableResults(singleTableResults);
        return tabularResult;
    }

    private void initColumns(PssType pssType) {
        columnsForOoi = new HashMap<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        for (Dimension dimension : dimensionSet) {
            columnsForOoi.put(dimension, new SingleElementTabularResultColumnSet(new TabularResultColumn(getOoiColumnName(dimension))));
        }
    }

    private String getOoiColumnName(Dimension dimension) {
        return dimension.getName() + "(name, real, local)";
    }

    private List<SingleTableResult> generateSingleTableResult(PssType pssType, OoiGroupMapper ooiGroupMapper, PssVariables pssVariables) throws PssException {
        Set<PssGroup> pssGroups = ooiGroupMapper.getOoiCombinationMap().keySet();
        List<PssGroup> orderedPssGroups = new ArrayList<>(pssGroups);
        orderedPssGroups.sort((o1, o2) -> Integer.compare(o1.getPssGroupId(), o2.getPssGroupId()));
        List<SingleTableResult> singleTableResults = new ArrayList<>();
        for (PssGroup pssGroup : orderedPssGroups) {
            SingleTableResult singleTableResult = generateSingleTableForPssGroup(pssGroup, pssType, ooiGroupMapper, pssVariables);
            singleTableResults.add(singleTableResult);
        }
        return singleTableResults;
    }

    private SingleTableResult generateSingleTableForPssGroup(PssGroup pssGroup, PssType pssType, OoiGroupMapper ooiGroupMapper, PssVariables pssVariables) throws PssException {
        SingleTableResult singleTableResult = new SingleTableResult(getPssGroupHeading(pssGroup));
        addColumns(singleTableResult);
        List<TabularResultRow> resultRows = createRows(pssGroup, pssType, ooiGroupMapper, pssVariables);
        singleTableResult.addRows(resultRows);
        return singleTableResult;
    }

    private String getPssGroupHeading(PssGroup pssGroup) {
        return String.format("Pss Group : %d", pssGroup.getPssGroupId());
    }

    private void addColumns(SingleTableResult singleTableResult) {
        Collection<SingleElementTabularResultColumnSet> columnSetForOoi = columnsForOoi.values();
        for (SingleElementTabularResultColumnSet columnSet : columnSetForOoi) {
            singleTableResult.addColumn(columnSet);
        }
        singleTableResult.addColumn(columnForOoiCombinationValue);
    }

    private List<TabularResultRow> createRows(PssGroup pssGroup, PssType pssType, OoiGroupMapper ooiGroupMapper, PssVariables pssVariables) throws PssException {
        List<TabularResultRow> tabularResultRows = new ArrayList<>();
        Set<OoiCombination> ooiCombinations = ooiGroupMapper.getOoiCombinationMap().get(pssGroup);
        for (OoiCombination ooiCombination : ooiCombinations) {
            TabularResultRow resultRow = createRow(pssGroup, ooiCombination, pssType, pssVariables);
            tabularResultRows.add(resultRow);
        }
        return tabularResultRows;
    }

    private TabularResultRow createRow(PssGroup pssGroup, OoiCombination ooiCombination, PssType pssType, PssVariables pssVariables) throws PssException {
        TabularResultRow resultRow = new TabularResultRow();
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = createTabularResultCellMap(pssGroup, ooiCombination, pssType, pssVariables);
        resultRow.addColumns(tabularResultCellMap);
        return resultRow;
    }

    private Map<TabularResultColumn, TabularResultCell> createTabularResultCellMap(PssGroup pssGroup, OoiCombination ooiCombination, PssType pssType, PssVariables pssVariables) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();

        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleTabularResultCellMap(dimensionSet, pssGroup, (SingleOoiCombination) ooiCombination, (SinglePssVariables) pssVariables);

            case MULTI:
                return createMultiTabularResultCellMap(dimensionSet, pssGroup, (MultiOoiCombination) ooiCombination, (MultiPssVariables) pssVariables);

        }
        throw new PssException(String.format("tabular result cell map creation called for invalid pss type %s", pssDimensionType));
    }

    private Map<TabularResultColumn, TabularResultCell> createSingleTabularResultCellMap(Set<Dimension> dimensionSet, PssGroup pssGroup, SingleOoiCombination ooiCombination, SinglePssVariables pssVariables) throws PssException {
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = new HashMap<>();

        for (Dimension dimension : dimensionSet) {
            SingleElementTabularResultColumnSet columnSet = columnsForOoi.get(dimension);
            Ooi ooi = ooiCombination.getOoi();
            String ooiInformation = String.format("%s,%d,", ooi.getName(), ooi.getId());
            SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
            int localOoiId = localOoiMapper.getOoiToIdMap().get(ooi);
            ooiInformation += localOoiId;
            tabularResultCellMap.put(columnSet.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooiInformation));
        }
        int ooiCombinationValue = pssVariables.getValueMap().getValue(ooiCombination).getIntValue();
        tabularResultCellMap.put(columnForOoiCombinationValue.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooiCombinationValue));
        return tabularResultCellMap;
    }

    private Map<TabularResultColumn, TabularResultCell> createMultiTabularResultCellMap(Set<Dimension> dimensionSet, PssGroup pssGroup, MultiOoiCombination ooiCombination, MultiPssVariables pssVariables) {
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = new HashMap<>();

        for (Dimension dimension : dimensionSet) {
            SingleElementTabularResultColumnSet columnSet = columnsForOoi.get(dimension);
            Ooi ooi = ooiCombination.getOoiMap().get(dimension);
            String ooiInformation = String.format("%s,%d,", ooi.getName(), ooi.getId());
            MultiLocalOoiMapper localOoiMapper = (MultiLocalOoiMapper) pssGroup.getLocalOoiMapper();
            int localOoiId = localOoiMapper.getOoiToIdMaps().get(dimension).get(ooi);
            ooiInformation += localOoiId;
            tabularResultCellMap.put(columnSet.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooiInformation));
        }
        int ooiCombinationValue = pssVariables.getValueMap().getValue(ooiCombination).getIntValue();
        tabularResultCellMap.put(columnForOoiCombinationValue.getTabularResultColumn(), new SingleDataTabularResultCell<>(ooiCombinationValue));
        return tabularResultCellMap;
    }
}
