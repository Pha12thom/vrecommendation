import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;

public class PlayfairCipherUI {
    private static char[][] matrix = new char[5][5];

    public static void generateMatrix(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        
        for (char c : key.toCharArray()) set.add(c);
        for (char c = 'A'; c <= 'Z'; c++) if (c != 'J') set.add(c);

        Character[] uniqueChars = set.toArray(new Character[0]);
        int index = 0;
        
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                matrix[row][col] = uniqueChars[index++];
            }
        }
    }

    public static String encrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            formattedText.append(text.charAt(i));
            if (i + 1 < text.length() && text.charAt(i) == text.charAt(i + 1)) {
                formattedText.append('X');
            }
        }
        if (formattedText.length() % 2 != 0) formattedText.append('X');

        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < formattedText.length(); i += 2) {
            char a = formattedText.charAt(i);
            char b = formattedText.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) { // Same row
                encryptedText.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                encryptedText.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) { // Same column
                encryptedText.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                encryptedText.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            } else { // Rectangle swap
                encryptedText.append(matrix[posA[0]][posB[1]]);
                encryptedText.append(matrix[posB[0]][posA[1]]);
            }
        }

        return encryptedText.toString();
    }

    private static int[] findPosition(char letter) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == letter) return new int[]{row, col};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Playfair Cipher Encryption");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel keyLabel = new JLabel("Enter Key:");
        keyLabel.setBounds(20, 20, 100, 25);
        frame.add(keyLabel);

        JTextField keyField = new JTextField();
        keyField.setBounds(120, 20, 250, 25);
        frame.add(keyField);

        JLabel textLabel = new JLabel("Enter Text:");
        textLabel.setBounds(20, 60, 100, 25);
        frame.add(textLabel);

        JTextField textField = new JTextField();
        textField.setBounds(120, 60, 250, 25);
        frame.add(textField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(120, 100, 100, 25);
        frame.add(encryptButton);

        JLabel resultLabel = new JLabel("Encrypted Text: ");
        resultLabel.setBounds(20, 140, 450, 25);
        frame.add(resultLabel);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String inputText = textField.getText();
                generateMatrix(key);
                String encryptedText = encrypt(inputText);
                resultLabel.setText("Encrypted Text: " + encryptedText);
            }
        });

        frame.setVisible(true);
    }
}
