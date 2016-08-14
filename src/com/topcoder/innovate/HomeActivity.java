package com.topcoder.innovate;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	private Button infoButton;
	private ImageView speakersImageView;
	private ImageView mapImageView;
	private myHandler handler;
	private ProgressDialog progressDialog = null;
	private int size;
	private int readLen;
	private int hasReadLen;
	private byte buffer[] = new byte[1024];
	private String readStr;
	private boolean succeed;
	private AssetManager assetManager;
	private static boolean doneDownload = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_layout);

		infoButton = (Button) findViewById(R.id.info_button);
		infoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						WebViewActivity.class);
				startActivity(intent);
			}
		});
		speakersImageView = (ImageView) findViewById(R.id.imageView1_3);
		speakersImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						SpeakerListActivity.class);
				startActivity(intent);
				finish();
			}
		});
		mapImageView = (ImageView) findViewById(R.id.imageView3_2);
		mapImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, MapActivity.class);
				startActivity(intent);
			}
		});

		if (!doneDownload) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("Loading ... ");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setIcon(android.R.drawable.ic_menu_more);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			String url = getResources().getString(R.string.feeds_speakers);
			download(url, "speakers.txt");
		}
	}

	public boolean download(final String url_s, final String filename) {
		succeed = true;

		new Thread(new Runnable() {

			@Override
			public void run() {
				size = 1;
				readLen = hasReadLen = 0;
				readStr = new String();

				try {

					URL url = new URL(url_s);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					size = connection.getContentLength();
					InputStream input = connection.getInputStream();
					OutputStream outputStream = getApplication()
							.openFileOutput(filename,
									getApplicationContext().MODE_APPEND);

					while ((readLen = input.read(buffer)) != -1) {
						outputStream.write(buffer);
						hasReadLen += readLen;
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
						Thread.sleep(100);
					}

					input.close();
				} catch (Exception e) {
					succeed = false;
					e.printStackTrace();
				}
			}

		}).start();

		return succeed;
	}

	class myHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				int progress = hasReadLen * 100 / size;
				progressDialog.setProgress(progress);
				if (hasReadLen >= size) {
					progressDialog.dismiss();
					doneDownload = true;
				}
			}

			super.handleMessage(msg);
		}

	}

}
