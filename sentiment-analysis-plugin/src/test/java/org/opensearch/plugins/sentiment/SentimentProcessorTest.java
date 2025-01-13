package org.opensearch.plugins.sentiment;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SentimentProcessorTest {

    @Test
    void testAnalyzeSentiment() {
        SentimentProcessor processor = new SentimentProcessor("test", "test description", null);
        Map<String, Object> result = processor.analyzeSentiment("This is a great product!");
        assertTrue((Double) result.get("score") > 0, "Positive sentiment should have a score greater than 0");
    }

}
