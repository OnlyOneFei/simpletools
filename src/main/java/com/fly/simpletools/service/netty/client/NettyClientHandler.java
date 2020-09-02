package com.fly.simpletools.service.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Mr_Fei
 * @description 客户端处理器
 * @date 2020-08-24 16:26
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("channelRead0 收到服务端消息: " + msg);
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        log.info("远程地址IP：[" + clientIp + "]");
    }

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        System.out.println("channelRead 收到服务端消息: " + msg);
    }*/
}
