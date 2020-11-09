package pss.deanonymization.idgas;

import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.deanonymization.SingleDeanonymizable;
import pss.decodability.checking.SingleDecodabilityChecker;
import pss.report.finalreport.SingleFinalReport;
import pss.result.deanonymization.SingleDeanonymizationResult;

import java.util.HashMap;
import java.util.Map;

public class SingleIdgasDeanonymizer extends IdgasDeanonymizer<SingleOcTable> implements SingleDeanonymizable {
    private final SingleDecodabilityChecker decodabilityChecker;
    private final IdgasDecodingTableUpdater idgasDecodingTableUpdater;
    private Map<SingleLocalOoiCombination, Value> decodedValueMap;

    public SingleIdgasDeanonymizer(SingleOcTable ocTable, SingleDecodabilityChecker decodabilityChecker, IdgasDecodingTableUpdater idgasDecodingTableUpdater) {
        super(ocTable);
        this.decodabilityChecker = decodabilityChecker;
        this.idgasDecodingTableUpdater = idgasDecodingTableUpdater;
        this.decodedValueMap = new HashMap<>();
    }


    @Override
    public SingleDeanonymizationResult deanonymize(SingleFinalReport finalReport) {
        boolean isDecoded = deanonymizeReport(finalReport);
        int totalDecoded = updateDecodingStatus();
        return new SingleDeanonymizationResult(finalReport, ocTable.cloneOcTable(), totalDecoded, isDecoded);
    }

    @Override
    public Map<SingleLocalOoiCombination, Value> getDecodedValueMap() {
        return decodedValueMap;
    }

    private int updateDecodingStatus() {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        int previouslyDecoded = decodedValueMap.size();
        int updatedDecodeCount = countTotalDecoded(ocRowMap);
        while (previouslyDecoded != updatedDecodeCount) {
            previouslyDecoded = updatedDecodeCount;
            updatedDecodeCount = countTotalDecoded(ocRowMap);
        }
        return updatedDecodeCount;
    }

    private int countTotalDecoded(Map<Value, SingleOcRow> ocRowMap) {
        int totalDecoded = 0;
        for (Value value : ocRowMap.keySet()) {
            if (decodabilityChecker.isDecoded(value)) {
                totalDecoded++;
                SingleLocalOoiCombination decodedLocalOoiCombination = decodabilityChecker.getDecodedLocalOoiCombination(value);
                decodedValueMap.put(decodedLocalOoiCombination, value);
            }
        }
        return totalDecoded;
    }

    private boolean deanonymizeReport(SingleFinalReport finalReport) {
        decodeReport(finalReport);
        Value value = finalReport.getValue();
        return decodabilityChecker.isDecoded(value);
    }

    private void decodeReport(SingleFinalReport finalReport) {
        idgasDecodingTableUpdater.update(finalReport);
    }

    public SingleOcTable getOcTable() {
        return ocTable;
    }

    public SingleDecodabilityChecker getDecodabilityChecker() {
        return decodabilityChecker;
    }
}
