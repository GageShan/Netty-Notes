package com.gageshan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create by gageshan on 2020/4/24 21:34
 */
public class NIOFileChannel03 {
    /**
     * 使用一个buffer完成文件拷贝
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("d:\\file.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2014);

        while(true) {
            byteBuffer.clear();
            int len = fileChannel01.read(byteBuffer);
            if(len == -1) {
                break;
            }
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
