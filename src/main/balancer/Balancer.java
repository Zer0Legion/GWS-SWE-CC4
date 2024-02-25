package src.main.balancer;

import src.main.balancer.io.Parser;

import java.util.Scanner;

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
                break;
            } else {
                parser.parse(input);
            }
        }
    }
}
