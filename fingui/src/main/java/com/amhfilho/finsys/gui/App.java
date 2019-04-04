package com.amhfilho.finsys.gui;

import java.awt.EventQueue;

import com.amhfilho.finsys.gui.operation.OperationFrame;
import com.amhfilho.finsys.gui.operation.OperationRepository;
import com.amhfilho.finsys.persistence.DatabaseOperationRepository;
import com.amhfilho.finsys.persistence.PersistenceUtil;

public class App {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				OperationRepository repo = new DatabaseOperationRepository(PersistenceUtil.getEntityManager());
				OperationFrame frame = new OperationFrame(repo);
				frame.setVisible(true);
//				TransactionService service = new TransactionService(PersistenceUtil.getEntityManager());
//				LocalDate now = LocalDate.now();
//				YearMonth thisMonth = YearMonth.of(now.getYear(), now.getMonth());
//				TransactionFrame frame = new TransactionFrame(service);
//				//frame.setTransactions(service.findByMonth(YearMonth.of(now.getYear(), now.getMonth())));
//				frame.updateTransactions(thisMonth);
//				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public App() {
		
	}
}
