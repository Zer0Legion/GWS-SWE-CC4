package balancer.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The class encapsulating the logic behind the app.
 */
public class Logic {
    private ArrayList<String> names;
    private ArrayList<Float> ledger;
    private StringBuilder answer;
    private float totalImbalance;
    private int nTransactions;

    /**
     * Constructor for Logic class. Initializes the payments map.
     * @param map The mapping for payments.
     * @param average The average amount per person to be paid.
     */
    public Logic(HashMap<String, Float> map, float average) {
        this.answer = new StringBuilder();
        this.names = new ArrayList<>(map.keySet());
        this.ledger = computeLedger(map, average);
        this.totalImbalance = calculateImbalance();
    }

    /**
     * Calculates the outstanding imbalance in accounts. Used for rounding.
     * @return The outstanding imbalance.
     */
    float calculateImbalance() {
        return this.ledger.stream().reduce(0F, (x, y) -> x + Math.max(0, y));
    }

    /**
     * Getter for the list of names.
     * @return The list of names.
     */
    ArrayList<String> getNames() {
        return this.names;
    }

    /**
     * Getter for the ledger.
     * @return The ledger.
     */
    ArrayList<Float> getLedger() {
        return this.ledger;
    }

    /**
     * Creates a ledger of outstanding balances with reference to the average amount paid.
     * @param map The mapping of payments.
     * @param average The average amount paid.
     * @return The ledger taking the average as reference.
     */
    ArrayList<Float> computeLedger(HashMap<String, Float> map, float average) {
        return new ArrayList<>(map.values()).stream()
                .map(x -> x - average).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * The getter for answer string.
     * @return The answer string to be returned by the app.
     */
    public String getAnswer() {
        return this.answer.toString();
    }

    /**
     * Calculates the answer to the problem.
     */
    public void calculateAnswer() {
        while (!ledger.isEmpty() && totalImbalance > 0.01) {

            float payerOwes = pollMin();
            String payerName = pollName(payerOwes);
            float payeeOwed = pollMax();
            String payeeName = pollName(payeeOwed);

            handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        }

        this.answer.append(String.format("Number of transactions: %d", nTransactions));
    }

    /**
     * Returns and removes the current minimum value in the ledger.
     * @return The minimum value in the ledger.
     */
    float pollMin() {
        float value = ledger.stream().reduce(Float.MAX_VALUE, Math::min);
        return value;
    }

    /**
     * Returns the current maximum value in the ledger.
     * @return The maximum value in the ledger.
     */
    float pollMax() {
        float value = ledger.stream().reduce(Float.MIN_VALUE, Math::max);
        return value;
    }

    /**
     * Returns and removes the name and value corresponding to the amount in the ledger.
     * @param amount The amount the person owes or is owed.
     * @return The person's name.
     */
    String pollName(float amount) {
        String name = names.get(ledger.indexOf(amount));
        names.remove(name);
        ledger.remove(amount);
        return name;
    }

    /**
     * Handles a single transaction between a payer and a payee.
     * @param payerOwes The amount the payer owes in total.
     * @param payerName The payer's name.
     * @param payeeOwed The amount the payee is owed in total.
     * @param payeeName The payee's name.
     */
    void handleTransaction(float payerOwes, String payerName, float payeeOwed, String payeeName) {
        float amountPaid = (float) Math.round(Math.min(payeeOwed, -payerOwes) * 100) / 100;
        totalImbalance -= amountPaid;

        if (payerOwes == -payeeOwed) { // Both cancel out
            addAnswer(payerName, payeeName, payeeOwed);
        } else if (-payerOwes > payeeOwed) { // Payer still owes money after evening out with payee
            addAnswer(payerName, payeeName, amountPaid);
            names.add(payerName);
            ledger.add(payerOwes + amountPaid);
        } else { // Payee is still owed money
            addAnswer(payerName, payeeName, amountPaid);
            names.add(payeeName);
            ledger.add(payeeOwed - amountPaid);
        }
        nTransactions++;
    }

    /**
     * Formats the lowercase string into Title Case.
     * @param name The name of the person.
     * @return The Title Case formatted name.
     */
    String toName(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] parts = name.split(" ");
        for (int i = 0; i < parts.length; i++) {
            stringBuilder.append(String.valueOf(parts[i].charAt(0)).toUpperCase());
            stringBuilder.append(parts[i].substring(1));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().strip();
    }
    public void addAnswer(String payer, String payee, Float amount) {
        this.answer.append(String.format("%s pays %s $%.2f%n", toName(payer), toName(payee), amount));
    }
}
