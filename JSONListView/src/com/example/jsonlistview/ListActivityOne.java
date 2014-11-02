package com.example.jsonlistview;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 

public class ListActivityOne extends Activity {
       private static final String TAG_ID = "id";
       private static final String TAG_CONTENT = "content";
       private static final String TAG_DETAILS = "details";
       private String url;
       JSONObject jsonObj;
       JSONArray allDetails = null;
       String list_id, list_content;
       ArrayList<HashMap<String,String>> detailsList;
       String encodeUrl,amp;
       String ampSymbol = "&";
       @Override
       protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_list);
              ListView lv =(ListView) findViewById(R.id.listview);
             
              Log.d("jsonlog", "inside ListActivity");
              ArrayList<HashMap<String, String>> arl = (ArrayList<HashMap<String,String>>)getIntent().getSerializableExtra("objlist");
              String[] identities=new String[arl.size()];
              String[] contents = new String[arl.size()];
             
              for(int i=0;i<arl.size();i++){
                     HashMap<String, String> map = arl.get(i);
                     identities[i]=map.get(TAG_ID);
                     contents[i]=map.get(TAG_CONTENT);
              }

              Log.d("jsonlog", "before setting the adapter");
              CustomAdapter adapter = new CustomAdapter(this,identities,contents);
          lv.setAdapter(adapter);
       }
             
              class CustomAdapter extends ArrayAdapter<String>{
                    
                     Context context;
                     String [] identities;
                     String[] contents;
                     OnClickListener mEditButtonClickListener;
                    
                     public CustomAdapter(Context c, String[] identities, String[] contents) {
                           // TODO Auto-generated constructor stub
                           super(c, R.layout.list_item, R.id.textViewId, identities);
                          
                           this.context = c;
                           this.identities = identities;
                           this.contents = contents;
                     }
                    
                     @Override
                     public View getView(int position, View convertView, ViewGroup parent) {
                           // TODO Auto-generated method stub
                          
                           LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                           View row = inflater.inflate(R.layout.list_item, parent, false);
                           TextView ids = (TextView) row.findViewById(R.id.textViewId);
                           TextView con = (TextView)row.findViewById(R.id.textViewContent);
                           Button edit= (Button)row.findViewById(R.id.buttonEdit);
                          
                           edit.setOnClickListener(mEditButtonClickListener); 
                           ids.setText(identities[position]);
                           con.setText(contents[position]);
                          
                 
                          
                         mEditButtonClickListener = new OnClickListener() {
                                 
                                 
								
								@Override
                                  public void onClick(View v) {
                                         // TODO Auto-generated method stub
                                         View parentRow = (View)v.getParent();
                                         ListView listView = (ListView)parentRow.getParent();
                                         final int pos = listView.getPositionForView(parentRow);
                                         final String oid = identities[pos];
                                         final String ocontent = contents[pos];
                                  
                                        
                                         final Dialog dialog = new Dialog(context);
                                         dialog.setContentView(R.layout.get_name_dialog);
                                         dialog.setTitle("Enter a new name!!");
                                        
                                         Button dialogButton = (Button)dialog.findViewById(R.id.buttonOk);
                                         final EditText name = (EditText)dialog.findViewById(R.id.editTextNewName);
                                        
                                         dialogButton.setOnClickListener(new OnClickListener() {
                                               
                                                @Override
                                                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                                                public void onClick(View v) {
                                                       // TODO Auto-generated method stub
                                                	
                                                	Log.d("jsonlog", "on click of dialog button");
                                                       String newName = name.getText().toString();
                                            
                                                       
                                                      try {
														  encodeUrl = URLEncoder.encode(oid, "UTF-8");
														  amp = URLEncoder.encode(ampSymbol, "UTF-8");
													} catch (UnsupportedEncodingException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
                                                       
                                                       url = "http://10.0.2.2:8080/editgreeting?id="+encodeUrl+ampSymbol+"content="+newName;
                                                       detailsList = new ArrayList<HashMap<String,String>>();
                                                       AsyncTask<Void,Void,Void> myTask = new getEditJson();
                                                       if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                                                    	    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    	else
                                                    	    myTask.execute();
                                                      
                                                       dialog.cancel();
                                                       
                                                       Intent intent = new Intent(ListActivityOne.this, MainActivity.class);
                                                       intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                       startActivity(intent);
                                                       
                                                       
                                                }
                                                
                                         
                                                class getEditJson extends AsyncTask<Void, Void, Void>{

                                					@Override
                                					protected Void doInBackground(Void... params) {
                                						// TODO Auto-generated method stub
                                						Log.d("jsonlog", "start of do in background of get edit json");
                                						ServiceHandler shan = new ServiceHandler();
                                						Log.d("jsonlog",url);
                                						String jsonStrEdited = shan.makeServiceCall(url, shan.POST);
                                						try {
                                							 jsonObj = new JSONObject(jsonStrEdited);
                                							 Log.d("jsonlog",jsonStrEdited);
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
                                								// TODO Auto-generated catch block
                                								e.printStackTrace();
                                							}
                                							return null;
                                					}
                                           	 
                                            }  
                                         });
                                         dialog.show();
                                         
                                  }
                                
                           };
                           
                           return row;
                     }
                     
                     
              }            
}
 