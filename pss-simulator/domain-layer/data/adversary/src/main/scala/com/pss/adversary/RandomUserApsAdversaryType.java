package com.pss.adversary;


import java.util.HashSet;
import java.util.Set;

public class RandomUserApsAdversaryType extends ApsAdversaryType {
    protected final int adversaryUserCount;

    public RandomUserApsAdversaryType(int adversaryUserCount) {
        super(ApsAdversaryUserStrength.DISGUISED_AS_RANDOM_USER);
        this.adversaryUserCount = adversaryUserCount;
    }

    public int getAdversaryUserCount() {
        return adversaryUserCount;
    }

    @Override
    public Set<Integer> getAdversaryUserIds() {
        Set<Integer> userIds = new HashSet<>();
        userIds.add(adversaryUserCount);
        return userIds;
    }
}
