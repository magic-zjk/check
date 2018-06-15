package com.jifeng.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

@SuppressWarnings({ "deprecation", "resource" })
public class HttpClientPoolUtil {
	private  String url;
	private PoolingClientConnectionManager cm;
	public  HttpClientPoolUtil(String url){
		this.url = url;
		 SchemeRegistry schemeRegistry = new SchemeRegistry();  
		    schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));  
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));  
			cm = new PoolingClientConnectionManager(schemeRegistry);  
		    cm.setMaxTotal(200);  
		    cm.setDefaultMaxPerRoute(2);  
		    HttpHost post = new HttpHost(this.url, 80);  
		    cm.setMaxPerRoute(new HttpRoute(post), 30);  
		
	}
	
	public  String getHttpClientGetPararm(String u){
		String result ="";
	    DefaultHttpClient client = new DefaultHttpClient(cm);  
	    Integer socketTimeout = 10000;  
	    Integer connectionTimeout = 10000;  
	    final int retryTime = 3;  
	    client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);  
	    client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);  
	    client.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, false);  
	    client.getParams().setParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 1024 * 1024);  
	    HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler()  
	    {  
	        @Override  
	        public boolean retryRequest(IOException exception, int executionCount, HttpContext context)  
	        {  
	            if (executionCount >= retryTime)  
	            {  
	                // Do not retry if over max retry count  
	                return false;  
	            }  
	            if (exception instanceof InterruptedIOException)  
	            {  
	                // Timeout  
	                return false;  
	            }  
	            if (exception instanceof UnknownHostException)  
	            {  
	                // Unknown host  
	                return false;  
	            }  
	            if (exception instanceof ConnectException)  
	            {  
	                // Connection refused  
	                return false;  
	            }  
	            if (exception instanceof SSLException)  
	            {  
	                // SSL handshake exception  
	                return false;  
	            }  
	            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);  
	            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);  
	            if (idempotent)  
	            {  
	                // Retry if the request is considered idempotent  
	                return true;  
	            }  
	            return false;  
	        }  
	      
	    };  
	    client.setHttpRequestRetryHandler(myRetryHandler);  
	    
	    
	    HttpResponse response = null;  
	    HttpGet get = new HttpGet(u);  
	    get.addHeader("Accept", "text/html");  
	    get.addHeader("Accept-Charset", "gb2312");  
	    get.addHeader("Accept-Encoding", "gzip");  
	    get.addHeader("Accept-Language", "en-US,en");  
	    get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");  
	    try {
			response = client.execute(get);
		    HttpEntity entity = response.getEntity();  
		    Header header = entity.getContentEncoding();  
		    if (header != null)  
		    {  
		        HeaderElement[] codecs = header.getElements();  
		        for (int i = 0; i < codecs.length; i++)  
		        {  
		            if (codecs[i].getName().equalsIgnoreCase("gzip"))  
		            {  
		                response.setEntity(new GzipDecompressingEntity(entity));  
		            }  
		        }  
		    }  
		    
		    byte[] rs = null;
		    HttpEntity resEntity = response.getEntity();
			if (resEntity == null) {
	            return null;
	        }
	        if (resEntity.isStreaming()) {
	            InputStream instream = resEntity.getContent();
	            ByteArrayOutputStream bos = null;
	            BufferedInputStream bufis ;
	            try{
	            	bos = new ByteArrayOutputStream();
	            	byte[] buf = new byte[1024];
	            	bufis = new BufferedInputStream(instream);
	            	int len = -1;
	            	while((len=bufis.read(buf))!=-1){
	            		bos.write(buf,0,len);
	            	}
	            	rs = bos.toByteArray();
	            }catch(Exception e){
	            }finally {
	            	if (instream != null) {
		                instream.close();
		            }
	            	if(bos != null){
	            		bos.close();
	            	}	            	
	            }
	        }
	        try {
				result = new String(rs,"gb2312");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    
		return result ;
	}
	
	
	
   public static void main(String[] args) {
	 HttpClientPoolUtil httpClientPoolUtil = new HttpClientPoolUtil("http://ip.taobao.com");
    String str = httpClientPoolUtil.getHttpClientGetPararm("http://ip.taobao.com/service/getIpInfo.php?ip="+"116.226.71.209");
	System.out.println(str);
   }
}
