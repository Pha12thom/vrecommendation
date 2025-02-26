import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarCipherUI {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base + shift) % 26 + base);
                result.append(encryptedChar);
            } else if (Character.isDigit(ch)) {
                char encryptedDigit = (char) ((ch - '0' + shift) % 10 + '0');
                result.append(encryptedDigit);
            } else {
                result.append(ch); // Keep special characters unchanged
            }
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Caesar Cipher Encryption");
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Enter text:");
        label.setBounds(20, 20, 100, 25);
        frame.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(120, 20, 250, 25);
        frame.add(textField);

        JLabel shiftLabel = new JLabel("Enter shift:");
        shiftLabel.setBounds(20, 60, 100, 25);
        frame.add(shiftLabel);

        JTextField shiftField = new JTextField();
        shiftField.setBounds(120, 60, 50, 25);
        frame.add(shiftField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(120, 100, 100, 25);
        frame.add(encryptButton);

        JLabel resultLabel = new JLabel("Encrypted Text: ");
        resultLabel.setBounds(20, 140, 400, 25);
        frame.add(resultLabel);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                try {
                    int shift = Integer.parseInt(shiftField.getText()); // Get shift value
                    String encryptedText = encrypt(inputText, shift);
                    resultLabel.setText("Encrypted Text: " + encryptedText);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter a valid shift number.");
                }
            }
        });

        frame.setVisible(true);
    }
}
