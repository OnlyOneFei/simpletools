package com.fly.simpletools.service.observer.subject;

import com.fly.simpletools.service.observer.observer.Observer;

/**
 * @author Mr_Fei
 * @description 自己实现的主题接口
 * @date 2020-07-19 16:18
 */
public interface Subject {

    /**
     * @param observer 观察者
     * @author Mr_Fei
     * @date 2020/7/19 16:35
     * @description 注册观察者接口
     */
    void addObserver(Observer observer);

    /**
     * @param observer 观察者
     * @author Mr_Fei
     * @date 2020/7/19 16:35
     * @description 移除观察者
     */
    void deleteObserver(Observer observer);

    /**
     * @author Mr_Fei
     * @date 2020/7/19 16:35
     * @description 通知观察者
     */
    void notifyObserver();

    /**
     * @author Mr_Fei
     * @date 2020/7/19 16:35
     * @description 设置改变状态
     */
    void setChanged();
}
