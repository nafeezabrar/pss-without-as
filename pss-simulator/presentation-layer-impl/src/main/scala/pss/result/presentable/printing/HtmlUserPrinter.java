package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.writing.html.HtmlUserWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlUserPrinter implements UserPrintable {
    protected final String fileName;

    public HtmlUserPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printUsers(UserMapper userMapper) throws URISyntaxException, PssException, IOException {
        HtmlUserWriter.writeUserInHtml(fileName, userMapper);
    }
}
