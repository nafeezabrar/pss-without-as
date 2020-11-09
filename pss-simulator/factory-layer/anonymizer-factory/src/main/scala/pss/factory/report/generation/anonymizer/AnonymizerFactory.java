package pss.factory.report.generation.anonymizer;

import pss.data.pssvariable.group.PssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

import java.util.Set;

public interface AnonymizerFactory {
    AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException, InvalidConfigException, ReaderException;
}
