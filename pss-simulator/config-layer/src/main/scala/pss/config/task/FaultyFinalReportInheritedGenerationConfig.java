package pss.config.task;

public class FaultyFinalReportInheritedGenerationConfig extends FinalReportInheritedGenerationConfig {

    protected final double probability;

    public FaultyFinalReportInheritedGenerationConfig(double probability) {
        super(FinalReportInheritedGenerationMethod.SIMPLE_FAULTY);
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
