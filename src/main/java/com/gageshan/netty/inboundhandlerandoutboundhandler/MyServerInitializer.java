package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Create by gageshan on 2020/4/29 17:08
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();



        //入站的handler 进行解码
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());
        //加入相关的handler
        pipeline.addLast(new MyServerHandler());
    }
}
