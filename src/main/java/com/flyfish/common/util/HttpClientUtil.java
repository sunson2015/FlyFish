package  com.flyfish.common.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Http client请求工具类
 *
 */
public class HttpClientUtil {
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static  MultiThreadedHttpConnectionManager connectionManager;
	static{
		connectionManager =  new   MultiThreadedHttpConnectionManager();
	}

	
	public static String postJSON(String url, String jsonStr) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
	   try {
			HttpEntity entity = new StringEntity(jsonStr, "UTF-8");
			request.setHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(entity);
			CloseableHttpResponse response = null;
			
				response = client.execute(request);
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch (Exception e) {
			log.error("发送json格式的post请求失败", e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("关闭http client失败", e);
			}
		}
		return null;
	}
	
	/**
	 * POST单个内容
	 * @param url
	 * @param strContent
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String execPOSTMethodParamesForPhp(String url, String strContent)
			throws HttpException, IOException {
		StringBuilder reponseStr = new StringBuilder();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000); 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		if(null!=strContent&&!"".equals(strContent.trim())){
			NameValuePair[] data = new NameValuePair[1];
			data[0] = new NameValuePair("data",
					strContent);
			postMethod.setRequestBody(data);
		}	

		// 执行postMethod
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			log.info("request url" + url + "   Method failed: "
					+ postMethod.getStatusLine());
		}
		// 读取内容
		byte[] responseBody = postMethod.getResponseBody();
		reponseStr.append(new String(responseBody,"UTF-8"));
		postMethod.releaseConnection();
		return reponseStr.toString();
	}
	
	public static String sendGet(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error("发送get请求失败", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				client.close();
			} catch (IOException e) {
				log.error("关闭http client失败", e);
			}
		}
		return null;
	}
	
	
	/**
	 * POST多个参数 
	 * @param url
	 * @param parames
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static String execPOSTMethodMParames(String url,
			Map<String, String> parames) throws HttpException, IOException {
		StringBuilder reponseStr = new StringBuilder();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000); 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		// 填入各个表单域的值
		/*
		 * NameValuePair[] data = { new NameValuePair("id", "youUserName"), new
		 * NameValuePair("passwd", "yourPwd") };
		 */
		// 将表单的值放入postMethod中
		// 设置参数
		if (parames.size() > 0) {
			NameValuePair[] data = new NameValuePair[parames.size()];
			int parameLenth = 0;
			for (Map.Entry<String, String> entry : parames.entrySet()) {
				data[parameLenth] = new NameValuePair(entry.getKey(),
						entry.getValue());
				parameLenth +=1;
			}
			postMethod.setRequestBody(data);
		}
		// 执行postMethod
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			log.info("request url" + url + "   Method failed: "
					+ postMethod.getStatusLine());
		}
		// 读取内容
		byte[] responseBody = postMethod.getResponseBody();
		reponseStr.append(new String(responseBody,"UTF-8"));
		postMethod.releaseConnection();
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		/*
		 * if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode ==
		 * HttpStatus.SC_MOVED_TEMPORARILY) { // 从头中取出转向的地址 Header
		 * locationHeader = postMethod.getResponseHeader("location"); String
		 * location = null; if (locationHeader != null) { location =
		 * locationHeader.getValue();
		 * System.out.println("The page was redirected to:" + location); } else
		 * { System.err.println("Location field value is null."); } return; }
		 */
		return reponseStr.toString();
	}
	
	
	/**
	 * POST--Entity 
	 * @param url
	 * @param parames
	 * @return
	 * @throws Exception 
	 */
	public static String execPOSTMethodEntity(String url,
			String content) throws Exception {
//		StringBuilder reponseStr = new StringBuilder();
		// 构造HttpClient的实例

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new StringEntity(content, "UTF-8");
		request.setHeader("content-type", "application/json");
		request.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error("发送json格式的post请求失败", e);
			throw new Exception(e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("关闭http client失败", e);
			}
		}
		
		//return reponseStr.toString();
	}
	
	
	/**
	 * 返回以对象属性名-属性值为内容的HashMap
	 * @param target
	 * @return
	 */
	public static Map<String, Object> toParameterHashMap(Object target) throws Exception{
		Class<? extends Object> clazz = target.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Method method = null;
			String methodName = null;
			String fieldName = null;
			for (Field field : fields) {
				fieldName = field.getName();
				methodName = "get" + Character.toUpperCase(fieldName.charAt(0))
						+ fieldName.substring(1);
				method = clazz.getMethod(methodName);
				Object filedValue = method.invoke(target);
				if (filedValue != null) {
					map.put(fieldName, filedValue.toString());
				}
			}
			return map;
		}
		
		throw new IllegalArgumentException("目标对象没有可用的属性");
	}
	
	
	public static String execPOSTMethodParamesHera(String url, String str,String contentType)
			throws HttpException, IOException {
		StringBuilder reponseStr = new StringBuilder();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000); 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		Header header = new Header("content-type",contentType);
		postMethod.addRequestHeader(header);
		postMethod.setRequestBody(str);
		
		// 执行postMethod
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			log.info("Method failed: " + postMethod.getStatusLine());
			log.info(new String(postMethod.getResponseBody()));
			return null;
		}
		log.info("http status: " + statusCode);
		// 读取内容
		byte[] responseBody = postMethod.getResponseBody();
		reponseStr.append(new String(responseBody));
		postMethod.releaseConnection();
		return reponseStr.toString();
	}
	
	
	/**
	 * POST测试，如果，url地址响应200返回true，否则返回false
	 * 
	 * @param url
	 * @param parames
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static boolean execPostTest(String url, Integer connectionTimeout, Integer soTimeout){
		if (StringUtils.isBlank(url)) {
			return false;
		}
		if (connectionTimeout == null || connectionTimeout <= 0) {
			connectionTimeout = 1000;
		}
		if (soTimeout == null || soTimeout <= 0) {
			soTimeout = 1000;
		}
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		// 执行postMethod
		int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			log.error("****execPostTest****出现异常****url==>"+url);
			e.printStackTrace();
			return false;
		}
		if (statusCode == HttpStatus.SC_OK) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * POST测试，如果，url地址响应200返回true，否则返回false
	 * 
	 * @param url
	 * @param parames
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static boolean execPostTest(String url){
		return execPostTest(url, null, null);
	}
	
	
	
}
