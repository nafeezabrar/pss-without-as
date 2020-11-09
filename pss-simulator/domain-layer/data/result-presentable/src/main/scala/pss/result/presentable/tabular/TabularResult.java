package pss.result.presentable.tabular;

import pss.result.presentable.PresentableResult;

import java.util.ArrayList;
import java.util.List;

public class TabularResult extends PresentableResult {
    protected final String heading;
    protected List<SingleTableResult> singleTableResultList;

    public TabularResult(String heading) {
        this.heading = heading;
        this.singleTableResultList = new ArrayList<>();
    }

    public String getHeading() {
        return heading;
    }

    public List<SingleTableResult> getTabularResults() {
        return singleTableResultList;
    }

    public void addTableResult(SingleTableResult tableResult) {
        singleTableResultList.add(tableResult);
    }

    public void addTableResults(List<SingleTableResult> tableResults) {
        singleTableResultList.addAll(tableResults);
    }
}
