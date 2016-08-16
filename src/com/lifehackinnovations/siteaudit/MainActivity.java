package com.lifehackinnovations.siteaudit;



import java.io.BufferedReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildList;
//import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;







import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;



public class MainActivity extends Activity {
	
	
	final int INSTALLMISSINGAPP=0;
	final int UNZIP=1;
	protected ProgressDialog progressDialog; 
	
	String tempapkpath;
	File temperaryapklocation;
	static  String PREFS_NAME = "Preferences";
	
	  static final int REQUEST_ACCOUNT_PICKER = 1;
	  static final int REQUEST_AUTHORIZATION = 2;
	  static final int CAPTURE_IMAGE = 3;
	  
	  static String CurrentAppVersion="1";
	  static ArrayList<String> AppsPros = new ArrayList<String>();
	  static ArrayList<String> AppsCons = new ArrayList<String>();
	  public static Boolean fromdrive=false;
	  
	  public boolean installtexportalfiles=false;
	  
	  static String[] updatestrings;
	  static String[] updatedetailsstrings;
	  static String Avaialableupdate="null";
	  static String[] additionalavaialableapps;
	  
	  static String ELUTIONSPROJECTFOLDERPATH;
	  static String SUGARSYNCELUTIONSFOLDERPATH;
	  static String UPDATESFOLDERPATH;
	  static String ELUTIONSCONFIGFOLDERPATH;
	  static String ADDITIONALAPPSFOLDERPATH;
	  
	  static String FOLDERPATH;
	  static String INTERNALDATABASEPATH;
	  static String EXTERNALDATABASEPATH;
	  static String SUGARSYNCFOLDERPATH;
	  
	  static String FOLDERNAME;
	  static String CheckforAutoUpdate="check";
	  
	  
	  int PREFS=1;
	
	 

	  static Context ctx;
	  
	  
	  LayoutParams lpfw = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
	  
	private AlertDialog pd;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;
        
        
        String mainactivityhasbeenranbefore=new String();
        mainactivityhasbeenranbefore="false";
    	try{
    		mainactivityhasbeenranbefore=(String)u.get(PREFS_NAME,"mainranbefore", ctx);
    		System.out.println("try succeeded");
    		if(mainactivityhasbeenranbefore==null){
    			mainactivityhasbeenranbefore="false";
    		}
    	}catch(Throwable e){
    		System.out.println("try failed");
    		mainactivityhasbeenranbefore="false";
    	}
    	if(!(mainactivityhasbeenranbefore.equals("true"))){
    		setDefaultPreferences();
    	}
    	getPreferences();
       
        
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = pInfo.versionName;
	        this.setTitle("Life Hack Innovations Site Audit Assistant V"+version);   
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        progressDialog=new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
    	progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_icon_drawable_animation));
    	progressDialog.setIcon(R.drawable.ic_launcher);
    	progressDialog.setTitle("Loading");
    	progressDialog.setMessage("Please Wait");
    	progressDialog.setCancelable(false);
       
      
        
     
        
        ImageView startnew = (ImageView)findViewById(R.id.newsite);
        startnew.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				startActivity(u.intent("choosefolderfirst"));
   				//do something
   			}});
        ImageView openold = (ImageView)findViewById(R.id.oldsite);
        openold.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				openfromdialog();
   				//do something
   			}});
        ImageView delete = (ImageView)findViewById(R.id.deletesite);
        delete.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				startActivity(u.intent("deletefolder"));
   				//do something
   			}});
        
        ImageView update = (ImageView)findViewById(R.id.update);
        update.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   			   AppsPros = new ArrayList<String>();
   			   AppsCons = new ArrayList<String>();
   				
   			    currentversiondetails();
   				currentversiondialogue();
   				//do something
   			}});
        
        ImageView instructions = (ImageView)findViewById(R.id.instructions);
        instructions.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   			   showinstructionsmenu();
   				
   			}});
        ImageView additional = (ImageView)findViewById(R.id.additional);
        additional.setOnClickListener(new ImageView.OnClickListener(){

   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				additionalapps();
   				
   			}});
       
        try{ 
	        currentversiondetails();
	        checkfordifferentversion();
        }catch(Throwable e){
        	
        }
        
        try{ 
	        if(CheckforAutoUpdate.equals("check")){
	        	checkforupdate();
        }
        }catch(Throwable e){
        	try{
	        	currentversiondetails();
	        	checkforupdate();
        	}catch(Throwable e1){
        		
        	}
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
    	
    	

		LayoutParams warningfieldlayoutparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		new LayoutParams(500,
				677);

		TableLayout warningalertlayout = new TableLayout(this);
		warningalertlayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// mopdesignlayout.setBackgroundColor(Color.parseColor(darkblue));
		warningalertlayout.setPadding(2, 2, 2, 2);
		
    	String label = null;
    	Boolean showwarning=false;
    	
    	switch (item.getItemId()) {
 
        case R.id.menu_settings:
            startActivityForResult(u.intent("MainActivityPrefs"),PREFS);
            showwarning=false;
            break;
 
        
	    case R.id.menu_cleardata:
	    	label="Clear database & Set default Prefs";
	    	showwarning=true;
	        break;
	
	    
		case R.id.menu_restoredefaults:
			label="Clear prefs & Set default Prefs";
	    	showwarning=true;
		    break;
		
		case R.id.menu_resetapprename:
			label="Clear everything,Set default Prefs & rename(store projects separately)";
	    	showwarning=true;
			
			break;
		case R.id.menu_resetapp:
			label="Clear everything,Set default Prefs & delete all projects";
	    	showwarning=true;
			break;
		    
		case R.id.menu_uninstall:
			label="Uninstall the app";
	    	showwarning=true;
		    break;
		    
		case R.id.menu_fulluninstall:
			label="Uninstall the app and delete all Project folders";
	    	showwarning=true;
		    break;    
		case R.id.menu_help:
			System.out.println("help clicked");
			showinstructionsmenu();
		    break;
	
        }
    	
    	AlertDialog.Builder warningalert = new AlertDialog.Builder(this);
		warningalert.setTitle("Warning: "+label);
		warningalert.setView(warningalertlayout);

		warningalert.setPositiveButton(label,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						switch (item.getItemId()) {
						 
				        case R.id.menu_settings:
				            startActivityForResult(u.intent("MainActivityPrefs"),PREFS);
				            break;
				 
				        
					    case R.id.menu_cleardata:
					    	cleareveryting();
					    	setDefaultPreferences();
					        break;
					
					    
						case R.id.menu_restoredefaults:
							clearpreferences();
							setDefaultPreferences();
						    break;
						
						case R.id.menu_resetapprename:
							cleareveryting();
							renamelhifolder();
							setDefaultPreferences();
							
							break;
						case R.id.menu_resetapp:
							cleareveryting();
							deletelhifolder();
							setDefaultPreferences();
							break;
						    
						case R.id.menu_uninstall:
							uninstall();
						    break;
						    
						case R.id.menu_fulluninstall:
							deletelhifolder();
							uninstall();
						    break;    
						case R.id.help:
							showinstructionsmenu();
						    break;
					
				        }
					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		if(showwarning){
			final AlertDialog warningalertdialog = warningalert.create();
			showwarning=false;
			warningalertdialog.show();
		}
        return true;
    }
    
 
    public static void setDefaultPreferences(){
    		System.out.println("Setting default preferences");
    	
    		u.set(PREFS_NAME,"externalstoragedirectory",(Environment.getExternalStorageDirectory().toString()),ctx);
	    	u.set(PREFS_NAME,"lhistoragedirectory","lhi",ctx);
	    	u.set(PREFS_NAME,"lhiconfigfilesstoragedirectory","Config",ctx);
	    	u.set(PREFS_NAME,"projectsstoragedirectory","Survey Documents",ctx);
	    	
	    	u.set(PREFS_NAME,"mainsugarsyncdirectory","My SugarSync Folders/My SugarSync",ctx);
	    	u.set(PREFS_NAME,"sugarsynclhiconfigfilesstoragedirectory","Config",ctx);
	    	u.set(PREFS_NAME,"sugarsyncprojectsstoragedirectory","Survey Documents",ctx);
	    	u.set(PREFS_NAME,"sugarsyncupdatedirectory","updates",ctx);
	    	u.set(PREFS_NAME,"sugarsyncsoftwaredirectory","softwares",ctx);
	    	u.set(PREFS_NAME,"updatecheck","check",ctx);
	    	u.set(PREFS_NAME,"storetype","true",ctx);
	    	
	    	u.set(PREFS_NAME,"tabimageresolutions","1",ctx);
	    	
	    	File rootsd = Environment.getExternalStorageDirectory();
			File dcim = new File(rootsd.getAbsolutePath() + "/DCIM/Camera");
			String dcimlocationstring=dcim.getAbsolutePath();
	    	u.set(PREFS_NAME,"dcimdirectory", dcimlocationstring,ctx);
	    	
	    	u.set(PREFS_NAME,"mainranbefore","true",ctx);
	    	System.out.println("ranbefore in setdefaults"+ u.get(PREFS_NAME,"mainranbefore", ctx));
	    	
    }
    
   public static void getPreferences(){
		String a=(String)u.get(PREFS_NAME,"externalstoragedirectory",ctx);
		String b=(String)u.get(PREFS_NAME,"lhistoragedirectory",ctx);
		String c=(String)u.get(PREFS_NAME,"lhiconfigfilesstoragedirectory",ctx);
		String d=(String)u.get(PREFS_NAME,"projectsstoragedirectory",ctx);
	   	
		String e=(String)u.get(PREFS_NAME,"mainsugarsyncdirectory",ctx);
		String f=(String)u.get(PREFS_NAME,"sugarsynclhiconfigfilesstoragedirectory",ctx);
		String g=(String)u.get(PREFS_NAME,"sugarsyncprojectsstoragedirectory",ctx);
		String h=(String)u.get(PREFS_NAME,"sugarsyncupdatedirectory",ctx);
		String j=(String)u.get(PREFS_NAME,"sugarsyncsoftwaredirectory",ctx);
		
		CheckforAutoUpdate =(String)u.get(PREFS_NAME,"updatecheck",ctx);
		
	   	ELUTIONSPROJECTFOLDERPATH=a+"/"+b;
		SUGARSYNCELUTIONSFOLDERPATH=a+"/"+e+"/"+g;
		UPDATESFOLDERPATH=a+"/"+e+"/"+h;
		ELUTIONSCONFIGFOLDERPATH=a+"/"+b+"/"+c;
		try{
		ADDITIONALAPPSFOLDERPATH=a+"/"+e+"/"+j;
		}catch(Throwable e1){
		ADDITIONALAPPSFOLDERPATH=a+"/"+e+"/"+"softwares";
		}
		FOLDERPATH=a+"/"+b+"/"+d+"/";
	   	INTERNALDATABASEPATH=a+"/"+b+"/"+d+"/";
	   	SUGARSYNCFOLDERPATH=a+"/"+e+"/"+g+"/";
	   	
	   	FOLDERNAME="";
		
   }
   

	
	
	public void openfromdialog(){
		
	
		ImageView fromcloud = new ImageView(this);
		fromcloud.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		fromcloud.setPadding(25, 25, 25, 25);
		fromcloud.setImageResource(R.drawable.sugarsync);
		fromcloud.setOnClickListener(new ImageView.OnClickListener(){
			public void onClick(View v) {
							
				fromdrive=true;
				startActivity(u.intent("AndroidExplorer"));
				pd.cancel();
				
				
			}
		});
		
		
		ImageView fromsdcard = new ImageView(this);
		fromsdcard.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		fromsdcard.setPadding(25, 25, 25, 25);
		fromsdcard.setImageResource(R.drawable.openfromsd);
		fromsdcard.setOnClickListener(new ImageView.OnClickListener(){
	
			public void onClick(View v) {
				Log.d("5/23","openning old project");
				fromdrive = false;
				startActivity(u.intent("AndroidExplorer"));
				pd.cancel();
			}
			
		});
		
		LinearLayout choosepicturelocationlayout;
		choosepicturelocationlayout = new LinearLayout(this);
		choosepicturelocationlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		choosepicturelocationlayout.setOrientation(LinearLayout.HORIZONTAL);
		choosepicturelocationlayout.addView(fromsdcard);
		choosepicturelocationlayout.addView(fromcloud);
		choosepicturelocationlayout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        builder.setTitle("Select Project Location")
			            .setMessage("Open Local Project, or Import Project from the Cloud")
			            .setView(choosepicturelocationlayout)
			           
			            .setIcon(R.drawable.ic_launcher)
			            
			            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                    dialog.cancel();
			                }
			            })
			            .setCancelable(true);
		
			        pd=builder.create();       	
			        pd.show();
			       
	}
	public void getappfromplay(String appName){
		u.log(appName);
		u.log(installtexportalfiles);
		if(appName.equals("com.agilesoftresource")&&installtexportalfiles){
			 try {
			     u.log("starting the activity to get androzip for result to get back to Unzip");
				 startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)),UNZIP);
			 } catch (android.content.ActivityNotFoundException anfe) {
			     startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)),UNZIP);
			 }
		 }else{
		
		
			 try {
			     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
			 } catch (android.content.ActivityNotFoundException anfe) {
			     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
			 }
		 }
	 }
	
	public void currentversiondetails(){
		try{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(
					getAssets().open("versiondetails.txt")));
			String line;
			while ((line = br1.readLine()) != null) {
				Log.d("line read from version", line);
				if(line.contains(":")){
					int Vindex=line.indexOf("_V.")+3;
					CurrentAppVersion=line.substring(Vindex, line.length());
					Log.d("Current Version",CurrentAppVersion);
				}
				if(line.contains("*")){
					AppsPros.add(line.substring(1, line.length()));
					Log.d("Pros",line.substring(1, line.length()));
				}
				if(line.contains("%")){
					AppsCons.add(line.substring(1, line.length()));
					Log.d("Cons",line.substring(1, line.length()));
				}
				
			}
		}catch(Throwable e){
			
		}
	}
	public void currentversiondialogue(){
		LayoutParams versiontextviewparams=new LayoutParams(0,LayoutParams.WRAP_CONTENT,5.0f);
		LayoutParams versiontitletextviewparams=new LayoutParams(0,LayoutParams.WRAP_CONTENT,10.0f);
		versiontitletextviewparams.height=100;
		
		TableLayout versiondetailslayout=new TableLayout(this);
		versiondetailslayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		versiondetailslayout.setPadding(2, 2, 2, 2);
		
		TableRow Proname=new TableRow(this);
		Proname.setLayoutParams(lpfw);
		
		TextView Pronameview=new TextView(this);
		Pronameview.setLayoutParams(versiontitletextviewparams);
		Pronameview.setText("Added Features in this Version");
		Proname.addView(Pronameview);
		versiondetailslayout.addView(Proname);
		
		for(int r=0;r<AppsPros.size();r++){
		
		TableRow prostablerow=new TableRow(this);
		prostablerow.setLayoutParams(lpfw);
		
		TextView prostextview=new TextView(this);
		prostextview.setLayoutParams(versiontextviewparams);
		prostextview.setText("* "+AppsPros.get(r));
		
		prostablerow.addView(prostextview);
		
		versiondetailslayout.addView(prostablerow);
		}
		
		// for blank spaces
		for(int r=0;r<3;r++){
			
			TableRow prostablerow=new TableRow(this);
			prostablerow.setLayoutParams(lpfw);
			
			TextView prostextview=new TextView(this);
			prostextview.setLayoutParams(versiontextviewparams);
			prostextview.setText(" ");
			
			prostablerow.addView(prostextview);
			
			versiondetailslayout.addView(prostablerow);
		}
		
		TableRow Conname=new TableRow(this);
		Conname.setLayoutParams(lpfw);
		
		TextView Conameview=new TextView(this);
		Conameview.setLayoutParams(versiontitletextviewparams);
		Conameview.setText("These Features are NOT available in this Version:");
		Conname.addView(Conameview);
		versiondetailslayout.addView(Conname);
		
		for(int r=0;r<AppsCons.size();r++){
			
			TableRow constablerow=new TableRow(this);
			constablerow.setLayoutParams(lpfw);
			
			TextView constextview=new TextView(this);
			constextview.setLayoutParams(versiontextviewparams);
			constextview.setText("* "+AppsCons.get(r));
			
			constablerow.addView(constextview);
			
			versiondetailslayout.addView(constablerow);
		
		}
		
		
		
		AlertDialog.Builder currentversionbuilder =new AlertDialog.Builder(this);
		currentversionbuilder.setTitle("Current Version:   "+CurrentAppVersion);
		currentversionbuilder.setView(versiondetailslayout);
		
		
		
		currentversionbuilder.setPositiveButton("Check for Updates",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						checkforupdate();
					}
				})
				.setNeutralButton("Other Versions",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
							checkforotherversions();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								
							}
						});
	
		final AlertDialog currentversionalert=currentversionbuilder.create();
		
		currentversionalert.show();
	}
	
	public void checkforupdate(){
		try {
			
				updatestrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
				public boolean accept(File directory, String fileName) {
					return fileName.endsWith(".apk");
				}
			  });
		}catch(Throwable e){
			
		}
		try {
			
			updatedetailsstrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".txt");
			}
		  });
		}catch(Throwable e){
			
		}
	 
	   String Avaialableupdate="0.0";
	   int menuitem=0;
		for (int k=0;k<updatestrings.length;k++){
		
		   String version= updatestrings[k].substring(updatestrings[k].indexOf("_V.")+3,updatestrings[k].length()-4);
	       Log.d("available versions",version);
		   if(Double.parseDouble(version)>Double.parseDouble(CurrentAppVersion)&&Double.parseDouble(Avaialableupdate)<Double.parseDouble(version)){
	    	   Avaialableupdate=version;
	    	   menuitem=k;
	       }
	   
	   }
	
	 if(!Avaialableupdate.equals("0.0")){
		 updatenewversion(Avaialableupdate,menuitem);
	 }else{
		 showToast("Update NOT Available"); 
	 }
	
	
	}
	
	public void checkforotherversions(){
		try {
			
			updatestrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".apk");
			}
		  });
	}catch(Throwable e){
		
	}
		if (!(updatestrings == null) && !(updatestrings.length == 0)) {
			for (int t = 0; t < updatestrings.length; t++) {

				updatestrings[t] = updatestrings[t].replace(".apk", "");
				Log.d("versions found", updatestrings[t]);
			}

			final CharSequence[] items = updatestrings;

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Make your selection");
			// builder.setItems( menuitems[type], new
			// DialogInterface.OnClickListener() {
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int menuitem) {
					// Do something with the selection
					String newversion=updatestrings[menuitem].substring(updatestrings[menuitem].indexOf("_V.")+3,updatestrings[menuitem].length()); 
					
					AppsPros = new ArrayList<String>();
				     AppsCons = new ArrayList<String>();
				     newversiondetails(newversion,menuitem);
				     newversiondialogue(newversion,menuitem);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			showToast(" Otherversions NOT Available");
		}
	}
	
	public void additionalapps(){
		u.log(ADDITIONALAPPSFOLDERPATH);
		//check if apps are available
		
		ArrayList<String> missingapps=new ArrayList<String>();
		if(!u.appInstalledOrNot("com.google.android.apps.maps",ctx)){
			missingapps.add("com_google_android_apps_maps.apk");
		}
	
		if(!u.appInstalledOrNot("com.metago.astro",ctx)){
			missingapps.add("Astro_File_Manager.apk");
		}
		if(!u.appInstalledOrNot("com.chartcross.gpstest",ctx)){
			missingapps.add("GPS_Test.apk");
		}
		if(!u.appInstalledOrNot("com.dataviz.docstogo",ctx)){
			missingapps.add("Documents_To_Go_FULL.apk");
		}
		if(!u.appInstalledOrNot("com.iudesk.android.photo.editor",ctx)){
			missingapps.add("Photo_Editor.apk");
		}
		if(!u.appInstalledOrNot("com.sharpcast.sugarsync",ctx)){
			missingapps.add("SugarSync.apk");
		}
		if(!u.appInstalledOrNot("lah.texportal",ctx)){
			missingapps.add("TeXPortal.apk");
		}
		
		if(!(missingapps.size()==0)){
		
		
		
//		u.appInstalledOrNot("com.chartcross.gpstest",ctx);
//		u.appInstalledOrNot("com.dataviz.docstogo",ctx);
//		u.appInstalledOrNot("com.iudesk.android.photo.editor",ctx);
//		u.appInstalledOrNot("com.sharpcast.sugarsync",ctx);
//		u.appInstalledOrNot("lah.texportal",ctx);
		
		
	
		
			
			additionalavaialableapps = missingapps.toArray(new String[missingapps.size()]);
			
			final CharSequence[] items = additionalavaialableapps;

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("You are missing the following components. Please install for optimal app performance.");
			// builder.setItems( menuitems[type], new
			// DialogInterface.OnClickListener() {
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int menuitem) {
					temperaryapklocation=new File(Environment.getExternalStorageDirectory().toString()+"/temperaryapklocation");
					// Do something with the selection
					temperaryapklocation.mkdir();
					tempapkpath=temperaryapklocation+"/"+additionalavaialableapps[menuitem];
					if(additionalavaialableapps[menuitem].equals("TeXPortal.apk")){
						installtexportalfiles=true;
					}
					try {
						u.copyfilefromAssets(additionalavaialableapps[menuitem], tempapkpath, ctx);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try{
				
					File  newappapk= new File(tempapkpath);
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);	
					intent.setDataAndType(Uri.fromFile(newappapk),
							u.getMimeType(newappapk.getAbsolutePath()));
					Log.d("file to be openned", newappapk.getAbsolutePath());
					startActivityForResult(intent, INSTALLMISSINGAPP);
					
					}catch(Throwable e1){
						
					}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			showToast(" Additional Apps Not Available");
		}
	}
	
	
	public void updatenewversion(String newversion, int menuitem){
		 int menuitems=menuitem;
		 AppsPros = new ArrayList<String>();
	     AppsCons = new ArrayList<String>();
	     newversiondetails(newversion,menuitems);
	     newversiondialogue(newversion,menuitems);
		
		
	}
	
	public void newversiondetails(String newversion,int menuitem){
		try {
			
			updatestrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".apk");
			}
		  });
		}catch(Throwable e){
			
		}
		
	
		try{
			int menuitems=menuitem;
			if(!(menuitems==-1)){
				//u.log(updatestrings[menuitems]);
			if(updatestrings[menuitems].contains(newversion)){
			File versiondetails = new File(UPDATESFOLDERPATH+"/"+updatestrings[menuitems].replace(".apk", ".txt"));
			
			BufferedReader br1 = new BufferedReader( new FileReader(versiondetails));
			String line;
			while ((line = br1.readLine()) != null) {
				Log.d("line read from version", line);
				if(line.contains(":")){
					int Vindex=line.indexOf("_V.")+3;
					CurrentAppVersion=line.substring(Vindex, line.length());
					Log.d("Current Version",CurrentAppVersion);
				}
				if(line.contains("*")){
					AppsPros.add(line.substring(1, line.length()));
					Log.d("Pros",line.substring(1, line.length()));
				}
				if(line.contains("%")){
					AppsCons.add(line.substring(1, line.length()));
					Log.d("Cons",line.substring(1, line.length()));
				}
				
			}
			br1.close();
				}
			}
		}catch(Throwable e){
			
		}
	}
	public void newversiondialogue(String newversion,int menuitem){
		final String finalnewversion =newversion;
		final int menuitems=menuitem;
		
		LayoutParams versiontextviewparams=new LayoutParams(0,LayoutParams.WRAP_CONTENT,5.0f);
		LayoutParams versiontitletextviewparams=new LayoutParams(0,LayoutParams.WRAP_CONTENT,10.0f);
		versiontitletextviewparams.height=100;
		
		TableLayout versiondetailslayout=new TableLayout(this);
		versiondetailslayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		versiondetailslayout.setPadding(2, 2, 2, 2);
		
		TableRow Proname=new TableRow(this);
		Proname.setLayoutParams(lpfw);
		
		TextView Pronameview=new TextView(this);
		Pronameview.setLayoutParams(versiontitletextviewparams);
		Pronameview.setText("Added Features in this Version");
		Proname.addView(Pronameview);
		versiondetailslayout.addView(Proname);
		
		for(int r=0;r<AppsPros.size();r++){
		
		TableRow prostablerow=new TableRow(this);
		prostablerow.setLayoutParams(lpfw);
		
		TextView prostextview=new TextView(this);
		prostextview.setLayoutParams(versiontextviewparams);
		prostextview.setText("* "+AppsPros.get(r));
		
		prostablerow.addView(prostextview);
		
		versiondetailslayout.addView(prostablerow);
		}
		
		// for blank spaces
		for(int r=0;r<3;r++){
			
			TableRow prostablerow=new TableRow(this);
			prostablerow.setLayoutParams(lpfw);
			
			TextView prostextview=new TextView(this);
			prostextview.setLayoutParams(versiontextviewparams);
			prostextview.setText(" ");
			
			prostablerow.addView(prostextview);
			
			versiondetailslayout.addView(prostablerow);
		}
		
		TableRow Conname=new TableRow(this);
		Conname.setLayoutParams(lpfw);
		
		TextView Conameview=new TextView(this);
		Conameview.setLayoutParams(versiontitletextviewparams);
		Conameview.setText("These Features are NOT available in this Version:");
		Conname.addView(Conameview);
		versiondetailslayout.addView(Conname);
		
		for(int r=0;r<AppsCons.size();r++){
			
			TableRow constablerow=new TableRow(this);
			constablerow.setLayoutParams(lpfw);
			
			TextView constextview=new TextView(this);
			constextview.setLayoutParams(versiontextviewparams);
			constextview.setText("* "+AppsCons.get(r));
			
			constablerow.addView(constextview);
			
			versiondetailslayout.addView(constablerow);
		
		}
		
		
		
		AlertDialog.Builder newversionbuilder =new AlertDialog.Builder(this);
		newversionbuilder.setTitle("Update App to Version:   "+newversion);
		newversionbuilder.setView(versiondetailslayout);
		
		
		
		newversionbuilder.setPositiveButton("Update",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try{
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_VIEW);	
							try {
								
								updatestrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
								public boolean accept(File directory, String fileName) {
									return fileName.endsWith(".apk");
								}
							  });
							}catch(Throwable e){
								
							}
							
							if(!(menuitems==-1)){
								//u.log(updatestrings[menuitems]);
								if(updatestrings[menuitems].contains(finalnewversion)){
									File  newversionapk= new File(UPDATESFOLDERPATH+"/"+updatestrings[menuitems]);
									intent.setDataAndType(Uri.fromFile(newversionapk),
											u.getMimeType(newversionapk.getAbsolutePath()));
									Log.d("file to be openned", newversionapk.getAbsolutePath());
									startActivity(intent);
										
								}
								
								
							}else{
								
							}
							}catch(Throwable e){
							
						}
					}
				})
				.setNeutralButton("Don't Ask again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								u.set(PREFS_NAME,"updatecheck",finalnewversion,ctx);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								
							}
						});
	
		final AlertDialog newversionalert=newversionbuilder.create();
		
		newversionalert.show();
	}
	
	public void showToastandlog(final String toast) {
		u.log(toast);
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void cleareveryting(){
		clearpreferences();
    	for(int x=0; x<ctx.databaseList().length;x++){
    		ctx.deleteDatabase(ctx.databaseList()[x]);
    	}
	}
	public void clearpreferences(){
		ctx.getSharedPreferences(PREFS_NAME,0).edit().clear().commit();
	}
	public void deletelhifolder(){
		u.DeleteRecursive(new File(ELUTIONSPROJECTFOLDERPATH));
		System.out.println(ELUTIONSPROJECTFOLDERPATH);
		File file=new File(ELUTIONSPROJECTFOLDERPATH);
		file.delete();
	}
	public void renamelhifolder(){
		File file=new File(ELUTIONSPROJECTFOLDERPATH);
		int next=1;
		File nextfolder=new File(ELUTIONSPROJECTFOLDERPATH+"("+next+")");
		while (nextfolder.exists()){
			next++;
			nextfolder=new File(ELUTIONSPROJECTFOLDERPATH+"("+next+")");
		}
		file.renameTo(nextfolder);
	}
	
	public void uninstall(){
		Uri packageUri = Uri
				.parse("package:com.lifehackinnovations.siteaudit");
		Intent uninstallIntent = new Intent(
				Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
		startActivity(uninstallIntent);
	}
	
	public void showinstructionsmenu(){
		
	ArrayList<String> Instructionsmenu =new ArrayList<String>();
	Instructionsmenu.add("Home Screen");
	Instructionsmenu.add("Open Close Delete Projects");
	Instructionsmenu.add("Sit Info Tab");
	Instructionsmenu.add("Floorplan");
	Instructionsmenu.add("Components Tab");
	Instructionsmenu.add("Asstes Tab");
	Instructionsmenu.add("Consumption");
	Instructionsmenu.add("Recommendations Tab");
	Instructionsmenu.add("Maestro Commisioning Tab");
	Instructionsmenu.add("BOM Tab");
	Instructionsmenu.add("Notes Tab");
	Instructionsmenu.add("File Explorer Tab");
	Instructionsmenu.add("MOP Tab");
	Instructionsmenu.add("Savings Projects to Cloud");
	Instructionsmenu.add("How to use Sugar Sync");
	Instructionsmenu.add("How to do Sitewalk");
	Instructionsmenu.add("Restoring Site Audit App");
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Site Audit Instructions Menu");
	// builder.setItems( menuitems[type], new
	// DialogInterface.OnClickListener() {
		if (Instructionsmenu.size()>0) {
			final CharSequence[] items=new CharSequence[Instructionsmenu.size()];
			for (int t = 0; t < Instructionsmenu.size(); t++) {
				items[t]=FilenameUtils.getBaseName(new File(Instructionsmenu.get(t)).getName());
			}
		
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int menuitem) {
				// Do something with the selection
				u.log("help menu, "+u.s(menuitem)+"selected");
				
				switch (menuitem) {
				
				case 0:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=03YMJ11aER4")));
					break;
				case 1:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=h496Uaofdq4")));
					break;
				case 2:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vu-8djCApHo")));
					break;
				case 3:
					// florplan video comming soon
					break;
				case 4:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=BpTZ5NoSSRU")));
					break;
				case 5:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=nuQgRiU5Y8U")));	
					break;
				case 6:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=i6pXnOeCg4o")));
					break;
				case 7:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=slgylhhKzzg")));
					break;
				case 8:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=oVTo6gC52h0")));
					break;
				case 9:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-sGHIQnAilU")));
					break;
				case 10:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=CpKDBpUn990")));
					break;
				case 11:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=GfZMKb3WTP8")));
					break;
				case 12:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6Kayxw4Xnc8")));
					break;
				case 13:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=XMKBXXMKmUE")));
					break;
				case 14:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=3Nn3j34qe18")));
					break;
				case 15:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=7Yzo9AKChmk")));
					break;
				case 16:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bKJvOjpGdr4")));
					break;
				
				}
			}
			
		});
		
	 }
		AlertDialog alert = builder.create();
		alert.show();
	}
 public void checkfordifferentversion(){
		try {
			
			updatestrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".apk");
			}
		  });
	}catch(Throwable e){
		
	}
	try {
		
		updatedetailsstrings = new File(UPDATESFOLDERPATH).list(new FilenameFilter() {
		public boolean accept(File directory, String fileName) {
			return fileName.endsWith(".txt");
		}
	  });
	}catch(Throwable e){
		
	}
	 String Avaialableupdate="0.0";
	   int menuitem=0;
		for (int k=0;k<updatestrings.length;k++){
		
		   String version= updatestrings[k].substring(updatestrings[k].indexOf("_V.")+3,updatestrings[k].length()-4);
	       Log.d("available versions",version);
		   if(Double.parseDouble(version)>Double.parseDouble(CurrentAppVersion)&&Double.parseDouble(Avaialableupdate)<Double.parseDouble(version)){
	    	   Avaialableupdate=version;
	    	   menuitem=k;
	       }
	   
	   }
		
		String nocheckversion= (String)u.get(PREFS_NAME,"updatecheck",ctx);
		u.log("Availableupdate, "+Avaialableupdate);
		u.log("nocheckversion, "+nocheckversion);
		if(!Avaialableupdate.equals(nocheckversion)){
			u.log("updatecheck, "+"check");
			u.set(PREFS_NAME,"updatecheck","check",ctx);
			CheckforAutoUpdate="check";
		}
 }
 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
	 switch (requestCode) {
		case INSTALLMISSINGAPP:
			if(resultCode == RESULT_OK){
				try{	
					u.log(temperaryapklocation);
					u.DeleteRecursive(temperaryapklocation);
				}catch(Throwable e){
					
				}
			}
			if(installtexportalfiles){
				copylahtexportalfiletotempfolder();
				
				u.log("about to try and unzip file for first time "+tempapkpath);
				trytounziplahtexportalfile(tempapkpath);
			}
			break;
			case UNZIP:
				//u.log("in unzip result code="+resultCode);
				//if(resultCode == RESULT_OK){
				
				u.log("just entered unzip, so we must have androzip?");
				
				File oldfolder=new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/lah.texportal/");
				long valuemb=dirSize(oldfolder);
				long size=(long) 38;
				u.log(valuemb);
				u.log(size);
				if(!(valuemb>size)){
					
				
				
					deleteoldlahfolder();
					
					u.log("trying to unzip file again "+tempapkpath);
					trytounziplahtexportalfile(tempapkpath);
						
					u.log("at this point the folder should be unzipped");
					u.log("trying to copy it into the correct folder");
					copylahtexportalfoldertoandroiddata();
				//}
				}
			break;
	 }
	 
 }
 
 public void showneedandrozipdialog(final String appname,
			final String appaddress) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Please download additional software")
				.setMessage(
						"In order to retrieve all the files, you'll need to download"
								+ appname
								+ ". Would you like to download it now?")
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("CONTINUE",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								getappfromplay(appaddress);
								dialog.dismiss();
							}
						})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).create().show();

		
	}
	 public void trytounziplahtexportalfile(String location){

			
			
			Intent intent = new Intent();
			
			File file =new File(location);
			intent.setDataAndType(Uri.fromFile(file),
					u.getMimeType(location));

			intent.setAction(android.content.Intent.ACTION_VIEW);
			
				if(u.appInstalledOrNot("com.agilesoftresource", ctx)){
					startActivityForResult(intent,UNZIP);
				}else{
			
					u.log("apparently we don't have androzip so we're asking to download it");
					showneedandrozipdialog(
							"AndroZip",
							"com.agilesoftresource");
	 			}	
			
			
			
			
		
	 }
	 public void copylahtexportalfiletotempfolder(){
		 temperaryapklocation=new File(Environment.getExternalStorageDirectory().toString()+"/temperaryapklocation");
			// Do something with the selection
			temperaryapklocation.mkdir();
			String texportalfilesname="lah.texportal.zip";
			tempapkpath=temperaryapklocation+"/"+texportalfilesname;
			
			
			
			showToastandlog("TexPortal Installed, getting file from assets");
			try {
				String location=tempapkpath;
				
				u.copyfilefromAssets(texportalfilesname, location, ctx);
				u.log("copied file, "+texportalfilesname+" to "+location);
			}catch(Throwable e){
				
			}
	 }
	 public void copylahtexportalfoldertoandroiddata(){
		 	File currentlahtexportallocation=new File(Environment.getExternalStorageDirectory().toString()+"/AndroZip/lah.texportal/");
		 	File finallahtexportallocation=new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/lah.texportal/");
		 	try {
				u.copyDirectory(currentlahtexportallocation, finallahtexportallocation);
				installtexportalfiles=false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	// Do something with the selection
			
	 }
	 public void deleteoldlahfolder(){
		 u.log("deleteing folder /Android/data/lah.texportal/");

		 try{
			 File oldfolder=new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/lah.texportal/");
			 u.deleteDirectory(oldfolder);
		 }catch(Throwable e){
			 e.printStackTrace();
			 u.log("folderdidn't delete");
		 }
	}
	 private static long dirSize(File dir) {

		    if (dir.exists()) {
		        long result = 0;
		        File[] fileList = dir.listFiles();
		        for(int i = 0; i < fileList.length; i++) {
		            // Recursive call if it's a directory
		            if(fileList[i].isDirectory()) {
		                result += dirSize(fileList [i]);
		            } else {
		                // Sum the file size in bytes
		                result += fileList[i].length();
		            }
		        }
		        return result; // return the file size
		    }
		    return 0;
		}
}
