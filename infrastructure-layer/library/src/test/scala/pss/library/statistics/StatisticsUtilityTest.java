package pss.library.statistics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StatisticsUtilityTest {
    private ArrayList<Double> numbers;

    @Before
    public void setUp() throws Exception {
        numbers = new ArrayList<>();
        double[] numberArray = {9, 2, 5, 4, 12, 7};
        for (int i = 0; i < numberArray.length; i++) {
            numbers.add(numberArray[i]);
        }
    }

    @Test
    public void calculateMean() throws Exception {
        double mean = StatisticsUtility.calculateMean(numbers);
        assertEquals(mean, 6.5, 0.001);
    }

    @Test
    public void calculateStd() throws Exception {
        double std = StatisticsUtility.calculateStd(numbers);
        assertEquals(std, 3.619, 0.001);
    }

}