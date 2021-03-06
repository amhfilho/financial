package com.amhfilho.finsys.gui.transaction;

import com.amhfilho.finsys.CollectionUtils;
import com.amhfilho.finsys.gui.category.Categories;
import com.amhfilho.finsys.persistence.Transaction;
import com.amhfilho.finsys.persistence.TransactionService;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class TransactionFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel pnlButtons;
	private JButton categoriesButton;
	private JPanel pnlTotals;
	private JLabel lblTotalDebitResult; 
	private JLabel lblTotalCredit;
	private JLabel lblTotalCreditResult;
	private JLabel lblTotalDebitLate;
	private JLabel lblTotalDebitLateResult;
	private JLabel lblBalanceResult;
	private JLabel lblBalance;
	private JComboBox<String> cmbMonth;
	private YearMonth yearMonth;
	private TransactionService service;
	private JComboBox cmbYear;
	private JTextField balance;
	private JButton balanceUpdate;
	private JCheckBox checkBox;

	private List<Transaction> transactions;
	
	public void setTransactions(List<Transaction> transactions) {
	    this.transactions=transactions;
		TransactionTableModel model = new TransactionTableModel(transactions);
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		lblTotalCreditResult.setText(model.getTotalCredit().toString());
		lblTotalDebitResult.setText(model.getTotalDebit().toString());
		lblBalanceResult.setText(model.getTotal().toString());
		if(model.getTotal().doubleValue() >= 0){
			lblBalanceResult.setForeground(Color.BLUE);
		}
		else {
			lblBalanceResult.setForeground(Color.RED);
		}
		lblTotalDebitLateResult.setText(service.getTotalDebitLateTransactions().toString());
	}
	
	public void updateTransactions(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
		List<Transaction> transactions = service.findTransactionsFor(yearMonth);
		setTransactions(transactions);
		cmbMonth.setSelectedIndex(yearMonth.getMonthValue()-1);
		cmbYear.setSelectedItem(yearMonth.getYear());
		if(CollectionUtils.isNullOrEmpty(cmbYear.getActionListeners()))
			cmbYear.addActionListener(new ComboActionListener());
		if(CollectionUtils.isNullOrEmpty(cmbMonth.getActionListeners()))
			cmbMonth.addActionListener(new ComboActionListener());
		if(CollectionUtils.isNullOrEmpty(categoriesButton.getActionListeners()))
			categoriesButton.addActionListener((e -> {
				new Categories(transactions).setVisible(true);
			}));
		
	}
	
	public void updateTransactionsForCurrentMonth() {
		updateTransactions(this.yearMonth);
	}

	public TransactionFrame(TransactionService transactionService) {
		this.service = transactionService;
		setTitle("Transactions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.addMouseListener(new TableMouseListener(this));
		table.setAutoCreateRowSorter(true);
		table.getSelectionModel().addListSelectionListener((e)->{
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			StringBuilder output = new StringBuilder();
			if (lsm.isSelectionEmpty()) {
				output.append(" <none>");
			} else {
				// Find out which indexes are selected.
				int minIndex = lsm.getMinSelectionIndex();
				int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {
						output.append(" " + i);
					}
				}
			}
			System.out.println(output.toString());
		});
		scrollPane.setViewportView(table);

		pnlButtons = new JPanel();
		contentPane.add(pnlButtons, BorderLayout.NORTH);

		categoriesButton = new JButton("Categories...");
		pnlButtons.add(categoriesButton);
		
		String[] months = new DateFormatSymbols().getMonths();
		cmbMonth = new JComboBox(months);
		cmbMonth.setName("monthCombo");
		
		pnlButtons.add(cmbMonth);
		
		String[] years = {"2019","2020","2021"};
		cmbYear = new JComboBox(years);
		cmbYear.setName("yearCombo");
		
		pnlButtons.add(cmbYear);

		balance = new JTextField(8);
		pnlButtons.add(balance);

		balanceUpdate = new JButton("Update");
		pnlButtons.add(balanceUpdate);
		balanceUpdate.addActionListener((e)->{
			updateBalance();
		});

		lblTotalDebitLate = new JLabel("Debit Late:");
		lblTotalDebitLateResult = new JLabel();
		pnlButtons.add(lblTotalDebitLate);
		pnlButtons.add(lblTotalDebitLateResult);

		checkBox = new JCheckBox("Show only PENDING");
		pnlButtons.add(checkBox);
		checkBox.addItemListener((e)->{
			if(e.getStateChange() == ItemEvent.SELECTED){
				List<Transaction> pendingTransactions = transactions.stream()
						.filter(t -> t.getStatus().equals(Transaction.Status.PENDING))
						.collect(Collectors.toList());
				table.setModel(new TransactionTableModel(pendingTransactions));
			} else {
				table.setModel(new TransactionTableModel(transactions));
			}
		});

		pnlTotals = new JPanel();
		contentPane.add(pnlTotals, BorderLayout.SOUTH);
		
		JLabel lblTotalDebit = new JLabel("Total debit:");
		
		lblTotalDebitResult = new JLabel("...");
		lblTotalCredit = new JLabel("Total credit:");
		
		lblTotalCreditResult = new JLabel("...");
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

	}

	private void updateBalance(){
        BigDecimal balanceValue = new BigDecimal(balance.getText());
        List<Transaction> pendingTransactions = transactions.stream()
				.filter(t -> t.getStatus().equals(Transaction.Status.PENDING))
				.collect(Collectors.toList());
        table.setModel(new TransactionTableModel(service.getTransactionsWithBalance(pendingTransactions,balanceValue)));
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
	            int newRow = table.convertRowIndexToModel(row);
	            Transaction selected = model.getTransaction(newRow);
	            new TransactionDialog(frame,selected).setVisible(true);
	        }
	    }
	}
	
	class ComboActionListener implements ActionListener {
		
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
