package balancer.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogicTest {
    private Logic logic;
    private HashMap<String, Float> map;
    private float average;

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
        float amount = 10F;
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
        String expectedAnswer = "Alice pays Charlie $10.\r\nNumber of transactions: 1";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testCalculateAnswerLedgerSize() {
        logic.calculateAnswer();
        assertEquals(1, logic.getLedger().size());
    }

    @Test
    public void testCalculateAnswerNamesSize() {
        logic.calculateAnswer();
        assertEquals(1, logic.getNames().size());
    }

    @Test
    public void testHandleTransactionPayerMore() {
        String payerName = "alice";
        String payeeName = "charlie";
        float payerOwes = -20F;
        float payeeOwed = 10F;
        logic.handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        String expectedAnswer = "Alice pays Charlie $10.\r\nNumber of transactions: 1";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testHandleTransactionPayeeMore() {
        String payerName = "alice";
        String payeeName = "charlie";
        float payerOwes = -10F;
        float payeeOwed = 20F;
        logic.handleTransaction(payerOwes, payerName, payeeOwed, payeeName);
        String expectedAnswer = "Alice pays Charlie $10.\r\nNumber of transactions: 1";
        assertEquals(expectedAnswer, logic.getAnswer());
    }

    @Test
    public void testGetNames() {
        Set<String> names = new HashSet<>(logic.getNames());
        Set<String> expected = new HashSet<>(Arrays.asList("alice", "bob", "charlie"));
        assertEquals(names, expected);
    }

    @Test
    public void testGetLedger() {
        Set<Float> amounts = new HashSet<>(logic.getLedger());
        Set<Float> expected = new HashSet<>(Arrays.asList(10F, 0F, -10F));
        assertEquals(amounts, expected);
    }


}
