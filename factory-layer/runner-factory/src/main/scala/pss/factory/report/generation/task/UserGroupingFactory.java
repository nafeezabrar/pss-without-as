package pss.factory.report.generation.task;

import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.ShouldPrintConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.config.task.UserGroupingConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.result.presentable.printing.HtmlUserGroupPrinter;
import pss.result.presentable.printing.UserGroupPrintable;
import pss.saving.user_group_mapper.UserGroupSavable;
import pss.user.grouping.*;

import static pss.config.task.UserGroupingConfig.UserGroupingMethod;

public class UserGroupingFactory {
    public static UserGroupable generateUserGrouper(UserGroupingConfig userGroupingConfig, String resultDirectory) throws RunnerCreationException, PssException, InvalidConfigException {
        UserGroupingMethod userGroupingMethod = userGroupingConfig.getUserGroupingMethod();
        switch (userGroupingMethod) {
            case FROM_FILE:
                return generateUserGrouperFromFile(userGroupingConfig);
            case EQUAL_DISTRIBUTION:
                return generateEquallyDistributedUserGrouper(userGroupingConfig, resultDirectory);
            case EQUAL_DISTRIBUTION_SEQUENTIAL:
                return generateSequentialEquallyDistributedUserGrouper(userGroupingConfig, resultDirectory);
            case RANDOM_DISTRIBUTION:
                return generateRandomUserGrouper(userGroupingConfig);
        }
        throw new PssException("Invalid UserGrouping method");
    }

    private static UserGroupable generateSequentialEquallyDistributedUserGrouper(UserGroupingConfig userGroupingConfig, String resultDirectory) throws InvalidConfigException {
        UserGroupable userGrouper = new SequentialEqualDistributionUserGrouper();
        if (userGroupingConfig.getSaveConfig().shouldSave()) {
            userGrouper = withSaver(userGrouper, generateUserGroupSaver((ShouldSaveConfig) userGroupingConfig.getSaveConfig()));
        }
        if (userGroupingConfig.getPrintConfig().shouldPrint()) {
            userGrouper = withPrinter(userGrouper, generateUserGroupPrinter((ShouldPrintConfig) userGroupingConfig.getPrintConfig(), resultDirectory));
        }
        return userGrouper;
    }

    private static UserGroupable generateUserGrouperFromFile(UserGroupingConfig userGroupingConfig) {
        throw new UnsupportedOperationException("user grouper from file not implemented");
    }

    private static UserGroupable generateEquallyDistributedUserGrouper(UserGroupingConfig userGroupingConfig, String resultDirectory) throws InvalidConfigException {
        UserGroupable userGrouper = new EqualDistributionUserGrouper();
        if (userGroupingConfig.getSaveConfig().shouldSave()) {
            userGrouper = withSaver(userGrouper, generateUserGroupSaver((ShouldSaveConfig) userGroupingConfig.getSaveConfig()));
        }
        if (userGroupingConfig.getPrintConfig().shouldPrint()) {
            userGrouper = withPrinter(userGrouper, generateUserGroupPrinter((ShouldPrintConfig) userGroupingConfig.getPrintConfig(), resultDirectory));
        }
        return userGrouper;
    }

    private static UserGroupable generateRandomUserGrouper(UserGroupingConfig userGroupingConfig) {
        throw new UnsupportedOperationException("user grouper with random distribution not implemented");
    }

    private static UserGroupable withPrinter(UserGroupable userGenerator, UserGroupPrintable userPrinter) {
        return new UserGrouperWithPrinter(userGenerator, userPrinter);
    }

    private static UserGroupable withSaver(UserGroupable userGenerator, UserGroupSavable userSaver) {
        return new UserGrouperWithSaver(userGenerator, userSaver);
    }


    private static UserGroupSavable generateUserGroupSaver(ShouldSaveConfig saveConfig) throws InvalidConfigException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(String.format("User Group saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("User Group saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(String.format("User Group saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("User Group saver not implemented for %s", savingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("User saving target %s not recognized", savingTargetType.toString()));
    }

    private static UserGroupPrintable generateUserGroupPrinter(ShouldPrintConfig printConfig, String resultDirectory) throws InvalidConfigException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String targetFileName = DefaultPrintingFileName.getUserGroupResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlUserGroupPrinter(targetFileName);
            case CSV:
                throw new UnsupportedOperationException(String.format("User Group printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("User Group printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("User Group printer not implemented for %s", printingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("User Group printer target %s not recognized", printingTargetType.toString()));
    }
}
