package pss.report.generation;

import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ObservedReportSetGenerable<TValueMap extends ValueMap, TObservedReport extends ObservedReport, TOoiUserGroupMappable extends OoiUserGroupMappable> {
    List<TObservedReport> generateObservedReports(TValueMap valueMaps, TOoiUserGroupMappable ooiUserGroupMapper) throws PssException, IOException, URISyntaxException, ReaderException;
}
