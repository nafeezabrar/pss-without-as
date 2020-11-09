package pss.factory.report.generation.task;

import pss.exception.RunnerCreationException;
import pss.factory.report.generation.SimpleOoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;

public class OoiGroupMapperFactoryCreator {
    public static OoiGroupMapperCreatorFactory createOoiGroupMapperFactory(boolean printOoiMapper) throws RunnerCreationException {
        return new SimpleOoiGroupMapperCreatorFactory(printOoiMapper);
    }
}
