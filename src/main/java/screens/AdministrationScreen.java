package screens;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import screens_common_things.AdminInfo;
import screens_common_things.ScreenConfig;
import screens_common_things.Session;
import screens_common_things.Styles;
import util.Host;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AdministrationScreen extends JFrame {

    public static int filterMenuSelected = 0;
    public static String filterValue = "";
    private JButton addNewAdminButtonLink, filterButton;
    private JComboBox<String> filterMenu;
    private JTextField filterInputField;
    private JPanel mainPanel, addNewAdminButtonLinkPanel, filterPanel, resultsHeaderPanel, resultsPanel, resultsBodyPanel;

    public AdministrationScreen() {
        initComponents();
        ScreenConfig.initFrame(this);
        try {
            Response[] responses = getAdmins();
            if(responses.length > 1) {
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
        Styles.styleLabel(adminIdLabel);
        Styles.styleLabel(adminNameLabel);
        Styles.styleLabel(adminPasswordLabel);
        Styles.styleLabel(isSuperAdminLabel);
        Styles.styleLabel(hasInsertPrivilegeLabel);
        Styles.styleLabel(hasViewEditPrivilegeLabel);
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
        Styles.styleLabel(filterLabel);
        filterMenu = new JComboBox<>(new String[] {"", "Admin ID", "Admin Name"});
        Styles.styleInputField(filterMenu);
        filterInputField = new JTextField();
        Styles.styleInputField(filterInputField);
        mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.setBackground(Color.WHITE);
        addNewAdminButtonLinkPanel = new JPanel();
        addNewAdminButtonLinkPanel.setBackground(Color.WHITE);
        filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(resultsPanel.getPreferredSize().width, resultsPanel.getPreferredSize().height + 500));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.add(resultsHeaderPanel);
        resultsPanel.add(resultsBodyPanelScroll);
        ScreenConfig.initTitleBar(this, "Administration");
        addNewAdminButtonLink = new JButton("Add New Admin");
        styleButton(addNewAdminButtonLink, Color.BLUE, new Dimension(400, 100));
        filterButton = new JButton("GO");
        styleButton(filterButton, Color.GREEN, new Dimension(100, 100));
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

    private void styleButton(JButton button, Color backgroundColor, Dimension dimension) {
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setPreferredSize(dimension);
    }

    private Response[] getAdmins() throws URISyntaxException {
        URI apiURL = new URI(Host.domainForAPIs + "/getAdmins.php");
        Map<String, String> headers = new HashMap<>();
        headers.put("auth_token", new Session().getAuthToken());
        headers.put("admin_id", AdminInfo.adminId);
        //Get the search header and search value
        String searchHeader = (filterMenuSelected == 0 || filterMenuSelected == 1)?"admin_id":"admin_name";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("searchCriteriaHeader", searchHeader);
        jsonObject.put("searchCriteriaValue", filterValue);
        Response response = RestAssured.with().headers(headers).body(jsonObject.toString()).request("GET", apiURL);
        if(response.getStatusCode() == 200) {
            showResults(response);
            return new Response[] {null};
        }
        else return new Response[] {response, null};
    }

    private void showResults(Response response) {
        JSONArray array = new JSONArray(response.jsonPath().getString("admins"));
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            JPanel resultPanel = new JPanel(new GridLayout(1, 6));
            for(String key : object.keySet()) {
                if() {

                }
                resultPanel.add();
            }
        }
    }
}
