package pss.runner.result;

import pss.library.statistics.StatisticsUtility;
import pss.reader.text.TextFileReader;
import pss.runner.PssRunnable;

import java.util.List;

public class MeanStdResultFilteringRunner implements PssRunnable {
    protected final String fileName;

    public MeanStdResultFilteringRunner(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() throws Exception {
        TextFileReader textFileReader = new TextFileReader(fileName);
        List<Double> outputNumbers = textFileReader.readDoubles();
        double mean = StatisticsUtility.calculateMean(outputNumbers);
        double std = StatisticsUtility.calculateStd(outputNumbers);
        System.out.printf("mean = %.3f", mean);
        System.out.println();
        System.out.printf("STD = %.3f", std);
    }
}
