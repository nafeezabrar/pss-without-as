package pss.conversion.data_to_result;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.result.presentable.PresentableResult;

public interface UserGroupingToResultConvertable {
    PresentableResult generateUserGroupingResult(UserGroupMapper userGroupingMapper);
}
