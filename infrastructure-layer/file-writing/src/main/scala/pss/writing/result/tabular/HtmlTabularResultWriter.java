package pss.writing.result.tabular;

import pss.result.presentable.tabular.SingleTableResult;
import pss.result.presentable.tabular.TabularResult;

import java.io.IOException;
import java.io.Writer;

public class HtmlTabularResultWriter extends TabularResultWriter {
    protected static final String HTML_BEGIN = "<html>";
    protected static final String HTML_END = "</html>";
    protected static final String STYLE_BEGIN = "<style>";
    protected static final String STYLE_END = "</style>";
    protected static final String BODY_BEGIN = "<body>";
    protected static final String BODY_END = "</body>";
    protected static final String NEW_LINE = "<br>";


    public HtmlTabularResultWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void writeResult(TabularResult results) throws IOException {
        HtmlPageWithTableWriter htmlPageWithTableWriter = new HtmlPageWithTableWriter(results);
        htmlPageWithTableWriter.writeHtmlPage();
        writer.close();
    }

    protected class HtmlPageWithTableWriter {
        protected final TabularResult tabularResult;

        protected HtmlPageWithTableWriter(TabularResult tabularResult) {
            this.tabularResult = tabularResult;
        }

        protected void writeHtmlPage() throws IOException {
            writeInitials();
            writeBody();
            writeEndings();
        }

        private void writeInitials() throws IOException {
            writer.append(HTML_BEGIN);
            writer.append(STYLE_BEGIN);
            writer.append(getCssStyle());
            writer.append(STYLE_END);
            writer.append(BODY_BEGIN);

        }

        private String getCssStyle() {
            return "table, th, td {\n" +
                    "    border: 1px solid black;\n" +
                    "    border-collapse: collapse;\n" +
                    "}";
        }

        private void writeBody() throws IOException {
            writeHeading();
            writeTable();
        }

        private void writeHeading() throws IOException {
            if (tabularResult.getHeading() != "") {
                writer.append(tabularResult.getHeading());
                writer.append(NEW_LINE);
            }
        }

        private void writeTable() throws IOException {
            writer.append(NEW_LINE);
            for (SingleTableResult singleTableResult : tabularResult.getTabularResults()) {
                HtmlTableWriter htmlTableWriter = new HtmlTableWriter(writer, singleTableResult);
                htmlTableWriter.writeHtmlTable();
                writer.append(NEW_LINE);
            }
        }

        private void writeEndings() throws IOException {
            writer.append(NEW_LINE);
            writer.append(BODY_END);
            writer.append(HTML_END);
        }
    }
}
