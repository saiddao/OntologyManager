package it.cnr.isti.sedc.bieco.ontologyManager.rest.monitor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.FileReader;
import it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1.MoniitoringInstantiatedRules;

public class MonitorManager {
    public static void main(String[] args) {
    	try {
            // Create the URL object with the endpoint URL
            URL url = new URL("http://localhost:4700/monitoring/biecointerface/loadrules");
            url = new URL("http://146.48.81.167:8181/monitoring/biecointerface");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Enable output and input streams
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Set the request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Prepare the request body
            String requestBody = "{\n" +
                    "    \"jobID\": \"1234\",\n" +
                    "    \"timestamp\": \"2023-01-18 08:29:30\",\n" +
                    "    \"messageType\": \"loadRules\",\n" +
                    "    \"sourceID\": \"4\",\n" +
                    "    \"event\": \"" + getTextAreaValue() + "\",\n" +
                    "    \"crc\": 1234565\n" +
                    "}";

            // Write the request body
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();
            outputStream.close();

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
               response.append(line);
            }
            reader.close();

            // Print the response
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());

            // Close the connection
            connection.disconnect();

            // Display alert message
            System.out.println("Rules sent to the Monitoring");

            // Reload the opener window
            // window.opener.location.reload(false);
            // This line doesn't have an equivalent in Java as it is a client-side operation.
            // You would need to handle the window reloading on the client-side separately.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextAreaValue() {
        // Implement the logic to retrieve the value of the 'ruletextarea' textarea element.
        // You can either hard-code a value or fetch it from user input.
    	
    	
    	
    	StringBuilder content = new StringBuilder();
        try {
            String filePath = "checkEventSequenceICTGW.drl"; // Replace with the actual file path
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
     
        
        
           reader = new BufferedReader(new FileReader(filePath));
          //  StringBuilder content = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            String fileContent = content.toString();
            System.out.println(fileContent); // Display the content of the file

            // You can assign the fileContent variable to your desired textarea or use it as needed
            // For example:
            // textarea.setText(fileContent);

        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(content);
        
        
        return content.toString();
    	
    }
    
}