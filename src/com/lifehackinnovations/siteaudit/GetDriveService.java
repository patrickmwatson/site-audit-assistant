package com.lifehackinnovations.siteaudit;



import java.io.IOException;
import java.util.Arrays;
import java.util.List;


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
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class GetDriveService extends Activity {
	
	protected ProgressDialog progressDialog; 
	
	private SharedPreferences.Editor editor;
	private SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	
	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int CAPTURE_IMAGE = 3;

	private static Uri fileUri;
	private static Drive service;
	private GoogleAccountCredential credential;
	  
	private boolean drivelogonsuccessful=false;
	  
	private AlertDialog pd;
	  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        
        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_icon_drawable_animation));
        progressDialog.setIcon(R.drawable.ic_launcher);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        
        
        credential = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);
        
      
        
        try{
        	startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }catch(RuntimeException e){
        	e.printStackTrace();
        	showneedmoresoftwaredialog();
        }
       
       
        
     
        
        
      
    }

   
    public void getrequiredPreferences(){
    
    	settings = getSharedPreferences(PREFS_NAME, 0);

    }
    public void setDefaultPreferences(){
    	settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	//first check if defaults have been set
    	
    	if (!settings.getString("defaults", "not set").equals("set")){
    		set("masterfoldername","lhi");
    		set("foldername","My Project");
    		set("componentselected","0");
    		set("tab","1");
    		
    		
    	    
    	}
       
    }
    public void set(String prefname, String value){
    	
    	editor=settings.edit();
    	editor.putString(prefname, value);
    	editor.commit();

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
      switch (requestCode) {
      case REQUEST_ACCOUNT_PICKER:
        if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
        	String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
              credential.setSelectedAccountName(accountName);
              setService(getDriveService(credential));
              drivelogonsuccessful=true;
              Log.d("apparently drivelogon was successful","but maybe not");
              new EssureAuthorizationTask(progressDialog).execute();
             
              
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
    private Drive getDriveService(GoogleAccountCredential credential) {
        return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
            .build();
      }

      public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
          public void run() {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
          }
        });
      }

	public static Drive getService() {
		return service;
	}

	public static void setService(Drive service) {
		GetDriveService.service = service;
	}
	
	public void getappfromplay(String appName){
		 
		 try {
		     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
		 } catch (android.content.ActivityNotFoundException anfe) {
		     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
		 }
	 }
	public void showneedmoresoftwaredialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please download additional software")
            .setMessage("In order to access the cloud you must first download the Drive App. Would you like to download it now?")
            .setCancelable(true)
            .setIcon(R.drawable.ic_launcher)
            .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    getappfromplay("com.google.android.apps.docs");
                	dialog.dismiss();
                	finish();
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).create().show();

      
    	Log.d("EAS Error","You need to install a choose account intent");
	}
	
	protected class EssureAuthorizationTask extends AsyncTask<Object, Void, String>
	{
		ProgressDialog progressDialog;
		public EssureAuthorizationTask(ProgressDialog progressDialog){
			this.progressDialog=progressDialog;
			
		}
	    @Override
	    protected String doInBackground( Object... params ) 
	    {
	    	try{
	    		//looper.prepare();
	    	}catch(RuntimeException e){
		    }
	    	
                 	   About about = null;
                  		try {
                  			about = service.about().get().execute();
                  		
                  		} catch (UserRecoverableAuthIOException e) {
                  	          startActivity(e.getIntent());
                  		} catch (RuntimeException e){
                  			
                  		} catch (IOException e) {
                  			// TODO Auto-generated catch block
                  			e.printStackTrace();
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
	        finish();             
	    }
	}    
}
