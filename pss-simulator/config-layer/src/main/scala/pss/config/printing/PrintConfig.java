package pss.config.printing;

public abstract class PrintConfig {
    protected final boolean shouldPrint;

    public PrintConfig(boolean shouldPrint) {
        this.shouldPrint = shouldPrint;
    }

    public boolean shouldPrint() {
        return shouldPrint;
    }

    public boolean isShouldPrint() {
        return shouldPrint;
    }
}
