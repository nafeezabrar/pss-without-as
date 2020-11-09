package pss.deanonymization.dgas;

import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.valuemap.Value;
import pss.report.finalreport.FinalReport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DgasDeanonymizer<TLocalOoiCombination extends LocalOoiCombination, TFinalReport extends FinalReport> {
    protected final DecodedMapTable<TLocalOoiCombination> decodedMapTable;
    protected final DgasDecodingTableUpdatable<TFinalReport> decodingTableUpdater;
    protected final Set<ValueKey> valueKeys;
    protected Map<Value, ValueKey> valueToValueKeyMap;
    protected Set<Value> assignedValues;

    public DgasDeanonymizer(DecodedMapTable<TLocalOoiCombination> decodedMapTable, DgasDecodingTableUpdatable<TFinalReport> decodingTableUpdater, Set<ValueKey> valueKeys) {
        this.decodedMapTable = decodedMapTable;
        this.decodingTableUpdater = decodingTableUpdater;
        this.valueToValueKeyMap = new HashMap<>();
        this.valueKeys = valueKeys;
        this.assignedValues = new HashSet<>();
    }
}
