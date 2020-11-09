package pss.writing.textfile;

import pss.exception.PssException;
import pss.library.FileUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static pss.config.printing.ShouldPrintConfig.AppendMode.NOT_APPEND;

public class TextFileMeanValueOfEqualSizeWriter<TMapKeyType, TMapValueType, TKeyComparator extends Comparator> {
    protected final String fileName;
    protected final TKeyComparator keyComparator;

    public TextFileMeanValueOfEqualSizeWriter(String fileName, TKeyComparator keyComparator) {
        this.fileName = fileName;
        this.keyComparator = keyComparator;
    }

    public void writeReportCountOfEachGroup(Map<TMapKeyType, TMapValueType> valueMap) throws IOException, URISyntaxException, PssException {
        Set<TMapKeyType> keys = valueMap.keySet();
        List<TMapKeyType> sortedKeys = new ArrayList<>(keys);
        sortedKeys.sort(keyComparator);
        int iteration = 0;
        List<Double> valueList;
        TextFileValueWriter textFileValueWriter = new TextFileValueWriter();
        if (FileUtility.fileExists(fileName)) {
            File file = new File(fileName);
            iteration = getIteration(file);
            valueList = updateValueList(valueMap, file, sortedKeys, iteration);
            file.delete();
        } else {
            valueList = getValueList(valueMap, sortedKeys);

        }
        textFileValueWriter.writeValuesInTextFile(fileName, iteration + 1, valueList, NOT_APPEND);
    }

    private int getIteration(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        return scanner.nextInt();
    }

    private List<Double> getValueList(Map<TMapKeyType, TMapValueType> valueMap, List<TMapKeyType> sortedKeySet) {
        List<Double> values = new ArrayList<>();
        for (TMapKeyType key : sortedKeySet) {
            TMapValueType value = valueMap.get(key);
            values.add(convertToDouble(value));
        }
        return values;
    }

    private Double convertToDouble(TMapValueType value) {
        return Double.valueOf(value.toString());
    }

    private List<Double> updateValueList(Map<TMapKeyType, TMapValueType> valueMap, File file, List<TMapKeyType> sortedKeySet, int iteration) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.next(); //refactor it later
        List<Double> updatedMean = new ArrayList<>();
        int currentIndex = 0;
        while (scanner.hasNext()) {
            TMapValueType mapValue = valueMap.get(sortedKeySet.get(currentIndex));
            double newValue = convertToDouble(mapValue);
            currentIndex++;
            double previousValue = scanner.nextDouble();
            double totalValues = previousValue * iteration + newValue;
            double mean = totalValues / (iteration + 1);
            updatedMean.add(mean);
        }
        return updatedMean;
    }
}
