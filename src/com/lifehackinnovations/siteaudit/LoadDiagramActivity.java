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
import java.util.Calendar;
import java.util.Currency;
import java.util.List;


import com.lifehackinnovations.siteaudit.FloorPlanActivity.SaveTask;
import com.lifehackinnovations.siteaudit.Tabs1.LoadTask;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class LoadDiagramActivity extends Activity {
	/** Called when the activity is first created. */
	
	int PREFS=1;
	
	static int ACTION_preferences = 1;

	private ImageView preferences;
	
	private View toptoolbar;

	static RelativeLayout rl;

	static LoadDiagramView view;

	static TextView LoadDiagramtitle;

	static String DefaultTextSize="25";
	
	View toolbar;

	private static Looper looper;
	
	protected ProgressDialog progressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		if (Tabs1.foldername.equals("Paul")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.paul_animation));

		} else if (Tabs1.foldername.equals("Will")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.will_animation));

		}else if (Tabs1.foldername.equals("Bill")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.bill_animation));

		} else {
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
		toolbar = inflater.inflate(R.layout.loaddiagramtoolbar, null);

		toptoolbar = (View) toolbar.findViewById(R.id.toptoolbar);
		

	
		preferences = (ImageView) toolbar.findViewById(R.id.preferences);

		LoadDiagramtitle = (TextView) toolbar.findViewById(R.id.Riserdiagramtitle);
		LoadDiagramtitle.setText("Load Diagram");

	
		preferences.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
			
				// startActivityForResult(u.intent("LoadDiagramPrefs"),PREFS);
			}
		});

	

		view = new LoadDiagramView(this);
		view.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		rl.addView(view);
		rl.addView(toolbar);

		setContentView(rl);

	}

	

	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			new SaveTask(progressDialog).execute();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
	    	
	    	
			if (requestCode == PREFS) {
			
				view.refreshDrawableState();
			}
	    }
	    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
		    Paint paint = new Paint();
		    paint.setTextSize(textSize);
		    paint.setColor(textColor);
		    //paint.setTextAlign(Paint.Align.CENTER);
		    Log.d("AddText",text);
		    int width = (int) (paint.measureText(text) + 0.5f); // round
		    float baseline = paint.ascent() + 0.5f;
		    int height = (int) (textSize+textSize);
		    
		    Log.d("Width & Height", width +"  "+height+"  "+baseline);
		    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		    Canvas canvas = new Canvas(image);
		    canvas.drawText(text, 0, image.getHeight()-((float)1.25*(float)textSize), paint);
		    return image;
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

				Canvas canvascopy = new Canvas();
				
				Bitmap canvasbitmap=resizedbitmaponmemorycheck(LoadDiagramView.bitright,LoadDiagramView.bitbottom);
				canvascopy.setBitmap(canvasbitmap);

				LoadDiagramView.DONTSCALEORTRANSLATE = true;
				LoadDiagramView.CanvasChanges(canvascopy);
				LoadDiagramView.DONTSCALEORTRANSLATE = false;
		
				File file = new File(LoadDiagramView.OutputBitmapPath);
				file.mkdirs();
				if (file.exists())
					file.delete();
				Log.d("outputpath=",LoadDiagramView.OutputBitmapPath);
				
				FileOutputStream fos = new FileOutputStream(
						new File(LoadDiagramView.OutputBitmapPath));

				canvasbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				try {
					fos.flush();
					fos.close();
					fos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				canvasbitmap.recycle();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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

			}

			@Override
			protected void onPostExecute(String result) {
				//progressDialog.dismiss();

			}
		}
public Bitmap resizedbitmaponmemorycheck(int width, int height){
			
			
			
			final Runtime rt = Runtime.getRuntime();
			Log.d("free memory, maxmemory, total memory",rt.freeMemory()+" "+rt.maxMemory()+" "+rt.totalMemory());
			final long freeMem = rt.freeMemory() + (rt.maxMemory() - rt.totalMemory());
			double ratio=0.50;
			
			
			Bitmap canvasbitmap;
			//canvasbitmap = Bitmap.createBitmap(width, height,
			//		Bitmap.Config.ARGB_8888);
			Log.d("width and height",u.s(width)+" "+u.s(height));
			
			
			
			if (!u.isbluestacks()) { 
				Log.d("not bluestacks","true");
				canvasbitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				
			}else {
				
				int FINALBITMAPSCALE=4;
				Log.d("bluestacks","true");
				canvasbitmap = Bitmap.createBitmap(width/FINALBITMAPSCALE, height/FINALBITMAPSCALE,
						Bitmap.Config.ARGB_8888);
				
			}
			
			return canvasbitmap;
		}
}
