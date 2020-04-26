package com.gageshan.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Create by gageshan on 2020/4/26 23:35
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {


            //创建客户端启动对象
            //注意客户端使用的不是ServerBootstrap，而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)  //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler()); //加入自己的处理器
                        }
                    });
            System.out.println("客户端 is ok..");
            //启动客户端，去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
