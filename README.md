# opensearch-sentiment-analyzer
This is an OpenSearch Ingest Processor plugin. Ingest processors are used to pre-process documents before they are indexed. This plugin will analyze the sentiment of text fields in documents during the indexing process.


Sample customer review addition to opensearch:
```
POST /customer_reviews/_doc
{
  "review_id": "21",
  "product_id": "P00123",
  "customer_id": "C0018134",
  "review_text": "This product is fantastic! I love it.",
  "review_date": "2025-01-06"
}
```
