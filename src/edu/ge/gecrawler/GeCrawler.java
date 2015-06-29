package edu.ge.gecrawler;

import java.util.Set;

/**
 * Created by Ge on 2015/6/14.
 * The Crawler
 */
public class GeCrawler {

    CrawlerQueue crawlerQueue;

    public GeCrawler(){
        crawlerQueue = new CrawlerQueue();
    }

    private void init(String[] seeds){
        for (String seed : seeds) crawlerQueue.addNewUrl(seed);
    }

    /**
     *
     * @param seeds Start from those urls
     * @param dirStr Save downloaded pages in this dir
     * @param filterStr Limitation to keep crawler safely
     * @param limitedNum Max download number of pages
     */
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
        while (!crawlerQueue.isUnvisitedListEmpty()
                && crawlerQueue.sizeOfVisitedUrl() <= limitedNum){
            //Get next url to visit
            String visitUrl = crawlerQueue.nextUnvisitedUrl();
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
