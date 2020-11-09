package pss.report.generation;

import pss.data.pss_type.PssType;
import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.presentable.printing.ObservedReportPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ObservedReportSetGeneratorWithPrinter<TValueMap extends ValueMap, TObservedReport extends ObservedReport, TOoiUserGroupMappable extends OoiUserGroupMappable> implements ObservedReportSetGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> {
    protected final ObservedReportSetGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> observedReportGenerator;
    protected final ObservedReportPrintable observedReportPrinter;
    protected final PssType pssType;

    public ObservedReportSetGeneratorWithPrinter(ObservedReportSetGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> observedReportGenerator, ObservedReportPrintable observedReportPrinter, PssType pssType) {
        this.observedReportGenerator = observedReportGenerator;
        this.observedReportPrinter = observedReportPrinter;
        this.pssType = pssType;
    }

    @Override
    public List<TObservedReport> generateObservedReports(TValueMap valueMaps, TOoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException {
        List<TObservedReport> observedReports = observedReportGenerator.generateObservedReports(valueMaps, ooiUserGroupMapper);
        observedReportPrinter.printObservedReports((List<ObservedReport>) observedReports, pssType, ooiUserGroupMapper);
        return observedReports;
    }
}
