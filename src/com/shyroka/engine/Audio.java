package com.shyroka.engine;
import javax.sound.sampled.*;
import java.io.IOException;

public class Audio {

    Clip clip;

    public Audio(String path) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream decodedais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(decodedais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void play() {
        stop();
        clip.setFramePosition(0);
        clip.start();

    }


    public void stop() {
        if(clip.isRunning())
            clip.stop();
    }

    public void close()
    {
        stop();
        clip.close();
    }

}