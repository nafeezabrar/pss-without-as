package pss;

import pss.config.printing.DefaultPrintingFileName;
import pss.config.runner.RunnerConfig;
import pss.config.runner.RunnerType;
import pss.config.seed.SeedGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.factory.report.generation.runner.PssRunnerFactory;
import pss.factory.report.generation.seed.generation.SeedGenerationFactory;
import pss.library.FileCleaner;
import pss.library.FileUtility;
import pss.reader.config.readers.JsonSeedGenerationConfigReader;
import pss.reader.config.runner.JsonMainConfigReader;
import pss.reader.json.JsonFileReader;
import pss.result.presentable.printing.TextFileDefaultResultSummaryPrinter;
import pss.runner.PssRunnable;
import pss.seed.generation.SeedGenerable;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static pss.config.printing.ShouldPrintConfig.AppendMode.SHOULD_APPEND;

public class Main {
    public static void main(String[] args) throws Exception {
        String mainConfigFileName = "main-multiple-runner-config.json";
        JsonMainConfigReader jsonMainConfigReader = new JsonMainConfigReader(mainConfigFileName);
        Map<String, RunnerConfig> runnerConfigMap = jsonMainConfigReader.readPssRunnerConfigs();
        String resultParentDir = "Results";
        FileUtility.createDirectory(resultParentDir);
        for (String directoryName : runnerConfigMap.keySet()) {
            RunnerConfig runnerConfig = runnerConfigMap.get(directoryName);
            String fullDirectoryName = resultParentDir + "/" + directoryName;
            FileUtility.createDirectory(fullDirectoryName);
            System.out.println("running " + directoryName);
            runSingleRunnerConfig(runnerConfig, mainConfigFileName, fullDirectoryName);
            System.out.println("running completed " + directoryName);
            writeResultSummary(fullDirectoryName);
        }

    }

    private static void writeResultSummary(String fullDirectoryName) throws URISyntaxException, IOException, PssException {
        String resultSummaryFileName = DefaultPrintingFileName.getResultSummaryFileName();
        TextFileDefaultResultSummaryPrinter resultSummaryPrinter = new TextFileDefaultResultSummaryPrinter(fullDirectoryName, resultSummaryFileName, SHOULD_APPEND);
        resultSummaryPrinter.printResultSummary();
    }

    private static void runSingleRunnerConfig(RunnerConfig runnerConfig, String mainConfigFileName, String resultDirectory) throws Exception {

        printRunningStartingLog(resultDirectory);

        if (runnerConfig == null) {
            throw new InvalidConfigException("runner config cannot be created");
        }

        RunnerType runnerType = runnerConfig.getRunnerType();
        if (runnerType == RunnerType.RESULT_FILTERING_RUNNER) {
            runSingleIteration(runnerConfig, 1, resultDirectory);
            return;
        }

        List<Long> seeds = generateSeeds(mainConfigFileName);
        int i = 0;

        for (Long seed : seeds) {
            runSingleIteration(runnerConfig, seed, resultDirectory);
            System.out.println("iteration " + i + " completed");
            i++;
        }
        printRunningCompletionLog(resultDirectory);
    }

    private static void printRunningCompletionLog(String resultDirectory) throws IOException {
        FileWriter fileWriter = FileUtility.createFileWriteSafelyInAppendMode("running_information");
        fileWriter.append("completed '" + resultDirectory + "'" + "\n");
        fileWriter.close();
    }

    private static void printRunningStartingLog(String resultDirectory) throws IOException {
        FileWriter fileWriter = FileUtility.createFileWriteSafelyInAppendMode("running_information");
        fileWriter.append("started '" + resultDirectory + "'" + "\n");
        fileWriter.close();
    }

    private static void runSingleIteration(RunnerConfig runnerConfig, long seed, String resultDirectory) throws Exception {
        PssRunnable pssRunner = PssRunnerFactory.generatePssRunner(runnerConfig, seed, resultDirectory);
        pssRunner.run();
        clean(resultDirectory);
    }

    private static void clean(String resultDirectory) {
        String ocTablePrefix = DefaultPrintingFileName.getOcTableSaveFileName();
        FileCleaner.cleanFiles(resultDirectory, ocTablePrefix);
    }

    private static List<Long> generateSeeds(String mainConfigFileName) throws ReaderException, InvalidConfigException, PssException {
        JsonFileReader jsonFileReader = new JsonFileReader(mainConfigFileName);
        SeedGenerationConfig seedGenerationConfig = JsonSeedGenerationConfigReader.readSeedGenerationConfig(jsonFileReader);
        SeedGenerable seedGenerator = SeedGenerationFactory.createSeedGenerator(seedGenerationConfig);
        return seedGenerator.generateSeeds();
    }
}
