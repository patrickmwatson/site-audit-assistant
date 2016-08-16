/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class RiserDiagramView extends View {
	private static final int INVALID_POINTER_ID = -1;
	static float textpadding = 5;
	
	static int lowestrighty;
	
	static int presentableblue = Color.parseColor("#548BD4");
	static int presentablered = Color.parseColor("#D9958F");
	static int presentablegreen = Color.parseColor("#C4D6A0");
	static int presentableorange = Color.parseColor("#F59D56");
	static int presentablepurple = Color.parseColor("#7E649E");
		
	static int maxbms=50;
	static int bmscount=0;
	static String[] bmsnamesfromfloorplan=new String[maxbms];
	
	static boolean DONTSCALEORTRANSLATE = false;

	static float absolutex;

	static float absolutey;

	private static float pivotpointx;
	private static float pivotpointy;

	private static float mPosX;
	private static float mPosY;

	static float scrnwidth;
	static float scrnheight;

	private float bitheight;
	private float bitwidth;

	private static float distx;
	private static float disty;

	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;

	private float mLastUpX;
	private float mLastUpY;

	private float mLastDownX;
	private float mLastDownY;

	private ScaleGestureDetector mScaleDetector;
	private static float mScaleFactor = 1.f;
	
	static Bitmap BMS;
	static Bitmap ELC;
	static Bitmap Gateway;
	static Bitmap SAM;
	static Bitmap MINISAM;
	static Bitmap tempsensor;
	static Bitmap LAN;
	static Bitmap Legendtop;
	static Bitmap Legendbottom;
	
	private static Paint p;

	static Rect rect;
	//private String SiteListsPath;
	//private String MCInputPath;
	static String OutputBitmapPath;
	static Bitmap canvasbitmap;

	static Context ctx;

	static ArrayList<String> loadname = new ArrayList<String>();
	static ArrayList<String> elcno_metering = new ArrayList<String>();
	static ArrayList<String> referenceonmap =new ArrayList<String>();
	static ArrayList<String> elcno_tempsensor = new ArrayList<String>();
	static ArrayList<String> bmsnames = new ArrayList<String>();
	static int maxitems = 1000;
	static int rectwidth = 200;
	static int layoutheight;
	static int border=0;
	static int[] columnmeter = new int[maxitems];
	static int[] rowmeter = new int[maxitems];
	static int[] columntemp = new int[maxitems];
	static int[] rowtemp = new int[maxitems];
	
	static int edgelength = 7;
	static int rorgap =5;
	static int columngap =60;
	static int samnamegap = 3;
	static int maxelc =0;
	static int maxmetelc =0;
	static int maxtempelc =0;
	static int bitright;
	static int bitbottom;
	
	
	static Paint borderpaint = new Paint();
	static TextPaint namecolor = new TextPaint();
	static Paint ethernetpaint = new Paint();
	static Paint modbuspaint = new Paint();
	static Paint temppaint = new Paint();
	static Paint elccircle = new Paint();
	
	
	static int rowstart =5;
	static int ethernetgap =25;
	static int elctempgap = 30;
	static int tempgap = 5;
	static int samgap = 10;
	static int elcradius = 15;
	
	static int samadjustment = 12;
	static int left = 5;
	static int samlastline[] = new int[maxitems];
	static int templastline[] = new int[maxitems];
	static int linegap =15;
	static int bmsgatewaygap =30;
	static int bmsedge = 20;
	
	static int legendrightgap = 70;
	static int bmslocationx = 0;
	static int bmslocationy =0;
	static int lanadustment = 0;
	
	static int x=0;
	static int y=0;
	
	static int farthestleft=0;
	static int newleft=0;
	static int farthestright=0;
	static int newright=0;
	
	public RiserDiagramView(Context context) {
		this(context, null, 0);
	}

	public RiserDiagramView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RiserDiagramView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);


		
		
		ctx = context;
		
		
		//SiteListsPath = Tabs1.SiteListsPath;
		//MCInputPath = Tabs1.MCInputPath;
		OutputBitmapPath = Tabs1.OutputBitmapPath;
		Log.d("riserran","true");
		readexcelsite();
		readexcelmcr();
		// BitmapFactory.Options o = new BitmapFactory.Options();
		// o.inSampleSize = 2;
		for (int em=0; em<elcno_metering.size(); em++)
		{
			if(u.i(elcno_metering.get(em))>maxmetelc)
			{
				maxmetelc = u.i(elcno_metering.get(em));
				
			}
			
			if(u.i(elcno_metering.get(em))>maxelc)
			{
				maxelc = u.i(elcno_metering.get(em));
			}
		}
		
		for (int et=0; et<elcno_tempsensor.size(); et++)
		{
			try{
				if(u.i(elcno_tempsensor.get(et))>maxtempelc)
				{
					maxtempelc = u.i(elcno_tempsensor.get(et));
				}
	
				if(u.i(elcno_tempsensor.get(et))>maxelc)
				{
					maxelc = u.i(elcno_tempsensor.get(et));
				}
			}catch(Throwable e){
				
			}
		}
		
		
		BMS = new BitmapFactory()
		.decodeResource(getResources(), R.drawable.bms);
		ELC = new BitmapFactory()
		.decodeResource(getResources(), R.drawable.elc);
		Gateway = new BitmapFactory().decodeResource(getResources(),
		R.drawable.gateway);
		SAM = new BitmapFactory()
		.decodeResource(getResources(), R.drawable.sam);
		
		
		tempsensor = new BitmapFactory().decodeResource(getResources(),
				R.drawable.tempsensor);
		LAN = new BitmapFactory()
				.decodeResource(getResources(), R.drawable.lan);
		Legendtop = new BitmapFactory().decodeResource(getResources(),
				R.drawable.legendtop);
		Legendbottom = new BitmapFactory()
				.decodeResource(getResources(), R.drawable.componentstable2);
		
		BMS=Bitmap.createScaledBitmap(BMS, 100, 100, true);
		ELC=Bitmap.createScaledBitmap(ELC, 150, 120, true);
		Gateway=Bitmap.createScaledBitmap(Gateway, 100, 100, true);
		tempsensor=Bitmap.createScaledBitmap(tempsensor, 30, 30, true);
		SAM=Bitmap.createScaledBitmap(SAM, 35, 40, true);
		LAN=Bitmap.createScaledBitmap(LAN, 200, 150, true);
		
		Legendtop= Bitmap.createScaledBitmap(Legendtop, 3*ELC.getWidth(), 1*ELC.getHeight(), true);
				
		Legendbottom = Bitmap.createScaledBitmap(Legendbottom, Legendtop.getWidth(), Legendbottom.getHeight()*Legendtop.getWidth()/Legendbottom.getWidth(), true);
		
		
		
		
		
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displaymetrics);
		scrnheight = displaymetrics.heightPixels;
		scrnwidth = displaymetrics.widthPixels;
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

		p = new Paint();
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setDither(true);
		p.setColor(Color.BLUE);
		p.setStrokeWidth(10);
		p.setTextSize(30);
		
		ethernetpaint.setColor(presentableblue);
		ethernetpaint.setStyle(Paint.Style.STROKE);
		ethernetpaint.setStrokeWidth(3);
		
		modbuspaint.setColor(presentablepurple);
		modbuspaint.setStyle(Paint.Style.STROKE);
		modbuspaint.setStrokeWidth(4);
		
		temppaint.setColor(presentableorange);
		temppaint.setStyle(Paint.Style.STROKE);
		temppaint.setStrokeWidth(5);
		
		elccircle.setColor(presentablered);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Let the ScaleGestureDetector inspect all events.
		mScaleDetector.onTouchEvent(ev);

		final int action = ev.getAction();

		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();

			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = ev.getPointerId(0);

			mLastDownX = x;
			mLastDownY = y;

			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = ev.findPointerIndex(mActivePointerId);
			final float x = ev.getX();
			final float y = ev.getY();

			// Only move if the ScaleGestureDetector isn't processing a gesture.
			if (!mScaleDetector.isInProgress()) {
				final float dx = (x - mLastTouchX) / mScaleFactor;
				final float dy = (y - mLastTouchY) / mScaleFactor;

				mPosX += dx;
				mPosY += dy;

				invalidate();
			}

			mLastTouchX = x;
			mLastTouchY = y;

			try {
				distx = ev.getX(0) + ev.getX(1);
				disty = ev.getY(0) + ev.getY(1);
			} catch (IllegalArgumentException e) {

			}

			break;
		}

		case MotionEvent.ACTION_UP: {
			final float x = ev.getX();
			final float y = ev.getY();

			mLastUpX = x;
			mLastUpY = y;

			Log.d("lastup", x + " " + y);
			// Get absolute position of finger touch.

			float percentofscreenx = mLastTouchX / scrnwidth;
			float percentofscreeny = mLastTouchY / scrnheight;

			absolutex = (rect.right - rect.left) * percentofscreenx + rect.left;
			absolutey = (rect.bottom - rect.top) * percentofscreeny + rect.top;

			// ACTION UP
			if (Math.abs(mLastUpX - mLastDownX) < 20
					&& Math.abs(mLastUpY - mLastDownY) < 20) {

					invalidate();
				
			}
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_POINTER_UP: {
			final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = ev.getPointerId(pointerIndex);
			if (pointerId == mActivePointerId) {
				// This was our active pointer going up. Choose a new
				// active pointer and adjust accordingly.
				final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				mLastTouchX = ev.getX(newPointerIndex);
				mLastTouchY = ev.getY(newPointerIndex);
				mActivePointerId = ev.getPointerId(newPointerIndex);

				mLastUpX = mLastTouchX;
				mLastUpY = mLastTouchY;

			}

			break;
		}
		}

		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		CanvasChanges(canvas);

	}

	public static void CanvasChanges(Canvas canvas) {
		canvas.save();
		canvas.drawColor(Color.WHITE);
		if (!DONTSCALEORTRANSLATE)
			canvas.translate(mPosX, mPosY);

		float px = (distx / 2 - mPosX);
		float py = (disty / 2 - mPosY);
		pivotpointx = px;
		pivotpointy = py;

		if (!DONTSCALEORTRANSLATE)
			canvas.scale(mScaleFactor, mScaleFactor, px, py);
		
		rect = canvas.getClipBounds();
		namecolor.setColor(Color.BLACK);
		borderpaint.setColor(Color.BLACK);
        borderpaint.setStyle(Paint.Style.STROKE);
        borderpaint.setStrokeWidth(2);
        
		int s1 = loadname.size();
		int s2 = referenceonmap.size();
		
		int columnwidth = rectwidth+samnamegap+SAM.getWidth()+edgelength+columngap;
		int tempstart =columnwidth-(5*edgelength)-tempsensor.getWidth();
		
		int elcmin =maxelc;
		int legendbottom =rowstart+LAN.getHeight()+ethernetgap+ELC.getHeight()+elctempgap+(10*tempsensor.getHeight());
		
		
		if(!(loadname.size()==elcno_metering.size()))
		{
			//LoadDiagramActivity.showToast("Check Metering Commisioning Table some Loads might be missing",ctx);
			
			s1 =Math.min(loadname.size(),elcno_metering.size());
		}
		
		if(!(referenceonmap.size()==elcno_tempsensor.size()))
		{
			//LoadDiagramActivity.showToast("Check Tempsensor Commisioning Table some Loads might be missing",ctx);
			
			s2 =Math.min(referenceonmap.size(),elcno_tempsensor.size());
		}
		
		
		
		//drawing lan

		if(legendrightgap+ left+((maxelc)*columnwidth) -left+columnwidth*maxelc/2+(LAN.getWidth()/2)<Legendtop.getWidth())
		{
			lanadustment = Legendtop.getWidth()-(legendrightgap+ left+((maxelc)*columnwidth) -left+columnwidth*maxelc/2+(LAN.getWidth()/2));
		}
		
		Path lanelc = new Path();
		
		x=left+columnwidth*maxelc/2+(LAN.getWidth()/2)-lanadustment;
		lanelc.moveTo(x, rowstart+(LAN.getHeight()/2));
		lanelc.lineTo(x , rowstart+LAN.getHeight()+(ethernetgap/4));
		canvas.drawPath(lanelc, ethernetpaint);
		
		x=left+columnwidth*maxelc/2-lanadustment;
		canvas.drawBitmap(LAN, x, rowstart, p);
		farthestleft=x;
		farthestright=x;
		namecolor.setTextSize(30f);
		canvas.drawText("LAN", left+columnwidth*maxelc/2+(LAN.getWidth()/4)-lanadustment, rowstart+(LAN.getHeight()/2), namecolor);
		

		
		//drawing elcs
		for (int e=0; e<maxelc;e++)
		{
			if(elcno_metering.contains(u.s(e+1))||elcno_tempsensor.contains(u.s(e+1))){
					Path elclan = new Path();
					x=left+columnwidth/2+(e*columnwidth);
					getlimits(x);
					elclan.moveTo(x, rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2) );
					elclan.lineTo(x , rowstart+LAN.getHeight()+(ethernetgap/4));
					canvas.drawPath(elclan, ethernetpaint);
					
					canvas.drawBitmap(ELC, left+columnwidth/2+(e*columnwidth)-(ELC.getWidth()/2), rowstart+LAN.getHeight()+ethernetgap, p);
					canvas.drawCircle(left+columnwidth/2+(e*columnwidth), rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2), elcradius, elccircle);
					namecolor.setTextSize(30f);
					canvas.drawText(u.s(e+1),left+ columnwidth/2+(e*columnwidth)-(elcradius/2), rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2)+(p.getTextSize()/4)+1, namecolor);
					rowtemp[e+1]=rowstart+LAN.getHeight()+ethernetgap+ELC.getHeight()+elctempgap;
					rowmeter[e+1]=rowstart+LAN.getHeight()+ethernetgap+ELC.getHeight()+elctempgap+(10*tempsensor.getHeight());
					
								
					if(elcno_metering.contains(u.s(e+1))){
						Path elcsam1 = new Path();
						x=left+columnwidth/2+(e*columnwidth)-(ELC.getWidth()/2)+2;
						getlimits(x);
						elcsam1.moveTo(x, rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2) );
						x=left+(e*columnwidth);
						getlimits(x);
						elcsam1.lineTo(x , rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2));
						canvas.drawPath(elcsam1, modbuspaint);
					}
					
					if(elcno_tempsensor.contains(u.s(e+1))){
						Path elctemp1 = new Path();
						x=left+columnwidth/2+(e*columnwidth)+(ELC.getWidth()/2);
						getlimits(x);
						elctemp1.moveTo(x, rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2) );
						x=left+((e+1)*columnwidth)-linegap;
						getlimits(x);
						elctemp1.lineTo( x, rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2));
						canvas.drawPath(elctemp1, temppaint);
					}
			}
		}
		//drawing tempsensors
		for (int t=0;t<s2;t++)
		{
			try{
			namecolor.setTextSize(20f);
			
			int elc = u.i(elcno_tempsensor.get(t));
			String zone = referenceonmap.get(t);
			zone=zone.trim();
			int h=1;
			loop: while (h != 0) {
				if(zone.contains(" ")){
					zone=zone.replace(" ", "");
					h=1;
				}else if(zone.contains(" ")){
					zone=zone.replace(" ", "");
				}else{
					h=0;
				}
			}
			
			
			u.log("Zone name length, "+zone.length());	
			float textstart = tempstart-samnamegap-(namecolor.measureText(zone))+2;
			canvas.drawText(zone,left+ textstart+((elc-1)*columnwidth),rowtemp[elc]+(namecolor.getTextSize()) , namecolor);
			
			Path templine = new Path();
			templine.moveTo(left+tempstart+((elc-1)*columnwidth)+(tempsensor.getWidth()/2), rowtemp[elc]+(tempsensor.getHeight()/2));
			templine.lineTo(left+(elc*columnwidth)-linegap+2, rowtemp[elc]+(tempsensor.getHeight()/2));
			canvas.drawPath(templine, temppaint);
			
			canvas.drawBitmap(tempsensor, left+tempstart+((elc-1)*columnwidth), rowtemp[elc], p);
			templastline[elc]= rowtemp[elc]+(tempsensor.getHeight()/2)+2;
			
			rowtemp[elc] = rowtemp[elc]+tempsensor.getHeight()+tempgap;
			}catch(Throwable e){
				
			}
			
		}
		
		//drawing elc to tempsensor
				for (int e=0; e<maxtempelc;e++)
				{
					if(elcno_tempsensor.contains(u.s(e+1))){
						Path elctemp2 = new Path();
						elctemp2.moveTo(left+((e+1)*columnwidth)-linegap , rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2)-2);
						elctemp2.lineTo(left+((e+1)*columnwidth)-linegap ,templastline[e+1]);
						canvas.drawPath(elctemp2, temppaint);
					}
				}
		
		
		//drawing sammeters
		for(int s= 0;s<s1;s++)
		{
			try{
			int elc = u.i(elcno_metering.get(s));
			String load = loadname.get(s);
			Rectangle(load,canvas,left+edgelength+((elc-1)*columnwidth)+(rectwidth/4), rowmeter[elc]);
			
			Path sam = new Path();
			sam.moveTo(left+ edgelength+((elc-1)*columnwidth)+SAM.getWidth()/2, rowmeter[elc]+layoutheight/2-samadjustment+(SAM.getHeight()/2) );
			sam.lineTo(left+((elc-1)*columnwidth) , rowmeter[elc]+layoutheight/2-samadjustment+(SAM.getHeight()/2));
			canvas.drawPath(sam, modbuspaint);
			
			canvas.drawBitmap(SAM,left+ edgelength+((elc-1)*columnwidth), rowmeter[elc]+layoutheight/2-samadjustment, p);
			
			
			samlastline[elc]=rowmeter[elc]+layoutheight/2-samadjustment+(SAM.getHeight()/2)+1;
			
			rowmeter[elc] = rowmeter[elc]+layoutheight+2*samgap;
			}catch(Throwable e){
				
			}
			}
			
		//drawing elc to sam modbus
		
	for (int e=0; e<maxmetelc;e++)
		{
			if(elcno_metering.contains(u.s(e+1))){
				Path elcsam2 = new Path();
				elcsam2.moveTo(left+(e*columnwidth) , rowstart+LAN.getHeight()+ethernetgap+(ELC.getHeight()/2)-2 );
				elcsam2.lineTo(left+(e*columnwidth) ,samlastline[e+1]);
				canvas.drawPath(elcsam2, modbuspaint);
			}
		}
		
	// finding minimum elc
	for (int em=0; em<s1;em++)
	{
		try{
			elcmin = Math.min(elcmin, u.i(elcno_metering.get(em)));
		}catch(Throwable e){
			
		}
	}
	for (int em2=0; em2<s2;em2++)
	{
		try{
			elcmin = Math.min(elcmin, u.i(elcno_tempsensor.get(em2)));
		}catch(Throwable e){
			
		}
	}
		
	Log.d("elcmin",u.s(elcmin)+" "+u.s(maxelc));
	//drawing path from ELC's to Bms & Gateway
				Path elcgatway = new Path();
				
				x=left+columnwidth/2+((elcmin-1)*columnwidth);
				getlimits(x);
				elcgatway.moveTo(x, rowstart+LAN.getHeight()+(ethernetgap/4));
				x=left+columnwidth/2+((maxelc)*columnwidth)+(BMS.getWidth()/2)+bmsedge ;
				getlimits(x);
				elcgatway.lineTo(x, rowstart+LAN.getHeight()+(ethernetgap/4));
				x=left+columnwidth/2+((maxelc)*columnwidth)+(BMS.getWidth()/2)+bmsedge ;
				getlimits(x);
				elcgatway.lineTo(x, rowstart+LAN.getHeight()+ethernetgap+(bmscount*(BMS.getHeight()+bmsgatewaygap))+(Gateway.getWidth()/2));
				x=left+columnwidth/2+((maxelc)*columnwidth)+(BMS.getWidth()/2)-bmsedge;
				getlimits(x);
				elcgatway.lineTo(x, rowstart+LAN.getHeight()+ethernetgap+(bmscount*(BMS.getHeight()+bmsgatewaygap))+(Gateway.getWidth()/2));
				canvas.drawPath(elcgatway, ethernetpaint);
				
				
		
		//drawing BMS and Gateway Server
		if(bmscount>0){
			Log.d("bmscount",u.s(bmscount));
			for (int f=0; f<bmscount; f++){
			bmslocationx=left+columnwidth/2+((maxelc)*columnwidth)-(BMS.getWidth()/2);
			bmslocationy=rowstart+LAN.getHeight()+ethernetgap+((ethernetgap+BMS.getHeight())*(f));
			canvas.drawBitmap(BMS, bmslocationx, bmslocationy, p);
			
			String text = bmsnamesfromfloorplan[f];
			float[] widths = new float[text.length()];
			float totalwidth = 0;
			p.getTextWidths(text, widths);

			for (int u = 0; u < widths.length; u++) {
				totalwidth = totalwidth + widths[u];
			}

			float textlocationx = bmslocationx+(BMS.getWidth()/2) - totalwidth / 2;
			float textlocationy = bmslocationy+BMS.getHeight()+ p.getTextSize() / 2
					- textpadding;
			
			
			p.setColor(presentablepurple);
			textpadding = p.getTextSize() / 10;
			canvas.drawRoundRect(
					new RectF(
							new Rect(
									(int) (textlocationx - textpadding),
									(int) (textlocationy
											- p.getTextSize() + (textpadding / 2)),
									(int) (textlocationx
											+ totalwidth + textpadding),
									(int) (textlocationy + textpadding))),
					textpadding, textpadding, p);
			p.setColor(Color.WHITE);

			canvas.drawText(text, textlocationx, textlocationy,p);
			Path bmslan = new Path();
			bmslan.moveTo(left+columnwidth/2+((maxelc)*columnwidth)+(BMS.getWidth()/2)+bmsedge , bmslocationy+(BMS.getHeight()/2));
			bmslan.lineTo(left+columnwidth/2+((maxelc)*columnwidth)+(BMS.getWidth()/2)-bmsedge, bmslocationy+(BMS.getHeight()/2));
			canvas.drawPath(bmslan, ethernetpaint);
			}
		}
		canvas.drawBitmap(Gateway, left+columnwidth/2+((maxelc)*columnwidth)-(BMS.getWidth()/2), rowstart+LAN.getHeight()+ethernetgap+(bmscount*(BMS.getHeight()+bmsgatewaygap)), p);
		legendbottom = rowstart+LAN.getHeight()+ethernetgap+((ethernetgap+BMS.getHeight())*(bmscount+2));
		for (int e=0; e<maxmetelc;e++)
		{
			if(elcno_metering.contains(u.s(e+1))){
				legendbottom = Math.max(legendbottom, rowmeter[e+1]);
			}
		}
		
		if(legendbottom<(bmslocationy+3*BMS.getHeight()))
		{
			legendbottom = bmslocationy+3*BMS.getHeight();
		}
		
		//drawing legend
		canvas.drawBitmap(Legendtop,legendrightgap+ left+((maxelc)*columnwidth), rowstart, p);
		canvas.drawBitmap(Legendbottom, legendrightgap+left+((maxelc)*columnwidth)-Legendbottom.getWidth()+Legendtop.getWidth(),legendbottom+2*samgap, p);
		
		bitright = legendrightgap+left+((maxelc)*columnwidth)+Legendtop.getWidth()+2*samgap;
        bitbottom = legendrightgap+legendbottom+4*samgap+Legendbottom.getHeight();
		canvas.restore();
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();

			// Don't let the object get too small or too large.
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

			invalidate();
			return true;
		}
	}
	

	public static void Rectangle(String loadtypename, Canvas canvas,int x, int y) {
		canvas.save();
		int fontsize = 20;
		namecolor.setTextSize(fontsize);
		StaticLayout sl = new StaticLayout(loadtypename, namecolor,
				rectwidth - 4, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		while (sl.getLineCount() > 3) {

			fontsize--;
			namecolor.setTextSize(fontsize);
			sl = new StaticLayout(loadtypename, namecolor, rectwidth,
					Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		}
		layoutheight = sl.getHeight();
		int tl = sl.getLineCount();
		canvas.translate(x + 2, y + 4);
		sl.draw(canvas);
		canvas.restore();
		
		int rectleft = x ;
		int recttop = y;
		int rectright= x+ rectwidth;
		int rectbottom= y +layoutheight+8;
		canvas.drawRect(rectleft,recttop,rectright,rectbottom, borderpaint);
	}
	private void readexcelmcr() {
		//WorkbookSettings ws = new WorkbookSettings();

		//File sdcard = Environment.getExternalStorageDirectory();

    	 //Get the text file
    	//File file1 = new File(MCInputPath);
		
		
//		long size = file1.length();
//		int sizeint = (int) size;
//		Log.d("filesize",u.s(sizeint));
//		ws.setInitialFileSize(sizeint);
//		Workbook workbook = null;
//		try {
//			workbook = Workbook.getWorkbook(file1, ws);
//		} catch (BiffException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//String[] sheets = workbook.getSheetNames();
				int sheetnum = Tabs1.METERINGLIST;
		
				int worksheet = sheetnum;
//				Sheet sheet = workbook.getSheet(worksheet);

				// Fill out engineer name and sitewalk date
				// name

				// rect left
				try {
				int start = 0;
				
				
				loadname = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_METERINGLIST, Tabs1.LOADNAMECOLUMN, Tabs1.db.KEY_MCR_METERING_TITLES);
				for(int i=0; i<loadname.size(); i++){
					Log.d("Loads read",loadname.get(i));
				}
				elcno_metering = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_METERINGLIST, Tabs1.ELCNUMBERCOLUMN, Tabs1.db.KEY_MCR_METERING_TITLES);
			}
			catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
				
				try {
				
				referenceonmap = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_TEMPSLIST, Tabs1.TEMPSECTIONOFBUILDINGCOLUMN, Tabs1.db.KEY_MCR_TEMPLIST_TITLES);
				System.out.println("referenceonmap size"+referenceonmap.size());
				for(int i=0; i<referenceonmap.size(); i++){
					try{
						Log.d("Zone read",referenceonmap.get(i));
					}catch(Throwable e){
						
					}
				}
				elcno_tempsensor = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_TEMPSLIST, 2, Tabs1.db.KEY_MCR_TEMPLIST_TITLES);
			}
			catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
	}
	private void readexcelsite() {
		


    	 //Get the text file
		
		

				// Fill out engineer name and sitewalk date
				// name
				ArrayList<String> itemno=Tabs1.db.getcolumn(Tabs1.db.TABLE_FLOORPLAN, 0, Tabs1.db.KEY_FLOORPLAN_TITLES);
				// rect left
				int TYPE_BMS = 0;
				int startcolumn=0;

				int numberofitems = itemno.size();


				ArrayList<String> type=Tabs1.db.getcolumn(Tabs1.db.TABLE_FLOORPLAN, 5, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> string=Tabs1.db.getcolumn(Tabs1.db.TABLE_FLOORPLAN, 15, Tabs1.db.KEY_FLOORPLAN_TITLES);
				bmscount=0;
				for (int t = 0; t < numberofitems; t++) {

					if(u.i(type.get(t))==TYPE_BMS){
						bmsnamesfromfloorplan[bmscount]=string.get(t);
						bmscount++;
					}
				}

				Log.d("bmscount",u.s(bmscount));
				for(int h=0; h<bmsnamesfromfloorplan.length; h++){
					try{
						Log.d("bmsnames",bmsnamesfromfloorplan[h]);
					}catch(Throwable e){
						
					}
				}
			}
		
	
	public static void Legend(String panelname, Canvas canvas,
			Paint panellocationpain, Paint namecolor,int x, int y) {
		
		int boxwidth  = 25;
		int boxheight =25;
		int bx = x-65;
		LinearGradient gradient = new LinearGradient(bx - (boxwidth / 2), y
				- (boxheight / 2),bx + (boxwidth / 2), y + (boxheight / 2),
				Color.WHITE, panellocationpain.getColor(), Shader.TileMode.MIRROR);
		panellocationpain.setShader(gradient);
       
		canvas.drawRect(bx - (boxwidth / 2), y - (boxheight / 2), bx
				+ (boxwidth / 2), y + (boxheight / 2), panellocationpain);
       
		canvas.drawRect(bx-border - (boxwidth / 2), y-border - (boxheight / 2),
				bx+border + (boxwidth / 2), y +border+ (boxheight / 2),
                borderpaint);

		
		int fontsize = 20;
		namecolor.setTextSize(fontsize);
		canvas.drawText(panelname, (x) - (rectwidth / 4), y + (fontsize / 4),
				namecolor);

	}
	
	public static void getlimits(int x){
		
		if(farthestleft<x){
			farthestleft=x;
		}
		if(farthestright<x){
			farthestright=x;
		}
		Log.d("farthestleft, farthestright",farthestleft+" "+farthestright);
	}
}
