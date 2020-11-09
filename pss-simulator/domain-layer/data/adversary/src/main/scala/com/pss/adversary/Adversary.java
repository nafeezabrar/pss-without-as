package com.pss.adversary;

import pss.deanonymization.Deanonymizable;

import java.util.Set;

public abstract class Adversary<TDeanonymizer extends Deanonymizable> {
    protected final AdversaryType adversaryType;
    protected final TDeanonymizer deanonymizer;

    protected Adversary(AdversaryType adversaryType, TDeanonymizer deanonymizer) {
        this.adversaryType = adversaryType;
        this.deanonymizer = deanonymizer;

    }

    public AdversaryType getAdversaryType() {
        return adversaryType;
    }

    public TDeanonymizer getDeanonymizer() {
        return deanonymizer;
    }

    public abstract Set<Integer> getAdversaryUserIds();

    public enum AdversaryType {
        APS_ADVERSARY
    }
}
