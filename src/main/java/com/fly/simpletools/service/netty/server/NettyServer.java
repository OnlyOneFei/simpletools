package com.fly.simpletools.service.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Mr_Fei
 * @description Netty 服务端
 * @date 2020-08-24 16:22
 */
@Slf4j
@Component
public class NettyServer {

    /**
     * 端口号
     */
//    @Value("${netty.port}")
    private int port = 8000;

    /**
     * 启动服务器方法
     */
    public void run() {
        //初始化用于Acceptor的主"线程池"以及用于I/O工作的从"线程池"；
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //通过ServerBootstrap的group方法，设置（1）中初始化的主从"线程池"；
            serverBootstrap.group(bossGroup, workerGroup);
            //指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
            serverBootstrap.channel(NioServerSocketChannel.class);
            //配置ServerSocketChannel的选项
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            //设置ServerSocketChannel的处理器
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            //配置子通道也就是SocketChannel的选项
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //设置子通道也就是SocketChannel的处理器， 其内部是实际业务开发的"主战场"
            serverBootstrap.childHandler(new NettyServerInitializer());
            // 绑定端口,开始接收进来的连接
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("netty服务启动: [port:" + port + "]");
            // 等待服务器socket关闭 绑定并侦听某个端口
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty服务启动异常-" + e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
