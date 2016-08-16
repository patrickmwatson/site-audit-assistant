package com.lifehackinnovations.siteaudit;

import java.util.ArrayList;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
public class MainActivityPrefs extends PreferenceActivity {
	static  String PREFS_NAME = "Main_Preferences";
	static Context ctx;
	
	static String storagekeys[]={"externalstoragedirectory","lhistoragedirectory","lhiconfigfilesstoragedirectory","projectsstoragedirectory","mainsugarsyncdirectory","sugarsynclhiconfigfilesstoragedirectory","sugarsyncprojectsstoragedirectory","sugarsyncupdatedirectory","dcimdirectory"};
	static ArrayList<Integer[]> dependents;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.main_activity_prefs);
        
        ctx=this;
     
        
        refreshpreferences();
    }   
   public void refreshpreferences(){
	   Preference restorepref=(Preference) findPreference("restoredefaultdirectories");
       restorepref.setOnPreferenceClickListener(new EditTextPreference.OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				MainActivity.setDefaultPreferences();
				refreshpreferences();
				return false;
			}});
       
       CheckBoxPreference storetypepref =(CheckBoxPreference)getPreferenceManager(). findPreference("storetype");
       
       storetypepref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				u.set(MainActivity.PREFS_NAME,preference.getKey(),newValue.toString(), ctx);
				u.log("is grocery prference, "+newValue.toString());
				refreshpreferences();
				return true;
			}});
       
       EditTextPreference imageresolutions=(EditTextPreference) findPreference("tabimageresolutions");
       
       String string=(String)u.get(MainActivity.PREFS_NAME,"tabimageresolutions", ctx);
       if(string.equals("")){
    	   u.set(MainActivity.PREFS_NAME,imageresolutions.getKey(),"1", ctx);
       }
       string=(String)u.get(MainActivity.PREFS_NAME,"tabimageresolutions", ctx);
       
    	imageresolutions.setSummary(string);
    
    	imageresolutions.setOnPreferenceClickListener(new EditTextPreference.OnPreferenceClickListener(){

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			((EditTextPreference) preference).getEditText().setText((String)u.get(MainActivity.PREFS_NAME,preference.getKey(), ctx));
		
			return false;
		}});
    	imageresolutions.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

		@Override
		public boolean onPreferenceChange(Preference preference,
				Object newValue) {
			// TODO Auto-generated method stub
			u.set(MainActivity.PREFS_NAME,preference.getKey(),newValue, ctx);
			refreshpreferences();
			return false;
		}});
   
  
  
	   
	   for(int i=0; i<storagekeys.length; i++){
	       
	        EditTextPreference pref=(EditTextPreference) findPreference(storagekeys[i]);
	        string="";
	        switch(i){
	        case 0:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/";
	        	break;
	        case 1:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[1], ctx)+"/";
	        	break;
	        case 2:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[1], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[2], ctx)+"/";
	        	break;
	        case 3:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[1], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[3], ctx)+"/";
	        	break;
	        case 4:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[4], ctx)+"/";
	        	break;
	        case 5:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[4], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[5], ctx)+"/";
	        	break;
	        case 6:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[4], ctx)+"/"+
	        			(String)u.get(MainActivity.PREFS_NAME,storagekeys[6], ctx)+"/";
	        	break;
	        
		   case 7:
	       	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
	       			(String)u.get(MainActivity.PREFS_NAME,storagekeys[4], ctx)+"/"+
	       			(String)u.get(MainActivity.PREFS_NAME,storagekeys[7], ctx)+"/";
	       	break;
	       	
		   case 8:
		       	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[8], ctx)+"/";
		       	break;
	        }
	        pref.setSummary(string);
	        
	        pref.setOnPreferenceClickListener(new EditTextPreference.OnPreferenceClickListener(){

				@Override
				public boolean onPreferenceClick(Preference preference) {
					// TODO Auto-generated method stub
					((EditTextPreference) preference).getEditText().setText((String)u.get(MainActivity.PREFS_NAME,preference.getKey(), ctx));
				
					return false;
				}});
	        pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					// TODO Auto-generated method stub
					u.set(MainActivity.PREFS_NAME,preference.getKey(),newValue, ctx);
					refreshpreferences();
					return false;
				}});
	       
	   }

   }
 
}
