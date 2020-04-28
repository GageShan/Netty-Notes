在handler中如果要处理一个非常耗时的业务，应当异步执行，提交给该channel对应的NioEventLoop的taskQueue中。

1、用户程序自定义的普通任务
```java
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ctx.channel().eventLoop().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            System.out.println("异常处理：" + e.getMessage());
                        }
                    }
                }
        );
        System.out.println("server ctx =" + ctx);
        //将msg转换成一个ByteBuf
        //ByteBuf 是Netty提供的，不是NIO 的 ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

    }
}
```

2、用户自定义定时任务
```java
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                        Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                        System.out.println("异常处理：" + e.getMessage());
                        }
                    }
                },5, TimeUnit.SECONDS
        );
    }
}
```
3、非当前Reactor线程调用Channel的各种方法
