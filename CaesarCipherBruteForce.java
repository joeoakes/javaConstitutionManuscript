import java.io.*;
import java.util.*;

public class CaesarCipherBruteForce {

    // Decrypt text with a given shift
    public static String decrypt(String text, int shift) {
        StringBuilder decrypted = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                decrypted.append((char) ((c - base - shift + 26) % 26 + base));
            } else {
                decrypted.append(c); // Keep non-alphabetic characters unchanged
            }
        }
        return decrypted.toString();
    }

    // Load dictionary from file into a set
    public static Set<String> loadDictionary(String dictionaryFile) {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFile))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading the dictionary file: " + e.getMessage());
        }
        return dictionary;
    }

    // Count valid words in a decrypted text
    public static int countValidWords(String text, Set<String> dictionary) {
        String[] words = text.split("\\W+"); // Split by non-word characters
        int validWordCount = 0;

        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                validWordCount++;
            }
        }
        return validWordCount;
    }

    public static void main(String[] args) {
        String cipherFile = "ciphertext.txt"; // File containing the ciphertext
        String dictionaryFile = "dictionary.txt"; // File containing the dictionary
        String ciphertext = "";

        // Load the dictionary
        Set<String> dictionary = loadDictionary(dictionaryFile);

        // Read the ciphertext from the file
        try (BufferedReader br = new BufferedReader(new FileReader(cipherFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            ciphertext = sb.toString();
        } catch (IOException e) {
            System.err.println("Error reading the ciphertext file: " + e.getMessage());
            return;
        }

        // Perform brute force decryption and dictionary lookup
        System.out.println("Brute-forcing Caesar cipher with dictionary lookup:");
        int bestShift = -1;
        int maxValidWords = 0;

        for (int shift = 0; shift < 26; shift++) {
            String decrypted = decrypt(ciphertext, shift);
            int validWords = countValidWords(decrypted, dictionary);

            System.out.println("Shift " + shift + ": " + validWords + " valid words");
            System.out.println(decrypted);
            System.out.println();

            // Keep track of the shift with the most valid words
            if (validWords > maxValidWords) {
                maxValidWords = validWords;
                bestShift = shift;
            }
        }

        System.out.println("Best shift: " + bestShift);
        System.out.println("Decrypted text: " + decrypt(ciphertext, bestShift));
    }
}
