package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import pss.data.user.User;
import pss.report.finalreport.FinalReport;

import java.util.List;
import java.util.Set;

public interface AdversaryFactory {
    Adversary createAdversary(List<FinalReport> finalReports, Set<User> users);

    Adversary createTargetUserAdversary(int targetUserId, List<FinalReport> finalReports, Set<User> users);
}
