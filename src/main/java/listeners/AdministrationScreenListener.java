package listeners;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import screens.AdministrationScreen;
import screens_common_things.GetAdminsAPICommon;
import screens_common_things.Session;
import util.Host;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AdministrationScreenListener implements ItemListener, MouseListener, ActionListener {

    private String adminId, whichPrivilege;
    private JComboBox<String> menu;
    private JPanel panel, adminIdAndDeleteButtonPanel, rowToDelete, resultsBodyPanel;
    private Color panelColor;
    private JComboBox<String>  filterMenu;
    private JTextField filterInputField;
    private AdministrationScreen administrationScreen;

    public AdministrationScreenListener(AdministrationScreen administrationScreen) {
        this.administrationScreen = administrationScreen;
    }

    public AdministrationScreenListener(JComboBox<String> filterMenu, JTextField filterInputField, JPanel resultsBodyPanel) {
        this.resultsBodyPanel = resultsBodyPanel;
        this.filterMenu = filterMenu;
        this.filterInputField = filterInputField;
    }

    public AdministrationScreenListener(String adminId, String whichPrivilege, JComboBox<String> menu) {
        this.adminId = adminId;
        this.whichPrivilege = whichPrivilege;
        this.menu = menu;
    }

    public AdministrationScreenListener(JPanel adminIdAndDeleteButtonPanel, JPanel panel, Color panelColor) {
        this.panel = panel;
        this.adminIdAndDeleteButtonPanel = adminIdAndDeleteButtonPanel;
        this.panelColor = panelColor;
    }

    public AdministrationScreenListener(String adminId, JPanel panel, JPanel rowToDelete) {
        this.adminId = adminId;
        this.panel = panel;
        this.rowToDelete = rowToDelete;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int toWhat = (("" + menu.getSelectedItem()).equals("yes")) ? 1 : 0;
            try {
                updateAdmin(toWhat);
                JOptionPane.showMessageDialog(null, "Successfully updated Admin " + adminId + "'s " + whichPrivilege, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateAdmin(int toWhat) throws Exception {
        URI apiURL = new URI(Host.domainForAPIs + "/updateAdmin.php");
        Map<String, String> headers = new HashMap<>();
        headers.put("auth_token", new Session().getAuthToken());
        Response response =
                RestAssured.
                        given().
                        headers(headers).
                        queryParam("adminId", adminId).
                        queryParam("stateName", whichPrivilege).
                        queryParam("stateValue", toWhat).
                        request("PUT", apiURL);
        if (response.getStatusCode() != 204) {
            if (response.getStatusCode() == 500) throw new Exception("Server Error");
            else {
                String errorMessage = response.jsonPath().getString("error");
                throw new Exception(errorMessage);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        panel.setBackground(Color.RED);
        adminIdAndDeleteButtonPanel.setBackground(Color.RED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        panel.setBackground(panelColor);
        adminIdAndDeleteButtonPanel.setBackground(panelColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("X")) {
            try {
                deleteAdmin();
                removeAdminRow();
                JOptionPane.showMessageDialog(null, "Successfully deleted Admin " + adminId, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getActionCommand().equals("GO")) {
            int filterMenuSelected = filterMenu.getSelectedIndex();
            String filterValue = filterInputField.getText().trim();
            GetAdminsAPICommon commonMethods = new GetAdminsAPICommon(filterMenuSelected, filterValue, resultsBodyPanel);
            try {
                commonMethods.getAdmins();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getActionCommand().equals("Add New Admin")) {
            administrationScreen.setEnabled(false);
            administrationScreen.showAddNewAdminScreen();
        }
    }

    private void deleteAdmin() throws Exception{
        URI apiURL = new URI(Host.domainForAPIs + "/removeAdmin.php");
        Map<String, String> headers = new HashMap<>();
        headers.put("auth_token", new Session().getAuthToken());
        Response response =
                RestAssured.
                        given().
                        headers(headers).
                        queryParam("adminId", adminId).
                        request("DELETE", apiURL);
        if (response.getStatusCode() != 204) {
            if (response.getStatusCode() == 500) throw new Exception("Server Error");
            else {
                String errorMessage = response.jsonPath().getString("error");
                throw new Exception(errorMessage);
            }
        }
    }

    private void removeAdminRow() {
        panel.remove(rowToDelete);
        panel.revalidate();
        panel.repaint();
    }
}
