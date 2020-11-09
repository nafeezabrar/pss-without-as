package pss.config.saving;

import pss.SavingTargetType;

public class ShouldSaveConfig extends SaveConfig {
    protected final SavingTargetType savingTargetType;
    protected final String targetFileName;

    public ShouldSaveConfig(SavingTargetType savingTargetType, String targetFileName) {
        super(true);
        this.savingTargetType = savingTargetType;
        this.targetFileName = targetFileName;
    }

    public SavingTargetType getSavingTargetType() {
        return savingTargetType;
    }

    public String getTargetFileName() {
        return targetFileName;
    }
}
