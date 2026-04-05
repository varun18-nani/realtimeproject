def generate_roadmap(goal: str):
    """Generates a structured roadmap schedule based on the user's career goal."""
    goal_lower = goal.lower().strip()
    
    if "web" in goal_lower:
        topics = ["HTML & CSS Basics", "JavaScript Fundamentals", "React Framework", "NodeJS & Express", "Database Integration", "Final Portfolio Project"]
    elif "data" in goal_lower or "science" in goal_lower:
        topics = ["Python Basics", "Numpy & Pandas", "Data Visualization", "Machine Learning Intro", "Deep Learning Basics", "Final Data Science Project"]
    elif "android" in goal_lower or "app" in goal_lower:
        topics = ["Java/Kotlin Basics", "OOP Concepts", "Android Studio UI", "Firebase Integration", "REST APIs in Android", "Build a Mobile App"]
    elif "software" in goal_lower or "dev" in goal_lower:
        topics = ["Programming Fundamentals", "Data Structures", "Algorithms", "Version Control (Git)", "System Design", "Capstone Project"]
    else:
        topics = ["General Programming", "Problem Solving", "Basic Web Tools", "Intro to Databases", "Final Assessment"]
        
    schedule = []
    for index, topic in enumerate(topics):
        schedule.append({
            "day": index + 1,
            "topic": topic,
            "status": "pending"
        })
        
    return schedule