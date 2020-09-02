package com.fly.simpletools.service.netty.server;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Mr_Fei
 * @description 服务监听器
 * @date 2020-08-24 16:20
 */
@Slf4j
@WebListener
public class NettyServerListener implements ServletContextListener {

    /**
     * 注入NettyServer
     */
    @Resource
    private NettyServer nettyServer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContext初始化...");
        // 启动netty服务
        new Thread(() -> nettyServer.run(), "nettyStart").start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}