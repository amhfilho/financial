package com.amhfilho.myfinance.operation;

import java.util.List;

public interface OperationRepository {

    List<Operation> findAll();
}
