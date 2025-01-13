from flask import Flask, request, jsonify
import nltk
from nltk.sentiment import SentimentIntensityAnalyzer
import logging

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

app = Flask(__name__)


class SentimentAnalyzer:
    def __init__(self):
        try:
            self.sia = SentimentIntensityAnalyzer()
            logger.info("SentimentAnalyzer initialized successfully")
        except Exception as e:
            logger.error(f"Error initializing SentimentAnalyzer: {str(e)}")
            raise

    def analyze(self, text):
        return self.sia.polarity_scores(text)['compound']

    def categorize_sentiment(self, score):
        if score > 0.05:
            return 'Positive'
        elif score < -0.05:
            return 'Negative'
        else:
            return 'Neutral'


try:
    analyzer = SentimentAnalyzer()
except Exception as e:
    logger.error(f"Failed to initialize analyzer: {str(e)}")
    raise


@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({'status': 'healthy'}), 200


@app.route('/analyze', methods=['POST'])
def analyze_sentiment():
    try:
        data = request.json
        if not data:
            logger.error("No JSON data in request")
            return jsonify({'error': 'No JSON data provided'}), 400
        
        text = data.get('text')
        if not text:
            logger.error("No text field in JSON data")
            return jsonify({'error': 'No text provided'}), 400

        score = analyzer.analyze(text)
        category = analyzer.categorize_sentiment(score)
        
        return jsonify({
            'score': score,
            'category': category
        })
    except Exception as e:
        logger.error(f"Error processing request: {str(e)}")
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)