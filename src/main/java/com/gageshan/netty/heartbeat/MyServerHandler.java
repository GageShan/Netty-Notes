package com.gageshan.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Create by gageshan on 2020/4/29 10:31
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (stateEvent.state()) {
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + eventType);
        }
    }
}
