import java.util.Scanner;

public class HillCipher {
    
    public static int[][] keyMatrix = {{6, 9, 8}, {10, 11, 12}, {13, 12, 7}}; // Example key matrix
    
    public static String encrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        while (text.length() % 3 != 0) {
            text += "X"; // Padding
        }
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i += 3) {
            int[] vector = {text.charAt(i) - 'A', text.charAt(i + 1) - 'A', text.charAt(i + 2) - 'A'};
            
            for (int row = 0; row < 3; row++) {
                int sum = 0;
                for (int col = 0; col < 3; col++) {
                    sum += keyMatrix[row][col] * vector[col];
                }
                result.append((char) ((sum % 26) + 'A'));
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
