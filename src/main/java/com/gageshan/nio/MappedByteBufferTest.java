package com.gageshan.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create by gageshan on 2020/4/24 22:40
 */

/**
 * MappedByteBuffer可让文件只在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\2.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2：0 可以直接修改的起始位置
         * 参数3：5 映射到内存的大小，不是索引位置
         * 可以直接修改的范围就是0-5
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'p');
        randomAccessFile.close();
    }
}
