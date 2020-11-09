package pss.variable.grouping;

import pss.data.dimension.Dimension;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class MultiPssGrouper implements PssGroupable<MultiPssGroup, MultiPssVariables> {
    protected final Map<Dimension, Integer> ooiInEachGroupMap;

    protected MultiPssGrouper(Map<Dimension, Integer> ooiInEachGroupMap) {
        this.ooiInEachGroupMap = ooiInEachGroupMap;
    }

    public Set<MultiPssGroup> generateGroups(PssType pssType, MultiPssVariables pssVariables) throws PssException, InvalidConfigException {
        int division = calculateTotalGroups(pssType, pssVariables);
        MultiOoiCollection[] ooiCollections = getMultiOoiCollections(pssType, pssVariables, division);

        Set<MultiPssGroup> pssVariablesDivisions = new HashSet<>();
        for (int i = 0; i < division; i++) {
            pssVariablesDivisions.add(generatePssGroups(ooiCollections[i], i + 1, pssType));
        }
        return pssVariablesDivisions;
    }

    protected abstract int calculateTotalGroups(PssType pssType, MultiPssVariables pssVariables) throws PssException, InvalidConfigException;

    protected abstract MultiPssGroup generatePssGroups(MultiOoiCollection ooiCollection, int divisionId, PssType pssType);


    protected abstract MultiOoiCollection[] getMultiOoiCollections(PssType pssType, MultiPssVariables pssVariables, int division) throws PssException, InvalidConfigException;

}
