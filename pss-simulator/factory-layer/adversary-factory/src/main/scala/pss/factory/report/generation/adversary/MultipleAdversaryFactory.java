package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.data.pss_type.PssType;
import pss.data.user.User;
import pss.exception.PssException;
import pss.report.finalreport.FinalReport;

import java.util.List;
import java.util.Set;

public interface MultipleAdversaryFactory {
    List<Adversary> createAdversaries(List<FinalReport> finalReports, Set<User> users, ReportFilteringConfig reportFilteringConfig, PssType pssType) throws PssException;
}
