package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import pss.adversary.report_filtering.ReportFilter;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.data.pss_type.PssType;
import pss.data.user.User;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleSetOneApsAdversaryFactory implements MultipleAdversaryFactory {

    protected final ReportFilter reportFilter;
    protected final DeanonymizationMethod deanonymizationMethod;
    protected final MyRandom myRandom;

    public SimpleSetOneApsAdversaryFactory(ReportFilter reportFilter, DeanonymizationMethod deanonymizationMethod, MyRandom myRandom) {
        this.reportFilter = reportFilter;
        this.deanonymizationMethod = deanonymizationMethod;
        this.myRandom = myRandom;
    }

    @Override
    public List<Adversary> createAdversaries(List<FinalReport> finalReports, Set<User> users, ReportFilteringConfig reportFilteringConfig, PssType pssType) throws PssException {

        List<Integer> userIdSet = new ArrayList<>();
        for (User user : users) {
            userIdSet.add(user.getId());
        }
        List<Adversary> adversaries = new ArrayList<>();
        userIdSet.sort((id1, id2) -> Integer.compare(id1, id2));
        for (int i = 0; i < userIdSet.size(); i++) {
            List<Adversary> specificAdversaires = SingleUserAdversaryFactoryWithReportFilter.generateAdversariesWithReportFilter(pssType, reportFilteringConfig, userIdSet.get(i), deanonymizationMethod, finalReports, this.myRandom, users, users);
            adversaries.addAll(specificAdversaires);
        }
        return adversaries;
    }

}
