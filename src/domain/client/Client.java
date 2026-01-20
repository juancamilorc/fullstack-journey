package domain.client;

import domain.account.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Client {

    private final String id;
    private final String name;
    private final String document;
    private final List<Account> accounts = new ArrayList<>();

    public Client(String id, String name, String document) {
        this.id = validateRequired(id, "id");
        this.name = validateRequired(name, "name");
        this.document = validateRequired(document, "document");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDocument() { return document; }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(Account account) {
        accounts.add(Objects.requireNonNull(account, "account"));
    }

    // Validación centralizada
    private static String validateRequired(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return trimmed;
    }
}
