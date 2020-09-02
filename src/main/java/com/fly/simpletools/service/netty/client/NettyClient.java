package com.fly.simpletools.service.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Mr_Fei
 * @description 客户端
 * @date 2020-08-24 16:25
 */
public class NettyClient {

    /**
     * 主机
     */
    private String host;
    /**
     * 端口号
     */
    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 连接方法
     */
    public void connect(String jsonMsg) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            //保持长连接，2小时无数据激活心跳机制
            //bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new NettyClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.writeAndFlush(jsonMsg);
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 测试入口
     *
     * @param args args
     */
/*    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        // 发送json字符串
        String msg = "{\"name\":\"admin\",\"age\":27}\n";
        NettyClient nettyClient = new NettyClient(host, port);
        nettyClient.connect(msg);
    }*/

}
