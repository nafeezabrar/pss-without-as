package pss.factory.report.generation.task;

import com.github.javafaker.Faker;
import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.config.task.UserGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.library.MyRandom;
import pss.random.RandomProvider;
import pss.result.presentable.printing.HtmlUserPrinter;
import pss.result.presentable.printing.UserPrintable;
import pss.saving.user.UserSavable;
import pss.user.generation.RandomUserGenerator;
import pss.user.generation.UserGenerable;
import pss.user.generation.UserGeneratorWithPrinter;
import pss.user.generation.UserGeneratorWithSaver;

import static pss.config.task.UserGenerationConfig.UserGenerationMethod;

public class UserGenerableFactory {
    public static UserGenerable generateUserGenerator(UserGenerationConfig userGenerationConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, PssException, InvalidConfigException {
        UserGenerationMethod userGenerationMethod = userGenerationConfig.getUserGenerationMethod();
        switch (userGenerationMethod) {
            case FROM_FILE:
                throw new UnsupportedOperationException("User generation from file not implemented");
            case RANDOM:
                return generateRandomUserGenerator(userGenerationConfig, runnerSeed, resultDirectory);
        }
        throw new PssException(String.format("User Generation factory called for invalid method %s", userGenerationMethod.toString()));
    }

    private static UserGenerable generateRandomUserGenerator(UserGenerationConfig userGenerationConfig, long runnerSeed, String resultDirectory) throws InvalidConfigException, PssException {
        int totalUsers = userGenerationConfig.getTotalUsers();
        MyRandom myRandom = RandomProvider.getMyRandom(userGenerationConfig.getRandomSource(), runnerSeed);
        UserGenerable userGenerator = new RandomUserGenerator(new Faker(myRandom), totalUsers);
        SaveConfig saveConfig = userGenerationConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            userGenerator = withSaver(userGenerator, generateUserSaver((ShouldSaveConfig) saveConfig));
        }
        PrintConfig printConfig = userGenerationConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            userGenerator = withPrinter(userGenerator, generateUserPrinter((ShouldPrintConfig) printConfig, resultDirectory));
        }
        return userGenerator;
    }

    private static UserGenerable withPrinter(UserGenerable userGenerator, UserPrintable userPrinter) {
        return new UserGeneratorWithPrinter(userGenerator, userPrinter);
    }

    private static UserGenerable withSaver(UserGenerable userGenerator, UserSavable userSaver) {
        return new UserGeneratorWithSaver(userGenerator, userSaver);
    }


    private static UserSavable generateUserSaver(ShouldSaveConfig saveConfig) throws InvalidConfigException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(String.format("User saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("User saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(String.format("User saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("User saver not implemented for %s", savingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("User saving target %s not recognized", savingTargetType.toString()));
    }

    private static UserPrintable generateUserPrinter(ShouldPrintConfig printConfig, String resultDirectory) throws InvalidConfigException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String fileName = DefaultPrintingFileName.getUserResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlUserPrinter(fileName);
            case CSV:
                throw new UnsupportedOperationException(String.format("User printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("User printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("User printer not implemented for %s", printingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("User printer target %s not recognized", printingTargetType.toString()));
    }

}
