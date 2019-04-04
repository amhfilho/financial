package com.amhfilho.fingui;

import java.util.List;

import com.amhfilho.finsys.gui.operation.OperationRepository;
import com.amhfilho.finsys.persistence.Operation;

public class TestOperationRepository implements OperationRepository {
	
	private List<Operation> operations;
	
	public TestOperationRepository(List<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public List<Operation> findAll() {
		return operations;
	}

	@Override
	public void save(Operation operation) {
		
	}

	@Override
	public void delete(Operation operation) {
		
	}

	
}
