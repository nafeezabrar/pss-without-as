package pss.report.generation;

import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.reader.report.ObservedReportDataReadable;
import pss.report.observed.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pss.data.pss_type.PssType.PssDimensionType;

public class FromFileObservedReportSetGenerator implements ObservedReportSetGenerable {
    protected final ObservedReportDataReadable observedReportDataReader;
    protected final PssType pssType;

    public FromFileObservedReportSetGenerator(ObservedReportDataReadable observedReportDataReader, PssType pssType) {
        this.observedReportDataReader = observedReportDataReader;
        this.pssType = pssType;
    }


    @Override
    public List<ObservedReport> generateObservedReports(ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException {
        Map<Value, OoiCombination> valueToCombinationMap = getValueMaps(valueMap);
        List<ObservedReportFromFile> observedReportsFromFile = observedReportDataReader.generateObservedReportData();
        List<ObservedReportFromFile> sortedReportData = new ArrayList<>();
        sortedReportData.addAll(observedReportsFromFile);
        sortedReportData.sort((r1, r2) -> Integer.compare(r1.getReportId(), r2.getReportId()));
        List<ObservedReport> observedReports = generateObservedReports(valueToCombinationMap, sortedReportData, ooiUserGroupMapper);
        return observedReports;
    }

    private List<ObservedReport> generateObservedReports(Map<Value, OoiCombination> valueToCombinationMap, List<ObservedReportFromFile> observedReportsFromFile, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return generateSingleObservedReports(valueToCombinationMap, observedReportsFromFile, ooiUserGroupMapper);
            case MULTI:
                return generateMultiObservedReports(observedReportsFromFile, valueToCombinationMap, ooiUserGroupMapper);
        }
        throw new PssException(String.format("Pss dimension %s not matched", pssDimensionType.toString()));
    }

    private List<ObservedReport> generateMultiObservedReports(List<ObservedReportFromFile> observedReportsFromFile, Map<Value, OoiCombination> valueToCombinationMap, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        List<ObservedReport> observedReports = new ArrayList<>();
        for (ObservedReportFromFile reportFromFile : observedReportsFromFile) {
            MultiObservedReport multiObservedReport = generateMultiObservedReport(reportFromFile, valueToCombinationMap, ooiUserGroupMapper);
            observedReports.add(multiObservedReport);
        }
        return observedReports;
    }

    private List<ObservedReport> generateSingleObservedReports(Map<Value, OoiCombination> valueToCombinationMap, List<ObservedReportFromFile> observedReportsFromFile, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        List<ObservedReport> observedReports = new ArrayList<>();
        for (ObservedReportFromFile reportFromFile : observedReportsFromFile) {
            SingleObservedReport singleObservedReport = generateSingleObservedReport(reportFromFile, valueToCombinationMap, ooiUserGroupMapper);
            observedReports.add(singleObservedReport);
        }
        return observedReports;
    }

    private Map<Value, OoiCombination> getValueMaps(ValueMap valueMap) {
        Map<OoiCombination, Value> combinationToValueMap = valueMap.getValues();
        Map<Value, OoiCombination> valueToCombinationMap = new HashMap<>();
        for (OoiCombination ooiCombination : combinationToValueMap.keySet()) {
            Value value = combinationToValueMap.get(ooiCombination);
            valueToCombinationMap.put(value, ooiCombination);
        }
        return valueToCombinationMap;
    }

    private SingleObservedReport generateSingleObservedReport(ObservedReportFromFile reportFromFile, Map<Value, OoiCombination> valueToCombinationMap, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        int reportId = reportFromFile.getReportId();
        int userId = reportFromFile.getUserId();
        Value value = reportFromFile.getValue();
        OoiCombination ooiCombination = valueToCombinationMap.get(value);
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(userId);
        SingleLocalOoiCombination localOoiCombination = (SingleLocalOoiCombination) ooiUserGroupMapper.getLocalOoiCombination(ooiCombination, pssGroup);
        return new SingleObservedReport(reportId, value, new SingleObservedReportData(userId, localOoiCombination));
    }

    private MultiObservedReport generateMultiObservedReport(ObservedReportFromFile reportFromFile, Map<Value, OoiCombination> valueToCombinationMap, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        int reportId = reportFromFile.getReportId();
        int userId = reportFromFile.getUserId();
        Value value = reportFromFile.getValue();
        OoiCombination ooiCombination = valueToCombinationMap.get(value);
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(userId);
        MultiLocalOoiCombination localOoiCombination = (MultiLocalOoiCombination) ooiUserGroupMapper.getLocalOoiCombination(ooiCombination, pssGroup);
        return new MultiObservedReport(reportId, value, new MultiObservedReportData(userId, localOoiCombination));
    }
}
