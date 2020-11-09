package pss.report.generation;

import pss.data.anonymity.SingleAnonymity;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.finalreport.FinalReport;
import pss.report.finalreport.SingleFinalReport;
import pss.report.finalreport.SingleFinalReportData;

import java.util.Set;

public class SingleFinalReportGenerator implements FinalReportInheritedGenerable<SingleLocalOoiCombination, SingleAnonymizedLocalOoiSet, SingleAnonymity, SingleLocalOoiCollection> {

    @Override
    public FinalReport generateFinalReport(int reportId, Set<SingleLocalOoiCollection> singleLocalOoiCollections, SingleLocalOoiCombination reportedOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, SingleAnonymity anonymity) throws PssException {
        SingleLocalOoiCollection ooiIdCollection = new SingleLocalOoiCollection(anonymizedLocalOoiSet.getAnonymizedIdSet());
        SingleFinalReportData reportData = new SingleFinalReportData(user.getId(), ooiIdCollection);
        return new SingleFinalReport(reportId, value, reportData);
    }
}
