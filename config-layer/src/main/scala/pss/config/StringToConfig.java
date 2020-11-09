package pss.config;

import com.pss.adversary.Adversary;
import com.pss.adversary.ApsAdversaryType;
import pss.PrintingTargetType;
import pss.ReadingSourceType;
import pss.SavingTargetType;
import pss.config.ConfigKeyString.Adversary.*;
import pss.config.adversary.MultipleApsAdversaryConfig;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.printing.DecodabilityPrintConfig;
import pss.config.runner.ResultFilteringRunnerConfig;
import pss.config.runner.RunnerType;
import pss.config.seed.SeedGenerationConfig;
import pss.config.task.FinalReportDirectGenerationConfig.FinalReportDirectGenerationMethod;
import pss.config.task.FinalReportInheritedGenerationConfig.FinalReportInheritedGenerationMethod;
import pss.config.task.ObservedReportGenerationConfig.ReportGenerationMethod;
import pss.config.task.PssGroupingConfig.PssGroupingMethod;
import pss.config.task.PssVariableGenerationConfig;
import pss.config.task.UserGenerationConfig.UserGenerationMethod;
import pss.config.task.UserGroupingConfig.UserGroupingMethod;
import pss.data.decodability.DecodabilityType;
import pss.data.dimension.Dimension;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;

import java.util.Set;

import static com.pss.adversary.Adversary.AdversaryType.APS_ADVERSARY;
import static com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength.*;
import static java.lang.String.format;
import static pss.config.ConfigKeyString.Adversary.GenerationType.*;
import static pss.config.ConfigKeyString.Adversary.ReportFiltering.*;
import static pss.config.ConfigKeyString.Adversary.*;
import static pss.config.ConfigKeyString.AnonymityGeneration.*;
import static pss.config.ConfigKeyString.DecodabilityCalculation.*;
import static pss.config.ConfigKeyString.*;
import static pss.config.ConfigKeyString.PssGrouping.*;
import static pss.config.ConfigKeyString.PsvGeneration.*;
import static pss.config.ConfigKeyString.ResultFiltering.mean;
import static pss.config.ConfigKeyString.ResultFiltering.meanStd;
import static pss.config.ConfigKeyString.Runner.*;
import static pss.config.ConfigKeyString.SeedGeneration.fixedSeed;
import static pss.config.ConfigKeyString.SeedGeneration.randomSeeds;
import static pss.config.ConfigKeyString.UserGrouping.*;
import static pss.config.adversary.MultipleApsAdversaryConfig.ApsAdversaryGenerationType.*;
import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.*;
import static pss.config.printing.DecodabilityPrintConfig.PrintingSource.INHERITED;
import static pss.config.printing.DecodabilityPrintConfig.PrintingSource.SEPARATE;
import static pss.config.runner.ResultFilteringRunnerConfig.ResultFilteringType.CALCULATE_MEAN;
import static pss.config.runner.ResultFilteringRunnerConfig.ResultFilteringType.CALCULATE_MEAN_STD;
import static pss.config.runner.RunnerType.*;
import static pss.config.seed.SeedGenerationConfig.SeedGenerationType.FIXED_SEED;
import static pss.config.seed.SeedGenerationConfig.SeedGenerationType.RANDOM_GENERATED_SEEDS;
import static pss.config.task.AnonymityGenerationConfig.AnonymityGenerationMethod;
import static pss.config.task.AnonymityGenerationConfig.AnonymityGenerationMethod.*;
import static pss.config.task.AnonymizationConfig.AnonymizationMethod;
import static pss.config.task.AnonymizationConfig.AnonymizationMethod.*;
import static pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import static pss.config.task.FinalReportDirectGenerationConfig.FinalReportDirectGenerationMethod.FROM_FILE;
import static pss.config.task.FinalReportInheritedGenerationConfig.FinalReportInheritedGenerationMethod.*;
import static pss.config.task.PssGroupingConfig.PssGroupingMethod.*;
import static pss.config.task.PssVariableGenerationConfig.OoiGenerationMethod;
import static pss.config.task.PssVariableGenerationConfig.OoiGenerationMethod.*;
import static pss.config.task.PssVariableGenerationConfig.OoiGenerationMethod.RANDOM;
import static pss.config.task.PssVariableGenerationConfig.OoiValueGenerationMethod.SEQUENTIAL;
import static pss.config.task.UserGroupingConfig.UserGroupingMethod.*;

public class StringToConfig {
    public static RunnerType getRunnerType(String runnerTypeString) throws InvalidConfigException {
        switch (runnerTypeString) {
            case pssVariableGeneratingRunner:
                return PSS_VARIABLE_GENERATING_RUNNER;
            case userGeneratingRunner:
                return USER_GENERATING_RUNNER;
            case pssGroupingRunner:
                return PSS_GROUPING_RUNNER;
            case anonymizingRunner:
                return ANONYMIZING_RUNNER;
            case deanonymizingRunner:
                return DEANONYMIZING_RUNNER;
            case resultFilteringRunner:
                return RESULT_FILTERING_RUNNER;
            case fullSimulationRunner:
                return FULL_SIMULATION_RUNNER;
            case userGroupingRunner:
                return USER_GROUPING_RUNNER;
            case observedReportGeneratingRunner:
                return REPORT_GENERATING_RUNNER;
            case fullSimulationRunnerWithAdversary:
                return FULL_SIMULATION_WITH_ADVERSARY;
            case fullSimulationRunnerWithGeneratedAdversary:
                return FULL_SIMULATION_WITH_GENERATED_ADVERSARY;
            case fullSimulationRunnerWithGeneratedAdversaryTillDecoding:
                return FULL_SIMULATION_WITH_GENERATED_ADVERSARY_TILL_DECODING;
            case fullSimulationRunnerTillDecoding:
                return FULL_SIMULATION_TILL_DECODING;
            case fullSimulationRunnerWithAdversaryTillDecoding:
                return FULL_SIMULATION_WITH_ADVERSARY_TILL_DECODING;
        }
        throw new InvalidConfigException(format("Invalid Runner typeKey %s", runnerTypeString));
    }

    public static ReadingSourceType getSourceType(String sourceTypeString) throws InvalidConfigException {
        switch (sourceTypeString) {
            case SourceTypeString.json:
                return ReadingSourceType.JSON;
        }
        throw new InvalidConfigException(format("Invalid Source typeKey %s", sourceTypeString));
    }

    public static PssType.PssDimensionType getDimensionType(String dimensionTypeString) throws InvalidConfigException {
        switch (dimensionTypeString) {
            case ConfigKeyString.PssType.singleDimensionString:
                return PssType.PssDimensionType.SINGLE;
            case ConfigKeyString.PssType.multiDimensionString:
                return PssType.PssDimensionType.MULTI;
        }
        throw new InvalidConfigException(format("Invalid Dimension typeKey %s", dimensionTypeString));
    }

    public static OoiGenerationMethod getPssVariableGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case random:
                return RANDOM;
            case fromFile:
                return OoiGenerationMethod.FROM_FILE;
            case capitalLetterGeneration:
                return ALPHABET_CAPITAL_LETTER;
            case smallLetterGeneration:
                return ALPHABET_SMALL_LETTER;
            case minAreaPointOoiGeneration:
                return LOCATION_MIN_AREA_POINTS;
        }
        throw new InvalidConfigException(format("Invalid PSV Generation Method %s", methodString));
    }

    public static PssGroupingMethod getPssGroupingMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case oneDimensionalGrouping:
                return ONE_DIMENSION_GROUPING;
            case allDimensionalGrouping:
                return ALL_DIMENSION_GROUPING;
            case allDimensionalSequentialGrouping:
                return ALL_DIMENSION_SEQUENTIAL_GROUPING;
        }
        throw new InvalidConfigException(format("Invalid PSS Grouping Method %s", methodString));
    }

    public static UserGenerationMethod getUserGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case random:
                return UserGenerationMethod.RANDOM;
            case fromFile:
                return UserGenerationMethod.FROM_FILE;
        }
        throw new InvalidConfigException(format("Invalid User Generation Method %s", methodString));
    }

    public static UserGroupingMethod getUserGroupingMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case userGroupingEqualDistribution:
                return EQUAL_DISTRIBUTION;
            case sequentialUserGroupingEqualDistribution:
                return EQUAL_DISTRIBUTION_SEQUENTIAL;
            case userGroupingRandomDistribution:
                return RANDOM_DISTRIBUTION;
            case fromFile:
                return UserGroupingMethod.FROM_FILE;
        }
        throw new InvalidConfigException(format("Invalid User Grouping Method %s", methodString));
    }

    public static AnonymityGenerationMethod getAnonymityGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case fixedAnonymity:
                return AnonymityGenerationMethod.FIXED;
            case fromFileAnonymity:
                return AnonymityGenerationMethod.FROM_FILE;
            case fromFileInheritedAnonymity:
                return AnonymityGenerationMethod.FROM_FILE_INHERITED;
            case randomAnonymity:
                return AnonymityGenerationMethod.RANDOM;
            case fixedAasAnonymity:
                return FIXED_AAS;
            case fixedRasAnonymity:
                return FIXED_RAS;
            case uniformAnonymity:
                return UNIFORM_ANONYMITY;
        }
        throw new InvalidConfigException(format("Invalid Anonymity Generation Method %s", methodString));
    }

    public static ReportGenerationMethod getReportGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case fromFile:
                return ReportGenerationMethod.FROM_FILE;
            case random:
                return ReportGenerationMethod.RANDOM;
        }
        throw new InvalidConfigException(format("Invalid Report Generation Method %s", methodString));
    }

    public static AnonymizationMethod getAnonymizationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case idgas:
                return IDGAS;
            case fromFile:
                return IDGAS_FROM_FILE;
            case aas:
                return AAS;
            case ras:
                return RAS;
            case dgas:
                return DGAS;
        }
        throw new InvalidConfigException(format("Invalid Anonymization Method %s", methodString));
    }

    public static DeanonymizationMethod getDeanonymizationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case idgas:
                return DeanonymizationMethod.IDGAS;
            case dgas:
                return DeanonymizationMethod.DGAS;
        }
        throw new InvalidConfigException(format("Invalid Deanonymization Method %s", methodString));
    }

    public static FinalReportDirectGenerationMethod getFinalReportDirectGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case fromFile:
                return FROM_FILE;
        }
        throw new InvalidConfigException(format("Invalid Final Report Generation Method %s", methodString));
    }

    public static FinalReportInheritedGenerationMethod getFinalReportInheritedGenerationMethod(String methodString) throws InvalidConfigException {
        switch (methodString) {
            case FinalReportGeneration.simpleGeneration:
                return SIMPLE;
            case FinalReportGeneration.faultyGeneration:
                return SIMPLE_FAULTY;
            case FinalReportGeneration.randomAasGeneration:
                return AAS_RANDOM;
            case FinalReportGeneration.randomRasGeneration:
                return RAS_RANDOM;
        }
        throw new InvalidConfigException(format("Invalid Final Report Generation Method %s", methodString));
    }
    public static SavingTargetType getSavingTargetType(String saveTargetTypeString) throws InvalidConfigException {
        switch (saveTargetTypeString) {
            case SourceTypeString.csv:
                return SavingTargetType.CSV;
            case SourceTypeString.db:
                return SavingTargetType.DB;
            case SourceTypeString.json:
                return SavingTargetType.JSON;
            case SourceTypeString.textFile:
                return SavingTargetType.TEXT_FILE;
        }
        throw new InvalidConfigException(format("Invalid Saving Target typeKey %s", saveTargetTypeString));
    }

    public static PrintingTargetType getPrintingTargetType(String printTargetTypeString) throws InvalidConfigException {
        switch (printTargetTypeString) {
            case SourceTypeString.csv:
                return PrintingTargetType.CSV;
            case SourceTypeString.html:
                return PrintingTargetType.HTML;
            case SourceTypeString.json:
                return PrintingTargetType.JSON;
            case SourceTypeString.textFile:
                return PrintingTargetType.TEXT_FILE;
        }
        throw new InvalidConfigException(format("Invalid Printing Target typeKey %s", printTargetTypeString));
    }

    public static Dimension getDimensionFromString(String dimensionString, Set<Dimension> dimensionSet) throws InvalidConfigException {
        for (Dimension dimension : dimensionSet) {
            if (dimension.getName().equals(dimensionString))
                return dimension;
        }
        throw new InvalidConfigException(format("Invalid Dimension %s written in config", dimensionString));
    }

    public static Adversary.AdversaryType getAdversaryType(String adversaryTypeString) throws InvalidConfigException {
        switch (adversaryTypeString) {
            case apsAdversary:
                return APS_ADVERSARY;
        }
        throw new InvalidConfigException(format("Invalid Adversary typeKey %s written in config", adversaryTypeString));
    }

    public static ReportFilteringConfig.ReportFilteringMethod getReportFilteringType(String reportFilteringTypeString) throws InvalidConfigException {
        switch (reportFilteringTypeString) {
            case ReportFiltering.allReports:
                return ALL_REPORTS;
            case ReportFiltering.randomReports:
                return RANDOM_REPORTS;
            case ReportFiltering.randomReportsWithPercentage:
                return RANDOM_REPORTS_PERCENTAGE;
            case ReportFiltering.limitedTimeReports:
                return LIMITED_TIME_ALL_REPORTS;
            case ReportFiltering.lmiitedTimeReportWithPercentage:
                return LIMITED_TIME_ALL_REPORTS_PERCENTAGE;
            case limitedTimeRandomReports:
                return LIMITED_TIME_RANDOM_REPORTS;
            case targetUserReports:
                return TARGET_USER_REPORT;
            case targetUserSetReports:
                return TARGET_USER_SET_REPORT;
            case targetUserLimitedTimeReports:
                return TARGET_USER_LIMITED_TIME;
        }
        throw new InvalidConfigException(format("Invalid Report Filtering typeKey %s written in config", reportFilteringTypeString));
    }

    public static ApsAdversaryType.ApsAdversaryUserStrength getApsAdversaryType(String adversaryStrengthString) throws InvalidConfigException {
        switch (adversaryStrengthString) {
            case disguisedAsSingleUser:
                return DISGUISED_AS_SINGLE_USER;
            case disguisedAsMultipleUsers:
                return DISGUISED_AS_MULTI_USER;
            case disguisedAsRandomUsers:
                return DISGUISED_AS_RANDOM_USER;
        }
        throw new InvalidConfigException(format("Invalid adversary strength %s written in config", adversaryStrengthString));
    }

    public static DecodabilityType getDecodabilityType(String decodabilityTypeString) throws InvalidConfigException {
        switch (decodabilityTypeString) {
            case decodabilityTypeFull:
                return DecodabilityType.FULL;
            case decodabilityTypePartial:
                return DecodabilityType.PARTIAL;
            case decodabilityTypeLocation:
                return DecodabilityType.LOCATION;
            case decodabilityTypeRandomLocation:
                return DecodabilityType.LOCATION_RANDOM;
            case decodabilityTypeLocationAddingPreDecoding:
                return DecodabilityType.LOCATION_ADD_PRE_DECODING;
        }
        throw new InvalidConfigException(format("Invalid decodability typeKey %s written in config", decodabilityTypeString));
    }

    public static DecodabilityPrintConfig.PrintingSource getDecodabilityPrintingSource(String decodabilityPrintSourceString) throws InvalidConfigException {
        switch (decodabilityPrintSourceString) {
            case inherited:
                return INHERITED;
            case separatePrint:
                return SEPARATE;
        }
        throw new InvalidConfigException(format("Invalid decodability printing source %s written in config", decodabilityPrintSourceString));
    }

    public static PssVariableGenerationConfig.OoiValueGenerationMethod getOoiValueGenerationMethod(String ooiValueGenerationMethodString) throws InvalidConfigException {
        switch (ooiValueGenerationMethodString) {
            case PsvGeneration.sequentialValueGeneration:
                return SEQUENTIAL;
        }
        throw new InvalidConfigException(format("Invalid ooi value generation method %s written in config", ooiValueGenerationMethodString));
    }

    public static MultipleApsAdversaryConfig.ApsAdversaryGenerationType getAdversaryGenerationType(String apsAdversaryGenerationTypeString) throws InvalidConfigException {
        switch (apsAdversaryGenerationTypeString) {
            case simpleSetOne:
                return SIMPLE_SET_1;
            case simpleSetTwo:
                return SIMPLE_SET_2;
            case simpleSetThree:
                return SIMPLE_SET_3;
        }
        throw new InvalidConfigException(format("Invalid ooi value generation method %s written in config", apsAdversaryGenerationTypeString.toString()));
    }

    public static ResultFilteringRunnerConfig.ResultFilteringType getResultFilteringType(String typeKey) throws InvalidConfigException {
        switch (typeKey) {
            case meanStd:
                return CALCULATE_MEAN_STD;
            case mean:
                return CALCULATE_MEAN;
        }
        throw new InvalidConfigException(format("Invalid ooi value generation method %s written in config", typeKey.toString()));
    }

    public static SeedGenerationConfig.SeedGenerationType getSeedGenerationMethod(String seedGenerationTypeString) throws InvalidConfigException {
        switch (seedGenerationTypeString) {
            case fixedSeed:
                return FIXED_SEED;
            case randomSeeds:
                return RANDOM_GENERATED_SEEDS;
        }
        throw new InvalidConfigException(format("Invalid seed generation method %s written in config", seedGenerationTypeString.toString()));
    }
}
