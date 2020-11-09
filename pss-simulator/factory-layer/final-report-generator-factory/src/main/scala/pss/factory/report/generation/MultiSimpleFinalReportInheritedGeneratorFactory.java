package pss.factory.report.generation;

import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.generation.MultiFinalReportGenerator;

public class MultiSimpleFinalReportInheritedGeneratorFactory implements FinalReportInheritedGeneratorFactory<MultiOoiUserGroupMappable> {

    @Override
    public FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        return new MultiFinalReportGenerator();
    }
}
