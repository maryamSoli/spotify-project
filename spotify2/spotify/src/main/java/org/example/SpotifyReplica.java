package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SpotifyReplica extends Application {
    private static final String AUDIO_PATH = "Desktop.mp3";
    private static final String ALBUM_ART_PATH = "Desktop.png";

    @Override
    public void start(Stage stage) throws Exception {
        // Create a media player with the audio file
        Media media = new Media(AUDIO_PATH);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set the initial volume to 50%
        mediaPlayer.setVolume(0.5);

        // Load the album artwork image
        Image albumArtImage = new Image(ALBUM_ART_PATH);
        ImageView albumArtImageView = new ImageView(albumArtImage);

        // Create a slider for adjusting the volume
        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(mediaPlayer.getVolume());
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                mediaPlayer.setVolume(newValue.doubleValue())
        );

        // Create a label to display the current volume value
        Label volumeLabel = new Label("Volume: " + mediaPlayer.getVolume());

        // Create a container to hold the volume slider and label
        HBox volumeControlBox = new HBox();
        volumeControlBox.setAlignment(Pos.CENTER_LEFT);
        volumeControlBox.setSpacing(10);
        volumeControlBox.getChildren().addAll(volumeSlider, volumeLabel);

        // Create a container to hold the album artwork and volume control
        BorderPane container = new BorderPane();
        container.setCenter(albumArtImageView);
        container.setBottom(volumeControlBox);
        container.setPadding(new Insets(10));

        // Create the scene
        Scene scene = new Scene(container, 400, 400);

        // Set the stage properties
        stage.setTitle("Spotify Replica");
        stage.setScene(scene);
        stage.show();

        // Start playing the audio
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
