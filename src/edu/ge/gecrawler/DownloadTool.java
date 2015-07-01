package edu.ge.gecrawler;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.UnknownHostException;


/**
 * Created by Ge on 2015/6/13.
 * Crawler's download functions.
 */
public class DownloadTool {
    /**
     * Get a formal file name by url
     * get rid of invalid characters
     * bug: what char to escape;            .pdf.pdf
     */
    private String getFileNameByUrl(String url, String contentType){
        String fileName;
        //get rid of http://
        fileName = url.replace("http://","");
        //replace those invalid char with '_'
        fileName = contentType.contains("html") ?
                fileName.replaceAll("[\\?/:*|<>\"]", "_") + ".html"
                : fileName.replaceAll("[\\?/:*|<>\"]", "_") + "."
                + contentType.substring(contentType.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     * download from url
     * Save to file
     */
    public String downloadFile(String urlStr, String dirStr){
        //Give up try connecting after 5s
        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy(){
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1 ) keepAlive = 5000;
                return keepAlive;
            }
        };

        //Set the configuration of the retry handler
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i >= 5) return false;
                if (e instanceof InterruptedIOException) return false;
                if (e instanceof UnknownHostException) return false;
                if (e instanceof ConnectTimeoutException) return false;
                if (e instanceof SSLException) return false;
                HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
                HttpRequest request = clientContext.getRequest();
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };

        //Create the closable http client and the get method
        CloseableHttpClient httpClient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrategy).setRetryHandler(retryHandler).build();
        HttpGet httpGet = new HttpGet(urlStr);

        try {
            //execute without response handler
            HttpResponse response = httpClient.execute(httpGet);
            //get status code
            int statusCode = response.getStatusLine().getStatusCode();

            //handler accident
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + response.getStatusLine());
                return null;
            }
            //get entity of get method
            HttpEntity entity = response.getEntity();
            //get the file name from the url
            String filename = getFileNameByUrl(urlStr, entity.getContentType().getValue());
            //get directory form parameter; get file from file name
            File dir = new File(dirStr);
            File file = new File(dirStr,filename);
            //create dir if it does not exist; delete file if it exists
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    System.err.println("Failed to create the new directory!!");
                    System.exit(1);
                }
            } else if (file.exists())
                if (!file.delete())
                    System.err.print("Failed to delete the existing file!!");
            //create output stream for file an write the content in the entity into it
            OutputStream output = new FileOutputStream(file);
            entity.writeTo(output);
            output.flush();
            output.close();
            //return the file path
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (httpClient != null) httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
