package screens;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import listeners.AdministrationScreenListener;
import org.json.JSONArray;
import org.json.JSONObject;
import screens_common_things.*;
import util.DB;
import util.Host;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdministrationScreen extends JFrame {

    private JButton addNewAdminButtonLink, filterButton;
    private JComboBox<String> filterMenu;
    private JTextField filterInputField;
    private JPanel mainPanel, addNewAdminButtonLinkPanel, filterPanel, resultsHeaderPanel, resultsPanel, resultsBodyPanel;
    private GetAdminsAPICommon commonMethods;

    public AdministrationScreen() {
        commonMethods = new GetAdminsAPICommon();
        initComponents();
        ScreenConfig.initFrame(this);
        commonMethods = new GetAdminsAPICommon(resultsBodyPanel);
        try {
            Response[] responses = commonMethods.getAdmins();
            if (responses.length > 1) {
                JOptionPane.showMessageDialog(null, responses[0].jsonPath().getString("error"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        JLabel adminIdLabel = new JLabel("Admin ID"),
                adminNameLabel = new JLabel("Admin Name"),
                adminPasswordLabel = new JLabel("Admin Password"),
                isSuperAdminLabel = new JLabel("Is Super Admin"),
                hasInsertPrivilegeLabel = new JLabel("Has Insert Privilege"),
                hasViewEditPrivilegeLabel = new JLabel("Has View/Edit Privilege");
        Styles.styleLabel(adminIdLabel, Color.BLACK);
        Styles.styleLabel(adminNameLabel, Color.BLACK);
        Styles.styleLabel(adminPasswordLabel, Color.BLACK);
        Styles.styleLabel(isSuperAdminLabel, Color.BLACK);
        Styles.styleLabel(hasInsertPrivilegeLabel, Color.BLACK);
        Styles.styleLabel(hasViewEditPrivilegeLabel, Color.BLACK);
        resultsHeaderPanel = new JPanel(new GridLayout(1, 6));
        resultsHeaderPanel.add(adminIdLabel);
        resultsHeaderPanel.add(adminNameLabel);
        resultsHeaderPanel.add(adminPasswordLabel);
        resultsHeaderPanel.add(isSuperAdminLabel);
        resultsHeaderPanel.add(hasInsertPrivilegeLabel);
        resultsHeaderPanel.add(hasViewEditPrivilegeLabel);
        resultsHeaderPanel.setBackground(Color.WHITE);
        resultsBodyPanel = new JPanel();
        resultsBodyPanel.setLayout(new BoxLayout(resultsBodyPanel, BoxLayout.Y_AXIS));
        resultsBodyPanel.setBackground(Color.WHITE);
        JScrollPane resultsBodyPanelScroll = new JScrollPane(resultsBodyPanel);
        resultsBodyPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel filterLabel = new JLabel("Filter: ");
        Styles.styleLabel(filterLabel, Color.BLACK);
        filterMenu = new JComboBox<>(new String[]{"", "Admin ID", "Admin Name"});
        Styles.styleInputField(filterMenu);
        filterInputField = new JTextField();
        Styles.styleInputField(filterInputField);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        addNewAdminButtonLinkPanel = new JPanel();
        addNewAdminButtonLinkPanel.setAlignmentY(SwingConstants.NORTH);
        addNewAdminButtonLinkPanel.setBackground(Color.WHITE);
        filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(resultsPanel.getPreferredSize().width, resultsPanel.getPreferredSize().height + 200));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.add(resultsHeaderPanel);
        resultsPanel.add(resultsBodyPanelScroll);
        ScreenConfig.initTitleBar(this, "Administration");
        addNewAdminButtonLink = new JButton("Add New Admin");
        addNewAdminButtonLink.addActionListener(new AdministrationScreenListener(this));
        commonMethods.styleButton(addNewAdminButtonLink, Color.BLUE, new Dimension(400, 100));
        filterButton = new JButton("GO");
        filterButton.addActionListener(new AdministrationScreenListener(filterMenu, filterInputField, resultsBodyPanel));
        commonMethods.styleButton(filterButton, Color.GREEN, new Dimension(100, 100));
        addNewAdminButtonLinkPanel.add(addNewAdminButtonLink);
        filterPanel.add(filterLabel);
        filterPanel.add(filterMenu);
        filterPanel.add(filterInputField);
        filterPanel.add(filterButton);
        mainPanel.add(addNewAdminButtonLinkPanel);
        mainPanel.add(filterPanel);
        mainPanel.add(resultsPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void showAddNewAdminScreen() {
        new AddNewAdminScreen(this);
    }

    private static class AddNewAdminScreen extends JFrame implements ActionListener {

        private JComboBox<String> isSuperAdminMenu, hasInsertPrivilegeMenu, hasViewEditPrivilegeMenu;
        private JButton exitButton, addNewAdminButton;
        private JTextField adminNameField;
        private JPasswordField adminPasswordField;
        private JLabel adminNameLabel, adminPasswordLabel, isSuperAdminLabel, hasInsertPrivilegeLabel, hasViewEditPrivilegeLabel;
        private JPanel mainPanel, labelsPanel, inputFieldsPanel, addAdminButtonPanel;
        private AdministrationScreen administrationScreen;

        public AddNewAdminScreen(AdministrationScreen administrationScreen) {
            this.administrationScreen = administrationScreen;
            initComponents();
            setUndecorated(true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize(screenSize.width, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.BLACK);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void initComponents() {
            mainPanel = new JPanel(new GridLayout(3, 1));
            mainPanel.setBackground(Color.BLACK);
            labelsPanel = new JPanel(new GridLayout(1, 5));
            labelsPanel.setBackground(Color.BLACK);
            inputFieldsPanel = new JPanel(new GridLayout(1, 5));
            inputFieldsPanel.setBackground(Color.BLACK);
            addAdminButtonPanel = new JPanel();
            addAdminButtonPanel.setBackground(Color.BLACK);

            addNewAdminButton = new JButton("Add New Admin");
            addNewAdminButton.addActionListener(this);
            styleAddButton();
            addAdminButtonPanel.add(addNewAdminButton);

            exitButton = new JButton("X");
            exitButton.addActionListener(this);
            styleExitButton();
            JPanel titleBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            titleBarPanel.setBackground(Color.BLACK);
            titleBarPanel.add(exitButton);

            adminNameLabel = new JLabel("Admin Name");
            Styles.styleLabel(adminNameLabel, Color.WHITE);
            adminNameLabel.setVerticalAlignment(SwingConstants.CENTER);
            labelsPanel.add(adminNameLabel);

            adminPasswordLabel = new JLabel("Admin Password");
            Styles.styleLabel(adminPasswordLabel, Color.WHITE);
            adminPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
            labelsPanel.add(adminPasswordLabel);

            isSuperAdminLabel = new JLabel("Is Super Admin");
            Styles.styleLabel(isSuperAdminLabel, Color.WHITE);
            isSuperAdminLabel.setVerticalAlignment(SwingConstants.CENTER);
            labelsPanel.add(isSuperAdminLabel);

            hasInsertPrivilegeLabel = new JLabel("Has Insert Privilege");
            Styles.styleLabel(hasInsertPrivilegeLabel, Color.WHITE);
            hasInsertPrivilegeLabel.setVerticalAlignment(SwingConstants.CENTER);
            labelsPanel.add(hasInsertPrivilegeLabel);

            hasViewEditPrivilegeLabel = new JLabel("Has View/Edit Privilege");
            Styles.styleLabel(hasViewEditPrivilegeLabel, Color.WHITE);
            hasViewEditPrivilegeLabel.setVerticalAlignment(SwingConstants.CENTER);
            labelsPanel.add(hasViewEditPrivilegeLabel);

            adminNameField = new JTextField();
            adminPasswordField = new JPasswordField();
            styleInputFields(adminNameField);
            styleInputFields(adminPasswordField);
            inputFieldsPanel.add(adminNameField);
            inputFieldsPanel.add(adminPasswordField);

            String[] menuItems = new String[] {"no", "yes"};

            isSuperAdminMenu = new JComboBox<>(menuItems);
            inputFieldsPanel.add(isSuperAdminMenu);

            hasInsertPrivilegeMenu = new JComboBox<>(menuItems);
            inputFieldsPanel.add(hasInsertPrivilegeMenu);

            hasViewEditPrivilegeMenu = new JComboBox<>(menuItems);
            inputFieldsPanel.add(hasViewEditPrivilegeMenu);

            GetAdminsAPICommon stylesCommon = new GetAdminsAPICommon();
            stylesCommon.styleMenu(isSuperAdminMenu);
            stylesCommon.styleMenu(hasInsertPrivilegeMenu);
            stylesCommon.styleMenu(hasViewEditPrivilegeMenu);

            mainPanel.add(labelsPanel);
            mainPanel.add(inputFieldsPanel);
            mainPanel.add(addAdminButtonPanel);

            add(titleBarPanel, BorderLayout.NORTH);
            add(mainPanel, BorderLayout.CENTER);
        }

        private void styleExitButton() {
            exitButton.setBackground(Color.RED);
            exitButton.setForeground(Color.WHITE);
            exitButton.setFont(new Font("Arial", Font.BOLD, 20));
            exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            exitButton.setFocusable(false);
        }

        private void styleInputFields(JTextComponent inputField) {
            inputField.setForeground(Color.BLACK);
            inputField.setBorder(new LineBorder(Color.GREEN, 2));
            inputField.setFont(new Font("Arial", Font.BOLD, 20));
        }

        private void styleAddButton() {
            addNewAdminButton.setForeground(Color.WHITE);
            addNewAdminButton.setBackground(Color.GREEN);
            addNewAdminButton.setFocusable(false);
            addNewAdminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            addNewAdminButton.setFont(new Font("Arial", Font.BOLD, 30));
            addNewAdminButton.setPreferredSize(new Dimension(400, 100));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("X")) {
                this.dispose();
                administrationScreen.dispose();
                new AdministrationScreen();
            }
            else if(e.getActionCommand().equals("Add New Admin")) {
                try {
                    String adminName = adminNameField.getText().trim();
                    String adminPassword = new String(adminPasswordField.getPassword()).trim();
                    int isSuperAdmin = isSuperAdminMenu.getSelectedIndex();
                    int hasInsertPriv = hasInsertPrivilegeMenu.getSelectedIndex();
                    int hasViewEditPriv = hasViewEditPrivilegeMenu.getSelectedIndex();
                    if(adminName.isEmpty() || adminPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Check Fields", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        insertNewAdmin(adminName, adminPassword, isSuperAdmin, hasInsertPriv, hasViewEditPriv);
                        JOptionPane.showMessageDialog(null, "Successfully Added Admin: " + adminName, "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        administrationScreen.dispose();
                        new AdministrationScreen();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void insertNewAdmin(String adminName, String adminPassword, int isSuperAdmin, int hasInsertPriv, int hasViewEditPriv) throws Exception{
            Connection conn = DriverManager.getConnection(DB.url, DB.username, DB.password);
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO admins(`admin_name`,`admin_password`,`is_super_admin`,`has_insert_privilege`,`has_view_edit_privilege`) VALUES('" + adminName + "', '" + adminPassword + "', '" + isSuperAdmin + "', '" + hasInsertPriv + "', '" + hasViewEditPriv + "')";
            int result = stmt.executeUpdate(query);
            if(result == 0) throw new Exception("Couldn't add admin, try again please");
            stmt.close();
            conn.close();
        }
    }
}
