package pss.result.presentable.printing;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface OoiGroupMapperPrintable {
    void printOoiGroupMapper(PssType pssType, PssVariables pssVariables, OoiGroupMapper ooiGroupMapper) throws URISyntaxException, PssException, IOException;
}
