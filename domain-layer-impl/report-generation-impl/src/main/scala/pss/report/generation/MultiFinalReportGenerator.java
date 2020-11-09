package pss.report.generation;

import pss.data.anonymity.MultiAnonymity;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.finalreport.FinalReport;
import pss.report.finalreport.MultiFinalReport;
import pss.report.finalreport.MultiFinalReportData;

import java.util.Set;

public class MultiFinalReportGenerator implements FinalReportInheritedGenerable<MultiLocalOoiCombination, MultiAnonymizedLocalOoiSet, MultiAnonymity, MultiLocalOoiCollection> {

    @Override
    public FinalReport generateFinalReport(int reportId, Set<MultiLocalOoiCollection> multiLocalOoiCollections, MultiLocalOoiCombination reportedOoiCombination, MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, MultiAnonymity anonymity) throws PssException {
        MultiLocalOoiCollection ooiIdCollection = new MultiLocalOoiCollection(anonymizedLocalOoiSet.getAnonymizedOoiSets());
        MultiFinalReportData reportData = new MultiFinalReportData(user.getId(), ooiIdCollection);
        return new MultiFinalReport(reportId, value, reportData);
    }
}
