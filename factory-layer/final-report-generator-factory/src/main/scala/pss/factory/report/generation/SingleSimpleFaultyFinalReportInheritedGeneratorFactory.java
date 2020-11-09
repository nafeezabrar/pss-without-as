package pss.factory.report.generation;

import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.generation.SingleFaultyFinalReportGenerator;

public class SingleSimpleFaultyFinalReportInheritedGeneratorFactory implements FinalReportInheritedGeneratorFactory<SingleOoiUserGroupMappable> {
    protected final double probability;

    public SingleSimpleFaultyFinalReportInheritedGeneratorFactory(double probability) {
        this.probability = probability;
    }

    @Override
    public FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        return new SingleFaultyFinalReportGenerator(probability);
    }
}
