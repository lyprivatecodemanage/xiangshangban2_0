package com.xiangshangban.transit_service.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
public class HttpClientUtil {
	/**
	* httpclient 模拟post 发送json请求  (ContentType=application/json)
	* @param sendurl
	* @param data
	* @return
	*/
	public static String sendRequet(String sendurl, Object data) {
	return sendRequet(sendurl, data, ContentType.APPLICATION_JSON, new HashMap<String,String>());
	}
	/**
	* httpclient 模拟post 发送json请求
	* @param sendurl
	* @param data
	* @param contentType 
	* @param headers 头信息，map集合
	* @return
	*/
	public static String sendRequet(String sendurl, Object data, ContentType contentType, Map<String,String> headers) {
		HttpPost post = new HttpPost(sendurl);
		StringEntity myEntity = new StringEntity(JSON.toJSONString(data,false), contentType);// 构造请求数据
		myEntity.setContentEncoding("utf-8");
		post.setEntity(myEntity);// 设置请求体
		
		Iterator iter = headers.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    post.addHeader((String)entry.getKey(), (String)entry.getValue());
		}
		
		String responseContent = null; // 响应内容
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
		    response = client.execute(post);
		    if (response.getStatusLine().getStatusCode() == 200) {
		        HttpEntity entity = response.getEntity();
		        responseContent = EntityUtils.toString(entity, "UTF-8");
		    }
		} catch (ClientProtocolException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (response != null)
		            response.close();
		
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (client != null)
		                client.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		return responseContent;
	}
	/**
	 * 根据模块名获取模块根路径url
	 * @param modeCode
	 * @return
	 */
	public static String getModeUrl(String modeCode){
		try {
			return PropertiesUtils.pathUrl(modeCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String[] getIncludeMode(){
		try {
			return PropertiesUtils.pathUrl("mode-include").split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
