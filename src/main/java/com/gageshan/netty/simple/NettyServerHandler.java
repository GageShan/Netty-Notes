package com.gageshan.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Create by gageshan on 2020/4/26 23:12
 */

/**
 * 自定义handler，需要继承netty规定好的HandlerAdapter（规范）
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server ctx =" + ctx);
        //将msg转换成一个ByteBuf
        //ByteBuf 是Netty提供的，不是NIO 的 ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~",CharsetUtil.UTF_8));

    }

    //发生异常后的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();

    }
}
