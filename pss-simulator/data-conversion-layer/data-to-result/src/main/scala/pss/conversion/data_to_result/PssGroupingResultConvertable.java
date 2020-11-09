package pss.conversion.data_to_result;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

import java.util.Set;

public interface PssGroupingResultConvertable {
    PresentableResult generatePssGroupingResult(PssType pssType, Set<PssGroup> pssGroups) throws PssException;
}
