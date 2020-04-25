package com.gageshan.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Create by gageshan on 2020/4/25 11:36
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //将serverSocketChannel 注册到selector中心
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {

            if (selector.select(1000) == 0) {
                System.out.println("无连接");
                continue;
            }

            //获取关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()) {
                //获取selectionkey
                SelectionKey key = keyIterator.next();

                if(key.isAcceptable()) {  //有新的客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将socketchannel注册到selector,同时给socketchannel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if(key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }
                //防止重复操作
                keyIterator.remove();
            }
        }
    }
}
