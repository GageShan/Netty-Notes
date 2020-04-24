package com.gageshan.nio;

import java.nio.IntBuffer;
import java.nio.channels.Channel;

/**
 * Create by gageshan on 2020/4/24 16:37
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }

        //在读写之前，先要切换一下
        intBuffer.flip();

        while(intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
