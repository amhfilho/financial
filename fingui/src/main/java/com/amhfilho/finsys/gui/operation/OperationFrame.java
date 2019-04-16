package com.amhfilho.finsys.gui.operation;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.amhfilho.finsys.gui.transaction.TransactionFrame;
import com.amhfilho.finsys.gui.transaction.TransactionRepository;
import com.amhfilho.finsys.persistence.Operation;
import com.amhfilho.finsys.persistence.TransactionService;

@SuppressWarnings("serial")
public class OperationFrame extends JFrame {
	private OperationRepository operationRepository;
	private TransactionRepository transactionRepository;

	private JPanel contentPane;
	private JTable table;
	private JPanel buttonsPanel;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnForecast;


	/**
	 * Create the frame.
	 */
	public OperationFrame(OperationRepository operationRepository, TransactionRepository transactionRepository) {
		this.operationRepository = operationRepository;
		this.transactionRepository = transactionRepository;
		setTitle("Operations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 797, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new OperationTableMouseListener(this));
		table.getSelectionModel().addListSelectionListener(new OperationTableSelectionListener(this));
		loadOperations();
		
		buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel, BorderLayout.NORTH);
		
		btnAdd = new JButton("Add...");
		btnAdd.addActionListener((e) -> {
			new OperationDialog(null, operationRepository,this).setVisible(true);
		});
		buttonsPanel.add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		buttonsPanel.add(btnDelete);
		
		btnDelete.addActionListener((e)->{
			if(JOptionPane.showConfirmDialog(
					this, 
					"Do you want to delete?", 
					"Cancel dialog",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE) == 0){
				operationRepository.delete(getSelectedOperation());
				loadOperations();
			
			}
		});
		
		btnForecast = new JButton("Forecast...");
		buttonsPanel.add(btnForecast);
		btnForecast.addActionListener((e)->{
			TransactionFrame frame = new TransactionFrame(new TransactionService(operationRepository, transactionRepository));
			YearMonth yearMonth = YearMonth.from(LocalDate.now());
			frame.updateTransactions(yearMonth);
			frame.setVisible(true);
			
		});
	}
	
	protected void loadOperations() {
		OperationTableModel model = new OperationTableModel(this.operationRepository.findAll());
		table.setModel(model);
	}
	
	protected void editOperation() {
		 Operation operation =  getSelectedOperation();
		 new OperationDialog(operation, operationRepository, this).setVisible(true);
	}
	
	protected void setEnableDeleteButton(boolean enable) {
		btnDelete.setEnabled(enable);
	}
	
	private Operation getSelectedOperation() {
		int row = table.convertRowIndexToModel(table.getSelectedRow());
		return ((OperationTableModel)table.getModel()).getOperationAtRow(row);
	}

}
