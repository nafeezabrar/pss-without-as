package pss.factory.report.generation.task;

import pss.exception.RunnerCreationException;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;

public class OoiUserGroupMapperFactoryCreator {
    public static OoiUserGroupMapperFactory createOoiUserGroupMapperFactory() throws RunnerCreationException {
        return new OoiUserGroupMapperFactory();
    }
}
