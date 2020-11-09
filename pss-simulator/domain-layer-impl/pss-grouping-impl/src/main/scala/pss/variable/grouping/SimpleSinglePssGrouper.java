package pss.variable.grouping;


import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.library.IdGenerator;

import java.util.*;

public class SimpleSinglePssGrouper implements PssGroupable<SinglePssGroup, SinglePssVariables> {

    private final IdGenerator<Ooi> idGenerator;
    private final Map<Dimension, Integer> ooiInEachGroupMap;

    public SimpleSinglePssGrouper(IdGenerator<Ooi> idGenerator, Map<Dimension, Integer> ooiInEachGroupMap) {
        this.idGenerator = idGenerator;
        this.ooiInEachGroupMap = ooiInEachGroupMap;
    }

    public Set<SinglePssGroup> generateGroups(PssType pssType, SinglePssVariables pssVariables) throws PssException, InvalidConfigException {
        int totalGroups = calculateTotalGroup(pssType, pssVariables);

        SingleOoiCollection[] ooiCollections = getSingleOoiCollections(pssVariables, totalGroups);

        Set<SinglePssGroup> pssVariablesDivisions = new HashSet<>();
        for (int i = 0; i < totalGroups; i++) {
            pssVariablesDivisions.add(generatePssVariablesDivision(ooiCollections[i], i + 1));
        }
        return pssVariablesDivisions;
    }

    private int calculateTotalGroup(PssType pssType, SinglePssVariables pssVariables) throws PssException, InvalidConfigException {
        Dimension dimension = pssType.getDimension();
        if (!ooiInEachGroupMap.containsKey(dimension)) {
            throw new PssException(String.format("dimension %s not matched with provided grouping information", dimension.getName()));
        }
        int totalOois = pssVariables.getOoiCollection().getOoiSet().size();
        int ooiInEachGroup = ooiInEachGroupMap.get(dimension);
        if (totalOois < ooiInEachGroup || totalOois % ooiInEachGroup != 0) {
            throw new InvalidConfigException(String.format("pss grouping config is not compatible, total Ooi = %d, Ooi in Each group = %d", totalOois, ooiInEachGroup));
        }
        return totalOois / ooiInEachGroup;
    }

    private SinglePssGroup generatePssVariablesDivision(SingleOoiCollection ooiCollection, int groupId) {
        Map<Ooi, Integer> ooiToIdMap = idGenerator.generateUniqueIdMap(ooiCollection.getOoiSet());
        Map<Integer, Ooi> idToOoiMap = generate(ooiToIdMap);
        Set<Integer> localOoiSet = new HashSet<>();
        localOoiSet.addAll(idToOoiMap.keySet());
        SingleLocalOoiCollection localOoiCollection = new SingleLocalOoiCollection(localOoiSet);
        SingleLocalOoiMapper localOoiMapper = new SingleLocalOoiMapper(ooiToIdMap, idToOoiMap);
        return new SinglePssGroup(ooiCollection, groupId, localOoiCollection, localOoiMapper);
    }

    private Map<Integer, Ooi> generate(Map<Ooi, Integer> ooiToIdMap) {
        Map<Integer, Ooi> idToOoiMap = new HashMap<>();
        for (Ooi ooi : ooiToIdMap.keySet()) {
            idToOoiMap.put(ooiToIdMap.get(ooi), ooi);
        }
        return idToOoiMap;
    }

    private SingleOoiCollection[] getSingleOoiCollections(SinglePssVariables pssVariables, int division) {
        SingleOoiCollection[] ooiCollections = getEmptyOoiCollections(division);
        Set<Ooi> ooiSet = pssVariables.getOoiCollection().getOoiSet();
        Iterator<Ooi> ooiIterator = ooiSet.iterator();
        int i = 0;
        while (ooiIterator.hasNext()) {
            ooiCollections[i].getOoiSet().add(ooiIterator.next());
            i = (i + 1) % division;
        }
        return ooiCollections;
    }

    private SingleOoiCollection[] getEmptyOoiCollections(int totalDivisions) {
        SingleOoiCollection[] ooiCollections = new SingleOoiCollection[totalDivisions];
        for (int i = 0; i < totalDivisions; i++) {
            ooiCollections[i] = new SingleOoiCollection(new HashSet<>());
        }
        return ooiCollections;
    }
}
