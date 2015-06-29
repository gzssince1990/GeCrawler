package edu.ge.gecrawler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ge on 2015/6/13.
 * CrawlerQueue is used to manage the visited url and unvisited url.
 */
public class CrawlerQueue {
    //visited HashSet;
    private Set<String> visitedUrl = new HashSet<String>();

    public void addVisitedUrl(String url) {visitedUrl.add(url);}
    public void removeVisitedUrl(String url) {visitedUrl.remove(url);}
    public int sizeOfVisitedUrl() {return visitedUrl.size();}

    //unvisited
    private MyQueue<String> unvisitedList = new MyQueue<String>();

    public MyQueue<String> getUnvisitedList(){return unvisitedList;}
    public String nextUnvisitedUrl() {return unvisitedList.deQueue();}
    public void addNewUrl(String url){
        if (url != null && !(url = url.trim()).equals("") && !visitedUrl.contains(url)
                && !unvisitedList.contains(url))
            unvisitedList.enQueue(url);
    }
    public boolean isUnvisitedListEmpty() {return unvisitedList.isEmpty();}

}
