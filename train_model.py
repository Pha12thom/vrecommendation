import pandas as pd
import numpy as np
from sklearn.neighbors import NearestNeighbors
import joblib

# Load vehicle dataset
df = pd.read_csv("vehicle.csv")

# Encode categorical values
size_mapping = {"Sedan": 0, "SUV": 1, "Hatchback": 2}
usage_mapping = {"Daily": 0, "Travel": 1, "City": 2}
location_mapping = {"Nairobi": 0, "Mombasa": 1, "Kisumu": 2}
time_mapping = {"Weekdays": 0, "Weekends": 1, "Anytime": 2}

df["size"] = df["size"].map(size_mapping)
df["usage"] = df["usage"].map(usage_mapping)
df["location"] = df["location"].map(location_mapping)
df["time_of_usage"] = df["time_of_usage"].map(time_mapping)

# Select features
X = df[["price", "size", "usage", "location", "time_of_usage"]]

# Train KNN model
knn = NearestNeighbors(n_neighbors=3, metric='euclidean')
knn.fit(X)

# Save the trained model
joblib.dump(knn, "vehicle_recommendation_model.pkl")
print("Model trained and saved successfully!")
