package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.post.result.process.PostResultProcessConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.PostResultProcess.*;

public class JsonPostResultProcessConfigReader {
    protected final JsonFileReader jsonFileReader;
    protected JSONObject jsonObject;

    public JsonPostResultProcessConfigReader(JsonFileReader jsonFileReader) throws ReaderException {
        this.jsonFileReader = jsonFileReader;
        jsonObject = jsonFileReader.getJsonObject(postResultProcessConfigKey);
    }

    public PostResultProcessConfig readPostResultProcessConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException, PssException {

        PostResultProcessConfig postResultProcessConfig = new PostResultProcessConfig();
        if (!fileReader.containsKey(postResultProcessConfigKey)) {
            return postResultProcessConfig;
        }

        setConfig(postResultProcessConfig, observedReportCountMap);
        setConfig(postResultProcessConfig, observedReportValueCountMapKey);
        setConfig(postResultProcessConfig, apsDecodabilityResult);
        setConfig(postResultProcessConfig, adversaryOwnReportCountMap);
        setConfig(postResultProcessConfig, adversaryInterceptedReport);
        setConfig(postResultProcessConfig, adversaryOwnReportedValue);
        setConfig(postResultProcessConfig, reportCountToDecodeKey);
        setConfig(postResultProcessConfig, reportCountOfEachGroupKey);
        setConfig(postResultProcessConfig, adversaryDecodedValue);
        setConfig(postResultProcessConfig, adversaryOwnReportedDecodability);

        return postResultProcessConfig;
    }

    private void setConfig(PostResultProcessConfig postResultProcessConfig, String key) throws InvalidConfigException {
        boolean keyFound = jsonFileReader.containsKey(jsonObject, key);
        postResultProcessConfig.setPrintConfig(key, keyFound);
    }
}
