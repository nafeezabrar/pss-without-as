package pss.domain.utils;

import org.junit.Before;
import org.junit.Test;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.valuemap.SingleValueMap;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SinglePssUtilsTest {
    private SinglePssUtils pssUtils;

    @Before
    public void setUp() throws Exception {
        pssUtils = new SinglePssUtils();
    }

    @Test
    public void testCountTotalOoisForSingleDimension() throws Exception {
        Set<Ooi> ooiSet = new HashSet<>();
        ooiSet.add(new Ooi(1, "ab"));
        ooiSet.add(new Ooi(2, "ab"));
        ooiSet.add(new Ooi(3, "ab"));

        SingleOoiCollection ooiCollection = new SingleOoiCollection(ooiSet);
        SinglePssVariables pssVariables = new SinglePssVariables(ooiCollection, mock(SingleValueMap.class));
        int totalOois = pssUtils.countTotalOois(pssVariables);
        assertEquals(3, totalOois);
    }


}