package pss.report.generation;

import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ObservedReportGenerable<TValueMap extends ValueMap, TObservedReport extends ObservedReport, TOoiUserGroupMappable extends OoiUserGroupMappable> {
    TObservedReport generateObservedReport(int reportId, TValueMap valueMaps, TOoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException;
}
