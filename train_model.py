import pandas as pd
import numpy as np
import pickle
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder

# Load dataset
data = pd.read_csv("vehicles.csv")

# Label encoding for categorical columns
size_encoder = LabelEncoder()
usage_encoder = LabelEncoder()
location_encoder = LabelEncoder()
time_encoder = LabelEncoder()

data["size"] = size_encoder.fit_transform(data["size"])
data["usage"] = usage_encoder.fit_transform(data["usage"])
data["location"] = location_encoder.fit_transform(data["location"])
data["time_of_usage"] = time_encoder.fit_transform(data["time_of_usage"])

# Assign unique indices to each vehicle
data["vehicle_index"] = range(len(data))

# Split data
X = data.drop(columns=["vehicle_name", "vehicle_index"])  # Remove text columns
y = data["vehicle_index"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# Save model and encoders
with open("model.pkl", "wb") as f:
    pickle.dump(model, f)

with open("size_encoder.pkl", "wb") as f:
    pickle.dump(size_encoder, f)

with open("usage_encoder.pkl", "wb") as f:
    pickle.dump(usage_encoder, f)

with open("location_encoder.pkl", "wb") as f:
    pickle.dump(location_encoder, f)

with open("time_encoder.pkl", "wb") as f:
    pickle.dump(time_encoder, f)

# Save processed data for use in API
data.to_csv("vehicle.csv", index=False)

print("Model and encoders saved successfully!")
