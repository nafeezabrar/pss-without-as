package pss.config.adversary;

import com.pss.adversary.Adversary.AdversaryType;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.DeanonymizationConfig;

public class MultipleApsAdversaryConfig extends AdversaryConfig {

    protected final DeanonymizationConfig deanonymizationConfig;
    protected final ReportFilteringConfig reportFilteringConfig;
    protected final ApsAdversaryGenerationType apsAdversaryGenerationType;

    public MultipleApsAdversaryConfig(SaveConfig saveConfig, PrintConfig printConfig, DecodabilityCalculationConfig decodabilityCalculationConfig, DeanonymizationConfig deanonymizationConfig, ReportFilteringConfig reportFilteringConfig, ApsAdversaryGenerationType apsAdversaryGenerationType) {
        super(AdversaryType.APS_ADVERSARY, saveConfig, printConfig, decodabilityCalculationConfig);
        this.deanonymizationConfig = deanonymizationConfig;
        this.reportFilteringConfig = reportFilteringConfig;
        this.apsAdversaryGenerationType = apsAdversaryGenerationType;
    }

    public DeanonymizationConfig getDeanonymizationConfig() {
        return deanonymizationConfig;
    }

    public ReportFilteringConfig getReportFilteringConfig() {
        return reportFilteringConfig;
    }

    public ApsAdversaryGenerationType getApsAdversaryGenerationType() {
        return apsAdversaryGenerationType;
    }

    public enum ApsAdversaryGenerationType {
        SIMPLE_SET_1,
        SIMPLE_SET_2,
        SIMPLE_SET_3,
    }
}
