package sample.Controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.Model.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AboutController {
    @FXML
    private MediaView mediaView;

    private final String FILENAME = "Advertisement.csv";
    private ArrayList<String> files;

    public void initialise() {
        try {
            files = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            do {
                s = br.readLine();
                if (s != null) {
                    files.add(s);
                }
            } while (s != null);
            Media media = new Media(new File(files.get(0)).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(-1);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setFitWidth(480);
        } catch (Exception e) {
            throw new IllegalArgumentException("Loading video failed: " + e.getMessage());
        }
    }

    public void stop() {
        mediaView.getMediaPlayer().stop();
    }

}
