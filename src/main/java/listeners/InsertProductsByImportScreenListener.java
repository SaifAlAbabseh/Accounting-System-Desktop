package listeners;

import com.opencsv.CSVReader;
import screens.InsertProductsByImportScreen;
import screens_common_things.AdminInfo;
import util.DB;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertProductsByImportScreenListener implements ActionListener {

    private InsertProductsByImportScreen insertProductsByImportScreen;
    private static File fileToImport;
    private JButton fileChooserButton;

    public InsertProductsByImportScreenListener(InsertProductsByImportScreen insertProductsByImportScreen) {
        this.insertProductsByImportScreen = insertProductsByImportScreen;
    }

    public InsertProductsByImportScreenListener(JButton fileChooserButton) {
        fileToImport = null;
        this.fileChooserButton = fileChooserButton;
    }

    public InsertProductsByImportScreenListener(JButton fileChooserButton, String temp) {
        this.fileChooserButton = fileChooserButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Download Template File")) {
            saveTemplateFile();
        } else if (e.getSource() == fileChooserButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            int returnValue = fileChooser.showOpenDialog(insertProductsByImportScreen);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if(path.endsWith("csv")) {
                    fileToImport = fileChooser.getSelectedFile();
                    fileChooserButton.setText(fileToImport.getName());
                }
                else {
                    fileToImport = null;
                    fileChooserButton.setText("Choose .CSV file to import");
                    JOptionPane.showMessageDialog(null, "The file isn't csv type", "File Type Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getActionCommand().equals("Import")) {
            if (fileToImport != null) {
                try {
                    int[] numberOfImportedAndNotImported = importCSVFile();
                    fileToImport = null;
                    fileChooserButton.setText("Choose .CSV file to import");
                    JOptionPane.showMessageDialog(null, "Successfully imported: " + numberOfImportedAndNotImported[0] + " products\n Failed to import: " + numberOfImportedAndNotImported[1] + " products", "Done", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select the .csv file and try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveTemplateFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Set mode to select directories
        int returnValue = fileChooser.showSaveDialog(insertProductsByImportScreen);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            saveFile(selectedDirectory);
        }
    }

    private void saveFile(File selectedDirectory) {
        File sourceFile = new File("src/main/resources/templates/insert_products_template.csv");

        try {
            // Create a destination file in the selected directory
            File destinationFile = new File(selectedDirectory, sourceFile.getName());
            InputStream inputStream = new FileInputStream(sourceFile);
            OutputStream outputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

            JOptionPane.showMessageDialog(null, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[] importCSVFile() throws Exception{
        int howMuchImported = 0;
        int howMuchNotImported = 0;
        String csvFile = fileToImport.getAbsolutePath();
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] nextLine = reader.readNext();
        String expectedHeader = "product_name,product_buy_price,product_quantity,product_tax,product_discount,product_selling_price";
        if(String.join(",", nextLine).trim().equals(expectedHeader)) { //Check the header
            while ((nextLine = reader.readNext()) != null) { //Check the rows values
                // Iterate through each row
                if(nextLine.length == 6) {
                    String prodName = nextLine[0].trim();
                    double prodBuyPrice = Double.parseDouble(nextLine[1].trim());
                    int prodQuantity = Integer.parseInt(nextLine[2].trim());
                    double prodTax = Double.parseDouble(nextLine[3].trim());
                    double prodDiscount = Double.parseDouble(nextLine[4].trim());
                    double prodSellingPrice = Double.parseDouble(nextLine[5].trim());
                    if(!prodName.isEmpty()) {
                        if (!completeImportProcess(prodName, prodBuyPrice, prodQuantity, prodTax, prodDiscount, prodSellingPrice))
                            howMuchNotImported++;
                        else howMuchImported++;
                    }
                    else throw new Exception("Invalid values, please check and try again.");

                }
                else throw new Exception("Invalid values, please check and try again.");
            }
        }
        else throw new Exception("Invalid values, please check and try again.");
        return new int[] {howMuchImported, howMuchNotImported};
    }

    private boolean completeImportProcess(String prodName, double prodBuyPrice, int prodQuantity, double prodTax, double prodDiscount, double prodSellingPrice) throws Exception{
        Connection conn = DriverManager.getConnection(DB.url, DB.username, DB.password);
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO products(`admin_id`, `name`, `buy_price`, `quantity`, `tax`, `discount`, `selling_price`) VALUES('" + AdminInfo.adminId + "', '" + prodName + "', '" + prodBuyPrice + "', '" + prodQuantity + "', '" + prodTax + "', '" + prodDiscount + "', '" + prodSellingPrice + "')";
        int result = stmt.executeUpdate(query);
        stmt.close();
        conn.close();
        return result != 0;
    }
}
