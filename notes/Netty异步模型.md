# 异步模型
1）、异步的概念与同步相对。当一个异步过程调用发出后，调用者不能立即得到结果。实际处理这个调用的组件在完成后，通过状态、通知和回调来通知调用者

2）、Netty的IO操作是异步的，包括bind、write，connect等操作会简单的返回一个ChannelFuture

3)、调用者并不能立即得到结果，而是通过Future-Listener机制，用户可以方便的主动获取或者通过通知机制获得IO操作结果

4）、Netty的异步模型是建立在future和callback上。callback就是回调。重点说future，核心思想是：假设执行一个fun，计算过程可能非常耗时，那么等待方法fun的返回结果
就不是那么合适。所以在调用fun时，返回一个future，后续可以通过future去监控fun的处理过程

#Future-Listener机制
1）、当Future对象刚创建的时候，处于非完成状态，调用者可以通过返回的ChannelFuture来获取操作执行的状态，注册监听函数来执行完成后的操作。  
绑定端口是异步操作，当绑定操作处理完，将会调用相应的监听器处理逻辑
```java
public class NettyServer {
    public static void main(String[] args) throws Exception {
        ChannelFuture channelFuture = bootstrap.bind(6668).sync();
        
        //给channelFuture注册监听器，监控我们关心的事件
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    System.out.println("绑定端口 6668 成功");
                } else {
                    System.out.println("绑定端口 6668 失败");
                }
            }
        });
    }
}
```

相比传统阻塞I/O，执行I/O操作后线程会被阻塞，直到操作完成；异步处理的好处是不会造成线程阻塞，线程在I/O操作期间可以执行别的程序，在高并发情形下会更加稳定和更高的吞吐量