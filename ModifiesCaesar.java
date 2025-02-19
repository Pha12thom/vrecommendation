import java.util.Scanner;

public class ModifiesCaesar {
    
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base + shift) % 26 + base);
                result.append(encryptedChar);
            } else {
                result.append(ch);
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter text to encrypt: ");
        String text = scanner.nextLine();
        
        int shift = 4; // Modified to a 4-letter shift
        String encryptedText = encrypt(text, shift);
        System.out.println("Encrypted Text: " + encryptedText);
        
        scanner.close();
    }
}
