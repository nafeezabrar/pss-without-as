package pss.config.adversary;

import com.pss.adversary.Adversary.AdversaryType;
import com.pss.adversary.ApsAdversaryType;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.DeanonymizationConfig;

public class ApsAdversaryConfig extends AdversaryConfig {
    protected final DeanonymizationConfig deanonymizationConfig;
    protected final ReportFilteringConfig reportFilteringConfig;
    protected final ApsAdversaryType apsAdversaryType;

    public ApsAdversaryConfig(AdversaryType adversaryType, SaveConfig saveConfig, PrintConfig printConfig, DecodabilityCalculationConfig decodabilityCalculationConfig, DeanonymizationConfig deanonymizationConfig, ReportFilteringConfig reportFilteringConfig, ApsAdversaryType apsAdversaryType) {
        super(adversaryType, saveConfig, printConfig, decodabilityCalculationConfig);
        this.deanonymizationConfig = deanonymizationConfig;
        this.reportFilteringConfig = reportFilteringConfig;
        this.apsAdversaryType = apsAdversaryType;
    }


    public DeanonymizationConfig getDeanonymizationConfig() {
        return deanonymizationConfig;
    }

    public ReportFilteringConfig getReportFilteringConfig() {
        return reportFilteringConfig;
    }

    public ApsAdversaryType getApsAdversaryType() {
        return apsAdversaryType;
    }
}
