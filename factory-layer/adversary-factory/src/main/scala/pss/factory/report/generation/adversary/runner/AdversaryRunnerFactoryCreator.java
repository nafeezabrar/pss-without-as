package pss.factory.report.generation.adversary.runner;

import com.pss.adversary.Adversary;
import pss.config.InheritedRandomSource;
import pss.config.adversary.AdversaryConfig;
import pss.config.adversary.ApsAdversaryConfig;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.random.RandomProvider;

public class AdversaryRunnerFactoryCreator {
    public static AdversaryRunnerFactory createAdversaryRunnerFactory(AdversaryConfig adversaryConfig, String resultDirectory, long runnerSeed) throws PssException {
        Adversary.AdversaryType adversaryType = adversaryConfig.getAdversaryType();
        MyRandom myRandom = RandomProvider.getMyRandom(new InheritedRandomSource(), runnerSeed);
        switch (adversaryType) {
            case APS_ADVERSARY:
                return createApsAdversaryRunnerFactory((ApsAdversaryConfig) adversaryConfig, resultDirectory, myRandom);
        }
        throw new UnsupportedOperationException(String.format("adversary type %s not recognized", adversaryType.toString()));
    }

    private static AdversaryRunnerFactory createApsAdversaryRunnerFactory(ApsAdversaryConfig adversaryConfig, String resultDirectory, MyRandom myRandom) throws PssException {

        return new SimpleApsAdversaryRunnerFactory(adversaryConfig, resultDirectory, myRandom);

    }
}
