package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import com.pss.adversary.SingleUserApsAdversaryType;
import com.pss.adversary.TargetUserApsAdversary;
import pss.adversary.report_filtering.ReportFilter;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SingleUserApsAdversaryFactory implements AdversaryFactory {

    protected final int userId;
    protected final ReportFilter reportFilter;
    protected final Deanonymizable deanonymizer;
    protected final ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod;

    public SingleUserApsAdversaryFactory(int userId, ReportFilter reportFilter, Deanonymizable deanonymizer, ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod) {
        this.userId = userId;
        this.reportFilter = reportFilter;
        this.deanonymizer = deanonymizer;
        this.apsAdversaryReportFilteringMethod = apsAdversaryReportFilteringMethod;
    }

    public Adversary createAdversary(List<FinalReport> finalReports, Set<User> users) {
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        SingleUserApsAdversaryType apsAdversaryType = new SingleUserApsAdversaryType(userId);
        return new ApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod);
    }

    public Adversary createTargetUserAdversary(int targetUserId, List<FinalReport> finalReports, Set<User> users) {
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        SingleUserApsAdversaryType apsAdversaryType = new SingleUserApsAdversaryType(userId);
        List<FinalReport> reportsByTargetUser = new ArrayList<>();
        for(FinalReport finalReport: finalReports) {
            if(finalReport.getReportData().getUserId() == targetUserId)
                reportsByTargetUser.add(finalReport);
        }
        return new TargetUserApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod, targetUserId, reportsByTargetUser);
    }
}
