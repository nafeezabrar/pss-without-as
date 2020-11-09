package pss.conversion.data_to_result.tabular;


import pss.conversion.data_to_result.UserToResultConvertable;
import pss.data.user.User;
import pss.mapper.user.UserMapper;
import pss.result.presentable.tabular.*;

import java.util.*;

public class UserToTabularResultConverter implements UserToResultConvertable {
    private SingleElementTabularResultColumnSet columnForUserId = ColumnProvider.getUserIdColumn();
    private SingleElementTabularResultColumnSet columnForUserName = ColumnProvider.getUserNameColumn();

    @Override
    public TabularResult generateUserTabularResult(UserMapper userMapper) {
        TabularResult tabularResult = new TabularResult(getUserTableHeading(userMapper));
        SingleTableResult userTable = generateUserInfoTable(userMapper);
        tabularResult.addTableResult(userTable);
        return tabularResult;
    }

    private String getUserTableHeading(UserMapper userMapper) {
        return String.format("User Table : Total User = %d", userMapper.getUsers().size());
    }

    private SingleTableResult generateUserInfoTable(UserMapper userMapper) {
        SingleTableResult tableResult = new SingleTableResult("User Information");
        addColumns(tableResult);
        List<TabularResultRow> tabularResultRows = generateRows(userMapper);
        tableResult.addRows(tabularResultRows);
        return tableResult;
    }

    private void addColumns(SingleTableResult tableResult) {
        tableResult.addColumn(columnForUserId);
        tableResult.addColumn(columnForUserName);
    }

    private List<TabularResultRow> generateRows(UserMapper userMapper) {
        List<TabularResultRow> rowList = new ArrayList<>();
        Set<User> users = userMapper.getUsers();
        List<User> orderedUsers = new ArrayList<>(users);
        orderedUsers.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        for (User user : orderedUsers) {
            TabularResultRow row = generateRow(user);
            rowList.add(row);
        }
        return rowList;
    }

    private TabularResultRow generateRow(User user) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        cellMap.put(columnForUserId.getTabularResultColumn(), new SingleDataTabularResultCell<>(user.getId()));
        cellMap.put(columnForUserName.getTabularResultColumn(), new SingleDataTabularResultCell<>(user.getName()));
        TabularResultRow row = new TabularResultRow();
        row.addColumns(cellMap);
        return row;
    }
}
