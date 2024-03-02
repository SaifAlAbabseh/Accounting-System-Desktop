package screens;

import listeners.InsertProductsByImportScreenListener;
import screens_common_things.ScreenConfig;
import screens_common_things.Styles;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InsertProductsByImportScreen extends JFrame {

    private JLabel templateLabel;
    private JButton downloadTemplateFileButton, fileChooserButton, importButton;
    private JPanel mainPanel;

    public InsertProductsByImportScreen() {
        initComponents();
        ScreenConfig.initFrame(this);
    }

    private void initComponents() {
        ScreenConfig.initTitleBar(this, "Insert Products By Import");
        templateLabel = new JLabel("Don't have the template file? download it.");
        Styles.styleLabel(templateLabel, Color.BLACK);
        downloadTemplateFileButton = new JButton("Download Template File");
        styleButton(downloadTemplateFileButton, Color.BLUE);
        downloadTemplateFileButton.addActionListener(new InsertProductsByImportScreenListener(this));
        fileChooserButton = new JButton("Choose .CSV file to import");
        fileChooserButton.addActionListener(new InsertProductsByImportScreenListener(fileChooserButton));
        styleFileChooserButton();
        importButton = new JButton("Import");
        importButton.addActionListener(new InsertProductsByImportScreenListener(fileChooserButton, ""));
        styleButton(importButton, Color.GREEN);
        mainPanel = new JPanel(new GridLayout(4, 1));
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.WHITE);
        labelPanel.add(templateLabel);
        JPanel downloadPanel = new JPanel();
        downloadPanel.setBackground(Color.WHITE);
        downloadPanel.add(downloadTemplateFileButton);
        JPanel fileChooserPanel = new JPanel();
        fileChooserPanel.setBackground(Color.WHITE);
        fileChooserPanel.add(fileChooserButton);
        JPanel ImportButtonPanel = new JPanel();
        ImportButtonPanel.setBackground(Color.WHITE);
        ImportButtonPanel.add(importButton);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(labelPanel);
        mainPanel.add(downloadPanel);
        mainPanel.add(fileChooserPanel);
        mainPanel.add(ImportButtonPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setPreferredSize(new Dimension(500, 100));
    }

    private void styleFileChooserButton() {
        fileChooserButton.setBackground(Color.WHITE);
        fileChooserButton.setBorder(new LineBorder(Color.GREEN, 5));
        fileChooserButton.setForeground(Color.GREEN);
        fileChooserButton.setFocusable(false);
        fileChooserButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fileChooserButton.setFont(new Font("Arial", Font.BOLD, 25));
        fileChooserButton.setPreferredSize(new Dimension(400, 100));
    }
}
