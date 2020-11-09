package pss.factory.report.generation;

import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.generation.SingleFinalReportGenerator;

public class SingleSimpleFinalReportInheritedGeneratorFactory implements FinalReportInheritedGeneratorFactory<SingleOoiUserGroupMappable> {

    @Override
    public FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        return new SingleFinalReportGenerator();
    }
}
