package pss.library.statistics;

import java.util.List;

public class StatisticsUtility {
    public static double calculateMean(List<Double> numbers) {
        double total = 0.0;
        for (int i = 0; i < numbers.size(); i++) {
            total += numbers.get(i);
        }
        return total / numbers.size();
    }

    public static double calculateStd(List<Double> numbers) {
        double mean = calculateMean(numbers);
        double totalDeviations = 0.0;
        double deviation;
        for (int i = 0; i < numbers.size(); i++) {
            deviation = mean - numbers.get(i);
            totalDeviations += deviation * deviation;
        }
        double deviationPerNumber = totalDeviations / (numbers.size() - 1);
        return Math.sqrt(deviationPerNumber);
    }
}
