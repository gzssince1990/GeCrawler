package edu.ge.gecrawler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ge on 2015/6/13.
 */
public class CrawlerQueue {
    //visited
    private Set<String> visitedUrl = new HashSet<String>();
    public void addVisitedUrl(String url){
        visitedUrl.add(url);
    }
    public void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }
    public int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    //unvisited
    private MyQueue<String> toVisitList = new MyQueue<String>();
    public MyQueue<String> getToVisitList(){
        return toVisitList;
    }
    public String next(){
        return toVisitList.deQueue();
    }
    public void addNewUrl(String url){
        if (url != null && !(url = url.trim()).equals("")
                && !visitedUrl.contains(url) && !toVisitList.contains(url)){
            toVisitList.enQueue(url);
        }
    }
    public boolean isToVisitListEmpty(){
        return toVisitList.isEmpty();
    }

}
