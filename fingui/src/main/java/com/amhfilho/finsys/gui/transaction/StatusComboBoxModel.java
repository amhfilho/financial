package com.amhfilho.finsys.gui.transaction;

import com.amhfilho.finsys.persistence.Transaction;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class StatusComboBoxModel implements ComboBoxModel<Transaction.Status> {

    private Transaction.Status selected;

    public StatusComboBoxModel(){
        setSelectedItem(Transaction.Status.PENDING);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Transaction.Status)anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public Transaction.Status getElementAt(int index) {
        switch(index){
            case 0: return Transaction.Status.PENDING;
            case 1: return Transaction.Status.DONE;
            case 2: return Transaction.Status.LATE;
        }
        return null;
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
