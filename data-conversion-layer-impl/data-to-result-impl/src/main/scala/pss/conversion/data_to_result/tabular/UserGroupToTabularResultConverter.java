package pss.conversion.data_to_result.tabular;


import pss.conversion.data_to_result.UserGroupToResultConvertable;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.user.User;
import pss.exception.PssException;
import pss.result.presentable.tabular.*;

import java.util.*;

public class UserGroupToTabularResultConverter implements UserGroupToResultConvertable {
    private SingleElementTabularResultColumnSet columnForUserId = ColumnProvider.getUserIdColumn();
    private SingleElementTabularResultColumnSet columnForUserName = ColumnProvider.getUserNameColumn();
    private SingleElementTabularResultColumnSet columnForGroupId = ColumnProvider.getGroupIdColumn();


    @Override
    public TabularResult generateUserGroupTabularResult(UserGroupMapper UserGroupMapper) throws PssException {
        TabularResult tabularResult = new TabularResult(getUserTableHeading());
        SingleTableResult userTable = generateUserInfoTable(UserGroupMapper);
        tabularResult.addTableResult(userTable);
        return tabularResult;
    }

    private String getUserTableHeading() {
        return "Group number of registered Users";
    }

    private SingleTableResult generateUserInfoTable(UserGroupMapper UserGroupMapper) throws PssException {
        SingleTableResult tableResult = new SingleTableResult("User Information");
        addColumns(tableResult);
        List<TabularResultRow> tabularResultRows = generateRows(UserGroupMapper);
        tableResult.addRows(tabularResultRows);
        return tableResult;
    }

    private void addColumns(SingleTableResult tableResult) {
        tableResult.addColumn(columnForUserId);
        tableResult.addColumn(columnForUserName);
        tableResult.addColumn(columnForGroupId);
    }

    private List<TabularResultRow> generateRows(UserGroupMapper userGroupMapper) throws PssException {
        List<TabularResultRow> rowList = new ArrayList<>();
        Set<User> users = userGroupMapper.getUserPssGroupMap().keySet();
        List<User> orderedUsers = new ArrayList<>(users);
        orderedUsers.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        for (User user : orderedUsers) {
            int pssGroupId = userGroupMapper.getGroupId(user);
            TabularResultRow row = generateRow(user, pssGroupId);
            rowList.add(row);
        }
        return rowList;
    }

    private TabularResultRow generateRow(User user, int groupId) {
        Map<TabularResultColumn, TabularResultCell> cellMap = new HashMap<>();
        cellMap.put(columnForUserId.getTabularResultColumn(), new SingleDataTabularResultCell<>(user.getId()));
        cellMap.put(columnForUserName.getTabularResultColumn(), new SingleDataTabularResultCell<>(user.getName()));
        cellMap.put(columnForGroupId.getTabularResultColumn(), new SingleDataTabularResultCell<>(groupId));
        TabularResultRow row = new TabularResultRow();
        row.addColumns(cellMap);
        return row;
    }

}
