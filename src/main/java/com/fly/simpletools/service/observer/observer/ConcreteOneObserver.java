package com.fly.simpletools.service.observer.observer;

import com.fly.simpletools.service.observer.subject.Subject;
import com.fly.simpletools.service.observer.subject.ConcreteSubject;

/**
 * @author Mr_Fei
 * @description 观察者1
 * @date 2020-07-19 16:26
 */
public class ConcreteOneObserver implements Observer {

    @Override
    public void update(Subject subject, Object arg) {
        ConcreteSubject testSubject = (ConcreteSubject) subject;
        //进行业务操作
        System.out.println("TestOneObserverImpl收到主题通知消息内容：" + testSubject.getContent());
    }
}
