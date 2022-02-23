package ro.fasttrackit.curs20.service;

import org.springframework.stereotype.Component;
import ro.fasttrackit.curs20.model.Transaction;
import ro.fasttrackit.curs20.model.Type;

import java.util.List;

@Component
public class TransactionDataProvider {

    public List<Transaction> getTransactions() {
        return List.of(
                new Transaction(1, "Casti", Type.BUY, 9),
                new Transaction(2, "Mouse", Type.SELL, 20),
                new Transaction(3, "Casti", Type.BUY, 7),
                new Transaction(4, "Laptop", Type.BUY, 5));
    }
}
