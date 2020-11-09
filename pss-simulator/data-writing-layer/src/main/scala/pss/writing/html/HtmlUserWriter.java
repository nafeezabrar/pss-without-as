package pss.writing.html;

import pss.conversion.data_to_result.tabular.UserToTabularResultConverter;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.mapper.user.UserMapper;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlUserWriter {
    public static void writeUserInHtml(String fileName, UserMapper userMapper) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        UserToTabularResultConverter resultConverter = new UserToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateUserTabularResult(userMapper);
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
