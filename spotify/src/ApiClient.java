import java.net.*;
import java.io.*;

public class ApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Create URL object
            URL url = new URL("http://localhost:3000/api/users");

            // Step 2: Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set request headers and method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Step 4: Create request body
            String requestBody = "{\"name\":\"John Doe\",\"email\":\"johndoe@example.com\",\"password\":\"password123\"}";

            // Step 5: Write request body to request stream
            OutputStream outputStream = connection.getOutputStream();
            byte[] requestBodyBytes = requestBody.getBytes("UTF-8");
            outputStream.write(requestBodyBytes);
            outputStream.close();

            // Step 6: Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                System.out.println(jsonResponse); // Success - display account created message and user data
            } else {
                // Handle error response
            }

            // Step 7: Close connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
