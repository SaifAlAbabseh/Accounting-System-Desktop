package screens;

import listeners.LoginScreenListener;
import screens_common_things.ScreenConfig;
import screens_common_things.Styles;
import screens_common_things.TitleBar;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JTextField adminIdField;
    private JPasswordField adminPasswordField;
    private JButton loginButton;

    public LoginScreen() {
        initComponents();
        ScreenConfig.initFrame(this);
    }

    private void initComponents() {
        JPanel titleBar = new TitleBar("Login", this).getTitleBarPanel();
        add(titleBar, BorderLayout.NORTH);
        JLabel adminIdLabel = new JLabel("Admin ID");
        JLabel adminPasswordLabel = new JLabel("Admin Password");
        Styles.styleLabel(adminIdLabel, Color.BLACK);
        Styles.styleLabel(adminPasswordLabel, Color.BLACK);
        adminIdField = new JTextField();
        JPanel adminIdFieldPanel = new JPanel();
        adminIdFieldPanel.setBackground(Color.WHITE);
        adminIdFieldPanel.add(adminIdField);
        adminPasswordField = new JPasswordField();
        JPanel adminPasswordFieldPanel = new JPanel();
        adminPasswordFieldPanel.setBackground(Color.WHITE);
        adminPasswordFieldPanel.add(adminPasswordField);
        Styles.styleInputField(adminIdField);
        Styles.styleInputField(adminPasswordField);
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

    private void styleLoginButton() {
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
