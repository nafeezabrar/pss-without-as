package pss.writing.result.tabular;

import pss.result.presentable.tabular.*;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class HtmlTableWriter {
    protected static final String TABLE_BEGIN = "<table>";
    protected static final String TABLE_END = "</table>";
    protected static final String TABLE_COLUMN_BEGIN = "<td>";
    protected static final String TABLE_COLUMN_END = "</td>";
    protected static final String TABLE_ROW_BEGIN = "<tr>";
    protected static final String TABLE_ROW_END = "</tr>";
    protected static final String TABLE_HEADER_BEGIN = "<th>";
    protected static final String TABLE_HEADER_END = "</th>";
    protected static final String NEW_LINE = "<br>";
    protected final Writer writer;
    protected final SingleTableResult singleTableResult;

    protected HtmlTableWriter(Writer writer, SingleTableResult singleTableResult) {
        this.writer = writer;
        this.singleTableResult = singleTableResult;
    }

    protected void writeHtmlTable() throws IOException {
        writeHeading();
        writeInitialsForTables();
        writeHeader();
        writeTableContent();
        writeEndingForTables();
    }

    private void writeHeading() throws IOException {
        if (singleTableResult.getHeading() != "") {
            writer.append(singleTableResult.getHeading());
            writer.append(NEW_LINE);
        }
    }

    protected void writeTableContent() throws IOException {
        for (TabularResultRow tabularResultRow : singleTableResult.getTabularResultRows()) {
            writer.append(TABLE_ROW_BEGIN);
            ContentRowWriter contentRowWriter = new ContentRowWriter(tabularResultRow);
            contentRowWriter.writeRow(singleTableResult.getTabularResultColumns());
            writer.append(TABLE_ROW_END);
        }
    }

    private void writeHeader() throws IOException {
        HeaderRowWriter headerRowWriter = new HeaderRowWriter();
        headerRowWriter.writeRow(singleTableResult.getTabularResultColumns());
    }

    private void writeEndingForTables() throws IOException {
        writer.append(TABLE_END);
    }

    private void writeInitialsForTables() throws IOException {
        writer.append(TABLE_BEGIN);
    }

    protected abstract class RowWriter {

        public void writeRow(List<TabularResultColumnSet> columnSets) throws IOException {
            writer.append(TABLE_ROW_BEGIN);
            for (TabularResultColumnSet columnSet : columnSets)
                writeCell(columnSet);
            writer.append(TABLE_ROW_END);
        }

        protected void writeCell(TabularResultColumnSet column) throws IOException {
            if (column.getType() == TabularResultColumnSet.TabularResultColumnsType.SINGLE)
                writeSingleCell((SingleElementTabularResultColumnSet) column);
            else
                writeMultiCell((MultiElementTabularResultColumnSet) column);
        }

        protected void writeSingleCell(SingleElementTabularResultColumnSet column) throws IOException {
            writeCellValue(column.getTabularResultColumn());
        }

        protected void writeMultiCell(MultiElementTabularResultColumnSet column) throws IOException {
            for (TabularResultColumn singleColumn : column.getTabularResultColumnList()) {
                writeCellValue(singleColumn);
            }
        }

        protected abstract void writeCellValue(TabularResultColumn column) throws IOException;
    }

    protected class HeaderRowWriter extends RowWriter {

        protected void writeCellValue(TabularResultColumn column) throws IOException {
            writer.append(TABLE_HEADER_BEGIN);
            writer.append(column.getColumnName());
            writer.append(TABLE_HEADER_END);
        }
    }

    protected class ContentRowWriter extends RowWriter {

        protected TabularResultRow row;

        public ContentRowWriter(TabularResultRow row) {
            this.row = row;
        }

        protected void writeCellValue(TabularResultColumn column) throws IOException {
            writer.append(TABLE_COLUMN_BEGIN);
            writeCellContent(column);
            writer.append(TABLE_COLUMN_END);
        }

        protected void writeCellContent(TabularResultColumn column) throws IOException {
            TabularResultCell resultCell = row.getTabularResultCell(column);
            if (resultCell == null) {
                writer.append("");
                return;
            }

            switch (resultCell.getCellType()) {
                case SINGLE_DATA:
                    writer.append(getSingleCellData(resultCell));
                    break;
                case SUB_TABLE_DATA:
                    writeSubTableCellData((SubTableTabularResultCell) resultCell);
                    break;
            }
        }

        private String getSingleCellData(TabularResultCell resultCell) {
            if (resultCell != null)
                return resultCell.toString();
            else
                return "";
        }

        private void writeSubTableCellData(SubTableTabularResultCell resultCell) throws IOException {
            HtmlTableWriter tableWriter = new HtmlTableWriter(writer, resultCell.getChildSingleTableResult());
            tableWriter.writeHtmlTable();
        }
    }
}


