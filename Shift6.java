import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarCipherUI {

    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                // Shift letters backward by 6 with wrap-around
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base - 6 + 26) % 26 + base);
                result.append(encryptedChar);
            } else if (Character.isDigit(ch)) {
                // Shift numbers backward by 6 with wrap-around
                char encryptedDigit = (char) ((ch - '0' - 6 + 10) % 10 + '0');
                result.append(encryptedDigit);
            } else {
                result.append(ch); // Keep special characters unchanged
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Caesar Cipher Encryption (6 Shift)");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Enter text:");
        label.setBounds(20, 20, 100, 25);
        frame.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(120, 20, 200, 25);
        frame.add(textField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(120, 60, 100, 25);
        frame.add(encryptButton);

        JLabel resultLabel = new JLabel("Encrypted Text: ");
        resultLabel.setBounds(20, 100, 350, 25);
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
