package screens;

import util.DB;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateProductSubScreen extends JFrame implements ActionListener {

    private JFrame fromWhereScreen;
    private JButton exitButton, updateButton, deleteButton;
    private String screenName;
    private JTextField productIdField, productNameField;
    private JSpinner productPriceField, productQuantityField, productTaxField, productDiscountField, productSellingPriceField;

    public UpdateProductSubScreen(String screenName, JFrame fromWhereScreen, String productId, String name, double price, int quantity, double tax, double discount, double sellingPrice) {
        this.screenName = screenName;
        this.fromWhereScreen = fromWhereScreen;
        initComponents(productId, name, price, quantity, tax, discount, sellingPrice);
        setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String productId, String name, double price, int quantity, double tax, double discount, double sellingPrice) {
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        styleInsertButtons(updateButton, Color.GREEN);
        styleInsertButtons(deleteButton, Color.RED);
        JLabel productIdLabel = new JLabel("Product ID");
        JLabel productNameLabel = new JLabel("Product Name");
        JLabel productPriceLabel = new JLabel("Product Price");
        JLabel productQuantityLabel = new JLabel("Quantity");
        JLabel productTaxLabel = new JLabel("Tax");
        JLabel productDiscountLabel = new JLabel("Discount");
        JLabel productSellingPriceLabel = new JLabel("Selling Price");
        styleInsertLabels(productIdLabel, Color.WHITE);
        styleInsertLabels(productNameLabel, Color.WHITE);
        styleInsertLabels(productPriceLabel, Color.WHITE);
        styleInsertLabels(productQuantityLabel, Color.WHITE);
        styleInsertLabels(productTaxLabel, Color.WHITE);
        styleInsertLabels(productDiscountLabel, Color.WHITE);
        styleInsertLabels(productSellingPriceLabel, Color.WHITE);
        productIdField = new JTextField();
        productNameField = new JTextField();
        productPriceField = new JSpinner();
        productQuantityField = new JSpinner();
        productTaxField = new JSpinner();
        productDiscountField = new JSpinner();
        productSellingPriceField = new JSpinner();
        styleInsertInputFields(productIdField);
        productIdField.setEnabled(false);
        productIdField.setBackground(Color.gray);
        productIdField.setHorizontalAlignment(SwingConstants.CENTER);
        styleInsertInputFields(productNameField);
        styleInsertInputFields(productPriceField);
        styleInsertInputFields(productQuantityField);
        styleInsertInputFields(productTaxField);
        styleInsertInputFields(productDiscountField);
        styleInsertInputFields(productSellingPriceField);
        productQuantityField.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JPanel insertPanel = new JPanel(new GridLayout(2, 1));
        insertPanel.setBackground(Color.BLACK);
        JPanel labelsAndFieldPanel = new JPanel(new GridLayout(2, 1));
        JPanel labelsPanel = new JPanel();
        labelsPanel.setBackground(Color.BLACK);
        labelsPanel.add(productIdLabel);
        labelsPanel.add(productNameLabel);
        labelsPanel.add(productPriceLabel);
        labelsPanel.add(productQuantityLabel);
        labelsPanel.add(productTaxLabel);
        labelsPanel.add(productDiscountLabel);
        labelsPanel.add(productSellingPriceLabel);
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setBackground(Color.BLACK);
        inputFieldsPanel.add(productIdField);
        inputFieldsPanel.add(productNameField);
        inputFieldsPanel.add(productPriceField);
        inputFieldsPanel.add(productQuantityField);
        inputFieldsPanel.add(productTaxField);
        inputFieldsPanel.add(productDiscountField);
        inputFieldsPanel.add(productSellingPriceField);
        labelsAndFieldPanel.add(labelsPanel);
        labelsAndFieldPanel.add(inputFieldsPanel);
        JPanel insertButtonPanel = new JPanel();
        insertButtonPanel.add(updateButton);
        insertButtonPanel.add(deleteButton);
        insertButtonPanel.setBackground(Color.BLACK);
        insertPanel.add(labelsAndFieldPanel);
        insertPanel.add(insertButtonPanel);
        add(insertPanel, BorderLayout.CENTER);
        exitButton = new JButton("X");
        exitButton.addActionListener(this);
        JPanel titleBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBarPanel.setBackground(Color.BLACK);
        titleBarPanel.add(exitButton);
        add(titleBarPanel, BorderLayout.NORTH);
        styleExitButton();
        productIdField.setText(productId);
        productNameField.setText(name);
        productPriceField.setValue(price);
        productQuantityField.setValue(quantity);
        productTaxField.setValue(tax);
        productDiscountField.setValue(discount);
        productSellingPriceField.setValue(sellingPrice);
    }

    public void styleInsertLabels(JLabel label, Color textColor) {
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 50));
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

    private void styleExitButton() {
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFocusable(false);
    }

    private void styleInsertButtons(JButton button, Color backgroundColor) {
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setPreferredSize(new Dimension(400, 100));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("X")) {
            this.dispose();
            fromWhereScreen.dispose();
            if(screenName.equals("insertProductsScreen")) new InsertProductsScreen();
            else if(screenName.equals("viewProductsScreen")) new ViewProductsScreen();
        }
        else if(e.getActionCommand().equals("Update") || e.getActionCommand().equals("Delete")) {
            try {
                Connection conn = DriverManager.getConnection(DB.url, DB.username, DB.password);
                Statement stmt = conn.createStatement();
                String id = productIdField.getText().trim();
                String query;
                if(e.getActionCommand().equals("Update")) {
                    String name = productNameField.getText().trim();
                    Double price = (productPriceField.getValue() != null)?(Double)productPriceField.getValue():null;
                    Integer quantity = (productQuantityField.getValue() != null)?(Integer)productQuantityField.getValue():null;
                    Double tax = (productTaxField.getValue() != null)?(Double)productTaxField.getValue():null;
                    Double discount = (productDiscountField.getValue() != null)?(Double)productDiscountField.getValue():null;
                    Double sellingPrice = (productSellingPriceField.getValue() != null)?(Double)productSellingPriceField.getValue():null;
                    if(!name.isEmpty() && price != null && quantity != null && tax != null && discount != null && sellingPrice != null) {
                        query = "UPDATE products SET `name`='"+name+"', `buy_price`='"+price+"', `quantity`='"+quantity+"', `tax`='"+tax+"', `discount`='"+discount+"', `selling_price`='"+sellingPrice+"' WHERE product_id='" + id + "'";
                        int result = stmt.executeUpdate(query);
                        if(result == 0) throw new Exception("Couldn't update the product, please try again");
                        JOptionPane.showMessageDialog(null, "Success Update", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(e.getActionCommand().equals("Delete")) {
                    query = "DELETE FROM products WHERE product_id='" + id + "'";
                    int result = stmt.executeUpdate(query);
                    if(result == 0) throw new Exception("Couldn't delete the product, please try again");
                    this.dispose();
                    fromWhereScreen.dispose();
                    if(screenName.equals("insertProductsScreen")) new InsertProductsScreen();
                    else if(screenName.equals("viewProductsScreen")) new ViewProductsScreen();
                }
                stmt.close();
                conn.close();
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}