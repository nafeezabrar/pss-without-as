package com.pss.adversary;


import java.util.Set;

public abstract class ApsAdversaryType {
    protected final ApsAdversaryUserStrength apsAdversaryUserStrength;

    public ApsAdversaryType(ApsAdversaryUserStrength apsAdversaryUserStrength) {
        this.apsAdversaryUserStrength = apsAdversaryUserStrength;
    }

    public ApsAdversaryUserStrength getApsAdversaryUserStrength() {
        return apsAdversaryUserStrength;
    }

    public abstract Set<Integer> getAdversaryUserIds();

    public enum ApsAdversaryUserStrength {
        DISGUISED_AS_SINGLE_USER,
        DISGUISED_AS_MULTI_USER,
        DISGUISED_AS_RANDOM_USER
    }
}
