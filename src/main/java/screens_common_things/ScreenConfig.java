package screens_common_things;

import javax.swing.*;
import java.awt.*;

public class ScreenConfig {

    public static void initFrame(JFrame screen) {
        screen.setUndecorated(true);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.getContentPane().setBackground(Color.WHITE);
        screen.setVisible(true);
    }

    public static void initTitleBar(JFrame screen, String titleText) {
        JPanel titleBar = new TitleBar(titleText, screen).getTitleBarPanel();
        JPanel goBackButtonPanel = new GoBackButton(screen).getGoBackButtonPanel();
        JPanel titleBarMainPanel = new JPanel(new GridLayout(2, 1));
        titleBarMainPanel.setBackground(Color.WHITE);
        titleBarMainPanel.add(titleBar);
        titleBarMainPanel.add(goBackButtonPanel);
        screen.add(titleBarMainPanel, BorderLayout.NORTH);
    }
}
