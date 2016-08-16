/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
//test
package com.lifehackinnovations.siteaudit;

import android.util.Log; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.util.zip.ZipEntry; 
import java.util.zip.ZipInputStream; 

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Set;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import com.lifehackinnovations.siteaudit.Tabs1.SiteAuditItem;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.common.AssertionFailed;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
//Utility File
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class u extends Activity{
	
	public static Boolean databaseimports=false; 
	
	public static String getRealPathFromURI(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	
	public static String getmimetype(File selected){
		
		     Uri selectedUri = Uri.fromFile(selected);
		     String fileExtension
		      = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
		     String mimeType
		      = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
		       
		     System.out.println("FileExtension: " + fileExtension + "\n" +
		       "MimeType: " + mimeType);
			return mimeType;
	}
	public static boolean issupportedimage(File path){
		  Uri selectedUri = Uri.fromFile(path);
		     String fileExtension
		      = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
		    boolean supportedimage=false;
		if(fileExtension.contains("jpg")||
				fileExtension.contains("gif")||
				fileExtension.contains("png")||
				fileExtension.contains("bmp")){
			supportedimage=true;
		}
		return supportedimage;
	}
	
	public static Intent intent(String classname){
		Intent myIntent=new Intent();
		myIntent.setClassName(MainActivity.class.getPackage().getName(), MainActivity.class.getPackage().getName()+"."+classname);
		return myIntent;
	}
	public void cleardata(){
		deleteDatabase("data");
	}
	public static String appname(Context context){
	     Resources appR = context.getResources();
	     CharSequence txt = appR.getText(appR.getIdentifier("app_name",
	     "string", context.getPackageName()));
		return (String)txt;
	}
	public static String s(int i){
		String s=String.valueOf(i);
		return s;
	}
	public static int i(String s){
		if(s.contains(",")){
		s=s.replace(",","");
		}
		int i;
		if (s.equals("")){
			i=0;
		}else{
			float m=Float.parseFloat(s);
			
			i=(int)(m);
		}
		return i;
	}
	public static float f(String s){
		if(s.contains(",")){
		s=s.replace(",","");
		}
		float m;
		if (s.equals("")){
			m=0;
		}else{
			m=Float.parseFloat(s);
		}
		return m;
	}
	public static long l(String s){
		if(s.contains(",")){
		s=s.replace(",","");
		}
		long m;
		if (s.equals("")){
			m=0;
		}else{
			m=Long.parseLong(s);
		}
		return m;
	}
	public static double d(String s){
		double i;
		s=s.replace(",","");
		s=s.replace("$","");
		if (s.equals("")){
			i=0;
		}else{
			i=Double.parseDouble(s);
		}
		return i;
	}
	public static String sd(double i){
		String s=String.valueOf(i);
		return s;
	}
	public static String sf(float f){
		String s=String.valueOf(f);
		return s;
	}
	public static String sl(long l){
		String s=String.valueOf(l);
		return s;
	}
	public static double ds(String s){
		double d=0;
		try{
			d=Double.parseDouble(s);
		}catch(Throwable e){
			
		}
		return d;
	}
	
	
	

	
	public static void getpref(String pref){
		
	}
	public static void savepref(String pref){
		
	}
	public static void startintentontop(String intent,Activity activity){
		 Activity ACTIVITY;
    	 ACTIVITY = activity;
   	    Intent restart=u.intent(intent);
   	    restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
   	    PendingIntent RESTART_INTENT = PendingIntent.getActivity(activity.getBaseContext(), 0, restart, activity.getIntent().getFlags());
         activity.finish();
     	AlarmManager mgr = (AlarmManager)ACTIVITY.getSystemService(Context.ALARM_SERVICE);
       	mgr.set(AlarmManager.RTC, System.currentTimeMillis() , RESTART_INTENT);
       	System.exit(2);
	}
	/*
	//Generic Button Code
	 
	 
	 ImageView button = (ImageView)findViewById(R.id.button);
     button.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//do something
			}});
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	/*
	//Example Spinner
	
	//Hour Spinner Values
    String[] hours = { "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12" ... "24" };

//Hour Spinner
     final Spinner hourspinner = (Spinner) layout.findViewById(R.id.hourspinner);
     ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, hours);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     hourspinner.setAdapter(adapter);
     hourspinner.setSelection(mHour);
     
     
     String hourvalue = hourspinner.getSelectedItem().toString();
     */
     
	
	
	
	
	
	
//Example AlertDialog with spinner
/*	
	
	 AlertDialog.Builder b = new Builder(this);
	    b.setTitle("Example");
	    String[] types = {"By Zip", "By Category"};
	    b.setItems(types, new OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	            dialog.dismiss();
	            switch(which){
	            case 0:
	                onZipRequested();
	                break;
	            case 1:
	                onCategoryRequested();
	                break;
	            }
	        }

	    });

	    b.show();
	    
*/	
/*	
	package com.testing.splashscreensample;
	 
	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	 
	public class SplashScreen extends Activity {
	   /** Called when the activity is first created. 
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.splash);
	      Thread splashThread = new Thread() {
	         @Override
	         public void run() {
	            try {
	               int waited = 0;
	               while (waited < 5000) {
	                  sleep(100);
	                  waited += 100;
	               }
	            } catch (InterruptedException e) {
	               // do nothing
	            } finally {
	               finish();
	               Intent i = new Intent();
	               i.setClassName("com.testing.splashscreensample",
	                              "com.testing.splashscreensample.MainActivitymenu");
	               startActivity(i);
	            }
	         }
	      };
	      splashThread.start();
	   }
	}	

	*/
	public void printFileNames(File fName,TextView tv){
		    int count=0;
		    if(fName.listFiles()!=null)
		    for (File f : fName.listFiles()) {
		        if (f.isDirectory()){

		            String name = f.getName();
		            System.out.println("Dir:"+ name + "\n" );
		            //tv.setText(tv.getText().toString()+"\n" + "Dir:"+ name + "\n" );
		            printFileNames(f, null);
		         }else{
		             String name = f.getName();
		             System.out.println("    File:"+ name +"\n" );
		              //  tv.setText(tv.getText().toString()+ "    File:"+ name +"\n" );
		                count++;

		         }

		    }
	}
	public static String gsits(Spinner sp){
		
		return sp.getSelectedItem().toString();
	}
	public static String gtts(TextView tv){
		
		return tv.getText().toString();
	}
	public static double gttd(TextView tv){
			
			return d(tv.getText().toString().replaceAll("[^\\d.]", ""));
	}
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
		        destFile.createNewFile();
		}
		
		FileChannel source = null;
		FileChannel destination = null;
		
		try {
		        source = new FileInputStream(sourceFile).getChannel();
		        destination = new FileOutputStream(destFile).getChannel();
		        destination.transferFrom(source, 0, source.size());
		}
		finally {
		        if(source != null) {
		            source.close();
		        }
		        if(destination != null) {
		            destination.close();
		        }
		}
	}
	public static void copyInputStreamtoFile(InputStream is, File destFile) throws IOException {
		if(!destFile.exists()) {
		        destFile.createNewFile();
		}
		
        OutputStream out=new FileOutputStream(destFile);
        byte buf[]=new byte[1024];
        int len;
        while((len=is.read(buf))>0)
        out.write(buf,0,len);
        out.close();
        is.close();
	}
		public static Intent OpenOfficeFile(String filenamestring){
			 Intent intent = new Intent(); 
				
			 	File copy = new File(filenamestring);
				
				String extension=filenamestring.substring(filenamestring.indexOf(".") + 1);
			 
			 	intent.setAction(android.content.Intent.ACTION_VIEW);
				
				if (extension.equals("xls")||extension.equals("xlsx")){
					intent.setDataAndType(Uri.fromFile(copy), "application/msexcel");
				}
				if (extension.equals("doc")||extension.equals("docx")){
					intent.setDataAndType(Uri.fromFile(copy), "application/msword");
				}
				
				return intent;
		 }
		public static Intent openpicture(String filenamestring) {
			//String extension=filenamestring.substring(filenamestring.indexOf(".") + 1);
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			
			
			intent.setDataAndType(Uri.fromFile(new File(filenamestring)), "image/*");
		
			return intent;
		}
		public static String cellid(int x, int y){
			int letter=x+65;
			int number=y+1;
			String cellid=String.valueOf((char)letter)+u.s(number);
			return cellid;
		 }
		public static int cellx(String cellid){
			char firstchar=cellid.charAt(0);
			int x=(int)firstchar-65;

			return x;
		}
		public static int celly(String cellid){
		
			int number=i(cellid.substring(1));
			int y=number-1;
			return y;
		}
		public static String[] getrow(Sheet sheet, String cellstart, String cellstop){
			 int rownum=u.celly(cellstart);
			 System.out.println("rownum"+rownum);
			 int y=rownum;
			 String[] row=new String[u.cellx(cellstop)-u.cellx(cellstart)+1];
			 System.out.println(u.cellx(cellstop)-u.cellx(cellstart)+1);
			 System.out.println(row.length);
			 for(int x=0;x<row.length;x++){
				System.out.println("x="+x);
				System.out.println("y="+y);
				System.out.println("cellid="+u.cellid(x+u.cellx(cellstart), y));
				 row[x]=sheet.getCell(u.cellid(x+u.cellx(cellstart), y)).getContents().toString();
				
			}
			return row;
		 }
		public static String[] getrowfixed(Sheet sheet, String cellstart, String cellstop){
			 int rownum=u.celly(cellstart);
			 int y=rownum;
			 String[] row=new String[u.cellx(cellstop)+1];
			 for(int x=0;x<=(u.cellx(cellstop)-u.cellx(cellstart));x++){
				 
				try{
					row[x]=sheet.getCell(u.cellid((x+u.cellx(cellstart)), y)).getContents().toString();
				}catch(ArrayIndexOutOfBoundsException e){
					row[x]="";
				}
				
			}
			return row;
		 }
		public static ArrayList<String> getcolumn(Sheet sheet, int columnindex, int rowstart ,boolean withstartblank, boolean withendingnew){
			 
			 int y=rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 String value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
			 String nextvalue=value;
			 if(withstartblank){
				 column.add("     ");
			 }
			 while(!(nextvalue.equals(""))){
				value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				column.add(value);
				y++;
				try{
					nextvalue=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				}catch(ArrayIndexOutOfBoundsException e){
					nextvalue="";
				}
			}
			if(withendingnew){
				column.add("new");
			}
			return column;
		 }
		
		public static ArrayList<String> getcolumngivenlength(Sheet sheet, int columnindex, int rowstart ,boolean withstartblank, boolean withendingnew, int lengthwithnewandblank){
			 int i=0;
			 int y=rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 String value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
			 String nextvalue=value;
			 if(withstartblank){
				 column.add("     ");
			 }
			 while(i<lengthwithnewandblank-2){
				i++;
				 value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				column.add(value);
				y++;
				try{
					nextvalue=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				}catch(ArrayIndexOutOfBoundsException e){
					nextvalue="";
				}
			}
			if(withendingnew){
				column.add("new");
				i++;
			}
			return column;
		 }
		public static ArrayList<String> getcolumnrange(Sheet sheet, int columnindex, int rowstart, int rowstop ){
			 int count=rowstop-rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 for(int num=rowstart;num<count;num++){
				
				 String value=sheet.getCell(u.cellid(columnindex, num)).getContents().toString();
				 column.add(value);
			 }
			return column;
		 }
		
		public static void liststringarray(String[] array){
			for(int num=0; num<array.length; num++){
				System.out.println(num+":  "+array[num]);
			}
		}
		public static void writesheetandcell(WritableSheet sheet, Workbook workbook, WritableWorkbook copy, int sheetint, char xchar, int yint, String whattowrite, String type, WritableCellFormat nf, HashMap<String,String> linkhash){
			  
			  
		      	
			
			  int retsheet;
			  int retx;
			  int rety;
			  
			  retsheet=sheetint-1;
			  retx=(int)xchar-97;
			  rety=yint-1;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  if(type.equals("string")){
				 if(u.isInteger(whattowrite)){
					 type="double";
				 }
			  }
			  
			  Cell readcell=sheet.getCell(retx, rety);
			  WritableCellFormat newformat=new WritableCellFormat(readcell.getCellFormat());
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  if(linkhash.containsKey(whattowrite)){
					  WritableHyperlink hl = null;
					  String hyperlink=linkhash.get(whattowrite);
					  try {
						hl = new WritableHyperlink(retx, rety, 
						            new URL(hyperlink));
						Log.d("URL",hyperlink);
						 if(!(hl==null)){
							  try {
								sheet.addHyperlink(hl);
							} catch (RowsExceededException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (WriteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						  }
					  } catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			      		
			      	
			      	}
				  
				  Label value = new Label(retx,rety,whattowrite);
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(retx,rety,u.d(whattowrite));
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(retx,rety,whattowrite);
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("format")&&!whattowrite.equals("")){
				  Formula value = new Formula(retx,rety, whattowrite);
				  newformat=nf;
				  value.setCellFormat(newformat);
				  
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			 
			 
			  
			 // WritableSheet sheet[retsheet];
		  }
		public static void writecellnew(WritableSheet sheet,View view,int x, int y){
			if (!(view==null)){ 
			
			 String whattowrite=gettextfromview(view);
			 
			 String type="string";
			 if (isDouble(whattowrite)){
				 type="double";
			 }else{
				 type="string";
			 }
			 
			 
			  Boolean oldformat=true;
			  Boolean prevcellexists=true;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readprevcell=null;
			  Cell readcell=sheet.getCell(x,y);
			  int prevnum=y-1;
			  try{
				  readprevcell=sheet.getCell(x,prevnum);
			  }catch(RuntimeException e){
				  prevcellexists=false;
			  }
			  
			  
			  WritableCellFormat newformat = null;
			 
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  try{
					  if(prevcellexists){
						  newformat=new WritableCellFormat(readprevcell.getCellFormat());
					  }else{
						  oldformat=false;
					  }
				  }catch(AssertionFailed e1){
					  oldformat=false;
				  }
			  }
			
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  Label value = new Label(x,y,whattowrite);
				  
				  if(oldformat){
					  value.setCellFormat(newformat);
					  
				  }
					  try {
						sheet.addCell(value);
						
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					  }
			  } 
			  
			 
			}
			 // WritableSheet sheet[retsheet];
		  }
		public static void writecellnewwhyperlink(WritableSheet sheet,View view,int x, int y, String hyperlink){
			if (!(view==null)){ 
			
			 String whattowrite=gettextfromview(view);
			 
			 String type="string";
			 if (isDouble(whattowrite)){
				 type="double";
			 }else{
				 type="string";
			 }
			 
			 
			  Boolean oldformat=true;
			  Boolean prevcellexists=true;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readprevcell=null;
			  Cell readcell=sheet.getCell(x,y);
			  int prevnum=y-1;
			  try{
				  readprevcell=sheet.getCell(x,prevnum);
			  }catch(RuntimeException e){
				  prevcellexists=false;
			  }
			  
			  
			  WritableCellFormat newformat = null;
			 
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  try{
					  if(prevcellexists){
						  newformat=new WritableCellFormat(readprevcell.getCellFormat());
					  }else{
						  oldformat=false;
					  }
				  }catch(AssertionFailed e1){
					  oldformat=false;
				  }
			  }
			
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				  WritableHyperlink hl = null;
				  try {
					hl = new WritableHyperlink(x, y, 
					            new URL(hyperlink));
					Log.d("URL",hyperlink);
					
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  Label value = new Label(x,y,whattowrite);
				  
				  if(oldformat){
					  value.setCellFormat(newformat);
					  
				  }
					  try {
						  if(!(hl==null)){
							  
							  sheet.addHyperlink(hl);
							  Log.d(hl+" added to cell with value",x+" "+y+" "+whattowrite);
						  }
						
						  sheet.addCell(value);
						  Log.d("added cell with value",x+" "+y+" "+whattowrite);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  
			 
			}
			 // WritableSheet sheet[retsheet];
		  }
		public static void writecelldoub(WritableSheet sheet,Double doub,int x, int y){
			if (!(doub==null)){ 
			
			 String whattowrite=u.sd(doub);
			 
			 String type="string";
			 if (isDouble(whattowrite)){
				 type="double";
			 }else{
				 type="string";
			 }
			 
			 
			  Boolean oldformat=true;
			  Boolean prevcellexists=true;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readprevcell=null;
			  Cell readcell=sheet.getCell(x,y);
			  int prevnum=y-1;
			  try{
				  readprevcell=sheet.getCell(x,prevnum);
			  }catch(RuntimeException e){
				  prevcellexists=false;
			  }
			  
			  
			  WritableCellFormat newformat = null;
			 
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  try{
					  if(prevcellexists){
						  newformat=new WritableCellFormat(readprevcell.getCellFormat());
					  }else{
						  oldformat=false;
					  }
				  }catch(AssertionFailed e1){
					  oldformat=false;
				  }
			  }
			
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  Label value = new Label(x,y,whattowrite);
				  
				  if(oldformat){
					  value.setCellFormat(newformat);
					  
				  }
					  try {
						sheet.addCell(value);
						
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  
			 
			}
			 // WritableSheet sheet[retsheet];
		  }
		
		public static void writehyperlinkonly(WritableSheet sheet, int x, int y, String hyperlink){
			WritableHyperlink hl = null;
			  try {
				hl = new WritableHyperlink(x, y, 
				            new URL(hyperlink));
				Log.d("URL",hyperlink);
			  } catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  try {
				  if(!(hl==null)){
					  sheet.addHyperlink(hl);
				  }
				
				
			  } catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  } catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
		}
		public static void writecell(WritableSheet sheet, int x, int y, String whattowrite, String type, WritableCellFormat nf){
			  
			if(type.equals("string")){
				 if(u.isInteger(whattowrite)){
					 type="double";
				 }
			  }
			
			  Boolean oldformat=true;
			  Boolean prevcellexists=true;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readprevcell=null;
			  Cell readcell=sheet.getCell(x, y);
			  int prevnum=y-1;
			  try{
				  readprevcell=sheet.getCell(x,prevnum);
			  }catch(RuntimeException e){
				  prevcellexists=false;
			  }
			  
			  
			  WritableCellFormat newformat = null;
			 
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  try{
					  if(prevcellexists){
						  newformat=new WritableCellFormat(readprevcell.getCellFormat());
					  }else{
						  oldformat=false;
					  }
				  }catch(AssertionFailed e1){
					  oldformat=false;
				  }
			  }
			
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  Label value = new Label(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
					  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("format")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y, whattowrite);
				  newformat=nf;
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			 
			 
			  
			 // WritableSheet sheet[retsheet];
		  }
		public static void writecellwlink(WritableSheet sheet, int x, int y, String whattowrite, String type, WritableCellFormat nf,String hyperlink){
			  
			WritableHyperlink hl = null;
			  try {
				hl = new WritableHyperlink(x, y, 
				            new URL(hyperlink));
				Log.d("URL",hyperlink);
			  } catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  Boolean oldformat=true;
			  Boolean prevcellexists=true;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readprevcell=null;
			  Cell readcell=sheet.getCell(x, y);
			  int prevnum=y-1;
			  try{
				  readprevcell=sheet.getCell(x,prevnum);
			  }catch(RuntimeException e){
				  prevcellexists=false;
			  }
			  
			  
			  WritableCellFormat newformat = null;
			 
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  try{
					  if(prevcellexists){
						  newformat=new WritableCellFormat(readprevcell.getCellFormat());
					  }else{
						  oldformat=false;
					  }
				  }catch(AssertionFailed e1){
					  oldformat=false;
				  }
			  }
			
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  Label value = new Label(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
					  try {
						  if(!(hl==null)){
							  sheet.addHyperlink(hl);
						  }
						  sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("format")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y, whattowrite);
				  newformat=nf;
				  if(oldformat){
					  value.setCellFormat(newformat);
				  }
				  
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			 
			 
			  
			 // WritableSheet sheet[retsheet];
		  }
		public static void writecellcorrect(WritableSheet sheet, int x, int y, String whattowrite, String type, WritableCellFormat nf){
			  
			 
			
			
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  
			  Cell readcell=sheet.getCell(x, y);
			  int prevnum=y-1;
			  Cell readprevcell=sheet.getCell(x,prevnum);
			  WritableCellFormat newformat;
			  try{
				  newformat=new WritableCellFormat(readcell.getCellFormat());
			  }catch(AssertionFailed e){
				  newformat=new WritableCellFormat(readprevcell.getCellFormat());
			  }
			 
			  
			  if (type.equals("string")&&!whattowrite.equals("")){
				 
				  Label value = new Label(x,y,whattowrite);
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("double")&&!whattowrite.equals("")){
				  if(whattowrite.equals("")){
					  whattowrite="0";
				  }
				  Number value = new Number(x,y,u.d(whattowrite));
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("formula")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y,whattowrite);
				  value.setCellFormat(newformat);
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			  if (type.equals("format")&&!whattowrite.equals("")){
				  Formula value = new Formula(x,y, whattowrite);
				  newformat=nf;
				  value.setCellFormat(newformat);
				  
				  try {
						sheet.addCell(value);
					  } catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  } catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
			  } 
			 
			 
			  
			 // WritableSheet sheet[retsheet];
		  }
		 public static String readsheetandcell(Sheet sheet, char xchar, int yint, String type, WritableCellFormat nf){
			  
			 
			 
			  int retsheet;
			  int retx;
			  int rety;
			  
			 
			  retx=(int)xchar-97;
			  rety=yint-1;
			  
			  //WritableSheet sheet=copy.createSheet(u.s(retsheet), retsheet);
			  
			  Cell readcell = null;
			  try{
				  readcell=sheet.getCell(retx, rety);
			
				  String contents=readcell.getContents().toString();
				  
				  return contents;
			  }catch(ArrayIndexOutOfBoundsException e){
				  return "";
			  }
			  
			 // WritableSheet sheet[retsheet];
		  }
		 public static void copytemplatesfromAssets(String ProjectDirectory, List<String> docslist, Context ctx) throws IOException{
			  new File(ProjectDirectory).mkdirs(); 
				for(int num=0;num<docslist.size();num++){
						
						if(!docslist.get(num).equals("kioskmode")){
							if(!(new File(ProjectDirectory + "/"+docslist.get(num)).exists())){
							Log.d("copying doc",docslist.get(num));
							InputStream input =ctx.getResources().getAssets().open(docslist.get(num));
				           
							OutputStream output = new FileOutputStream(ProjectDirectory + "/"+docslist.get(num));
	
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
							}
						}
				}
		  }
		 public static void copyfilefromAssets(String Assetname, String Path, Context ctx) throws IOException{
			 
			 InputStream input =ctx.getResources().getAssets().open(Assetname);
	           
				OutputStream output = new FileOutputStream(Path);

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
		 }
		 public static void copymasterlistfromAssets(String MasterDirectory, Context ctx) throws IOException{
			  new File(MasterDirectory).mkdirs(); 
			  Log.d("copying doc","Master_Lists.xls");	
			  InputStream input =ctx.getResources().getAssets().open("Master_Lists.xls");
			  OutputStream output = new FileOutputStream(MasterDirectory+"Master_Lists.xls");
			  Log.d("output for master list is",MasterDirectory+"Master_Lists.xls");	
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
		 }
		 
		 public static void copymorrisonsIPfromAssets(String MasterDirectory, Context ctx) throws IOException{
			  new File(MasterDirectory).mkdirs(); 
			  Log.d("copying doc","Morrisons_IP.xls");	
			  InputStream input =ctx.getResources().getAssets().open("Morrisons_IP.xls");
			  OutputStream output = new FileOutputStream(MasterDirectory+"/Morrisons_IP.xls");
			  log("morrisons ip inticipated path location"+MasterDirectory+"/Morrisons_IP.xls");
			  
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
		 }
		 
		 public static void copymorrisonsEnergydatafromAssets(String MasterDirectory, Context ctx) throws IOException{
			  new File(MasterDirectory).mkdirs(); 
			  Log.d("copying doc","Morrisons - Estate Energy Data.xls");	
			  InputStream input =ctx.getResources().getAssets().open("Morrisons - Estate Energy Data.xls");
			  OutputStream output = new FileOutputStream(MasterDirectory+"/Morrisons - Estate Energy Data.xls");
	
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
		 }
		 
		 public static WritableSheet[] openallsheets(WritableWorkbook workbook){
			 
			 WritableSheet[] writesheet=new WritableSheet[workbook.getNumberOfSheets()];
			 for(int num=0;num<workbook.getNumberOfSheets();num++){
				 writesheet[num]=workbook.getSheet(num);
			 }
			 
			 return writesheet;
		 }
		 public static String gettextfromview(View view){
			 String string = null;
				if (view instanceof EditText) {
				    EditText edittext = (EditText) view;
				    string=edittext.getText().toString();
				}
				else if (view instanceof TextView) {
				    TextView textview = (TextView) view;
				    string=textview.getText().toString();
				}
				else if (view instanceof AutoCompleteTextView) {
					AutoCompleteTextView textview = (AutoCompleteTextView) view;
				    string=textview.getText().toString();
				}
				else if (view instanceof Spinner){
					Spinner spinner = (Spinner) view;
					string=spinner.getSelectedItem().toString();
				}
		 return string;
		 }
		 public static void settexttoview(String string,View view){
			 
				if (view instanceof EditText) {
				    ((EditText) view).setText(string);
				}
				else if (view instanceof TextView) {
				    ((TextView) view).setText(string);
				}
				else if (view instanceof Spinner){
					
					((Spinner) view).setSelection(getIndexofSpinner(((Spinner) view),string));
					
				}else{
					
				}
		 
		 }
		 public static void settexttoviewordoub(String string,View view){
			 
				if (view instanceof EditText) {
				    ((EditText) view).setText(string);
				}
				else if (view instanceof TextView) {
				    ((TextView) view).setText(string);
				}
				else if (view instanceof Spinner){
					if(!string.equals("")){
						((Spinner) view).setSelection(getIndexofSpinner(((Spinner) view),string));
					}
				}else{
					
				}
		 
		 }
		
		
		 public static boolean isDouble( String input )  
		 {  
		    try  
		    {  
		       Double.parseDouble( input );  
		       return true;  
		    }  
		    catch(Exception e)  
		    {  
		       return false;  
		    }  
		 }  
		 static int getIndexofSpinner(Spinner spinner, String myString) {
			 	if(myString==null){
			 		myString="";
			 	}
				int index = 0;
				System.out.println("count on spinner "+spinner.getCount());
				for (int i = 0; i < spinner.getCount(); i++) {

					if (spinner.getItemAtPosition(i).toString().equals(myString)) {

						index = i;
					}
				}
				//if spinner doesn't have the string, add it
				if(index==0&&!spinner.getItemAtPosition(index).toString().trim().equals(myString)){
					ArrayAdapter<String> adapter=(ArrayAdapter<String>) spinner.getAdapter();
					adapter.add(myString);
					spinner.setAdapter(adapter);
					index=getIndexofSpinner(spinner, myString);
				}
				return index;
			}
		 public static int getallchildrencount(ViewGroup parent){
				int count=0;
			
				for(int num=0; num<parent.getChildCount(); num++){
					
					if (!(parent.getChildAt(num) instanceof ViewGroup)){
						
						
						count++;
					}
					
					if (parent.getChildAt(num) instanceof ViewGroup){
						
						count=count+getallchildrencount((ViewGroup)parent.getChildAt(num));
					}
				}
				return count;
				
			}
		 public static List<View> getallchildren(ViewGroup parent){
				List<View> children = new ArrayList<View>();
				int n=0;
				for(int num=0; num<parent.getChildCount(); num++){
					
					if (!((parent.getChildAt(num) instanceof ViewGroup)||(parent.getChildAt(num) instanceof ImageView))){
						
						children.add(parent.getChildAt(num));
						
						
					}
					
					if (parent.getChildAt(num) instanceof ViewGroup){
						
						children.addAll(getallchildren((ViewGroup) parent.getChildAt(num)));
					}
					
				}
				return children;
				
			}
		 public static List<View> getallchildrenwimageview(ViewGroup parent){
				List<View> children = new ArrayList<View>();
				int n=0;
				for(int num=0; num<parent.getChildCount(); num++){
					
					if (!((parent.getChildAt(num) instanceof ViewGroup))){
						
						children.add(parent.getChildAt(num));
						
						
					}
					
					if (parent.getChildAt(num) instanceof ViewGroup){
						
						children.addAll(getallchildren((ViewGroup) parent.getChildAt(num)));
					}
					
				}
				return children;
				
			}
		 public static List<View> getallchildrenwithspinners(ViewGroup parent){
				List<View> children = new ArrayList<View>();
				int n=0;
				for(int num=0; num<parent.getChildCount(); num++){
					
					if (!((parent.getChildAt(num) instanceof ViewGroup)||(parent.getChildAt(num) instanceof ImageView)||(parent.getChildAt(num) instanceof Spinner))){
						children.add(parent.getChildAt(num));
					}
					
					if (parent.getChildAt(num) instanceof Spinner){
						children.add(parent.getChildAt(num));
						Log.d("Spinner Added","++++++++++++++++++");
					}
					
					
					if (parent.getChildAt(num) instanceof ViewGroup&&!(parent.getChildAt(num) instanceof Spinner)){
						
						children.addAll(getallchildrenwithspinners((ViewGroup) parent.getChildAt(num)));
					}
					
					
					
				}
				return children;
				
			}
		 public static List<View> getallchildrenandviewgroups(ViewGroup parent){
				List<View> children = new ArrayList<View>();
				
				for(int num=0; num<parent.getChildCount(); num++){
					
					if (!((parent.getChildAt(num) instanceof ViewGroup)||(parent.getChildAt(num) instanceof ImageView))){
						
						children.add(parent.getChildAt(num));
						
						
					}
					
					if (parent.getChildAt(num) instanceof ViewGroup){
						
						children.add(parent.getChildAt(num));
						children.addAll(getallchildrenandviewgroups((ViewGroup) parent.getChildAt(num)));
					}
					
				}
				return children;
				
			}
		 public static List<ImageView> getallimageviewchildren(ViewGroup parent){
				List<View> children = new ArrayList<View>();
				List<ImageView>imageviewchildren=new ArrayList<ImageView>();
				children=getallchildren(parent);
				for(int num=0; num<children.size();num++){
					if(children.get(num) instanceof ImageView){
						imageviewchildren.add((ImageView)children.get(num));
					}
				}
				return imageviewchildren;
			}
		 private void getDir(String dirPath)
		    {
			 	
			 	String root="/";
			 	List<String> item = null;
				List<String> path = null;
		    	
		    	item = new ArrayList<String>();
		    	path = new ArrayList<String>();
		    	
		    	File f = new File(dirPath);
		    	File[] files = f.listFiles();
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
			    			path.add(file.getPath());
			    			item.add(file.getName());
			    			
			    	}
		    	}
		    	
		    	
		    }
		
		 public static String getfilenamefrompath(String path){
			  String[] cut=path.split("/");
			  String name=cut[cut.length-1];
			  return name;
		  }
		 public static String getMimeType(String url)
		 {
		     String type = null;
		     String filenameArray[] = url.split("\\.");
		        String extension = filenameArray[filenameArray.length-1];
		     Log.d("extension",extension);
		     if (extension != null) {
		         MimeTypeMap mime = MimeTypeMap.getSingleton();
		         type = mime.getMimeTypeFromExtension(extension);
		     }
		     return type;
		 }
		
		public static String[] listfolders(String path){
			File file = new File(path);
			String[] directories = file.list(new FilenameFilter() {
			  public boolean accept(File dir, String name) {
			    return new File(dir, name).isDirectory();
			  }
			});
			for (int i=0;i<directories.length; i++){
				directories[i]=path+"/"+directories[i];
			}
			System.out.println(Arrays.toString(directories));
			return directories;
		}
		public void showToast(final String toast) {
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), toast,
							Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		public static ArrayList<String> getcol(Sheet sheet, int columnindex, int rowstart){
			 
			 int y=rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 String value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
			 String nextvalue=value;
		
			 while(!(nextvalue.equals(""))){
				value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				column.add(value);
				y++;
				try{
					nextvalue=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				}catch(ArrayIndexOutOfBoundsException e){
					nextvalue="";
				}
			}
		
			return column;
		 }
		
		public static ArrayList<String> mgetcol(Sheet sheet, int columnindex, int rowstart){
			 
			 int y=rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 String value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
			 String nextvalue=value;
		     System.out.println("energy values,"+nextvalue);
			 while(!(nextvalue.equals(""))){
				value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				column.add(value);
				y++;
				try{
					nextvalue=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
					System.out.println("energy values,"+nextvalue);
				}catch(ArrayIndexOutOfBoundsException e){
					nextvalue="";
				}
			}
		
			return column;
		 }
		
		public static ArrayList<String> getcolwblanks(Sheet sheet, int columnindex, int rowstart, int allowedconsecutiveblanks){
			 Log.d("getcolstarted","");
			 int y=rowstart;
			 ArrayList<String> column=new ArrayList<String>();
			 String value;
		     int numofblanks=0;
		     Log.d("numofblanks=",u.s(numofblanks));
			 loop:while(numofblanks<allowedconsecutiveblanks){
				
				 try{
					 value=sheet.getCell(u.cellid(columnindex, y)).getContents().toString();
				 }catch(Throwable e){
					 break loop;
				 }
				 if((value.equals("")||value==null)){
						numofblanks++;
					}else{			
						numofblanks=0;
					}
				 Log.d("numofblanks=",u.s(numofblanks));
				 Log.d("value",value);

				 column.add(value);
				y++;
				
				
			}
		
			return column;
		 }
	public static InputStream filetoinputstream(String path){
		InputStream is = null;
	
	    try {
	        is = new FileInputStream(path);
	
	        is.close();
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	
	    return is;
	}
	public static boolean isbluestacks(){
		boolean isbluestacks=false;
		File bluestacksfolder=new File(Environment.getExternalStorageDirectory()+"/bstfolder");
		Log.d("bluestacksfolder",bluestacksfolder.getAbsolutePath());
		if (bluestacksfolder.exists()){
			isbluestacks=true;
		}
		return isbluestacks;
	}
	public static void copyDirectory(File sourceLocation , File targetLocation)
			throws IOException {

			    if (sourceLocation.isDirectory()) {
			        if (!targetLocation.exists() && !targetLocation.mkdirs()) {
			            throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
			        }

			        String[] children = sourceLocation.list();
			        for (int i=0; i<children.length; i++) {
			            copyDirectory(new File(sourceLocation, children[i]),
			                    new File(targetLocation, children[i]));
			        }
			    } else {

			        // make sure the directory we plan to store the recording in exists
			        File directory = targetLocation.getParentFile();
			        if (directory != null && !directory.exists() && !directory.mkdirs()) {
			            throw new IOException("Cannot create dir " + directory.getAbsolutePath());
			        }

			        InputStream in = new FileInputStream(sourceLocation);
			        OutputStream out = new FileOutputStream(targetLocation);

			        // Copy the bits from instream to outstream
			        byte[] buf = new byte[4096];
			        int len;
			        while ((len = in.read(buf)) > 0) {
			            out.write(buf, 0, len);
			        }
			        in.close();
			        out.close();
			    }
			}
	public static void copyDirectory_Cloud(File sourceLocation , File targetLocation)
			throws IOException {

			    if (sourceLocation.isDirectory()) {
			    	if(targetLocation.exists()){
			    		deleteDirectory(targetLocation);
			    	}
			    	if (!targetLocation.exists() && !targetLocation.mkdirs()) {
			            throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
			        }

			        String[] children = sourceLocation.list();
			        for (int i=0; i<children.length; i++) {
			            copyDirectory(new File(sourceLocation, children[i]),
			                    new File(targetLocation, children[i]));
			        }
			    } else {

			        // make sure the directory we plan to store the recording in exists
			        File directory = targetLocation.getParentFile();
			        if (directory != null && !directory.exists() && !directory.mkdirs()) {
			            throw new IOException("Cannot create dir " + directory.getAbsolutePath());
			        }

			        InputStream in = new FileInputStream(sourceLocation);
			        OutputStream out = new FileOutputStream(targetLocation);

			        // Copy the bits from instream to outstream
			        byte[] buf = new byte[4096];
			        int len;
			        while ((len = in.read(buf)) > 0) {
			            out.write(buf, 0, len);
			        }
			        in.close();
			        out.close();
			    }
			}
	public static List<View> getallchildrenwithspinnersthatdonthavevalues(ViewGroup parent){
		List<View> children = new ArrayList<View>();
		int n=0;
		for(int num=0; num<parent.getChildCount(); num++){
			
			if (!((parent.getChildAt(num) instanceof ViewGroup)||(parent.getChildAt(num) instanceof ImageView)||(parent.getChildAt(num) instanceof Spinner)||(parent.getChildAt(num) instanceof ProgressBar))){
				String str=((TextView)parent.getChildAt(num)).getText().toString();
				if(str == null || str.trim().isEmpty()){
					children.add(parent.getChildAt(num));
					
				}
			
			}
			
			if (parent.getChildAt(num) instanceof Spinner){
				int spinpos=((Spinner)parent.getChildAt(num)).getSelectedItemPosition();
				if (spinpos==0){
					children.add(parent.getChildAt(num));
				}
			
			}
			
			
			if (parent.getChildAt(num) instanceof ViewGroup&&!(parent.getChildAt(num) instanceof Spinner)){
				
				children.addAll(getallchildrenwithspinnersthatdonthavevalues((ViewGroup) parent.getChildAt(num)));
			}
			
			
			
		}
		return children;
		
	}
	
	public static List<View> getallchildrenwithspinnerstoclear(ViewGroup parent){
		List<View> children = new ArrayList<View>();
		int n=0;
		for(int num=0; num<parent.getChildCount(); num++){
			
			if (!((parent.getChildAt(num) instanceof ViewGroup)||(parent.getChildAt(num) instanceof ImageView)||(parent.getChildAt(num) instanceof Spinner)||(parent.getChildAt(num) instanceof ProgressBar))){
				
				children.add(parent.getChildAt(num));
			}
			
			if (parent.getChildAt(num) instanceof Spinner){
				children.add(parent.getChildAt(num));
			}
			if (parent.getChildAt(num) instanceof ViewGroup&&!(parent.getChildAt(num) instanceof Spinner)){
				children.addAll(getallchildrenwithspinnerstoclear((ViewGroup) parent.getChildAt(num)));
			}
			
			
			
		}
		return children;
		
	}
	public static String priceWithDecimal (Double price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}
	public static void removeview(View view){
		((ViewGroup)view.getParent()).removeViewInLayout(view);
	}
	static LinkedHashMap<Integer, Double> sortByComparator(HashMap<Integer, Double> unsortMap, final boolean order)
    {

        List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Integer, Double>>()
        {
            public int compare(Entry<Integer, Double> o1,
                    Entry<Integer, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Entry<Integer, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	public double getMean(double[] numberList) {
	    double total = 0;
	    for (double d: numberList) {
	        total += d;
	    }
	    return total / (numberList.length);
	}
	public double getMedian(double[] numberList) {
	    int factor = numberList.length - 1;
	    double[] first = new double[(int)((double)factor / 2)];
	    double[] last = new double[first.length];
	    double[] middleNumbers = new double[1];
	    for (int i = 0; i < first.length; i++) {
	        first[i] = numberList[i];
	    }
	    for (int i = numberList.length; i > last.length; i--) {
	        last[i] = numberList[i];
	    }
	    for (int i = 0; i <= numberList.length; i++) {
	        if (numberList[i] != first[i] || numberList[i] != last[i]) middleNumbers[i] = numberList[i];
	    }
	    if (numberList.length % 2 == 0) {
	        double total = middleNumbers[0] + middleNumbers[1];
	        return total / 2;
	    } else {
	        return middleNumbers[0];
	    }
	}
	public static double getMode(double[] numberList) {
	    HashMap<Double,Double> freqs = new HashMap<Double,Double>();
	    for (double d: numberList) {
	        Double freq = freqs.get(d);
	        freqs.put(d, (freq == null ? 1 : freq + 1));   
	    }
	    double mode = 0;
	    double maxFreq = 0;    
	    for (Map.Entry<Double,Double> entry : freqs.entrySet()) {     
	        double freq = entry.getValue();
	        if (freq > maxFreq) {
	            maxFreq = freq;
	            mode = entry.getKey();
	        }
	    }    
	    return mode;
	}
	public double getRange(double[] numberList) {
	    double initMin = numberList[0];
	    double initMax = numberList[0];
	    for (int i = 1; i <= numberList.length; i++) {
	        if (numberList[i] < initMin) initMin = numberList[i];
	        if (numberList[i] > initMax) initMax = numberList[i];
	    }
	    return initMax - initMin;
	}       
	static Bitmap getBitmapFromAsset(String strName, Context ctx)
    {
        AssetManager assetManager = ctx.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	 static int drawMultilineText(String str, int x, int y, Paint paint, Canvas canvas, int fontSize, Rect drawSpace) {
		    int      lineHeight = 0;
		    int      yoffset    = 0;
		    String[] lines      = str.split(" ");

		    // set height of each line (height of text + 20%)
		    lineHeight = (int) (calculateHeightFromFontSize(str, fontSize) * 1.2);
		    // draw each line
		    String line = "";
		    for (int i = 0; i < lines.length; ++i) {

		        if(calculateWidthFromFontSize(line + " " + lines[i], fontSize) <= drawSpace.width()){
		            line = line + " " + lines[i];

		        }else{
		            canvas.drawText(line, x, y + yoffset, paint);
		            yoffset = yoffset + lineHeight;
		            line = lines[i];
		        }
		    }
		    canvas.drawText(line, x, y + yoffset, paint);
		    yoffset = yoffset + lineHeight;
			return yoffset;

		}

		private static int calculateWidthFromFontSize(String testString, int currentSize)
		{
		    Rect bounds = new Rect();
		    Paint paint = new Paint();
		    paint.setTextSize(currentSize);
		    paint.getTextBounds(testString, 0, testString.length(), bounds);

		    return (int) Math.ceil( bounds.width());
		}

		private static int calculateHeightFromFontSize(String testString, int currentSize)
		{
		    Rect bounds = new Rect();
		    Paint paint = new Paint();
		    paint.setTextSize(currentSize);
		    paint.getTextBounds(testString, 0, testString.length(), bounds);

		    return (int) Math.ceil( bounds.height());
		}

		public static float getwidthofpainttext(Paint p, String text){
			float[] widths = new float[text.length()];
			float totalwidth = 0;
			p.getTextWidths(text, widths);

			for (int u = 0; u < widths.length; u++) {
				totalwidth = totalwidth + widths[u];
			}
			return totalwidth;
		}
		
		public static String calculateandsetCTvalues(int br, Double percentdeviation) {

			String ctamps="10";
			int ctamp = 0;
			String ctphy = "SCT-0750-" + ctamps;
			if (percentdeviation * br <= 2000) {
			if (percentdeviation * br <= 1000) {
			if (percentdeviation * br <= 600) {
			if (percentdeviation * br <= 400) {
			if (percentdeviation * br <= 300) {
			if (percentdeviation * br <= 250) {
			if (percentdeviation * br <= 200) {
			if (percentdeviation * br <= 150) {
			if (percentdeviation * br <= 100) {
			if (percentdeviation * br <= 70) {
			if (percentdeviation * br <= 50) {
			if (percentdeviation * br <= 30) {
			if (percentdeviation * br <= 10) {
			ctamp = 10;
			} else
			ctamp = 30;
			} else
			ctamp = 50;
			} else
			ctamp = 70;
			} else
			ctamp = 100;
			} else
			ctamp = 150;
			} else
			ctamp = 200;
			} else
			ctamp = 250;
			} else
			ctamp = 300;
			} else
			ctamp = 400;
			} else
			ctamp = 600;
			} else
			ctamp = 1000;
			} else
			ctamp = 2000;
			} else{
				ctamps = "2000";
			}
			
			ctamps = String.format("%03d", ctamp);

			int[] rcssizes={400,1000,2000};
			int[] sct2000sizes={200,250,300,400,600,1000};
			int[] sct1250sizes={50,70,100,150,200,250,300,400,600};
			int[] sct0750sizes={10,30,50,70,100,150,200};
			
			boolean sizefound=false;
			int closestsizeup=0;
			System.out.println("ctamp before check = "+ctamp);
			if (ctamp >= 2000/percentdeviation) {
				ctamps="2000";
				for (int h=0; h<rcssizes.length; h++){
					if(u.i(ctamps)==rcssizes[h]){
						sizefound=true;
						break;
					}
					try{
						if(rcssizes[h-1]<u.i(ctamps)&&rcssizes[h]>u.i(ctamps)){
							closestsizeup=rcssizes[h];
						}
					}catch(Throwable e){
						if(rcssizes[h]>u.i(ctamps)){
							closestsizeup=rcssizes[h];
						}
					}
				}
				if(!sizefound){
					ctamps=u.s(closestsizeup);
				}
				ctphy = "RCS-1800-" + ctamps;
			
			}else if (ctamp >= 1000) {
				for (int h=0; h<rcssizes.length; h++){
					if(u.i(ctamps)==rcssizes[h]){
						sizefound=true;
						break;
					}
					try{
						if(rcssizes[h-1]<u.i(ctamps)&&rcssizes[h]>u.i(ctamps)){
							closestsizeup=rcssizes[h];
						}
					}catch(Throwable e){
						if(rcssizes[h]>u.i(ctamps)){
							closestsizeup=rcssizes[h];
						}
					}
				}
				if(!sizefound){
					ctamps=u.s(closestsizeup);
				}
				ctphy = "RCS-1800-" + ctamps;
			
			}else if (ctamp > 200) {
						for (int h=0; h<sct1250sizes.length; h++){
							if(u.i(ctamps)==sct1250sizes[h]){
								sizefound=true;
								break;
							}
							try{
								if(sct1250sizes[h-1]<u.i(ctamps)&&sct1250sizes[h]>u.i(ctamps)){
									closestsizeup=sct1250sizes[h];
								}
							}catch(Throwable e){
								if(sct1250sizes[h]>u.i(ctamps)){
									closestsizeup=sct1250sizes[h];
								}
							}
						}
						if(!sizefound){
							ctamps=u.s(closestsizeup);
						}
						ctphy = "SCT-1250-" + ctamps;
					} else {
						if (ctamp >= 0) {
							for (int h=0; h<sct0750sizes.length; h++){
								if(u.i(ctamps)==sct0750sizes[h]){
									sizefound=true;
									break;
								}
								try{
									if(sct0750sizes[h-1]<u.i(ctamps)&&sct0750sizes[h]>u.i(ctamps)){
										closestsizeup=sct0750sizes[h];
									}
								}catch(Throwable e){
									if(sct0750sizes[h]>u.i(ctamps)){
										closestsizeup=sct0750sizes[h];
									}
								}
							}
							if(!sizefound){
								ctamps=u.s(closestsizeup);
							}
							ctphy = "SCT-0750-" + ctamps;
						}
					}
				
///////////////////////////			
//Ibe's CT Type bom insertion			
//totalSAM
//////////////////////////
			u.log("ctphy, "+ctphy);
			return ctphy;
			
	}
		static int getIndex(Spinner spinner,String string){

			//Pseudo code because I dont remember the API

			int index = 0;
			
			System.out.println("childcount on spinner"+spinner.getChildCount());
			
			for (int i = 0; i < spinner.getCount(); i++){

			if (spinner.getItemAtPosition(i).equals(string)){
			   index = i;
			}

			}

			return index;

			}
		public static CustomTextWatcher cttypewatcher(final EditText et, final Spinner sp, double ctdownsizepercent){
			CustomTextWatcher ctw=new CustomTextWatcher(et, sp){

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
					String cttype=u.calculateandsetCTvalues(u.i(et.getText().toString()),Tabs1.ctdownsizepercent);
					System.out.println("calculated ct string "+cttype);
					sp.setSelection(u.getIndex(sp,cttype));
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}};
			return ctw;
			
		}
		public static CustomTextWatcher zonetypewatcher(final EditText et, final Spinner sp){
			CustomTextWatcher ctw=new CustomTextWatcher(et, sp){

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					String zontypestring=" ";
					try{
						zontypestring=Tabs1.correspondingtypes.get(1);
						for(int c=0;c<Tabs1.commontemplocations.size();c++){
							if(et.getText().toString().contains(Tabs1.commontemplocations.get(c))){
								zontypestring=Tabs1.correspondingtypes.get(c+1);
								break;
							}
						}
						
					}catch(Throwable e){
						
					}
					
					u.settexttoview(zontypestring, sp);
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}};
			return ctw;
			
		}
		
		
		public static TextWatcher charactercounttextwatcher(int count, final TextView current, final TextView next, final AlertDialog dialog){
			TextWatcher tw=new TextWatcher(){
		    public void onTextChanged(CharSequence s, int start,int before, int count) 
		    {
		        // TODO Auto-generated method stub
		        if(current.getText().toString().length()==count)     //size as per your requirement
		        {
		            next.requestFocus();
		            System.out.println(current+" "+next);
		            if(current==next){
			        
						 dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
			
		            }
		            }
		        	
		        
		    }
		    public void beforeTextChanged(CharSequence s, int start,
		                    int count, int after) {
		                // TODO Auto-generated method stub

		    }

		    public void afterTextChanged(Editable s) {
		                // TODO Auto-generated method stub
		    }
			
		
			
			};
			return tw;
			}
	
		
		public void set(String PreferencesTable ,String prefname, Object value){
	    	
	     	SharedPreferences.Editor editor;
	    	SharedPreferences settings;
	    	
	    	settings = getSharedPreferences(PreferencesTable, 0);
	    	editor=settings.edit();
	    	
	    	if(value instanceof Boolean){
	    		editor.putBoolean(prefname, (Boolean) value);
	    	}
	    	if(value instanceof Float){
	    		editor.putFloat(prefname, (Float) value);
	    	}
	    	if(value instanceof Integer){
	    		editor.putInt(prefname, (Integer) value);
	    	}
	    	if(value instanceof Long){
	    		editor.putLong(prefname, (Long) value);
	    	}
	    	if(value instanceof String){
	    		editor.putString(prefname, (String) value);
	    	}
	    	if(value instanceof Set<?>){
	    		editor.putStringSet(prefname, (Set<String>) value);
	    	}
	    	
	    	
	    	editor.commit();

	    }
		public Object get(String PreferencesTable ,String prefname){
	    	Object object=null;
			
	     	SharedPreferences.Editor editor;
	    	SharedPreferences settings;
	    	
	    	settings = getSharedPreferences(PreferencesTable, 0);
	    	editor=settings.edit();
	    	
	    	object=settings.getString(prefname, null);
	    	
	    	return object;

	    }

		public static Object get(String PreferencesTable ,String prefname, Context ctx){
	    	Object object=null;
			
	     	SharedPreferences.Editor editor;
	    	SharedPreferences settings;
	    	
	    	settings = ctx.getSharedPreferences(PreferencesTable, 0);
	    	editor=settings.edit();
	    	
	    	object=settings.getString(prefname, null);
	    	
	    	return object;

	    }
		public static void set(String PreferencesTable, String prefname,
				Object value, Context ctx) {
			// TODO Auto-generated method stub
			SharedPreferences.Editor editor;
	    	SharedPreferences settings;
	    	
	    	settings = ctx.getSharedPreferences(PreferencesTable, 0);
	    	editor=settings.edit();
	    	
	    	if(value instanceof Boolean){
	    		editor.putBoolean(prefname, (Boolean) value);
	    	}
	    	if(value instanceof Float){
	    		editor.putFloat(prefname, (Float) value);
	    	}
	    	if(value instanceof Integer){
	    		editor.putInt(prefname, (Integer) value);
	    	}
	    	if(value instanceof Long){
	    		editor.putLong(prefname, (Long) value);
	    	}
	    	if(value instanceof String){
	    		editor.putString(prefname, (String) value);
	    	}
	    	if(value instanceof Set<?>){
	    		editor.putStringSet(prefname, (Set<String>) value);
	    	}
	    	
	    	
	    	editor.commit();
		}
		static void DeleteRecursive(File fileOrDirectory) {
		    if (fileOrDirectory.isDirectory())
		        for (File child : fileOrDirectory.listFiles())
		            DeleteRecursive(child);

		    final File to = new File(fileOrDirectory.getAbsolutePath() + System.currentTimeMillis());
		    fileOrDirectory.renameTo(to);
		    to.delete();
		   
		}
		public void zip(String[] _files, String _zipFile) {
			try {
				int BUFFER = 2048;
				BufferedInputStream origin = null;
				FileOutputStream dest = new FileOutputStream(_zipFile);

				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
						dest));

				byte data[] = new byte[BUFFER];

				for (int i = 0; i < _files.length; i++) {
					Log.v("Compress", "Adding: " + _files[i]);
					Log.v("Compress", "to: " + _zipFile);
					FileInputStream fi = new FileInputStream(_files[i]);
					origin = new BufferedInputStream(fi, BUFFER);
					ZipEntry entry = new ZipEntry(_files[i].substring(_files[i]
							.lastIndexOf("/") + 1));
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				}
				dest.close();
				out.close();
			} catch (Exception e) {
				
			}

		}
		public static void databaseexport(String internaldbpath, String externaldbpath, Context ctx) {
			try {
				File sd = Environment.getExternalStorageDirectory();
				File data = Environment.getDataDirectory();

				if (sd.canWrite()) {
					
					File internaldb = new File(data, internaldbpath);
					File externaldb = new File(externaldbpath);
					if (externaldb.exists() && internaldb.exists()) {
						externaldb.delete();
					}
					FileChannel intl = new FileInputStream(internaldb).getChannel();
					FileChannel ext = new FileOutputStream(externaldb).getChannel();
					ext.transferFrom(intl, 0, intl.size());
					intl.close();
					ext.close();
					Toast.makeText(ctx, "Save Successful",
							Toast.LENGTH_LONG).show();
					log("databaseexport, "+externaldb.toString());
					log("databaseimport, "+internaldb.toString());
					
				}
			} catch (Exception e) {
			}
			try{
			File olddbfile=new File(Tabs1.ProjectDirectory + "/"
			+ Tabs1.basedirectory[Tabs1.GENERATEDDOCUMENTSFOLDER]+"/database.db");
			if(olddbfile.exists()){
				olddbfile.delete();
			}
			}catch(Throwable e){
				
			}
			
		}
		
		public static Boolean containscaseinsensitive(String searchstring,String variablestring){
			Boolean result =false;
			if(variablestring.contains(Character.toString(searchstring.charAt(0)))){
				int firstpos =variablestring.indexOf(Character.toString(searchstring.charAt(0)));
				int checklength=0;
				int searchindex=0;
				System.out.println("firstpos,"+firstpos);
				
				for(int t=firstpos; t<firstpos+searchstring.length();t++){
					try{
					if(Character.toString(searchstring.charAt(searchindex)).equalsIgnoreCase(Character.toString(variablestring.charAt(t)))){
						checklength++;
						System.out.println("matched char,"+Character.toString(variablestring.charAt(t))+",at,"+t);	
					}
					}catch(Throwable e){
						if(t<5){
							//TaskDialogs.showException(e);
						}
					}
					searchindex++;
				}
				System.out.println("Checklength,"+checklength);
				if(checklength==searchstring.length()){
					result=true;
				}
				
			}
			char caseinversed=searchstring.charAt(0);
			if (Character.isUpperCase(caseinversed))
	        {
				caseinversed = Character.toLowerCase(caseinversed);
	        }
	        else if (Character.isLowerCase(caseinversed))
	        {
	        	caseinversed = Character.toUpperCase(caseinversed);
	        }
			
			if(variablestring.contains(Character.toString(caseinversed))){
				int firstpos =variablestring.indexOf(Character.toString(caseinversed));
				int checklength=0;
				int searchindex=0;
				System.out.println("firstpos,"+firstpos);
				
				for(int t=firstpos; t<firstpos+searchstring.length();t++){
					try{
					if(Character.toString(searchstring.charAt(searchindex)).equalsIgnoreCase(Character.toString(variablestring.charAt(t)))){
						checklength++;
						System.out.println("matched char,"+Character.toString(variablestring.charAt(t))+",at,"+t);	
					}}catch(Throwable e){
						if(t<5){
							//TaskDialogs.showException(e);
						}
						}
					searchindex++;
				}
				System.out.println("Checklength,"+checklength);
				if(checklength==searchstring.length()){
					result=true;
				}
				
			}
			return result;
		}
		
		public static void copyfilesfromasset(String MasterDirectory, String Filename,
				Context ctx) {

			try {
				new File(MasterDirectory).mkdirs();

				InputStream input = ctx.getResources().getAssets().open(Filename);
				OutputStream output = new FileOutputStream(MasterDirectory + "/"
						+ Filename);

				byte data[] = new byte[1024];
				int count;
				while ((count = input.read(data)) != -1) {
					output.write(data, 0, count);

				}

				output.flush();
				output.close();
				input.close();
			} catch (Throwable e) {
				
			}

		}
		static public boolean deleteDirectory(File path) {
		    if( path.exists() ) {
		      File[] files = path.listFiles();
		      if (files == null) {
		          return true;
		      }
		      for(int i=0; i<files.length; i++) {
		         if(files[i].isDirectory()) {
		           deleteDirectory(files[i]);
		         }
		         else {
		        	 final File to = new File(files[i].getAbsolutePath() + System.currentTimeMillis());
		        	 files[i].renameTo(to);
		        	 to.delete();
		        	 
		          
		         }
		      }
		    }
		    return( path.delete() );
		  }
		static void databaseimport(String masterfoldername, String foldername, Context ctx) {
	        // TODO Auto-generated method stub
			u.log("databaseimportstarted");
			databaseimports=false;
	        try {
	            File sd = Environment.getExternalStorageDirectory();
	            File data  = Environment.getDataDirectory();

	            if (sd.canWrite()) {
	            	 String  internaldbpath= "//data//" +ctx.getPackageName()
	                         + "//databases//" + foldername;
	            	 Log.d("externaldb",internaldbpath);
	            	 String ProjectDirectory = masterfoldername + foldername;
	         		 String DatabaseDirectory=ProjectDirectory+ "/"
	        					+ Tabs1.basedirectory[Tabs1.GENERATEDDOCUMENTSFOLDER];
	            	log("DatabaseDirectory ,"+DatabaseDirectory);
	         		 int maxdbs=5;
	            	 String[] databasename= new String[maxdbs];
	        		for(String string:databasename){
	        			string=null;
	        		}
	         		
	         		try {
	        			databasename = new File(DatabaseDirectory).list(new FilenameFilter() {
	        			public boolean accept(File directory, String fileName) {
	        				return fileName.endsWith(".db");
	        			}
	        		  });
	        		}catch(Throwable e){
	        			
	        		}
	        		String olddatabasename="database.db";
	        		String finaldatabasename=olddatabasename;
	        		try{
	        			if(!(databasename[0].equals(null)))
	        			{
	        			   for(int d=0;d<databasename.length;d++){
	        				   if(!(databasename[d].equals(olddatabasename))){
	        					  log("databasecheck, "+databasename[d]);
	        					   finaldatabasename=databasename[d];
	        					   break;
	        				   }
	        			   }
	        			}
	        		}catch(Throwable e){
	        			e.printStackTrace();
	        		}
	        		log("finaldatabasename, "+finaldatabasename);
	            	 String externaldbpath  = ProjectDirectory + "/"
	 						+ Tabs1.basedirectory[0]+ "/"+finaldatabasename;
	            	 log("externaldb,"+externaldbpath);
	            	 log("Intrernaldb ,"+internaldbpath);
	            	 File  internaldb= new File(data, internaldbpath);
	                File externaldb  = new File(externaldbpath);
	                if(externaldb.exists()){
		                internaldb.delete();	                
		                FileInputStream fileInputStream = new FileInputStream(externaldb);
						FileChannel src = fileInputStream.getChannel();
		                FileOutputStream fileOutputStream = new FileOutputStream(internaldb);
						FileChannel dst = fileOutputStream.getChannel();
		                dst.transferFrom(src, 0, src.size());
		                src.close();
		                dst.close();
		                fileInputStream.close();
		                fileOutputStream.close();
		                Log.d("databaseimport success",internaldb.toString());
		                Tabs1.databasefoldername=finaldatabasename;
		                databaseimports=true;
	                }
	            }
	        } catch (Throwable e) {

	        	 e.printStackTrace();
	        }
	    }
		
		public static String finddatabasefoldername(String masterfoldername, String foldername, Context ctx){
			String ProjectDirectory = masterfoldername + foldername;
    		 String DatabaseDirectory=ProjectDirectory+ "/"
   					+ Tabs1.basedirectory[Tabs1.GENERATEDDOCUMENTSFOLDER];
    		 log("DatabaseDirectory ,"+DatabaseDirectory);
	    		 int maxdbs=5;
	       	 String[] databasename= new String[maxdbs];
	   		for(String string:databasename){
	   			string=null;
	   		}
	    		
	    		try {
	   			databasename = new File(DatabaseDirectory).list(new FilenameFilter() {
	   			public boolean accept(File directory, String fileName) {
	   				return fileName.endsWith(".db");
	   			}
	   		  });
	   		}catch(Throwable e){
	   			
	   		}
     		String olddatabasename="database.db";
     		String finaldatabasename=foldername;
     		try{
     			if(!(databasename[0].equals(null)))
     			{
     			   for(int d=0;d<databasename.length;d++){
     				   if(!(databasename[d].equals(olddatabasename))){
     					   finaldatabasename=databasename[d];
     					   break;
     				   }
     			   }
     			}
     		}catch(Throwable e){
     			
     		}
			if(finaldatabasename.contains(".db")){
				finaldatabasename=finaldatabasename.replace(".db", "");
			}
     		return finaldatabasename;
		}
		
		public static boolean isInteger(String str) {
			if (str == null) {
				return false;
			}
			int length = str.length();
			if (length == 0) {
				return false;
			}
			int i = 0;
			if (str.charAt(0) == '-') {
				if (length == 1) {
					return false;
				}
				i = 1;
			}
			for (; i < length; i++) {
				char c = str.charAt(i);
				if (c <= '/' || c >= ':') {
					return false;
				}
			}
			return true;
		}
		public static ArrayList GetUniqueValues(Collection values)
		{
		    return new ArrayList(new HashSet(values));
		}
		public static void log(Object message)
	    {
	       
	            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
	            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
	            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
	            
	            String fullClassName1 = Thread.currentThread().getStackTrace()[4].getClassName();            
	            String className1 = fullClassName1.substring(fullClassName1.lastIndexOf(".") + 1);
	            String methodName1 = Thread.currentThread().getStackTrace()[4].getMethodName();
	            int lineNumber1 = Thread.currentThread().getStackTrace()[4].getLineNumber();
	            
	            String fullClassName2 = Thread.currentThread().getStackTrace()[5].getClassName();            
	            String className2 = fullClassName2.substring(fullClassName2.lastIndexOf(".") + 1);
	            String methodName2 = Thread.currentThread().getStackTrace()[5].getMethodName();
	            int lineNumber2 = Thread.currentThread().getStackTrace()[5].getLineNumber();

	            
	            System.out.println(message);
	            Log.d(className + "." + methodName + "():" + lineNumber,"Called from "+className1 + "." + methodName1 + "():" + lineNumber1+"/"+className2 + "." + methodName2 + "():" + lineNumber2);
	            
	        
	    }
		static boolean appInstalledOrNot(String uri, Context ctx) {
	        PackageManager pm = ctx.getPackageManager();
	        boolean app_installed = false;
	        try {
	            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	            app_installed = true;
	        }
	        catch (PackageManager.NameNotFoundException e) {
	            app_installed = false;
	        }
	        log("uri "+uri+"is installed"+app_installed);
	        return app_installed ;
	    }
	
		public static int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
		public static Bitmap getgoodmemorybitmappercentofscreen(String pathName,
		        int reqWidthpercent, Context ctx) {
			int reqWidth=width(reqWidthpercent, ctx);
		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    log(pathName);
		    BitmapFactory.decodeFile(pathName, options);
		    int reqHeight=(int) ((double) reqWidth*(double) options.outHeight/(double) options.outWidth);
		    log(reqHeight);
		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(pathName, options);
		}
		public static Bitmap getgoodmemorybitmappixels(String pathName,
		        int reqWidth, Context ctx) {
		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    log(pathName);
		    BitmapFactory.decodeFile(pathName, options);
		    int reqHeight=(int) ((double) reqWidth*(double) options.outHeight/(double) options.outWidth);
		    log(reqHeight);
		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(pathName, options);
		}
		
		public static Bitmap getgoodmemorybitmap(String pathName, Context ctx) {
		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    log(pathName);
		    BitmapFactory.decodeFile(pathName, options);
		    
		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, options.outHeight, options.outWidth);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(pathName, options);
		}
		public long aspectratiowidthtoheight(int width, int height){
			return width/height;
		}
		public static int width(int percentofscreenlandscape, Context ctx){
			WindowManager wm = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			/*
			 * Display display = wm.getDefaultDisplay(); Point size = new Point();
			 * display.getSize(size); scrnwidth = size.x; scrnheight = size.y;
			 */
			DisplayMetrics displaymetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(displaymetrics);
			int scrnheight = displaymetrics.heightPixels;
			int scrnwidth = displaymetrics.widthPixels;
			
			return (int)((long)scrnwidth/(long)percentofscreenlandscape);
			
		}
		public void height(float aspectratiowidthto){
			
		}
		public static Bitmap decodeSampledBitmapFromResource(String filepath,
		        int reqWidth, int reqHeight) {

		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(filepath, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(filepath, options);
		}
		public void firefocuschange(){
			if(getCurrentFocus()!=null)getCurrentFocus().clearFocus();
		}




  public static void unzip(String _location, String _zipFile) { 
   
	  
	  try  { 
      FileInputStream fin = new FileInputStream(_zipFile); 
      ZipInputStream zin = new ZipInputStream(fin); 
      ZipEntry ze = null; 
      while ((ze = zin.getNextEntry()) != null) { 
        Log.v("Decompress", "Unzipping " + ze.getName()); 
 
        if(ze.isDirectory()) { 
          _dirChecker(ze.getName(), _location); 
        } else { 
          FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
          for (int c = zin.read(); c != -1; c = zin.read()) { 
            fout.write(c); 
          } 
 
          zin.closeEntry(); 
          fout.close(); 
        } 
         
      } 
      zin.close(); 
    } catch(Exception e) { 
      Log.e("Decompress", "unzip", e); 
    } 
 
  } 
 
  private static void _dirChecker(String dir, String _location) { 
    File f = new File(_location + dir); 
 
    if(!f.isDirectory()) { 
      f.mkdirs(); 
    } 
  } 
} 

class CustomTextWatcher implements TextWatcher {
    private EditText mEditText;
    private Spinner mSpinner;
    
    public CustomTextWatcher(EditText e, Spinner sp) { 
        mEditText = e;
        mSpinner=sp;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
    }
    
}
