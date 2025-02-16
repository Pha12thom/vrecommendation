from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# Load the trained model
model = joblib.load("vehicle_recommendation_model.pkl")

# Load vehicle data
df = pd.read_csv("vehicle.csv")

# Define encoding mappings
size_mapping = {"Sedan": 0, "SUV": 1, "Hatchback": 2}
usage_mapping = {"Daily": 0, "Travel": 1, "City": 2}
location_mapping = {"Nairobi": 0, "Mombasa": 1, "Kisumu": 2}
time_mapping = {"Weekdays": 0, "Weekends": 1, "Anytime": 2}

# Reverse mappings (for decoding)
reverse_size_mapping = {v: k for k, v in size_mapping.items()}
reverse_usage_mapping = {v: k for k, v in usage_mapping.items()}
reverse_location_mapping = {v: k for k, v in location_mapping.items()}
reverse_time_mapping = {v: k for k, v in time_mapping.items()}

@app.route("/recommend", methods=["POST"])
def recommend():
    data = request.json

    try:
        user_input = [[
            data["price"],
            size_mapping.get(data["size"], -1),  # Handle missing mappings
            usage_mapping.get(data["usage"], -1),
            location_mapping.get(data["location"], -1),
            time_mapping.get(data["time_of_usage"], -1)
        ]]

        distances, indices = model.kneighbors(user_input)
        recommended_vehicles = df.iloc[indices[0]].copy()

        # Convert numerical values back to readable labels (handling NaN)
        recommended_vehicles["size"] = recommended_vehicles["size"].map(reverse_size_mapping).fillna("Unknown")
        recommended_vehicles["usage"] = recommended_vehicles["usage"].map(reverse_usage_mapping).fillna("Unknown")
        recommended_vehicles["location"] = recommended_vehicles["location"].map(reverse_location_mapping).fillna("Unknown")
        recommended_vehicles["time_of_usage"] = recommended_vehicles["time_of_usage"].map(reverse_time_mapping).fillna("Unknown")

        return jsonify({
            "recommended_vehicles": recommended_vehicles[[
                "vehicle_name", "price", "size", "usage", "location", "time_of_usage"
            ]].to_dict(orient="records")
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=5000)
