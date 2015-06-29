package edu.ge.gecrawler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.NodeList;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ge on 2015/6/14.
 * Simply analyze the downloaded pages and retrieve new url.
 */
public class HtmlParserTool {

    /**
     * inner interface for deciding which url to accept.
     */
    public interface LinkFilter{
        boolean accept(String url);
    }

    /**
     *
     * @param urlStr analyze content in this url
     * @param filter filter object which implemented the LinkFilter interface
     * @return Links in the url
     */
    public static Set<String> extractLinks(String urlStr,LinkFilter  filter){
        Set<String> links = new HashSet<String>();

        try {
            Parser parser = new Parser(urlStr);
            parser.setEncoding("gb2312");

            NodeFilter frameFilter = new NodeFilter() {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean accept(Node node) {
                    return node.getText().startsWith("frame src=");
                }
            };

            OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);

            NodeList list = parser.extractAllNodesThatMatch(linkFilter);

            for (int i=0; i<list.size(); i++){
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag){
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();
                    if (filter.accept(linkUrl))
                        links.add(linkUrl);
                    }else {
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1) end = frame.indexOf(">");
                    String frameUrl = frame.substring(5, end - 1);
                    if (filter.accept(frameUrl))
                        links.add(frameUrl);
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }

        return links;
    }
}
