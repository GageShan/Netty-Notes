package com.gageshan.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Create by gageshan on 2020/4/29 17:10
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param channelHandlerContext
     * @param byteBuf
     * @param list  将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //需要判断是否有8个字节，才能读取一个long
        if(byteBuf.readableBytes() >= 8) {
            list.add(byteBuf.readLong());
        }
    }
}
