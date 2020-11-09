package pss.writing.result.text;

import org.junit.Before;
import org.junit.Test;
import pss.result.presentable.text.TextResult;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextFileTextResultWriterTest {

    private TextResult textResult;
    private String s1, s2;
    private String expectedTextResult = String.format("%s\n%s\n", s1, s2);

    @Before
    public void setUp() throws Exception {
        List<String> stringList = new ArrayList<>();
        stringList.add(s1);
        stringList.add(s2);
        textResult = new TextResult(stringList);
    }

    @Test
    public void writeResult() throws Exception {
        Writer writer = new StringWriter();
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(writer);
        resultWriter.writeResult(textResult);
        assertEquals(expectedTextResult, writer.toString());
    }

}