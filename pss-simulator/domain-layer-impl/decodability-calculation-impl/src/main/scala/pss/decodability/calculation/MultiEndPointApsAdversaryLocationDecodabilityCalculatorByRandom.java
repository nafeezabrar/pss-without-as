package pss.decodability.calculation;

import com.pss.adversary.*;
import pss.data.decodability.DecodabilityType;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.OcTable;
import pss.data.ooi.LocationOoi;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.decodability.LocationDecodabilityResult;
import pss.decodability.NullTargetUserLocationDecodabilityResult;
import pss.decodability.SingleTargetUserLocationDecodabilityResult;
import pss.decodability.TargetUserLocationDecodabilityResult;
import pss.decodability.calculation.aps.AdversaryEndPointDecodabilityCalculable;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;

import java.util.*;

import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod.TARGET_USER_LIMITED_TIME;
import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod.TARGET_USER_REPORT;
import static com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import static java.lang.String.format;

public class MultiEndPointApsAdversaryLocationDecodabilityCalculatorByRandom implements AdversaryEndPointDecodabilityCalculable<LocationDecodabilityResult, AdversaryResultWithSingleOcTable> {
    protected final MyRandom myRandom;
    protected final ApsAdversary apsAdversary;
    protected final ValueMap valueMap;
    protected final PssVariables pssVariables;
    protected final PssType pssType;
    protected final Set<PssGroup> pssGroups;
    protected final MultiOoiUserGroupMappable ooiUserGroupMapper;
    protected final List<ObservedReport> observedReports;
    protected final List<FinalReport> finalReports;
    private final OcTable adversaryOcTable;

    private Dimension locationDimension;
    private Set<LocationOoi> locationOois;
    private Map<Value, LocationOoi> locationValueMap;
    private Set<Integer> adversaryUserIds;
    private Map<Value, LocationOoi> reportedLocationsByAdversary;
    private Map<Integer, Set<LocationOoi>> probableOoisUnderLocalIdMap;


    public MultiEndPointApsAdversaryLocationDecodabilityCalculatorByRandom(MyRandom myRandom, ApsAdversary apsAdversary, ValueMap valueMap, PssVariables pssVariables, PssType pssType, Set<PssGroup> pssGroups, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, List<FinalReport> finalReports, OcTable adversaryOcTable) {
        this.myRandom = myRandom;
        this.apsAdversary = apsAdversary;
        this.valueMap = valueMap;
        this.pssVariables = pssVariables;
        this.pssType = pssType;
        this.pssGroups = pssGroups;
        this.ooiUserGroupMapper = (MultiOoiUserGroupMappable) ooiUserGroupMapper;
        this.observedReports = observedReports;
        this.finalReports = finalReports;
        this.adversaryOcTable = adversaryOcTable;
    }

    @Override
    public DecodabilityType getDecodabilityType() {
        return DecodabilityType.LOCATION_RANDOM;
    }

    @Override
    public LocationDecodabilityResult calculateDecodability(AdversaryResultWithSingleOcTable adversaryResult) throws PssException {
        initLocationDimension();
        initLocationOois();
        initLocationValueMap();
        initAdversaryUserIds();
        initReportedLocationByAdversary(adversaryResult);
        initProbableOoisUnderLocalIdMap();
        return calculateLocationDecodability();
    }

    private void initLocationValueMap() {
        locationValueMap = new HashMap<>();
        Map<MultiOoiCombination, Value> values = valueMap.getValues();
        for (MultiOoiCombination ooiCombination : values.keySet()) {
            Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
            LocationOoi locationOoi = (LocationOoi) ooiMap.get(locationDimension);
            locationValueMap.put(values.get(ooiCombination), locationOoi);
        }
    }


    private void initLocationDimension() {
        for (Dimension dimension : pssType.getDimensionSet()) {
            if (dimension.isLocationDimension()) {
                locationDimension = dimension;
                return;
            }
        }
    }

    private void initAdversaryUserIds() {
        adversaryUserIds = new HashSet<>();
        ApsAdversaryType apsAdversaryType = apsAdversary.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                SingleUserApsAdversaryType singleUserApsAdversaryType = (SingleUserApsAdversaryType) apsAdversaryType;
                adversaryUserIds.add(singleUserApsAdversaryType.getUserId());
                break;
            case DISGUISED_AS_MULTI_USER:
                MultiUserApsAdversaryType multiUserApsAdversaryType = (MultiUserApsAdversaryType) apsAdversaryType;
                adversaryUserIds.addAll(multiUserApsAdversaryType.getUserIds());
        }
    }

    private void initReportedLocationByAdversary(AdversaryResultWithSingleOcTable adversaryResult) throws PssException {
        reportedLocationsByAdversary = new HashMap<>();
        for (int i = 0; i < finalReports.size(); i++) {
            FinalReport finalReport = finalReports.get(i);
            ObservedReport observedReport = observedReports.get(i);
            int userId = finalReport.getReportData().getUserId();
            if (adversaryUserIds.contains(userId)) {
                Value value = finalReport.getValue();
                LocationOoi locationOoi = getLocationOoi(observedReport);
                reportedLocationsByAdversary.put(value, locationOoi);
            }
        }
    }

    private LocationOoi getLocationOoi(ObservedReport observedReport) throws PssException {
        MultiLocalOoiCombination localOoiCombination = (MultiLocalOoiCombination) observedReport.getReportData().getLocalOoiCombination();
        Map<Dimension, Integer> localOoiMap = localOoiCombination.getLocalOoiMap();
        Integer localLocationOoi = localOoiMap.get(locationDimension);
        int userId = observedReport.getReportData().getUserId();
        LocationOoi locationOoi = (LocationOoi) ooiUserGroupMapper.getOoi(locationDimension, localLocationOoi, userId);
        return locationOoi;
    }

    private LocationDecodabilityResult calculateLocationDecodability() {
        Map<Value, LocationOoi> decodedLocationValues = new HashMap<>();
        Map<Value, MultiOcRow> ocRowMap = adversaryOcTable.getOcRowMap();
        int totalDecoded = 0;
        for (Value value : reportedLocationsByAdversary.keySet()) {
            totalDecoded++;
            decodedLocationValues.put(value, reportedLocationsByAdversary.get(value));
        }
        for (Value value : ocRowMap.keySet()) {
            if (!reportedLocationsByAdversary.containsKey(value)) {
                LocationOoi decodedLocationOoi = decodeLocationOoi(ocRowMap.get(value));
                if (isCorrectlyDecoded(decodedLocationOoi, value)) {
                    decodedLocationValues.put(value, decodedLocationOoi);
                    totalDecoded++;
                }
            }
        }
        int totalValue = valueMap.getValues().size();
        double decodability = (double) totalDecoded / totalValue;
        TargetUserLocationDecodabilityResult targetUserLocationDecodabilityResult = computeTargetUserLocationDecodability(decodedLocationValues);
        return new LocationDecodabilityResult(decodability, decodedLocationValues, targetUserLocationDecodabilityResult);
    }

    private TargetUserLocationDecodabilityResult computeTargetUserLocationDecodability(Map<Value, LocationOoi> decodedLocationValues) {
        ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod = apsAdversary.getApsAdversaryReportFilteringMethod();
        if (apsAdversaryReportFilteringMethod == TARGET_USER_REPORT || apsAdversaryReportFilteringMethod == TARGET_USER_LIMITED_TIME) {
            TargetUserApsAdversary targetUserApsAdversary = (TargetUserApsAdversary) apsAdversary;
            double decodabilityOfTarget = computeLocationDecodabilityOfTarget(targetUserApsAdversary, decodedLocationValues);
            return new SingleTargetUserLocationDecodabilityResult(decodabilityOfTarget);
        }
        return new NullTargetUserLocationDecodabilityResult();
    }


    private double computeLocationDecodabilityOfTarget(TargetUserApsAdversary targetUserApsAdversary, Map<Value, LocationOoi> decodedLocationValues) {
        Set<Value> valuesReportedByTarget = new HashSet<>();
        int targetUserId = targetUserApsAdversary.getTargetUserId();
        for (ObservedReport observedReport : observedReports) {
            if (observedReport.getReportData().getUserId() == targetUserId) {
                Value value = observedReport.getValue();
                valuesReportedByTarget.add(value);
            }
        }

        int totalLocationsVisitedByTargetUser = valuesReportedByTarget.size();
        int targetUserLocationdecodedByAdversary = 0;
        for (Value value : decodedLocationValues.keySet()) {
            if (valuesReportedByTarget.contains(value)) {
                targetUserLocationdecodedByAdversary++;
            }
        }
        double targetUserDecodability = targetUserLocationdecodedByAdversary / (double) (totalLocationsVisitedByTargetUser);
        return targetUserDecodability;

    }

    private void initLocationOois() throws PssException {
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        for (Dimension dimension : dimensionSet) {
            if (dimension.isLocationDimension()) {
                setLocationOois(dimension);
                return;
            }
        }
        throw new PssException(format("location decodability cant be calaculted if locaiton dimension not exists"));
    }

    private void setLocationOois(Dimension locationDimension) {
        locationOois = new HashSet<>();
        MultiOoiCollection ooiCollection = (MultiOoiCollection) pssVariables.getOoiCollection();
        Map<Dimension, Set<Ooi>> ooiSetMap = ooiCollection.getOoiSetMap();
        Set<Ooi> oois = ooiSetMap.get(locationDimension);
        for (Ooi ooi : oois) {
            locationOois.add((LocationOoi) ooi);
        }
    }

    private void initProbableOoisUnderLocalIdMap() {
        probableOoisUnderLocalIdMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            MultiLocalOoiMapper localOoiMapper = (MultiLocalOoiMapper) pssGroup.getLocalOoiMapper();
            Map<Dimension, Map<Integer, Ooi>> idToOoiMaps = localOoiMapper.getIdToOoiMaps();
            Map<Integer, Ooi> localOoiIds = idToOoiMaps.get(locationDimension);
            for (Integer localOoiId : localOoiIds.keySet()) {
                LocationOoi ooi = (LocationOoi) localOoiIds.get(localOoiId);
                if (probableOoisUnderLocalIdMap.containsKey(localOoiId)) {
                    Set<LocationOoi> oois = probableOoisUnderLocalIdMap.get(localOoiId);
                    oois.add(ooi);
                } else {
                    Set<LocationOoi> oois = new HashSet<>();
                    oois.add(ooi);
                    probableOoisUnderLocalIdMap.put(localOoiId, oois);
                }
            }
        }
    }

    private boolean isCorrectlyDecoded(LocationOoi decodedLocationOoi, Value value) {
        LocationOoi realLocationOoi = locationValueMap.get(value);
        if (realLocationOoi.getId() == decodedLocationOoi.getId())
            return true;
        return false;
    }

    private LocationOoi decodeLocationOoi(MultiOcRow multiOcRow) {
        Set<LocationOoi> probableLocationOois = getProbableLocationOois(multiOcRow);
        return myRandom.nextItem(probableLocationOois);
    }

    private Set<LocationOoi> getProbableLocationOois(MultiOcRow multiOcRow) {
        Map<Dimension, Map<Integer, OcCell>> ocCellMaps = multiOcRow.getOcCellMap();
        Map<Integer, OcCell> ocCellMap = ocCellMaps.get(locationDimension);
        int maxOcCount;
        maxOcCount = getMaxOcCount(ocCellMap);
        return getLocationOoisWithOcCount(ocCellMap, maxOcCount);
    }

    private int getMaxOcCount(Map<Integer, OcCell> ocCellMap) {
        int maxOcCount = 0;
        for (Integer localOoi : ocCellMap.keySet()) {
            OcCell ocCell = ocCellMap.get(localOoi);
            int ocCount = ocCell.getOcCount();
            if (ocCount > maxOcCount)
                maxOcCount = ocCount;
        }
        return maxOcCount;
    }

    private Set<LocationOoi> getLocationOoisWithOcCount(Map<Integer, OcCell> ocCellMap, int maxOcCount) {
        Set<Integer> localLocationOois = getLocalLocationOois(ocCellMap, maxOcCount);
        return getProbableLocationOois(localLocationOois);
    }

    private Set<LocationOoi> getProbableLocationOois(Set<Integer> localLocationOois) {
        Set<LocationOoi> locationOois = new HashSet<>();
        for (Integer localId : localLocationOois) {
            Set<LocationOoi> oois = probableOoisUnderLocalIdMap.get(localId);
            locationOois.addAll(oois);
        }
        return locationOois;
    }


    private Set<Integer> getLocalLocationOois(Map<Integer, OcCell> ocCellMap, int maxOcCount) {
        Set<Integer> localLocationOois = new HashSet<>();
        for (Integer localLocationOoi : ocCellMap.keySet()) {
            int ocCount = ocCellMap.get(localLocationOoi).getOcCount();
            if (ocCount == maxOcCount) {
                localLocationOois.add(localLocationOoi);
            }
        }
        return localLocationOois;
    }
}
