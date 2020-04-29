package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Create by gageshan on 2020/4/29 17:28
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    //发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(12345L);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("服务器的ip=" + channelHandlerContext.channel().remoteAddress() + "发送了消息 " + aLong);
    }
}
