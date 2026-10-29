
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountStore {

    private final Map<String, Account> accounts = new HashMap<>();

    private final ArrayList<String> transactionHistory =
            new ArrayList<>();

    public void add(Account account) {
        accounts.put(account.getAccountNumber(), account);
        transactionHistory.add(
                "Account opened: " + account.getAccountNumber());
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }

    public void recordTransaction(String message) {
        transactionHistory.add(message);
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public List<Account> sortedByBalance() {
        List<Account> result = new ArrayList<>(accounts.values());

        result.sort(
                Comparator.comparingDouble(Account::getBalance)
                        .reversed()
        );

        return result;
    }

    public int size() {
        return accounts.size();
    }
}
