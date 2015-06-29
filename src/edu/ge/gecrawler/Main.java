package edu.ge.gecrawler;

/**
 * Created by Ge on 2015/6/13.
 * Main function.
 */
public class Main {
    public static void main(String[] args) {

        GeCrawler geCrawler = new GeCrawler();
        geCrawler.crawl(new String[]{"http://cs.montclair.edu"},"E:/CrawlerTest1","http://cs.montclair.edu",10);
    }
}
