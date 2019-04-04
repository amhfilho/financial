package com.amhfilho.finsys.gui.operation;

import java.util.List;

import com.amhfilho.finsys.persistence.Operation;

public interface OperationRepository {
	
	List<Operation> findAll();
	
	void save(Operation operation);
	
	void delete(Operation operation);
	
	
}
