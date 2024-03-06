package screens;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import listeners.ViewProductsScreenListener;
import org.json.JSONArray;
import org.json.JSONObject;
import screens_common_things.*;
import util.Host;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ViewProductsScreen extends JFrame {

    private JRadioButton filterByPositionRadioButton, filterByTextRadioButton;
    private JPanel mainPanel, filterByWhatPanel, filterPanel, exportButtonPanel, resultsPanel, resultsBodyPanel, filterByTextPanel, filterByPositionPanel, resultsArrowsPanel;
    private JComboBox<String> filterByMenu, filterByPositionMenu;
    private JButton filterGoButton, filterClearButton, exportButton, leftArrow, rightArrow;
    private JTextField filterByTextField;
    private JCheckBox filterByTextExact;

    // ------------------------------- //
    public int currentPosition = 0;
    public int currentRowsNumber = 0;
    public int totalRows = 0;
    public int currentPageNumOfRows = 0;
    public String currentFilterHeader = "product_id";
    public String currentFilterValue = "DESC";
    public boolean isFilter = false;
    public boolean filterMethod = false;
    public boolean isFilterExact = false;
    // ------------------------------- //

    public JTextField getFilterByTextField() {
        return filterByTextField;
    }

    public JComboBox<String> getFilterByMenu() {
        return filterByMenu;
    }

    public JComboBox<String> getFilterByPositionMenu() {
        return filterByPositionMenu;
    }

    public JButton getLeftArrow() {
        return leftArrow;
    }

    public JButton getRightArrow() {
        return rightArrow;
    }

    public ViewProductsScreen() {
        initComponents();
        ScreenConfig.initFrame(this);
        try {
            getProducts(true, 10, currentPosition, false);
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        rightArrow.setVisible(currentRowsNumber != totalRows);
    }

    public JRadioButton getFilterByPositionRadioButton() {
        return filterByPositionRadioButton;
    }

    public JCheckBox getFilterByTextExact() {
        return filterByTextExact;
    }

    private void initComponents() {
        ScreenConfig.initTitleBar(this, "View Products");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        resultsArrowsPanel = new JPanel();
        resultsArrowsPanel.setBackground(Color.WHITE);
        leftArrow = new JButton("<");
        leftArrow.addActionListener(new ViewProductsScreenListener(this));
        leftArrow.setVisible(false);
        rightArrow = new JButton(">");
        rightArrow.addActionListener(new ViewProductsScreenListener(this));
        styleArrowButton(leftArrow);
        styleArrowButton(rightArrow);
        resultsArrowsPanel.add(leftArrow);
        resultsArrowsPanel.add(rightArrow);
        filterByWhatPanel = new JPanel();
        filterByWhatPanel.setBackground(Color.WHITE);
        filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setBackground(Color.WHITE);
        exportButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exportButtonPanel.setBackground(Color.WHITE);
        filterByPositionRadioButton = new JRadioButton("<html><font size='100'>Filter By ASC/DESC</font></html>");
        filterByPositionRadioButton.setSelected(true);
        filterByPositionRadioButton.addItemListener(new ViewProductsScreenListener(this));
        styleRadioButtons(filterByPositionRadioButton);
        filterByTextRadioButton = new JRadioButton("<html><font size='100'>Filter By Text</font></html>");
        filterByTextRadioButton.addItemListener(new ViewProductsScreenListener(this));
        styleRadioButtons(filterByTextRadioButton);
        ButtonGroup group = new ButtonGroup();
        group.add(filterByPositionRadioButton);
        group.add(filterByTextRadioButton);
        filterByWhatPanel.add(filterByPositionRadioButton);
        filterByWhatPanel.add(filterByTextRadioButton);
        JLabel filterLabel = new JLabel("Filter: ");
        Styles.styleLabel(filterLabel, Color.BLACK);
        filterByMenu = new JComboBox<>(new String[] {"", "product_id", "admin_id", "name", "buy_price", "quantity", "tax", "discount", "selling_price", "date"});
        Styles.styleInputField(filterByMenu);
        initFilterByPosition();
        filterGoButton = new JButton("GO");
        filterGoButton.addActionListener(new ViewProductsScreenListener(this));
        GetAdminsAPICommon common = new GetAdminsAPICommon();
        common.styleButton(filterGoButton, Color.GREEN, new Dimension(100, 100));
        filterClearButton = new JButton("Clear");
        filterClearButton.addActionListener(new ViewProductsScreenListener(this));
        common.styleButton(filterClearButton, Color.GREEN, new Dimension(100, 100));
        JPanel labelWithMenuPanel = new JPanel();
        labelWithMenuPanel.setBackground(Color.WHITE);
        labelWithMenuPanel.add(filterLabel);
        labelWithMenuPanel.add(filterByMenu);
        filterPanel.add(labelWithMenuPanel);
        filterPanel.add(filterByPositionPanel);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(filterGoButton);
        buttonsPanel.add(filterClearButton);
        filterPanel.add(buttonsPanel);
        exportButton = new JButton("Export");
        common.styleButton(exportButton, Color.GREEN, new Dimension(250, 100));
        exportButtonPanel.add(exportButton);
        String[] labelsText = new String[] {"product_id", "admin_id", "name", "buy_price", "quantity", "tax", "discount", "selling_price", "date"};
        ScrollResultsPanel scrollResultsPanel = new ScrollResultsPanel(9, labelsText);
        resultsPanel = scrollResultsPanel.getResultsPanel();
        resultsBodyPanel = scrollResultsPanel.getResultsBodyPanel();
        mainPanel.add(filterByWhatPanel);
        mainPanel.add(filterPanel);
        mainPanel.add(exportButtonPanel);
        mainPanel.add(resultsPanel);
        mainPanel.add(resultsArrowsPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void initFilterByText() {
        int index = filterPanel.getComponentZOrder(filterByPositionPanel);
        filterByTextField = new JTextField();
        Styles.styleInputField(filterByTextField);
        filterByTextExact = new JCheckBox();
        filterByTextPanel = new JPanel();
        filterByTextPanel.setBackground(Color.WHITE);
        filterByTextPanel.add(filterByTextField);
        filterByTextPanel.add(new JLabel("Exact?"));
        filterByTextPanel.add(filterByTextExact);
        filterPanel.remove(filterByPositionPanel);
        filterPanel.add(filterByTextPanel, index);
        filterPanel.revalidate();
        filterPanel.repaint();
    }

    public void initFilterByPosition() {
        int index = filterPanel.getComponentZOrder(filterByTextPanel);
        filterByPositionPanel = new JPanel();
        filterByPositionPanel.setBackground(Color.WHITE);
        filterByPositionMenu = new JComboBox<>(new String[] {"", "Low to High", "High to Low"});
        filterByPositionPanel.add(filterByPositionMenu);
        Styles.styleInputField(filterByPositionMenu);
        if(index != -1) {
            filterPanel.remove(filterByTextPanel);
            filterPanel.add(filterByPositionPanel, index);
            filterPanel.revalidate();
            filterPanel.repaint();
        }
        else filterPanel.add(filterByPositionPanel);
    }

    public void clearResultsBody() {
        resultsBodyPanel.removeAll();
        filterPanel.revalidate();
        filterPanel.repaint();
    }

    public void resetResults() {
        currentPosition = 0;
        currentRowsNumber = 0;
        totalRows = 0;
        currentPageNumOfRows = 0;
        leftArrow.setVisible(false);
    }

    public void getProducts(boolean isCurrentNumOfRows, int numOfRows, int startPosition, boolean subtract) throws Exception{
        String filters = (isFilter)?"&filterMethod=" + filterMethod + "&filterBy=" + currentFilterHeader + "&filterCriteria=" + currentFilterValue + "&isFilterExact=" + isFilterExact:"";
        URI apiURL = new URI(Host.domainForAPIs + "/getProducts.php?numOfRows=" + numOfRows + "&startPosition=" + startPosition + filters);
        Map<String, String> headers = new HashMap<>();
        headers.put("auth_token", new Session().getAuthToken());
        Response response =
                RestAssured.
                        given().
                        headers(headers).
                        request("GET", apiURL);
        if (response.getStatusCode() == 200) {
            JSONArray productsArray = new JSONArray(response.jsonPath().getList("products"));
            totalRows = Integer.parseInt(response.jsonPath().getString("meta_data['total']"));
            if(isCurrentNumOfRows) {
                if(subtract) currentRowsNumber -= currentPageNumOfRows;
                else currentRowsNumber += Integer.parseInt(response.jsonPath().getString("meta_data['available']"));
            }
            currentPageNumOfRows = Integer.parseInt(response.jsonPath().getString("meta_data['available']"));
            for(int i = 0; i < productsArray.length(); i++) {
                JSONObject productObject = productsArray.getJSONObject(i);
                JPanel resultPanel = new JPanel(new GridLayout(1, 9));
                resultPanel.setBackground((i % 2 == 0)?Color.LIGHT_GRAY:Color.WHITE);
                resultPanel.setPreferredSize(new Dimension(resultPanel.getPreferredSize().width, 100));
                JLabel label = new JLabel("" + productObject.getInt("product_id"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getInt("admin_id"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel(productObject.getString("product_name"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getDouble("product_buy_price"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getInt("product_quantity"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getDouble("product_tax"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getDouble("product_discount"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel("" + productObject.getDouble("product_selling_price"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                label = new JLabel(productObject.getString("product_sell_date"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                resultPanel.add(label);
                resultsBodyPanel.add(resultPanel);
            }
        }
        else throw new Exception(response.jsonPath().getString("error"));
    }

    private void styleRadioButtons(JRadioButton radioButton) {
        radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButton.setBackground(Color.WHITE);
        radioButton.setFocusable(false);
    }

    private void styleArrowButton(JButton arrowButton) {
        arrowButton.setForeground(Color.WHITE);
        arrowButton.setBackground(Color.GREEN);
        arrowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        arrowButton.setFocusable(false);
        arrowButton.setFont(new Font("Arial", Font.BOLD, 25));
        arrowButton.setPreferredSize(new Dimension(50, 50));
    }
}
