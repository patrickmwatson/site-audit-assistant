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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

public class LoadDiagramView extends View {
	private static final int INVALID_POINTER_ID = -1;

		
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

	
	static int legendgap = 30;
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
	

	private static Paint p;

	static Rect rect;

	private String MCRlocation;
	static String OutputBitmapPath;
	static Bitmap canvasbitmap;

	static Context ctx;

	static ArrayList<String> loadname = new ArrayList<String>();
	static ArrayList<String> panellocation = new ArrayList<String>();
	static ArrayList<String> loadtype =new ArrayList<String>();
	static ArrayList<String> typesoflocations = new ArrayList<String>();
	
	static int rectwidth = 150;
	static int rectheight = 100;
	static int border=0;
	
	static int legendleft;
	static int legendtop;		
	static int legendright;
	static int legendbottom;
	static int bitright;
	static int bitbottom;
	static Paint borderpaint = new Paint();
	
	static int virtualmetercolor=Color.argb(0xFF,100,255,100);
	
	public LoadDiagramView(Context context) {
		this(context, null, 0);
	}

	public LoadDiagramView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadDiagramView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);


		ctx = context;

		MCRlocation = Tabs1.MCRlocation;
		OutputBitmapPath = Tabs1.OutputBitmapPath;
		readexcel();
		// BitmapFactory.Options o = new BitmapFactory.Options();
		// o.inSampleSize = 2;


		

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
		p.setTextSize(50);
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
		int[] panelcolors =  new int[]{Color.argb(0xFF,250,234,220),Color.argb(0xFF, 239,242,230),Color.argb(0xFF, 231,227,238),Color.argb(0xFF, 209,248,224),Color.argb(0xFF, 253,225,185),Color.argb(0xFF, 225,241,184),Color.argb(0xFF,225,235,243),Color.argb(0xFF,215,227,191),Color.argb(0xFF,198,216,240),Color.argb(0xFF,200,165,157),Color.argb(0xFF,169,200,157),Color.argb(0xFF,157,187,200),Color.argb(0xFF,180,157,200),Color.argb(0xFF,200,157,191),Color.argb(0xFF,200,157,158)};
		
		String[] loadtypename = new String[]{"Mechanical Load (ML)","Lighting/ Power (LP)", "Production Load (PL)","Total Load (TL)"};
		Paint panellocationpaint = new Paint();
		Paint loadtypecolor = new Paint();
		TextPaint namecolor = new TextPaint();
		namecolor.setColor(Color.BLACK);
		loadtypecolor.setColor(Color.argb(0xFF, 196,210,227));
		panellocationpaint.setDither(true);
		
        borderpaint.setColor(Color.BLACK);
        borderpaint.setStyle(Paint.Style.STROKE);
        borderpaint.setStrokeWidth(2);
        
		int s = loadname.size();
		int mx =4*rectheight, my= 0, lx =0, ly =0;
		int mechno =0,lighno =0;
		int spacemlp=0,spacelpl=0;
		int rowmechno = 0,rowlighno = 0;
		int mechrow =1,mechcol=0;
		int lighrow = 1, lighcol = 0;
		
		if(!(loadname.size()==loadtype.size())||!(loadname.size()==panellocation.size())||!(panellocation.size()==loadtype.size()))
		{
			//LoadDiagramActivity.showToast("Check Commisioning Table some Loads might be missing",ctx);
			
			s =Math.min(Math.min(loadname.size(),loadtype.size()), panellocation.size());
		
		}
		
		ArrayList<String> buildingloads = new ArrayList<String>();
		for (int q = 0; q < s; q++)
		{
			System.out.println(q);
			if (isMECHANICAL(q))
			{
				mechno++;
			}
			else if (isBUILDING(q))
			{
				buildingloads.add(u.s(q));
				Log.d("building",u.s(q)+q);
			}
			else
			{
				lighno++;
			}
		}
		if(mechno <= 18)
		{
			rowmechno = 3; 
		    spacemlp = 4;
		}
		if(lighno <= 18)
		{
			rowlighno = 3;
			spacelpl = 3;
		}
		if(mechno > 18)
		{
				rowmechno = mechno/6;
			if (!(mechno%6 == 0))
			{
				rowmechno++;
			}
			spacemlp = 4+rowmechno-3;
		}
		if(lighno> 18)
		{
			rowlighno = lighno/6;
			if (!(lighno%6 == 0))
			{
				rowlighno++;
			}
		spacelpl = 3+rowlighno-3;
		}
		
		if(mechno<3)
		{
			rowmechno = mechno;
			if(mechno==0)rowmechno =1;
		}
		if(lighno<3)
		{
			rowlighno = lighno;
			if(lighno==0)rowlighno =1;
		}
		
		Log.d("mechno,lighno,rowmechno,rowlighno,spacelpl,spacempl",mechno+" "+lighno+" "+rowmechno+" "+rowlighno+" "+spacelpl+" "+spacemlp);
		for (int q = 0; q < s; q++)
		{
			if (isMECHANICAL(q))
			{
				int l = typesoflocations.indexOf(panellocation.get(q));
				if(l<panelcolors.length)
				{
					panellocationpaint.setColor(panelcolors[l]);
				}else
				{
					panellocationpaint.setColor(Color.WHITE);
				}
				
				if(panellocation.get(q).equals("Virtual Meter")){
					panellocationpaint.setColor(virtualmetercolor);
				}
				Rectangle(loadname.get(q), canvas, panellocationpaint, namecolor,mechcol*rectwidth,(5+mechrow)*rectheight+(rectheight/4));
				mechcol++;
				
			    if (mechcol >= rowmechno)
			    {
			    	mechcol=0;
			    	mechrow++;
			    }
			}else if (isBUILDING(q)){
				
			}
			else 
			{
				int l = typesoflocations.indexOf(panellocation.get(q));
				if(l<panelcolors.length)
				{
					panellocationpaint.setColor(panelcolors[l]);
				}else
				{
					panellocationpaint.setColor(Color.WHITE);
				}
				
				if(panellocation.get(q).equals("Virtual Meter")){
					panellocationpaint.setColor(virtualmetercolor);
				}
				Rectangle(loadname.get(q), canvas, panellocationpaint, namecolor,(lighcol+rowmechno+1)*rectwidth, (5+lighrow)*rectheight+(rectheight/4));
				lighcol++;
			    if (lighcol >= rowlighno)
			    {
			    	lighcol=0;
			    	lighrow++;
			    }
			}
		}
		Rectangle("PL=TL-BL-ML-LL", canvas, loadtypecolor, namecolor,(rowlighno+rowmechno+2)*rectwidth, (6)*rectheight+(rectheight/4));
		Path recttodia = new Path();
		recttodia.moveTo((rowmechno-1)*rectwidth/2+(rectwidth), (6)*rectheight-(rectheight/4));
		recttodia.lineTo((rowmechno-1)*rectwidth/2+(rectwidth), (6)*rectheight-(rectheight/2));
		canvas.drawPath(recttodia, borderpaint);
		Path recttodia2 = new Path();
		recttodia2.moveTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (6)*rectheight-(rectheight/4));
		recttodia2.lineTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (6)*rectheight-(rectheight/2));
		canvas.drawPath(recttodia2, borderpaint);
		Path recttodia3 = new Path();
		recttodia3.moveTo((1+rowlighno+rowmechno+2)*rectwidth, (6)*rectheight-(rectheight/4));
		recttodia3.lineTo((1+rowlighno+rowmechno+2)*rectwidth, (6)*rectheight-(rectheight/2));
		canvas.drawPath(recttodia3, borderpaint);
		int currrow = 5;
		Diamond(loadtypename[0], canvas, loadtypecolor, namecolor,(rowmechno-1)*rectwidth/2,(currrow)*rectheight);
		Diamond(loadtypename[1], canvas, loadtypecolor, namecolor,(rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2,(currrow)*rectheight);
		Diamond(loadtypename[2], canvas, loadtypecolor, namecolor,(rowlighno+rowmechno+2)*rectwidth,(currrow)*rectheight);
		Path loadtypepath = new Path();
		loadtypepath.moveTo((rowmechno-1)*rectwidth/2+(rectwidth), ((2*currrow)-1)*rectheight/2);
		loadtypepath.lineTo((rowmechno-1)*rectwidth/2+(rectwidth), (((2*currrow)-1)*rectheight/2)-(rectheight/4));
		loadtypepath.lineTo((1+rowlighno+rowmechno+2)*rectwidth,  (((2*currrow)-1)*rectheight/2)-(rectheight/4));
		loadtypepath.lineTo((1+rowlighno+rowmechno+2)*rectwidth, ((2*currrow)-1)*rectheight/2);
		canvas.drawPath(loadtypepath, borderpaint);
		if(!(buildingloads.size()==0))
		{
		    int bs = buildingloads.size();
			currrow--;
			int buildcol=0;
			int adjustment=(bs-1)*rectwidth/2;
			Log.d("bilding befdraw",u.s(bs));
			for (int bl = 0; bl<bs;bl++)
			{
				int b = u.i(buildingloads.get(bl));
				int l = typesoflocations.indexOf(panellocation.get(b));
				panellocationpaint.setColor(panelcolors[l]);
				Rectangle(loadname.get(b), canvas, panellocationpaint, namecolor,((rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2)+buildcol*rectwidth-adjustment,((2*currrow)-1)*rectheight/2);
				buildcol++;
			}
			Path buildpath = new Path();
			buildpath.moveTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (((2*currrow)-1)*rectheight/2)+(rectheight/2));
			buildpath.lineTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, ((2*currrow)+1)*rectheight/2);
			canvas.drawPath(buildpath, borderpaint);
			
			Path buildpath2 = new Path();
			buildpath2.moveTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (((2*currrow)-1)*rectheight/2)-(rectheight/2));
			buildpath2.lineTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (currrow-2)*rectheight+(rectheight/2));
			canvas.drawPath(buildpath2, borderpaint);
		}
		else
		{
			Path buildpathn = new Path();
			buildpathn.moveTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, (currrow-2)*rectheight+(rectheight/2));
			buildpathn.lineTo((1+rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2, ((2*currrow)-1)*rectheight/2);
			canvas.drawPath(buildpathn, borderpaint);
		}
		currrow--;
		currrow--;
		Rectangle(loadtypename[3], canvas, loadtypecolor, namecolor,(rowmechno+1)*rectwidth+(rowlighno-1)*rectwidth/2,(currrow)*rectheight);
		
		int panelno = typesoflocations.size();
		int panelrectwidth = 0;
		for(int p = 0;p<panelno;p++)
		{
		   if(p<panelcolors.length)
			{
				panellocationpaint.setColor(panelcolors[p]);
			}else
			{
				panellocationpaint.setColor(Color.WHITE);
			}
			Legend(typesoflocations.get(p),canvas,
					panellocationpaint, namecolor,(1+rowlighno+rowmechno+2)*rectwidth, (11)*rectheight+(rectheight/4)-(p*25));
			
			if (namecolor.measureText(typesoflocations.get(p)) > panelrectwidth)
			{
				panelrectwidth = (int)namecolor.measureText(typesoflocations.get(p));
			}
		
		}
	   int adjustment = 0;
		if(panelrectwidth<250)
	   {
		   adjustment =280-panelrectwidth;
	   }
		   panelrectwidth = panelrectwidth +65;
		legendleft =(1+rowlighno+rowmechno+2)*rectwidth-border - (panelrectwidth / 2)+87-adjustment;
		legendtop =(11)*rectheight+(rectheight/4)-border - (panelno*25) +5-40;		
		legendright =(1+rowlighno+rowmechno+2)*rectwidth-border+border + (panelrectwidth / 2)+130;
		legendbottom = (11)*rectheight+(rectheight/4)+border+25;
		
		canvas.drawRect(legendleft,legendtop+40,legendright,legendbottom,borderpaint);
		Paint legendpaint = new Paint();
		legendpaint.setColor(Color.BLUE);
		LinearGradient gradient = new LinearGradient(legendleft,legendtop, legendright, legendtop+legendgap,
				Color.WHITE, legendpaint.getColor(), Shader.TileMode.MIRROR);
		legendpaint.setShader(gradient);
		canvas.drawRect(legendleft,legendtop,legendright,legendtop+40,legendpaint);
		canvas.drawRect(legendleft,legendtop,legendright,legendtop+40,borderpaint);
		namecolor.setTextSize(25f);
		canvas.drawText("LEGEND", ((legendleft+legendright)/2) - ((legendright-legendleft) / 4)+5, ((legendtop+legendtop+legendgap)/2)+ (42 / 4),
				namecolor);
		
		bitright = legendright+legendgap;
        bitbottom = legendbottom+legendgap;
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
	public static void Diamond(String loadtypename, Canvas canvas,
			Paint panellocationpain, TextPaint namecolor,int x, int y) {
		canvas.save();
		int rhombuswidth = 150;
		int rhombusheight = 100;
		x = x+rhombuswidth;
		
		LinearGradient gradient = new LinearGradient(x - (rhombuswidth / 2), y
				- (rhombusheight / 2), x + (rhombuswidth / 2), y
				+ (rhombusheight / 2), Color.WHITE, panellocationpain.getColor(),
				Shader.TileMode.MIRROR);
		panellocationpain.setShader(gradient);
		Path diapath = new Path();
		diapath.moveTo(x - (rhombuswidth / 2), y);
		diapath.lineTo(x, y + (rhombusheight / 2));
		diapath.lineTo(x + (rhombuswidth / 2), y);
		diapath.lineTo(x, y - (rhombusheight / 2));
		canvas.drawPath(diapath, panellocationpain);
		Path diapath1 = new Path();
		diapath1.moveTo(x - (rhombuswidth / 2), y);
		diapath1.lineTo(x, y + (rhombusheight / 2));
		diapath1.lineTo(x + (rhombuswidth / 2), y);
		diapath1.lineTo(x, y - (rhombusheight / 2));
		diapath1.lineTo(x - (rhombuswidth / 2), y);
		canvas.drawPath(diapath1, borderpaint);
		int fontsize = 16;
		namecolor.setTextSize(fontsize);
		StaticLayout sl = new StaticLayout(loadtypename, namecolor,
				rectwidth - 10, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		int layoutheight = sl.getHeight();
		Log.d("layout height", u.s(layoutheight));
		while (sl.getHeight() > rectheight) {

			fontsize--;
			namecolor.setTextSize(fontsize);
			sl = new StaticLayout(loadtypename, namecolor, rectwidth-10,
					Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		}

		int tl = sl.getLineCount();
		
		if (tl < 3)
			canvas.translate(x - (rectwidth / 2) + 2, y - (tl * (fontsize))
					+ 16);
		else
			canvas.translate(x - (rectwidth / 2), y - (rectheight / 2)+12);

		sl.draw(canvas);
		canvas.restore();

	}

	public static void Rectangle(String loadtypename, Canvas canvas,
			Paint panellocationpain, TextPaint namecolor,int x, int y) {
		
		canvas.save();
		x = x+rectwidth;
		
		LinearGradient gradient = new LinearGradient(x - (rectwidth / 2), y
				- (rectheight / 2), x + (rectwidth / 2), y + (rectheight / 2),
				Color.WHITE, panellocationpain.getColor(), Shader.TileMode.MIRROR);
		panellocationpain.setShader(gradient);
		int rectleft = x - (rectwidth / 2);
		int recttop = y - (rectheight / 2);
		int rectright= x+ (rectwidth / 2);
		int rectbottom= y + (rectheight / 2);

		canvas.drawRect(rectleft,recttop,rectright ,rectbottom , panellocationpain);
       
		int borderleft=rectleft-border;
		int bordertop=recttop-border;
		int borderright=rectright-border;
		int borderbottom=rectbottom-border;
		
		canvas.drawRect(borderleft,bordertop,borderright,borderbottom, borderpaint);
		int fontsize = 20;
		namecolor.setTextSize(fontsize);
		StaticLayout sl = new StaticLayout(loadtypename, namecolor,
				rectwidth - 4, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		int layoutheight = sl.getHeight();
		Log.d("layout height", u.s(layoutheight));
		while (sl.getHeight() > rectheight) {

			fontsize--;
			namecolor.setTextSize(fontsize);
			sl = new StaticLayout(loadtypename, namecolor, rectwidth,
					Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
		}

		int tl = sl.getLineCount();
		
		if (tl < 3)
			canvas.translate(x - (rectwidth / 2) + 2, y - (tl * (fontsize))
					+ 4);
		else
			canvas.translate(x - (rectwidth / 2), y - (rectheight / 2));

		sl.draw(canvas);
		canvas.restore();
//		int fontsize = determineMaxTextSize(loadtypename, rectwidth / 2);
//		namecolor.setTextSize(fontsize);
//		canvas.drawText(loadtypename, x - (rectwidth / 4), y + (fontsize / 4),
//				namecolor);

	}
	private void readexcel() {
				DatabaseHandler db=Tabs1.db;
				
				loadname = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_METERINGLIST, Tabs1.LOADNAMECOLUMN, Tabs1.db.KEY_MCR_METERING_TITLES);
				for(int i=0; i<loadname.size(); i++){
					Log.d("Loads read",loadname.get(i));
				}
				panellocation = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_METERINGLIST, Tabs1.PANELLOCATIONCOLUMN, Tabs1.db.KEY_MCR_METERING_TITLES);
				loadtype = Tabs1.db.getcolumn(Tabs1.db.TABLE_MCR_METERINGLIST, Tabs1.LOADTYPECOLUMN, Tabs1.db.KEY_MCR_METERING_TITLES);
				for(int y=0; y<loadtype.size(); y++){
					System.out.println(loadtype.get(y));
				}
				
			
				
				
				List<String> vrmloadnames=db.getcolumn(db.TABLE_MCR_VIRTUAL_METERINGLIST, Tabs1.VRMLOADNAMECOLUMN, db.KEY_VIRTUAL_MCR_METERING_TITLES);
				List<String> vrmpanellocations=new ArrayList<String>();
				for(String st:vrmloadnames){
					vrmpanellocations.add("Virtual Meter");
				}
				
				List<String> vrmloadtypes=db.getcolumn(db.TABLE_MCR_VIRTUAL_METERINGLIST, Tabs1.VRMLOADTYPECOLUMN, db.KEY_VIRTUAL_MCR_METERING_TITLES);
				
				//Add Virtual Meters			
				
				loadname.addAll(vrmloadnames);
				loadtype.addAll(vrmloadtypes);
				panellocation.addAll(vrmpanellocations);
				
				
				typesoflocations = u.GetUniqueValues(panellocation);
				Log.d("typesoflocatios size",u.s(typesoflocations.size()));
				
			
				
				
				
				
				
				
			}
	public static void Legend(String panelname, Canvas canvas,
			Paint panellocationpain, Paint namecolor,int x, int y) {
		
		int boxwidth  = 25;
		int boxheight =25;
		int bx = x-65;
		
		if(panelname.equals("Virtual Meter")){
			panellocationpain.setColor(virtualmetercolor);
		}
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
	public static boolean isLIGHTINGANDPOWER(int q){
		boolean islight=false;
		if(loadtype.get(q).contains("Car Park Lighting")
				|| loadtype.get(q).contains("Customer Café")
				|| loadtype.get(q).contains("Dry Cleaners")
				|| loadtype.get(q).contains("Food Preparation")
				|| loadtype.get(q).contains("Petrol Filling Station")
				|| loadtype.get(q).contains("Sales Lighting")
				|| loadtype.get(q).contains("Staff Facilities")
				|| loadtype.get(q).contains("Sub Tenant")
				|| loadtype.get(q).contains("Warehouse")
				|| loadtype.get(q).contains("Sub meter Car Park Lighting")
				|| loadtype.get(q).contains("Sub meter Customer Café")
				|| loadtype.get(q).contains("Sub meter Dry Cleaners")
				|| loadtype.get(q).contains("Sub meter Food Preparation")
				|| loadtype.get(q).contains("Sub meter Sales Lighting")
				|| loadtype.get(q).contains("Sub meter Staff Facilities")
				|| loadtype.get(q).contains("Sub meter Sub Tenant")
				|| loadtype.get(q).contains("Sub meter Warehouse")
				|| loadtype.get(q).contains("Lighting/Power")
				|| loadtype.get(q).contains("Lighting")){
			System.out.println(loadtype.get(q));
			islight=true;
		}
		return islight;
	}
	public static boolean isMECHANICAL(int q){
		boolean ismech=false;
		if(loadtype.get(q).contains("HT Refrigeration")
				|| loadtype.get(q).contains("HVAC")
				|| loadtype.get(q).contains("LT Refrigeration")
				|| loadtype.get(q).contains("Refrigeration")
				|| loadtype.get(q).contains("Shop Services")
				|| loadtype.get(q).contains("Sub meter HT Refrigeration")
				|| loadtype.get(q).contains("Sub meter HVAC")
				|| loadtype.get(q).contains("Sub meter LT Refrigeration")
				|| loadtype.get(q).contains("Sub meter Shop Services")
				|| loadtype.get(q).contains("Sub meter Refrigeration")
				|| loadtype.get(q).contains("Mechanical Load")
				|| loadtype.get(q).contains("Mechanical")){

			ismech=true;
		}
		return ismech;
	}
	public static boolean isBUILDING(int q){
		boolean isbuild=false;
		if(loadtype.get(q).contains("Main Meter")
				|| loadtype.get(q).contains("Building Load")
				|| loadtype.get(q).contains("Building")){

			isbuild=true;
		}
		return isbuild;
	}
}
