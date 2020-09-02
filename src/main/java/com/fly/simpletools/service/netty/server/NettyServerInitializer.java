package com.fly.simpletools.service.netty.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author Mr_Fei
 * @description 服务端初始化
 * @date 2020-08-24 16:22
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 注册通道初始化
     *
     * @param ch 注册的通道
     * @author Mr_Fei
     * @date 2020/8/24 16:58
     * @description initChannel
     */
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        //配置解码字符串的最大长度
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //设置字符串编码器
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //设置字符串解码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new NettyServerHandler());
    }

}