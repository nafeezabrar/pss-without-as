package pss.decodability;

import static pss.decodability.TargetUserLocationDecodabilityResult.TargetUserInfo.SINGLE_TARGET_USER;

public class SingleTargetUserLocationDecodabilityResult extends TargetUserLocationDecodabilityResult {
    protected final double targetUserLocationDecodability;

    public SingleTargetUserLocationDecodabilityResult(double targetUserLocationDecodability) {
        super(SINGLE_TARGET_USER);
        this.targetUserLocationDecodability = targetUserLocationDecodability;
    }

    public double getTargetUserLocationDecodability() {
        return targetUserLocationDecodability;
    }
}
