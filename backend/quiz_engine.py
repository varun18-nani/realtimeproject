def get_questions(goal: str):
    """Returns specialized quiz questions depending on the user's career goal."""
    goal_lower = goal.lower().strip()
    
    if "web" in goal_lower:
        return [
            {
                "question": "What does HTML stand for?",
                "options": ["Hyper Trainer Marking Language", "Hyper Text Markup Language", "Hyper Text Marketing Language"],
                "answer": "Hyper Text Markup Language"
            },
            {
                "question": "Which HTTP status code means 'Not Found'?",
                "options": ["200", "404", "500"],
                "answer": "404"
            },
            {
                "question": "Which operator is used for strict equality in JavaScript?",
                "options": ["==", "===", "="],
                "answer": "==="
            }
        ]
    elif "data" in goal_lower or "science" in goal_lower:
        return [
            {
                "question": "Which Python library is primarily used for data manipulation?",
                "options": ["Requests", "Pandas", "Flask"],
                "answer": "Pandas"
            },
            {
                "question": "What does ML stand for?",
                "options": ["Machine Language", "Micro Logic", "Machine Learning"],
                "answer": "Machine Learning"
            }
        ]
    elif "android" in goal_lower or "app" in goal_lower:
        return [
            {
                "question": "What is the official language for Android development?",
                "options": ["Swift", "Kotlin", "C#"],
                "answer": "Kotlin"
            },
            {
                "question": "What file format is used to distribute Android apps?",
                "options": [".ipa", ".apk", ".exe"],
                "answer": ".apk"
            }
        ]
    
    # Default fallback quiz
    return [
        {
            "question": "What is an algorithm?",
            "options": ["A graphical interface", "A step-by-step procedure for solving a problem", "A database system"],
            "answer": "A step-by-step procedure for solving a problem"
        },
        {
            "question": "Which language is known as the 'mother of all languages'?",
            "options": ["C", "Assembly", "Python"],
            "answer": "C"
        }
    ]