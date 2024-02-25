package balancer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalancerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void providedTestcase1() {
        String providedInput = "Ali,40.105\nBob,40.105\nCharlie,10\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected = "Charlie pays Bob $10.04.\r\nCharlie pays Ali $10.03.\r\nNumber of transactions: 2\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void providedTestcase2() {
        String providedInput = "Ali,40\nBob,40\nCharlie,10\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected = "Charlie pays Bob $10.\r\nCharlie pays Ali $10.\r\nNumber of transactions: 2\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void providedTestcase3() {
        String providedInput = "Ali,10\nBob,20\nCharlie,0\nDon,10\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected = "Charlie pays Bob $10.\r\nNumber of transactions: 1\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void providedTestcase4() {
        String providedInput = "Alice,40\nBob,40\nCharlie,10\nDon,10\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected = "Don pays Bob $15.\r\nCharlie pays Alice $15.\r\nNumber of transactions: 2\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void providedTestcase5() {
        String providedInput = "Alice,200\nBob,80\nCharlie,50\nDon,20\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected =
                "Don pays Alice $67.50.\r\n"
                        + "Charlie pays Alice $37.50.\r\nBob pays Alice $7.50.\r\nNumber of transactions: 3\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void providedTestcase6() {
        String providedInput = "Alice,160\nBob,120\nCharlie,50\nDon,20\n\n";
        System.setIn(new ByteArrayInputStream(providedInput.getBytes()));
        Balancer.main(new String[]{providedInput});
        String expected =
                "Don pays Alice $67.50.\r\n"
                        + "Charlie pays Bob $32.50.\r\nCharlie pays Alice $5.00.\r\nNumber of transactions: 3\r\n";
        assertEquals(expected, outContent.toString());
    }
}
