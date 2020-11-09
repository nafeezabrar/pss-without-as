package pss.result.presentable.printing;

import pss.config.printing.DefaultPrintingFileName;
import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.DecodabilityResult;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.decodability.LocationDecodabilityResult;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.writing.textfile.TextFileMeanValueOfUnequalSizeWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static pss.data.decodability.DecodabilityType.LOCATION;

public class TextFileAdversaryResultWithIntermediateDecodabilityPrinter implements AdversaryResultWithIntermediateDecodabilityResultPrintable {
    protected final String resultDirectory;

    public TextFileAdversaryResultWithIntermediateDecodabilityPrinter(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    @Override
    public void printAdversaryResultWithIntermediateDecodability(List<FullCycleResult> fullCycleResults, PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResultWithSingleOcTable, IntermediatePointDecodabilityResults decodabilityResultMaps, String resultDirectory) throws IOException, PssException, URISyntaxException {
        Set<DecodabilityType> decodabilityTypes = decodabilityResultMaps.getDecodabilityTypes();

        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {

                case FULL:
                    break;
                case PARTIAL:
                    break;
                case LOCATION:
                    printLocationIntermediateDecodabilities(fullCycleResults, pssVariables, decodabilityResultMaps);
            }
        }
    }

    private void printLocationIntermediateDecodabilities(List<FullCycleResult> fullCycleResults, PssVariables pssVariables, IntermediatePointDecodabilityResults decodabilityResultMap) throws IOException, PssException, URISyntaxException {
        Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps = decodabilityResultMap.getDecodabilityResultMaps();
        List<Double> decodabilities = getDecodabilityResults(decodabilityResultMaps);
        String resultFileName = DefaultPrintingFileName.getIntermediateAdversaryDecodabilityResultFileName(resultDirectory);
        TextFileMeanValueOfUnequalSizeWriter valueWriter = new TextFileMeanValueOfUnequalSizeWriter(resultFileName);
        valueWriter.writeMeanValues(decodabilities);
    }

    private List<Double> getDecodabilityResults(Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps) {
        Set<FullCycleResult> fullCycleResults = decodabilityResultMaps.keySet();
        List<FullCycleResult> sortedFullCycleResults = new ArrayList<>();
        sortedFullCycleResults.addAll(fullCycleResults);
        sortedFullCycleResults.sort(new Comparator<FullCycleResult>() {
            @Override
            public int compare(FullCycleResult o1, FullCycleResult o2) {
                int r1 = o1.getFinalReport().getId();
                int r2 = o2.getFinalReport().getId();
                return Integer.compare(r1, r2);
            }
        });
        List<Double> decodabilityResults = new ArrayList<>();
        for (FullCycleResult fullCycleResult : sortedFullCycleResults) {
            LocationDecodabilityResult decodabilityResult = (LocationDecodabilityResult) decodabilityResultMaps.get(fullCycleResult).get(LOCATION);
            decodabilityResults.add(decodabilityResult.getDecodability());
        }
        return decodabilityResults;
    }
}
