package pss.conversion.data_to_result.tabular;

import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.valuemap.SingleValueMap;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SinglePssVariableToTabularResultConverter {

    private static final String mainHeading = "Pss Variable Generation Output";
    protected final PssVariables pssVariables;
    protected final PssType pssType;
    protected TabularResult tabularResult;
    protected SingleTableResult singleTableResult;
    protected SingleElementTabularResultColumnSet ooiIdColumn;
    protected SingleElementTabularResultColumnSet ooiNameColumn;
    protected SingleElementTabularResultColumnSet ooiValueColumn;


    public SinglePssVariableToTabularResultConverter(PssVariables pssVariables, PssType pssType) {
        this.pssVariables = pssVariables;
        this.pssType = pssType;
    }

    public TabularResult generateSinglePssVariableResult() throws PssException {
        tabularResult = new TabularResult(mainHeading);
        generateSingleTableResult();
        return tabularResult;
    }

    private void generateSingleTableResult() throws PssException {
        String heading = generatePssVariableHeading();
        singleTableResult = new SingleTableResult(heading);
        initColumns();
        addColumns();
        addRows();
        tabularResult.addTableResult(singleTableResult);
    }

    private String generatePssVariableHeading() throws PssException {
        return String.format("Single Pss : N = %d", pssType.getN());
    }


    private void initColumns() {
        ooiIdColumn = ColumnProvider.getOoiIdColumn();
        ooiNameColumn = ColumnProvider.getOoiNameColumn();
        ooiValueColumn = ColumnProvider.getOoiValueColumn();
    }

    private void addColumns() {
        singleTableResult.addColumn(ooiIdColumn);
        singleTableResult.addColumn(ooiNameColumn);
        singleTableResult.addColumn(ooiValueColumn);
    }

    private void addRows() {
        SingleOoiCollection ooiCollection = (SingleOoiCollection) pssVariables.getOoiCollection();
        SingleValueMap valueMap = (SingleValueMap) pssVariables.getValueMap();
        ArrayList<Ooi> orderedOoiList = new ArrayList<>(ooiCollection.getOoiSet());
        orderedOoiList.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        for (Ooi ooi : orderedOoiList) {
            TabularResultRow tabularResultRow = generateTabularRowForOoi(valueMap, ooi);
            singleTableResult.addRow(tabularResultRow);
        }
    }

    private TabularResultRow generateTabularRowForOoi(SingleValueMap valueMap, Ooi ooi) {
        TabularResultRow row = new TabularResultRow();
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = generateCellMap(valueMap, ooi);
        row.addColumns(tabularResultCellMap);
        return row;
    }

    private Map<TabularResultColumn, TabularResultCell> generateCellMap(SingleValueMap valueMap, Ooi ooi) {
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = new HashMap<>();
        SingleDataTabularResultCell<Integer> ooiIdCell = new SingleDataTabularResultCell<>(ooi.getId());
        tabularResultCellMap.put(ooiIdColumn.getTabularResultColumn(), ooiIdCell);
        SingleDataTabularResultCell<String> ooiNameCell = new SingleDataTabularResultCell<>(ooi.getName());
        tabularResultCellMap.put(ooiNameColumn.getTabularResultColumn(), ooiNameCell);
        Value value = valueMap.getValue(new SingleOoiCombination(ooi));
        SingleDataTabularResultCell<Integer> ooiValueCell = new SingleDataTabularResultCell<>(value.getIntValue());
        tabularResultCellMap.put(ooiValueColumn.getTabularResultColumn(), ooiValueCell);
        return tabularResultCellMap;
    }
}

