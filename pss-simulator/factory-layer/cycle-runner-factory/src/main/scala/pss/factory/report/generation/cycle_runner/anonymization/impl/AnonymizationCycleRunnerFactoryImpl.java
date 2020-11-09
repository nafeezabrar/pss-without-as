package pss.factory.report.generation.cycle_runner.anonymization.impl;

import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.config.runner.CycleRunnerConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.factory.report.generation.cycle_runner.anonymization.AnonymizationCycleRunnerFactory;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.AnonymizableReportGenerable;
import pss.result.presentable.printing.AnonymizationResultPrintable;
import pss.result.presentable.printing.HtmlAnonymizationResultPrinter;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;
import pss.runner.cycle.impl.anonymization.AnonymizationCycleRunnerImpl;
import pss.runner.cycle.impl.anonymization.AnonymizationCycleRunnerWithPrinter;
import pss.runner.cycle.impl.anonymization.AnonymizationCycleRunnerWithSaver;
import pss.saving.anonymization_result.AnonymizationResultSavable;

public class AnonymizationCycleRunnerFactoryImpl implements AnonymizationCycleRunnerFactory {
    protected final CycleRunnerConfig cycleRunnerConfig;
    protected final String resultDirectory;

    public AnonymizationCycleRunnerFactoryImpl(CycleRunnerConfig cycleRunnerConfig, String resultDirectory) {
        this.cycleRunnerConfig = cycleRunnerConfig;
        this.resultDirectory = resultDirectory;
    }

    @Override
    public AnonymizationCycleRunner generateAnonymizationCycleRunner(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizerSelector anonymizerChooser, UserMapper userMapper, AnonymityGenerable anonymityGenerator, AnonymizableReportGenerable anonymizableReportGenerator) throws PssException {
        AnonymizationCycleRunner anonymizationCycleRunner = new AnonymizationCycleRunnerImpl(anonymizerChooser, userMapper, anonymityGenerator, anonymizableReportGenerator);
        SaveConfig saveConfig = cycleRunnerConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            anonymizationCycleRunner = withSaver(pssType, ooiUserGroupMapper, anonymizationCycleRunner, (ShouldSaveConfig) saveConfig);
        }
        PrintConfig printConfig = cycleRunnerConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            anonymizationCycleRunner = withPrinter(pssType, ooiUserGroupMapper, anonymizationCycleRunner, (ShouldPrintConfig) printConfig);
        }
        return anonymizationCycleRunner;
    }

    private AnonymizationCycleRunner withSaver(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizationCycleRunner anonymizationCycleRunner, ShouldSaveConfig saveConfig) throws PssException {
        AnonymizationResultSavable anonymizationResultSaver = getAnonymizationResultSaver(saveConfig);
        return new AnonymizationCycleRunnerWithSaver(anonymizationCycleRunner, pssType, ooiUserGroupMapper, anonymizationResultSaver);
    }

    private AnonymizationResultSavable getAnonymizationResultSaver(ShouldSaveConfig saveConfig) throws PssException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        String targetFileName = saveConfig.getTargetFileName();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner saver not implemented for %s", savingTargetType.toString()));
        }
        throw new PssException(String.format("Saving Target typeKey %s not recognized", savingTargetType.toString()));
    }

    private AnonymizationCycleRunner withPrinter(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizationCycleRunner anonymizationCycleRunner, ShouldPrintConfig printConfig) throws PssException {
        AnonymizationResultPrintable anonymizationResultPrinter = getPrinter(printConfig);
        return new AnonymizationCycleRunnerWithPrinter(anonymizationCycleRunner, pssType, ooiUserGroupMapper, anonymizationResultPrinter);
    }

    private AnonymizationResultPrintable getPrinter(ShouldPrintConfig printConfig) throws PssException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String targetFileName = DefaultPrintingFileName.getAnonymizationResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlAnonymizationResultPrinter(targetFileName);
            case CSV:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("Anonymization cycle runner printer not implemented for %s", printingTargetType.toString()));
        }
        throw new PssException(String.format("Saving Target typeKey %s not recognized", printingTargetType.toString()));
    }
}
