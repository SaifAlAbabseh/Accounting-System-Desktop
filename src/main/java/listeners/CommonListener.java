package listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommonListener implements ActionListener {

    private final JFrame screen;

    public CommonListener(JFrame screen) {
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String whichButton = e.getActionCommand();
        if(whichButton.equals("X")) {
            System.exit(0);
        }
        else if(whichButton.equals("_")) {
            screen.setState(JFrame.ICONIFIED);
        }
    }
}
