package pss.anonymizer.selection;

import pss.anonymization.Anonymizable;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

public class SimpleAnonymizerSelector implements AnonymizerSelector {
    protected final UserGroupMapper userGroupMapper;
    protected final AnonymizerGroupMapper anonymizerGroupMapper;

    public SimpleAnonymizerSelector(UserGroupMapper userGroupMapper, AnonymizerGroupMapper anonymizerGroupMapper) {
        this.userGroupMapper = userGroupMapper;
        this.anonymizerGroupMapper = anonymizerGroupMapper;
    }

    @Override
    public Anonymizable chooseAnonymizer(User user) throws PssException {
        PssGroup pssGroup = userGroupMapper.getPssGroup(user);
        return anonymizerGroupMapper.getAnoymizer(pssGroup);
    }
}
