import pandas as pd
import pickle
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder

# Load the dataset
df = pd.read_csv("vehicles.csv")

# Encode categorical columns
encoders = {}
categorical_columns = ["Make", "Model", "Color", "Transmission", "Type"]

for col in categorical_columns:
    encoders[col] = LabelEncoder()
    df[col] = encoders[col].fit_transform(df[col])

# Define features and target
X = df[["Year", "Price", "Mileage", "Color", "Transmission", "Type"]]
y = df.index  # Using index as the target to retrieve recommended vehicles

# Train a RandomForest model
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# Save the model and encoders
with open("vehicle_model.pkl", "wb") as f:
    pickle.dump(model, f)

with open("encoders.pkl", "wb") as f:
    pickle.dump(encoders, f)

print("Model and encoders saved successfully!")
