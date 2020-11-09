package pss.deanonymizer.selection;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.exception.PssException;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

public class SimpleDeanonymizerSelector implements DeanonymizerSelector {
    protected final UserGroupMapper userGroupMapper;
    protected final DeanonymizerGroupMapper deanonymizerGroupMapper;

    public SimpleDeanonymizerSelector(UserGroupMapper userGroupMapper, DeanonymizerGroupMapper deanonymizerGroupMapper) {
        this.userGroupMapper = userGroupMapper;
        this.deanonymizerGroupMapper = deanonymizerGroupMapper;
    }

    @Override
    public Deanonymizable selectDeanonymizer(User user) throws PssException {
        PssGroup pssGroup = userGroupMapper.getPssGroup(user);
        return selectDeanonymizer(pssGroup);
    }

    @Override
    public Deanonymizable selectDeanonymizer(PssGroup pssGroup) throws PssException {
        return deanonymizerGroupMapper.getDeanonymizer(pssGroup);
    }
}
