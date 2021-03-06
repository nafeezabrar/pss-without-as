package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ObservedReportPrintable {
    void printObservedReports(List<ObservedReport> observedReportList, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException;
}
