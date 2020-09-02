package com.fly.simpletools.service.netty.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author Mr_Fei
 * @description 服务端处理器
 * @date 2020-08-24 16:23
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 监听接收消息，处理业务逻辑
     *
     * @param ctx 通道处理上下文
     * @param msg 收到的请求消息内容
     * @author Mr_Fei
     * @date 2020/8/24 16:35
     * @description channelRead0
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        StringBuilder sb;
        Map<String, Object> result;
        try {
            // 报文解析处理
            sb = new StringBuilder();
            result = JSON.parseObject(msg);
            sb.append(result);
            sb.append("解析成功");
            sb.append("\n");
            ctx.writeAndFlush(sb);
        } catch (Exception e) {
            String errorCode = "-1\n";
            ctx.writeAndFlush(errorCode);
            log.error("报文解析失败: " + e.getMessage());
        }
    }

    /**
     * 收到连接，获取ip信息
     *
     * @param ctx 通道上下文
     * @author Mr_Fei
     * @date 2020/8/24 16:34
     * @description channelActive
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        log.info("收到客户端[ip:" + clientIp + "]连接");
    }

    /**
     * 异常关闭连接
     *
     * @param ctx   ctx
     * @param cause cause
     * @author Mr_Fei
     * @date 2020/8/24 16:34
     * @description exceptionCaught
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        log.info("客户端[ip:" + clientIp + "]连接出现异常，服务器主动关闭连接。。。");
        ctx.close();
    }

}