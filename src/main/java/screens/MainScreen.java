package screens;

import listeners.MainScreenListener;
import screens_common_things.AdminInfo;
import screens_common_things.TitleBar;
import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame{

    private JButton logoutButton;
    private JPanel featuresPanel;
    private JButton insertProductsButton, insertProductsByImportButton, viewProductsButton, administrationButton;
    private JLabel adminIdLabel, adminNameLabel;

    public MainScreen() {
        initComponents();
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void initComponents() {
        JPanel titleBar = new TitleBar("Welcome", this).getTitleBarPanel();
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adminIdLabel = new JLabel("<html><center>Admin ID<br>" + AdminInfo.adminId + "</center></html>");
        adminNameLabel = new JLabel("<html><center>Admin Name<br>" + AdminInfo.adminName + "</center></html>");
        styleAdminInfoLabels();
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new MainScreenListener(this, "logout"));
        styleLogoutButton();
        logoutButtonPanel.add(logoutButton);
        logoutButtonPanel.add(adminIdLabel);
        logoutButtonPanel.add(adminNameLabel);
        logoutButtonPanel.setBackground(Color.WHITE);
        JPanel titleBarMainPanel = new JPanel(new GridLayout(2, 1));
        titleBarMainPanel.setBackground(Color.WHITE);
        titleBarMainPanel.add(titleBar);
        titleBarMainPanel.add(logoutButtonPanel);
        featuresPanel = new JPanel();
        featuresPanel.setBackground(Color.WHITE);
        insertProductsButton = new JButton("<html><div style='text-align: center; width: 100px;'>Insert Products</div></html>");
        insertProductsButton.addActionListener(new MainScreenListener(this, "insert-products"));
        insertProductsByImportButton = new JButton("<html><div style='text-align: center; width: 100px;'>Insert Products By Importing File</div></html>");
        insertProductsByImportButton.addActionListener(new MainScreenListener(this, "insert-products-by-import"));
        viewProductsButton = new JButton("<html><div style='text-align: center; width: 100px;'>View Inserted Products</div></html>");
        viewProductsButton.addActionListener(new MainScreenListener(this, "view-products"));
        administrationButton = new JButton("<html><div style='text-align: center; width: 100px;'>Administration</div></html>");
        administrationButton.addActionListener(new MainScreenListener(this, "administration"));
        styleFeatures();
        showFeatures();
        add(titleBarMainPanel, BorderLayout.NORTH);
        add(featuresPanel, BorderLayout.CENTER);
    }

    private void styleLogoutButton() {
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(Color.RED);
        logoutButton.setFocusable(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 30));
        logoutButton.setPreferredSize(new Dimension(200, 100));
    }

    private void styleAdminInfoLabels() {
        adminIdLabel.setForeground(Color.DARK_GRAY);
        adminIdLabel.setFont(new Font("Arial", Font.BOLD, 30));
        adminNameLabel.setForeground(Color.DARK_GRAY);
        adminNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
    }

    private void showFeatures() {
        if(AdminInfo.hasInsertPrivilege.equals("1")) {
            featuresPanel.add(insertProductsButton);
            featuresPanel.add(insertProductsByImportButton);
        }
        if(AdminInfo.hasViewEditPrivilege.equals("1")) {
            featuresPanel.add(viewProductsButton);
        }
        if(AdminInfo.isSuperAdmin.equals("1")) {
            featuresPanel.add(administrationButton);
        }
    }

    private void styleFeatures() {
        insertProductsButton.setBackground(new Color(0, 0, 255));
        insertProductsByImportButton.setBackground(new Color(165, 42, 42));
        viewProductsButton.setBackground(new Color(0, 0, 0));
        administrationButton.setBackground(new Color(0, 128, 0));
        styleFeaturesCommon(insertProductsButton);
        styleFeaturesCommon(insertProductsByImportButton);
        styleFeaturesCommon(viewProductsButton);
        styleFeaturesCommon(administrationButton);
    }

    private void styleFeaturesCommon(JButton button) {
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(350, 200));
    }
}
