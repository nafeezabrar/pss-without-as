package com.pss.adversary.runner;

import com.pss.adversary.Adversary;

public class AdversaryRunner<TAdversary extends Adversary> {
    protected final TAdversary adversary;

    protected AdversaryRunner(TAdversary adversary) {
        this.adversary = adversary;
    }
}
