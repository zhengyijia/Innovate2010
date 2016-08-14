package com.topcoder.innovate;

import java.util.Iterator;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.topcoder.innovate.model.Position;
import com.topcoder.innovate.util.PosRetriever;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class MapActivity extends Activity{
	private MapView mapView;
	private BaiduMap baiduMap;
	private List<Position> positionList;
	private BitmapDescriptor icon;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map_layout);
		mapView = (MapView)findViewById(R.id.map_view);
		baiduMap = mapView.getMap();
		LatLng ll = new LatLng(22.256993, 113.540548);
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
		baiduMap.animateMapStatus(update);
		update = MapStatusUpdateFactory.zoomTo(14f);
		baiduMap.animateMapStatus(update);
		
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){
			@Override
			public boolean onMarkerClick(Marker marker){
				String name = marker.getExtraInfo().getString("name");
				String address = marker.getExtraInfo().getString("address");
				Toast.makeText(MapActivity.this, name + "." + address, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		positionList = new PosRetriever().retrieveAllPositions(this);
		icon = BitmapDescriptorFactory.fromResource(R.drawable.u);
		for(Iterator<Position> iter = positionList.iterator(); iter.hasNext(); ){
			Position temp = iter.next();
			LatLng position = new LatLng(temp.getLatitude(), temp.getLongitude());
			Bundle extraMsg = new Bundle();
			extraMsg.putString("name", temp.getName());
			extraMsg.putString("address", temp.getAddress());
			OverlayOptions option = 
					new MarkerOptions().position(position).icon(icon).extraInfo(extraMsg);
			baiduMap.addOverlay(option);
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		mapView.onResume();
	}
}
