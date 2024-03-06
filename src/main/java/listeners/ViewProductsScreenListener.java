package listeners;

import screens.ViewProductsScreen;
import screens_common_things.ExportJSONToCSV;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class ViewProductsScreenListener implements ItemListener, ActionListener {

    private ViewProductsScreen viewProductsScreen;

    public ViewProductsScreenListener(ViewProductsScreen viewProductsScreen) {
        this.viewProductsScreen = viewProductsScreen;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if(e.getItem() instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) e.getItem();
                if(radioButton.getText().contains("By ASC/DESC")) {
                    viewProductsScreen.initFilterByPosition();
                    viewProductsScreen.filterMethod = false;
                }
                else if(radioButton.getText().contains("By Text")) {
                    viewProductsScreen.initFilterByText();
                    viewProductsScreen.filterMethod = true;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Clear")) {
            viewProductsScreen.clearResultsBody();
            viewProductsScreen.resetResults();
            viewProductsScreen.currentFilterHeader = "product_id";
            viewProductsScreen.currentFilterValue = "DESC";
            if(viewProductsScreen.filterMethod) viewProductsScreen.getFilterByPositionRadioButton().setSelected(true);
            viewProductsScreen.getFilterByMenu().setSelectedIndex(0);
            viewProductsScreen.getFilterByPositionMenu().setSelectedIndex(0);
            viewProductsScreen.isFilter = false;
            try {
                viewProductsScreen.getProducts(true, 10, viewProductsScreen.currentPosition, false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            viewProductsScreen.getRightArrow().setVisible(viewProductsScreen.currentRowsNumber != viewProductsScreen.totalRows);
        }
        else if(e.getActionCommand().equals("GO")) {
            String filterBy = (viewProductsScreen.getFilterByMenu().getSelectedIndex() == 0)?"product_id":(String)viewProductsScreen.getFilterByMenu().getSelectedItem();
            String filterCriteria;
            if(!viewProductsScreen.filterMethod) filterCriteria = (("" + (viewProductsScreen.getFilterByPositionMenu().getSelectedItem())).equals("Low to High"))?"ASC":"DESC";
            else {
                filterCriteria = viewProductsScreen.getFilterByTextField().getText().trim();
                viewProductsScreen.isFilterExact = viewProductsScreen.getFilterByTextExact().isSelected();
            }
            viewProductsScreen.currentFilterHeader = filterBy;
            viewProductsScreen.currentFilterValue = filterCriteria;
            viewProductsScreen.clearResultsBody();
            viewProductsScreen.resetResults();
            viewProductsScreen.isFilter = true;
            try {
                viewProductsScreen.getProducts(true, 10, viewProductsScreen.currentPosition, false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            viewProductsScreen.getRightArrow().setVisible(viewProductsScreen.currentRowsNumber != viewProductsScreen.totalRows);
        }
        else if(e.getActionCommand().equals("<")) {
            viewProductsScreen.clearResultsBody();
            try {
                viewProductsScreen.getProducts(true, 10, --viewProductsScreen.currentPosition, true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            viewProductsScreen.getLeftArrow().setVisible(viewProductsScreen.currentPosition != 0);
            viewProductsScreen.getRightArrow().setVisible(true);
        }
        else if(e.getActionCommand().equals(">")) {
            viewProductsScreen.clearResultsBody();
            try {
                viewProductsScreen.getProducts(true, 10, ++viewProductsScreen.currentPosition, false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            viewProductsScreen.getRightArrow().setVisible(viewProductsScreen.currentRowsNumber != viewProductsScreen.totalRows);
            viewProductsScreen.getLeftArrow().setVisible(true);
        }
        else if(e.getActionCommand().equals("Export")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Set mode to select directories
            int returnValue = fileChooser.showSaveDialog(viewProductsScreen);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    ExportJSONToCSV exportJSONToCSV = new ExportJSONToCSV(selectedDirectory, viewProductsScreen.getAllProducts());
                    exportJSONToCSV.saveCSV();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
