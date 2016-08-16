package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;





import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidExplorerGivenFolder extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root="/";
	private TextView myPath;


	private SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	private SharedPreferences.Editor editor;
	private String folderpath;
	private String masterfoldername;
   //preference variables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidexplorer);
        Intent intent = getIntent();
        folderpath = intent.getExtras().getString("folder");
        
        getPreferences();
        root=folderpath;
        
        myPath = (TextView)findViewById(R.id.path);
        getstorageready();
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
    	myPath.setText("Please Select a File");
    	
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	Log.d("!!!!!!!!!!!!!!!!!",f.toString());
    	if(!dirPath.equals(root))
    	{

    		item.add(root);
    		path.add(root);
    		
    		item.add("../");
    		path.add(f.getParent());
            
    	}
    	try{
	    	if (!(files.length==0)){
	    		for(int i=0; i < files.length; i++){
		    			File file = files[i];
		    			path.add(file.getPath());
		    			if(file.isDirectory()){
		    				if(!(file.getName().equals("docs"))){
		    					item.add(file.getName());
		    				}
	    				}else{
	    					
	    					if(!(file.getName().equals("docs"))){
		    					item.add(file.getName());
		    				}
	    				}
		    	}
	    	}
    	}catch(NullPointerException e){
    		
    		Toast.makeText(this, "No Files!",Toast.LENGTH_SHORT).show();
    		this.finish();
    	}
    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.androidexplorerrow, item);
    	setListAdapter(fileList);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent=new Intent();
		intent.putExtra("RESULT_STRING", folderpath+"/"+((TextView)v).getText().toString());
		setResult(RESULT_OK, intent);
		finish();
		
     	
	}
	 public void getPreferences(){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	masterfoldername=settings.getString("masterfoldername", "lhi");
	    }
	 public void getstorageready(){
	    	String msg;
	        try {
	            String theState = Environment.getExternalStorageState();
	            if (theState.equals(Environment.MEDIA_MOUNTED)) {
	                File theBasedir = Environment.getExternalStorageDirectory();
	                File theMasdir = new File(theBasedir, masterfoldername);
	                theMasdir.mkdirs();
	                
	                msg = theMasdir.exists() ? "Success" : "Fail";
	            } else {
	                msg = "Invalid State";
	                Log.d("myapp"," msg = Invalid State;");
	            }
	        } catch (Exception e) {
	            msg = "Error - " + e;
	            Log.d("myapp","msg = Error -  + e;");
	        }
	      
	        
	    }
	 public void set(String prefname, String value){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	editor=settings.edit();
	    	editor.putString(prefname, value);
	    	editor.commit();
	    }
}