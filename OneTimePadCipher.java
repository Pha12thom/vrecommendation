import java.util.Random;
import java.util.Scanner;

public class OneTimePadCipher {
    
    public static String encrypt(String text, int[] key) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base + key[i]) % 26 + base);
                result.append(encryptedChar);
            } else {
                result.append(ch);
            }
        }
        
        return result.toString();
    }
    
    public static int[] generateKey(int length) {
        Random rand = new Random();
        int[] key = new int[length];
        for (int i = 0; i < length; i++) {
            key[i] = rand.nextInt(26); // Random shift between 0 and 25
        }
        return key;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter text to encrypt: ");
        String text = scanner.nextLine();
        
        int[] key = generateKey(text.length());
        String encryptedText = encrypt(text, key);
        
        System.out.println("Encrypted Text: " + encryptedText);
        System.out.print("Key: ");
        for (int k : key) {
            System.out.print(k + " ");
        }
        System.out.println();
        
        scanner.close();
    }
}