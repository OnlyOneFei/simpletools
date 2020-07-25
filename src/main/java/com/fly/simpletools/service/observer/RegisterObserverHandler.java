package com.fly.simpletools.service.observer;

import com.fly.simpletools.service.observer.observer.ConcreteOneObserver;
import com.fly.simpletools.service.observer.observer.ConcreteTwoObserver;
import com.fly.simpletools.service.observer.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Mr_Fei
 * @description 观察者注册器
 * @date 2020-07-19 16:16
 */
@Component
public class RegisterObserverHandler {

    @Resource
    private Subject subject;

    @Bean
    private void register() {
        //注册观察者1
        subject.addObserver(new ConcreteOneObserver());
        //注册观察者2
        subject.addObserver(new ConcreteTwoObserver());
    }
}
