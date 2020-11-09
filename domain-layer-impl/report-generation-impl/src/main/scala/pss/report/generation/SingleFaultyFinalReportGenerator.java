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

import java.util.HashSet;
import java.util.Set;

public class SingleFaultyFinalReportGenerator implements FinalReportInheritedGenerable<SingleLocalOoiCombination, SingleAnonymizedLocalOoiSet, SingleAnonymity, SingleLocalOoiCollection> {
    protected final double probability;

    public SingleFaultyFinalReportGenerator(double probability) {
        this.probability = probability;
    }

    @Override
    public FinalReport generateFinalReport(int reportId, Set<SingleLocalOoiCollection> singleLocalOoiCollections, SingleLocalOoiCombination reportedOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, SingleAnonymity anonymity) throws PssException {
        Set<Integer> anonymizedIdSet = anonymizedLocalOoiSet.getAnonymizedIdSet();

        double random = Math.random() * 100;
        if (random < probability) {
            int reportedOoi = reportedOoiCombination.getLocalOoi();
            anonymizedIdSet.remove(reportedOoi);
            int dummyOoi = getAnotherOoi(reportedOoiCombination, singleLocalOoiCollections, anonymizedIdSet);
            if (dummyOoi >= 0)
                anonymizedIdSet.add(dummyOoi);
        }
        SingleLocalOoiCollection ooiIdCollection = new SingleLocalOoiCollection(anonymizedIdSet);
        SingleFinalReportData reportData = new SingleFinalReportData(user.getId(), ooiIdCollection);
        return new SingleFinalReport(reportId, value, reportData);
    }

    private int getAnotherOoi(SingleLocalOoiCombination reportedOoiCombination, Set<SingleLocalOoiCollection> localOoiCollection, Set<Integer> anonymizedIdSet) {
        Set<Integer> localOoiSet = new HashSet<>();
        for (SingleLocalOoiCollection ooiCollection : localOoiCollection) {
            localOoiSet.addAll(ooiCollection.getLocalOoiSet());
        }
        for (Integer included : anonymizedIdSet) {
            localOoiSet.remove(included);
        }
        localOoiSet.remove(reportedOoiCombination.getLocalOoi());
        if (localOoiSet.size() == 0) return -3;
        return localOoiSet.iterator().next();
    }

}
