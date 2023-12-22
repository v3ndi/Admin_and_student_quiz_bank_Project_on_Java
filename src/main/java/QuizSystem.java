import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class QuizSystem {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("System:> Welcome to the quiz!");
        System.out.println("System:> Are you an admin or a student? Enter 'admin' or 'student':");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("admin")) {
            adminLogin();
        } else if (userType.equalsIgnoreCase("student")) {
            studentLogin();
        } else {
            System.out.println("System:> Invalid user type. Exiting program.");
        }

        scanner.close();
    }

    private static void adminLogin() throws IOException, ParseException {
        String adminUsername = readAdminJSONArray();
        String adminPassword = readAdminPassJSONArray();

        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Enter your username:");
        String userInput = scanner.next();
        System.out.println("System:> Enter your password:");
        String userInputPass = scanner.next();

        if (userInput.equals(adminUsername) && userInputPass.equals(adminPassword)) {
            System.out.println("System:> Admin authentication successful. Welcome admin!");
            inputAndWrite();
        } else {
            System.out.println("System:> Admin authentication failed. Exiting program.");
        }

        scanner.close();
    }

    private static void studentLogin() throws IOException, ParseException {
        String studentUsername = readUserJSONArray();
        String studentPassword = readUserPassJSONArray();

        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Enter your username:");
        String userInput = scanner.next();
        System.out.println("System:> Enter your password:");
        String userInputPass = scanner.next();

        if (userInput.equals(studentUsername) && userInputPass.equals(studentPassword)) {
            System.out.println("System:> Student authentication successful. Welcome student!");
            generateAndDisplayQuestions();
        } else {
            System.out.println("System:> Student authentication failed. Exiting program.");
        }

        scanner.close();
    }

    private static void inputAndWrite() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        char userChoice;
        do {
            System.out.println("System:> Input the questions (Or Enter only 'q' to stop):");
            String qns = scanner.nextLine();

            if (qns.equalsIgnoreCase("q")) {
                break;
            }

            String[] opt = new String[4];
            for (int j = 1; j <= 4; j++) {
                System.out.print("System:> Option " + j + ": ");
                opt[j - 1] = scanner.nextLine();
            }

            System.out.println("System:> Input the answer:");
            String ans = scanner.next();
            scanner.nextLine();

            JSONParser parser = new JSONParser();
            JSONArray qnaArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/QnA.json"));
            JSONObject qnaObj = new JSONObject();
            qnaObj.put("Question", qns);
            qnaObj.put("Option 1", opt[0]);
            qnaObj.put("Option 2", opt[1]);
            qnaObj.put("Option 3", opt[2]);
            qnaObj.put("Option 4", opt[3]);
            qnaObj.put("Answer Key", ans);

            qnaArray.add(qnaObj);

            FileWriter writer = new FileWriter("./src/main/resources/QnA.json");
            writer.write(qnaArray.toJSONString() + "\n");
            writer.flush();
            writer.close();

            System.out.println("System:> Question saved successfully!");


            System.out.println("System:> Do you want to add more questions? (Press 's' for start and 'q' for quit)");
            userChoice = scanner.next().charAt(0);
            scanner.nextLine();
        } while (userChoice == 's');

        scanner.close();
    }

    private static void generateAndDisplayQuestions() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        char startQuiz;
        do {
            System.out.println("System:> Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' for start. Or press 'q' to quit ");

            startQuiz = scanner.next().charAt(0);

            if (startQuiz == 's') {
                List<JSONObject> questions = getRandomQuestions();
                int score = 0;

                for (int i = 0; i < questions.size(); i++) {
                    JSONObject question = questions.get(i);
                    displayQuestion(question, i + 1);
                    System.out.println("System:> Enter your answer (Option 1/2/3/4): ");
                    String userAnswer = scanner.next();

                    if (question.get("Answer Key").equals(userAnswer)) {
                        System.out.println("System:> Correct! You earned 1 point.");
                        score++;
                    } else {
                        System.out.println("System:> Incorrect! The correct answer is Option " + question.get("Answer Key"));
                    }
                }

                if (score >= 8) {
                    System.out.println("System:> Excellent! You have got " + score + " out of 10.");
                } else if (score >= 5) {
                    System.out.println("System:> Good. You have got " + score + " out of 10.");
                } else if (score >= 2) {
                    System.out.println("System:> Very poor! You have got only " + score + " out of 10.");
                } else {
                    System.out.println("System:> Very sorry you have failed in the Quiz.  You have got only " + score + " out of 10.");
                }

                System.out.println("System:> Would you like to start again? Press 's' for start or 'q' for quit");
            }

        } while (startQuiz == 's');

        System.out.println("System:> Exiting program.");
        scanner.close();
    }

    private static List<JSONObject> getRandomQuestions() throws IOException, ParseException {
        List<JSONObject> allQuestions = readQuestionsJSONArray();
        List<JSONObject> randomQuestions = new ArrayList<>();

        if (10 > allQuestions.size()) {
            System.out.println("System:> Warning: Insufficient questions available. Showing all questions.");
            return allQuestions;
        }

        Set<Integer> selectedIndices = new HashSet<>();
        Random random = new Random();

        while (selectedIndices.size() < 10) {
            int randomIndex = random.nextInt(allQuestions.size());
            if (selectedIndices.add(randomIndex)) {
                randomQuestions.add(allQuestions.get(randomIndex));
            }
        }

        return randomQuestions;
    }

    private static void displayQuestion(JSONObject question, int questionNumber) {
        System.out.println("System:> Question " + questionNumber + ": " + question.get("Question"));
        System.out.println("System:> Option 1: " + question.get("Option 1"));
        System.out.println("System:> Option 2: " + question.get("Option 2"));
        System.out.println("System:> Option 3: " + question.get("Option 3"));
        System.out.println("System:> Option 4: " + question.get("Option 4"));
    }

    private static List<JSONObject> readQuestionsJSONArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray qnaArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/QnA.json"));

        List<JSONObject> questions = new ArrayList<>();
        for (Object obj : qnaArray) {
            questions.add((JSONObject) obj);
        }
        return questions;
    }

    private static String readAdminJSONArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/User.json"));
        JSONObject userInfo = (JSONObject) usersArray.get(0);
        return (String) userInfo.get("username");
    }

    private static String readAdminPassJSONArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/User.json"));
        JSONObject userInfo = (JSONObject) usersArray.get(0);
        return (String) userInfo.get("password");
    }

    private static String readUserJSONArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/User.json"));
        JSONObject userInfo = (JSONObject) usersArray.get(1);
        return (String) userInfo.get("username");
    }

    private static String readUserPassJSONArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/User.json"));
        JSONObject userInfo = (JSONObject) usersArray.get(1);
        return (String) userInfo.get("password");
    }
}
