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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;


import com.google.android.gms.internal.db;
import com.google.android.gms.maps.model.LatLng;
import com.lifehackinnovations.siteaudit.Tabs1.SiteAuditItem;

import jxl.format.Orientation;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class FloorPlanView extends View {

	static int doesntexist=-1;
	static AlertDialog dialog;
	
	Boolean longpresscancelled=true;
	
	static Boolean SHOWBUTTONRECTS = false;
	static int paddingvariable = 10;
	static int shapepaddingvariable = 10;
	static int extraspaceforbuttons = 20;
	
	
	static Integer[] templistorderedbyitemnumber;
	static LayoutParams[] lp;
	static Boolean TOOLBARSELECTED = false;

	static float firstscalepointx;
	static float firstscalepointy;
	float secondscalepointx;
	float secondscalepointy;
	static float getscalefrompointspixeldistance;

	private AlertDialog deleteorduplicatepicturedialog;
	private AlertDialog deletepicturedialog;

	public String oldcaption = "null";
	public String oldnotes = "null";

	static boolean resizeboolean = false;

	static SeekBar resizeiconsseekbar;

	static int longpresstolerance=50;
	
	// googmapsstuff
	static float ratiodegreepermeter = 360 / 6367500;

	private static final int INVALID_POINTER_ID = -1;

	static int i = 0;

	static AlertDialog samdialog;

	static int presentableblue = Color.parseColor("#548BD4");
	static int presentablered = Color.parseColor("#D9958F");
	static int presentablegreen = Color.parseColor("#C4D6A0");
	static int presentableorange = Color.parseColor("#F59D56");
	static int presentablepurple = Color.parseColor("#7E649E");

	private final static int ORANGE = Color.parseColor("#FF6200");

	static int currentpercentchange = 100;
	static int lastpercentchange = 100;

	static int currentpercentchangeone = 100;
	static int lastpercentchangeone = 100;

	// MENU INTS
	int MENU_GENERIC_MOVE = 0;
	int MENU_GENERIC_RESIZE = MENU_GENERIC_MOVE + 1;
	int MENU_GENERIC_DELETE = MENU_GENERIC_RESIZE + 1;
	int MENU_GENERIC_ADDPICTURE = MENU_GENERIC_DELETE + 1;

	// BMS
	int MENU_BMS_NAMEBMS = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_BMS_MOVETEXT = MENU_BMS_NAMEBMS + 1;

	// DISTRIBUTION BOARD
	int MENU_DISTRIBUTIONBOARD_RENAME = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_DISTRIBUTIONBOARD_MOVETEXT = MENU_DISTRIBUTIONBOARD_RENAME + 1;

	// SAMARRAY
	int MENU_SAMARRAY_RENAME = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_SAMARRAY_MOVETEXT = MENU_SAMARRAY_RENAME + 1;

	// DATAHUB

	int MENU_DATAHUB_MOVETEXT = MENU_GENERIC_ADDPICTURE + 1;

	// ELC
	int MENU_ELC_ADDTEMPS = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_ELC_EDITSAMS = MENU_ELC_ADDTEMPS + 1;
	int MENU_ELC_CHANGEELCNUMBER = MENU_ELC_EDITSAMS + 1;
	int MENU_ELC_MOVETEXT = MENU_ELC_CHANGEELCNUMBER + 1;

	// TEMP

	int MENU_TEMP_CHANGEVALUES = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_TEMP_MOVETEXT = MENU_TEMP_CHANGEVALUES + 1;

	// TEXT
	int MENU_TEXT_EDITTEXT = MENU_GENERIC_ADDPICTURE + 1;
	
	// gws
	int MENU_GWS_MOVETEXT = MENU_GENERIC_ADDPICTURE + 1;

	// LEGEND
	int MENU_LEGEND_PORTRAIT = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_LEGEND_LANDSCAPE = MENU_LEGEND_PORTRAIT + 1;
	int MENU_LEGEND_SQUEEZEDLANDSCAPE = MENU_LEGEND_LANDSCAPE + 1;

	// ethernetport
	int MENU_ETHERNETPORT_TOGGLE_AVAILABLE = MENU_GENERIC_ADDPICTURE + 1;
	int MENU_ETHERNETPORT_MOVETEXT = MENU_ETHERNETPORT_TOGGLE_AVAILABLE + 1;

	CharSequence[] genericmenuitems = { "Move", "Resize", "Delete",
			"Add Picture (Beta)" };
	CharSequence[][] menuitems = new CharSequence[15][];
	DialogInterface.OnClickListener[] menuclicker = new DialogInterface.OnClickListener[maxitems];

	// mcr items

	AlertDialog menu;
	
	static int imagetab;
	
	Boolean JUSTMOVED = false;

	static int maxitems = 1000;
	static Boolean PLACENEWITEM = false;
	Boolean ITEMSELECTED = false;
	Boolean MOVEITEM = false;
	Boolean MOVETEXT = false;

	static int ceilingheightpixels = 0;

	static int itemselectednumber = -1;

	static int lastposition = 0;

	static int CLOSESTUNFILLEDELCNUMBER = 0;
	static int NEXTITEMDISPLAYNUMBER = 0;

	static int ADDITIONALCHANGEAFTERDIALOG = 0;
	static int ADDITONALCHANGE_DISPLAYNUMBER = 1;
	static int ADDITONALCHANGE_BMSNAME = 2;

	static boolean DONTSCALEORTRANSLATE = false;

	static float absolutex;

	static float absolutey;

	private static float pivotpointx;
	private static float pivotpointy;

	private static float mPosX;
	private static float mPosY;

	static float scrnwidth;
	static float scrnheight;

	static float[] bitheight = new float[Tabs1.maxfloorplans];
	static float[] bitwidth = new float[Tabs1.maxfloorplans];

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
	private static float itemscalefactor = 1.f;

	static Bitmap[] originalbitmap = new Bitmap[Tabs1.maxfloorplans];
	static Bitmap[] bm = new Bitmap[Tabs1.maxfloorplans];

	static Bitmap[] originaliconbitmap = new Bitmap[20];
	static Bitmap[] iconbitmap = new Bitmap[20];

	static float textpadding = 5;

	static Bitmap BMS;
	static Bitmap ELC;
	static Bitmap Gateway;
	static Bitmap SAM;
	static Bitmap MINISAM;
	static Bitmap tinygreencheck;
	static Bitmap tinyredx;
	static Bitmap tempsensor;
	static Bitmap ethernetport;
	static Bitmap distributionboard;
	static Bitmap samarray;
	static Bitmap datahub;
	static Bitmap portraitlegend;
	static Bitmap landscapelegend;
	static Bitmap squeezedlandscapelegend;

	static Bitmap NGBlegend;

	final static int TYPE_NULLITEM = -1;
	final static int TYPE_BMS = 0;
	final static int TYPE_ELC = 1;
	final static int TYPE_GATEWAY = 2;
	final static int TYPE_SAM = 3;
	final static int TYPE_MINISAM = 4;
	final static int TYPE_TEMPSENSOR = 5;
	final static int TYPE_ETHERNETPORT = 6;
	final static int TYPE_DISTRIBUTIONBOARD = 7;
	final static int TYPE_SAMARRAY = 8;
	final static int TYPE_DATAHUB = 9;
	final static int TYPE_ADDTEXT = 10;
	final static int TYPE_LEGEND1 = 11;
	final static int TYPE_LEGEND2 = 12;
	final static int TYPE_LEGEND3 = 13;
	final static int TYPE_LEGEND4 = 14;

	// this must be greatestitemtype all items with higher integer values will
	// not get menus
	final static int TYPE_METERSTOPIXEL = 15;

	final static int TYPE_TINYCHECK = 16;
	final static int TYPE_TINYX = 17;

	static int maxtempsperelc = 8;
	static int ELCCOUNT = 0;
	static int TEMPCOUNT = 0;

	static String itemname;
	static int fontsize;
	static int color;

	static float[] ITEMx = new float[maxitems];
	static float[] ITEMy = new float[maxitems];

	static float[] ITEMtextoffsetx = new float[maxitems];
	static float[] ITEMtextoffsety = new float[maxitems];

	static Rect[] ITEMrect = new Rect[maxitems];
	static Rect[] itemtextrect = new Rect[maxitems];
	static int[] ITEMtype = new int[maxitems];
	static int[] ITEMtempsensorcount = new int[maxitems];
	static int[] ITEMsizepercent = new int[maxitems];
	static int[] ITEMmasterelcnumber = new int[maxitems];
	static int[] ITEMsamscount = new int[maxitems];
	static int[] ITEMdisplaynumber = new int[maxitems];
	static int[] ELCdisplaynumber = new int[maxitems];

	static String[] ITEMstring = new String[maxitems];
	static int[] ITEMfontsize = new int[maxitems];
	static int[] ITEMfontcolor = new int[maxitems];

	static Bitmap[] ITEMbitmap = new Bitmap[maxitems];
	static Bitmap[] ScaledITEMbitmap = new Bitmap[maxitems];
	static float[] SCALEDITEMx = new float[maxitems];
	static float[] SCALEDITEMy = new float[maxitems];

	static float[] ITEMmetersperpixel = new float[maxitems];
	static int[] ITEMfloorplannumber = new int[maxitems];

	static int[] ITEMLINKtabnumber = new int[maxitems];
	static int[] ITEMLINKtabitemnumber = new int[maxitems];

	static Paint p;

	static Rect rect;

	static int adjustitemnum;
	static int adjustelcnum;

	static Bitmap canvasbitmap;

	static Context ctx;

	static int defaulttextsize = 50;

	static int maxelcs = 50;
	static int maxsams = 32;

	static String[] samtitlesstring = { "Load Name", "Phase / CT count",
			"Breaker Size", "CT type", "Load Type", "Loads", "Remove SAM","Panel location","Comments" };
	static int SAMOPTION_LOADNAME = 0;
	static int SAMOPTION_PHASE = 1;
	static int SAMOPTION_BREAKERSIZE = 2;
	static int SAMOPTION_CTTYPE = 3;
	static int SAMOPTION_LOADTYPE = 4;
	static int SAMOPTION_LOADS = 5;
	static int SAMOPTION_PANELLOCATION = 7;
	static int SAMOPTION_COMMENTS = 8;

	
	static TextView[] samcounttv = new TextView[maxelcs];
	static TableLayout[] samtablelayout = new TableLayout[maxelcs];
	static Button[] addsambutton = new Button[maxelcs];
	static ScrollView[] sv = new ScrollView[maxelcs];
	static LinearLayout[] ll = new LinearLayout[maxelcs];
	static int maxvalues = samtitlesstring.length;
	static String[][][] samstrings = new String[maxelcs][maxsams][maxvalues];
	static TableRow[][] samsrow = new TableRow[maxelcs][maxsams];
	static AutoCompleteTextView[][][] samsedittext = new AutoCompleteTextView[maxelcs][maxsams][maxvalues];
	static Spinner[][][] samsspinner = new Spinner[maxelcs][maxsams][maxvalues];
	static ImageView[][] removesambutton = new ImageView[maxelcs][maxsams];
	static View[][][] samview = new View[maxelcs][maxsams][maxvalues];
	static TableRow[][][] samoldviewparent = new TableRow[maxelcs][maxsams][maxvalues];
	static int[][][] samoldviewindex = new int[maxelcs][maxsams][maxvalues];
	static LayoutParams[][][] samoldviewlp = new LayoutParams[maxelcs][maxsams][maxvalues];

	static TextView[][] samtitles = new TextView[maxelcs][maxvalues];

	static TableRow[] samtitlerow = new TableRow[maxelcs];

	static int fp;

	static int NOTABSLINK = -1;
	
	private Handler handler = new Handler();
	

     
	public FloorPlanView(Context context) {
		this(context, null, 0);
	}

	public FloorPlanView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FloorPlanView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		ctx = context;
		fp = FloorPlanActivity.floorplannumber;
		initializevariables();
		createmenuitems();
		setsamdialoglayoutparams();
		// set all items to null
		for (int y = 0; y < ITEMtype.length; y++) {
			ITEMtype[y] = TYPE_NULLITEM;
		}

		resizeiconsseekbar = FloorPlanActivity.resizeiconsseekbar;

		// InputPath = Tabs1.InputFloorPlanLocation[fp];
		// OutputPath = Tabs1.OutputFloorPlanLocation[fp];

		// BitmapFactory.Options o = new BitmapFactory.Options();
		// o.inSampleSize = 2;

		BMS = new BitmapFactory()
				.decodeResource(getResources(), R.drawable.bms);
		iconbitmap[TYPE_BMS] = BMS;
		originaliconbitmap[TYPE_BMS] = BMS;

		ELC = new BitmapFactory()
				.decodeResource(getResources(), R.drawable.elc);
		iconbitmap[TYPE_ELC] = ELC;
		originaliconbitmap[TYPE_ELC] = ELC;

		Gateway = new BitmapFactory().decodeResource(getResources(),
				R.drawable.gateway);
		iconbitmap[TYPE_GATEWAY] = Gateway;
		originaliconbitmap[TYPE_GATEWAY] = Gateway;

		SAM = new BitmapFactory()
				.decodeResource(getResources(), R.drawable.sam);
		iconbitmap[TYPE_SAM] = SAM;
		originaliconbitmap[TYPE_SAM] = SAM;

		MINISAM = Bitmap.createScaledBitmap(SAM, SAM.getWidth() / 2,
				SAM.getHeight() / 2, true);
		iconbitmap[TYPE_MINISAM] = MINISAM;
		originaliconbitmap[TYPE_MINISAM] = MINISAM;

		tempsensor = new BitmapFactory().decodeResource(getResources(),
				R.drawable.tempsensor);
		iconbitmap[TYPE_TEMPSENSOR] = tempsensor;
		originaliconbitmap[TYPE_TEMPSENSOR] = tempsensor;

		ethernetport = new BitmapFactory().decodeResource(getResources(),
				R.drawable.ethernetport);
		ethernetport = Bitmap
				.createScaledBitmap(ethernetport, ethernetport.getWidth() / 2,
						ethernetport.getHeight() / 2, true);
		iconbitmap[TYPE_ETHERNETPORT] = ethernetport;
		originaliconbitmap[TYPE_ETHERNETPORT] = ethernetport;

		distributionboard = new BitmapFactory().decodeResource(getResources(),
				R.drawable.distributionpanel);
		iconbitmap[TYPE_DISTRIBUTIONBOARD] = distributionboard;
		originaliconbitmap[TYPE_DISTRIBUTIONBOARD] = distributionboard;

		samarray = new BitmapFactory().decodeResource(getResources(),
				R.drawable.samarray);
		iconbitmap[TYPE_SAMARRAY] = samarray;
		originaliconbitmap[TYPE_SAMARRAY] = samarray;

		datahub = new BitmapFactory().decodeResource(getResources(),
				R.drawable.datahub);
		iconbitmap[TYPE_DATAHUB] = datahub;
		originaliconbitmap[TYPE_DATAHUB] = datahub;

		landscapelegend = new BitmapFactory().decodeResource(getResources(),
				R.drawable.componentstable2);

		iconbitmap[TYPE_LEGEND1] = landscapelegend;
		originaliconbitmap[TYPE_LEGEND1] = landscapelegend;

		portraitlegend = new BitmapFactory().decodeResource(getResources(),
				R.drawable.componentstable1);

		iconbitmap[TYPE_LEGEND2] = portraitlegend;
		originaliconbitmap[TYPE_LEGEND2] = portraitlegend;

		squeezedlandscapelegend = new BitmapFactory().decodeResource(
				getResources(), R.drawable.componentstable3);

		iconbitmap[TYPE_LEGEND3] = squeezedlandscapelegend;
		originaliconbitmap[TYPE_LEGEND3] = squeezedlandscapelegend;

		NGBlegend = new BitmapFactory().decodeResource(getResources(),
				R.drawable.ngblegend);

		iconbitmap[TYPE_LEGEND4] = NGBlegend;
		originaliconbitmap[TYPE_LEGEND4] = NGBlegend;

		for (int h = 0; h < FloorPlanActivity.floorplancount; h++) {
			
			BitmapFactory.Options o=new BitmapFactory.Options();
		
			int multiplyer=0;
			int samplerate=2;
			
			//File fpfile=new File(Tabs1.floorplanstrings.get(h));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			
			//Returns null, sizes are in the options variable
			BitmapFactory.decodeFile(Tabs1.floorplanstrings.get(h), options);
			int width = options.outWidth;
			int height = options.outHeight;
			//If you want, the MIME type will also be decoded (if possible)
			String type = options.outMimeType;
			File fpfile=new File(Tabs1.floorplanstrings.get(h));
			u.log(fpfile.length()+" "+getmaxfloorplansize());
				if(!((fpfile).length()>getmaxfloorplansize())){
					
			originalbitmap[h] = BitmapFactory.decodeFile(Tabs1.floorplanstrings.get(h));
			System.out.println("old Bytes"+new File(Tabs1.floorplanstrings.get(h)).length());
				}else{
					originalbitmap[h]=decodeSampledBitmapFromResource(Tabs1.floorplanstrings.get(h),Tabs1.absolutescreenwidth, Tabs1.absolutescreenwidth/(width*height));
			}
			
			bm[h] = originalbitmap[h];
			bitheight[h] = bm[h].getHeight();
			bitwidth[h] = bm[h].getWidth();
		}
		
		int tinymarkerscale = 4;
		Bitmap bitmap;
		bitmap = new BitmapFactory().decodeResource(getResources(),
				R.drawable.tinygreencheck);
		tinygreencheck = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()
				/ tinymarkerscale, bitmap.getHeight() / tinymarkerscale, true);
		iconbitmap[TYPE_TINYCHECK] = tinygreencheck;
		originaliconbitmap[TYPE_TINYCHECK] = tinygreencheck;

		bitmap = new BitmapFactory().decodeResource(getResources(),
				R.drawable.tinyredx);
		tinyredx = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()
				/ tinymarkerscale, bitmap.getHeight() / tinymarkerscale, true);
		iconbitmap[TYPE_TINYX] = tinyredx;
		originaliconbitmap[TYPE_TINYX] = tinyredx;

		Log.d("bitwidth,bitheight", bitwidth + " " + bitheight);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		/*
		 * Display display = wm.getDefaultDisplay(); Point size = new Point();
		 * display.getSize(size); scrnwidth = size.x; scrnheight = size.y;
		 */
		DisplayMetrics displaymetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displaymetrics);
		scrnheight = displaymetrics.heightPixels;
		scrnwidth = displaymetrics.widthPixels;

		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

		p = new Paint();
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setDither(true);
		p.setColor(Color.WHITE);
		p.setStrokeWidth(10);
		p.setTextSize(defaulttextsize);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Let the ScaleGestureDetector inspect all events.
		mScaleDetector.onTouchEvent(ev);

		final int action = ev.getAction();
		
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			
			
			 handler.postDelayed(runnable, 1000);
		     longpresscancelled=false;
			
			try{
			final float x = ev.getX();
			final float y = ev.getY();

			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = ev.getPointerId(0);

			mLastDownX = x;
			mLastDownY = y;

			float percentofscreenx = mLastTouchX / scrnwidth;
			float percentofscreeny = mLastTouchY / scrnheight;

			invalidate();

			absolutex = (rect.right - rect.left) * percentofscreenx + rect.left;
			absolutey = (rect.bottom - rect.top) * percentofscreeny + rect.top;

			//

			checkifitemselected: for (int c = 0; c < i; c++) {
				if (ITEMrect[c].contains((int) absolutex, (int) absolutey)
						&& ITEMfloorplannumber[c] == fp) {
					ITEMSELECTED = true;
					itemselectednumber = c;
					break checkifitemselected;
				} else {
					ITEMSELECTED = false;
				}
			}
			}catch(NullPointerException e){
				System.out.println("too much touching during loading floorplan");
				e.printStackTrace();
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			
			if (MOVEITEM) {

				Log.d("itemselectednumber", u.s(itemselectednumber));

				final float x = ev.getX();
				final float y = ev.getY();

				mLastUpX = x;
				mLastUpY = y;

				Log.d("Move held down", x + " " + y);
				// Get absolute position of finger touch.

				float percentofscreenx = mLastUpX / scrnwidth;
				float percentofscreeny = mLastUpY / scrnheight;

				// invalidate();

				absolutex = (rect.right - rect.left) * percentofscreenx
						+ rect.left;
				absolutey = (rect.bottom - rect.top) * percentofscreeny
						+ rect.top;

				ITEMx[itemselectednumber] = absolutex;
				ITEMy[itemselectednumber] = absolutey;

				refreshclickablearea(itemselectednumber, FloorPlanActivity.FINALBITMAPSCALE);
				
				Log.d("itemxy and rect", ITEMx[itemselectednumber] + ","
						+ ITEMy[itemselectednumber] + ","
						+ ITEMrect[itemselectednumber].bottom);

				invalidate();
			} else if (MOVETEXT) {
				Log.d("itemselectednumber", u.s(itemselectednumber));

				final float x = ev.getX();
				final float y = ev.getY();

				mLastUpX = x;
				mLastUpY = y;

				Log.d("Move held down", x + " " + y);
				// Get absolute position of finger touch.

				float percentofscreenx = mLastUpX / scrnwidth;
				float percentofscreeny = mLastUpY / scrnheight;

				// invalidate();

				absolutex = (rect.right - rect.left) * percentofscreenx
						+ rect.left;
				absolutey = (rect.bottom - rect.top) * percentofscreeny
						+ rect.top;

				// ITEMx[itemselectednumber] = absolutex;
				// ITEMy[itemselectednumber] = absolutey;

				ITEMtextoffsetx[itemselectednumber] = absolutex
						- ITEMx[itemselectednumber];
				ITEMtextoffsety[itemselectednumber] = absolutey
						- ITEMy[itemselectednumber];

				Log.d("itemxy and rect", ITEMx[itemselectednumber] + ","
						+ ITEMy[itemselectednumber] + ","
						+ ITEMrect[itemselectednumber].bottom);

				invalidate();
			} else {
				final int pointerIndex = ev.findPointerIndex(mActivePointerId);
				final float x = ev.getX();
				final float y = ev.getY();

				// Only move if the ScaleGestureDetector isn't processing a
				// gesture.
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
					
					if(distx>longpresstolerance||disty>longpresstolerance){
						longpresscancelled=true;
					}
				} catch (IllegalArgumentException e) {

				}

			}
			break;
		}

		case MotionEvent.ACTION_UP: {
			longpresscancelled=true;
			try{
			Log.d("event", "actionup occurred");
			if (MOVEITEM) {
				if (ITEMtype[itemselectednumber] == TYPE_TEMPSENSOR
						|| ITEMtype[itemselectednumber] == TYPE_ELC) {
					try {
						AUTORENUMBERalltempsensorsbasedonlocation();
					} catch (Throwable e) {

					}
				}
				MOVEITEM = false;
				FloorPlanActivity.writeonedbitem(itemselectednumber);
			}
			if (MOVETEXT) {
				MOVETEXT = false;
				FloorPlanActivity.writeonedbitem(itemselectednumber);
			}

			final float x = ev.getX();
			final float y = ev.getY();

			mLastUpX = x;
			mLastUpY = y;

			Log.d("lastup", x + " " + y);
			// Get absolute position of finger touch.

			float percentofscreenx = mLastTouchX / scrnwidth;
			float percentofscreeny = mLastTouchY / scrnheight;

			invalidate();

			absolutex = (rect.right - rect.left) * percentofscreenx + rect.left;
			absolutey = (rect.bottom - rect.top) * percentofscreeny + rect.top;

			showlongandlat(absolutex, absolutey);

			// ACTION UP
			if (Math.abs(mLastUpX - mLastDownX) < 20
					&& Math.abs(mLastUpY - mLastDownY) < 20) {

				Log.d("event", "actionup was local (no finger drag)");
				if (!(FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_GETSCALEFROMPOINTS)) {
					
					u.log("checkingifitemselected");
					checkifitemselected: for (int c = 0; c < i; c++) {
						u.log("checking item "+c);
						if(itemtextrect[c]==null){
							
							itemtextrect[c]=new Rect(0,0,0,0);
						}
						
						
						u.log("Rect of item being checked is "+ITEMrect[c].left+" "+ITEMrect[c].top+" "+ITEMrect[c].right+" "+ITEMrect[c].bottom+" ");
						u.log("absolutex="+absolutex+"absolutey="+absolutey);
						if ((ITEMrect[c].contains((int) absolutex,
								(int) absolutey)||itemtextrect[c].contains((int) absolutex,
										(int) absolutey))
								&& ITEMfloorplannumber[c] == fp) {
							Log.d("event", "actionup was inside an item rect");
							ITEMSELECTED = true;
							itemselectednumber = c;
							break checkifitemselected;
						} else {
							Log.d("event", "actionup was outside an item rect");
							ITEMSELECTED = false;
						}
					}

					// double tap stuff

					if (ITEMSELECTED) {
						Log.d("Item Selected", u.s(itemselectednumber));
						try{
							showitemmenu(itemselectednumber, gettabofitemselected(itemselectednumber));
						}catch(Throwable e){
							e.printStackTrace();
						}
						ITEMSELECTED = false;

					} else if (!ITEMSELECTED) {
						Log.d("event",
								"since we're outside an item rect we can verify that we are not moving an item, and that mode isn't dothing");
						Log.d("event", "MOVEITEM=" + MOVEITEM
								+ "  FloorPlanActivity.MODE="
								+ FloorPlanActivity.MODE);
						if (!MOVEITEM
								&& !MOVETEXT
								&& !(FloorPlanActivity.MODE == FloorPlanActivity.MODE_DONOTHING)) {

							PLACENEWITEM = true;

							setsomeinitialnewitemvalues();

							Log.d("event",
									"since mode wasn't donothing and we weren't moving item, PLACENEWITE");
							invalidate();

						}
					}

					// if (MOVEITEM) {
					// ITEMx[itemselectednumber] = absolutex;
					// ITEMy[itemselectednumber] = absolutey;
					// float buttonleft = absolutex
					// - (ITEMbitmap[itemselectednumber].getWidth() / 2);
					// float buttontop = absolutey
					// - (ITEMbitmap[itemselectednumber].getHeight() / 2);
					// float buttonright = absolutex
					// + (ITEMbitmap[itemselectednumber].getWidth() / 2);
					// float buttonbottom = absolutey
					// + (ITEMbitmap[itemselectednumber].getHeight() / 2);
					// ITEMrect[itemselectednumber] = new Rect((int)
					// (buttonleft),
					// (int) (buttontop), (int) (buttonright),
					// (int) (buttonbottom));
					// MOVEITEM = false;
					// invalidate();
					// }
				} else {
					System.out.println("doing get scale from points stuff");
					dogetscalefrompointsstuff();

				}
			}
			mActivePointerId = INVALID_POINTER_ID;
			break;
			}catch(NullPointerException e){
				System.out.println("too much screen touching on fp load");
				e.printStackTrace();
			}
		}

		case MotionEvent.ACTION_CANCEL: {
			longpresscancelled=true;
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

		if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_FLOORPLANPREVIOUS) {
			Log.d("fp", u.s(fp));
			Log.d("floorplancount", u.s(FloorPlanActivity.floorplancount));
			if ((fp - 1) >= 0) {
				FloorPlanActivity.floorplannumber--;
			} else {
				FloorPlanActivity.floorplannumber = FloorPlanActivity.floorplancount - 1;
			}
			fp = FloorPlanActivity.floorplannumber;
			switchfloorplan(fp);
			FloorPlanActivity.ACTION = FloorPlanActivity.ACTION_DONOTHING;

		}
		if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_FLOORPLANNEXT) {
			Log.d("fp", u.s(fp));
			if (!((fp + 1) >= FloorPlanActivity.floorplancount)) {
				FloorPlanActivity.floorplannumber++;
			} else {
				FloorPlanActivity.floorplannumber = 0;
			}
			fp = FloorPlanActivity.floorplannumber;
			switchfloorplan(fp);

			FloorPlanActivity.ACTION = FloorPlanActivity.ACTION_DONOTHING;
		}

		canvas.save();
		canvas.drawColor(Color.BLACK);

		ITEMx[i] = absolutex;
		ITEMy[i] = absolutey;

		if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_DONOTHING) {
			PLACENEWITEM = false;
		}
		if (!DONTSCALEORTRANSLATE)
			canvas.translate(mPosX, mPosY);

		float px = (distx / 2 - mPosX);
		float py = (disty / 2 - mPosY);
		pivotpointx = px;
		pivotpointy = py;

		if (!DONTSCALEORTRANSLATE)
			canvas.scale(mScaleFactor, mScaleFactor, px, py);
		// mIcon.draw(canvas);
		int FINALBITMAPSCALE = FloorPlanActivity.FINALBITMAPSCALE;

		if (FINALBITMAPSCALE != 1) {
			p.setTextSize(p.getTextSize() / FINALBITMAPSCALE);
			bm[fp] = Bitmap.createScaledBitmap(bm[fp], bm[fp].getWidth()
					/ FINALBITMAPSCALE, bm[fp].getHeight() / FINALBITMAPSCALE,
					true);
		}

		if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_SHOWLAYERS) {
			// draw current floor plan
			canvas.drawBitmap(bm[fp], 0, 0, p);
			p.setAlpha(100);
			// draw everything after it
			for (int h = (fp + 1); h < FloorPlanActivity.floorplancount; h++) {
				canvas.drawBitmap(bm[h], 0, 0, p);
			}
			// draw everything before it
			if (!(fp == 0)) {
				for (int h = 0; h < fp; h++) {
					canvas.drawBitmap(bm[h], 0, 0, p);
				}
			}
			p.setAlpha(255);
		} else {
			canvas.drawBitmap(bm[fp], 0, 0, p);
		}

		// Get absolute position of finger touch.
		rect = canvas.getClipBounds();
		int width = rect.right - rect.left;
		if (width == FloorPlanActivity.righttoolbar.getWidth()
				|| width == FloorPlanActivity.righttoolbarscrollview.getWidth()
				|| width == FloorPlanActivity.righttoolbarbuttonparent
						.getWidth()) {
			TOOLBARSELECTED = true;
			System.out.println("TOOLBARSELECTED" + TOOLBARSELECTED);
		} else {
			TOOLBARSELECTED = false;
			System.out.println("TOOLBARSELECTED" + TOOLBARSELECTED);
		}

		if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_RESIZEICONS) {
			resizeicons();
			FloorPlanActivity.view.invalidate();
		}
		if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_RESIZEICON) {
			resizeicons();
			FloorPlanActivity.view.invalidate();
		}

		// ///////////////////////////////////////////////
		placeitem: if (PLACENEWITEM) {

			// Remove all left over redo items
			for (int w = i + 1; w < ITEMtype.length; w++) {
				if (!(ITEMtype[w] == -1)) {
					reinitializevariable(w);
				}
			}

			Bitmap bitmap = BMS;
			int type = 0;
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_BMS) {

				type = TYPE_BMS;
				bitmap = iconbitmap[type];
				
				ITEMstring[i]="BMS "+u.s(getitemcount(TYPE_BMS)+1);
				if(!FloorPlanActivity.RAPIDPLACEMENT){
					getstringdialog("Please name this BMS", i, ITEMstring);
				}
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_ELC) {

				try{
					ITEMsamscount[i]=getsamcountfromdb(i);
				}catch(Throwable e){
					u.log("tried to get sam count from tabs, but likely there aren't any sams");
				}
				ELCdisplaynumber[i] = smallestelcnumbernotinuse();
				CLOSESTUNFILLEDELCNUMBER=ELCdisplaynumber[i];
				Log.d("ELCdisplaynumber of item " + i, u.s(ELCdisplaynumber[i]));
				type = TYPE_ELC;
				bitmap = iconbitmap[type];
				ELCCOUNT++;
				
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_GATEWAY) {
				ITEMstring[i]=u.s(getitemcount(TYPE_GATEWAY)+1);
				type = TYPE_GATEWAY;
				bitmap = iconbitmap[type];
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_ETHERNETPORT) {
				type = TYPE_ETHERNETPORT;
				bitmap = iconbitmap[type];
				// set value to check by default
				ITEMdisplaynumber[i] = TYPE_TINYCHECK;
				
				ITEMstring[i]=u.s(getitemcount(TYPE_ETHERNETPORT)+1);
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_SAM) {
				// type = TYPE_SAM;
				// bitmap = iconbitmap[type];
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_ADDTEXT) {

				type = TYPE_ADDTEXT;
				bitmap = null;
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_TEMPSENSOR) {

				int totaltemps = 0;
				for (int m = 0; m < i; m++) {
					totaltemps = totaltemps + ITEMtempsensorcount[m];
				}

				int tempsthisfloor = 0;
				int elcsthisfloor = 0;
				for (int m = 0; m < i; m++) {
					if (ITEMfloorplannumber[m] == fp) {
						tempsthisfloor = tempsthisfloor
								+ ITEMtempsensorcount[m];
						if (ITEMtype[m] == TYPE_ELC) {
							elcsthisfloor = elcsthisfloor + 1;
						}
					}
				}

				Log.d("totaltemps, maxtemsp*elccount", u.s(totaltemps) + "  "
						+ maxtempsperelc + "  " + ELCCOUNT);
				if (FloorPlanActivity.MULTILEVEL) {
					if (totaltemps >= (int) ((double) maxtempsperelc * (double) ELCCOUNT)) {
						FloorPlanActivity.MODE = FloorPlanActivity.MODE_DONOTHING;
						showToast("Add More ELCs");
						break placeitem;
					} else {
						type = TYPE_TEMPSENSOR;
						bitmap = iconbitmap[type];
						ITEMtype[i] = type;
					}
				} else {
					if ((tempsthisfloor >= (int) ((double) maxtempsperelc * (double) elcsthisfloor))
							&& (!(totaltemps >= (int) ((double) maxtempsperelc * (double) ELCCOUNT)))) {
						FloorPlanActivity.MODE = FloorPlanActivity.MODE_DONOTHING;
						showToast("You have extra ELCs on another floor plan. Select Multi Level from Preferences");
						break placeitem;
					} else if (totaltemps >= (int) ((double) maxtempsperelc * (double) ELCCOUNT)) {
						FloorPlanActivity.MODE = FloorPlanActivity.MODE_DONOTHING;
						showToast("Add More ELCs");
						break placeitem;
					} else {
						type = TYPE_TEMPSENSOR;
						bitmap = iconbitmap[type];
						ITEMtype[i] = type;
						

					}
					
				}
				
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_DISTRIBUTIONBOARD) {
				type = TYPE_DISTRIBUTIONBOARD;
				bitmap = iconbitmap[type];
				if(!FloorPlanActivity.RAPIDPLACEMENT){
					getstringdialog("Please name this distribution board", i,
						ITEMstring);
					
				}
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_SAMARRAY) {
				type = TYPE_SAMARRAY;
				bitmap = iconbitmap[type];
				if(!FloorPlanActivity.RAPIDPLACEMENT){
					getsamarraystringsdialog(i);
				}
			}
			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_DATAHUB) {
				type = TYPE_DATAHUB;
				bitmap = iconbitmap[type];
				int dhcount=1;
				for(int t=0; t<i; t++){
					if(ITEMtype[t]==TYPE_DATAHUB){
						dhcount++;
					}
				}
				ITEMstring[i]=u.s(dhcount);
				updatetabsdatabases(i);
			}

			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_LEGEND) {
				break placeitem;
//				if (!FloorPlanActivity.NGBICONS) {
//					type = TYPE_LEGEND1;
//					bitmap = iconbitmap[TYPE_LEGEND1];
//				} else {
//					type = TYPE_LEGEND4;
//					bitmap = iconbitmap[TYPE_LEGEND4];
//				}

			}

			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_METERSTOPIXEL) {
				ITEMmetersperpixel[i] = FloorPlanActivity.metersperpixel;
				type = TYPE_METERSTOPIXEL;
			}

			if (FloorPlanActivity.NGBICONS
					&& (type == TYPE_LEGEND1 || type == TYPE_LEGEND2 || type == TYPE_LEGEND3)) {
				ITEMtype[i] = TYPE_LEGEND4;
			} else if ((!FloorPlanActivity.NGBICONS) && (type == TYPE_LEGEND4)) {
				ITEMtype[i] = TYPE_LEGEND1;
			} else {
				ITEMtype[i] = type;
			}

			ITEMbitmap[i] = bitmap;

			refreshclickablearea(i,FloorPlanActivity.FINALBITMAPSCALE);
			
			

			if (FloorPlanActivity.MODE == FloorPlanActivity.MODE_METERSTOPIXEL) {
				ITEMrect[i] = new Rect(0, 0, 0, 0);
			}
			if (ITEMtype[i] == TYPE_TEMPSENSOR || ITEMtype[i] == TYPE_ELC) {
				try {
					if(FloorPlanActivity.AUTORENUMBERTEMPS){
						AUTORENUMBERalltempsensorsbasedonlocation();
					}else{
						if(totaltempcount(i,CLOSESTUNFILLEDELCNUMBER)<maxtempsperelc){
						manualtempsensornumbering(i);
						}else{
							break placeitem;
						}
					}
					
				} catch (Throwable e) {

				}
				
			}
			if (ITEMtype[i] == TYPE_TEMPSENSOR ) {
				if(!FloorPlanActivity.RAPIDPLACEMENT){
					gettemperaturestringsdialog(i);
				}
			}
			
			
			i++;

			PLACENEWITEM = false;
			Log.d("i, mode, x, y", i + " " + FloorPlanActivity.MODE + " "
					+ ITEMx[i] + " " + ITEMx[i]);

			// bitmap.recycle();
		
			if(!(ITEMtype[i-1]==TYPE_ADDTEXT)&&
					!(ITEMtype[i-1]==TYPE_LEGEND1)&&
					!(ITEMtype[i-1]==TYPE_LEGEND2)&&
					!(ITEMtype[i-1]==TYPE_LEGEND3)&&
					!(ITEMtype[i-1]==TYPE_LEGEND4))
			{
				
			//	createandlinkitemontabs(i - 1, gettabofitemselected(i-1));
			
				
			}
			if (ITEMtype[i-1] == TYPE_TEMPSENSOR ) {
				//addtempvaluesdb();
			}
			FloorPlanActivity.writeonedbitem(i-1);
		}
		// canvas.drawCircle(absolutex, absolutey, 50, p);

		// CYCLE LOOP THAT RUNS THROUGH ALL ITEMS
		if (!(FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_GETSCALEFROMPOINTS)) {
			if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_SHOWLAYERS) {
				for (int w = 0; w < i; w++) {
					if (ITEMfloorplannumber[w] == fp) {
						drawitem(w, canvas, FINALBITMAPSCALE);
					} else {
						p.setAlpha(100);
						drawitem(w, canvas, FINALBITMAPSCALE);
						p.setAlpha(255);
					}
					if (SHOWBUTTONRECTS == true) {
						canvas.drawRect(ITEMrect[w], p);
					}
				}

			} else {
				for (int w = 0; w < i; w++) {
					if (ITEMfloorplannumber[w] == fp) {
						drawitem(w, canvas, FINALBITMAPSCALE);
					}
					if (SHOWBUTTONRECTS == true) {
						canvas.drawRect(ITEMrect[w], p);
					}
				}
				if (FloorPlanActivity.DRAWSAMREFERENCE == true) {
					drawSAMreferencetable(canvas, p);
				}
			}

			if (!DONTSCALEORTRANSLATE && !TOOLBARSELECTED) {
				drawscale(canvas);
			}
		} else {
			System.out.println("in scale stage "
					+ FloorPlanActivity.GETSCALESTAGE
					+ " and trying to draw point");
			if (FloorPlanActivity.GETSCALESTAGE == FloorPlanActivity.STAGE_GETSECONDPOINT) {
				float screenheight = rect.bottom - rect.top;
				System.out.println(firstscalepointx + " " + firstscalepointy
						+ " " + screenheight + " " + p.getColor() + " "
						+ ((float) screenheight * ((float) 1 / (float) 100)));
				p.setColor(presentableblue);
				canvas.drawCircle(firstscalepointx, firstscalepointy,
						(float) screenheight * ((float) 1 / (float) 100), p);
				p.setStrokeWidth((float) screenheight
						* ((float) .5 / (float) 100));
				canvas.drawLine(firstscalepointx, firstscalepointy, absolutex,
						absolutey, p);
				canvas.drawCircle(absolutex, absolutey, (float) screenheight
						* ((float) 1 / (float) 100), p);
			}
		}

		canvas.restore();
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();

			// Don't let the object get too small or too large.
			// Increase this to be able to zoom in closer (slows down process)
			float maxzoom = 10.0f;

			// Increase this to be able to zoom out farther
			float minzoom = 0.1f;

			mScaleFactor = Math.max(minzoom, Math.min(mScaleFactor, maxzoom));

			invalidate();
			return true;
		}
	}
	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() {
		      /* do what you need to do */
			   if(!longpresscancelled){
			      System.out.println("Long Press!");
			      /* and here comes the "trick" */
			      handler.postDelayed(this, 1000);
			   }
		   }
		};
	void showitemmenu(final int itemselectednumber, int imagetab) {

		final int type = ITEMtype[itemselectednumber];
		final CharSequence[] items = menuitems[type];
		Log.d("type in showitemmenu", u.s(type));
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setTitle(getGenericDisplayText(itemselectednumber)
				+ "       Make your selection");
		builder.setIcon(new BitmapDrawable(
				originaliconbitmap[ITEMtype[itemselectednumber]]));
		// builder.setItems( menuitems[type], new
		// DialogInterface.OnClickListener() {
	
		HorizontalScrollView view = new HorizontalScrollView(ctx);
		view.setLayoutParams(Tabs1.lpfw);

		TableRow row = new TableRow(ctx);
		row.setLayoutParams(Tabs1.lpfw);

		TableLayout tl = new TableLayout(ctx);
		tl.setLayoutParams(Tabs1.lpfw);

		TableRow.LayoutParams tr10lp = new TableRow.LayoutParams();
		tr10lp.weight = (1 / 4);
		
		File folder=new File(Tabs1.ProjectDirectory
				+ "/"
				+ Tabs1.basedirectory[getdirectorynum(gettabofitemselected(itemselectednumber))]
				+ "/"
				+ getGenericDisplayText(itemselectednumber)
						.toString());
		u.log(folder);
		if (folder.exists()){
		
		
		for (final File picture: folder.listFiles()) {
			
			if(u.issupportedimage(picture.getAbsoluteFile())){
				u.log(picture.getAbsolutePath());
				
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inSampleSize = Tabs1.PICTURESAMPLESIZE;
			int displaypercentofscreen = 25;
			Bitmap imagebitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), o);
			double calcheight = (double) Tabs1.screenheight
					* (double) displaypercentofscreen / (double) 100;
			double calcwidth = (double) imagebitmap.getWidth()
					/ (double) imagebitmap.getHeight() * (double) calcheight;

			int height = (int) calcheight;
			int width = (int) calcwidth;

			Bitmap resizedbitmap = null;

			resizedbitmap = Bitmap.createScaledBitmap(imagebitmap, width,
					height, true);
			
			imagebitmap.recycle();
			
			ImageView imageview = new ImageView(ctx);
			imageview.setLayoutParams(Tabs1.lpww);
			imageview.setImageBitmap(resizedbitmap);
			imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					try {
						

						try {
							FloorPlanActivity.editpicturelocation = picture.getAbsolutePath();
						
							FloorPlanActivity.hiddeneditpicturebutton
									.performClick();
							menu.dismiss();
						} catch (ActivityNotFoundException e) {
							Tabs1.showneedmoresoftwaredialog(
									"ASTRO File Manager/Browser",
									"com.metago.astro");
						}

					} catch (IndexOutOfBoundsException e) {

					}
				}
			});
			imageview.setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View v) {

					
					try {

						Log.d("This is the view pressed", v.toString());
						TableRow parentrow = (TableRow) v.getParent();
						deleteorduplicatepicturedialog(
								new File(picture.getAbsolutePath()), (ImageView) v,
								parentrow);

					} catch (IndexOutOfBoundsException e) {

					}

					return false;
				}

			});
			row.addView(imageview);
			}
		}
		}
		tl.addView(row);
		view.addView(tl);
		builder.setView(view);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int menuitem) {
				// Do something with the selection

				executemenuselection(type, menuitem, itemselectednumber);
				

			}
		});
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				 System.gc();
			}
		});
//		builder.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss(DialogInterface arg0) {
//				// TODO Auto-generated method stub
//			}
//		});
		menu = builder.create();
		menu.show();
	}

	private void createmenuitems() {
		//
		for (int i = 0; i < TYPE_LEGEND4 + 1; i++) {

			// add initial generic menu values
			ArrayList<String> itemstoadd = new ArrayList<String>();
			int w = 0;
			for (w = 0; w < genericmenuitems.length; w++) {
				itemstoadd.add(genericmenuitems[w].toString());
				Log.d("menuitems[i][w] getting value genericmenuitems[w]",
						genericmenuitems[w].toString());
			}
			if (i == TYPE_ELC) {
				itemstoadd.add("Add Temp Sensors");
				itemstoadd.add("Add/Modify/Delete SAMs");
				itemstoadd.add("Swap Number with another ELC");
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_TEMPSENSOR) {
				itemstoadd.add("Edit Temperature Sensor Values");
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_GATEWAY) {
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_ADDTEXT) {
				itemstoadd.add("Edit");

			}
			if (i == TYPE_BMS) {
				itemstoadd.add("Name this BMS or Controller");
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_DISTRIBUTIONBOARD) {
				itemstoadd.add("Rename this board or panel");
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_SAMARRAY) {
				itemstoadd.add("Edit Array Values");
				itemstoadd.add("Move Text");

			}
			if (i == TYPE_DATAHUB) {
				
				itemstoadd.add("Move Text");

			}

			if (i == TYPE_LEGEND1 || i == TYPE_LEGEND2 || i == TYPE_LEGEND3) {
				itemstoadd.add("Portrait");
				itemstoadd.add("Landscape");
				itemstoadd.add("Squeezed Landscape");
			}
			if (i == TYPE_ETHERNETPORT) {
				itemstoadd.add("Toggle Ethernet Port Available");
				itemstoadd.add("Move Text");
			}

			menuitems[i] = new CharSequence[itemstoadd.size()];
			for (int u = 0; u < itemstoadd.size(); u++) {
				menuitems[i][u] = itemstoadd.get(u);
			}
		}

	}

	private void executemenuselection(int type, int menuitem,
			int itemselectednumber) {
		this.itemselectednumber=itemselectednumber;
		if (menuitem == MENU_GENERIC_MOVE) {
			Log.d("menuitemselected", "Move");
			MOVEITEM = true;

		}
		if (menuitem == MENU_GENERIC_RESIZE) {
			// this.itemselectednumber=itemselectednumber;
			resizeboolean = true;
			FloorPlanActivity.ACTION = FloorPlanActivity.ACTION_RESIZEICON;
			FloorPlanActivity.toptoolbar.setVisibility(View.INVISIBLE);
			FloorPlanActivity.righttoolbar.setVisibility(View.INVISIBLE);

			FloorPlanActivity.resizeiconsseekbar.setVisibility(View.VISIBLE);
			FloorPlanActivity.resizeiconscancel.setVisibility(View.VISIBLE);
			FloorPlanActivity.resizeiconsfinished.setVisibility(View.VISIBLE);
			Log.d("menuitemselected", "Resize");

		}
		if (menuitem == MENU_GENERIC_DELETE) {

			Log.d("menuitemselected", "Delete");
			deleteitem(itemselectednumber);
		}
		if (menuitem == MENU_GENERIC_ADDPICTURE) {
			
			 Log.d("menuitemselected", "add picture");
			 Log.d("itemtype of item selected for add picture",u.s(ITEMtype[itemselectednumber]));
			 this.itemselectednumber=itemselectednumber;
			 if(!(ITEMtype[itemselectednumber]==TYPE_LEGEND1||ITEMtype[itemselectednumber]==TYPE_LEGEND2||ITEMtype[itemselectednumber]==TYPE_LEGEND3||ITEMtype[itemselectednumber]==TYPE_LEGEND4||ITEMtype[itemselectednumber]==TYPE_ADDTEXT||ITEMtype[itemselectednumber]==TYPE_SAM)){
				 System.gc();
				
				 FloorPlanActivity.hiddenaddpicturebutton.performClick();
				 
			 }

		}

		if (type == TYPE_ELC) {
			if (menuitem == MENU_ELC_ADDTEMPS) {
				Log.d("menuitemselected", "Add Temp Sensors");
				FloorPlanActivity.MODE = FloorPlanActivity.MODE_TEMPSENSOR;

				CLOSESTUNFILLEDELCNUMBER = ELCdisplaynumber[itemselectednumber];

			}
			if (menuitem == MENU_ELC_EDITSAMS) {

				addsamsdialog(
						"ELC# " + u.s(ELCdisplaynumber[itemselectednumber])
								+ ": SAMs Menu", itemselectednumber);

			}
			if (menuitem == MENU_ELC_CHANGEELCNUMBER) {
				Log.d("menuitemselected", "Change ELC Number");
				getintdialog(
						"Please choose the ELC number with which you'd like to swap",
						itemselectednumber, ELCdisplaynumber);
			}
			if (menuitem == MENU_ELC_MOVETEXT) {
				MOVETEXT = true;
			}
		}

		if (type == TYPE_TEMPSENSOR) {

			if (menuitem == MENU_TEMP_CHANGEVALUES) {
				Log.d("menuitemselected", "Change Master ELC Number");
				gettemperaturestringsdialog(itemselectednumber);
				ADDITIONALCHANGEAFTERDIALOG = ADDITONALCHANGE_DISPLAYNUMBER;
			}
			if (menuitem == MENU_TEMP_MOVETEXT) {
				MOVETEXT = true;
			}
		}
		if (type == TYPE_ADDTEXT) {
			if (menuitem == MENU_TEXT_EDITTEXT) {
				FloorPlanActivity.getaddtextdialog("Edit Text",
						itemselectednumber, ctx).show();

			}

		}
		if (type == TYPE_BMS) {
			if (menuitem == MENU_BMS_NAMEBMS) {

				getstringdialog("Please name this BMS", itemselectednumber,
						ITEMstring);
			}
			if (menuitem == MENU_BMS_MOVETEXT) {
				MOVETEXT = true;
			}

		}
		if (type == TYPE_DISTRIBUTIONBOARD) {
			if (menuitem == MENU_DISTRIBUTIONBOARD_RENAME) {
				getstringdialog("Please rename this board or panel",
						itemselectednumber, ITEMstring);
			}
			if (menuitem == MENU_DISTRIBUTIONBOARD_MOVETEXT) {
				MOVETEXT = true;
			}

		}
		if (type == TYPE_SAMARRAY) {
			if (menuitem == MENU_SAMARRAY_RENAME) {
				getsamarraystringsdialog(itemselectednumber);

			}
			if (menuitem == MENU_SAMARRAY_MOVETEXT) {
				MOVETEXT = true;
			}

		}
		if (type == TYPE_DATAHUB) {

			if (menuitem == MENU_DATAHUB_MOVETEXT) {
				MOVETEXT = true;
			}

		}
		if (type == TYPE_GATEWAY) {

			if (menuitem == MENU_GWS_MOVETEXT) {
				MOVETEXT = true;
			}

		}
		if (type == TYPE_LEGEND1 || type == TYPE_LEGEND2
				|| type == TYPE_LEGEND3) {

			if (menuitem == MENU_LEGEND_PORTRAIT) {
				Log.d("legend", "portrait slected");
				ITEMstring[itemselectednumber] = "portrait";
				ITEMbitmap[itemselectednumber] = portraitlegend;
				ITEMtype[itemselectednumber] = TYPE_LEGEND2;
				FloorPlanActivity.view.invalidate();
			}

			if (menuitem == MENU_LEGEND_LANDSCAPE) {

				ITEMstring[itemselectednumber] = "landscape";
				ITEMbitmap[itemselectednumber] = landscapelegend;
				ITEMtype[itemselectednumber] = TYPE_LEGEND1;

				FloorPlanActivity.view.invalidate();
			}

			if (menuitem == MENU_LEGEND_SQUEEZEDLANDSCAPE) {

				ITEMstring[itemselectednumber] = "squeezedlandscape";
				ITEMbitmap[itemselectednumber] = squeezedlandscapelegend;
				ITEMtype[itemselectednumber] = TYPE_LEGEND3;

				FloorPlanActivity.view.invalidate();
			}
			invalidate();
		}
		if (type == TYPE_ETHERNETPORT) {
		
			if (menuitem == MENU_ETHERNETPORT_TOGGLE_AVAILABLE) {
				// toggle display x or check
				if (ITEMdisplaynumber[itemselectednumber] == TYPE_TINYX) {
					ITEMdisplaynumber[itemselectednumber] = TYPE_TINYCHECK;
					ITEMmasterelcnumber[itemselectednumber] = 0;
				} else {
					ITEMdisplaynumber[itemselectednumber] = TYPE_TINYX;
					ITEMmasterelcnumber[itemselectednumber] = getclosestedelcnumber(itemselectednumber);
				}

			}
			if (menuitem == MENU_ETHERNETPORT_MOVETEXT) {
				MOVETEXT = true;
				
			}
			FloorPlanActivity.view.invalidate();
		}

	}

	public static int smallestelcnumbernotinuse() {
		int smallestnumber = 1;

		for (int y = 0; y < i; y++) {
			try {
				Log.d("current smallest number compared to ELCdisplaynumber",
						smallestnumber + " " + ELCdisplaynumber[y]);
				if (ELCdisplaynumber[y] == smallestnumber) {
					// number in use
					Log.d("number in use", u.s(ELCdisplaynumber[y]));
					smallestnumber++;
					y = -1;
				}
			} catch (Throwable e) {

			}
		}

		return smallestnumber;
	}

	public static int smallesttempsensornumbernotinuse(int master) {
		int smallestnumber = 1;

		for (int y = 0; y < i; y++) {
			Log.d("current smallest number compared to ITEMdisplaynumber",
					smallestnumber + " " + ITEMdisplaynumber[y]);
			if (ITEMdisplaynumber[y] == smallestnumber
					&& ITEMmasterelcnumber[y] == master) {
				// number in use
				Log.d("number in use", u.s(ITEMdisplaynumber[y]));
				smallestnumber++;
				y = -1;

			}
		}

		return smallestnumber;
	}

	public void getintdialog(String title, final int itemselectednumber,
			final int[] item) {

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		final AutoCompleteTextView nameet = new AutoCompleteTextView(ctx);
		nameet.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		nameet.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		nameet.setText(u.s(item[itemselectednumber]));
		nameet.setSelectAllOnFocus(true);
		getstring.setView(nameet);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		getstring.setTitle(title);
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String string = nameet.getText().toString();

						if (ITEMtype[itemselectednumber] == TYPE_ELC) {
							boolean changemade = false;
							for (int y = 0; y < i; y++) {
								if (ELCdisplaynumber[y] == u.i(string)) {
									Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
									Tabs1.db.swapelcs(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN], 
											u.s(ELCdisplaynumber[itemselectednumber]), string);
									
									// elca
									ELCdisplaynumber[y] = ELCdisplaynumber[itemselectednumber];
									// elcb
									item[itemselectednumber] = u.i(string);
									
									
									changemade = true;
									if (FloorPlanActivity.AUTORENUMBERTEMPS) {
										AUTORENUMBERalltempsensorsbasedonlocation();
									}
									break;
								}
							}
							if (changemade) {
								showToast("ELCs Swapped");
							} else {
								showToast("No Such ELC Number " + string);
							}
						} else {
							item[itemselectednumber] = u.i(string);
						}
						
						// ITEMdisplaynumber[itemselectednumber] = u.i(string);
						if (ADDITIONALCHANGEAFTERDIALOG == ADDITONALCHANGE_DISPLAYNUMBER) {
							ITEMdisplaynumber[itemselectednumber] = smallesttempsensornumbernotinuse(item[itemselectednumber]);
							ADDITIONALCHANGEAFTERDIALOG = 0;
						}
						invalidate();
						dialog.dismiss();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
					}

				});
		getstring.create().show();

	}

	public static void addsamsdialog(String title, final int itemselectednumber) {

		AlertDialog.Builder samdialogbuilder = new AlertDialog.Builder(ctx);
		final int elcnum = ELCdisplaynumber[itemselectednumber];
		// add count of sams on elc
		try{
			sv[elcnum].removeAllViews();
		}catch(Throwable e){
			
		}
		try{
			samtablelayout[elcnum].removeAllViews();
		}catch(Throwable e){
			
		}
		
		samcounttv[elcnum] = new TextView(ctx);
		samcounttv[elcnum].setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		samcounttv[elcnum].setText(u.s(ITEMsamscount[itemselectednumber]));

	
		
			samtablelayout[elcnum] = new TableLayout(ctx);

			samtitlerow[elcnum] = new TableRow(ctx);

			for (int w = 0; w < maxvalues-2; w++) {
				samtitles[elcnum][w] = new TextView(ctx);
				// lp.weight = 1;
				samtitles[elcnum][w].setLayoutParams(lp[w]);
				samtitles[elcnum][w].setText(samtitlesstring[w]);
				samtitlerow[elcnum].addView(samtitles[elcnum][w]);
			}

			samtablelayout[elcnum].addView(samtitlerow[elcnum]);

			samtablelayout[elcnum].setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			try {
				addsamrows(samtablelayout[elcnum], itemselectednumber,
						samcounttv[elcnum], lp);
				Log.d("addsamrows completed", "true" + u.s(elcnum));

			} catch (Throwable e) {
				e.printStackTrace();
			}
		

		// addtitlerow

		addsambutton[elcnum] = new Button(ctx);
		addsambutton[elcnum].setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addsambutton[elcnum].setText("Add SAM");
		addsambutton[elcnum].setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("add sam ct", u.s(ITEMsamscount[itemselectednumber]));
				if (ITEMsamscount[itemselectednumber] < maxsams) {
					addsamrow(samtablelayout[elcnum], itemselectednumber, lp);
				}
			}
		});

		sv[elcnum] = new ScrollView(ctx);
		sv[elcnum].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		ll[elcnum] = new LinearLayout(ctx);
		ll[elcnum].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		ll[elcnum].setOrientation(LinearLayout.VERTICAL);
		ll[elcnum].addView(samcounttv[elcnum]);

		ll[elcnum].addView(samtablelayout[elcnum]);
		ll[elcnum].addView(addsambutton[elcnum]);
		sv[elcnum].addView(ll[elcnum]);

		samdialogbuilder.setView(sv[elcnum]);
		samdialogbuilder.setCancelable(false);
		samdialogbuilder.setTitle(title);
		samdialogbuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						deletevaluesforelcfromdb(elcnum);
						addallvaluesforelctodb(elcnum);
						
						dialog.dismiss();
						ll[elcnum].removeView(samtablelayout[elcnum]);
						FloorPlanActivity.view.invalidate();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
					}

				});
		samdialogbuilder.create();
		samdialog = samdialogbuilder.show();

		samdialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

		// (That new View is just there to have something inside the dialog that
		// can grow big enough to cover the whole screen.)

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(samdialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;

		samdialog.getWindow().setAttributes(lp);
	}

	public static void addsamrows(TableLayout tl, final int itemselectednumber,
			final TextView samscountindialog, LayoutParams[] lp) {
		int elcnum = ELCdisplaynumber[itemselectednumber];
		// int newsamnum = ITEMsamscount[itemselectednumber];

		Log.d("elcnum", u.s(elcnum));

		//int rownum = 0;

		//int sizeofdb=Tabs1.db.countrows(Tabs1.db.TABLE_MCR_METERINGLIST);
	
		//int largestelcnumber=Tabs1.db.getmaxintvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[elcnumcolumn]);
	//	u.log("largestelcnumber, "+largestelcnumber);
		
	//	int[] samcountperelc=new int[largestelcnumber];
//		for(int elcnumbers=1; elcnumbers<=largestelcnumber; elcnumbers++){
//			samcountperelc[elcnumbers]=Tabs1.db.getnumberofrowswithcolumnvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[elcnumcolumn],u.s(elcnumbers));
//			
//			
//		
//		}
		
		//int samcountforthiselc=Tabs1.db.getnumberofrowswithcolumnvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[elcnumcolumn],u.s(elcnum));
		Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
		u.log(Tabs1.db.KEY_MCR_METERING_TITLES[0]);
		u.log(Tabs1.db.KEY_MCR_METERING_TITLES[1]);
		u.log(Tabs1.db.KEY_MCR_METERING_TITLES[2]);
		
		Cursor samrows=Tabs1.db.getrowswithvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN],u.s(elcnum));
		
		u.log("samrows.getCount() "+samrows.getCount());
		for (int samnum = 0; samnum < samrows.getCount(); samnum++) {

				samsrow[elcnum][samnum] = new TableRow(ctx);

				// Load Names
				int position = 0;

				samview[elcnum][samnum][position] = new AutoCompleteTextView(
						ctx);
				((TextView) samview[elcnum][samnum][position])
						.setImeOptions(EditorInfo.IME_ACTION_NEXT);

				Log.d("AutoCompleteTextView created", "samview[" + elcnum
						+ "][" + samnum + "][" + position + "]");
				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				
				u.log("samrows.getString(Tabs1.LOADNAMECOLUMN)"+samrows.getString(Tabs1.LOADNAMECOLUMN));
				((AutoCompleteTextView) samview[elcnum][samnum][position]).setText(samrows.getString(Tabs1.LOADNAMECOLUMN));
				samsrow[elcnum][samnum]
						.addView(samview[elcnum][samnum][position]);
				
				
				// Phase / CT count
				position++;

				samview[elcnum][samnum][position] = new Spinner(ctx);
				Log.d("Spinner created", "samview[" + elcnum + "]["
						+ samnum + "][" + position + "]");
				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				((Spinner) samview[elcnum][samnum][position]).setAdapter(Tabs1.mcrphspinneradapter);
				((Spinner) samview[elcnum][samnum][position]).setSelection(u.getIndexofSpinner(((Spinner) samview[elcnum][samnum][position]), samrows.getString(Tabs1.PHCOLUMN)));
				samsrow[elcnum][samnum].addView(samview[elcnum][samnum][position]);

				// Breaker Size
				position++;

				samview[elcnum][samnum][position] = new AutoCompleteTextView(
						ctx);
				Log.d("AutoCompleteTextView created", "samview[" + elcnum
						+ "][" + samnum + "][" + position + "]");
				((TextView) samview[elcnum][samnum][position])
						.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				((TextView) samview[elcnum][samnum][position])
						.setInputType(InputType.TYPE_CLASS_NUMBER
								| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				((AutoCompleteTextView) samview[elcnum][samnum][position]).setText(samrows.getString(Tabs1.BREAKERSIZECOLUMN));

				samsrow[elcnum][samnum]
						.addView(samview[elcnum][samnum][position]);

				// CT Type
				position++;

				samview[elcnum][samnum][position] = new Spinner(ctx);
				Log.d("Spinner created", "samview[" + elcnum + "]["
						+ samnum + "][" + position + "]");
				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				((Spinner) samview[elcnum][samnum][position]).setAdapter(Tabs1.mcrcttypespinneradapter);
				((Spinner) samview[elcnum][samnum][position]).setSelection(u.getIndexofSpinner(((Spinner) samview[elcnum][samnum][position]), samrows.getString(Tabs1.CTSIZEPHYISICALCOLUMN)));
				
				samsrow[elcnum][samnum]
						.addView(samview[elcnum][samnum][position]);
				((AutoCompleteTextView) samview[elcnum][samnum][position - 1])
						.addTextChangedListener(u
								.cttypewatcher(
										(AutoCompleteTextView) samview[elcnum][samnum][position - 1],
										(Spinner) samview[elcnum][samnum][position],
										Tabs1.ctdownsizepercent));
				// Load Type
				position++;

				samview[elcnum][samnum][position] = new Spinner(ctx);
				Log.d("Spinner created", "samview[" + elcnum + "]["
						+ samnum + "][" + position + "]");
				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				((Spinner) samview[elcnum][samnum][position]).setAdapter(Tabs1.mcrloadtypespinneradapter);
				((Spinner) samview[elcnum][samnum][position]).setSelection(u.getIndexofSpinner(((Spinner) samview[elcnum][samnum][position]), samrows.getString(Tabs1.LOADTYPECOLUMN)));
				
				samsrow[elcnum][samnum]
						.addView(samview[elcnum][samnum][position]);

				// Loads
				position++;

				samview[elcnum][samnum][position] = new AutoCompleteTextView(
						ctx);
				Log.d("AutoCompleteTextView created", "samview[" + elcnum
						+ "][" + samnum + "][" + position + "]");
				((TextView) samview[elcnum][samnum][position])
						.setImeOptions(EditorInfo.IME_ACTION_NEXT);

				samview[elcnum][samnum][position]
						.setLayoutParams(lp[position]);
				
				((AutoCompleteTextView) samview[elcnum][samnum][position]).setText(samrows.getString(Tabs1.LOADSONEACHBREAKERCOLUMN));

				samsrow[elcnum][samnum]
						.addView(samview[elcnum][samnum][position]);

				// Remove Sams				
				position++;

				removesambutton[elcnum][samnum] = new ImageView(ctx);
				removesambutton[elcnum][samnum]
						.setImageResource(R.drawable.deleteitem48);
				removesambutton[elcnum][samnum]
						.setLayoutParams(lp[position]);
				removesambutton[elcnum][samnum]
						.setOnClickListener(new ImageView.OnClickListener() {

							@Override
							public void onClick(View v) {
								int[] z = getsequencenumber(removesambutton, v);
								int elcnum = z[0];
								int samnum = z[1];

								Log.d("elcnum, samnum, ITEMsamcount[itemselectednumber], maxvalues",
										u.s(elcnum)
												+ " "
												+ u.s(samnum)
												+ " "
												+ u.s(ITEMsamscount[itemselectednumber])
												+ " " + u.s(maxvalues));

								for (int a = samnum; a < ITEMsamscount[itemselectednumber]; a++) {
									for (int b = 0; b < maxvalues; b++) {
										Log.d("a,b", u.s(a) + " " + u.s(b));
										try {
											((TextView) samview[elcnum][a][b])
													.setText(((TextView) samview[elcnum][a + 1][b])
															.getText()
															.toString());
											Log.d("text transfered",
													((TextView) samview[elcnum][a + 1][b])
															.getText()
															.toString());
											Log.d("success", "tv");
										} catch (Throwable e) {
											e.printStackTrace();
										}
										try {
											((Spinner) samview[elcnum][a][b])
													.setSelection(((Spinner) samview[elcnum][a + 1][b])
															.getSelectedItemPosition());
											Log.d("success", "spinner");

										} catch (Throwable e) {
											e.printStackTrace();
										}
									}

								}

								samtablelayout[elcnum]
										.removeView(samsrow[elcnum][ITEMsamscount[itemselectednumber] - 1]);
								ITEMsamscount[itemselectednumber]--;
								samcounttv[ELCdisplaynumber[itemselectednumber]].setText(u
										.s(ITEMsamscount[itemselectednumber]));
							}
						});
				
					samsrow[elcnum][samnum]
							.addView(removesambutton[elcnum][samnum]);
				
					// Panel location
					position++;

					samview[elcnum][samnum][position] = new AutoCompleteTextView(
							ctx);
					Log.d("AutoCompleteTextView created", "samview[" + elcnum
							+ "][" + samnum + "][" + position + "]");
					((TextView) samview[elcnum][samnum][position])
							.setImeOptions(EditorInfo.IME_ACTION_NEXT);

					samview[elcnum][samnum][position]
							.setLayoutParams(lp[position]);
					
					((AutoCompleteTextView) samview[elcnum][samnum][position]).setText(samrows.getString(Tabs1.PANELLOCATIONCOLUMN));

					samsrow[elcnum][samnum]
							.addView(samview[elcnum][samnum][position]);
					samview[elcnum][samnum][position].setVisibility(View.GONE);
					
					// Comments
					position++;

					samview[elcnum][samnum][position] = new AutoCompleteTextView(
							ctx);
					Log.d("AutoCompleteTextView created", "samview[" + elcnum
							+ "][" + samnum + "][" + position + "]");
					((TextView) samview[elcnum][samnum][position])
							.setImeOptions(EditorInfo.IME_ACTION_NEXT);

					samview[elcnum][samnum][position]
							.setLayoutParams(lp[position]);
					
					((AutoCompleteTextView) samview[elcnum][samnum][position]).setText(samrows.getString(Tabs1.COMMENTSCOLUMN));

					samsrow[elcnum][samnum]
							.addView(samview[elcnum][samnum][position]);
					samview[elcnum][samnum][position].setVisibility(View.GONE);
				
				try {
					tl.addView(samsrow[elcnum][samnum]);

					samscountindialog.setText(u
							.s(ITEMsamscount[itemselectednumber]));
				} catch (Throwable e) {
					e.printStackTrace();
				}
				samrows.moveToNext();
		}

	}

	public static void addsamrow(TableLayout tl, final int itemselectednumber,
			LayoutParams[] lp) {
		int elcnum = ELCdisplaynumber[itemselectednumber];
		int newsamnum = ITEMsamscount[itemselectednumber];

		Log.d("elcnum", u.s(elcnum));
		Log.d("newsamnum", u.s(newsamnum));

		samsrow[elcnum][newsamnum] = new TableRow(ctx);
		// Load Name
		int position = 0;

		samview[elcnum][newsamnum][position] = new AutoCompleteTextView(ctx);
		((AutoCompleteTextView) samview[elcnum][newsamnum][position])
				.setImeOptions(EditorInfo.IME_ACTION_NEXT);

		Log.d("AutoCompleteTextView created", "samview[" + elcnum + "]["
				+ newsamnum + "][" + position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);
		List<String> loadnamestemp =new ArrayList<String>();
		loadnamestemp.clear();
		for(int ln =0;ln<Tabs1.loadnames.size();ln++){
			loadnamestemp.add(Tabs1.loadnames.get(ln));
			u.log("loadnames, "+Tabs1.loadnames.get(ln));
		}
		for(int l=0;l<Tabs1.assetnames.length;l++){
			try{
				if(!Tabs1. assetnames[l].equals(null)){
				loadnamestemp.add(Tabs1.assetnames[l]);
				u.log("loadnamestemp, "+Tabs1.assetnames[l]);
				}
			}catch(Throwable e){}
		}
		ArrayAdapter loadnamesadapter = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_dropdown_item_1line, loadnamestemp);

		((AutoCompleteTextView) samview[elcnum][newsamnum][position]).setAdapter(loadnamesadapter);
		
		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);
		
		// Phase/CT count
		position++;

		samview[elcnum][newsamnum][position] = new Spinner(ctx);
		Log.d("Spinner created", "samview[" + elcnum + "][" + newsamnum + "]["
				+ position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		((Spinner) samview[elcnum][newsamnum][position])
				.setAdapter(Tabs1.mcrphspinneradapter);

		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);

		// Breaker Size
		position++;

		samview[elcnum][newsamnum][position] = new AutoCompleteTextView(ctx);
		((TextView) samview[elcnum][newsamnum][position])
				.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		((TextView) samview[elcnum][newsamnum][position])
				.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		Log.d("AutoCompleteTextView created", "samview[" + elcnum + "]["
				+ newsamnum + "][" + position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);
		// CT Type
		position++;

		samview[elcnum][newsamnum][position] = new Spinner(ctx);
		Log.d("Spinner created", "samview[" + elcnum + "][" + newsamnum + "]["
				+ position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		((Spinner) samview[elcnum][newsamnum][position])
				.setAdapter(Tabs1.mcrcttypespinneradapter);
		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);

		((AutoCompleteTextView) samview[elcnum][newsamnum][position - 1])
				.addTextChangedListener(u
						.cttypewatcher(
								(AutoCompleteTextView) samview[elcnum][newsamnum][position - 1],
								(Spinner) samview[elcnum][newsamnum][position],
								Tabs1.ctdownsizepercent));

		// Load Type
		position++;

		samview[elcnum][newsamnum][position] = new Spinner(ctx);
		Log.d("Spinner created", "samview[" + elcnum + "][" + newsamnum + "]["
				+ position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		((Spinner) samview[elcnum][newsamnum][position])
				.setAdapter(Tabs1.mcrloadtypespinneradapter);
		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);

		// Loads
		position++;

		samview[elcnum][newsamnum][position] = new AutoCompleteTextView(ctx);
		((TextView) samview[elcnum][newsamnum][position])
				.setImeOptions(EditorInfo.IME_ACTION_NEXT);

		Log.d("AutoCompleteTextView created", "samview[" + elcnum + "]["
				+ newsamnum + "][" + position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);

		// Remove SAMS
		position++;

		removesambutton[elcnum][newsamnum] = new ImageView(ctx);
		removesambutton[elcnum][newsamnum]
				.setImageResource(R.drawable.deleteitem48);
		removesambutton[elcnum][newsamnum].setLayoutParams(lp[position]);
		removesambutton[elcnum][newsamnum]
				.setOnClickListener(new ImageView.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int[] z = getsequencenumber(removesambutton, v);
						int elcnum = z[0];
						int samnum = z[1];

						Log.d("elcnum, samnum, ITEMsamcount[itemselectednumber], maxvalues",
								u.s(elcnum)
										+ " "
										+ u.s(samnum)
										+ " "
										+ u.s(ITEMsamscount[itemselectednumber])
										+ " " + u.s(maxvalues));

						for (int a = samnum; a < ITEMsamscount[itemselectednumber]; a++) {
							for (int b = 0; b < maxvalues; b++) {
								Log.d("a,b", u.s(a) + " " + u.s(b));
								try {
									((TextView) samview[elcnum][a][b])
											.setText(((TextView) samview[elcnum][a + 1][b])
													.getText().toString());
									Log.d("text transfered",
											((TextView) samview[elcnum][a + 1][b])
													.getText().toString());
									Log.d("success", "tv");
								} catch (Throwable e) {
									e.printStackTrace();
								}
								try {
									((Spinner) samview[elcnum][a][b])
											.setSelection(((Spinner) samview[elcnum][a + 1][b])
													.getSelectedItemPosition());
									Log.d("success", "spinner");

								} catch (Throwable e) {
									e.printStackTrace();
								}
							}

						}

						samtablelayout[elcnum]
								.removeView(samsrow[elcnum][ITEMsamscount[itemselectednumber] - 1]);
						ITEMsamscount[itemselectednumber]--;
						samcounttv[ELCdisplaynumber[itemselectednumber]]
								.setText(u.s(ITEMsamscount[itemselectednumber]));

					}
				});
		samsrow[elcnum][newsamnum].addView(removesambutton[elcnum][newsamnum]);
		
		
		// Panel Location
		position++;

		samview[elcnum][newsamnum][position] = new AutoCompleteTextView(ctx);
		((TextView) samview[elcnum][newsamnum][position])
				.setImeOptions(EditorInfo.IME_ACTION_NEXT);

		Log.d("AutoCompleteTextView created", "samview[" + elcnum + "]["
				+ newsamnum + "][" + position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);
		samview[elcnum][newsamnum][position].setVisibility(View.GONE);
		
		// Comments
		position++;

		samview[elcnum][newsamnum][position] = new AutoCompleteTextView(ctx);
		((TextView) samview[elcnum][newsamnum][position])
				.setImeOptions(EditorInfo.IME_ACTION_NEXT);

		Log.d("AutoCompleteTextView created", "samview[" + elcnum + "]["
				+ newsamnum + "][" + position + "]");
		samview[elcnum][newsamnum][position].setLayoutParams(lp[position]);

		samsrow[elcnum][newsamnum]
				.addView(samview[elcnum][newsamnum][position]);
		samview[elcnum][newsamnum][position].setVisibility(View.GONE);

		tl.addView(samsrow[elcnum][newsamnum]);
		
		
		ITEMsamscount[itemselectednumber]++;
		samcounttv[ELCdisplaynumber[itemselectednumber]].setText(u
				.s(ITEMsamscount[itemselectednumber]));
	}

	public static void getstringdialog(String title,
			final int itemselectednumber, final String[] ITEMstring) {

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		final AutoCompleteTextView nameet = new AutoCompleteTextView(ctx);
		nameet.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		nameet.setThreshold(0);
		nameet.setSingleLine();
		String string = ITEMstring[itemselectednumber];
		System.out.println("itemtype is " + ITEMtype[itemselectednumber]);
		if (ITEMtype[itemselectednumber] == TYPE_BMS) {
			System.out.println("alertdialog type is bms");
			Tabs1.bmsnamesadapter = new ArrayAdapter<String>(ctx,
					android.R.layout.simple_dropdown_item_1line, Tabs1.bmsnames);
			nameet.setAdapter(Tabs1.bmsnamesadapter);
			
		}
		if (ITEMtype[itemselectednumber] == TYPE_TEMPSENSOR) {
			System.out.println("alertdialog type is tempsensor");
			Tabs1.commontemplocationsadapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line,
					Tabs1.commontemplocations);
			nameet.setAdapter(Tabs1.commontemplocationsadapter);
			try {
				string = ITEMstring[itemselectednumber].split(":")[1];
			} catch (Throwable e) {
				string = "";
			}

		}

		nameet.setText(string);
		nameet.setSelectAllOnFocus(true);
		getstring.setView(nameet);
		getstring.setNegativeButton("Cancel",null);
		getstring.setTitle(title);
		getstring.setPositiveButton("OK",null);
		getstring.setCancelable(false);
		
		
		dialog=getstring.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

		    @Override
		    public void onShow(final DialogInterface dialog) {

		        Button ok = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
		        ok.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            	String string = nameet.getText().toString();
						
						
						if (ITEMtype[itemselectednumber] == TYPE_TEMPSENSOR) {

							ITEMstring[itemselectednumber] = ITEMstring[itemselectednumber]
									.split(":")[0] + ":" + string;

						} else {
							ITEMstring[itemselectednumber] = string;
							
						}

						if(duplicategenericnameexists(itemselectednumber)){
							showToast("Duplicate Item, Please Rename");
						}else{
						// ITEMdisplaynumber[itemselectednumber] = u.i(string);

							FloorPlanActivity.view.invalidate();
							
							updatetabsdatabases(itemselectednumber);
							
							dialog.dismiss();
							FloorPlanActivity.writeonedbitem(itemselectednumber);
						}
		            }
		        });
		        Button cancel = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
		        cancel.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {
		                // TODO Do something
		            	if(duplicategenericnameexists(itemselectednumber)){
							showToast("Duplicate Item, Please Rename");
						}else{
			            	updatetabsdatabases(itemselectednumber);
							dialog.cancel();
						}
		            }
		        });
		    }
		});
		dialog.show();

	}

	public static void getstringfromspinnerdialog(String title,
			final int itemselectednumber) {

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		final Spinner nameet = new Spinner(ctx);
		nameet.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		nameet.setAdapter(new ArrayAdapter<String>(ctx,
				R.layout.spinnertextview, Tabs1.floornumbers));
		System.out.println("itemtype is " + ITEMtype[itemselectednumber]);

		String floornumber;

		if (ITEMstring[itemselectednumber].contains(":")) {
			floornumber = ITEMstring[itemselectednumber].split(":")[0];
			nameet.setSelection(u.getIndex(nameet, floornumber));
		} else {
			nameet.setSelection(2);
		}

		getstring.setView(nameet);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		getstring.setTitle(title);
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						String string = (String) nameet.getSelectedItem();
						ITEMstring[itemselectednumber] = string + ":";
						// ITEMdisplaynumber[itemselectednumber] = u.i(string);
						getstringdialog(
								"Please name the location of this temperature sensor",
								itemselectednumber, ITEMstring);


						dialog.dismiss();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
					}

				});
		dialog=getstring.create();
		dialog.show();

	}

	public static void gettemperaturestringsdialog(final int itemselectednumber) {

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		TableLayout tl = new TableLayout(ctx);
		tl.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr1 = new TableRow(ctx);
		tr1.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr2 = new TableRow(ctx);
		tr2.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr3 = new TableRow(ctx);
		tr3.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr4 = new TableRow(ctx);
		tr4.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);

		final TextView floornamelable = new TextView(ctx);
		floornamelable.setLayoutParams(lp);
		floornamelable.setText("Floor:");

		final Spinner floorname = new Spinner(ctx);
		floorname.setLayoutParams(lp);
		floorname.setAdapter(new ArrayAdapter<String>(ctx,
				R.layout.spinnertextview, Tabs1.floornumbers));
		System.out.println("itemtype is " + ITEMtype[itemselectednumber]);

		String floornumber;

		if (ITEMstring[itemselectednumber].contains(":")) {
			floornumber = ITEMstring[itemselectednumber].split(":")[0];
			floorname.setSelection(u.getIndex(floorname, floornumber));
		} else {
			floorname.setSelection(u.getIndex(floorname, "Ground Floor (1st US)"));
		}

		final TextView physicallocationlable = new TextView(ctx);
		physicallocationlable.setLayoutParams(lp);
		physicallocationlable.setText("Location:");

		final AutoCompleteTextView physicallocation = new AutoCompleteTextView(ctx);
		physicallocation.setLayoutParams(lp);
		physicallocation.setThreshold(0);
		physicallocation.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
		physicallocation.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		String string = ITEMstring[itemselectednumber];
		System.out.println("itemtype is " + ITEMtype[itemselectednumber]);
		System.out.println("alertdialog type is tempsensor");
		Tabs1.commontemplocationsadapter = new ArrayAdapter<String>(Tabs1.ctx,
				android.R.layout.simple_dropdown_item_1line,
				Tabs1.commontemplocations);
		
		try {
			string = ITEMstring[itemselectednumber].split(":")[1];
			physicallocation.setText(string);
		} catch (Throwable e) {
			string = "";
		}
		physicallocation.setAdapter(Tabs1.commontemplocationsadapter);
		physicallocation.setSelectAllOnFocus(true);
		
		final TextView masterelcnumlable = new TextView(ctx);
		masterelcnumlable.setLayoutParams(lp);
		masterelcnumlable.setText("ELC Number:");
	
	
		
		
		final EditText masterelcnum = new EditText(ctx);
		
		
		masterelcnum.setLayoutParams(lp);
		try {
			masterelcnum.setText(u.s(ITEMmasterelcnumber[itemselectednumber]));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		masterelcnum.setInputType(InputType.TYPE_CLASS_NUMBER);
		masterelcnum.setSelectAllOnFocus(true);
		
		

		final TextView zonenumlable = new TextView(ctx);
		zonenumlable.setLayoutParams(lp);
		zonenumlable.setText("Zone Number:");
		
		

		final EditText zonenum = new EditText(ctx);
		zonenum.setLayoutParams(lp);
		
		
		try {
			zonenum.setText(u.s(ITEMdisplaynumber[itemselectednumber]));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		zonenum.setInputType(InputType.TYPE_CLASS_NUMBER);
		zonenum.setSelectAllOnFocus(true);
		masterelcnum.addTextChangedListener(u.charactercounttextwatcher(1, masterelcnum, zonenum, dialog));
		
		zonenum.setImeOptions(EditorInfo.IME_ACTION_DONE);
		zonenum.setOnEditorActionListener(new OnEditorActionListener()
		{
		    @Override
		    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event)
		    {
		        /** Same code here that goes in the dialog.setPositiveButton OnClickListener */
		    	dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
		        return true;
		    }
		});
		
		
		
		tr1.addView(floornamelable);
		tr1.addView(floorname);
		tr2.addView(physicallocationlable);
		tr2.addView(physicallocation);
		tr3.addView(masterelcnumlable);
		tr3.addView(masterelcnum);
		tr4.addView(zonenumlable);
		tr4.addView(zonenum);

		tl.addView(tr1);
		tl.addView(tr2);
		tl.addView(tr3);
		tl.addView(tr4);

		getstring.setView(tl);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						updatetabsdatabases(itemselectednumber);
						dialog.cancel();
					}
				});
		getstring.setTitle("Edit Temperature Sensor Values");
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						// step1
						String floor = (String) floorname.getSelectedItem();
						if (ITEMstring[itemselectednumber].split(":").length > 1) {
							ITEMstring[itemselectednumber] = floor
									+ ":"
									+ ITEMstring[itemselectednumber].split(":")[1];
						} else {
							ITEMstring[itemselectednumber] = floor + ":";
						}

						ITEMstring[itemselectednumber] = ITEMstring[itemselectednumber]
								.split(":")[0]
								+ ":"
								+ physicallocation.getText().toString();
						ITEMmasterelcnumber[itemselectednumber] = u
								.i(masterelcnum.getText().toString());

						ITEMdisplaynumber[itemselectednumber] = u.i(zonenum
								.getText().toString());

						// ITEMdisplaynumber[itemselectednumber] = u.i(string);

						FloorPlanActivity.view.invalidate();
						//addtempvaluesdb();
						dialog.dismiss();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
						updatetabsdatabases(itemselectednumber);
					}

				});
		dialog=getstring.create();
		zonenum.addTextChangedListener(u.charactercounttextwatcher(1, zonenum, zonenum, dialog));
		dialog.show();
		

	}

	public static void getsamarraystringsdialog(final int itemselectednumber) {
		try{

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		TableLayout tl = new TableLayout(ctx);
		tl.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr1 = new TableRow(ctx);
		tr1.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr2 = new TableRow(ctx);
		tr2.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr3 = new TableRow(ctx);
		tr3.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr4 = new TableRow(ctx);
		tr4.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TableRow tr5 = new TableRow(ctx);
		tr5.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);

		// SAMARRAYREF
		final TextView samarrayreflable = new TextView(ctx);
		samarrayreflable.setLayoutParams(lp);
		samarrayreflable.setText("SAM Array Reference:");

		String arrayreferencesuggestion = "A";
		int samarraycount = 0;
		for (int j = 0; j < i; j++) {
			if (ITEMtype[j] == TYPE_SAMARRAY) {
				samarraycount++;
			}
		}
		switch (samarraycount) {
		case 0:
			arrayreferencesuggestion = "A";
			break;
		case 1:
			arrayreferencesuggestion = "B";
			break;
		case 2:
			arrayreferencesuggestion = "C";
			break;
		case 3:
			arrayreferencesuggestion = "D";
			break;
		case 4:
			arrayreferencesuggestion = "E";
			break;
		case 5:
			arrayreferencesuggestion = "F";
			break;
		case 6:
			arrayreferencesuggestion = "G";
			break;
		case 7:
			arrayreferencesuggestion = "H";
			break;
		case 8:
			arrayreferencesuggestion = "I";
			break;
		case 9:
			arrayreferencesuggestion = "J";
			break;

		}

		final EditText samarrayref = new EditText(ctx);
		samarrayref.setLayoutParams(lp);
		try {
			if (ITEMstring[itemselectednumber].split(",")[0].trim().equals("")) {
				samarrayref.setText(arrayreferencesuggestion);
			} else {
				samarrayref
						.setText(ITEMstring[itemselectednumber].split(",")[0]);

			}

		} catch (Throwable e) {
			samarrayref.setText(arrayreferencesuggestion);
			e.printStackTrace();
		}

		// NUMOFSAMS
		final TextView numofsamslable = new TextView(ctx);
		numofsamslable.setLayoutParams(lp);
		numofsamslable.setText("Number of SAMs:");

		final EditText numofsams = new EditText(ctx);
		numofsams.setLayoutParams(lp);
		try {
			numofsams.setText(ITEMstring[itemselectednumber].split(",")[1]);

		} catch (Throwable e) {
			e.printStackTrace();

		}
		if (numofsams.getText().toString().trim().length() == 0) {
			numofsams.setText(u.s(9));
		}
		numofsams.setInputType(InputType.TYPE_CLASS_NUMBER);
		numofsams.requestFocus();
		numofsams.setSelectAllOnFocus(true);

		// ELCNUMBER
		final TextView masterelcnumlable = new TextView(ctx);
		masterelcnumlable.setLayoutParams(lp);
		masterelcnumlable.setText("ELC Number:");

		final EditText masterelcnum = new EditText(ctx);
		masterelcnum.setLayoutParams(lp);
		masterelcnum.setInputType(InputType.TYPE_CLASS_NUMBER);
		try {
			if (ITEMmasterelcnumber[itemselectednumber] == 0) {
				masterelcnum.setText(u
						.s(getclosestedelcnumber(itemselectednumber)));

			} else if (getclosestedelcnumber(itemselectednumber) != -1) {
				masterelcnum.setText(u.s(1));

			} else {
				masterelcnum.setText(u
						.s(ITEMmasterelcnumber[itemselectednumber]));
			}
		} catch (Throwable e) {
			// masterelcnum.setText(getclosestedelcnumber(itemselectednumber));
			e.printStackTrace();
		}

		// CIRCUIT REFRENCE
		final TextView circuitreferencelable = new TextView(ctx);
		circuitreferencelable.setLayoutParams(lp);
		circuitreferencelable.setText("Circuit Reference:");

		final EditText circuitreference = new EditText(ctx);
		circuitreference.setLayoutParams(lp);
		circuitreference.setHint("Example: DB M - 24 L123");
		try {
			circuitreference
					.setText(ITEMstring[itemselectednumber].split(",")[2]);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		// DATAREQUIRED?
				final TextView datarequiredlabel = new TextView(ctx);
				datarequiredlabel.setLayoutParams(lp);
				datarequiredlabel.setText("Data Required?");

				final CheckBox datarequired = new CheckBox(ctx);
				datarequired.setLayoutParams(lp);
				try {
					if(ITEMstring[itemselectednumber].split(",")[3].equals("Data Required")){
						datarequired.setChecked(true);
					}else{
						datarequired.setChecked(false);
					}
				} catch (Throwable e) {
					e.printStackTrace();
					datarequired.setChecked(true);
				}

		tr1.addView(samarrayreflable);
		tr1.addView(samarrayref);
		tr2.addView(numofsamslable);
		tr2.addView(numofsams);
		tr3.addView(masterelcnumlable);
		tr3.addView(masterelcnum);
		tr4.addView(circuitreferencelable);
		tr4.addView(circuitreference);
		tr5.addView(datarequiredlabel);
		tr5.addView(datarequired);

		tl.addView(tr1);
		tl.addView(tr2);
		tl.addView(tr3);
		tl.addView(tr4);
		tl.addView(tr5);

		getstring.setView(tl);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						updatetabsdatabases(itemselectednumber);
						dialog.cancel();
					}
				});
		getstring.setTitle("Edit SAM Array Values");
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String requiredstring="";
						if(datarequired.isChecked()){
							requiredstring="Data Required";
						}
						
						ITEMstring[itemselectednumber] = samarrayref.getText()
								.toString()
								+ ","
								+ numofsams.getText().toString()
								+ ","
								+ circuitreference.getText().toString()
								+","
								+ requiredstring;

						ITEMmasterelcnumber[itemselectednumber] = u
								.i(masterelcnum.getText().toString());

						FloorPlanActivity.view.invalidate();

						dialog.dismiss();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
						updatetabsdatabases(itemselectednumber);
					}

				});
		getstring.create().show();
		}catch(Throwable e){
			
		}
	}

	public void initializevariables() {
		ELCCOUNT = 0;

		try {
			FloorPlanActivity.recycleallbitmaps();

		} catch (Throwable e) {

		}
		

		for (int x = 0; x < maxelcs; x++) {
			for (int y = 0; y < maxsams; y++) {
				for (int z = 0; z < maxvalues; z++) {
					try {

						samtablelayout[x] = null;
						samstrings[x][y][z] = null;
						samsrow[x][y] = null;
						samsedittext[x][y][z] = null;
						samsspinner[x][y][z] = null;

						removesambutton[x][y] = null;
						samview[x][y][z] = null;
						samoldviewparent[x][y][z] = null;
						samoldviewindex[x][y] = null;
						samoldviewlp[x][y][z] = null;
					} catch (Throwable e) {

					}
				}
			}
		}

		for (int i = 0; i < maxitems - 1; i++) {
			ITEMrect[i] = null;
			ITEMtype[i] = TYPE_NULLITEM;
			ITEMtempsensorcount[i] = 0;
			ITEMsizepercent[i] = 100;
			ITEMsamscount[i] = 0;
			ITEMmasterelcnumber[i] = 0;
			ITEMdisplaynumber[i] = 0;
			ELCdisplaynumber[i] = 0;
			ITEMbitmap[i] = null;
			ScaledITEMbitmap[i] = null;
			SCALEDITEMx[i] = 0;
			SCALEDITEMy[i] = 0;
			ITEMx[i] = 0;
			ITEMy[i] = 0;
			ITEMtextoffsetx[i] = 0;
			ITEMtextoffsety[i] = 0;

			ITEMstring[i] = "";
			ITEMfontsize[i] = 0;
			ITEMfontcolor[i] = 0;
			ITEMfloorplannumber[i] = 0;

			ITEMLINKtabnumber[i] = NOTABSLINK;
			ITEMLINKtabitemnumber[i] = NOTABSLINK;

		}
		i = 0;

	}

	public static void reinitializevariable(int i) {

		ITEMrect[i] = null;
		ITEMtype[i] = TYPE_NULLITEM;
		ITEMtempsensorcount[i] = 0;
		ITEMsizepercent[i] = 100;
		ITEMsamscount[i] = 0;
		ITEMmasterelcnumber[i] = 0;
		ITEMdisplaynumber[i] = 0;

		ITEMbitmap[i] = null;
		ScaledITEMbitmap[i] = null;
		SCALEDITEMx[i] = 0;
		SCALEDITEMy[i] = 0;
		ITEMx[i] = 0;
		ITEMy[i] = 0;

		ITEMstring[i] = "";
		ITEMfontsize[i] = 0;
		ITEMfontcolor[i] = 0;
		ITEMfloorplannumber[i] = 0;
		ELCdisplaynumber[i] = 0;
		ITEMLINKtabnumber[i] = NOTABSLINK;
		ITEMLINKtabitemnumber[i] = NOTABSLINK;

	}

	public static void addvaluestobom() {
		// Ibe's Count number of sams
		int countedgwsfromfloorplan = 0;
		for (int t = 0; t < i; t++) {
			if (ITEMtype[t] == TYPE_GATEWAY) {
				countedgwsfromfloorplan++;
			}
		}
		Tabs1.db.addorupdate(DatabaseHandler.TABLE_BOM,
				"bomgatewayservercount",
				u.s(countedgwsfromfloorplan));
		
//		// Ibe's Count number of sams
//		int countedsamsfromfloorplan = 0;
//		for (int t = 0; t < i; t++) {
//			if (ITEMtype[t] == TYPE_ELC) {
//				countedsamsfromfloorplan = countedsamsfromfloorplan
//						+ ITEMsamscount[t];
//			}
//		}
//		// Ibe's Count number of elcs
//		int countedelcsfromfloorplan = 0;
//		for (int t = 0; t < i; t++) {
//			if (ITEMtype[t] == TYPE_ELC) {
//				countedelcsfromfloorplan++;
//			}
//		}
//
//		// Ibe's Count number of tempsensor
//		int countedtsfromfloorplan = 0;
//		for (int t = 0; t < i; t++) {
//			if (ITEMtype[t] == TYPE_TEMPSENSOR) {
//				countedtsfromfloorplan++;
//			}
//		}
//
//		// Ibe's Count for total number of CTs
//		int bomctcount = 0;
//		int ctcountcolumn = 1;
//		int cttypecolumn = 3;
//		for (int elcnum = 0; elcnum < maxelcs; elcnum++) {
//			for (int samnum = 0; samnum < maxsams; samnum++) {
//				try {
//
//					bomctcount = u
//							.i(((Spinner) samview[elcnum][samnum][ctcountcolumn])
//									.getSelectedItem().toString())
//							+ bomctcount;
//					// bomctcount++;
//				} catch (Throwable e) {
//
//				}
//			}
//		}
//		// Ibe's Detailed CT info rom insertion
//		int bomctdetail = 0;
//		int brsizecolumn = 2;
//
//		String ctphy;
//		/* Array for List with duplicates */
//		List<String> Listwduplicates = new ArrayList<String>();
//		List<String> PH = new ArrayList<String>();
//
//		TableRow.LayoutParams lpff = new TableRow.LayoutParams(
//				TableRow.LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		TableRow.LayoutParams lpfw = new LayoutParams(LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		TableRow.LayoutParams lpwf = new LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
//		TableRow.LayoutParams lpww = new LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//		for (int elcnum = 0; elcnum < maxelcs; elcnum++) {
//			for (int samnum = 0; samnum < maxsams; samnum++) {
//				try {
//
//					String ctamps = ((Spinner) samview[elcnum][samnum][cttypecolumn])
//							.getSelectedItem().toString();
//					String phval = ((Spinner) samview[elcnum][samnum][ctcountcolumn])
//							.getSelectedItem().toString();
//					try {
//						if (u.i(phval) == 0) {
//							phval = "3";
//						}
//					} catch (Throwable e) {
//						phval = "3";
//					}
//					PH.add(phval);
//					Listwduplicates.add(ctamps);
//				} catch (Throwable e) {
//
//				}
//
//			}
//		}
//		Collections.sort(Listwduplicates);
//		for (int h = 0; h < Listwduplicates.size(); h++) {
//			Log.d("List item with duplicates" + h, Listwduplicates.get(h));
//			Log.d("List item phase with duplicates" + h, PH.get(h));
//
//		}
//
//		List<String> Listwoutduplicates = new ArrayList<String>();
//
//		Listwoutduplicates = new ArrayList<String>(new HashSet<String>(
//				Listwduplicates));
//		Collections.sort(Listwoutduplicates);
//		int[] quantitypertype = new int[Listwoutduplicates.size()];
//		int[] phpertype = new int[Listwoutduplicates.size()];
//		for (int h = 0; h < Listwoutduplicates.size(); h++) {
//			Log.d("List item w/o duplicate " + h, Listwoutduplicates.get(h));
//			String teststring = Listwoutduplicates.get(h);
//			for (int y = 0; y < Listwduplicates.size(); y++) {
//				if (Listwduplicates.get(y).equals(teststring)) {
//					quantitypertype[h]++;
//					if (teststring.contains("RCS")) {
//						// only count one ct for ropes
//						phpertype[h] = phpertype[h] + 1;
//
//					} else {
//						phpertype[h] = phpertype[h] + u.i(PH.get(y));
//
//					}
//				}
//			}
//		}
//
//		int newctrowscount = Listwoutduplicates.size();
//
//		// ?Create a look to check when it's three phase or 4 or 1 then use that
//		// in ur CTquantity calculation below
//
//		// If Spinner is empty, use PH as 3 by default, else use Spiner Value
//
//		for (int u = 0; u < newctrowscount; u++) {
//			TableRow tr = new TableRow(ctx);
//			TextView CTblank = new TextView(ctx);
//			CTblank.setLayoutParams(lpww);
//			CTblank.setText("          ");
//			CTblank.setTextSize(22);
//			CTblank.setTypeface(null, Typeface.BOLD_ITALIC);
//
//			TextView CTblank1 = new TextView(ctx);
//			CTblank1.setLayoutParams(lpww);
//			CTblank1.setText("                            ");
//			CTblank1.setTextSize(22);
//			CTblank1.setTypeface(null, Typeface.BOLD_ITALIC);
//
//			TextView CTblank2 = new TextView(ctx);
//			CTblank2.setLayoutParams(lpww);
//			CTblank2.setText("          ");
//			CTblank2.setTextSize(22);
//			CTblank2.setTypeface(null, Typeface.BOLD_ITALIC);
//
//			TextView CTblank3 = new TextView(ctx);
//			CTblank3.setLayoutParams(lpww);
//			CTblank3.setText("          ");
//			CTblank3.setTextSize(22);
//			CTblank3.setTypeface(null, Typeface.BOLD_ITALIC);
//
//			TextView CTblank4 = new TextView(ctx);
//			CTblank4.setLayoutParams(lpww);
//			CTblank4.setText("          ");
//			CTblank4.setTextSize(22);
//			CTblank4.setTypeface(null, Typeface.BOLD_ITALIC);
//
//			TextView CTsizeTV = new TextView(ctx);
//			CTsizeTV.setLayoutParams(lpww);
//			CTsizeTV.setText(Listwoutduplicates.get(u));
//			CTsizeTV.setTextSize(24);
//			CTsizeTV.setTypeface(null, Typeface.ITALIC);
//
//			TextView CTquantityTV = new TextView(ctx);
//			CTquantityTV.setLayoutParams(lpww);
//			CTquantityTV.setText(String.valueOf(phpertype[u] + " "));
//			CTquantityTV.setTextSize(22);
//			CTquantityTV.setTypeface(null, Typeface.BOLD_ITALIC);
//			CTquantityTV.setGravity(Gravity.CENTER);
//
//			tr.addView(CTblank);
//			tr.addView(CTsizeTV);
//			tr.addView(CTquantityTV);
//			tr.addView(CTblank1);
//			tr.addView(CTblank2);
//			tr.addView(CTblank3);
//			tr.addView(CTblank4);
//			Tabs1.bomcttablelayout.addView(tr);
//
//		}
//
//		// int phcolumn=6;
//		// for (int y=0; y<Tabs1.maxitems; y++){
//		//
//		// try{
//		// bomctcount=u.i(Tabs1.mcrstringarray[Tabs1.METERINGLIST][y][phcolumn])+bomctcount;
//		// }catch (Throwable e){
//		// e.printStackTrace();
//		// }
//		// }
//		Tabs1.bomgatewayservercount.setText(u.s(countedgwsfromfloorplan));
//		Tabs1.bomelccount.setText(u.s(countedelcsfromfloorplan));
//		Tabs1.bomsamcount.setText(u.s(countedsamsfromfloorplan));
//		Tabs1.bomtscount.setText(u.s(countedtsfromfloorplan));
//		Tabs1.bomCTcount.setText(u.s(bomctcount));
//		Tabs1.refreshbom();

	}

	public static void addvaluestotabstable() {

		addtempvaluesdb();

	}

	public static void addtempvaluesdb() {

		// grab all table values in that order
		int rownumberontabs = 0;
		
		String tempvalue="null";
		Tabs1.db.showfulldblog(DatabaseHandler.TABLE_MCR_TEMPSLIST);
		int totaltempsensorcount=0;
		for (int t=0;t<i;t++) {
			if (ITEMtype[t] == TYPE_TEMPSENSOR) {
				totaltempsensorcount++;
				for (int columnnumberontabs = 0; columnnumberontabs < DatabaseHandler.KEY_MCR_TEMPLIST_TITLES.length; columnnumberontabs++) {

					if (columnnumberontabs == 1) {
						String tempfloornumber = ITEMstring[t].split(":")[0];
						tempvalue=String.valueOf(tempfloornumber);
						
						if (!(tempvalue.equals("null")||tempvalue.equals(""))) {
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}
					}

					if (columnnumberontabs == 2) {
						
						tempvalue=u.s(ITEMmasterelcnumber[t]);
						u.log("rownumberontabs,columnnumberontabs, value"+
								rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
						if(!(tempvalue.equals("null")||tempvalue.equals(""))){
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}

					}
					if (columnnumberontabs == 3) {
						
						tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
						u.log("rownumberontabs,columnnumberontabs, value"+
								rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
						if(!(tempvalue.equals("null")||tempvalue.equals(""))){
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}
						
					}
					
					if (columnnumberontabs == 4) {
						try {
							String templocationname = ITEMstring[t].split(":")[1];
							tempvalue=templocationname;
							u.log("rownumberontabs,columnnumberontabs, value"+
									rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
							if(!(tempvalue.equals("null")||tempvalue.equals(""))){
								Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
										tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
							}else{
								tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
								Cursor tempcursor=Tabs1.db.getrowswithvalue(DatabaseHandler.TABLE_MCR_TEMPSLIST, 
										DatabaseHandler.KEY_MCR_TEMPLIST_TITLES[Tabs1.TEMPSECTIONOFBUILDINGCOLUMN], tempvalue);
								tempvalue=tempcursor.getString(Tabs1.TEMPZONENAMECOLUMN);
								Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
										tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
							}

						} catch (Throwable e) {
							tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
							Cursor tempcursor=Tabs1.db.getrowswithvalue(DatabaseHandler.TABLE_MCR_TEMPSLIST, 
									DatabaseHandler.KEY_MCR_TEMPLIST_TITLES[Tabs1.TEMPSECTIONOFBUILDINGCOLUMN], tempvalue);
							tempvalue=tempcursor.getString(Tabs1.TEMPZONENAMECOLUMN);
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}
					}
					if (columnnumberontabs == 5) {
						tempvalue=u.s(ITEMdisplaynumber[t]-1);
						u.log("rownumberontabs,columnnumberontabs, value"+
								rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
						if(!(tempvalue.equals("null")||tempvalue.equals(""))){
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}
					}
					
					if (columnnumberontabs == 6) {
						try {
							String templocationname = ITEMstring[t].split(":")[1];
							tempvalue=templocationname;
							u.log("rownumberontabs,columnnumberontabs, value"+
									rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
							if(!(tempvalue.equals("null")||tempvalue.equals(""))){
								Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
										tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
							}else{
								tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
								Cursor tempcursor=Tabs1.db.getrowswithvalue(DatabaseHandler.TABLE_MCR_TEMPSLIST, 
										DatabaseHandler.KEY_MCR_TEMPLIST_TITLES[Tabs1.TEMPSECTIONOFBUILDINGCOLUMN], tempvalue);
								tempvalue=tempcursor.getString(Tabs1.TEMPCOMMENTSCOLUMN);
								Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
										tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
							}

						} catch (Throwable e) {
							tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
							Cursor tempcursor=Tabs1.db.getrowswithvalue(DatabaseHandler.TABLE_MCR_TEMPSLIST, 
									DatabaseHandler.KEY_MCR_TEMPLIST_TITLES[Tabs1.TEMPSECTIONOFBUILDINGCOLUMN], tempvalue);
							tempvalue=tempcursor.getString(Tabs1.TEMPCOMMENTSCOLUMN);
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}
					}
					if (columnnumberontabs == 10) {
						
						String zontypestring=" ";
						String temporarylocation="null";
						u.log(Tabs1.correspondingtypes);
						try{
							zontypestring=Tabs1.correspondingtypes.get(1);

							String templocationname = ITEMstring[t].split(":")[1];
							temporarylocation=templocationname;
							
							for(int c=0;c<Tabs1.commontemplocations.size();c++){
								if(templocationname.contains(Tabs1.commontemplocations.get(c))){
									zontypestring=Tabs1.correspondingtypes.get(c+1);
									break;
								}
							}
							
						}catch(Throwable e){
							e.printStackTrace();
						}
						Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
								zontypestring,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);	
						tempvalue=zontypestring;
						u.log("zontypestring, "+zontypestring);
						u.log("rownumberontabs,columnnumberontabs, value"+
								rownumberontabs + " " +columnnumberontabs + " " + tempvalue);
						if(!(temporarylocation.equals("null")||temporarylocation.equals(""))){
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}else{
							tempvalue=u.s(ITEMmasterelcnumber[t]) + "."+ u.s(ITEMdisplaynumber[t]);
							Cursor tempcursor=Tabs1.db.getrowswithvalue(DatabaseHandler.TABLE_MCR_TEMPSLIST, 
									DatabaseHandler.KEY_MCR_TEMPLIST_TITLES[Tabs1.TEMPSECTIONOFBUILDINGCOLUMN], tempvalue);
							tempvalue=tempcursor.getString(Tabs1.TEMPZONETYPECOLUMN);
							Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, rownumberontabs, columnnumberontabs,
									tempvalue,DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
						}

					}
				}
				rownumberontabs++;
			}
		}
		
		int totaltempcountbefore=Tabs1.db.countrows(DatabaseHandler.TABLE_MCR_TEMPSLIST);
		for(int x=totaltempsensorcount+1;x<=totaltempcountbefore;x++){
			Tabs1.db.deleteSingleRow(DatabaseHandler.TABLE_MCR_TEMPSLIST, u.s(x));
		}
		
		Tabs1.db.showfulldblog(DatabaseHandler.TABLE_MCR_TEMPSLIST);
	}
	
	public static void addtempvalues() {

		// grab all table values in that order
		int rownumberontabs = 0;
		
		for (int t : templistorderedbyitemnumber) {
			if (ITEMtype[t] == TYPE_TEMPSENSOR) {

				for (int columnnumberontabs = 0; columnnumberontabs < Tabs1.maxexcelcolumns; columnnumberontabs++) {

					if (columnnumberontabs == 1) {
						String tempfloornumber = ITEMstring[t].split(":")[0];
						if (!String.valueOf(tempfloornumber).equals("null")) {
							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.setText(tempfloornumber);
							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.requestFocus();
						}
					}

					if (columnnumberontabs == 2) {
						Log.d("rownumberontabs,columnnumberontabs, u.s(ITEMmasterelcnumber[t]",
								rownumberontabs + " " + columnnumberontabs
										+ " " + u.s(ITEMmasterelcnumber[t]));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.setText(u.s(ITEMmasterelcnumber[t]));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.requestFocus();

					}
					if (columnnumberontabs == 3) {
						Log.d("rownumberontabs,columnnumberontabs, u.s(ITEMmasterelcnumber[t]",
								rownumberontabs + " " + columnnumberontabs
										+ " " + u.s(ITEMmasterelcnumber[t]));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.setText(u.s(ITEMmasterelcnumber[t]) + "."
										+ u.s(ITEMdisplaynumber[t]));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.requestFocus();

					}
					if (columnnumberontabs == 4) {
						Log.d("rownumberontabs,columnnumberontabs, u.s(ITEMmasterelcnumber[t]",
								rownumberontabs + " " + columnnumberontabs
										+ " " + u.s(ITEMmasterelcnumber[t]));
						try {
							String templocationname = ITEMstring[t].split(":")[1];

							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.setText(templocationname);
							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.requestFocus();

						} catch (Throwable e) {

						}
					}
					if (columnnumberontabs == 5) {
						Log.d("rownumberontabs,columnnumberontabs, u.s(ITEMmasterelcnumber[t]",
								rownumberontabs + " " + columnnumberontabs
										+ " " + u.s(ITEMmasterelcnumber[t]));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.setText(u.s(ITEMdisplaynumber[t]-1));
						Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
								.requestFocus();

					}
					
					if (columnnumberontabs == 6) {
						Log.d("rownumberontabs,columnnumberontabs, u.s(ITEMmasterelcnumber[t]",
								rownumberontabs + " " + columnnumberontabs
										+ " " + u.s(ITEMmasterelcnumber[t]));
						try {
							String templocationname = ITEMstring[t].split(":")[1];

							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.setText(templocationname);
							Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]
									.requestFocus();
							
						} catch (Throwable e) {

						}
					}
					if (columnnumberontabs == 10) {
						String zontypestring="Sales Area";
						String templocationname = ITEMstring[t].split(":")[1];
						try{
							List<String> commonlocations = Tabs1.commonlocations;
							List<String> correspondingtypes = Tabs1.correspondingtypes;
							for(int c=0;c<commonlocations.size();c++){
								if(templocationname.contains(commonlocations.get(c))){
									zontypestring=correspondingtypes.get(c);
									break;
								}
							}
							
						}catch(Throwable e){
							
						}
						u.settexttoview(zontypestring, Tabs1.mcrentries[Tabs1.TEMPSENSORCOMMISSIONING][rownumberontabs][columnnumberontabs]);
					}
				}
				rownumberontabs++;
			}
		}
	}

	

	public static void showlongandlat(float x, float y) {

		float h2 = (float) Math.sqrt((y * y) + (x * x));
		float alat = FloorPlanActivity.topleftcornerlatitude;
		Log.d("alat", String.valueOf(alat));
		float along = FloorPlanActivity.topleftcornerlongitude;
		float theta = FloorPlanActivity.bearing;
		Log.d("theta", String.valueOf(theta));
		float omega = (float) (-theta + Math.atan(x / y));
		float o2 = (float) (Math.sin(omega) * h2 * FloorPlanActivity.metersperpixel);
		Log.d("o2", String.valueOf(o2));
		float clong = along + o2 * ratiodegreepermeter;
		float a2 = (float) (o2 / Math.tan(theta));
		Log.d("a2", String.valueOf(a2));
		float clat = alat - a2 * FloorPlanActivity.metersperpixel
				* ratiodegreepermeter;

		Log.d("long,lat", clong + " " + clat);

	}

	public float distancebetweentwopoints(LatLng A, LatLng B) {

		double LongA = A.longitude;
		double LongB = B.longitude;
		double LatA = A.latitude;
		double LatB = B.latitude;

		double earthRadius = 3958.75;
		double latDiff = Math.toRadians(LatB - LatA);
		double lngDiff = Math.toRadians(LongB - LongA);
		double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
				+ Math.cos(Math.toRadians(LatA))
				* Math.cos(Math.toRadians(LatB)) * Math.sin(lngDiff / 2)
				* Math.sin(lngDiff / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = earthRadius * c;

		int meterConversion = 1609;

		double dist = distance * meterConversion;

		return (float) Math.abs(dist);

	}

	public static int[] getsequencenumber(Object obj, View arg0) {

		int[] t = new int[3];

		if (obj instanceof View[][]) {

			View[][] view = (View[][]) obj;
			int x = 0;
			int y = 0;
			for (y = 0; y < maxsams; y++) {
				for (x = 0; x < maxelcs; x++) {
					if (view[x][y] == arg0) {
						t[0] = x;
						t[1] = y;
						break;
					}
				}
			}
		}
		return t;
	}

	public static int getsequencenumberview(View arg0) {
		int t = 0;
		ViewGroup parent = (ViewGroup) arg0.getParent();
		for (int h = 0; h < parent.getChildCount(); h++) {
			if (parent.getChildAt(h) == arg0) {
				t = h;
			}
		}
		return t;
	}

	// public static int getclosestunmaxededelcnumber(int itemnum){
	// double[] elcdistance=new double[ELCCOUNT+1];
	//
	// Log.d("ELCCOUNT",u.s(ELCCOUNT));
	// for (int elcnum=1; elcnum<=ELCCOUNT; elcnum++){
	// elcdistance[elcnum]=Math.sqrt(Math.pow(absolutex-ITEMx[getitemnumofelc(elcnum)],2)+Math.pow(absolutey-ITEMy[getitemnumofelc(elcnum)],2));
	// Log.d("ELC Number, ELC Distance", elcnum+" "+elcdistance[elcnum]);
	// }
	//
	//
	//
	// double distance;
	// double smallestdistance = Double.POSITIVE_INFINITY;
	// int closestelc=-1;
	// for (int elcnum=1; elcnum<=ELCCOUNT; elcnum++){
	// distance=elcdistance[elcnum];
	// if
	// ((distance<smallestdistance)&&!(ITEMtempsensorcount[getitemnumofelc(elcnum)]==maxtempsperelc)){
	// smallestdistance=distance;
	// closestelc=elcnum;
	//
	// }
	// }
	//
	//
	// Log.d("closesunmaxedtelc","ELC#"+closestelc);
	// return closestelc;
	// }
	public static int getitemnumofelc(int elcnum) {
		int itemnum = -1;
		for (int u = 0; u < i; u++) {
			if ((ITEMtype[u] == TYPE_ELC) && ELCdisplaynumber[u] == elcnum) {
				itemnum = u;
				break;
			}
		}
		return itemnum;
	}

	// public static void AUTOSEQUENCEalltempsensorsbasedonlocation(){
	// ITEMmasterelcnumber[i] = getclosestunmaxededelcnumber(i);
	// ITEMdisplaynumber[i] =
	// smallesttempsensornumbernotinuse(ITEMmasterelcnumber[i]);
	// ITEMtempsensorcount[getitemnumofelc(ITEMmasterelcnumber[i])]++;
	//
	// }
	public static double getdistancepixels(int itemnum1, int itemnum2) {
		return Math.sqrt(Math.pow(ITEMx[itemnum1] - ITEMx[itemnum2], 2)
				+ Math.pow(ITEMy[itemnum1] - ITEMy[itemnum2], 2));
	}

	public static int getclosestedelcnumber(int itemnum) {
		// int ELCacCount=0;
		Log.d("ELC Count", u.s(ELCCOUNT));
		// for (int ielc=0;ielc<=i;ielc++){
		// if((ITEMtype[ielc]==TYPE_ELC||ITEMtype[ielc]==TYPE_OLDELC)){
		// ELCacCount++;
		// }
		// }
		// ELCCOUNT=ELCacCount;
		Log.d("New ELC Count", u.s(ELCCOUNT));
		double[] elcdistance = new double[ELCCOUNT + 1];

		Log.d("ELCCOUNT", u.s(ELCCOUNT));
		for (int elcnum = 1; elcnum <= ELCCOUNT; elcnum++) {
			try {
				Log.d("ITEMx[itemnum]", String.valueOf(ITEMx[itemnum]));
				Log.d("ITEMx[getitemnumofelc(elcnum)]",
						String.valueOf(ITEMx[getitemnumofelc(elcnum)]));
				Log.d("ITEMy[itemnum]", String.valueOf(ITEMy[itemnum]));
				Log.d("ITEMy[getitemnumofelc(elcnum)]",
						String.valueOf(ITEMy[getitemnumofelc(elcnum)]));
				Log.d("getitemnumofelc(elcnum)", u.s(getitemnumofelc(elcnum)));

				int templevel = ITEMfloorplannumber[itemnum];
				int elclevel = ITEMfloorplannumber[getitemnumofelc(elcnum)];
				if (FloorPlanActivity.MULTILEVEL) {
					if (templevel != elclevel) {
						elcdistance[elcnum] = Math
								.sqrt(Math
										.pow((ITEMx[itemnum] - ITEMx[getitemnumofelc(elcnum)]),
												2)
										+ Math.pow(
												(ITEMy[itemnum] - ITEMy[getitemnumofelc(elcnum)]),
												2))
								+ ceilingheightpixels;
					} else {
						elcdistance[elcnum] = Math
								.sqrt(Math
										.pow((ITEMx[itemnum] - ITEMx[getitemnumofelc(elcnum)]),
												2)
										+ Math.pow(
												(ITEMy[itemnum] - ITEMy[getitemnumofelc(elcnum)]),
												2));
					}

				} else {
					if (templevel != elclevel) {
						elcdistance[elcnum] = Double.POSITIVE_INFINITY;
					} else {
						elcdistance[elcnum] = Math
								.sqrt(Math
										.pow((ITEMx[itemnum] - ITEMx[getitemnumofelc(elcnum)]),
												2)
										+ Math.pow(
												(ITEMy[itemnum] - ITEMy[getitemnumofelc(elcnum)]),
												2));
					}
				}

				Log.d("ELC Number, ELC Distance", elcnum + " "
						+ elcdistance[elcnum]);
			} catch (Throwable e) {

			}
		}

		double distance;
		double smallestdistance = Double.POSITIVE_INFINITY;
		double newsmallest = Double.POSITIVE_INFINITY;
		double[][] smallestdistanceelc = new double[i + 1][ELCCOUNT + 1];
		for (int x = 0; x < i; x++) {
			for (int y = 0; y < ELCCOUNT; y++) {
				smallestdistanceelc[x][y] = Double.POSITIVE_INFINITY;
			}
		}
		// int closestelc=-1;
		int closestnotmaxed = -1;
		for (int elcnum = 1; elcnum <= ELCCOUNT; elcnum++) {
			distance = elcdistance[elcnum];
			Log.d("elcnumchecking, itemnum, distance", elcnum + " "
					+ getitemnumofelc(elcnum) + " " + distance);
			Log.d("sensors on elc, maxtempsperelc",
					ITEMtempsensorcount[getitemnumofelc(elcnum)] + " "
							+ maxtempsperelc);
			if ((distance < smallestdistance)
					&& (ITEMtempsensorcount[getitemnumofelc(elcnum)] < maxtempsperelc)) {
				smallestdistance = distance;

				closestnotmaxed = elcnum;

				Log.d("Temps adjustment", "temps on elc" + u.s(elcnum) + "is"
						+ u.s(ITEMtempsensorcount[getitemnumofelc(elcnum)])
						+ "max of" + u.s(maxtempsperelc));

			}

		}

		if (closestnotmaxed < 1) {
			showToast("No available ELC");
		}
		Log.d("closestelc", "ELC#" + closestnotmaxed);

		return closestnotmaxed;
	}

	public static void itemstomasterelcs() {
		int totalTEMPCOUNT = 0;
		Log.d("Total items", u.s(i));
		for (int itemp = 0; itemp <= i; itemp++) {
			if (ITEMtype[itemp] == TYPE_TEMPSENSOR) {
				totalTEMPCOUNT++;
			}
		}

		double[][] TemptoELC = new double[i + 1][ELCCOUNT + 1];
		int temploc[] = new int[totalTEMPCOUNT + 1];
		double totaldistances[] = new double[totalTEMPCOUNT + 1];

		Log.d("ELC Count", u.s(ELCCOUNT));
		Log.d("Total temp items", u.s(totalTEMPCOUNT));
		for (int x = 0; x <= i; x++) {
			for (int y = 0; y <= ELCCOUNT; y++) {
				TemptoELC[x][y] = 0;
			}
		}
		for (int x = 0; x <= totalTEMPCOUNT; x++) {
			totaldistances[x] = 0;
		}

		int loc = 1;
		for (int x = 0; x <= i; x++) {
			if (ITEMtype[x] == TYPE_TEMPSENSOR) {
				temploc[loc] = x;
				loc++;
			}
		}
		for (int x = 1; x <= ELCCOUNT; x++) {
			for (int y = 1; y <= totalTEMPCOUNT; y++) {
				Log.d("elcnum in items to master elcs", u.s(x));
				Log.d("temp number in items to master elcs", u.s(y));
				int templevel = ITEMfloorplannumber[temploc[y]];
				int elclevel = ITEMfloorplannumber[getitemnumofelc(x)];
				Log.d("templevel", u.s(templevel));
				Log.d("elclevel", u.s(elclevel));
				if (FloorPlanActivity.MULTILEVEL) {
					if (templevel != elclevel) {
						TemptoELC[y][x] = getdistancepixels(temploc[y],
								getitemnumofelc(x)) + ceilingheightpixels;
					} else {
						TemptoELC[y][x] = getdistancepixels(temploc[y],
								getitemnumofelc(x));
					}

				} else {
					if (templevel != elclevel) {
						TemptoELC[y][x] = Double.POSITIVE_INFINITY;
					} else {
						TemptoELC[y][x] = getdistancepixels(temploc[y],
								getitemnumofelc(x));
					}
				}
			}
		}
		for (int x = 1; x <= totalTEMPCOUNT; x++) {
			for (int y = 1; y <= ELCCOUNT; y++) {
				totaldistances[x] = totaldistances[x] + TemptoELC[x][y];
			}
		}
		// Arrays.sort(totaldistances);

		for (int x = 1; x <= totalTEMPCOUNT; x++) {
			double tempmaxvalue = 0;
			int currenttemp = 0;
			for (int y = 1; y <= totalTEMPCOUNT; y++) {
				if (totaldistances[y] > tempmaxvalue) {
					tempmaxvalue = totaldistances[y];
					currenttemp = y;
				}
			}
			totaldistances[currenttemp] = 0;
			int closestelc = getclosestedelcnumber(temploc[currenttemp]);
			if (closestelc >= 1) {
				ITEMmasterelcnumber[temploc[currenttemp]] = getclosestedelcnumber(temploc[currenttemp]);
				ITEMtempsensorcount[getitemnumofelc(ITEMmasterelcnumber[temploc[currenttemp]])]++;
			} else {
				System.out.println("No available elcs");
			}

		}

	}

	public static void AUTORENUMBERalltempsensorsbasedonlocation() {

		if (FloorPlanActivity.AUTORENUMBERTEMPS) {

			double distancetoelc[][] = new double[maxitems][maxelcs];

			for (int m = 0; m < (i + 1); m++) {
				ITEMtempsensorcount[m] = 0;
			}

			itemstomasterelcs();

			Log.d("Master ELC, Item Number, Distance", "<");
			int tempsensorcount = 0;
			for (int m = 0; m < (i + 1); m++) {
				if (ITEMtype[m] == TYPE_TEMPSENSOR) {
					tempsensorcount++;
					for (int elcnum = 1; elcnum < ELCCOUNT; elcnum++) {
						// distancetoelc[itemnumber][elcnumber]
						int templevel = ITEMfloorplannumber[m];
						int elclevel = ITEMfloorplannumber[getitemnumofelc(elcnum)];
						if (FloorPlanActivity.MULTILEVEL) {
							if (templevel != elclevel) {
								distancetoelc[m][elcnum] = getdistancepixels(m,
										getitemnumofelc(elcnum))
										+ ceilingheightpixels;
							} else {
								distancetoelc[m][elcnum] = getdistancepixels(m,
										getitemnumofelc(elcnum));
							}

						} else {
							if (templevel != elclevel) {
								distancetoelc[m][elcnum] = Double.POSITIVE_INFINITY;
							} else {
								distancetoelc[m][elcnum] = getdistancepixels(m,
										getitemnumofelc(elcnum));
							}
						}

					}

				}
			}

			String ELCTITLEROW = "ts item#";
			for (int h = 1; h < ELCCOUNT; h++) {
				ELCTITLEROW = ELCTITLEROW + "      " + u.s(h);
			}
			System.out.println(ELCTITLEROW);
			String[] ROW = new String[tempsensorcount];
			int rownum = 0;
			for (int m = 0; m < (i + 1); m++) {
				if (ITEMtype[m] == TYPE_TEMPSENSOR) {

					ROW[rownum] = u.s(m);
					for (int elcnum = 1; elcnum < ELCCOUNT; elcnum++) {
						ROW[rownum] = ROW[rownum] + "  "
								+ distancetoelc[m][elcnum];
					}
					System.out.println(ROW[rownum]);
					rownum++;
				}
			}

			// put in order
			int[] tempsensoritemnumbers = new int[i + 1];
			double[] distance = new double[i + 1];
			int tempnum = 0;
			for (int p = 1; p <= ELCCOUNT; p++) {
				Log.d("Master ELC" + p + ", Item Number, Distance", "<");
				for (int m = 0; m < (i + 1); m++) {
					if (ITEMtype[m] == TYPE_TEMPSENSOR
							&& ITEMmasterelcnumber[m] == p) {

						int templevel = ITEMfloorplannumber[m];
						int elclevel = ITEMfloorplannumber[getitemnumofelc(p)];
						if (FloorPlanActivity.MULTILEVEL) {
							if (templevel != elclevel) {
								distance[tempnum] = getdistancepixels(m,
										getitemnumofelc(ITEMmasterelcnumber[m]))
										+ ceilingheightpixels;
							} else {
								distance[tempnum] = getdistancepixels(m,
										getitemnumofelc(ITEMmasterelcnumber[m]));
							}

						} else {
							if (templevel != elclevel) {
								distance[tempnum] = Double.POSITIVE_INFINITY;
							} else {
								distance[tempnum] = getdistancepixels(m,
										getitemnumofelc(ITEMmasterelcnumber[m]));
							}
						}

						tempsensoritemnumbers[tempnum] = m;
						Log.d(u.s(ITEMmasterelcnumber[m]) + "           , "
								+ u.s(m) + "         , " + distance[m], "<");
						tempnum++;

					}
				}
			}

			// for real put in order
			tempsensoritemnumbers = orderarrayreturningsecond(
					tempsensoritemnumbers, distance);
			distance = orderarray(distance);

			loopelc: for (int p = 1; p <= ELCCOUNT; p++) {
				int tempcounttodisplay = 1;
				Log.d("In Order!! Master ELC" + p + ", Item Number, Distance",
						"<");

				for (int distancenum = 0; distancenum < distance.length; distancenum++) {

					if (ITEMtype[tempsensoritemnumbers[distancenum]] == TYPE_TEMPSENSOR
							&& ITEMmasterelcnumber[tempsensoritemnumbers[distancenum]] == p) {
						if (!(tempcounttodisplay > maxtempsperelc)) {
							Log.d(u.s(ITEMmasterelcnumber[tempsensoritemnumbers[distancenum]])
									+ "           , "
									+ tempsensoritemnumbers[distancenum]
									+ "         , " + distance[distancenum],
									"<");
							ITEMdisplaynumber[tempsensoritemnumbers[distancenum]] = tempcounttodisplay;

							tempcounttodisplay++;
						} else {
							FloorPlanActivity.MODE = FloorPlanActivity.MODE_DONOTHING;
						}
					}

				}
			}

			
			// in order by master

		}
		FloorPlanActivity.rewritewholedb();
	}

	public static int[] orderarrayreturningsecond(int[] secondary, double[] j) {

		for (int s = 0; s <= j.length - 1; s++) {
			for (int k = 0; k <= j.length - 2; k++) {
				if (j[k] > j[k + 1]) { // comparing array values

					double temp = 0;
					temp = j[k]; // storing value of array in temp variable

					j[k] = j[k + 1]; // swaping values
					j[k + 1] = temp; // now storing temp value in array

					int secondarytemp = 0;
					secondarytemp = secondary[k]; // storing value of array in
													// temp variable

					secondary[k] = secondary[k + 1]; // swaping values
					secondary[k + 1] = secondarytemp; // now storing temp value
														// in array

				} // end if block
			} // end inner loop
		}
		// end outer loop

		for (int s = 0; s <= j.length - 1; s++) {
			System.out.println(j[s]); // retrieving values of array in ascending
										// order

		}

		return secondary;

	}

	public static double[] orderarray(double[] j) {
		// declaring array with disordered values

		for (int s = 0; s <= j.length - 1; s++) {
			for (int k = 0; k <= j.length - 2; k++) {
				if (j[k] > j[k + 1]) { // comparing array values

					double temp = 0;
					temp = j[k]; // storing value of array in temp variable

					j[k] = j[k + 1]; // swaping values
					j[k + 1] = temp; // now storing temp value in array

				} // end if block
			} // end inner loop
		}
		// end outer loop

		for (int s = 0; s <= j.length - 1; s++) {
			System.out.println(j[s]); // retrieving values of array in ascending
										// order

		}
		return j;
	}

	public static void showtempsensordistribution() {
		System.out.println("Counts per ELC");
		for (int h = 1; h <= ELCCOUNT; h++) {
			System.out.println("ELC" + h + ": "
					+ ITEMtempsensorcount[getitemnumofelc(h)]);
		}

	}

	public static void showToast(final String toast) {

		Toast.makeText(FloorPlanActivity.view.ctx, toast, Toast.LENGTH_SHORT)
				.show();

	}

	public void deleteitem(int itemselectednumber) {
		int type = ITEMtype[itemselectednumber];
		// delete from table
		// shift all links after deleted itemlink -1
		
		
		try {
			deletefromtabsdb(itemselectednumber,type);
			//deletefromtabsandrelink(itemselectednumber, type);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		if (type == TYPE_ELC) {
			
			int deletedelcnumber = ELCdisplaynumber[itemselectednumber];
			u.log("deleting ELC, "+deletedelcnumber);
			deletevaluesforelcfromdb(deletedelcnumber);
			//Tabs1.db.renumberelcsgreaterthanelc(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN],u.s(deletedelcnumber));
			
			
			//Log.d("deletedelcnumber=", u.s(deletedelcnumber));
			// //ELCdisplaynumber[j] = ELCdisplaynumber[j + 1];
//			int lastelcnumber = -1;
//			Log.d("Runs from ", "0 to <=" + i + "");

//			for (int itemnum = 0; itemnum <= i; itemnum++) {
//				Log.d("itemnum", u.s(itemnum));
//
//				if ((ITEMtype[itemnum] == TYPE_ELC)) {
//					Log.d("ELCs found initially",
//							u.s(ELCdisplaynumber[itemnum]));
//					lastelcnumber = ELCdisplaynumber[itemnum];
//
//				}
//			}

		//	Log.d("lastelcnumber", u.s(lastelcnumber));
//			if (!(lastelcnumber == -1)) {
//				for (int elcnumber = deletedelcnumber; elcnumber < lastelcnumber; elcnumber++) {
//					Log.d("about to try transfering view values from ", "ELC"
//							+ (elcnumber + 1) + " to ELC" + (elcnumber));
//					try {
//						while (samtablelayout[elcnumber].getChildCount() > samtablelayout[elcnumber + 1]
//								.getChildCount()) {
//							removesambutton[elcnumber][0].performClick();
//						}
//						while (samtablelayout[elcnumber].getChildCount() < samtablelayout[elcnumber + 1]
//								.getChildCount()) {
//							addsambutton[elcnumber].performClick();
//						}
//						List<View> elcchildrenA = u
//								.getallchildren(samtablelayout[elcnumber]);
//						List<View> elcchildrenB = u
//								.getallchildren(samtablelayout[elcnumber + 1]);
//
//						Log.d("about to start loop moving views ", "ELC"
//								+ (elcnumber + 1) + " to ELC" + (elcnumber));
//						for (int k = 0; k < elcchildrenA.size(); k++) {
//
//							Log.d("Views A,B", elcchildrenA.get(k).toString()
//									+ "," + elcchildrenB.get(k).toString());
//
//							try {
//								u.settexttoview(
//										u.gettextfromview(elcchildrenB.get(k)),
//										elcchildrenA.get(k));
//							} catch (Throwable e) {
//
//							}
//						}
//					} catch (Throwable e) {
//
//					}
//					try {
//						int elca = getitemnumofelc(elcnumber);
//						int elcb = getitemnumofelc(elcnumber + 1);
//						ELCdisplaynumber[elcb] = ELCdisplaynumber[elca];
//						updatedatabase(ELCdisplaynumber[elcb]);
//					} catch (Throwable e) {
//
//					}
//				}
//				// clear leftover elcvalues
//				Log.d("Last elcnumber which needs to be cleared",
//						u.s(lastelcnumber));
//
//				for (int y = 0; y < maxsams; y++) {
//					for (int z = 0; z < maxvalues; z++) {
//						try {
//
//							samtablelayout[lastelcnumber] = null;
//							samstrings[lastelcnumber][y][z] = null;
//							samsrow[lastelcnumber][y] = null;
//							samsedittext[lastelcnumber][y][z] = null;
//							samsspinner[lastelcnumber][y][z] = null;
//							removesambutton[lastelcnumber][y] = null;
//
//							samview[lastelcnumber][y][z] = null;
//							samoldviewparent[lastelcnumber][y][z] = null;
//							samoldviewindex[lastelcnumber][y] = null;
//							samoldviewlp[lastelcnumber][y][z] = null;
//						} catch (Throwable e) {
//
//						}
//					}
//				}
//
				
//			}

			ELCCOUNT--;
			
			

		}

		int last = 0;

		for (int j = itemselectednumber; j < maxitems - 1; j++) {
			ITEMrect[j] = ITEMrect[j + 1];
			ITEMtype[j] = ITEMtype[j + 1];
			ITEMtempsensorcount[j] = ITEMtempsensorcount[j + 1];
			ITEMsizepercent[j] = ITEMsizepercent[j + 1];
			ITEMsamscount[j] = ITEMsamscount[j + 1];
			ITEMmasterelcnumber[j] = ITEMmasterelcnumber[j + 1];
			ITEMdisplaynumber[j] = ITEMdisplaynumber[j + 1];

			ITEMbitmap[j] = ITEMbitmap[j + 1];
			ScaledITEMbitmap[j] = ScaledITEMbitmap[j + 1];
			SCALEDITEMx[j] = SCALEDITEMx[j + 1];
			SCALEDITEMy[j] = SCALEDITEMy[j + 1];
			ITEMx[j] = ITEMx[j + 1];
			ITEMy[j] = ITEMy[j + 1];

			ITEMtextoffsetx[j] = ITEMtextoffsetx[j + 1];
			ITEMtextoffsety[j] = ITEMtextoffsety[j + 1];

			ITEMstring[j] = ITEMstring[j + 1];
			ITEMfontsize[j] = ITEMfontsize[j + 1];
			ITEMfontcolor[j] = ITEMfontcolor[j + 1];
			ITEMfloorplannumber[j] = ITEMfloorplannumber[j + 1];

			ITEMLINKtabnumber[j] = ITEMLINKtabnumber[j + 1];
			ITEMLINKtabitemnumber[j] = ITEMLINKtabitemnumber[j + 1];
			ELCdisplaynumber[j] = ELCdisplaynumber[j + 1];
			
			last = i + 1;
		}
		ITEMrect[last] = null;
		ITEMtype[last] = 0;
		ITEMtempsensorcount[last] = 0;
		ITEMsizepercent[last] = 100;
		ITEMsamscount[last] = 0;
		ITEMmasterelcnumber[last] = 0;
		ITEMdisplaynumber[last] = 0;

		ITEMbitmap[last] = null;
		ScaledITEMbitmap[last] = null;
		SCALEDITEMx[last] = 0;
		SCALEDITEMy[last] = 0;
		ITEMx[last] = 0;
		ITEMy[last] = 0;

		ITEMtextoffsetx[last] = 0;
		ITEMtextoffsety[last] = 0;

		ITEMstring[last] = null;
		ITEMfontsize[last] = 0;
		ITEMfontcolor[last] = 0;
		ITEMfloorplannumber[last] = 0;

		ITEMLINKtabnumber[last] = NOTABSLINK;
		ITEMLINKtabitemnumber[last] = NOTABSLINK;
		ELCdisplaynumber[last] = 0;
		i--;

		try {
			AUTORENUMBERalltempsensorsbasedonlocation();
		} catch (Throwable e) {

		}
		invalidate();
		FloorPlanActivity.rewritewholedb();
	}

	public static void resizeicons() {
		// difference is the increase or decrease of the percent.

		// do once
		if (resizeboolean) {
			resizeiconsseekbar.setProgress((int) ((float) resizeiconsseekbar
					.getMax() / (float) 4));
			lastposition = (int) ((float) resizeiconsseekbar.getMax() / (float) 4);
			resizeboolean = false;
		}

		Log.d("lastposition", u.s(lastposition));
		currentpercentchangeone = (int) ((float) resizeiconsseekbar
				.getProgress() - lastposition);

		Log.d("currentpercentchangeone", u.s(currentpercentchangeone));
		Log.d("lastpercentchangeone", u.s(lastpercentchangeone));

		if (currentpercentchangeone != lastpercentchangeone) {
			if (FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_RESIZEICONS) {
				for (int itemselectednumber = 0; itemselectednumber < i; itemselectednumber++) {
					if (ITEMfloorplannumber[itemselectednumber] == fp) {

						commenceresizeofitem(itemselectednumber, true);
					}
				}
			} else {
				if (ITEMfloorplannumber[itemselectednumber] == fp) {
					commenceresizeofitem(itemselectednumber, true);
				}
			}

			lastpercentchangeone = currentpercentchangeone;
			lastposition = resizeiconsseekbar.getProgress();
		}
	}

	public static void switchfloorplan(int fp) {
		Log.d("fp in switchfloorplan", u.s(fp));
		FloorPlanActivity.floorplantitle.setText(Tabs1.sitefptextview[fp]
				.getText().toString());

	}

	public static void drawitem(int w, Canvas canvas, int FINALBITMAPSCALE) {

		if (FloorPlanActivity.ICONSCALING) {
			// dont draw on canvas, draw on relative layout over canvas
			// this sets the size of icons constant so they dont change when you
			// zoom in and out
			// ScaleTheIconAsPercentageOfScreen(w);
			itemscalefactor = mScaleFactor;
			// now drawing occurs on floorplanactivity and not on view
		} else {
			itemscalefactor = 1f;
		}
		if (!(ITEMbitmap[w] == null)) {

			// bitmaps draw from top left corner, show we have to shift it to
			// match where our finger is
			float x = ITEMx[w] - ITEMbitmap[w].getWidth() / 2;
			float y = ITEMy[w] - ITEMbitmap[w].getHeight() / 2;

			p.setTextSize(ITEMfontsize[w] / itemscalefactor);

			if (!(ITEMtype[w] == TYPE_METERSTOPIXEL)) {
				if (!FloorPlanActivity.NGBICONS) {
					drawlhiicons(w, canvas, FINALBITMAPSCALE, x, y);
				} else {
					// show indicator that this is ngb floor plan
					drawNGBicons(w, canvas, FINALBITMAPSCALE, x, y);
				}
			}

		} else {
			if (ITEMtype[w] == TYPE_ADDTEXT) {
				String text = ITEMstring[w];
				float initialtextsize = p.getTextSize();
				p.setTextSize(ITEMfontsize[w]/ itemscalefactor);
				float totalwidth = u.getwidthofpainttext(p, text);

				float textlocationx = ITEMx[w];
				float textlocationy = ITEMy[w];

				if (FINALBITMAPSCALE != 1) {
					textlocationx = textlocationx / FINALBITMAPSCALE;
					textlocationy = textlocationy / FINALBITMAPSCALE;
				}

				p.setColor(ITEMfontcolor[w]);

				textpadding = p.getTextSize() / paddingvariable;
				ITEMrect[w] = new Rect(
						(int) (textlocationx - textpadding),
						(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
						(int) (textlocationx + totalwidth + textpadding),
						(int) (textlocationy + textpadding));
				canvas.drawRoundRect(new RectF(ITEMrect[w]), textpadding,
						textpadding, p);

				p.setColor(Color.WHITE);
				canvas.drawText(text, textlocationx, textlocationy, p);
				p.setTextSize(initialtextsize);
				// if the last drawnitem is a text set mode to do nothing

			}

		}

	}

	public static void commenceresizeofitem(int itemselectednumber,
			boolean seekbar) {
		if (seekbar) {
			ITEMsizepercent[itemselectednumber] = ITEMsizepercent[itemselectednumber]
					+ currentpercentchangeone;
			Log.d("ITEMsizepercent[itemselectednumber]",
					u.s(ITEMsizepercent[itemselectednumber]));
			ITEMfontsize[itemselectednumber] = (int) ((float) defaulttextsize
					* (float) ITEMsizepercent[itemselectednumber] / (float) 100);
			// p.setTextSize(defaulttextsize * currentpercentchangeone / 100);
			Log.d("p.textsize",
					u.s(defaulttextsize * currentpercentchangeone / 100));

			Log.d("currentpercentchangeone", u.s(currentpercentchangeone));
			Log.d("lastpercentchangeone", u.s(lastpercentchangeone));
		}
		int i = ITEMtype[itemselectednumber];
		Log.d("i", u.s(i));
		
		if(!(i==TYPE_ADDTEXT)){
			float bitheight = (float) originaliconbitmap[i].getHeight();
			try{
			bitheight = bitheight * (float) ITEMsizepercent[itemselectednumber] / (float) 100;
			}catch(Throwable e){
				
			}
			float bitwidth = (float) originaliconbitmap[i].getWidth();
			try{
			bitwidth = bitwidth * (float) ITEMsizepercent[itemselectednumber] / (float) 100;
			}catch(Throwable e){
				
			}
			if (bitheight < 1) {
				bitheight = 1;
			}
			if (bitwidth < 1) {
				bitwidth = 1;
			}
	
			Log.d("icon bitheight, bitwidth", bitheight + "  " + bitwidth);
			iconbitmap[i] = Bitmap.createScaledBitmap(originaliconbitmap[i],
					(int) bitwidth, (int) bitheight, true);
	
			ITEMbitmap[itemselectednumber] = iconbitmap[i];
	
			
			
			if (!(FloorPlanActivity.ACTION == FloorPlanActivity.ACTION_RESIZEICONS)) {
				try {
					iconbitmap[i] = originaliconbitmap[i];
				} catch (Throwable e) {
	
				}
			}
			}
		refreshclickablearea(itemselectednumber, FloorPlanActivity.FINALBITMAPSCALE);
	}

//	public static void createandlinkitemontabs(int itemnumber, int tab) {
//		u.log("createandlinkitemontabs("+itemnumber+","+tab+")");
//		ITEMLINKtabnumber[itemnumber] = tab;
//		u.log("ITEMLINKtabnumber["+itemnumber+"]="+tab);
//		if (tab == Tabs1.SITETAB) {
//			// ITEMLINKtabitemnumber[itemnumber]=Tabs1.siteitemcount;
//		}
//		if (tab == Tabs1.ASSETSTAB) {
//			 
//			Tabs1.addasset.performClick();
//			 u.log(Tabs1.assetcount-1);
//			 ITEMLINKtabitemnumber[itemnumber]=Tabs1.assetcount-1;
//			 u.log(Tabs1.assetcount-1);
//		
//		}
//		if (tab == Tabs1.COMPONENTSTAB) {
//			 u.log("componentcount before click "+u.s(Tabs1.componentcount-1));
//			Tabs1.addcomponent.performClick();
//			
//			 ITEMLINKtabitemnumber[itemnumber]=Tabs1.componentcount-1;
//			 u.log("componentcount after click "+u.s(Tabs1.componentcount-1));
//		}
//
//	}

	public static void updatetabsdatabases(int itemselectednumber) {
		
		int type=ITEMtype[itemselectednumber];
		int tab=gettabofitemselected(itemselectednumber);
		String table=getdbtable(itemselectednumber);
		String name=getGenericDisplayText(itemselectednumber);
		u.log("table,"+table);
		u.log("name,"+name);
		u.log("tab,"+tab);
		
		int tabitemnumber = getobjectcountfromdb(tab)+1;
		u.log(tabitemnumber+" should be 1 on new project");
		try{
			tabitemnumber=tabitemnumber(tab, itemselectednumber);
			
		}catch(Throwable e){
			e.printStackTrace();
		}
		u.log("tabitemnumber ,"+tabitemnumber);
		
		switch (type) {
		case TYPE_BMS:
			
			//db.addorupdatemulticolumn(Table, row, column , value , Key);
			String[] Attributes1={getGenericDisplayText(itemselectednumber),"1",Tabs1.assettypespinneradapter.getItem(3),"","0","","0","0","",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes1, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			break;
		case TYPE_TEMPSENSOR:
			String[] Attributes2={getGenericDisplayText(itemselectednumber),Tabs1.componenttypespinneradapter.getItem(5),"N/A","","To Be Installed",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes2, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
			break;
		case TYPE_SAMARRAY:
			String[] Attributes3={getGenericDisplayText(itemselectednumber),Tabs1.componenttypespinneradapter.getItem(4),"N/A","","To Be Installed",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes3, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			break;
		case TYPE_ELC:
			String[] Attributes4={getGenericDisplayText(itemselectednumber),Tabs1.componenttypespinneradapter.getItem(1),"N/A","","To Be Installed",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes4, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
			break;
		case TYPE_ETHERNETPORT:
			String[] Attributes5={getGenericDisplayText(itemselectednumber),Tabs1.componenttypespinneradapter.getItem(2),"N/A","","To Be Installed",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes5, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
			break;
		case TYPE_DISTRIBUTIONBOARD:
			String[] Attributes6={getGenericDisplayText(itemselectednumber),"1",Tabs1.assettypespinneradapter.getItem(20),"","0","","0","0","",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes6, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
			break;
		case TYPE_GATEWAY:
			String[] Attributes7={getGenericDisplayText(itemselectednumber),Tabs1.componenttypespinneradapter.getItem(3),"N/A","","To Be Installed",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes7, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
		
			break;
		case TYPE_DATAHUB:
			String[] Attributes8={getGenericDisplayText(itemselectednumber),"1",Tabs1.assettypespinneradapter.getItem(19),"","0","","0","0","",u.s(itemselectednumber)};
			Tabs1.db.addentirerow(table,Attributes8, tabitemnumber);
			Tabs1.db.showfulldblog(table);
			
			break;

		}
//		DB_sitename.add("sitename" + u.s(s));
//		DB_sitesizem.add("sitesizem" + u.s(s));
//		DB_sitesizeft.add("sitesizeft" + u.s(s));
//		DB_sitedescription.add("sitedescription" + u.s(s));
//		DB_sitetype.add("sitetype" + u.s(s));
//		
//		DB_componentname.add("componentname" + u.s(c));
//		DB_componenttype.add("componenttype" + u.s(c));
//		DB_componentspec.add("componentspec" + u.s(c));
//		DB_componentlocation.add("componentlocation" + u.s(c));
//		DB_componentnotes.add("componentnotes" + u.s(c));
//		
//		DB_assetname.add("assetname" + u.s(i));
//		DB_assetquantity.add("assetquantity" + u.s(i));
//		DB_assettype.add("assettype" + u.s(i));
//		DB_assetmodel.add("assetmodel" + u.s(i));
//		DB_assetpower.add("assetpower" + u.s(i));
//		DB_assetlocation.add("assetlocation" + u.s(i));
//		DB_assetserviceaream.add("assetserviceaream" + u.s(i));
//		DB_assetserviceareaft.add("assetserviceareaft" + u.s(i));
//		DB_assetnotes.add("assetnotes" + u.s(i));
/*		
		if (ITEMtype[itemselectednumber] == TYPE_DISTRIBUTIONBOARD) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_ASSETINFO, "assetname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));
			
		}
		if (ITEMtype[itemselectednumber] == TYPE_SAMARRAY) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_COMPONENTINFO, "componentname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));
//			
//			int selectiononspinner = 4;
//			view = Tabs1.componenttype[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(selectiononspinner);
//			view.requestFocus();
//
//			view = Tabs1.componentnotes[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("TO BE INSTALLED");
//			view.requestFocus();

		}
		if (ITEMtype[itemselectednumber] == TYPE_DATAHUB) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_ASSETINFO, "assetname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));
//
//			view = Tabs1.quantity[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("1");
//			view.requestFocus();
//
//			int selectiononspinner = 19;
//			view = Tabs1.type[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(selectiononspinner);
//			view.requestFocus();

		}
		if (ITEMtype[itemselectednumber] == TYPE_BMS) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_ASSETINFO, "assetname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));

//			view = Tabs1.quantity[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("1");
//			view.requestFocus();
//
//			int bmsselectiononspinner = 3;
//			view = Tabs1.type[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(bmsselectiononspinner);
//			view.requestFocus();

		}
		if (ITEMtype[itemselectednumber] == TYPE_ELC) {
//			
			Tabs1.db.addorupdate(Tabs1.db.TABLE_COMPONENTINFO, "componentname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));
			
		
//			int elcselectiononspinner = 1;
//			view = Tabs1.componenttype[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(elcselectiononspinner);
//			view.requestFocus();
//
//
//			view = Tabs1.componentnotes[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("TO BE INSTALLED");
//			view.requestFocus();
		}
		if (ITEMtype[itemselectednumber] == TYPE_GATEWAY) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_COMPONENTINFO, "componentname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));
//
//
//			int gatewayselectiononspinner = 3;
//			view = Tabs1.componenttype[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(gatewayselectiononspinner);
//			view.requestFocus();
//
//			
//			view = Tabs1.componentnotes[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("TO BE INSTALLED");
//			view.requestFocus();
		}
		if (ITEMtype[itemselectednumber] == TYPE_ETHERNETPORT) {
			Tabs1.db.addorupdate(Tabs1.db.TABLE_COMPONENTINFO, "componentname"
					+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
					getGenericDisplayText(itemselectednumber));

//			int lanselectiononspinner = 2;
//			view = Tabs1.componenttype[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//			((Spinner) view).setSelection(lanselectiononspinner);
//			view.requestFocus();
//
//			view = Tabs1.componentnotes[ITEMLINKtabitemnumber[itemselectednumber]].et;
//			((AutoCompleteTextView) view).setText("TO BE INSTALLED");
//			view.requestFocus();
		}

		if (ITEMtype[itemselectednumber] == TYPE_TEMPSENSOR) {
			try{
				Tabs1.db.addorupdate(Tabs1.db.TABLE_COMPONENTINFO, "componentname"
						+ u.s(ITEMLINKtabitemnumber[itemselectednumber]),
						getGenericDisplayText(itemselectednumber));
//	
//				int tempsensorselectiononspinner = 5;
//				view = Tabs1.componenttype[ITEMLINKtabitemnumber[itemselectednumber]].sp;
//				((Spinner) view).setSelection(tempsensorselectiononspinner);
//				view.requestFocus();
//	
//				view = Tabs1.componentnotes[ITEMLINKtabitemnumber[itemselectednumber]].et;
//				((AutoCompleteTextView) view).setText("TO BE INSTALLED");
//				view.requestFocus();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
*/
		Tabs1.db.showfulldblog(table);
	}

	public void deleteorduplicatepicturedialog(final File file,
			final ImageView imageview, final TableRow parentrow) {

		Button AddDetailsbutton = new Button(ctx);
		AddDetailsbutton.setText("Add Details");
		AddDetailsbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				// Get the text file
				File file1 = new File(file.getAbsolutePath().replace(".jpg",
						".txt"));
				if (file1.exists()) {
					// File f = new
					// File(Environment.getExternalStorageDirectory(),
					// "fileOut.txt");
					// Read text from file
					Log.d("Edit Caption", " text file exists");
					StringBuilder detailtext = new StringBuilder();

					try {
						BufferedReader br = new BufferedReader(new FileReader(
								file1));
						String line;

						while ((line = br.readLine()) != null) {
							detailtext.append(line);
							detailtext.append('\n');
						}
					} catch (IOException e) {
						// You'll need to add proper error handling here
					}
					Log.d("text files", detailtext.toString());
					int n = detailtext.length();
					int i = 0;
					int j = 0;
					for (i = 0; i < n; i++) {
						if (detailtext.charAt(i) == '\n') {
							break;
						}

					}

					oldcaption = detailtext.substring(9, i);
					for (j = i; j < n; j++) {
						if (detailtext.charAt(j) == ':') {
							break;
						}

					}

					oldnotes = detailtext.substring(j + 1, n);
					Log.d("oldcaption", oldcaption);
					Log.d("oldnotes", oldnotes);

				}

				caption(file);
				deleteorduplicatepicturedialog.cancel();

			}

		});

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage("Image Options")
				.setCancelable(true)
				.setView(AddDetailsbutton)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								deleteorduplicatepicturedialog.dismiss();
								deletepicturedialog(file, imageview, parentrow);

							}
						})
				.setNeutralButton("Duplicate",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								try {
									FileInputStream in = new FileInputStream(
											file);
									FileOutputStream out = new FileOutputStream(
											FloorPlanActivity
													.DevelopeFileName(null,gettabofitemselected(itemselectednumber)));
									byte[] buf = new byte[4096];
									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
									in.close();
									out.close();
									menu.dismiss();
									showitemmenu(itemselectednumber,gettabofitemselected(itemselectednumber));
									// add1pic();
								} catch (Throwable e) {
									e.printStackTrace();
								}

								try {
									File file1 = new File(file
											.getAbsolutePath().replace(".jpg",
													".txt"));
									Log.d("file1 path suppose to be .txt file",
											file1.getAbsolutePath());
									FileInputStream in = new FileInputStream(
											file1);
									FileOutputStream out = new FileOutputStream(
											FloorPlanActivity
													.DevelopeFileName("txt",gettabofitemselected(itemselectednumber)));
									byte[] buf = new byte[4096];
									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
									in.close();
									out.close();
									// add1pic();
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		deleteorduplicatepicturedialog = builder.create();

		// CustomizeDialog customizeDialog = new CustomizeDialog(this);
		deleteorduplicatepicturedialog.show();
		// this.finish();
	}
	
	public void caption(final File file) {

		File file1 = new File(file.getAbsolutePath().replace(".jpg", ".txt"));

		Log.d("5/21", file.getAbsolutePath());

		final ImageView imageview = new ImageView(ctx);
		imageview.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = 8;
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), o);
		imageview.setImageBitmap(bm);

		TableLayout captionEntryView = new TableLayout(ctx);
		captionEntryView.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));

		final TextView captionlabel = new TextView(ctx);
		captionlabel.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));
		captionlabel.setText("Caption:");

		final TextView Notelabel = new TextView(ctx);
		Notelabel.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));
		Notelabel.setText("Notes:");

		final AutoCompleteTextView caption = new AutoCompleteTextView(ctx);
		caption.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT,
				1f));

		final AutoCompleteTextView Note = new AutoCompleteTextView(ctx);
		Note.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

		if (file1.exists()) {
			caption.setText(oldcaption);
			Note.setText(oldnotes);
		}

		TableRow tr1 = new TableRow(ctx);
		tr1.addView(imageview);

		TableRow tr2 = new TableRow(ctx);
		tr2.addView(captionlabel);

		TableRow tr3 = new TableRow(ctx);
		tr3.addView(caption);

		TableRow tr4 = new TableRow(ctx);
		tr4.addView(Notelabel);

		TableRow tr5 = new TableRow(ctx);
		tr5.addView(Note);

		captionEntryView.addView(tr1);
		captionEntryView.addView(tr2);
		captionEntryView.addView(tr3);
		captionEntryView.addView(tr4);
		captionEntryView.addView(tr5);

		AlertDialog.Builder adb = new AlertDialog.Builder(ctx)
				.setCancelable(false)
				.setTitle("Insert Caption")
				.setView(captionEntryView)

				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								dialog.cancel();

							}
						})
				.setPositiveButton("Insert",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String tempfilename = file.getAbsolutePath()
										.replace(".jpg", "modified.jpg");

								File file1 = new File(file.getAbsolutePath()
										.replace(".jpg", ".txt"));
								try {

									Bitmap inputbitmap = BitmapFactory
											.decodeFile(file.getAbsolutePath());
									if (file1.exists()) {

										file1.delete();
										if (!oldcaption.equals("")) {
											inputbitmap = chopoffcaption(
													inputbitmap, oldcaption);
										}
									}

									FileOutputStream out = new FileOutputStream(
											tempfilename);
									// Grap bitmap to be modified
									String capstring = caption.getText()
											.toString();
									// Do/Save modifications
									if (!capstring.equals("")) {
										EditBitmap(inputbitmap,
												caption.getText().toString())
												.compress(
														Bitmap.CompressFormat.JPEG,
														90, out);
									} else {
										inputbitmap.compress(
												Bitmap.CompressFormat.JPEG, 90,
												out);
									}

									// Grab modified bitmap
									// display modified bitmap to imageview
									// displayimageview.setImageBitmap(outputbitmap);
									// writing notes to textfile
									String inputtext = Note.getText()
											.toString();

									BufferedWriter writer = new BufferedWriter(
											new FileWriter(file1, true));

									writer.write("Caption: "
											+ caption.getText().toString()
											+ "\n" + "\n" + "\n" + "Notes:"
											+ inputtext);
									writer.newLine();
									writer.flush();
									writer.close();

									File tempfile = new File(tempfilename);
									file.delete();
									tempfile.renameTo(file);

								} catch (Exception e) {
									e.printStackTrace();
								}
								menu.dismiss();
							
								try{
									showitemmenu(itemselectednumber,gettabofitemselected(itemselectednumber));
								}catch(Throwable e){
									
								}
								
								// refresh1pic(typeselected,
								// u.i(numberselected),
								// u.i(subnumberselected));
							}
						});
		adb.create().show();

	}

	private Bitmap EditBitmap(Bitmap InputBitmap, String text) {

		// Create dummy bitmap with dimensions of inputbitmap
		Bitmap modifiedbitmaptobesaved = Bitmap.createBitmap(
				InputBitmap.getWidth(), InputBitmap.getHeight(),
				Bitmap.Config.ARGB_8888);
		Paint p = new Paint();

		int fontsize = determineMaxTextSize(text, InputBitmap.getWidth());
		Bitmap TextBitmap = Bitmap.createBitmap(InputBitmap.getWidth(),
				fontsize + fontsize, Bitmap.Config.ARGB_8888);
		Canvas textcanvas = new Canvas(TextBitmap);

		p.setTextSize(fontsize);
		p.setColor(Color.RED);

		textcanvas.drawText(text, 0, TextBitmap.getHeight()
				- ((float) 1.25 * (float) fontsize), p);
		Bitmap combinedBitmap = Bitmap.createBitmap(InputBitmap.getWidth(),
				InputBitmap.getHeight() + fontsize, Bitmap.Config.ARGB_8888);
		Log.d("Original Height, Original Height+fontsize",
				InputBitmap.getHeight() + " " + InputBitmap.getHeight()
						+ fontsize);
		Canvas comboImage = new Canvas(combinedBitmap);

		comboImage.drawBitmap(InputBitmap, 0f, 0f, null);
		comboImage.drawBitmap(TextBitmap, 0f, InputBitmap.getHeight(), null);

		return combinedBitmap;
	}

	private Bitmap chopoffcaption(Bitmap inputbitmap, String oldcaption) {
		// TODO Auto-generated method stub
		int fontsize = determineMaxTextSize(oldcaption, inputbitmap.getWidth());
		Bitmap originalBitmap = Bitmap.createBitmap(inputbitmap, 0, 0,
				inputbitmap.getWidth(), inputbitmap.getHeight() - fontsize);
		return originalBitmap;
	}

	private int determineMaxTextSize(String str, float maxWidth) {
		int size = 0;
		Paint paint = new Paint();

		do {
			paint.setTextSize(++size);
		} while (paint.measureText(str) < maxWidth - 10);

		return size;
	} // End getMaxTextSize()

	public void deletepicturedialog(final File file, final ImageView imageview,
			final TableRow parentrow) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage("Are you sure you want to delete this image?")
				.setCancelable(false)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								File parentfile = file.getParentFile();
								file.delete();
								try {
									String associatedtextfile = file
											.getAbsolutePath().split(".")[file
											.getAbsolutePath().split(".").length - 1];
									Log.d("associatedtextfile",
											associatedtextfile);
									File textfile = new File(associatedtextfile);
									textfile.delete();
								} catch (Throwable e) {
									e.printStackTrace();
								}
								Log.d("parentfile.list.length",
										u.s(parentfile.list().length));
								if (parentfile.list().length == 0) {
									parentfile.delete();
								}
								parentrow.removeView(imageview);
								deletepicturedialog.dismiss();

							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						deletepicturedialog.cancel();
					}
				});
		deletepicturedialog = builder.create();

		// CustomizeDialog customizeDialog = new CustomizeDialog(this);
		deletepicturedialog.show();
		// this.finish();
	}

	public static void drawscale(Canvas canvas) {
		float textoutlinewidth = (float) 3;
		float lineoutlinewidth = (float) 8;
		float linestrokewidth = (float) 4;
		int fillcolor = Color.DKGRAY;
		int outlinecolor = Color.GRAY;

		Paint mTextPaint = new Paint();
		mTextPaint.setTextSize(defaulttextsize);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(fillcolor);
		mTextPaint.setStyle(Paint.Style.FILL);

		Paint mTextPaintOutline = new Paint();
		mTextPaintOutline.setTextSize(defaulttextsize);
		mTextPaintOutline.setAntiAlias(true);
		mTextPaintOutline.setColor(outlinecolor);
		mTextPaintOutline.setStyle(Paint.Style.STROKE);
		mTextPaintOutline.setStrokeWidth(textoutlinewidth);

		Paint mLinePaint = new Paint();
		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(fillcolor);
		mLinePaint.setStyle(Paint.Style.FILL);
		mLinePaint.setStrokeWidth(linestrokewidth);

		Paint mLinePaintOutline = new Paint();
		mLinePaintOutline.setAntiAlias(true);
		mLinePaintOutline.setColor(outlinecolor);
		mLinePaintOutline.setStyle(Paint.Style.STROKE);
		mLinePaintOutline.setStrokeWidth(lineoutlinewidth);

		int screenwidth = rect.right - rect.left;
		int screenheight = rect.bottom - rect.top;

		float startX = rect.left + (5 * screenwidth / 100);
		float startY = rect.top + (95 * screenheight / 100);
		float stopX = startX + (10 * screenwidth / 100);
		float stopY = startY;

		float metersperpixel = FloorPlanActivity.metersperpixel;
		float feetperpixel = (float) 3.2808399 * metersperpixel;

		float scalelinelimitpixels = stopX - startX;
		float scalelinelimitmeters = scalelinelimitpixels * metersperpixel;
		float scalelinelimitfeet = scalelinelimitpixels * feetperpixel;

		System.out.println(scalelinelimitpixels + " " + scalelinelimitmeters
				+ " " + scalelinelimitfeet);

		float stopXmet = (float) .1 / metersperpixel;
		float stopXcus = (float) .1 / feetperpixel;
		if (scalelinelimitmeters > 1) {
			stopXmet = (float) 1 / metersperpixel;
			if (scalelinelimitmeters > 2) {
				stopXmet = (float) 2 / metersperpixel;
				if (scalelinelimitmeters > 5) {
					stopXmet = (float) 5 / metersperpixel;
					if (scalelinelimitmeters > 10) {
						stopXmet = (float) 10 / metersperpixel;
					}
				}
			}
		}
		if (scalelinelimitfeet > 1) {
			stopXcus = (float) 1 / feetperpixel;
			if (scalelinelimitfeet > 2) {
				stopXcus = (float) 2 / feetperpixel;
				if (scalelinelimitfeet > 5) {
					stopXcus = (float) 5 / feetperpixel;
					if (scalelinelimitfeet > 10) {
						stopXcus = (float) 10 / feetperpixel;
					}
				}
			}
		}
		int[] increments = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000,
				5000, 10000, 20000, 50000, 100000, 200000, 500000 };
		for (int j : increments) {
			if (scalelinelimitmeters > j) {
				stopXmet = (float) j / metersperpixel;
			}
		}
		for (int j : increments) {
			if (scalelinelimitfeet > j) {
				stopXcus = (float) j / feetperpixel;
			}
		}

		float meters = stopXmet * metersperpixel;
		float feet = stopXcus * feetperpixel;

		mTextPaint.setStrokeWidth((mTextPaint.getStrokeWidth() / 2)
				/ mScaleFactor);
		mTextPaint.setTextSize((mTextPaint.getTextSize() / 2) / mScaleFactor);

		mTextPaintOutline
				.setStrokeWidth(((mTextPaintOutline.getStrokeWidth() / 2) / mScaleFactor));
		mTextPaintOutline.setTextSize((mTextPaintOutline.getTextSize() / 2)
				/ mScaleFactor);

		mLinePaint
				.setStrokeWidth(((mLinePaint.getStrokeWidth() / 2) / mScaleFactor));

		mLinePaintOutline
				.setStrokeWidth(((mLinePaintOutline.getStrokeWidth() / 2) / mScaleFactor));

		System.out.println(mPosX + " " + mScaleFactor + " " + startX + " "
				+ startY + " " + stopX + " " + stopY);
		System.out.println(rect.left + " " + rect.top + " " + rect.right + " "
				+ rect.bottom);
		if (!(FloorPlanActivity.metersperpixel == FloorPlanActivity.NOSCALESET)) {

			if (stopXcus > stopXmet) {
				stopX = stopXcus + startX;
			} else {
				stopX = stopXmet + startX;
			}
		}

		// Outline
		// top metric
		canvas.drawLine(stopXmet + startX, startY, stopXmet + startX, startY
				- (1 * screenheight / 100), mLinePaintOutline);
		// buttom customery line
		canvas.drawLine(stopXcus + startX, startY, stopXcus + startX, startY
				+ (1 * screenheight / 100), mLinePaintOutline);
		// main line
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaintOutline);
		// left line
		canvas.drawLine(startX, startY + (1 * screenheight / 100), startX,
				startY - (1 * screenheight / 100), mLinePaintOutline);

		if (!(FloorPlanActivity.metersperpixel == FloorPlanActivity.NOSCALESET)) {
			// left stationary line

			canvas.drawText(String.format("%.1f", meters) + " m", startX
					+ ((float) .5 * screenwidth / 100), startY
					- ((float) .5 * screenheight / 100), mTextPaintOutline);
			canvas.drawText(String.format("%.1f", meters) + " m", startX
					+ ((float) .5 * screenwidth / 100), startY
					- ((float) .5 * screenheight / 100), mTextPaint);

			canvas.drawText(String.format("%.1f", feet) + " ft", startX
					+ ((float) .5 * screenwidth / 100), startY
					+ ((float) 1.7 * screenheight / 100), mTextPaintOutline);
			canvas.drawText(String.format("%.1f", feet) + " ft", startX
					+ ((float) .5 * screenwidth / 100), startY
					+ ((float) 1.7 * screenheight / 100), mTextPaint);

		} else {
			mTextPaintOutline.setColor(Color.RED);

			canvas.drawText("No Scale Set", startX
					+ ((float) .5 * screenwidth / 100), startY
					- ((float) .5 * screenheight / 100), mTextPaintOutline);
			canvas.drawText("No Scale Set", startX
					+ ((float) .5 * screenwidth / 100), startY
					- ((float) .5 * screenheight / 100), mTextPaint);

		}

		// Not-Outline lines
		// top metric
		canvas.drawLine(stopXmet + startX, startY, stopXmet + startX, startY
				- (1 * screenheight / 100), mLinePaint);
		// buttom customery line
		canvas.drawLine(stopXcus + startX, startY, stopXcus + startX, startY
				+ (1 * screenheight / 100), mLinePaint);
		// left line
		canvas.drawLine(startX, startY + (1 * screenheight / 100), startX,
				startY - (1 * screenheight / 100), mLinePaint);
		// main line
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
	}

	public void dogetscalefrompointsstuff() {
		System.out.println(FloorPlanActivity.GETSCALESTAGE);
		if (FloorPlanActivity.GETSCALESTAGE == FloorPlanActivity.STAGE_GETFIRSTPOINT) {

			firstscalepointx = absolutex;
			firstscalepointy = absolutey;

			FloorPlanActivity.floorplantitle
					.setText("Please select second point");
			FloorPlanActivity.GETSCALESTAGE = FloorPlanActivity.STAGE_GETSECONDPOINT;
		} else if (FloorPlanActivity.GETSCALESTAGE == FloorPlanActivity.STAGE_GETSECONDPOINT) {
			secondscalepointx = absolutex;
			secondscalepointy = absolutey;
			getscalefrompointspixeldistance = (float) Math.sqrt(Math.pow(
					(secondscalepointx - firstscalepointx), 2)
					+ Math.pow((secondscalepointy - firstscalepointy), 2));

			FloorPlanActivity.GETSCALESTAGE = FloorPlanActivity.STAGE_ENTERPHYSICALDISTANCE;
			FloorPlanActivity.ACTION = FloorPlanActivity.ACTION_DONOTHING;
			FloorPlanActivity.toptoolbar.setVisibility(View.VISIBLE);
			FloorPlanActivity.righttoolbar.setVisibility(View.VISIBLE);

			FloorPlanActivity.floorplantitle.setText(Tabs1.floorplanname);

			FloorPlanActivity.GETSCALESTAGE = FloorPlanActivity.STAGE_NOTGETTINGSCALE;

			getphsyicaldistancedialog();
		}

	}

	public static void getphsyicaldistancedialog() {

		AlertDialog.Builder getstring = new AlertDialog.Builder(ctx);
		final TableLayout layout = new TableLayout(ctx);
		layout.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		final TableRow tr = new TableRow(ctx);
		final Spinner unit = new Spinner(ctx);
		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		unit.setLayoutParams(lp);
		List<String> unitlist = new ArrayList<String>();
		unitlist.add("m");
		unitlist.add("ft");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
				R.layout.spinnertextview, unitlist);
		adapter.setDropDownViewResource(R.layout.largespinnertextview);
		unit.setAdapter(adapter);
		unit.setSelection(0);
		final AutoCompleteTextView nameet = new AutoCompleteTextView(ctx);
		nameet.setLayoutParams(lp);
		nameet.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		tr.addView(nameet);
		tr.addView(unit);
		layout.addView(tr);
		getstring.setView(layout);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		getstring.setTitle("Please enter the distance and unit");
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String string = nameet.getText().toString();
						FloorPlanActivity.metersperpixel = (float) (u
								.ds(string) / getscalefrompointspixeldistance);
						FloorPlanActivity.view.invalidate();
						dialog.dismiss();
						FloorPlanActivity.writeonedbitem(itemselectednumber);
					}

				});
		getstring.create().show();

	}

	public static Integer[] getorderedtemplist() {
		int countedtempsfromfloorplan = 0;

		for (int t = 0; t < i; t++) {
			if (ITEMtype[t] == TYPE_TEMPSENSOR) {
				countedtempsfromfloorplan++;
			}
		}

		

		// makelistarranging t by zone name
		// get initial list
		LinkedHashMap<Integer, Double> listoftemps = new LinkedHashMap<Integer, Double>();
		for (int t = 0; t < i; t++) {
			if (ITEMtype[t] == TYPE_TEMPSENSOR) {
				listoftemps.put(
						t,
						Double.parseDouble(ITEMmasterelcnumber[t] + "."
								+ ITEMdisplaynumber[t]));
			}
		}
		// sort that list in order by double 1.1,1.2,1.3....
		listoftemps = u.sortByComparator(listoftemps, true);

		int m = 0;
		Integer[] spaceholder = new Integer[listoftemps.size()];
		for (LinkedHashMap.Entry<Integer, Double> entry : listoftemps
				.entrySet()) {
			spaceholder[m] = entry.getKey();
			m++;
		}
		return spaceholder;
	}

	public void setsomeinitialnewitemvalues() {
		ITEMfloorplannumber[i] = fp;
		int itemsonthisfpcount = 0;
		for (int h = 0; h < i; h++) {
			if (ITEMfloorplannumber[h] == fp) {
				itemsonthisfpcount++;
			}
		}
		double[] allsizes = new double[itemsonthisfpcount];
		double[] allfontsizes = new double[itemsonthisfpcount];
		int iteration = 0;
		for (int h = 0; h < i; h++) {
			if (ITEMfloorplannumber[h] == fp) {
				allsizes[iteration] = (ITEMsizepercent[h]);
				allfontsizes[iteration] = (ITEMfontsize[h]);
				iteration++;
			}
		}
		if ((int) u.getMode(allsizes) >= 1) {
			ITEMsizepercent[i] = (int) u.getMode(allsizes);
			ITEMfontsize[i] = (int) u.getMode(allfontsizes);
		} else {
			ITEMsizepercent[i] = 100;
			ITEMfontsize[i] = defaulttextsize;
		}
	}

//	public void deletefromtabsandrelink(int itemnumber, int type) {
//		if (type == TYPE_BMS || type == TYPE_ELC || type == TYPE_GATEWAY
//				|| type == TYPE_TEMPSENSOR || type == TYPE_DISTRIBUTIONBOARD
//				|| type == TYPE_SAMARRAY || type == TYPE_DATAHUB) {
//			
//			if(type == TYPE_BMS || type == TYPE_DISTRIBUTIONBOARD|| type == TYPE_DATAHUB){
//				Tabs1.removeassetbutton[ITEMLINKtabitemnumber[itemnumber]]
//						.performClick();
//			}else{
//				Tabs1.removecomponentbutton[ITEMLINKtabitemnumber[itemnumber]]
//						.performClick();
//			}
//			
//			for (int h = 0; h < i; h++) {
//				if (ITEMLINKtabitemnumber[h] > ITEMLINKtabitemnumber[itemnumber]) {
//					ITEMLINKtabitemnumber[h]--;
//				}
//			}
//
//		}
//	}

	public static void drawbasictriangle(Canvas canvas, float x, float y,
			float radius, Paint paint) {

		float width = 2 * radius;
		Point p1 = new Point((int) x, (int) y);
		Point p2 = new Point((int) (p1.x + width), p1.y);
		Point p3 = new Point((int) (p1.x + (width / 2)), (int) (p1.y - width));

		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);

		canvas.drawPath(path, paint);

		// canvas.drawLine(point1_draw.x,point1_draw.y,point2_draw.x,point2_draw.y,
		// paint);

	}

	public void setsamdialoglayoutparams() {
		int loadnamecolumn = 0;
		int phasectcountcolumn = 1;
		int breakersizecolumn = 2;
		int cttypecolumn = 3;
		int loadtypecolumn = 4;
		int loadscolumn = 5;
		int removesamcolumn = 6;
		int panellocationcoloumn = 7;
		int commentscoloumn = 8;

		float fullwidth = 2f;
		float halfwidth = 1f;

		lp = new LayoutParams[maxvalues];
		lp[loadnamecolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				fullwidth);
		lp[phasectcountcolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[breakersizecolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[cttypecolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[loadtypecolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[loadscolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				fullwidth);
		lp[removesamcolumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[panellocationcoloumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
		lp[commentscoloumn] = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				halfwidth);
	}
	public static void drawtrianglewithtext(Canvas canvas, int itemnumber,
			String text, int FINALBITMAPSCALE, int color) {
		float totalwidth = u.getwidthofpainttext(p, text);

		p.setColor(Color.BLACK);
		float innercircleradius = p.getTextSize() / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		float textlocationx = (ITEMx[itemnumber] + outercircleradius)
				+ ITEMtextoffsetx[itemnumber];
		float textlocationy = ITEMy[itemnumber] - outercircleradius
				+ ITEMtextoffsety[itemnumber];
		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}
		

		drawtriangle(canvas, itemnumber, color);
		textpadding = p.getTextSize() / paddingvariable;
		p.setColor(Color.GRAY);
		itemtextrect[itemnumber]=new Rect(
				(int) (textlocationx - textpadding),
				(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
				(int) (textlocationx + totalwidth + textpadding),
				(int) (textlocationy + textpadding));
		canvas.drawRoundRect(
				new RectF(itemtextrect[itemnumber]),
				textpadding, textpadding, p);
		p.setColor(color);
		
		canvas.drawText(text, textlocationx, textlocationy, p);

	}
	public static void drawtriangle(Canvas canvas, int itemnum, int color) {
		p.setColor(Color.BLACK);
		float innercircleradius = p.getTextSize() / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		drawbasictriangle(canvas, ITEMx[itemnum] - (outercircleradius / 2),
				ITEMy[itemnum] + (outercircleradius / 2), outercircleradius, p);
		// canvas.drawRect(x-outercircleradius, y-outercircleradius,
		// x+outercircleradius, y+outercircleradius, p);
		// canvas.drawCircle(x,y,outercircleradius, p);

		p.setColor(color);
		drawbasictriangle(canvas, ITEMx[itemnum] - (innercircleradius / 2),
				ITEMy[itemnum] + (innercircleradius / 2), innercircleradius, p);
		// canvas.drawRect(x-innercircleradius, y-innercircleradius,
		// x+innercircleradius, y+innercircleradius, p);
		// canvas.drawCircle(x,y, innercircleradius, p);

		textpadding = p.getTextSize() / paddingvariable;
		
		
	};

	public static void drawminisam(Canvas canvas, int itemnum, String text,
			int FINALBITMAPSCALE, float x, float y) {

		Bitmap bm = iconbitmap[TYPE_MINISAM];

		if (ITEMsizepercent[itemnum] != 100) {
			try {

				float bitheight = (float) bm.getHeight()
						* (float) ITEMsizepercent[itemnum] / (float) 100;
				float bitwidth = (float) bm.getWidth()
						* (float) ITEMsizepercent[itemnum] / (float) 100;

				if (bitheight < 1) {
					bitheight = 1;
				}
				if (bitwidth < 1) {
					bitwidth = 1;
				}

				Log.d("icon bitheight, bitwidth", bitheight + "  " + bitwidth);
				try {
					bm = Bitmap.createScaledBitmap(bm, (int) bitwidth,
							(int) bitheight, true);
				} catch (Throwable e) {

				}
			} catch (Throwable e) {

			}
		}
		if (FINALBITMAPSCALE != 1) {
			x = x / FINALBITMAPSCALE;
			y = y / FINALBITMAPSCALE;
			bm = Bitmap.createScaledBitmap(bm,
					bm.getWidth() / FINALBITMAPSCALE, bm.getHeight()
							/ FINALBITMAPSCALE, true);
		}

		float positionx = ITEMx[itemnum] - ITEMbitmap[itemnum].getWidth() / 2;
		float positiony = ITEMy[itemnum] - ITEMbitmap[itemnum].getHeight() / 2
				+ (float) 0.75 * ITEMbitmap[itemnum].getHeight();
		canvas.drawBitmap(bm, positionx / FINALBITMAPSCALE, positiony
				/ FINALBITMAPSCALE, p);
		// bm.recycle();
		text = " x " + u.s(ITEMsamscount[itemnum]);
		float totalwidth = u.getwidthofpainttext(p, text);

		float textlocationx = (positionx + bm.getWidth())
				+ ITEMtextoffsetx[itemnum];
		float textlocationy = (positiony + bm.getHeight())
				+ ITEMtextoffsety[itemnum];

		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}

		p.setColor(presentablegreen);
		textpadding = p.getTextSize() / paddingvariable;
		canvas.drawRoundRect(
				new RectF(
						new Rect(
								(int) (textlocationx - textpadding),
								(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
								(int) (textlocationx + totalwidth + textpadding),
								(int) (textlocationy + textpadding))),
				textpadding, textpadding, p);
		p.setColor(Color.WHITE);
		canvas.drawText(text, textlocationx, textlocationy, p);

	}

	public static void drawcheckorx(Canvas canvas, int itemnumber,
			int FINALBITMAPSCALE, float x, float y) {
		Bitmap bm;
		if (ITEMdisplaynumber[itemnumber] == TYPE_TINYCHECK) {
			bm = iconbitmap[TYPE_TINYCHECK];
		} else {
			bm = iconbitmap[TYPE_TINYX];
		}

		if (ITEMsizepercent[itemnumber] != 100) {
			try {

				float bitheight = (float) bm.getHeight()
						* (float) ITEMsizepercent[itemnumber] / (float) 100;
				float bitwidth = (float) bm.getWidth()
						* (float) ITEMsizepercent[itemnumber] / (float) 100;

				if (bitheight < 1) {
					bitheight = 1;
				}
				if (bitwidth < 1) {
					bitwidth = 1;
				}

				Log.d("icon bitheight, bitwidth", bitheight + "  " + bitwidth);
				try {
					bm = Bitmap.createScaledBitmap(bm, (int) bitwidth,
							(int) bitheight, true);
				} catch (Throwable e) {

				}
			} catch (Throwable e) {

			}
		}
		if (FINALBITMAPSCALE != 1) {
			x = x / FINALBITMAPSCALE;
			y = y / FINALBITMAPSCALE;
			bm = Bitmap.createScaledBitmap(bm,
					bm.getWidth() / FINALBITMAPSCALE, bm.getHeight()
							/ FINALBITMAPSCALE, true);
		}

		float positionx = ITEMx[itemnumber] + ITEMbitmap[itemnumber].getWidth()
				/ 6;

		float positiony = ITEMy[itemnumber]
				+ ITEMbitmap[itemnumber].getHeight() / 6;

		canvas.drawBitmap(bm, positionx / FINALBITMAPSCALE, positiony
				/ FINALBITMAPSCALE, p);
		// bm.recycle();

	}

	public static void drawcircle(Canvas canvas, int itemnumber, int color) {

		p.setColor(Color.BLACK);
		float innercircleradius = p.getTextSize() / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		canvas.drawCircle(ITEMx[itemnumber], ITEMy[itemnumber],
				outercircleradius, p);

		p.setColor(color);
		canvas.drawCircle(ITEMx[itemnumber], ITEMy[itemnumber],
				innercircleradius, p);
		textpadding = p.getTextSize() / paddingvariable;

	}

	public static void ScaleTheIconAsPercentageOfScreen(int itemnumber) {

		float percentx = ((ITEMx[itemnumber] - rect.left) / rect.width());
		float percenty = ((ITEMy[itemnumber] - rect.top) / rect.height());
		if (percentx < 0)
			percentx = 0;
		if (percentx > 1)
			percentx = 1;
		if (percenty < 0)
			percenty = 0;
		if (percenty > 1)
			percenty = 1;

		SCALEDITEMx[itemnumber] = percentx * scrnwidth;
		SCALEDITEMy[itemnumber] = percenty * scrnheight;
	}

	public static void makeBitmapRectangleIntoButton(int itemselectednumber) {
		Bitmap bitmap=ITEMbitmap[itemselectednumber];
		float buttonleft = (float) ITEMx[itemselectednumber]
				- ((float) bitmap.getWidth() / (float) 2);
		float buttontop = (float) ITEMy[itemselectednumber]
				- ((float) bitmap.getHeight() / (float) 2);
		float buttonright = (float) ITEMx[itemselectednumber]
				+ ((float) bitmap.getWidth() / (float) 2);
		float buttonbottom = (float) ITEMy[itemselectednumber]
				+ ((float) bitmap.getHeight() / (float) 2);

		ITEMrect[itemselectednumber] = new Rect((int) (buttonleft), (int) (buttontop),
				(int) (buttonright), (int) (buttonbottom));
	}

	public static void makeDrawnTextIntoButton(int FINALBITMAPSCALE) {

		String text;
		float[] widths;
		try {
			text = ITEMstring[i];
			widths = new float[text.length()];
		} catch (NullPointerException e) {

			// this allows for immediate duplicate text
			ITEMstring[i] = ITEMstring[i - 1];
			ITEMfontsize[i] = ITEMfontsize[i - 1];
			ITEMfontcolor[i] = ITEMfontcolor[i - 1];

			itemname = ITEMstring[i];
			fontsize = ITEMfontsize[i];
			color = ITEMfontcolor[i];

			text = ITEMstring[i];
			widths = new float[text.length()];

		}
		float totalwidth = 0;
		p.getTextWidths(text, widths);

		for (int u = 0; u < widths.length; u++) {
			totalwidth = totalwidth + widths[u];
		}

		float textlocationx = ITEMx[i];
		float textlocationy = ITEMy[i];

		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}
		p.setColor(presentableblue);
		textpadding = p.getTextSize() / paddingvariable;

		float buttonleft = (float) (textlocationx - textpadding);
		float buttontop = (float) (textlocationy - p.getTextSize() + (textpadding / 2));
		float buttonright = (float) (textlocationx + totalwidth + textpadding);
		float buttonbottom = (float) (textlocationy + textpadding);

		ITEMrect[i] = new Rect((int) (buttonleft), (int) (buttontop),
				(int) (buttonright), (int) (buttonbottom));

	}

	public static void makeDrawnNGBiconIntoButton(int i) {
		u.log("makeDrawnNGBiconIntoButton(int "+i+")");
		u.log("text size="+p.getTextSize());
		
		float innercircleradius =(ITEMfontsize[i]/ itemscalefactor) / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		float buttonleft = (float) (ITEMx[i] - outercircleradius)
				- extraspaceforbuttons;
		float buttontop = (float) (ITEMy[i] - outercircleradius)
				- extraspaceforbuttons;
		float buttonright = (float) (ITEMx[i] + outercircleradius)
				+ extraspaceforbuttons;
		float buttonbottom = (float) (ITEMy[i] + outercircleradius)
				+ extraspaceforbuttons;
	
		ITEMrect[i] = new Rect((int) (buttonleft), (int) (buttontop),
				(int) (buttonright), (int) (buttonbottom));
		u.log((buttonleft)+" "+(buttontop)+" "+(buttonright)+" "+(buttonbottom));
	}

	public static void drawcirclewithtext(Canvas canvas, int itemnumber,
			String text, int FINALBITMAPSCALE, int color) {
		float totalwidth = u.getwidthofpainttext(p, text);

		p.setColor(Color.BLACK);
		float innercircleradius = p.getTextSize() / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		float textlocationx = (ITEMx[itemnumber] + outercircleradius)
				+ ITEMtextoffsetx[itemnumber];
		float textlocationy = ITEMy[itemnumber] - outercircleradius
				+ ITEMtextoffsety[itemnumber];
		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}
		canvas.drawCircle(ITEMx[itemnumber], ITEMy[itemnumber],
				outercircleradius, p);

		p.setColor(color);
		canvas.drawCircle(ITEMx[itemnumber], ITEMy[itemnumber],
				innercircleradius, p);
		textpadding = p.getTextSize() / paddingvariable;
		p.setColor(Color.GRAY);
		itemtextrect[itemnumber]=new Rect(
				(int) (textlocationx - textpadding),
				(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
				(int) (textlocationx + totalwidth + textpadding),
				(int) (textlocationy + textpadding));
		canvas.drawRoundRect(
				new RectF(itemtextrect[itemnumber]),
				textpadding, textpadding, p);
		p.setColor(color);

		canvas.drawText(text, textlocationx, textlocationy, p);

	}

	public static void drawtextoverbitmapicon(Canvas canvas, String text,
			int itemnumber, int FINALBITMAPSCALE, int color) {
		// if (!(text == "") && !(text == "null") && !(text == null)) {
		float totalwidth = u.getwidthofpainttext(p, text);
		float textlocationx = ITEMx[itemnumber] + ITEMtextoffsetx[itemnumber];
		float textlocationy = ITEMy[itemnumber] + ITEMtextoffsety[itemnumber];
		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}

		p.setColor(color);
		textpadding = p.getTextSize() / paddingvariable;
		itemtextrect[itemnumber]=new Rect(
				(int) (textlocationx - textpadding),
				(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
				(int) (textlocationx + totalwidth + textpadding),
				(int) (textlocationy + textpadding));
		canvas.drawRoundRect(
				new RectF(
						itemtextrect[itemnumber]),
				textpadding, textpadding, p);
		p.setColor(Color.WHITE);

		canvas.drawText(text, textlocationx, textlocationy, p);
	}

	public static void drawcentercircletextoverbitmapicon(Canvas canvas,
			String text, int itemnumber, int FINALBITMAPSCALE) {
		float totalwidth = u.getwidthofpainttext(p, text);

		float textlocationx = (ITEMx[itemnumber] - totalwidth / 2)
				+ ITEMtextoffsetx[itemnumber];
		float textlocationy = ITEMy[itemnumber] + p.getTextSize() / 2
				- textpadding + ITEMtextoffsety[itemnumber];

		if (FINALBITMAPSCALE != 1) {
			textlocationx = textlocationx / FINALBITMAPSCALE;
			textlocationy = textlocationy / FINALBITMAPSCALE;
		}

		p.setColor(presentablered);
		textpadding = p.getTextSize() / paddingvariable;
		canvas.drawCircle(textlocationx + totalwidth / 2,
				textlocationy - (p.getTextSize() / 2) + textpadding,
				p.getTextSize() / 2, p);
		// canvas.drawRect(textlocationx-textpadding,textlocationy-p.getTextSize()+(textpadding/2),textlocationx+totalwidth+textpadding,textlocationy+textpadding,p);

		p.setColor(Color.WHITE);
		canvas.drawText(text, textlocationx, textlocationy, p);

	}

	public static void drawlhiicons(int itemnumber, Canvas canvas,
			int FINALBITMAPSCALE, float x, float y) {

		Bitmap bm = ITEMbitmap[itemnumber];
		if (FINALBITMAPSCALE != 1) {
			x = x / FINALBITMAPSCALE;
			y = y / FINALBITMAPSCALE;
			bm = Bitmap.createScaledBitmap(bm,
					bm.getWidth() / FINALBITMAPSCALE, bm.getHeight()
							/ FINALBITMAPSCALE, true);
		}
		canvas.drawBitmap(bm, x, y, p);
		int type = ITEMtype[itemnumber];
		String text = "";

		switch (type) {
		case TYPE_ELC:
			text = u.s(ELCdisplaynumber[itemnumber]);
			u.log("itemnumber"+itemnumber);
			
			try{
				ITEMsamscount[itemnumber]=getsamcountfromdb(itemnumber);
			}catch(Throwable e){
				u.log("ITEMsamscount[itemnumber]"+ITEMsamscount[itemnumber]);
			}
			drawcentercircletextoverbitmapicon(canvas, text, itemnumber,
					FINALBITMAPSCALE);
			
			if (ITEMsamscount[itemnumber] > 0) {
				u.log("ITEMsamscount[itemnumber]"+ITEMsamscount[itemnumber]);
				drawminisam(canvas, itemnumber, text, FINALBITMAPSCALE, x, y);
			}
			break;
		case TYPE_ETHERNETPORT:
			drawcheckorx(canvas, itemnumber, FINALBITMAPSCALE, x, y);
			break;
		case TYPE_TEMPSENSOR:
			text = u.s(ITEMmasterelcnumber[itemnumber]) + "."
					+ u.s(ITEMdisplaynumber[itemnumber]);
			drawtextoverbitmapicon(canvas, text, itemnumber, FINALBITMAPSCALE,
					presentableblue);
			break;
		case TYPE_BMS:
			text = ITEMstring[itemnumber];
			drawtextoverbitmapicon(canvas, text, itemnumber, FINALBITMAPSCALE,
					presentablepurple);
			break;
		case TYPE_DISTRIBUTIONBOARD:
			text = ITEMstring[itemnumber];
			drawtextoverbitmapicon(canvas, text, itemnumber, FINALBITMAPSCALE,
					presentableorange);
			break;
		case TYPE_SAMARRAY:
			text = ITEMstring[itemnumber].split(",")[0];
			drawtextoverbitmapicon(canvas, text, itemnumber, FINALBITMAPSCALE,
					presentablegreen);
			break;
		case TYPE_DATAHUB:
			// no text
			break;
		case TYPE_GATEWAY:
			// no text
			break;

		}

	}

	public static void drawNGBicons(int itemnumber, Canvas canvas,
			int FINALBITMAPSCALE, float x, float y) {
		int type = ITEMtype[itemnumber];
		String text = getGenericDisplayText(itemnumber);
		switch (type) {
		case TYPE_BMS:
			drawcirclewithtext(canvas, itemnumber, text, FINALBITMAPSCALE,
					Color.MAGENTA);
			break;
		case TYPE_TEMPSENSOR:
			drawcirclewithtext(canvas, itemnumber, text, FINALBITMAPSCALE,
					Color.RED);
			break;
		case TYPE_SAMARRAY:
			drawcirclewithtext(canvas, itemnumber, text, FINALBITMAPSCALE,
					Color.GREEN);
			break;
		case TYPE_ELC:
			try{
				ITEMsamscount[itemnumber]=getsamcountfromdb(itemnumber);
			}catch(Throwable e){
				
			}		
			drawcirclewithtext(canvas, itemnumber, text, FINALBITMAPSCALE,
					ORANGE);
			break;
		case TYPE_ETHERNETPORT:
			drawtrianglewithtext(canvas, itemnumber, getGenericDisplayText(itemnumber),FINALBITMAPSCALE, Color.BLUE);
			break;
		case TYPE_DISTRIBUTIONBOARD:
			drawsquarewithtext(canvas, itemnumber, text, FINALBITMAPSCALE,
					Color.GREEN);
			break;
		case TYPE_GATEWAY:
			drawtrianglewithtext(canvas, itemnumber, getGenericDisplayText(itemnumber),FINALBITMAPSCALE, Color.RED);
			break;
		case TYPE_DATAHUB:
			drawcirclewithtext(canvas, itemnumber, getGenericDisplayText(itemnumber),FINALBITMAPSCALE,Color.BLUE);
			break;
		case TYPE_LEGEND4:
			ITEMbitmap[itemnumber] = NGBlegend;
			Bitmap bm = NGBlegend;
			if (FINALBITMAPSCALE != 1) {
				x = x / FINALBITMAPSCALE;
				y = y / FINALBITMAPSCALE;
				bm = Bitmap.createScaledBitmap(bm, bm.getWidth()
						/ FINALBITMAPSCALE, bm.getHeight() / FINALBITMAPSCALE,
						true);
			}
			canvas.drawBitmap(bm, x, y, p);
			break;

		}

	}

	public static String getGenericDisplayText(int itemnumber) {
		int type = ITEMtype[itemnumber];
		String text = "";
		switch (type) {
		case TYPE_BMS:
			text = ITEMstring[itemnumber].split(",")[0];
			break;
		case TYPE_TEMPSENSOR:
			text = "Zone " + u.s(ITEMmasterelcnumber[itemnumber]) + "."
					+ u.s(ITEMdisplaynumber[itemnumber]);
			break;
		case TYPE_SAMARRAY:
			text = "SAM Array " + ITEMstring[itemnumber].split(",")[0];
			break;
		case TYPE_ELC:
			text = "ELC " + u.s(ELCdisplaynumber[itemnumber]);
			break;
		case TYPE_ETHERNETPORT:
		
			text = "Ethernet Port "+ ITEMstring[itemnumber];
			break;
		case TYPE_DISTRIBUTIONBOARD:
			text = "DB " + ITEMstring[itemnumber];
			break;
		case TYPE_GATEWAY:
			text = "Gateway Server"+ ITEMstring[itemnumber];
			break;
		case TYPE_DATAHUB:
			text = "Data Hub "+ ITEMstring[itemnumber];
			break;
		case TYPE_LEGEND4:
			break;

		}
		return text;

	}

	public static void drawsquarewithtext(Canvas canvas, int itemnumber,
			String text, int FINALBITMAPSCALE, int color) {
		float totalwidth = u.getwidthofpainttext(p, text);

		p.setColor(Color.BLACK);

		float innercircleradius = p.getTextSize() / 4;
		float outercircleradius = innercircleradius + innercircleradius
				/ shapepaddingvariable;
		float textlocationx = (ITEMx[itemnumber] + outercircleradius)
				+ ITEMtextoffsetx[itemnumber];
		float textlocationy = (ITEMy[itemnumber] - outercircleradius)
				+ ITEMtextoffsety[itemnumber];

		canvas.drawRect(ITEMx[itemnumber] - outercircleradius,
				ITEMy[itemnumber] - outercircleradius, ITEMx[itemnumber]
						+ outercircleradius, ITEMy[itemnumber]
						+ outercircleradius, p);
		// canvas.drawCircle(x,y,outercircleradius, p);

		p.setColor(color);
		canvas.drawRect(ITEMx[itemnumber] - innercircleradius,
				ITEMy[itemnumber] - innercircleradius, ITEMx[itemnumber]
						+ innercircleradius, ITEMy[itemnumber]
						+ innercircleradius, p);
		// canvas.drawCircle(x,y, innercircleradius, p);
		textpadding = p.getTextSize() / paddingvariable;
		p.setColor(Color.GRAY);
		itemtextrect[itemnumber]=new Rect(
				(int) (textlocationx - textpadding),
				(int) (textlocationy - p.getTextSize() + (textpadding / 2)),
				(int) (textlocationx + totalwidth + textpadding),
				(int) (textlocationy + textpadding));
		canvas.drawRoundRect(
				new RectF(itemtextrect[itemnumber]),
				textpadding, textpadding, p);
		p.setColor(Color.GREEN);

		canvas.drawText(text, textlocationx, textlocationy, p);
	}

	public static void drawSAMreferencetable(Canvas canvas, Paint p) {
		float samreferencetabletextsize = 30f;
		p.setTextSize(samreferencetabletextsize);

		List<String> samreference = new ArrayList<String>();
		List<String> numberofsams = new ArrayList<String>();
		List<String> elcreference = new ArrayList<String>();
		List<String> circuitreference = new ArrayList<String>();
		List<String> datarequired = new ArrayList<String>();
		int samarraycount = 0;
		int totalsamcount = 0;
		float greatestwidth = 0;
		float greatestheight = p.getTextSize();
		int columncount = 5;
		for (int o = 0; o < i; o++) {
			if (ITEMtype[o] == TYPE_SAMARRAY) {
				try {
					samreference.add(ITEMstring[o].split(",")[0]);
					float textwidth = u.getwidthofpainttext(p,
							ITEMstring[o].split(",")[0]);
					if (textwidth > greatestwidth) {
						greatestwidth = textwidth;
					}
				} catch (Throwable e) {
				}
				try {
					numberofsams.add(ITEMstring[o].split(",")[1]);
					float textwidth = u.getwidthofpainttext(p,
							ITEMstring[o].split(",")[1]);
					if (textwidth > greatestwidth) {
						greatestwidth = textwidth;
					}
					totalsamcount = totalsamcount
							+ u.i(ITEMstring[o].split(",")[1]);
				} catch (Throwable e) {

				}
				try {
					elcreference.add(u.s(ITEMmasterelcnumber[o]));
					float textwidth = u.getwidthofpainttext(p,
							u.s(ITEMmasterelcnumber[o]));
					if (textwidth > greatestwidth) {
						greatestwidth = textwidth;
					}
				} catch (Throwable e) {
				}
				try {
					circuitreference.add(ITEMstring[o].split(",")[2]);
					float textwidth = u.getwidthofpainttext(p,
							ITEMstring[o].split(",")[2]);
					if (textwidth > greatestwidth) {
						greatestwidth = textwidth;
					}
				} catch (Throwable e) {
				}
			try{
				String text = ITEMstring[o].split(",")[3];
				datarequired.add(text);

				float textwidth = u.getwidthofpainttext(p, text);
				if (textwidth > greatestwidth) {
					greatestwidth = textwidth;
				}
			}catch(Throwable e){
				datarequired.add("");
			}
				samarraycount++;
			}

		}
		String[] titles = { "Sam Reference", "Number of Sams", "ELC Reference",
				"Circuit Reference", "DATA" };
		for (String text : titles) {
			float textwidth = u.getwidthofpainttext(p, text);
			if (textwidth > greatestwidth) {
				greatestwidth = textwidth;
			}
		}

		int width = (int) bitwidth[fp];
		int height = (int) bitheight[fp];
		Rect tablerect = new Rect(0, (int) (height - samarraycount
				* greatestheight)
				- 20 * paddingvariable, (int) (columncount * greatestwidth)
				+ 20 * paddingvariable, height);

		if (samarraycount > 0) {
			int ArrayCount = samarraycount;
			int titleline = 1;
			int totalline = 1;
			int Columns = 5;

			int gridborderwidth = 5;

			int rows = ArrayCount + titleline + totalline;

			p.setColor(Color.BLUE);

			canvas.drawRect(tablerect, p);

			Rect[][] grid = new Rect[Columns][rows];
			for (int x = 0; x < Columns; x++) {
				for (int y = 0; y < rows; y++) {
					int rectwidth = tablerect.width() / Columns;
					int rectheight = tablerect.height() / rows;
					int left = rectwidth * x + gridborderwidth;
					int top = ((height - tablerect.height()) + rectheight * y)
							+ gridborderwidth;
					int right = left + rectwidth - gridborderwidth;
					int bottom = top + rectheight - gridborderwidth;
					System.out.println("x&y" + x + " " + y);
					System.out.println(left + " " + top + " " + right + " "
							+ bottom);
					p.setColor(Color.WHITE);
					try {
						grid[x][y] = new Rect(left, top, right, bottom);
						canvas.drawRect(grid[x][y], p);

					} catch (Throwable e) {
						e.printStackTrace();
					}
					p.setColor(Color.BLACK);
					if (y == 0) {
						canvas.drawText(titles[x], left, bottom, p);
					} else if (y < rows - 1) {
						if (x == 0) {
							try {
								canvas.drawText(samreference.get(y - 1), left,
										bottom, p);
							} catch (Throwable e) {

							}
						}
						if (x == 1) {
							try {
								canvas.drawText(numberofsams.get(y - 1), left,
										bottom, p);
							} catch (Throwable e) {

							}
						}
						if (x == 2) {
							try {
								canvas.drawText(elcreference.get(y - 1), left,
										bottom, p);
							} catch (Throwable e) {

							}
						}
						if (x == 3) {
							try {
								canvas.drawText(circuitreference.get(y - 1),
										left, bottom, p);
							} catch (Throwable e) {

							}
						}
						if (x == 4) {
							try {
								canvas.drawText(datarequired.get(y - 1), left, bottom,
										p);
							} catch (Throwable e) {

							}
						}
					} else {
						if (x == 0) {
							canvas.drawText("Total", left, bottom, p);

						}
						if (x == 1) {
							canvas.drawText(u.s(totalsamcount), left, bottom, p);
						}
						if (x == 2) {
							canvas.drawText(u.s(ELCCOUNT), left, bottom, p);
						}

					}

				}
			}
		}

	}
	
	public static void manualtempsensornumbering(int itemno){
		ITEMmasterelcnumber[itemno]=CLOSESTUNFILLEDELCNUMBER;
		ITEMtempsensorcount[getitemnumofelc(CLOSESTUNFILLEDELCNUMBER)]++;
		int DISPLAYNUMBER=1;
		
		for(int l=1; l<=maxtempsperelc;l++){
		Boolean Unique=true;
		 for(int k=0;k<itemno;k++){
			if(ITEMmasterelcnumber[k]==CLOSESTUNFILLEDELCNUMBER){
			  System.out.println("Master ELC matched for item,"+k);
				if(ITEMtype[k]==TYPE_TEMPSENSOR){
				  if(ITEMdisplaynumber[k]==DISPLAYNUMBER){
					  System.out.println("Displaymateched for item,"+k);
					  Unique=false;
				   }
			  }
			}
		  }
		 if(Unique){
				break;
			}else{
				DISPLAYNUMBER++;
			}
		}
		ITEMdisplaynumber[itemno]=DISPLAYNUMBER;
		
	}
	public static int getdirectorynum(int tabnum){
		int foldernum = 0;
		switch(tabnum){
		case (Tabs1.ASSETSTAB):
			foldernum=Tabs1.ASSETSFOLDER;
			break;
		case (Tabs1.COMPONENTSTAB):
			foldernum=Tabs1.COMPONENTSFOLDER;
			break;
		case (Tabs1.SITETAB):
			foldernum=Tabs1.SITEINFOFOLDER;
			break;
		
		}
		return foldernum;
	}
	public static int gettabofitemselected(int itemselectednum){
			int tab=Tabs1.COMPONENTSTAB;
			switch(ITEMtype[itemselectednum]){
			case (TYPE_BMS):
				tab=Tabs1.ASSETSTAB;
				break;
			case (TYPE_ELC):
				tab=Tabs1.COMPONENTSTAB;
				break;
			case (TYPE_SAMARRAY):
				tab=Tabs1.COMPONENTSTAB;
				break;
			case (TYPE_DISTRIBUTIONBOARD):
				tab=Tabs1.ASSETSTAB;
				break;
			case (TYPE_DATAHUB):
				tab=Tabs1.ASSETSTAB;
				break;
			case (TYPE_ETHERNETPORT):
				tab=Tabs1.COMPONENTSTAB;
				break;
			case (TYPE_TEMPSENSOR):
				tab=Tabs1.COMPONENTSTAB;
				break;
			case (TYPE_GATEWAY):
				tab=Tabs1.COMPONENTSTAB;
				break;
		}
			return tab;
	}
	public static String getdbtable(int itemselectednum){
		String table = null;
		switch(ITEMtype[itemselectednum]){
		case (TYPE_BMS):
			table=Tabs1.db.TABLE_ASSETINFO;
			break;
		case (TYPE_ELC):
			table=Tabs1.db.TABLE_COMPONENTINFO;
			break;
		case (TYPE_SAMARRAY):
			table=Tabs1.db.TABLE_COMPONENTINFO;
			break;
		case (TYPE_DISTRIBUTIONBOARD):
			table=Tabs1.db.TABLE_ASSETINFO;
			break;
		case (TYPE_DATAHUB):
			table=Tabs1.db.TABLE_ASSETINFO;
			break;
		case (TYPE_ETHERNETPORT):
			table=Tabs1.db.TABLE_COMPONENTINFO;
			break;
		case (TYPE_TEMPSENSOR):
			table=Tabs1.db.TABLE_COMPONENTINFO;
			break;
		case (TYPE_GATEWAY):
			table=Tabs1.db.TABLE_COMPONENTINFO;
			break;
	}
		return table;
}

	public static void refreshclickablearea(int i, int FINALBITMAPSCALE){
		if (!(ITEMtype[i] == TYPE_ADDTEXT)) {
			if (!FloorPlanActivity.NGBICONS) {
				System.out.println("lhi buttons");
				makeBitmapRectangleIntoButton(i);
			} else {
				System.out.println("ngb buttons");
				if (ITEMtype[i] == TYPE_LEGEND4) {
					makeBitmapRectangleIntoButton(i);
				} else {
					makeDrawnNGBiconIntoButton(i);

				}
			}

		} else {
			makeDrawnTextIntoButton(FINALBITMAPSCALE);
		}
		

	}
	public static int getsamcountfromdb(int itemselectednumber){
		int elcnum = ELCdisplaynumber[itemselectednumber];
		Cursor samrows=Tabs1.db.getrowswithvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN],u.s(elcnum));
		int count=samrows.getCount();
		u.log("samrows.getCount() "+count);
		samrows.close();
		return count;
	}
	public static void deletevaluesforelcfromdb(int elcnum){
		
		Tabs1.db.deleteallrowswithvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN],u.s(elcnum));
		Tabs1.db.settingmcritemno(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ITEMNUMBERCOLUMN]);
		Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
	}

	public static void addallvaluesforelctodb(int elcnum){
			int maxrows=u.i(samcounttv[elcnum].getText().toString());
			int columninfloorplan;
			int columninmeteringlistdb;
//			try{
//			Cursor samrows=Tabs1.db.getrowswithvalue(Tabs1.db.TABLE_MCR_METERINGLIST,Tabs1.db.KEY_MCR_METERING_TITLES[Tabs1.ELCNUMBERCOLUMN],u.s(elcnum));
//			int[] availablerows= new int[samrows.getCount()];
//			samrows.moveToFirst();
//			for(int a=0;a<availablerows.length;a++){
//				availablerows[a]=u.i(samrows.getString(Tabs1.ITEMNUMBERCOLUMN));
//				samrows.moveToNext();}
//			}catch(Throwable e){
//				
//			}
			
			
			int rowstart=Tabs1.db.countrows(Tabs1.db.TABLE_MCR_METERINGLIST);
			u.log("rowstart="+rowstart);
			for(int y=0; y<maxrows; y++){
				
				u.log("y="+y);
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.ELCNUMBERCOLUMN, u.s(elcnum),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.LOADNAMECOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_LOADNAME]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.PHCOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_PHASE]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.BREAKERSIZECOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_BREAKERSIZE]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.CTSIZEPHYISICALCOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_CTTYPE]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.LOADTYPECOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_LOADTYPE]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.LOADSONEACHBREAKERCOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_LOADS]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.PANELLOCATIONCOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_PANELLOCATION]),Tabs1.db.KEY_MCR_METERING_TITLES );
				Tabs1.db.addorupdatemulticolumn(Tabs1.db.TABLE_MCR_METERINGLIST, rowstart+y,Tabs1.COMMENTSCOLUMN, u.gettextfromview(samview[elcnum][y][SAMOPTION_COMMENTS]),Tabs1.db.KEY_MCR_METERING_TITLES );
				
			}
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
	}
	public int getmaxfloorplansize(){
		double heapfreesize=Debug.getNativeHeapFreeSize();
		double test=heapfreesize*1;
		double maxtotalspaceavailableforfloorplans=heapfreesize*.75;
		double numberoffloorplans=FloorPlanActivity.floorplancount;
		double maxfloorplansize=maxtotalspaceavailableforfloorplans/numberoffloorplans;
		return (int)maxfloorplansize;
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	public static Bitmap decodeSampledBitmapFromResource(String filepath,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filepath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filepath, options);
	}
	public void findtabdbnumber(int tab, String name){
		int tabdbnumber;
		switch(tab){
		case Tabs1.SITETAB:
			
			break;
		case Tabs1.COMPONENTSTAB:
			
			break;
		case Tabs1.ASSETSTAB:
			
			break;
		}
	}

	public static int tabitemnumber(int tab, int itemselectednumber) {
		String table = getdbtable(itemselectednumber);
		String keyname = null;
		int tabitemnumber = getobjectcountfromdb(tab)+1;
		u.log(getobjectcountfromdb(tab));
			if(getobjectcountfromdb(tab)>0){
			Cursor c=Tabs1.db.getrowswithvalue(table, "floorplanlink", u.s(itemselectednumber));
			c.moveToFirst();
			tabitemnumber=c.getInt(0);
			u.log("tabitemnumber inside cursor"+tabitemnumber);
			c.close();
			}
		u.log("tabitemnumber inside tabitemnumber"+tabitemnumber);
		return tabitemnumber;
	}
	public static int getobjectcountfromdb(int tab){
		int attributes = 0;
		int rows = 0;
		int itemcount;
		switch(tab){
		case Tabs1.SITETAB:
			rows=Tabs1.db.countrows(Tabs1.db.TABLE_SITEINFO);
			break;
		case Tabs1.COMPONENTSTAB:
			rows=Tabs1.db.countrows(Tabs1.db.TABLE_COMPONENTINFO);
			break;
		case Tabs1.ASSETSTAB:
			rows=Tabs1.db.countrows(Tabs1.db.TABLE_ASSETINFO);
			break;
		}
		
		
		return rows;
		
		

		
	}
	
	public static void deletefromtabsdb(int itemselectednumber,int type){
		u.log("itemselectednumber "+itemselectednumber);
		int tab=gettabofitemselected(itemselectednumber);
		String table=getdbtable(itemselectednumber);
		int tabitemnumber=tabitemnumber(tab, itemselectednumber);
		Tabs1.db.showfulldblog(table);
		for(int num=tabitemnumber-1; num<Tabs1.db.countrows(table)-1; num++){
			u.log("num"+num);
			u.log("trying to rename componentname" + u.s(num)+"to componentname" + u.s(num+1));
			try{
				
				if (tab==Tabs1.SITETAB) {
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitename"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitesizem"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizem" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitesizeft"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizeft" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitedescription"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitedescription" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitetype"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitetype" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"floorplanlink"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "floorplanlink" , (num+1)), Tabs1.db.KEY_SITE_ATTRIBUTES);
					
				}
				if (tab==Tabs1.COMPONENTSTAB) {
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentname"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componenttype"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componenttype" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentspec"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentspec" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentlocation"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentlocation" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentnotes"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"floorplanlink"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "floorplanlink" , (num+1)), Tabs1.db.KEY_COMPONENT_ATTRIBUTES);

				}
				if (tab==Tabs1.ASSETSTAB) {
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetname"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetquantity"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetquantity" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assettype"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assettype" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetmodel"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetmodel" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetpower"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetpower" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetlocation"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetlocation" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetserviceaream"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceaream" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetserviceareaft"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceareaft" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetnotes"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);
					Tabs1.db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, Tabs1.db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"floorplanlink"), Tabs1.db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "floorplanlink" , (num+1)), Tabs1.db.KEY_ASSET_ATTRIBUTES);

				}
				
				Tabs1.db.showfulldblog(table);
			}catch(Throwable e){
				e.printStackTrace();
				u.log("surpassed limit on db");
			}
		}
		
		Tabs1.db.showfulldblog(table);
		
		u.log(u.s(Tabs1.db.countrows(table)));
		
		Tabs1.db.deleteSingleRow(table, u.s(Tabs1.db.countrows(table)));
		Tabs1.db.showfulldblog(table);
		
		table=DatabaseHandler.TABLE_SITEINFO;
		String[] attributes=Tabs1.db.KEY_SITE_ATTRIBUTES;
		Tabs1.db.showfulldblog(table);
		for(int x=0; x<Tabs1.db.countrows(table); x++){
			int column=Tabs1.db.getcolumnnumberbytitle(table,"floorplanlink");
			String[] titles=attributes;
			
			try{
				int oldfloorplanlinknumber=u.i(Tabs1.db.getvaluemulticolumn(table, x, column, titles));
				u.log(oldfloorplanlinknumber);
				if(oldfloorplanlinknumber>itemselectednumber){
					String newfloorplanlinknumber=u.s(oldfloorplanlinknumber-1);
					Tabs1.db.addorupdatemulticolumn(table, x, column, newfloorplanlinknumber, titles);
				}
			}catch(Throwable e){
				System.out.println("itemnumberontab "+x+" doesn't have floorplanlink");
			}
		}
		
		table=DatabaseHandler.TABLE_COMPONENTINFO;
		attributes=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
		Tabs1.db.showfulldblog(table);
		for(int x=0; x<Tabs1.db.countrows(table); x++){
			int column=Tabs1.db.getcolumnnumberbytitle(table,"floorplanlink");
			String[] titles=attributes;
			
			try{
			int oldfloorplanlinknumber=u.i(Tabs1.db.getvaluemulticolumn(table, x, column, titles));
			u.log(oldfloorplanlinknumber);
			if(oldfloorplanlinknumber>itemselectednumber){
				String newfloorplanlinknumber=u.s(oldfloorplanlinknumber-1);
				Tabs1.db.addorupdatemulticolumn(table, x, column, newfloorplanlinknumber, titles);
			}
			}catch(Throwable e){
				System.out.println("itemnumberontab "+x+" doesn't have floorplanlink");
			}
		}
		Tabs1.db.showfulldblog(table);
		table=DatabaseHandler.TABLE_ASSETINFO;
		attributes=Tabs1.db.KEY_ASSET_ATTRIBUTES;
		Tabs1.db.showfulldblog(table);
		for(int x=0; x<Tabs1.db.countrows(table); x++){
			int column=Tabs1.db.getcolumnnumberbytitle(table,"floorplanlink");
			String[] titles=attributes;
			try{
			int oldfloorplanlinknumber=u.i(Tabs1.db.getvaluemulticolumn(table, x, column, titles));
			u.log(oldfloorplanlinknumber);
			if(oldfloorplanlinknumber>itemselectednumber){
				String newfloorplanlinknumber=u.s(oldfloorplanlinknumber-1);
				Tabs1.db.addorupdatemulticolumn(table, x, column, newfloorplanlinknumber, titles);
			}
			}catch(Throwable e){
				System.out.println("itemnumberontab "+x+" doesn't have floorplanlink");
			}
		}
		
		Tabs1.db.showfulldblog(table);
	}
	public static int getitemcount(int type){
		int count=0;
		for(int t=0; t<i; t++){
			if(ITEMtype[t]==type){
				count++;
			}
		}
		return count;
	}
	public static boolean duplicategenericnameexists(int itemselectednumber){
		u.log(itemselectednumber);
		u.log("running check for duplicate");
		boolean duplicateexists=false;
		u.log("Genericdisplaytext being checked for duplicate "+getGenericDisplayText(itemselectednumber));
		for(int x=0; x<i; x++){
			
			u.log("number x="+x+"with display "+getGenericDisplayText(x));
			if(!(x==itemselectednumber)){
				if(getGenericDisplayText(x).equals(getGenericDisplayText(itemselectednumber))){
				u.log("duplicate found");
				duplicateexists=true;
				break;
			}
			}
		}
		u.log("duplicate not found");
		return duplicateexists;
		
	}
	public static int totaltempcount(int itemno, int elcno){
		int count=0;
		for(int k=0;k<itemno-1;k++){
			if(ITEMmasterelcnumber[k]==elcno){
				if(ITEMtype[k]==TYPE_TEMPSENSOR){
					count++;
				}
			}
		}
		return count;
	}
}
