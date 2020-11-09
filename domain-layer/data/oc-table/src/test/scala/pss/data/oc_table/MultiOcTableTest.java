package pss.data.oc_table;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pss.data.dimension.Dimension;
import pss.data.valuemap.Value;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MultiOcTableTest {
    private MultiOcTable multiOcTable;
    private String fileName = "octable";
    private Value value = new Value(10);
    private MultiOcRow multiOcRow;
    private Map<Integer, OcCell> d1ocCellMap;
    private Map<Integer, OcCell> d2ocCellMap;
    private OcCell d1OcCellOne, d1OcCellTwo, d2OcCellOne, d2OcCellTwo;
    private Dimension location, product;
    private Set<Dimension> dimensionSet;
    private int d1ooiOneId = 15;
    private int d1OoiTwoId = 20;
    private int d1OcCountOne = 2;
    private int d1OcCountTwo = 3;

    private int d2ooiOneId = 5;
    private int d2OoiTwoId = 2;
    private int d2OcCountOne = 1;
    private int d2OcCountTwo = 7;

    @Before
    public void Setup() {
        initMultiOcTable();
    }

    @Test
    public void testEmptyOcTableSerializability() throws Exception {
        testOcTableSerializability(multiOcTable);
    }

    @Test
    public void testNonEmptyOcTableSerializability() throws Exception {
        addRowInMultiOcTable();
        testOcTableSerializability(multiOcTable);
    }

    @After
    public void Teardown() {
        clearFile();
    }

    private void assertIsEqual(MultiOcTable actual, MultiOcTable expected) {
        Map<Value, MultiOcRow> actualOcRowMap = actual.getOcRowMap();
        Map<Value, MultiOcRow> expectedOcRowMap = expected.getOcRowMap();


        assertEquals(expected.ocRowMap.get(new Value(10)), actual.ocRowMap.get(new Value(10)));
        assertEquals(expected.getDimensions(), actual.getDimensions());

    }

    private void testOcTableSerializability(MultiOcTable given) throws IOException, ClassNotFoundException {

        writeMultiOcTable(given);
        MultiOcTable found = readMultiOcTable();
        assertIsEqual(found, given);
    }

    private void addRowInMultiOcTable() {
        Map<Value, MultiOcRow> ocRowMap = multiOcTable.getOcRowMap();
        multiOcRow = new MultiOcRow(this.value, dimensionSet);
        Map<Dimension, Map<Integer, OcCell>> ocCellMap = multiOcRow.getOcCellMap();
        d1ocCellMap = new HashMap<>();
        d1OcCellOne = new OcCell(d1ooiOneId);
        d1OcCellTwo = new OcCell(d1OoiTwoId);
        d1OcCellOne.setOcCount(d1OcCountOne);
        d1OcCellTwo.setOcCount(d1OcCountTwo);
        d1ocCellMap.put(d1ooiOneId, d1OcCellOne);
        d1ocCellMap.put(d1OoiTwoId, d1OcCellTwo);
        ocCellMap.put(location, d1ocCellMap);

        d2ocCellMap = new HashMap<>();
        d2OcCellOne = new OcCell(d2ooiOneId);
        d2OcCellTwo = new OcCell(d2OoiTwoId);
        d2OcCellOne.setOcCount(d2OcCountOne);
        d2OcCellTwo.setOcCount(d2OcCountTwo);
        d2ocCellMap.put(d2ooiOneId, d2OcCellOne);
        d2ocCellMap.put(d2OoiTwoId, d2OcCellTwo);
        ocCellMap.put(location, d2ocCellMap);

        multiOcRow.incrementTotalReportCount();
        ocRowMap.put(this.value, multiOcRow);
    }

    private void clearFile() {
        File file = new File(fileName);
        file.delete();
    }

    private MultiOcTable readMultiOcTable() throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (MultiOcTable) objectInputStream.readObject();
            }
        }
    }

    private void writeMultiOcTable(MultiOcTable multiOcTable) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName))) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(multiOcTable);
            }
        }
    }

    private void initMultiOcTable() {
        location = new Dimension(1, "location", true);
        product = new Dimension(2, "product", false);
        dimensionSet = new HashSet<>();
        dimensionSet.add(location);
        dimensionSet.add(product);
        multiOcTable = new MultiOcTable(2, dimensionSet);
    }
}