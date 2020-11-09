package pss.config;

public class ConfigKeyString {
    public static final String mainConfigurationFilePathKey = "resources/configurations/main-config.json";
    public static final String configSourceType = "Config Source Type";
    public static final String fileNameKey = "File Name";
    public static final String fileNamesKey = "File Names";
    public static final String saveFileNameKey = "Saved File Name";
    public static final String printFileNameKey = "Printed File Name";
    public static final String filePath = "File Path";
    public static final String fileType = "File Type";
    public static final String save = "Save";
    public static final String print = "Print";
    public static final String appendKey = "Append";
    public static final String yes = "Yes";
    public static final String no = "No";
    public static final String idKey = "Id";
    public static final String nameKey = "Name";
    public static final String fromFile = "From File";
    public static final String random = "Random";
    public static final String method = "Method";
    public static final String dimensionKey = "Dimension";
    public static final String idgas = "IDGAS";
    public static final String aas = "AAS";
    public static final String ras = "RAS";
    public static final String dgas = "DGAS";
    public static final String seed = "Seed";
    public static final String printTargetType = "Print To";
    public static final String saveTargetType = "Save To";
    public static final String numberKey = "Number";
    public static final String inherited = "Inherited";
    public static final String readingSourceKey = "Source Type";
    public static final String percentage = "Percentage";

    public static class PssType {
        public static final String pssType = "Pss Type";
        public static final String dimensionType = "Dimension Type";
        public static final String singleDimensionString = "SINGLE";
        public static final String multiDimensionString = "MULTI";
        public static final String nString = "N";
        public static final String hasLocationDimension = "Location Dimension";
        public static final String locationDimensionIdKey = "Location Dimension Id";
    }

    public static class SourceTypeString {
        public static final String json = "Json";
        public static final String db = "DB";
        public static final String csv = "CSV";
        public static final String textFile = "Text File";
        public static final String html = "HTML";
    }

    public static class Runner {
        public static final String runnerType = "Runner Type";
        public static final String pssVariableGeneratingRunner = "Psv Generation";
        public static final String pssGroupingRunner = "Pss Grouping";
        public static final String userGeneratingRunner = "User Generation";
        public static final String userGroupingRunner = "User Grouping";
        public static final String observedReportGeneratingRunner = "Observed Report Generation";
        public static final String anonymizingRunner = "Anonymization";
        public static final String deanonymizingRunner = "Deanonymization";
        public static final String fullSimulationRunner = "Full Simulation";
        public static final String resultFilteringRunner = "Result Filtering";
        public static final String fullSimulationRunnerWithAdversary = "Full Simulation With Adversary";
        public static final String fullSimulationRunnerWithAdversaryTillDecoding = "Full Simulation With Adversary Till Decoding";
        public static final String fullSimulationRunnerWithGeneratedAdversary = "Full Simulation With Generated Adversary";
        public static final String fullSimulationRunnerWithGeneratedAdversaryTillDecoding = "Full Simulation With Generated Adversary Till Decoding";
        public static final String fullSimulationRunnerTillDecoding = "Full Simulation Till Decoding";
    }

    public static class SeedGeneration {
        public static final String seedGenerationConfigKey = "Seed Generation";
        public static final String seedGenerationType = "Type";
        public static final String fixedSeed = "Fixed";
        public static final String randomSeeds = "Random";
        public static final String totalSeedKey = "Total Seeds";
    }
    public static class PsvGeneration {
        public static final String pssVariableGenerationConfigKey = "Psv Generation Config";
        public static final String smallLetterGeneration = "Alphabet Small";
        public static final String capitalLetterGeneration = "Alphabet Capital";
        public static final String ooiValueGenerationMethodKey = "Value Generation";
        public static final String sequentialValueGeneration = "Sequential";
        public static final String minAreaPointOoiGeneration = "Point";
    }

    public static class PssGrouping {
        public static final String pssGroupingConfigKey = "Pss Grouping Config";
        public static final String oneDimensionalGrouping = "One Dimension";
        public static final String allDimensionalGrouping = "All Dimension";
        public static final String allDimensionalSequentialGrouping = "All Dimension Sequential";
        public static final String ooiInEachGroupKey = "Ooi In Each Group";
        public static final String dividingDimensionKey = "Dividing Dimension";
        public static final String printOoiMapperKey = "Print Ooi Values Per Group";
    }

    public static class UserGeneration {
        public static final String userGenerationConfigKey = "User Generation Config";
        public static final String totalUsersKey = "Number Of Users";
    }

    public static class UserGrouping {
        public static final String userGroupingConfigKey = "User Grouping Config";
        public static final String userGroupingEqualDistribution = "Equal Distribution";
        public static final String sequentialUserGroupingEqualDistribution = "Equal Distribution Sequential";
        public static final String userGroupingRandomDistribution = "Random Distribution";
    }

    public static class AnonymityGeneration {
        public static final String anonymityGenerationConfigKey = "Anonymity Generation Config";
        public static final String anonymityKey = "Anonymity";
        public static final String addingAnonymityKey = "Adding Anonymity";
        public static final String replacingAnonymityKey = "Replacing Anonymity";
        public static final String fixedAnonymity = "Fixed";
        public static final String uniformAnonymity = "From Set Uniform";
        public static final String fixedAasAnonymity = "Fixed AAS";
        public static final String fixedRasAnonymity = "Fixed RAS";
        public static final String randomAnonymity = "Random";
        public static final String fromFileInheritedAnonymity = "From File Inherited";
        public static final String fromFileAnonymity = "From File";
        public static final String anonymityLowerBoundKey = "Minimum Anonymity";
        public static final String anonymityUpperBoundKey = "Maximum Anonymity";
    }

    public static class Anonymization {
        public static final String anonymizationConfigKey = "Anonymization Config";
        public static final String anonymizedReportKey = "Anonymized Reports";
        public static final String reportIdKey = "Report Id";
        public static final String anonymizedLocalOoisKey = "Anonymized Local Oois";
        public static final String localOoisKey = "Local Oois";
    }

    public static class Deanonymization {
        public static final String deanonymizationConfigKey = "Deanonymization Config";
    }

    public static class ObservedReportGeneration {
        public static final String observedReportGenerationConfigKey = "Observed Report Generation Config";
        public static final String reportCountKey = "Number Of Reports";
        public static final String reportDataKey = "Reports";
        public static final String reportIdKey = "Report Id";
        public static final String userIdKey = "User Id";
        public static final String reportValueKey = "Value";
    }

    public static class FinalReportGeneration {
        public static final String finalReportGenerationConfigKey = "Final Report Generation Config";
        public static final String simpleGeneration = "Simple";
        public static final String faultyGeneration = "Faulty";
        public static final String randomAasGeneration = "AAS Random";
        public static final String randomRasGeneration = "RAS Random";
    }

    public static class Cycle {
        public static final String cycleRunnerConfigKey = "Cycle Runner Config";
    }

    public static class FilePaths {
        public static final String ooiMapperOutputFileName = "ooi-mapper-output.html";
    }

    public static class Adversary {
        public static final String adversaryConfigKey = "Adversary Config";
        public static final String adversaryType = "Adversary Type";

        public static final String apsAdversary = "Aps Adversary";
        public static final String adversaryStrengthKey = "Strength";
        public static final String adversaryGenerationType = "Generation Type";
        public static final String disguisedAsSingleUser = "One User";
        public static final String disguisedAsMultipleUsers = "Multiple Users";
        public static final String disguisedAsRandomUsers = "Random Users";
        public static final String adversaryUserId = "User Id";
        public static final String adversaryUserNumber = "Adversary User Count";

        public static class ReportFiltering {
            public static final String reportFilteringType = "Report Filter By";
            public static final String randomReports = "Random";
            public static final String randomReportsWithPercentage = "Random Percentage";
            public static final String reportCountKey = "Number Of Reports";
            public static final String reportPercentageKey = "Percentage Of Reports";
            public static final String allReports = "None";
            public static final String limitedTimeReports = "Limited Time";
            public static final String lmiitedTimeReportWithPercentage = "Limited Time Percentage";
            public static final String limitedTimeRandomReports = "Limited Time Random";
            public static final String targetUserReports = "Target User";
            public static final String targetUserSetReports = "Target User Set";
            public static final String targetUserLimitedTimeReports = "Target User Limited Time";
            public static final String targetUserIdKey = "Target User Id";
            public static final String targetUserIdSetKey = "Target User Id Set";
            public static final String startReportIdKey = "From Report Id";
            public static final String endReportIdKey = "To";
        }

        public static class GenerationType {
            public static final String simpleSetOne = "Simple Set 1";
            public static final String simpleSetTwo = "Simple Set 2";
            public static final String simpleSetThree = "Simple Set 3";

        }
    }

    public static class DecodabilityCalculation {
        public static final String decodabilityCalculationConfigKey = "Decodability Calculation Config";
        public static final String decodabilityCalculationPeriodKey = "Period";
        public static final String decodabilityType = "Type";
        public static final String calculationEndPoint = "End Point";
        public static final String calculationIntermediatePoints = "Intermediate Points";
        public static final String decodabilityTypeFull = "Full";
        public static final String decodabilityTypePartial = "Partial";
        public static final String decodabilityPrintConfigKey = "Print Source";
        public static final String separatePrint = "Separate";
        public static final String decodabilityTypeLocation = "Location";
        public static final String decodabilityTypeRandomLocation = "Location Random";
        public static final String decodabilityTypeLocationAddingPreDecoding = "Location Adding Pre-decoding";
    }

    public static class ResultFiltering {

        public static final String typeKey = "Type";
        public static final String meanStd = "Mean Std";
        public static final String mean = "Mean";
    }

    public static class PostResultProcess {
        public static final String postResultProcessConfigKey = "Post Result Config";
        public static final String observedReportCountMap = "Observed Report Count Map";
        public static final String observedReportValueCountMapKey = "Observed Report Value Count Map";
        public static final String apsDecodabilityResult = "Aps Decodability Result";
        public static final String adversaryOwnReportCountMap = "Adversary Own Report Count Map";
        public static final String adversaryInterceptedReport = "Adversary Intercepted Report Count";
        public static final String adversaryOwnReportedValue = "Adversary Reported Value Count";
        public static final String adversaryOwnReportedDecodability = "Adversary Reported Decodability";
        public static final String adversaryDecodedValue = "Adversary Decoded Value Count";
        public static final String reportCountToDecodeKey = "Report Count to Decode";
        public static final String reportCountOfEachGroupKey = "Report Count Of Each Group";
    }
}
