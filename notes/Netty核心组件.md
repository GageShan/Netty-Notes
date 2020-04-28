# BootStrap ServerBootServerStrap
一个netty应用通常由一个bootstrap开始，主要作用是配置整个netty程序，串联各个组件。netty中BootStrap类是客户端程序的启动引导类，ServerBootStrap是服务器端启动引导类。

# Future、ChannelFuture
netty中所有io操作都是异步的，不能立即得知消息是否被正确的处理，但是可以过一会等它执行完或者直接注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件。

# Channel
1、netty网络通信的组件，能够用于执行网络I/O操作  
2、获取当前网络连接的通道的状态、当前网络连接的配置参数（接收缓冲区大小）  

出站：事件的运动方向从客户端到服务器端
入站：事件的运动方向从服务器端到客户端