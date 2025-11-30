import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HangJava {
    private final Scanner scanner;
    private final boolean testing;
    private String word;
    private List<Character> charsGuessed;
    private List<Character> wordChars;

    public HangJava(boolean testing) {
        this.scanner = new Scanner(System.in);
        this.testing = testing;
        this.charsGuessed = new ArrayList<>();
        this.wordChars = new ArrayList<>();
    }

    public void run () {
        System.out.print("Please enter the word/phrase to guess: ");
        this.word = scanner.nextLine();
        if (this.testing) {
            System.out.println("String: " + this.word);
        }

        String lowerCaseWord = this.word.toLowerCase();
        for (int i = 0; i < this.word.length(); i++) {
            if (Character.isAlphabetic(lowerCaseWord.charAt(i)) && !this.wordChars.contains(lowerCaseWord.charAt(i))) {
                this.wordChars.add(lowerCaseWord.charAt(i));
            }
        }

        int i = 0;
        int hasGuessed = 0;
        while (i < 7 && hasGuessed != this.word.length()) {
            hasGuessed = guess();

            if (hasGuessed == -2) {
                System.out.println("You already guessed that");
                continue;
            }

            if (hasGuessed == -1) {
                System.out.println("Better luck next time!");
                i++;
            }

            if (hasGuessed == this.word.length()) {
                System.out.println("CONGRATS, YOU HAVE GUESSED THE WORD!!\n");
            } else if (hasGuessed > 0) {
                System.out.println("Good guess! It exists in " + hasGuessed + " places!");
            }

            String drawing = printDrawing(i+1);
            String currentState = printWord();
            System.out.print(drawing);
            System.out.println(currentState);
        }

        if (i >= 7) {
            System.out.println("womp womp... the word was" + this.word);
        }
    }

    // -2 -> already guessed
    // -1 -> wrong
    // 0 -> guessed word
    // N > 0 -> exists in N places
    private int guess () {
        System.out.print("Please enter your guess: ");
        String guess = scanner.nextLine().toLowerCase();
        String lowerCaseWord = this.word.toLowerCase();
        // guessed word
        if (guess.length() > 1 && guess.equals(lowerCaseWord)) {
            for (char c : lowerCaseWord.toCharArray()) {
                this.charsGuessed.add(c);
            }
            return lowerCaseWord.length();
        } else if (guess.length() != 1) {
            // string but not the word
            return -1;
        }

        char guessChar = guess.charAt(0);
        // already guessed char
        if (this.charsGuessed.contains(guessChar)) {
            return -2;
        }

        if (!lowerCaseWord.contains(Character.toString(guessChar))) {
            // guess not in string
            return -1;
        } else {
            // guess in string
            this.charsGuessed.add(guessChar);
            // all chars guessed
            boolean hasAllChars = true;
            for (char c: this.wordChars) {
                if (!this.charsGuessed.contains(c)) {
                    hasAllChars = false;
                    break;
                }
            }

            if (hasAllChars) {
                return this.word.length();
            }
            return numOfOccurences(guessChar);
        }
    }

    private int numOfOccurences (char c) {
        int n = 0;
        String lowerCaseWord = this.word.toLowerCase();
        for (char cc : lowerCaseWord.toCharArray()) {
            if (cc == c) {
                n++;
            }
        }
        return n;
    }

    private String printWord () {
        StringBuilder sb = new StringBuilder();
        String lowerCaseWord = this.word.toLowerCase();
        for (int i = 0; i < this.word.length(); i++) {
            char guessChar = lowerCaseWord.charAt(i);
            if (!Character.isAlphabetic(guessChar) || this.charsGuessed.contains(guessChar)) {
                sb.append(this.word.charAt(i));
            } else {
                sb.append("_");
            }
            sb.append(" ");
        }

        return sb.toString();
    }

    private String printDrawing (int guessNum) {
        StringBuilder sb = new StringBuilder();
        switch (guessNum) {
            case 1:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 2:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 3:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 4:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 5:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 6:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("/    |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            case 7:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("/ \\  |\n");
                sb.append("     |\n");
                sb.append("=========\n");
                break;
            default:
                sb.append("ERROR INVALID GUESS NO!!");
                break;
        }

        return sb.toString();
    }
}
