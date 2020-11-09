package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import pss.adversary.report_filtering.ReportFilter;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.data.pss_type.PssType;
import pss.data.user.User;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.library.combinatorics.CombinatoricsUtility;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleSetTwoApsAdversaryFactory implements MultipleAdversaryFactory {

    protected final ReportFilter reportFilter;
    protected final DeanonymizationMethod deanonymizationMethod;
    protected final MyRandom myRandom;

    public SimpleSetTwoApsAdversaryFactory(ReportFilter reportFilter, DeanonymizationMethod deanonymizationMethod, MyRandom myRandom) {
        this.reportFilter = reportFilter;
        this.deanonymizationMethod = deanonymizationMethod;
        this.myRandom = myRandom;
    }

    @Override
    public List<Adversary> createAdversaries(List<FinalReport> finalReports, Set<User> users, ReportFilteringConfig reportFilteringConfig, PssType pssType) throws PssException {

        Set<Integer> userIdSet = new HashSet<>();
        for (User user : users) {
            userIdSet.add(user.getId());
        }

        List<Adversary> adversaries = new ArrayList<>();
        Set<Set<Integer>> combinations = CombinatoricsUtility.getCombinations(userIdSet, 2);

        for (Set<Integer> combination : combinations) {
            List<Adversary> specificAdversaries = MultiUserAdversaryFactoryWithReportFilter.generateAdversariesWithReportFilter(pssType, reportFilteringConfig, combination, deanonymizationMethod, finalReports, myRandom, users, users);

            adversaries.addAll(specificAdversaries);
        }
        return adversaries;
    }
}
