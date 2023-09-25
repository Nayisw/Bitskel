package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    File[] soundURL = new File[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURL[0] = new File("./res/sound/Theme.wav");
        soundURL[1] = new File("./res/sound/coin.wav");
        soundURL[2] = new File("./res/sound/fanfare.wav");
        soundURL[3] = new File("./res/sound/unlock.wav");
        soundURL[4] = new File("./res/sound/powerup.wav");
        soundURL[5] = new File("./res/sound/Theme_2.wav");

    }

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        } catch (Exception e) {
        }
    }
    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f;break;
            case 2: volume = -12f;break;
            case 3: volume = -5f;break;
            case 4: volume = 1f;break;
            case 5: volume = 6f;break;
        }
        fc.setValue(volume);
    }

    public void play() {

    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}