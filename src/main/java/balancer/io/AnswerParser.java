package balancer.io;

import java.util.ArrayList;

/**
 * The class encapsulating the formatting for the answer.
 */
public class AnswerParser {

    private ArrayList<AnswerEntry> entries;
    private StringBuilder answer;
    private int nTransactions;
    private boolean allRounded;

    /**
     * Constructor for the answer parser class.
     */
    public AnswerParser() {
        this.answer = new StringBuilder();
        this.nTransactions = 0;
        this.entries = new ArrayList<>();
        this.allRounded = true;
    }

    @Override
    public String toString() {
        for (AnswerEntry answerEntry : entries) {
            answer.append(answerEntry.toString(allRounded));
        }
        appendNTransactions();
        return this.answer.toString();
    }

    private void incrementNTransactions() {
        this.nTransactions++;
    }

    private void appendNTransactions() {
        this.answer.append(String.format("Number of transactions: %d", nTransactions));
    }


    /**
     * Adds an answer entry.
     * @param payer The person who pays the amount.
     * @param payee The person who receives the amount.
     * @param amount The amount to be paid.
     */
    public void addAnswer(String payer, String payee, Float amount) {
        if (Math.abs(amount - Math.round(amount)) > 0.001) {
            this.allRounded = false;
        }
        this.entries.add(new AnswerEntry(payer, payee, amount));
        incrementNTransactions();
    }
}
