package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface AdversaryResultWithIntermediateDecodabilityResultPrintable {
    void printAdversaryResultWithIntermediateDecodability(List<FullCycleResult> fullCycleResults, PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResultWithSingleOcTable, IntermediatePointDecodabilityResults decodabilityResultsMap, String resultDirectory) throws IOException, PssException, URISyntaxException, ClassNotFoundException;
}
