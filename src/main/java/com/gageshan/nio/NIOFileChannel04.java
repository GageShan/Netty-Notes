package com.gageshan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Create by gageshan on 2020/4/24 22:04
 */
public class NIOFileChannel04 {

    /**
     * 使用channel transferFrom拷贝文件
     * @param args
     */
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("D:\\Output\\楚门的世界-蓝光1080P.flv");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Output\\楚门的世界-蓝光1080P.mp4");

        FileChannel fileChannel01 = fileInputStream.getChannel();
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        fileChannel02.transferFrom(fileChannel01,0,fileChannel01.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
