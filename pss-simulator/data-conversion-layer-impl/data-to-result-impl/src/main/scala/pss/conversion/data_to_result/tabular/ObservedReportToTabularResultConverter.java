package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.ObservedReportToResultConvertable;
import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.report.observed.ObservedReportData;
import pss.result.presentable.tabular.*;

import java.util.*;

import static pss.data.pss_type.PssType.PssDimensionType;

public class ObservedReportToTabularResultConverter implements ObservedReportToResultConvertable {

    protected final PssType pssType;

    private final SingleElementTabularResultColumnSet columnForReportId = ColumnProvider.getReportIdColumn();
    private final SingleElementTabularResultColumnSet columnForReportedValue = ColumnProvider.getOoiValueColumn();
    private final SingleElementTabularResultColumnSet columnForUserId = ColumnProvider.getUserIdColumn();
    private final SingleElementTabularResultColumnSet columnForUserName = ColumnProvider.getUserNameColumn();
    private final SingleElementTabularResultColumnSet columnForUserGroupId = ColumnProvider.getGroupIdColumn();
    private MultiElementTabularResultColumnSet columnsForOoi;
    private Map<Dimension, TabularResultColumn> ooiColumnMapForDimension;

    public ObservedReportToTabularResultConverter(PssType pssType) {
        this.pssType = pssType;
    }

    @Override
    public TabularResult generateObservedReportResult(List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        TabularResult tabularResult = new TabularResult(getReportResultHeading());
        SingleTableResult singleTableResult = createTable(observedReports, ooiUserGroupMapper);
        tabularResult.addTableResult(singleTableResult);
        return tabularResult;
    }

    private String getReportResultHeading() {
        return "Observed Report Result information";
    }

    private SingleTableResult createTable(List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        SingleTableResult tableResult = new SingleTableResult(getReportTableHeading());
        initColumns(tableResult);
        addRows(tableResult, observedReports, ooiUserGroupMapper);
        return tableResult;
    }

    private void initColumns(SingleTableResult tableResult) {
        tableResult.addColumn(columnForReportId);
        tableResult.addColumn(columnForUserId);
        tableResult.addColumn(columnForUserName);
        tableResult.addColumn(columnForUserGroupId);
        initOoiColumns(tableResult);
        tableResult.addColumn(columnForReportedValue);
    }

    private String getReportTableHeading() {
        return "Observed Report List";
    }

    private void initOoiColumns(SingleTableResult tableResult) {
        ooiColumnMapForDimension = new HashMap<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        List<TabularResultColumn> columnList = new ArrayList<>();
        for (Dimension dimension : dimensionSet) {
            TabularResultColumn column = new TabularResultColumn(getOoiColumnName(dimension));
            columnList.add(column);
            ooiColumnMapForDimension.put(dimension, column);
        }
        columnsForOoi = new MultiElementTabularResultColumnSet(columnList);
        tableResult.addColumn(columnsForOoi);
    }

    private String getOoiColumnName(Dimension dimension) {
        return String.format("Ooi of %s:<br>local (real id, name)", dimension.getName());
    }

    private void addRows(SingleTableResult tableResult, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        for (ObservedReport report : observedReports) {
            TabularResultRow row = createRow(report, ooiUserGroupMapper);
            tableResult.addRow(row);
        }
    }

    private TabularResultRow createRow(ObservedReport report, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        int reportId = report.getId();
        cellMap.put(columnForReportId.getTabularResultColumn(), new SingleDataTabularResultCell<>(reportId));
        ObservedReportData reportData = report.getReportData();
        int userId = reportData.getUserId();
        cellMap.put(columnForUserId.getTabularResultColumn(), new SingleDataTabularResultCell<>(userId));
        User user = ooiUserGroupMapper.getUser(userId);
        String userName = user.getName();
        cellMap.put(columnForUserName.getTabularResultColumn(), new SingleDataTabularResultCell<>(userName));
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user);
        int pssGroupId = pssGroup.getPssGroupId();
        cellMap.put(columnForUserGroupId.getTabularResultColumn(), new SingleDataTabularResultCell<>(pssGroupId));
        int intValue = report.getIntValue();
        cellMap.put(columnForReportedValue.getTabularResultColumn(), new SingleDataTabularResultCell<>(intValue));
        Map<TabularResultColumn, TabularResultCell> ooiCellMap = createCellMapsForOoi(report, ooiUserGroupMapper);
        cellMap.putAll(ooiCellMap);
        TabularResultRow row = new TabularResultRow();
        row.addColumns(cellMap);
        return row;
    }

    private Map<TabularResultColumn, TabularResultCell> createCellMapsForOoi(ObservedReport report, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();

        switch (pssDimensionType) {
            case SINGLE:
                return createOoiCellMapsForSinglePss(report, (SingleOoiUserGroupMappable) ooiUserGroupMapper);
            case MULTI:
                return createOoiCellMapsForMultiPss(report, (MultiOoiUserGroupMappable) ooiUserGroupMapper);
        }
        throw new PssException(String.format("Cell map for ooi called for invalid pss type %s", pssDimensionType.toString()));
    }

    private Map<TabularResultColumn, TabularResultCell> createOoiCellMapsForSinglePss(ObservedReport report, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Dimension dimension = pssType.getDimension();
        TabularResultColumn tabularResultColumn = ooiColumnMapForDimension.get(dimension);
        Map<TabularResultColumn, TabularResultCell> ooiCellMap = new HashMap<>();
        SingleLocalOoiCombination localOoiCombination = (SingleLocalOoiCombination) report.getReportData().getLocalOoiCombination();
        User user = getUser(report, ooiUserGroupMapper);
        SingleOoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
        Ooi ooi = ooiCombination.getOoi();
        String ooiInfo = getOoiInfo(ooi, localOoiCombination.getLocalOoi());
        ooiCellMap.put(tabularResultColumn, new SingleDataTabularResultCell<>(ooiInfo));
        return ooiCellMap;
    }

    private String getOoiInfo(Ooi ooi, int localOoiId) {
        return String.format("%d (%d, %s)", localOoiId, ooi.getId(), ooi.getName());
    }

    private User getUser(ObservedReport report, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        int userId = report.getReportData().getUserId();
        return ooiUserGroupMapper.getUser(userId);
    }

    private Map<TabularResultColumn, TabularResultCell> createOoiCellMapsForMultiPss(ObservedReport report, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Map<TabularResultColumn, TabularResultCell> ooiCellMap = new HashMap<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        MultiLocalOoiCombination localOoiCombination = (MultiLocalOoiCombination) report.getReportData().getLocalOoiCombination();
        Map<Dimension, Integer> localOoiIdMap = localOoiCombination.getLocalOoiMap();
        User user = getUser(report, ooiUserGroupMapper);
        MultiOoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
        Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
        for (Dimension dimension : dimensionSet) {
            TabularResultColumn tabularResultColumn = ooiColumnMapForDimension.get(dimension);
            int localOoiId = localOoiIdMap.get(dimension);
            Ooi ooi = ooiMap.get(dimension);
            ooiCellMap.put(tabularResultColumn, new SingleDataTabularResultCell<>(getOoiInfo(ooi, localOoiId)));
        }
        return ooiCellMap;
    }

}
