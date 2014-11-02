package com.example.jsonlistview;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button submit,showListView;
	EditText username;
	String namestring,id,content;
	String url;
	private static final String TAG_ID = "id";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_DETAILS = "details";
	JSONObject jsonObj;
	JSONArray allDetails = null;
	ArrayList<HashMap<String,String>> detailsList;
	String list_id, list_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		username = (EditText)findViewById(R.id.editTextUsername);
		submit = (Button)findViewById(R.id.buttonSubmit);
		showListView = (Button)findViewById(R.id.buttonListView);
		
       submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("jsonlog", "on click");
				// TODO Auto-generated method stub
				 namestring =username.getText().toString();
				 
				 url = "http://10.0.2.2:8080/greeting?name="+namestring;
				 new getJson().execute();
			
			}
		});
	
       
        showListView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("jsonlog", "on click of show list view");
				
				 detailsList = new ArrayList<HashMap<String,String>>();
				url = "http://10.0.2.2:8080/allgreetings";
				
				new getjsonlist().execute();
				
			}
		});
   }

	public class getJson extends AsyncTask<Void, Void, Void>{
	    
		@Override
		protected Void doInBackground(Void... params) {
			
			Log.d("jsonlog","start of do in backgroud");
			 
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(url, sh.GET);
			Log.d("jsonlog", jsonStr);
			
			try {
				 jsonObj = new JSONObject(jsonStr);
				 Log.d("jsonlog",jsonStr);
				  id = jsonObj.getString(TAG_ID);
				  Log.d("jsonlog",id);
			      content = jsonObj.getString(TAG_CONTENT);
			      Log.d("jsonlog", content);
			      
			     
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
			return null;
		}
		@Override
      protected void onPostExecute(Void result) {
          super.onPostExecute(result);
          Toast.makeText(MainActivity.this, "id is:"+id, Toast.LENGTH_LONG).show();
          Toast.makeText(MainActivity.this, "content is"+content, Toast.LENGTH_LONG).show();      
         
		}
		
	}
	
	public class getjsonlist extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			Log.d("jsonlog", "start of do in background of getjsonlist");
			
			
			ServiceHandler sh = new ServiceHandler();
			
			Log.d("jsonlog", "after getting service handler");
		
			String jsonStr = sh.makeServiceCall(url, sh.GET);
			
			Log.d("jsonlog", "after getting json string");
			
			try {
				Log.d("jsonlog", jsonStr);
				JSONObject jsonObj = new JSONObject(jsonStr);
				allDetails = jsonObj.getJSONArray(TAG_DETAILS);
				
				for(int i=0;i<allDetails.length();i++){
					JSONObject jobj = allDetails.getJSONObject(i);
					list_id = jobj.getString(TAG_ID);
					list_content = jobj.getString(TAG_CONTENT);
					
					Log.d("jsonlog", list_id);
					Log.d("jsonlog", list_content);
					
					HashMap<String, String> detail = new HashMap<String, String>();
					detail.put(TAG_ID, list_id);
					detail.put(TAG_CONTENT, list_content);
					Log.d("jsonlog", detail.get(TAG_ID).toString()+" in hashmap");
					Log.d("jsonlog", detail.get(TAG_CONTENT).toString()+" in hashmap");

					 
					detailsList.add(detail);
					Log.d("jsonlog", "after adding into detail list");
				}
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			return null;
		}
		protected  void  onPostExecute(Void result) {
			
			Log.d("jsonlog", "list view onPostExecute");
			Log.d("jsonlog", "inside post execute of json list");
			Intent intent = new Intent(MainActivity.this,ListActivityOne.class);
			Log.d("jsonlog", "after calling intent");
			intent.putExtra("objlist", detailsList);
			Log.d("jsonlog", "after calling detail list");
			startActivity(intent);
		
		}
		
	}
}

