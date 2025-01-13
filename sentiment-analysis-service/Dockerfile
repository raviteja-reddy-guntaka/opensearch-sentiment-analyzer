FROM python:3.9-slim

WORKDIR /app

# Install build dependencies and curl for healthcheck
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    gcc \
    python3-dev \
    curl && \
    rm -rf /var/lib/apt/lists/*

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY sentiment_analysis_service.py .

# Download NLTK data during build and verify
RUN python3 -c "import nltk; nltk.download('vader_lexicon')" && \
    python3 -c "from nltk.sentiment import SentimentIntensityAnalyzer; SentimentIntensityAnalyzer()"

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:5000/health || exit 1

CMD ["python", "-u", "sentiment_analysis_service.py"]