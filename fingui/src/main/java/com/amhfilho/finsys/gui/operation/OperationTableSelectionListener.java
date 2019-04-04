package com.amhfilho.finsys.gui.operation;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OperationTableSelectionListener implements ListSelectionListener {

	private OperationFrame operationFrame;
	
	public OperationTableSelectionListener(OperationFrame operationFrame) {
		this.operationFrame = operationFrame;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent event) {
		ListSelectionModel lsm = (ListSelectionModel)event.getSource();
		operationFrame.setEnableDeleteButton(!lsm.isSelectionEmpty());
		
	}

}
