package pss.conversion.data_to_result;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface OoiGroupMapperToResultConvertable {
    PresentableResult generateOoiGroupMapperResult(PssType pssType, PssVariables pssVariables, OoiGroupMapper ooiGroupMapper) throws PssException;
}
