package com.amhfilho.finsys.gui.operation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class OperationTableMouseListener extends MouseAdapter {

	private OperationFrame frame;

	public OperationTableMouseListener(OperationFrame frame) {
		this.frame = frame;
	}

	public void mousePressed(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		//Point point = mouseEvent.getPoint();
		//int row = table.rowAtPoint(point);
		if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			frame.editOperation();
		}
	}

}
