package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;





import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class deletefolder extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root="/";
	private TextView myPath;
	private String foldername;
	private String count;
	private String seriesname;
	private String password;
	private String bitmaptablename;
	private String numosupportedpicturesizes;
	private String trialexpired;
	private String picturewidth;
	private String pictureheight;
	private String freezefirstframe;
	private String snapinterval;
	private String thumbnailreduction;

	private SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	private SharedPreferences.Editor editor;
	private String extStorageDirectory;
	private String masterfoldername;
	private AlertDialog alert;
   //preference variables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidexplorer);
        getPreferences();
        root=Environment.getExternalStorageDirectory().toString()+"/"+masterfoldername+"/";
        
        myPath = (TextView)findViewById(R.id.path);
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
    	myPath.setText("Please select a project to delete");
    	
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	try{
    		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
    	}catch(Throwable e){
    		
    	}
    	
    	if(!dirPath.equals(root))
    	{

    		item.add(root);
    		path.add(root);
    		
    		item.add("../");
    		path.add(f.getParent());
            
    	}
    	
    	try{
	    	for(int i=0; i < files.length; i++)
	    	{
	    		File file = files[i];
	    		if (file.isDirectory()){
		    		path.add(file.getPath());
		    		item.add(file.getName());
	    		}
	    	}
    	}catch(Throwable e){
    		
    	}
    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.androidexplorerrow, item);
    	setListAdapter(fileList);
    	
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		final File file = new File(path.get(position));
		final String currentfoldername = item.get(position);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this site audit?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   getPreferences();
                	   u.deleteDirectory(file);
                	   deletedatabase(currentfoldername);
                	   getDir(root);
                	   alert.dismiss();
                	   
                	 
                   } 
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert.cancel();
                   }
               });
        alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener(){

            @Override
            public void onShow(DialogInterface dialog) {

                Button positive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setFocusable(true);
                positive.setFocusableInTouchMode(true);
                positive.requestFocus();
            }
        });
    	//CustomizeDialog customizeDialog = new CustomizeDialog(this);
        alert.show();
        
        //this.finish();
        
		
		
     	
	}
	 public void getPreferences(){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	foldername=settings.getString("foldername",null);
	    	masterfoldername=settings.getString("masterfoldername", null);
	    	seriesname=settings.getString("seriesname",null);
	    	count=settings.getString("count", null);
	    	
	    }
	 public void getstorageready(){
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
	 public void set(String prefname, String value){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	editor=settings.edit();
	    	editor.putString(prefname, value);
	    	editor.commit();
	    }
	
	 public void deletedatabase(String dbname){
		 try {
	            File sd = Environment.getExternalStorageDirectory();
	            File data = Environment.getDataDirectory();
	          
	            if (sd.canWrite()) {
	                String  currentDBPath= "//data//" +this.getPackageName()
	                        + "//databases//" + dbname;
	               
	                File currentDB = new File(data, currentDBPath);
	                if(currentDB.exists()){
	                	currentDB.delete();
	                }	                
	                Log.d("databasedelete",currentDB.toString());
	            }
	        } catch (Exception e) {
	        	 e.printStackTrace();

	        }
	 }
	 
}