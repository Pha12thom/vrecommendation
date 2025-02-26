import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VigenereCipherUI {

    public static String encrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char plainChar = text.charAt(i);
            char keyChar = key.charAt(keyIndex);
            
            char encryptedChar = (char) (((plainChar - 'A' + (keyChar - 'A')) % 26) + 'A');
            encryptedText.append(encryptedChar);
            
            keyIndex = (keyIndex + 1) % key.length();
        }

        return encryptedText.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polyalphabetic Cipher (Vigenère)");
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
                String encryptedText = encrypt(inputText, key);
                resultLabel.setText("Encrypted Text: " + encryptedText);
            }
        });

        frame.setVisible(true);
    }
}
