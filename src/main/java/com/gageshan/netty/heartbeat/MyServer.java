package com.gageshan.netty.heartbeat;

import com.gageshan.netty.groupchat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Create by gageshan on 2020/4/29 10:17
 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());

                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());

                            /**
                             * 心跳检测机制
                             * 当 IdleStateHandler触发后，就会传递给管道的下一个handler去处理
                             * 通过调用下一个handler的userEventTriggered方法，在该方法中处理
                             */
                            //读，写， 读写
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //业务处理handler
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();

            //监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
