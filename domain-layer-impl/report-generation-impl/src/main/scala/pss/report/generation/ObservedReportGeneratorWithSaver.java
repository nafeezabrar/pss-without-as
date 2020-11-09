package pss.report.generation;

import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.saving.observed_report.ObservedReportSavable;

import java.io.IOException;
import java.net.URISyntaxException;

public class ObservedReportGeneratorWithSaver implements ObservedReportGenerable {
    protected final ObservedReportGenerable observedReportGenerator;
    protected final ObservedReportSavable observedReportSaver;

    public ObservedReportGeneratorWithSaver(ObservedReportGenerable observedReportGenerator, ObservedReportSavable observedReportSaver) {
        this.observedReportGenerator = observedReportGenerator;
        this.observedReportSaver = observedReportSaver;
    }


    @Override
    public ObservedReport generateObservedReport(int reportId, ValueMap valueMaps, OoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException {
        throw new UnsupportedOperationException("observed report generation with printer not implemetned");
    }
}
