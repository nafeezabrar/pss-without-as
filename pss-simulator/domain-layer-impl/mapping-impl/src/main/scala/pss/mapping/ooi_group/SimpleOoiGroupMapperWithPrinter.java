package pss.mapping.ooi_group;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.result.presentable.printing.OoiGroupMapperPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class SimpleOoiGroupMapperWithPrinter implements OoiGroupMapperCreator {
    protected final OoiGroupMapperCreator ooiGroupMapper;
    protected final OoiGroupMapperPrintable ooiGroupMapperPrinter;

    public SimpleOoiGroupMapperWithPrinter(OoiGroupMapperCreator ooiGroupMapper, OoiGroupMapperPrintable ooiGroupMapperPrinter) {
        this.ooiGroupMapper = ooiGroupMapper;
        this.ooiGroupMapperPrinter = ooiGroupMapperPrinter;
    }

    @Override
    public OoiGroupMapper generateOoiGroupMapper(PssType pssType, Set<PssGroup> pssGroups, PssVariables pssVariables) throws PssException, IOException, URISyntaxException {
        OoiGroupMapper ooiGroupMapper = this.ooiGroupMapper.generateOoiGroupMapper(pssType, pssGroups, pssVariables);
        ooiGroupMapperPrinter.printOoiGroupMapper(pssType, pssVariables, ooiGroupMapper);
        return ooiGroupMapper;
    }
}
