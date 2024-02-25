package balancer.io;

import java.util.HashMap;

/**
 * Class to handle text parsing related to the Balancer app.
 *
 */
public class Parser {
    private static final String INCORRECT_FORMAT = "Please input an entry like this!\n{name},{amount paid}";

    /**
     * HashMap to store the payment mappings. It is chosen to handle multiple inputs from the same name.
     */
    private HashMap<String, Float> payments;

    private float total;
    private int nPax;
    private float average;

    /**
     * Constructor for the Parser class. Creates a parser to track information related to the app.
     */
    public Parser() {
        this.payments = new HashMap<>();
        this.total = 0;
        this.nPax = 0;
    }

    /**
     * The getter method for total amount paid.
     * @return The total amount of money paid.
     */
    public float getTotal() {
        return this.total;
    }

    /**
     * The getter method for number of people.
     * @return The total number of people.
     */
    public int getnPax() {
        return this.nPax;
    }

    /**
     * The getter method for payment mappings.
     * @return The mappings for payments.
     */
    public HashMap<String, Float> getPayments() {
        return this.payments;
    }

    /**
     * The getter method for average amount paid.
     * @return The average amount of money paid.
     */
    public float getAverage() {
        return this.average;
    }

    /**
     * Handles the logic for a payment input. Allows for multiple inputs with the same payer.
     * @param name The person who paid for the item.
     * @param paid The amount paid.
     */
    private void handlePayment(String name, float paid) {
        if (payments.containsKey(name)) {
            payments.put(name, payments.get(name) + paid);
        } else {
            payments.put(name, paid);
            nPax++;
        }
        total += paid;
    }

    /**
     * Computes the average amount of money paid.
     */
    public void computeAverage() {
        this.average = getTotal() / getnPax();
    }

    /**
     * Parses a raw input string from the user.
     * @param str The string to be parsed.
     */
    public void parse(String str) {
        String[] params = str.split(",");
        if (params.length != 2) {
            System.out.println(INCORRECT_FORMAT);
        } else {
            String name = params[0].strip().toLowerCase();
            try {
                float paid = Float.parseFloat(params[1]);
                handlePayment(name, paid);
            } catch (NumberFormatException e) {
                System.out.println(INCORRECT_FORMAT);
            }
        }
    }
}
