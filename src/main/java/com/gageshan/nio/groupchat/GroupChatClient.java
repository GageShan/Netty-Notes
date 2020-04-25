package com.gageshan.nio.groupchat;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Create by gageshan on 2020/4/25 22:58
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;

    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws Exception {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    //向服务器发送消息
    public void sendInfo(String info) {
        info = username + " say: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {

        }
    }
    //从服务器端读取回复的消息
    public void readInfo() {
        try {
            int select = selector.select();
            if(select > 0) { //有事件发生的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        String message = new String(byteBuffer.array()).trim();
                        System.out.println(message);
                    }
                }
                iterator.remove();
            } else {
                //System.out.println("没有可用的通道...");
            }
        } catch (Exception e) {

        }
    }
    public static void main(String[] args) throws Exception{
        GroupChatClient chatClient = new GroupChatClient();

        //启动一个线程，每隔3秒，读取从服务器发送的数据
        new Thread() {
          public void run() {
              while(true) {
                  chatClient.readInfo();
                  try {
                      Thread.currentThread().sleep(3000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
        }.start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
