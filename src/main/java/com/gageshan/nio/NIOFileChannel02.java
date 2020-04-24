package com.gageshan.nio;



import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create by gageshan on 2020/4/24 20:53
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File("d:\\file.txt"));
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //从channel读数据到buffer
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
