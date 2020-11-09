package com.pss.adversary.runner;

import com.pss.adversary.ApsAdversary;
import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.oc_table.OcTable;
import pss.data.pss_type.PssType;
import pss.deanonymization.Deanonymizable;
import pss.deanonymization.idgas.MultiIdgasDeanonymizer;
import pss.deanonymization.idgas.SingleIdgasDeanonymizer;
import pss.exception.PssException;
import pss.report.finalreport.FinalReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.deanonymization.DeanonymizationResult;
import pss.saving.OcTableSavable.OcTableSavable;

import java.io.IOException;
import java.util.List;

import static pss.data.pss_type.PssType.PssDimensionType;

public class SimpleApsAdversaryRunner extends AdversaryRunner<ApsAdversary> implements AdversaryRunnable<AdversaryResultWithSingleOcTable> {

    protected final List<FinalReport> finalReports;
    protected final PssType pssType;
    protected final OcTableSavable ocTableSaver;

    public SimpleApsAdversaryRunner(ApsAdversary adversary, List<FinalReport> finalReports, PssType pssType, OcTableSavable ocTableSaver) {
        super(adversary);
        this.finalReports = finalReports;
        this.pssType = pssType;
        this.ocTableSaver = ocTableSaver;
    }

    @Override
    public AdversaryResultWithSingleOcTable runAdversary() throws PssException, IOException, ClassNotFoundException {
        List<FinalReport> leakedReports = adversary.getLeakedReports();
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        OcTable finalOcTable;
        Deanonymizable deanonymizer;
        switch (pssDimensionType) {
            case SINGLE:
                SingleIdgasDeanonymizer singleDeanonymizer = (SingleIdgasDeanonymizer) adversary.getDeanonymizer();
                finalOcTable = singleDeanonymizer.getOcTable();
                deanonymizer = singleDeanonymizer;
                break;
            case MULTI:
                MultiIdgasDeanonymizer multiDeanonymizer = (MultiIdgasDeanonymizer) adversary.getDeanonymizer();
                finalOcTable = multiDeanonymizer.getOcTable();
                deanonymizer = multiDeanonymizer;
                break;
            default:
                throw new PssException(String.format("psstype %s not matched", pssDimensionType.toString()));
        }

        int totalDecodedValue = 0;

        for (FinalReport finalReport : finalReports) {
            if (leakedReports.contains(finalReport)) {
                DeanonymizationResult deanonymizationResult = deanonymizer.deanonymize(finalReport);
                totalDecodedValue = deanonymizationResult.getTotalDecoded();
                OcTable ocTable = deanonymizationResult.getOcTable();
                ocTableSaver.saveOcTable(ocTable);
                finalOcTable = ocTable.cloneOcTable();
            } else {
                ocTableSaver.saveOcTable(finalOcTable);
            }
        }
        int ownReportsByAdversary = AdversaryReportCounter.getOwnReportsByAdversary(adversary);
        int ownReportedValueByAdversary = AdversaryReportCounter.getOwnReportedValueByAdversary(adversary);

        return new AdversaryResultWithSingleOcTable(ownReportsByAdversary, ownReportedValueByAdversary, leakedReports.size(), totalDecodedValue, leakedReports, finalOcTable);
    }
}
