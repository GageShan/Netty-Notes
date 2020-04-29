package com.gageshan.netty.websocket;

import com.gageshan.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Create by gageshan on 2020/4/29 10:48
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

                            //基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());


                            /**
                             * 1、http数据在传输过程中是分段的， HttpObjectAggregator ，可以将多个段聚合
                             * 这也就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));


                            /**
                             * 对应sebsoclet，它的数据是以帧（frame）形式传递
                             * WebSocketServerProtocolHandler 核心功能就是将http协议升级为ws协议，保持长连接
                             *
                             * 协议提升 状态码 101
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            pipeline.addLast(new MyTextWebSocketFrameHandler());
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
