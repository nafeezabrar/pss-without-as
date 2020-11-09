package pss.runner;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_group.OoiGroupMapperCreator;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.ObservedReportSetGenerable;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.Set;

public class ObservedReportGeneratingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final UserGenerable userGenerator;
    protected final UserGroupable userGrouper;
    protected final ObservedReportSetGenerable reportGenerator;
    protected final OoiUserGroupMapperFactory ooiUserGroupMapperFactory;
    protected final OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory;


    public ObservedReportGeneratingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory, UserGenerable userGenerator, UserGroupable userGrouper, OoiUserGroupMapperFactory ooiUserGroupMapperFactory, ObservedReportSetGenerable reportGenerator) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.userGenerator = userGenerator;
        this.userGrouper = userGrouper;
        this.reportGenerator = reportGenerator;
        this.ooiUserGroupMapperFactory = ooiUserGroupMapperFactory;
        this.ooiGroupMapperCreatorFactory = ooiGroupMapperCreatorFactory;
    }

    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        OoiGroupMapperCreator ooiGroupMapperCreator = ooiGroupMapperCreatorFactory.generateOoiGroupMapperCreator();
        OoiGroupMapper ooiGroupMapper = ooiGroupMapperCreator.generateOoiGroupMapper(pssType, pssGroups, pssVariables);
        UserMapper userMapper = userGenerator.generateUsers();
        UserGroupMapper userGroupMapper = userGrouper.assignGroup(userMapper.getUsers(), pssGroups);
        OoiUserGroupMappable ooiUserGroupMapper = ooiUserGroupMapperFactory.createOoiUserGroupMapper(pssType, pssGroups, userMapper, ooiGroupMapper, userGroupMapper);
        reportGenerator.generateObservedReports(pssVariables.getValueMap(), ooiUserGroupMapper);
    }
}
