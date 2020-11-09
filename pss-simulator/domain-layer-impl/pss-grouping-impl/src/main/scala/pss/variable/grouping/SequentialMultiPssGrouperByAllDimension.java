package pss.variable.grouping;


import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.library.IdGenerator;

import java.util.*;

public class SequentialMultiPssGrouperByAllDimension extends SimpleMultiPssGrouperByAllDimension {

    public SequentialMultiPssGrouperByAllDimension(Map<Dimension, Integer> ooiInEachGroupMap, IdGenerator<Ooi> idGenerator) {
        super(ooiInEachGroupMap, idGenerator);
    }

    @Override
    protected Map<Dimension, List<Set<Ooi>>> generateDividingOoiSetMap(PssType pssType, MultiPssVariables pssVariables) {
        Map<Dimension, Set<Ooi>> ooiSetMap = pssVariables.getOoiCollection().getOoiSetMap();
        Set<Dimension> dimensions = pssType.getDimensionSet();
        Map<Dimension, List<Set<Ooi>>> dividingOoiSetMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            Set<Ooi> allOoiSet = ooiSetMap.get(dimension);
            List<Ooi> orderedOois = new ArrayList<>();
            orderedOois.addAll(allOoiSet);
            orderedOois.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
            List<Set<Ooi>> ooiSets = getOoiSetsForOneDimension(dimension, orderedOois);
            dividingOoiSetMap.put(dimension, ooiSets);
        }

        return dividingOoiSetMap;
    }


    private List<Set<Ooi>> getOoiSetsForOneDimension(Dimension dimension, List<Ooi> allOoiSet) {
        int totalGroup = getTotalGroup(dimension, allOoiSet);
        List<Set<Ooi>> ooiSets = getListOfEmptySets(totalGroup);
        int ooiInEachGroup = allOoiSet.size() / totalGroup;
        Iterator<Ooi> ooiIterator = allOoiSet.iterator();
        for (int i = 0; i < totalGroup; i++) {
            for (int j = 0; j < ooiInEachGroup; j++) {
                ooiSets.get(i).add(ooiIterator.next());
            }
        }
        return ooiSets;
    }

    private List<Set<Ooi>> getListOfEmptySets(int totalGroup) {
        List<Set<Ooi>> ooiSets = new ArrayList<>();
        for (int i = 0; i < totalGroup; i++) {
            ooiSets.add(new HashSet<>());
        }
        return ooiSets;
    }

    private int getTotalGroup(Dimension dimension, List<Ooi> allOoiSet) {
        int totalOoi = allOoiSet.size();
        int groupSize = ooiInEachGroupMap.get(dimension);
        return totalOoi / groupSize;
    }

    @Override
    protected MultiPssGroup generatePssGroups(MultiOoiCollection ooiCollection, int groupId, PssType pssType) {
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMapping = new HashMap<>();
        Map<Dimension, Map<Integer, Ooi>> idToOoiMapping = new HashMap<>();
        Map<Dimension, Set<Integer>> localOoiIdMap = new HashMap<>();
        Map<Dimension, Set<Ooi>> ooiSetMap = ooiCollection.getOoiSetMap();
        List<Dimension> orderedDimensions = new ArrayList<>();
        orderedDimensions.addAll(pssType.getDimensionSet());
        orderedDimensions.sort((d1, d2) -> Integer.compare(d1.getId(), d2.getId()));
        for (Dimension dimension : orderedDimensions) {
            Map<Ooi, Integer> idMap = new HashMap<>();
            Set<Ooi> oois = ooiSetMap.get(dimension);
            List<Ooi> orderedOois = new ArrayList<>();
            orderedOois.addAll(oois);
            orderedOois.sort((ooi1, ooi2) -> Integer.compare(ooi1.getId(), ooi2.getId()));

            for (int i = 0; i < orderedOois.size(); i++) {
                idMap.put(orderedOois.get(i), i + 1);
            }
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
