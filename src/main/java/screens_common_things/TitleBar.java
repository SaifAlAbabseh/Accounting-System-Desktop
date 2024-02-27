package screens_common_things;

import listeners.CommonListener;
import javax.swing.*;
import java.awt.*;

public class TitleBar {

    private JPanel titleBarPanel;
    private JButton exitButton, minimizeButton;
    private JLabel titleLabel;

    public TitleBar(String titleText, JFrame screen) {
        initComponents(titleText);
        setListeners(screen);
    }

    public void initComponents(String titleText) {
        titleBarPanel = new JPanel();
        titleBarPanel.setLayout(new GridLayout(1, 2));
        titleBarPanel.setBackground(Color.WHITE);
        exitButton = new JButton("X");
        styleExitButton();
        minimizeButton = new JButton("_");
        styleMinimizeButton();
        JPanel titleBarLeftPanel = new JPanel();
        titleBarLeftPanel.setBackground(Color.WHITE);
        titleBarLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel titleBarRightPanel = new JPanel();
        titleBarRightPanel.setBackground(Color.WHITE);
        titleBarRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        titleLabel = new JLabel(titleText);
        styleTitleLabel();
        titleBarRightPanel.add(minimizeButton);
        titleBarRightPanel.add(exitButton);
        titleBarLeftPanel.add(titleLabel);
        titleBarPanel.add(titleBarLeftPanel);
        titleBarPanel.add(titleBarRightPanel);
    }

    public void setListeners(JFrame screen) {
        exitButton.addActionListener(new CommonListener(screen));
        minimizeButton.addActionListener(new CommonListener(screen));
    }

    public void styleExitButton() {
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFocusable(false);
    }

    public void styleMinimizeButton() {
        minimizeButton.setBackground(Color.BLUE);
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 20));
        minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeButton.setFocusable(false);
    }

    public void styleTitleLabel() {
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
    }

    public JPanel getTitleBarPanel() {
        return titleBarPanel;
    }
}
