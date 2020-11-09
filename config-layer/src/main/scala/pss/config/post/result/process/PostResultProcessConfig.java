package pss.config.post.result.process;

import pss.exception.InvalidConfigException;

import static pss.config.ConfigKeyString.PostResultProcess;
import static pss.config.ConfigKeyString.PostResultProcess.*;

public class PostResultProcessConfig {
    protected boolean printReportCountMap = false;
    protected boolean printReportedValueCountMap = false;
    protected boolean printAdversaryOwnReportCountMap = false;
    protected boolean printAdversaryInterceptedReportCount = false;
    protected boolean printAdversaryOwnReportedValueCount = false;
    protected boolean printAdversaryDecodedValueCount = false;
    protected boolean printDecodabilityAnalysis = false;
    protected boolean printReportCountToDecode = false;
    protected boolean printReportCountOfEachGroup = false;
    protected boolean printAdversaryOwnReportedDecodability = false;

    public boolean isPrintReportCountOfEachGroup() {
        return printReportCountOfEachGroup;
    }

    public boolean isPrintReportCountToDecode() {
        return printReportCountToDecode;
    }

    public boolean isPrintReportCountMap() {
        return printReportCountMap;
    }

    public boolean isPrintAdversaryOwnReportCountMap() {
        return printAdversaryOwnReportCountMap;
    }

    public boolean isPrintAdversaryInterceptedReportCount() {
        return printAdversaryInterceptedReportCount;
    }

    public boolean isPrintAdversaryOwnReportedValueCount() {
        return printAdversaryOwnReportedValueCount;
    }

    public boolean isPrintReportedValueCountMap() {
        return printReportedValueCountMap;
    }

    public boolean isPrintDecodabilityAnalysis() {
        return printDecodabilityAnalysis;
    }

    public boolean isPrintAdversaryOwnReportedDecodability() {
        return printAdversaryOwnReportedDecodability;
    }

    public void setPrintConfig(String key, boolean keyFound) throws InvalidConfigException {
        switch (key) {
            case adversaryInterceptedReport:
                printAdversaryInterceptedReportCount = keyFound;
                break;
            case adversaryOwnReportCountMap:
                printAdversaryOwnReportCountMap = keyFound;
                break;
            case adversaryOwnReportedValue:
                printAdversaryOwnReportedValueCount = keyFound;
                break;
            case apsDecodabilityResult:
                printDecodabilityAnalysis = keyFound;
                break;
            case PostResultProcess.observedReportCountMap:
                printReportCountMap = keyFound;
                break;
            case PostResultProcess.observedReportValueCountMapKey:
                printReportedValueCountMap = keyFound;
                break;
            case PostResultProcess.reportCountOfEachGroupKey:
                printReportCountOfEachGroup = keyFound;
                break;
            case PostResultProcess.reportCountToDecodeKey:
                printReportCountToDecode = keyFound;
                break;
            case PostResultProcess.adversaryDecodedValue:
                printAdversaryDecodedValueCount = keyFound;
                break;
            case PostResultProcess.adversaryOwnReportedDecodability:
                printAdversaryOwnReportedDecodability = keyFound;
                break;
            default:
                throw new InvalidConfigException(String.format("post result process config %s not found", key));
        }
    }
}
