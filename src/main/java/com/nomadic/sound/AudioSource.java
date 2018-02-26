package com.nomadic.sound;

import java.nio.ShortBuffer;

public interface AudioSource {

    default void prepare(int bufferSize, int samplerRte){

    }

    void getNextAudioBlock(AudioBuffer buffer);
}
