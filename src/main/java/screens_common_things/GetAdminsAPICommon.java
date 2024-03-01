package screens_common_things;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import listeners.AdministrationScreenListener;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Host;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class GetAdminsAPICommon {

    private int filterMenuSelected = 0;
    private String filterValue = "";
    private JPanel resultsBodyPanel;

    public GetAdminsAPICommon() {}
    public GetAdminsAPICommon(JPanel resultsBodyPanel) {
        this.resultsBodyPanel = resultsBodyPanel;
    }

    public GetAdminsAPICommon(int filterMenuSelected, String filterValue, JPanel resultsBodyPanel) {
        this.filterMenuSelected = filterMenuSelected;
        this.filterValue = filterValue;
        this.resultsBodyPanel = resultsBodyPanel;
    }

    public Response[] getAdmins() throws URISyntaxException {
        URI apiURL = new URI(Host.domainForAPIs + "/getAdmins.php");
        Map<String, String> headers = new HashMap<>();
        headers.put("auth_token", new Session().getAuthToken());
        headers.put("admin_id", AdminInfo.adminId);
        //Get the search header and search value
        String searchHeader = (filterMenuSelected == 0 || filterMenuSelected == 1) ? "admin_id" : "admin_name";
        Response response =
                RestAssured.
                        given().
                        headers(headers).
                        queryParam("searchCriteriaHeader", searchHeader).
                        queryParam("searchCriteriaValue", filterValue).
                        request("GET", apiURL);
        if (response.getStatusCode() == 200) {
            showResults(response);
            return new Response[]{null};
        } else return new Response[]{response, null};
    }

    public void showResults(Response response) {
        resultsBodyPanel.removeAll();
        resultsBodyPanel.revalidate();
        resultsBodyPanel.repaint();
        JSONArray array = new JSONArray(response.jsonPath().getList("admins"));
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            JPanel resultPanel = new JPanel(new GridLayout(1, 6));
            resultPanel.setPreferredSize(new Dimension(resultPanel.getPreferredSize().width, 100));
            addComponent(object, resultPanel, i);
            resultsBodyPanel.add(resultPanel);
        }
        resultsBodyPanel.revalidate();
        resultsBodyPanel.repaint();
    }

    public void addComponent(JSONObject object, JPanel resultPanel, int i) {

        String adminId = object.getString("admin_id");

        Color panelColor = Color.WHITE;
        if(i % 2 == 0) panelColor = Color.BLUE;

        JPanel adminIdAndDeleteButtonPanel = new JPanel();
        adminIdAndDeleteButtonPanel.setLayout(new BoxLayout(adminIdAndDeleteButtonPanel, BoxLayout.X_AXIS));
        adminIdAndDeleteButtonPanel.setAlignmentX(SwingConstants.CENTER);
        adminIdAndDeleteButtonPanel.setBackground(panelColor);
        JButton deleteAdminButton = new JButton("X");
        styleButton(deleteAdminButton, Color.RED, new Dimension(100, 100));
        JLabel adminIdLabel = new JLabel(object.getString("admin_id"));
        Styles.styleLabel(adminIdLabel, Color.BLACK);
        adminIdLabel.setVerticalAlignment(SwingConstants.CENTER);
        adminIdAndDeleteButtonPanel.add(deleteAdminButton);
        adminIdAndDeleteButtonPanel.add(adminIdLabel);
        resultPanel.add(adminIdAndDeleteButtonPanel);


        resultPanel.setBackground(panelColor);
        resultPanel.addMouseListener(new AdministrationScreenListener(adminIdAndDeleteButtonPanel, resultPanel, panelColor, deleteAdminButton));
        deleteAdminButton.addMouseListener(new AdministrationScreenListener(adminIdAndDeleteButtonPanel, resultPanel, panelColor, deleteAdminButton));
        deleteAdminButton.addActionListener(new AdministrationScreenListener(adminId, resultsBodyPanel, resultPanel));

        JLabel adminNameLabel = new JLabel(object.getString("admin_name"));
        Styles.styleLabel(adminNameLabel, Color.BLACK);
        adminNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        resultPanel.add(adminNameLabel);

        JLabel adminPasswordLabel = new JLabel(object.getString("admin_password"));
        Styles.styleLabel(adminPasswordLabel, Color.BLACK);
        adminPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
        resultPanel.add(adminPasswordLabel);

        String[] menuItems;

        if (object.getString("is_super_admin").equals("1")) menuItems = new String[]{"yes", "no"};
        else menuItems = new String[]{"no", "yes"};
        JComboBox<String> isSuperAdminMenu = new JComboBox<>(menuItems);
        isSuperAdminMenu.addItemListener(new AdministrationScreenListener(adminId, "is_super_admin", isSuperAdminMenu));
        resultPanel.add(isSuperAdminMenu);
        isSuperAdminMenu.addMouseListener(new AdministrationScreenListener(adminIdAndDeleteButtonPanel, resultPanel, panelColor, deleteAdminButton));

        if (object.getString("has_insert_privilege").equals("1")) menuItems = new String[]{"yes", "no"};
        else menuItems = new String[]{"no", "yes"};
        JComboBox<String> hasInsertPrivilegeMenu = new JComboBox<>(menuItems);
        hasInsertPrivilegeMenu.addItemListener(new AdministrationScreenListener(adminId, "has_insert_privilege", hasInsertPrivilegeMenu));
        resultPanel.add(hasInsertPrivilegeMenu);
        hasInsertPrivilegeMenu.addMouseListener(new AdministrationScreenListener(adminIdAndDeleteButtonPanel, resultPanel, panelColor, deleteAdminButton));

        if (object.getString("has_view_edit_privilege").equals("1")) menuItems = new String[]{"yes", "no"};
        else menuItems = new String[]{"no", "yes"};
        JComboBox<String> hasViewEditPrivilegeMenu = new JComboBox<>(menuItems);
        hasViewEditPrivilegeMenu.addItemListener(new AdministrationScreenListener(adminId, "has_view_edit_privilege", hasViewEditPrivilegeMenu));
        resultPanel.add(hasViewEditPrivilegeMenu);
        hasViewEditPrivilegeMenu.addMouseListener(new AdministrationScreenListener(adminIdAndDeleteButtonPanel, resultPanel, panelColor, deleteAdminButton));

        styleMenu(isSuperAdminMenu);
        styleMenu(hasInsertPrivilegeMenu);
        styleMenu(hasViewEditPrivilegeMenu);

    }

    public void styleButton(JButton button, Color backgroundColor, Dimension dimension) {
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setPreferredSize(dimension);
    }

    public void styleMenu(JComboBox<String> menu) {
        menu.setFont(new Font("Arial", Font.BOLD, 25));
        menu.setForeground(Color.BLUE);
        menu.setFocusable(false);
    }
}
