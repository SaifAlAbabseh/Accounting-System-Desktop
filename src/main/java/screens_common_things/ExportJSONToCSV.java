package screens_common_things;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.io.FileWriter;

public class ExportJSONToCSV {

    private JSONArray jsonArray;
    private String directoryToSaveFile;

    public ExportJSONToCSV(String directoryToSaveFile, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.directoryToSaveFile = directoryToSaveFile;
    }

    public void saveCSV() {
        try {
            String csvContent = convertToCSV();
            FileWriter writer = new FileWriter(directoryToSaveFile + "/data.csv");
            writer.write(csvContent);
            writer.close();
            JOptionPane.showMessageDialog(null, "Successfully exported CSV file\nPath: " + directoryToSaveFile + "\nFile Name: data.csv", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't save the CSV file, please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String convertToCSV() {
        if (jsonArray.isEmpty()) {
            return "";
        }
        String[] headers = jsonArray.getJSONObject(0).keySet().toArray(new String[0]);
        StringBuilder csvContent = new StringBuilder(String.join(",", headers) + "\n");
        for(int j = 0; j < jsonArray.length(); j++) {
            JSONObject item = jsonArray.getJSONObject(j);
            String[] values = new String[headers.length];
            for (int i = 0; i < headers.length; i++) {
                String escapedValue = item.get(headers[i]).toString().replace("\"", "\"\"");
                values[i] = "\"" + escapedValue + "\"";
            }
            csvContent.append(String.join(",", values)).append("\n");
        }
        return csvContent.toString();
    }
}
