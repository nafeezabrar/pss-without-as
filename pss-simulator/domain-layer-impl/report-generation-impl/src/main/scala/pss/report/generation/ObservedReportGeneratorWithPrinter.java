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

public class ObservedReportGeneratorWithPrinter<TValueMap extends ValueMap, TObservedReport extends ObservedReport, TOoiUserGroupMappable extends OoiUserGroupMappable> implements ObservedReportGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> {
    protected final ObservedReportGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> observedReportGenerator;
    protected final ObservedReportPrintable observedReportPrinter;
    protected final PssType pssType;

    public ObservedReportGeneratorWithPrinter(ObservedReportGenerable<TValueMap, TObservedReport, TOoiUserGroupMappable> observedReportGenerator, ObservedReportPrintable observedReportPrinter, PssType pssType) {
        this.observedReportGenerator = observedReportGenerator;
        this.observedReportPrinter = observedReportPrinter;
        this.pssType = pssType;
    }


    @Override
    public TObservedReport generateObservedReport(int reportId, TValueMap valueMaps, TOoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException {
        throw new UnsupportedOperationException("observed report generation with printer not implemented");
    }
}
