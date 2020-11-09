package pss.deanonymization.dgas.decodingMapTable.updating;

import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.NotAllowedMappings;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.deanonymization.dgas.DgasDecodingTableUpdatable;
import pss.report.finalreport.SingleFinalReport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleDgasDecodingTableUpdater extends DgasDecodingTableUpdater<SingleLocalOoiCombination, SingleLocalOoiCollection> implements DgasDecodingTableUpdatable<SingleFinalReport> {

    public SingleDgasDecodingTableUpdater(DecodedMapTable<SingleLocalOoiCombination> decodedMapTable, SingleLocalOoiCollection localOoiCollection) {
        super(decodedMapTable, localOoiCollection);
    }

    @Override
    public void update(SingleFinalReport finalReport, ValueKey reportedValueKey) {
        SingleLocalOoiCollection ooiCollection = finalReport.getReportData().getLocalOoiCollection();
        Set<DecodedState> removableDecodingStates = getRemovableDecodingStates(ooiCollection, reportedValueKey);
        decodedMapTable.removeDecodedStates(removableDecodingStates);
    }

    private Set<DecodedState> getRemovableDecodingStates(SingleLocalOoiCollection anonymization, ValueKey valueKey) {
        NotAllowedMappings notAllowedMappings = getOppositeConditionFromAnonymization(anonymization, valueKey);
        Set<DecodedState> alignedDecodedStates = decodedMapTable.getAlignedDecodedStates(notAllowedMappings);
        return alignedDecodedStates;
    }

    private NotAllowedMappings getOppositeConditionFromAnonymization(SingleLocalOoiCollection anonymization, ValueKey valueKey) {
        Map<ValueKey, SingleLocalOoiCombination> constraintMap = new HashMap<>();
        Set<Integer> excludedLocalOois = getExcludedOoiSet(anonymization);
        for (Integer excludedLocalOoi : excludedLocalOois) {
            constraintMap.put(valueKey, new SingleLocalOoiCombination(excludedLocalOoi));
        }
        return new NotAllowedMappings(constraintMap);
    }

    private Set<Integer> getExcludedOoiSet(SingleLocalOoiCollection anonymization) {
        Set<Integer> excludedLocalOois = new HashSet<>();
        Set<Integer> anonymizedLocalOoiSet = anonymization.getLocalOoiSet();
        for (Integer localOoi : localOoiCollection.getLocalOoiSet()) {
            if (!anonymizedLocalOoiSet.contains(localOoi)) {
                excludedLocalOois.add(localOoi);
            }
        }
        return excludedLocalOois;
    }
}
