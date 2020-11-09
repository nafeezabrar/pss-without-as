package pss.factory.report.generation.task;

import pss.anonymizer.selection.AnonymizerSelector;
import pss.anonymizer.selection.SimpleAnonymizerSelector;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.anonymizer.AnonymizerSelectorFactory;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

public class AnonymizerSelectorFactoryCreator {
    public static AnonymizerSelectorFactory generateAnonymizerChoosableFactory() throws RunnerCreationException {
        return new SimpleAnonymizerSelectorFactory();
    }

    private static class SimpleAnonymizerSelectorFactory implements AnonymizerSelectorFactory {

        @Override
        public AnonymizerSelector generateAnonymizerChooser(UserGroupMapper userGroupMapper, AnonymizerGroupMapper anonymizerGroupMapper) {
            return new SimpleAnonymizerSelector(userGroupMapper, anonymizerGroupMapper);
        }
    }
}
