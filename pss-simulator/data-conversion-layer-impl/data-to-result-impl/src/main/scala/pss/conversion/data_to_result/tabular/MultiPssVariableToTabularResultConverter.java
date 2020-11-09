package pss.conversion.data_to_result.tabular;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.valuemap.MultiValueMap;
import pss.data.valuemap.Value;
import pss.result.presentable.tabular.*;

import java.util.*;

public class MultiPssVariableToTabularResultConverter {
    private static final String mainHeading = "Pss Variable Generation Output";

    protected final PssVariables pssVariables;
    protected final PssType pssType;
    protected final SingleElementTabularResultColumnSet ooiIdColumn = ColumnProvider.getOoiIdColumn();
    protected final SingleElementTabularResultColumnSet ooiNameColumn = ColumnProvider.getOoiNameColumn();
    protected final SingleElementTabularResultColumnSet ooiValueColumn = ColumnProvider.getOoiValueColumn();
    protected TabularResult tabularResult;
    protected MultiElementTabularResultColumnSet columnsForOoi;
    protected Map<Dimension, TabularResultColumn> columnMapForDimension;

    public MultiPssVariableToTabularResultConverter(PssVariables pssVariables, PssType pssType) {
        this.pssVariables = pssVariables;
        this.pssType = pssType;
    }

    public TabularResult generateMultiPssVariableResult() {
        tabularResult = new TabularResult(mainHeading);
        initMultiElementColumns();
        generateTabularResults();
        return tabularResult;
    }

    private void initMultiElementColumns() {
        initOoiColumns();
    }

    private void initOoiColumns() {
        columnMapForDimension = new HashMap<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        List<TabularResultColumn> columnList = new ArrayList<>();
        for (Dimension dimension : dimensionSet) {
            TabularResultColumn column = new TabularResultColumn(String.format("Ooi of %s", dimension.getName()));
            columnList.add(column);
            columnMapForDimension.put(dimension, column);
        }
        columnsForOoi = new MultiElementTabularResultColumnSet(columnList);
    }

    private void generateTabularResults() {
        List<SingleTableResult> ooiTableResults = generateOoiListsTableResult();
        tabularResult.addTableResults(ooiTableResults);
        SingleTableResult ooiValueTableResult = generateOoiValueTableResult();
        tabularResult.addTableResult(ooiValueTableResult);
    }

    private List<SingleTableResult> generateOoiListsTableResult() {
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        List<SingleTableResult> ooiListResults = new ArrayList<>();
        for (Dimension dimension : dimensionSet) {
            SingleTableResult ooiListTable = generateOoiListTableResult(dimension);
            ooiListResults.add(ooiListTable);
        }
        return ooiListResults;
    }

    private SingleTableResult generateOoiListTableResult(Dimension dimension) {
        SingleTableResult ooiListTableResult = new SingleTableResult(String.format("Ooi List for %s dimension", dimension.getName()));
        addColumnsForOoiList(ooiListTableResult);
        MultiOoiCollection ooiCollection = (MultiOoiCollection) pssVariables.getOoiCollection();
        Set<Ooi> oois = ooiCollection.getOoiSetMap().get(dimension);
        ArrayList<Ooi> orderedOoiList = new ArrayList<>(oois);
        orderedOoiList.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        for (Ooi ooi : orderedOoiList) {
            TabularResultRow tabularResultRow = generateTabularRowForOoi(ooi);
            ooiListTableResult.addRow(tabularResultRow);
        }
        return ooiListTableResult;
    }

    private TabularResultRow generateTabularRowForOoi(Ooi ooi) {
        TabularResultRow row = new TabularResultRow();
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = generateCellMap(ooi);
        row.addColumns(tabularResultCellMap);
        return row;
    }

    private Map<TabularResultColumn, TabularResultCell> generateCellMap(Ooi ooi) {
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = new HashMap<>();
        SingleDataTabularResultCell ooiIdCell = new SingleDataTabularResultCell<>(ooi.getId());
        tabularResultCellMap.put(ooiIdColumn.getTabularResultColumn(), ooiIdCell);
        SingleDataTabularResultCell ooiNameCell = new SingleDataTabularResultCell<>(ooi.getName());
        tabularResultCellMap.put(ooiNameColumn.getTabularResultColumn(), ooiNameCell);
        return tabularResultCellMap;
    }

    private void addColumnsForOoiList(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(ooiIdColumn);
        singleTableResult.addColumn(ooiNameColumn);
    }

    private SingleTableResult generateOoiValueTableResult() {
        SingleTableResult ooiValueTableResult = new SingleTableResult("Values for the Ooi combinations");
        addColumnsForOoiValues(ooiValueTableResult);
        addRowsForOoiValues(ooiValueTableResult);
        return ooiValueTableResult;
    }

    private void addColumnsForOoiValues(SingleTableResult singleTableResult) {
        singleTableResult.addColumn(columnsForOoi);
        singleTableResult.addColumn(ooiValueColumn);
    }

    private void addRowsForOoiValues(SingleTableResult ooiValueTableResult) {
        MultiValueMap valueMap = (MultiValueMap) pssVariables.getValueMap();
        Map<MultiOoiCombination, Value> valueMapValues = valueMap.getValues();
        List<MultiOoiCombination> orderedCombinations = new ArrayList<>();
        orderedCombinations.addAll(valueMapValues.keySet());
        orderedCombinations.sort((o1, o2) -> Integer.compare(valueMapValues.get(o1).getIntValue(), valueMapValues.get(o2).getIntValue()));
        for (MultiOoiCombination ooiCombination : orderedCombinations) {
            TabularResultRow tabularResultRow = createRowForOoiCombination(ooiCombination, valueMapValues.get(ooiCombination).getIntValue());
            ooiValueTableResult.addRow(tabularResultRow);
        }

    }

    private TabularResultRow createRowForOoiCombination(MultiOoiCombination ooiCombination, int value) {
        TabularResultRow tabularResultRow = new TabularResultRow();
        Map<TabularResultColumn, TabularResultCell> tabularResultCellMap = new HashMap<>();
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        for (Dimension dimension : dimensionSet) {
            Ooi ooi = ooiCombination.getOoiMap().get(dimension);
            String ooiInfo = String.format("%s (%d)", ooi.getName(), ooi.getId());
            tabularResultCellMap.put(columnMapForDimension.get(dimension), new SingleDataTabularResultCell<>(ooiInfo));
        }
        tabularResultCellMap.put(ooiValueColumn.getTabularResultColumn(), new SingleDataTabularResultCell<>(value));
        tabularResultRow.addColumns(tabularResultCellMap);
        return tabularResultRow;
    }

}
