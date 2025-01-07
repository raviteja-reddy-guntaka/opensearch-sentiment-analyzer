import sys
from sentiment_analyzer import SentimentAnalyzer

if __name__ == "__main__":
    if len(sys.argv) > 1:
        analyzer = SentimentAnalyzer()
        text = " ".join(sys.argv[1:])  # Join all arguments as they might contain spaces
        result = analyzer.analyze(text)
        print(result)
    else:
        print("Please provide text for sentiment analysis")
