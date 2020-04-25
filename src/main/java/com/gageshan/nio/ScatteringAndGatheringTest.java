package com.gageshan.nio;

/**
 * Create by gageshan on 2020/4/24 23:24
 */

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering:将数据写入到buffer时，可以采用buffer数组，一次写入
 * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception{
        //使用ServerSocketChannel 和 SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        while(true) {
            int byteRead = 0;

            while(byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead=" + byteRead);

                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position() + ",limit="
                + buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            long byteWrite = 0;
            while(byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead=" + byteRead + "byteWrite=" + byteWrite + ",messageLength=" + messageLength);
        }

    }
}
