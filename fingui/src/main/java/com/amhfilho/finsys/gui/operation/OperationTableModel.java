package com.amhfilho.finsys.gui.operation;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.amhfilho.finsys.persistence.Operation;

@SuppressWarnings("serial")
public class OperationTableModel extends AbstractTableModel {
	
	private List<Operation> operations;
	
	private static final String[] columns = {
		"Initial Date","Description","Amount","Installments","Category"
	};
	
	public OperationTableModel(List<Operation> operations) {
		this.operations = operations;
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
	public int getRowCount() {
		return operations.size();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Operation operation = operations.get(row);
		switch (col) {
			case 0: return operation.getInitialDate();
			case 1: return operation.getDescription();
			case 2: return operation.getAmount();
			case 3: return operation.getInstallments();
			case 4: return operation.getCategory();
		}
		return null;
	}
	
	public Operation getOperationAtRow(int row) {
		return operations.get(row);
	}

}
