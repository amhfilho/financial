package com.amhfilho.myfinance.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationResource {

    private static final Logger log = LoggerFactory.getLogger(OperationResource.class.getName());

    @Autowired
    private OperationRepository repository;

    @PutMapping(value = "")
    public ResponseEntity<Operation> create(@RequestBody Operation operation){
        log.info("Saving operation {}",operation);
        repository.save(operation);
        log.info("Operation saved");
        return ResponseEntity.status(HttpStatus.CREATED).body(operation);
    }

    @GetMapping(value = "")
    public List<Operation> findAll(){
        log.info("Reading all operations");
        return repository.findAll();
    }
}
