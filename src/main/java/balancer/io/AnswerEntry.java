package balancer.io;

/**
 * Encapsulates an entry for the final answer.
 */
public class AnswerEntry {
    private String payer;
    private String payee;
    private float amount;

    /**
     * Constructor for an answer entry.
     * @param payer The person who pays the amount.
     * @param payee The person who receives the amount.
     * @param amount The amount to be paid.
     */
    public AnswerEntry(String payer, String payee, float amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
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

    /**
     * Overloaded toString method to specify rounding.
     * @param isRounded whether to round the amounts.
     * @return The string representation for the entry.
     */
    public String toString(boolean isRounded) {
        return isRounded
                ? String.format("%s pays %s $%d.%n", toName(payer), toName(payee), Math.round(amount))
                : String.format("%s pays %s $%.2f.%n", toName(payer), toName(payee), amount);
    }
}
