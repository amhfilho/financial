package com.amhfilho.finsys.gui.transaction;

import com.amhfilho.finsys.persistence.Transaction;

import javax.swing.*;
import java.math.BigDecimal;

public class TransactionDialog extends JDialog{
    private JPanel transactionPanel;
    private JPanel labelPanel;
    private JPanel textFieldPanel;
    private JPanel buttonPanel;
    private JLabel dateLabel;
    private JLabel descriptionLabel;
    private JLabel amountLabel;
    private JTextField dateField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JLabel statusLabel;
    private JComboBox statusCombo;
    private JButton okButton;

    private Transaction transaction;


    public TransactionDialog(JFrame parent, Transaction transaction) {
        super(parent);
        setModal(true);
        setTitle("Transaction");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statusCombo.setModel(new StatusComboBoxModel());
        okButton.addActionListener((event)->{
            updateTransaction();
            dispose();
        });
        fillTransaction(transaction);
        getContentPane().add(transactionPanel);
        pack();
    }

    private void fillTransaction(Transaction transaction){
        this.transaction = transaction;
        dateField.setText(transaction.getTransactionDate().toString());
        descriptionField.setText(transaction.getDescription());
        amountField.setText(transaction.getAmount().toString());
        statusCombo.setSelectedIndex(transaction.getStatus().ordinal());
    }

    private void updateTransaction(){
        transaction.setDescription(descriptionField.getText());
        transaction.setAmount(new BigDecimal(amountField.getText()));
        transaction.setStatus((Transaction.Status)(statusCombo.getModel()).getSelectedItem());
        TransactionFrame frame = ((TransactionFrame)getParent());
        frame.save(transaction);
        frame.updateTransactionsForCurrentMonth();
    }
}
