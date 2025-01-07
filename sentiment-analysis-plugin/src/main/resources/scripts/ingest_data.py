from sentiment_analyzer import SentimentAnalyzer
import csv
from opensearchpy import OpenSearch

# Initialize OpenSearch client
client = OpenSearch(hosts=[{'host': 'localhost', 'port': 9200}])

# Initialize SentimentAnalyzer
analyzer = SentimentAnalyzer()

# Read and process the CSV file
with open('data/customer_reviews.csv', 'r') as file:
    reader = csv.DictReader(file)
    for row in reader:
        # Analyze sentiment
        sentiment_score = analyzer.analyze(row['review_text'])
        row['sentiment_score'] = sentiment_score
        row['sentiment_category'] = analyzer.categorize_sentiment(sentiment_score)

        # Index the document in OpenSearch
        client.index(index='customer_reviews', body=row)

print("Data ingestion complete.")
