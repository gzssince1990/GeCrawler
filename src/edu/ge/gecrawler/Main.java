package edu.ge.gecrawler;

/**
 * Created by Ge on 2015/6/13.
 * Main function.
 */
public class Main {
    public static void main(String[] args) {

        GeCrawler geCrawler = new GeCrawler();
        //geCrawler.crawl(new String[]{"http://cs.montclair.edu"},"E:/CrawlerTest4","http://cs.montclair.edu",100);
        geCrawler.crawl(new String[]{"http://www.genkit.net"},"E:/CrawlerTest4","http://www.genkit.net",10);

        //String pdfStr = "adfafsdfasf.pdf";

        //System.out.println(pdfStr.endsWith("pdf"));
    }
}
