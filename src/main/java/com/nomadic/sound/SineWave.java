package com.nomadic.sound;


import java.util.ArrayList;
import java.util.Iterator;

public class SineWave implements AudioSource {

    private float frequency;
    private float amplitude;
    private float timeStep;
    private ArrayList<Float> buffer = new ArrayList<>();
    private Iterator<Float> bufferItr;

    SineWave(float frequency, float amplitude){
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    @Override
    public void prepare(int bufferSize, int samplerRte) {
        timeStep = 1.0f/samplerRte;
        fillBuffer();
    }

    void fillBuffer(){
        buffer.clear();
        float t = 0;
        float amp;
        do{
            amp = (float)Math.exp(-3 * t);
            float sample = amp  * (float)Math.sin(2 * Math.PI * frequency * t);
            buffer.add(sample);
            t += timeStep;
        }while(amp != 0);
        bufferItr = buffer.iterator();
    }

    @Override
    public void getNextAudioBlock(AudioBuffer buffer) {
        for(int s = 0; s < buffer.size(); s++) {
            if(bufferItr.hasNext()) {
                float sample = bufferItr.next();
                for (int c = 0; c < buffer.numChannels(); c++) {
                    buffer.getChannel(c).set(s, sample);
                }
            }
        }
    }

    @Override
    public boolean drained() {
        return bufferItr.hasNext();
    }

    @Override
    public void repeat() {
        repeat();
    }
}
