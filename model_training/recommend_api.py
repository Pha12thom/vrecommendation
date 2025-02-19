import pickle
import pandas as pd
from flask import Flask, request, jsonify

# Load the trained model and encoders
with open("pkl/vehicle_model.pkl", "rb") as f:
    model = pickle.load(f)

with open("pkl/encoders.pkl", "rb") as f:
    encoders = pickle.load(f)

# Load the original dataset to retrieve vehicle details
df = pd.read_csv("vehicles.csv")

# Flask app
app = Flask(__name__)

@app.route("/", methods=["GET"])
def home():
    return jsonify({"message": "Vehicle Recommendation API is running!"})

@app.route("/recommend", methods=["POST"])
def recommend():
    try:
        data = request.get_json()

        # Encode input features
        input_data = pd.DataFrame([data])
        input_data["Color"] = encoders["Color"].transform([data["Color"]])
        input_data["Transmission"] = encoders["Transmission"].transform([data["Transmission"]])
        input_data["Type"] = encoders["Type"].transform([data["Type"]])

        # Predict vehicle index
        predicted_index = model.predict(input_data)[0]
        recommended_vehicle = df.iloc[predicted_index].to_dict()

        return jsonify({"recommended_vehicle": recommended_vehicle})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True)
