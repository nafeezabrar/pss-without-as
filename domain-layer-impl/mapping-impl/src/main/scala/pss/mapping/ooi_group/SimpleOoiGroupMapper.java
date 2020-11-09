package pss.mapping.ooi_group;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.OoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.domain.utils.ooi.MultiOoiCombinationMaker;
import pss.domain.utils.ooi.OoiCombinationMaker;
import pss.domain.utils.ooi.SingleOoiCombinationMaker;
import pss.exception.PssException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleOoiGroupMapper implements OoiGroupMapperCreator {

    @Override
    public OoiGroupMapper generateOoiGroupMapper(PssType pssType, Set<PssGroup> pssGroups, PssVariables pssVariables) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return generateSingleOoiGroupMapper(pssGroups);
            case MULTI:
                return generateMultiOoiGroupMapper(pssGroups);
        }
        throw new PssException(String.format("OoiGroupmapper cannot be generated using pssType %s", pssDimensionType.toString()));

    }

    private OoiGroupMapper generateSingleOoiGroupMapper(Set<PssGroup> pssGroups) {
        Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap = new HashMap<>();
        Map<PssGroup, Set<OoiCombination>> ooiCombinationMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleOoiCollection ooiCollection = (SingleOoiCollection) pssGroup.getOoiCollection();
            OoiCombinationMaker localOoiCombinationMaker = new SingleOoiCombinationMaker(ooiCollection);
            Set<OoiCombination> ooiCombinations = localOoiCombinationMaker.generateOoiCombinations();
            ooiCombinationMap.put(pssGroup, ooiCombinations);
            for (OoiCombination ooiCombination : ooiCombinations) {
                ooiCombinationToPssGroupMap.put(ooiCombination, pssGroup);
            }
        }
        return new OoiGroupMapper(ooiCombinationToPssGroupMap, ooiCombinationMap);
    }

    private OoiGroupMapper generateMultiOoiGroupMapper(Set<PssGroup> pssGroups) {
        Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap = new HashMap<>();
        Map<PssGroup, Set<OoiCombination>> ooiCombinationMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            MultiOoiCollection ooiCollection = (MultiOoiCollection) pssGroup.getOoiCollection();
            OoiCombinationMaker localOoiCombinationMaker = new MultiOoiCombinationMaker(ooiCollection);
            Set<OoiCombination> ooiCombinations = localOoiCombinationMaker.generateOoiCombinations();
            ooiCombinationMap.put(pssGroup, ooiCombinations);
            for (OoiCombination ooiCombination : ooiCombinations) {
                ooiCombinationToPssGroupMap.put(ooiCombination, pssGroup);
            }
        }
        return new OoiGroupMapper(ooiCombinationToPssGroupMap, ooiCombinationMap);
    }
}
