package pss.report.generation;

import pss.data.valuemap.ValueMap;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.saving.observed_report.ObservedReportSetSavable;

import java.util.List;

public class ObservedReportSetGeneratorWithSaver implements ObservedReportSetGenerable {
    protected final ObservedReportSetGenerable observedReportGenerator;
    protected final ObservedReportSetSavable observedReportSaver;

    public ObservedReportSetGeneratorWithSaver(ObservedReportSetGenerable observedReportGenerator, ObservedReportSetSavable observedReportSaver) {
        this.observedReportGenerator = observedReportGenerator;
        this.observedReportSaver = observedReportSaver;
    }

    @Override
    public List<ObservedReport> generateObservedReports(ValueMap valueMaps, OoiUserGroupMappable ooiUserGroupMapper) {
        throw new UnsupportedOperationException("observed report generation with printer not implemetned");
    }
}
