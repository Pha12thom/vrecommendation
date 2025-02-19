import java.util.Scanner;

public class CaesarCipher {
    
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base - 3 + 26) % 26 + base);
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
        
        String encryptedText = encrypt(text);
        System.out.println("Encrypted Text: " + encryptedText);
        
        scanner.close();
    }
}
