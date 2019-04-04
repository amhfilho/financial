package com.amhfilho.finsys.gui.transaction;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.amhfilho.finsys.persistence.Transaction;

@SuppressWarnings("serial")
public class TransactionTableModel extends AbstractTableModel {
	private static final String[] columnNames = { "Date", "Description", "Amount" };

	private List<Transaction> transactions;

	public TransactionTableModel(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return transactions.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Transaction t = getTransaction(row);
		switch (col) {
			case 0:
				return t.getTransactionDate();
			case 1:
				return t.getDescription();
			case 2:
				return t.getAmount();
		}
		return null;
	}
	
	public Transaction getTransaction(int row) {
		if(transactions.size() == 0) return null;
		return transactions.get(row);
	}
	
	public BigDecimal getTotalCredit() {
		BigDecimal total= new BigDecimal("0");
		for(Transaction t: transactions) {
			if(t.getAmount().doubleValue() >= 0) {
				total = total.add(t.getAmount());
			}
		}
		return total;
	}
	
	public BigDecimal getTotalDebit() {
		BigDecimal total= new BigDecimal("0");
		for(Transaction t: transactions) {
			if(t.getAmount().doubleValue() < 0) {
				total = total.add(t.getAmount());
			}
		}
		return total;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total= new BigDecimal("0");
		for(Transaction t: transactions) {
			total = total.add(t.getAmount());
		}
		return total;
	}

}
