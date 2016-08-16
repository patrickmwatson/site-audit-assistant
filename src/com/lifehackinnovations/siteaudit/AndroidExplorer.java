package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;





import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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

public class AndroidExplorer extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root="/";
	private TextView myPath;

	public Context ctx;

	private SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	private SharedPreferences.Editor editor;

	private String masterfoldername;
   //preference variables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidexplorer);
        ctx=this;
        getPreferences();
        root=Environment.getExternalStorageDirectory().toString()+"/"+masterfoldername+"/";
        if(MainActivity.fromdrive){
        	root=Environment.getExternalStorageDirectory().toString()+"/My SugarSync Folders/My SugarSync/"+masterfoldername+"/";
        }
        myPath = (TextView)findViewById(R.id.path);
        getstorageready();
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
    	myPath.setText("Please select a project");
    	
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
    	
    	Log.d("!!!!!!!!!!!!!!!!!",f.toString());
    	if(!dirPath.equals(root))
    	{

    		item.add(root);
    		path.add(root);
    		
    		item.add("../");
    		path.add(f.getParent());
            
    	}
    	if (!(files.length==0)){
    		for(int i=0; i < files.length; i++){
	    			File file = files[i];
	    			if(file.isDirectory()){
	    				path.add(file.getPath());
	    			}
	    			if(file.isDirectory()){
	    				if(!(file.getName().equals("docs"))){
	    					item.add(file.getName());
	    				}
    				
    				}
	    	}
    	}
    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.androidexplorerrow, item);
    	setListAdapter(fileList);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		File file = new File(path.get(position));
		
		for(int i=0; i<path.size(); i++){
			Log.d("5/23", path.get(i));
		}
		
		for(int i=0; i<item.size(); i++){
			Log.d("5/23", item.get(i));
		}
		
		set("foldername",file.getName());
		
		Log.d("5/23","foldername="+file.getName());
		if(MainActivity.fromdrive){
			copyfilesfromdrive(((TextView)v).getText().toString());
		}
		
		startActivity(u.intent("Tabs1"));
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
	 
	
	 
	 public void copyfilesfromdrive(String projectname){
		 File currentprojectdirectory = new File(Environment.getExternalStorageDirectory().toString()+"/My SugarSync Folders/My SugarSync/lhi/"+projectname);
			//if(u.isbluestacks()){
			//	Log.d("bluestacks","true");
			//	set("masterfoldername","bstfolder/BstSharedFolder/lhi");
			//}else{
			//	Log.d("notbluestacks","true");
			Log.d("copydirectory started","Sugar Sync's");
			File movetolocation=new File(Environment.getExternalStorageDirectory().toString()+"/"+masterfoldername+"/"+projectname);
			try {
				u.copyDirectory_Cloud(currentprojectdirectory, movetolocation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}