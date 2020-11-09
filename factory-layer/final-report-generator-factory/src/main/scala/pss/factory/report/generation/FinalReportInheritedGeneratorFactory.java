package pss.factory.report.generation;


import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.FinalReportInheritedGenerable;

public interface FinalReportInheritedGeneratorFactory<TOoiUserGroupMappable extends OoiUserGroupMappable> {
    FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, TOoiUserGroupMappable ooiUserGroupMapper) throws PssException;
}
