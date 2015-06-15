import edu.ge.gecrawler.DownloadTool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ge on 2015/6/12.
 */
public class Downloader {



    public void postData() throws Exception {


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.genkit.net");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("mydata","Mek Mek"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer bab = new ByteArrayBuffer(20);

            int current = 0;
            while ((current = bis.read()) != -1){
                bab.append((byte) current);
            }

            String text = new String(bab.toByteArray(),"utf-8");
            System.out.println(text);
            is.close();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getData(String path) throws Exception{
        InputStream input = null;
        OutputStream output = null;

        //HttpClient httpClient = new DefaultHttpClient();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        /*
        HeaderElement[] head = entity.getContentType().getElements();
        for (HeaderElement h: head){
            System.out.println(h);
        }
        */

        String filename = path.substring(path.lastIndexOf('/')+1) + ".html";
        output = new FileOutputStream(filename);

        entity.writeTo(output);

        /*
        input = response.getEntity().getContent();
        String filename = path.substring(path.lastIndexOf('/')+1) + ".html";

        output = new FileOutputStream(filename);

        int tempByte;
        while ((tempByte = input.read()) > 0){
            output.write(tempByte);
        }

        if (input != null){
            input.close();
        }

        if (output != null){
            output.close();
        }

        */
    }


    public static void main(String[] args) throws Exception {
        /*
        Downloader downloader = new Downloader();
        downloader.getData("http://www.genkit.net");

        try {
            downloader.postData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //edu.ge.gecrawler.CrawlerQueue<String> cs = new edu.ge.gecrawler.CrawlerQueue<String>();
        //System.out.println(cs.deQueue());

        edu.ge.gecrawler.MyQueue<String> myQueue = new edu.ge.gecrawler.MyQueue<String>();
        String s1 = "www.baidu.com";
        myQueue.enQueue(s1);
        String s = "    www.baidu.com    ";
        if (s != null && !(s = s.trim()).equals("")
                && !myQueue.contains(s)){
            myQueue.enQueue(s);
        }
        System.out.println(myQueue.size());


        String url = "http://afasfsfadsfsadf";
        url = Crawler.RegexString(url,"http://(.+)").get(0);
        System.out.println(url);


        edu.ge.gecrawler.DownloadTool downloadTool = new edu.ge.gecrawler.DownloadTool();
        downloadTool.downloadFile("http://www.genkit.net");
        */

        DownloadTool tool = new DownloadTool();
        tool.downloadFile("http://www.genkit.net","E:/CrawlerTest");

    }
}
