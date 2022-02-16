package Tank.menus;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundPlayer implements Runnable {

    private AudioInputStream soundStream;
    private String soundFile;
    private Clip clip;

    public SoundPlayer(String soundFile){
        this.soundFile=soundFile;

        try{
            this.soundStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getClassLoader().getResource(soundFile));
            this.clip = AudioSystem.getClip();
            clip.open(soundStream);
        } catch(Exception e){
            System.out.println("Error loading audio file :"+soundFile);
        }
    }

    @Override
    public void run() {
        clip.start();
    }
}
