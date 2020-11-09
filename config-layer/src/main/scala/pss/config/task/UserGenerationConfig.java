package pss.config.task;

import pss.config.RandomSource;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class UserGenerationConfig {
    protected final int totalUsers;
    protected final UserGenerationMethod userGenerationMethod;
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;
    protected final RandomSource randomSource;

    public UserGenerationConfig(int totalUsers, UserGenerationMethod userGenerationMethod, SaveConfig saveConfig, PrintConfig printConfig, RandomSource randomSource) {
        this.totalUsers = totalUsers;
        this.userGenerationMethod = userGenerationMethod;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
        this.randomSource = randomSource;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public UserGenerationMethod getUserGenerationMethod() {
        return userGenerationMethod;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public enum UserGenerationMethod {
        FROM_FILE,
        RANDOM
    }
}
