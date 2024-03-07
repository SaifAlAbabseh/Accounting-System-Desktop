package screens;

import listeners.InsertProductsScreenListener;
import screens_common_things.ScreenConfig;
import util.DB;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertProductsScreen extends JFrame {

    private JButton insertButton;
    private JTextField productNameField;
    private JSpinner productPriceField, productQuantityField, productTaxField, productDiscountField, productSellingPriceField;

    public InsertProductsScreen() {
        initComponents();
        ScreenConfig.initFrame(this);
    }

    private void initComponents() {
        ScreenConfig.initTitleBar(this, "Insert Products");
        JLabel productNameLabel = new JLabel("Product Name");
        JLabel productPriceLabel = new JLabel("Product Price");
        JLabel productQuantityLabel = new JLabel("Quantity");
        JLabel productTaxLabel = new JLabel("Tax");
        JLabel productDiscountLabel = new JLabel("Discount");
        JLabel productSellingPriceLabel = new JLabel("Selling Price");
        styleInsertLabels(productNameLabel, Color.BLACK);
        styleInsertLabels(productPriceLabel, Color.BLACK);
        styleInsertLabels(productQuantityLabel, Color.BLACK);
        styleInsertLabels(productTaxLabel, Color.BLACK);
        styleInsertLabels(productDiscountLabel, Color.BLACK);
        styleInsertLabels(productSellingPriceLabel, Color.BLACK);
        productNameField = new JTextField();
        productPriceField = new JSpinner();
        productQuantityField = new JSpinner();
        productTaxField = new JSpinner();
        productDiscountField = new JSpinner();
        productSellingPriceField = new JSpinner();
        styleInsertInputFields(productNameField);
        styleInsertInputFields(productPriceField);
        styleInsertInputFields(productQuantityField);
        styleInsertInputFields(productTaxField);
        styleInsertInputFields(productDiscountField);
        styleInsertInputFields(productSellingPriceField);
        productQuantityField.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JPanel insertPanel = new JPanel(new GridLayout(2, 1));
        insertPanel.setBackground(Color.WHITE);
        JPanel labelsAndFieldPanel = new JPanel(new GridLayout(2, 1));
        JPanel labelsPanel = new JPanel();
        labelsPanel.setBackground(Color.WHITE);
        labelsPanel.add(productNameLabel);
        labelsPanel.add(productPriceLabel);
        labelsPanel.add(productQuantityLabel);
        labelsPanel.add(productTaxLabel);
        labelsPanel.add(productDiscountLabel);
        labelsPanel.add(productSellingPriceLabel);
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setBackground(Color.WHITE);
        inputFieldsPanel.add(productNameField);
        inputFieldsPanel.add(productPriceField);
        inputFieldsPanel.add(productQuantityField);
        inputFieldsPanel.add(productTaxField);
        inputFieldsPanel.add(productDiscountField);
        inputFieldsPanel.add(productSellingPriceField);
        insertButton = new JButton("Insert");
        insertButton.addActionListener(new InsertProductsScreenListener(this));
        styleInsertButton();
        JPanel insertButtonPanel = new JPanel();
        insertButtonPanel.add(insertButton);
        insertButtonPanel.setBackground(Color.WHITE);
        labelsAndFieldPanel.add(labelsPanel);
        labelsAndFieldPanel.add(inputFieldsPanel);
        insertPanel.add(labelsAndFieldPanel);
        insertPanel.add(insertButtonPanel);
        add(insertPanel, BorderLayout.CENTER);
    }

    private void styleInsertButton() {
        insertButton.setForeground(Color.WHITE);
        insertButton.setBackground(Color.GREEN);
        insertButton.setFocusable(false);
        insertButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        insertButton.setFont(new Font("Arial", Font.BOLD, 30));
        insertButton.setPreferredSize(new Dimension(400, 100));
    }

    public void styleInsertInputFields(Object inputField) {
        if(inputField instanceof JTextField) {
            JTextField field = (JTextField) inputField;
            field.setForeground(Color.BLACK);
            field.setBorder(new LineBorder(Color.GREEN, 2));
            field.setFont(new Font("Arial", Font.BOLD, 20));
            field.setPreferredSize(new Dimension(200, 100));
        }
        else if(inputField instanceof JSpinner){
            JSpinner field = (JSpinner) inputField;
            field.setForeground(Color.BLACK);
            field.setBorder(new LineBorder(Color.GREEN, 2));
            field.setFont(new Font("Arial", Font.BOLD, 20));
            field.setPreferredSize(new Dimension(200, 100));
            SpinnerModel spinnerModel = new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01);
            field.setModel(spinnerModel);
        }
    }

    public void styleInsertLabels(JLabel label, Color textColor) {
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 50));
    }

    public void clearFields() {
        productNameField.setText("");
        productPriceField.setValue(0.0);
        productQuantityField.setValue(0);
        productTaxField.setValue(0.0);
        productDiscountField.setValue(0.0);
        productSellingPriceField.setValue(0.0);
    }

    public JTextField getProductNameField() {
        return productNameField;
    }

    public JSpinner getProductPriceField() {
        return productPriceField;
    }

    public JSpinner getProductQuantityField() {
        return productQuantityField;
    }

    public JSpinner getProductTaxField() {
        return productTaxField;
    }

    public JSpinner getProductDiscountField() {
        return productDiscountField;
    }

    public JSpinner getProductSellingPriceField() {
        return productSellingPriceField;
    }

    public void showUpdateProductSubScreen(String productId, String name, double price, int quantity, double tax, double discount, double sellingPrice) {
        new UpdateProductSubScreen("insertProductsScreen", this, productId, name, price, quantity, tax, discount, sellingPrice);
    }
}
