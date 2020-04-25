package com.gageshan.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Create by gageshan on 2020/4/25 22:28
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);

            //将listenChannel注册到Selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听请求

    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if(count > 0) { //有事件要处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while(iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线了");
                        }
                        if(key.isReadable()) {  //通道是可读的状态
                            readData(key);
                        }

                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    private void readData(SelectionKey key) {
        SocketChannel socketChannel = null;

        try {
            socketChannel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if(count > 0) {
                String message = new String(byteBuffer.array());
                System.out.println("from 客户端 " + message);

                //向其它客户端转发消息（不同的通道）
                sendInfoToOtherClients(message,socketChannel);
            }

        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了...");
                //取消注册
                key.channel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {

        }
    }

    private void sendInfoToOtherClients(String message,SocketChannel self) throws IOException{
        System.out.println("服务器转发消息中...");
        for(SelectionKey key : selector.keys()) {
            Channel target = key.channel();
            if(target instanceof SocketChannel && target != self) {
                SocketChannel dest = (SocketChannel)target;
                ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                dest.write(byteBuffer);
            }
        }
    }
    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
