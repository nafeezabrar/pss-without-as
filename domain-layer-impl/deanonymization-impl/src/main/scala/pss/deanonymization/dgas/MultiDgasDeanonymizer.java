package pss.deanonymization.dgas;

import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.MultiFinalReport;
import pss.result.deanonymization.MultiDeanonymizationResult;

import java.util.Map;
import java.util.Set;

public class MultiDgasDeanonymizer extends DgasDeanonymizer implements Deanonymizable<MultiFinalReport, MultiDeanonymizationResult, MultiLocalOoiCombination> {


    public MultiDgasDeanonymizer(DecodedMapTable decodedMapTable, DgasDecodingTableUpdatable decodingTableUpdater, Set set) {
        super(decodedMapTable, decodingTableUpdater, set);
    }

    @Override
    public MultiDeanonymizationResult deanonymize(MultiFinalReport finalReport) {
        throw new UnsupportedOperationException("not impl");
    }

    @Override
    public Map<MultiLocalOoiCombination, Value> getDecodedValueMap() {
        throw new UnsupportedOperationException("not impl");
    }
}
