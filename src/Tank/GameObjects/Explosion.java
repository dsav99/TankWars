package Tank.GameObjects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Explosion implements Runnable {

    private AudioInputStream soundStream;
    private String soundFile;
    private Clip clip;

    public Explosion(String soundFile){
        this.soundFile = soundFile;

        try{
            soundStream = AudioSystem.getAudioInputStream(Explosion.class.getClassLoader().getResource(soundFile));
            clip = AudioSystem.getClip();
            clip.open(soundStream);
        }
        catch(Exception e){
            System.out.println(e.getMessage() + "No sound documents are fouond");
        }

    }


    public void play(){
        clip.start();
    }

    @Override
    public void run() {
        clip.start();
    }
}
