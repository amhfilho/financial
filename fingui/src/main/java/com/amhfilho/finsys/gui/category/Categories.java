package com.amhfilho.finsys.gui.category;

import com.amhfilho.finsys.persistence.Transaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories extends JFrame {
    private JPanel categoriesPanel;
    private JPanel buttonsPanel;
    private JPanel bodyPanel;
    private JButton closeButton;
    private JScrollPane scrollPane;
    private JTable categoriesTable;

    private List<Transaction> transactions;

    public Categories(List<Transaction> transactions) {
        this.transactions = transactions;
        setTitle("Categories");
        setLocationRelativeTo(null);
        getContentPane().add(categoriesPanel);
        categoriesTable.setModel(new CategoriesTableModel(getCategoriesMap()));
        categoriesTable.setAutoCreateRowSorter(true);
        closeButton.addActionListener((e)-> {
            dispose();
        });
        pack();
    }

    private String printCategories(){
        Map<String, BigDecimal> map = getCategoriesMap();
        StringBuilder result = new StringBuilder();
        map.forEach((k,v) ->{
            result.append(k).append(": ").append(v.toString()).append("\n");
        });
        return result.toString();
    }

    private Map<String, BigDecimal> getCategoriesMap(){
        Map<String, BigDecimal> categories = new HashMap<>();
        transactions.forEach(x ->{
            final String category = x.getOperation().getCategory();
            if(categories.containsKey(category)){
                BigDecimal amount = categories.get(category).add(x.getAmount());
                categories.remove(category);
                categories.put(category,amount);
            } else {
                categories.put(category, x.getAmount());
            }
        });

        return categories;
    }
}
