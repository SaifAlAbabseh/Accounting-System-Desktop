package screens_common_things;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Styles {

    public static void styleLabel(JLabel label, Color textColor) {
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
    }

    public static void styleInputField(JComponent field) {
        field.setForeground(Color.BLACK);
        field.setBorder(new LineBorder(Color.GREEN, 2));
        field.setFont(new Font("Arial", Font.BOLD, 20));
        field.setPreferredSize(new Dimension(400, 100));
    }
}
