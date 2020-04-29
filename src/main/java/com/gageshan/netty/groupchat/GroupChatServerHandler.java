package com.gageshan.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by gageshan on 2020/4/28 23:35
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    //定义一个channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE 全局事件执行器，是一个单例
    private static ChannelGroup channelGroup  = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //handlerAdded 表示连接建立，一旦建立，第一个被执行
    //将当前channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其它在线客户端
        channelGroup.writeAndFlush("[客户端] "+ channel.remoteAddress() + "加入聊天" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    //表示channel处于活动状态，提升上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }
    //表示channel处于非活跃状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线了");
    }

    //断开连接，将用户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch -> {
            if(channel != ch) { //不是当前channel，推送消息
                ch.writeAndFlush("[客户端]" + channel.remoteAddress() + " 发送了消息" + s + "\n");
            } else {
                channel.writeAndFlush("[自己]" + channel.remoteAddress() + " 发送了消息" + s + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
