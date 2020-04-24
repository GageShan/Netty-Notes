package com.gageshan.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by gageshan on 2020/4/23 22:20
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.submit(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket) {
        InputStream inputStream = null;
        try {
            byte[] bytes = new byte[1024];
            inputStream = socket.getInputStream();

            int read = 1024;
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("客户端连接断开");
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}