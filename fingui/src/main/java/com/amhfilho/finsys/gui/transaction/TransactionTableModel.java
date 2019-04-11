package com.amhfilho.finsys.gui.transaction;

import com.amhfilho.finsys.persistence.Transaction;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("serial")
public class TransactionTableModel extends AbstractTableModel {
	private static final String[] columnNames = { "Date", "Description", "Amount", "Installments", "Status", "Category", "Balance" };

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
			case 3:
				return t.getInstallmentsDescription();
			case 4:
				return t.getStatus();
			case 5:
				return t.getOperation().getCategory();
			case 6:
				return t.getBalance();

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
