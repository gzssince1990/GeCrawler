import java.util.ArrayList;

/**
 * Created by Ge on 2015/6/13.
 */
public class Zhihu {
    static final String  ZHIHU_URL = "http://www.zhihu.com";
    static final String ZHIHU_URL_QUESTION = ZHIHU_URL + "/question/";

    public String question;
    public String questionDescription;
    public String zhihuUrl;
    public ArrayList<String> answers;

    public Zhihu(String url){
        zhihuUrl = url;
        getAll(zhihuUrl);
    }

    public boolean getAll(String url){
        if (getRealUrl(url)){
            System.out.println("Catching" + zhihuUrl);
            //Question details page
            String content = Crawler.SendGet(zhihuUrl);

            try {
                question = Crawler.RegexString(content,"zh-question-title.+?<h2.+?>(.+?)</h2>").get(0);
                questionDescription = Crawler.RegexString(content,
                        "zh-question-detail.+?<div.+?>(.*?)</div>").get(0);
                answers = Crawler.RegexString(content,"/answer/content.+?<div.+?>(.*?)</div>");
                return true;
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("Invalid Url");
            return false;
        }
    }

    boolean getRealUrl(String url){
        try {
            zhihuUrl = ZHIHU_URL_QUESTION + Crawler.RegexString(url,"question/(.*?)/").get(0);
            return true;
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
    }

    public String getString(){
        String result = "";
        result += "Q: " + question + "\r\n";
        result += "Description: " + questionDescription + "\r\n";
        result += "Link: " + zhihuUrl + "\r\n";
        for (int i = 0; i <answers.size() ; i++)
            result += "A" + i + ": " + answers.get(i) + "\r\n";
        result += "\r\n\r\n\r\n";

        result.replaceAll("<br>","\r\n");
        result.replaceAll("<.+?>","");

        return result;
    }

    @Override
    public String toString() {
        return "Q: " + question + "\nDescription: " + questionDescription +
                "\nLink: " + zhihuUrl + "\nA: " + answers +"\n";
    }
}
