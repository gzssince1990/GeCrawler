package edu.ge.gecrawler;

import java.util.LinkedList;

/**
 * Created by Ge on 2015/6/13.
 * Implement a simple Queue for CrawlerQueue
 */
public class MyQueue<T> {

    private LinkedList<T> queue = new LinkedList<T>();

    //methods: enQueue, deQueue, isEmpty, contains, size;
    public void enQueue(T t) {queue.addLast(t);}
    public T deQueue() {return queue.removeFirst();}
    public boolean isEmpty() {return queue.isEmpty();}
    public boolean contains(T t) {return queue.contains(t);}
    public int size() {return queue.size();}
}
