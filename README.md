# The Scholarly Path - Student Career Platform

**The Scholarly Path** is a premium, AI-powered career roadmap platform designed to help students navigate their professional journeys with precision. Fully integrated with **Firebase**, it offers secure authentication, cloud data persistence, and a modern 3D interface.

---

## 🔗 Live Application
*   **GitHub Repository**: [https://github.com/varun18-nani/realtimeproject](https://github.com/varun18-nani/realtimeproject)
*   **Live Preview (GitHub Pages)**: [https://varun18-nani.github.io/realtimeproject/](https://varun18-nani.github.io/realtimeproject/)

---

## 🚀 How to Run the Application

The application is designed to be **Serverless** via Firebase, meaning it runs directly on **GitHub Pages** without any complex setup.

### 1. Running on GitHub Pages (Recommended)
1.  Enable GitHub Pages in your repository settings.
2.  Set the source to the `main` branch and the `/ (root)` folder.
3.  Access the app at `https://[your-username].github.io/[repo-name]/`.

### 2. Running Locally with FastAPI
If you want to run the Python backend for local development:
1.  **Fix Python Environment**: Ensure `python` is in your system's PATH. (See `setup_env.txt` if you see "Python not found").
2.  **Install Requirements**:
    ```bash
    pip install -r requirements.txt
    ```
3.  **Start the Server**:
    ```bash
    python -m uvicorn backend.main:app --reload
    ```
4.  **Open at**: `http://127.0.0.1:8000/`

---

## 🛠️ Key Features
*   **Firebase Auth**: Secure Email and Google Sign-In.
*   **Cloud Firestore**: All career goals and milestones are saved in the cloud.
*   **Personalized Roadmaps**: AI-driven path generation based on your selected focus.
*   **Knowledge Checks**: Real-time quizzes to validate your learning progress.
*   **Premium UX**: 3D effects, 60fps animations, and a sleek dark-mode aesthetic.

---

## 🔒 Firebase Configuration
The app is pre-configured with the project: `careerroadmapproject-af870`. 
To use your own Firebase project:
1.  Create a project at [console.firebase.google.com](https://console.firebase.google.com/).
2.  Enable **Authentication** (Email & Google).
3.  Enable **Cloud Firestore**.
4.  Update the `firebaseConfig` object in [`frontend/lib/firebase-config.js`](./frontend/lib/firebase-config.js).

---

© 2026 The Scholarly Path
