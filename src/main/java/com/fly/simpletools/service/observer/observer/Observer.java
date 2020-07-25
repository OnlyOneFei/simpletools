package com.fly.simpletools.service.observer.observer;

import com.fly.simpletools.service.observer.subject.Subject;

/**
 * @author Mr_Fei
 * @description 观察者接口
 * @date 2020-07-19 16:19
 */
public interface Observer {

    /**
     * @param subject 主题对象，拉方式参数
     * @param arg     消息内容，推方式参数
     * @author Mr_Fei
     * @date 2020/7/19 16:20
     * @description 更新方法
     */
    void update(Subject subject, Object arg);

}
