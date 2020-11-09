package pss.factory.report.generation;

import pss.config.InheritedRandomSource;
import pss.config.RandomSource;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.random.RandomProvider;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.generation.MultiFinalRasReportGenerator;

public class MultiRasFinalReportInheritedGeneratorFactory implements FinalReportInheritedGeneratorFactory<MultiOoiUserGroupMappable> {

    @Override
    public FinalReportInheritedGenerable generateFinalReportGenerator(long runnerSeed, PssVariables pssVariables, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        RandomSource randomSource = new InheritedRandomSource();
        MyRandom myRandom = RandomProvider.getMyRandom(randomSource, runnerSeed);
        return new MultiFinalRasReportGenerator(myRandom, pssVariables, ooiUserGroupMapper);
    }
}
