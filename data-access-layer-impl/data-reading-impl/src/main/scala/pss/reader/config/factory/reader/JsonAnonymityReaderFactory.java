package pss.reader.config.factory.reader;

import org.json.simple.JSONObject;
import pss.config.task.AnonymityGenerationConfig;
import pss.data.pss_type.PssType;
import pss.exception.ReaderException;
import pss.reader.config.readers.*;
import pss.reader.json.JsonFileReader;

public class JsonAnonymityReaderFactory {
    public static JsonAnonymityReader getJsonAnonymityReader(PssType pssType, JSONObject jsonObject, JsonFileReader jsonFileReader, AnonymityGenerationConfig.AnonymityGenerationMethod anonymityGenerationMethod) throws ReaderException {
        switch (anonymityGenerationMethod) {
            case FIXED_AAS:
                return getJsonAasAnonymityReader(pssType, jsonObject, jsonFileReader);
            case FIXED_RAS:
                return getJsonRasAnonymityReader(pssType, jsonObject, jsonFileReader);
            case UNIFORM_ANONYMITY:
                return getUniformAnonymityReader(pssType, jsonObject, jsonFileReader);
            default:
                return getJsonAnonymityReader(pssType, jsonObject, jsonFileReader);
        }
    }

    private static JsonAnonymityReader getJsonRasAnonymityReader(PssType pssType, JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                return new SingleJsonRasAnonymityReader(jsonObject, jsonFileReader);
            case MULTI:
                return new MultiJsonRasAnonymityReader(jsonObject, jsonFileReader, pssType.getDimensionSet());
        }
        throw new ReaderException(String.format("Anonymity Reader not implemented for pss dimensional type %d", pssType.getPssDimensionType().toString()));
    }

    private static JsonAnonymityReader getUniformAnonymityReader(PssType pssType, JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                return new SingleJsonUniformAnonymityReader(jsonObject, jsonFileReader);
            case MULTI:
                throw new UnsupportedOperationException("not impl");
        }
        throw new ReaderException(String.format("Anonymity Reader not implemented for pss dimensional type %d", pssType.getPssDimensionType().toString()));
    }

    private static JsonAnonymityReader getJsonAasAnonymityReader(PssType pssType, JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                return new SingleJsonAasAnonymityReader(jsonObject, jsonFileReader);
            case MULTI:
                return new MultiJsonAasAnonymityReader(jsonObject, jsonFileReader, pssType.getDimensionSet());
        }
        throw new ReaderException(String.format("Anonymity Reader not implemented for pss dimensional type %d", pssType.getPssDimensionType().toString()));
    }

    private static JsonAnonymityReader getJsonAnonymityReader(PssType pssType, JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                return new SingleJsonAnonymityReader(jsonObject, jsonFileReader);
            case MULTI:
                return new MultiJsonAnonymityReader(jsonObject, jsonFileReader, pssType.getDimensionSet());
        }
        throw new ReaderException(String.format("Anonymity Reader not implemented for pss dimensional type %d", pssType.getPssDimensionType().toString()));
    }
}
