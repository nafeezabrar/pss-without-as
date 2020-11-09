package pss.conversion.data_to_result;

import pss.mapper.user.UserMapper;
import pss.result.presentable.PresentableResult;

public interface UserToResultConvertable {
    PresentableResult generateUserTabularResult(UserMapper userMapper);
}
