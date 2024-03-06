package screens_common_things;

import javax.swing.*;
import java.awt.*;

public class ScrollResultsPanel {

    private JPanel resultsPanel, resultsHeaderPanel, resultsBodyPanel;
    private JScrollPane resultsBodyPanelScroll;

    public ScrollResultsPanel(int resultsHeaderColumnsCount, String[] resultsHeaderLabelsText) {
        resultsHeaderPanel = new JPanel(new GridLayout(1, resultsHeaderColumnsCount));
        resultsHeaderPanel.setBackground(Color.WHITE);
        for(String labelText : resultsHeaderLabelsText) {
            JLabel label = new JLabel(labelText);
            Styles.styleLabel(label, Color.BLACK);
            resultsHeaderPanel.add(label);
        }
        resultsBodyPanel = new JPanel();
        resultsBodyPanel.setLayout(new BoxLayout(resultsBodyPanel, BoxLayout.Y_AXIS));
        resultsBodyPanel.setBackground(Color.WHITE);
        resultsBodyPanelScroll = new JScrollPane(resultsBodyPanel);
        resultsBodyPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(resultsPanel.getPreferredSize().width, resultsPanel.getPreferredSize().height + 400));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.add(resultsHeaderPanel);
        resultsPanel.add(resultsBodyPanelScroll);
    }

    public JPanel getResultsPanel() {
        return resultsPanel;
    }

    public JPanel getResultsBodyPanel() {
        return resultsBodyPanel;
    }
}
