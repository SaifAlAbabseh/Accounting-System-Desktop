package screens_common_things;

import listeners.CommonListener;

import javax.swing.*;
import java.awt.*;

public class GoBackButton {

    private JButton goBackButton;
    private JPanel goBackButtonPanel;

    public GoBackButton(JFrame screen) {
        initComponents();
        setListeners(screen);
    }

    private void initComponents() {
        goBackButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goBackButton = new JButton();
        ImageIcon icon = new ImageIcon("src/main/resources/images/go-back-arrow.png");
        goBackButton.setIcon(icon);
        styleGoBackButton();
        goBackButtonPanel.add(goBackButton);
        goBackButtonPanel.setBackground(Color.WHITE);
    }

    private void styleGoBackButton() {
        goBackButton.setPreferredSize(new Dimension(120, 120));
        goBackButton.setBackground(Color.WHITE);
        goBackButton.setFocusable(false);
        goBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        goBackButton.setBorder(null);
    }

    private void setListeners(JFrame screen) {
        goBackButton.addActionListener(new CommonListener(screen, "go-back"));
    }

    public JPanel getGoBackButtonPanel() {
        return goBackButtonPanel;
    }


}
