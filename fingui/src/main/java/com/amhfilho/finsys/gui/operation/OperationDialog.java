package com.amhfilho.finsys.gui.operation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.amhfilho.finsys.persistence.Operation;

@SuppressWarnings("serial")
public class OperationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField dateField;
	private JTextField descriptionField;
	private JTextField amountField;
	private JTextField categoryField;
	private JCheckBox undefinedCheckBox;
	
	private Operation operation;
	private OperationRepository repository;
	private JTextField installmentsField;
	
	public OperationDialog(Operation operation, OperationRepository repository, OperationFrame frame) {
		super(frame);
		this.repository = repository;
		initGUI();
		initOperation(operation);
	}
	
	private void save() {
		if(operation == null) operation = new Operation();
		operation.setAmount(new BigDecimal(amountField.getText()));
		operation.setInitialDate(LocalDate.parse(dateField.getText(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		operation.setDescription(descriptionField.getText());
		operation.setCategory(categoryField.getText());
		operation.setInstallments(Integer.parseInt(installmentsField.getText()));
		if(undefinedCheckBox.isSelected()) {
			operation.setInstallments(0);
		}
		repository.save(operation);
		((OperationFrame)getParent()).loadOperations();
		dispose();
	}
	
	private void initOperation(Operation operation) {
		this.operation = operation;
		if(operation!=null) {
			dateField.setText(this.operation.getInitialDate().toString());
			descriptionField.setText(this.operation.getDescription());
			amountField.setText(this.operation.getAmount().toString());
			categoryField.setText(this.operation.getCategory());
			installmentsField.setText(this.operation.getInstallments()==null?"0":this.operation.getInstallments().toString());
			undefinedCheckBox.setSelected(this.operation.isUndefined());	
		}
	}
	
	
	public void initGUI() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setTitle("Edit Operation");
		setBounds(100, 100, 355, 290);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblInitialDate = new JLabel("Initial Date:");
		
		dateField = new JTextField();
		dateField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		
		descriptionField = new JTextField();
		descriptionField.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount:");
		
		amountField = new JTextField();
		amountField.setColumns(10);
		
		JLabel lblInstallments = new JLabel("Installments:");
		
		undefinedCheckBox = new JCheckBox("Undefined");
		
		JLabel lblCategory = new JLabel("Category:");
		
		categoryField = new JTextField();
		categoryField.setColumns(10);
		
		installmentsField = new JTextField();
		installmentsField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblInitialDate)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDescription)
								.addComponent(lblAmount)
								.addComponent(lblInstallments))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(installmentsField, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(undefinedCheckBox))
								.addComponent(amountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(descriptionField, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblCategory)
							.addGap(18)
							.addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInitialDate)
						.addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(descriptionField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmount)
						.addComponent(amountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInstallments)
						.addComponent(installmentsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(undefinedCheckBox))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategory)
						.addComponent(categoryField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener((e) -> {
					save();
				} );
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener((e)->{
					dispose();
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
