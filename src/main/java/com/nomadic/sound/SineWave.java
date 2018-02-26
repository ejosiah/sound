package com.nomadic.sound;


public class SineWave implements AudioSource {

    private float frequency;
    private float amplitude;
    private float timeStep;

    SineWave(float frequency, float amplitude){
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    @Override
    public void prepare(int bufferSize, int samplerRte) {
        timeStep = 1.0f/samplerRte;
    }

    @Override
    public void getNextAudioBlock(AudioBuffer buffer) {
        for(int s = 0; s < buffer.size(); s++) {
            float sample = amplitude * (float)Math.sin(2 * Math.PI * frequency * (timeStep * s));
            for( int c = 0; c < buffer.numChannels(); c ++){
                buffer.getChannel(c).set(s, sample);
            }
        }
    }
}
