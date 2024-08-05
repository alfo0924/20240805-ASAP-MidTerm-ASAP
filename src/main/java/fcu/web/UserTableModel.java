package fcu.web;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
  private final String[] columnNames = {"使用者名稱", "啟用", "功能1", "功能2", "功能3"};
  private final List<User> users;

  public UserTableModel() {
    users = new ArrayList<>();
  }

  public void addUser(String userName, boolean isActive) {
    users.add(new User(userName, isActive, false, false, false));
    fireTableRowsInserted(users.size() - 1, users.size() - 1);
  }

  public void clear() {
    int size = users.size();
    if (size > 0) {
      users.clear();
      fireTableRowsDeleted(0, size - 1);
    }
  }

  @Override
  public int getRowCount() {
    return users.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (columnIndex == 0) {
      return String.class;
    } else {
      return Boolean.class;
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return columnIndex > 0; // 只有啟用和功能列可編輯
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    User user = users.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return user.getUserName();
      case 1:
        return user.isActive();
      case 2:
        return user.isFeature1();
      case 3:
        return user.isFeature2();
      case 4:
        return user.isFeature3();
      default:
        return null;
    }
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    User user = users.get(rowIndex);
    switch (columnIndex) {
      case 1:
        user.setActive((Boolean) aValue);
        break;
      case 2:
        user.setFeature1((Boolean) aValue);
        break;
      case 3:
        user.setFeature2((Boolean) aValue);
        break;
      case 4:
        user.setFeature3((Boolean) aValue);
        break;
    }
    fireTableCellUpdated(rowIndex, columnIndex);
  }
}
