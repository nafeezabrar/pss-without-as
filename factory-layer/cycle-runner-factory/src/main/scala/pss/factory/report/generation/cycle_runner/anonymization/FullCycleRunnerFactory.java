package pss.factory.report.generation.cycle_runner.anonymization;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.exception.PssException;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactory;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.AnonymizableReportGenerable;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.util.Set;


public interface FullCycleRunnerFactory {
    FullCycleRunner generateCycleRunner(PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, AnonymizerSelector anonymizerSelector, UserMapper userMapper, AnonymityGenerable anonymityGenerator, AnonymizableReportGenerable anonymizableReportGenerator, FinalReportInheritedGeneratorFactory finalReportGenerable, DeanonymizerGroupMapper deanonymizerGroupMapper, DeanonymizerSelector deanonymizerSelector, Set<PssGroup> pssGroups) throws PssException;
}
