package org.example;

import java.net.*;
import java.io.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;
import org.json.JSONObject;
public class ApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Create URL object
            URL url = new URL("http://localhost:5432/api/users");

            // Step 2: Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set request headers and method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Step 4: Create request body for creating user account
            String requestBody = "{\"username\":\"JohnDoe\",\"email\":\"johndoe@example.com\",\"password\":\"password123\"}";

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

                // Get the user ID from the response JSON
                JSONObject jsonObject = new JSONObject(jsonResponse);
                int userId = jsonObject.getInt("id");

                // Download a sample song for the user
                //String fileUrl = "http://example.com/song.mp3";
                String fileUrl = "https://musicfeed.ir/%D8%A2%D9%87%D9%86%DA%AF-%D9%87%D8%AA%D9%84-%DA%A9%D8%A7%D9%84%DB%8C%D9%81%D8%B1%D9%86%DB%8C%D8%A7/";
                String artist = "Eagles";
                String album = "Hotel";
                String fileName = "Eagles-Hotel-California.mp3";
                String filePath = "Desktop" + artist + "/" + album + "/" + fileName;

                try (InputStream in = new URL(fileUrl).openStream();
                     OutputStream out = new FileOutputStream(filePath)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Add the song to the user's liked songs
                url = new URL("http://localhost:5432/api/users/" + userId + "/liked_songs");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                requestBody = "{\"title\":\"" + fileName + "\",\"artist\":\"" + artist + "\",\"album\":\"" + album + "\",\"file_path\":\"" + filePath + "\"}";

                outputStream = connection.getOutputStream();
                requestBodyBytes = requestBody.getBytes("UTF-8");
                outputStream.write(requestBodyBytes);
                outputStream.close();

                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    response = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    jsonResponse = response.toString();
                    System.out.println(jsonResponse); // Success - display song added message
                } else {
                    // Handle error response
                }

                // Play the user's liked song
                FileInputStream fis = new FileInputStream(filePath);
                AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
                Player player = new Player(fis, audioDevice);

                player.play();

                // Wait for the song to finish playing
                while (!player.isComplete()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                player.close();

            } else {
                // Handle error response
            }

            // Step 7: Close connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}

