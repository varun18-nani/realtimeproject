from pydantic import BaseModel
from typing import List, Dict, Any, Optional

class UserCreate(BaseModel):
    name: str
    email: str
    password: str
    goal: str

class UserLogin(BaseModel):
    email: str
    password: str

class ProgressUpdate(BaseModel):
    user_id: int
    course: str
    progress: int

class QuizResponse(BaseModel):
    question: str
    options: List[str]
    answer: str

class RoadmapResponse(BaseModel):
    goal: str
    schedule: List[Dict[str, Any]]
