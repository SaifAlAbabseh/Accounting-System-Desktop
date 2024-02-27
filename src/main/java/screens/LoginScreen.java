package screens;

import listeners.LoginScreenListener;
import screens_common_things.TitleBar;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JLabel adminIdLabel, adminPasswordLabel;
    private JTextField adminIdField;
    private JPasswordField adminPasswordField;
    private JButton loginButton;

    public LoginScreen() {
        initComponents();
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public void initComponents() {
        JPanel titleBar = new TitleBar("Login", this).getTitleBarPanel();
        add(titleBar, BorderLayout.NORTH);
        adminIdLabel = new JLabel("Admin ID");
        adminPasswordLabel = new JLabel("Admin Password");
        styleFieldLabels();
        adminIdField = new JTextField();
        JPanel adminIdFieldPanel = new JPanel();
        adminIdFieldPanel.setBackground(Color.WHITE);
        adminIdFieldPanel.add(adminIdField);
        adminPasswordField = new JPasswordField();
        JPanel adminPasswordFieldPanel = new JPanel();
        adminPasswordFieldPanel.setBackground(Color.WHITE);
        adminPasswordFieldPanel.add(adminPasswordField);
        styleInputFields();
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginScreenListener(this));
        styleLoginButton();
        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setBackground(Color.WHITE);
        loginButtonPanel.add(loginButton);
        JPanel loginMainPanel = new JPanel(new GridLayout(5, 1));
        loginMainPanel.setBackground(Color.WHITE);
        loginMainPanel.add(adminIdLabel);
        loginMainPanel.add(adminIdFieldPanel);
        loginMainPanel.add(adminPasswordLabel);
        loginMainPanel.add(adminPasswordFieldPanel);
        loginMainPanel.add(loginButtonPanel);
        add(loginMainPanel, BorderLayout.CENTER);
    }

    public void styleFieldLabels() {
        adminIdLabel.setForeground(Color.BLACK);
        adminIdLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminIdLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        adminPasswordLabel.setForeground(Color.BLACK);
        adminPasswordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminPasswordLabel.setVerticalAlignment(SwingConstants.BOTTOM);
    }

    public void styleInputFields() {
        adminIdField.setForeground(Color.BLACK);
        adminIdField.setBorder(new LineBorder(Color.GREEN, 2));
        adminIdField.setFont(new Font("Arial", Font.BOLD, 20));
        adminIdField.setPreferredSize(new Dimension(400, 100));
        adminPasswordField.setForeground(Color.BLACK);
        adminPasswordField.setBorder(new LineBorder(Color.GREEN, 2));
        adminPasswordField.setFont(new Font("Arial", Font.BOLD, 20));
        adminPasswordField.setPreferredSize(new Dimension(400, 100));
    }

    public void styleLoginButton() {
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.GREEN);
        loginButton.setFocusable(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFont(new Font("Arial", Font.BOLD, 30));
        loginButton.setPreferredSize(new Dimension(400, 100));
    }

    public JTextField getAdminIdField() {
        return adminIdField;
    }

    public JPasswordField getAdminPasswordField() {
        return adminPasswordField;
    }
}
