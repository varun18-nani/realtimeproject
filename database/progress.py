db = SessionLocal()

data = db.query(Progress).filter(Progress.user_id == user_id).all()