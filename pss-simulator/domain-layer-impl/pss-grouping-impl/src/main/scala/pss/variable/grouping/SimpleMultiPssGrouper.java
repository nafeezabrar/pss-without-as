package pss.variable.grouping;


import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.library.IdGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SimpleMultiPssGrouper extends MultiPssGrouper {
    private final IdGenerator<Ooi> idGenerator;

    protected SimpleMultiPssGrouper(Map<Dimension, Integer> ooiInEachGroupMap, IdGenerator<Ooi> idGenerator) {
        super(ooiInEachGroupMap);
        this.idGenerator = idGenerator;
    }


    protected MultiPssGroup generatePssGroups(MultiOoiCollection ooiCollection, int groupId, PssType pssType) {
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMapping = new HashMap<>();
        Map<Dimension, Map<Integer, Ooi>> idToOoiMapping = new HashMap<>();
        Map<Dimension, Set<Integer>> localOoiIdMap = new HashMap<>();

        for (Dimension dimension : pssType.getDimensionSet()) {
            Map<Ooi, Integer> idMap = idGenerator.generateUniqueIdMap(ooiCollection.getOoiSetMap().get(dimension));
            ooiToIdMapping.put(dimension, idMap);
            Map<Integer, Ooi> idToOoiMap = new HashMap<>();
            for (Ooi ooi : idMap.keySet()) {
                idToOoiMap.put(idMap.get(ooi), ooi);
            }
            idToOoiMapping.put(dimension, idToOoiMap);
            Set<Integer> localOoiSet = new HashSet<>();
            localOoiSet.addAll(idMap.values());
            localOoiIdMap.put(dimension, localOoiSet);
        }
        MultiLocalOoiMapper localOoiMapper = new MultiLocalOoiMapper(ooiToIdMapping, idToOoiMapping);
        MultiLocalOoiCollection localOoiCollection = new MultiLocalOoiCollection(localOoiIdMap);
        return new MultiPssGroup(ooiCollection, groupId, localOoiCollection, localOoiMapper);
    }
}
