package pss.report.generation;

import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;
import pss.report.finalreport.FinalReport;

import java.util.Set;

public interface FinalReportInheritedGenerable<TLocalOoiCombination extends LocalOoiCombination, TAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet, TAnonymity extends Anonymity, TLocalOoiCollection extends LocalOoiCollection> {
    FinalReport generateFinalReport(int reportId, Set<TLocalOoiCollection> localOoiCollections, TLocalOoiCombination reportedOoiCombination, TAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, TAnonymity anonymity) throws PssException;
}
