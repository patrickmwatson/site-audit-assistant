package com.lifehackinnovations.siteaudit;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


import android.location.Location;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Math;

public class Getscalefromgooglemaps extends FragmentActivity {
	int count = 0;
	double lat_a = 0;
	double lng_a = 0;
	float dist;
	double distance = 0;
	Location locationA = new Location("point A");
	private GoogleMap mMap;

	int bitmapreductionsize=4;
	float IRR=bitmapreductionsize;
	float xm, ym, hm, xs, ys, hs, xi, yi, hi, xo, yo, ho, PMX, PMY;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemaplayout);
		
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		xs=width;
		ys=height;
		
		
		String InputPath = Tabs1.floorplanstrings.get(FloorPlanActivity.floorplannumber);
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(InputPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, null, bitmapOptions);
		int imageWidth = bitmapOptions.outWidth;
		int imageHeight = bitmapOptions.outHeight;
		xo=imageWidth;
		yo=imageHeight;
		try {
			inputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("originalimagesize x,y",imageWidth+" "+imageHeight);
		
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = bitmapreductionsize;
		final Bitmap originalbitmap = BitmapFactory.decodeFile(InputPath,o);
		Log.d("reducedimage x, y", originalbitmap.getWidth()+" "+originalbitmap.getHeight());
		xi=originalbitmap.getWidth();
		yi=originalbitmap.getHeight();
		
		final ImageView floorplanimage=(ImageView)findViewById(R.id.floorplanimage);
		floorplanimage.setImageBitmap(originalbitmap);
		floorplanimage.setAlpha(100);
		
		try{
			floorplanimage.setFitsSystemWindows(true);
		}catch(NoSuchMethodError e){
			
		}
		floorplanimage.setScaleType(ScaleType.FIT_CENTER);
		
		final RelativeLayout rl=(RelativeLayout)findViewById(R.id.relativelayout);
		
		
		
		Button getscalebutton=(Button)findViewById(R.id.getscalebutton);
		getscalebutton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int left=floorplanimage.getLeft();
				int top=floorplanimage.getTop();
				int right=floorplanimage.getRight();
				int bottom=floorplanimage.getBottom();
				
				Log.d("ltrb",left+" "+top+" "+right+" "+bottom);
				
				Point topleft=new Point(left, top);
				Point topright=new Point(right, top);
				Point bottomright=new Point(right, bottom);
				Point bottomleft=new Point(left, bottom);
				
				LatLng TOPLEFT=mMap.getProjection().fromScreenLocation(topleft);
				LatLng TOPRIGHT=mMap.getProjection().fromScreenLocation(topright);
				LatLng BOTTOMRIGHT=mMap.getProjection().fromScreenLocation(bottomright);
				LatLng BOTTOMLEFT=mMap.getProjection().fromScreenLocation(bottomleft);
				
			
				Log.d("Lat Long",TOPLEFT.latitude+" "+TOPLEFT.longitude);
				Log.d("Lat Long",TOPRIGHT.latitude+" "+TOPRIGHT.longitude);
				Log.d("Lat Long",BOTTOMRIGHT.latitude+" "+BOTTOMRIGHT.longitude);
				Log.d("Lat Long",BOTTOMLEFT.latitude+" "+BOTTOMLEFT.longitude);
				
				//float scaledfloorplanwidth=distancebetweentwopoints(TOPLEFT,TOPRIGHT);
				//float scaledfloorplanheight=distancebetweentwopoints(TOPLEFT,BOTTOMLEFT);
				float[] widthresults = new float[1];
				float[] heightresults = new float[1];
				Location.distanceBetween(TOPLEFT.latitude, TOPLEFT.longitude, TOPRIGHT.latitude, TOPRIGHT.longitude, widthresults);
				Location.distanceBetween(TOPLEFT.latitude, TOPLEFT.longitude, BOTTOMLEFT.latitude, BOTTOMLEFT.longitude, heightresults);
				
				
				Log.d("width, height", widthresults[0]+" "+heightresults[0]);
				
				
				float scaledfloorplanwidth=widthresults[0];
				float scaledfloorplanheight=heightresults[0];
				
				Log.d("scaledfloorplanwidth scaledfloorplanheight",scaledfloorplanwidth+" "+scaledfloorplanheight);
				Log.d("ratioinmeters",String.valueOf(scaledfloorplanheight/scaledfloorplanwidth));
				Log.d("rationinpixels",String.valueOf(yo/xo));
				
				Log.d("x,y",scaledfloorplanwidth/xo+" "+scaledfloorplanheight/yo);
				float metersperpixel=scaledfloorplanwidth/xo;
				FloorPlanView.PLACENEWITEM=true;
				FloorPlanActivity.MODE = FloorPlanActivity.MODE_METERSTOPIXEL;
				FloorPlanActivity.topleftcornerlatitude=(float) TOPLEFT.latitude;
				FloorPlanActivity.topleftcornerlongitude=(float) TOPLEFT.longitude;
				FloorPlanActivity.metersperpixel=metersperpixel;
				Log.d("bearing",String.valueOf(mMap.getCameraPosition().bearing));
				FloorPlanActivity.bearing=mMap.getCameraPosition().bearing;
				finish();
				
				/*
				Intent floorplanintent=new Intent();
				floorplanintent.setClassName(MainActivity.class
						.getPackage().getName(), MainActivity.class
						.getPackage().getName()
						+ "."
						+ "FloorPlanActivity");
				floorplanintent.putExtra("metersperpixel", metersperpixel);
				startActivity(floorplanintent);
				*/
				
			}});
		
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		mMap.setMyLocationEnabled(true);
		mMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng field) {
				// TODO Auto-generated method stub

				// mMap.addMarker(new
				// MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
				float bearing=mMap.getCameraPosition().bearing;
				Log.d("bearing",String.valueOf(bearing));
				
				
				double latb=field.latitude;
				double lngb=field.longitude;
				
				try {
					//dist=(float)distancebetweentwopoints(lng_a, lat_a, lngb, latb);

				} catch (NullPointerException e) {
				}

				Log.d("coordinate=", lngb + " " + latb);
				Log.d("distance=", String.valueOf(dist));
				
				  lat_a=field.latitude; lng_a=field.longitude;
				  
				  locationA.setLatitude(field.latitude);
				  locationA.setLongitude(field.longitude); count++;
				 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.getscalefromgooglemain, menu);
		return true;
	}
	public float distancebetweentwopoints(LatLng A, LatLng B){
			
				double LongA=A.longitude;
				double LongB=B.longitude;
				double LatA=A.latitude;
				double LatB=B.latitude;
		
				double earthRadius = 3958.75;
				double latDiff = Math.toRadians(LatB - LatA);
				double lngDiff = Math.toRadians(LongB - LongA);
				double a = Math.sin(latDiff / 2)
						* Math.sin(latDiff / 2)
						+ Math.cos(Math.toRadians(LatA))
						* Math.cos(Math.toRadians(LatB))
						* Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
				double c = 2 * Math.atan2(Math.sqrt(a),
						Math.sqrt(1 - a));
				double distance = earthRadius * c;

				 int meterConversion = 1609;

				 double dist= distance * meterConversion;

			
		
		return (float) Math.abs(dist);
		
	}
	
}
