package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.valuemap.Value;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;
import pss.reader.report.ObservedReportDataReadable;
import pss.report.observed.ObservedReportFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static pss.config.ConfigKeyString.ObservedReportGeneration.*;

public class JsonObservedReportDataReader implements ObservedReportDataReadable {
    protected final JsonFileReader jsonFileReader;

    public JsonObservedReportDataReader(JsonFileReader jsonFileReader) {
        this.jsonFileReader = jsonFileReader;
    }

    @Override
    public List<ObservedReportFromFile> generateObservedReportData() throws ReaderException {
        Set<JSONObject> jsonObjects = jsonFileReader.getJsonObjects(reportDataKey);
        List<ObservedReportFromFile> observedReports = new ArrayList<>();
        for (JSONObject reportJsonObject : jsonObjects) {
            ObservedReportFromFile observedReportFromFile = readObservedReportData(jsonFileReader, reportJsonObject);
            observedReports.add(observedReportFromFile);
        }
        return observedReports;
    }

    private ObservedReportFromFile readObservedReportData(JsonFileReader jsonFileReader, JSONObject reportJsonObject) throws ReaderException {
        int reportId = jsonFileReader.getInt(reportJsonObject, reportIdKey);
        int userId = jsonFileReader.getInt(reportJsonObject, userIdKey);
        int value = jsonFileReader.getInt(reportJsonObject, reportValueKey);
        return new ObservedReportFromFile(reportId, new Value(value), userId);
    }
}
