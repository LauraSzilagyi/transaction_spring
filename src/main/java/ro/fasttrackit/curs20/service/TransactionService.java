package ro.fasttrackit.curs20.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.curs20.model.Type;
import ro.fasttrackit.curs20.exceptions.TransactionNotFoundException;
import ro.fasttrackit.curs20.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static int ID_SEQUENCE = 1;
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionService(TransactionDataProvider datas) {
        adjustIdSequence(datas);
        transactions.addAll(datas.getTransactions());
    }

    private void adjustIdSequence(TransactionDataProvider datas) {
        Optional<Integer> maxID = datas.getTransactions().stream()
                .max(Comparator.comparing(Transaction::id))
                .map(Transaction::id)
                .stream().findFirst();
        maxID.ifPresent(integer -> ID_SEQUENCE = ++integer);
    }


    public List<Transaction> getAll(String product, String type, Double maxAmount, Double minAmount) {
        return transactions.stream()
                .filter(transaction -> Objects.isNull(product) || product.equalsIgnoreCase(transaction.product()))
                .filter(transaction -> Objects.isNull(type) || type.equalsIgnoreCase(transaction.type().name()))
                .filter(transaction -> Objects.isNull(maxAmount) || transaction.amount() >= maxAmount)
                .filter(transaction -> Objects.isNull(minAmount) || transaction.amount() <= minAmount)
                .collect(Collectors.toList());
    }

    public Transaction getById(int id) {
        return transactions.stream().
                filter(transaction -> transaction.id() == id)
                .findFirst()
                .orElseThrow(TransactionNotFoundException::new);
    }

    public Transaction addTransaction(Transaction transaction) {
        Transaction toAdd = new Transaction(ID_SEQUENCE, transaction.product(), transaction.type(), transaction.amount());
        transactions.add(toAdd);
        ID_SEQUENCE++;
        return toAdd;
    }

    public Transaction updateTransaction(int id, Transaction transaction) {
        Transaction transactionById = getById(id);
        Transaction newTransaction = new Transaction(id, transaction.product(), transaction.type(), transaction.amount());
        transactions.remove(transactionById);
        transactions.add(newTransaction);
        return newTransaction;
    }

    public Transaction delete(int id) {
        Transaction transaction = getById(id);
        transactions.remove(transaction);
        return transaction;
    }

    public Map<Type, List<Transaction>> getMapOfTypes() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));
    }

    public Map<String, List<Transaction>> getMapOfProducts() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::product));
    }
}
