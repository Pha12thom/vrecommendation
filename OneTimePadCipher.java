import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class OneTimePadUI {

    public static String generateKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('A' + random.nextInt(26)); // Random letter A-Z
            key.append(randomChar);
        }

        return key.toString();
    }

    public static String encrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", ""); // Keep only letters

        if (text.length() != key.length()) {
            return "Error: Key must match text length!";
        }

        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            int plaintextChar = text.charAt(i) - 'A';
            int keyChar = key.charAt(i) - 'A';

            int cipherChar = (plaintextChar + keyChar) % 26; // Mod 26
            encryptedText.append((char) ('A' + cipherChar));
        }

        return encryptedText.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("One-Time Pad Cipher");
        frame.setSize(500, 300);
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

        JLabel keyLabel = new JLabel("Generated Key: ");
        keyLabel.setBounds(20, 100, 400, 25);
        frame.add(keyLabel);

        JLabel resultLabel = new JLabel("Encrypted Text: ");
        resultLabel.setBounds(20, 140, 400, 25);
        frame.add(resultLabel);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String key = generateKey(inputText.length());
                String encryptedText = encrypt(inputText, key);
                keyLabel.setText("Generated Key: " + key);
                resultLabel.setText("Encrypted Text: " + encryptedText);
            }
        });

        frame.setVisible(true);
    }
}
