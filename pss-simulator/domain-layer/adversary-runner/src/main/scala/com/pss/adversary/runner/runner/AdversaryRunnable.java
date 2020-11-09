package com.pss.adversary.runner.runner;

import pss.exception.PssException;
import pss.result.adversary.AdversaryResult;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AdversaryRunnable<TAdversaryResult extends AdversaryResult> {
    TAdversaryResult runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException;
}
