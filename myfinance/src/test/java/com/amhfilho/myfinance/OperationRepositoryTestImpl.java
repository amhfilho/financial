package com.amhfilho.myfinance;

import java.util.List;

public class OperationRepositoryTestImpl implements OperationRepository {
    private List<Operation> operations;

    public OperationRepositoryTestImpl(List<Operation> operations){
        this.operations = operations;
    }

    @Override
    public List<Operation> findAll() {
        return this.operations;
    }
}
