package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

public class MusicPlayer {

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        String artist = "Eagles";
        String album = "Hotel";
        String fileName = "Eagles-Hotel-California.mp3";
        String filePath = "Desktop" + artist + "/" + album + "/" + fileName;
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
    }
}

