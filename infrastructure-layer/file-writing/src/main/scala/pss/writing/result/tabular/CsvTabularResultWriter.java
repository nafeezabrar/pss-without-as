package pss.writing.result.tabular;

import pss.result.presentable.tabular.*;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvTabularResultWriter extends TabularResultWriter {
    protected static final char COLUMN_DELIMITER = ',';
    protected static final char ROW_DELIMITER = '\n';

    public CsvTabularResultWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void writeResult(TabularResult results) throws IOException {
        for (SingleTableResult singleTableResult : results.getTabularResults()) {
            writeHeader(singleTableResult);
            writeBody(singleTableResult);
        }
    }

    protected void writeHeader(SingleTableResult result) throws IOException {
        HeaderRowWriter headerRowWriter = new HeaderRowWriter();
        headerRowWriter.writeRow(result.getTabularResultColumns());
    }

    protected void writeBody(SingleTableResult result) throws IOException {
        for (TabularResultRow row : result.getTabularResultRows()) {
            writer.append(ROW_DELIMITER);
            BodyRowWriter bodyRowWriter = new BodyRowWriter(row);
            bodyRowWriter.writeRow(result.getTabularResultColumns());
        }
    }

    protected abstract class RowWriter {

        public void writeRow(List<TabularResultColumnSet> columns) throws IOException {
            for (TabularResultColumnSet column : columns)
                writeCell(column);
        }

        protected void writeCell(TabularResultColumnSet column) throws IOException {
            if (column.getType() == TabularResultColumnSet.TabularResultColumnsType.SINGLE)
                writeSingleCell((SingleElementTabularResultColumnSet) column);
            else
                writeMultiCell((MultiElementTabularResultColumnSet) column);
        }

        protected void writeSingleCell(SingleElementTabularResultColumnSet column) throws IOException {
            String cellValue = getCellValue(column.getTabularResultColumn());
            writeCellValue(cellValue);
        }

        protected void writeMultiCell(MultiElementTabularResultColumnSet column) throws IOException {
            for (TabularResultColumn singleColumn : column.getTabularResultColumnList()) {
                String cellValue = getCellValue(singleColumn);
                writeCellValue(cellValue);
            }
        }

        protected void writeCellValue(String cellValue) throws IOException {
            writer.append(cellValue);
            writer.append(COLUMN_DELIMITER);
        }

        protected abstract String getCellValue(TabularResultColumn column);
    }

    protected class HeaderRowWriter extends RowWriter {

        protected String getCellValue(TabularResultColumn column) {
            return column.getColumnName();
        }
    }

    protected class BodyRowWriter extends RowWriter {

        protected TabularResultRow row;

        public BodyRowWriter(TabularResultRow row) {
            this.row = row;
        }

        protected String getCellValue(TabularResultColumn column) {
            if (row.getTabularResultCell(column) != null)
                return row.getTabularResultCell(column).toString();
            else
                return "";
        }
    }
}
