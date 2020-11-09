package com.pss.adversary;


import java.util.Set;

public class MultiUserApsAdversaryType extends ApsAdversaryType {
    protected final Set<Integer> userIds;

    public MultiUserApsAdversaryType(Set<Integer> userIds) {
        super(ApsAdversaryUserStrength.DISGUISED_AS_MULTI_USER);
        this.userIds = userIds;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    @Override
    public Set<Integer> getAdversaryUserIds() {
        return userIds;
    }
}
