import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HillCipherUI {

    // 2x2 Key Matrix for encryption
    private static final int[][] keyMatrix = {
        {3, 2},
        {5, 7}
    };

    public static String encrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", ""); // Keep only letters

        if (text.length() % 2 != 0) {
            text += "X"; // Padding if odd-length
        }

        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            int char1 = text.charAt(i) - 'A';
            int char2 = text.charAt(i + 1) - 'A';

            // Matrix multiplication
            int newChar1 = (keyMatrix[0][0] * char1 + keyMatrix[0][1] * char2) % 26;
            int newChar2 = (keyMatrix[1][0] * char1 + keyMatrix[1][1] * char2) % 26;

            encryptedText.append((char) ('A' + newChar1));
            encryptedText.append((char) ('A' + newChar2));
        }

        return encryptedText.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hill Cipher Encryption");
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Enter text:");
        label.setBounds(20, 20, 100, 25);
        frame.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(120, 20, 250, 25);
        frame.add(textField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(120, 60, 100, 25);
        frame.add(encryptButton);

        JLabel resultLabel = new JLabel("Encrypted Text: ");
        resultLabel.setBounds(20, 100, 400, 25);
        frame.add(resultLabel);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String encryptedText = encrypt(inputText);
                resultLabel.setText("Encrypted Text: " + encryptedText);
            }
        });

        frame.setVisible(true);
    }
}
