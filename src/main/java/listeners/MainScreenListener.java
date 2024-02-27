package listeners;

import screens.LoginScreen;
import screens.MainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenListener implements ActionListener {

    private final MainScreen mainScreen;

    public MainScreenListener(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Logout")) {
            mainScreen.dispose();
            new LoginScreen();
        }
    }
}
