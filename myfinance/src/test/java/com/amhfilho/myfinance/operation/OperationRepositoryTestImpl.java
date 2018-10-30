package com.amhfilho.myfinance.operation;

import com.amhfilho.myfinance.operation.Operation;
import com.amhfilho.myfinance.operation.OperationRepository;

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
