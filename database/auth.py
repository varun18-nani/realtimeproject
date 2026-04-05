from database import SessionLocal
from models import User

def create_user(name, email, password, goal):

    db = SessionLocal()   # 🔹 CONNECT TO DB

    user = User(
        name=name,
        email=email,
        password=password,
        goal=goal
    )

    db.add(user)          # 🔹 INSERT DATA
    db.commit()           # 🔹 SAVE

    return {"message": "User created successfully"}