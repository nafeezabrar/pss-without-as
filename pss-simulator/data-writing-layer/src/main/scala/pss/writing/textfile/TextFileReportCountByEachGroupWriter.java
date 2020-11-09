package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.library.FileUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static pss.config.printing.ShouldPrintConfig.AppendMode.NOT_APPEND;

public class TextFileReportCountByEachGroupWriter {
    public static void writeReportCountOfEachGroup(String fileName, Map<PssGroup, Integer> reportCountMap) throws IOException, URISyntaxException, PssException {
        Set<PssGroup> pssGroups = reportCountMap.keySet();
        List<PssGroup> sortedPssGroups = new ArrayList<>(pssGroups);
        sortedPssGroups.sort((p1, p2) -> Integer.compare(p1.getPssGroupId(), p2.getPssGroupId()));
        int iteration = 0;
        List<Double> reportCounts;
        TextFileValueWriter textFileValueWriter = new TextFileValueWriter();
        if (FileUtility.fileExists(fileName)) {
            File file = new File(fileName);
            iteration = getIteration(file);
            reportCounts = updateReportCount(reportCountMap, file, sortedPssGroups, iteration);
            file.delete();
        } else {
            reportCounts = getReportCount(reportCountMap, sortedPssGroups);

        }
        textFileValueWriter.writeValuesInTextFile(fileName, iteration+1, reportCounts, NOT_APPEND);
    }

    private static int getIteration(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        return scanner.nextInt();
    }

    private static List<Double> getReportCount(Map<PssGroup, Integer> reportCountMap, List<PssGroup> sortedPssGroups) {
        List<Double> reportCounts = new ArrayList<>();
        for (PssGroup pssGroup : sortedPssGroups) {
            double reportCount = reportCountMap.get(pssGroup);
            reportCounts.add(reportCount);
        }
        return reportCounts;
    }

    private static List<Double> updateReportCount(Map<PssGroup, Integer> reportCountMap, File file, List<PssGroup> sortedPssGroups, int iteration) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.nextInt(); //refactor it later
        List<Double> updatedReportCounts = new ArrayList<>();
        int currentGroupIndex = 0;
        while (scanner.hasNext()) {
            double previousReportCount = scanner.nextDouble();
            double newReportCount = reportCountMap.get(sortedPssGroups.get(currentGroupIndex));
            double totalReportCount = previousReportCount * iteration + newReportCount;
            double mean = totalReportCount / (iteration + 1);
            updatedReportCounts.add(mean);
        }
        return updatedReportCounts;
    }
}
