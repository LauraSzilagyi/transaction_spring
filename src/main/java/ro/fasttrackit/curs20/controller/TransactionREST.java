package ro.fasttrackit.curs20.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs20.model.Transaction;
import ro.fasttrackit.curs20.service.TransactionService;
import ro.fasttrackit.curs20.model.Type;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TransactionREST {

    private final TransactionService service;

    public TransactionREST(TransactionService transactionService) {
        service = transactionService;
    }

    @GetMapping(path = "transactions")
    ResponseEntity<List<Transaction>> getAll(@RequestParam(required = false) String product,
                                             @RequestParam(required = false) String type,
                                             @RequestParam(required = false) Double minAmount,
                                             @RequestParam(required = false) Double maxAmount) {
        List<Transaction> transactions = service.getAll(product, type, minAmount, maxAmount);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping(path = "transactions/{id}")
    ResponseEntity<Transaction> getById(@PathVariable int id) {
        Transaction transaction = service.getById(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping(path = "transactions")
    ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction newTransaction = service.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @PutMapping(path = "transactions/{id}")
    ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction,
                                                  @PathVariable int id) {
        Transaction newTransaction = service.updateTransaction(id, transaction);
        return ResponseEntity.ok(newTransaction);
    }

    @DeleteMapping(path = "transactions/{id}")
    ResponseEntity<Transaction> deleteTransaction(@PathVariable int id){
        Transaction deleted = service.delete(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping(path = "transactions/reports/type")
    ResponseEntity<Map<Type,List<Transaction>>> getTypeMap(){
        Map<Type,List<Transaction>> response = service.getMapOfTypes();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "transactions/reports/product")
    ResponseEntity<Map<String,List<Transaction>>> getMapOfProducts(){
        Map<String,List<Transaction>> response = service.getMapOfProducts();
        return ResponseEntity.ok(response);
    }
}

