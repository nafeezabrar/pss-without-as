package pss.decodability.calculation;

import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.SingleUserApsAdversaryType;
import pss.data.decodability.DecodabilityType;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.decodability.FullDecodabilityResult;
import pss.decodability.calculation.aps.AdversaryEndPointDecodabilityCalculable;
import pss.decodability.checking.AdversaryMultiIdgasDecodabilityChecker;
import pss.decodability.checking.AdversarySingleIdgasDecodabilityChecker;
import pss.decodability.checking.DecodabilityChecker;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;

import java.util.HashMap;
import java.util.Map;

public class EndPointApsAdversaryFullDecodabilityCalculator implements AdversaryEndPointDecodabilityCalculable<FullDecodabilityResult, AdversaryResultWithSingleOcTable> {
    protected final ApsAdversary apsAdversary;
    protected final ValueMap valueMap;
    protected final PssType pssType;
    protected final OoiUserGroupMappable ooiUserGroupMapper;
    protected final FinalReport lastFinalReport;

    public EndPointApsAdversaryFullDecodabilityCalculator(ApsAdversary apsAdversary, ValueMap valueMap, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, FinalReport lastFinalReport) {
        this.apsAdversary = apsAdversary;
        this.valueMap = valueMap;
        this.pssType = pssType;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
        this.lastFinalReport = lastFinalReport;
    }

    @Override
    public FullDecodabilityResult calculateDecodability(AdversaryResultWithSingleOcTable adversaryResultWithSingleOcTable) throws PssException {
        ApsAdversaryType apsAdversaryType = apsAdversary.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return calculateDecodabilityForSingleUserAdversary((SingleUserApsAdversaryType) apsAdversaryType, adversaryResultWithSingleOcTable.getFinalOcTable());
        }
        throw new PssException(String.format("adversary with user strength %s not matched", apsAdversaryUserStrength.toString()));
    }


    private FullDecodabilityResult calculateDecodabilityForSingleUserAdversary(SingleUserApsAdversaryType apsAdversaryType, OcTable finalOcTable) throws PssException {
        DecodabilityChecker decodabilityChecker = createDecodabilityChecker(finalOcTable);
        Map<LocalOoiCombination, Value> decodedValueMap = decodabilityChecker.getDecodedValueMap();
        return calculateDecodabilityForSingleUserAdversary(apsAdversaryType, decodedValueMap);

    }


    private FullDecodabilityResult calculateDecodabilityForSingleUserAdversary(SingleUserApsAdversaryType apsAdversaryType, Map<LocalOoiCombination, Value> decodedValueMap) throws PssException {
        int userId = apsAdversaryType.getUserId();
        User user = ooiUserGroupMapper.getUser(userId);
        Map<OoiCombination, Value> decodedByAdversary = new HashMap<>();
        int totalDecoded = 0;

        for (LocalOoiCombination localOoiCombination : decodedValueMap.keySet()) {
            OoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, user);
            if (valueMap.getValue(ooiCombination).getIntValue() == decodedValueMap.get(localOoiCombination).getIntValue()) {
                totalDecoded++;
                decodedByAdversary.put(ooiCombination, valueMap.getValue(ooiCombination));
            }
        }
        int totalValues = valueMap.getValues().size();
        double decodability = totalDecoded / (double) totalValues;
        return new FullDecodabilityResult(decodability, decodedByAdversary);
    }

    private DecodabilityChecker createDecodabilityChecker(OcTable ocTable) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new AdversarySingleIdgasDecodabilityChecker((SingleOcTable) ocTable);
            case MULTI:
                return new AdversaryMultiIdgasDecodabilityChecker((MultiOcTable) ocTable);
        }
        throw new PssException(String.format("pss type %s not matched", pssDimensionType.toString()));
    }


    @Override
    public DecodabilityType getDecodabilityType() {
        return DecodabilityType.FULL;
    }
}
