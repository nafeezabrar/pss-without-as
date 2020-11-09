package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.text.ApsDecodabilityResultToTextResultConverter;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileApsDecodabilityResultWriter {
    public static void writeApsDecodabilityInTextFile(String fileName, List<Double> decodabilities, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        File file = new File(fileName);
        int iteration = 1;
        boolean previousFileExists = false;
        List<Double> previousDecodabilities = new ArrayList<>();
        if (file.exists()) {
            previousFileExists = true;
            Scanner scanner = new Scanner(file);
            iteration = scanner.nextInt();
            while (scanner.hasNext()) {
                previousDecodabilities.add(scanner.nextDouble());
            }
            file.delete();
        }
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);

        ApsDecodabilityResultToTextResultConverter resultConverter = new ApsDecodabilityResultToTextResultConverter();
        List<Double> updatedDecodabilities = new ArrayList<>();
        if (previousFileExists) {
            int i;
            for (i = 0; i < decodabilities.size(); i++) {
                double total;
                if (i < previousDecodabilities.size()) {
                    total = previousDecodabilities.get(i) * iteration;
                } else {
                    total = 1 * iteration;
                }
                total += decodabilities.get(i);
                double decodability = total / (iteration + 1);
                updatedDecodabilities.add(decodability);
            }
            if (i < previousDecodabilities.size()) {
                for (int j = i; j < previousDecodabilities.size(); j++) {
                    double total = previousDecodabilities.get(j) * iteration + 1.0;
                    double decodability = total / (iteration + 1);
                    updatedDecodabilities.add(decodability);
                }
            }

        } else {
            updatedDecodabilities.addAll(decodabilities);
        }

        TextResult textResult = resultConverter.generateReportCountResult(updatedDecodabilities, iteration + 1);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
