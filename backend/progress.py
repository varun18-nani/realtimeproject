from sqlalchemy.orm import Session
from models import Progress

def update_progress(db: Session, user_id: int, course: str, progress_value: int):
    # Check if entry already exists and update
    existing = db.query(Progress).filter(Progress.user_id == user_id, Progress.course == course).first()
    if existing:
        existing.progress = progress_value
    else:
        progress = Progress(
            user_id=user_id,
            course=course,
            progress=progress_value
        )
        db.add(progress)
        
    db.commit()
    return {"message": "Progress updated successfully"}

def get_progress(db: Session, user_id: int):
    data = db.query(Progress).filter(Progress.user_id == user_id).all()
    # Format the data for JSON
    return [{"course": item.course, "progress": item.progress} for item in data]