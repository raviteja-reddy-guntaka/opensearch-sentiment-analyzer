package org.opensearch.plugins.sentiment;

import org.opensearch.plugins.Plugin;
import org.opensearch.plugins.IngestPlugin;
import org.opensearch.ingest.Processor;

import java.util.Map;

public class SentimentAnalysisPlugin extends Plugin implements IngestPlugin {

    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        return Map.of("sentiment_analysis", new Processor.Factory() {
            @Override
            public Processor create(Map<String, Processor.Factory> processorFactories, 
                                    String tag, String description, Map<String, Object> config) throws Exception {
                return new SentimentProcessor(tag, description, config);
            }
        });
    }

}
