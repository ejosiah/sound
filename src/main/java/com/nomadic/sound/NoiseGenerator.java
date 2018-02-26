package com.nomadic.sound;

import java.util.Random;

public class NoiseGenerator implements AudioSource {

    private Random random = new Random();

    @Override
    public void getNextAudioBlock(AudioBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++){
            for(int channel = 0; channel < buffer.numChannels(); channel++){
                buffer.write(channel, i, random.nextFloat() * 0.25f - 0.125f);
            }
        }
    }

}
