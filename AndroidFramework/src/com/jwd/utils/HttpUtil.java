package com.jwd.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jwd.api.LXHTTPException;
import com.jwd.api.LXParameters;
import com.jwd.api.RequestListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class HttpUtil {
	private static String TAG =  "http";
	private static final boolean debug = true ; 
	public static final String HTTPMETHOD_POST = "POST"; //POST请求
	public static final String HTTPMETHOD_GET = "GET"; //GET请求
	public static final int  NET_DISCONNECT =  -2 ; 
	public static final int NET_ERROR = -1 ; 
	public static final int REQUEST_TIMEOUT = 10*1000;
	public static final int SO_TIMEOUT = 10*1000;
	/**
	 * @param args
	 * 客户端HTTP工具类
	 * autor:yy
	 * @throws LXHTTPException 
	 */
	public static String doPostForLX (String url ,LXParameters lxParameters) throws LXHTTPException {
		return doPostForLX(null ,url, lxParameters.toHttpParam());
	} 
	public static String doGetForLX (String url ,LXParameters lxParameters) throws LXHTTPException {
		return doGetForLX(url + "?" + lxParameters.toHttpParamString());
	} 
	public static void request (final String url ,final LXParameters lxParameters,final String httpMethod,final RequestListener requestListener ) {
		new Thread() {
			@Override
			public void run()  {
				String resp;
				try {
					resp = requestUrl(url,lxParameters , httpMethod );
					requestListener.onComplete(resp);
				} catch (LXHTTPException e) {
					e.printStackTrace();
					requestListener.onError(e);
				}
				
			}
		}.start();
	}
	public static String requestUrl (final String url ,final LXParameters lxParameters,final String httpMethod) throws LXHTTPException{
		if(HTTPMETHOD_GET.equals(httpMethod)){
			return doGetForLX(url,lxParameters);
		}else {
			return doPostForLX(url, lxParameters);
		}
	}
	/**
	 * @param args
	 * 客户端HTTP工具类
	 * autor:yy
	 * @throws LXHTTPException 
	 */
	public static String doPostForLX(Context context,String url,List<NameValuePair>  params) throws LXHTTPException{
		if(debug)Log.d(TAG, "post url = "+url);
		for (int i = 0; i < params.size(); i++) {
			NameValuePair np = params.get(i);
			if(debug)Log.d(TAG, "post url params = "+np.getName() + "------>"+np.getValue());
		}
		long a = System.currentTimeMillis();
		String result="";
		if(url == null || "".equals(url)){
			return result;
		}
		if(context != null ){
			if(!isConnectInternet(context)){
				//网络为连接
				throw new LXHTTPException(-2);
			}
		}
		HttpParams httpParams = new BasicHttpParams();  
		//新建HttpClient对象  
		HttpClient client  = new DefaultHttpClient(httpParams);
		HttpPost request = new HttpPost(url);
		request.setHeader("Accept-Encoding","gzip");
		
		try { 
			request.setEntity(new UrlEncodedFormEntity(params,"GBk"));
			//尝试压缩
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String isZip = getResponseBody(response);
				if(StrUtil.isNotEmpty(isZip)){
					if(debug)Log.v(TAG, "post httpGzip str = "+isZip.trim() );
					if(debug)Log.v(TAG, "post isZip size is "+isZip.length()/1024+"K");
					return isZip.trim();
				}
				result = EntityUtils.toString(response.getEntity());
				if(debug)Log.v(TAG, "post size is "+result.length()/1024 +"K");
				if(result!=null) result = result.trim();
			}else{
				//如果返回的不是200,抛出异常
				if(debug)Log.d(TAG, "post StatusCode = "+response.getStatusLine().getStatusCode());
				long b = System.currentTimeMillis();
				if(debug)Log.v(TAG, "cost"+(b-a));
				throw new LXHTTPException(result, response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			//网络问题,抛出异常
			long b = System.currentTimeMillis();
			if(debug)Log.v(TAG, "cost"+(b-a));
			throw new LXHTTPException(e);
		}
		long b = System.currentTimeMillis();
		if(debug)Log.v(TAG, "cost"+(b-a));
		if(debug)Log.v(TAG, "result="+result);
		return result;
	}
	/**
	 * @param args
	 * 客户端HTTP工具类
	 * autor:yy
	 */
	public static String doPostNoGZip(Context context,String url,List<NameValuePair>  params){
		if(debug)Log.d(TAG, "post url = "+url);
		long a = System.currentTimeMillis();
		String result="";
		if(url == null || "".equals(url)){
			return result;
		}
		if(!isConnectInternet(context)){
			return "-2";
		}
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams,  
		                15000);  
		HttpConnectionParams.setSoTimeout(httpParams,  
		               15000); 
		//新建HttpClient对象  
		HttpClient client  = new DefaultHttpClient(httpParams);
		HttpPost request = new HttpPost(url);
		request.setHeader("Accept-Language","ZH-CN");
		try { 
			request.setEntity(new UrlEncodedFormEntity(params,"GBk"));
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(response.getEntity());
				if(debug)Log.v(TAG, "post size is "+result.length()/1024 +"K");
				if(result!=null) result = result.trim();
			}else{
				if(debug)Log.d(TAG, "post StatusCode = "+response.getStatusLine().getStatusCode());
				result="-1";
			}
		} catch (UnsupportedEncodingException e) {
			result="-7";
			//e.printStackTrace();
		} catch (ClientProtocolException e) {
			result="-7";
			//e.printStackTrace();
		} catch (ParseException e) {
			result="-7";
			//e.printStackTrace();
		} catch (IOException e) {
			result="-7";
			//e.printStackTrace();
		} catch (Exception e) {
			result="-7";
		}
		long b = System.currentTimeMillis();
		if(debug)Log.v(TAG, "cost"+(b-a));
		if(debug)Log.v(TAG, "result="+result);
//		writeInSDCard(url, a,result.length());
		return result;
	}
	public static String doGet2(String urlBuf ,String longStr){
        if(longStr.equals("")){
        	return "-1";
        }
		StringBuffer backState = new StringBuffer();   
		 try{   
	          // 用于测试，读的自己本地的XML文件
	          URL url = new URL(urlBuf);   
	          HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	          conn.setRequestProperty("content-type", "textml");
	          conn.setDoOutput(true); 
	          conn.setDoInput(true);

	          conn.setRequestMethod("POST");   
	   
	          OutputStream out = conn.getOutputStream();   
	          GZIPOutputStream gzout = new GZIPOutputStream(out);   
	          gzout.write(longStr.getBytes());   
	          gzout.close();
	          out.close(); 
	           
	          
	          // 获取返回内容
	          InputStream in = conn.getInputStream();   
	          BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
	         
	          
	          String line = null;   
	          while((line = br.readLine()) != null){   
	                backState.append(line);   
	          }    
	          in.close();
	          
	       }catch(Exception ex){    
	                return "-1";
	       }
	       if(debug)Log.v(TAG, "backState="+backState);
	       return  backState.toString();
	}

	/**
     * 读取请求内容
     * 如果返回内容是压缩内容，解压缩
     * @param get
     * @return
     * @throws IOException
     */
    public static String getResponseBodyAsString(HttpResponse get) throws IOException {
        if (get.getEntity() != null) {
            if(get.getFirstHeader("Content-Encoding") != null
                    && get.getFirstHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip") > -1) {
                //For GZip response
                InputStream is = get.getEntity().getContent();
                GZIPInputStream gzin = new GZIPInputStream(is);
               
                InputStreamReader isr = new InputStreamReader(gzin);
                java.io.BufferedReader br = new java.io.BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String tempbf;
                while ((tempbf = br.readLine()) != null) {
                    sb.append(tempbf);
                    sb.append("/r/n");
                }
                isr.close();
                gzin.close();
                return sb.toString();
            } else {
                return  EntityUtils.toString(get.getEntity());
            }
        } else {
            return null;
        }
       
    }
	public static String doGetNoGZip(String url , Context context){
		if(debug)Log.d(TAG, "get url = "+url);
		long a = System.currentTimeMillis();
		String result="";
		if(context!=null && !isConnectInternet(context)){
			return "-2";
		}
		if(url == null || "".equals(url)){
			return result;
		}
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams,  
		                  15000);  
		HttpConnectionParams.setSoTimeout(httpParams,  
		                 15000); 
		HttpClient client  = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response =	client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(response.getEntity()).trim();
				if(debug)Log.v(TAG, "get size is "+result.length()/1024 +"K");
			}else{
				if(debug)Log.d(TAG, "get StatusCode = "+response.getStatusLine().getStatusCode());
				result="-1";
			}
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
			return "-6";
		} catch (IOException e) {
			//e.printStackTrace();
			return "-7";

		} catch (Exception e) {
			return "-7";
		}
		long b = System.currentTimeMillis();
		if(debug)Log.v(TAG, "cost"+(b-a));
		if(debug)Log.v(TAG, "result="+result);
//		writeInSDCard(url, a,result.length());
		return result;
	}
	/**
	 * HTTP GET
	 * @param url
	 * @return
	 * @throws LXHTTPException 
	 */
	public static String doGetForLX(String url) throws LXHTTPException{
		if(debug)Log.d(TAG, "get url = "+url);
		long a = System.currentTimeMillis();
		String result="";
		if(url == null || "".equals(url)){
			return result;
		}
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams,  
		                  15000);  
		HttpConnectionParams.setSoTimeout(httpParams,  
		                 15000); 
		HttpClient client  = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(url);
		request.setHeader("Accept-Encoding","gzip");
		try {
			HttpResponse response =	client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String isZip = getResponseBody(response);
				if(StrUtil.isNotEmpty(isZip)){
					if(debug)Log.v(TAG, "get httpGzip str = "+isZip.trim() );
					if(debug)Log.v(TAG, "get isZip size is "+isZip.length()/1024 +"K");
//					writeInSDCard(url, a,isZip.length());
					return isZip.trim();
				}
				result = EntityUtils.toString(response.getEntity()).trim();
				if(debug)Log.v(TAG, "get size is "+result.length()/1024 +"K");
			}else{
				if(debug)Log.d(TAG, "get StatusCode = "+response.getStatusLine().getStatusCode());
				long b = System.currentTimeMillis();
				if(debug)Log.v(TAG, "cost"+(b-a));
				throw new LXHTTPException(response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			long b = System.currentTimeMillis();
			if(debug)Log.v(TAG, "cost"+(b-a));
			throw new LXHTTPException(e);
		}
		long b = System.currentTimeMillis();
		if(debug)Log.v(TAG, "cost"+(b-a));
		if(debug)Log.v(TAG, "result="+result);
		return result;
	}
	public static boolean isConnectInternet(Context context) {

        ConnectivityManager conManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if(networkInfo != null){ 
             return networkInfo.isAvailable();
        }
        return false;
    }
	public static void processResponse(String result,Context context) {
		if(!StrUtil.isNotEmpty(result)){
			Toast.makeText(context, "程序异常", Toast.LENGTH_SHORT).show();
			return;
		}
		if (result.equals("-1") ||result.equals("-2") || result.equals("-6") || result.equals("-7") ) {
			Toast.makeText( context, "当前网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
			return;
		}
		if (result.equals("ACCOUNTIDISZERO")) {
			// accountId异常
			Toast.makeText( context, "账户异常", Toast.LENGTH_SHORT).show();
			return;
		} else if (result.equals("BALANCENOTENOUGH")) {
			// 余额不足
			Toast.makeText( context, "余额不足", Toast.LENGTH_SHORT).show();
			return;
		} else if (result.equals("INPUTISCONTACTINVALID")) {
			// 联系人手机号或姓名有误
			Toast.makeText( context, "联系人手机号或姓名有误", Toast.LENGTH_SHORT).show();
			return;
		} else if (result.equals("ACCOUNTSMSINFOISNULL")) {
			// 短信权限
			Toast.makeText( context, "您还没有邀请权限", Toast.LENGTH_SHORT).show();
			return;
		} else {
			// 发送成功
			Toast.makeText( context, "邀请完成", Toast.LENGTH_SHORT).show();
			return;
		}
	}
	public static String  getResponseBody(HttpResponse reponse) {
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = reponse.getEntity().getContent();
            Header header = reponse.getFirstHeader("Content-Encoding");
            String encoding = header == null ? null : header.getValue();
            if (encoding != null && encoding.toLowerCase().indexOf("gzip") > -1) {
                is = new GZIPInputStream(is);
            }
            return toString(reponse.getEntity(),is,null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
        	if(is!=null){
        		try {
					is.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
        	}
        }
        return new String(baos.toByteArray());
    } 
	 public static String toString(
	            final HttpEntity entity,InputStream instream, final String defaultCharset) throws IOException, ParseException {
	        if (entity == null) {
	            throw new IllegalArgumentException("HTTP entity may not be null");
	        } 
	        if (instream == null) {
	            return "";
	        }
	        if (entity.getContentLength() > Integer.MAX_VALUE) {
	            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
	        }
	        int i = (int)entity.getContentLength();
	        if (i < 0) {
	            i = 4096;
	        }
	        String    
	            charset = "GBK";
	        Reader reader = new InputStreamReader(instream, charset);
	        CharArrayBuffer buffer = new CharArrayBuffer(i); 
	        try {
	            char[] tmp = new char[1024];
	            int l;
	            while((l = reader.read(tmp)) != -1) {
	                buffer.append(tmp, 0, l);
	            }
	        } finally {
	            reader.close();
	        }
	        return buffer.toString();
	    }
	 /**
	  * 记录
	  * @param url
	  * @param creatTime
	  * @param size
	  */
	 public static void writeInSDCard(String url,long creatTime,long size){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date = new Date(creatTime);
			String createTime = dateFormat.format(date);
			String siez_ = StrUtil.formatTraffic(size);
		    String record  = url+" "+ createTime +" "+siez_+"\n";
		    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
		    	try {
			    	String filename = "httpif(debug)Log.txt";
			    	File sdfile = Environment.getExternalStorageDirectory();
			    	File newFile = new File(sdfile.getAbsolutePath()+File.separator+"tixa"+File.separator+filename);
		    		if(!newFile.exists()){
			    		newFile.createNewFile();
			    	}
		    		FileOutputStream fos = new FileOutputStream(newFile,true);
		    		fos.flush();
		    		fos.write(record.getBytes());
		    		fos.close();
				} catch (Exception e) {
				}
		    }
	 }
	 /**
	  * 解析HTTP请求异常
	  * @param context
	  * @param e
	  */
	 public static void handlerHttpMsg (Context context , LXHTTPException e) {
		  if (e == null ) {
			  return ; 
		  }
		  if ( e.getStatusCode() == NET_DISCONNECT ) {
			  Toast.makeText(context, "无法连接到服务器", Toast.LENGTH_SHORT).show();
		  } else if (e.getStatusCode() == NET_ERROR ) {
			  Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
		  } else {
			  Toast.makeText(context, "网络异常,代码："+  e.getStatusCode() , Toast.LENGTH_SHORT).show();
		  }
	 }
	 public static void progressInviteMessage(Context context ,String result) {
			try {
				JSONArray jar = new JSONArray(result);
				if(jar.length()<=0){
					Toast.makeText(context, "服务器返回格式异常", Toast.LENGTH_SHORT).show();
				}else{
					JSONObject json = jar.getJSONObject(0);
					int status = json.optInt("s", 0);
					if(status == -1){
						Toast.makeText(context, "邀请失败", Toast.LENGTH_SHORT).show();
					}else if(status == 1){
						Toast.makeText(context, "对方已经是你的好友", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "成功发出邀请", Toast.LENGTH_SHORT).show();
					}
				}
			} catch (JSONException e) {
				Toast.makeText(context, "服务器返回格式异常", Toast.LENGTH_SHORT).show();
			}
		}
	 /**
	  * 获取一个httpClient对象
	  * @return
	  */
	 public static HttpClient getHttpClient(){
		     BasicHttpParams httpParams = new BasicHttpParams();
		     HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		     HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		     HttpClient client = new DefaultHttpClient(httpParams);
		     return client;
		 
		 }

	 /**
		 * @param args
		 * 客户端HTTP工具类
		 * autor:yy
		 */
		public static String doPost(Context context,String url,List<NameValuePair>  params){
			Log.d(TAG, "post url = "+url);
			for (int i = 0; i < params.size(); i++) {
				NameValuePair np = params.get(i);
				Log.d(TAG, "post url params = "+np.getName() + "------>"+np.getValue());
			}
			long a = System.currentTimeMillis();
			String result="";
			if(url == null || "".equals(url)){
				return result;
			}
			if(!isConnectInternet(context)){
				return "-2";
			}
			//新建HttpClient对象  
			HttpClient client  = getHttpClient();
			HttpPost request = new HttpPost(url);
			request.setHeader("Accept-Encoding","gzip");
			
			try { 
				request.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
				Log.i(TAG,"1111211");
				//尝试压缩
				//request.addHeader("accept-encoding", "gzip,deflate") ;
				HttpResponse response = client.execute(request);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String isZip = getResponseBody(response);
					if(StrUtil.isNotEmpty(isZip)){
						Log.v(TAG, "post httpGzip str = "+isZip.trim() );
						Log.v(TAG, "post isZip size is "+isZip.length()/1024+"K");
//	 					writeInSDCard(url, a,isZip.length());
						return isZip.trim();
					}
					result = EntityUtils.toString(response.getEntity());
					Log.v(TAG, "post size is "+result.length()/1024 +"K");
					if(result!=null) result = result.trim();
				}else{
					Log.d(TAG, "post StatusCode = "+response.getStatusLine().getStatusCode());
					result="-1";
				}
			} catch (UnsupportedEncodingException e) {
				result="-7";
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				result="-7";
				e.printStackTrace();
			} catch (ParseException e) {
				result="-7";
				e.printStackTrace();
			} catch (IOException e) {
				result="-7";
				e.printStackTrace();
			} catch (Exception e ) {
				result = "-7";
				e.printStackTrace();
			}
			long b = System.currentTimeMillis();
			Log.v(TAG, "cost"+(b-a));
			Log.v(TAG, "result="+result);
//			writeInSDCard(url, a,result.length());
			return result;
		}
		public static String doGet(String url ){
			return doGet(url , null);
		}
		public static String doGet(String url ,Context context ){
			Log.d(TAG, "get url = "+url);
			long a = System.currentTimeMillis();
			String result="";
			if(context!=null && !isConnectInternet(context)){
				return "-2";
			}
			if(url == null || "".equals(url)){
				return result;
			}
			HttpClient client  = getHttpClient();
			HttpGet request = new HttpGet(url);
			request.setHeader("Accept-Encoding","gzip");
			try {
				HttpResponse response =	client.execute(request);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String isZip = getResponseBody(response);
					if(StrUtil.isNotEmpty(isZip)){
						Log.v(TAG, "get httpGzip str = "+isZip.trim() );
						Log.v(TAG, "get isZip size is "+isZip.length()/1024 +"K");
//						writeInSDCard(url, a,isZip.length());
						return isZip.trim();
					}
					result = EntityUtils.toString(response.getEntity()).trim();
					Log.v(TAG, "get size is "+result.length()/1024 +"K");
				}else{
					Log.d(TAG, "get StatusCode = "+response.getStatusLine().getStatusCode());
					result="-1";
				}
			} catch (ClientProtocolException e) {
				//e.printStackTrace();
				return "-6";
			} catch (IOException e) {
				//e.printStackTrace();
				return "-7";

			}catch (Exception e) {
				return "-7";
			}
			long b = System.currentTimeMillis();
			Log.v(TAG, "cost"+(b-a));
			Log.v(TAG, "result="+result);
//			writeInSDCard(url, a,result.length());
			return result;
		}
}
