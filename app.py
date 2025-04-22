
from flask import Flask, request, jsonify, send_from_directory
from flask_cors import CORS
import os
from datetime import datetime

app = Flask(__name__)
CORS(app) # Enable CORS for all origins — useful for Angular calls
UPLOAD_FOLDER = 'uploads'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

@app.route('/')
def home():
    return 'Flask is running!'

@app.route('/run-script', methods=['POST'])
def run_script():
    data = request.json
    message = data.get('message', 'No message received')
    return jsonify({
        'status': 'success',
        'echo': f'You sent: {message}'
    })

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({'error': True, 'message': 'No file part'}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': True, 'message': 'No selected file'}), 400
    try:
        # Create a unique filename with timestamp
        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        original_filename = file.filename
        filename = f'{timestamp}_{original_filename}'
        file_path = os.path.join(UPLOAD_FOLDER, filename)
        file.save(file_path)
        return jsonify({
            'error': False,
            'message': 'File uploaded successfully',
            'originalFilename': original_filename,
            'savedFilename': filename,
            'path': file_path
        }), 200
    except Exception as e:
        return jsonify({'error': True, 'message': f'File upload failed: {str(e)}'}), 500

@app.route('/uploads/<filename>', methods=['GET'])
def uploaded_file(filename):
    return send_from_directory('uploads', filename)

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, port=5001)
