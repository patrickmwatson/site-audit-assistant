package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.lifehackinnovations.siteaudit.Tabs1.LoadTask;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class choosefolderfirst extends Activity {
    /** Called when the activity is first created. */
	//for preferences
		

		private String foldername;

        public static Boolean newproject=false;	
		private SharedPreferences settings;
		private String PREFS_NAME = "Preferences";
		private SharedPreferences.Editor editor;
		private String extStorageDirectory;
		private String masterfoldername;
	   //preference variables
		
	  //  private String foldername;
		static String[] M395storesbyCONVENTION;
	   
		private AlertDialog.Builder ad;
		private AlertDialog alertdialog;
		private AlertDialog.Builder ad1;
		private AlertDialog alertdialog1;
	    private ProgressDialog progressDialog;
		
	    private AutoCompleteTextView choosefoldernametext;
		
	    															//NO ERRORS WHEN BACKING OUT AND GOING BACK IN-----------------	    
	    private Activity ACTIVITY;
	    private PendingIntent RESTART_INTENT;
	    ArrayAdapter<String> M395storenamesadapter;
														//-----------------------------------------------------------
	    Context ctx;
	    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ctx=this;
        
        LoadM395conventionnames();
        		
		progressDialog=new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
    	progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_icon_drawable_animation));
    	progressDialog.setIcon(R.drawable.ic_launcher);
    	progressDialog.setTitle("Loading");
    	progressDialog.setMessage("Please Wait");
    	progressDialog.setCancelable(false);
		
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        getPreferences();
		
        
        LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.choosefolder, null);
		choosefoldernametext= (AutoCompleteTextView)textEntryView.findViewById(R.id.choosefoldertext);
		choosefoldernametext.setAdapter(M395storenamesadapter);
		choosefoldernametext.setOnEditorActionListener(new OnEditorActionListener() {
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
	            	alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
	            }    
	            return false;
	        }
	    });
		
		ad1 = new AlertDialog.Builder(this)
        .setCancelable(false)
        .setTitle("Folder Already Exists")
         .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	finish();
     
            	
            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	finish();
            	startActivity(u.intent("choosefolderfirst"));
            	
            	
            }
        });
        ad1.create();
		
		 ad = new AlertDialog.Builder(this)
       
		 .setCancelable(false)
         .setTitle("Name this Site Audit")
         .setView(textEntryView)
         .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	finish();
     
            	
            }
        })
         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
            	
            	File project = new File(extStorageDirectory+"/"+masterfoldername+"/"+choosefoldernametext.getText().toString()+"/"); 
            	if (project.exists()){
            		ad1.show();
            		
            		
            	}else{
            	
            	newproject=true;	
            	new LoadTask(progressDialog).execute();	
            	
             	
            	}
             }
         });
        

		alertdialog=ad.create();
		
        alertdialog.show();
        
       
  
		

    }
	
	
																						//NO ERRORS WHEN BACKING OUT AND GOING BACK IN-----------------	 
	
	 																					//-----------------------------------------------------------
    public void getPreferences(){
    	settings = getSharedPreferences(PREFS_NAME, 0);
    	foldername=settings.getString("foldername",null);
    	System.out.println("foldername in choosefolderfirst"+foldername);
    	masterfoldername=settings.getString("masterfoldername", null);
    
    }
    public void set(String prefname, String value){
    	settings = getSharedPreferences(PREFS_NAME, 0);
    	editor=settings.edit();
    	editor.putString(prefname, value);
    	editor.commit();
    }
    public void getstorageready(){
    	String foldername=getSharedPreferences("Preferences",0).getString("foldername", null);
    	Log.d("myapp","asdfasdf"+foldername);
    	String msg;
        try {
            String theState = Environment.getExternalStorageState();
            if (theState.equals(Environment.MEDIA_MOUNTED)) {
                File theBasedir = Environment.getExternalStorageDirectory();
                File theMasdir = new File(theBasedir, masterfoldername);
                theMasdir.mkdirs();
                File theSubdir = new File(theMasdir, foldername);
                theSubdir.mkdirs();
                msg = theSubdir.exists() ? "Success" : "Fail";
            } else {
                msg = "Invalid State";
                Log.d("myapp"," msg = Invalid State;");
            }
        } catch (Exception e) {
            msg = "Error - " + e;
            Log.d("myapp","msg = Error -  + e;");
        }
      
        
    }
    public void setDefaultPreferences(){
    	settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	//first check if defaults have been set
    	
    	//if(u.isbluestacks()){
		//	Log.d("bluestacks","true");
		//	set("masterfoldername","bstfolder/BstSharedFolder/lhi");
		//}else{
		//	Log.d("notbluestacks","true");
			set("masterfoldername","lhi");
		//}
		set("foldername","My Project");
		set("seriesname", "image");
	
    }
    protected class LoadTask extends AsyncTask<Object, Void, String>
    {
	 	 
		 ProgressDialog progressDialog;
		 String filenamestring;
		 public LoadTask(ProgressDialog progressDialog){
			 this.filenamestring=filenamestring;
			 this.progressDialog=progressDialog;
		 }
		   
		
	
                @Override
                protected String doInBackground( Object... params ) 
                {
                	
                	setDefaultPreferences();
                	set("foldername",choosefoldernametext.getText().toString());
                    getPreferences();
                    getstorageready();
                //    startActivity(u.intent("Writepreferencesfile"));
                	Log.d("myapp", extStorageDirectory+"/"+masterfoldername+"/"+foldername);
                     /* User clicked OK so do some stuff */
                	startActivity(u.intent("Tabs1"));
                	finish();
                	
					return null;
                }
                
                // -- gets called just before thread begins
                @Override
                protected void onPreExecute() 
                {
                	
                	 progressDialog.show();
                	
                }
         
                @Override
                protected void onPostExecute( String result ) 
                {
                      progressDialog.dismiss();
                     
                }
    }    
    public void LoadM395conventionnames(){
    	DatabaseHandler masterdb = new DatabaseHandler(this, DatabaseHandler.MASTER_DATABASE);
    	
    	String[] titles;
    	if (masterdb.tableexists(DatabaseHandler.TABLE_M395)){
    	
    		titles=masterdb.gettitles(DatabaseHandler.TABLE_M395);
    	}else{
    		masterdb.createmastertables();
    		titles=masterdb.gettitles(DatabaseHandler.TABLE_M395);
    	}
        ArrayList<String> M395storenumbers;
  	  	ArrayList<String> M395storenames;
        
  	  	int storenumbercolumnoncsv=1;
  	  	int storenamecolumnoncsv=3;
  	  	
  	  	M395storenumbers=masterdb.getcolumn(masterdb.TABLE_M395, storenumbercolumnoncsv, titles);
        M395storenames=masterdb.getcolumn(masterdb.TABLE_M395, storenamecolumnoncsv, titles);
        
        M395storesbyCONVENTION=new String[M395storenames.size()];
        for (int h=0; h<M395storenames.size(); h++){
        	 M395storesbyCONVENTION[h]=M395storenumbers.get(h)+" "+M395storenames.get(h);
        }
        
        M395storenamesadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, M395storesbyCONVENTION);
	}
   
}
