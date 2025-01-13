package org.opensearch.plugins.sentiment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.common.xcontent.json.JsonXContent;
import org.opensearch.core.common.bytes.BytesReference;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.ingest.AbstractProcessor;
import org.opensearch.ingest.IngestDocument;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class SentimentProcessor extends AbstractProcessor {
    public static final String TYPE = "sentiment_analysis";
    private static final Logger logger = LogManager.getLogger(SentimentProcessor.class);
    private static final String SERVICE_URL = "http://sentiment-analysis-service:5000/analyze";

    public SentimentProcessor(String tag, String description, Map<String, Object> config) {
        super(tag, description);
    }
    
    @Override
    public IngestDocument execute(IngestDocument ingestDocument) throws Exception {
        String text = ingestDocument.getFieldValue("review_text", String.class);
        Map<String, Object> result = analyzeSentiment(text);
        ingestDocument.setFieldValue("sentiment_score", result.get("score"));
        ingestDocument.setFieldValue("sentiment_category", result.get("category"));
        return ingestDocument;
    }

    public Map<String, Object> analyzeSentiment(String text) {
        try {
            HttpURLConnection con = (HttpURLConnection) URI.create(SERVICE_URL).toURL().openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            XContentBuilder builder = JsonXContent.contentBuilder();
            builder.startObject();
            builder.field("text", text);
            builder.endObject();

            try (OutputStream os = con.getOutputStream()) {
                builder.generator().flush();
                BytesReference.bytes(builder).writeTo(os);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return JsonXContent.jsonXContent.createParser(null, null, response.toString()).map();
            }
        } catch (Exception e) {
            logger.error("Failed to analyze sentiment", e);
            throw new RuntimeException("Failed to analyze sentiment", e);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
