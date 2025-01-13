import pandas as pd

# Create a sample dataset of customer reviews
data = {
    'review_id': [1, 2, 3, 4, 5],
    'product_id': ['P001', 'P002', 'P003', 'P001', 'P002'],
    'customer_id': ['C001', 'C002', 'C003', 'C004', 'C005'],
    'review_text': [
        'This product is amazing! Highly recommend it.',
        'Not satisfied with the quality of the product.',
        'Decent product for the price, but could be better.',
        'Absolutely love it! Will buy again.',
        'Terrible experience. Would not recommend.'
    ],
    'review_date': ['2025-01-01', '2025-01-02', '2025-01-03', '2025-01-04', '2025-01-05']
}

# Convert to a DataFrame
customer_reviews = pd.DataFrame(data)

# Save the dataset to a CSV file
customer_reviews.to_csv('data/customer_reviews.csv', index=False)

print(customer_reviews.head())