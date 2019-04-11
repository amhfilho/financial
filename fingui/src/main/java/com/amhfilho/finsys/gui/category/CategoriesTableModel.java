package com.amhfilho.finsys.gui.category;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoriesTableModel extends AbstractTableModel {
    private Map<String, BigDecimal> categories;
    private static final String[] columns = {"Category","Amount" };

    public CategoriesTableModel(Map<String, BigDecimal> categories){
        this.categories = categories;
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] selected = getCategoriesList().get(rowIndex);
        switch (columnIndex){
            case 0: return selected[0];
            case 1: return selected[1];
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return  getValueAt(0,columnIndex).getClass();
    }

    private List<Object[]> getCategoriesList(){
        List<Object[]> list = new ArrayList<>();
        categories.forEach((k,v)-> list.add(new Object[]{k,v}));
        return list;
    }
}
