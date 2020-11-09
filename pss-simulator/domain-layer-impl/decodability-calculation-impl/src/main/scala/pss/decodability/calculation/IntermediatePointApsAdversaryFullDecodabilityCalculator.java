package pss.decodability.calculation;

import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.SingleUserApsAdversaryType;
import pss.config.printing.DefaultPrintingFileName;
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
import pss.decodability.calculation.aps.AdversaryIntermediatePointsDecodabilityCalculable;
import pss.decodability.checking.AdversaryMultiIdgasDecodabilityChecker;
import pss.decodability.checking.AdversarySingleIdgasDecodabilityChecker;
import pss.decodability.checking.DecodabilityChecker;
import pss.exception.PssException;
import pss.library.SerializableObjectReader;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediatePointApsAdversaryFullDecodabilityCalculator implements AdversaryIntermediatePointsDecodabilityCalculable<FullDecodabilityResult, AdversaryResultWithSingleOcTable> {
    protected final PssType pssType;
    protected final ApsAdversary apsAdversary;
    protected final ValueMap valueMap;
    protected final OoiUserGroupMappable ooiUserGroupMapper;
    protected final String resultDirectory;
    protected final List<FinalReport> finalReports;
    protected double previousDecodability;
    protected Map<OoiCombination, Value> previousDecodedMap;
    protected Map<FinalReport, OcTable> ocTableMap;

    public IntermediatePointApsAdversaryFullDecodabilityCalculator(PssType pssType, ApsAdversary apsAdversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, String resultDirectory, List<FinalReport> finalReports) {
        this.pssType = pssType;
        this.apsAdversary = apsAdversary;
        this.valueMap = valueMap;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
        this.resultDirectory = resultDirectory;
        this.finalReports = finalReports;
        init();
    }

    private void init() {
        previousDecodedMap = new HashMap<>();
        previousDecodability = 0.0;
    }

    @Override
    public FullDecodabilityResult calculateDecodability(AdversaryResultWithSingleOcTable adversaryResult, FinalReport finalReport) throws PssException, IOException, ClassNotFoundException {

        updateOcTableMapIfNeeded();
        if (!ocTableMap.containsKey(finalReport)) {
            return new FullDecodabilityResult(previousDecodability, previousDecodedMap);
        }
        OcTable ocTable = ocTableMap.get(finalReport);
        DecodabilityChecker decodabilityChecker = createDecodabilityChecker(ocTable);
        Map<LocalOoiCombination, Value> decodedValueMap = decodabilityChecker.getDecodedValueMap();
        ApsAdversaryType apsAdversaryType = apsAdversary.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return calculateDecodabilityForSingleUserAdversary((SingleUserApsAdversaryType) apsAdversaryType, decodedValueMap);
        }
        throw new PssException(String.format("adversary with user strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    private void updateOcTableMapIfNeeded() throws IOException, ClassNotFoundException {
        if (ocTableMap == null) {
            String ocTableFileName = DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory);
            SerializableObjectReader<MultiOcTable> objectReader = new SerializableObjectReader<>(ocTableFileName);
            List<MultiOcTable> multiOcTables = objectReader.getObjects(finalReports.size());
            ocTableMap = new HashMap<>();
            for (int i = 0; i < finalReports.size(); i++) {
                ocTableMap.put(finalReports.get(i), multiOcTables.get(i));
            }
        }
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
        previousDecodability = decodability;
        previousDecodedMap.clear();
        previousDecodedMap.putAll(decodedByAdversary);
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
