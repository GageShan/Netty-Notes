package com.gageshan.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.time.LocalDateTime;

/**
 * Create by gageshan on 2020/4/29 11:34
 */

//TextWebSocketFrame 表示一个文本帧（frame）
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息" + textWebSocketFrame.text());
        Channel channel = channelHandlerContext.channel();
        channel.writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + textWebSocketFrame.text()));


    }

    //客户端连接被触发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handleradded被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生" + cause.getMessage());
        ctx.close();
    }
}
