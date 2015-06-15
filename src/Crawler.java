import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ge on 2015/6/13.
 */
public class Crawler {

    static String SendGet(String url){
        //the string store the content of the web page
        String result = "";
        //buffer
        BufferedReader in = null;

        try {
            //string to url
            URL realUrl = new URL(url);
            //initial the connection
            URLConnection connection = realUrl.openConnection();
            //start connection
            connection.connect();
            //init buffer
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            //store the input
            String line;
            while ((line = in.readLine()) != null)
                result += line;//store content from buffer to result;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        //use finally to close the stream
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    static ArrayList<Zhihu> GetZhihu(String content){
        ArrayList<Zhihu> results = new  ArrayList<Zhihu>();

        //ArrayList<String> questionList = RegexString(content,"question_link.+?>(.+?)<");
        ArrayList<String> urlList = RegexString(content,"<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");

        int n = urlList.size();
            for (int i=0; i<n; i++){
                results.add(new Zhihu(urlList.get(i)));
            }

        return results;
    }

    static ArrayList<String> RegexString(String targetStr, String patternStr){
        ArrayList<String> results = new ArrayList<String>();
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()){
            results.add(matcher.group(1));
        }
        return results;
    }
}
