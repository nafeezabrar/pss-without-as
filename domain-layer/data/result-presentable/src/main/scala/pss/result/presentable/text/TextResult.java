package pss.result.presentable.text;

import pss.result.presentable.PresentableResult;

import java.util.List;

public class TextResult extends PresentableResult {
    protected final List<String> resultText;

    public TextResult(List<String> resultText) {
        this.resultText = resultText;
    }

    public List<String> getResultText() {
        return resultText;
    }
}
