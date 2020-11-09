package pss.decodability;

import static pss.decodability.TargetUserLocationDecodabilityResult.TargetUserInfo.NO_TARGET_USER;

public class NullTargetUserLocationDecodabilityResult extends TargetUserLocationDecodabilityResult {

    public NullTargetUserLocationDecodabilityResult() {
        super(NO_TARGET_USER);
    }
}
