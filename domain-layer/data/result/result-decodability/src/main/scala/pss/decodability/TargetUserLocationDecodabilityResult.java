package pss.decodability;

public class TargetUserLocationDecodabilityResult {
    protected final TargetUserInfo targetUserInfo;

    public TargetUserLocationDecodabilityResult(TargetUserInfo targetUserInfo) {
        this.targetUserInfo = targetUserInfo;
    }

    public enum TargetUserInfo {
        NO_TARGET_USER,
        SINGLE_TARGET_USER
    }

    public TargetUserInfo getTargetUserInfo() {
        return targetUserInfo;
    }
}
