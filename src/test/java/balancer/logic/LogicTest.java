package balancer.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

public class LogicTest {
    private Logic logic;
    HashMap<String, Float> map;
    float average;

    @BeforeEach
    public void setUp() {
        map = new HashMap<>();
        map.put("alice", 20F);
        map.put("bob", 30F);
        map.put("charlie", 40F);
        average = 30F;
        logic = new Logic(map, average);
    }

    @Test
    public void testCalculateImbalance() {
        float expected = 10F;
        assertEquals(expected, logic.calculateImbalance());
    }

    @Test
    public void testGetLedgerSize() {
        assertEquals(3, logic.computeLedger(map, average).size());
    }

    @Test
    public void testPollMin() {
        float min = logic.pollMin();
        assertEquals(-10F, min);
    }

    @Test
    public void testPollMax() {
        float max = logic.pollMax();
        assertEquals(10F, max);
    }

    @Test
    public void testPollName() {
        float amount = -10F;
        String name = logic.pollName(amount);
        assertEquals("alice", name);
    }

    @Test
    public void testPollNameRemovesName() {
        float amount = -10F;
        String name = logic.pollName(amount);
        assertFalse(logic.getNames().contains(name));
    }

    @Test
    public void testPollNameRemovesLedger() {
        float amount = -10F;
        logic.pollName(amount);
        assertFalse(logic.getLedger().contains(amount));
    }

    @Test
    public void testHandleTransactionBothEqual() {
        String payerName = "alice";
        String payeeName = "charlie";
        float payerOwes = -10F;
        float payeeOwed = 10F;
        logic.handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        String expectedAnswer = "Alice pays Charlie $10.00\r\n";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testHandleTransactionPayerMore() {
        String payerName = "alice";
        String payeeName = "charlie";
        float payerOwes = -20F;
        float payeeOwed = 10F;
        logic.handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        String expectedAnswer = "Alice pays Charlie $10.00\r\n";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testHandleTransactionPayeeMore() {
        String payerName = "alice";
        String payeeName = "charlie";
        float payerOwes = -10F;
        float payeeOwed = 20F;
        logic.handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        String expectedAnswer = "Alice pays Charlie $10.00\r\n";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testToName() {
        String name = "old mcdonald";
        String expected = "Old Mcdonald";
        String actual = logic.toName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNames() {
        assertTrue(logic.getNames().contains("alice"));
    }

    @Test
    public void testGetLedger() {
        assertTrue(logic.getLedger().contains(-10F));
    }


}
