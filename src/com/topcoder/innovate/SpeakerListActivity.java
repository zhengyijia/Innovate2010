package com.topcoder.innovate;

import java.util.List;
import java.util.Locale;

import com.topcoder.innovate.model.Speaker;
import com.topcoder.innovate.util.DataRetriever;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SpeakerListActivity extends Activity {
	private Button infoButton;
	private Button homeButton;
	private ListView listView;
	private List<Speaker> speakerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speaker_list_layout);
		
		infoButton = (Button)findViewById(R.id.speaker_info_button);
		infoButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SpeakerListActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		
		homeButton = (Button)findViewById(R.id.speaker_home_button);
		homeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SpeakerListActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		DataRetriever dataRetriever = new DataRetriever();
		speakerList = dataRetriever.retrieveAllSpeakers(this);
		SpeakerAdapter speakerAdapter = new SpeakerAdapter(SpeakerListActivity.this, R.layout.speaker_item, speakerList);
		listView = (ListView)findViewById(R.id.speaker_list);
		listView.setAdapter(speakerAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Intent intent = new Intent(SpeakerListActivity.this,
						SpeakerDetailActivity.class);
				intent.putExtra("speaker", speakerList.get(position));
				startActivity(intent);
				finish();
			}
		});
	}
	
	public class SpeakerAdapter extends ArrayAdapter<Speaker>{
		private int resourceId;
		
		public SpeakerAdapter(Context context, int textViewResourceId, List<Speaker> objects){
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			Speaker speaker = getItem(position);
			View view;
			ViewHolder viewHolder;
			if(convertView == null){
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
				viewHolder = new ViewHolder();
				viewHolder.speakerImage = (ImageView)view.findViewById(R.id.speaker_image);
				viewHolder.speakerName = (TextView)view.findViewById(R.id.speaker_name);
				viewHolder.speakerTitle = (TextView)view.findViewById(R.id.speaker_title);
				view.setTag(viewHolder);
			}else{
				view = convertView;
				viewHolder = (ViewHolder)view.getTag();
			}
			int indentify = getResources().getIdentifier(getPictureName(speaker.getPicture()), "drawable", getPackageName());
			if (indentify > 0){
				viewHolder.speakerImage.setImageResource(indentify);
			}
			else {
				Log.e("SpeakerListActivity", "can't found speaker image indentify");
			}
			viewHolder.speakerName.setText(speaker.getName());
			viewHolder.speakerTitle.setText(speaker.getTitle());
			return view;
		}
		
		class ViewHolder{
			ImageView speakerImage;
			TextView speakerName;
			TextView speakerTitle;
		}
	}
	
	private String getPictureName(String str) {
		// TODO Auto-generated method stub
		str.trim();
		String[] temp = str.split("/");
		String wholeName = temp[temp.length - 1];
		String ret = wholeName.split("[.]")[0];
		ret = ret.toLowerCase(Locale.getDefault());
		ret.replace('-', '_');
		
		if (ret.charAt(0) >= '0' && ret.charAt(0) <= '9'){
			ret = "x" + ret;
		}
		
		return ret;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(SpeakerListActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}