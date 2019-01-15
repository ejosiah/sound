package com.nomadic.sound;

import java.util.Random;
import static java.lang.Math.*;

public class NoiseGenerator implements AudioSource {

    private Random random = new Random();
    float seed = 0;

    @Override
    public void getNextAudioBlock(AudioBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++){
            seed += i;
            for(int channel = 0; channel < buffer.numChannels(); channel++){
                buffer.write(channel, i, random(seed) * 0.25f - 0.125f);
            }
        }
    }

    public float random(float v){
        return fract((float)sin(v) * 10000.0f);
    }

    public float fract(float v){
       return v - (float)floor(v);
    }

    @Override
    public boolean drained() {
        return false;
    }

    @Override
    public void repeat() {

    }

}
