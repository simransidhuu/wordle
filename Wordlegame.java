import java.util.Random;
import java.util.Scanner;

public class Wordlegame{
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static boolean IfGreen(String guess, String word, int index){
        return guess.charAt(index) == word.charAt(index);
    }

    public static boolean IfYellow(String guess, String word, int index){
        return guess.charAt(index) != word.charAt(index) && word.contains(String.valueOf(guess.charAt(index)));
    }

public static void main(String[] args){
    System.out.println("WORDLE! NEW FEATURE!!!!!");
    System.out.println("You have 6 guesses");

    Scanner fileScanner = new Scanner(Wordlegame.class.getResourceAsStream("words.txt"));
    String[] words = fileScanner.useDelimiter("\\A").next().toUpperCase().split("\n");
    fileScanner.close();

    Random random = new Random();
    String word = words[random.nextInt(words.length)].trim();

    System.out.println("6 guesses");

    boolean wordFound = false;

    for (int i = 0; i< 6; i++){
        Scanner scanner = new Scanner(System.in);
        System.err.print("Guess number" + (i + 1) + ":");
        String guess = scanner.nextLine().trim().toUpperCase();

        while(!containsWord(guess,words)){
            System.out.println("Invalid word. Please try again");
            System.out.println("Guess number " + (i+1) + ": ");
            guess = scanner.nextLine().trim().toUpperCase();
        }
        
        String wordcopy = word;
        String guesscopy = guess;

        StringBuilder colours = new StringBuilder("bbbbb");

        for (int index = 0; index < guesscopy.length(); index++){
            if (IfGreen(guesscopy, wordcopy, index)){
                colours.setCharAt(index, 'g');
                wordcopy = wordcopy.substring(0, index) + '*' + wordcopy.substring(index + 1);

            }
        }

        for(int index = 0; index < guesscopy.length(); index++){
            if(IfYellow(guesscopy, wordcopy, index) && colours.charAt(index) != 'g'){
                colours.setCharAt(index, 'y' );
                wordcopy = wordcopy.replaceFirst(String.valueOf(guesscopy.charAt(index)), "*");
            }
        }

        for(int index = 0; index < guess.length();index++){
            char currentChar = guesscopy.charAt(index);
            if (colours.charAt(index) == 'g'){
                System.out.print(GREEN +currentChar + RESET);
            }
            else if (colours.charAt(index) == 'y'){
                System.out.print(YELLOW + currentChar +RESET);
            }
            else{
                System.out.print(currentChar);
            }
        }

        System.out.println();

        if (guess.equals(word)){
            wordFound = true;
            break;
        }
    }
    if(wordFound){
        System.out.println("YOU GUESS RIGHT!");
    }
    else{
        System.out.println("You're out of guesses. The word was " + word + ".");
    }
}
private static boolean containsWord(String guess, String[] words){
    for (String word : words){
        if(word.trim().equalsIgnoreCase(guess)){
            return true;
        }
    }
    return false;
}
}



