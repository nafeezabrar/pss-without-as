package pss.factory.report.generation.anonymizer;


import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

public interface AnonymizerSelectorFactory {
    AnonymizerSelector generateAnonymizerChooser(UserGroupMapper userGroupMapper, AnonymizerGroupMapper anonymizerGroupMapper);
}
