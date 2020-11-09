package pss.config.task;

import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class UserGroupingConfig {
    protected final UserGroupingMethod userGroupingMethod;
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;

    public UserGroupingConfig(UserGroupingMethod userGroupingMethod, SaveConfig saveConfig, PrintConfig printConfig) {
        this.userGroupingMethod = userGroupingMethod;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
    }

    public UserGroupingMethod getUserGroupingMethod() {
        return userGroupingMethod;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public enum UserGroupingMethod {
        FROM_FILE,
        EQUAL_DISTRIBUTION,
        EQUAL_DISTRIBUTION_SEQUENTIAL,
        RANDOM_DISTRIBUTION,
    }
}
