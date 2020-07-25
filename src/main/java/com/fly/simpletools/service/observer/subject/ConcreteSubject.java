package com.fly.simpletools.service.observer.subject;

import com.fly.simpletools.service.observer.observer.Observer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr_Fei
 * @description 主题实现类
 * @date 2020-07-19 16:22
 */
@Component
public class ConcreteSubject implements Subject {

    /**
     * 传递通知内容
     */
    private Object content;
    /**
     * 存放观察者的集合
     */
    private List<Observer> observers = new ArrayList<>();
    /**
     * 判断是否内容发生变化
     */
    private boolean changed = false;

    @Override
    public void addObserver(Observer observer) {
        if (observer != null) {
            this.observers.add(observer);
        }
    }

    @Override
    public void deleteObserver(Observer observer) {
        if (observer != null) {
            this.observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        if (observers.isEmpty() || !changed) {
            return;
        }
        for (Observer observer : observers) {
            observer.update(this, content);
        }
        this.changed = false;
    }

    @Override
    public void setChanged() {
        this.changed = true;
    }

    public Object getContent() {
        return content;
    }

    /**
     * @param content 内容
     * @author Mr_Fei
     * @date 2020/7/19 16:36
     * @description set方法判断值是否发生改变，改变则通知观察者
     */
    public void setContent(Object content) {
        if (content != this.content) {
            this.setChanged();
        }
        this.content = content;
        this.notifyObserver();
    }

}
