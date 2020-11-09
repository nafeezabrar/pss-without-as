package pss.factory.report.generation.task;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.deanonymizer.selection.SimpleDeanonymizerSelector;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.deanonymizer.DeanonymizerSelectorFactory;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

public class DeanonymizerSelectorFactoryCreator {
    public static DeanonymizerSelectorFactory generateDeanonymizerChoosableFactory() throws RunnerCreationException {
        return new SimpleDeanonymizerSelectorFactory();
    }

    private static class SimpleDeanonymizerSelectorFactory implements DeanonymizerSelectorFactory {

        @Override
        public DeanonymizerSelector generateDeanonymizerChooser(UserGroupMapper userGroupMapper, DeanonymizerGroupMapper deanonymizerGroupMapper) {
            return new SimpleDeanonymizerSelector(userGroupMapper, deanonymizerGroupMapper);
        }
    }
}
