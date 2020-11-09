package pss.variable.grouping;


import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.MultiPssVariables;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.library.IdGenerator;

import java.util.*;

public class SimpleMultiPssGrouperByAllDimension extends SimpleMultiPssGrouper {
    protected MultiOoiCollection[] multiOoiCollections;
    private int index = 0;

    public SimpleMultiPssGrouperByAllDimension(Map<Dimension, Integer> ooiInEachGroupMap, IdGenerator<Ooi> idGenerator) {
        super(ooiInEachGroupMap, idGenerator);
    }


    @Override
    protected int calculateTotalGroups(PssType pssType, MultiPssVariables pssVariables) throws PssException, InvalidConfigException {
        int totalGroups = 1;
        for (Dimension dimension : ooiInEachGroupMap.keySet()) {
            int groups = calculateGroupsInDimension(pssVariables, dimension);
            totalGroups *= groups;
        }
        return totalGroups;
    }

    private int calculateGroupsInDimension(MultiPssVariables pssVariables, Dimension dimension) throws InvalidConfigException {
        int ooiInEachGroup = ooiInEachGroupMap.get(dimension);
        int totalOois = pssVariables.getOoiCollection().getOoiSetMap().get(dimension).size();
        if (ooiInEachGroup > totalOois || totalOois % ooiInEachGroup != 0) {
            throw new InvalidConfigException(String.format("pss grouping config is not compatible, total Ooi = %d, Ooi in Each group = %d", totalOois, ooiInEachGroup));
        }
        return totalOois / ooiInEachGroup;
    }

    @Override
    protected MultiOoiCollection[] getMultiOoiCollections(PssType pssType, MultiPssVariables pssVariables, int division) throws PssException, InvalidConfigException {
        Map<Dimension, List<Set<Ooi>>> dividingOoiSetMap = generateDividingOoiSetMap(pssType, pssVariables);

        Set<Dimension> dimensionSet = new HashSet<>();
        dimensionSet.addAll(pssVariables.getOoiCollection().getOoiSetMap().keySet());
        index = 0;
        int totalGroups = calculateTotalGroups(pssType, pssVariables);
        multiOoiCollections = new MultiOoiCollection[totalGroups];
        List<Dimension> dimensions = new ArrayList<>();
        dimensions.addAll(pssType.getDimensionSet());
        dimensions.sort((d1, d2) -> Integer.compare(d1.getId(), d2.getId()));
        generateMultiOoiCollections(dividingOoiSetMap, dimensions, null);
        return multiOoiCollections;
    }

    private void generateMultiOoiCollections(Map<Dimension, List<Set<Ooi>>> dividingOoiSetMap, List<Dimension> remainingDimensions, Map<Dimension, Set<Ooi>> currentMultiOoiCollectionMap) {
        if (remainingDimensions.isEmpty()) {
            MultiOoiCollection multiOoiCollection = new MultiOoiCollection(currentMultiOoiCollectionMap);
            multiOoiCollections[index++] = multiOoiCollection;
            return;
        }

        Dimension dimension = remainingDimensions.iterator().next();
        List<Dimension> newRemainingDimensions = new ArrayList<>();
        newRemainingDimensions.addAll(remainingDimensions);
        newRemainingDimensions.remove(dimension);
        List<Set<Ooi>> listOfOoiSet = dividingOoiSetMap.get(dimension);
        for (Set<Ooi> ooiSet : listOfOoiSet) {
            //clone a refactoringpkg collection Map
            Map<Dimension, Set<Ooi>> newOoiCollectionMap = new HashMap<>();
            if (currentMultiOoiCollectionMap != null)
                newOoiCollectionMap.putAll(currentMultiOoiCollectionMap);
            newOoiCollectionMap.put(dimension, ooiSet);
            generateMultiOoiCollections(dividingOoiSetMap, newRemainingDimensions, newOoiCollectionMap);
        }
    }

    protected Map<Dimension, List<Set<Ooi>>> generateDividingOoiSetMap(PssType pssType, MultiPssVariables pssVariables) {
        Map<Dimension, List<Set<Ooi>>> dividingOoiSetMap = new HashMap<>();
        Set<Dimension> dimensions = pssType.getDimensionSet();
        for (Dimension dimension : dimensions) {
            Set<Ooi> allOoiSet = pssVariables.getOoiCollection().getOoiSetMap().get(dimension);
            List<Set<Ooi>> ooiSets = getOoiSetsForOneDimension(dimension, allOoiSet);
            dividingOoiSetMap.put(dimension, ooiSets);
        }
        return dividingOoiSetMap;
    }

    private List<Set<Ooi>> getOoiSetsForOneDimension(Dimension dimension, Set<Ooi> allOoiSet) {
        int totalGroup = getTotalGroup(dimension, allOoiSet);
        List<Set<Ooi>> ooiSets = getListOfEmptySets(totalGroup);

        int i = 0;
        for (Ooi ooi : allOoiSet) {
            ooiSets.get(i).add(ooi);
            ++i;
            i = i % totalGroup;
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

    private int getTotalGroup(Dimension dimension, Set<Ooi> allOoiSet) {
        int totalOoi = allOoiSet.size();
        int groupSize = ooiInEachGroupMap.get(dimension);
        return totalOoi / groupSize;
    }
}
