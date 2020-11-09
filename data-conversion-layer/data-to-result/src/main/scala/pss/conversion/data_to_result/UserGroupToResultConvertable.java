package pss.conversion.data_to_result;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface UserGroupToResultConvertable {
    PresentableResult generateUserGroupTabularResult(UserGroupMapper userGroupMapper) throws PssException;
}
