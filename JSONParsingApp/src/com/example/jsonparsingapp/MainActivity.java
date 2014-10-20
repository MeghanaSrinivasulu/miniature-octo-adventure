package com.example.jsonparsingapp;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
Button submit;
EditText username;
String namestring;
private static final String TAG_ID = "id";
private static final String TAG_CONTENT = "content";
private String url = "http://10.0.2.2:8080/greeting";
ProgressDialog pDialog;
JSONObject jsonObj;
int id;
String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		submit = (Button)findViewById(R.id.buttonSubmit);
		username = (EditText)findViewById(R.id.editTextName);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("jsonlog", "on click");
				// TODO Auto-generated method stub
				 namestring =username.getText().toString();
				 url = "http://10.0.2.2:8080/greeting";
				 
				 new getJson().execute();
				 
				 Toast.makeText(MainActivity.this, "id is:"+id, Toast.LENGTH_LONG).show();
		            Toast.makeText(MainActivity.this, "content is"+content, Toast.LENGTH_LONG).show();
			}
		});
		
	}

	public class getJson extends AsyncTask<Void, Void, Void>{
     
		
		  /*@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	 
	        }*/
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.d("jsonlog","start of do in backgroud");
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(url, sh.GET);
			Log.d("jsonlog", jsonStr);
			
			try {
				 jsonObj = new JSONObject(jsonStr);
				  id = jsonObj.getInt(TAG_ID);
				  
			      content = jsonObj.getString(TAG_CONTENT);
			      Log.d("jsonlog", content);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		/*@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
           
		}*/
		
	}
	
	
}
	
