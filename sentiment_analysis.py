import nltk
from nltk.sentiment import SentimentIntensityAnalyzer

# Download the VADER lexicon
nltk.download('vader_lexicon')

def analyze_sentiment(text):
    sia = SentimentIntensityAnalyzer()
    sentiment_scores = sia.polarity_scores(text)
    return sentiment_scores

# Test the function
sample_text = "I love this product! It's amazing."
result = analyze_sentiment(sample_text)
print(f"Sentiment analysis result: {result}")
