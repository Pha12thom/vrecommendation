import pickle
import numpy as np
import pandas as pd
from flask import Flask, request, jsonify

app = Flask(__name__)

# Load trained model
with open("model.pkl", "rb") as f:
    model = pickle.load(f)

# Load encoders
with open("size_encoder.pkl", "rb") as f:
    size_encoder = pickle.load(f)

with open("usage_encoder.pkl", "rb") as f:
    usage_encoder = pickle.load(f)

with open("location_encoder.pkl", "rb") as f:
    location_encoder = pickle.load(f)

with open("time_encoder.pkl", "rb") as f:
    time_encoder = pickle.load(f)

# Load processed vehicle database
vehicles = pd.read_csv("vehicle.csv")

def safe_encode(encoder, value):
    """Encodes categorical values safely, returning -1 if not found."""
    try:
        return encoder.transform([value])[0]
    except ValueError:
        return -1  # If category is unknown, assign -1

@app.route("/recommend", methods=["POST"])
def recommend():
    try:
        data = request.get_json()

        # Encode user input
        user_input = [[
            data["price"],
            safe_encode(size_encoder, data["size"]),
            safe_encode(usage_encoder, data["usage"]),
            safe_encode(location_encoder, data["location"]),
            safe_encode(time_encoder, data["time_of_usage"])
        ]]

        # Convert to NumPy array and predict
        user_input = np.array(user_input)
        predicted_indices = model.predict(user_input)

        # Retrieve recommended vehicles
        recommended_vehicles = vehicles.iloc[predicted_indices].to_dict(orient="records")

        return jsonify({"recommended_vehicles": recommended_vehicles})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True)
