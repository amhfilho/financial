package com.amhfilho.finsys.gui.transaction;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.amhfilho.finsys.persistence.PersistenceUtil;
import com.amhfilho.finsys.persistence.Transaction;
import com.amhfilho.finsys.persistence.TransactionService;

@SuppressWarnings("serial")
public class TransactionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDate;
	private JTextField txtDescription;
	private JTextField txtAmount;
	private TransactionFrame parent;
	private Transaction transaction;
	
	public TransactionDialog(TransactionFrame parent, Transaction transaction) {
		this(parent);
		if(transaction != null) {
			this.transaction = transaction;
			this.txtDate.setText(transaction.getTransactionDate().toString());
			this.txtDescription.setText(transaction.getDescription());
			this.txtAmount.setText(transaction.getAmount().toString());
			
		} else {
			this.transaction = new Transaction();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TransactionDialog(TransactionFrame parent) {
		super(parent);
		this.parent = parent;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Transaction Editor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblDate = new JLabel("Date:");
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount:");
		
		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		
		JLabel lblRepeatFor = new JLabel("Repeat for:");
		
		JSpinner spnRepeat = new JSpinner();
		
		JLabel lblMonths = new JLabel("months");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDescription)
								.addComponent(lblDate)
								.addComponent(lblAmount))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblRepeatFor)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnRepeat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblMonths)))
					.addContainerGap(131, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate)
						.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmount)
						.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRepeatFor)
						.addComponent(spnRepeat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMonths))
					.addContainerGap(79, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new OkButtonActionListener(this));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new CancelButtonActionListener(this));
			}
		}
	}

	class CancelButtonActionListener implements ActionListener {
		private TransactionDialog dialog;

		public CancelButtonActionListener(TransactionDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			int response = JOptionPane.showConfirmDialog(
					dialog, 
					"Do you want to cancel?", 
					"Cancel dialog",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE);
			
			if(response == 0) dialog.dispose();

		}
	}
	
	class OkButtonActionListener implements ActionListener {
		private TransactionDialog dialog;
		
		public OkButtonActionListener(TransactionDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(txtDate.getText(), formatter);
			
			BigDecimal amount = new BigDecimal(txtAmount.getText());
			
			transaction.setAmount(amount);
			transaction.setDescription(txtDescription.getText());
			transaction.setTransactionDate(date);
			//TransactionService service = new TransactionService(new DatabaseTransactionPersistenceUtil.getEntityManager());
			//service.save(transaction);
			dialog.parent.updateTransactionsForCurrentMonth();
			dialog.dispose();
			
		}
		
	}
}
