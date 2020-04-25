package com.gageshan.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Create by gageshan on 2020/4/25 21:31
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();

        //非阻塞
        socketChannel.configureBlocking(false);

        //提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if(!socketChannel.connect(inetSocketAddress)) {
            while(!socketChannel.finishConnect()) {
                System.out.println("正在连接...");
            }
        }

        //连接成功就发送数据
        String str = "hello, world";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //将buffer数据写入socketchannel
        socketChannel.write(buffer);
        System.in.read();
    }
}
