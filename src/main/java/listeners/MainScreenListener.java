package listeners;

import screens.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenListener implements ActionListener {

    private final MainScreen mainScreen;
    private final String clickedButton;

    public MainScreenListener(MainScreen mainScreen, String clickedButton) {
        this.mainScreen = mainScreen;
        this.clickedButton = clickedButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(clickedButton.equals("logout")) {
            mainScreen.dispose();
            new LoginScreen();
        }
        else if(clickedButton.equals("insert-products")) {
            mainScreen.dispose();
            new InsertProductsScreen();
        }
        else if(clickedButton.equals("insert-products-by-import")) {
            mainScreen.dispose();
            new InsertProductsByImportScreen();
        }
        else if(clickedButton.equals("view-products")) {
            mainScreen.dispose();
            new ViewProductsScreen();
        }
        else if(clickedButton.equals("administration")) {
            mainScreen.dispose();
            new AdministrationScreen();
        }
    }
}
