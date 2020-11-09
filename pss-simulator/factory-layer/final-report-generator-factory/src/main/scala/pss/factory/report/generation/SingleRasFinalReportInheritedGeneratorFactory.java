package pss.factory.report.generation;

import pss.config.InheritedRandomSource;
import pss.config.RandomSource;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.random.RandomProvider;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.generation.SingleFinalRasReportGenerator;

public class SingleRasFinalReportInheritedGeneratorFactory implements FinalReportInheritedGeneratorFactory<SingleOoiUserGroupMappable> {
    @Override
    public FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        RandomSource randomSource = new InheritedRandomSource();
        MyRandom myRandom = RandomProvider.getMyRandom(randomSource, runnerSeed);
        return new SingleFinalRasReportGenerator(myRandom, pssVariables, ooiUserGroupMapper);
    }
}
