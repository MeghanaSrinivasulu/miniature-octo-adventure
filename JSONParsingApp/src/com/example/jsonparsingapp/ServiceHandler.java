package com.example.jsonparsingapp;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class ServiceHandler{
	public static final int GET =1;
	static String response = null;
	
	public ServiceHandler(){
		
	}
	
	public String makeServiceCall(String url,int method){
		Log.d("jsonlog", "start of makeServiceCall");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpEntity httpEntity = null;
		HttpResponse httpResponse = null;
		
		/*if(params!=null){
			String paramString = URLEncodedUtils.format(params, "UTF-8");
			url = url +"?name="+paramString;
		}*/
		
		Log.d("jsonlog", "before getting the url");
		HttpGet httpGet = new HttpGet(url);
		Log.d("jsonlog", "after getting the url");
		Log.d("jsonlog", httpGet.toString());
		
		
		try {
			Log.d("jsonlog", "before executing httpget");
			httpResponse = httpClient.execute(httpGet);
			Log.d("jsonlog", "after executing httpget");
			
			Log.d("jsonlog", "before getting entity");
			httpEntity = httpResponse.getEntity();
			Log.d("jsonlog", "after getting entity");
			
			Log.d("jsonlog", "before getting response");
			response = EntityUtils.toString(httpEntity);
			Log.d("jsonlog", "after getting response");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	
	
}