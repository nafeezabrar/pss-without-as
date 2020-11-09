package pss.data.oc_table;

import org.junit.Test;
import pss.data.valuemap.Value;

import java.io.*;
import java.util.Map;

public class SingleOcTableTest {
    private SingleOcTable singleOcTable;
    private String fileName = "octable";
    private Value value = new Value(10);
    private SingleOcRow singleOcRow;
    private Map<Integer, OcCell> ocCellMap;
    private OcCell ocCellOne, ocCellTwo;
    private int ooiOneId = 15;
    private int ooiTwoId = 20;
    private int ocCountOne = 2;
    private int ocCountTwo = 3;
    private int totalReportCount = 1;

    @Test
    public void testSerializability() throws Exception {
        initSingleOcTable();
        testOcTableSerializability();
        clearFile();
        addRowInSingleOcTable();
        testOcTableSerializability();
    }

    private void testOcTableSerializability() throws IOException, ClassNotFoundException {
        writeSingleOcTable();
        testReadingSingleOcTable();
    }

    private void addRowInSingleOcTable() {
        Map<Value, SingleOcRow> ocRowMap = singleOcTable.getOcRowMap();
        singleOcRow = new SingleOcRow(this.value);

        ocCellMap = singleOcRow.getOcCellMap();
        ocCellOne = new OcCell(ooiOneId);
        ocCellTwo = new OcCell(ooiTwoId);
        ocCellOne.setOcCount(ocCountOne);
        ocCellTwo.setOcCount(ocCountTwo);
        ocCellMap.put(ooiOneId, ocCellOne);
        ocCellMap.put(ooiTwoId, ocCellTwo);
        singleOcRow.incrementTotalReportCount();
        ocRowMap.put(this.value, singleOcRow);
    }

    private void clearFile() {
        File file = new File(fileName);
        file.delete();
    }

    private void testReadingSingleOcTable() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SingleOcTable retrieved = (SingleOcTable) objectInputStream.readObject();
        System.out.println(retrieved.toString());
    }

    private void writeSingleOcTable() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(singleOcTable);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    private void initSingleOcTable() {
        singleOcTable = new SingleOcTable();
    }
}