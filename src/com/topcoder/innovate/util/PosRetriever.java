package com.topcoder.innovate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topcoder.innovate.R;
import com.topcoder.innovate.model.Position;

import android.app.Activity;
import android.util.Log;

public class PosRetriever {
	public List<Position> retrieveAllPositions(Activity activity){
		List<Position> positionArrayList = new ArrayList<Position>();
//		String url = activity.getResources().getString(R.string.feeds_positions);
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
		
		byte buffer[] = new byte[1024];
		String s = new String();
		
		try{
			InputStream input = activity.getResources().openRawResource(R.raw.my_bling);
			StringBuffer outdata = new StringBuffer();
			BufferedReader br = new BufferedReader(  
					//由于我自己加的位置信息文件中有中文，所以使用GB2312编码
                    new InputStreamReader(input,"GB2312"));
			//int len = 0;
			String data;
			
			while((data = br.readLine()) != null){
				outdata.append(data);
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
				Position position = new Position();
				position.setName(jsonObjectField.getString("name"));
				position.setAddress(jsonObjectField.getString("address"));
				position.setLatitude(jsonObjectField.getDouble("latitude"));
				position.setLongitude(jsonObjectField.getDouble("longitude"));
				positionArrayList.add(position);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return positionArrayList;
	}
}
