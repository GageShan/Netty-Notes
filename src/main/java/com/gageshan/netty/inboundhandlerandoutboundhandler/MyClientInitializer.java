package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Create by gageshan on 2020/4/29 17:22
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入出站的handler，对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyByteToLongDecoder());

        //自定义的handler，处理业务
        pipeline.addLast(new MyClientHandler());
        System.out.println();
    }
}
