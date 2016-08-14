package com.topcoder.innovate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.topcoder.innovate.R;
import com.topcoder.innovate.model.Speaker;

public class DataRetriever {
	public List<Speaker> retrieveAllSpeakers(Activity activity){
		List<Speaker> speakerArrayList = new ArrayList<Speaker>();
//		String url = activity.getResources().getString(R.string.feeds_positions);
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
		
		byte buffer[] = new byte[1024];
		String s = new String();
		try{
			InputStream input = activity.getResources().openRawResource(R.raw.speakers);
			StringBuffer outdata = new StringBuffer();
			int len = 0;
			
			while((len = input.read(buffer)) != -1){
				outdata.append(new String(buffer, 0, len));
			}
			s = outdata.toString();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
//			HttpResponse response = httpClient.execute(httpGet);
//			HttpEntity httpEntity = response.getEntity();
//			String s = EntityUtils.toString(httpEntity);
			JSONArray jsonArray = new JSONArray(s);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObjectField = jsonObject.getJSONObject("fields");
				Speaker speaker = new Speaker();
				speaker.setName(jsonObjectField.getString("name"));
				speaker.setPicture(jsonObjectField.getString("picture"));
				speaker.setDetails(jsonObjectField.getString("details"));
				speaker.setTitle(jsonObjectField.getString("title"));
				
				List<String> sessions = new ArrayList<String>();
				JSONArray jsonArraySessions = jsonObjectField.getJSONArray("sessions");
				for(int j = 0; j < jsonArraySessions.length(); j++){
					sessions.add(jsonArraySessions.getString(j));
				}
				
				speaker.setSessionIds(sessions);
				speakerArrayList.add(speaker);
			}
		}catch(JSONException e1){
			e1.printStackTrace();
		}
		
		return speakerArrayList;
	}
	
	public boolean networkCheck(Context context){
		if(context != null){
			ConnectivityManager connectivityManager = 
					(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if(networkInfo != null){
				return networkInfo.isAvailable();
			}
		}
		return false;
	}
}