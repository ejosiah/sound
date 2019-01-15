package com.nomadic.sound;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioPlayer {
    protected int sampleRate;
    protected int bufferSize;
    protected AudioSource source;
    protected AudioFormat format;
    protected SourceDataLine line;
    protected AtomicBoolean stopped = new AtomicBoolean(false);
    protected AudioBuffer audioBuffer;
    protected ByteBuffer buffer;
    protected int numChannels;

    public AudioPlayer(int sampleRate, int bufferSize, int numChannels){
        this.sampleRate = sampleRate;
        this.bufferSize = bufferSize * 2 * numChannels;
        this.numChannels = numChannels;
        buffer = ByteBuffer.allocate(this.bufferSize).order(ByteOrder.BIG_ENDIAN);
        audioBuffer = new AudioBuffer(numChannels, buffer.asShortBuffer());
        createLine();
    }

    public void setSource(AudioSource source){
        this.source = source;
        this.source.prepare(bufferSize, sampleRate);
    }

    protected void createLine(){
        try{
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, numChannels, 2 * numChannels, sampleRate * numChannels, true);
            line = AudioSystem.getSourceDataLine(format);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void play(){
        try {
            line.open(format);
            line.start();
            while(!source.drained() || !stopped.get()) {
                play0();
            }
            line.drain();
            line.stop();
            line.close();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    protected void play0(){
        buffer.clear();
        source.getNextAudioBlock(audioBuffer);
        line.write(buffer.array(), 0, bufferSize);
    }

    public void stop(){
        stopped.getAndSet(true);
    }
}
