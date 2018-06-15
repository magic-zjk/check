package com.jifeng.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

@SuppressWarnings({"deprecation","resource"})
public class HttpClientUtil {

	private static final Logger log = Logger.getLogger(HttpClientUtil.class);
	
	static class Run implements Runnable {
		int i ;
		public Run(int i){
			this.i = i;
		}
		
		@Override
		public void run() {
		}
	}
	
	static class Fin extends Thread{
		
		public void run(){
			
		}
	}
	
//	public static void main(String... args){
//		ExecutorService se =  Executors.newFixedThreadPool(200);
//		long start = System.currentTimeMillis();
//		for(int i=0; i<1000; i++){
//			se.execute(new Thread(new Run(i)));
//			
//		}
//		se.shutdown();
//		
//		Runtime.getRuntime().addShutdownHook(new Fin());
//	}
	
	public static String getHttpPost(String url,String data){
		if(StringUtils.isBlank(data)) data = "";
		String rs = null;
		try {
			byte[] trans = data.getBytes("utf-8");
			byte[] rdata = getHttpPost(url,trans);
			rs = new String(rdata,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("getHttpPost出错68行：", e);
		}
		
		return rs;
	}
	
	public static byte[] getHttpPost(String url,byte[] data){
		boolean isInfo = log.isInfoEnabled();
		HttpClient httpclient = new DefaultHttpClient();
		byte[] rs = null;
		ByteArrayInputStream bis = null;
		try {
			HttpPost httppost = new HttpPost(url);

			bis = new ByteArrayInputStream(data);

			InputStreamEntity reqEntity = new InputStreamEntity(bis, data.length);
			reqEntity.setContentType("binary/octet-stream");
			reqEntity.setContentEncoding("UTF-8");

			httppost.setEntity(reqEntity);
			
			if(isInfo) log.info("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			if(isInfo) log.info("----------------------------------------");
			if(isInfo) log.info(response.getStatusLine());
			if (resEntity != null) {
				if(isInfo) log.info("Response content length: "
						+ resEntity.getContentLength());
				if(isInfo) log.info("Chunked?: " + resEntity.isChunked());
			}
			//EntityUtils.consume(resEntity);
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
	            	log.error("getHttpPost127行：",e);
	            }finally {
	            	if (instream != null) {
		                instream.close();
		            }
	            	if(bos != null){
	            		bos.close();
	            	}	            	
	            }
	            
	        }
		} catch (Exception e) {
			log.error("",e);
		} finally {
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
			httpclient.getConnectionManager().shutdown();
		}	
		
		return rs;
	}	
	
	
	/**
	 * post 发送 xml
	 * @throws UnsupportedEncodingException 
	 * */
	public static byte[] HttpClientPost(String url,String parmater){
		boolean isInfo = log.isInfoEnabled();
		byte[] rs = null;
		HttpClient httpClient =new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(parmater);
			stringEntity.setContentType("text/xml; charset=utf-8");
			stringEntity.setContentEncoding("UTF-8");
			httppost.setEntity(stringEntity);
			
if(isInfo) log.info("executing request " + httppost.getRequestLine());

			HttpResponse response = httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			
if(isInfo) log.info("----------------------------------------");
if(isInfo) log.info(response.getStatusLine());
if (resEntity != null) {
	if(isInfo) log.info("Response content length: "
			+ resEntity.getContentLength());
	if(isInfo) log.info("Chunked?: " + resEntity.isChunked());
}
			
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
	            	log.error("getHttpPost127行：",e);
	            }finally {
	            	if (instream != null) {
		                instream.close();
		            }
	            	if(bos != null){
	            		bos.close();
	            	}	            	
	            }
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static String getHttpClientPost(String url,String paramter){
		if(StringUtils.isBlank(paramter)) paramter = "";
		String rs = null;
		try {
			byte[] rdata = HttpClientPost(url,paramter);
			rs = new String(rdata,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("getHttpPost出错68行：", e);
		}
		return rs;
	}
	
	/**
	 * 带参数post 请求
	 * 上传文件请求
	 * */
	public static String getHttpClientPostByParamter(String url,Map<String, Object> map){
		boolean isInfo = log.isInfoEnabled();
		byte[] rs = null;
		String result = null;
		try {
			HttpClient httpClient =new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
			MultipartEntity multipartEntity = new MultipartEntity();
			
			Set<Entry<String, Object>> set = map.entrySet();
			Iterator<Entry<String, Object>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				Object object = entry.getValue();
				if(object instanceof File){
					multipartEntity.addPart(entry.getKey(), new FileBody((File)object));
				}else{
					multipartEntity.addPart(entry.getKey(), new StringBody((String)object));
				}
			}
			httppost.setEntity(multipartEntity);
			
if(isInfo) log.info("executing request " + httppost.getRequestLine());

			HttpResponse response = httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

/*******日志记录*******/
if(isInfo) log.info("----------------------------------------");
if(isInfo) log.info(response.getStatusLine());
if (resEntity != null) {
	if(isInfo) log.info("Response content length: "
			+ resEntity.getContentLength());
	if(isInfo) log.info("Chunked?: " + resEntity.isChunked());
}
/*******日志记录*******/

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
	            	log.error("getHttpPost127行：",e);
	            }finally {
	            	if (instream != null) {
		                instream.close();
		            }
	            	if(bos != null){
	            		bos.close();
	            	}	            	
	            }
	        }
	        
	        //把结果转成xml
			try {
				result = new String(rs,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error("getHttpPost出错68行：", e);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 带参数post 请求
	 * */
	public static String getHttpClientPostParamter(String url,
			Map<String, String> map) {
		boolean isInfo = log.isInfoEnabled();
		byte[] rs = null;
		String result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();

			Set<Entry<String, String>> set = map.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				Object object = entry.getValue();
				formparams.add(new BasicNameValuePair(entry.getKey(),
						(String) object));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					"UTF-8");
			httppost.setEntity(entity);

			if (isInfo)
				log.info("executing request " + httppost.getRequestLine());

			HttpResponse response = httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			/******* 日志记录 *******/
			if (isInfo)
				log.info("----------------------------------------");
			if (isInfo)
				log.info(response.getStatusLine());
			if (resEntity != null) {
				if (isInfo)
					log.info("Response content length: "
							+ resEntity.getContentLength());
				if (isInfo)
					log.info("Chunked?: " + resEntity.isChunked());
			}
			/******* 日志记录 *******/

			if (resEntity == null) {
				return null;
			}
			if (resEntity.isStreaming()) {
				InputStream instream = resEntity.getContent();
				ByteArrayOutputStream bos = null;
				BufferedInputStream bufis;
				try {
					bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					bufis = new BufferedInputStream(instream);
					int len = -1;
					while ((len = bufis.read(buf)) != -1) {
						bos.write(buf, 0, len);
					}
					rs = bos.toByteArray();
				} catch (Exception e) {
					log.error("getHttpPost127行：", e);
				} finally {
					if (instream != null) {
						instream.close();
					}
					if (bos != null) {
						bos.close();
					}
				}
			}

			// 把结果转成xml
			try {
				result = new String(rs, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error("getHttpPost出错68行：", e);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/***
	 * 享说 联络圈 发送图片请求
	 * */
	public static String HttpEnjoyPostImage(Map<String,String> params,String actionUrl,File file,String fileName){
		 try {
		        String BOUNDARY = "--------------et567z"; //数据分隔线
		        String MULTIPART_FORM_DATA = "Multipart/form-data;charset=UTF-8"; 	 
		        URL url = new URL(actionUrl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();	 
		        conn.setDoInput(true);//允许输入
		        conn.setDoOutput(true);//允许输出
		        conn.setUseCaches(false);//不使用Cache
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Connection", "Keep-Alive");
//		        conn.setRequestProperty("Charset", "UTF-8");
		       // conn.setRequestProperty("Content-Encoding", "UTF-8");
		        conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);
		 
		        StringBuilder sb = new StringBuilder(); 
		 
		        //上传的表单参数部分，格式请参考文章
		        for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容
		            sb.append("--");
		            sb.append(BOUNDARY);
		            sb.append("\r\n");
		            sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
		            sb.append(entry.getValue());
		            sb.append("\r\n");
		        }
		        //System.out.println(sb.toString());
		        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		        outStream.write(sb.toString().getBytes("utf-8"));//发送表单字段数据
		        
		        byte[] content = readFileImage(file);
		       // byte[] content1 = readFileImage("d:\\1.png");

		        //上传的文件部分，格式请参考文章
		        //System.out.println("content:"+content.toString());
		        StringBuilder split = new StringBuilder();
		        split.append("--");
		        split.append(BOUNDARY);
		        split.append("\r\n");
		        split.append("Content-Disposition: form-data;name=\"pic\";filename=\""+fileName+"\"\r\n");
		        split.append("Content-Type: image/jpg\r\n\r\n");		        
		        //System.out.println(split.toString());
		        outStream.write(split.toString().getBytes());
		        outStream.write(content, 0, content.length);	  
		        outStream.write("\r\n".getBytes());
		        
		        //--
		       /* StringBuilder split1 = new StringBuilder();
		        split1.append("--");
		        split1.append(BOUNDARY);
		        split1.append("\r\n");
		        split1.append("Content-Disposition: form-data;name=\"cov\";filename=\"temp.jpg\"\r\n");
		        split1.append("Content-Type: image/jpg\r\n\r\n");		        
		        //System.out.println(split.toString());
		        outStream.write(split1.toString().getBytes());
		        outStream.write(content1, 0, content1.length);	  
		        outStream.write("\r\n".getBytes());*/
		    	        
		 
		        byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();//数据结束标志
		        outStream.write(end_data);
		        outStream.flush();
		        
		        
		        int cah = conn.getResponseCode();
		        
		        if(cah == 200)//如果发布成功则提示成功
		        {
		            /*读返回数据*/
		            //String strResult = EntityUtils.toString(httpResponse.getEntity());
		 
		        }
		        else if(cah == 400)
		        {

		        }else{
		            throw new RuntimeException("请求url失败:"+cah);
		        }
		            
		            byte[] responseData = null;
		            int length = conn.getContentLength();
//		            System.out.println("长度："+length);
		            // 如果回复消息长度不为-1，读取该消息。
		            if (length != -1) {
		                DataInputStream dis = new DataInputStream(conn.getInputStream());
		                responseData = new byte[length];
		                dis.readFully(responseData);
		                dis.close();
		            }
//		            System.out.println("解压前长度："+responseData.length);
//		            System.out.println(new String(responseData, "UTF-8"));
		            String test=new String(responseData, "UTF-8");
		           return test;

		    }
		    catch (IOException e)
		    {
		        e.printStackTrace();
		 
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace(); 
		 
		    } 
		return null;
	}
	
	public static byte[] readFileImage(File file) throws IOException {
	    BufferedInputStream bufferedInputStream = new BufferedInputStream(
	            new FileInputStream(file));
	    int len = bufferedInputStream.available();
	    byte[] bytes = new byte[len];
	    int r = bufferedInputStream.read(bytes);
	    if (len != r) {
	        bytes = null;
	        throw new IOException("读取文件不正确");
	    }
	    bufferedInputStream.close();
	    return bytes;
	}
	
	
	
	
	/**
	 * 豆瓣apihttp请求  设置头部
	 * */
	public static String getHttpClientDouban(String url,Map<String, Object> map,String token){
			boolean isInfo = log.isInfoEnabled();
			byte[] rs = null;
			String result = null;
			try {
				HttpClient httpClient =new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				
				MultipartEntity multipartEntity = new MultipartEntity();
				
				Set<Entry<String, Object>> set = map.entrySet();
				Iterator<Entry<String, Object>> iterator = set.iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					Object object = entry.getValue();
					if(object instanceof File){
						multipartEntity.addPart(entry.getKey(), new FileBody((File)object));
					}else{
						multipartEntity.addPart(entry.getKey(),new StringBody((String)object, Charset.forName("UTF-8")));
					}
				}
				httppost.setEntity(multipartEntity);
				httppost.setHeader("Authorization", "Bearer "+token);
	if(isInfo) log.info("executing request " + httppost.getRequestLine());

				HttpResponse response = httpClient.execute(httppost);
				HttpEntity resEntity = response.getEntity();

	/*******日志记录*******/
	if(isInfo) log.info("----------------------------------------");
	if(isInfo) log.info(response.getStatusLine());
	if (resEntity != null) {
		if(isInfo) log.info("Response content length: "
				+ resEntity.getContentLength());
		if(isInfo) log.info("Chunked?: " + resEntity.isChunked());
	}
	/*******日志记录*******/

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
		            	log.error("getHttpPost127行：",e);
		            }finally {
		            	if (instream != null) {
			                instream.close();
			            }
		            	if(bos != null){
		            		bos.close();
		            	}	            	
		            }
		        }
		        
		        //把结果转成xml
				try {
					result = new String(rs,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					log.error("getHttpPost出错68行：", e);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
	}
	
	/**
	 * 带参数post 请求
	 * 上传文件请求
	 * */
	public static String getHttpClientPost(String url,Map<String, Object> map){
		boolean isInfo = log.isInfoEnabled();
		byte[] rs = null;
		String result = null;
		try {
			HttpClient httpClient =new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
			MultipartEntity multipartEntity = new MultipartEntity();
			
			Set<Entry<String, Object>> set = map.entrySet();
			Iterator<Entry<String, Object>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				Object object = entry.getValue();
				if(object instanceof File){
					multipartEntity.addPart(entry.getKey(), new FileBody((File)object));
				}else{
//					multipartEntity.addPart(entry.getKey(), StringBody.create((String)object,"", Charset.forName("UTF-8")));
					multipartEntity.addPart(entry.getKey(),new StringBody((String)object, Charset.forName("UTF-8")));
				}
			}
			httppost.setEntity(multipartEntity);
			
if(isInfo) log.info("executing request " + httppost.getRequestLine());

			HttpResponse response = httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

/*******日志记录*******/
if(isInfo) log.info("----------------------------------------");
if(isInfo) log.info(response.getStatusLine());
if (resEntity != null) {
	if(isInfo) log.info("Response content length: "
			+ resEntity.getContentLength());
	if(isInfo) log.info("Chunked?: " + resEntity.isChunked());
}
/*******日志记录*******/

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
	            	log.error("getHttpPost127行：",e);
	            }finally {
	            	if (instream != null) {
		                instream.close();
		            }
	            	if(bos != null){
	            		bos.close();
	            	}	            	
	            }
	        }
	        
	        //把结果转成xml
			try {
				result = new String(rs,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error("getHttpPost出错68行：", e);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 下载
	 * */
	public static boolean download(String url,String filepath,String filename) {  
        HttpClient httpClient1 = new DefaultHttpClient();  
        HttpGet httpGet1 = new HttpGet(url);  
        try {  
            HttpResponse httpResponse1 = httpClient1.execute(httpGet1);  
  
            StatusLine statusLine = httpResponse1.getStatusLine();  
            if (statusLine.getStatusCode() == 200) {  
//                String filePath = PropertiesUtil.getValue("ip_txt")+filename;  
                         
                File file = new File(filepath);  
                FileOutputStream outputStream = new FileOutputStream(file);  
                InputStream inputStream = httpResponse1.getEntity()  
                        .getContent();  
                byte b[] = new byte[1024];  
                int j = 0;  
                while ((j = inputStream.read(b)) != -1) {  
                    outputStream.write(b, 0, j);  
                }  
                outputStream.flush();  
                outputStream.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            httpClient1.getConnectionManager().shutdown();  
        }  
        return true;  
    } 
	
	
	public static String sendGet(String url) {
		String result = "";
		try {
			String urlName = url ;//
			URL U = new URL(urlName);
			URLConnection connection = U.openConnection();
			connection.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {
			System.out.println("没有结果！" + e);
		}
		return result;
	}

	
	public static void main(String[] args) {
//		String url = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
//		String filename = "ip.txt";
//		String url  = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=80.187.101.86";
//		System.out.println(sendGet(url));
//		System.out.println(download(url,filename));
//		String filepath = PropertiesUtil.getValue("ip_txt")+filename;
//		File file = new File(filepath);
//		 BufferedReader reader = null;
//	        try {
//	            System.out.println("以行为单位读取文件内容，一次读一整行：");
//	            reader = new BufferedReader(new FileReader(file));
//	            String tempString = null;
//	            int line = 1;
//	            // 一次读入一行，直到读入null为文件结束
//	            while ((tempString = reader.readLine()) != null) {
//	                // 显示行号
//	                System.out.println("line " + line + ": " + tempString);
//	                line++;
//	            }
//	            reader.close();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } finally {
//	            if (reader != null) {
//	                try {
//	                    reader.close();
//	                } catch (IOException e1) {
//	                }
//	            }
//	        }
//		String url = "http://tthemestyle.jifeng.com/apitheme/themezip/20131202162745/lu:sexinqing.zip";
//		download(url,"E:/lu-sexinqing.zip","lu-sexinqing.zip");
		
		
	}
}
