package pss.decodability.calculation;

import com.pss.adversary.*;
import pss.data.decodability.DecodabilityType;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.OcTable;
import pss.data.oc_table.SingleOcRow;
import pss.data.ooi.LocationOoi;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.decodability.LocationDecodabilityResult;
import pss.decodability.NullTargetUserLocationDecodabilityResult;
import pss.decodability.SingleTargetUserLocationDecodabilityResult;
import pss.decodability.TargetUserLocationDecodabilityResult;
import pss.decodability.calculation.aps.AdversaryIntermediatePointsDecodabilityCalculable;
import pss.exception.PssException;
import pss.library.geometry.Point;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.reader.octable.OcTableReadable;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;

import java.io.IOException;
import java.util.*;

import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod.TARGET_USER_LIMITED_TIME;
import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod.TARGET_USER_REPORT;
import static com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import static java.lang.String.format;

public class SingleIntermediatePointApsAdversaryLocationDecodabilityCalculator implements AdversaryIntermediatePointsDecodabilityCalculable<LocationDecodabilityResult, AdversaryResultWithSingleOcTable> {
    protected final ApsAdversary apsAdversary;
    protected final ValueMap valueMap;
    protected final PssVariables pssVariables;
    protected final PssType pssType;
    protected final Set<PssGroup> pssGroups;
    protected final SingleOoiUserGroupMappable ooiUserGroupMapper;
    protected final List<ObservedReport> observedReports;
    protected final List<FinalReport> finalReports;
    protected final String resultDirectory;
    protected final OcTableReadable ocTableReader;
    private Set<LocationOoi> locationOois;
    private Map<Integer, ObservedReport> observedReportMap;
    private Map<Value, LocationOoi> locationValueMap;
    private Set<Integer> adversaryUserIds;
    private Map<Value, LocationOoi> reportedLocationsByAdversary;
    private Map<Integer, Set<LocationOoi>> probableOoisUnderLocalIdMap;
    private Map<LocationOoi, Map<LocationOoi, Double>> pairWiseDistanceMap;
    private Map<LocationOoi, Double> averageDistances;

    public SingleIntermediatePointApsAdversaryLocationDecodabilityCalculator(ApsAdversary apsAdversary, ValueMap valueMap, PssVariables pssVariables, PssType pssType, Set<PssGroup> pssGroups, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, List<FinalReport> finalReports, String resultDirectory, OcTableReadable ocTableReader) throws PssException {
        this.apsAdversary = apsAdversary;
        this.valueMap = valueMap;
        this.pssVariables = pssVariables;
        this.pssType = pssType;
        this.pssGroups = pssGroups;
        this.ooiUserGroupMapper = (SingleOoiUserGroupMappable) ooiUserGroupMapper;
        this.observedReports = observedReports;
        this.finalReports = finalReports;
        this.resultDirectory = resultDirectory;
        this.ocTableReader = ocTableReader;
        init();
    }

    private void init() throws PssException {
        initLocationOois();
        initLocationValueMap();
        initReportedByAdversaryMap();
        initObservedReportMap();
        initAdversaryUserIds();
        initProbableOoisUnderLocalIdMap();
        initPairWiseDistances();
        initAverageDistances();
    }

    private void initObservedReportMap() {
        observedReportMap = new HashMap<>();
        for (ObservedReport observedReport : observedReports) {
            observedReportMap.put(observedReport.getId(), observedReport);
        }
    }

    private void initReportedByAdversaryMap() {
        reportedLocationsByAdversary = new HashMap<>();
    }

    private void initLocationValueMap() {
        locationValueMap = new HashMap<>();
        Map<SingleOoiCombination, Value> values = valueMap.getValues();
        for (SingleOoiCombination ooiCombination : values.keySet()) {
            LocationOoi locationOoi = (LocationOoi) ooiCombination.getOoi();
            locationValueMap.put(values.get(ooiCombination), locationOoi);
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

//    }

    private LocationOoi getLocationOoi(ObservedReport observedReport) throws PssException {
        SingleLocalOoiCombination localOoiCombination = (SingleLocalOoiCombination) observedReport.getReportData().getLocalOoiCombination();
        int localLocationOoi = localOoiCombination.getLocalOoi();
        int userId = observedReport.getReportData().getUserId();
        return (LocationOoi) ooiUserGroupMapper.getOoi(localLocationOoi, userId);
    }

    @Override
    public LocationDecodabilityResult calculateDecodability(AdversaryResultWithSingleOcTable adversaryResult, FinalReport finalReport) throws PssException, IOException, ClassNotFoundException {
        Map<Value, LocationOoi> decodedLocationValues = new HashMap<>();
        OcTable adversaryOcTable = ocTableReader.readOcTable();
        Map<Value, SingleOcRow> ocRowMap = adversaryOcTable.getOcRowMap();
        updateReportedByAdversary(finalReport);
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


    private void updateReportedByAdversary(FinalReport finalReport) throws PssException {
        int userId = finalReport.getReportData().getUserId();
        ObservedReport observedReport = observedReportMap.get(finalReport.getId());
        if (adversaryUserIds.contains(userId)) {
            reportedLocationsByAdversary.put(finalReport.getValue(), getLocationOoi(observedReport));
        }
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

    private void initPairWiseDistances() {
        pairWiseDistanceMap = new HashMap<>();

        for (LocationOoi location1 : locationOois) {
            Map<LocationOoi, Double> pairWiseDistances = new HashMap<>();
            for (LocationOoi location2 : locationOois) {
                Point point1 = location1.getPoint();
                Point point2 = location2.getPoint();
                pairWiseDistances.put(location2, point1.distanceFrom(point2));
            }
            pairWiseDistanceMap.put(location1, pairWiseDistances);
        }
    }

    private void initAverageDistances() {
        averageDistances = new HashMap<>();
        int totalLocations = locationOois.size();
        for (LocationOoi location : locationOois) {
            double totalDistance = 0.0;
            for (LocationOoi otherLocation : locationOois) {
                if (location.getId() != otherLocation.getId()) {
                    double pairWiseDistance = getDistance(location, otherLocation);
                    totalDistance += pairWiseDistance;
                }
            }
            double averageDistance = totalDistance / totalLocations;
            averageDistances.put(location, averageDistance);
        }
    }

    private double getDistance(LocationOoi location1, LocationOoi location2) {
        return pairWiseDistanceMap.get(location1).get(location2);
    }

    private void initLocationOois() throws PssException {
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        for (Dimension dimension : dimensionSet) {
            if (dimension.isLocationDimension()) {
                setLocationOois();
                return;
            }
        }
        throw new PssException(format("location decodability cant be calaculted if locaiton dimension not exists"));
    }

    private void setLocationOois() {
        locationOois = new HashSet<>();
        SingleOoiCollection ooiCollection = (SingleOoiCollection) pssVariables.getOoiCollection();
        Set<Ooi> oois = ooiCollection.getOoiSet();
        for (Ooi ooi : oois) {
            locationOois.add((LocationOoi) ooi);
        }
    }

    private void initProbableOoisUnderLocalIdMap() {
        probableOoisUnderLocalIdMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
            Map<Integer, Ooi> idToOoiMap = localOoiMapper.getIdToOoiMap();

            for (Integer localOoiId : idToOoiMap.keySet()) {
                LocationOoi ooi = (LocationOoi) idToOoiMap.get(localOoiId);
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

    private LocationOoi decodeLocationOoi(SingleOcRow singleOcRow) {
        Set<LocationOoi> probableLocationOois = getProbableLocationOois(singleOcRow);
        double minDistance = Integer.MAX_VALUE;
        LocationOoi decodedLocation = null;
        for (LocationOoi probableLocation : probableLocationOois) {
            double probableDistance = calculateDistanceForLocationOoi(probableLocation, probableLocationOois);
            if (probableDistance < minDistance) {
                minDistance = probableDistance;
                decodedLocation = probableLocation;
            }
        }
        return decodedLocation;
    }

    private double calculateDistanceForLocationOoi(LocationOoi probableLocation, Set<LocationOoi> probableLocationOois) {
        double totalDistance = 0;
        for (LocationOoi locationOoi : probableLocationOois) {
            double distance = getDistance(locationOoi, probableLocation);
            totalDistance += distance;
        }
        totalDistance /= probableLocationOois.size();
        double probableDistance = totalDistance / averageDistances.get(probableLocation);
        return probableDistance;
    }

    private Set<LocationOoi> getProbableLocationOois(SingleOcRow singleOcRow) {
        Map<Integer, OcCell> ocCellMap = singleOcRow.getOcCellMap();
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

    @Override
    public DecodabilityType getDecodabilityType() {
        return DecodabilityType.LOCATION;
    }
}
