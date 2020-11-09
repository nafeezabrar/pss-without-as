package pss.config.saving;

public abstract class SaveConfig {
    protected final boolean shouldSave;

    public SaveConfig(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

    public boolean shouldSave() {
        return shouldSave;
    }
}
