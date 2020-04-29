package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Create by gageshan on 2020/4/29 17:23
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用" );
        System.out.println("msg = " + aLong);
        byteBuf.writeLong(aLong);
    }
}
