package com.amhfilho.myfinance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents an actual Checking Account from a Bank. Contains one or more Operations and an actual
 * Balance value. It is delimited by a date range, set by initial date and end date.
 * This is a mutable class that stores the balance value as its state.
 * The main goal of this class is to provide a forecast of a concrete checking account state - its balance -
 * in a specific period of time.
 */
public class CheckingAccount {
    /**
     * Natural identifier, unique value among other CheckingAccounts
     */
    private String name;

    /**
     * The state of the account, its actual balance. This balance is the amount of cash in the account at the present
     * time. Can be changed during the object lifecycle
     */
    private BigDecimal balance;

    /**
     * The list of the operations of this account. Could be at any date range, from past or future
     */
    private List<Operation> operations;

    /**
     * Returns the balance for this account on specific date, calculated by the operations of this account
     * @param date the given date of the Balance requested
     * @return the Balance for this date
     */
    public BigDecimal getBalanceOn(LocalDate date){
        if(date==null) throw new IllegalArgumentException("Date must not be null");
        if(date.isBefore(LocalDate.now())) return this.balance;

        Collections.sort(this.operations);
        BigDecimal tempBalance = BigDecimal.valueOf(this.balance.doubleValue());
        DateRange dateRange = new DateRange(date, LocalDate.now());
        for(Operation o: this.operations){
            if(dateRange.contains(o.getDate())){
                tempBalance = tempBalance.add(o.getValue());
            }
        }
        return tempBalance;
    }

    /**
     * Creates a CheckingAccount with no balance set. in this case the CheckingAccount is created with a default value 0
     * @param name the name of this account
     */
    public CheckingAccount(String name) {
        this.name = name;
        this.balance = new BigDecimal("0");
        this.operations = new ArrayList<>();
    }

    /**
     * Creates a CheckingAccount with a given name and balance
     * @param name the name of this CheckingAccount
     * @param balance the initial balance, represents the balance of the account now
     */
    public CheckingAccount(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.operations = new ArrayList<>();
    }

    /**
     * Creates a CheckingAccount with a given name, balance and operation list
     * @param name the name of this CheckingAccount
     * @param balance the initial balance, represents the balance of the account now
     * @param operations the List of the operations
     */
    public CheckingAccount(String name, BigDecimal balance, List<Operation> operations) {
        this.name = name;
        this.balance = balance;
        this.operations = operations;
    }

    /**
     * Add a list of operations to the current list
     * @param operations the list of operations to be added
     */
    public void addOperations(List<Operation> operations){
        this.operations.addAll(operations);
    }

    /**
     * Add a single operation to the current list
     * @param operation the operation to be added
     */
    public void addOperation(Operation operation){
        this.operations.add(operation);
    }

    /**
     * Removes an operation from the list
     * @param operation the operation to be removed
     */
    public void removeOperation(Operation operation){
        this.operations.remove(operation);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
