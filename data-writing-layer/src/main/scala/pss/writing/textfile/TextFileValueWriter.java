package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TextFileValueWriter<TValueType, TIterationValueType> {
    public void writeValueInTextFile(String fileName, TValueType value, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        List<String> values = new ArrayList<>();
        values.add(String.valueOf(value));
        TextResult textResult = new TextResult(values);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }

    public void writeValuesInTextFile(String fileName, TIterationValueType iterationValue, List<TValueType> values, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        List<String> results = new ArrayList<>();
        results.add(String.valueOf(iterationValue));
        for (TValueType value : values) {
            results.add(String.valueOf(value));
        }
        TextResult textResult = new TextResult(results);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }

    public void writeValuesInTextFile(String fileName, List<TValueType> values, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        List<String> results = new ArrayList<>();
        for (TValueType value : values) {
            results.add(String.valueOf(value));
        }
        TextResult textResult = new TextResult(results);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
