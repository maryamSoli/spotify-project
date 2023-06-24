package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileStorage {

    public static void main(String[] args) {
        String fileUrl = "http://example.com/song.mp3";
        String artist = "Artist Name";
        String album = "Album Name";
        String fileName = "song.mp3";

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
