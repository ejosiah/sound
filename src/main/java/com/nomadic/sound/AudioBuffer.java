package com.nomadic.sound;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class AudioBuffer {
    private ShortBuffer buffer;
    private int numChannels;
    private ArrayList<Channel> channels = new ArrayList<>();
    private int size;

    public AudioBuffer(int numChannels, ShortBuffer buffer){
        this.numChannels = numChannels;
        this.buffer = buffer;
        for(int i = 0; i < numChannels; i++){
            channels.add(new Channel(i));
        }
        size = buffer.capacity()/2;
    }

    public void write(int channel, int pos, float sample){
        if(channel >= numChannels) throw new IndexOutOfBoundsException(channel + " exceeds number of channels available");
        channels.get(channel).set(pos, sample);
    }


    public int numChannels(){
        return numChannels;
    }

    public int size(){
        return size;
    }

    public Channel getChannel(int channel){
        if(channel >= numChannels) throw new IndexOutOfBoundsException(channel + " exceeds number of channels available");
        return channels.get(channel);
    }

    public class Channel{
        private int offset;
        private Channel(int offset){
            this.offset = offset;
        }

        void set(int pos, float sample){
            buffer.put(offset + (pos * 2),  (short)(sample * Short.MAX_VALUE));
        }

    }
}
