package com.example.jsonlistview;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ServiceHandler {

	static String response = null;
	public final static int GET  = 1;
	public final static int POST =2;
	
	public ServiceHandler(){
		
	}
	
	public String makeServiceCall(String url, int method){
		return this.makeServiceCall(url, method, null);
	}
	
	public String makeServiceCall(String url,int method, List<NameValuePair> params){
		
	
			
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpEntity httpEntity = null;
		HttpResponse httpResponse = null;
		
		if(method == POST){
			HttpPost httpPost = new HttpPost(url);
			
			if(params!=null){
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		else if(method == GET){
			if(params!=null){
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?"+paramString;
			}
			HttpGet httpGet = new HttpGet(url);
			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		httpEntity = httpResponse.getEntity();
		try {
			response = EntityUtils.toString(httpEntity);
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return response;
	}

}
