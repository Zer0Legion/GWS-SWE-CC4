package balancer.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnswerEntryTest {
    private AnswerEntry answerEntry;

    @BeforeEach
    private void setUp() {
        this.answerEntry = new AnswerEntry("alice", "bob", 10F);
    }

    @Test
    public void testRoundingTrue() {
        String expected = "Alice pays Bob $10.\r\n";
        assertEquals(expected, answerEntry.toString(true));
    }

    @Test
    public void testRoundingFalse() {
        String expected = "Alice pays Bob $10.00.\r\n";
        assertEquals(expected, answerEntry.toString(false));
    }
}
