package com.lifehackinnovations.siteaudit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;







import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author 9android.net
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	
	static final String KEY_ID = "id";
	private static final String KEY_LABEL = "label";
	private static final String KEY_VALUE = "value";
	
//Engineer Info Table Name	
	static final String TABLE_ENGINEERINFO = "engineerinfo";
	

	
	static final String TABLE_FLOORPLAN = "floorplan";
//Site Info Table
	static final String TABLE_SITEINFO = "siteinfo";
	static final String[] KEY_SITE_ATTRIBUTES={"Item_Number","sitename","sitesizem","sitesizeft","sitedescription","sitetype","floorplanlink"};
	static final String TABLE_NOTES="notes";
	
	static final String TABLE_COMPONENTINFO = "componentinfo";
	static final String[] KEY_COMPONENT_ATTRIBUTES={"Item_Number","componentname","componenttype","componentspec","componentlocation","componentnotes","floorplanlink"};
	static final String TABLE_ASSETINFO = "assetinfo";
	
	static final String[] KEY_ASSET_ATTRIBUTES={"Item_Number","assetname","assetquantity","assettype","assetmodel","assetpower","assetlocation","assetserviceaream","assetserviceareaft","assetnotes","floorplanlink"};
	static final String TABLE_CONSUMPTION = "consumption";
	static final String TABLE_RECOMMENDATIONS = "recommendations";
	
	static final String TABLE_MCR_VIRTUAL_METERINGLIST = "virtualmcrmeteringlist";
	static final String TABLE_MCR_METERINGLIST = "mcrmeteringlist";
	static final String TABLE_MCR_TEMPSLIST = "mcrtemplist";
	
	static final String TABLE_MCR_ELCMODBUSLIST= "mcrelcmodbuslist";
	static final String TABLE_MCR_SAMVALIDATIONLIST = "mcrsamvalidationlist";
	static final String TABLE_MCR_ELCCOMMISSIONINGCHECKLIST = "mcrelccommissioninglist";
	
	static final String MASTER_DATABASE="master_database";

	
	static final String m395detailsasset="mdetails.csv";
	static String TABLE_M395=m395detailsasset.split("\\.")[0];
	
	static final String m395storesizes="storesizes.csv";
	static String TABLE_M395_SIZES=m395storesizes.split("\\.")[0];
	
	static final String KEY_VIRTUAL_MCR_METERING_TITLES[] = {"Item_Number", "Virtual_Meter_Name", "Formula", "Load_Type"};
	static final String KEY_MCR_METERING_TITLES[] = {"Item_Number","ELC_Number","Load_Name","Panel_Location","Voltage","PH","Trans_Config","Breaker_Size","CT_Size_AMPS","CT_Size_Physical_Inside","CT_QTY","Modbus_Address","Comments","Load_Type","Loads_on_each_breaker_or_panel"};
	static final String KEY_MCR_TEMPLIST_TITLES[] = {"Item_Number","Floor_Number","ELC_Number","Reference_On_Map","Zone_Name","Analog_Input_Number", "Comments_Locations","ELC_Temps","Handheld_Temps","Offset","Zone_Type"};
	
	static final String KEY_FLOORPLAN_TITLES[] = {"Item_Number","Rectangle_Left","Rectangle_Top","Rectangle_Right","Rectangle_Bottom","Item_Type", "TempSensor_Count", "Size_Percent", "Sams_Count", "Master_ELC_number", "Display_Number", "ScaleMx", "ScaleMy", "ITEMx", "ITEMy", "String", "Font_Size", "Font_Color", "Meters_Per_Pixel", "Floor_Plan_Number","ELC_Display","Tab_Number","Tab_Item_Number","Item_Text_Offset_x","Item_Text_Offset_y"};
	
	static final String TABLE_BOM = "bom";
	static final String TABLE_SITE_RECOMMENDATIONS="siterecommendations";
	
	//static final String KEY_MCR_METERING_INAPPTITLES[] = {"Item #","ELC #","Load Name","Panel Location","Voltage","PH","Trans Config","Breaker Size","Comments","Load Type","Loads on each breaker or panel."};
	static Context ctx;

	public DatabaseHandler(Context context, String ProjectName) {
		super(context, ProjectName, null, DATABASE_VERSION);
		this.ctx=context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {


		

		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGINEERINFO);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	
	public void createtable(String TableName, String[] Titles, SQLiteDatabase db){
		String sqlstring="";
		int k=0;
		for (int u=1; u<Titles.length-1; u++){
			sqlstring=sqlstring+Titles[u]+" TEXT,";
			k=u;
		}
		k++;
		sqlstring=sqlstring+Titles[k]+" TEXT)";
		Log.d("sqlstring==",sqlstring);
		String CREATE_TABLE = "CREATE TABLE " + TableName + "("
				+ Titles[0] + " INTEGER PRIMARY KEY,"
				+ sqlstring;
		db.execSQL(CREATE_TABLE);
	}
	
	public void createfloorplandb(String floorplanname){
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlstring="";
		int k=0;
		for (int u=1; u<KEY_FLOORPLAN_TITLES.length-1; u++){
			sqlstring=sqlstring+KEY_FLOORPLAN_TITLES[u]+" TEXT,";
			k=u;
		}
		k++;
		sqlstring=sqlstring+KEY_FLOORPLAN_TITLES[k]+" TEXT)";
		Log.d("sqlstring==",sqlstring);
		String CREATE_FLOORPLAN_TABLE = "CREATE TABLE " + floorplanname + "("
				+ KEY_FLOORPLAN_TITLES[0] + " INTEGER PRIMARY KEY,"
				+ sqlstring;
		db.execSQL(CREATE_FLOORPLAN_TABLE);
	}
	
	public void deletedbtable(String tablename){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+ tablename);
	}
	
	void addValue(String table, String label, String value) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LABEL, label); 
		values.put(KEY_VALUE, value); 

		// Inserting Row
		db.insert(table, null, values);
		//showfulldblog(table);
	}
	
	// Getting single contact
	public String getValue(String table, String label) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(table, new String[] {
				KEY_ID,KEY_LABEL, KEY_VALUE }, KEY_LABEL + "=?",
				new String[] { label }, null, null, null, null);
		cursor.moveToFirst();
		
		String value="";
		
	
		cursor.moveToFirst();
	
		if (cursor != null)
				value = cursor.getString(2);
		// return contact
		cursor.close();
		
		return value;
	}
	
	public String getvaluemulticolumnhybrid(String table, String Column, int Row) {
		String value;
		String[] titles=gettitles(table);
		int column;
		if(table.equals(TABLE_SITEINFO)){
			column=getcolumnnumberbytitle(table,Column);
			value=getvaluemulticolumn(table, Row, column, titles);
			u.log("column"+column);
			u.log("value"+value);
		}
		else if(table.equals(TABLE_COMPONENTINFO)){
			column=getcolumnnumberbytitle(table,Column);
			value=getvaluemulticolumn(table, Row, column, titles);
		}
		else if(table.equals(TABLE_NOTES)){
			column=getcolumnnumberbytitle(table,Column);
			value=getvaluemulticolumn(table, Row, column, titles);
		}
		else if(table.equals(TABLE_ASSETINFO)){
			column=getcolumnnumberbytitle(table,Column);
			value=getvaluemulticolumn(table, Row, column, titles);
		}else if(table.equals(TABLE_ENGINEERINFO)){
			value=getValue(table, Column);
		}else if(table.equals(TABLE_BOM)){
			value=getValue(table, Column);
		}else if(table.equals(TABLE_MCR_VIRTUAL_METERINGLIST)){
			column=getcolumnnumberbytitle(table,Column);
			value=getvaluemulticolumn(table, Row, column, titles);
			u.log("column"+column);
			u.log("value"+value);
		}
		
		else{
			value=getValue(table, Column+u.s(Row));
		}
		
		return value;
		
	}
	
	public String getvaluemulticolumn(String table, int Row, int Column, String[] titles) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(table, titles, titles[0] + "=?",
				new String[]{u.s(Row+1)}, null, null, null, null);
		cursor.moveToFirst();
		
		String value="";
		
	
		cursor.moveToFirst();
	
		if (cursor != null)
				value = cursor.getString(Column);
		// return contact
		cursor.close();
		
		return value;
	}
	public Cursor getcursor(String table){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query(table, null, null, null, null, null, null);
		
		
		return cursor;
		
	}
	
	public ArrayList<String> getcolumn(String table, int Column, String[] titles) {
		ArrayList<String> columnvalues = new ArrayList<String>();
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor;
			
			cursor = db.query(table, new String[]{titles[Column]}, null, null, null, null, null, null);
			cursor.moveToFirst();
			Log.d("column name",cursor.getColumnName(0));
			Log.d("value at cursor position 0",cursor.getString(0));
			
			for (int j=0; j<cursor.getCount();j++){
				if(cursor.getString(0)!=null){
					columnvalues.add(cursor.getString(0));
				}
				cursor.moveToNext();
			}
			
			cursor.close();
			
		}catch(Throwable e){
			e.printStackTrace();
		}
		return columnvalues;
	}
	
	// Getting All Contacts
	

	// Updating single contact
	public int updateValue(String table, String label, String value) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LABEL, label);
		values.put(KEY_VALUE, value);
	
		// updating row
		
		return db.update(table, values, KEY_LABEL + " = ?",
				new String[] { label });
		
	}
	public void addorupdate(String table, String label, String value){
		try{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, null, KEY_LABEL + " = ?",
				new String[]{label}, null, null, null, null);
		
			cursor.moveToFirst();
		
		
		int numberofcolumns=cursor.getColumnCount();
		
		int numberofrows=cursor.getCount();
		
		Log.d("Columns=",u.s(numberofcolumns));
		Log.d("Rows=",u.s(numberofrows));
		cursor.close();
		
		if(numberofrows<1){
			addValue(table, label, value);
		}else{
			updateValue(table, label, value);
		}
		//showfulldblog(table);
		}catch(Throwable e){
			
		}
	}
public void addorupdatemulticolumn(String table, int Row, int Column, String value, String[] titles){
	try{	
	if(table.equals(this.TABLE_ASSETINFO)){
			u.log("row, "+Row+" Column, "+Column+" value, "+value);
		}
		SQLiteDatabase db = this.getReadableDatabase();
		
			Cursor cursor = db.query(table, null, titles[0] + " = ?",
					new String[]{u.s(Row+1)}, null, null, null, null);
			cursor.moveToFirst();
			int numberofcolumns=cursor.getColumnCount();
			int numberofrows=cursor.getCount();
			//u.log(table+" "+Row+" "+Column+" "+value);
			
		//	Log.d("Columns=",u.s(numberofcolumns));
		//	Log.d("Rows=",u.s(numberofrows));
			
			cursor.close();
			
			if(numberofrows<1){
				addValuemcr(table, Row, Column, value, titles);
			}else{
				updateValuemcr(table, Row, Column, value, titles);
			}
			
			//showmodifieddblog(table, Row, Column);
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		
	}


	 public void addValuemcr(String table, int Row, int Column, String value, String[] titles) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		ContentValues values = new ContentValues();
		values.put(titles[0], u.s(Row+1)); 
		values.put(titles[Column], value); 
	
		// Inserting Row
		db.insert(table, null, values);
		
		
	}
	 public void addentirerow(String table, String[] strings, int Row) {
			SQLiteDatabase db = this.getWritableDatabase();
			String[] titles=this.gettitles(table);
			
			ContentValues values = new ContentValues();
			int x=1;
			for(String string: strings){
				values.put(titles[x], string); 
				x++;
			}
		
			// Inserting Row
			if(Row>countrows(table)){
				db.insert(table, null, values);
			}else{
				db.update(table, values,titles[0] + " = ?",
					new String[] { u.s(Row) });		
			}
		}
	 public void addrow(String table, String[] strings, int Row) {
			SQLiteDatabase db = this.getWritableDatabase();
			String[] titles=this.gettitles(table);
			
			ContentValues values = new ContentValues();
			int x=1;
			for(String string: strings){
				u.log(titles[x]+","+string);
				values.put(titles[x], string); 
				x++;
			}
		
			// Inserting Row
			u.log(Row+" "+countrows(table));
			if(Row>=countrows(table)){
				u.log("rowinserted");
				db.insert(table, null, values);
			}else{
				u.log("rowupdated");
				db.update(table, values,titles[0] + " = ?",
					new String[] { u.s(Row) });		
			}
		}
	public void addnewValuemcr(String table, int Row, int Column, String value, String[] titles) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		ContentValues values = new ContentValues();
		values.put(titles[0], u.s(countrows(table)+1)); 
		values.put(titles[Column], value); 
	
		// Inserting Row
		db.insert(table, null, values);
		
		
	}

	// Deleting single contact
	public void deleteValue(String table, String label) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table, KEY_LABEL + " = ?",
				new String[] { label});
		
		
	}
	public void deleteSingleRow(String table, String rowId) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table, this.gettitles(table)[0] + "=" + rowId, null);
		
		
	}
	
	public int updateValuemcr(String table, int Row, int Column, String value, String[] titles) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(titles[0], u.s(Row+1)); 
		values.put(titles[Column], value); 

		// updating row
		
		return db.update(table, values,titles[0] + " = ?",
				new String[] { u.s(Row+1) });
		
	}

	public boolean tableexists(String table){
		boolean exists=false;
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(table, null, null,
					null, null, null, null, null);
			cursor.moveToFirst();
			exists=true;
		}catch(Throwable e){
			exists= false;
		}
		
		return exists;
	}
	public void showmodifieddblog(String table, int row, int column){
		try{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToPosition(row);
		
		
	
		Log.d("Table Name=",table);
		Log.d("Column Name", cursor.getColumnName(column));
		Log.d("Column Number=",u.s(column));
		Log.d("Row=",u.s(row));
		Log.d("Value=",cursor.getString(column));
		
		cursor.close();
		}catch(Throwable e){
			System.out.println("Could not show table, it likely doesn't exist. Here are the tables that do");
			showtableslog();
		}
	}
	public void showfulldblog(String table){
		try{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		int numberofcolumns=cursor.getColumnCount();
		int numberofrows=cursor.getCount();
		u.log("Table Name= "+table);
		u.log("Columns= "+u.s(numberofcolumns));
		u.log("Rows= "+u.s(numberofrows));
		String[] columnnames=cursor.getColumnNames();
		String columnnamestring="";
		for (String c:columnnames){
			columnnamestring=columnnamestring+","+c;
		}
		u.log("Column Names "+columnnamestring);
	
		String rowstring="";
		for(int y=0; y<numberofrows; y++){
			for (int x=0; x<numberofcolumns; x++){
				rowstring=rowstring+","+cursor.getString(x);

			}
			u.log("Row "+rowstring);
			rowstring="";
			cursor.moveToNext();
		}
		cursor.close();
		}catch(Throwable e){
			System.out.println("Could not show table, it likely doesn't exist. Here are the tables that do");
			showtableslog();
		}
	}
	
	public Boolean checktableindb(String table){
		try{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		int numberofcolumns=cursor.getColumnCount();
		int numberofrows=cursor.getCount();
		//u.log("Table Name= "+table);
		//u.log("Columns= "+u.s(numberofcolumns));
		//u.log("Rows= "+u.s(numberofrows));
		String[] columnnames=cursor.getColumnNames();
		String columnnamestring="";
		for (String c:columnnames){
			columnnamestring=columnnamestring+","+c;
		}
		//u.log("Column Names "+columnnamestring);
	
		String rowstring="";
		for(int y=0; y<numberofrows; y++){
			for (int x=0; x<numberofcolumns; x++){
				rowstring=rowstring+","+cursor.getString(x);

			}
		//u.log("Row "+rowstring);
			rowstring="";
			cursor.moveToNext();
		}
		cursor.close();
		return true;
		}catch(Throwable e){
			return false;
		}
	}
	
	
	public void showfullcursor(Cursor cursor){
		
		
		
		cursor.moveToFirst();
		int numberofcolumns=cursor.getColumnCount();
		int numberofrows=cursor.getCount();
		
		u.log("Columns= "+u.s(numberofcolumns));
		u.log("Rows= "+u.s(numberofrows));
		String[] columnnames=cursor.getColumnNames();
		String columnnamestring="";
		for (String c:columnnames){
			columnnamestring=columnnamestring+","+c;
		}
		u.log("Column Names "+columnnamestring);
	
		String rowstring="";
		for(int y=0; y<numberofrows; y++){
			for (int x=0; x<numberofcolumns; x++){
				rowstring=rowstring+","+cursor.getString(x);

			}
			u.log("Row "+rowstring);
			rowstring="";
			cursor.moveToNext();
		}
		
		
	}
	public String[] gettitles(String table){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		String[] titles=cursor.getColumnNames();
		cursor.close();
		return titles;
	}
	public void showtableslog(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		        u.log( "Table Name=> "+c.getString(0));
		        c.moveToNext();
		    }
		}
		c.close();
	}
	public void renametable(String from, String to){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("ALTER TABLE "+ from +" RENAME TO "+ to);
	}
	public void createdbtablefromcsv( Context ctx, String asset) throws IOException{
		SQLiteDatabase db = this.getWritableDatabase();
		InputStream input = ctx.getResources().getAssets().open(asset);
		
		InputStreamReader r = new InputStreamReader(input);
		BufferedReader in = new BufferedReader(r);
	    String reader = "";

//get titles 
	    reader = in.readLine();
	    System.out.println(asset);
	    String tablename=asset.split("\\.")[0];
	    String[] TitleData=reader.split(",");
	    String CREATE_TABLE = "CREATE TABLE " + tablename + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,";
	    
	    int i=0;
	    for(i=0; i<TitleData.length-1; i++){
	    	System.out.println("title i="+TitleData[i]+i);

	    	CREATE_TABLE=CREATE_TABLE+TitleData[i] + " TEXT,";
	    }
    	System.out.println("title i="+TitleData[i]+i);

	    CREATE_TABLE=CREATE_TABLE+TitleData[i] + " TEXT)";
	    		
		db.execSQL(CREATE_TABLE);
	    
		ContentValues values = new ContentValues();
	        while ((reader = in.readLine()) != null){
	            
	        	String[] RowData = reader.split(",");
	            for (int y=0; y<RowData.length; y++){
	            	//System.out.println(RowData[y]);
	            }
	            
	            
	            for(i=0; i<RowData.length; i++){
	    	    	//System.out.println("title i="+RowData[i]+i);
	            	try{
	            		values.put(TitleData[i], RowData[i]);
	            	}catch(Throwable e){
	            		
	            	}
	    	    }
	            db.insert(tablename, null, values);
	            
	        }
	        in.close();
	}
	
	public void createprojecttables(){
		System.out.println("creatingprojecttables");
		//project tables		
			
				SQLiteDatabase db = this.getWritableDatabase();
				String CREATE_ENGINEERINFO_TABLE = "CREATE TABLE " + TABLE_ENGINEERINFO + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_ENGINEERINFO_TABLE);
			
				
				createtable(TABLE_SITEINFO,KEY_SITE_ATTRIBUTES, db);
				createtable(TABLE_COMPONENTINFO,KEY_COMPONENT_ATTRIBUTES, db);
				createtable(TABLE_ASSETINFO,KEY_ASSET_ATTRIBUTES, db);
		
/*				
				String CREATE_SITEINFO_TABLE = "CREATE TABLE " + TABLE_SITEINFO + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_SITEINFO_TABLE);
				String CREATE_COMPONENTINFO_TABLE = "CREATE TABLE " + TABLE_COMPONENTINFO + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_COMPONENTINFO_TABLE);
				String CREATE_ASSETINFO_TABLE = "CREATE TABLE " + TABLE_ASSETINFO + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_ASSETINFO_TABLE);
				*/
				
				String CREATE_CONSUMPTION_TABLE = "CREATE TABLE " + TABLE_CONSUMPTION + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_CONSUMPTION_TABLE);
				String CREATE_RECOMMENDATIONS_TABLE = "CREATE TABLE " + TABLE_RECOMMENDATIONS + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_RECOMMENDATIONS_TABLE);
				String CREATE_BOM_TABLE = "CREATE TABLE " + TABLE_BOM + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_BOM_TABLE);
				
				String CREATE_SITE_RECOMMENDATIONS_TABLE = "CREATE TABLE " + TABLE_SITE_RECOMMENDATIONS + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_SITE_RECOMMENDATIONS_TABLE);
				
				String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
						+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
						+ KEY_VALUE + " TEXT" + ")";
				db.execSQL(CREATE_NOTES_TABLE);
				
		//MCR Table		
				String sqlstringmcrtitlesnoitemnum="";
				int k=0;
				for (int u=1; u<KEY_MCR_METERING_TITLES.length-1; u++){
					sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_METERING_TITLES[u]+" TEXT,";
					k=u;
				}
				k++;
				sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_METERING_TITLES[k]+" TEXT)";
				Log.d("sqlstringmcrtitlesnoitemnum=",sqlstringmcrtitlesnoitemnum);
				String CREATE_MCR_METERINGLIST_TABLE = "CREATE TABLE " + TABLE_MCR_METERINGLIST + "("
						+ KEY_MCR_METERING_TITLES[0] + " INTEGER PRIMARY KEY,"
						+ sqlstringmcrtitlesnoitemnum;
				db.execSQL(CREATE_MCR_METERINGLIST_TABLE);
				
		//VIRTUAL MCR Table		
						sqlstringmcrtitlesnoitemnum="";
						k=0;
						for (int u=1; u<KEY_VIRTUAL_MCR_METERING_TITLES.length-1; u++){
							sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_VIRTUAL_MCR_METERING_TITLES[u]+" TEXT,";
							k=u;
						}
						k++;
						sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_VIRTUAL_MCR_METERING_TITLES[k]+" TEXT)";
						Log.d("sqlstringmcrtitlesnoitemnum=",sqlstringmcrtitlesnoitemnum);
						String CREATE_VIRTUAL_MCR_METERINGLIST_TABLE = "CREATE TABLE " + TABLE_MCR_VIRTUAL_METERINGLIST + "("
								+ KEY_VIRTUAL_MCR_METERING_TITLES[0] + " INTEGER PRIMARY KEY,"
								+ sqlstringmcrtitlesnoitemnum;
						db.execSQL(CREATE_VIRTUAL_MCR_METERINGLIST_TABLE);
				
				
		//Temp Table		
				sqlstringmcrtitlesnoitemnum="";
				k=0;
				for (int u=1; u<KEY_MCR_TEMPLIST_TITLES.length-1; u++){
					sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_TEMPLIST_TITLES[u]+" TEXT,";
					k=u;
				}
				k++;
				sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_TEMPLIST_TITLES[k]+" TEXT)";
				Log.d("sqlstringmcrtitlesnoitemnum=",sqlstringmcrtitlesnoitemnum);
				String CREATE_MCR_TEMPLIST_TABLE = "CREATE TABLE " + TABLE_MCR_TEMPSLIST + "("
						+ KEY_MCR_TEMPLIST_TITLES[0] + " INTEGER PRIMARY KEY,"
						+ sqlstringmcrtitlesnoitemnum;
				db.execSQL(CREATE_MCR_TEMPLIST_TABLE);
	}
	public void createmcrtemptable(){
		//Temp Table	
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlstringmcrtitlesnoitemnum="";
		int k=0;
		for (int u=1; u<KEY_MCR_TEMPLIST_TITLES.length-1; u++){
			sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_TEMPLIST_TITLES[u]+" TEXT,";
			k=u;
		}
		k++;
		sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_TEMPLIST_TITLES[k]+" TEXT)";
		Log.d("sqlstringmcrtitlesnoitemnum=",sqlstringmcrtitlesnoitemnum);
		String CREATE_MCR_TEMPLIST_TABLE = "CREATE TABLE " + TABLE_MCR_TEMPSLIST + "("
				+ KEY_MCR_TEMPLIST_TITLES[0] + " INTEGER PRIMARY KEY,"
				+ sqlstringmcrtitlesnoitemnum;
		db.execSQL(CREATE_MCR_TEMPLIST_TABLE);
	}
	
	public void createvirtualmcrmeteringtable(){
		//VIRTUAL_MCR Table	
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlstringVIRTUAL_MCRtitlesnoitemnum="";
		int k=0;
		for (int u=1; u<KEY_VIRTUAL_MCR_METERING_TITLES.length-1; u++){
			sqlstringVIRTUAL_MCRtitlesnoitemnum=sqlstringVIRTUAL_MCRtitlesnoitemnum+KEY_VIRTUAL_MCR_METERING_TITLES[u]+" TEXT,";
			k=u;
		}
		k++;
		sqlstringVIRTUAL_MCRtitlesnoitemnum=sqlstringVIRTUAL_MCRtitlesnoitemnum+KEY_VIRTUAL_MCR_METERING_TITLES[k]+" TEXT)";
		Log.d("sqlstringVIRTUAL_MCRtitlesnoitemnum=",sqlstringVIRTUAL_MCRtitlesnoitemnum);
		String CREATE_VIRTUAL_MCR_METERINGLIST_TABLE = "CREATE TABLE " + TABLE_MCR_VIRTUAL_METERINGLIST + "("
				+ KEY_VIRTUAL_MCR_METERING_TITLES[0] + " INTEGER PRIMARY KEY,"
				+ sqlstringVIRTUAL_MCRtitlesnoitemnum;
		db.execSQL(CREATE_VIRTUAL_MCR_METERINGLIST_TABLE);
	}
	public void createmcrmeteringtable(){
		//MCR Table	
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlstringmcrtitlesnoitemnum="";
		int k=0;
		for (int u=1; u<KEY_MCR_METERING_TITLES.length-1; u++){
			sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_METERING_TITLES[u]+" TEXT,";
			k=u;
		}
		k++;
		sqlstringmcrtitlesnoitemnum=sqlstringmcrtitlesnoitemnum+KEY_MCR_METERING_TITLES[k]+" TEXT)";
		Log.d("sqlstringmcrtitlesnoitemnum=",sqlstringmcrtitlesnoitemnum);
		String CREATE_MCR_METERINGLIST_TABLE = "CREATE TABLE " + TABLE_MCR_METERINGLIST + "("
				+ KEY_MCR_METERING_TITLES[0] + " INTEGER PRIMARY KEY,"
				+ sqlstringmcrtitlesnoitemnum;
		db.execSQL(CREATE_MCR_METERINGLIST_TABLE);
	}
	public void createmastertables(){
		//Master tables		
				SQLiteDatabase db = this.getWritableDatabase();
				System.out.println(TABLE_M395);
				try {
					createdbtablefromcsv( ctx, m395detailsasset);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(TABLE_M395_SIZES);
				try {
					createdbtablefromcsv(ctx, m395storesizes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}
	public int countrows(String table){
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(table, null, null,
					null, null, null, null, null);
			cursor.moveToFirst();
			int numberofrows=cursor.getCount();
			cursor.close();
			return numberofrows;
		}catch(Throwable e){
			return 0;
		}
		
	}
	public int getmaxintvalue(String table, String columntitle){
		int max=0;
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor =db.query(table, null, columntitle+"=(SELECT MAX("+columntitle+"))", null, null, null, null);
			cursor.moveToFirst();
			max=u.i(cursor.getString(cursor.getColumnIndex(columntitle)));
		}catch(Throwable e){
			
		}
		
		return max;
		
	}
	public int getnumberofrowswithcolumnvalue(String table, String columntitle, String value){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =db.query(table, new String[]{columntitle}, columntitle+"=?"+value, null, null, null, null);
		cursor.moveToFirst();
		int numberofrows=cursor.getCount();
		return numberofrows;
		
	}
	public Cursor getrowswithvalue(String table, String columntitle, String value){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =db.query(table, null, columntitle+"=?",new String[]{value}, null, null, null, null);
		cursor.moveToFirst();
		return cursor;
		
	}
	
	
	public void deleteallrowswithvalue(String table, String columntitle, String value){
		SQLiteDatabase db = this.getWritableDatabase();
		String[] whereArgs = new String[] { value };
		db.delete(table, columntitle+"=?", whereArgs);
		
	}
	public void settingmcritemno(String table, String columnname){
	
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.query(table, null, null,
					null, null, null, null, null);
			cursor.moveToFirst();
			int numberofrows=cursor.getCount();
			for(int a=0;a<numberofrows;a++){
				ContentValues value = new ContentValues();
				value.put(columnname, u.s(a+1)); 
				u.log(u.s(a+1));
				
				final String[] whereArgs = { cursor.getString(0) };
				db.update(table, value, columnname+" = ?", whereArgs);
				cursor.moveToNext();
			}
			
			cursor.close();
		
	}
	public void setorderassendingbycolumn(String table, String columnnamefororder, String numberkeycolumn){
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, columnnamefororder, null);
		cursor.moveToFirst();
		showfullcursor(cursor);
		cursor.moveToFirst();
		int numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			
			ContentValues value = new ContentValues();
			value.put(numberkeycolumn, u.s(a+1000)); 
			
			
			final String[] whereArgs = { cursor.getString(0) };
			u.log(cursor.getString(0));
			db.update(table, value, numberkeycolumn+" = ?", whereArgs);
			cursor.moveToNext();
		}
		cursor.close();
//delete		
		db = this.getWritableDatabase();
		cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		showfullcursor(cursor);
		cursor.moveToFirst();
		numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows/2;a++){	
			final String[] whereArgs = { u.s(a+1) };
			u.log(cursor.getString(0));
			db.delete(table, numberkeycolumn+" = ?", whereArgs);
			
		}
		cursor.close();
//rename		
		
		db = this.getWritableDatabase();
		cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		showfullcursor(cursor);
		cursor.moveToFirst();
		numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			
			ContentValues value = new ContentValues();
			value.put(numberkeycolumn, u.s(a+1)); 
			
			
			final String[] whereArgs = { cursor.getString(0) };
			u.log(cursor.getString(0));
			db.update(table, value, numberkeycolumn+" = ?", whereArgs);
			cursor.moveToNext();
		}
		cursor.close();
		
		
		
	
}
	public void renumberelcsgreaterthanelc(String table, String columntitle, String elcnum){
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		showfullcursor(cursor);
		cursor.moveToFirst();
		int numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			if(u.i(cursor.getString(Tabs1.ELCNUMBERCOLUMN))> u.i(elcnum)){
			
				ContentValues value = new ContentValues();
				value.put(columntitle, u.s(u.i(cursor.getString(Tabs1.ELCNUMBERCOLUMN))-1)); 
				
				final String[] whereArgs = { cursor.getString((Tabs1.ELCNUMBERCOLUMN)) };
				u.log(cursor.getString(0));
				db.update(table, value, columntitle+" = ?", whereArgs);
			
			}
			cursor.moveToNext();
		}
		cursor.close();
	}
	
	
public void swapelcs(String table, String columntitle, String elcnum1, String elcnum2){
	u.log("Swap ELC ELC1, "+elcnum1+", ELC2, "+elcnum2);
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		showfullcursor(cursor);
		cursor.moveToFirst();
		int numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			
			if(cursor.getString(Tabs1.ELCNUMBERCOLUMN).equals(elcnum1) ){
				ContentValues value = new ContentValues();
				value.put(columntitle, "x"); 
				
				final String[] whereArgs = { elcnum1 };
				u.log(cursor.getString(0));
				db.update(table, value, columntitle+" = ?", whereArgs);
				
			
			}
			cursor.moveToNext();
		}
		cursor.close();

		db = this.getWritableDatabase();
		cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			
			 if(cursor.getString(Tabs1.ELCNUMBERCOLUMN).equals(elcnum2)){
				ContentValues value = new ContentValues();
				value.put(columntitle, elcnum1); 
				
				final String[] whereArgs = { elcnum2 };
				u.log(cursor.getString(0));
				db.update(table, value, columntitle+" = ?", whereArgs);
				
			}
			cursor.moveToNext();
		}
		cursor.close();
		

		 db = this.getWritableDatabase();
		cursor = db.query(table, null, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		numberofrows=cursor.getCount();
		for(int a=0;a<numberofrows;a++){
			
			if(cursor.getString(Tabs1.ELCNUMBERCOLUMN).equals("x") ){
				ContentValues value = new ContentValues();
				value.put(columntitle, elcnum2); 
				
				final String[] whereArgs = { "x" };
				u.log(cursor.getString(0));
				db.update(table, value, columntitle+" = ?", whereArgs);
				
			
			}
			cursor.moveToNext();
		}
		cursor.close();
		
		
		u.log("Swap ELC done");
		Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
	}
	public int getcolumnnumberbytitle(String table, String title){
		int q=-1;
		String[] titles=gettitles(table);
		for(int x=0; x<titles.length; x++){
			if(title.equals(titles[x])){
				q=x;
			}
		}
		return q;
	}
	public void createmissingtables(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		if(!checktableindb(TABLE_M395)){
			createmastertables();
		}
		if(! checktableindb(TABLE_ENGINEERINFO)){
			String CREATE_ENGINEERINFO_TABLE = "CREATE TABLE " + TABLE_ENGINEERINFO + "("
					+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
					+ KEY_VALUE + " TEXT" + ")";
			db.execSQL(CREATE_ENGINEERINFO_TABLE);
			u.log("TABLE_ENGINEERINFO created");
		}
		if(! checktableindb(TABLE_SITEINFO)){
			createtable(TABLE_SITEINFO,KEY_SITE_ATTRIBUTES, db);
			u.log("TABLE_SITEINFO created");
		}
		if(! checktableindb(TABLE_COMPONENTINFO)){
			createtable(TABLE_COMPONENTINFO,KEY_COMPONENT_ATTRIBUTES, db);
			u.log("TABLE_COMPONENTINFO created");
		}
		
		if(! checktableindb(TABLE_ASSETINFO)){
			createtable(TABLE_ASSETINFO,KEY_ASSET_ATTRIBUTES, db);
			u.log("TABLE_ASSETINFO created");
		}else{
			u.log("TABLE_ASSETINFO exists");
		}
		
		if(! checktableindb(TABLE_BOM)){
			String CREATE_BOM_TABLE = "CREATE TABLE " + TABLE_BOM + "("
					+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LABEL + " TEXT,"
					+ KEY_VALUE + " TEXT" + ")";
			db.execSQL(CREATE_BOM_TABLE);
			u.log("TABLE_BOM created");
		}
		if(! checktableindb(TABLE_MCR_METERINGLIST)){
			createmcrmeteringtable();
			u.log("METERING TABLE created");
		}

		if(! checktableindb(TABLE_MCR_VIRTUAL_METERINGLIST)){
			createvirtualmcrmeteringtable();
			u.log("VIRTUAL METERING TABLE created");
		}
		if(! checktableindb(TABLE_MCR_TEMPSLIST)){
			createmcrtemptable();
			u.log("MCR TEMP TABLE created");
		}
		
		
	}
}
