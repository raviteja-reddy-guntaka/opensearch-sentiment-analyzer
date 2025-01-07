package org.opensearch.plugins.sentiment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.ingest.AbstractProcessor;
import org.opensearch.ingest.IngestDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class SentimentProcessor extends AbstractProcessor {
    public static final String TYPE = "sentiment";
    private static final Logger logger = LogManager.getLogger(SentimentProcessor.class);

    public SentimentProcessor(String tag, String description, Map<String, Object> config) {
        super(tag, description);
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDocument) throws Exception {
        String text = ingestDocument.getFieldValue("review_text", String.class);
        double sentimentScore = analyzeSentiment(text);
        ingestDocument.setFieldValue("sentiment_score", sentimentScore);
        return ingestDocument;
    }
    

    // public double analyzeSentiment(String text) {
    //     try {
    //         ProcessBuilder pb = new ProcessBuilder("py", "/scripts/perform_sentiment_analysis.py", text);
    //         Process p = pb.start();
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //         String result = reader.readLine();
    //         return Double.parseDouble(result);
    //     } catch (Exception e) {
    //         throw new RuntimeException("Failed to analyze sentiment", e);
    //     }
    // }

    public double analyzeSentiment(String text) {
        try {
            URL resourceUrl = getClass().getResource("/scripts/perform_sentiment_analysis.py");
            if (resourceUrl == null) {
                logger.debug("Script file not found in resources");
                throw new RuntimeException("Script file not found in resources");
            }
            File scriptFile = new File(resourceUrl.toURI());
            
            ProcessBuilder pb = new ProcessBuilder("python3", scriptFile.getAbsolutePath(), text);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = reader.readLine();
            if (result == null) {
                logger.debug("No output from sentiment analysis script", p);
                throw new RuntimeException("No output from sentiment analysis script");
            }
            return Double.parseDouble(result);
        } catch (Exception e) {
            logger.debug("Failed to analyze sentiment", e);
            throw new RuntimeException("Failed to analyze sentiment", e);
        }
    }
    

    @Override
    public String getType() {
        return TYPE;
    }
}
