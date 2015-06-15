package edu.ge.gecrawler;

import edu.ge.gecrawler.CrawlerQueue;

import java.util.Set;

/**
 * Created by Ge on 2015/6/14.
 */
public class GeCrawler {

    CrawlerQueue crawlerQueue;

    public GeCrawler(){
        crawlerQueue = new CrawlerQueue();
    }

    private void init(String[] seeds){
        for (int i = 0; i < seeds.length; i++) crawlerQueue.addNewUrl(seeds[i]);
    }

    public void crawl(String[] seeds, String dirStr,final String filterStr, int limitedNum){
        //Implement LinkFilter
        HtmlParserTool.LinkFilter filter = new HtmlParserTool.LinkFilter() {
            @Override
            public boolean accept(String url) {
                return url.startsWith(filterStr);
            }
        };
        //Initial the crawler queue
        init(seeds);
        //When visit plan in crawler queue is empty or when we get enough pages, stop!
        while (!crawlerQueue.isToVisitListEmpty()
                && crawlerQueue.getVisitedUrlNum() <= limitedNum){
            //Get next url to visit
            String visitUrl = crawlerQueue.next();
            //Url is null, go to next loop
            if (visitUrl == null) continue;
            //Download pages;
            DownloadTool downloader = new DownloadTool();
            downloader.downloadFile(visitUrl,dirStr);

            //Mark visited
            crawlerQueue.addVisitedUrl(visitUrl);

            //Extract new links from this link
            Set<String> links = HtmlParserTool.extractLinks(visitUrl, filter);
            for (String link : links) crawlerQueue.addNewUrl(link);
        }
    }
}
