package com.topcoder.innovate;

import java.util.Locale;

import com.topcoder.innovate.model.Speaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerDetailActivity extends Activity{
	
	private Button homeButton;
	private Button infoButton;
	private ImageView speakerImage;
	private TextView speakerName;
	private TextView speakerTitle;
	private TextView speakerDetails;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speaker_detail_layout);
		
		infoButton = (Button)findViewById(R.id.detail_info_button);
		infoButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SpeakerDetailActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		
		homeButton = (Button)findViewById(R.id.detail_home_button);
		homeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SpeakerDetailActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		speakerImage = (ImageView)findViewById(R.id.detail_speaker_image);
		speakerName = (TextView)findViewById(R.id.detail_speaker_name);
		speakerTitle = (TextView)findViewById(R.id.detail_speaker_title);
		speakerDetails = (TextView)findViewById(R.id.speaker_detail);
		backButton = (Button)findViewById(R.id.detail_back_button);
		
		Speaker speaker = (Speaker)getIntent().getExtras().get("speaker");
		
		speakerName.setText(speaker.getName());
		speakerTitle.setText(speaker.getTitle());
		speakerDetails.setText(speaker.getDetails());
		
		int indentify = getResources().getIdentifier(getPictureName(speaker.getPicture()), "drawable", getPackageName());
		if (indentify > 0){
			speakerImage.setImageResource(indentify);
		}
		else {
			Log.e("SpeakerDetailActivity", "can't found speaker image indentify");
		}
		
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SpeakerDetailActivity.this, SpeakerListActivity.class);
				startActivity(intent);
				finish();
			}
		});
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
			Intent intent = new Intent(SpeakerDetailActivity.this, 
					SpeakerListActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
