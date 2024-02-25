package balancer.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputParserTest {
    private static final String INCORRECT_FORMAT = "Please input an entry like this!\n{name},{amount paid}\r\n";
    private InputParser inputParser;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        inputParser = new InputParser();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testParseNPaxWithValidInput() {
        String testInput = "Alice,20";
        float expectedNPax = 1;

        inputParser.parse(testInput);
        assertEquals(expectedNPax, inputParser.getnPax());
    }

    @Test
    public void testParseTotalWithValidInput() {
        String testInput = "Alice,20";
        float expectedTotal = 20;

        inputParser.parse(testInput);
        assertEquals(expectedTotal, inputParser.getTotal());
    }

    @Test
    public void testParsePaymentsWithValidInput() {
        String testInput = "Alice,20";
        HashMap<String, Float> expectedMapping = new HashMap<>();
        expectedMapping.put("alice", 20F);

        inputParser.parse(testInput);
        assertEquals(expectedMapping, inputParser.getPayments());
    }

    @Test
    public void testSamePersonMultiplePayments() {
        String testInput = "Alice,20";
        HashMap<String, Float> expectedMapping = new HashMap<>();
        expectedMapping.put("alice", 40F);
        inputParser.parse(testInput);
        inputParser.parse(testInput);
        assertEquals(expectedMapping, inputParser.getPayments());
    }

    @Test
    public void testAverageNotComputedBeforeTermination() {
        String testInput = "Alice,20";
        inputParser.parse(testInput);
        assertEquals(0F, inputParser.getAverage());
    }
    @Test
    public void testEmptyInputAverageComputed() {
        String testInput1 = "Alice,20";
        inputParser.parse(testInput1);
        inputParser.computeAverage();
        float expectedAverage = 20F;
        assertEquals(expectedAverage, inputParser.getAverage());
    }

    @Test
    public void testParseInvalidFloatNumberFormatWarning() {
        String testInput = "Alice, not_float";
        inputParser.parse(testInput);
        assertEquals(INCORRECT_FORMAT, outContent.toString());
    }

    @Test
    public void testParseNotEnoughParamsNumberFormatWarning() {
        String testInput = "alice";
        inputParser.parse(testInput);
        assertEquals(INCORRECT_FORMAT, outContent.toString());
    }
}
