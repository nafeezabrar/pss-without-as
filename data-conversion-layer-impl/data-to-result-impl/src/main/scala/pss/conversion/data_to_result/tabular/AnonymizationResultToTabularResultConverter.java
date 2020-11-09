package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.AnonymizationResultToPresentableResultConvertable;
import pss.conversion.data_to_result.IocTableToResultConvertable;
import pss.data.dimension.Dimension;
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
import pss.exception.PssException;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.report.observed.ObservedReportData;
import pss.result.anonymization.AnonymizationResult;
import pss.result.presentable.tabular.*;

import java.util.*;

import static pss.data.pss_type.PssType.PssDimensionType;

public class AnonymizationResultToTabularResultConverter implements AnonymizationResultToPresentableResultConvertable {

    private final SingleElementTabularResultColumnSet columnForReportId = ColumnProvider.getReportIdColumn();
    private final SingleElementTabularResultColumnSet columnForUserIdName = ColumnProvider.getUserIdNameColumn();
    private final SingleElementTabularResultColumnSet columnForUserGroupId = ColumnProvider.getGroupIdColumn();
    private final SingleElementTabularResultColumnSet columnForOoiCombinationValue = ColumnProvider.getOoiValueColumn();
    private final SingleElementTabularResultColumnSet columnForObservedGlobalAndLocalOois = ColumnProvider.getOoiLocalAndGlobalIdColumn();
    private final SingleElementTabularResultColumnSet columnForIocTable = ColumnProvider.getIocTableColumn();
    private Map<Dimension, MultiElementTabularResultColumnSet> columnSetMapForAnonymizedOoi;
    private Map<Dimension, Integer> anonymityMap;

    @Override
    public TabularResult generateAnonymizationResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws PssException {
        TabularResult tabularResult = new TabularResult(getAnonymizationResultTableHeading());
        SingleTableResult singleTableResult = generateTableResult(pssType, ooiUserGroupMapper, anonymizationResults, observedReports);
        tabularResult.addTableResult(singleTableResult);
        return tabularResult;
    }

    private SingleTableResult generateTableResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws PssException {
        SingleTableResult tableResult = new SingleTableResult(getAnonymizationResultTableHeading());
        initColumns(pssType, anonymizationResults);
        addColumns(tableResult);
        addRows(tableResult, pssType, ooiUserGroupMapper, anonymizationResults, observedReports);
        return tableResult;
    }

    private void initColumns(PssType pssType, List<AnonymizationResult> anonymizationResults) throws PssException {
        initColumnsForAnonymizedOois(pssType, anonymizationResults);
    }

    private void initColumnsForAnonymizedOois(PssType pssType, List<AnonymizationResult> anonymizationResults) throws PssException {
        columnSetMapForAnonymizedOoi = new HashMap<>();
        initAnonymityMap(pssType, anonymizationResults.get(0));
        for (Dimension dimension : pssType.getDimensionSet()) {
            String dimensionName = dimension.getName();
            List<TabularResultColumn> columnsForOois = new ArrayList<>();
            int anonymity = anonymityMap.get(dimension);
            for (int i = 1; i <= anonymity; i++) {
                columnsForOois.add(new TabularResultColumn(String.format("%s: %d local(global)", dimensionName, i)));
            }
            columnSetMapForAnonymizedOoi.put(dimension, new MultiElementTabularResultColumnSet(columnsForOois));
        }
    }

    private void initAnonymityMap(PssType pssType, AnonymizationResult anonymizationResult) throws PssException {
        anonymityMap = new HashMap<>();
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
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
        singleTableResult.addColumn(columnForIocTable);
    }

    private void addColumnsForAnonymizedOois(SingleTableResult singleTableResult) {
        for (Dimension dimension : columnSetMapForAnonymizedOoi.keySet()) {
            MultiElementTabularResultColumnSet columnSet = columnSetMapForAnonymizedOoi.get(dimension);
            singleTableResult.addColumn(columnSet);
        }
    }

    private void addRows(SingleTableResult tableResult, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws PssException {
        int totalReportCount = anonymizationResults.size();
        for (int i = 0; i < totalReportCount; i++) {
            TabularResultRow resultRow = generateRow(anonymizationResults.get(i), observedReports.get(i), ooiUserGroupMapper, pssType);
            tableResult.addRow(resultRow);
        }

    }

    private TabularResultRow generateRow(AnonymizationResult anonymizationResult, ObservedReport observedReport, OoiUserGroupMappable ooiUserGroupMapper, PssType pssType) throws PssException {
        TabularResultRow resultRow = new TabularResultRow();
        addObservedReportRelatedColumns(resultRow, observedReport, ooiUserGroupMapper, pssType);
        IocTableToResultConvertable iocTableToResultConverter;
        int userId = observedReport.getReportData().getUserId();
        if (pssType.getPssDimensionType() == PssDimensionType.SINGLE)
            iocTableToResultConverter = new SingleIocTableToTabularResultConverter(pssType.getDimension(), (SinglePssGroup) ooiUserGroupMapper.getPssGroup(userId));
        else
            iocTableToResultConverter = new MultiIocTableToTabularResultConverter(pssType.getDimensionSet(), (MultiPssGroup) ooiUserGroupMapper.getPssGroup(userId));
        addAnonymizationRelatedCells(resultRow, observedReport, anonymizationResult, ooiUserGroupMapper, pssType, iocTableToResultConverter);
        return resultRow;
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
        return String.format("%d-%s", userId, user.getName());
    }

    private void addCellForObservedOois(TabularResultRow resultRow, LocalOoiCombination ooiCombination, PssType pssType, User user, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
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
        return String.format("%s -> %d (%d)", dimension.getName(), localOoiId, ooiId);
    }

    private String addCellForMultiObservedOois(Set<Dimension> dimensionSet, MultiLocalOoiCombination localOoiCombination, User user, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        String ooiInfoList = "";
        MultiOoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
        Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
        Map<Dimension, Integer> localOoiMap = localOoiCombination.getLocalOoiMap();
        for (Dimension dimension : dimensionSet) {
            Ooi ooi = ooiMap.get(dimension);
            int localOoi = localOoiMap.get(dimension);
            ooiInfoList += "\n" + getOoiInfoString(dimension, ooi.getId(), localOoi);
        }
        return ooiInfoList;
    }

    private void addAnonymizationRelatedCells(TabularResultRow resultRow, ObservedReport observedReport, AnonymizationResult anonymizationResult, OoiUserGroupMappable ooiUserGroupMapper, PssType pssType, IocTableToResultConvertable iocTableToResultConverter) throws PssException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                addSingleAnonymizedRelatedCells(resultRow, observedReport, anonymizationResult, (SingleOoiUserGroupMappable) ooiUserGroupMapper, pssType.getDimension());
                break;
            case MULTI:
                addMultiAnonymizedRelatedColumns(resultRow, observedReport, anonymizationResult, (MultiOoiUserGroupMappable) ooiUserGroupMapper, pssType.getDimensionSet());
                break;
        }
        //TODO
//        SingleTableResult singleTableResult = iocTableToResultConverter.generateIocTableResult(anonymizationResult.getIocTable());
//        SubTableTabularResultCell iocTableResultCell = new SubTableTabularResultCell(singleTableResult);
//        resultRow.addColumn(columnForIocTable.getTabularResultColumn(), iocTableResultCell);
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
        return String.format("%d(%d)", localAnonymizedOoiId, ooi.getId());
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
