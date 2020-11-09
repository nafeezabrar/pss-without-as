package pss.factory.report.generation.cycle_runner.anonymization;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.AnonymizableReportGenerable;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;


public interface AnonymizationCycleRunnerFactory {
    AnonymizationCycleRunner generateAnonymizationCycleRunner(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizerSelector anonymizerSelector, UserMapper userMapper, AnonymityGenerable anonymityGenerator, AnonymizableReportGenerable anonymizableReportGenerator) throws PssException;
}
