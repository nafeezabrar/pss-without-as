package pss.factory.report.generation.task;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymity.generation.FixedAnonymityGenerator;
import pss.anonymity.generation.UniformRandomSetAnonymityGenerator;
import pss.config.RandomSource;
import pss.config.task.AnonymityGenerationConfig;
import pss.config.task.AnonymityGenerationConfig.AnonymityGenerationMethod;
import pss.config.task.FixedAnonymityGenerationConfig;
import pss.config.task.UniformAnonymityGenerationConfig;
import pss.data.anonymity.UniformAnonymity;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.library.MyRandom;
import pss.random.RandomProvider;

public class AnonymityGeneratorFactory {
    public static AnonymityGenerable generateAnoymityGenerator(AnonymityGenerationConfig anonymityGenerationConfig, long runnerSeed) throws RunnerCreationException, PssException {
        AnonymityGenerationMethod anonymityGenerationMethod = anonymityGenerationConfig.getAnonymityGenerationMethod();
        switch (anonymityGenerationMethod) {
            case FIXED:
                return generateFixedAnonymityGenerator(anonymityGenerationConfig);
            case RANDOM:
                throw new UnsupportedOperationException(String.format("Anonymity generation of type %s not implemented", anonymityGenerationMethod.toString()));
            case FROM_FILE:
                throw new UnsupportedOperationException(String.format("Anonymity generation of type %s not implemented", anonymityGenerationMethod.toString()));
            case FROM_FILE_INHERITED:
                throw new UnsupportedOperationException(String.format("Anonymity generation of type %s not implemented", anonymityGenerationMethod.toString()));
            case UNIFORM_ANONYMITY:
                return generateUniformAnonymityGenerator(anonymityGenerationConfig, runnerSeed);
        }
        throw new PssException(String.format("Anonymity generator not implemented for type %s", anonymityGenerationConfig.toString()));
    }

    private static AnonymityGenerable generateUniformAnonymityGenerator(AnonymityGenerationConfig anonymityGenerationConfig, long runnerSeed) throws PssException {
        UniformAnonymityGenerationConfig uniformAnonymityGenerationConfig = (UniformAnonymityGenerationConfig) anonymityGenerationConfig;
        UniformAnonymity uniformAnonymity = uniformAnonymityGenerationConfig.getAnonymity();
        RandomSource randomSource = uniformAnonymityGenerationConfig.getRandomSource();
        MyRandom myRandom = RandomProvider.getMyRandom(randomSource, runnerSeed);

        return new UniformRandomSetAnonymityGenerator(uniformAnonymity.getAnonymities(), myRandom);
    }


    private static AnonymityGenerable generateFixedAnonymityGenerator(AnonymityGenerationConfig anonymityGenerationConfig) throws PssException {
        FixedAnonymityGenerationConfig fixedAnonymityGenerationConfig = (FixedAnonymityGenerationConfig) anonymityGenerationConfig;
        return new FixedAnonymityGenerator(fixedAnonymityGenerationConfig.getAnonymity());
    }
}
