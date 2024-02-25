package balancer.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnswerParserTest {
    private AnswerParser answerParser;

    @BeforeEach
    public void setUp() {
        this.answerParser = new AnswerParser();
    }

    @Test
    public void testParser() {
        answerParser.addAnswer("bob", "charlie", 20.2F);
        String expected = "Bob pays Charlie $20.20.\r\nNumber of transactions: 1";
        assertEquals(expected, answerParser.toString());
    }

}
