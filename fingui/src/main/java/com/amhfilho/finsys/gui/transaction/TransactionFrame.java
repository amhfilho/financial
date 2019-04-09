package com.amhfilho.finsys.gui.transaction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.amhfilho.finsys.persistence.Transaction;
import com.amhfilho.finsys.persistence.TransactionService;

@SuppressWarnings("serial")
public class TransactionFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel pnlButtons;
	private JButton btnAdd;
	private JPanel pnlTotals;
	private JLabel lblTotalDebitResult; 
	private JLabel lblTotalCredit;
	private JLabel lblTotalCreditResult;
	private JLabel lblBalanceResult;
	private JLabel lblBalance;
	private JComboBox<String> cmbMonth;
	private YearMonth yearMonth;
	private TransactionService service;
	private JComboBox cmbYear;
	
	public void setTransactions(List<Transaction> transactions) {
		TransactionTableModel model = new TransactionTableModel(transactions);
		table.setModel(model);
		lblTotalCreditResult.setText(model.getTotalCredit().toString());
		lblTotalDebitResult.setText(model.getTotalDebit().toString());
		lblBalanceResult.setText(model.getTotal().toString());
	}
	
	public void updateTransactions(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
		List<Transaction> transactions = service.findTransactionsFor(yearMonth);
		setTransactions(transactions);
		cmbMonth.setSelectedIndex(yearMonth.getMonthValue()-1);
		cmbYear.setSelectedItem(yearMonth.getYear());
		if(cmbYear.getActionListeners() == null || cmbYear.getActionListeners().length == 0)
			cmbYear.addActionListener(new ComboActionListener(this));
		if(cmbMonth.getActionListeners() == null || cmbMonth.getActionListeners().length == 0)
			cmbMonth.addActionListener(new ComboActionListener(this));
		
	}
	
	public void updateTransactionsForCurrentMonth() {
		updateTransactions(this.yearMonth);
	}
	
	

	
	public TransactionFrame(TransactionService transactionService) {
		this.service = transactionService;
		setTitle("Transactions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new TableMouseListener(this));
		scrollPane.setViewportView(table);

		pnlButtons = new JPanel();
		contentPane.add(pnlButtons, BorderLayout.NORTH);

		btnAdd = new JButton("Add...");
		pnlButtons.add(btnAdd);
		
		//String[] months = {"January","February","March","April","June","July","August","September","October","November","December"};
		String[] months = new DateFormatSymbols().getMonths();
		cmbMonth = new JComboBox(months);
		cmbMonth.setName("monthCombo");
		
		pnlButtons.add(cmbMonth);
		
		String[] years = {"2019","2020","2021"};
		cmbYear = new JComboBox(years);
		cmbYear.setName("yearCombo");
		
		pnlButtons.add(cmbYear);
		
		pnlTotals = new JPanel();
		contentPane.add(pnlTotals, BorderLayout.SOUTH);
		
		JLabel lblTotalDebit = new JLabel("Total debit:");
		
		lblTotalDebitResult = new JLabel("...");
		lblTotalDebitResult.setForeground(Color.RED);
		
		lblTotalCredit = new JLabel("Total credit:");
		
		lblTotalCreditResult = new JLabel("...");
		lblTotalCreditResult.setForeground(Color.BLUE);
		
		lblBalanceResult = new JLabel("...");
		
		lblBalance = new JLabel("Balance:");
		GroupLayout gl_pnlTotals = new GroupLayout(pnlTotals);
		gl_pnlTotals.setHorizontalGroup(
			gl_pnlTotals.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTotals.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlTotals.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTotalDebit)
						.addComponent(lblTotalCredit))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTotals.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlTotals.createSequentialGroup()
							.addComponent(lblTotalCreditResult)
							.addPreferredGap(ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
							.addComponent(lblBalance)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblBalanceResult))
						.addComponent(lblTotalDebitResult))
					.addContainerGap())
		);
		gl_pnlTotals.setVerticalGroup(
			gl_pnlTotals.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTotals.createSequentialGroup()
					.addGroup(gl_pnlTotals.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalDebit)
						.addComponent(lblTotalDebitResult))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_pnlTotals.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalCredit)
						.addComponent(lblTotalCreditResult)
						.addComponent(lblBalanceResult)
						.addComponent(lblBalance)))
		);
		pnlTotals.setLayout(gl_pnlTotals);
		btnAdd.addActionListener(new AddButtonActionListener(this));
		
	}
	
	class AddButtonActionListener implements ActionListener {
		private TransactionFrame frame;
		
		public AddButtonActionListener(TransactionFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
//			TransactionDialog dialog = new TransactionDialog(frame,null);
////			dialog.setVisible(true);
		}
	}
	
	class TableMouseListener extends MouseAdapter {
		private TransactionFrame frame;
		public TableMouseListener(TransactionFrame frame) {
			this.frame = frame;
		}
		public void mousePressed(MouseEvent mouseEvent) {
	        JTable table =(JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
	            TransactionTableModel model = (TransactionTableModel) table.getModel();
	            Transaction selected = model.getTransaction(row);
	            new TransactionDialog(frame,selected).setVisible(true);
	        }
	    }
	}
	
	class ComboActionListener implements ActionListener {
		private TransactionFrame frame;
		
		public ComboActionListener(TransactionFrame frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			JComboBox combo = (JComboBox)event.getSource();
			YearMonth yearMonth = readFromSelection(combo);
			updateTransactions(yearMonth);
		}
	}
	
	private YearMonth readFromSelection(JComboBox combo) {
		if(combo.getName().equals("monthCombo")) {
			int monthIndex = cmbMonth.getSelectedIndex() + 1;
			return this.yearMonth.withMonth(monthIndex);
		}
		else if(combo.getName().equals("yearCombo")) {
			int year = Integer.valueOf((String)cmbYear.getSelectedItem());
			return this.yearMonth.withYear(year);
		}
		throw new IllegalArgumentException("Invalid JComboBox. Expecting either cmbMonth or cmbYear");
		
	}

	public void save(Transaction transaction){
	    service.save(transaction);
    }
	
	
}
