package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;

import java.io.IOException;
import java.util.List;

public interface AnonymizationResultPrintable {
    void printAnonymizationResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws IOException, PssException;
}
