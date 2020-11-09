package pss.conversion.data_to_result.tabular;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.result.presentable.tabular.SingleTableResult;
import pss.result.presentable.tabular.TabularResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PssGroupingToTabularResultConverter {

    private static final String mainHeading = "Pss Grouping Output";
    protected final Set<PssGroup> pssGroups;
    protected final PssType pssType;
    protected TabularResult tabularResult;

    public PssGroupingToTabularResultConverter(Set<PssGroup> pssGroups, PssType pssType) {
        this.pssGroups = pssGroups;
        this.pssType = pssType;
    }

    public TabularResult generatePssGroupingResult() throws PssException {
        tabularResult = new TabularResult(mainHeading);
        List<PssGroup> orderedPssGroups = new ArrayList<>(pssGroups);
        orderedPssGroups.sort((o1, o2) -> Integer.compare(o1.getPssGroupId(), o2.getPssGroupId()));
        for (PssGroup pssGroup : orderedPssGroups) {
            PssGroupToTabularResultConverter resultConverter = new PssGroupToTabularResultConverter(pssType, pssGroup);
            List<SingleTableResult> singleTableResults = resultConverter.generateSingleTableResult();
            tabularResult.addTableResults(singleTableResults);
        }
        return tabularResult;
    }

}

