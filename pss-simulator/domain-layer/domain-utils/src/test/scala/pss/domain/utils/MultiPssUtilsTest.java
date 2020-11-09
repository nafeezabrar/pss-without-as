package pss.domain.utils;

import org.junit.Before;
import org.junit.Test;
import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.valuemap.MultiValueMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MultiPssUtilsTest {
    private MultiPssUtils pssUtils;

    @Before
    public void setUp() throws Exception {
        pssUtils = new MultiPssUtils();
    }

    @Test
    public void testCountTotalOoisForMultiDimension() throws Exception {
        Map<Dimension, Set<Ooi>> ooiSets = new HashMap<>();
        Set<Ooi> ooiSetD1 = new HashSet<>();
        ooiSetD1.add(new Ooi(1, "ab"));
        ooiSetD1.add(new Ooi(2, "ab"));
        ooiSetD1.add(new Ooi(3, "ab"));
        ooiSets.put(new Dimension(1, "loc", false), ooiSetD1);
        Set<Ooi> ooiSetD2 = new HashSet<>();
        ooiSetD2.add(new Ooi(1, "ab"));
        ooiSetD2.add(new Ooi(2, "ab"));
        ooiSets.put(new Dimension(2, "loc", false), ooiSetD2);
        MultiOoiCollection ooiCollection = new MultiOoiCollection(ooiSets);
        int totalOois = pssUtils.countTotalOois(new MultiPssVariables(ooiCollection, mock(MultiValueMap.class)));
        assertEquals(6, totalOois);
    }

}