package listeners;

import screens.MainScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommonListener implements ActionListener {

    private final JFrame screen;
    private final String whichButton;

    public CommonListener(JFrame screen, String whichButton) {
        this.screen = screen;
        this.whichButton = whichButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(whichButton.equals("X")) {
            System.exit(0);
        }
        else if(whichButton.equals("_")) {
            screen.setState(JFrame.ICONIFIED);
        }
        else if(whichButton.equals("go-back")) {
            screen.dispose();
            new MainScreen();
        }
    }
}
