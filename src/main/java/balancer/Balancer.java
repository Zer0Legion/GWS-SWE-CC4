package balancer;

import java.util.Scanner;

import balancer.io.Parser;
import balancer.logic.Logic;

/**
 * The main class for the Balancer app.
 * The input/output will be in the format provided by the Word document.
 */
public class Balancer {
    /**
     * The main method to run the program.
     * @param args Any additional arguments will be ignored.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        while (true) {
            String input = scanner.nextLine();
            if (input.isBlank()) {
                parser.computeAverage();
                Logic logic = new Logic(parser.getPayments(), parser.getAverage());
                logic.calculateAnswer();
                System.out.println(logic.getAnswer());
                break;
            } else {
                parser.parse(input);
            }
        }
    }
}
