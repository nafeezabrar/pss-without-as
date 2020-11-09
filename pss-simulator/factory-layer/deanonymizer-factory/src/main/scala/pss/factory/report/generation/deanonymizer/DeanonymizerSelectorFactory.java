package pss.factory.report.generation.deanonymizer;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

public interface DeanonymizerSelectorFactory {
    DeanonymizerSelector generateDeanonymizerChooser(UserGroupMapper userGroupMapper, DeanonymizerGroupMapper deanonymizerGroupMapper);
}
