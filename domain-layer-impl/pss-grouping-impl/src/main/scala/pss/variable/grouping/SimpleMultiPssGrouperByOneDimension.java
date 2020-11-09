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

public class SimpleMultiPssGrouperByOneDimension extends SimpleMultiPssGrouper {

    private final Dimension dividingDimension;

    public SimpleMultiPssGrouperByOneDimension(Map<Dimension, Integer> ooiInEachGroupMap, IdGenerator<Ooi> idGenerator, Dimension dividingDimension) {
        super(ooiInEachGroupMap, idGenerator);
        this.dividingDimension = dividingDimension;
    }


    @Override
    protected int calculateTotalGroups(PssType pssType, MultiPssVariables pssVariables) throws PssException, InvalidConfigException {
        int ooiInEachGroup = ooiInEachGroupMap.get(dividingDimension);
        int totalOois = pssType.getnSet().get(dividingDimension);
        if (ooiInEachGroup > totalOois || totalOois % ooiInEachGroup != 0) {
            throw new InvalidConfigException(String.format("pss grouping config is not compatible, total Ooi = %d, Ooi in Each group = %d", totalOois, ooiInEachGroup));
        }
        return totalOois / ooiInEachGroup;
    }

    protected MultiOoiCollection[] getMultiOoiCollections(PssType pssType, MultiPssVariables pssVariables, int division) {
        Set<Ooi> dividingOoiSet = pssVariables.getOoiCollection().getOoiSetMap().get(dividingDimension);
        Iterator<Ooi> ooiIterator = dividingOoiSet.iterator();
        MultiOoiCollection[] ooiCollections = new MultiOoiCollection[division];
        for (int i = 0; i < division; i++) {
            Map<Dimension, Set<Ooi>> ooiMap = new HashMap<>();
            for (Dimension dimension : pssType.getDimensionSet()) {
                if (dimension != dividingDimension) {
                    ooiMap.put(dimension, pssVariables.getOoiCollection().getOoiSetMap().get(dimension));
                }
            }
            ooiMap.put(dividingDimension, new HashSet<>());
            ooiCollections[i] = new MultiOoiCollection(ooiMap);
        }
        int i = 0;
        while (ooiIterator.hasNext()) {
            ooiCollections[i].getOoiSetMap().get(dividingDimension).add(ooiIterator.next());
            i = (i + 1) % division;
        }
        return ooiCollections;
    }
}
