package NVC.Service;

/**
 * Created by ${BIM} on 23.02.2016.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MP3Sound {

    private MediaPlayer mediaPlayer;
    private int state;
    private File file;

    public File getfTest() {
        return file;
    }

    public void setfTest(File fTest) {
        this.file = fTest;
    }


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void playSounds(String sound, Slider slider, double trackDuration, Label timer) {

        setfTest(new File(sound).getAbsoluteFile());
        String pathurlOfNeSound = "file:///" + file.getAbsolutePath().replace("\\", "/");

        if (getMediaPlayer() == null) {
            setfTest(new File(sound).getAbsoluteFile());
            String path = "file:///" + getfTest().getAbsolutePath().replace("\\", "/");
            Media hit = new Media(path);
            setMediaPlayer(new MediaPlayer(hit));
            slider.setMax(trackDuration);
            sliderMower(slider, timer);
            playMusic(getMediaPlayer());

        } else if (getMediaPlayer() != null && !getMediaPlayer().getMedia().getSource().toString().equals(pathurlOfNeSound)) {
            File f = new File(sound).getAbsoluteFile();
            String path = "file:///" + f.getAbsolutePath().replace("\\", "/");
            Media hit = new Media(path);
            setMediaPlayer(new MediaPlayer(hit));
            slider.setMax(trackDuration);
            sliderMower(slider, timer);
            playMusic(getMediaPlayer());

        } else if (getMediaPlayer() != null && getMediaPlayer().getMedia().getSource().toString().equals(pathurlOfNeSound)) {
            //       getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
            playMusic(getMediaPlayer());
        } else {
            System.out.println("Ошибка в методе playSound");
        }
    }

    public int playMusic(MediaPlayer mediaPlayer1) {
     /*   if (mediaPlayer1.getCurrentTime().toSeconds() == 0.0) {
            mediaPlayer1.setStartTime(getMediaPlayer().getCurrentTime());
            mediaPlayer1.play();
            setState(1);
        } else if (mediaPlayer1.getCurrentTime().toSeconds() > 0.0) {
            mediaPlayer1.setStartTime(getMediaPlayer().getCurrentTime());
            mediaPlayer1.setStartTime(mediaPlayer1.getCurrentTime());
            mediaPlayer1.play();
            setState(1);
        }*/

        mediaPlayer1.setStartTime(getMediaPlayer().getCurrentTime());
        mediaPlayer1.play();
        setState(1);
        return getState();
    }

    public int pauseMusic() {
        getMediaPlayer().pause();
        setState(0);
        return getState();
    }

    public void rewideAudio(Slider slider, Label timer) {
        getMediaPlayer().pause();
        getMediaPlayer().setStartTime(getMediaPlayer().getCurrentTime().divide(1.2));
        slider.setValue(getMediaPlayer().getStartTime().toSeconds());
        timer.setText(String.valueOf(Math.round(slider.getValue())));
        getMediaPlayer().play();
    }

    public void forwardAudio(Slider slider, Label timer) {
        getMediaPlayer().pause();
        getMediaPlayer().setStartTime(getMediaPlayer().getCurrentTime().multiply(1.2));
        slider.setValue(getMediaPlayer().getStartTime().toSeconds());
        timer.setText(String.valueOf(Math.round(slider.getValue())));
        getMediaPlayer().play();
    }

    public void sliderMower(Slider slider, Label trackTime) {
        getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                slider.setValue(newValue.toSeconds());
                trackTime.setText(String.valueOf(Math.round(slider.getValue())));
                getMediaPlayer().setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        getMediaPlayer().setStartTime(Duration.millis(0.0));
                        getMediaPlayer().seek(Duration.ZERO);
                    }
                });
            }
        });
    }
}

