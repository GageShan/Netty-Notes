package com.gageshan.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * Create by gageshan on 2020/4/29 0:19
 */
public class GroupChatClient {

    private final String host;
    private final int port;

    public GroupChatClient(String host,int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Scanner scanner = new Scanner(System.in);
            Channel channel = channelFuture.channel();
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
//            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1",7000).run();
    }
}
