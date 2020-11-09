package com.pss.adversary.runner;


import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.exception.PssException;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.saving.adversary_result.AdversaryResultSavable;

import java.io.IOException;
import java.net.URISyntaxException;

public class AdversaryRunnerWithSaver implements AdversaryRunnable {
    protected final AdversaryRunnable adversaryRunnable;
    protected final AdversaryResultSavable adversaryResultSaver;

    public AdversaryRunnerWithSaver(AdversaryRunnable adversaryRunnable, AdversaryResultSavable adversaryResultSaver) {
        this.adversaryRunnable = adversaryRunnable;
        this.adversaryResultSaver = adversaryResultSaver;
    }

    @Override
    public AdversaryResultWithSingleOcTable runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException {
        AdversaryResultWithSingleOcTable adversaryResultWithSingleOcTable = (AdversaryResultWithSingleOcTable) adversaryRunnable.runAdversary();
        adversaryResultSaver.saveAdversaryResult(adversaryResultWithSingleOcTable);
        return adversaryResultWithSingleOcTable;
    }
}
