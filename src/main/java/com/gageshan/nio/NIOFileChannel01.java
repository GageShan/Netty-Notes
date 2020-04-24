package com.gageshan.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create by gageshan on 2020/4/24 20:29
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello world";

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file.txt");

        //通过FileOutputStream得到FileChannel
        //FileOutputStream内置一个Channel
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        //读写操作一定要反转
        byteBuffer.flip();

        //从buffer中写数据到channel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
