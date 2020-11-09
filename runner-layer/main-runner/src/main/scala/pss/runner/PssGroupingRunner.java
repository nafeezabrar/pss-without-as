package pss.runner;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.mapping.ooi_group.OoiGroupMapperCreator;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.Set;

public class PssGroupingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory;

    public PssGroupingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.ooiGroupMapperCreatorFactory = ooiGroupMapperCreatorFactory;
    }

    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        OoiGroupMapperCreator ooiGroupMapperCreator = ooiGroupMapperCreatorFactory.generateOoiGroupMapperCreator();
        ooiGroupMapperCreator.generateOoiGroupMapper(pssType, pssGroups, pssVariables);
    }
}
