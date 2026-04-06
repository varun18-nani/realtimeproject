from fastapi import FastAPI, HTTPException, Depends
from fastapi.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from database import engine, SessionLocal
import models
import schemas
import auth
import progress
import os

from roadmap_ai import generate_roadmap
from quiz_engine import get_questions

app = FastAPI(title="Student Career Roadmap Platform")

# Get absolute path for frontend directory
frontend_dir = os.path.join(os.path.dirname(os.path.dirname(__file__)), "frontend")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins for local web development
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Create tables if not exists
models.Base.metadata.create_all(bind=engine)

# Dependency to get DB session
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.get("/", response_class=HTMLResponse)
def home():
    # Serve index.html as the primary landing page
    index_path = os.path.join(frontend_dir, "index.html")
    if os.path.exists(index_path):
        with open(index_path, "r", encoding="utf-8") as f:
            return f.read()
    return {"message": "Career Roadmap Platform Running (Missing index.html)"}

# Mount the entire frontend directory for static assets (CSS, JS, images, etc.)
app.mount("/", StaticFiles(directory=frontend_dir), name="frontend")

# ---------------- AUTHENTICATION ----------------
@app.post("/register")
def register(user: schemas.UserCreate, db: Session = Depends(get_db)):
    # Check if exists
    existing = db.query(models.User).filter(models.User.email == user.email).first()
    if existing:
        raise HTTPException(status_code=400, detail="Email already registered")
    return auth.create_user(db, user.name, user.email, user.password, user.goal)

@app.post("/login")
def login(user: schemas.UserLogin, db: Session = Depends(get_db)):
    record = db.query(models.User).filter(models.User.email == user.email, models.User.password == user.password).first()
    if not record:
        raise HTTPException(status_code=401, detail="Invalid email or password")
    return {
        "message": "Login successful", 
        "user": {
            "id": record.id, 
            "name": record.name, 
            "email": record.email, 
            "goal": record.goal
        }
    }

# ---------------- ROADMAP ----------------
@app.get("/roadmap/{goal}")
def get_roadmap(goal: str):
    schedule = generate_roadmap(goal)
    return {"goal": goal, "schedule": schedule}

# ---------------- QUIZ ----------------
@app.get("/quiz/{goal}")
def quiz(goal: str):
    return get_questions(goal)

# ---------------- PROGRESS ----------------
@app.post("/progress")
def save_progress(prog: schemas.ProgressUpdate, db: Session = Depends(get_db)):
    return progress.update_progress(db, prog.user_id, prog.course, prog.progress)

@app.get("/progress/{user_id}")
def fetch_progress(user_id: int, db: Session = Depends(get_db)):
    return progress.get_progress(db, user_id)