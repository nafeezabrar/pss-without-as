package com.pss.adversary;


import java.util.HashSet;
import java.util.Set;

public class SingleUserApsAdversaryType extends ApsAdversaryType {
    protected final int userId;

    public SingleUserApsAdversaryType(int userId) {
        super(ApsAdversaryUserStrength.DISGUISED_AS_SINGLE_USER);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Set<Integer> getAdversaryUserIds() {
        Set<Integer> userIds = new HashSet<>();
        userIds.add(userId);
        return userIds;
    }
}
