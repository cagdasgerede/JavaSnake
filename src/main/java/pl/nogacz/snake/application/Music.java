package pl.nogacz.snake.application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    //Musics are taken from "https://mixkit.co"
    static final String START_MUSIC_PATH = "src/main/resources/startMusic.wav";
    static final String END_MUSIC_PATH = "src/main/resources/endMusic.wav";
    public static final String EAT_MUSIC_PATH = "src/main/resources/eatMusic.wav";
    public static final String GAME_MUSIC_PATH = "src/main/resources/gameMusic.wav";
    private static Clip audioClip_GameMusic;

    public static void play_Start_End_Eat_GameMusic(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }    
    }
    public static void playLoopedMusic(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            audioClip_GameMusic = (Clip) AudioSystem.getLine(info);
            audioClip_GameMusic.open(audioStream);
            audioClip_GameMusic.start();
            audioClip_GameMusic.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }    
    }
    public static void stopLoopedMusic() {
        audioClip_GameMusic.stop();
    }
}
