package com.amhfilho.myfinance;

import java.util.List;

public interface OperationRepository {

    List<Operation> findAll();
}
