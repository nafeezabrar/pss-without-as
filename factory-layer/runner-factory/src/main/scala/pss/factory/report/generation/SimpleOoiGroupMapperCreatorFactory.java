package pss.factory.report.generation;

import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.mapping.ooi_group.OoiGroupMapperCreator;
import pss.mapping.ooi_group.SimpleOoiGroupMapper;
import pss.mapping.ooi_group.SimpleOoiGroupMapperWithPrinter;
import pss.result.presentable.printing.HtmlOoiGroupMapperPrinter;

import static pss.config.ConfigKeyString.FilePaths.ooiMapperOutputFileName;

public class SimpleOoiGroupMapperCreatorFactory implements OoiGroupMapperCreatorFactory {
    protected final boolean shouldPrintOoiGroupMapper;

    public SimpleOoiGroupMapperCreatorFactory(boolean shouldPrintOoiGroupMapper) {
        this.shouldPrintOoiGroupMapper = shouldPrintOoiGroupMapper;
    }

    @Override
    public OoiGroupMapperCreator generateOoiGroupMapperCreator() {
        OoiGroupMapperCreator ooiGroupMapper;
        ooiGroupMapper = new SimpleOoiGroupMapper();
        if (shouldPrintOoiGroupMapper) {
            ooiGroupMapper = new SimpleOoiGroupMapperWithPrinter(ooiGroupMapper, new HtmlOoiGroupMapperPrinter(ooiMapperOutputFileName));
        }
        return ooiGroupMapper;
    }
}
