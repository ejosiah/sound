package com.nomadic.sound;

public class Main {

    public static void main(String[] args) throws Exception {
        AudioSource source = new SineWave(1000.0f, 0.5f);
        AudioSource noise = new NoiseGenerator();
        final AudioPlayer player = new AudioPlayer(48000, 480, 2);
        player.setSource(source);
        new Thread(player::play).start();
        Thread.sleep(20000);
        player.stop();
    }

}
