package pss.writing.result.tabular;

import org.junit.Before;
import org.junit.Test;
import pss.result.presentable.tabular.*;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class HtmlSingleTableResultWriterTest {
    private HtmlTabularResultWriter resultWriter;
    private StringWriter writer;
    private TabularResult tabularResult;
    private SingleTableResult singleTableResult;
    private String heading = "HTML page heading";
    private String[] columnHeaders = {"Name", "Age", "Grade"};
    private String[] names = {"John", "Bob", "Alice"};
    private int[] ages = {29, 20, 10};
    private double[] marks = {3.2, 3.83, 4.0};
    private int totalRow = 3;

    @Before
    public void setUp() throws Exception {
        prepareResult();
        writer = new StringWriter();
        resultWriter = new HtmlTabularResultWriter(writer);
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
    public void writeHtmlCorrectly() throws Exception {
        resultWriter.writeResult(tabularResult);
        String actual = writer.toString();
        System.out.println(actual);
        String expected = "<html><style>table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse;\n" +
                "}</style><body><br>" + heading + "<br><table><tr><th>Name</th><th>Age</th><th>Grade</th></tr><tr><tr><td>John</td><td>29</td><td>3.2</td></tr></tr><tr><tr><td>Bob</td><td>20</td><td>3.83</td></tr></tr><tr><tr><td>Alice</td><td>10</td><td>4.0</td></tr></tr>" +
                "</table><br><br></body></html>";
        System.out.println(expected);
        assertEquals(expected, actual);
    }
}