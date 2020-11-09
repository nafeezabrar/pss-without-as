package pss.runner;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.mapper.user.UserMapper;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.Set;

public class UserGroupingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final UserGenerable userGenerator;
    protected final UserGroupable userGrouper;

    public UserGroupingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, UserGenerable userGenerator, UserGroupable userGrouper) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.userGenerator = userGenerator;
        this.userGrouper = userGrouper;
    }

    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        UserMapper userMapper = userGenerator.generateUsers();
        userGrouper.assignGroup(userMapper.getUsers(), pssGroups);
    }
}
