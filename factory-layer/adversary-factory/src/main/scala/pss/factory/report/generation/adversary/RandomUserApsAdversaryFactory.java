package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.TargetUserApsAdversary;
import pss.adversary.report_filtering.ReportFilter;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomUserApsAdversaryFactory implements AdversaryFactory {

    protected final MyRandom myRandom;
    protected final int adversaryUserCount;
    protected final ReportFilter reportFilter;
    protected final Deanonymizable deanonymizer;
    protected final ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod;

    public RandomUserApsAdversaryFactory(MyRandom myRandom, int adversaryUserCount, ReportFilter reportFilter, Deanonymizable deanonymizer, ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod) {
        this.myRandom = myRandom;
        this.adversaryUserCount = adversaryUserCount;
        this.reportFilter = reportFilter;
        this.deanonymizer = deanonymizer;
        this.apsAdversaryReportFilteringMethod = apsAdversaryReportFilteringMethod;
    }


    public Adversary createAdversary(List<FinalReport> finalReports, Set<User> users) {
        Set<Integer> adversaryUserIds = createAdversaryUsers(users);
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        MultiUserApsAdversaryType apsAdversaryType = new MultiUserApsAdversaryType(adversaryUserIds);
        return new ApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod);
    }

    private Set<Integer> createAdversaryUsers(Set<User> users) {
        Set<Integer> adversaryUserIds = new HashSet<>();
        while (adversaryUserIds.size() != adversaryUserCount) {
            User user = myRandom.nextItem(users);
            adversaryUserIds.add(user.getId());
        }
        return adversaryUserIds;
    }

    @Override
    public Adversary createTargetUserAdversary(int targetUserId, List<FinalReport> finalReports, Set<User> users) {
        List leakedReports = reportFilter.getLeakedReports(finalReports);
        Set<Integer> adversaryUserIds = createAdversaryUsers(users);
        MultiUserApsAdversaryType apsAdversaryType = new MultiUserApsAdversaryType(adversaryUserIds);
        List<FinalReport> reportsByTargetUser = new ArrayList<>();
        for (FinalReport finalReport : finalReports) {
            if (finalReport.getReportData().getUserId() == targetUserId)
                reportsByTargetUser.add(finalReport);
        }
        return new TargetUserApsAdversary(deanonymizer, apsAdversaryType, leakedReports, finalReports, apsAdversaryReportFilteringMethod, targetUserId, reportsByTargetUser);
    }
}
