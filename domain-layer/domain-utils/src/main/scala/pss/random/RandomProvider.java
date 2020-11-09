package pss.random;

import pss.config.GivenRandomSource;
import pss.config.RandomSource;
import pss.config.RandomSource.RandomSourceType;
import pss.exception.PssException;
import pss.library.MyRandom;


public class RandomProvider {
    public static MyRandom getMyRandom(RandomSource randomSource, long seed) throws PssException {
        RandomSourceType randomSourceType = randomSource.getRandomSourceType();
        switch (randomSourceType) {
            case INHERITED:
                return new MyRandom(seed);
            case GIVEN:
                GivenRandomSource givenRandomSource = (GivenRandomSource) randomSource;
                return new MyRandom(givenRandomSource.getSeed());
        }
        throw new PssException(String.format("RandomSource not recognized for %s", randomSourceType.toString()));
    }
}
