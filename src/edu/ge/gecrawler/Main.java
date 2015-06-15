package edu.ge.gecrawler;

/**
 * Created by Ge on 2015/6/13.
 */
public class Main {
    public static void main(String[] args) {

        /* Zhihu
        String url = "http://www.zhihu.com/explore/recommendations";
        String content = Crawler.SendGet(url);
        ArrayList<Zhihu> myZhihu = Crawler.GetZhihu(content);

        for (Zhihu zhihu: myZhihu)
            FileRW.writeToFile(zhihu.getString(), "E:/Zhihu_content.txt", true);
        */

        //yeah~~~~~~~~~~~~~~~~~~
        GeCrawler geCrawler = new GeCrawler();
        geCrawler.crawl(new String[]{"http://www.genkit.net"},"E:/CrawlerTest","http://www.genkit.net",20);
    }
}
