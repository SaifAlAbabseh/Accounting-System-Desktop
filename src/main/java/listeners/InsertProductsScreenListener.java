package listeners;

import screens.InsertProductsScreen;
import screens_common_things.AdminInfo;
import util.DB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InsertProductsScreenListener implements ActionListener {

    private final InsertProductsScreen insertProductsScreen;

    public InsertProductsScreenListener(InsertProductsScreen insertProductsScreen) {
        this.insertProductsScreen = insertProductsScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Insert")) {
            try {
                String name = insertProductsScreen.getProductNameField().getText().trim();
                Double price = (insertProductsScreen.getProductPriceField().getValue() != null)?(Double)insertProductsScreen.getProductPriceField().getValue():null;
                Integer quantity = (insertProductsScreen.getProductQuantityField().getValue() != null)?(Integer)insertProductsScreen.getProductQuantityField().getValue():null;
                Double tax = (insertProductsScreen.getProductTaxField().getValue() != null)?(Double)insertProductsScreen.getProductTaxField().getValue():null;
                Double discount = (insertProductsScreen.getProductDiscountField().getValue() != null)?(Double)insertProductsScreen.getProductDiscountField().getValue():null;
                Double sellingPrice = (insertProductsScreen.getProductSellingPriceField().getValue() != null)?(Double)insertProductsScreen.getProductSellingPriceField().getValue():null;
                if(!name.isEmpty() && price != null && quantity != null && tax != null && discount != null && sellingPrice != null) {
                    try {
                        String productId = insertProduct(name, price, quantity, tax, discount, sellingPrice);
                        // Success
                        insertProductsScreen.clearFields();
                        insertProductsScreen.setEnabled(false);
                        insertProductsScreen.showUpdateProductSubScreen(productId, name, price, quantity, tax, discount, sellingPrice);
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Returns the product ID after inserting it
    private String insertProduct(String name, double price, int quantity, double tax, double discount, double sellingPrice) throws Exception{
        Connection conn = DriverManager.getConnection(DB.url, DB.username, DB.password);
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO products(`admin_id`, `name`, `buy_price`, `quantity`, `tax`, `discount`, `selling_price`) VALUES('" + AdminInfo.adminId + "', '" + name + "', '" + price + "', '" + quantity + "', '" + tax + "', '" + discount + "', '" + sellingPrice + "');";
        int result = stmt.executeUpdate(query);
        if(result == 0) throw new Exception("No products has been inserted, try again");
        stmt.close();
        Statement stmt2 = conn.createStatement();
        String getQuery = "SELECT product_id FROM products WHERE admin_id='" + AdminInfo.adminId + "' ORDER BY product_id DESC LIMIT 1;";
        ResultSet getResult = stmt2.executeQuery(getQuery);
        getResult.next();
        String productId = getResult.getString("product_id");
        getResult.close();
        stmt2.close();
        conn.close();
        return productId;
    }
}
