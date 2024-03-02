package listeners;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import screens.LoginScreen;
import screens.MainScreen;
import screens_common_things.AdminInfo;
import screens_common_things.Session;
import util.DB;
import util.Host;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoginScreenListener implements ActionListener {

    private final LoginScreen loginScreen;

    public LoginScreenListener(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Login")) {
            String adminId = loginScreen.getAdminIdField().getText().trim();
            String adminPassword = new String(loginScreen.getAdminPasswordField().getPassword());
            login(adminId, adminPassword);
        }
    }

    private void login(String adminId, String adminPassword) {
        if(!(adminId.isEmpty() || adminPassword.isEmpty())) {
            try {
                Connection conn = DriverManager.getConnection(DB.url, DB.username, DB.password);
                Statement stmt = conn.createStatement();
                String query = "SELECT `admin_id`, `admin_name`, `is_super_admin`, `has_insert_privilege`, `has_view_edit_privilege` FROM admins WHERE BINARY admin_id='" + adminId + "' AND admin_password='" + adminPassword + "'";
                ResultSet result = stmt.executeQuery(query);
                if(result.next()) { // Success Login
                    String[] authToken = generateAuthToken(adminId, adminPassword);
                    if(authToken.length > 1) {
                        if(authToken[1] == null) {
                            JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, authToken[1], "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        AdminInfo.adminId = result.getString("admin_id");
                        AdminInfo.adminName = result.getString("admin_name");
                        AdminInfo.isSuperAdmin = result.getString("is_super_admin");
                        AdminInfo.hasInsertPrivilege = result.getString("has_insert_privilege");
                        AdminInfo.hasViewEditPrivilege = result.getString("has_view_edit_privilege");
                        new Session(authToken[0]);
                        loginScreen.dispose();
                        new MainScreen();
                    }
                }
                else { // Failed Login
                    JOptionPane.showMessageDialog(null, "Invalid Admin Data", "Error", JOptionPane.ERROR_MESSAGE);
                }
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] generateAuthToken(String adminId, String adminPassword) throws URISyntaxException {
        Map<String, String> headers = new HashMap<>();
        headers.put("admin_id", adminId);
        headers.put("admin_password", adminPassword);
        URI apiURL = new URI(Host.domainForAPIs + "/generateAuthToken.php");
        Response response =
                RestAssured.
                        with().
                        headers(headers).
                        request("GET", apiURL);
        String error = response.jsonPath().getString("error");
        if(response.getStatusCode() == 200) {
            return new String[] {response.jsonPath().getString("auth_token")};
        }
        else return new String[] {"error", error};
    }
}
