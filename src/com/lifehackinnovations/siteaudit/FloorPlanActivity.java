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
 * new
 */
package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.io.FilenameUtils;


import com.lifehackinnovations.siteaudit.Tabs1.LoadTask;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class FloorPlanActivity extends Activity {
	/** Called when the activity is first created. */
	static List<Integer>[] itemstoeditperrowontabs=(ArrayList<Integer>[]) new ArrayList[50];
	static List<Integer> rowstoeditontabs;
	static String dbtablename;
	String tempfoldername;
	int FLOORPLANGETPICFROMCAMERA=99;
	int PREFS=1;
	int EDITPICTUREACTIVITY=2;
	
	static Long PICTURESTARTTIME;
	static int TABFORGETPICTURE;
	static String FOLDERNAMEFORGETPICTURE;
	
	static String editpicturelocation;
	
	static int floorplannumber;
	Drawable outputfloorplandrawable;
	Bitmap outputfloorplanthumbnail;
	
	static int FINALBITMAPSCALE =1;
	
	static Boolean ICONSCALING = false;
	static Boolean ICONBOUNDING= false;
	static Boolean MULTILEVEL;
	
	static Boolean AUTORENUMBERTEMPS=true;
	
	static Boolean DRAWSAMREFERENCE=true;
	static Boolean RAPIDPLACEMENT=false;
	
	
	static Boolean NGBICONS=false;
	
	static int NOSCALESET=-1;
	static int STAGE_NOTGETTINGSCALE=-1;
	static int STAGE_GETFIRSTPOINT=0;
	static int STAGE_GETSECONDPOINT=1;
	static int STAGE_ENTERPHYSICALDISTANCE=2;
	static int GETSCALESTAGE=STAGE_NOTGETTINGSCALE;
	
	
	
	public static int GENERATEDDOCUMENTS = 0;
	public static int DOCUMENTS = 1;
	public static int COMPONENTS = 2;
	public static int ASSETS = 3;
	public static int SITEINFO = 4;
	public static int CONSUMPTIONFOLDER = 5;
	
	static int MODE;
	static int MODE_DONOTHING = -1;
	static int MODE_BMS = 0;
	static int MODE_ELC = 1;
	static int MODE_GATEWAY = 2;
	static int MODE_SAM = 3;
	static int MODE_TEMPSENSOR = 4;
	static int MODE_ETHERNETPORT = 5;
	static int MODE_ADDTEXT = 6;
	static int MODE_LEGEND = 7;
	static int MODE_METERSTOPIXEL=8;
	static int MODE_DISTRIBUTIONBOARD=9;
	static int MODE_SAMARRAY=10;
	static int MODE_DATAHUB=11;


	
	static int ACTION_DONOTHING=-1;
	static int ACTION=ACTION_DONOTHING;
	static int ACTION_FLOORPLANPREVIOUS = 0;
	static int ACTION_FLOORPLANNEXT = 1;
	static int ACTION_FULLSCREEN = 2;
	static int ACTION_UNFULLSCREEN = 3;
	static int ACTION_RESIZEICONS = 4;
	static int ACTION_PREFERENCES = 5;
	static int ACTION_RESIZEICON = 6;
	static int ACTION_SHOWLAYERS = 7;
	static int ACTION_GETSCALEFROMPOINTS = 8;
	
	public Context ctx;

	public static ImageView.OnLayoutChangeListener hourglasslistener;
	
	public static float metersperpixel=NOSCALESET;
	public static float topleftcornerlatitude=0;
	public static float topleftcornerlongitude=0;
	public static float bearing=0;
	
	private ImageView floorplanprevious, floorplannext, fullscreen, unfullscreen, showlayers, resizeicons,
			preferences,metersperpixelbutton;
	
	static Button hiddenaddpicturebutton;
	static Button hiddeneditpicturebutton;
	
	static SeekBar resizeiconsseekbar;
	static Button resizeiconscancel;

	static Button resizeiconsfinished;
	
	private ImageView BMS, ELC, Gateway, SAM, tempsensor, ethernetport, AddText, distributionboard, samarray, datahub,legend;
	static Bitmap Addtextbit;
	
	static View toptoolbar;

	static View righttoolbar;
	
	static ScrollView righttoolbarscrollview;

	static View righttoolbarbuttonparent;

	static RelativeLayout rl;

	static FloorPlanView view;

	static ImageView[] itemimageview = new ImageView[view.maxitems];
	static TextView floorplantitle;

	static String DefaultTextSize="25";
	
	View toolbar;
	static Boolean deletingitem=false;
	static int floorplancount;

	protected ProgressDialog progressDialog;
	private static Looper looper;
	
	public AlertDialog getscaledialog;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		try{
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
		}catch(Throwable e){
			
		}
		ctx=this;
		
		
		
		
		try{
		if(Tabs1.foldername.contains("_")){
			tempfoldername=Tabs1.foldername.substring(0,Tabs1.foldername.indexOf("_"));
		}else{
			tempfoldername=Tabs1.foldername;
		}
		
		Log.d("foldername",tempfoldername);
		
		dbtablename="floorplan";
		if(Tabs1.db.checktableindb(dbtablename)){
			//
		}else{
			try{
				Tabs1.db.createfloorplandb(dbtablename);
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		
		
		try{
			MULTILEVEL=ReadBoolean(this,"multilevel", false);
		}catch(Throwable e){
			System.out.println("couldn't read multilevel value from preferences on start");
			MULTILEVEL=false;
		}
		
		
		
		try{
			NGBICONS=ReadBoolean(this,"ngbicons", false);
		}catch(Throwable e){
			System.out.println("couldn't read autorenumber temp sensors value from preferences on start");
			NGBICONS=true;
		}
		
		try{
			DRAWSAMREFERENCE=ReadBoolean(this,"drawsamreferencetable",true);
		}catch(Throwable e){
			
		}
		try{
			RAPIDPLACEMENT=ReadBoolean(this,"rapidplacement",false);
		}catch(Throwable e){
			
		}
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		Intent intent=getIntent();
		
		FloorPlanActivity.floorplannumber=intent.getExtras().getInt("floorplannumber");
		try{
			FloorPlanActivity.floorplancount=Tabs1.FloorPlanCount;
		}catch(Throwable e){
			FloorPlanActivity.floorplancount=new File(Tabs1.inputfloorplandirectory).list().length;
		}
		MODE=MODE_DONOTHING;
		
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		if (tempfoldername.equals("Paul")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.paul_animation));

		} else if (tempfoldername.equals("Will")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.will_animation));

		} else if (tempfoldername.equals("Bill")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.bill_animation));
		}else {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.progress_dialog_icon_drawable_animation));

		}

		progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle("Loading");
		progressDialog.setMessage("Please Wait");
		progressDialog.setCancelable(false);
		
		

		rl = new RelativeLayout(this);
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		rl.setLayoutParams(rllp);

		toolbar = new View(this);
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		toolbar = inflater.inflate(R.layout.toolbar, null);

		toptoolbar = (View) toolbar.findViewById(R.id.toptoolbar);
		righttoolbar = (View) toolbar.findViewById(R.id.righttoolbar);
		righttoolbarbuttonparent= (View) toolbar.findViewById(R.id.righttoolbarbuttonparent);
		
		righttoolbarscrollview = (ScrollView) toolbar.findViewById(R.id.righttoolbarscrollview);
		
		floorplanprevious = (ImageView) toolbar.findViewById(R.id.floorplanprevious);
		floorplannext = (ImageView) toolbar.findViewById(R.id.floorplannext);
		fullscreen = (ImageView) toolbar.findViewById(R.id.fullscreen);
		unfullscreen = (ImageView) toolbar.findViewById(R.id.unfullscreen);
		showlayers = (ImageView) toolbar.findViewById(R.id.layers);
		resizeicons = (ImageView) toolbar.findViewById(R.id.resizeicons);
		metersperpixelbutton= (ImageView) toolbar.findViewById(R.id.metersperpixelbutton);
		
		
		resizeiconsseekbar = (SeekBar) toolbar.findViewById(R.id.resizeiconsseekbar);
		resizeiconsseekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				view.invalidate();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		
		
		resizeiconscancel = (Button) toolbar.findViewById(R.id.resizeiconscancel);
		resizeiconsfinished = (Button) toolbar.findViewById(R.id.resizeiconsfinished);
		
		preferences = (ImageView) toolbar.findViewById(R.id.preferences);

		floorplantitle = (TextView) toolbar.findViewById(R.id.floorplantitle);
		BMS = (ImageView) toolbar.findViewById(R.id.bms);
		ELC = (ImageView) toolbar.findViewById(R.id.elc);
		Gateway = (ImageView) toolbar.findViewById(R.id.gateway);
		SAM = (ImageView) toolbar.findViewById(R.id.sam);
		tempsensor = (ImageView) toolbar.findViewById(R.id.tempsensor);
		ethernetport = (ImageView) toolbar.findViewById(R.id.ethernetport);
		distributionboard = (ImageView) toolbar.findViewById(R.id.distributionboard);
		samarray = (ImageView) toolbar.findViewById(R.id.samarray);
		datahub = (ImageView) toolbar.findViewById(R.id.datahub);
		
		AddText = (ImageView) toolbar.findViewById(R.id.addtexttofloorplan);

	
		
		legend = (ImageView) toolbar
				.findViewById(R.id.legend);

		hiddenaddpicturebutton=new Button(this);
		hiddenaddpicturebutton.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {

				
				int tabforpicture=view.gettabofitemselected(view.itemselectednumber);
				String foldername=view.getGenericDisplayText(view.itemselectednumber);
				
				
			//	System.out.println("this is the name of the new file to be saved."+imageF.getAbsolutePath());
				//takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				//		Uri.fromFile(imageF));
				
				
				
				Intent takePictureIntent = new Intent(
						MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
				u.log("starting activity camera");

				
				PICTURESTARTTIME=System.currentTimeMillis();
				TABFORGETPICTURE=tabforpicture;
				FOLDERNAMEFORGETPICTURE= foldername;
				
				
				startActivityForResult(takePictureIntent, FLOORPLANGETPICFROMCAMERA);
				
				
				
				
			}
		});
		hiddeneditpicturebutton=new Button(this);
		hiddeneditpicturebutton.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = u.openpicture(editpicturelocation);
				System.out.println("this is the name of the file to be edited."+editpicturelocation);
				startActivityForResult(intent, EDITPICTUREACTIVITY);
				
				
			}
		});
		floorplantitle.setTextColor(view.presentableblue);
		floorplantitle.setText(Tabs1.floorplanname);

		
		floorplanprevious.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				ACTION = ACTION_FLOORPLANPREVIOUS;
				view.invalidate();
			}
		});		
		floorplannext.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				ACTION = ACTION_FLOORPLANNEXT;
				view.invalidate();
			}
		});
		fullscreen.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
			
				ACTION = ACTION_FULLSCREEN;
				toptoolbar.setVisibility(View.INVISIBLE);
				righttoolbar.setVisibility(View.INVISIBLE);
				unfullscreen.setVisibility(View.VISIBLE);

			}
		});
		
		unfullscreen.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				ACTION = ACTION_UNFULLSCREEN;
				toptoolbar.setVisibility(View.VISIBLE);
				righttoolbar.setVisibility(View.VISIBLE);
				unfullscreen.setVisibility(View.INVISIBLE);

			}
		});
		showlayers.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				Log.d("show layers selected","true");
				if(!(ACTION==ACTION_SHOWLAYERS)){
					ACTION = ACTION_SHOWLAYERS;
				}else{
					ACTION = ACTION_DONOTHING;
				}
				view.invalidate();

			}
		});
		metersperpixelbutton.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				//show dialog asking if you want scale from google maps, or from 2 points on map
				getscaledialog();
				//startActivity(u.intent("Getscalefromgooglemaps"));

			}
		});
		
		
		resizeicons.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				view.resizeboolean=true;
				ACTION = ACTION_RESIZEICONS;
				toptoolbar.setVisibility(View.INVISIBLE);
				righttoolbar.setVisibility(View.INVISIBLE);
				
				resizeiconsseekbar.setVisibility(View.VISIBLE);
				resizeiconscancel.setVisibility(View.VISIBLE);
				resizeiconsfinished.setVisibility(View.VISIBLE); 
				
				
			}
		});
		resizeiconscancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				//restore size
				//resizeiconsseekbar.setProgress((int)((float)resizeiconsseekbar.getMax()/(float)4));
				view.invalidate();
				ACTION = ACTION_DONOTHING;
				toptoolbar.setVisibility(View.VISIBLE);
				righttoolbar.setVisibility(View.VISIBLE);
				
				resizeiconsseekbar.setVisibility(View.INVISIBLE);
				resizeiconscancel.setVisibility(View.INVISIBLE);
				resizeiconsfinished.setVisibility(View.INVISIBLE); 
				
				
				
			}
		});
		resizeiconsfinished.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if(ACTION==ACTION_RESIZEICON){
					writeonedbitem(view.itemselectednumber);
				}else{
					rewritewholedb();
				}
				
				ACTION = ACTION_DONOTHING;
				toptoolbar.setVisibility(View.VISIBLE);
				righttoolbar.setVisibility(View.VISIBLE);
				
				resizeiconsseekbar.setVisibility(View.INVISIBLE);
				resizeiconscancel.setVisibility(View.INVISIBLE);
				resizeiconsfinished.setVisibility(View.INVISIBLE); 
				
				
				
				
				
			}
		});
		
		
		
		preferences.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				ACTION = ACTION_PREFERENCES;
				//preferences();
				startActivityForResult(u.intent("FloorPlanPrefs"),PREFS);
			}
		});


		BMS.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_BMS;
				Log.d("mode", u.s(MODE));
				
			}
		});
		ELC.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_ELC;
				Log.d("mode", u.s(MODE));
				
				
			}
		});
		Gateway.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_GATEWAY;
				Log.d("mode", u.s(MODE));
				
			}
		});
		SAM.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				//MODE = MODE_SAM;
				//Log.d("mode", u.s(MODE));
				int itemnum=-1;
				for (int k=0; k<view.i; k++){
					if(view.ITEMtype[k]==view.TYPE_ELC){
						itemnum=k;
					}
				}
				if(!(itemnum==-1)){
					view.addsamsdialog("ELC# "+u.s(view.ELCdisplaynumber[itemnum])+": SAMs Menu",
							itemnum);
				}
				
			}
		});
		tempsensor.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_TEMPSENSOR;
				Log.d("mode", u.s(MODE));
				
				
			}
		});
		ethernetport.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_ETHERNETPORT;
				Log.d("mode", u.s(MODE));
				
			}
		});
		distributionboard.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_DISTRIBUTIONBOARD;
				Log.d("mode", u.s(MODE));
				
			}
		});
		samarray.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_SAMARRAY;
				Log.d("mode", u.s(MODE));
				
			}
		});
		datahub.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_DATAHUB;
				Log.d("mode", u.s(MODE));
				
			}
		});
		
		AddText.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE=MODE_ADDTEXT;
				getaddtextdialog("ADD TEXT",view.i, FloorPlanActivity.this).show();
				Log.d("mode", u.s(MODE));
				
			}
		});

		

		legend.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				highlightbutton(arg0);
				MODE = MODE_LEGEND;
				Log.d("mode", u.s(MODE));
				
			}
		});
		
		view = new FloorPlanView(this);
		view.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		rl.addView(view);
		rl.addView(toolbar);

		
		
		setContentView(rl);
		//readexcel();
		
		
			
			AUTORENUMBERTEMPS=!Tabs1.db.tableexists(dbtablename);
			
			WriteBoolean(this,"autorenumbertemps", AUTORENUMBERTEMPS);
			System.out.println("auto number="+AUTORENUMBERTEMPS);
			
		
		
		readdb();
		grabsamcounts();
		grabelccount();
		System.out.println("item"+" "+"type"+"  "+"tempcount");
		for (int h=0; h<view.i;h++){
			System.out.println(h+" "+view.ITEMtype[h]+"  "+view.ITEMtempsensorcount[h]);
		}
	}catch(Throwable e){
		e.printStackTrace();
		finish();
	}
	}

	// Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.floorplan_menu, menu);
		return true;
	}

	

	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void writeexcel() {
		File file = new File(Tabs1.Siteslistlocation);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WritableWorkbook copy = null;
		try {
			copy = Workbook.createWorkbook(file, workbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean sheetexists = false;
		String[] sheets = workbook.getSheetNames();
		int r = 0;
		int sheetnumber = 0;
		for (r = 0; r < sheets.length; r++) {
			if (Tabs1.floorplanname.equals(sheets[r])) {
				sheetexists = true;
				sheetnumber = r;
				break;
			}
		}
		if (!sheetexists) {
			copy.createSheet(Tabs1.floorplanname, sheets.length + 1);
			sheetnumber = sheets.length;
		}

		// int snum = 0;

		WritableSheet sheet = copy.getSheet(sheetnumber);
		sheet.setName(Tabs1.floorplanname);
		
		int startcolumn=0;
		
		u.writecell(sheet, startcolumn++, 0, "Rectangle Left", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Rectangle Top", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Rectangle Right", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Bottom", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Item Type", "string", null);
		u.writecell(sheet, startcolumn++, 0, "TempSensor Count", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Size Percent", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Sams Count", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Master ELC number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Display Number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "ScaleMx", "string", null);
		u.writecell(sheet, startcolumn++, 0, "ScaleMy", "string", null);
		u.writecell(sheet, startcolumn++, 0, "ITEMx", "string", null);
		u.writecell(sheet, startcolumn++, 0, "ITEMy", "string", null);
		u.writecell(sheet, startcolumn++, 0, "String", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Font Size", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Font Color", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Meters Per Pixel", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Floor Plan Number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "ELC display Number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Item Link Tab Number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Item Link Tab Item Number", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Item Text Offset x", "string", null);
		u.writecell(sheet, startcolumn++, 0, "Item Text Offset y", "string", null);
		
	
		
		for (int t = 0; t < view.i; t++) {

			int column = 0;

			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMrect[t].left), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMrect[t].top), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMrect[t].right), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMrect[t].bottom), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMtype[t]), "string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMtempsensorcount[t]),
					"string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMsizepercent[t]),
					"string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMsamscount[t]),
					"string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMmasterelcnumber[t]),
					"string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMdisplaynumber[t]), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.SCALEDITEMx[t]), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.SCALEDITEMy[t]), "string",
					null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMx[t]), "string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMy[t]), "string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMstring[t]), "string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMfontsize[t]), "string", null);
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMfontcolor[t]), "string", null);
			
			
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMmetersperpixel[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMfloorplannumber[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ELCdisplaynumber[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMLINKtabnumber[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMLINKtabitemnumber[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMtextoffsetx[t]), "string", null);
			
			u.writecell(sheet, column++, t + 1,
					String.valueOf(view.ITEMtextoffsety[t]), "string", null);
			
			

		}
		Log.d("Sheet Writen", "true");

		try {
			copy.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copy.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readexcel() {
		WorkbookSettings ws = new WorkbookSettings();

		File file = new File(Tabs1.Siteslistlocation);
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] sheets = workbook.getSheetNames();
		int sheetnum = 0;
		for (int r = 0; r < sheets.length; r++) {
			try{
			if (sheets[r].equals(Tabs1.floorplanname)) {
				sheetnum = r;
				int worksheet = sheetnum;
				Sheet sheet = workbook.getSheet(worksheet);

			

				int startcolumn=0;
				ArrayList<String> rectleft = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> recttop = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> rectright = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> rectbottom = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> type = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> tempsensorcount = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> percentsize = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> samscount = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> masterelcnumber = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> displaynumber = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> scaledx = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> scaledy = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> x = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> y = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> string = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> fontsize = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> fontcolor = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> metersperpixel = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> floorplannumber = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> elcdisplaynumber = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> tabnumber = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> tabitemnumber = u.getcol(sheet, startcolumn++, 1);
				
				ArrayList<String> textoffsetx = u.getcol(sheet, startcolumn++, 1);
				ArrayList<String> textoffsety = u.getcol(sheet, startcolumn++, 1);

				int numberofitems = rectleft.size();

				for (int t = 0; t < numberofitems; t++) {

					// view.ITEMrect[t].left=u.i(rectleft.get(t));
					// view.ITEMrect[t].top=u.i(rectleft.get(t));
					// view.ITEMrect[t].right=u.i(rectleft.get(t));
					// view.ITEMrect[t].bottom=u.i(rectleft.get(t));

					view.ITEMrect[t] = new Rect(u.i(rectleft.get(t)),
							u.i(recttop.get(t)), u.i(rectright.get(t)),
							u.i(rectbottom.get(t)));
					view.ITEMtype[t] = u.i(type.get(t));
					view.ITEMtempsensorcount[t] = u.i(tempsensorcount
							.get(t));
					view.ITEMsizepercent[t] = u.i(percentsize
							.get(t));
					view.ITEMsamscount[t] = u.i(samscount
							.get(t));
					view.ITEMmasterelcnumber[t] = u.i(masterelcnumber.get(t));
					view.ITEMdisplaynumber[t] = u.i(displaynumber.get(t));
					view.SCALEDITEMx[t] = Float.parseFloat(scaledx
							.get(t));
					view.SCALEDITEMy[t] = Float.parseFloat(scaledy
							.get(t));
					view.ITEMx[t] = Float.parseFloat(x.get(t));
					view.ITEMy[t] = Float.parseFloat(y.get(t));
					
				
					view.ITEMstring[t] = string.get(t);
					view.ITEMfontsize[t] = u.i(fontsize.get(t));
					view.ITEMfontcolor[t] = u.i(fontcolor.get(t));
					
					view.ITEMmetersperpixel[t] = Float.parseFloat(metersperpixel.get(t));
					view.ITEMfloorplannumber[t]=u.i(floorplannumber.get(t));
					
					view.ELCdisplaynumber[t]=u.i(elcdisplaynumber.get(t));
					
					view.ITEMLINKtabnumber[t]=u.i(tabnumber.get(t));
					view.ITEMLINKtabitemnumber[t]=u.i(tabitemnumber.get(t));
					
					view.ITEMtextoffsetx[t]=u.i(textoffsetx.get(t));
					view.ITEMtextoffsetx[t]=u.i(textoffsety.get(t));
					
					
					Log.d("item found", u.s((int) view.ITEMx[t]));
				}
				
				
				
				view.i = numberofitems;
				
				int i=0;
				for (i=0; i < numberofitems; i++) {
					assignbitmaps(i);
				}
				if(i!=0){
					resizeiconsseekbar.setProgress(view.ITEMsizepercent[i-1]);
				}
			}
			}catch(ArrayIndexOutOfBoundsException e){
				
			}
		}

	}

	public static void rewritewholedb() {
		deletingitem=true;
		String dbtablename=Tabs1.floorplandbname;
		Log.d("floorplan writedb",dbtablename);
		try{
			Tabs1.db.deletedbtable(dbtablename);
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		try{
			Tabs1.db.createfloorplandb(dbtablename);
		}catch(Throwable e){
			e.printStackTrace();
		}
//		for (int w = 0; w < view.i; w++) {
//			try{
//				view.ITEMsizepercent[w] = view.currentpercentchange;
//				view.ITEMbitmap[w] = view.iconbitmap[view.ITEMtype[w]];
//			}catch(Throwable e){
//				
//			}
//		}
		for (int t = 0; t < view.i; t++) {
			writeonedbitem(t);
			
		}
		
		
	}
	public static void writeonedbitem(int t){
		try{
		int startnum=1;
		
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMrect[t].left), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMrect[t].top), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMrect[t].right), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMrect[t].bottom), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMtype[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMtempsensorcount[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMsizepercent[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMsamscount[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMmasterelcnumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMdisplaynumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.SCALEDITEMx[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.SCALEDITEMy[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMx[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMy[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMstring[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMfontsize[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMfontcolor[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMmetersperpixel[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMfloorplannumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ELCdisplaynumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMLINKtabnumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMLINKtabitemnumber[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMtextoffsetx[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);
			Tabs1.db.addorupdatemulticolumn(dbtablename, t, startnum++, String.valueOf(view.ITEMtextoffsety[t]), Tabs1.db.KEY_FLOORPLAN_TITLES);

			
			view.updatetabsdatabases(t);
			
			
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    readdb();
	}
	private void readdb() {
		
		String dbtablename="floorplan";

				int startcolumn=1;
				
				Log.d("reading from db started","true");
				Tabs1.db.showtableslog();
				Tabs1.db.showfulldblog(dbtablename);
				
				ArrayList<String> rectleft = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> recttop = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> rectright = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> rectbottom = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> type = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> tempsensorcount = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> percentsize = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> samscount = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> masterelcnumber = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> displaynumber = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> scaledx = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> scaledy = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> x = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> y = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> string = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> fontsize = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> fontcolor = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> metersperpixel = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> floorplannumber = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> elcdisplaynum = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				
				ArrayList<String> tabnumber = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> tabitemnumber = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				
				ArrayList<String> textoffsetx = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);
				ArrayList<String> textoffsety = Tabs1.db.getcolumn(dbtablename, startcolumn++, Tabs1.db.KEY_FLOORPLAN_TITLES);

				
				int numberofitems = rectleft.size();

				for (int t = 0; t < numberofitems; t++) {
					try{
					// view.ITEMrect[t].left=u.i(rectleft.get(t));
					// view.ITEMrect[t].top=u.i(rectleft.get(t));
					// view.ITEMrect[t].right=u.i(rectleft.get(t));
					// view.ITEMrect[t].bottom=u.i(rectleft.get(t));

					view.ITEMrect[t] = new Rect(u.i(rectleft.get(t)),
							u.i(recttop.get(t)), u.i(rectright.get(t)),
							u.i(rectbottom.get(t)));
					view.ITEMtype[t] = u.i(type.get(t));
					view.ITEMtempsensorcount[t] = u.i(tempsensorcount
							.get(t));
					view.ITEMsizepercent[t] = u.i(percentsize
							.get(t));
					view.ITEMsamscount[t] = u.i(samscount
							.get(t));
					view.ITEMmasterelcnumber[t] = u.i(masterelcnumber.get(t));
					view.ITEMdisplaynumber[t] = u.i(displaynumber.get(t));
					
					view.SCALEDITEMx[t] = Float.parseFloat(scaledx
							.get(t));
					view.SCALEDITEMy[t] = Float.parseFloat(scaledy
							.get(t));
					view.ITEMx[t] = Float.parseFloat(x.get(t));
					System.out.println("t="+t);
					view.ITEMy[t] = Float.parseFloat(y.get(t));
					
				
					view.ITEMstring[t] = string.get(t);
					
					try{
						view.ITEMfontsize[t] = u.i(fontsize.get(t));
					}catch(Throwable e){
						e.printStackTrace();
						view.ITEMfontsize[t] = view.defaulttextsize;
					}
					view.ITEMfontcolor[t] = u.i(fontcolor.get(t));
					
					view.ITEMmetersperpixel[t] = Float.parseFloat(metersperpixel.get(t));
					
					view.ITEMfloorplannumber[t]=u.i(floorplannumber.get(t));
					
					view.ELCdisplaynumber[t] = u.i(elcdisplaynum.get(t));
					
					view.ITEMLINKtabnumber[t]=u.i(tabnumber.get(t));
					view.ITEMLINKtabitemnumber[t]=u.i(tabitemnumber.get(t));
					
					view.ITEMtextoffsetx[t]=u.i(textoffsetx.get(t));
					view.ITEMtextoffsety[t]=u.i(textoffsety.get(t));
					
					Log.d("item found", u.s((int) view.ITEMx[t]));
					}catch(NullPointerException e){
						System.out.println("couldn't find item t="+t);
					}
				}
				
				
				
				view.i = numberofitems;
				Log.d("i on readdb",u.s(view.i));
				
				int i=0;
				for (i=0; i < numberofitems; i++) {
					assignbitmaps(i);
				}
				for (i=0; i < numberofitems; i++) {
					view.commenceresizeofitem(i, false);
				}
			}
		
	
	
	public void assignbitmaps(int itemnumber) {

	
		assignonebitmap(itemnumber, view.TYPE_BMS);
		assignonebitmap(itemnumber, view.TYPE_ELC);
		assignonebitmap(itemnumber, view.TYPE_GATEWAY);
		assignonebitmap(itemnumber, view.TYPE_SAM);
		assignonebitmap(itemnumber, view.TYPE_TEMPSENSOR);
		assignonebitmap(itemnumber, view.TYPE_ETHERNETPORT);
		assignonebitmap(itemnumber, view.TYPE_DISTRIBUTIONBOARD);
		assignonebitmap(itemnumber, view.TYPE_SAMARRAY);
		assignonebitmap(itemnumber, view.TYPE_DATAHUB);
		assignonebitmap(itemnumber, view.TYPE_LEGEND1);
		assignonebitmap(itemnumber, view.TYPE_LEGEND2);
		assignonebitmap(itemnumber, view.TYPE_LEGEND3);
		assignonebitmap(itemnumber, view.TYPE_LEGEND4);
		
		//assignonebitmap(itemnumber, view.TYPE_LEGEND);
		
	}
	public void assignonebitmap(int itemnumber,int type){
		
		
		if (view.ITEMtype[itemnumber] == type){
			view.ITEMbitmap[itemnumber] = Bitmap.createScaledBitmap(view.originaliconbitmap[type],view.originaliconbitmap[type].getWidth()*view.ITEMsizepercent[itemnumber]/100,view.originaliconbitmap[type].getHeight()*view.ITEMsizepercent[itemnumber]/100,true);
			view.iconbitmap[type]=view.ITEMbitmap[itemnumber];
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showchecksdialog();
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_FLOORPLAN);
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_ASSETINFO);
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_ASSETINFO);
			Tabs1.db.showfulldblog(Tabs1.db.TABLE_ASSETINFO);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void makepreferencechanges() {
		if (ICONSCALING) {
			turnonscaling();
		} else {
			turnoffscaling();
		}
	}
	
	 public static String Read(Context context, final String key) {
	        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
	        return pref.getString(key, "");
	    }
	 
	    public static void Write(Context context, final String key, final String value) {
	          SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	          SharedPreferences.Editor editor = settings.edit();
	          editor.putString(key, value);
	          editor.commit();        
	    }
	     
	    // Boolean  
	    public static boolean ReadBoolean(Context context, final String key, final boolean defaultValue) {
	        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	        return settings.getBoolean(key, defaultValue);
	    }
	 
	    public static void WriteBoolean(Context context, final String key, final boolean value) {
	          SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	          SharedPreferences.Editor editor = settings.edit();
	          editor.putBoolean(key, value);
	          editor.commit();        
	    }
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
			// Check which request we're responding to
	    	
	    	Log.d("Request Code",u.s(requestCode));
	    	if (requestCode == FLOORPLANGETPICFROMCAMERA) {
				// Make sure the request was successful
	    	
	    		
				long picturestoptime=System.currentTimeMillis();
				
				executegetpicfromcameraresult(TABFORGETPICTURE,FOLDERNAMEFORGETPICTURE, PICTURESTARTTIME,picturestoptime);
				
				System.gc();
				try{
					view.showitemmenu(view.itemselectednumber,view.gettabofitemselected(view.itemselectednumber));
				}catch(Throwable e){
					
				}
				if (resultCode == RESULT_OK) {
					// startActivity(u.intent("Tabs1"));
					// this.finish();
					
				}
			}
	    	if (requestCode == EDITPICTUREACTIVITY) {
				view.showitemmenu(view.itemselectednumber,view.gettabofitemselected(view.itemselectednumber));

	    		// Make sure the request was successful
				
			}
			if (requestCode == PREFS) {
				
				RAPIDPLACEMENT=ReadBoolean(this,"rapidplacement",false);
				
				DRAWSAMREFERENCE=ReadBoolean(this,"drawsamreferencetable",true);
					
				Boolean SCALEDICONS=ReadBoolean(this,"scaledicons", false);
				Log.d("Scaled Icons",String.valueOf(SCALEDICONS));
				if (SCALEDICONS){
					ICONSCALING=true;
					Boolean BOUNDICONS=ReadBoolean(this,"boundicons", false);
					if (BOUNDICONS){
						ICONBOUNDING=true;
					}else{
						ICONBOUNDING=false;
					}
				}else{
					ICONSCALING=false;
				}
				
				
				Boolean SHOWTOOLBARKEY=ReadBoolean(this,"toolbarkey", false);
				Log.d("SHOWTOOLBARKEY",String.valueOf(SHOWTOOLBARKEY));
				if (SHOWTOOLBARKEY){
					for(int i=0; i<((ViewGroup)righttoolbarbuttonparent).getChildCount(); i++){
						if (((ViewGroup)righttoolbarbuttonparent).getChildAt(i) instanceof TextView){
							((ViewGroup)righttoolbarbuttonparent).getChildAt(i).setVisibility(View.VISIBLE);
						}
						
					}
					
					
				}else{
					for(int i=0; i<((ViewGroup)righttoolbarbuttonparent).getChildCount(); i++){
						if (((ViewGroup)righttoolbarbuttonparent).getChildAt(i) instanceof TextView){
							((ViewGroup)righttoolbarbuttonparent).getChildAt(i).setVisibility(View.GONE);
						}
						
					}
				}
				
				
				AUTORENUMBERTEMPS=ReadBoolean(this,"autorenumbertemps", true);
				
				
				NGBICONS=ReadBoolean(this,"ngbicons", false);
			
				
				
				MULTILEVEL=ReadBoolean(this,"multilevel", false);
				try{
					if(AUTORENUMBERTEMPS){
						view.AUTORENUMBERalltempsensorsbasedonlocation();
						
					}
				}catch(Throwable e){
					
				}
				
				
				makepreferencechanges();
				view.refreshDrawableState();
				view.invalidate();
			}
	    }
	    public static AlertDialog getaddtextdialog(String title, final int itemnumber, Context ctx){
			
	    	
	    	
			AlertDialog.Builder getaddtext=new AlertDialog.Builder(ctx);
			
			final LinearLayout linearlayout=new LinearLayout(ctx);
			linearlayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			linearlayout.setOrientation(LinearLayout.VERTICAL);
			
			final EditText nameet=new EditText(ctx);
			nameet.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			final TextView fontname=new TextView(ctx);
			fontname.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			final TextView colorname=new TextView(ctx);
			colorname.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			final Spinner fontsizespinner=new Spinner(ctx);
			fontsizespinner.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			final Spinner colorspinner=new Spinner(ctx);
			fontsizespinner.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			List<String> fontsizelist=new ArrayList<String>();
			for(int t=1; t<200; t++){
				fontsizelist.add(u.s(t));
			}
			ArrayAdapter<String> fontsizearrayadapter = new ArrayAdapter<String>(ctx, R.layout.spinnertextview, fontsizelist);
			fontsizearrayadapter.setDropDownViewResource(R.layout.spinnertextview);
			
			List<String> colorlist=new ArrayList<String>();
			{
				colorlist.add("RED");
				colorlist.add("BLACK");
				colorlist.add("BLUE");
				colorlist.add("GREEN");
				colorlist.add("WHITE");
				colorlist.add("GRAY");
			}
			ArrayAdapter<String> colorlistarrayadapter = new ArrayAdapter<String>(ctx, R.layout.spinnertextview, colorlist);
			fontsizearrayadapter.setDropDownViewResource(R.layout.spinnertextview);
			colorspinner.setAdapter(colorlistarrayadapter);
			fontsizespinner.setAdapter(fontsizearrayadapter);
			fontsizespinner.setSelection(getIndexofSpinner(fontsizespinner, "25"));
			
			fontname.setText("Select Font Size");
			fontname.setTextSize(20f);
			colorname.setText("Select Color");
			colorname.setTextSize(20f);

			linearlayout.addView(nameet);
			linearlayout.addView(fontname);
			linearlayout.addView(fontsizespinner);
			linearlayout.addView(colorname);
			linearlayout.addView(colorspinner);
			
			
			if(!(itemnumber==view.i)){
	    		nameet.setText(view.ITEMstring[itemnumber]);
	    		fontsizespinner.setSelection(getIndexofSpinner(fontsizespinner,u.s(view.ITEMfontsize[itemnumber])));
	    		colorspinner.setSelection(getIndexofSpinner(colorspinner,u.s(view.ITEMfontcolor[itemnumber])));
	    	}
			
			getaddtext.setView(linearlayout);
			getaddtext.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					dialog.cancel();
				}
			});
			getaddtext.setTitle(title);
			getaddtext.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String string=nameet.getText().toString();
					
					
					float fontsize = fontsizespinner.getSelectedItemPosition()+1;
					//ITEMelcnumber[itemselectednumber]=u.i(string);
					int colpos = colorspinner.getSelectedItemPosition();
					int[] col = new int[]{Color.RED,Color.BLACK,Color.BLUE,Color.GREEN,Color.WHITE,Color.GRAY};
					int color=col[colpos];
					
					Log.d("addtext",u.s((int)fontsize)+colpos+color);
					
						
							
							view.ITEMstring[itemnumber]=string;
							view.ITEMfontsize[itemnumber] = (int)fontsize;
							view.ITEMfontcolor[itemnumber] = color;
							
							view.itemname=string;
							view.fontsize = (int)fontsize;
							view.color = color;
							view.invalidate();
							dialog.dismiss();
							FloorPlanActivity.writeonedbitem(itemnumber);
					}
			
			});
			
			return getaddtext.create();
		}
		
		private static int getIndexofSpinner(Spinner spinner, String myString) {

			int index = 0;

			for (int i = 0; i < spinner.getCount(); i++) {

				if (spinner.getItemAtPosition(i).toString().equals(myString)) {

					index = i;
				}
			}
			return index;
		}
		public void save(){
			try {
				runOnUiThread(new Runnable() {
					public void run() {
						view.addvaluestotabstable();
						view.addvaluestobom();
						if(u.isbluestacks()){
							//Tabs1.bluestacksimagequalitydialog(getBaseContext());
						}
					}
				});
				
				if(ACTION==ACTION_SHOWLAYERS){
					ACTION=ACTION_DONOTHING;
				}
				
				Log.d("MULTILEVEL checked in save",String.valueOf(MULTILEVEL));
//				if(!MULTILEVEL){
//					Canvas canvascopy = new Canvas();
//					Bitmap canvasbitmap=resizedbitmaponmemorycheck();
//					canvascopy.setBitmap(canvasbitmap);
//
//					view.DONTSCALEORTRANSLATE = true;
//					view.CanvasChanges(canvascopy);
//					view.DONTSCALEORTRANSLATE = false;
//
//					File file = new File(Tabs1.OutputFloorPlanLocation[FloorPlanView.fp]);
//					file.mkdirs();
//					if (file.exists())
//						file.delete();
//
//					FileOutputStream fos = new FileOutputStream(
//							new File(Tabs1.OutputFloorPlanLocation[FloorPlanView.fp]));
//
//					canvasbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//					//canvasbitmap.recycle();
//					try {
//						Log.d("trying outputstream flush and close","true");
//						fos.flush();
//						fos.close();
//						fos = null;
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}else{
					
				//recycling original canvas
				
				
				Log.d("floorplancount",u.s(floorplancount));
					for(int x=0; x<floorplancount; x++){
						Log.d("floorplan count",u.s(x));
						FloorPlanView.fp=x;
//						view.originalbitmap = BitmapFactory.decodeFile(Tabs1.InputFloorPlanLocation[x]);
//						Log.d("inputfloorplan path",Tabs1.InputFloorPlanLocation[x].toString());
//						view.bm = view.originalbitmap;
//						view.bitheight = view.bm.getHeight();
//						view.bitwidth = view.bm.getWidth();
						view.invalidate();
						
						Canvas canvascopy = new Canvas();
						Bitmap canvasbitmap=resizedbitmaponmemorycheck();
						canvascopy.setBitmap(canvasbitmap);

						view.DONTSCALEORTRANSLATE = true;
						view.CanvasChanges(canvascopy);
						view.DONTSCALEORTRANSLATE = false;

						String newoutputfilestring=Tabs1.floorplanstrings.get(x).replace(Tabs1.inputfloorplandirectory, Tabs1.outputfloorplandirectory);
						
						File file = new File(newoutputfilestring);
						file.mkdirs();
						if (file.exists())
							file.delete();

						FileOutputStream fos = new FileOutputStream(
								new File(newoutputfilestring));

						Log.d("outputfloorplan path",newoutputfilestring);
						canvasbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
						//canvasbitmap.recycle();
						try {
							Log.d("trying outputstream flush and close","true");
							fos.flush();
							fos.close();
							fos = null;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						BitmapFactory.Options o = new BitmapFactory.Options();
						o.inSampleSize = Tabs1.FLOORPLANPICTURESIZE;
						Bitmap bm = BitmapFactory.decodeFile(newoutputfilestring, o);

						double calcheight = (double) Tabs1.screenheight
								* (double) Tabs1.FLOORPLANPICTUREDISPLAYPERCENTOFSCREEN
								/ (double) 100;
						double calcwidth = (double) bm.getWidth()
								/ (double) bm.getHeight() * (double) calcheight;

						int height = (int) calcheight;
						int width = (int) calcwidth;

						Bitmap resizedbitmap = null;

						outputfloorplanthumbnail = Bitmap.createScaledBitmap(bm, width, height,
								true);
						bm.recycle();
						canvasbitmap.recycle();
						
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//writeexcel();
			//rewritewholedb();
			System.out.println("database after write db");
			try{
				Tabs1.db.showfulldblog(dbtablename);
			}catch(Throwable e){
				
			}
			FINALBITMAPSCALE=1;
		}
		protected class SaveTask extends AsyncTask<Object, Void, String> {

			ProgressDialog progressDialog;

			public SaveTask(ProgressDialog progressDialog) {
				this.progressDialog = progressDialog;
			}

			@Override
			protected String doInBackground(Object... params) {

				try {
					looper.prepare();
				} catch (RuntimeException e) {

				}
				save();
				return null;

				
			}

			// -- gets called just before thread begins
			@Override
			protected void onPreExecute() {
				// progressDialog = new ProgressDialog(Tabs1.this);

				//progressDialog.show();
				rl.removeAllViews();
				view.destroyDrawingCache();
				try{
					refreshcomponentimagesontabs();
				}catch(Throwable e){
					//no images to update
				}
			}

			@Override
			protected void onPostExecute(String result) {
				//progressDialog.dismiss();
				Log.d("saving process completed","true");
				try{
					removehourglassicons();
					
				}catch(Throwable e){
					e.printStackTrace();
					try {
						
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		}
		public Bitmap resizedbitmaponmemorycheck(){
			int width=(int) view.bitwidth[view.fp];
			int height=(int) view.bitheight[view.fp];
			
			
			final Runtime rt = Runtime.getRuntime();
			Log.d("free memory, maxmemory, total memory",rt.freeMemory()+" "+rt.maxMemory()+" "+rt.totalMemory());
			final long freeMem = rt.freeMemory() + (rt.maxMemory() - rt.totalMemory());
			File file1=new File(Tabs1.floorplanstrings.get(view.fp));
			double ratio=0.50;
			
			long myResourceSize=file1.length();
			
			Bitmap canvasbitmap;
			//canvasbitmap = Bitmap.createBitmap(width, height,
			//		Bitmap.Config.ARGB_8888);
			Log.d("width and height",u.s(width)+" "+u.s(height));
			Log.d("myResourceSize, freeMem, ration",myResourceSize+" "+freeMem+" "+ratio);
			
			
			
			if (!u.isbluestacks()) { 
				Log.d("not bluestacks","true");
				///This is where the app get out of memory error!!!
				
				canvasbitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				
			}else {
				
				FINALBITMAPSCALE=4;
				Log.d("bluestacks","true");
				canvasbitmap = Bitmap.createBitmap(width/FINALBITMAPSCALE, height/FINALBITMAPSCALE,
						Bitmap.Config.ARGB_8888);
				
			}
			
			return canvasbitmap;
		}
		public static void recycleallbitmaps(){
			
			for (int h=0; h<floorplancount; h++){
				view.originalbitmap[h].recycle();
				view.bm[h].recycle();
			}
			//view.bm.recycle();

			
			for (int h=0; h<view.originaliconbitmap.length; h++){
				view.originaliconbitmap[h].recycle();
				view.iconbitmap[h].recycle();
			}
			

			view.BMS.recycle();
			view.ELC.recycle();
			view.Gateway.recycle();
			view.SAM.recycle();
			view.MINISAM.recycle();
			view.tempsensor.recycle();
			view.ethernetport.recycle();
			view.tinygreencheck.recycle();
			view.tinyredx.recycle();
			view.ethernetport.recycle();
			view.distributionboard.recycle();
			view.samarray.recycle();
			view.datahub.recycle();
			view.portraitlegend.recycle();
			view.squeezedlandscapelegend.recycle();
			
			view.NGBlegend.recycle();
			
			view.landscapelegend.recycle();
			for (int h=0; h<view.ITEMbitmap.length; h++){
				
				try{
					view.ITEMbitmap[h].recycle();
				}catch(NullPointerException e){
					e.printStackTrace();
				}
				try{
					view.ScaledITEMbitmap[h].recycle();
				}catch(NullPointerException e){
					e.printStackTrace();
				}
			
			}
			view.canvasbitmap.recycle();
			
			Addtextbit.recycle();
		}
		public void sethourglassicons(){
			
			hourglasslistener=hourglasslistener();
			
			setonehourglass(Tabs1.riserdiagrambutton);
			setonehourglass(Tabs1.mopmcrbutton);
			setonehourglass(Tabs1.loaddiagrambutton);
			setonehourglass(Tabs1.mopfloorplanbutton);
			setonehourglass(Tabs1.latexbutton);
			
			
			Tabs1.floorplanpicturebutton.setClickable(false);
			
			Log.d("floorplannumber",u.s(floorplannumber));
			
			
			
				int floorplancount=Tabs1.sitefpimageview.length;
				for(int x=0; x<floorplancount; x++){
					setonehourglass(Tabs1.sitefpimageview[x]);
				}
			
		
		}
		public void removehourglassicons(){
			
			restoreonehourglass(Tabs1.riserdiagrambutton,R.drawable.riserdiagramicon);		
			restoreonehourglass(Tabs1.mopmcrbutton,R.drawable.mcricon);		
			restoreonehourglass(Tabs1.loaddiagrambutton,R.drawable.loaddiagrambutton);		
			restoreonehourglass(Tabs1.mopfloorplanbutton,R.drawable.floorplanicon);		
			restoreonehourglass(Tabs1.latexbutton,R.drawable.latex);		
				
			Tabs1.floorplanpicturebutton.setClickable(true);
			
			
				int floorplancount=Tabs1.sitefpimageview.length;
				for(int x=0; x<floorplancount; x++){
					
					Tabs1.sitefpimageview[x].setImageBitmap(outputfloorplanthumbnail);
					Tabs1.sitefpimageview[x].setClickable(true);
					try{
						Tabs1.sitefpimageview[x].removeOnLayoutChangeListener(hourglasslistener);
					}catch(Throwable e){
						
					}
					Tabs1.sitefpimageview[x].setAnimation(null);
				
				}
			
			
			
		}
		public void setonehourglass(final ImageView iv){
			
			int resourceint=R.drawable.hourglassicon;
			Bitmap resourcebm=BitmapFactory.decodeResource(getResources(), resourceint);
			iv.setImageResource(resourceint);
			iv.setClickable(false);
			iv.addOnLayoutChangeListener(hourglasslistener);
			
			
		}
		public void restoreonehourglass(ImageView iv, int drawable){
			iv.setImageResource(drawable);
			iv.setClickable(true);
			iv.removeOnLayoutChangeListener(hourglasslistener);
			iv.setAnimation(null);
		}
		public ImageView.OnLayoutChangeListener hourglasslistener(){
			
			OnLayoutChangeListener olcl=new ImageView.OnLayoutChangeListener(){

				@Override
				public void onLayoutChange(View v, int left, int top,
						int right, int bottom, int oldLeft, int oldTop,
						int oldRight, int oldBottom) {
					
					
					RotateAnimation animation = new RotateAnimation(0f, 350f, v.getWidth()/2,v.getHeight()/2); 
			        animation .setInterpolator(new LinearInterpolator());
			        animation .setRepeatCount(Animation.INFINITE);
			        animation .setDuration(700);
					
			       
			        ((ImageView)v).startAnimation(animation);
					
				}};
			return olcl;
			
		}
		public void grabelccount(){
			view.ELCCOUNT=0;
			for (int h=0; h<view.i;h++){
				if(view.ITEMtype[h]==view.TYPE_ELC){
					view.ELCCOUNT++;
				}
				
			}
			
		}
		public void grabsamcounts(){

			int[] samcount=new int[view.maxelcs];
			for (int h=0; h<samcount.length;h++){
				samcount[h]=0;
			}
			
			int rownum = 0;
			
			try{
				View firstview;
				TableRow tablerow;
				TableLayout tablelayout;
				LinearLayout linearlayout;
	
				firstview = Tabs1.mcrentries[Tabs1.METERINGLIST][rownum][u.cellx("B1")];
				tablerow = ((TableRow) firstview.getParent());
				tablelayout = ((TableLayout) tablerow.getParent());
				linearlayout = (LinearLayout) tablelayout.getParent();
	
				
				Log.d("tablelayoutchild count", u.s(linearlayout.getChildCount()));
				for (int k = 0; k < linearlayout.getChildCount(); k++) {
					
					int elcnum=u.i(Tabs1.mcrentries[Tabs1.METERINGLIST][k][u.cellx("B1")].getText().toString());
					
					for (int y=0; y<view.i; y++){
						if ((view.ITEMtype[y]==view.TYPE_ELC) && view.ELCdisplaynumber[y]==elcnum){
							samcount[elcnum]++;
						}
					}
				}
				for (int y=0; y<view.i; y++){
					if ((view.ITEMtype[y]==view.TYPE_ELC)){
						view.ITEMsamscount[y]=samcount[view.ELCdisplaynumber[y]];
					}
				}
			}catch(Throwable e){
				
			}
		}
		
		
		
		// Function to clear CT details before re-populating
		public void balmvalues(){
			try{
				Tabs1.bomcttablelayout.removeAllViews();
			}
			catch(Throwable e){
				e.printStackTrace();
			}
		}
		public void highlightbutton(View arg0){
			
			try{
				RelativeLayout parent=((RelativeLayout)arg0.getParent());
				for(int child=0; child<parent.getChildCount(); child++){
					parent.getChildAt(child).setBackgroundColor(Color.TRANSPARENT);
				}
				
			}catch(Throwable e){
				LinearLayout parent=((LinearLayout)arg0.getParent());
				for(int child=0; child<parent.getChildCount(); child++){
					parent.getChildAt(child).setBackgroundColor(Color.TRANSPARENT);
				}
				
			}
			
			arg0.setBackgroundColor(Color.parseColor("#501BADA9"));
		}
		public static File DevelopeFileName(String ext, int typeselected) {
			
			String numberselected=u.s(view.ITEMLINKtabitemnumber[view.itemselectednumber]);
			
			u.log("view.itemselectednumber"+view.itemselectednumber);
			u.log("numberselected "+numberselected+ " typeselected"+u.s(typeselected));

			new File(Tabs1.ProjectDirectory);
			File imageF = null;

		
				
				new File(Tabs1.ProjectDirectory
						+ "/"
						+ Tabs1.basedirectory[Tabs1.COMPONENTSFOLDER]
						+ "/"
						+ view.getGenericDisplayText(view.itemselectednumber)
								.toString())
						.mkdirs();

				imageF = new File(Tabs1.createnewimagename(null,Tabs1.COMPONENTSTAB,
						u.i(numberselected), 0, null));
				int v1 = 0;
				while (imageF.exists()) {
					v1++;
					imageF = new File(Tabs1.createnewimagename(null,Tabs1.COMPONENTSTAB,
							u.i(numberselected), v1, null));

				}
			
				

				

			
			

			File parent = imageF.getParentFile();
			if (!parent.exists() && !parent.mkdirs()) {
				throw new IllegalStateException("Couldn't create dir: " + parent);
			}
			if (!(ext == null)) {
				imageF = new File(imageF.getAbsolutePath().replace(".jpg",
						"." + ext));
				
			}
			return imageF;
		}
		
		
		public void lhiphotoeditororotherdialog(final String picturelocation,
				String type, String row, String num) {
			
			Intent intent = u.openpicture(picturelocation);
			startActivity(intent);

		}
		public void getscaledialog() {
			
			ImageView googlemaps = new ImageView(this);
			googlemaps.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			googlemaps.setPadding(25, 25, 25, 25);
			googlemaps.setImageResource(R.drawable.google_maps256x256);
			googlemaps.setOnClickListener(new ImageView.OnClickListener() {
				public void onClick(View v) {

					startActivity(u.intent("Getscalefromgooglemaps"));
					getscaledialog.cancel();

					

				}
			});

			ImageView twopoints = new ImageView(this);
			twopoints.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			twopoints.setPadding(25, 25, 25, 25);
			twopoints.setImageResource(R.drawable.pointsonmapicon);
			twopoints.setOnClickListener(new ImageView.OnClickListener() {

				public void onClick(View v) {

					ACTION = ACTION_GETSCALEFROMPOINTS;
					toptoolbar.setVisibility(View.INVISIBLE);
					righttoolbar.setVisibility(View.INVISIBLE);
					
					
					floorplantitle.setVisibility(View.VISIBLE);
					floorplantitle.setText("Please select first point");
					
					GETSCALESTAGE=STAGE_GETFIRSTPOINT;
					
					getscaledialog.cancel();

				}

			});

			LinearLayout choosepicturelocationlayout;
			choosepicturelocationlayout = new LinearLayout(this);
			choosepicturelocationlayout.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			choosepicturelocationlayout.setOrientation(LinearLayout.HORIZONTAL);
			choosepicturelocationlayout.addView(googlemaps);
			choosepicturelocationlayout.addView(twopoints);
			choosepicturelocationlayout.setGravity(Gravity.CENTER_HORIZONTAL);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choose Method for Getting Scale")
					.setMessage(
							"Please choose Google Maps, or Two Points on Map")
					.setView(choosepicturelocationlayout)
					.setCancelable(false)
					.setIcon(R.drawable.ic_launcher)

					.setNegativeButton("CANCEL",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							}).setCancelable(true);

			getscaledialog = builder.create();
			getscaledialog.show();

		}
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {

			Log.d("keycode", u.s(keyCode));
			Log.d("tab", u.s(KeyEvent.KEYCODE_TAB));

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				
				
				return true;
			}

			

			return false;
		}
		public void showchecksdialog() {
			Boolean passedchecks=false;
			String PositiveButtonString;
			String DisplayChecksOrPassedString;
			String ListChecksString=dochecks();
			String Title;

			if(ListChecksString.trim().length()==0){
				passedchecks=true;
			}
			if (passedchecks){
				DisplayChecksOrPassedString="Are you sure your finished editing floorplans?";
				Title="Exit";
				PositiveButtonString="OK";
			}else{
				DisplayChecksOrPassedString="You have outstanding issues with this floorplan, please correct these issues before exiting.";
				Title="Outstanding Issues!";
				PositiveButtonString="IGNORE & EXIT";
			}
			
			TextView showfailedcheckstv;
			showfailedcheckstv = new TextView(this);
			showfailedcheckstv.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			showfailedcheckstv.setText(ListChecksString);
			showfailedcheckstv.setPadding(5, 5, 5, 5);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(Title)
					.setMessage(DisplayChecksOrPassedString)
					.setView(showfailedcheckstv)
					.setCancelable(false)
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton(PositiveButtonString,  new DialogInterface.OnClickListener() {
						
						@Override
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						
							
							balmvalues();
								new SaveTask(progressDialog).execute();
								try{
									sethourglassicons();
								}catch(Throwable e){
									
								}
								finish();
								System.gc();
							dialog.dismiss();
							finish();		
						}
						
					})
					.setNegativeButton("CANCEL",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							}).setCancelable(true);

			getscaledialog = builder.create();
			getscaledialog.show();

		}
		public String dochecks(){
			String passed="";
			//Checks
			String CHECKduplicatetempsensors="- The following Temperature Sensor(s) is duplicated";
			String LISTduplicatetempsensors = "";
			
			String CHECKfewbmsinfo="- You have indicated less than two BMS on site";
			
			String CHECKnogatewayserver="- You have not indicated a location for a gateway server";
			
			String CHECKethernetportcountsdonotmatchcomponents="- You have not indicated ethernet access for all network required components";
			String LISTethernetportcountsdonotmatchcomponents = "";
			
			String CHECKemptyelc="- The following ELC(s) has no added components:";
			String LISTemptyelc= "";
			
			String CHECKnolegend="- You are missing a legend on the following floor plans";
			String LISTnolegend= "";
			
			String CHECKgapintempsensornumbering="- You have a gap in your Temperature Sensor numbering:";
			String LISTgapintempsensornumbering= "";
			
			String CHECKmissingimages="- You are missing pictures for the following items:";
			String LISTmissingimages= "";

//checkduplicatetemps			
			List<String> templist = new ArrayList<String>();
			int bmscount=0;
			int gwscount=0;
			int ethernetportcount=0;
			int ethernetcomponentcount=0;
			
			boolean[] floorplanhaslegend=new boolean[floorplancount];
			for(int h=0; h<floorplancount; h++){
				floorplanhaslegend[h]=false;
			}
			List<String> floorplanswoutlegend = new ArrayList<String>();
			
			boolean[] elcmissingsams=new boolean[view.maxitems];
			boolean[] elcmissingtemps=new boolean[view.maxitems];
			
			for(int h=1; h<view.ELCCOUNT+1; h++){
				elcmissingsams[h]=true;
				elcmissingtemps[h]=true;
			}
			
			List<String> elcswithoutattachments= new ArrayList<String>();
			
			for(int x=0; x<view.i; x++){
				if (view.ITEMtype[x]==view.TYPE_TEMPSENSOR){
					templist.add("Temperature Sensor "+view.ITEMmasterelcnumber[x]+"."+view.ITEMdisplaynumber[x]);
					
					elcmissingtemps[view.ITEMmasterelcnumber[x]]=false;
				}
				if (view.ITEMtype[x]==view.TYPE_BMS){
					bmscount++;
				}
				if (view.ITEMtype[x]==view.TYPE_GATEWAY){
					gwscount++;
					ethernetcomponentcount++;
				}
				if (view.ITEMtype[x]==view.TYPE_ELC){
					
					ethernetcomponentcount++;
					if(view.ITEMsamscount[x]>0){
						elcmissingsams[view.ELCdisplaynumber[x]]=false;
					}
					
					
				}
				if (view.ITEMtype[x]==view.TYPE_ETHERNETPORT){
					ethernetportcount++;
				}
				if (view.ITEMtype[x]==view.TYPE_LEGEND1||
						view.ITEMtype[x]==view.TYPE_LEGEND2||
						view.ITEMtype[x]==view.TYPE_LEGEND3||
						view.ITEMtype[x]==view.TYPE_LEGEND4){
					floorplanhaslegend[view.ITEMfloorplannumber[x]]=true;
				}
				
				
				
				
			}
//count number of duplicate temps			
			Set<String> uniqueSet = new HashSet<String>(templist);
			for (String temp : uniqueSet) {
				if(Collections.frequency(templist, temp)>1){
					LISTduplicatetempsensors=LISTduplicatetempsensors+temp+"\n";
				}
			}
			
			if(LISTduplicatetempsensors.length()==0){
				CHECKduplicatetempsensors=passed;
			}
			
//CHECKfewbmsinfo
			if(bmscount>1){
				CHECKfewbmsinfo=passed;
			}
//CHECKnogatewayserver	
			if(gwscount>0){
				CHECKnogatewayserver=passed;
			}

//CHECKnolegend	
			if(!NGBICONS){
				for(int h=0; h<floorplancount; h++){
					if(!floorplanhaslegend[h]){
						floorplanswoutlegend.add(Tabs1.sitefptextview[h].getText().toString());
					}
				}
				for (String fp : floorplanswoutlegend) {
						LISTnolegend=LISTnolegend+fp+"\n";
				}
				
				if(LISTnolegend.trim().length()==0){
					CHECKnolegend=passed;
				}
			}else{
				CHECKnolegend=passed;
			}
			
//CHECKethernetportcountsdonotmatchcomponents			
			if(ethernetportcount>=ethernetcomponentcount){
				CHECKethernetportcountsdonotmatchcomponents=passed;
			}
//CHECKemptyelc
			for(int h=1; h<view.ELCCOUNT+1; h++){
				if(elcmissingsams[h]&&elcmissingtemps[h]){
					elcswithoutattachments.add("ELC "+(h));
				}
				
			}
			for (String elc : elcswithoutattachments) {
				LISTemptyelc=LISTemptyelc+elc+"\n";
			}
			if(LISTemptyelc.trim().length()==0){
				CHECKemptyelc=passed;
			}

//CHECKgapintempsensornumbering
			view.templistorderedbyitemnumber=view.getorderedtemplist();
			
			for(int h=0; h<view.templistorderedbyitemnumber.length-1; h++){
				
				int itemnum=view.templistorderedbyitemnumber[h];
				int nextitemnum=view.templistorderedbyitemnumber[h+1];
				
				if(view.ITEMdisplaynumber[nextitemnum]-view.ITEMdisplaynumber[itemnum]!=1){
					//if(view.ITEMmasterelcnumber[nextitemnum]-view.ITEMmasterelcnumber[itemnum]!=1){
					if(view.ITEMmasterelcnumber[nextitemnum]==view.ITEMmasterelcnumber[itemnum]){
						String current=u.s(view.ITEMmasterelcnumber[itemnum]) + "."+ u.s(view.ITEMdisplaynumber[itemnum]);
						String next=u.s(view.ITEMmasterelcnumber[nextitemnum]) + "."+ u.s(view.ITEMdisplaynumber[nextitemnum]);
						String string="Temp Sensor "+current+" and "+next;
						LISTgapintempsensornumbering=LISTgapintempsensornumbering+string+"\n";
					}
						
					//}
				}
			}
			if(LISTgapintempsensornumbering.trim().length()==0){
				CHECKgapintempsensornumbering=passed;
			}
			
//Check missing images
			CHECKmissingimages=passed;		
			
			String checksstring=""+
			 CHECKduplicatetempsensors+"\n"+
			 LISTduplicatetempsensors+"\n\n"+
			 CHECKfewbmsinfo+"\n\n"+
			 CHECKnogatewayserver+"\n\n"+
			 CHECKethernetportcountsdonotmatchcomponents+"\n"+
			 LISTethernetportcountsdonotmatchcomponents+"\n\n"+
			 CHECKemptyelc+"\n"+
			 LISTemptyelc+"\n\n"+
			 CHECKnolegend+"\n"+
			 LISTnolegend+"\n\n"+
			 CHECKgapintempsensornumbering+"\n"+
			 LISTgapintempsensornumbering+"\n\n"+
			 CHECKmissingimages+"\n"+
			 LISTmissingimages;
			return checksstring;
		}
		public void refreshcomponentimagesontabs(){
			List<Integer> rowstoedit= rowstoeditontabs;
		
			String tab="component";
			for(int x: rowstoedit){
				List<Integer> itemstoedit= itemstoeditperrowontabs[x];
				
				for(int y: itemstoedit){
					try{
						Tabs1.refresh1pic(Tabs1.COMPONENTSTAB, x, y);
					}catch(Throwable e){
						
					}
				}
			}
			
			
			
			
		}
		public static void addimagetorefreshlist(int rowselected, int numberinrowselected){
			boolean rowstoeditontabsalreadyinlist=false;
			if(rowstoeditontabs==null){
				rowstoeditontabs=new ArrayList<Integer>();
			}else{
				for(int j=0; j<rowstoeditontabs.size();j++){
					if(rowstoeditontabs.get(j)==rowselected){
						rowstoeditontabsalreadyinlist=true;
						break;
					}
				}
			}
			if(rowstoeditontabsalreadyinlist){
				//do nothing
			}else{
				rowstoeditontabs.add(rowselected);
			}
			if(itemstoeditperrowontabs[rowselected]==null){
				itemstoeditperrowontabs[rowselected]=new ArrayList<Integer>();
			}
			itemstoeditperrowontabs[rowselected].add(numberinrowselected);
		}
		public void turnoffscaling(){
			//allow clicking through overlay with icons
			view.setOnTouchListener(null);
			//remove icons
			for (int w = 0; w < view.i; w++) {
				rl.removeView(itemimageview[w]);
			}
			//indicate setting change
			ICONSCALING = false;
			//refresh
			view.invalidate();
		}
		public void turnonscaling(){

			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub

					for (int w = 0; w < view.maxitems; w++) {
						try {
							rl.removeView(itemimageview[w]);
						} catch (NullPointerException e) {

						}
					}
					for (int w = 0; w < view.i; w++) {

						int x = (int) view.SCALEDITEMx[w], y = (int) view.SCALEDITEMy[w];
						float dx = (float) ((float) view.ITEMbitmap[w]
								.getWidth() / (float) 2.0);
						float dy = (float) ((float) view.ITEMbitmap[w]
								.getHeight() / (float) 2.0);

						x = x - (int) dx;
						y = y - (int) dy;

						if (ICONBOUNDING){
							itemimageview[w] = new ImageView(
									FloorPlanActivity.this);
							itemimageview[w]
									.setImageBitmap(view.ITEMbitmap[w]);

							Log.d("x and y", x + " " + y);
							LayoutParams mparam = new LayoutParams(
									(int) (LayoutParams.WRAP_CONTENT),
									(int) (LayoutParams.WRAP_CONTENT));

							Log.d("itemimageview, wdith, height",
									view.ITEMbitmap[w].getWidth()
											+ " "
											+ view.ITEMbitmap[w]
													.getHeight()
											+ " "
											+ (float) view.ITEMbitmap[w]
													.getWidth() / (float) 2.0);

							Log.d("dx, and dy", dx + " " + dy);
							Log.d("x, and y", x + " " + y);
							itemimageview[w].setMinimumHeight(view.ITEMbitmap[w].getHeight());
							itemimageview[w].setMinimumWidth(view.ITEMbitmap[w].getWidth());
							itemimageview[w].setLayoutParams(mparam);
							itemimageview[w].setPadding(x, y, 0, 0);
							rl.addView(itemimageview[w]);
						}else if(x > 0 && x < view.scrnwidth-2*dx && y > 0
								&& y < view.scrnheight-2*dy) {
							itemimageview[w] = new ImageView(
									FloorPlanActivity.this);
							itemimageview[w]
									.setImageBitmap(view.ITEMbitmap[w]);

							Log.d("x and y", x + " " + y);
							LayoutParams mparam = new LayoutParams(
									(int) (LayoutParams.WRAP_CONTENT),
									(int) (LayoutParams.WRAP_CONTENT));

							Log.d("itemimageview, wdith, height",
									view.ITEMbitmap[w].getWidth()
											+ " "
											+ view.ITEMbitmap[w]
													.getHeight()
											+ " "
											+ (float) view.ITEMbitmap[w]
													.getWidth() / (float) 2.0);

							Log.d("dx, and dy", dx + " " + dy);
							Log.d("x, and y", x + " " + y);
							itemimageview[w].setMinimumHeight(view.ITEMbitmap[w].getHeight());
							itemimageview[w].setMinimumWidth(view.ITEMbitmap[w].getWidth());
							itemimageview[w].setLayoutParams(mparam);
							itemimageview[w].setPadding(x, y, 0, 0);
							rl.addView(itemimageview[w]);
						}
						Log.d("w", u.s(w));
					}

					return false;
				}
			});

			ICONSCALING = true;
			view.invalidate();
		
		}
		public void executegetpicfromcameraresult(int typeselected, String foldername, long picturestarttime, long picturestoptime){
			if ((typeselected==Tabs1.SITETAB)|| (typeselected==Tabs1.COMPONENTSTAB)||(typeselected==Tabs1.ASSETSTAB)) {
			u.log("");
			processpendingimagecapture(typeselected, foldername, picturestarttime, picturestoptime);
				
			picturestarttime=-1;
			picturestoptime=-1;
			Tabs1.set("picturestarttime", u.sl(picturestarttime));
			Tabs1.set("picturestoptime", u.sl(picturestoptime));
			
			}
		}
		public void processpendingimagecapture(int typeselected, String foldername, long picturestarttime, long picturestoptime){
				File dcimlocationfile=new File(Tabs1.dcimlocationstring);
				if(picturestoptime==-1){
					picturestoptime=System.currentTimeMillis();
				}
				u.log("pictuerstarttime,picturestoptime "+picturestarttime+" "+picturestoptime);
				System.out.println(picturestarttime);
				System.out.println(picturestoptime);
				
				
				try{
					for(File file:dcimlocationfile.listFiles()){
						u.log(file.lastModified());
						u.log(String.valueOf(file.lastModified()>picturestarttime)+" "+String.valueOf(file.lastModified()<picturestoptime));
						if(file.lastModified()>picturestarttime&&file.lastModified()<picturestoptime){
							String destinationpath = null;
							File parent;
							switch(typeselected){
								case Tabs1.SITETAB:
								parent=new File(Tabs1.ProjectDirectory + "/"+ Tabs1.basedirectory[Tabs1.SITEINFOFOLDER]+"/"+foldername);
								parent.mkdirs();
								destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
							break;
							case Tabs1.COMPONENTSTAB:
								parent=new File(Tabs1.ProjectDirectory + "/"+ Tabs1.basedirectory[Tabs1.COMPONENTSFOLDER]+"/"+foldername);
								parent.mkdirs();
								destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
								break;
							case Tabs1.ASSETSTAB:
								parent=new File(Tabs1.ProjectDirectory + "/"+ Tabs1.basedirectory[Tabs1.ASSETSFOLDER]+"/"+foldername);
								parent.mkdirs();
								destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
								break;
							}
							
							File destFile=new File(destinationpath);
							u.log(file.getAbsolutePath()+" "+destFile.getAbsolutePath());
							try {
								u.copyFile(file, destFile);
								file.delete();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						}
					}
				}catch(Throwable e){
					e.printStackTrace();
					showToast("Your images did not save properly into the project, your DCIM directory may not set properly. Please locate this directory and modify it's location in settings.");
				}
			}
		
}

