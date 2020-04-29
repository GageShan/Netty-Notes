package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Create by gageshan on 2020/4/29 17:17
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("从客户端 " + channelHandlerContext.channel().remoteAddress() + "读取到long， " + aLong);
        channelHandlerContext.writeAndFlush(98765L);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("ppp");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
