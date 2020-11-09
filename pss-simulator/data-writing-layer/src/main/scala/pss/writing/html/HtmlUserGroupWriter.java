package pss.writing.html;

import pss.conversion.data_to_result.tabular.UserGroupToTabularResultConverter;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlUserGroupWriter {
    public static void writeUserGroupInHtml(String fileName, UserGroupMapper userGroupMapper) throws IOException, URISyntaxException, PssException {

        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        UserGroupToTabularResultConverter resultConverter = new UserGroupToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateUserGroupTabularResult(userGroupMapper);
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
