package pss.factory.report.generation.deanonymizer;

import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

import java.util.Set;

public interface DeanonymizerSetFactory {
    DeanonymizerGroupMapper createDeanonymizer(Set<PssGroup> pssGroups) throws PssException;
}
