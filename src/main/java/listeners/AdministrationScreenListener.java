package listeners;

import screens.AdministrationScreen;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AdministrationScreenListener implements ItemListener {

    private String adminId, whichPrivilege;

    public AdministrationScreenListener(String adminId, String whichPrivilege) {
        this.adminId = adminId;
        this.whichPrivilege = whichPrivilege;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
