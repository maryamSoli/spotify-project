package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileStorage {

    public static void main(String[] args) {
        String fileUrl = "https://musicfeed.ir/%D8%A2%D9%87%D9%86%DA%AF-%D9%87%D8%AA%D9%84-%DA%A9%D8%A7%D9%84%DB%8C%D9%81%D8%B1%D9%86%DB%8C%D8%A7/";
        String artist = "Eagles";
        String album = "Hotel";
        String fileName = "Eagles-Hotel-California.mp3";

        try (InputStream in = new URL(fileUrl).openStream();
             OutputStream out = new FileOutputStream("/path/to/your/files/" + artist + "/" + album + "/" + fileName)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
