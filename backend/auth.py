from sqlalchemy.orm import Session
from models import User

def create_user(db: Session, name: str, email: str, password: str, goal: str):
    user = User(
        name=name,
        email=email,
        password=password,
        goal=goal
    )

    db.add(user)
    db.commit()
    db.refresh(user)

    return {"message": "User created successfully", "user_id": user.id}