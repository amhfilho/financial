package com.amhfilho.finsys.gui;

import com.amhfilho.finsys.gui.operation.OperationFrame;
import com.amhfilho.finsys.gui.operation.OperationRepository;
import com.amhfilho.finsys.gui.transaction.TransactionRepository;
import com.amhfilho.finsys.persistence.DatabaseOperationRepository;
import com.amhfilho.finsys.persistence.DatabaseTransactionRepository;
import com.amhfilho.finsys.persistence.PersistenceUtil;

import javax.persistence.EntityManager;
import java.awt.*;

public class App {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				EntityManager em = PersistenceUtil.getEntityManager();
				OperationRepository databaseOperationRepository = new DatabaseOperationRepository(em);
				TransactionRepository databaseTransactionRepository = new DatabaseTransactionRepository(em);
				OperationFrame frame = new OperationFrame(databaseOperationRepository, databaseTransactionRepository);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public App() {
		
	}
}
