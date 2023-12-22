# Admin and Student's activity for Quiz System.
The Quiz System is a simple Java program that allows users to either act as an administrator to input quiz questions or as a student to take a quiz.
The questions and user information are stored in JSON files.


## Prerequisite :
- Java Development Kit (JDK): Ensure JDK is installed.
- Java IDE: Recommended for easier development.
- Git (Optional): For version control (optional).
- JSON-Simple Library: Included in the project.
- Open the QuizSystem class: Run the project from the main class.
- To log in into the system use "User.json" file's credintials. 
These points cover the essential prerequisites to run and manage the Quiz System code.

  
## Features

- **Admin Mode:** Admins can input quiz questions, and the data is stored in a JSON file (`QnA.json`).
- **Student Mode:** Students can take a quiz with randomly selected questions from the question bank.
- **Authentication:** Both admin and student modes require username and password authentication.
- **Question Bank:** The question bank is stored in a JSON file for easy management and retrieval.
- **Random Question Selection:** When in student mode, the system randomly selects questions from the question bank.

## How to Use

1. Clone the repository: `git clone https://github.com/v3ndi/Admin_and_student_quiz_bank_Project_on_Java.git`
2. Open the project in your Java IDE.
3. Run the `QuizSystem` class to start the program.
4. Follow the prompts to either act as an admin or a student.

## Video 
https://www.loom.com/share/75d9f38911174c36b6877936e0efce1b?sid=52996e8d-a715-4240-862e-a90528cc2845


here is a video sample how to use it, clcick on the play button to play the video.

## File Structure

- **src/main/resources/QnA.json:** JSON file containing the quiz questions.
- **src/main/resources/User.json:** JSON file containing user information.

## Contributing

Pull requests and suggestions are welcome. For major changes, please open an issue first to discuss what you would like to change.

