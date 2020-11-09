package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.TargetUserApsAdversary;
import pss.adversary.report_filtering.ReportFilter;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MultiUserApsAdversaryFactory implements AdversaryFactory {

    protected final Set<Integer> userIds;
    protected final ReportFilter reportFilter;
    protected final Deanonymizable deanonymizer;
    protected final ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod;

    public MultiUserApsAdversaryFactory(Set<Integer> userIds, ReportFilter reportFilter, Deanonymizable deanonymizer, ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod) {
        this.userIds = userIds;
        this.reportFilter = reportFilter;
        this.deanonymizer = deanonymizer;
        this.apsAdversaryReportFilteringMethod = apsAdversaryReportFilteringMethod;
    }


    public Adversary createAdversary(List<FinalReport> finalReports, Set<User> users) {
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        MultiUserApsAdversaryType apsAdversaryType = new MultiUserApsAdversaryType(userIds);
        return new ApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod);
    }

    @Override
    public Adversary createTargetUserAdversary(int targetUserId, List<FinalReport> finalReports, Set<User> users) {
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        MultiUserApsAdversaryType apsAdversaryType = new MultiUserApsAdversaryType(userIds);
        List<FinalReport> reportsByTargetUser = new ArrayList<>();
        for(FinalReport finalReport: finalReports) {
            if(finalReport.getReportData().getUserId() == targetUserId)
                reportsByTargetUser.add(finalReport);
        }
        return new TargetUserApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod, targetUserId, reportsByTargetUser);
    }
}
