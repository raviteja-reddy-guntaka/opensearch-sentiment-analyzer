import nltk
from nltk.sentiment import SentimentIntensityAnalyzer

# Modified the sentiment analysis script to follow OOP concepts
class SentimentAnalyzer:
    def __init__(self):
        nltk.download('vader_lexicon', quiet=True)
        self.sia = SentimentIntensityAnalyzer()

    def analyze(self, text):
        return self.sia.polarity_scores(text)['compound']

    def analyze_batch(self, texts):
        return [self.analyze(text) for text in texts]

    def categorize_sentiment(self, score):
        if score > 0.05:
            return 'Positive'
        elif score < -0.05:
            return 'Negative'
        else:
            return 'Neutral'
