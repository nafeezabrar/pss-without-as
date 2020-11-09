package pss.deanonymization.idgas;

import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.deanonymization.MultiDeanonymizable;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.report.finalreport.MultiFinalReport;
import pss.result.deanonymization.MultiDeanonymizationResult;

import java.util.HashMap;
import java.util.Map;

public class MultiIdgasDeanonymizer extends IdgasDeanonymizer<MultiOcTable> implements MultiDeanonymizable {
    private final MultiDecodabilityChecker decodabilityChecker;
    private final MultiIdgasDecodingTableUpdater ocTableUpdater;

    public MultiIdgasDeanonymizer(MultiOcTable ocTable, MultiDecodabilityChecker decodabilityChecker, MultiIdgasDecodingTableUpdater ocTableUpdater) {
        super(ocTable);
        this.decodabilityChecker = decodabilityChecker;
        this.ocTableUpdater = ocTableUpdater;
    }


    @Override
    public MultiDeanonymizationResult deanonymize(MultiFinalReport finalReport) {
        boolean isDecoded = deanonymizeReport(finalReport);
        int totalDecoded = countTotalDecoded();
        return new MultiDeanonymizationResult(finalReport, ocTable.cloneOcTable(), totalDecoded, isDecoded);
    }

    @Override
    public Map<MultiLocalOoiCombination, Value> getDecodedValueMap() {
        Map<MultiLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        for (Value value : ocRowMap.keySet()) {
            if (decodabilityChecker.isDecoded(value)) {
                MultiLocalOoiCombination decodedLocalOoiCombination = decodabilityChecker.getDecodedLocalOoiCombination(value);
                decodedValueMap.put(decodedLocalOoiCombination, value);
            }
        }
        return decodedValueMap;
    }

    private int countTotalDecoded() {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        int totalDecoded = 0;
        for (Value value : ocRowMap.keySet()) {
            if (decodabilityChecker.isDecoded(value))
                totalDecoded++;
        }
        return totalDecoded;
    }

    private boolean deanonymizeReport(MultiFinalReport finalReport) {
        decodeReport(finalReport);
        Value value = finalReport.getValue();
        return decodabilityChecker.isDecoded(value);
    }

    private void decodeReport(MultiFinalReport finalReport) {
        ocTableUpdater.update(finalReport);
    }

    public MultiOcTable getOcTable() {
        return ocTable;
    }
}
