package pss.conversion.data_to_result.tabular;

import pss.config.printing.DefaultPrintingFileName;
import pss.conversion.data_to_result.AdversaryOcTableToResultConvertable;
import pss.conversion.data_to_result.OcTableToResultConvertable;
import pss.conversion.data_to_result.SimplestAdversaryResultWithIntermediateDecodabilityToResultConvertable;
import pss.data.decodability.DecodabilityType;
import pss.data.dimension.Dimension;
import pss.data.oc_table.OcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.data.pssvariable.group.PssGroup;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.data.user.User;
import pss.decodability.DecodabilityResult;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.library.SerializableObjectReader;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.report.observed.ObservedReportData;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.anonymization.AnonymizationResult;
import pss.result.deanonymization.DeanonymizationResult;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.tabular.*;

import java.io.IOException;
import java.util.*;

public class SimplestAdversaryResultWithIntermediatePointDecodabilityToTabularResultConverter implements SimplestAdversaryResultWithIntermediateDecodabilityToResultConvertable {
    private final SingleElementTabularResultColumnSet columnForReportId = ColumnProvider.getReportIdColumn();
    private final SingleElementTabularResultColumnSet columnForUserIdName = ColumnProvider.getUserIdNameColumn();
    private final SingleElementTabularResultColumnSet columnForUserGroupId = ColumnProvider.getGroupIdColumn();
    private final SingleElementTabularResultColumnSet columnForOoiCombinationValue = ColumnProvider.getOoiValueColumn();
    private final SingleElementTabularResultColumnSet columnForObservedGlobalAndLocalOois = ColumnProvider.getOoiLocalAndGlobalIdColumn();
    private final SingleElementTabularResultColumnSet columnForOcTable = ColumnProvider.getOcTableColumn();
    private final SingleElementTabularResultColumnSet columnForAdversaryOcTable = ColumnProvider.getAdversaryOcTableColumn();
    private Map<Dimension, MultiElementTabularResultColumnSet> columnSetMapForAnonymizedOoi;
    private Map<Dimension, Integer> anonymityMap;
    private Map<DecodabilityType, SingleElementTabularResultColumnSet> columnForDecodabilityResult;

    @Override
    public TabularResult generateAdversaryWithIntermediateDecodabilityPresentableResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryRunResultWithSingleOcTable, IntermediatePointDecodabilityResults decodabilityResultsMap, String resultDirectory) throws PssException, IOException, ClassNotFoundException {
        TabularResult tabularResult = new TabularResult(getAnonymizationResultTableHeading());
        SingleTableResult singleTableResult = generateTableResult(pssType, ooiUserGroupMapper, fullCycleResults, observedReports, adversaryRunResultWithSingleOcTable, decodabilityResultsMap, resultDirectory);
        tabularResult.addTableResult(singleTableResult);
        return tabularResult;
    }

    private SingleTableResult generateTableResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResult, IntermediatePointDecodabilityResults decodabilityResultsMap, String resultDirectory) throws PssException, IOException, ClassNotFoundException {
        SingleTableResult tableResult = new SingleTableResult(getAnonymizationResultTableHeading());
        initColumns(pssType, fullCycleResults, decodabilityResultsMap.getDecodabilityTypes());
        addColumns(tableResult);
        addRows(tableResult, pssType, ooiUserGroupMapper, fullCycleResults, observedReports, adversaryResult, decodabilityResultsMap.getDecodabilityResultMaps(), resultDirectory);
        return tableResult;
    }

    private void initColumns(PssType pssType, List<FullCycleResult> fullCycleResults, Set<DecodabilityType> decodabilityTypes) throws PssException {
        initColumnsForAnonymizedOois(pssType, fullCycleResults);
        initColumnsForDecodabilityResults(decodabilityTypes);
    }

    private void initColumnsForDecodabilityResults(Set<DecodabilityType> decodabilityTypes) {
        columnForDecodabilityResult = new HashMap<>();
        for (DecodabilityType decodabilityType : decodabilityTypes) {
            String decodabilityColumnName = getDecodabilityColumnName(decodabilityType);
            TabularResultColumn decodabilityResultColumn = new TabularResultColumn(decodabilityColumnName);
            columnForDecodabilityResult.put(decodabilityType, new SingleElementTabularResultColumnSet(decodabilityResultColumn));
        }
    }

    private String getDecodabilityColumnName(DecodabilityType decodabilityType) {
        return decodabilityType.toString() + "Adversary decodability";
    }

    private void initColumnsForAnonymizedOois(PssType pssType, List<FullCycleResult> fullCycleResults) throws PssException {
        columnSetMapForAnonymizedOoi = new HashMap<>();
        initAnonymityMap(pssType, fullCycleResults.get(0).getAnonymizationResult());
        for (Dimension dimension : pssType.getDimensionSet()) {
            String dimensionName = dimension.getName();
            List<TabularResultColumn> columnsForOois = new ArrayList<>();
            int anonymity = anonymityMap.get(dimension);
            for (int i = 1; i <= anonymity; i++) {
                columnsForOois.add(new TabularResultColumn(dimensionName + ": " + i + " local(global)"));
            }
            columnSetMapForAnonymizedOoi.put(dimension, new MultiElementTabularResultColumnSet(columnsForOois));
        }
    }

    private void initAnonymityMap(PssType pssType, AnonymizationResult anonymizationResult) throws PssException {
        anonymityMap = new HashMap<>();
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                addSingleAnonymity(pssType, anonymityMap, anonymizationResult);
                break;
            case MULTI:
                addMultiAnonymity(anonymityMap, anonymizationResult);
                break;
            default:
                throw new PssException(String.format("anonymity map cannot be created for pss type %s", pssDimensionType.toString()));
        }
    }

    private void addSingleAnonymity(PssType pssType, Map<Dimension, Integer> anonymityMap, AnonymizationResult anonymizationResult) throws PssException {
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = (SingleAnonymizedLocalOoiSet) anonymizationResult.getAnonymizedLocalOoiSet();
        int anonymity = anonymizedLocalOoiSet.getAnonymizedIdSet().size();
        anonymityMap.put(pssType.getDimension(), anonymity);
    }

    private void addMultiAnonymity(Map<Dimension, Integer> anonymityMap, AnonymizationResult anonymizationResult) {
        MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet = (MultiAnonymizedLocalOoiSet) anonymizationResult.getAnonymizedLocalOoiSet();
        Map<Dimension, Set<Integer>> anonymizedOoiSets = anonymizedLocalOoiSet.getAnonymizedOoiSets();
        for (Dimension dimension : anonymizedOoiSets.keySet()) {
            anonymityMap.put(dimension, anonymizedOoiSets.get(dimension).size());
        }
    }

    private String getAnonymizationResultTableHeading() {
        return "Anonymization Result Table";
    }

    private void addColumns(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnForReportId);
        singleTableResult.addColumn(columnForUserIdName);
        singleTableResult.addColumn(columnForUserGroupId);
        singleTableResult.addColumn(columnForObservedGlobalAndLocalOois);
        addColumnsForAnonymizedOois(singleTableResult);
        singleTableResult.addColumn(columnForOoiCombinationValue);
        singleTableResult.addColumn(columnForOcTable);
        singleTableResult.addColumn(columnForAdversaryOcTable);
        addColumnsForDecodability(singleTableResult);
    }

    private void addColumnsForDecodability(SingleTableResult singleTableResult) {
        for (SingleElementTabularResultColumnSet tabularResultColumnSet : columnForDecodabilityResult.values()) {
            singleTableResult.addColumn(tabularResultColumnSet);
        }
    }

    private void addColumnsForAnonymizedOois(SingleTableResult singleTableResult) {
        for (Dimension dimension : columnSetMapForAnonymizedOoi.keySet()) {
            MultiElementTabularResultColumnSet columnSet = columnSetMapForAnonymizedOoi.get(dimension);
            singleTableResult.addColumn(columnSet);
        }
    }

    private void addRows(SingleTableResult tableResult, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResult, Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMap, String resultDirectory) throws PssException, IOException, ClassNotFoundException {
        int totalReportCount = fullCycleResults.size();
        String ocTableFileName = DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory);
        SerializableObjectReader<SingleOcTable> objectReader = new SerializableObjectReader<>(ocTableFileName);
        List<SingleOcTable> adversaryOcTables = objectReader.getObjects(totalReportCount);
        for (int i = 0; i < totalReportCount; i++) {
            FullCycleResult fullCycleResult = fullCycleResults.get(i);
            AnonymizationResult anonymizationResult = fullCycleResult.getAnonymizationResult();
            DeanonymizationResult deanonymizationResult = fullCycleResult.getDeanonymizationResult();
            OcTable adversaryOcTable = adversaryOcTables.get(i);
            TabularResultRow resultRow = generateRow(anonymizationResult, deanonymizationResult, observedReports.get(i), ooiUserGroupMapper, pssType, adversaryOcTable, decodabilityResultMap.get(fullCycleResult));
            tableResult.addRow(resultRow);
        }

    }

    private TabularResultRow generateRow(AnonymizationResult anonymizationResult, DeanonymizationResult deanonymizationResult, ObservedReport observedReport, OoiUserGroupMappable ooiUserGroupMapper, PssType pssType, OcTable adversaryOcTable, Map<DecodabilityType, DecodabilityResult> decodabilityResultMap) throws PssException {
        TabularResultRow resultRow = new TabularResultRow();
        addObservedReportRelatedColumns(resultRow, observedReport, ooiUserGroupMapper, pssType);
        OcTableToResultConvertable ocTableToResultConverter;
        AdversaryOcTableToResultConvertable adversaryOcTableToResultConverter;
        int userId = observedReport.getReportData().getUserId();
        if (pssType.getPssDimensionType() == PssType.PssDimensionType.SINGLE) {
            ocTableToResultConverter = new SingleOcTableToTabularResultConverter(pssType.getDimension(), (SinglePssGroup) ooiUserGroupMapper.getPssGroup(userId));
            adversaryOcTableToResultConverter = new SingleAdversaryOcTableToTabularResultConverter();
        } else {
            ocTableToResultConverter = new MultiOcTableToTabularResultConverter(pssType.getDimensionSet(), (MultiPssGroup) ooiUserGroupMapper.getPssGroup(userId));
            adversaryOcTableToResultConverter = new MultiAdversaryOcTableToTabularResultConverter(pssType.getDimensionSet());
        }
        addAnonymizationRelatedCells(resultRow, observedReport, anonymizationResult, deanonymizationResult, ooiUserGroupMapper, pssType, ocTableToResultConverter, adversaryOcTable, adversaryOcTableToResultConverter);
        addDecodabilityRows(resultRow, decodabilityResultMap);

        return resultRow;
    }

    private void addDecodabilityRows(TabularResultRow resultRow, Map<DecodabilityType, DecodabilityResult> decodabilityResultMap) {
        for (DecodabilityType decodabilityType : decodabilityResultMap.keySet()) {
            DecodabilityResult decodabilityResult = decodabilityResultMap.get(decodabilityType);
            SingleElementTabularResultColumnSet tabularResultColumnSet = columnForDecodabilityResult.get(decodabilityType);
            SingleDataTabularResultCell resultCell = new SingleDataTabularResultCell(decodabilityResult.toString());
            resultRow.addColumn(tabularResultColumnSet.getTabularResultColumn(), resultCell);
        }
    }

    private void addObservedReportRelatedColumns(TabularResultRow resultRow, ObservedReport observedReport, OoiUserGroupMappable ooiUserGroupMapper, PssType pssType) throws PssException {
        int reportId = observedReport.getId();
        resultRow.addColumn(columnForReportId.getTabularResultColumn(), new SingleDataTabularResultCell<>(reportId));
        int value = observedReport.getIntValue();
        resultRow.addColumn(columnForOoiCombinationValue.getTabularResultColumn(), new SingleDataTabularResultCell<>(value));
        ObservedReportData reportData = observedReport.getReportData();
        int userId = reportData.getUserId();
        User user = ooiUserGroupMapper.getUser(userId);
        resultRow.addColumn(columnForUserIdName.getTabularResultColumn(), new SingleDataTabularResultCell<>(getUserInfoString(userId, user)));
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user);
        resultRow.addColumn(columnForUserGroupId.getTabularResultColumn(), new SingleDataTabularResultCell<>(pssGroup.getPssGroupId()));

        LocalOoiCombination localOoiCombination = reportData.getLocalOoiCombination();
        addCellForObservedOois(resultRow, localOoiCombination, pssType, user, ooiUserGroupMapper);

    }

    private String getUserInfoString(int userId, User user) {
        return userId + "-" + user.getName();
    }

    private void addCellForObservedOois(TabularResultRow resultRow, LocalOoiCombination ooiCombination, PssType pssType, User user, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        String observedOoiInfo = "";
        switch (pssDimensionType) {
            case SINGLE:
                Dimension dimension = pssType.getDimension();
                observedOoiInfo = addCellForSingleObservedOoi(dimension, (SingleLocalOoiCombination) ooiCombination, user, (SingleOoiUserGroupMappable) ooiUserGroupMapper);
                break;
            case MULTI:
                observedOoiInfo = addCellForMultiObservedOois(pssType.getDimensionSet(), (MultiLocalOoiCombination) ooiCombination, user, (MultiOoiUserGroupMappable) ooiUserGroupMapper);
                break;
        }
        resultRow.addColumn(columnForObservedGlobalAndLocalOois.getTabularResultColumn(), new SingleDataTabularResultCell<>(observedOoiInfo));
    }

    private String addCellForSingleObservedOoi(Dimension dimension, SingleLocalOoiCombination localOoiCombination, User user, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        int localOoiId = localOoiCombination.getLocalOoi();
        SingleOoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
        int ooiId = ooiCombination.getOoi().getId();
        return getOoiInfoString(dimension, ooiId, localOoiId);
    }

    private String getOoiInfoString(Dimension dimension, int ooiId, int localOoiId) {
        return dimension.getName() + " -> " + localOoiId + " (" + ooiId + ")";
    }

    private String addCellForMultiObservedOois(Set<Dimension> dimensionSet, MultiLocalOoiCombination localOoiCombination, User user, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        String ooiInfos = "";
        MultiOoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
        Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
        Map<Dimension, Integer> localOoiMap = localOoiCombination.getLocalOoiMap();
        for (Dimension dimension : dimensionSet) {
            Ooi ooi = ooiMap.get(dimension);
            int localOoi = localOoiMap.get(dimension);
            ooiInfos += "\n" + getOoiInfoString(dimension, ooi.getId(), localOoi);
        }
        return ooiInfos;
    }

    private void addAnonymizationRelatedCells(TabularResultRow resultRow, ObservedReport observedReport, AnonymizationResult anonymizationResult, DeanonymizationResult deanonymizationResult, OoiUserGroupMappable ooiUserGroupMapper, PssType pssType, OcTableToResultConvertable ocTableToResultConverter, OcTable adversaryOcTable, AdversaryOcTableToResultConvertable adversaryOcTableToResultConvertable) throws PssException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                addSingleAnonymizedRelatedCells(resultRow, observedReport, anonymizationResult, (SingleOoiUserGroupMappable) ooiUserGroupMapper, pssType.getDimension());
                break;
            case MULTI:
                addMultiAnonymizedRelatedColumns(resultRow, observedReport, anonymizationResult, (MultiOoiUserGroupMappable) ooiUserGroupMapper, pssType.getDimensionSet());
                break;
        }

        SingleTableResult singleTableResult = ocTableToResultConverter.generateOcTableResult(deanonymizationResult.getOcTable());
        SubTableTabularResultCell ocTableResultCell = new SubTableTabularResultCell(singleTableResult);
        resultRow.addColumn(columnForOcTable.getTabularResultColumn(), ocTableResultCell);

        SingleTableResult adversaryOcTableResult = adversaryOcTableToResultConvertable.generateOcTableResult(adversaryOcTable);
        SubTableTabularResultCell adversaryOcTableResultCell = new SubTableTabularResultCell(adversaryOcTableResult);
        resultRow.addColumn(columnForAdversaryOcTable.getTabularResultColumn(), adversaryOcTableResultCell);
    }

    private void addSingleAnonymizedRelatedCells(TabularResultRow resultRow, ObservedReport observedReport, AnonymizationResult anonymizationResult, SingleOoiUserGroupMappable ooiUserGroupMapper, Dimension dimension) throws PssException {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = (SingleAnonymizedLocalOoiSet) anonymizationResult.getAnonymizedLocalOoiSet();
        Set<Integer> anonymizedIdSet = anonymizedLocalOoiSet.getAnonymizedIdSet();
        int userId = observedReport.getReportData().getUserId();
        User user = ooiUserGroupMapper.getUser(userId);
        MultiElementTabularResultColumnSet columnSet = columnSetMapForAnonymizedOoi.get(dimension);
        int i = 0;
        for (Integer localAnonymizedOoiId : anonymizedIdSet) {
            Ooi ooi = ooiUserGroupMapper.getOoi(localAnonymizedOoiId, user);
            String ooiInfo = getAnonymizedOoiInfo(ooi, localAnonymizedOoiId);
            TabularResultColumn tabularResultColumn = columnSet.getTabularResultColumnList().get(i);
            cellMap.put(tabularResultColumn, new SingleDataTabularResultCell<>(ooiInfo));
            i++;
        }
        resultRow.addColumns(cellMap);
    }

    private String getAnonymizedOoiInfo(Ooi ooi, int localAnonymizedOoiId) {
        return localAnonymizedOoiId + "(" + ooi.getId() + ")";
    }

    private void addMultiAnonymizedRelatedColumns(TabularResultRow resultRow, ObservedReport observedReport, AnonymizationResult anonymizationResult, MultiOoiUserGroupMappable ooiUserGroupMapper, Set<Dimension> dimensionSet) throws PssException {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        MultiAnonymizedLocalOoiSet multiAnonymizedLocalOoiSet = (MultiAnonymizedLocalOoiSet) anonymizationResult.getAnonymizedLocalOoiSet();
        Map<Dimension, Set<Integer>> anonymizedLocalOoiSet = multiAnonymizedLocalOoiSet.getAnonymizedOoiSets();
        int userId = observedReport.getReportData().getUserId();
        User user = ooiUserGroupMapper.getUser(userId);
        for (Dimension dimension : dimensionSet) {
            MultiElementTabularResultColumnSet columnSet = columnSetMapForAnonymizedOoi.get(dimension);
            Set<Integer> anonymizedIdSet = anonymizedLocalOoiSet.get(dimension);
            int i = 0;
            for (Integer localAnonymizedOoiId : anonymizedIdSet) {
                Ooi ooi = ooiUserGroupMapper.getOoi(dimension, localAnonymizedOoiId, user);
                String ooiInfo = getAnonymizedOoiInfo(ooi, localAnonymizedOoiId);
                TabularResultColumn tabularResultColumn = columnSet.getTabularResultColumnList().get(i);
                cellMap.put(tabularResultColumn, new SingleDataTabularResultCell<>(ooiInfo));
                i++;
            }
        }
        resultRow.addColumns(cellMap);
    }
}
