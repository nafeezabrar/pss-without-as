package pss.decodability.calculation;


import pss.data.decodability.DecodabilityType;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pss_type.PssType.PssDimensionType;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.decodability.FullDecodabilityResult;
import pss.decodability.calculation.aps.IntermediatePointsDecodabilityCalculable;
import pss.decodability.checking.DecodabilityChecker;
import pss.decodability.checking.IdealMultiIdgasDecodabilityChecker;
import pss.decodability.checking.IdealSingleIdgasDecodabilityChecker;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.finalreport.FinalReportData;
import pss.result.deanonymization.DeanonymizationResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IntermediatePointApsFullDecodabilityCalculator implements IntermediatePointsDecodabilityCalculable {
    protected final PssType pssType;
    protected final Set<PssGroup> pssGroups;
    protected final OoiUserGroupMappable ooiUserGroupMapper;
    protected final ValueMap valueMap;
    protected final Map<PssGroup, Integer> totalDecodedCountMap;
    protected Map<PssGroup, Map<OoiCombination, Value>> decodedValueMap;
    protected int totalDecoded;

    public IntermediatePointApsFullDecodabilityCalculator(Set<PssGroup> pssGroups, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap, Map<PssGroup, Integer> totalDecodedCountMap, PssType pssType) {
        this.pssGroups = pssGroups;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
        this.valueMap = valueMap;
        this.totalDecodedCountMap = totalDecodedCountMap;
        this.pssType = pssType;
        initDecodedValueMap();
    }

    private void initDecodedValueMap() {
        decodedValueMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            decodedValueMap.put(pssGroup, new HashMap<>());
        }
    }

    @Override
    public FullDecodabilityResult calculateDecodability(DeanonymizationResult deanonymizationResult) throws PssException {
        int userId = getUserId(deanonymizationResult);
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(userId);

        Map<LocalOoiCombination, Value> decodedValueMap = getDecodedValueMap(deanonymizationResult);
        Map<OoiCombination, Value> oldDecodedValueMap = this.decodedValueMap.get(pssGroup);
        this.decodedValueMap.remove(pssGroup);
        Map<OoiCombination, Value> matchedDecodedValueMap = new HashMap<>();
        for (LocalOoiCombination localOoiCombination : decodedValueMap.keySet()) {
            OoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, pssGroup);
            if (valueMap.getValue(ooiCombination) == decodedValueMap.get(localOoiCombination)) {
                matchedDecodedValueMap.put(ooiCombination, valueMap.getValue(ooiCombination));
            }
        }
        this.decodedValueMap.put(pssGroup, matchedDecodedValueMap);
        updateTotalDecoded(oldDecodedValueMap, matchedDecodedValueMap);
        Map<OoiCombination, Value> allDecodedValueMap = getAllDecodedValueMap();
        double decodability = countDecodability();
        return new FullDecodabilityResult(decodability, allDecodedValueMap);
    }

    private Map<LocalOoiCombination, Value> getDecodedValueMap(DeanonymizationResult deanonymizationResult) throws PssException {
        DecodabilityChecker decodabilityChecker = createDecodabilityChecker(deanonymizationResult);
        return decodabilityChecker.getDecodedValueMap();
    }

    private DecodabilityChecker createDecodabilityChecker(DeanonymizationResult deanonymizationResult) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new IdealSingleIdgasDecodabilityChecker((SingleOcTable) deanonymizationResult.getOcTable());
            case MULTI:
                return new IdealMultiIdgasDecodabilityChecker((MultiOcTable) deanonymizationResult.getOcTable());
        }
        throw new PssException(String.format("pss type %s not matched", pssDimensionType.toString()));
    }

    private double countDecodability() {
        int totalOoiCombinations = valueMap.getValues().size();
        return (double) totalDecoded / totalOoiCombinations;
    }

    private Map<OoiCombination, Value> getAllDecodedValueMap() {
        Map<OoiCombination, Value> allDecodedValueMap = new HashMap<>();
        for (Map<OoiCombination, Value> valueMap : decodedValueMap.values()) {
            allDecodedValueMap.putAll(valueMap);
        }
        return allDecodedValueMap;
    }

    private void updateTotalDecoded(Map<OoiCombination, Value> oldDecodedValueMap, Map<OoiCombination, Value> newDecodedValueMap) {
        totalDecoded -= oldDecodedValueMap.size();
        totalDecoded += newDecodedValueMap.size();
    }

    private int getUserId(DeanonymizationResult deanonymizationResult) {
        FinalReport finalReport = deanonymizationResult.getFinalReport();
        FinalReportData reportData = finalReport.getReportData();
        return reportData.getUserId();
    }


    @Override
    public DecodabilityType getDecodabilityType() {
        return DecodabilityType.FULL;
    }
}
