package pss.result.presentable.printing;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.exception.PssException;
import pss.writing.html.HtmlUserGroupWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlUserGroupPrinter implements UserGroupPrintable {
    protected final String fileName;

    public HtmlUserGroupPrinter(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void printUserGroupMapper(UserGroupMapper userGroupMapper) throws URISyntaxException, PssException, IOException {
        HtmlUserGroupWriter.writeUserGroupInHtml(fileName, userGroupMapper);
    }
}
