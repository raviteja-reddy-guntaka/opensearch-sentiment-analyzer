package org.opensearch.plugins.sentiment;

import org.opensearch.plugins.Plugin;
import org.opensearch.ingest.Processor;
import org.opensearch.plugins.IngestPlugin;

import java.util.Map;
import java.util.Collections;

public class SentimentAnalysisPlugin extends Plugin implements IngestPlugin {
    @Override
    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        return Collections.singletonMap(SentimentProcessor.TYPE, 
            (processorFactories, tag, description, config) -> new SentimentProcessor(tag, description, config));
    }
}
