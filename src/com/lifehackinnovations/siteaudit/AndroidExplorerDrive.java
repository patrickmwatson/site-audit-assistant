package com.lifehackinnovations.siteaudit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildReference;
import com.lifehackinnovations.siteaudit.Tabs1.LoadTask;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidExplorerDrive extends ListActivity {
	static final int REQUEST_ACCOUNT_PICKER = 4;
	static final int REQUEST_AUTHORIZATION = 5;
	private static Drive service;
	public static List<com.google.api.services.drive.model.File> files;
	
	private static GoogleAccountCredential credential;
	private static String RootFolder;
	private static String SiteWalksFolder;
	private static String CompanyFolder;
	private static String ProjectFolder;
	
	public Context ctx;
	
	private static Looper looper;
	
	private List<String> item = null;
	private List<String> path = null;
	
	private TextView myPath;


	private SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	private SharedPreferences.Editor editor;

	private String masterfoldername;
	public static HashMap<String,String> filehash;
	
	public static String folderkey;
	
	protected ProgressDialog progressDialog; 
   //preference variables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidexplorer);
        grabdriveservice();
        progressDialog=new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
    	progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_icon_drawable_animation));
    	progressDialog.setIcon(R.drawable.ic_launcher);
    	progressDialog.setTitle("Loading");
    	progressDialog.setMessage("Please Wait");
    	progressDialog.setCancelable(false);
		new LoadTask(progressDialog).execute();
        
        
    }
    
    private void getDir()
    {
    	
    	myPath.setText("Please select a project to Import from the Cloud");
    	item=new ArrayList<String>();
    	/*
    	item = new ArrayList<String>();
    	if (!(files.size()==0)){
    		for(int i=0; i < files.size(); i++){
	    		if(files.get(i).getParents().get(0).getId().equals(filehash.get(key)))
    			item.add(files.get(i).getTitle());
	    	}
    	}
    	
    	*/
    	List<String> companieslist=new ArrayList<String>();
    	for(int i=0;i<files.size();i++){
    		com.google.api.services.drive.model.File file=files.get(i);
    		if(file.getParents().size()>0){
    			if(file.getParents().get(0).getId().equals(RootFolder)&&file.getTitle().equals("SiteWalks")){
    				SiteWalksFolder=file.getId();
    				break;		
    			}
    		}
    	}
    	for(int i=0;i<files.size();i++){
    		com.google.api.services.drive.model.File file=files.get(i);
    		if(file.getParents().size()>0){
    			if(file.getParents().get(0).getId().equals(SiteWalksFolder)){
    				companieslist.add(file.getId());
    				Log.d("company added to list",filehash.get(file.getId()));
    			}
    		}
    	}
    	
    	for(int i=0;i<files.size();i++){
    		com.google.api.services.drive.model.File file=files.get(i);
    		if(file.getParents().size()>0){
    			if(companieslist.contains(file.getParents().get(0).getId())){
    				
    				item.add(filehash.get(file.getParents().get(0).getId())+"-"+filehash.get(file.getId()));
    				
    			}
    		}
    	}
    	
    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.androidexplorerrow, item);
    	setListAdapter(fileList);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
		String folderandcompanyname=((TextView)v).getText().toString();
		String companyname=folderandcompanyname.split("-")[0];
		String foldername=folderandcompanyname.split("-")[1];
		setDefaultPreferences();
		set("foldername",(foldername));
        
		//startActivity(u.intent("Tabs1"));
		Log.d("foldername=",foldername);
		
		for(int i=0;i<files.size();i++){
    		com.google.api.services.drive.model.File file=files.get(i);
    		if(filehash.get(file.getId()).equals(foldername)&&filehash.get(file.getParents().get(0).getId()).equals(companyname)){
    			folderkey=file.getId();
    			break;		
    		}
    	}
		Log.d("folderkey=",folderkey);
		
        getPreferences();
        getstorageready();
        startActivity(u.intent("Writepreferencesfile"));
        
        new DriveTask(progressDialog).execute();
        
         /* User clicked OK so do some stuff */
    	
     	
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
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// Check which request we're responding to
			
			switch (requestCode) {
		      case REQUEST_ACCOUNT_PICKER:
		        if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
		        	String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
		            Log.d("accountName",accountName);
		        	if (accountName != null) {
		              credential.setSelectedAccountName(accountName);
		              service=getDriveService(credential);
		             
		              Log.d("apparently drivelogon was successful","but maybe not");
		            
		              new LoadTask(progressDialog).execute();
		              
		            }
		        }
		        break;
		      case REQUEST_AUTHORIZATION:
		        if (resultCode == Activity.RESULT_OK) {
		        	
		        } else {
		          startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
		        }
		        break;
		      }
	 }
	 public void set(String prefname, String value){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	editor=settings.edit();
	    	editor.putString(prefname, value);
	    	editor.commit();
	    }
	 public void grabdriveservice(){
		 service=GetDriveService.getService();
	 }
	 public void createdrivehashmap(String rootfolderid){
		 try {
			files=drive.retrieveAllFiles(service);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 filehash = new HashMap<String,String>();
     	long start=System.currentTimeMillis();
    
     	filehash.put(rootfolderid, "Root");
     	for(int i=0;i<files.size();i++){
     		filehash.put(files.get(i).getId(), files.get(i).getTitle());
     		Log.d("key,title",files.get(i).getId()+","+files.get(i).getTitle());
     	}
     	
     	long stop=System.currentTimeMillis();
     	Log.d("time to make hash",String.valueOf(stop-start));
     	
     	
	 }
	 protected class LoadTask extends AsyncTask<Object, Void, String>
	    {
		 	 
			 ProgressDialog progressDialog;
			 public LoadTask(ProgressDialog progressDialog){
				 this.progressDialog=progressDialog;
			 }
	                @Override
	                protected String doInBackground( Object... params ) 
	                {
	                	
	                	try{
	                		looper.prepare();
	                	}catch(RuntimeException e){
	                		
	                	}
	                
	                	if (service==null){
		            		  Log.d("service is null","true");
		            		  
		            		  credential = GoogleAccountCredential.usingOAuth2(AndroidExplorerDrive.this, DriveScopes.DRIVE);
		            		  try{
		            			  startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
		            		  }catch(RuntimeException e){
		            			  e.printStackTrace();
		            			  runOnUiThread(new Runnable() {
		            				  public void run() {
		            					  
		            					  showneedmoresoftwaredialog("Drive","com.google.android.apps.docs");
		            				  }});
		            		  }
		            	       
		            	  }else{
		            	 
		            		  About about = null;
			            		try {
			            			about = service.about().get().execute();
			            		
			            		} catch (UserRecoverableAuthIOException e) {
			            	          startActivity(e.getIntent());
			            		} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			            		try{
			            		RootFolder=about.getRootFolderId();
			            		createdrivehashmap(about.getRootFolderId());
			                    
			                    getPreferences();
			                   
			                    
			                    myPath = (TextView)findViewById(R.id.path);
			                    getstorageready();
			                   
			                	
			                	
			                	
			                	runOnUiThread(new Runnable() {
			            		     public void run() {
			            		    	
					            		 getDir();
					            	}
			            		});
			                	/*
			               
			            		runOnUiThread(new Runnable() {
			            		     public void run() {

			            		     }
			            		});
			            		*/
			            		}catch(NullPointerException b){
			            			showToast("Sorry Google Drive is Down Right Now, Check Back Later");
			            			
			            		}
								return null;
		            	  }
						return null;
	                	
	                }
	                
	                // -- gets called just before thread begins
	                @Override
	                protected void onPreExecute() 
	                {
	                	//progressDialog = new ProgressDialog(Tabs1.this);
	                	
	                	
	                	progressDialog.show();  
	                        
	                }
	         
	                @Override
	                protected void onPostExecute( String result ) 
	                {
	                      progressDialog.dismiss();
	                     
	                }
	    }
	 
	 private Drive getDriveService(GoogleAccountCredential credential) {
	        return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
	            .build();
	      }

	     

		public static Drive getService() {
			return service;
		}

		public static void setService(Drive service) {
			AndroidExplorerDrive.service = service;
		}
	 public void setDefaultPreferences(){
	    	settings = getSharedPreferences(PREFS_NAME, 0);
	    	
	    	//first check if defaults have been set
	    	
	    	set("masterfoldername","lhi");
			set("foldername","My Project");
			set("seriesname", "image");
		
	    }
	 protected class DriveTask extends AsyncTask<Object, Void, String>
	  {
		 	 
			 ProgressDialog progressDialog;
			 public DriveTask(ProgressDialog progressDialog){
				 this.progressDialog=progressDialog;
			 }
	              @Override
	              protected String doInBackground( Object... params ) 
	              {
	         
	            	  try {
						copyFilesFromDrive();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	 
	            		runOnUiThread(new Runnable() {
	            		     public void run() {  
	            		    	 startActivity(u.intent("Tabs1"));
	  	        	
	            		    	 finish();
	            		     }
	            		});
						return null;
	              }
	              
	              // -- gets called just before thread begins
	              @Override
	              protected void onPreExecute() 
	              {
	              	//progressDialog = new ProgressDialog(Tabs1.this);
	              	
	              	
	              	progressDialog.show();  
	                      
	              }
	       
	              @Override
	              protected void onPostExecute( String result ) 
	              {
	                    progressDialog.dismiss();
	                   
	              }
	  }
	 private void copyFilesFromDrive() throws IOException {
		 		String ProjectDirectory = Environment.getExternalStorageDirectory().toString()
					+ "/" + masterfoldername + "/" + filehash.get(folderkey);
	        	copyprojectfilesfromdrive(folderkey, ProjectDirectory);
	        	
	  
	  }

	 public void copyprojectfilesfromdrive(String folderkey, String Directory) throws IOException{
		 String fileid = null;
		// About about = service.about().get().execute();
		
		 Log.d("The directory where the file is being downloaded to",Directory);
		 List<ChildReference> childreference=service.children().list(folderkey).execute().getItems();
		 for (int num=0; num<childreference.size(); num++){
			 fileid=childreference.get(num).getId();
			 Log.d("The file being copied",filehash.get(fileid));
			 
			 
			 InputStream input=downloadFile(service, service.files().get(fileid).execute());
//Drive strips file extensions sometimes when files are edited with msexcel
//This field fixes that.
			
			 if (!filehash.get(fileid).contains(".")){
				 AssetManager aMan = this.getAssets();
					String[] filelist = new String[aMan.list("").length];
					try {
						filelist = aMan.list("");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i=0;i<filelist.length;i++){
						if(filelist[i].contains(filehash.get(fileid))){
							filehash.put(fileid, filelist[i]);
							Log.d("Replacing filename",filehash.get(fileid)+"with "+filelist[i]);
							break;
						}
					}
			 }
			 if (filehash.get(fileid).contains(".")){
			 OutputStream output = new FileOutputStream(Directory + "/"+filehash.get(fileid));
				 Log.d("Writing file from drive",filehash.get(fileid));
				 showToast("Writing file from drive: "+filehash.get(fileid));
	//check if file is a directory
				
					 
				 
				 byte data[] = new byte[1024];
				 long total = 0;
				 int count;
				 while ((count = input.read(data)) != -1) {
					 total += count;
					 
					 output.write(data, 0, count);
					 
				 }
				 
				 output.flush();
				 output.close();
				 input.close();
			 }else{
				 String subdirectory=Directory + "/"+filehash.get(fileid);
				 File newdir=new File(subdirectory);
				 newdir.mkdirs();
				 copyprojectfilesfromdrive(fileid, subdirectory);
			 }
		 }
	
	 }
	 private static InputStream downloadFile(Drive service, com.google.api.services.drive.model.File file) {
		    
		 if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
		      try {
		        HttpResponse resp =
		            service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
		                .execute();
		        return resp.getContent();
		      } catch (IOException e) {
		        // An error occurred.
		        e.printStackTrace();
		        return null;
		      }
		    } else {
		      // The file doesn't have any content stored on Drive.
		      return null;
		    }
		  }
	 public void showToast(final String toast) {
	        runOnUiThread(new Runnable() {
	          public void run() {
	            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
	          }
	        });
	      }
	 public void showneedmoresoftwaredialog(final String appname, final String appaddress){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Please download additional software")
	            .setMessage("In order to view this file you must download "+appname+", or a similar app. Would you like to download it now?")
	            .setCancelable(true)
	            .setIcon(R.drawable.ic_launcher)
	            .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    getappfromplay(appaddress);
	                	dialog.dismiss();
	                }
	            })
	            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            }).create().show();

	      
	    	Log.d("EAS Error","You need to install a choose account intent");
		}
	 public void getappfromplay(String appName){
		 
	 		try {
	 			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
	 		} catch (android.content.ActivityNotFoundException anfe) {
	 			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
	 		}
	 	}
	 public void doloadstuff(){
		 
	 }
}