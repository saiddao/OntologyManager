package it.cnr.isti.sedc.bieco.ontologyManager.rest.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RuleLoader {
    public static void main(String[] args) {
        executePost();
    }
    
    public static void executePost() {
        try {
            String endpoint = "http://localhost:4700/monitoring/biecointerface/loadrules";
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            File file = new File("checkEventSequenceICTGW.drl");
            if (file.exists()) {
                StringBuilder contentBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line);
                        contentBuilder.append(System.lineSeparator());
                    }
                }
                String fileContent = contentBuilder.toString();
                
                String requestBody = "{\n" +
                        "  \"jobID\": \"1234\",\n" +
                        "  \"timestamp\": \"2023-01-18 08:29:30\",\n" +
                        "  \"messageType\": \"loadRules\",\n" +
                        "  \"sourceID\": \"4\",\n" +
                        "  \"event\": \"" + fileContent + "\",\n" +
                        "  \"crc\": 1234565\n" +
                        "}";
                
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String responseStr = reader.readLine();
                    System.out.println(responseStr);
                    reader.close();
                } else {
                    System.out.println("Error: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
                }
            } else {
                System.out.println("File does not exist.");
            }
            
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Rules sent to the Monitoring");
    }
}

