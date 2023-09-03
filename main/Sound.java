package main;

import java.io.File;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    File[] soundURL = new File[30];
    FloatControl volumeControl;

    public Sound(){
        soundURL[0] = new File("./res/sound/Theme.wav");
        soundURL[1] = new File("./res/sound/coin.wav");
        soundURL[2] = new File("./res/sound/fanfare.wav");
        soundURL[3] = new File("./res/sound/unlock.wav");
        soundURL[4] = new File("./res/sound/powerup.wav");

    }

    public void setFile(int i){

        try {
            
            AudioInputStream ais= AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void setVolume(float volume) {
        if (volumeControl != null) {
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
