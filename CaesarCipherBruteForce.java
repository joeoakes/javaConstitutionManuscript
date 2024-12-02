import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args) {
        String filePath = "ciphertext.txt";
        String ciphertext = "";

        // Read the ciphertext from the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            ciphertext = sb.toString();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Perform brute force decryption for all possible shifts
        System.out.println("Brute-forcing Caesar cipher:");
        for (int shift = 0; shift < 26; shift++) {
            System.out.println("Shift " + shift + ":");
            System.out.println(decrypt(ciphertext, shift));
            System.out.println();
        }
    }
}
