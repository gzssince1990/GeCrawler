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
                && crawlerQueue.sizeOfToDownloadUrl() < limitedNum){
            //Get next url to visit
            String visitUrl = crawlerQueue.nextUnvisitedUrl();
            //Url is null, go to next loop
            if (visitUrl == null) continue;

            //Mark visited
            crawlerQueue.addVisitedUrl(visitUrl);
            if (visitUrl.endsWith("pdf")){
                System.err.println("WARNING: URL " + visitUrl + " does not contain text");
                continue;
            }else if (visitUrl.endsWith("png")){
                System.err.println("WARNING: URL " + visitUrl + " does not contain text");
                continue;
            }
            //Extract new links from this link
            Set<String> links = HtmlParserTool.extractLinks(visitUrl, filter);
            for (String link : links) crawlerQueue.addNewUrl(link);
        }


        /*
        while (!crawlerQueue.isToDownloadListEmpty() && crawlerQueue.sizeOfDownloadedUrl() < limitedNum){

            //Get next url to download
            String visitUrl = crawlerQueue.nextToDownloadUrl();
            //Url is null, go to next loop
            if (visitUrl == null) continue;

            //Download pages;
            DownloadTool downloader = new DownloadTool();
            downloader.downloadFile(visitUrl, dirStr);
            crawlerQueue.addDownloadedUrl(visitUrl);
            //if(downloader.downloadFile(visitUrl, dirStr) != null){}
        }
        */

        //Use a download thread to download
        new DownloadThread(dirStr, limitedNum).start();
    }


    /**
     * @ inner class download thread
     */

    class DownloadThread extends Thread{
        String dirStr;
        int limitedNum;

        public DownloadThread(String dirStr, int limitedNum){
            this.dirStr = dirStr;
            this.limitedNum = limitedNum;
        }

        @Override
        public void run() {
            while (!crawlerQueue.isToDownloadListEmpty() && crawlerQueue.sizeOfDownloadedUrl() < limitedNum){

                //Get next url to download
                String visitUrl = crawlerQueue.nextToDownloadUrl();
                //Url is null, go to next loop
                if (visitUrl == null) continue;

                //Download pages;
                DownloadTool downloader = new DownloadTool();
                downloader.downloadFile(visitUrl, dirStr);
                crawlerQueue.addDownloadedUrl(visitUrl);
            }
        }
    }
}
