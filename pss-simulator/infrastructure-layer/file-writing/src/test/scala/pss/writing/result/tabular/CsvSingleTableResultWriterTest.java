package pss.writing.result.tabular;

import org.junit.Before;
import org.junit.Test;
import pss.result.presentable.tabular.*;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class CsvSingleTableResultWriterTest {
    private CsvTabularResultWriter resultWriter;
    private StringWriter writer;
    private TabularResult tabularResult;
    private SingleTableResult singleTableResult;
    private String heading = "Html page HEADING";
    private String[] columnHeaders = {"Name", "Age", "Grade"};
    private String[] names = {"John", "Bob", "Alice"};
    private int[] ages = {29, 20, 10};
    private double[] marks = {3.2, 3.83, 4.0};
    private int totalRow = 3;

    @Before
    public void setUp() throws Exception {
        prepareResult();
        writer = new StringWriter();
        resultWriter = new CsvTabularResultWriter(writer);
    }

    private void prepareResult() {
        singleTableResult = new SingleTableResult(heading);

        TabularResultColumn[] columns = createColumns();

        for (TabularResultColumn column : columns)
            singleTableResult.addColumn(new SingleElementTabularResultColumnSet(column));

        for (int i = 0; i < totalRow; i++) {
            TabularResultRow row = new TabularResultRow();

            row.addColumn(columns[0], new SingleDataTabularResultCell<>(names[i]));
            row.addColumn(columns[1], new SingleDataTabularResultCell<>(ages[i]));
            row.addColumn(columns[2], new SingleDataTabularResultCell<>(marks[i]));

            singleTableResult.addRow(row);
        }
        tabularResult = new TabularResult("");
        tabularResult.addTableResult(singleTableResult);
    }

    private TabularResultColumn[] createColumns() {
        TabularResultColumn[] columns = new TabularResultColumn[columnHeaders.length];
        for (int i = 0; i < columnHeaders.length; i++)
            columns[i] = new TabularResultColumn(columnHeaders[i]);
        return columns;
    }

    @Test
    public void writeCsvCorrectly() throws Exception {
        resultWriter.writeResult(tabularResult);
        String actual = writer.toString();
        String expected = "Name,Age,Grade,\nJohn,29,3.2,\nBob,20,3.83,\nAlice,10,4.0,";
        assertEquals(expected, actual);
    }
}