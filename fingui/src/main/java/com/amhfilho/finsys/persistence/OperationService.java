package com.amhfilho.finsys.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.amhfilho.finsys.gui.operation.OperationRepository;

public class OperationService {
	
	private OperationRepository repository;
	
	public OperationService(OperationRepository repository) {
		this.repository = repository;
	}
	
	public List<Transaction> findTransactionFrom(YearMonth yearMonth){
		List<Operation> operations = this.repository.findAll();
		List<Transaction> transactions = new ArrayList<>();
		for(Operation operation: operations) {
			
		}
		
		return transactions;
	}
	
	private EntityManager em;
	
	public void setup() {
		em.createQuery("select o from Operation o").getResultList();
	}
	
	public OperationService(EntityManager em) {
		this.em = em;
	}
	
	public List<Operation> findAll(){
		return em.createQuery("select o from Operation o order by o.initialDate",Operation.class).getResultList();
	}
	
	public void save(Operation operation) {
		this.em.getTransaction().begin();
		if(operation.getId() == null) this.em.persist(operation);
		else this.em.merge(operation);
		this.em.getTransaction().commit();
	}
	
	public void delete(Operation operation) {
		this.em.getTransaction().begin();
		this.em.remove(operation);
		this.em.getTransaction().commit();
	}
	
	
	
	public static void main(String[] args) throws IOException, ParseException {
		
		//List<List<String>> records = new ArrayList<>();
		List<Operation> operations = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("c:/Users/mult-e/Downloads/extrato.csv"), "UTF8"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(";");
		        operations.add(createOperation(values));
		       
		    }
		}
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		String[] input = {
				"30/03/2019",
				"Salário",
				"1872,92",
				"Salario",
				"",
				"",
				"",
				"PENDING"
		};
		Operation operation = createOperation(input);
		em.persist(operation);
		em.getTransaction().commit();
		em.close();
		
	}
	
	private static Operation createOperation(String[] data) throws ParseException {
		Operation operation = new Operation();
		operation.setInitialDate(parse(data[0]));
		operation.setDescription(data[1]);
		double amount = DecimalFormat.getInstance().parse(data[2]).doubleValue();
		operation.setAmount(BigDecimal.valueOf(amount));
		operation.setCategory(data[3]);
		operation.setInstallments(parseInteger(data[5]));
		operation.setPayCheckNumber(data[6]);
		if(operation.getPayCheckNumber()!=null && !operation.getPayCheckNumber().equals("")) {
			operation.setPayCheckDoneDate(operation.getInitialDate());
		}
		operation.setStatus(OperationStatus.valueOf(data[7]));
		return operation;
	}
	
	private static LocalDate parse(String input) {
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(input, f2);
	}
	
	private static Integer parseInteger(String input) {
		if(input==null || "".equals(input)) {
			input = "0";
		}
		return Integer.parseInt(input);
	}

}
