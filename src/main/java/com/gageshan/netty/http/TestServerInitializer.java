package com.gageshan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Create by gageshan on 2020/4/28 10:41
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入一个netty提供的httpServerCodec（处理http的编码-解码器）
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());

        //增加一个自定义的handler
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());
    }
}
