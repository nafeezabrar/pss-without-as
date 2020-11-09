package pss.writing.textfile;

import pss.exception.PssException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pss.config.printing.ShouldPrintConfig.AppendMode.SHOULD_APPEND;

public class TextFileMeanValueOfUnequalSizeWriter<TValueType> {
    protected final String fileName;

    public TextFileMeanValueOfUnequalSizeWriter(String fileName) {
        this.fileName = fileName;
    }

    public void writeMeanValues(List<TValueType> values) throws IOException, URISyntaxException, PssException {
        File file = new File(fileName);
        int iteration = 0;
        boolean previousFileExists = false;
        List<Double> previousValues = new ArrayList<>();
        if (file.exists()) {
            previousFileExists = true;
            Scanner scanner = new Scanner(file);
            iteration = scanner.nextInt();
            while (scanner.hasNext()) {
                previousValues.add(scanner.nextDouble());
            }
            file.delete();
        }

        List<Double> updatedValues = new ArrayList<>();
        double previousValue = 0;
        if (previousFileExists) {
            int i;
            for (i = 0; i < values.size(); i++) {
                double total;
                if (i < previousValues.size()) {
                    previousValue = previousValues.get(i);
                    total = previousValue * iteration;
                } else {
                    total = previousValue * iteration;
                }
                TValueType newValue = values.get(i);

                total += convertToDouble(newValue);
                double decodability = total / (iteration + 1);
                updatedValues.add(decodability);
            }

            if (i < previousValues.size()) {
                TValueType lastCurrentValue = values.get(values.size() - 1);
                double lastCurrentDecodability = convertToDouble(lastCurrentValue);
                for (int j = i; j < previousValues.size(); j++) {
                    double total = previousValues.get(j) * iteration + lastCurrentDecodability;
                    double decodability = total / (iteration + 1);
                    updatedValues.add(decodability);
                }
            }

        } else {
            for (TValueType value : values) {
                updatedValues.add(convertToDouble(value));
            }
        }
        TextFileValueWriter valueWriter = new TextFileValueWriter();
        valueWriter.writeValuesInTextFile(fileName, iteration + 1, updatedValues, SHOULD_APPEND);
    }

    private Double convertToDouble(TValueType value) {
        return Double.valueOf(value.toString());
    }
}
