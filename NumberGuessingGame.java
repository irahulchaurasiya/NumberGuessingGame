import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int MAX_GUESSES = 10;
    private static final int MAX_HIGH_SCORES = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int difficultyLevel;
        int targetNumber;
        int userGuess = 0;
        int attempts;
        int score = 0;
        boolean isNewHighScore;

        HighScoreList highScoreList = new HighScoreList(MAX_HIGH_SCORES);

        System.out.println("Welcome to the Advanced Number Guessing Game!");

        do {
            try {
                System.out.println("\nSelect Difficulty Level:");
                System.out.println("1. Easy (1-50)");
                System.out.println("2. Medium (1-100)");
                System.out.println("3. Hard (1-200)");
                System.out.print("Enter your choice (1-3): ");

                difficultyLevel = scanner.nextInt();

                switch (difficultyLevel) {
                    case 1:
                        upperBound = 50;
                        break;
                    case 2:
                        upperBound = 100;
                        break;
                    case 3:
                        upperBound = 200;
                        break;
                    default:
                        System.out.println("Invalid choice. Defaulting to Medium difficulty.");
                        upperBound = 100;
                        break;
                }

                targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                attempts = 0;
                score = 0;
                isNewHighScore = false;

                System.out.println("Guess a number between " + lowerBound + " and " + upperBound);

                while (attempts < MAX_GUESSES) {
                    try {
                        System.out.print("Enter your guess: ");
                        userGuess = scanner.nextInt();
                        attempts++;

                        if (userGuess < lowerBound || userGuess > upperBound) {
                            System.out.println("Invalid guess. Please enter a number within the specified range.");
                            continue;
                        }

                        if (userGuess < targetNumber) {
                            System.out.println("Too low! Try again.");
                        } else if (userGuess > targetNumber) {
                            System.out.println("Too high! Try again.");
                        } else {
                            System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                            score = calculateScore(attempts, difficultyLevel);
                            isNewHighScore = highScoreList.addScore(score);
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine();  
                    }
                }

                if (isNewHighScore) {
                    System.out.println("New High Score! Your score: " + score);
                }

                System.out.print("Do you want to play again? (yes/no): ");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the difficulty level.");
                scanner.nextLine(); 
            }
        } while (scanner.next().equalsIgnoreCase("yes"));

        System.out.println("Thank you for playing!");
        highScoreList.displayHighScores();
        scanner.close();
    }

    private static int calculateScore(int attempts, int difficultyLevel) {
        int baseScore = MAX_GUESSES - attempts;
        return baseScore * (difficultyLevel + 1);
    }
}

class HighScoreList {
    private int[] highScores;
    private int maxSize;
    private int size;

    public HighScoreList(int maxSize) {
        this.maxSize = maxSize;
        this.highScores = new int[maxSize];
        this.size = 0;
    }

    public boolean addScore(int score) {
        if (size < maxSize || score > highScores[size - 1]) {
            if (size < maxSize) {
                highScores[size++] = score;
            } else {
                highScores[size - 1] = score;
            }
            sortScores();
            return true;
        }
        return false;
    }

    private void sortScores() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (highScores[j] < highScores[j + 1]) {
                    int temp = highScores[j];
                    highScores[j] = highScores[j + 1];
                    highScores[j + 1] = temp;
                }
            }
        }
    }

    public void displayHighScores() {
        System.out.println("\nHigh Scores:");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + highScores[i]);
        }
    }
}
