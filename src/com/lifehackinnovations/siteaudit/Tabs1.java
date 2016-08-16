package com.lifehackinnovations.siteaudit;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.lang.String;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.preference.EditTextPreference;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.ParentReference;
import com.google.common.base.Strings;
import com.google.common.primitives.Chars;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class Tabs1 extends TabActivity {
	String latexfileslocation_pics;
	String latexfileslocation_chaps;
	
	static String storagekeys[]={"externalstoragedirectory","lhistoragedirectory","lhiconfigfilesstoragedirectory","projectsstoragedirectory","mainsugarsyncdirectory","sugarsynclhiconfigfilesstoragedirectory","sugarsyncprojectsstoragedirectory","sugarsyncupdatedirectory","dcimdirectory"};
	String externalstoragedirectory,lhistoragedirectory,lhiconfigfilesstoragedirectory,projectsstoragedirectory,mainsugarsyncdirectory,sugarsynclhiconfigfilesstoragedirectory,sugarsyncprojectsstoragedirectory,sugarsyncupdatedirectory;
	
	static long picturestarttime;
	static long picturestoptime;
	
	String[] heldexcelrow;
	 
	String internaldbpath;
	String externaldbpath;
	final int MCRDBVALUES=0;
	final int MCREXCELVALUES=1;
	int SOURCE=MCRDBVALUES;
	
	int infinity=Integer.MAX_VALUE;
	
	final static int VRMITEMNUMBERCOLUMN=0;
	final static int VRMLOADNAMECOLUMN=1;
	final static int VRMFORMULA=2;
	final static int VRMLOADTYPECOLUMN=3;
	
	final static int ITEMNUMBERCOLUMN=0;
	final static int ELCNUMBERCOLUMN=1;
	final static int LOADNAMECOLUMN=2;
	final static int PANELLOCATIONCOLUMN=3;
	final static int VOLTAGECOLUMN=4;
	final static int PHCOLUMN=5;
	final static int TRANSCONFIGCOLUMN=6;
	final static int BREAKERSIZECOLUMN=7;
	final static int CTSIZEAMPSCOLUMN=8;
	final static int CTSIZEPHYISICALCOLUMN=9;
	final static int CTQTYCOLUMN=10;
	final static int MODBUSADDRESSCOLUMN=11;
	final static int COMMENTSCOLUMN=12;
	final static int LOADTYPECOLUMN=13;
	final static int LOADSONEACHBREAKERCOLUMN=14;

//columnnumbersonmcrsheettempsensors
	int TEMPITEMNUMBERCOLUMN=u.cellx("A1");
	int TEMPFLOORNUMBERCOLUMN=u.cellx("B1");
	int TEMPELCNUMBERCOLUMN=u.cellx("C1");
	static int TEMPSECTIONOFBUILDINGCOLUMN=u.cellx("D1");
	static int TEMPZONENAMECOLUMN=u.cellx("E1");
	int TEMPANALOGINPUTCOLUMN=u.cellx("F1");
	static int TEMPCOMMENTSCOLUMN=u.cellx("G1");
	int TEMPELCTEMPSCOLUMN=u.cellx("H1");
	int TEMPHANDHELDTEMPSCOLUMN=u.cellx("I1");
	int TEMPOFFSETCOLUMN=u.cellx("J1");
	static int TEMPZONETYPECOLUMN=u.cellx("K1");
	
	 DatabaseHandler masterdb;
	
	 
	int FIRSTSHEET=0;
	int SECONDSHEET=1;
	static int THIRDSHEET=2;
	static int FOURTHSHEET=3;
	 
	 
	int M395index;
	String storenumber;
	ArrayList<String> M395storenumbers;
	ArrayList<String> M395storenames;
	static String[] M395storesbyCONVENTION;
	int M395storenumbercolumnoncsv=1;
	int M395storenamecolumnoncsv=3;
	int[] storeaddresscolumnsoncsv={4,5,6,7,8};
	String storeaddress;
	String storesizeft;
	
	
	static List<String> commonlocations;
	static List<String> commonmanufacturers;
	static List<String> commontemplocations;
	static List<String> commontemplocationsammendments;
	static List<String> correspondingtypes;
	
	static List<String> floornumbers;
	ArrayAdapter<String> floornumbersadapter;	
	
	
	List<String> componentnames = new ArrayList<String>();
	static List<String> bmsnames = new ArrayList<String>();
	static List<String> loadnames = new ArrayList<String>();
	
	
	
	ArrayAdapter<String> commonroomsadapter; 
	ArrayAdapter<String> commonmanufacturerssadapter; 
	ArrayAdapter<String> componentnamesadapter;
	ArrayAdapter<String> assetnamesadapter;
	static ArrayAdapter<String> commontemplocationsadapter;
	static ArrayAdapter<String> bmsnamesadapter;
	
	int defaultvoltage=400;
	int defaultphase=3;
	String defaulttransconfig="Wye";
	
	int tinyfontsize =10;
	
	static int FloorPlanCount;

	AsyncTask loadtask;
	
	
	final static int SITETAB = 0;
	final static int COMPONENTSTAB=1;
	final static int ASSETSTAB = 2;
	final static int CONSUMPTIONTAB = 3;
	final static int RECOMMENDATIONSTAB = 4;
	final static int MAESTROCOMMISSIONINGTAB = 5;
	final static int BOMTAB = 6;
	final static int NOTESTAB = 7;
	final static int FILEEXPLORERTAB = 8;
	final static int MOPTAB = 9;
	final static int EXTRASTAB = 10;
	final static int PROGRESSREPORTTAB = 11;
	final static int FLOORPLANTAB = 12;
	
	boolean SITETABLOADED=false; 
	boolean COMPONENTSTABLOADED=false;
	boolean ASSETSTABLOADED=false; 
	boolean CONSUMPTIONTABLOADED=false; 
	boolean RECOMMENDATIONSTABLOADED=false; 
	boolean MAESTROCOMMISSIONINGTABLOADED=false; 
	boolean BOMTABLOADED=false;
	boolean NOTESTABLOADED=false;
	boolean FILEEXPLORERTABLOADED=false; 
	boolean MOPTABLOADED=false; 
	boolean EXTRASTABLOADED=false; 
	boolean PROGRESSREPORTTABLOADED=false; 
	boolean FLOORPLANTABLOADED=false; 
	boolean importandreplaceassets=false;
	
	int vrmvariablecount;
	
	
	static int maxitems = 50;
	
	static HashMap<String, String> viewnametoexcelvalues = new HashMap<String, String>();


	static ImageView fromcamera;
	ImageView fromfolder;

	Button addbuilding;
	Button addfloor;
	Button addzone;
	Button addroom;
	Button addcomponent;
	Button addasset;
	Button addrecommendation;
	Button importassets;

	List<String> companynamemop;
	String morrisonscompanyname="Morrisons";
	Boolean ISMORRISONS=false;
	static Boolean ISDATACENTER=false;
	static Boolean ISGROCERYSTRORE=false;
//company code
	final int MORRISONS=0;
	boolean unitcostfill=false;
	
	static Context ctx;
	static Intent myIntent;

	ImageView coverpagethumbnail;
	ImageView clientlogothumbnail;
	String coverpagefilepath;
	String clientlogofilepath;
	String defaultcoverpagefilepath;
	String defaultclientlogofilepath;
	String coverpagecopydest;
	String clientlogocopydest;
	Boolean defaultcoverpagepathset = false;
	Boolean defaultclientlogopathset = false;

	String oldname;
	String newname;
	String[][][] sortedmcr;
	Boolean companynameselectedboolean = false;

	Button updateprogressbutton;
	ProgressBar sitewalkprogressbar;
	int sitewalkprogress = 50;
	TextView siteinfoprogresstextview;
	TextView assetsprogresstextview;
	TextView consumptionprogresstextview;
	TextView recommendationsprogresstextview;

	
	String DB_engineername = "engineername";

	String DB_engineeremail = "engineeremail";
	String DB_engineerphonenumber = "engineerphonenumber";
	
	
	
	Boolean dbloadcountsitestab = false;
	Boolean dbloadcountassetstab = false;
	Boolean dbloadcountcomponentstab = false;
	Boolean dbloadcountconsumptiontab = false;
	
	
	Button reordermeteringtable;
	Button savetoexcel;
	Button importfromexcel;
	Button addmcrmeteringlist;
	Button addvirtualmcrmeteringlist;
	
	Button addmcrtempsensor;

	static ArrayList<String> floorplanstrings=new ArrayList<String>();
	static ArrayList<String> outputfloorplanstrings=new ArrayList<String>();
	static String floorplanname;
	static String string;
	static String inputfloorplandirectory;
	static String outputfloorplandirectory;
	
	static int maxvirtualmeters=20;
	static int maxelcs=15;
	static int maxsamsperelc=32;
	static int maxtempsensors=50;
	static int maxfloorplans = 10;
	static int maxlocations = 20;
	static int maxassets = 70;
	static int maxcomponents = 50;
	static int maxrecommendations = 20;
	
	static String[] assetnames = new String[maxassets] ;
	
	static String[] elcassetstrings=new String[maxelcs];
	static String[] tempsensorassetsstrings=new String[maxtempsensors];
	static String[] siteassetstrings =new String[maxassets];

	private ImageView fileexplorerhome;
	private ImageView fileexplorerup;
	private TextView fileexplorerparent;

	public String oldcaption = "null";
	public String oldnotes = "null";


	public String SiteListsPath;
	public static String MCRlocation;
	public static String OutputBitmapPath;
	public String MCCSVInputpath;
	public String ConsumptionPath;
	public String ReportPath;
	public String GeneralPath;
	public String AssetsPath;
	public String MeteringbalmPath;
	public String BOMtotalPath;
	public String CTbomPath;
	public String ConsumptionPath2;
	public String TempsensorPath;
	public String SiterecommendationsPath;
	public String SamArrayReferencePath;
	
	// Base Directories

	public static String[] basedirectory = { "Generated Documents",
			"Documents", "Assets","Components", "Site Info", "Consumption"

	};
	public final static int GENERATEDDOCUMENTSFOLDER = 0;
	public final static int DOCUMENTSFOLDER = 1;
	public final static int ASSETSFOLDER = 2;
	public final static int COMPONENTSFOLDER =3;
	public final static int SITEINFOFOLDER = 4;
	public final static int CONSUMPTIONFOLDER = 5;

	public static GoogleAccountCredential credential;
	public static int DRIVETASKMODE;
	HashMap<ArrayAdapter<String>, List<String>> SpinnersHash;

	private List<String> itemfordocs = null;
	private List<String> pathfordocs = null;
	String rootfordocs;
	int totaltempsensornumber = 0;

	// MCR Ints
	
	final static int METERINGLIST = 1;
	final static int TEMPSENSORCOMMISSIONING = 2;
	final static int ELCMODBUSADDRESSVALIDATION = 3;
	final static int SAMVALIDATIONCOMMISSIONING = 4;
	final static int ELCCOMMISSIONINGCHECKLIST = 5;
	

	public EditText notes;
	
	// Drive save modes
	int FROMDRIVE = 0;
	int TODRIVE = 1;
	int SYNCDRIVE = 2;

	final String[] ReservedChars = { "|", "\\", "?", "*", "<", "\"", ":", ">",
			"/" };
	final String[] ReservedCharsIncludingSpace = { "|", "\\", "?", "*", "<",
			"\"", ":", ">", "/", " " };

	public OnItemClickListener docsandreflistener;
	public LocationListener ll;
	public LocationManager lm;
	public ImageView mapsbutton;

	public static String addressString;

	private static Boolean performingmoveoperation = false;

	private int eptype;
	private int eprow;
	private int epnum;

	private String[] FileNameOnDrive = new String[250];

	// protected ProgressDialog progressDialog;

	static String[] from;

	static int PICTURESAMPLESIZE = 16;
	static int FLOORPLANPICTURESIZE = 4;
	static int PICTUREDISPLAYPERCENTOFSCREEN = 25;
	static int FLOORPLANPICTUREDISPLAYPERCENTOFSCREEN = 50;

	static int screenheight;
	static int screenwidth;
	private AutoCompleteTextView txtCalc = null;
	private Button btnZero = null;
	private Button btnOne = null;
	private Button btnTwo = null;
	private Button btnThree = null;
	private Button btnFour = null;
	private Button btnFive = null;
	private Button btnSix = null;
	private Button btnSeven = null;
	private Button btnEight = null;
	private Button btnNine = null;
	private Button btnPlus = null;
	private Button btnMinus = null;
	private Button btnMultiply = null;
	private Button btnDivide = null;
	private Button btnEquals = null;
	private Button btnC = null;
	private Button btnDecimal = null;
	private Button btnMC = null;
	private Button btnMR = null;
	private Button btnMM = null;
	private Button btnMP = null; 
	private Button btnBS = null;
	private Button btnPerc = null;
	private Button btnSqrRoot = null;
	private Button btnPM = null;
	private double num = 0;
	private double memNum = 0;
	private int operator = 1;
	private boolean readyToClear = false;
	private boolean hasChanged = false;
	private static View calc;

	private static int SITE_LIST = 0;
	static int MASTER_LIST = 1;

	final int COMPANY = 0;
	final int SITE = 1;
	final int BUILDING = 2;
	final int FLOOR = 3;
	final int ZONE = 4;
	final int ROOM = 5;
	
//masterlistsheet 0 general
	private int ASSETTYPES = 0;
	private int COMPONENTTYPES = 1;
	private int RECOMMENDATIONTYPES = 2;
	private int RECOMMENDATIONSAREASOFOPPORTUNITY = 3;
	private int RECOMMENDATIONOBSERVATIONS = 4;
	private int GENERICRECOMMENDATIONSSUMMARY = 5;
	private int GENERICRECOMMENDATIONSDETAILED = 6;
	private int VOLTAGE = 7;
	private int PH = 8;
	private int TRANSCONFIG = 9;
	private int CTTYPE = 10;
	private int LOADTYPE = 5;
	private int COMPANYNAME = 12;
	private int ENGINEERNAME = 13;
	private int ENGINEEREMAIL = 14;
	private int ENGINEERPHONENUMBER = 15;
	private int FLOORNUMBERS=16;
	private int COMMONMANUFACTURES = 17;
	private int PREPOSITIONS=18;
	
	//sheet 1 model information
	
	//sheet 2
	
	//largest floorplan that will 
	static int maxfloorplansize=600000;
	
	private int COMMONLOCATIONS = 0;
	static int COMMONTEMPERATURESENSORLOCATIONS = 1;
	int ZONETYPE = 2;
	static int CORRESPONDINGZONETYPE = 3;
	private int COMMONLOADNAMES = 4;

	static int GETPICFROMCAMERA = 0;
	private int CALCULATOR = 1;
	private int GETPICFROMFILE = 2;
	private int OFFICEACTIVITY = 4;
	private int GETCOVERPAGEPATH = 10;
	private int GETCLIENTLOGOPATH = 16;
	final int GETIMPORTMCRLOCATION=17;
	private final int FLOORPLANACTIVITY = 18;
	int GETASSETIMPORTLOCATION=20;
	
	static final int REQUEST_AUTHORIZATION = 5;
	static final int CAPTURE_IMAGE = 6;
	static int EDITPICTUREACTIVITY = 7;
	private int SELECTFILETOOPEN = 8;
	private final int BARCODE_SCAN = 9;
	private final int PREFS = 10;
	
	
	private static int fontsize = 30;
	final double mtfc = 10.7639104;

	private static int typeforgetpicture;
	static int n = 250;
	static int[] componentpicnum = new int[maxcomponents];
	static int[] assetpicnum = new int[maxassets];
	int[] sitepicnum = new int[maxlocations];
	static int maxtabs=13;
	
	
	static int maximagesperfolder=30;
	static int maxsiteaudititemspertab=Math.max(maxcomponents, Math.max(maxassets, maxlocations));
	static String imagepaths[][][]= new String[maxtabs][maxsiteaudititemspertab][maximagesperfolder];
	int floorplanpicnum = 0;
	static int componentcount = 0;
	static int assetcount = 0;

	int c = 0;
	int i = 0;
	int s = 0;
	int b = 0;
	int f = 0;
	int z = 0;
	int rm = 0;
	int r = 0;
	
	static int maxfiles = 15;
	static int maxsheets = 10;
	static int maxexcelrows =150;
	static int maxexcelcolumns = 150;
	
	static int[] m = new int[maxsheets];
	int[][] melc = new int[maxsheets][maxexcelrows];
	int sv = 0;
	// determines alot how much memory app takes
	

	public static boolean execsum;
	public static boolean facility;
	public static boolean facilityasset;
	public static boolean facilityenergy;
	public static boolean BOM;
	public static boolean floorplan;
	public static boolean load;
	public static boolean main1;
	public static boolean main2;
	public static boolean metering;
	public static boolean optimization;
	public static boolean riser;
	public static boolean tempsensorlist;
	public static boolean SAMreferencetable;
	public static boolean lhiassets;
	public static boolean sitesassets;
	public static boolean sitespecificrecommendations;
	public static boolean sitenotes;
	
	
	
	TableLayout[][] mcrtl = new TableLayout[maxsheets][maxexcelrows];
	TableRow[][][] mcrtr = new TableRow[maxsheets][maxexcelrows][maxexcelcolumns];
	TextView[][][] mcrtitles = new TextView[maxsheets][maxexcelrows][maxexcelcolumns];
	static AutoCompleteTextView[][][] mcrentries = new AutoCompleteTextView[maxsheets][maxexcelrows][maxexcelcolumns];
	CheckBox[][] mcrcheckbox = new CheckBox[maxsheets][maxexcelrows];
	TextView[][][] mcrtventries = new TextView[maxsheets][maxexcelrows][maxexcelcolumns];
	Spinner[][][] mcrspinnerentries = new Spinner[maxsheets][maxexcelrows][maxexcelcolumns];
	LinearLayout[] mcrtable = new LinearLayout[maxsheets];
	
	LinearLayout virtualmcrtable;
	TableLayout[] virtualmcrtablelayouts = new TableLayout[maxvirtualmeters];
	
	String[][] mcrtabletitles = new String[maxsheets][maxexcelcolumns];
	ImageView[][][] mcrdeletebutton = new ImageView[maxsheets][maxexcelrows][maxexcelcolumns];
	ImageView[][][] mcrtemdeletebutton = new ImageView[maxsheets][maxexcelrows][maxexcelcolumns];
	String[] mcrtabletitlesneat = { "Item#", "ELC#", "Load Name",
			"Panel Location", "Voltage", "PH", "Trans Config", "Breaker Size",
			"", "CT Type", "", "", "Comments", "Load Type",
			"Loads on each breaker or panel" };
	String[] mcrtemptabletitlesneat = { "Item#", "Floor#", "ELC#",
			"Reference on Map", "Zone Name", "Analog Input #", "Comments/Locations", "ELC Temps",
			"Handheld Temps", "Offset +/-","Zone Type"};
	public static ImageView[][][] plusbuttoniv = new ImageView[maxsheets][maxexcelrows][maxexcelcolumns];

	// Ibe's bom variables
	static TableLayout bomcttablelayout;

	static TextView bomgatewayservercount;
	static TextView bomelccount;
	static TextView bomsamcount;
	static TextView bomtscount;
	static TextView bomCTcount;

	static AutoCompleteTextView bomtsdistance;
	static AutoCompleteTextView bomctdistance;
	static AutoCompleteTextView bompowerwiredistance;

	static AutoCompleteTextView bommisccount;

	static TextView UnitCostFill;
	
	static AutoCompleteTextView bomgatewayserverunitcost;
	static AutoCompleteTextView bomsamunitcost;
	static AutoCompleteTextView bomelcunitcost;
	static AutoCompleteTextView bomtsunitcost;
	static AutoCompleteTextView bomCTunitcost;

	static AutoCompleteTextView bomtsdistanceunitcost;
	static AutoCompleteTextView bomctdistanceunitcost;
	static AutoCompleteTextView bompowerwiredistanceunitcost;

	static AutoCompleteTextView bommisccountunitcost;

	static TextView bomgatewayservertotalcost;
	static TextView bomelctotalcost;
	static TextView bomsamtotalcost;
	static TextView bomtstotalcost;
	static TextView bomCTtotalcost;

	static TextView bomtsdistancetotalcost;
	static TextView bomctdistancetotalcost;
	static TextView bompowerwiredistancetotalcost;

	static TextView bommisctotalcost;

	static TextView bomtotalcost;

	//site recommendations
	static Spinner recommendationsitetype;
	static CheckBox genericcheck1;
	static CheckBox genericcheck2;
	static AutoCompleteTextView genericnotes1;
	static AutoCompleteTextView genericnotes2;

	
	String vrformula1column="R";
	String vrmetername1column="S";
	String vrloadtype1column="T";
	String vrformula2column="U";
	String vrmetername2column="V";
	String vrloadtype2column="W";

	static CheckBox grocerycheck1;
	static CheckBox grocerycheck2;
	static AutoCompleteTextView grocerynotes1;
	static AutoCompleteTextView grocerynotes2;
	static CheckBox grocerycheck3;
	static AutoCompleteTextView grocerynotes3;
	
	static ArrayList<String> mcr_temp = new ArrayList<String>();
	static ArrayList<String> report_temp = new ArrayList<String>();
	
	static int maxassetdistinguishers=10;
	public static String[][] Assetvalues = new String[maxassetdistinguishers][maxassets];
	static int consumptiondestinguishers=4;
	public static String[][] Consumptionvalues = new String[consumptiondestinguishers][maxexcelcolumns];
	public static String[][] ELUTIONSAssetvalues = new String[maxitems][maxexcelcolumns];

	private AlertDialog pd;
	private static AlertDialog deletepicturedialog;
	private AlertDialog deleteorduplicatepicturedialog;
	private static AlertDialog deleteorduplicatefloorplandialog;
	private static Locale[] locales = Locale.getAvailableLocales();
	private static List<Locale> localeslist = new ArrayList<Locale>();
	private static Locale locale = Locale.FRANCE;
	private static Locale secondlocale = Locale.US;
	private static List<String> currencylist = new ArrayList<String>();
	private static List<String> currencycodelist = new ArrayList<String>();
	private static List<String> currencysymbollist = new ArrayList<String>();
	private  RelativeLayout conscalcfilm;

	public static String floorplandbname="floorplan";
	
	private  RelativeLayout siteinfotabview;
	private  RelativeLayout componentstabview;
	private  RelativeLayout assetstabview;
	private  RelativeLayout consumptiontabview;
	
	private  RelativeLayout mcrtabview;
	private  RelativeLayout recommendationstabview;
	private  LinearLayout fileexplorertabview;

	private  RelativeLayout mcrview1;
	private  RelativeLayout mcrview2;
	private  RelativeLayout mcrview3;
	private  RelativeLayout mcrview4;
	private  RelativeLayout mcrview5;

	private  LinearLayout locationcontainer;
	private LinearLayout componentscontainer;
	private LinearLayout assetscontainer;
	private LinearLayout recommendationscontainer;
	private TableLayout recommendationscalcstable;
	private TableLayout sitetyperecommendations;
	
	private TableLayout engineerinfotable;
	 TableLayout[] componenttable = new TableLayout[maxcomponents];
	 TableLayout[] assettable = new TableLayout[maxassets];
	 TableLayout[] buildingtable = new TableLayout[maxlocations];
	private TableRow rctr1[] = new TableRow[maxrecommendations];
	private TableLayout[] recommendationtable = new TableLayout[maxrecommendations];
	
	private String darkblue = "#1BADA9";
	private String verylightblue = "#9AF7F3";
	private String lightblue = "#99E4E1";
	private String extremelylightblue = "#DFFFFD";
	private String thelightestblue = "#F3FFFE";
	
	private String darkgreen = "#19A83D";
	private String extremelylightgreen = "#A2FAB8";
	private String thelightestgreen = "#D1FFDD";
	
	private int transparentred = 0x66ff0000;
	
	public String StorageKey;
	
	// Latex variables initialization
	private  String clientnamelatex = "Morrisons";
	private  String headermaestrologo = "./Pictures/Latex_maestrologo.jpg";
	private  String paperwidthlatex = "8.5in";
	private  String paperheightlatex = "11.5in";
	private String coverpagewidthlatex = "8.5in";
	private String coverpageheightlatex = "11in";
	private String coverpagefilepathlatex = "./Pictures/Latex_Coverpage_Final.png";
	private String footercolorlatex = "165,228,250";
	private String coverpagebackgroundcolor = "4,5,105";
	private String tabletitlecolor = "165,228,250";
	private String tabledeepcolor = "224,224,224";
	private String tablelightcolor = "255,255,255";
	private String sectionheadingcolorlatex = "0,76,153";
	private String subselectionhead2 = "51,153,255";

	String PREFS_ENGINEERNAMESPINNERPOSITION="0";
	
	boolean siteadded=false;
	boolean companyadded=false;
	static String ProjectDirectory;
	public static String Siteslistlocation;
	public static String dcimlocationstring;
	private File folder;
	public static String foldername;
	private String seriesname;
	private String tab;
	
	private String tabimageresolutions;
	static int typeselected;
	static String numberselected;
	private String subnumberselected;
	private static SharedPreferences settings;
	private String PREFS_NAME = "Preferences";
	private static SharedPreferences.Editor editor;
	static String masterfoldername;

	private static Spinner engineernamesp;
	private static AutoCompleteTextView swdateet;
	private static TextView engineeremailtv;
	private static TextView engineerphonenumtv;

	private static SiteAuditItem sitename[] = new SiteAuditItem[maxlocations];
	private static SiteAuditItem sitesize[] = new SiteAuditItem[maxlocations];
	private static SiteAuditItem sitedescription[] = new SiteAuditItem[maxlocations];
	public static List<String> locationslist;
	public static List<String> panelslist;
	public static List<String> assetslist;
	public static List<String> componentslist;


	public static List<String> engineernameslist;
	public static List<String> engineeremaillist;
	public static List<String> engineerphonenumberlist;

	public static ImageView floorplanpicturebutton;
	static ImageView sitefpimageview[] = new ImageView[maxfloorplans];
	static TextView sitefptextview[] = new TextView[maxfloorplans];
	HorizontalScrollView horizsite[] = new HorizontalScrollView[maxlocations];

	private ImageView sitepicturebutton[] = new ImageView[maxlocations];
	private static ImageView sitepicture[][] = new ImageView[maxlocations][maximagesperfolder];
	private HorizontalScrollView horizfp;


	private TableRow ctr1[] = new TableRow[maxcomponents];
	static SiteAuditItem componentname[] = new SiteAuditItem[maxcomponents];
	private static TableRow ctr3[] = new TableRow[maxcomponents];
	 SiteAuditItem componenttype[] = new SiteAuditItem[maxcomponents];
	private TableRow ctr5[] = new TableRow[maxcomponents];
	private SiteAuditItem componentspec[] = new SiteAuditItem[maxcomponents];
	private TableRow ctr6[] = new TableRow[maxcomponents];
	private SiteAuditItem componentlocation[] = new SiteAuditItem[maxcomponents];
	private TableRow ctr8[] = new TableRow[maxcomponents];
	static SiteAuditItem componentnotes[] = new SiteAuditItem[maxcomponents];
	private TableRow ctr9[] = new TableRow[maxcomponents];
	ImageView componentpicturebutton[] = new ImageView[maxcomponents];
	private ImageView componentbarcodebutton[] = new ImageView[maxcomponents];
    HorizontalScrollView horizcomponent[] = new HorizontalScrollView[maxcomponents];
	private TableRow ctr10[] = new TableRow[maxcomponents];
	static ImageView componentpicture[][] = new ImageView[maxcomponents][maximagesperfolder];
	private TableRow ctr11[] = new TableRow[maxcomponents];
	
	private TableRow tr1[] = new TableRow[maxassets];
	static SiteAuditItem assetname[] = new SiteAuditItem[maxassets];
	private TableRow tr2[] = new TableRow[maxassets];
	SiteAuditItem quantity[] = new SiteAuditItem[maxassets];
	private TableRow tr3[] = new TableRow[maxassets];
	SiteAuditItem type[] = new SiteAuditItem[maxassets];
	private TableRow tr4[] = new TableRow[maxassets];
	 SiteAuditItem model[] = new SiteAuditItem[maxassets];
	private  TableRow tr5[] = new TableRow[maxassets];
	private  SiteAuditItem spec[] = new SiteAuditItem[maxassets];
	private  TableRow tr6[] = new TableRow[maxassets];
	private  SiteAuditItem location[] = new SiteAuditItem[maxassets];
	private  TableRow tr7[] = new TableRow[maxassets];
	private  SiteAuditItem servicearea[] = new SiteAuditItem[maxassets];
	private  TableRow tr8[] = new TableRow[maxassets];
	 SiteAuditItem assetnotes[] = new SiteAuditItem[maxassets];
	private  TableRow tr9[] = new TableRow[maxassets];
	 ImageView picturebutton[] = new ImageView[maxassets];
	private  ImageView barcodebutton[] = new ImageView[maxassets];
	 HorizontalScrollView horizasset[] = new HorizontalScrollView[maxassets];
	private  TableRow tr10[] = new TableRow[maxassets];
	 static ImageView picture[][] = new ImageView[maxassets][maximagesperfolder];
	private  TableRow tr11[] = new TableRow[maxassets];
	
	
	
	public static TextView constablename;
	private TextView constablemeters;
	private TextView constablefeet;
	private TextView constableavgmoncons;
	private double constableavgmonconsdoub;
	private double[] conslocdoub = new double[12];
	private AutoCompleteTextView[] consloc = new AutoCompleteTextView[12];
	private double[] consusddoub = new double[12];
	private TextView[] consusd = new TextView[12];
	private double[] consdoub = new double[12];
	private AutoCompleteTextView[] cons = new AutoCompleteTextView[12];
	private static TextView[] month = new TextView[12];
	private ImageView[] monthfilebutton = new ImageView[13];
	private double constotdoub;
	private TextView constot;
	private double constotlocdoub;
	private TextView constotloc;
	private double constotusddoub;
	private TextView constotusd;
	private ImageView calculatorbutton;
	private boolean calcisopen = false;
	private ImageView conversionratesgooglebutton;
	private ImageView conversionrateswebpagebutton;
	private Spinner localcurrencydisplayspinner;
	private Spinner secondcurrencydisplayspinner;
	private  double kwhratiodoub;
	private  TextView kwhratiotextview;
	private  double currencyratiodoub;
	private  AutoCompleteTextView currencyratio;
	private  TextView consumptioncurrencytopdisplay;
	private  TextView secondconsumptioncurrencytopdisplay;
	private  TextView localcurrencydisplaytext;
	private  TextView recssitenametextview;
	private  TextView recsareatextview;
	private  TextView recselectricconsumptiontextview;
	private  TextView recscostratiotitletextview;
	private  TextView recscostratiotextview;
	private double kwhmyrratio;
	private  TextView recsratiotextview;
	private  TextView recommendationnumbertextview[] = new TextView[maxrecommendations];
	private  Spinner recommendationtypespinner[] = new Spinner[maxrecommendations];
	private  Spinner recommendationareaofopportunityspinner[] = new Spinner[maxrecommendations];
	private  Spinner recommendationobservationsspinner[] = new Spinner[maxrecommendations];
	private  AutoCompleteTextView recommendationrecommendationsAutoCompleteTextView[] = new AutoCompleteTextView[maxrecommendations];
	private  double[] recommendationeaskwhdoub = new double[maxrecommendations];
	private  AutoCompleteTextView recommendationeaskwhAutoCompleteTextView[] = new AutoCompleteTextView[maxrecommendations];
	private  LinearLayout eassplitlinearlayout[] = new LinearLayout[maxrecommendations];
	private  double recommendationeascurrencydoub1[] = new double[maxrecommendations];
	private  TextView recommendationeascurrencytextview1[] = new TextView[maxrecommendations];
	private  double recommendationeascurrencydoub2[] = new double[maxrecommendations];
	private  TextView recommendationeascurrencytextview2[] = new TextView[maxrecommendations];
	private  LinearLayout coisplitlinearlayout[] = new LinearLayout[maxrecommendations];
	private  double recommendationestimatedcoidoub1[] = new double[maxrecommendations];
	private  AutoCompleteTextView recommendationestimatedcoiAutoCompleteTextView1[] = new AutoCompleteTextView[maxrecommendations];
	private  double recommendationestimatedcoidoub2[] = new double[maxrecommendations];
	private  AutoCompleteTextView recommendationestimatedcoiAutoCompleteTextView2[] = new AutoCompleteTextView[maxrecommendations];
	private  double recommendationestimatedroidoub[] = new double[maxrecommendations];
	private  TextView recommendationestimatedroitextview[] = new TextView[maxrecommendations];
	private  TextView eastotal;
	private  double eastotaldoub;
	private  TextView eascurtotal1;
	private  double eascurtotal1doub;
	private  TextView eascurtotal2;
	private  double eascurtotal2doub;
	private  TextView coitotal1;
	private  double coitotaldoub1;
	private  TextView coitotal2;
	private  double coitotaldoub2;
	private  TextView totalsavingspercenttextview;
	private  double totalsavingspercentdoub;
	private  TextView recommendationnumbertextviewdetailed[] = new TextView[maxrecommendations];
	private  TextView issuenametextview[] = new TextView[maxrecommendations];
	private  Spinner issuespinner[] = new Spinner[maxrecommendations];
	private  TextView recommendationtextview[] = new TextView[maxrecommendations];
	public  AutoCompleteTextView recommendationAutoCompleteTextView[] = new AutoCompleteTextView[maxrecommendations];
	private  ImageView calculatorbuttoncoi;
	private  ImageView zoominbutton;
	private  ImageView zoomoutbutton;
	private  double coutbi = 0;
	private  double acb1u = 0;
	private  double sol = 0;
	private  double calccoidoub = 0;
	private  TabHost tabHost;
	
	private RelativeLayout parentlayout;
	private Typeface font;

	
	ArrayList<String> checklist;
	
	boolean[] mopreporttypestates;
	
	
	 static ImageView riserdiagrambutton;
	 static ImageView mopmcrbutton;
	 static ImageView loaddiagrambutton;
	 static ImageView mopfloorplanbutton;
	 static ImageView latexbutton;

	 static  ImageView savereportbutton;
	  ImageView elcconfigurationexportbutton;
	  ImageView uninstallbutton;
	  ImageView syncreportbutton;
	  ImageView emailreportbutton;

	  ImageView skypebutton;
	  ImageView outlookwebaccessbutton;
	  ImageView companywebsitebutton;
	  ImageView maestroverizonbutton;
	  ImageView googlemapsbutton;
	  ImageView myscriptcalculatorbutton;

	  TableRow str1[] = new TableRow[maxlocations];
	  TableRow str2[] = new TableRow[maxlocations];
	  TableRow str3[] = new TableRow[maxlocations];
	  TableRow str4[] = new TableRow[maxlocations];
	  TableRow str5[] = new TableRow[maxlocations];
	  TableRow str6[] = new TableRow[maxlocations];
	  TableLayout floorplanlayout;
	  TableRow floorplannames;
	  TableRow str7[] = new TableRow[maxlocations];

	 TableRow.LayoutParams str5lp;

	private  ImageView removelocationbutton[] = new ImageView[maxlocations];
	private  ImageView duplicatelocationbutton[] = new ImageView[maxlocations];
	private  ImageView moveuplocationbutton[] = new ImageView[maxlocations];
	private  ImageView movedownlocationbutton[] = new ImageView[maxlocations];

	 ImageView removeassetbutton[] = new ImageView[maxassets];
	private  ImageView duplicateassetbutton[] = new ImageView[maxassets];
	private  ImageView moveupassetbutton[] = new ImageView[maxassets];
	private  ImageView movedownassetbutton[] = new ImageView[maxassets];
	
	 ImageView removecomponentbutton[] = new ImageView[maxcomponents];
	private  ImageView duplicatecomponentbutton[] = new ImageView[maxcomponents];
	private  ImageView moveupcomponentbutton[] = new ImageView[maxcomponents];
	private  ImageView movedowncomponentbutton[] = new ImageView[maxcomponents];

	private  ImageView removerecommendationbutton[] = new ImageView[maxrecommendations];
	private  ImageView moveuprecommendationbutton[] = new ImageView[maxrecommendations];
	private  ImageView movedownrecommendationbutton[] = new ImageView[maxrecommendations];

	private  ArrayAdapter<String> companynamespinneradapter;
	static ArrayAdapter<String> assettypespinneradapter;
	static ArrayAdapter<String> componenttypespinneradapter;
	private  ArrayAdapter<String> recommendationtypeadapter;
	private  ArrayAdapter<String> recommendationareaofopportunityadapter;
	private  ArrayAdapter<String> recommendationobservationsadapter;
	private  ArrayAdapter<String> issuespinneradapter;
	private  ArrayAdapter<CharSequence> monthsadapter;
	private  ArrayAdapter<CharSequence> yearadapter;
	private  ArrayAdapter<String> locationspinneradapter;
	private  ArrayAdapter<String> smalllocationspinneradapter;
	private  ArrayAdapter<String> mcrloadnamespinneradapter;
	private  ArrayAdapter<String> mcrpanelnamespinneradapter;
	private  ArrayAdapter<String> mcrvoltagespinneradapter;
	 static ArrayAdapter<String> mcrphspinneradapter;
	 ArrayAdapter<String> mcrtempzonetypespinneradapter;
	private  ArrayAdapter<String> mcrtransconfigspinneradapter;
	 static ArrayAdapter<String> mcrloadtypespinneradapter;
	 static ArrayAdapter<String> mcrloadtypespinneradapterlargeview;
	 static ArrayAdapter<String> mcrcttypespinneradapter;
	 
	 
	 static ArrayAdapter<String> operatorsadapter;
	 static ArrayAdapter<String> allmeternamesArrayAdapter;
	 static List<String> realmeternames;
	 static List<String> virtualmeternames;
	 static List<String> allmeternames;

	private static ArrayAdapter<String> engineernamespinneradapter;

	// shyam elc config values
	static int totalmorrisonssites = 695;
	public  String[] Morrisons_Siteno = new String[totalmorrisonssites];
	public  String[] Morrisons_Sitename = new String[totalmorrisonssites];
	public  String[] Morrisons_IP = new String[totalmorrisonssites];
	public  String[] Morrisons_subnet = new String[totalmorrisonssites];
	public  String[] Morrisons_defaultgateway = new String[totalmorrisonssites];
	public  String[][] Morrisons_ELC = new String[maxitems][totalmorrisonssites];
	public  String[][][] Elcconfigurationvalues = new String[maxelcs][maxsamsperelc][totalmorrisonssites];
	public  int maxelcconfig = 0;
	public  int[] maxsamconfig = new int[maxelcs];
	public  int[] maxtempconfig = new int[maxelcs];
	public  String configsiteno = "0";
	public  Boolean siteexists = false;

	private  List<String> gr;
	private  List<String> grdetailed;
	private  Resources res;
	private  ListView fileexplorerlistview;
	private  ArrayAdapter<String> fileList;
	public static  List<String> docslist;
	private  List<String> prettydocslist;
	private  TabHost mTabHost;
	private  String mcrfilename;
	private  Workbook mcrworkbook;
	private  Workbook assetworkbook;
	private  WritableWorkbook mcrwriteworkbook;
	private  Sheet[] mcrsheet = new Sheet[maxsheets];
	private  Sheet assetsheet;
	private  WritableSheet[] writeablemcrsheet = new WritableSheet[maxsheets];
	private  boolean thisisfirstrun;
	protected ProgressDialog progressDialog;
	private  int[] maxx = new int[maxexcelcolumns];
	private  int[] maxy = new int[maxexcelrows];
	static String[][][] mcrstringarray = new String[maxsheets][maxexcelrows][maxexcelcolumns];
	private  int maxworkbooks = 20;
	private  int[][] wmaxx = new int[maxworkbooks][maxsheets];
	private  int[][] wmaxy = new int[maxworkbooks][maxsheets];
	private  int maxxdistance = 26;
	private  int maxydistance = 100;
	private  View ViewHolder[][][][] = new View[maxfiles][maxsheets][maxxdistance][maxydistance];
	
	
	final int REPORT = 0;
	final int GENERAL = 0;
	final static int COMPONENTS = 1;
	final static int ASSETS = 2;
	final int CONSUMPTION = 3;
	final int RECOMMENDATIONS = 4;

	final int COMMISSIONING_REQUIREMENTS_TRACKER_TEMPLATE = 8;
	final int ENERGY_CONSUMPTION_TEMPLATE = 11;
	final int LISTS = 12;
	final int SITE_WALK_RECOMMENDATIONS_TABLE_TEMPLATE = 13;
	final int ASSETREPORT=14;
	
	
	public View[][] ViewHolderArea = new View[2][maxassets];
	public ScrollView tab0scrollview;
	public ScrollView tab1scrollview;
	public ScrollView tab2scrollview;
	public ScrollView tab3scrollview;
	public ScrollView tab4scrollview;
	public int SCROLPOSX=0;
	public int SCROLPOSY=0;
	Boolean saveincloud=false;
	
	LayoutParams lpff = new LayoutParams(LayoutParams.FILL_PARENT,
			LayoutParams.FILL_PARENT);
	static LayoutParams lpfw = new LayoutParams(LayoutParams.FILL_PARENT,
			LayoutParams.WRAP_CONTENT);
	LayoutParams lpwf = new LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.FILL_PARENT);
	static LayoutParams lpww = new LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);
	TableLayout.LayoutParams tlpfw = new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	public static WorkbookSettings ws;
	
	public static Workbook masterlistworkbook;
	public Sheet masterlistsheet1;
	public Sheet masterlistsheet2;
	public Sheet masterlistsheet3;
	public Sheet masterlistsheet4;
	
	public HashMap<String, String> filehash;
	public HashMap<String, String> linkhash;

	public static ImageView.OnLayoutChangeListener hourglasslistener;

	public static Location gpslocation = null;

	static int maxversions = 99999;
	static boolean ondrive = false;
	public static DatabaseHandler db;
	public static String databasefoldername;
	
	public static double ctdownsizepercent=0.65;

	public static int absolutescreenwidth;
	public static int absolutescreenheight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		absolutescreenwidth = size.x;
		absolutescreenheight = size.y;
	
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		if (!isTabletDevice()) {
			fontsize = 12;
		}
		ws = new WorkbookSettings();
		getAssetslist();

		res = getResources();
		getPreferences();
		
		databasefoldername=foldername;
		databasefoldername=u.finddatabasefoldername(masterfoldername,foldername,ctx);
		u.log("Foldername from databasename ,"+databasefoldername);
		db = new DatabaseHandler(Tabs1.this, foldername);
		masterdb = new DatabaseHandler(Tabs1.this, DatabaseHandler.MASTER_DATABASE);
		
		setcrashlogrecorder();
		
		try{
			getcompanyvalues();
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		
		try {
			 PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = pInfo.versionName;
	       // getActionBar().setTitle("Life Hack Innovations Site Audit Assistant V"+version); 
			getActionBar().setTitle("Life Hack Innovations Site Audit Assistant V"+version+" - " + foldername);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			this.setTitle("Life Hack Innovations Site Audit Assistant - " + foldername);
		}

		
		Boolean internaldbexists=false;
		if(db.checktableindb(DatabaseHandler.TABLE_ENGINEERINFO)){
			internaldbexists=true;
		}
		if(db.checktableindb(DatabaseHandler.TABLE_SITEINFO)){
			internaldbexists=true;
		}
		if(db.checktableindb(DatabaseHandler.TABLE_BOM)){
			internaldbexists=true;
		}
		
		if(internaldbexists){
			u.log("Internal Databaseexists");
		}else{
			try{
				u.log("Database Import Started");
				u.databaseimport(masterfoldername, foldername, ctx);
				
				if(!(u.databaseimports)){
					u.databaseimports=false;
					thisisfirstrun=true;
				}else{
					u.databaseimports=false;
					db = new DatabaseHandler(Tabs1.this, foldername);
				}
				
				if(db.checktableindb(DatabaseHandler.TABLE_M395)){
					u.log("TABLE_M395 exists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_ENGINEERINFO)){
					u.log("TABLE_ENGINEERINFO exists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_SITEINFO)){
					u.log("TABLE_SITEINFO exists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_ASSETINFO)){
					u.log("TABLE_ASSETINFO exists");
				}else{
					u.log("TABLE_ASSETINFO doesntexists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_BOM)){
					u.log("TABLE_BOM exists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_MCR_METERINGLIST)){
					u.log("TABLE_MCR_METERINGLIST exists");
				}
				if(db.checktableindb(DatabaseHandler.TABLE_FLOORPLAN)){
					u.log("TABLE_FLOORPLAN exists");
				}

				
			}catch(Throwable e1){
				e1.printStackTrace();
				thisisfirstrun=true;
			}
		}
		
		checkforfirstrun();
		//creating tables if got missed while creating or lost bcs corrupted
		db.createmissingtables();
		
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		if (foldername.equals("Paul")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.paul_animation));

		} else if (foldername.equals("Will")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.will_animation));

		} else if (foldername.equals("Bill")) {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.bill_animation));

		} else {
			progressDialog.setIndeterminateDrawable(getResources().getDrawable(
					R.anim.progress_dialog_icon_drawable_animation));

		}

		progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle("Loading");
		progressDialog.setMessage("Please Wait");
		progressDialog.setCancelable(false);
		progressDialog.setOnCancelListener(cancelListener);
	
		loadtask=new LoadTask(progressDialog).execute();

	}
	public void setcrashlogrecorder(){
		 String path=ProjectDirectory+"/CrashLogs";
	     try{
	    	 (new File(path)).mkdirs();
	     }catch(Throwable e){
	    	 
	     }
	     
		if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
	         Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(path,ctx));
	     }
		
	}
	protected class LoadTask extends AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public LoadTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {

			try {
				Looper.prepare();
			} catch (RuntimeException e) {

			}

			runOnUiThread(new Runnable() {
				public void run() {
					
					long time=System.currentTimeMillis();
					getPreferences();
					Log.d("timecheck","getPreferences() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					setvaluesbasedoncompany();
					Log.d("timecheck","setvaluesbasedoncompany() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					initializevalues();
					Log.d("timecheck","initializevalues() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					setuptabs();
					Log.d("timecheck","setuptabs() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					getscreensize();
					Log.d("timecheck","getscreensize() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					intializecalculator();
					Log.d("timecheck","intializecalculator() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

					
					
					Log.d("timecheck","setupdocstab() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
				

				
					intializeviewsfromxml();
					Log.d("timecheck","intializeviewsfromxml() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

				
					setupmainbuttons();
					Log.d("timecheck","setupmainbuttons() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
				
					
					Log.d("timecheck","setupsubmittabbuttons() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

				
				
					Log.d("timecheck","setupxtrastabbuttons() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

				}
			});
			long time=System.currentTimeMillis();
			getcurrencylists(null);
			Log.d("timecheck","getcurrencylists() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			
			setuparrayadapters();
			Log.d("timecheck","setuparrayadapters() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			
			
			
			try{
				destroysitestab();
				Log.d("timecheck","destroysitestab() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			}catch(Throwable e){
				
			}
			
			makeviewsforreadingsitesfromreportdb();
			Log.d("timecheck","makeviewsforreadingsitesfromreportdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
		//	makeviewsforreadingcomponentsfromreportdb();
			Log.d("timecheck","makeviewsforreadingcomponentsfromreportdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
		//	makeviewsforreadingassetsfromreportdb();
			Log.d("timecheck","makeviewsforreadingassetsfromreportdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

		//	makeviewsforreadingrecommendationsfromreportdb();
			Log.d("timecheck","makeviewsforreadingrecommendationsfromreportdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

			
			

		//	setuprecommendationstable();
			Log.d("timecheck","setuprecommendationstable() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			//createandlinkrecommendationviewstodb();
			Log.d("timecheck","createandlinkrecommendationviewstodb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			//siterecommendationlisteners();
			Log.d("timecheck","siterecommendationlisteners() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

			
//			runOnUiThread(new Runnable() {
//				public void run() {
//					long time=System.currentTimeMillis();	
				//	fillsiterecommendationvalues();
			Log.d("timecheck","fillsiterecommendationvalues() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
//
//				}
//			});
			
			if (thisisfirstrun) {
				dofirstrunstuff();
				Log.d("timecheck","dofirstrunstuff() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

				thisisfirstrun = false;
			}

		

			
			Log.d("timecheck","createbom() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			
			Log.d("timecheck","fillbomtabwdbvalues() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			
			Log.d("timecheck","createnotestab() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
			
			time=System.currentTimeMillis();
			getPreferences();
			Log.d("timecheck","getPreferences() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

			runOnUiThread(new Runnable() {
				public void run() {
					
					long time=System.currentTimeMillis();
					readsitedb();
					Log.d("timecheck","readreportdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

					
					Log.d("timecheck","repopulatemcrlocations() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					comparereportwithfilestructure(SITEINFOFOLDER);
					Log.d("timecheck","comparereportwithfilestructure() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
//					comparereportwithfilestructure(COMPONENTSFOLDER);
					Log.d("timecheck","comparereportwithfilestructure() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
//					comparereportwithfilestructure(ASSETSFOLDER);
					Log.d("timecheck","comparereportwithfilestructure() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					
					addpictures(SITETAB);
					Log.d("timecheck","addallpictures() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

					applyfontstoeverything();
					Log.d("timecheck","applyfontstoeverything() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

				//	setupprogressreporttab();

					

					foldersync();
					Log.d("timecheck","foldersync() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
					SITETABLOADED=true; 

				}
			});
			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			// progressDialog = new ProgressDialog(Tabs1.this);

			try {
				progressDialog.show();
			} catch (Throwable e) {

			}
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				progressDialog.dismiss();
			} catch (Throwable e) {

			}

		}
	}

	public void addnameanddatelines() {
		LayoutParams nadtextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams nadAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);

		engineerinfotable = new TableLayout(this);
		engineerinfotable.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		engineerinfotable.setBackgroundColor(Color.parseColor(darkblue));
		engineerinfotable.setPadding(2, 2, 2, 2);

		// engineer name

		TableRow nadr1 = new TableRow(this);
		nadr1.setLayoutParams(lpfw);

		TextView engineernamelabel = new TextView(this);
		engineernamelabel.setLayoutParams(nadtextviewparams);
		engineernamelabel.setText("Engineer Name:");

		engineernamesp = new Spinner(this);
		engineernamesp.setLayoutParams(nadAutoCompleteTextViewparams);
		engineernamesp.setAdapter(engineernamespinneradapter);
		u.log("PREFS_ENGINEERNAMESPINNERPOSITION"+PREFS_ENGINEERNAMESPINNERPOSITION);
		
		engineernamesp.setSelection(u.i(PREFS_ENGINEERNAMESPINNERPOSITION));
		engineernamesp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try{
					String selection = ((TextView) view).getText().toString();
					
	
					if (selection.equals("new")) {
						addnewvaluetoengineerlistdialog((Spinner) parent,
								engineernamespinneradapter, MASTER_LIST);
	
					} else {
						String engemail = engineeremaillist.get(0);
						String fonenumber = engineerphonenumberlist.get(0);
						try{
						engemail = engineeremaillist.get(position);
						fonenumber = engineerphonenumberlist.get(position);
						}catch(Throwable e){
							
						}
						
						engineeremailtv.setText(engemail);
						engineerphonenumtv.setText(fonenumber);
	
						if (SITETABLOADED) {
							db.addorupdate(DatabaseHandler.TABLE_ENGINEERINFO, DB_engineername,
									selection);
							db.addorupdate(DatabaseHandler.TABLE_ENGINEERINFO, DB_engineeremail,
									engemail);
							db.addorupdate(DatabaseHandler.TABLE_ENGINEERINFO,
									DB_engineerphonenumber, fonenumber);
							
	
						}
						set("engineernameposition", u.s(position));
					}
					
				}catch(Throwable e){
				}
			
				
			}
			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}

		});

		nadr1.addView(engineernamelabel);
		nadr1.addView(engineernamesp);

		// engineer date

		TableRow nadr2 = new TableRow(this);
		nadr2.setLayoutParams(lpfw);

		TextView swdatelabel = new TextView(this);
		swdatelabel.setLayoutParams(nadtextviewparams);
		swdatelabel.setText("Site Walk Date:");

		swdateet = new AutoCompleteTextView(this);
		swdateet.setLayoutParams(nadAutoCompleteTextViewparams);
		swdateet.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				String date = ((TextView) v).getText().toString();
				if (SITETABLOADED) {
					db.addorupdate(DatabaseHandler.TABLE_ENGINEERINFO, "date", date);
				}

			}
		});

		nadr2.addView(swdatelabel);
		nadr2.addView(swdateet);

		// engineer date

		TableRow nadr3 = new TableRow(this);
		nadr3.setLayoutParams(lpfw);

		TextView engineeremaillabel = new TextView(this);
		engineeremaillabel.setLayoutParams(nadtextviewparams);
		engineeremaillabel.setText("Engineer Email:");

		engineeremailtv = new TextView(this);
		engineeremailtv.setLayoutParams(nadAutoCompleteTextViewparams);

		nadr3.addView(engineeremaillabel);
		nadr3.addView(engineeremailtv);

		// engineer date

		TableRow nadr4 = new TableRow(this);
		nadr4.setLayoutParams(lpfw);

		TextView engineerphonenum = new TextView(this);
		engineerphonenum.setLayoutParams(nadtextviewparams);
		engineerphonenum.setText("Engineer Phone Number:");

		engineerphonenumtv = new TextView(this);
		engineerphonenumtv.setLayoutParams(nadAutoCompleteTextViewparams);

		nadr4.addView(engineerphonenum);
		nadr4.addView(engineerphonenumtv);

		// tying up engineer vies

		nadr1.setBackgroundColor(Color.parseColor(extremelylightblue));
		nadr2.setBackgroundColor(Color.parseColor(thelightestblue));
		nadr3.setBackgroundColor(Color.parseColor(extremelylightblue));
		nadr4.setBackgroundColor(Color.parseColor(thelightestblue));

		engineerinfotable.addView(nadr1);
		engineerinfotable.addView(nadr2);
		engineerinfotable.addView(nadr3);
		engineerinfotable.addView(nadr4);

		try{
			locationcontainer.addView(engineerinfotable);
		}catch(Throwable e){
			runOnUiThread(new Runnable() {
				public void run() {
					locationcontainer.addView(engineerinfotable);
				}});
		}
		
		link1cellto1view(REPORT, GENERAL, "B1", engineernamesp);
		link1cellto1view(REPORT, GENERAL, "B2", swdateet);
		link1cellto1view(REPORT, GENERAL, "B3", engineeremailtv);
		link1cellto1view(REPORT, GENERAL, "B4", engineerphonenumtv);

	}

	public void addlocation(int type) {

		if(type==COMPANY){
			companyadded=true;
		}
		if(type==SITE){
			siteadded=true;
		}
		
		LayoutParams loctextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams locAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);
		LayoutParams sitelocAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 6.5f);
		LayoutParams rlocAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 4.25f);
		LayoutParams locremovebuttonparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, .75f);
		LayoutParams unittextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, .5f);
		LayoutParams unitAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.125f);
		LayoutParams mapsbuttonparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, .75f);

		buildingtable[s] = new TableLayout(this);
		buildingtable[s].setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		buildingtable[s].setBackgroundColor(Color.parseColor(darkblue));
		buildingtable[s].setPadding(2, 2, 2, 2);

		// Row 1
		str1[s] = new TableRow(this);
		str1[s].setLayoutParams(lpfw);

		
		
		try{
			String Table=db.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitetype";
			String value=u.s(type);
			int row=s;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			db.addorupdatemulticolumn(Table, row, column , value , Key);
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		u.log("showing db after add sitetype");
		db.showfulldblog(db.TABLE_SITEINFO);
		
		
		sitename[s] = new SiteAuditItem(this);
		sitename[s].label.setLayoutParams(loctextviewparams);
		sitename[s].et.setLayoutParams(rlocAutoCompleteTextViewparams);
		sitename[s].et.setHint("");

		if (type == COMPANY) {
			sitename[s].label.setText("Company Name:");
			// sitename[s].et.setLayoutParams(locAutoCompleteTextViewparams);
			sitename[s].sp.setLayoutParams(locAutoCompleteTextViewparams);
			sitename[s].sp.setAdapter(companynamespinneradapter);
			u.log("Is morrisons"+ISMORRISONS);
			if(ISMORRISONS){
				sitename[s].sp.setSelection(getIndexofSpinner(sitename[s].sp, morrisonscompanyname));
				
				
				try{
					int companynamerow=0;
					String Table=db.TABLE_SITEINFO;
					String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
					StorageKey="sitename";
					String value=morrisonscompanyname;
					int row=companynamerow;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					db.addorupdatemulticolumn(Table, row, column , value , Key);
				}catch(Throwable e){
					e.printStackTrace();
				}
				
				
			}
			// skip first spinner selection

			sitename[s].sp
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {

//							if (!firstlocationsspinnerselectionskipped) {
//								firstlocationsspinnerselectionskipped = true;
//							} else {
								oldname = null;
								newname = null;
								try {
									oldname = sitename[0].string;
								} catch (NullPointerException e) {

								}

								String selection = ((TextView) view).getText()
										.toString();
								if (selection.equals("new")) {
									addnewvaluetolistdialog((Spinner) parent,
											COMPANYNAME,
											companynamespinneradapter,
											MASTER_LIST);
								} else {
									int s = getsequencenumber(sitename, view)[0];
									sitename[0].string = sitename[0].sp
											.getSelectedItem().toString();
									newname = sitename[0].string;

									addlocationtospinners(s, sitename[0].string);
									u.log(locationslist);
									u.log("onitemselected");
									displaydoubles();
									// Do Something
									
									if (!(position == 0)) {
										setcompanylogo(oldname,position);
									}
									if(selection.equals("Morrisons")){
										ISMORRISONS=true;
										ISGROCERYSTRORE=true;
										setstoretypegrocery();
									}
									if(selection.equals("Sainsbury's")){
										ISGROCERYSTRORE=true;
										setstoretypegrocery();
									}
									if(selection.equals("Consolidated")){
										ISDATACENTER=true;
										setstoretypedatacenter();
									}
									if(selection.equals("Sprint")){
										ISDATACENTER=true;
										setstoretypedatacenter();
									}


								}
								int type = SITETAB;
								String storagevariable = "s";
								countpics(type, 0);
								if (!performingmoveoperation) {
									String[] foldernames = u
											.listfolders(ProjectDirectory + "/"
													+ basedirectory[SITEINFOFOLDER]);
									String[] filenames = new String[0];

									String foldername = null;

									getfilenamesloop: for (int m = 0; m < foldernames.length; m++) {
										if (foldernames[m].contains("_"
												+ storagevariable + "_"
												+ u.s(s) + "_")) {
											foldername = foldernames[m];
											filenames = printFileNames(new File(
													foldernames[m]));
											break getfilenamesloop;
										}

									}

									// String[] filenames=printFileNames(new
									// File(ProjectDirectory));
									// checkstring[num]
									// =buildimagenamestring(type,k,num,null);
									List<String> rowAfilenames = new ArrayList<String>();
									for (int n = 0; n < filenames.length; n++) {
										if (filenames[n].contains("_"
												+ storagevariable + "_" + s
												+ "_")) {
											try {
												rowAfilenames.add(filenames[n]);

												
												
												File oldfile = new File(
														filenames[n]);
												
												String oldfilenamewithoutpath = filenames[n]
														.split("/")[filenames[n]
														.split("/").length - 1];
												String newfilenamewithoutpath = oldfilenamewithoutpath
														.replace(oldname,
																newname);
												File newfile = new File(
														filenames[n]
																.replace(
																		oldfilenamewithoutpath,
																		newfilenamewithoutpath));
												
												oldfile.renameTo(newfile);
											} catch (NullPointerException e) {

											}
										}
									}

									try {
										File folder = new File(foldername);
										File newfolder = new File(foldername
												.replace(oldname, newname));
										folder.renameTo(newfolder);
									} catch (NullPointerException e) {
										
									}

								}

								
								if (SITETABLOADED) {
									
									try{
										String Table=db.TABLE_SITEINFO;
										String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
										StorageKey="sitename";
										String value=((TextView) view)
												.getText().toString();
										int row=0;
										int column=db.getcolumnnumberbytitle(Table,StorageKey);
										db.addorupdatemulticolumn(Table, row, column , value , Key);
										u.log("companyname added to table");
									}catch(Throwable e){
										e.printStackTrace();
									}
									
								}
							}

				//		}

						public void onNothingSelected(AdapterView<?> parent) {
							showToast("Spinner1: unselected");
						}

					});

			link1cellto1view(REPORT, GENERAL, "B" + u.s(6), sitename[s].sp);

		} else if (type == SITE) {
			sitename[s].label.setText("Site Name:");
			sitename[s].et.setLayoutParams(locAutoCompleteTextViewparams);
			if (ISMORRISONS){
				
			
				sitename[s].et.setText(foldername);
					try{
						String Table=DatabaseHandler.TABLE_SITEINFO;
						String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
						StorageKey="sitename";
						String value=foldername;
						int row=s;
						int column=db.getcolumnnumberbytitle(Table,StorageKey);
						db.addorupdatemulticolumn(Table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
			}
			
		} else if (type == BUILDING) {
			sitename[s].label.setText("Building Name:");
		} else if (type == FLOOR) {
			sitename[s].label.setText("Floor Name:");
			sitename[s].et.setHint("B1F1, parking lot, ground floor, f1 etc..");
		} else if (type == ZONE) {
			sitename[s].label.setText("Zone Name:");
		} else if (type == ROOM) {
			sitename[s].label.setText("Room Name:");
			
			
			sitename[s].et.setAdapter(commonroomsadapter);
			sitename[s].et.setThreshold(0);
		}

		View.OnFocusChangeListener sitenamelistener = new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					int s = getsequencenumber(sitename, v)[0];

					if (hasinvalidcharacters((AutoCompleteTextView) v, false) == true
							&& hasFocus == false) {
						removeinvalidcharacterdialog(v);
					} else {
						String oldname = sitename[s].string;
						sitename[s].string = sitename[s].et.getText()
								.toString();
						String newname = sitename[s].string;
						if (oldname == null || oldname.length() == 0) {
							oldname = newname;
						}
						try {

							
							addlocationtospinners(s, sitename[s].string);
							u.log(locationslist);
							u.log("onfocuschange");

						} catch (IndexOutOfBoundsException e) {

						}
						
					
						// if images exist for site
						// change them all to the new name

						int type = SITETAB;
						String storagevariable = "s";
						countpics(type, s);
						if (!performingmoveoperation && hasFocus == false) {

							String[] foldernames = u
									.listfolders(ProjectDirectory + "/"
											+ basedirectory[SITEINFOFOLDER]);
							String[] filenames = new String[0];

							String foldername = null;

							getfilenamesloop: for (int m = 0; m < foldernames.length; m++) {
								
								if (foldernames[m].contains("_"
										+ storagevariable + "_" + u.s(s) + "_")) {
								
									foldername = foldernames[m];
									filenames = printFileNames(new File(
											foldernames[m]));
									break getfilenamesloop;
								}

							}

							// String[] filenames=printFileNames(new
							// File(ProjectDirectory));
							// checkstring[num]
							// =buildimagenamestring(type,k,num,null);
							List<String> rowAfilenames = new ArrayList<String>();
							for (int n = 0; n < filenames.length; n++) {
								if (filenames[n].contains("_" + storagevariable
										+ "_" + s + "_")) {
									rowAfilenames.add(filenames[n]);
									
									
									
									File oldfile = new File(filenames[n]);
									
									String oldfilenamewithoutpath = filenames[n]
											.split("/")[filenames[n].split("/").length - 1];
									
									String newfilenamewithoutpath = oldfilenamewithoutpath
											.replace(oldname, newname);
									
									File newfile = new File(
											filenames[n].replace(
													oldfilenamewithoutpath,
													newfilenamewithoutpath));
									
									oldfile.renameTo(newfile);
								}
							}

							try {
								File folder = new File(foldername);
								File newfolder = new File(foldername.replace(
										oldname, newname));
								folder.renameTo(newfolder);
							} catch (NullPointerException e) {
								
							}

							try{
								String Table=DatabaseHandler.TABLE_SITEINFO;
								String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
								StorageKey="sitename";
								String value=((TextView) v).getText().toString();
								int row=s;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}

						}
					}
				}
			}
		};

		sitename[s].et.setOnFocusChangeListener(sitenamelistener);
		int offset = 4;
		if (s == 0) {
			link1cellto1view(REPORT, GENERAL, "A6", sitename[s].label);
			link1cellto1view(REPORT, GENERAL, "B6", sitename[s].sp);
		} else {
			link1cellto1view(REPORT, GENERAL, "A" + u.s(4 * s + offset),
					sitename[s].label);
			link1cellto1view(REPORT, GENERAL, "B" + u.s(4 * s + offset),
					sitename[s].et);
		}

		str1[s].addView(sitename[s].label);
		if (type == COMPANY) {
			str1[s].addView(sitename[s].sp);
		} else {
			str1[s].addView(sitename[s].et);
		}

		if (!(type == SITE || type == COMPANY)) {
			moveuplocationbutton[s] = new ImageView(this);
			moveuplocationbutton[s].setLayoutParams(locremovebuttonparams);
			moveuplocationbutton[s].setImageResource(R.drawable.uparrow48);

			movedownlocationbutton[s] = new ImageView(this);
			movedownlocationbutton[s].setLayoutParams(locremovebuttonparams);
			movedownlocationbutton[s].setImageResource(R.drawable.downarrow48);

			duplicatelocationbutton[s] = new ImageView(this);
			duplicatelocationbutton[s].setLayoutParams(locremovebuttonparams);
			duplicatelocationbutton[s].setImageResource(R.drawable.copy48);

			removelocationbutton[s] = new ImageView(this);
			removelocationbutton[s].setLayoutParams(locremovebuttonparams);
			removelocationbutton[s].setImageResource(R.drawable.deleteitem48);

			str1[s].addView(moveuplocationbutton[s]);
			str1[s].addView(movedownlocationbutton[s]);
			str1[s].addView(duplicatelocationbutton[s]);
			str1[s].addView(removelocationbutton[s]);

			
			
			removelocationbutton[s]
					.setOnClickListener(new Button.OnClickListener() {
						public void onClick(View arg0) {
							int fosn = getsequencenumber(removelocationbutton,
									arg0)[0];
							
							remove(fosn, SITETAB, true);
						}
					});
		}

		str2[s] = new TableRow(this);
		str2[s].setLayoutParams(lpfw);

		sitesize[s] = new SiteAuditItem(this);
		sitesize[s].label.setLayoutParams(loctextviewparams);

		sitesize[s].et1.setLayoutParams(unitAutoCompleteTextViewparams);
		sitesize[s].et1.setSelectAllOnFocus(true);
		sitesize[s].et1.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		sitesize[s].tv1.setLayoutParams(unittextviewparams);
		sitesize[s].tv1.setText("m²");

		sitesize[s].et2.setLayoutParams(unitAutoCompleteTextViewparams);
		sitesize[s].et2.setSelectAllOnFocus(true);
		sitesize[s].et2.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		sitesize[s].tv2.setLayoutParams(unittextviewparams);
		sitesize[s].tv2.setText("ft²");

		switch (type) {
		case COMPANY:
			sitesize[s].label.setVisibility(View.INVISIBLE);
			sitesize[s].et1.setVisibility(View.INVISIBLE);
			sitesize[s].tv1.setVisibility(View.INVISIBLE);
			sitesize[s].et2.setVisibility(View.INVISIBLE);
			sitesize[s].tv2.setVisibility(View.INVISIBLE);
			break;
		case SITE:
			sitesize[s].label.setText("Site Size:");
			
			break;
		case BUILDING:
			sitesize[s].label.setText("Building Size:");
			break;
		case FLOOR:
			sitesize[s].label.setText("Floor Size:");
			break;
		case ZONE:
			sitesize[s].label.setText("Zone Size:");
			break;
		case ROOM:
			sitesize[s].label.setText("Room Size:");
			break;
		}

		addconversiontextchangelistener(s, "area");
		u.log("ISMORRISONS "+ISMORRISONS+"TYPE="+type);
		if(ISMORRISONS&&type==SITE){
			
			Cursor c=masterdb.getReadableDatabase().query(false, DatabaseHandler.TABLE_M395_SIZES, new String[]{"gross_internal_area_square_feet"},"default_store_number='"+storenumber+"'",null,null,null,null,null);
			c.moveToFirst();
			u.log("executing sql stuff");
			storesizeft="";
			masterdb.showfulldblog(DatabaseHandler.TABLE_M395_SIZES);
			try{
				
				storesizeft=c.getString(0);
				u.log("Store Size="+storesizeft);
				
				
				sitesize[s].doub2=(double)u.i(storesizeft);
				sitesize[s].doub1 = sitesize[s].doub2 / mtfc;
				sitesize[s].et2.setText(storesizeft);
				sitesize[s].et1.setText(u.sd(sitesize[s].doub2 / mtfc));
				
				try{
					String Table=DatabaseHandler.TABLE_SITEINFO;
					String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
					StorageKey="sitesizeft";
					String value=storesizeft;
					int row=1;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					db.addorupdatemulticolumn(Table, row, column , value , Key);
				}catch(Throwable e){
					e.printStackTrace();
				}
				try{
					String Table=DatabaseHandler.TABLE_SITEINFO;
					String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
					StorageKey="sitesizem";
					String value=u.sd(sitesize[s].doub2 / mtfc);
					int row=1;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					db.addorupdatemulticolumn(Table, row, column , value , Key);
				}catch(Throwable e){
					e.printStackTrace();
				}
				
				u.log(sitesize[s].et2.getText().toString());
				u.log(sitesize[s].et1.getText().toString());
				//sitesize[s].et2.requestFocus();
			}catch(Throwable e){
				e.printStackTrace();
			}
			

		}
		

		offset = 5;
		if (!(s == 0)) {
			link1cellto1view(REPORT, GENERAL, "A" + u.s(s * 4 + offset),
					sitesize[s].label);
			link1cellto1view(REPORT, GENERAL, "B" + u.s(s * 4 + offset),
					sitesize[s].et1);
			link1cellto1view(REPORT, GENERAL, "C" + u.s(s * 4 + offset),
					sitesize[s].tv1);
			link1cellto1view(REPORT, GENERAL, "D" + u.s(s * 4 + offset),
					sitesize[s].et2);
			link1cellto1view(REPORT, GENERAL, "E" + u.s(s * 4 + offset),
					sitesize[s].tv2);
		}

		str2[s].addView(sitesize[s].label);
		str2[s].addView(sitesize[s].et1);
		str2[s].addView(sitesize[s].tv1);
		str2[s].addView(sitesize[s].et2);
		str2[s].addView(sitesize[s].tv2);

		str3[s] = new TableRow(this);
		str3[s].setLayoutParams(lpfw);
		// description

		sitedescription[s] = new SiteAuditItem(this);
		sitedescription[s].label.setLayoutParams(loctextviewparams);

		switch (type) {
		case 0:
			str3[s].setVisibility(View.GONE);
			sitedescription[s].et.setVisibility(View.GONE);
			break;
		case 1:
			sitedescription[s].label.setText("Site Description:");
			sitedescription[s].et
					.setImeOptions(EditorInfo.IME_ACTION_UNSPECIFIED);
			sitedescription[s].et.setSingleLine(false);
			
			sitedescription[s].et.setHint("address, number of levels, etc...");
			
			try{		
				if(ISMORRISONS){
					
					Cursor c=masterdb.getReadableDatabase().query(false, DatabaseHandler.TABLE_M395, new String[]{"postcode","address_line_1","address_line_2","town_city","county"},"store_number='"+storenumber+"'",null,null,null,null,null);
					c.moveToFirst();
					u.log("executing sql stuff");
					storeaddress="";
					
						for (int x=0; x<c.getColumnCount(); x++){
									if(!(c.getString(x).trim().length()==0)){
										if(!(x==c.getColumnCount()-1)){
											storeaddress=storeaddress+c.getString(x)+"\n";
										}else{
											storeaddress=storeaddress+c.getString(x);
										}
									}
								}
					u.log(storeaddress);
					sitedescription[s].label.setText("Address:");
					sitedescription[s].et.setText(storeaddress);
					
					String Table=DatabaseHandler.TABLE_SITEINFO;
					String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
					StorageKey="sitedescription";
					String value=storeaddress;
					u.log(value);
					int row=s;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					db.addorupdatemulticolumn(Table, row, column , value , Key);
					
	
				}
			}catch(Throwable e){
				u.log("couldn't get store address from file");
				e.printStackTrace();
			}
			break;
		case 2:
			sitedescription[s].label.setText("Building Description:");
			sitedescription[s].et.setHint("age, occupany, etc..");
			sitedescription[s].et.setSingleLine(false);
			break;
		case 3:
			sitedescription[s].label.setText("Floor Description:");
			sitedescription[s].et
					.setHint("open spaces, data centers, HVAC, BMS etc..");
			sitedescription[s].et.setSingleLine(false);
			break;
		case 4:
			sitedescription[s].label.setText("Zone Description:");
			sitedescription[s].et
					.setHint("open spaces, data centers, HVAC, BMS etc..");
			sitedescription[s].et.setSingleLine(false);
			break;
		case 5:
			sitedescription[s].label.setText("Room Description:");
			sitedescription[s].et
					.setHint("open spaces, data centers, HVAC, BMS etc..");
			sitedescription[s].et.setSingleLine(false);
			
			break;
		}

		sitedescription[s].et.setLayoutParams(locAutoCompleteTextViewparams);
		if (s == 1) {
			sitedescription[s].et.setLayoutParams(sitelocAutoCompleteTextViewparams);
		}

		offset = 6;
		if (!(s == 0)) {
			link1cellto1view(REPORT, GENERAL, "A" + u.s(s * 4 + offset),
					sitedescription[s].label);
			link1cellto1view(REPORT, GENERAL, "B" + u.s(s * 4 + offset),
					sitedescription[s].et);

		}
		sitedescription[s].et
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						int s = getsequencenumber(sitedescription, v)[0];
						u.log(s);
						try{
							String Table=DatabaseHandler.TABLE_SITEINFO;
							String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
							StorageKey="sitedescription";
							String value=((TextView) v).getText().toString();
							u.log(value);
							int row=s;
							int column=db.getcolumnnumberbytitle(Table,StorageKey);
							db.addorupdatemulticolumn(Table, row, column , value , Key);
						}catch(Throwable e){
							e.printStackTrace();
						}	
						
					}
				});
		str3[s].addView(sitedescription[s].label);
		str3[s].addView(sitedescription[s].et);
		if (s == 1) {
			mapsbutton = new ImageView(this);
			mapsbutton.setLayoutParams(mapsbuttonparams);
			mapsbutton.setImageResource(R.drawable.maps48x48);
			mapsbutton.setOnClickListener(new ImageView.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(ISMORRISONS){
						String geoUriString="geo:0,0?q="+storeaddress;
						Uri geoUri = Uri.parse(geoUriString);
						
						Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
						startActivity(mapCall);
					}else{
						new GetAddressTask(progressDialog).execute();

					}

				}
			});
			str3[s].addView(mapsbutton);
		}

		str4[s] = new TableRow(this);
		str4[s].setLayoutParams(lpfw);
		// picturebutton

		sitepicturebutton[s] = new ImageView(this);
		lpww.gravity = Gravity.LEFT;
		sitepicturebutton[s].setLayoutParams(lpww);
		sitepicturebutton[s].setImageResource(R.drawable.addpicture);
		sitepicturebutton[s]
				.setOnClickListener(new ImageView.OnClickListener() {

					public void onClick(View arg0) {
						int s = getsequencenumber(sitepicturebutton, arg0)[0];
						
						if (s != 0
								&& sitename[s].et.getText().toString()
										.equals("")) {
							fieldcantbeleftblankdialog(sitename[s].et);
						} else if (s == 0
								&& sitename[s].sp.getSelectedItem().toString()
										.trim().length() == 0) {
							fieldcantbeleftblankdialog(sitename[s].sp);
						} else {

							SetTypeAndNumber(SITETAB, s);
							if(getCurrentFocus()!=null)getCurrentFocus().clearFocus();
							pictureorfiledialog(SITETAB, u.i(numberselected));
						}
					}
				});
		if (!(s == 0)) {
			str4[s].addView(sitepicturebutton[s]);
		}

		str5[s] = new TableRow(this);
		str5[s].setLayoutParams(lpfw);
		str5lp = new TableRow.LayoutParams();
		str5lp.weight = (1 / 4);
		
		try{
			sitepicnum[s] = countpics(SITETAB, s);
		}catch(Throwable e){
			sitepicnum[s] = 0;
		}
		
		u.log("counted pics on add location"+sitepicnum[s]);
		int[] num = new int[maximagesperfolder];
		for (num[s] = 0; num[s] <= sitepicnum[s]; num[s]++) {
			sitepicture[s][num[s]] = new ImageView(this);
			sitepicture[s][num[s]].setLayoutParams(lpfw);
			sitepicture[s][num[s]].setLayoutParams(str5lp);

			str5[s].addView(sitepicture[s][num[s]]);
		}

		horizsite[s] = new HorizontalScrollView(this);
		horizsite[s].setLayoutParams(lpfw);
		horizsite[s].addView(str5[s]);

		// Floor Plan Section
		if (s == 1) {
			str6[s] = new TableRow(this);
			str6[s].setLayoutParams(lpfw);
			// picturebutton

			floorplanpicturebutton = new ImageView(this);
			floorplanpicturebutton.setLayoutParams(lpww);
			floorplanpicturebutton.setImageResource(R.drawable.floorplan);

			floorplanpicturebutton
					.setOnClickListener(new ImageView.OnClickListener() {

						public void onClick(View arg0) {
							u.log("floorplaninputfloorplandirectory "+inputfloorplandirectory);
							new File(inputfloorplandirectory).mkdirs();
							u.log("making directories "+inputfloorplandirectory);
							
							
							floorplanstrings=new ArrayList<String>();
							for(File file:new File(inputfloorplandirectory).listFiles()){
								u.log("listing files "+file.getName());
								if (u.issupportedimage(file)){
									floorplanstrings.add(file.getAbsolutePath());
									u.log("Adding floorplanstrings to floorplanstrings "+file.getAbsolutePath());
								}
							}
							
							SetTypeAndNumber(FLOORPLANTAB,
									floorplanstrings.size());
							
							u.log("Starting pictureorfiledialog + number selected"+numberselected);
							pictureorfiledialog(typeselected,
									u.i(numberselected));
							
						}

					});
			str6[s].addView(floorplanpicturebutton);

			floorplanlayout = new TableLayout(this);
			floorplanlayout.setLayoutParams(lpfw);

			floorplannames = new TableRow(this);
			floorplannames.setLayoutParams(lpfw);
			str5lp.weight = (1 / 4);

			str7[s] = new TableRow(this);
			str7[s].setLayoutParams(lpfw);
			str5lp.weight = (1 / 4);

			try {
				refreshfloorplans();

			} catch (Throwable e) {
				e.printStackTrace();
			}
			floorplanlayout.addView(floorplannames);
			floorplanlayout.addView(str7[s]);

			horizfp = new HorizontalScrollView(this);
			horizfp.setLayoutParams(lpfw);
			horizfp.addView(floorplanlayout);
		}

		str1[s].setBackgroundColor(Color.parseColor(extremelylightblue));
		str2[s].setBackgroundColor(Color.parseColor(thelightestblue));
		str3[s].setBackgroundColor(Color.parseColor(extremelylightblue));
		str4[s].setBackgroundColor(Color.parseColor(thelightestblue));
		horizsite[s].setBackgroundColor(Color.parseColor(extremelylightblue));
		str5[s].setBackgroundColor(Color.parseColor(extremelylightblue));

		if (s == 1) {
			str6[s].setBackgroundColor(Color.parseColor(thelightestblue));
			horizfp.setBackgroundColor(Color.parseColor(extremelylightblue));
			str7[s].setBackgroundColor(Color.parseColor(extremelylightblue));
		}

		buildingtable[s].addView(str1[s], tlpfw);
		buildingtable[s].addView(str2[s], tlpfw);
		buildingtable[s].addView(str3[s], tlpfw);
		buildingtable[s].addView(str4[s], tlpfw);
		buildingtable[s].addView(horizsite[s], tlpfw);

		if (s == 1) {
			buildingtable[s].addView(str6[s], tlpfw);
			buildingtable[s].addView(horizfp, tlpfw);
		}

		locationcontainer.addView(buildingtable[s]);
		applyFonts(buildingtable[s], null, fontsize, null, null, null);
		s++;
	}

	public void makenewcomponent() {
	
	LayoutParams componenttextviewparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 2.1f);
	LayoutParams componentAutoCompleteTextViewparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 7.9f);

	LayoutParams rcomponenttextviewparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 2.1f);
	LayoutParams rcomponentAutoCompleteTextViewparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 3.9f);
	LayoutParams componenctremovebuttonparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 1f);

	LayoutParams locationrowparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 7.9f);
	LayoutParams servicearearowparams = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 1.975f);

	
	componenttable[c] = new TableLayout(this);
	componenttable[c].setLayoutParams(lpff);
	componenttable[c].setBackgroundColor(Color.parseColor(darkblue));
	componenttable[c].setPadding(2, 2, 2, 2);

	ctr1[c] = new TableRow(this);
	ctr1[c].setLayoutParams(lpfw);
	componentname[c] = new SiteAuditItem(this);
	componentname[c].label.setLayoutParams(rcomponenttextviewparams);
	componentname[c].label.setText("Component Name:");
	componentname[c].et.setLayoutParams(rcomponentAutoCompleteTextViewparams);

	View.OnFocusChangeListener componentnamelistener = new View.OnFocusChangeListener() {

		public void onFocusChange(View v, boolean hasFocus) {

			int s = getsequencenumber(componentname, v)[0];
			String componentnamestring = ((TextView) v).getText()
					.toString();

			
			
			if (!hasFocus) {
				
				if(COMPONENTSTABLOADED){
					try{
						String Table=db.TABLE_COMPONENTINFO;
						String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
						StorageKey="componentname";
						String value=componentnamestring;
						int row=s;
						int column=db.getcolumnnumberbytitle(db.TABLE_COMPONENTINFO,StorageKey);
						db.addorupdatemulticolumn(Table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
					
				}
			}
			if (componenttype[s].sp.getSelectedItem().toString().equals("Panel")) {

				if (!panelslist.contains(componentnamestring)) {
					panelslist.add(componentnamestring);
					
					try {
						mcrpanelnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}

				if (componentslist.contains(componentnamestring)) {
					componentslist.remove(componentnamestring);
					try {
						mcrloadnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}

			} else {
				if (!componentslist.contains(componentnamestring)) {
					componentslist.add(componentnamestring);
					
					try {
						mcrloadnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				if (panelslist.contains(componentnamestring)) {
					panelslist.remove(componentnamestring);
					try {
						mcrpanelnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				displaydoubles();
				// Do Something
				componentnames.add(componentnamestring);
				try{
				componentnamesadapter.notifyDataSetChanged();
				}catch(Throwable e){};
			}

			if (hasinvalidcharacters((AutoCompleteTextView) v, false) == true
					&& hasFocus == false) {
				removeinvalidcharacterdialog(v);
			} else {
				//renamepicturesandfolders(hasFocus, s);
			}

		}
	};
	componentname[c].et.setOnFocusChangeListener(componentnamelistener);
	viewnametoexcelvalues.put("componentname" + u.s(c), u.s(REPORT) + " "
			+ u.s(COMPONENTS) + " " + u.s(u.cellx("A" + u.s(c + 2))) + " "
			+ u.s(u.celly("A" + u.s(c + 2))));
	
	link1cellto1view(REPORT, COMPONENTS, "A" + u.s(c + 2),
			componentname[c].et);

	ctr1[c].addView(componentname[c].label);
	ctr1[c].addView(componentname[c].et);


	ctr3[c] = new TableRow(this);
	ctr3[c].setLayoutParams(lpfw);
	componenttype[c] = new SiteAuditItem(this);
	componenttype[c].label.setLayoutParams(componenttextviewparams);
	componenttype[c].label.setText("Type:");

	componenttype[c].sp.setLayoutParams(componentAutoCompleteTextViewparams);
	componenttype[c].sp.setAdapter(componenttypespinneradapter);
	componenttype[c].sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			try{
			int pos = getsequencenumber(componenttype, parent)[0];
			
			
			try {
				if(COMPONENTSTABLOADED){
					try{
						String Table=db.TABLE_COMPONENTINFO;
						String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
						StorageKey="componenttype";
						String value=((TextView) view).getText().toString();
						int row=pos;
						int column=db.getcolumnnumberbytitle(db.TABLE_COMPONENTINFO,StorageKey);
						db.addorupdatemulticolumn(Table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
					
					
				}
			} catch (Throwable e) {
				
			}
			String componentnamestring = componentname[pos].et.getText()
					.toString();
			String selection = ((TextView) view).getText().toString();
			
			if (selection.equals("new")) {
				addnewvaluetolistdialog((Spinner) parent, COMPONENTTYPES,
						componenttypespinneradapter, MASTER_LIST);

			} else if (selection.equals("Panel")) {

				if (!panelslist.contains(componentnamestring)) {
					panelslist.add(componentnamestring);
					
					try {
						mcrpanelnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				if (componentslist.contains(componentnamestring)) {
					componentslist.remove(componentnamestring);
					try {
						mcrloadnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				

			} else if (!selection.equals("Panel")) {
				if (!componentslist.contains(componentnamestring)) {
					componentslist.add(componentnamestring);
					
					try {
						mcrloadnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				if (panelslist.contains(componentnamestring)) {
					panelslist.remove(componentnamestring);
					try {
						mcrpanelnamespinneradapter.notifyDataSetChanged();
					} catch (NullPointerException e) {

					}
				}
				displaydoubles();
				if(selection.equals("BMS/Conctroller")){
					bmsnames.add(componentnamestring);
					u.log("BMS added to bmsnames"+componentnamestring);
					try{
						bmsnamesadapter.notifyDataSetChanged();
					}catch(Throwable e){};
				}
				// Do Something
			}
			}catch(Throwable e){};
		}

		public void onNothingSelected(AdapterView<?> parent) {
			showToast("Spinner1: unselected");
		}
	});

	viewnametoexcelvalues.put("componenttype" + u.s(c), u.s(REPORT) + " "
			+ u.s(COMPONENTS) + " " + u.s(u.cellx("B" + u.s(c + 2))) + " "
			+ u.s(u.celly("B" + u.s(c + 2))));
	link1cellto1view(REPORT, COMPONENTS, "B" + u.s(c + 2), componenttype[c].sp);

	ctr3[c].addView(componenttype[c].label);
	ctr3[c].addView(componenttype[c].sp);



	ctr5[c] = new TableRow(this);
	ctr5[c].setLayoutParams(lpfw);
	componentspec[c] = new SiteAuditItem(this);
	componentspec[c].label.setLayoutParams(componenttextviewparams);
	componentspec[c].label.setText("Specification:");
	componentspec[c].et.setLayoutParams(componentAutoCompleteTextViewparams);
	

	componentspec[c].et
			.setOnFocusChangeListener(new View.OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub

					int s = getsequencenumber(componentspec, v)[0];
					
					
					if (!hasFocus) {
						if(COMPONENTSTABLOADED){
							try{
								String Table=db.TABLE_COMPONENTINFO;
								String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
								StorageKey="componentspec";
								String value=((AutoCompleteTextView) v).getText().toString();
								int row=s;
								int column=db.getcolumnnumberbytitle(db.TABLE_COMPONENTINFO,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}
							
							
						}
					}
				}
			});

	viewnametoexcelvalues.put("componentspec" + u.s(c), u.s(REPORT) + " "
			+ u.s(COMPONENTS) + " " + u.s(u.cellx("C" + u.s(c + 2))) + " "
			+ u.s(u.celly("C" + u.s(c + 2))));
	link1cellto1view(REPORT, COMPONENTS, "C" + u.s(c + 2), componentspec[c].et);

	ctr5[c].addView(componentspec[c].label);
	ctr5[c].addView(componentspec[c].et);

	ctr6[c] = new TableRow(this);
	ctr6[c].setLayoutParams(lpfw);
	componentlocation[c] = new SiteAuditItem(this);
	componentlocation[c].label.setLayoutParams(componenttextviewparams);
	componentlocation[c].label.setText("Location:");

	componentlocation[c].sp = new Spinner(this);
	componentlocation[c].sp.setLayoutParams(locationrowparams);

	componentlocation[c].sp.setAdapter(locationspinneradapter);
	componentlocation[c].sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			int pos = getsequencenumber(componentlocation, parent)[0];

			try {
				
				
			} catch (Throwable e) {

			}

			try {
				if(COMPONENTSTABLOADED){
					try{
						String Table=db.TABLE_COMPONENTINFO;
						String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
						StorageKey="componentlocation";
						String value=((TextView) view).getText().toString();
						int row=pos;
						int column=db.getcolumnnumberbytitle(db.TABLE_COMPONENTINFO,StorageKey);
						db.addorupdatemulticolumn(Table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
				
				}
			} catch (Throwable e) {
				
			}
			int locationslistlength = locationslist.size();
			
			
			

		

			displaydoubles();
		}

		public void onNothingSelected(AdapterView<?> parent) {
			showToast("Spinner1: unselected");
		}
	});

	viewnametoexcelvalues.put("componentlocation" + u.s(c), u.s(REPORT)
			+ " " + u.s(COMPONENTS) + " " + u.s(u.cellx("D" + u.s(c + 2)))
			+ " " + u.s(u.celly("D" + u.s(c + 2))));
	link1cellto1view(REPORT, COMPONENTS, "D" + u.s(c + 2), componentlocation[c].sp);

	ctr6[c].addView(componentlocation[c].label);
	ctr6[c].addView(componentlocation[c].sp);


	ctr8[c] = new TableRow(this);
	ctr8[c].setLayoutParams(lpfw);
	componentnotes[c] = new SiteAuditItem(this);
	componentnotes[c].label.setLayoutParams(componenttextviewparams);
	componentnotes[c].label.setText("Notes:");

	componentnotes[c].et.setLayoutParams(componentAutoCompleteTextViewparams);
	componentnotes[c].et.setSingleLine(false);
	componentnotes[c].et
			.setOnFocusChangeListener(new View.OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub

					int s = getsequencenumber(componentnotes, v)[0];
					
					
					if (!hasFocus) {
						if(COMPONENTSTABLOADED){
							try{
								String Table=db.TABLE_COMPONENTINFO;
								String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
								StorageKey="componentnotes";
								String value=((AutoCompleteTextView) v)
										.getText().toString();
								int row=s;
								int column=db.getcolumnnumberbytitle(db.TABLE_COMPONENTINFO,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}	
							
						}
					}
				}
			});

	viewnametoexcelvalues.put("componentnotes" + u.s(c), u.s(REPORT) + " "
			+ u.s(COMPONENTS) + " " + u.s(u.cellx("E" + u.s(c + 2))) + " "
			+ u.s(u.celly("E" + u.s(c + 2))));
	link1cellto1view(REPORT, COMPONENTS, "E" + u.s(c + 2), componentnotes[c].et);

	ctr8[c].addView(componentnotes[c].label);
	ctr8[c].addView(componentnotes[c].et);

	ctr9[c] = new TableRow(this);
	ctr9[c].setLayoutParams(lpfw);

	componentpicturebutton[c] = new ImageView(this);
	componentpicturebutton[c].setLayoutParams(lpww);
	componentpicturebutton[c].setImageResource(R.drawable.addpicture);

	ctr9[c].addView(componentpicturebutton[c]);

	componentpicturebutton[c].setOnClickListener(new ImageView.OnClickListener() {

		public void onClick(View arg0) {
			int s = getsequencenumber(componentpicturebutton, arg0)[0];
			if (componentname[s].et.getText().toString().equals("")) {
				fieldcantbeleftblankdialog(componentname[s].et);
			} else {

				SetTypeAndNumber(COMPONENTSTAB,
						getsequencenumber(componentpicturebutton, arg0)[0]);
				componentname[u.i(numberselected)].et.requestFocus();
				pictureorfiledialog(COMPONENTSTAB, u.i(numberselected));
			}
		}
	});

	componentbarcodebutton[c] = new ImageView(this);
	componentbarcodebutton[c].setLayoutParams(lpww);
	componentbarcodebutton[c].setImageResource(R.drawable.barcode);

	ctr9[c].addView(componentbarcodebutton[c]);

	componentbarcodebutton[c].setOnClickListener(new ImageView.OnClickListener() {

		public void onClick(View arg0) {
			int s = getsequencenumber(componentbarcodebutton, arg0)[0];
			if (componentname[s].et.getText().toString().equals("")) {
				fieldcantbeleftblankdialog(componentname[s].et);
			} else {

				try {
					SetTypeAndNumber(COMPONENTSTAB, s);

					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.setPackage("com.google.zxing.client.android");
					intent.putExtra(
							"com.google.zxing.client.android.SCAN.SCAN_MODE",
							"QR_CODE_MODE");
					startActivityForResult(intent, BARCODE_SCAN);
				} catch (Throwable e) {
					showneedmoresoftwaredialog("Barcode Scanner",
							"com.google.zxing.client.android");
				}

			}

		}
	});
	ctr10[c] = new TableRow(this);
	ctr10[c].setLayoutParams(lpfw);

	TableRow.LayoutParams ctr10lp = new TableRow.LayoutParams();
	ctr10lp.weight = (1 / 4);

	componentpicnum[c] = countpics(COMPONENTSTAB, c);
	u.log("counted component pics"+componentpicnum[c]+ "for item "+c);
	int[] num = new int[maximagesperfolder];
	for (num[c] = 0; num[c] <= componentpicnum[c]; num[c]++) {
		componentpicture[c][num[c]] = new ImageView(this);
		componentpicture[c][num[c]].setLayoutParams(ctr10lp);
		componentpicture[c][num[c]].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					int[] z = getsequencenumber(componentpicture, v);
					try {

						String picturelocation = buildimagenamestringgivenname(
								componentname[z[0]].et.getText().toString(),
								COMPONENTSTAB, z[0], z[1], null);
						lhiphotoeditororotherdialog(picturelocation,
								COMPONENTSTAB, z[0], z[1]);

					} catch (ActivityNotFoundException e) {
						showneedmoresoftwaredialog(
								"ASctrO File Manager/Browser",
								"com.metago.asctro");
					}

				} catch (IndexOutOfBoundsException e) {
				}
			}
		});
		componentpicture[c][num[c]]
				.setOnLongClickListener(new OnLongClickListener() {
					public boolean onLongClick(View v) {

						int[] z = null;
						try {
							
							z = getsequencenumber(componentpicture, v);
							TableRow parenctrow = (TableRow) v.getParent();
							numberselected = u.s(z[0]);
							typeselected = COMPONENTSTAB;
							subnumberselected = u.s(z[1]);
							deleteorduplicatepicturedialog(
									new File(buildimagenamestringgivenname(
											componentname[z[0]].et
													.getText().toString(),
											COMPONENTSTAB, z[0], z[1], null)),
									(ImageView) v, parenctrow);

						} catch (IndexOutOfBoundsException e) {

						}

						return false;
					}

				});

		ctr10[c].addView(componentpicture[c][num[c]]);
	}

	horizcomponent[c] = new HorizontalScrollView(this);
	horizcomponent[c].setLayoutParams(lpfw);

	horizcomponent[c].addView(ctr10[c]);

	ctr11[c] = new TableRow(this);
	ctr11[c].setLayoutParams(lpfw);

	ctr11[c].addView(new TextView(this));
	ctr11[c].addView(new TextView(this));

	// //Remove component Button

	moveupcomponentbutton[c] = new ImageView(this);
	moveupcomponentbutton[c].setLayoutParams(componenctremovebuttonparams);
	moveupcomponentbutton[c].setImageResource(R.drawable.uparrow48);

	movedowncomponentbutton[c] = new ImageView(this);
	movedowncomponentbutton[c].setLayoutParams(componenctremovebuttonparams);
	movedowncomponentbutton[c].setImageResource(R.drawable.downarrow48);

	duplicatecomponentbutton[c] = new ImageView(this);
	duplicatecomponentbutton[c].setLayoutParams(componenctremovebuttonparams);
	duplicatecomponentbutton[c].setImageResource(R.drawable.copy48);

	removecomponentbutton[c] = new ImageView(this);
	removecomponentbutton[c].setLayoutParams(componenctremovebuttonparams);
	removecomponentbutton[c].setImageResource(R.drawable.deleteitem48);

	ctr1[c].addView(moveupcomponentbutton[c]);
	ctr1[c].addView(movedowncomponentbutton[c]);
	ctr1[c].addView(duplicatecomponentbutton[c]);
	ctr1[c].addView(removecomponentbutton[c]);

	
	

	removecomponentbutton[c]
			.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View arg0) {
					int fosn = getsequencenumber(removecomponentbutton,
							arg0)[0];
					
					remove(fosn, COMPONENTSTAB, true);
					
					
				}
			});

	ctr1[c].setBackgroundColor(Color.parseColor(extremelylightblue));
	ctr3[c].setBackgroundColor(Color.parseColor(extremelylightblue));
	ctr5[c].setBackgroundColor(Color.parseColor(extremelylightblue));
	ctr6[c].setBackgroundColor(Color.parseColor(thelightestblue));
	ctr8[c].setBackgroundColor(Color.parseColor(thelightestblue));
	ctr9[c].setBackgroundColor(Color.parseColor(extremelylightblue));
	horizcomponent[c].setBackgroundColor(Color.parseColor(thelightestblue));
	ctr10[c].setBackgroundColor(Color.parseColor(thelightestblue));
	ctr11[c].setBackgroundColor(Color.parseColor(extremelylightblue));

	componenttable[c].addView(ctr1[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr3[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr5[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr6[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr8[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr9[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(horizcomponent[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	componenttable[c].addView(ctr11[c], new TableLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

	try {
		componentscontainer.addView(componenttable[c]);
	} catch (Throwable e) {
		runOnUiThread(new Runnable() {
			public void run() {
				componentscontainer.addView(componenttable[c]);
			}
		});
	}
	
	applyFonts(componenttable[c], null, fontsize, null, null, null);
	c++;
	componentcount++;

	// countedcomps++;
	//
	// ///*****************************************
}
	
	public void makenewasset() {
		
		LayoutParams assettextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 2.1f);
		LayoutParams assetAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.9f);

		LayoutParams rassettextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 2.1f);
		LayoutParams rassetAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.9f);
		LayoutParams assetremovebuttonparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);

		LayoutParams locationrowparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.9f);
		LayoutParams servicearearowparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1.975f);

	
		
		assettable[i] = new TableLayout(this);
		assettable[i].setLayoutParams(lpff);
		assettable[i].setBackgroundColor(Color.parseColor(darkblue));
		assettable[i].setPadding(2, 2, 2, 2);

		tr1[i] = new TableRow(this);
		tr1[i].setLayoutParams(lpfw);
		assetname[i] = new SiteAuditItem(this);
		assetname[i].label.setLayoutParams(rassettextviewparams);
		assetname[i].label.setText("Asset Name:");
		assetname[i].et.setLayoutParams(rassetAutoCompleteTextViewparams);

		View.OnFocusChangeListener assetnamelistener = new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				int s = getsequencenumber(assetname, v)[0];
				String assetnamestring = ((TextView) v).getText()
						.toString();
				u.log("focus change started, sequence s, "+s);
				
				
				if (!hasFocus) {
					
					if(ASSETSTABLOADED){
						try{
							String Table=db.TABLE_ASSETINFO;
							String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
							StorageKey="assetname";
							String value=assetnamestring;
							int row=s;
							int column=db.getcolumnnumberbytitle(Table,StorageKey);
							u.log("asset name i, "+i+" sequence s, "+s);
							db.addorupdatemulticolumn(Table, row, column , value , Key);
						}catch(Throwable e){
							e.printStackTrace();
						}
						
						
					}
				}
				if (type[s].sp.getSelectedItem().toString().equals("Panel")) {

					if (!panelslist.contains(assetnamestring)) {
						panelslist.add(assetnamestring);
						
						try {
							mcrpanelnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}

					if (assetslist.contains(assetnamestring)) {
						assetslist.remove(assetnamestring);
						try {
							mcrloadnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}

				} else {
					if (!assetslist.contains(assetnamestring)) {
						assetslist.add(assetnamestring);
						
						try {
							mcrloadnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					if (panelslist.contains(assetnamestring)) {
						panelslist.remove(assetnamestring);
						try {
							mcrpanelnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					displaydoubles();
					// Do Something
					assetnames[i]=assetnamestring;
					
					
					try{
					assetnamesadapter.notifyDataSetChanged();
					}catch(Throwable e){};
				}

				if (hasinvalidcharacters((AutoCompleteTextView) v, false) == true
						&& hasFocus == false) {
					removeinvalidcharacterdialog(v);
				} else {
					renamepicturesandfolders(hasFocus, s, ASSETS);
				}

			}
		};
		assetname[i].et.setOnFocusChangeListener(assetnamelistener);
		viewnametoexcelvalues.put("assetname" + u.s(i), u.s(REPORT) + " "
				+ u.s(ASSETS) + " " + u.s(u.cellx("A" + u.s(i + 2))) + " "
				+ u.s(u.celly("A" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "A" + u.s(i + 2),
				assetname[i].et);

		tr1[i].addView(assetname[i].label);
		tr1[i].addView(assetname[i].et);

		tr2[i] = new TableRow(this);
		tr2[i].setLayoutParams(lpfw);
		quantity[i] = new SiteAuditItem(this);
		quantity[i].label.setLayoutParams(assettextviewparams);
		quantity[i].label.setText("Quantity:");
		quantity[i].et.setLayoutParams(assetAutoCompleteTextViewparams);
		quantity[i].et.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		quantity[i].et
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub

						int s = getsequencenumber(quantity, v)[0];
						
						
						if (!hasFocus) {
							if(ASSETSTABLOADED){
								try{
									String Table=db.TABLE_ASSETINFO;
									String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
									StorageKey="assetquantity";
									String value=((AutoCompleteTextView) v).getText().toString();
									int row=s;
									int column=db.getcolumnnumberbytitle(Table,StorageKey);
									db.addorupdatemulticolumn(Table, row, column , value , Key);
								}catch(Throwable e){
									e.printStackTrace();
								}		
								
								
							}
							
						}
					}
				});
		String defaultassetquantity="1";
		quantity[i].et.setText(defaultassetquantity);
		viewnametoexcelvalues.put("assetquantity" + u.s(i), u.s(REPORT)
				+ " " + u.s(ASSETS) + " " + u.s(u.cellx("B" + u.s(i + 2)))
				+ " " + u.s(u.celly("B" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "B" + u.s(i + 2), quantity[i].et);

		tr2[i].addView(quantity[i].label);
		tr2[i].addView(quantity[i].et);

		tr3[i] = new TableRow(this);
		tr3[i].setLayoutParams(lpfw);
		type[i] = new SiteAuditItem(this);
		type[i].label.setLayoutParams(assettextviewparams);
		type[i].label.setText("Type:");

		type[i].sp.setLayoutParams(assetAutoCompleteTextViewparams);
		type[i].sp.setAdapter(assettypespinneradapter);
		type[i].sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try{
				int pos = getsequencenumber(type, parent)[0];
				
				
				try {
					if(ASSETSTABLOADED){
						try{
							String Table=db.TABLE_ASSETINFO;
							String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
							StorageKey="assettype";
							String value=((TextView) view).getText().toString();
							int row=pos;
							int column=db.getcolumnnumberbytitle(Table,StorageKey);
							db.addorupdatemulticolumn(Table, row, column , value , Key);
						}catch(Throwable e){
							e.printStackTrace();
						}
						
						
					}
				} catch (Throwable e) {
					
				}
				String assetnamestring = assetname[pos].et.getText()
						.toString();
				String selection = ((TextView) view).getText().toString();
				
				if (selection.equals("new")) {
					addnewvaluetolistdialog((Spinner) parent, ASSETTYPES,
							assettypespinneradapter, MASTER_LIST);

				} else if (selection.equals("Panel")) {

					if (!panelslist.contains(assetnamestring)) {
						panelslist.add(assetnamestring);
						
						try {
							mcrpanelnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					if (assetslist.contains(assetnamestring)) {
						assetslist.remove(assetnamestring);
						try {
							mcrloadnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					

				} else if (!selection.equals("Panel")) {
					if (!assetslist.contains(assetnamestring)) {
						assetslist.add(assetnamestring);
						
						try {
							mcrloadnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					if (panelslist.contains(assetnamestring)) {
						panelslist.remove(assetnamestring);
						try {
							mcrpanelnamespinneradapter.notifyDataSetChanged();
						} catch (NullPointerException e) {

						}
					}
					displaydoubles();
					if(selection.equals("BMS/Controller")){
						bmsnames.add(assetnamestring);
						u.log("BMS added to bmsnames"+assetnamestring);
						try{
							bmsnamesadapter.notifyDataSetChanged();
						}catch(Throwable e){};
					}
					// Do Something
				}
				}catch(Throwable e){};
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}
		});

		viewnametoexcelvalues.put("assettype" + u.s(i), u.s(REPORT) + " "
				+ u.s(ASSETS) + " " + u.s(u.cellx("C" + u.s(i + 2))) + " "
				+ u.s(u.celly("C" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "C" + u.s(i + 2), type[i].sp);

		tr3[i].addView(type[i].label);
		tr3[i].addView(type[i].sp);

		tr4[i] = new TableRow(this);
		tr4[i].setLayoutParams(lpfw);
		model[i] = new SiteAuditItem(this);
		model[i].label.setLayoutParams(assettextviewparams);
		model[i].label.setText("Manufacture/Model:");
		model[i].et.setLayoutParams(assetAutoCompleteTextViewparams);
		
		

		model[i].et.setAdapter(commonmanufacturerssadapter);
		model[i].et.setThreshold(0);

		
		model[i].et
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub

						int s = getsequencenumber(model, v)[0];
						
						
						if (!hasFocus) {
							if(ASSETSTABLOADED){
								try{
									String Table=db.TABLE_ASSETINFO;
									String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
									StorageKey="assetmodel";
									String value= ((AutoCompleteTextView) v)
											.getText().toString();
									int row=s;
									int column=db.getcolumnnumberbytitle(Table,StorageKey);
									u.log("i number, "+i+" sequence number"+s);
									db.addorupdatemulticolumn(Table, row, column , value , Key);
								}catch(Throwable e){
									e.printStackTrace();
								}	
								
								
							}
						}
					}
				});

		viewnametoexcelvalues.put("assetmodel" + u.s(i), u.s(REPORT) + " "
				+ u.s(ASSETS) + " " + u.s(u.cellx("D" + u.s(i + 2))) + " "
				+ u.s(u.celly("D" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "D" + u.s(i + 2), model[i].et);

		tr4[i].addView(model[i].label);
		tr4[i].addView(model[i].et);

		tr5[i] = new TableRow(this);
		tr5[i].setLayoutParams(lpfw);
		spec[i] = new SiteAuditItem(this);
		spec[i].label.setLayoutParams(assettextviewparams);
		spec[i].label.setText("Power(kW):");
		spec[i].et.setLayoutParams(assetAutoCompleteTextViewparams);
		spec[i].et.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		spec[i].et
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub

						int s = getsequencenumber(spec, v)[0];
						
						
						if (!hasFocus) {
							if(ASSETSTABLOADED){
								try{
									String Table=db.TABLE_ASSETINFO;
									String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
									StorageKey="assetpower";
									String value=((AutoCompleteTextView) v)
											.getText().toString();
									int row=s;
									int column=db.getcolumnnumberbytitle(Table,StorageKey);
									db.addorupdatemulticolumn(Table, row, column , value , Key);
								}catch(Throwable e){
									e.printStackTrace();
								}		
								
							}
						}
					}
				});

		viewnametoexcelvalues.put("assetpower" + u.s(i), u.s(REPORT) + " "
				+ u.s(ASSETS) + " " + u.s(u.cellx("E" + u.s(i + 2))) + " "
				+ u.s(u.celly("E" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "E" + u.s(i + 2), spec[i].et);

		tr5[i].addView(spec[i].label);
		tr5[i].addView(spec[i].et);

		tr6[i] = new TableRow(this);
		tr6[i].setLayoutParams(lpfw);
		location[i] = new SiteAuditItem(this);
		location[i].label.setLayoutParams(assettextviewparams);
		location[i].label.setText("Location:");

		location[i].sp = new Spinner(this);
		location[i].sp.setLayoutParams(locationrowparams);

		location[i].sp.setAdapter(locationspinneradapter);
		location[i].sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				int pos = getsequencenumber(location, parent)[0];

				try {
					
					
				} catch (Throwable e) {

				}

				try {
					if(ASSETSTABLOADED){
						try{
							String Table=db.TABLE_ASSETINFO;
							String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
							StorageKey="assetlocation";
							String value=((TextView) view).getText().toString();
							int row=pos;
							int column=db.getcolumnnumberbytitle(Table,StorageKey);
							db.addorupdatemulticolumn(Table, row, column , value , Key);
						}catch(Throwable e){
							e.printStackTrace();
						}		
						
					}
				} catch (Throwable e) {
					
				}
				int locationslistlength = locationslist.size();
				
				
				

				try {
					servicearea[pos].doub1 = sitesize[position - 1].doub1;
					servicearea[pos].doub2 = sitesize[position - 1].doub2;
				} catch (Throwable e) {

				}

				displaydoubles();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}
		});

		viewnametoexcelvalues.put("assetlocation" + u.s(i), u.s(REPORT)
				+ " " + u.s(ASSETS) + " " + u.s(u.cellx("F" + u.s(i + 2)))
				+ " " + u.s(u.celly("F" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "F" + u.s(i + 2), location[i].sp);

		tr6[i].addView(location[i].label);
		tr6[i].addView(location[i].sp);

		// Row 7

		tr7[i] = new TableRow(this);
		tr7[i].setLayoutParams(lpfw);

		// service area
		servicearea[i] = new SiteAuditItem(this);
		servicearea[i].label.setLayoutParams(assettextviewparams);
		servicearea[i].label.setText("Service Area:");

		servicearea[i].et1.setLayoutParams(servicearearowparams);
		servicearea[i].et1.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		servicearea[i].tv1.setLayoutParams(servicearearowparams);
		servicearea[i].tv1.setText("m²");

		servicearea[i].et2.setLayoutParams(servicearearowparams);
		servicearea[i].et2.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		servicearea[i].tv2.setLayoutParams(servicearearowparams);
		servicearea[i].tv2.setText("ft²");

		addconversiontextchangelistenerarea(i, servicearea[i].et1,
				servicearea[i].et2);
		viewnametoexcelvalues.put(
				"assetserviceareaft" + u.s(i),
				u.s(REPORT) + " " + u.s(ASSETS) + " "
						+ u.s(u.cellx("G" + u.s(i + 2))) + " "
						+ u.s(u.celly("G" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "G" + u.s(i + 2),
				servicearea[i].et1);

		viewnametoexcelvalues.put("assetserviceaream" + u.s(i), u.s(REPORT)
				+ " " + u.s(ASSETS) + " " + u.s(u.cellx("H" + u.s(i + 2)))
				+ " " + u.s(u.celly("H" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "H" + u.s(i + 2),
				servicearea[i].et2);

		tr7[i].addView(servicearea[i].label);
		tr7[i].addView(servicearea[i].et1);
		tr7[i].addView(servicearea[i].tv1);
		tr7[i].addView(servicearea[i].et2);
		tr7[i].addView(servicearea[i].tv2);

		tr8[i] = new TableRow(this);
		tr8[i].setLayoutParams(lpfw);
		assetnotes[i] = new SiteAuditItem(this);
		assetnotes[i].label.setLayoutParams(assettextviewparams);
		assetnotes[i].label.setText("Notes:");

		assetnotes[i].et.setLayoutParams(assetAutoCompleteTextViewparams);
		assetnotes[i].et.setSingleLine(false);
		assetnotes[i].et
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub

						int s = getsequencenumber(assetnotes, v)[0];
						
						
						if (!hasFocus) {
							if(ASSETSTABLOADED){
								try{
									String Table=db.TABLE_ASSETINFO;
									String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
									StorageKey="assetnotes";
									String value=((AutoCompleteTextView) v)
											.getText().toString();
									int row=s;
									int column=db.getcolumnnumberbytitle(Table,StorageKey);
									u.log("i number, "+i+" sequence number"+s);
									db.addorupdatemulticolumn(Table, row, column , value , Key);
								}catch(Throwable e){
									e.printStackTrace();
								}	
							
							}

						}
					}
				});

		viewnametoexcelvalues.put("assetnotes" + u.s(i), u.s(REPORT) + " "
				+ u.s(ASSETS) + " " + u.s(u.cellx("I" + u.s(i + 2))) + " "
				+ u.s(u.celly("I" + u.s(i + 2))));
		link1cellto1view(REPORT, ASSETS, "I" + u.s(i + 2), assetnotes[i].et);

		tr8[i].addView(assetnotes[i].label);
		tr8[i].addView(assetnotes[i].et);

		tr9[i] = new TableRow(this);
		tr9[i].setLayoutParams(lpfw);

		picturebutton[i] = new ImageView(this);
		picturebutton[i].setLayoutParams(lpww);
		picturebutton[i].setImageResource(R.drawable.addpicture);

		tr9[i].addView(picturebutton[i]);

		picturebutton[i].setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				int s = getsequencenumber(picturebutton, arg0)[0];
				if (assetname[s].et.getText().toString().equals("")) {
					fieldcantbeleftblankdialog(assetname[s].et);
				} else {

					SetTypeAndNumber(ASSETSTAB,
							getsequencenumber(picturebutton, arg0)[0]);
					assetname[u.i(numberselected)].et.requestFocus();
					pictureorfiledialog(ASSETSTAB, u.i(numberselected));
				}
			}
		});

		barcodebutton[i] = new ImageView(this);
		barcodebutton[i].setLayoutParams(lpww);
		barcodebutton[i].setImageResource(R.drawable.barcode);

		tr9[i].addView(barcodebutton[i]);

		barcodebutton[i].setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				int s = getsequencenumber(barcodebutton, arg0)[0];
				if (assetname[s].et.getText().toString().equals("")) {
					fieldcantbeleftblankdialog(assetname[s].et);
				} else {

					try {
						SetTypeAndNumber(ASSETSTAB, s);

						Intent intent = new Intent(
								"com.google.zxing.client.android.SCAN");
						intent.setPackage("com.google.zxing.client.android");
						intent.putExtra(
								"com.google.zxing.client.android.SCAN.SCAN_MODE",
								"QR_CODE_MODE");
						startActivityForResult(intent, BARCODE_SCAN);
					} catch (Throwable e) {
						showneedmoresoftwaredialog("Barcode Scanner",
								"com.google.zxing.client.android");
					}

				}

			}
		});
		tr10[i] = new TableRow(this);
		tr10[i].setLayoutParams(lpfw);

		TableRow.LayoutParams tr10lp = new TableRow.LayoutParams();
		tr10lp.weight = (1 / 4);

		assetpicnum[i] = countpics(ASSETSTAB, i);
		u.log("counted asset pics"+assetpicnum[i]+ "for item "+i);
		int[] num = new int[maximagesperfolder];
		for (num[i] = 0; num[i] <= assetpicnum[i]; num[i]++) {
			picture[i][num[i]] = new ImageView(this);
			picture[i][num[i]].setLayoutParams(tr10lp);
			picture[i][num[i]].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					try {
						int[] z = getsequencenumber(picture, v);
						try {

							String picturelocation = buildimagenamestringgivenname(
									assetname[z[0]].et.getText().toString(),
									ASSETSTAB, z[0], z[1], null);
							lhiphotoeditororotherdialog(picturelocation,
									ASSETSTAB, z[0], z[1]);

						} catch (ActivityNotFoundException e) {
							showneedmoresoftwaredialog(
									"ASTRO File Manager/Browser",
									"com.metago.astro");
						}

					} catch (IndexOutOfBoundsException e) {
					}
				}
			});
			picture[i][num[i]]
					.setOnLongClickListener(new OnLongClickListener() {
						public boolean onLongClick(View v) {

							int[] z = null;
							try {
								
								z = getsequencenumber(picture, v);
								TableRow parentrow = (TableRow) v.getParent();
								numberselected = u.s(z[0]);
								typeselected = ASSETSTAB;
								subnumberselected = u.s(z[1]);
								deleteorduplicatepicturedialog(
										new File(buildimagenamestringgivenname(
												assetname[z[0]].et
														.getText().toString(),
												ASSETSTAB, z[0], z[1], null)),
										(ImageView) v, parentrow);

							} catch (IndexOutOfBoundsException e) {

							}

							return false;
						}

					});

			tr10[i].addView(picture[i][num[i]]);
		}

		horizasset[i] = new HorizontalScrollView(this);
		horizasset[i].setLayoutParams(lpfw);

		horizasset[i].addView(tr10[i]);

		tr11[i] = new TableRow(this);
		tr11[i].setLayoutParams(lpfw);

		tr11[i].addView(new TextView(this));
		tr11[i].addView(new TextView(this));

		// //Remove Asset Button

		moveupassetbutton[i] = new ImageView(this);
		moveupassetbutton[i].setLayoutParams(assetremovebuttonparams);
		moveupassetbutton[i].setImageResource(R.drawable.uparrow48);

		movedownassetbutton[i] = new ImageView(this);
		movedownassetbutton[i].setLayoutParams(assetremovebuttonparams);
		movedownassetbutton[i].setImageResource(R.drawable.downarrow48);

		duplicateassetbutton[i] = new ImageView(this);
		duplicateassetbutton[i].setLayoutParams(assetremovebuttonparams);
		duplicateassetbutton[i].setImageResource(R.drawable.copy48);

		removeassetbutton[i] = new ImageView(this);
		removeassetbutton[i].setLayoutParams(assetremovebuttonparams);
		removeassetbutton[i].setImageResource(R.drawable.deleteitem48);

		tr1[i].addView(moveupassetbutton[i]);
		tr1[i].addView(movedownassetbutton[i]);
		tr1[i].addView(duplicateassetbutton[i]);
		tr1[i].addView(removeassetbutton[i]);

		
	
		
		removeassetbutton[i]
				.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View arg0) {
						int fosn = getsequencenumber(removeassetbutton,
								arg0)[0];
						
						remove(fosn, ASSETSTAB, true);
					}
				});

		tr1[i].setBackgroundColor(Color.parseColor(extremelylightblue));
		tr2[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr3[i].setBackgroundColor(Color.parseColor(extremelylightblue));
		tr4[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr5[i].setBackgroundColor(Color.parseColor(extremelylightblue));
		tr6[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr7[i].setBackgroundColor(Color.parseColor(extremelylightblue));
		tr8[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr9[i].setBackgroundColor(Color.parseColor(extremelylightblue));
		horizasset[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr10[i].setBackgroundColor(Color.parseColor(thelightestblue));
		tr11[i].setBackgroundColor(Color.parseColor(extremelylightblue));

		assettable[i].addView(tr1[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr2[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr3[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr4[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr5[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr6[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr7[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr8[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr9[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(horizasset[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		assettable[i].addView(tr11[i], new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		try {
			assetscontainer.addView(assettable[i]);
		} catch (Throwable e) {
			runOnUiThread(new Runnable() {
				public void run() {
					assetscontainer.addView(assettable[i]);
				}
			});
		}
		
		applyFonts(assettable[i], null, fontsize, null, null, null);
		i++;
		assetcount++;

		// countedcomps++;
		//
		// ///*****************************************
	}

	public void addrecommendation() {
		// //** Build site tables to be added.

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		LayoutParams recommendationnumbertextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, .25f);
		LayoutParams recommendationtypespinnerparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, .75f);
		LayoutParams recommendationareaofopportunityspinnerparams = new LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams recommendationobservationsAutoCompleteTextViewparams = new LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 3f);
		LayoutParams recommendationrecommendationsAutoCompleteTextViewparams = new LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 3f);
		LayoutParams recommendationeaskwhAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams eassplitparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams coisplitparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams recommendationestimatedroitextviewparams = new LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1f);

		rctr1[r] = new TableRow(this);
		rctr1[r].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		recommendationnumbertextview[r] = new TextView(this);
		recommendationnumbertextview[r]
				.setLayoutParams(recommendationnumbertextviewparams);
		recommendationnumbertextview[r].setText(u.s(r + 1));
		if (RECOMMENDATIONSTABLOADED) {
			db.addorupdate(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationsnumber"
					+ u.s(r), u.s(r + 1));
		}

		recommendationtypespinner[r] = new Spinner(this);
		recommendationtypespinner[r]
				.setLayoutParams(recommendationtypespinnerparams);

		recommendationtypespinner[r].setAdapter(recommendationtypeadapter);

		recommendationtypespinner[r]
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						int pos = getsequencenumber(recommendationtypespinner,
								parent)[0];

						String selection = ((TextView) view).getText()
								.toString();
						
						if (RECOMMENDATIONSTABLOADED) {
							db.addorupdate(DatabaseHandler.TABLE_RECOMMENDATIONS,
									"recommendationstype" + u.s(pos), selection);
						}
						if (selection.equals("new")) {
							addnewvaluetolistdialog((Spinner) parent,
									RECOMMENDATIONTYPES,
									recommendationtypeadapter, MASTER_LIST);

						} else {

							displaydoubles();

							//
							// null, null);
						}

					}

					public void onNothingSelected(AdapterView<?> parent) {
						showToast("Spinner1: unselected");
					}
				});

		recommendationareaofopportunityspinner[r] = new Spinner(this);
		recommendationareaofopportunityspinner[r]
				.setLayoutParams(recommendationareaofopportunityspinnerparams);

		recommendationareaofopportunityspinner[r]
				.setAdapter(recommendationareaofopportunityadapter);
		recommendationareaofopportunityspinner[r]
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						int pos = getsequencenumber(
								recommendationareaofopportunityspinner, parent)[0];
						String selection = ((TextView) view).getText()
								.toString();
						if (RECOMMENDATIONSTABLOADED) {
							db.addorupdate(
									DatabaseHandler.TABLE_RECOMMENDATIONS,
									"recommendationsareaofopportunity"
											+ u.s(pos), selection);

						}
						if (selection.equals("new")) {
							addnewvaluetolistdialog((Spinner) parent,
									RECOMMENDATIONSAREASOFOPPORTUNITY,
									recommendationareaofopportunityadapter,
									MASTER_LIST);

						} else {
							displaydoubles();

							//
							// null, null);
						}
					}

					public void onNothingSelected(AdapterView<?> parent) {
						showToast("Spinner1: unselected");
					}
				});

		recommendationobservationsspinner[r] = new Spinner(this);
		recommendationobservationsspinner[r]
				.setLayoutParams(recommendationobservationsAutoCompleteTextViewparams);

		recommendationobservationsspinner[r]
				.setAdapter(recommendationobservationsadapter);
		recommendationobservationsspinner[r]
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						int pos = getsequencenumber(
								recommendationobservationsspinner, parent)[0];
						String selection = ((TextView) view).getText()
								.toString();
						if (RECOMMENDATIONSTABLOADED) {
							db.addorupdate(
									DatabaseHandler.TABLE_RECOMMENDATIONS,
									"recommenationsobservationssummary"
											+ u.s(pos), selection);

						}
						if (selection.equals("new")) {
							int forn = getsequencenumber(
									recommendationobservationsspinner, parent)[0];
							addnewvaluetolistdialogmulti(
									(Spinner) parent,
									RECOMMENDATIONOBSERVATIONS,
									recommendationobservationsadapter,
									issuespinner[forn],
									RECOMMENDATIONOBSERVATIONS,
									issuespinneradapter,
									recommendationrecommendationsAutoCompleteTextView[forn],
									GENERICRECOMMENDATIONSSUMMARY, gr,
									recommendationAutoCompleteTextView[forn],
									GENERICRECOMMENDATIONSDETAILED, grdetailed);

						} else {
							try {
								int forn = getsequencenumber(
										recommendationobservationsspinner,
										parent)[0];
								recommendationrecommendationsAutoCompleteTextView[forn]
										.setText(gr.get(position));
								issuespinner[forn].setSelection(position);
								recommendationAutoCompleteTextView[forn].setText(grdetailed
										.get(position));

							} catch (Throwable e) {

							}
							// Do Something
						}

					}

					public void onNothingSelected(AdapterView<?> parent) {
						showToast("Spinner1: unselected");
					}
				});

		recommendationrecommendationsAutoCompleteTextView[r] = new AutoCompleteTextView(this);
		recommendationrecommendationsAutoCompleteTextView[r]
				.setLayoutParams(recommendationrecommendationsAutoCompleteTextViewparams);
		recommendationrecommendationsAutoCompleteTextView[r]
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						int pos = getsequencenumber(
								recommendationrecommendationsAutoCompleteTextView, v)[0];
						if (RECOMMENDATIONSTABLOADED) {
							db.addorupdate(
									DatabaseHandler.TABLE_RECOMMENDATIONS,
									"recommenationsrecommendationssummary"
											+ u.s(pos), ((TextView) v)
											.getText().toString());

						}
					}
				});

		recommendationeaskwhAutoCompleteTextView[r] = new AutoCompleteTextView(this);
		recommendationeaskwhAutoCompleteTextView[r]
				.setLayoutParams(recommendationeaskwhAutoCompleteTextViewparams);
		recommendationeaskwhAutoCompleteTextView[r].setSelectAllOnFocus(true);
		recommendationeaskwhAutoCompleteTextView[r]
				.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		addtextchangelistener(recommendationeaskwhAutoCompleteTextView[r],
				recommendationeaskwhAutoCompleteTextView, recommendationeaskwhdoub);

		eassplitlinearlayout[r] = new LinearLayout(this);
		eassplitlinearlayout[r].setLayoutParams(eassplitparams);
		eassplitlinearlayout[r].setOrientation(LinearLayout.VERTICAL);

		recommendationeascurrencytextview1[r] = new TextView(this);
		recommendationeascurrencytextview1[r].setLayoutParams(lpww);

		recommendationeascurrencytextview2[r] = new TextView(this);
		recommendationeascurrencytextview2[r].setLayoutParams(lpww);

		addtvconversiontextchangelistener(recommendationeascurrencytextview1,
				recommendationeascurrencytextview2, r,
				recommendationeascurrencydoub1, recommendationeascurrencydoub2,
				currencyratiodoub);

		eassplitlinearlayout[r].addView(recommendationeascurrencytextview1[r]);
		eassplitlinearlayout[r].addView(recommendationeascurrencytextview2[r]);

		coisplitlinearlayout[r] = new LinearLayout(this);
		coisplitlinearlayout[r].setLayoutParams(coisplitparams);
		coisplitlinearlayout[r].setOrientation(LinearLayout.VERTICAL);

		recommendationestimatedcoiAutoCompleteTextView1[r] = new AutoCompleteTextView(this);
		recommendationestimatedcoiAutoCompleteTextView1[r].setSelectAllOnFocus(true);
		recommendationestimatedcoiAutoCompleteTextView1[r]
				.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		recommendationestimatedcoiAutoCompleteTextView2[r] = new AutoCompleteTextView(this);
		recommendationestimatedcoiAutoCompleteTextView2[r].setSelectAllOnFocus(true);
		recommendationestimatedcoiAutoCompleteTextView2[r]
				.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		addconversiontextchangelistener(r, "currency");

		coisplitlinearlayout[r].addView(recommendationestimatedcoiAutoCompleteTextView1[r]);
		coisplitlinearlayout[r].addView(recommendationestimatedcoiAutoCompleteTextView2[r]);

		recommendationestimatedroitextview[r] = new TextView(this);
		recommendationestimatedroitextview[r]
				.setLayoutParams(recommendationestimatedroitextviewparams);

		link1cellto1view(REPORT, RECOMMENDATIONS, "C" + u.s(r + 2),
				recommendationnumbertextview[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "D" + u.s(r + 2),
				recommendationtypespinner[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "E" + u.s(r + 2),
				recommendationareaofopportunityspinner[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "F" + u.s(r + 2),
				recommendationobservationsspinner[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "G" + u.s(r + 2),
				recommendationrecommendationsAutoCompleteTextView[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "H" + u.s(r + 2),
				recommendationeaskwhAutoCompleteTextView[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "I" + u.s(r + 2),
				recommendationeascurrencytextview1[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "J" + u.s(r + 2),
				recommendationeascurrencytextview2[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "K" + u.s(r + 2),
				recommendationestimatedcoiAutoCompleteTextView1[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "L" + u.s(r + 2),
				recommendationestimatedcoiAutoCompleteTextView2[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "M" + u.s(r + 2),
				recommendationestimatedroitextview[r]);

		rctr1[r].addView(recommendationnumbertextview[r]);
		rctr1[r].addView(recommendationtypespinner[r]);
		rctr1[r].addView(recommendationareaofopportunityspinner[r]);
		rctr1[r].addView(recommendationobservationsspinner[r]);
		rctr1[r].addView(recommendationrecommendationsAutoCompleteTextView[r]);
		rctr1[r].addView(recommendationeaskwhAutoCompleteTextView[r]);
		rctr1[r].addView(eassplitlinearlayout[r]);
		rctr1[r].addView(coisplitlinearlayout[r]);
		rctr1[r].addView(recommendationestimatedroitextview[r]);

		if ((r % 2) == 0) {
			// number is even
			rctr1[r].setBackgroundColor(Color.parseColor(extremelylightblue));
		} else {
			rctr1[r].setBackgroundColor(Color.parseColor(thelightestblue));
			// number is odd
		}

		rctr1[r].setPadding(5, 5, 5, 5);

		recommendationscalcstable.addView(rctr1[r]);

		LayoutParams recommendationnumbertextviewdetailedparams = new LayoutParams(
				0, LayoutParams.WRAP_CONTENT, .25f);
		LayoutParams rectextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams recAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 8.75f);
		LayoutParams recspinnerparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 5.75f);
		LayoutParams recremovebuttonparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);

		recommendationtable[r] = new TableLayout(this);
		recommendationtable[r].setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		recommendationtable[r].setBackgroundColor(Color.parseColor(darkblue));
		recommendationtable[r].setPadding(2, 2, 2, 2);

		// Row 1
		TableRow rtr1[] = new TableRow[maxrecommendations];

		rtr1[r] = new TableRow(this);
		rtr1[r].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		recommendationnumbertextviewdetailed[r] = new TextView(this);
		recommendationnumbertextviewdetailed[r]
				.setLayoutParams(recommendationnumbertextviewdetailedparams);
		recommendationnumbertextviewdetailed[r].setText(u.s(r + 1));

		issuenametextview[r] = new TextView(this);
		issuenametextview[r].setLayoutParams(rectextviewparams);

		issuenametextview[r].setText("Observation:");

		issuespinner[r] = new Spinner(this);
		issuespinner[r].setLayoutParams(recspinnerparams);

		recommendationAutoCompleteTextView[r] = new AutoCompleteTextView(this);
		recommendationAutoCompleteTextView[r].setLayoutParams(recAutoCompleteTextViewparams);

		issuespinner[r].setAdapter(issuespinneradapter);
		issuespinner[r].setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selection = ((TextView) view).getText().toString();
				if (selection.equals("new")) {
					addnewvaluetolistdialog((Spinner) parent,
							RECOMMENDATIONOBSERVATIONS, issuespinneradapter,
							MASTER_LIST);

				} else {
					try {
						int forn = getsequencenumber(issuespinner, parent)[0];
						recommendationAutoCompleteTextView[forn].setText(grdetailed
								.get(position));
					} catch (Throwable e) {

					}
					// Do Something
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}
		});

		moveuprecommendationbutton[r] = new ImageView(this);
		moveuprecommendationbutton[r].setLayoutParams(recremovebuttonparams);
		moveuprecommendationbutton[r].setImageResource(R.drawable.uparrow48);

		movedownrecommendationbutton[r] = new ImageView(this);
		movedownrecommendationbutton[r].setLayoutParams(recremovebuttonparams);
		movedownrecommendationbutton[r]
				.setImageResource(R.drawable.downarrow48);

		removerecommendationbutton[r] = new ImageView(this);
		removerecommendationbutton[r].setLayoutParams(recremovebuttonparams);
		removerecommendationbutton[r].setImageResource(R.drawable.deleteitem48);

		moveuprecommendationbutton[r]
				.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View arg0) {
						int fosn = getsequencenumber(
								moveuprecommendationbutton, arg0)[0];
					//	new MoveTask(progressDialog, fosn - 1,
					//			recommendationtable).execute();
					}
				});
		movedownrecommendationbutton[r]
				.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View arg0) {
						int fosn = getsequencenumber(
								movedownrecommendationbutton, arg0)[0];
					//	new MoveTask(progressDialog, fosn, recommendationtable)
					//			.execute();
					}
				});
		removerecommendationbutton[r]
				.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View arg0) {
						int fosn = getsequencenumber(
								removerecommendationbutton, arg0)[0];
						removerecommendation(fosn);
					}
				});

		link1cellto1view(REPORT, RECOMMENDATIONS, "A" + u.s(r + 2), issuespinner[r]);
		link1cellto1view(REPORT, RECOMMENDATIONS, "B" + u.s(r + 2), recommendationAutoCompleteTextView[r]);

		rtr1[r].addView(recommendationnumbertextviewdetailed[r]);
		rtr1[r].addView(issuenametextview[r]);

		rtr1[r].addView(issuespinner[r]);
		rtr1[r].addView(removerecommendationbutton[r]);
		// Row 2
		TableRow rtr2[] = new TableRow[maxrecommendations];

		rtr2[r] = new TableRow(this);
		rtr2[r].setLayoutParams(lpfw);

		recommendationtextview[r] = new TextView(this);
		recommendationtextview[r].setLayoutParams(rectextviewparams);
		recommendationtextview[r].setText("Recommendation:");

		TextView spacefiller = new TextView(this);
		spacefiller.setLayoutParams(recommendationnumbertextviewdetailedparams);

		rtr2[r].addView(spacefiller);
		rtr2[r].addView(recommendationtextview[r]);
		rtr2[r].addView(recommendationAutoCompleteTextView[r]);

		TableRow rtr3[] = new TableRow[maxrecommendations];

		rtr3[r] = new TableRow(this);
		rtr3[r].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		rtr3[r].addView(new TextView(this));
		rtr3[r].addView(new TextView(this));

		rtr1[r].setBackgroundColor(Color.parseColor(extremelylightblue));
		rtr2[r].setBackgroundColor(Color.parseColor(thelightestblue));
		rtr3[r].setBackgroundColor(Color.parseColor(extremelylightblue));

		recommendationtable[r].addView(rtr1[r], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		recommendationtable[r].addView(rtr2[r], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		recommendationtable[r].addView(rtr3[r], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		recommendationscontainer.addView(recommendationtable[r]);

		r++;

	}

	public void makeviewsforreadingsitesfromreportdb() {
		addnameanddatelines();
		u.log("makeviewsforreadingsitesfromreportdb started");
		u.log(db.countrows(db.TABLE_SITEINFO));
		for (int dbi = 0; dbi < db.countrows(db.TABLE_SITEINFO); dbi++) {
			
			int type = 0;

			
			try {
				String tempsitetype=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitetype" , dbi);
				type = u.i(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitetype" , dbi));
			} catch (Throwable e) {
				
				e.printStackTrace();
			}
			if (type == COMPANY&&!companyadded) {
				runOnUiThread(new Runnable() {
					public void run() {
						
						addlocation(COMPANY);

					}
				});
			}
			if (type == SITE&&!siteadded) {
				runOnUiThread(new Runnable() {
					public void run() {
						addlocation(SITE);
						if(ISMORRISONS){
							try {
								addM395floorplan();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
			}
			if (type == BUILDING) {
				runOnUiThread(new Runnable() {
					public void run() {
						addlocation(BUILDING);
					}
				});
			}
			if (type == FLOOR) {
				runOnUiThread(new Runnable() {
					public void run() {
						addlocation(FLOOR);
					}
				});
			}
			if (type == ZONE) {
				runOnUiThread(new Runnable() {
					public void run() {
						addlocation(ZONE);
					}
				});
			}
			if (type == ROOM) {
				runOnUiThread(new Runnable() {
					public void run() {
						addlocation(ROOM);
					}
				});
			}

		}

	}

	
	public void makeviewsforreadingcomponentsfromreportdb(){
		// u.log("makenewcomponent 1 in readfromdb");
		// makenewcomponent();
		u.log(db.countrows(db.TABLE_COMPONENTINFO));
	for (int dbi = 0; dbi < db.countrows(db.TABLE_COMPONENTINFO); dbi++) {
		
		int checkcount = 0;
		u.log("trying to get componentname" + dbi);
		u.log("trying to get componenttype" + dbi);
		u.log("trying to get componentspec" + dbi);
		u.log("trying to get componentlocation" + dbi);
		u.log("trying to get componentnotes" + dbi);

		try {
			db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" , dbi);
			u.log("got!  componentname" + dbi);
			checkcount++;
		} catch (Throwable e) {
			

		}
		try {
			db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componenttype" , dbi);
			u.log("got!  componenttype" + dbi);
			checkcount++;
		} catch (Throwable e) {
			

		}
		
		
		try {
			db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentspec" , dbi);
			u.log("got!  componentspec" + dbi);
			checkcount++;
		} catch (Throwable e) {
			

		}
		try {
			db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentlocation" , dbi);
			u.log("got!  componentlocation" + dbi);
			checkcount++;
		} catch (Throwable e) {
			

		}
		
		try {
			db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes" , dbi);
			u.log("got!  componentnotes" + dbi);
			checkcount++;
		} catch (Throwable e) {
			

		}

		
		if (checkcount > 0) {
			System.out
					.println("makenewcomponent 2 in readfromdb when checkcount>0");
			makenewcomponent();
		} else {
			break;
		}
	}

}
	public void makeviewsforreadingassetsfromreportdb() {
		// u.log("makenewasset 1 in readfromdb");
		// makenewasset();
		u.log(db.countrows(db.TABLE_ASSETINFO));
		for (int dbi = 0; dbi < db.countrows(db.TABLE_ASSETINFO); dbi++) {
			
			int checkcount = 0;
			u.log("trying to get assetname" + dbi);
			u.log("trying to get assetquantity" + dbi);
			u.log("trying to get assettype" + dbi);
			u.log("trying to get assetmodel" + dbi);
			u.log("trying to get assetpower" + dbi);
			u.log("trying to get assetlocation" + dbi);
			u.log("trying to get assetserviceaream" + dbi);
			u.log("trying to get assetserviceareaft" + dbi);
			u.log("trying to get assetnotes" + dbi);

			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" , dbi);
				u.log("got!  assetname" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetquantity" , dbi);
				u.log("got!  assetquantity" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assettype" , dbi);
				u.log("got!  assettype" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetmodel" , dbi);
				u.log("got!  assetmodel" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetpower" , dbi);
				u.log("got!  assetpower" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetlocation" , dbi);
				u.log("got!  assetlocation" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceaream"
						, dbi);
				u.log("got!  assetserviceaream" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceareaft"
						, dbi);
				u.log("got!  assetserviceareaft" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes" , dbi);
				u.log("got!  assetnotes" + dbi);
				checkcount++;
			} catch (Throwable e) {
				

			}

			
			if (checkcount > 0) {
				System.out
						.println("makenewasset 2 in readfromdb when checkcount>0");
				makenewasset();
			} else {
				break;
			}
		}

	}

	public void makeviewsforreadingrecommendationsfromreportdb() {
		runOnUiThread(new Runnable() {
			public void run() {
				addrecommendation();
			}
		});
		for (int i = 1; i < maxrecommendations; i++) {
			
			int checkcount = 0;

			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
						"recommendationsobservations" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
						"recommendationsrecommendation" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationsnumber"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationstype" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
						"recommendationsareaofopportunity" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
						"recommenationsobservationssummary" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
						"recommenationsrecommendationssummary" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationseaskwh"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationseaslc" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationseassc" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationscoilc" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationscoisc" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationsroi" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}

			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationsteaskwh"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationsteaslc"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommenationsteassc"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationstcoilc"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationstcoisc"
						, i);
				checkcount++;
			} catch (Throwable e) {
				

			}
			try {
				db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS, "recommendationstsp" , i);
				checkcount++;
			} catch (Throwable e) {
				

			}

			if (checkcount > 0) {
				addrecommendation();
			} else {
				break;
			}
		}

	}


	public void makeviewsforreadingrecommendationsfromreport() {

		File file = new File(ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Report.txt");

		// Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
				String[] tokens = new String[2];
				if (!(line.equals(""))) {
					tokens = line.split("[\\:\\.]+");
				} else {
					tokens = new String[2];
					tokens[0] = "";
					tokens[1] = "";
				}

				if (tokens[0].trim().equals("Observation")) {
					addrecommendation();

				}

			}
			br.close();
		} catch (IOException e) {
			// You'll need to add proper error handling here
		}

	}

	public void getPreferences() {
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		foldername = settings.getString("foldername", null);
		
		
		
		tabimageresolutions = settings.getString("tabimageresolutions", null);
		try{
			
		PICTURESAMPLESIZE = u.i(tabimageresolutions);
		} catch( Throwable e)
		{
			e.printStackTrace();
			PICTURESAMPLESIZE=16;
		}
		seriesname = settings.getString("seriesname", null);
		try{
			typeselected = u.i(settings.getString("typeselected", null));
			numberselected = settings.getString("numberselected", null);
			picturestarttime=u.l(settings.getString("picturestarttime", "-1"));
			picturestoptime=u.l(settings.getString("picturestoptime", "-1"));
		}catch(Throwable e){
			
		}
		tab = settings.getString("tab", null);
		if(Boolean.parseBoolean((String)u.get(MainActivity.PREFS_NAME,"storetype", ctx))){
			ISGROCERYSTRORE=true;
			ISDATACENTER=false;
		}else{
			ISGROCERYSTRORE=false;
			ISDATACENTER=true;
		}
		
		for(int i=0; i<storagekeys.length; i++){
		       
	        String string="";
	        switch(i){
	        case 0:
	        	string=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/";
	        	break;
	        case 1:
	        	masterfoldername=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
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
	        	mainsugarsyncdirectory=(String)u.get(MainActivity.PREFS_NAME,storagekeys[0], ctx)+"/"+
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
	        }
		}
		
		dcimlocationstring=(String)u.get(MainActivity.PREFS_NAME,storagekeys[8], ctx);
		
		ProjectDirectory = masterfoldername + foldername;
		Siteslistlocation = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/" + "Site_Lists.xls";

		inputfloorplandirectory = ProjectDirectory + "/"
				+ basedirectory[DOCUMENTSFOLDER] + "/" + "Floorplans";
		u.log(ProjectDirectory);
		u.log(basedirectory[DOCUMENTSFOLDER]);
		u.log("inputfloorplandirectory in get prefs="+inputfloorplandirectory);
		outputfloorplandirectory = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/" + "Floorplans";
		
		PREFS_ENGINEERNAMESPINNERPOSITION = settings.getString("engineernameposition", "0");
		MCRlocation=ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/"+foldername+".xls";
		internaldbpath = "//data//" + this.getPackageName()
				+ "//databases//" + foldername;
		
		int maxdbs=5;
		String[] databasename= new String[maxdbs];
		for(String string:databasename){
			string=null;
		}
		try {
			databasename = new File(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]).list(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".db");
			}
		  });
		}catch(Throwable e){
			
		}
		String finaldatabasename=foldername;
		String olddatabasename="database.db";
		try{
			if(!(databasename[0].equals(null)))
			{
				for(int d=0;d<databasename.length;d++){
 				   if(!(databasename[d].equals(olddatabasename))){
 					   u.log("finaldatabasename ,"+databasename[d]);
 					   finaldatabasename=databasename[d];
 					   break;
 				   }
 			   }
			}else{
				finaldatabasename=foldername;
			}
		}catch(Throwable e){
			finaldatabasename=foldername;
		}
		if(finaldatabasename.contains(".db")){
			finaldatabasename=finaldatabasename.replace(".db", "");
		}
		
		externaldbpath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/"+finaldatabasename+".db";
		
		latexfileslocation_pics = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Latex/Pictures";
		latexfileslocation_chaps = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Latex/Chapters";
	}

	void dispatchTakePictureIntent(int actionCode) throws IOException {

		if (typeselected==FLOORPLANTAB) {
			

			new File(inputfloorplandirectory).mkdirs();
			getstringdialog("Please Name This Floor Plan", actionCode);
			

		} else {
			picturestarttime=System.currentTimeMillis();
			set("picturestarttime",u.sl(picturestarttime));
			u.log("picturestarttime"+picturestarttime);
			System.out.println(picturestarttime);
			Intent takePictureIntent = new Intent(
					MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
			u.log("starting activity camera");
			u.log("actionCode"+actionCode);
			startActivityForResult(takePictureIntent, actionCode);
		}

	}

	private void copypictureintoproject(int type, String PathString)
			throws IOException {
		getPreferences();
		String extension = PathString.substring(
				PathString.lastIndexOf(".") + 1, PathString.length());

		File Path = new File(PathString);
		new File(ProjectDirectory);
		File file = null;

		if (type==ASSETSTAB) {

			file = new File(createnewimagename(Path,ASSETSTAB,
					u.i(numberselected), 0, extension));
			int v1 = 0;
			while (file.exists()) {
				v1++;
				file = new File(createnewimagename(Path,ASSETSTAB,
						u.i(numberselected), v1, extension));

			}
		}
		if (type==SITETAB) {

			file = new File(createnewimagename(Path,type, u.i(numberselected),
					0, null));
			int v1 = 0;
			while (file.exists()) {
				v1++;
				file = new File(createnewimagename(Path,SITETAB,
						u.i(numberselected), v1, null));

			}
		}
		if (type==CONSUMPTIONTAB) {

			file = new File(createnewimagename(Path,CONSUMPTIONTAB,
					u.i(numberselected), 0, extension));
			int v1 = 0;
			while (file.exists()) {
				v1++;
				file = new File(createnewimagename(Path,CONSUMPTIONTAB,
						u.i(numberselected), v1, extension));

			}
		}
		if (type==FLOORPLANTAB) {
			String filename = string;
			file = new File((inputfloorplandirectory + "/" + filename+"."+FilenameUtils.getExtension(new File(PathString).getName())));
			
			try {
				// string = PathString.substring(PathString.lastIndexOf("/") +
				// 1,
				// PathString.lastIndexOf("."));
			} catch (StringIndexOutOfBoundsException e) {

			}
		}

		if (!(type==CONSUMPTIONTAB)) {
			file = new File(file.getAbsolutePath());
		}
		
		

		// make directories in path if they don't exist
		File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}

		u.copyFile(Path, file);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		try{
			intializeviewsfromxml();
		}catch(Throwable e){
			
		}
		switch (requestCode) {
		case BARCODE_SCAN:
			if (resultCode == RESULT_OK && data != null
					&& data.getExtras() != null) {

				String[] Extras = { "SCAN_RESULT", "SCAN_RESULT_FORMAT" };

				String[] Results = new String[Extras.length];
				for (int i = 0; i < Extras.length; i++) {
					Results[i] = data.getStringExtra(Extras[i]);
					try {
						
					} catch (NullPointerException e) {
						
					}
				}

				// 
				// 
				try {
					FileOutputStream out = new FileOutputStream(
							DevelopeFileName(null,null));
					generateBarCode(Results[0]).compress(
							Bitmap.CompressFormat.PNG, 90, out);
					add1pic();
				} catch (Exception e) {
					
				}

			} else if (resultCode == RESULT_CANCELED) {

			}
			break;
		
		
		}
		if(requestCode==FLOORPLANACTIVITY){
			
			
			//((ViewGroup)mcrtable[METERINGLIST]).removeAllViews();
			db.showfulldblog(db.TABLE_MCR_METERINGLIST);
			db.showfulldblog(db.TABLE_ASSETINFO);
			db.showfulldblog(db.TABLE_COMPONENTINFO);
			
			refreshmaintabsfromdb();
			refreshmcrtable(MCRDBVALUES);
			
			//createmcrtablefrom(MCRDBVALUES);
			refreshpictures();
			
		}
		if(requestCode==PREFS){
			//((ViewGroup)mcrtable[METERINGLIST]).removeAllViews();
			getPreferences();
			refreshfloorplans();
			refreshpictures();
		}
		if (requestCode == EDITPICTUREACTIVITY) {
			// new LoadAllPicturesTask(progressDialog).execute();

			int type = eptype;
			int row = eprow;
			int num = epnum;
			try{
				refresh1pic(type, row, num);
			}catch(Throwable e){
				u.log("could not refresh1pic("+type+","+row+","+num);
			}
			// addallpictures();
		}
		if (requestCode == SELECTFILETOOPEN) {
			if (resultCode == RESULT_OK) {

				String filenamestring = data.getStringExtra("RESULT_STRING");
				Intent intent = new Intent();
				File file = new File(filenamestring);

				intent.setDataAndType(Uri.fromFile(file),
						u.getMimeType(filenamestring));

				intent.setAction(android.content.Intent.ACTION_VIEW);
				startActivity(intent);

			}
		}
		if (requestCode == OFFICEACTIVITY) {
		}

		if (requestCode == GETPICFROMCAMERA) {
			// Make sure the request was successful
			u.log("activityresult GETPICFROMCAMERA");
			if(db.checktableindb(DatabaseHandler.TABLE_ASSETINFO)){
				u.log("before pic from cam TABLE_ASSETINFO exists");
			}else{
				u.log("before pic from cam TABLE_ASSETINFO doesntexists");
			}
			if(picturestoptime==-1){
				picturestoptime=System.currentTimeMillis();
			}
			getPreferences();
			
			try{
				executegetpicfromcameraresult(typeselected, numberselected, picturestarttime, picturestoptime);
			}catch(Throwable e){
				
			}
			if(imagecapturenotprocessed()){
				processpendingimagecapture();
			}
			try{
				removepicturesforonerow(typeselected,u.i(numberselected));
				addpicturesforonerow(typeselected,u.i(numberselected));
			}catch(Throwable e){
				
			}
			
			if (resultCode == RESULT_OK) {
				// startActivity(u.intent("Tabs1"));
				// this.finish();
//				u.log("activityresult GETPICFROMCAMERA");
//				picturestoptime=System.currentTimeMillis();
//				executegetpicfromcameraresult(typeselected, numberselected, picturestarttime, picturestoptime);
				
			}
		
			if(db.checktableindb(DatabaseHandler.TABLE_ASSETINFO)){
				u.log("after pic from cam TABLE_ASSETINFO exists");
			}else{
				u.log("after pic from cam TABLE_ASSETINFO doesntexists");
			}
			
			
		}
		if (requestCode == CALCULATOR) {

			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				TextView View;
				View = (TextView) getCurrentFocus();
				View.setText(data.getDataString());

			}
		}
		if (requestCode == GETCOVERPAGEPATH) {
			if (resultCode == RESULT_OK) {

				coverpagefilepath = data.getData().getPath();
				
				
				coverpagethumbnail.setImageBitmap(BitmapFactory
						.decodeFile(coverpagefilepath));

			}
		}
		if (requestCode == GETCLIENTLOGOPATH) {
			if (resultCode == RESULT_OK) {

				clientlogofilepath = data.getData().getPath();
				
				
				clientlogothumbnail.setImageBitmap(BitmapFactory
						.decodeFile(clientlogofilepath));

			}
		}
		if (requestCode == GETIMPORTMCRLOCATION) {
			if (resultCode == RESULT_OK) {

				String theFilePath = data.getData().getPath();
				
				if (theFilePath.contains("external")) {
					theFilePath = getRealPathFromURI(data.getData());
				}
				
				// theFilePath.replace("/content:", "");
				//
				MCRlocation=theFilePath;
				refreshmcrtable(SOURCE);
				if(BOMTABLOADED){
					addvaluestobom();
				}
			}
		}
		
		if (requestCode == GETPICFROMFILE) {
			if (resultCode == RESULT_OK) {

				
				 if(data.getData() != null){
		                //If uploaded with Android Gallery (max 1 image)
		                Uri selectedImage = data.getData();
		                System.out.println(selectedImage);
		                //InputStream imageStream;
		                String theFilePath = data.getData().getPath();
		    			
						if (theFilePath.contains("external")) {
							theFilePath = getRealPathFromURI(data.getData());
						}
						
						// theFilePath.replace("/content:", "");
						//

						try {
							if(db.checktableindb(DatabaseHandler.TABLE_ASSETINFO)){
								u.log("before pic from cam TABLE_ASSETINFO exists");
							}else{
								u.log("before pic from cam TABLE_ASSETINFO doesntexists");
							}
							copypictureintoproject(typeforgetpicture, theFilePath);
							if(db.checktableindb(DatabaseHandler.TABLE_ASSETINFO)){
								u.log("after pic from camTABLE_ASSETINFO exists");
							}else{
								u.log("after pic from cam TABLE_ASSETINFO doesntexists");
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
						if (!(typeforgetpicture==CONSUMPTIONTAB)
								&& !(typeselected==FLOORPLANTAB)) {
							add1pic();
						} else {

							try {
								// string = getRealPathFromURI(data.getData());
								String[] strings = string.split("/");
								string = strings[strings.length - 1];
							} catch (Throwable e) {
								
							}

							try {
								refreshfloorplans();
							} catch (Throwable e) {

							}
						}
		            } else {
		                //If uploaded with the new Android Photos gallery
		               
		            	if(Build.VERSION.SDK_INT >= 19){
							
						
		            	ClipData clipData = data.getClipData();
		                for(int i = 0;
		                		i < clipData.getItemCount();
		                		i++){
		                    clipData.getItemAt(i);
		                    System.out.println(clipData.getItemAt(i).getUri());
		                    try {
								copypictureintoproject(typeforgetpicture, u.getRealPathFromURI(this,clipData.getItemAt(i).getUri()));
								if (!(typeforgetpicture==CONSUMPTIONTAB)
										&& !(typeselected==FLOORPLANTAB)) {
									refreshpictures();
								}
		                    } catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                }
		                }
		                
		            }
				
			}
		}
		if (requestCode == GETASSETIMPORTLOCATION) {
			try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}	
			if (resultCode == RESULT_OK) {
				String theFilePath = data.getData().getPath();
				String[] foldersinpath = theFilePath.split("/");	
				String assetimportlocation="";
				if(theFilePath.contains("Report.xls")){
				      assetimportlocation= theFilePath.substring(0,theFilePath.indexOf("/"+foldersinpath[foldersinpath.length-1]));
				}else{
					  assetimportlocation= theFilePath.substring(0,theFilePath.indexOf("/"+foldersinpath[foldersinpath.length-2]));
				}
				u.log("assetimportlocation, "+assetimportlocation);
				
				destroyassetstab();
				// delete asset pics if needed
			
				if(importandreplaceassets){
					importandreplaceassets=false;
					deletingassetpics();
					db.showfulldblog(db.TABLE_ASSETINFO);
					int dbrows=db.countrows(db.TABLE_ASSETINFO);
					u.log("dbrows, "+dbrows);
					for(int d=1;d <= dbrows;d++){
						u.log("deleting row, "+d);
						db.deleteSingleRow(db.TABLE_ASSETINFO, u.s(d));
					}
					db.showfulldblog(db.TABLE_ASSETINFO);
					
				}
				
				
				//copy external assets to the assets folder
				File importassetfile= new File(assetimportlocation);
				File projectassetfile= new File(ProjectDirectory + "/"
						+ basedirectory[ASSETSFOLDER]);
				try {
					u.copyDirectory_Cloud(importassetfile,projectassetfile);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
	// adding assets from excel sheet					
				try {
					final String[] asstesheetloc = new File(assetimportlocation).list(new FilenameFilter() {
					public boolean accept(File directory, String fileName) {
						return fileName.endsWith(".xls");
					}});
					File assetinfosheet =new File (assetimportlocation+"/"+asstesheetloc[0]);
					openassetsheet(assetinfosheet);
					assetinfotodbfromexcel();
					db.showfulldblog(db.TABLE_ASSETINFO);
					assetworkbook.close();
				}catch(Throwable e){
				e.printStackTrace();
			  }
				

	// LoadsAssets Tab from fresh
				LoadAssetsTab();
				
				
			}
		}

	}

	public static void set(String prefname, String value) {
    
		editor = settings.edit();
		editor.putString(prefname, value);
		editor.commit();

	}

	public static int[] getsequencenumber(Object obj, View arg0) {
		int[] t = new int[3];

		if (obj instanceof SiteAuditItem[]) {

			SiteAuditItem[] sai = (SiteAuditItem[]) obj;
			for (int y = 0; y < maxsiteaudititemspertab; y++) {
				try {
					if (arg0 instanceof AutoCompleteTextView) {
						if (sai[y].et == arg0) {
							t[0] = y;
							break;
						}
					} else if (arg0 instanceof Spinner) {
						if (sai[y].sp == arg0) {
							t[0] = y;
							break;
						}
					}
				} catch (NullPointerException e) {

				}
			}
		} else if (obj instanceof View[]) {
			
			View[] view = (View[]) obj;
			for (int y = 0; y < n; y++) {
				try {
					if (view[y] == arg0) {
						t[0] = y;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		} else if (obj instanceof View[][]) {

			View[][] view = (View[][]) obj;
			int x = 0;
			int y = 0;
			for (y = 0; y < n; y++) {
				for (x = 0; x < n; x++) {
					try{
						if (view[x][y] == arg0) {
							t[0] = x;
							t[1] = y;
							break;
						}
					}catch(Throwable e){
						
					}
				}
			}
		} else if (obj instanceof View[][][]) {

			View[][][] view = (View[][][]) obj;
			int x = 0;
			int y = 0;
			int z = 0;
			for (z = 0; z < maxexcelcolumns; z++) {
				for (y = 0; y < maxexcelrows; y++) {
					for (x = 0; x < maxsheets; x++) {
						if (view[x][y][z] == arg0) {
							t[0] = x;
							t[1] = y;
							t[2] = z;
							break;
						}
					}
				}
			}
		}
		return t;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		
		

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// do your stuff and Return true to prevent this event from being
			// propagated further
			
		      //IscrizioniActivity.this.finish();
			
			//attempt to save last field if focuschange hasn't occured
			try {
				sitename[1].et.requestFocus();
			} catch (Throwable e) {

			}
			try {
				this.getCurrentFocus().clearFocus();
			} catch (NullPointerException e) {

			}
			// locationslist.clear();

			// docslist.clear();
			// prettydocslist.clear();
			boolean andexit = true;
			foldersync();
			db.showfulldblog(db.TABLE_SITEINFO);
			db.showfulldblog(db.TABLE_COMPONENTINFO);
			db.showfulldblog(db.TABLE_ASSETINFO);
			db.showfulldblog(db.TABLE_MCR_METERINGLIST);
			db.showfulldblog(db.TABLE_MCR_TEMPSLIST);
			db.showfulldblog(db.TABLE_FLOORPLAN);
			savealldialog(andexit);
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			// do your stuff and Return true to prevent this event from being
			// propagated further

			ViewParent target = getCurrentFocus().getParent();
			checkforview5loop:

			while (!(target == parentlayout)) {
				target = target.getParent();
				if (target == mcrview1) {
					break checkforview5loop;
				}
				if (target == mcrview2) {
					break checkforview5loop;
				}
			}

			
			

			if (target == mcrview1) {
				try {
					KeyEvent pressback = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_DEL);
					dispatchKeyEvent(pressback);
					TextView nextField = (TextView) getCurrentFocus()
							.focusSearch(View.FOCUS_DOWN);
					nextField.requestFocus();

				} catch (NullPointerException e) {
					addmcrmeteringlist.performClick();
					KeyEvent pressback = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_DEL);
					dispatchKeyEvent(pressback);

				}
			}
			if (target == mcrview2) {
				try {
					KeyEvent pressback = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_DEL);
					dispatchKeyEvent(pressback);
					TextView nextField = (TextView) getCurrentFocus()
							.focusSearch(View.FOCUS_DOWN);
					nextField.requestFocus();

				} catch (NullPointerException e) {
					addmcrtempsensor.performClick();
					KeyEvent pressback = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_DEL);
					dispatchKeyEvent(pressback);

				}
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		return super.dispatchKeyEvent(event);

	}

	public void writereport() throws IOException {
		try {
			new File(ProjectDirectory).mkdirs();

			File reportfile = new File(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Report.txt");
			reportfile.delete();
			reportfile = new File(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Report.txt");
			FileWriter fw = new FileWriter(reportfile);
			PrintWriter pw = new PrintWriter(fw);

			for (int m = 0; m < s; m++) {
				if (!(m == 0)) {
					pw.println(sitename[m].label.getText().toString() + "\t"
							+ sitename[m].et.getText().toString() + "\n"
							+ sitesize[m].label.getText().toString() + "\t\t"
							+ sitesize[m].doub1 + ";"
							+ sitesize[m].tv1.getText().toString() + ";"
							+ sitesize[m].doub2 + ";"
							+ sitesize[m].tv2.getText().toString() + "\n"
							+ sitedescription[m].label.getText().toString()
							+ "\t\t"
							+ sitedescription[m].et.getText().toString() + "\n");
				} else {
					pw.println(sitename[m].label.getText().toString() + "\t"
							+ sitename[m].sp.getSelectedItem().toString()
							+ "\n");
				}

			}

			for (int m = 0; m < i; m++) {

				pw.println(assetname[m].label.getText().toString()
						+ "\t"
						+ assetname[m].et.getText().toString()
						+ "\n"
						+

						quantity[m].label.getText().toString()
						+ "\t\t\t\t"
						+ quantity[m].et.getText().toString()
						+ "\n"
						+

						type[m].label.getText().toString()
						+ "\t\t\t\t\t"
						+
						// type[m].getText().toString()+"\n"+
						type[m].sp.getSelectedItem().toString()
						+ "\n"
						+

						model[m].label.getText().toString()
						+ "\t\t\t\t\t"
						+ model[m].et.getText().toString()
						+ "\n"
						+

						spec[m].label.getText().toString()
						+ "\t\t\t\t\t"
						+ spec[m].et.getText().toString()
						+ "\n"
						+

						// //

						location[m].label.getText().toString()
						+ "\t\t\t\t\t"
						+ "\n"
						// + locationspinner[m].getSelectedItem().toString() +
						// "\n"

						+ servicearea[m].label.getText().toString() + "\t\t"
						+ servicearea[m].doub1 + ";"
						+ servicearea[m].tv1.getText().toString() + ";"
						+ servicearea[m].doub2 + ";"
						+ servicearea[m].tv2.getText().toString() + "\n" +

						assetnotes[m].label.getText().toString() + "\t\t\t\t\t"
						+ assetnotes[m].et.getText().toString() + "\n");

			}

			pw.println("Preconsumption Table: \t Site Name,\t Gross  m²,\t Gross ft²,\t 2011 Average Monthly Consumption\n\t\t\t\t\t\t\t"
					+ "Preconsumption Table Values:"
					+ constablename.getText().toString()
					+ ";\t"
					+ sitesize[1].doub1
					+ ";\t"
					+ sitesize[1].doub2
					+ ";\t"
					+ constableavgmonconsdoub + "\n");

			pw.println("Consumption Table: \t Month-Year, \t kW,\t Local Currency, \t Second Currency");
			for (int num = 0; num <= 11; num++) {
				pw.println("Month" + u.s(num) + ":\t"
						+ month[num].getText().toString() + ",\t\t"
						+ consdoub[num] + ",\t\t" + conslocdoub[num] + ",\t\t"
						+ consusddoub[num]);
			}

			pw.println("Extra Variables Titles: kWh Ratio, Currency, Second Currency, Currency Ratio");
			pw.println("Extra Variables:" + kwhratiodoub + ","
					+ localcurrencydisplayspinner.getSelectedItem().toString()
					+ ","
					+ secondcurrencydisplayspinner.getSelectedItem().toString()
					+ "," + currencyratiodoub);

			for (int m = 0; m < r; m++) {
				pw.println(issuenametextview[m].getText().toString() + "\t"
						+ issuespinner[m].getSelectedItem().toString() + "\n"
						+ recommendationtextview[m].getText().toString() + "\t"
						+ recommendationAutoCompleteTextView[m].getText().toString() + "\n");
			}

			pw.println("Recommendations Table:#,Type,Area of Opportunity,Observations,Recommendations,Estimated Annual Savings [kWh], Estimated Annual Savings Local Currency,Estimated Annual Savings Second Currency, Estimated COI Local Currency,Estimated COI Second Currency, Estimated ROI(months)");
			for (int m = 0; m < r; m++) {

				pw.println("Recommendations Table Row:"
						+ u.gtts(recommendationnumbertextview[m]) + ";"
						+ u.gsits(recommendationtypespinner[m]) + ";"
						+ u.gsits(recommendationareaofopportunityspinner[m])
						+ ";" + u.gsits(recommendationobservationsspinner[m])
						+ ";"
						+ u.gtts(recommendationrecommendationsAutoCompleteTextView[m])
						+ ";" + u.sd(recommendationeaskwhdoub[m]) + ";"
						+ u.sd(recommendationeascurrencydoub1[m]) + ";"
						+ u.sd(recommendationeascurrencydoub2[m]) + ";"
						+ u.sd(recommendationestimatedcoidoub1[m]) + ";"
						+ u.sd(recommendationestimatedcoidoub2[m]) + ";"
						+ u.sd(recommendationestimatedroidoub[m]));
			}
			pw.println("Recommendations Table Totals:,,,,,"
					+ u.sd(eastotaldoub) + "," + u.sd(eascurtotal1doub) + ","
					+ u.sd(eascurtotal2doub) + "," + u.sd(coitotaldoub1) + ","
					+ u.sd(coitotaldoub2));
			pw.println("Recommendations Table Percent:,,,,,,,,,,"
					+ u.sd(totalsavingspercentdoub));

			pw.close();
			fw.close();
		} catch (Throwable e) {

		}
	}

	

	private void readsitedb() {
		db.showfulldblog(db.TABLE_SITEINFO);
		int wb = REPORT;
		int ws = GENERAL;

		// Fill out engineer name and sitewalk date
		// name
		String cell = "B1";
		String value = "";
		try {
			value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ENGINEERINFO, DB_engineername,0);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
		} catch (Throwable e) {

		}
		// date
		cell = "B2";
		value = "";
		try {
			value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ENGINEERINFO, "date",0);
			u.log("date value retrieved "+value);
		} catch (Throwable e) {
			u.log("no date value retrieved");
			
		}
		
		if (value.equals("")) {
			String mydate = java.text.DateFormat.getDateTimeInstance().format(
					Calendar.getInstance().getTime());
			u.settexttoview(mydate,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
			ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)].requestFocus();

		} else {
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
			ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)].requestFocus();
		}

		// email
		cell = "B3";
		value = "";
		try {
			value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ENGINEERINFO, DB_engineeremail,0);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
		} catch (Throwable e) {
			
		}
		// phone
		cell = "B4";
		value = "";
		try {
			value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ENGINEERINFO, DB_engineerphonenumber,0);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
		} catch (Throwable e) {
			
		}

		// Fill out company name, and site info
		cell = "B6";
		
		value = "";
		try {
			String Table=DatabaseHandler.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitename";
			int row=0;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			value = db.getvaluemulticolumn(Table, row, column, Key);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
			addlocationtospinners(1, value);
			u.log(locationslist);
			u.log("readsitedb 1");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			sitename[0].string = new String();
			sitename[0].string = value;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		//
		// try {
		// u.log(locationslist);
		// locationslist.set(1, value);
		// 
		// u.log(locationslist);
		// } catch (IndexOutOfBoundsException e) {
		// try {
		// u.log(locationslist);
		// locationslist.add(1, value);
		// 
		// u.log(locationslist);
		// } catch (IndexOutOfBoundsException e1) {
		// u.log(locationslist);
		// locationslist.add(value);
		// u.log(locationslist);
		// 
		// + value);
		// }
		// }

		cell = "B8";
		value = "";
		try {
			String Table=DatabaseHandler.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitename";
			int row=1;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			value = db.getvaluemulticolumn(Table, row, column, Key);

			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
			addlocationtospinners(1, value);
			u.log(locationslist);
			u.log("readsitedb 2");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// try {
		// u.log("readsitedb 1");
		// u.log(locationslist);
		// locationslist.set(1, value);
		// u.log(locationslist);
		// } catch (IndexOutOfBoundsException e) {
		// try {
		// u.log("readsitedb 2");
		// u.log(locationslist);
		// locationslist.add(1, value);
		// u.log(locationslist);
		// } catch (IndexOutOfBoundsException e1) {
		// u.log("readsitedb 3");
		// u.log(locationslist);
		// locationslist.add(value);
		// u.log(locationslist);
		// }
		// }

		cell = "B9";
		value = "";
		try {
			String Table=DatabaseHandler.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitesizem";
			int row=1;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			value = db.getvaluemulticolumn(Table, row, column, Key);

			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		u.log("value"+value);
		u.log(ISMORRISONS);
		
		if (value==null){
			value="";
		}
		if((ISMORRISONS&&value.equals("")||value.equals("0"))){
			try {
				sitesize[1].doub1 = u.d(value);
			} catch (NullPointerException e) {
			
			}
		}else{
			try {
				sitesize[1].doub1=u.d(storesizeft) / mtfc;
			} catch (NullPointerException e) {

			}
		}

		cell = "D9";
		value = "";
		
		try {
			String Table=DatabaseHandler.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitesizeft";
			int row=1;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			value = db.getvaluemulticolumn(Table, row, column, Key);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
			
			
		} catch (Throwable e) {
			
		}
	
		if (value==null){
			value="";
		}
		if(ISMORRISONS&&(value.equals("")||value.equals("0"))){
			try {
				sitesize[1].doub2 = u.d(value);
			} catch (NullPointerException e) {
			
			}
		}else{
			try {
				sitesize[1].doub2=u.d(storesizeft);
			} catch (NullPointerException e) {

			}
		}

		cell = "B10";
		value = "";
		try {
			String Table=DatabaseHandler.TABLE_SITEINFO;
			String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			StorageKey="sitedescription";
			int row=1;
			int column=db.getcolumnnumberbytitle(Table,StorageKey);
			value = db.getvaluemulticolumn(Table, row, column, Key);
			
			u.settexttoview(value,
					ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
		} catch (Throwable e) {
			
		}
		int s = 0;
		cell = "B12";
		
		int h = 1;
		loop: while (h != 0) {
			h = 0;
			value = "";
			try {
				String Table=DatabaseHandler.TABLE_SITEINFO;
				String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
				StorageKey="sitename";
				int row=s+2;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);
				
				
				u.settexttoview(value,
						ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
				addlocationtospinners(s + 2, value);
				u.log(locationslist);
				u.log("readsitedb 3");
				h++;
			} catch (Throwable e) {
				
			}

			// try {
			// u.log("readsitedb 4");
			// u.log(locationslist);
			// locationslist.set(s + 2, value);
			// u.log(locationslist);
			// } catch (IndexOutOfBoundsException e) {
			// try {
			// u.log("readsitedb 5");
			// u.log(locationslist);
			// locationslist.add(s + 2, value);
			// u.log(locationslist);
			// } catch (IndexOutOfBoundsException e1) {
			// u.log("readsitedb 6");
			// u.log(locationslist);
			// locationslist.add(value);
			// u.log(locationslist);
			// }
			// }

			cell = "B" + u.s(13 + s * 4);
			value = "";
			try {
				value = db
						.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizem" , (s + 2));
				u.settexttoview(value,
						ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
				h++;
			} catch (Throwable e) {
				
			}
			

			try {
				sitename[s + 1].string = new String();
				sitename[s + 1].string = value;
			} catch (NullPointerException e) {

			}
			//
			try {
				sitesize[s + 2].doub1 = u.d(value);
			} catch (NullPointerException e) {

			}

			cell = "D" + u.s(13 + s * 4);
			value = "";
			try {
				
				String Table=DatabaseHandler.TABLE_SITEINFO;
				String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
				StorageKey="sitesizeft";
				int row=s+2;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);
				
				u.settexttoview(value,
						ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
				h++;
			} catch (Throwable e) {
				
			}
			try {
				sitesize[s + 2].doub2 = u.d(value);
			} catch (NullPointerException e) {

			}
			value = "";
			try {
				cell = "B" + u.s(14 + s * 4);
				
				String Table=DatabaseHandler.TABLE_SITEINFO;
				String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
				StorageKey="sitedescription";
				int row=s+2;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);
				
				u.settexttoview(value,
						ViewHolder[wb][ws][u.cellx(cell)][u.celly(cell)]);
				h++;
			} catch (Throwable e) {
				
			}
			s++;

			cell = "B" + u.s(12 + s * 4);

		}
		try {
			smalllocationspinneradapter.notifyDataSetChanged();
			locationspinneradapter.notifyDataSetChanged();
			location[0].sp.setAdapter(locationspinneradapter);
		} catch (NullPointerException e) {

		}

	}

	private void readcomponentsdb() {

	
	int wb = REPORT;
	int ws = COMPONENTS;

	// checkfirstrow
	String cellstart = "A2";
	String cellstop = "E2";

	int i = 0;

	int COMPONENTNAMECOLUMN=0;
	int COMPONENTTYPECOLUMN=1;
	int COMPONENTSPECCOLUMN=2;
	int COMPONENTLOCATIONCOLUMN=3;
	int COMPONENTNOTESCOLUMN=4;
	
	for (int num = COMPONENTNAMECOLUMN; num <= u.cellx(cellstop); num++) {
		String value = "";
		
		if (num == COMPONENTNAMECOLUMN) {
			try{
				String Table=DatabaseHandler.TABLE_COMPONENTINFO;
				String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
				StorageKey="componentname";
				int row=i;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);	
				
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				}catch(Throwable e){
					e.printStackTrace();
				}
			
		}
		
		if (num == COMPONENTTYPECOLUMN) {
			try{
				String Table=DatabaseHandler.TABLE_COMPONENTINFO;
				String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
				StorageKey="componenttype";
				int row=i;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);	
				
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				}catch(Throwable e){
					e.printStackTrace();
				}
			
		}
		
		if (num == COMPONENTSPECCOLUMN) {
			try{
				String Table=DatabaseHandler.TABLE_COMPONENTINFO;
				String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
				StorageKey="componentspec";
				int row=i;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);	
				
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				}catch(Throwable e){
					e.printStackTrace();
				}
			
		}
		if (num == COMPONENTLOCATIONCOLUMN) {
			try{
				String Table=DatabaseHandler.TABLE_COMPONENTINFO;
				String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
				StorageKey="componentlocation";
				int row=i;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);	
				
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				}catch(Throwable e){
					e.printStackTrace();
				}
			
		}
		
		
		if (num == COMPONENTNOTESCOLUMN) {
			try{
				String Table=DatabaseHandler.TABLE_COMPONENTINFO;
				String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
				StorageKey="componentnotes";
				int row=i;
				int column=db.getcolumnnumberbytitle(Table,StorageKey);
				value = db.getvaluemulticolumn(Table, row, column, Key);	
				
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				}catch(Throwable e){
					e.printStackTrace();
				}
		
		}

		// if the componenttype is not on local list file, add it
		if (ViewHolder[wb][ws][u.cellx(cellstart) + num][u.celly(cellstart)] instanceof Spinner
				&& (u.cellx(cellstart) + num) == 2) {
			addnewitemtolistwithoutdialog(FIRSTSHEET,value,
					ViewHolder[wb][ws][u.cellx(cellstart) + num][u
							.celly(cellstart)], COMPONENTTYPES);
		}

		if (num == COMPONENTLOCATIONCOLUMN) {
			
				smalllocationspinneradapter.notifyDataSetChanged();
				locationspinneradapter.notifyDataSetChanged();
				
				try{
				componentlocation[u.celly(cellstart) - 1].sp
						.setAdapter(locationspinneradapter);
				componentlocation[u.celly(cellstart) - 1].sp
						.setSelection(getIndexofSpinner(
								componentlocation[u.celly(cellstart) - 1].sp, value));
				}catch(Throwable e){
					u.log("componentlocations["+(u.celly(cellstart)-1)+"].sp doesn't exist");
				}

			}
		

		
	}

	int h = 1;

	loop: while (h != 0) {

		cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
		cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);

		h = 0;
		i++;
		// ((Spinner)ViewHolder[wb][ws][u.cellx(cellstart)+5][u.celly(cellstart)]);

		for (int num = 0; num <= u.cellx(cellstop); num++) {

			String value = "";
			
			
			
			if (num == COMPONENTNAMECOLUMN) {
				try{
					String Table=DatabaseHandler.TABLE_COMPONENTINFO;
					String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componentname";
					int row=i;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);	
					
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					}catch(Throwable e){
						e.printStackTrace();
					}
			}
			
			if (num == COMPONENTTYPECOLUMN) {
				try{
					String Table=DatabaseHandler.TABLE_COMPONENTINFO;
					String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componenttype";
					int row=i;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);	
					
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					}catch(Throwable e){
						e.printStackTrace();
					}
				
			}
			
			if (num == COMPONENTSPECCOLUMN) {
				try{
					String Table=DatabaseHandler.TABLE_COMPONENTINFO;
					String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componentspec";
					int row=i;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);	
					
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					}catch(Throwable e){
						e.printStackTrace();
					}
				
			}
			if (num == COMPONENTLOCATIONCOLUMN) {
				try{
					String Table=DatabaseHandler.TABLE_COMPONENTINFO;
					String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componentlocation";
					int row=i;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);	
					
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					}catch(Throwable e){
						e.printStackTrace();
					}
				
			}
			
			
			if (num == COMPONENTNOTESCOLUMN) {
				try{
					String Table=DatabaseHandler.TABLE_COMPONENTINFO;
					String[] Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componentnotes";
					int row=i;
					int column=db.getcolumnnumberbytitle(Table,StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);	
					
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					}catch(Throwable e){
						e.printStackTrace();
					}
			
			}

			// if the componenttype is not on local list file, add it
			if (ViewHolder[wb][ws][u.cellx(cellstart) + num][u.celly(cellstart)] instanceof Spinner
					&& (u.cellx(cellstart) + num) == 2) {
				addnewitemtolistwithoutdialog(FIRSTSHEET,value,
						ViewHolder[wb][ws][u.cellx(cellstart) + num][u
								.celly(cellstart)], COMPONENTTYPES);
			}

			if (num == COMPONENTLOCATIONCOLUMN) {
				
					
					smalllocationspinneradapter.notifyDataSetChanged();
					locationspinneradapter.notifyDataSetChanged();

					try{
					componentlocation[u.celly(cellstart) - 1].sp
							.setAdapter(locationspinneradapter);
					componentlocation[u.celly(cellstart) - 1].sp
							.setSelection(getIndexofSpinner(
									componentlocation[u.celly(cellstart) - 1].sp, value));
				
					}catch(Throwable e){
						u.log("componentlocations["+(u.celly(cellstart)-1)+"].sp doesn't exist");
					}
				}
			

			

			}
		}

	
}
	
	private void readassetsdb() {

		int wb = REPORT;
		int ws = ASSETS;

		// checkfirstrow
		String cellstart = "A1";
		String cellstop = "I1";

		int i = -1;
		final int ASSETNAMECOLUMN = 0;
		final int ASSETQUANTITYCOLUMN = 1;
		final int ASSETTYPECOLUMN = 2;
		final int ASSETMANUFACTURE = 3;
		final int ASSETPOWERCOLUMN = 4;
		final int ASSETLOCATIONCOLUMN = 5;
		final int ASSETSERVICEAREAM = 6;
		final int ASSETSERVICEAREAFT = 7;
		final int ASSETNOTESCOLUMN = 8;

		String Table = DatabaseHandler.TABLE_ASSETINFO;
		String[] Key = Tabs1.db.KEY_ASSET_ATTRIBUTES;
		String value = "";

		int h = 1;
		
		

		loop: while (h != 0) {

			cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
			cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);
			
			h = 0;
			i++;

			for (int num = ASSETNAMECOLUMN; num <= u.cellx(cellstop); num++) {
				try {
					value = "";

					switch (num) {
					case ASSETNAMECOLUMN:
						StorageKey = "assetname";
						break;
					case ASSETQUANTITYCOLUMN:
						StorageKey = "assetquantity";
						break;
					case ASSETTYPECOLUMN:
						StorageKey = "assettype";
						break;
					case ASSETMANUFACTURE:
						StorageKey = "assetmodel";
						break;
					case ASSETPOWERCOLUMN:
						StorageKey = "assetpower";
						break;
					case ASSETLOCATIONCOLUMN:
						StorageKey = "assetlocation";
						break;
					case ASSETSERVICEAREAM:
						StorageKey = "assetserviceaream";
						break;
					case ASSETSERVICEAREAFT:
						StorageKey = "assetserviceareaft";
						break;
					case ASSETNOTESCOLUMN:
						StorageKey = "assetnotes";
						break;
					}
					int row = i;

					int column = db.getcolumnnumberbytitle(Table, StorageKey);
					value = db.getvaluemulticolumn(Table, row, column, Key);
					u.log("column, "+column+" value, "+value+" num, "+num);
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					

					h++;
				} catch (Throwable e) {
					value = "";
					e.printStackTrace();
				}
				// if the assettype is not on local list file, add it
			
				
				if (ViewHolder[wb][ws][u.cellx(cellstart) + num][u
						.celly(cellstart)] instanceof Spinner
						&& (u.cellx(cellstart) + num) == 2) {
					
					
					
					try {
						addnewitemtolistwithoutdialog(FIRSTSHEET, value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)], ASSETTYPES);
					} catch (Throwable e) {
						e.printStackTrace();
					}

				}


				if (num == ASSETLOCATIONCOLUMN) {

					try {
						smalllocationspinneradapter.notifyDataSetChanged();
						locationspinneradapter.notifyDataSetChanged();

						location[u.celly(cellstart) - 1].sp
								.setAdapter(locationspinneradapter);
						location[u.celly(cellstart) - 1].sp
								.setSelection(getIndexofSpinner(
										location[u.celly(cellstart) - 1].sp,
										value));
					} catch (NullPointerException e) {

					}
				}

				
				if (num == ASSETSERVICEAREAM) {
					try {
						servicearea[u.celly(cellstart) - 1].doub1 = u.d(value);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				if (num == ASSETSERVICEAREAFT) {
					try {
						servicearea[u.celly(cellstart) - 1].doub2 = u.d(value);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

			}
			
			
		}
	}

	private void readrecommendationsdb() {

		int wb = REPORT;
		int ws = RECOMMENDATIONS;

		int i = 0;

		// checkfirstrow
		String cellstart = "A2";
		String cellstop = "S2";

		for (int num = 0; num <= u.cellx(cellstop); num++) {

			String value = "";
			if (num == 0) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationsobservations" , i);
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 1) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationsrecommendation" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 2) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationsnumber" , i);
					u.settexttoview("1", ViewHolder[wb][ws][u.cellx(cellstart)
							+ num][u.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 3) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationstype" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 4) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationsareaofopportunity" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 5) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsobservationssummary" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 6) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsrecommendationssummary" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 7) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationseaskwh" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					recommendationeaskwhdoub[0] = u.d(value);
				} catch (Throwable e) {

				}
			}
			if (num == 8) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationseaslc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 9) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationseassc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 10) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationscoilc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);

					String checkforeign = value;
					if (checkforeign.lastIndexOf(",") > checkforeign
							.lastIndexOf(".")) {

						checkforeign = checkforeign.replace(",", "*");
						checkforeign = checkforeign.replace(".", "@");
						checkforeign = checkforeign.replace("*", ".");
						checkforeign = checkforeign.replace("@", ",");
					}
					// recommendationestimatedcoidoub2[u.celly(cellstart)-1]=u.d(row[num]);
					try {
						recommendationestimatedcoidoub1[0] = u.d(checkforeign
								.replaceAll("[^\\d.]", ""));
						recommendationestimatedcoidoub2[0] = recommendationestimatedcoidoub1[0]
								* currencyratiodoub;
					} catch (NumberFormatException e) {

					}
				} catch (Throwable e) {

				}
			}
			if (num == 11) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationscoisc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 12) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsroi" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 13) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsteaskwh" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 14) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsteaslc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 15) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommenationsteassc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 16) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationstcoilc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 17) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationstcoisc" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 18) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
							"recommendationstsp" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}

		}
		int h = 1;

		while (h != 0) {

			cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
			cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);

			i++;
			h = 0;

			for (int num = 0; num <= u.cellx(cellstop); num++) {
				String value = "";
				if (num == 0) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationsobservations" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 1) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationsrecommendation" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 2) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationsnumber" , i);
						
						u.settexttoview("1",
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 3) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationstype" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 4) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationsareaofopportunity" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 5) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationsobservationssummary" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 6) {
					try {
						value = db
								.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
										"recommenationsrecommendationssummary"
												, i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 7) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationseaskwh" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						recommendationeaskwhdoub[0] = u.d(value);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 8) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationseaslc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 9) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationseassc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 10) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationscoilc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);

						String checkforeign = value;
						if (checkforeign.lastIndexOf(",") > checkforeign
								.lastIndexOf(".")) {

							checkforeign = checkforeign.replace(",", "*");
							checkforeign = checkforeign.replace(".", "@");
							checkforeign = checkforeign.replace("*", ".");
							checkforeign = checkforeign.replace("@", ",");
						}
						// recommendationestimatedcoidoub2[u.celly(cellstart)-1]=u.d(row[num]);
						try {
							recommendationestimatedcoidoub1[0] = u
									.d(checkforeign.replaceAll("[^\\d.]", ""));
							recommendationestimatedcoidoub2[0] = recommendationestimatedcoidoub1[0]
									* currencyratiodoub;
						} catch (NumberFormatException e) {

						}
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 11) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationscoisc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 12) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationsroi" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 13) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationsteaskwh" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 14) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationsteaslc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 15) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommenationsteassc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 16) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationstcoilc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 17) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationstcoisc" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
				if (num == 18) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_RECOMMENDATIONS,
								"recommendationstsp" , i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}
				}
			}

		}
	}

	private void readconsumptiondb() {

		int wb = REPORT;
		int ws = CONSUMPTION;

		// checkfirstrow
		String cellstart = "A2";
		String cellstop = "H2";

		int i = 0;

		for (int num = 0; num <= u.cellx(cellstop); num++) {

			String value = "";
			if (num == 0) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptiondate"
							, i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (num == 1) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptionkwh"
							, i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					consdoub[0] = u.d(value);
					
				} catch (Throwable e) {

				}
			}
			if (num == 2) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionlocalcurrency" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 3) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionsecondcurrency" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
				} catch (Throwable e) {

				}
			}
			if (num == 4) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionkwhratio" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					kwhratiodoub = u.d(value);
					
				} catch (Throwable e) {

				}
			}
			if (num == 5) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionlocalcurrencystring",0);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					/*
					 * Spinner sp = (Spinner)
					 * ViewHolder[wb][ws][u.cellx(cellstart) +
					 * num][u.celly(cellstart)];  int
					 * pos = 0; if (value.trim().length() == 0) {
					 * sp.setSelection(18); } else { for (int f = 0; f <
					 * localeslist.size(); f++) { try { String loc =
					 * value.substring( value.length() - 3, value.length());
					 * String loccheck = Currency.getInstance(
					 * localeslist.get(f)).getCurrencyCode();
					 * 
					 *  if
					 * (loc.equals(loccheck)) { pos = f; break; } } catch
					 * (StringIndexOutOfBoundsException e) {
					 *  } } sp.setSelection(pos); }
					 * localcurrencydisplayspinnerstring = value;
					 */
				} catch (Throwable e) {

				}
			}
			if (num == 6) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionsecondcurrencystring",0);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					/*
					 * Spinner sp = (Spinner)
					 * ViewHolder[wb][ws][u.cellx(cellstart) +
					 * num][u.celly(cellstart)];  int
					 * pos = 0; if (value.trim().length() == 0) { // String
					 * contains only whitespace sp.setSelection(30); } else {
					 * for (int f = 0; f < localeslist.size(); f++) { try {
					 * String loc = value.substring( value.length() - 3,
					 * value.length()); String loccheck = Currency.getInstance(
					 * localeslist.get(f)).getCurrencyCode();
					 * 
					 *  if
					 * (loc.equals(loccheck)) { pos = f; break; } } catch
					 * (StringIndexOutOfBoundsException e) {
					 *  } } sp.setSelection(pos);
					 * 
					 * } secondcurrencydisplayspinnerstring = value;
					 */
				} catch (Throwable e) {

				}
			}
			if (num == 7) {
				try {
					value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptioncurrencyratio" , i);
					
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					currencyratiodoub = u.d(value);
					
					ViewHolder[wb][ws][u.cellx(cellstart) + num][u
							.celly(cellstart)].requestFocus();
				} catch (Throwable e) {

				}
			}

		}

		int h = 1;

		loop: while (h != 0) {

			cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
			cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);
			i++;

			// ((Spinner)ViewHolder[wb][ws][u.cellx(cellstart)+5][u.celly(cellstart)]);
			h = 0;
			for (int num = 0; num <= u.cellx(cellstop); num++) {
				

				String value = "";

				if (num == 0) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptiondate" ,i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						h++;
					} catch (Throwable e) {

					}

				}
				if (num == 1) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionkwh" ,i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						consdoub[u.celly(cellstart) - 1] = u.d(value);
						h++;
					} catch (Throwable e) {

					}

				}
				if (num == 2) {
					try {
						value = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionlocalcurrency" ,i);
						
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						conslocdoub[u.celly(cellstart) - 1] = u.d(value);
						h++;
					} catch (Throwable e) {

					}

				}

			}

		}

	}


	/*
	 * public void readreport() {
	 * 
	 * File file = new File(ProjectDirectory + "/Report.txt");
	 * 
	 * // Read text from file StringBuilder text = new StringBuilder();
	 * 
	 * try { BufferedReader br = new BufferedReader(new FileReader(file));
	 * String line; int sites_read_so_far = 0; int comps = 0; int
	 * recommendations_read_so_far = 0; while ((line = br.readLine()) != null) {
	 * text.append(line); text.append('\n'); String[] tokens = new String[2];
	 * 
	 * if (!(line.equals(""))) {
	 * 
	 * tokens = line.split(":"); if (tokens.length > 1) { } else { } } else { //
	 * if nothing is on that line, just place blanks tokens = new String[2];
	 * tokens[0] = ""; tokens[1] = "";
	 * 
	 * } // Add missing blocks and fill them
	 * 
	 * if (tokens[0].trim().equals("Company Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } countedsites =
	 * sites_read_so_far;
	 * 
	 * sites_read_so_far++; }
	 * 
	 * if (tokens[0].trim().equals("Site Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } } if
	 * (tokens[0].trim().equals("Site Size")) {
	 * 
	 * readsize(tokens, sites_read_so_far);
	 * 
	 * 
	 * } if (tokens[0].trim().equals("Site Description")) {
	 * sitedescription[sites_read_so_far].et .setText(tokens[1].trim());
	 * countedsites = sites_read_so_far; sites_read_so_far++; }
	 * 
	 * if (tokens[0].trim().equals("Building Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } } if
	 * (tokens[0].trim().equals("Building Size")) {
	 * 
	 * readsize(tokens, sites_read_so_far); } if
	 * (tokens[0].trim().equals("Building Description")) {
	 * sitedescription[sites_read_so_far].et .setText(tokens[1].trim());
	 * countedsites = sites_read_so_far; sites_read_so_far++; }
	 * 
	 * if (tokens[0].trim().equals("Floor Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } } if
	 * (tokens[0].trim().equals("Floor Size")) { readsize(tokens,
	 * sites_read_so_far);
	 * 
	 * 
	 * } if (tokens[0].trim().equals("Floor Description")) {
	 * sitedescription[sites_read_so_far].et .setText(tokens[1].trim());
	 * countedsites = sites_read_so_far; sites_read_so_far++; }
	 * 
	 * if (tokens[0].trim().equals("Zone Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } } if
	 * (tokens[0].trim().equals("Zone Size")) { readsize(tokens,
	 * sites_read_so_far);
	 * 
	 * 
	 * } if (tokens[0].trim().equals("Zone Description")) {
	 * sitedescription[sites_read_so_far].et .setText(tokens[1].trim());
	 * countedsites = sites_read_so_far; sites_read_so_far++; }
	 * 
	 * if (tokens[0].trim().equals("Room Name")) { String
	 * sitenamestring=tokens[1].trim();
	 * sitename[sites_read_so_far].et.setText(sitenamestring); try{
	 * locationslist.set(sites_read_so_far,sitenamestring);
	 * }catch(IndexOutOfBoundsException e){
	 * locationslist.add(sites_read_so_far,sitenamestring); } } if
	 * (tokens[0].trim().equals("Room Size")) { readsize(tokens,
	 * sites_read_so_far);
	 * 
	 * 
	 * } if (tokens[0].trim().equals("Room Description")) {
	 * sitedescription[sites_read_so_far].et .setText(tokens[1].trim());
	 * countedsites = sites_read_so_far; sites_read_so_far++; }
	 * 
	 * // start adding assets
	 * 
	 * if (tokens[0].trim().equals("Asset Name")) {
	 * assetname[comps].et.setText(tokens[1].trim()); } if
	 * (tokens[0].trim().equals("Quantity")) {
	 * quantity[comps].et.setText(displayNumber
	 * (secondlocale,u.d(tokens[1].trim()))); } if
	 * (tokens[0].trim().equals("Type")) {
	 * type[comps].sp.setSelection(getIndexofSpinner(type[comps].sp,
	 * tokens[1].trim())); } if (tokens[0].trim().equals("Manufacture/Model")) {
	 * model[comps].et.setText(tokens[1].trim()); } if
	 * (tokens[0].trim().equals("Power(kW)")) {
	 * spec[comps].et.setText(displayNumber
	 * (secondlocale,u.d(tokens[1].trim()))); } if
	 * (tokens[0].trim()==SITETAB) {
	 * locationspinneradapter.notifyDataSetChanged();
	 * location[comps].sp.setAdapter(locationspinneradapter);
	 * location[comps].sp.
	 * setSelection(getIndexofSpinner(location[comps].sp,tokens[1].trim())); }
	 * if (tokens[0].trim().equals("Service Area")) {
	 * readserviceareasize(tokens,comps); }
	 * 
	 * if (tokens[0].trim().equals("Notes")) {
	 * notes[comps].et.setText(tokens[1].trim()); countedcomps = comps; comps++;
	 * }
	 * 
	 * if (tokens[0].trim().equals("Preconsumption Table")) { String[] values =
	 * new String[4]; String[] titleseparater = new String[2];
	 * 
	 * titleseparater = br.readLine().split("\\:"); values =
	 * titleseparater[1].split("\\;");
	 * 
	 * 
	 * constablename.setText(values[0].trim());
	 * constablemeters.setText(displayNumber
	 * (secondlocale,u.d(values[1].trim())));
	 * constablefeet.setText(displayNumber(secondlocale,u.d(values[2].trim())));
	 * constableavgmoncons
	 * .setText(displayNumber(secondlocale,u.d(values[3].trim())));
	 * 
	 * } if (tokens[0].trim().equals("Consumption Table")) { String[] constokens
	 * = new String[2]; String[] preconstokens = new String[2];
	 * 
	 * for(int num=0; num<=11;num++){ preconstokens =
	 * br.readLine().split("[\\:\\.]+"); constokens =
	 * preconstokens[1].split("[\\,\\.]+");
	 * month[num].setText(constokens[0].trim());
	 * 
	 * consdoub[num]=u.d(constokens[1]);
	 * cons[num].setText(displayNumber(secondlocale,consdoub[num]));
	 * 
	 * 
	 * }
	 * 
	 * } if (tokens[0].trim().equals("Extra Variables")) { String[]
	 * subtokens=tokens[1].split(","); kwhratiodoub=u.d(subtokens[0]);
	 * localcurrencydisplayspinner.setSelection(18);
	 * localcurrencydisplayspinner.setSelection(getIndexofSpinner(
	 * localcurrencydisplayspinner, subtokens[1].trim()));
	 * currencyratiodoub=u.d(subtokens[2]); }
	 * 
	 * 
	 * 
	 * 
	 * if (tokens[0].trim().equals("Issue")) {
	 * issuespinner[recommendations_read_so_far]
	 * .setSelection(getIndexofSpinner(
	 * issuespinner[recommendations_read_so_far], tokens[1].trim()));
	 * 
	 * } if (tokens[0].trim().equals("Recommendation")) {
	 * recommendationAutoCompleteTextView[recommendations_read_so_far]
	 * .setText(tokens[1].trim());
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * if (tokens[0].trim().equals("Recommendations Table Row")) { String[]
	 * subtokens=tokens[1].split(";");
	 * recommendationnumbertextview[recommendations_read_so_far
	 * ].setText(subtokens[0]);
	 * recommendationtypespinner[recommendations_read_so_far
	 * ].setSelection(getIndexofSpinner(
	 * recommendationtypespinner[recommendations_read_so_far],
	 * subtokens[1].trim()));
	 * recommendationareaofopportunityspinner[recommendations_read_so_far
	 * ].setSelection(getIndexofSpinner(
	 * recommendationareaofopportunityspinner[recommendations_read_so_far],
	 * subtokens[2].trim()));
	 * recommendationobservationsspinner[recommendations_read_so_far
	 * ].setSelection(getIndexofSpinner(
	 * recommendationobservationsspinner[recommendations_read_so_far],
	 * subtokens[3].trim()));
	 * recommendationrecommendationsAutoCompleteTextView[recommendations_read_so_far
	 * ].setText(subtokens[4]);
	 * recommendationeaskwhdoub[recommendations_read_so_far]=u.d(subtokens[5]);
	 * recommendationeascurrencydoub1
	 * [recommendations_read_so_far]=u.d(subtokens[6]);
	 * recommendationeascurrencydoub2
	 * [recommendations_read_so_far]=u.d(subtokens[7]);
	 * recommendationestimatedcoidoub1
	 * [recommendations_read_so_far]=u.d(subtokens[8]);
	 * recommendationestimatedcoidoub2
	 * [recommendations_read_so_far]=u.d(subtokens[9]);
	 * recommendationestimatedroidoub
	 * [recommendations_read_so_far]=u.d(subtokens[10]);
	 * 
	 * recommendations_read_so_far++;
	 * 
	 * } if (tokens[0].trim().equals("Recommendations Table Totals")) { String[]
	 * subtokens=tokens[1].split(",");
	 * 
	 * eastotaldoub=u.d(subtokens[5]); eascurtotal1doub=u.d(subtokens[6]);
	 * eascurtotal2doub=u.d(subtokens[7]); coitotaldoub1=u.d(subtokens[8]);
	 * coitotaldoub2=u.d(subtokens[9]);
	 * 
	 * } if (tokens[0].trim().equals("Recommendations Table Percent")) {
	 * String[] subtokens=tokens[1].split(",");
	 * 
	 * totalsavingspercentdoub=u.d(subtokens[10]);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * } catch (IOException e) { // You'll need to add proper error handling
	 * here }
	 * 
	 * }
	 */
	public void addpictures(int type) {
		//u.log("addpictures started");
		int counted = 0;
		if (type==SITETAB) {
			counted = db.countrows(db.TABLE_SITEINFO);
		}
		if (type==COMPONENTSTAB) {
			counted = db.countrows(db.TABLE_COMPONENTINFO);
		}
		if (type==ASSETSTAB) {
			counted = db.countrows(db.TABLE_ASSETINFO);
		}
		//u.log("counted "+counted);
		for(int q=0; q<counted; q++){
			try{
				if(!(type==SITETAB && q==0)){
					//u.log("addpicforonerowstarted ,type,q "+type+" "+q);
					addpicturesforonerow(type, q);
				}
			}catch(Throwable e){
				u.log("Error adding picture to following row, "+u.s(q));
			}
		}
	
	}

	public void addpicturesforonerow(int type, int q) {
		
		//u.log("type="+type);
		int[] picsforrow = null;
		TableRow[] tr = null;
		ImageView[][] pic = null;
		
		if (type==SITETAB) {
			for(int z=0; z<s;z++){
				//u.log("row in location"+z);
				sitepicnum[z] = countpics(SITETAB, z);
			
			}
			type = SITETAB;
			pic = sitepicture;
			
			picsforrow = sitepicnum;
			tr=str5;

		}
		if (type==COMPONENTSTAB) {
			for(int z=0; z<c;z++){
				int totalpiccountforrow=countpics(COMPONENTSTAB, z);
				//u.log("number of pics for row,"+z+"is"+totalpiccountforrow);
				componentpicnum[z] = totalpiccountforrow;
			}
			type = COMPONENTSTAB;
			pic = componentpicture;
			
			picsforrow = componentpicnum;
			tr=ctr10;
		}
		if (type==ASSETSTAB) {
			for(int z=0; z<i;z++){
				int totalpiccountforrow=countpics(ASSETSTAB, z);
				//u.log("number of pics for row,"+z+"is"+totalpiccountforrow);
				assetpicnum[z] = totalpiccountforrow;
			}
			type = ASSETSTAB;
			pic = picture;
			
			picsforrow = assetpicnum;
			tr=tr10;
		}

//		for(int v=0;v<tr.length;v++){
//			try{
//			tr[v].removeAllViews();
//			}catch(Throwable e){
//				
//			}
//			
//		}
		
		final int osmosistype = type;
		final ImageView[][] osmosispic = pic;
		
			
			BitmapFactory.Options o = new BitmapFactory.Options();

			// ********This value speeds up program dramatically!!!!!!!!!!!!
			// **************************************************************
			// ***************************************************************
			// ***************************************************************
			o.inSampleSize = PICTURESAMPLESIZE;
			// *************************************************************
			// *************************************************************
			
			String[] checkstring = new String[pic[q].length];

			File[] check = new File[pic[q].length];

			
			for (int num = 0; num < picsforrow[q]; num++) {
				checkstring[num] = imagepaths[type][q][num];
				//u.log("imagepath for type ,"+type+"row ,"+q+"column, "+num+", "+checkstring[num]);
				
				check[num] = new File(checkstring[num]);
				if (check[num].exists()) {
					
					Bitmap bm = BitmapFactory.decodeFile(checkstring[num], o);
					try {
						double calcheight = (double) screenheight
								* (double) PICTUREDISPLAYPERCENTOFSCREEN
								/ (double) 100;
						double calcwidth = (double) bm.getWidth()
								/ (double) bm.getHeight() * (double) calcheight;

						int height = (int) calcheight;
						int width = (int) calcwidth;

						Bitmap resizedbitmap = null;

						resizedbitmap = Bitmap.createScaledBitmap(bm, width,
								height, true);
						bm.recycle();
						

						
						
						
						pic[q][num] = new ImageView(this);
						pic[q][num].setLayoutParams(lpfw);
						pic[q][num].setLayoutParams(str5lp);
						pic[q][num].setImageBitmap(resizedbitmap);
						tr[q].addView(pic[q][num]);
//						u.log("pic["+q+"]["+num+"] wasn't in tablerow but now is");
						
						
						
						
						pic[q][num].setOnClickListener(new OnClickListener() {

							public void onClick(View v) {

								try {
									int[] z = getsequencenumber(osmosispic, v);
									String imagenamestring = null;
									if (osmosistype==ASSETSTAB) {
										imagenamestring = assetname[z[0]].et
												.getText().toString();
									}
									if (osmosistype==SITETAB) {
										if (z[0] != 0) {
											imagenamestring = sitename[z[0]].et
													.getText().toString();
										} else {
											imagenamestring = sitename[z[0]].sp
													.getSelectedItem()
													.toString();
										}
									}
									try {
										
										String picturelocation = imagepaths[osmosistype][z[0]][z[1]];

										lhiphotoeditororotherdialog(
												picturelocation, osmosistype,
												z[0], z[1]);

										// startActivityForResult(u.openpicture(buildimagenamestringgivenname(imagenamestring,
										// osmosistype, z[0], z[1],
										// null)),EDITPICTUREACTIVITY);
									} catch (ActivityNotFoundException e) {
										showneedmoresoftwaredialog(
												"ASTRO File Manager/Browser",
												"com.metago.astro");
									}

								} catch (IndexOutOfBoundsException e) {

								}
							}
						});
						pic[q][num]
								.setOnLongClickListener(new OnLongClickListener() {

									public boolean onLongClick(View v) {

										int[] z = null;
										try {
											z = getsequencenumber(osmosispic, v);
											String imagenamestring = null;
											if (osmosistype
													==ASSETSTAB) {
												imagenamestring = assetname[z[0]].et
														.getText().toString();
												typeselected = ASSETSTAB;
											} else if (osmosistype==SITETAB) {
												imagenamestring = sitename[z[0]].et
														.getText().toString();
												typeselected = SITETAB;
											}

											
											TableRow parentrow = (TableRow) v
													.getParent();
											numberselected = u.s(z[0]);
											subnumberselected = u.s(z[1]);
											deleteorduplicatepicturedialog(
													new File(imagepaths[osmosistype][z[0]][z[1]]),
													(ImageView) v, parentrow);
										} catch (IndexOutOfBoundsException e) {

										}

										return false;
									}

								});
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				} else {
					break;
				}
			}
			if (type==SITETAB) {
				sitepicture=pic;
			}
			if (type==ASSETSTAB) {
				picture=pic;
			}

		
	}

	public static void zip(File directory, File zipfile) throws IOException {
		URI base = directory.toURI();
		Deque<File> queue = new LinkedList<File>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;
			while (!queue.isEmpty()) {
				directory = queue.pop();
				for (File kid : directory.listFiles()) {
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory()) {
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new ZipEntry(name));
					} else {
						zout.putNextEntry(new ZipEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
		} finally {
			res.close();
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	private static void copy(File file, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			copy(in, out);
		} finally {
			in.close();
		}
	}

	

	public void zipall() {

		File folder = new File(ProjectDirectory);
		// from=printFileNames(folder, null);
        String masterzipfolder=masterfoldername.substring(0, masterfoldername.length()-1)+"zips";
		
		File destin = new File(masterzipfolder+"/"+ foldername + ".zip");
		destin.mkdirs();
		destin.delete();

		try {
			zip(folder, destin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

	public static String[] printFileNames(File fName) {
		ArrayList<String> newlist = new ArrayList<String>();
		if (fName.listFiles() != null)
			for (File f : fName.listFiles()) {
				if (f.isDirectory()) {

					printFileNames(f);

				} else {

					String extension = null;

					String name = f.getName();
					String[] separated = new String[2];

					if (!(name.equals(""))) {
						separated = name.split("[\\.\\.]+");

					}

					try {
						extension = separated[1];

						// extension = separated[1];

						if (!extension.equals("zip")) {

							name = f.getAbsolutePath();
							// name = ProjectDirectory + "/" + name;
							newlist.add(name);
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						
					}
				}

			}
		return newlist.toArray(new String[newlist.size()]);
	}

	public static String[] printFolderNames(File fName, TextView tv) {
		ArrayList<String> newlist = new ArrayList<String>();
		if (fName.listFiles() != null)
			for (File f : fName.listFiles()) {
				if (f.isDirectory()) {
					String name = f.getName();

					name = fName.getName() + "/" + name;
					newlist.add(name);

					printFileNames(f);
				}

			}
		return newlist.toArray(new String[newlist.size()]);
	}

	public void applyFonts(final View v, final Typeface fontToSet,
			final int size, final View excludeview1, final View excludeview2,
			final View excludeview3) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (!(v == excludeview1 || v == excludeview2 || v == excludeview3)) {
					try {
						if (v instanceof ViewGroup) {
							ViewGroup vg = (ViewGroup) v;
							for (int i = 0; i < vg.getChildCount(); i++) {
								View child = vg.getChildAt(i);
								applyFonts(child, fontToSet, size,
										excludeview1, excludeview2,
										excludeview3);
							}
						} else if (v instanceof TextView) {

							((TextView) v).setTypeface(fontToSet);
							((TextView) v).setTextSize(size);
							((TextView) v).setShadowLayer(6f, 4f, 4f,
									0xFF00ccff);

						}
					} catch (Exception e) {
						
						// ignore
					}
				}
			}
		});
	}

	public static void centerall(final View v, final View excludeview) {

		if (!(v == excludeview)) {
			try {
				if (v instanceof ViewGroup) {
					ViewGroup vg = (ViewGroup) v;
					for (int i = 0; i < vg.getChildCount(); i++) {
						View child = vg.getChildAt(i);
						centerall(child, excludeview);
					}
				} else if (v instanceof TextView) {
					((TextView) v).setGravity(Gravity.CENTER | Gravity.BOTTOM);

				} else if (v instanceof AutoCompleteTextView) {
					((AutoCompleteTextView) v).setGravity(Gravity.CENTER | Gravity.BOTTOM);

				} else if (v instanceof Spinner) {
					// ((Spinner) v).setGravity(Gravity.CENTER |
					// Gravity.BOTTOM);

				}
			} catch (Exception e) {
				
				// ignore
			}
		}
	}

	public void setupconsumptiontable() {
		
		

		conversionratesgooglebutton = (ImageView) findViewById(R.id.conversionratesgooglebutton);
		conversionratesgooglebutton
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

						String currency = localcurrencydisplayspinner
								.getSelectedItem().toString();
						String secondcurrency = secondcurrencydisplayspinner
								.getSelectedItem().toString();

						String[] cur = new String[2];
						cur = currency.split("[\\)\\.]+");
						if (cur.length > 1) {
							currency = cur[1].substring(0, 3);
						} else {
							currency = currency.substring(0, 3);
						}

						String[] seccur = new String[2];
						seccur = secondcurrency.split("[\\)\\.]+");
						if (seccur.length > 1) {
							secondcurrency = seccur[1].substring(0, 3);
						} else {
							secondcurrency = secondcurrency.substring(0, 3);
						}

						String search = "1" + currency + "=?" + secondcurrency;
						intent.putExtra(SearchManager.QUERY, search);
						startActivity(intent);
						getratiofromwebandposttodouble();
					}
				});
		conversionrateswebpagebutton = (ImageView) findViewById(R.id.conversionrateswebpagebutton);
		conversionrateswebpagebutton
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_BROWSABLE);
						intent.setData(Uri
								.parse("http://www.federalreserve.gov/releases/h10/Hist/"));
						startActivity(intent);
					}
				});

		constablename = (TextView) findViewById(R.id.constablename);
		constablename.setText(sitename[1].string);
		constablemeters = (TextView) findViewById(R.id.constablemeters);
		constablefeet = (TextView) findViewById(R.id.constablefeet);
		constableavgmoncons = (TextView) findViewById(R.id.constableavgmoncons);

		consumptioncurrencytopdisplay = (TextView) findViewById(R.id.consumptioncurrencytopdisplay);
		secondconsumptioncurrencytopdisplay = (TextView) findViewById(R.id.secondconsumptioncurrencytopdisplay);

		localcurrencydisplaytext = (TextView) findViewById(R.id.localcurrencydisplaytextview);

		localcurrencydisplayspinner = (Spinner) findViewById(R.id.localcurrencydisplayspinner);

		ArrayAdapter<String> localcurrencydisplayspinneradapter = new ArrayAdapter<String>(
				this, R.layout.spinnertextview, currencylist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		localcurrencydisplayspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);

		localcurrencydisplayspinner
				.setAdapter(localcurrencydisplayspinneradapter);

		int localdisp = 1, seconddisp = 1;
		for (int k = 0; k < currencycodelist.size(); k++) {
			if (currencycodelist.get(k).equals("EUR")) {
				localdisp = k;
			}
			if (currencycodelist.get(k).equals("USD")) {
				seconddisp = k;
			}
		}

		localcurrencydisplayspinner.setSelection(localdisp);
		localcurrencydisplayspinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// change text on top of chart
						// change text on for kwhratioAutoCompleteTextView

						try{
							locale = localeslist.get(position);
						}catch(Throwable e){
							locale = localeslist.get(0);
						}
						consumptioncurrencytopdisplay
								.setText(localcurrencydisplayspinner
										.getSelectedItem().toString());
						localcurrencydisplaytext
								.setText(localcurrencydisplayspinner
										.getSelectedItem().toString());
						if (CONSUMPTIONTABLOADED) {
							try {
								db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
										"consumptionlocalcurrencystring",
										((TextView) localcurrencydisplayspinner
												.getSelectedView()).getText()
												.toString());
							} catch (Throwable e) {
								
							}
						}
						displaydoubles();
					}

					public void onNothingSelected(AdapterView<?> parent) {
						showToast("Spinner1: unselected");
					}
				});

		secondcurrencydisplayspinner = (Spinner) findViewById(R.id.secondcurrencydisplayspinner);

		ArrayAdapter<String> secondcurrencydisplayspinneradapter = new ArrayAdapter<String>(
				this, R.layout.spinnertextview, currencylist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		secondcurrencydisplayspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);

		secondcurrencydisplayspinner
				.setAdapter(secondcurrencydisplayspinneradapter);
		secondcurrencydisplayspinner.setSelection(seconddisp);
		secondcurrencydisplayspinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// change text on top of chart
						// change text on for kwhratioAutoCompleteTextView

						
						try{
							secondlocale = localeslist.get(position);
						}catch(Throwable e){
							secondlocale = localeslist.get(0);
						}
						
						secondconsumptioncurrencytopdisplay
								.setText(secondcurrencydisplayspinner
										.getSelectedItem().toString());
						// secondcurrencydisplaytext.setText(secondcurrencydisplayspinner.getSelectedItem().toString());

						try {
							db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
									"consumptionsecondcurrencystring",
									((TextView) secondcurrencydisplayspinner
											.getSelectedView()).getText()
											.toString());
						} catch (Throwable e) {
							
						}

						displaydoubles();
					}

					public void onNothingSelected(AdapterView<?> parent) {
						showToast("Spinner1: unselected");
					}

				});

		kwhratiotextview = (TextView) findViewById(R.id.kwhratiotextview);

		currencyratio = (AutoCompleteTextView) findViewById(R.id.currencyratioAutoCompleteTextView);
		currencyratio.setSelectAllOnFocus(true);

		currencyratio.setFocusableInTouchMode(false);
		currencyratio.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				currencyratio.setFocusableInTouchMode(true);
				currencyratio.requestFocus();
				currencyratio.setText(String.valueOf(currencyratiodoub));
				currencyratio.selectAll();
				currencyratio.setFocusableInTouchMode(false);
				currencyratio.requestFocus();
			}

		});

		currencyratio
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						currencyratio.setSelectAllOnFocus(true);
						currencyratiodoub = u.gttd(currencyratio);
						displaydoubles();

						if (CONSUMPTIONTABLOADED) {

							try {
								db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
										"consumptioncurrencyratio",
										u.sd(currencyratiodoub));
							} catch (Throwable e) {
								
							}
						}

					}
				});

		month[0] = (TextView) findViewById(R.id.month0);
		month[1] = (TextView) findViewById(R.id.month1);
		month[2] = (TextView) findViewById(R.id.month2);
		month[3] = (TextView) findViewById(R.id.month3);
		month[4] = (TextView) findViewById(R.id.month4);
		month[5] = (TextView) findViewById(R.id.month5);
		month[6] = (TextView) findViewById(R.id.month6);
		month[7] = (TextView) findViewById(R.id.month7);
		month[8] = (TextView) findViewById(R.id.month8);
		month[9] = (TextView) findViewById(R.id.month9);
		month[10] = (TextView) findViewById(R.id.month10);
		month[11] = (TextView) findViewById(R.id.month11);

		constot = (TextView) findViewById(R.id.constot);
		constotloc = (TextView) findViewById(R.id.constotloc);
		constotusd = (TextView) findViewById(R.id.constotusd);

		cons[0] = (AutoCompleteTextView) findViewById(R.id.cons0);
		cons[1] = (AutoCompleteTextView) findViewById(R.id.cons1);
		cons[2] = (AutoCompleteTextView) findViewById(R.id.cons2);
		cons[3] = (AutoCompleteTextView) findViewById(R.id.cons3);
		cons[4] = (AutoCompleteTextView) findViewById(R.id.cons4);
		cons[5] = (AutoCompleteTextView) findViewById(R.id.cons5);
		cons[6] = (AutoCompleteTextView) findViewById(R.id.cons6);
		cons[7] = (AutoCompleteTextView) findViewById(R.id.cons7);
		cons[8] = (AutoCompleteTextView) findViewById(R.id.cons8);
		cons[9] = (AutoCompleteTextView) findViewById(R.id.cons9);
		cons[10] = (AutoCompleteTextView) findViewById(R.id.cons10);
		cons[11] = (AutoCompleteTextView) findViewById(R.id.cons11);

		consloc[0] = (AutoCompleteTextView) findViewById(R.id.consloc0);
		consloc[1] = (AutoCompleteTextView) findViewById(R.id.consloc1);
		consloc[2] = (AutoCompleteTextView) findViewById(R.id.consloc2);
		consloc[3] = (AutoCompleteTextView) findViewById(R.id.consloc3);
		consloc[4] = (AutoCompleteTextView) findViewById(R.id.consloc4);
		consloc[5] = (AutoCompleteTextView) findViewById(R.id.consloc5);
		consloc[6] = (AutoCompleteTextView) findViewById(R.id.consloc6);
		consloc[7] = (AutoCompleteTextView) findViewById(R.id.consloc7);
		consloc[8] = (AutoCompleteTextView) findViewById(R.id.consloc8);
		consloc[9] = (AutoCompleteTextView) findViewById(R.id.consloc9);
		consloc[10] = (AutoCompleteTextView) findViewById(R.id.consloc10);
		consloc[11] = (AutoCompleteTextView) findViewById(R.id.consloc11);

		consusd[0] = (TextView) findViewById(R.id.consusd0);
		consusd[1] = (TextView) findViewById(R.id.consusd1);
		consusd[2] = (TextView) findViewById(R.id.consusd2);
		consusd[3] = (TextView) findViewById(R.id.consusd3);
		consusd[4] = (TextView) findViewById(R.id.consusd4);
		consusd[5] = (TextView) findViewById(R.id.consusd5);
		consusd[6] = (TextView) findViewById(R.id.consusd6);
		consusd[7] = (TextView) findViewById(R.id.consusd7);
		consusd[8] = (TextView) findViewById(R.id.consusd8);
		consusd[9] = (TextView) findViewById(R.id.consusd9);
		consusd[10] = (TextView) findViewById(R.id.consusd10);
		consusd[11] = (TextView) findViewById(R.id.consusd11);

		monthfilebutton[0] = (ImageView) findViewById(R.id.monthfiles0);
		monthfilebutton[1] = (ImageView) findViewById(R.id.monthfiles1);
		monthfilebutton[2] = (ImageView) findViewById(R.id.monthfiles2);
		monthfilebutton[3] = (ImageView) findViewById(R.id.monthfiles3);
		monthfilebutton[4] = (ImageView) findViewById(R.id.monthfiles4);
		monthfilebutton[5] = (ImageView) findViewById(R.id.monthfiles5);
		monthfilebutton[6] = (ImageView) findViewById(R.id.monthfiles6);
		monthfilebutton[7] = (ImageView) findViewById(R.id.monthfiles7);
		monthfilebutton[8] = (ImageView) findViewById(R.id.monthfiles8);
		monthfilebutton[9] = (ImageView) findViewById(R.id.monthfiles9);
		monthfilebutton[10] = (ImageView) findViewById(R.id.monthfiles10);
		monthfilebutton[11] = (ImageView) findViewById(R.id.monthfiles11);
		monthfilebutton[12] = (ImageView) findViewById(R.id.monthfiles12);

		for (int num = 0; num <= 11; num++) {
			cons[num].setSelectAllOnFocus(true);
			addmonthsbuttonlistener(month[num], month);

			addmonthfilebuttonlistener(monthfilebutton[num], num, month[num]);
			addtextchangelistener(cons[num], cons, consdoub);
			addtextchangelistener(consloc[num], consloc, conslocdoub);

			link1cellto1view(REPORT, CONSUMPTION, "A" + u.s(num + 2), month[num]);
			link1cellto1view(REPORT, CONSUMPTION, "B" + u.s(num + 2), cons[num]);
			link1cellto1view(REPORT, CONSUMPTION, "C" + u.s(num + 2), consloc[num]);
			link1cellto1view(REPORT, CONSUMPTION, "D" + u.s(num + 2), consusd[num]);
		}
		addmonthfilebuttonlistener(monthfilebutton[12], 12, null);

		link1cellto1view(REPORT, CONSUMPTION, "E2", kwhratiotextview);
		link1cellto1view(REPORT, CONSUMPTION, "F2", localcurrencydisplayspinner);
		link1cellto1view(REPORT, CONSUMPTION, "G2", secondcurrencydisplayspinner);
		link1cellto1view(REPORT, CONSUMPTION, "H2", currencyratio);

	}

	public void calculateconsumptiontable() {

		// fill in missing months with averages
		int monthsavailable = 0;
		double constotavail = 0;
		double consloctot = 0;
		for (int i = 0; i <= 11; i++) {
			if (!month[i].getText().toString().contains("*")) {

				constotavail = consdoub[i] + constotavail;
				consloctot = conslocdoub[i] + consloctot;
				monthsavailable++;
			}
		}

		
		
		

		for (int i = 0; i <= 11; i++) {
			if (month[i].getText().toString().contains("*")) {

				cons[i].requestFocus();
				consdoub[i] = constotavail / ((double) monthsavailable);
				cons[i].setText(u.sd(consdoub[i]));

				consloc[i].requestFocus();
				conslocdoub[i] = consloctot / ((double) monthsavailable);
				consloc[i].setText(u.sd((double) Math
						.round(conslocdoub[i] * 100) / 100));

				consusddoub[i] = conslocdoub[i] * currencyratiodoub;
				consusd[i].setText(displayNumber(secondlocale, u.d(u
						.sd((double) Math.round(consusddoub[i] * 100) / 100))));

			}
		}
		double annualtot = 0;
		double loccurtot = 0;
		double usdcurtot = 0;

		int totalnums = 0;

		double avgkwhratio = 0;

		constablename.setText(sitename[1].et.getText().toString());
		constablemeters.setText(displayNumber(secondlocale,
				u.d(u.sd((double) Math.round(sitesize[1].doub1)))));
		constablefeet.setText(displayNumber(secondlocale,
				u.d(u.sd((double) Math.round(sitesize[1].doub2)))));

		for (int num = 0; num <= 11; num++) {
			cons[num].setText(u.sd(consdoub[num]));
			annualtot = consdoub[num] + annualtot;
			loccurtot = conslocdoub[num] + loccurtot;
			usdcurtot = consusddoub[num] + usdcurtot;

			if (!(consdoub[num] == 0)) {
				totalnums++;
			}

			if (conslocdoub[num] != 0 || consdoub[num] != 0) {
				avgkwhratio = ((conslocdoub[num] / consdoub[num]) + avgkwhratio) / 2;
			}
		}

		if (!(totalnums == 0)) {
			constableavgmonconsdoub = annualtot / totalnums;
			constableavgmoncons.setText(displayNumber(secondlocale,
					u.d(String.valueOf(annualtot / totalnums))));
			

		} else {

			constableavgmoncons.setText(displayNumber(secondlocale, u.d("")));

		}

		constotdoub = annualtot;
		constot.setText(displayNumber(secondlocale,
				u.d(String.valueOf(constotdoub))));
		constot.setTypeface(null, Typeface.BOLD);

		constotusddoub = usdcurtot;
		constotusd.setText(displayCurrency(secondlocale,
				u.d(String.valueOf(constotusddoub))));
		constotusd.setTypeface(null, Typeface.BOLD);

		constotlocdoub = loccurtot;
		constotloc.setText(displayCurrency(locale,
				u.d(String.valueOf(constotlocdoub))));
		constotloc.setTypeface(null, Typeface.BOLD);

		kwhratiodoub = avgkwhratio;
		kwhratiotextview
				.setText(u.sd((double) Math.round(kwhratiodoub * 100) / 100));

	}

	public void calculaterecommendationstable() {

		double eastot = 0;
		double eascurtot1 = 0;
		double eascurtot2 = 0;
		double coitot1 = 0;
		double coitot2 = 0;
		double totsavperc = 0;

		for (int num = 0; num < r; num++) {

			if (recommendationtypespinner[num].getSelectedItem().toString()
					.trim().equals("MCS")) {

				recommendationestimatedcoidoub1[num] = 0;
				recommendationestimatedcoiAutoCompleteTextView1[num]
						.setVisibility(View.INVISIBLE);
				recommendationestimatedcoidoub2[num] = 0;
				recommendationestimatedcoiAutoCompleteTextView2[num]
						.setVisibility(View.INVISIBLE);

				recommendationestimatedroitextview[num].setText("Instant");

			} else {
				recommendationestimatedroitextview[num]
						.setText(new DecimalFormat("##.##")
								.format(recommendationestimatedroidoub[num])
								+ " months");
				recommendationestimatedcoiAutoCompleteTextView1[num]
						.setVisibility(View.VISIBLE);
				recommendationestimatedcoiAutoCompleteTextView2[num]
						.setVisibility(View.VISIBLE);
			}

			eastot = recommendationeaskwhdoub[num] + eastot;
			eascurtot1 = recommendationeascurrencydoub1[num] + eascurtot1;
			eascurtot2 = recommendationeascurrencydoub2[num] + eascurtot2;
			coitot1 = recommendationestimatedcoidoub1[num] + coitot1;
			coitot2 = recommendationestimatedcoidoub2[num] + coitot2;

			if (!(sitesize[1].doub1 == 0)) {
				kwhmyrratio = constotdoub / sitesize[1].doub1;
			} else {
				kwhmyrratio = 0;
			}

			recommendationeaskwhAutoCompleteTextView[num].setText(displayNumber(
					secondlocale, u.d(u.sd((double) Math
							.round(recommendationeaskwhdoub[num])))));

			recommendationeascurrencydoub1[num] = recommendationeaskwhdoub[num]
					* kwhratiodoub;
			recommendationeascurrencydoub2[num] = recommendationeascurrencydoub1[num]
					* currencyratiodoub;

			recommendationestimatedcoiAutoCompleteTextView1[num].setText(displayCurrency(
					locale, u.d(u.sd((double) Math
							.round(recommendationestimatedcoidoub1[num])))));
			recommendationestimatedcoiAutoCompleteTextView2[num].setText(displayCurrency(
					secondlocale, u.d(u.sd((double) Math
							.round(recommendationestimatedcoidoub2[num])))));

			recssitenametextview.setText("Site Name:" + u.gtts(sitename[1].et));
			recsareatextview.setText((displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(sitesize[1].doub1)))))
					+ " m² ("
					+ (displayNumber(secondlocale,
							u.d(u.sd((double) Math.round(sitesize[1].doub2)))))
					+ " ft²)");
			recselectricconsumptiontextview.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(constotdoub)))));
			recscostratiotitletextview.setText("Cost ["
					+ Currency.getInstance(locale).getSymbol() + "/kWh]");

			recscostratiotextview.setText(u.sd(kwhratiodoub));

			recsratiotextview.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(kwhmyrratio)))));

		}

		totsavperc = eastot / constotdoub;

		eastotaldoub = eastot;
		eascurtotal1doub = eascurtot1;
		eascurtotal2doub = eascurtot2;
		coitotaldoub1 = coitot1;
		coitotaldoub2 = coitot2;
		totalsavingspercentdoub = totsavperc;

		eastotal.setText(displayNumber(secondlocale, eastotaldoub));
		eastotal.setTypeface(null, Typeface.BOLD);

		eascurtotal1.setText(displayCurrency(locale, eascurtotal1doub));
		eascurtotal1.setTypeface(null, Typeface.BOLD);

		eascurtotal2.setText(displayCurrency(secondlocale, eascurtotal2doub));
		eascurtotal2.setTypeface(null, Typeface.BOLD);

		coitotal1.setText(displayCurrency(secondlocale, coitotaldoub1));
		coitotal1.setTypeface(null, Typeface.BOLD);

		coitotal2.setText(displayCurrency(secondlocale, coitotaldoub2));
		coitotal2.setTypeface(null, Typeface.BOLD);

		totalsavingspercenttextview.setText(String.format("%.1f",
				totalsavingspercentdoub * 100) + "%");
		totalsavingspercenttextview.setTypeface(null, Typeface.BOLD);

		filloutrecscolumns();
	}

	public void setuprecommendationstable() {

		recssitenametextview = (TextView) findViewById(R.id.recssitenametextview);
		recsareatextview = (TextView) findViewById(R.id.recsareatextview);
		recselectricconsumptiontextview = (TextView) findViewById(R.id.recselectricconsumptiontextview);
		recscostratiotitletextview = (TextView) findViewById(R.id.recscostratiotitletextview);
		recscostratiotextview = (TextView) findViewById(R.id.recscostratiotextview);
		recsratiotextview = (TextView) findViewById(R.id.recsratiotextview);

		eastotal = (TextView) findViewById(R.id.eastotal);
		eascurtotal1 = (TextView) findViewById(R.id.eascurtotal1);
		eascurtotal2 = (TextView) findViewById(R.id.eascurtotal2);
		coitotal1 = (TextView) findViewById(R.id.coitotal1);
		coitotal2 = (TextView) findViewById(R.id.coitotal2);
		totalsavingspercenttextview = (TextView) findViewById(R.id.totalsavingspercenttextview);

		link1cellto1view(REPORT, RECOMMENDATIONS, "M2", eastotal);
		link1cellto1view(REPORT, RECOMMENDATIONS, "O2", eascurtotal1);
		link1cellto1view(REPORT, RECOMMENDATIONS, "P2", eascurtotal2);
		link1cellto1view(REPORT, RECOMMENDATIONS, "Q2", coitotal1);
		link1cellto1view(REPORT, RECOMMENDATIONS, "R2", coitotal2);
		link1cellto1view(REPORT, RECOMMENDATIONS, "S2",
				totalsavingspercenttextview);

		calculatorbuttoncoi = new ImageView(this);
		calculatorbuttoncoi.setImageResource(R.drawable.calculator48x48);
		calculatorbuttoncoi.setLayoutParams(lpww);
		calculatorbuttoncoi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				coicalcdialog();
			}
		});

		zoominbutton = (ImageView) findViewById(R.id.zoominbutton);
		zoominbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});
		zoomoutbutton = (ImageView) findViewById(R.id.zoomoutbutton);
		zoomoutbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

    final List<String> sitetypelist = new ArrayList<String>();
    sitetypelist.add("Grocery Store");
    sitetypelist.add("Data Center");
    sitetypelist.add("Telecom");
    sitetypelist.add("Office Building");
    sitetypelist.add("Water Plant");
    
	recommendationsitetype = (Spinner) findViewById(R.id.sitetypespinner);
	
	ArrayAdapter<String> recommendationsitetypeadapter = new ArrayAdapter<String>(
			this, R.layout.spinnertextview, sitetypelist) {
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
	
			((TextView) v).setTextSize(fontsize);
			((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);
	
			return v;
		}
	
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			View v = super.getDropDownView(position, convertView, parent);
			// ((TextView) v).setTypeface(fontToSet);
			((TextView) v).setTextSize(fontsize);
			((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);
	
			return v;
		}
		};
	
   recommendationsitetype.setAdapter(recommendationsitetypeadapter);
   recommendationsitetype
	.setOnItemSelectedListener(new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent,
				View view, int position, long id) {
			db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "recommendationsitetype",sitetypelist.get(position));
			 addsitespecificrecommendations(sitetypelist.get(position));
			 if(((TextView)view).getText().toString().equals("Grocery Store")){
				 setgrocerystorelisteners();
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			showToast("Spinner1: unselected");
		}

	});	
	
	
}

	public void addtextchangelistener(final AutoCompleteTextView textMessage,
			final AutoCompleteTextView[] sequence, final double[] doub) {

		textMessage.setFocusableInTouchMode(false);
		textMessage.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				showcalcbutton(v);

				int seqnum = getsequencenumber(sequence, textMessage)[0];

				textMessage.setFocusableInTouchMode(true);
				textMessage.requestFocus();
				textMessage.setText(String.valueOf(doub[seqnum]));
				textMessage.selectAll();
				textMessage.setFocusableInTouchMode(false);
			}

		});
		textMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					int seqnum = getsequencenumber(sequence, textMessage)[0];
					doub[seqnum] = u.gttd(textMessage);
					displaydoubles();
					removecalcorbutton(hasFocus);

					if (sequence == cons) {
						if (CONSUMPTIONTABLOADED) {
							try {
								db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
										"consumptionkwh" + seqnum,
										u.sd(consdoub[seqnum]));
							} catch (Throwable e) {
								
							}
						}

					}
					if (sequence == consloc) {
						if (CONSUMPTIONTABLOADED) {
							try {
								db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
										"consumptionlocalcurrency" + seqnum,
										u.sd(conslocdoub[seqnum]));
							} catch (Throwable e) {
								
							}
						}
					}
					if (sequence == recommendationeaskwhAutoCompleteTextView) {
						int pos = getsequencenumber(
								recommendationrecommendationsAutoCompleteTextView, v)[0];
						if (RECOMMENDATIONSTABLOADED) {
							db.addorupdate(DatabaseHandler.TABLE_RECOMMENDATIONS,
									"recommenationseaskwh" + u.s(pos),
									((TextView) v).getText().toString());

						}

					}

				}
			}
		});
	}

	public void addconversiontextchangelistener(final int num, final String type) {

		if (type.equals("currency")) {
			recommendationestimatedcoiAutoCompleteTextView1[num]
					.setFocusableInTouchMode(false);
			recommendationestimatedcoiAutoCompleteTextView2[num]
					.setFocusableInTouchMode(false);

			recommendationestimatedcoiAutoCompleteTextView1[num]
					.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
							showcalcbuttoncoi(v);

							recommendationestimatedcoiAutoCompleteTextView1[num]
									.setFocusableInTouchMode(true);
							recommendationestimatedcoiAutoCompleteTextView1[num]
									.requestFocus();
							recommendationestimatedcoiAutoCompleteTextView1[num].setText(String
									.valueOf(recommendationestimatedcoidoub1[num]));
							recommendationestimatedcoiAutoCompleteTextView1[num]
									.selectAll();
							recommendationestimatedcoiAutoCompleteTextView1[num]
									.setFocusableInTouchMode(false);

						}
					});
			recommendationestimatedcoiAutoCompleteTextView2[num]
					.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {

							showcalcbuttoncoi(v);

							recommendationestimatedcoiAutoCompleteTextView2[num]
									.setFocusableInTouchMode(true);
							recommendationestimatedcoiAutoCompleteTextView2[num]
									.requestFocus();
							recommendationestimatedcoiAutoCompleteTextView2[num].setText(String
									.valueOf(recommendationestimatedcoidoub2[num]));
							recommendationestimatedcoiAutoCompleteTextView2[num]
									.selectAll();
							recommendationestimatedcoiAutoCompleteTextView2[num]
									.setFocusableInTouchMode(false);
						}
					});

			recommendationestimatedcoiAutoCompleteTextView1[num]
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {

							String checkforeign = recommendationestimatedcoiAutoCompleteTextView1[num]
									.getText().toString();
							if (checkforeign.lastIndexOf(",") > checkforeign
									.lastIndexOf(".")) {

								checkforeign = checkforeign.replace(",", "*");
								checkforeign = checkforeign.replace(".", "@");
								checkforeign = checkforeign.replace("*", ".");
								checkforeign = checkforeign.replace("@", ",");
							}
							recommendationestimatedcoidoub1[num] = u
									.d(checkforeign.replaceAll("[^\\d.]", ""));
							recommendationestimatedcoidoub2[num] = recommendationestimatedcoidoub1[num]
									* currencyratiodoub;

							recommendationestimatedroidoub[num] = recommendationestimatedcoidoub1[num]
									/ recommendationeascurrencydoub1[num] * 12;

							displaydoubles();
							removecalcorbutton(hasFocus);
						}
					});

			recommendationestimatedcoiAutoCompleteTextView2[num]
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {

							String checkforeign = recommendationestimatedcoiAutoCompleteTextView2[num]
									.getText().toString();

							if (checkforeign.lastIndexOf(",") > checkforeign
									.lastIndexOf(".")) {
								checkforeign.replace(",", "*");
								checkforeign.replace(".", "@");
								checkforeign.replace("*", ".");
								checkforeign.replace("@", ",");
							}
							recommendationestimatedcoidoub2[num] = u
									.d(checkforeign.replaceAll("[^\\d.]", ""));
							recommendationestimatedcoidoub1[num] = recommendationestimatedcoidoub2[num]
									/ currencyratiodoub;

							displaydoubles();
							removecalcorbutton(hasFocus);
						}
					});
		}
		if (type.equals("area")) {

			sitesize[num].et1.setFocusableInTouchMode(false);
			sitesize[num].et2.setFocusableInTouchMode(false);
			sitesize[num].et1.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					showcalcbutton(v);
					u.log("num="+num+" "+String.valueOf(sitesize[num].doub1));
					sitesize[num].et1.setFocusableInTouchMode(true);
					sitesize[num].et1.setText(String
							.valueOf(sitesize[num].doub1));

					sitesize[num].et1.requestFocus();
					sitesize[num].et1.selectAll();

					sitesize[num].et1.setFocusableInTouchMode(false);

				}
			});
			sitesize[num].et2.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					showcalcbutton(v);
					u.log("num="+num+" "+String.valueOf(sitesize[num].doub2));
					sitesize[num].et2.setFocusableInTouchMode(true);
					sitesize[num].et2.setText(String
							.valueOf(sitesize[num].doub2));

					sitesize[num].et2.requestFocus();
					sitesize[num].et2.selectAll();

					sitesize[num].et2.setFocusableInTouchMode(false);

				}
			});

			sitesize[num].et1
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {
							
							u.log("num="+num+" "+String.valueOf(sitesize[num].doub1));
							
							sitesize[num].doub1 = u.d(sitesize[num].et1
									.getText().toString()
									.replaceAll("[^\\d.]", ""));
							sitesize[num].doub2 = sitesize[num].doub1 * mtfc;

							displaydoubles();
							removecalcorbutton(hasFocus);

							try{
								String Table=DatabaseHandler.TABLE_SITEINFO;
								String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
								StorageKey="sitesizem";
								String value=String.valueOf(sitesize[num].doub1);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}
							try{
								String Table=DatabaseHandler.TABLE_SITEINFO;
								String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
								StorageKey="sitesizeft";
								String value=String.valueOf(sitesize[num].doub2);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}
							
							
						}
					});

			sitesize[num].et2
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {
							u.log("num="+num+" "+String.valueOf(sitesize[num].doub2));
							sitesize[num].doub2 = u.d(sitesize[num].et2
									.getText().toString()
									.replaceAll("[^\\d.]", ""));
							sitesize[num].doub1 = sitesize[num].doub2 / mtfc;

							displaydoubles();
							removecalcorbutton(hasFocus);
							try{
								String Table=DatabaseHandler.TABLE_SITEINFO;
								String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
								StorageKey="sitesizem";
								String value=String.valueOf(sitesize[num].doub1);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}
							try{
								String Table=DatabaseHandler.TABLE_SITEINFO;
								String[] Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
								StorageKey="sitesizeft";
								String value=String.valueOf(sitesize[num].doub2);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}
							
							
						}
					});
		}
	}

	public void addconversiontextchangelistenerarea(final int num,
			final AutoCompleteTextView meters, final AutoCompleteTextView feet) {

		ViewHolderArea[0][num] = meters;
		ViewHolderArea[1][num] = feet;

		ViewHolderArea[0][num].setFocusableInTouchMode(false);
		ViewHolderArea[1][num].setFocusableInTouchMode(false);

		ViewHolderArea[0][num].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showcalcbutton(v);

				((AutoCompleteTextView) ViewHolderArea[0][num])
						.setFocusableInTouchMode(true);
				((AutoCompleteTextView) ViewHolderArea[0][num])
						.setText(((AutoCompleteTextView) ViewHolderArea[0][num]).getText()
								.toString());

				((AutoCompleteTextView) ViewHolderArea[0][num]).requestFocus();
				((AutoCompleteTextView) ViewHolderArea[0][num]).selectAll();

				((AutoCompleteTextView) ViewHolderArea[0][num])
						.setFocusableInTouchMode(false);

			}
		});
		ViewHolderArea[1][num].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showcalcbutton(v);

				((AutoCompleteTextView) ViewHolderArea[1][num])
						.setFocusableInTouchMode(true);
				((AutoCompleteTextView) ViewHolderArea[1][num])
						.setText(((AutoCompleteTextView) ViewHolderArea[1][num]).getText()
								.toString());

				((AutoCompleteTextView) ViewHolderArea[1][num]).requestFocus();
				((AutoCompleteTextView) ViewHolderArea[1][num]).selectAll();

				((AutoCompleteTextView) ViewHolderArea[1][num])
						.setFocusableInTouchMode(false);
			}
		});

		ViewHolderArea[0][num]
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {

						servicearea[num].doub1 = u
								.d(((AutoCompleteTextView) ViewHolderArea[0][num])
										.getText().toString()
										.replaceAll("[^\\d.]", ""));
						servicearea[num].doub2 = servicearea[num].doub1 * mtfc;

						displaydoubles();
						removecalcorbutton(hasFocus);

						if (!hasFocus) {
							try{
								String Table=db.TABLE_ASSETINFO;
								String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
								StorageKey="assetserviceaream";
								String value=u.sd(servicearea[num].doub1);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}	
							try{
								String Table=db.TABLE_ASSETINFO;
								String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
								StorageKey="assetserviceareaft";
								String value=u.sd(servicearea[num].doub2);
								int row=num;
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}	
							
						}
					}
				});

		ViewHolderArea[1][num]
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {

						servicearea[num].doub2 = u
								.d(((AutoCompleteTextView) ViewHolderArea[1][num])
										.getText().toString()
										.replaceAll("[^\\d.]", ""));
						servicearea[num].doub1 = servicearea[num].doub2 / mtfc;

						displaydoubles();
						removecalcorbutton(hasFocus);
						if (!hasFocus) {
							
							try{
								String Table=db.TABLE_ASSETINFO;
								String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
								StorageKey="assetserviceaream";
								String value=u.sd(servicearea[num].doub1);
								int row=(i - 1);
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}	
							try{
								String Table=db.TABLE_ASSETINFO;
								String[] Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
								StorageKey="assetserviceareaft";
								String value=u.sd(servicearea[num].doub2);
								int row=(i - 1);
								int column=db.getcolumnnumberbytitle(Table,StorageKey);
								db.addorupdatemulticolumn(Table, row, column , value , Key);
							}catch(Throwable e){
								e.printStackTrace();
							}	
							
					
						}
					}
				});

	}

	public void addtvconversiontextchangelistener(final TextView[] et1,
			final TextView[] et2, final int num, final double[] d1,
			final double[] d2, final double ratio) {

		et1[num].setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				d1[num] = u.d(et1[num].getText().toString()
						.replaceAll("[^\\d.]", ""));
				d2[num] = u.d(et1[num].getText().toString()
						.replaceAll("[^\\d.]", ""))
						* ratio;

				if (!(d1[num] == 0)) {
					displaydoubles();
				}
			}
		});

		et2[num].setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				d2[num] = u.d(et2[num].getText().toString()
						.replaceAll("[^\\d.]", ""));
				d1[num] = u.d(et2[num].getText().toString()
						.replaceAll("[^\\d.]", ""))
						/ ratio;

				if (!(d2[num] == 0)) {
					displaydoubles();
				}
			}
		});
	}

	public void coicalcdialog() {

		final Dialog d = new Dialog(this);
		d.setTitle("Cost of Implementation Calculator");
		d.setContentView(R.layout.coicalc);

		final Spinner suspinner = (Spinner) d.findViewById(R.id.suspinner);
		final AutoCompleteTextView coutbiet = (AutoCompleteTextView) d.findViewById(R.id.coutbiet);
		final Spinner coutbiunit = (Spinner) d.findViewById(R.id.coutbiunit);
		final AutoCompleteTextView acb1uet = (AutoCompleteTextView) d.findViewById(R.id.acb1uet);
		final Spinner acb1uunit = (Spinner) d.findViewById(R.id.acb1uunit);
		final TextView calccoitv = (TextView) d.findViewById(R.id.calccoitv);
		final TextView soltv = (TextView) d.findViewById(R.id.soltv);
		final Spinner louisp = (Spinner) d.findViewById(R.id.louisp);

		d
				.findViewById(R.id.coicalclayout);

		// su=select unit
		// coutbi=cost of unit to be installed
		// acb1u=area coveredby1unit
		// loui=location of unit installation
		// sol=size of location (m²)
		// ccoi=calculated coi

		List<String> coicalcunits = new ArrayList<String>();
		coicalcunits.add("new");
		coicalcunits.add("occupancy sensor");
		coicalcunits.add("luminocity sensor");

		ArrayAdapter<String> suadapter = (new ArrayAdapter<String>(this,
				R.layout.spinnertextview, coicalcunits));
		suadapter.setDropDownViewResource(R.layout.largespinnertextview);

		suspinner.setAdapter(suadapter);
		suspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				((TextView) arg1).getText().toString();

				if (suspinner.getSelectedItem().equals("new")) {
					acb1uet.setText("");
					coutbiet.setText("");
				}
				if (suspinner.getSelectedItem().equals("occupancy sensor")) {
					acb1uet.setText("600");
					coutbiet.setText("16");
					acb1u = 6;
				}
				if (suspinner.getSelectedItem().equals("luminocity sensor")) {
					acb1uet.setText("500");
					coutbiet.setText("15");
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		List<String> currencylist = new ArrayList<String>();
		currencylist.add(Currency.getInstance(secondlocale).getSymbol());
		currencylist.add(Currency.getInstance(locale).getSymbol());

		ArrayAdapter<String> adapter = (new ArrayAdapter<String>(this,
				R.layout.spinnertextview, currencylist));
		adapter.setDropDownViewResource(R.layout.largespinnertextview);

		coutbiunit.setAdapter(adapter);

		List<String> sizelist = new ArrayList<String>();
		sizelist.add("m²");
		sizelist.add("ft²");

		ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(this,
				R.layout.spinnertextview, sizelist));
		adapter1.setDropDownViewResource(R.layout.largespinnertextview);

		acb1uunit.setAdapter(adapter1);

		acb1uunit.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				if (acb1uunit.getSelectedItem().toString().equals("m²")) {
					soltv.setText(" = " + sol + "m²");
				} else {
					soltv.setText(" = " + sol + "ft²");
				}

				// Do Something
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		ArrayAdapter<String> adapter2 = (new ArrayAdapter<String>(this,
				R.layout.spinnertextview, locationslist));
		adapter2.setDropDownViewResource(R.layout.largespinnertextview);
		louisp.setAdapter(adapter2);

		louisp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				if (acb1uunit.getSelectedItem().toString().equals("m²")) {
					sol = sitesize[position].doub1;
					soltv.setText(" = " + sol + "m²");
				} else {
					sol = sitesize[position].doub2;
					soltv.setText(" = " + sol + "ft²");
				}

				// Do Something
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		Button calccoibutton = (Button) d.findViewById(R.id.calccoibutton);
		calccoibutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				coutbi = u.d(coutbiet.getText().toString());
				if (acb1uunit.getSelectedItem().toString().equals("m²")) {
					acb1u = u.d(acb1uet.getText().toString());
				} else {
					acb1u = u.d(acb1uet.getText().toString()) * mtfc;
				}
				if (coutbiunit.getSelectedItem().toString()
						.equals(Currency.getInstance(secondlocale).getSymbol())) {
					coutbi = u.d(coutbiet.getText().toString());
				} else {
					coutbi = u.d(coutbiet.getText().toString())
							* currencyratiodoub;
				}

				calccoidoub = coutbi * sol / acb1u;

				if (coutbiunit.getSelectedItem().toString()
						.equals(Currency.getInstance(secondlocale).getSymbol())) {
					calccoitv.setText(displayCurrency(secondlocale, u.d(u
							.sd((double) Math.round(calccoidoub * 100) / 100))));

				} else {
					calccoitv.setText(displayCurrency(locale, u.d(u
							.sd((double) Math.round(calccoidoub * 100) / 100))));
				}

				calccoitv.setTypeface(null, Typeface.BOLD_ITALIC);

			}
		});

		Button coicalccancel = (Button) d.findViewById(R.id.coicalccancel);
		coicalccancel.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {

				d.cancel();

			}
		});
		Button coicalcok = (Button) d.findViewById(R.id.coicalcok);
		coicalcok.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {

				d.dismiss();
				View v = getCurrentFocus();
				((AutoCompleteTextView) v).setText(displayCurrency(locale,
						u.d(u.sd((double) Math.round(calccoidoub * 100) / 100))));

			}
		});

		d.show();

	}

	public void addmonthfilebuttonlistener(final ImageView button,
			final int month, final View noblanktextview) {
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				typeforgetpicture = CONSUMPTIONTAB;
				if (!(noblanktextview == null)) {
					if (((TextView) noblanktextview).getText().toString()
							.equals("")) {
						if (!(noblanktextview == null)) {

							fieldcantbeleftblankdialog(noblanktextview);
						}
					} else {
						monthneworopendialog(month);
					}
				} else {
					monthneworopendialog(month);
				}
			}
		});
	}

	public void monthneworopendialog(final int month) {

		ImageView explore = new ImageView(this);
		explore.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		explore.setPadding(25, 25, 25, 25);
		explore.setImageResource(R.drawable.hiresopen);
		explore.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {

				pd.cancel();
				Intent intent = u.intent("AndroidExplorerGivenFolder");
				String foldername = buildfoldernamestring(CONSUMPTIONTAB, month);
				intent.putExtra("folder", foldername);
				startActivityForResult(intent, SELECTFILETOOPEN);

			}
		});

		ImageView newfile = new ImageView(this);
		newfile.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		newfile.setPadding(25, 25, 25, 25);
		newfile.setImageResource(R.drawable.hiresnew);
		newfile.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				// startactivityforgettingfile
				pd.cancel();
				pictureorfiledialog(CONSUMPTIONTAB, month);

			}

		});

		LinearLayout choosepicturelocationlayout;
		choosepicturelocationlayout = new LinearLayout(this);
		choosepicturelocationlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		choosepicturelocationlayout.setOrientation(LinearLayout.HORIZONTAL);
		choosepicturelocationlayout.addView(newfile);
		choosepicturelocationlayout.addView(explore);
		choosepicturelocationlayout.setGravity(Gravity.CENTER_HORIZONTAL);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("What would you like to do?")
				.setMessage("Add New File, or Open an existing file")
				.setView(choosepicturelocationlayout)
				.setIcon(R.drawable.ic_launcher)
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();
							}
						}).setCancelable(true);

		pd = builder.create();
		pd.show();

	}

	public void addmonthsbuttonlistener(final TextView textMessage,
			final TextView[] sequence) {
		textMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				datedialog(textMessage, sequence);
			}
		});
	}

	public void datedialog(final TextView textMessage, final TextView[] sequence) {
		TextView checkboxcaption = new TextView(this);
		checkboxcaption.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		checkboxcaption.setText("Check box to auto-fill all dates");
		final CheckBox fillrest;
		fillrest = new CheckBox(this);
		fillrest.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		final Spinner months;
		months = new Spinner(this);
		months.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		months.setAdapter(monthsadapter);
		months.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				// Do Something
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}
		});

		final Spinner year;
		year = new Spinner(this);
		year.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		year.setAdapter(yearadapter);
		year.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// Do Something
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner1: unselected");
			}
		});
		LinearLayout monthyearselectlayout;
		monthyearselectlayout = new LinearLayout(this);
		monthyearselectlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		monthyearselectlayout.setOrientation(LinearLayout.HORIZONTAL);
		monthyearselectlayout.addView(months);
		monthyearselectlayout.addView(year);
		// monthyearselectlayout.addView(fillrest);
		monthyearselectlayout.setGravity(Gravity.CENTER_HORIZONTAL);

		LinearLayout checkboxlayout;
		checkboxlayout = new LinearLayout(this);
		checkboxlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		checkboxlayout.setOrientation(LinearLayout.HORIZONTAL);

		checkboxlayout.addView(checkboxcaption);
		checkboxlayout.addView(fillrest);

		LinearLayout totallayout;
		totallayout = new LinearLayout(this);
		totallayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		totallayout.setOrientation(LinearLayout.VERTICAL);
		totallayout.setGravity(Gravity.CENTER_HORIZONTAL);

		totallayout.addView(monthyearselectlayout);
		totallayout.addView(checkboxlayout);

		String date = textMessage.getText().toString();
		if (!date.equals("") && !date.contains("*")) {
			String[] dateparts = date.split("[\\-\\.]+");
			months.setSelection(getIndexofSpinner(months, dateparts[0]));
			year.setSelection(getIndexofSpinner(year, dateparts[1]));
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Date")
				.setMessage("Please Choose Month and Year")
				.setView(totallayout)
				.setCancelable(false)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (fillrest.isChecked()) {
							int indexofboxselected = getsequencenumber(
									sequence, textMessage)[0];
							int monthindex = getIndexofSpinner(months, months
									.getSelectedItem().toString());
							int yearindex = getIndexofSpinner(year, year
									.getSelectedItem().toString());
							int i = indexofboxselected;
							int m = monthindex - i;
							int y = yearindex;
							for (int num = 0; num <= 11; num++) {

								if (m == 12) {
									m = 0;
									y = y + 1;
								}
								if (y == 6) {
									y = y - 6;
								}
								String monthyeartext = months
										.getItemAtPosition(m).toString()
										+ "-"
										+ year.getItemAtPosition(y).toString();
								sequence[num].setText(monthyeartext);
								if (CONSUMPTIONTABLOADED) {
									try {

										db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
												"consumptiondate" + u.s(num),
												monthyeartext);
									} catch (Throwable e) {
										
									}
								}
								m++;
							}
						} else {

							textMessage.setText(months.getSelectedItem()
									.toString()
									+ "-"
									+ year.getSelectedItem().toString());

						}
					}
				})
				.setNeutralButton("MISSING",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								textMessage.setText(" * Unavailable");
								int[] z = getsequencenumber(month, textMessage);
								
								int monthsavailable = 0;
								double constot = 0;
								double consloctot = 0;
								for (int i = 0; i <= 11; i++) {
									if (!month[i].getText().toString()
											.contains("*")) {

										constot = consdoub[i] + constot;
										consloctot = conslocdoub[i]
												+ consloctot;
										monthsavailable++;
									}
								}
								
								
								
								dialog.dismiss();

								cons[z[0]].requestFocus();
								consdoub[z[0]] = constot
										/ ((double) monthsavailable);
								cons[z[0]].setText(u.sd(consdoub[z[0]]));

								consloc[z[0]].requestFocus();
								conslocdoub[z[0]] = consloctot
										/ ((double) monthsavailable);
								consloc[z[0]].setText(u.sd((double) Math
										.round(conslocdoub[z[0]] * 100) / 100));

								consusddoub[z[0]] = conslocdoub[z[0]]
										* currencyratiodoub;
								consloc[z[0]].setText(displayNumber(
										secondlocale,
										u.d(u.sd((double) Math
												.round(consusddoub[z[0]] * 100) / 100))));

								displaydoubles();
							}
						})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		builder.create().show();
	}

	public void addnewvaluetolistdialogmulti(final Spinner sp,
			final int listid, final ArrayAdapter<String> adapter,
			final Spinner sp1, final int listid1,
			final ArrayAdapter<String> adapter1, final AutoCompleteTextView et2,
			final int listid2, final List<String> adapter2, final AutoCompleteTextView et3,
			final int listid3, final List<String> adapter3) {

		final AutoCompleteTextView et = new AutoCompleteTextView(this);
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout valueet;
		valueet = new LinearLayout(this);
		valueet.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		valueet.setOrientation(LinearLayout.HORIZONTAL);

		valueet.addView(et);
		valueet.setGravity(Gravity.CENTER_HORIZONTAL);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add New Value To List")
				.setMessage("Please Enter a Value")
				.setView(valueet)
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String value = et.getText().toString();
						addnewvaluetoexcellists(FIRSTSHEET,listid, value, MASTER_LIST);
						addnewvaluetoexcellists(FIRSTSHEET,listid1, value, MASTER_LIST);
						addnewvaluetoexcellists(FIRSTSHEET,listid2, "   ", MASTER_LIST);
						addnewvaluetoexcellists(FIRSTSHEET,listid3, "   ", MASTER_LIST);

						adapter.remove("new");
						adapter1.remove("new");
						adapter2.remove("new");
						adapter3.remove("new");

						adapter.add(value);
						adapter1.add(value);
						adapter2.add("   ");
						adapter3.add("   ");

						adapter.add("new");
						adapter1.add("new");
						adapter2.add("new");
						adapter3.add("new");

						sp.setAdapter(adapter);
						sp1.setAdapter(adapter1);

						sp.setSelection(getIndexofSpinner(sp, value));
						sp1.setSelection(getIndexofSpinner(sp1, value));
						et2.setText("   ");
						et3.setText("   ");

						dialog.dismiss();
					}
				})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

							}
						});

		builder.create().show();
	}

	public void addnewvaluetoengineerlistdialog(final Spinner sp,
			final ArrayAdapter<String> adapter, final int siteormaster) {

		LayoutParams smalllp = new LayoutParams(0, LayoutParams.MATCH_PARENT,
				1f);
		LayoutParams largelp = new LayoutParams(0, LayoutParams.MATCH_PARENT,
				3f);

		final AutoCompleteTextView etname = new AutoCompleteTextView(this);
		etname.setLayoutParams(largelp);
		final AutoCompleteTextView etemail = new AutoCompleteTextView(this);
		etemail.setLayoutParams(largelp);
		final AutoCompleteTextView etnumber = new AutoCompleteTextView(this);
		etnumber.setLayoutParams(largelp);

		final TextView tvname = new TextView(this);
		tvname.setLayoutParams(smalllp);
		tvname.setPadding(2, 0, 0, 0);
		tvname.setText("Name:");

		final TextView tvemail = new TextView(this);
		tvemail.setLayoutParams(smalllp);
		tvemail.setPadding(2, 0, 0, 0);
		tvemail.setText("Email:");

		final TextView tvnumber = new TextView(this);
		tvnumber.setLayoutParams(smalllp);
		tvnumber.setPadding(2, 0, 0, 0);
		tvnumber.setText("Phone Number:");

		TableRow rowname;
		rowname = new TableRow(this);
		rowname.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		rowname.addView(tvname);
		rowname.addView(etname);

		TableRow rowemail;
		rowemail = new TableRow(this);
		rowemail.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		rowemail.addView(tvemail);
		rowemail.addView(etemail);

		TableRow rowphonenumber;
		rowphonenumber = new TableRow(this);
		rowphonenumber.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rowphonenumber.addView(tvnumber);
		rowphonenumber.addView(etnumber);

		TableLayout valuesll;
		valuesll = new TableLayout(this);
		valuesll.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		valuesll.addView(rowname);
		valuesll.addView(rowemail);
		valuesll.addView(rowphonenumber);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add New Engineer")
				.setMessage("Please Enter Values")
				.setView(valuesll)
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						String name = etname.getText().toString();
						String email = etemail.getText().toString();
						String number = etnumber.getText().toString();

						if (!(name.length() == 0)) {
							addnewvaluetoexcellists(FIRSTSHEET,ENGINEERNAME, name,
									siteormaster);
							adapter.remove("new");
							adapter.add(name);
							adapter.add("new");
							sp.setAdapter(adapter);
							int selection=getIndexofSpinner(sp, name);
							sp.setSelection(selection);
							set("engineernameposition",u.s(selection));

							addnewvaluetoexcellistsgivenposition(ENGINEEREMAIL,
									email, siteormaster, adapter.getCount() - 2);
							engineeremaillist.remove("new");
							engineeremaillist.add(email);
							engineeremaillist.add("new");

							addnewvaluetoexcellistsgivenposition(
									ENGINEERPHONENUMBER, number, siteormaster,
									adapter.getCount() - 2);
							engineerphonenumberlist.remove("new");
							engineerphonenumberlist.add(number);
							engineerphonenumberlist.add("new");

							engineeremailtv.setText(email);
							engineerphonenumtv.setText(number);
							
							

							dialog.dismiss();
						} else {
							engineernamesp.setSelection(0);
							showToast("Some Fields Are Left Blank");
						}

					}
				})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		builder.create().show();
	}

	public void addnewvaluetolistdialog(final Spinner sp, final int listid,
			final ArrayAdapter<String> adapter, final int siteormaster) {
		final AutoCompleteTextView et = new AutoCompleteTextView(this);
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout valueet;
		valueet = new LinearLayout(this);
		valueet.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		valueet.setOrientation(LinearLayout.HORIZONTAL);

		valueet.addView(et);
		valueet.setGravity(Gravity.CENTER_HORIZONTAL);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add New Value To List")
				.setMessage("Please Enter a Value")
				.setView(valueet)
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String value = et.getText().toString();
						addnewvaluetoexcellists(FIRSTSHEET,listid, value, siteormaster);
						adapter.remove("new");
						adapter.add(value);
						adapter.add("new");
						sp.setAdapter(adapter);
						sp.setSelection(getIndexofSpinner(sp, value));
						dialog.dismiss();
					}
				})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		builder.create().show();
	}

	public void pictureorfiledialog(final int type, final int row) {
		
		SetTypeAndNumber(type, row);

		fromfolder = new ImageView(this);
		fromfolder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		fromfolder.setPadding(25, 25, 25, 25);
		fromfolder.setImageResource(R.drawable.folder_256);
		fromfolder.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				
				typeforgetpicture = type;
				numberselected = u.s(row);

				if (typeselected==FLOORPLANTAB) {
					

					new File(inputfloorplandirectory).mkdirs();
					getstringdialog("Please Name This Floor Plan",
							GETPICFROMFILE);
				}else{
					
					Intent intent = new Intent();
					if(Build.VERSION.SDK_INT >= 19){
						intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
					}
					intent.setAction(Intent.ACTION_GET_CONTENT);
					intent.setType("*/*");
				
					startActivityForResult(Intent.createChooser(intent,"Select Picture"),
							GETPICFROMFILE);
					
				}
				
				pd.cancel();


			}
		});

		fromcamera = new ImageView(this);
		fromcamera.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		fromcamera.setPadding(25, 25, 25, 25);
		fromcamera.setImageResource(R.drawable.digital_camera);
		fromcamera.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				String table = null;
				ArrayList<String> dbnames = null;
				String item = null;
				String[] Key = null;
				switch(type){
				case(COMPONENTSTAB):
					Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
					StorageKey="componentname";
					table=DatabaseHandler.TABLE_COMPONENTINFO;
					item=componentname[u.i(numberselected)].et.getText().toString();
					SCROLPOSX=tab1scrollview.getScrollX();
					SCROLPOSY=tab1scrollview.getScrollY();
					
				break;
				case(ASSETSTAB):
					Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
					StorageKey="assetname";
					table=DatabaseHandler.TABLE_ASSETINFO;
					item=assetname[u.i(numberselected)].et.getText().toString();
					SCROLPOSX=tab2scrollview.getScrollX();
					SCROLPOSY=tab2scrollview.getScrollY();

				break;
				case(SITETAB):
					Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
					StorageKey="sitename";
					table=DatabaseHandler.TABLE_SITEINFO;	
					item=sitename[u.i(numberselected)].et.getText().toString();
					SCROLPOSX=tab0scrollview.getScrollX();
					SCROLPOSY=tab0scrollview.getScrollY();
				break;
				}
				if(!(type==FLOORPLANTAB)){
					try{
						String value=item;
						int row=u.i(numberselected);
						int column=db.getcolumnnumberbytitle(table,StorageKey);
						db.addorupdatemulticolumn(table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
				
				}
				try {
					dispatchTakePictureIntent(GETPICFROMCAMERA);

					pd.cancel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
				}

			}

		});

		LinearLayout choosepicturelocationlayout;
		choosepicturelocationlayout = new LinearLayout(this);
		choosepicturelocationlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		choosepicturelocationlayout.setOrientation(LinearLayout.HORIZONTAL);
		choosepicturelocationlayout.addView(fromfolder);
		choosepicturelocationlayout.addView(fromcamera);
		choosepicturelocationlayout.setGravity(Gravity.CENTER_HORIZONTAL);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Picture")
				.setMessage(
						"Add a picture of file from external source, or add a picture from the camera")
				.setView(choosepicturelocationlayout)
				.setCancelable(false)
				.setIcon(R.drawable.ic_launcher)

				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).setCancelable(true);

		pd = builder.create();
		pd.show();

	}

//	public void removelocation(int k) {
//
//		BitmapFactory.Options o = new BitmapFactory.Options();
//		o.inSampleSize = PICTURESAMPLESIZE;
//		for (int l = k; l <= s; l++) {
//
//			String nexttype = null;
//			String currenttype = null;
//
//			try {
//				currenttype = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO,
//						"sitetype" + u.s(l));
//			} catch (Throwable e) {
//
//			}
//
//			try {
//				nexttype = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO,
//						"sitetype" + u.s(l + 1));
//			} catch (Throwable e) {
//
//			}
//
//			try {
//				db.updateValue(DatabaseHandler.TABLE_SITEINFO, "sitetype" + u.s(l), nexttype);
//			} catch (Throwable e) {
//				
//			}
//			try {
//				db.updateValue(DatabaseHandler.TABLE_SITEINFO, "sitetype" + u.s(l + 1),
//						currenttype);
//			} catch (Throwable e) {
//				
//			}
//
//			try {
//				if (!(sitename[l + 1].label == null)) {
//					sitename[l].et.setText(sitename[l + 1].et.getText());
//					sitename[l].et.requestFocus();
//					sitesize[l].et1.setText(sitesize[l + 1].et1.getText());
//					sitesize[l].et1.requestFocus();
//					sitesize[l].et2.setText(sitesize[l + 1].et2.getText());
//					sitesize[l].et2.requestFocus();
//					sitedescription[l].et.setText(sitedescription[l + 1].et
//							.getText());
//					sitedescription[l].et.requestFocus();
//
//					for (int num = 0; num <= sitepicnum[l]; num++) {
//
//						try {
//							sitepicture[l][num]
//									.setImageBitmap(sitepicture[l + 1][num]
//											.getDrawingCache());
//							sitepicture[l + 1][num].setImageBitmap(null);
//						} catch (Throwable e) {
//							
//						}
//
//					}
//
//					// Delete pictures to make rows equal
//					/*
//					 * 
//					 * while (sitepicnum[l + 1] <= sitepicnum[l]) { //
//					 * tr.removeViewAt(tr.getChildCount()-1);
//					 * 
//					 * File file = new File(buildimagenamestring("site", l,
//					 * sitepicnum[l], null)); file.delete(); sitepicnum[l]--; //
//					 * ds }
//					 * 
//					 * // now rows are equal // rename files of second row to
//					 * names of files in first row
//					 *  num
//					 * <= sitepicnum[l + 1]; num++) { int q = l + 1; File second
//					 * = new File(buildimagenamestring("site", q, num, null));
//					 * Log.d("String of second row",buildimagenamestring("site",
//					 * q, num, null)); File first = new
//					 * File(buildimagenamestring("site", l, num, null));
//					 * Log.d("String of first row",buildimagenamestring("site",
//					 * l, num, null));
//					 * 
//					 * first.delete(); second.renameTo(first);
//					 * 
//					 * if (first.exists()) {
//					 * sitepicture[l][num].setImageBitmap(Bitmap
//					 * .createBitmap(BitmapFactory.decodeFile( first.toString(),
//					 * o))); } else {
//					 * sitepicture[l][num].setImageDrawable(null); }
//					 * sitepicture[l + 1][num].setImageBitmap(null); }
//					 */
//				}
//			} catch (NullPointerException e) {
//				
//			}
//		}
//		try {
//			locationslist.remove(k);
//		} catch (IndexOutOfBoundsException e) {
//
//		}
//		smalllocationspinneradapter.notifyDataSetChanged();
//		locationspinneradapter.notifyDataSetChanged();
//		locationcontainer.removeView(buildingtable[s - 1]);
//
//		s--;
//	}

	

//	public void duplicate(final int k, TableLayout[] tl) {
////
////		int var = 0;
////		final int type = gettype(tl[k]);
////
////		if (type==SITETAB) {
////			addlocation(BUILDING);
////			var = s - 1;
////		}
////		if (type==ASSETSTAB) {
////			makenewasset();
////			var = i - 1;
////		}
////		if (type==RECOMMENDATIONSTAB) {
////			addrecommendation();
////			var = r - 1;
////		}
////
////		for (int num = var - 1; num > k; num--) {
////			
////			movedown(num, tl, true);
////		}
////
////		List<View> current = u.getallchildrenwithspinners(tl[k]);
////		List<View> next = u.getallchildrenwithspinners(tl[k + 1]);
////
////		try {
////			for (int num = 0; num < current.size(); num++) {
////				final View currentview = current.get(num);
////				final View nextview = next.get(num);
////				u.settexttoview(u.gettextfromview(currentview), nextview);
////				siteauditdoubfind(nextview);
////				nextview.requestFocus();
////			}
////		} catch (IndexOutOfBoundsException e) {
////			
////		}
////
////		int storagenamestring = -1;
////		String storagevariable = null;
////		String[] foldernames = null;
////		if (type==SITETAB) {
////			storagenamestring = SITETAB;
////			storagevariable = "s";
////			foldernames = u.listfolders(ProjectDirectory + "/"
////					+ basedirectory[SITEINFOFOLDER]);
////		}
////		if (type==ASSETSTAB) {
////			storagenamestring = ASSETSTAB;
////			storagevariable = "c";
////			foldernames = u.listfolders(ProjectDirectory + "/"
////					+ basedirectory[ASSETSFOLDER]);
////
////		}
////
////		int num = 0;
////		int count = countpics(type, k);
////		int nextcount = countpics(type, k + 1);
////		if (count >= nextcount) {
////			num = count - 1;
////		} else {
////			num = nextcount - 1;
////		}
////
////		// Copy Folder
////
////		getfolderandrenameloop: for (int i = 0; i < foldernames.length; i++) {
////			
////			if (foldernames[i].contains("_" + storagevariable + "_" + u.s(k)
////					+ "_")) {
////				Log.d("folder A" + foldernames[i] + " has ", "contains " + "_"
////						+ storagevariable + "_" + u.s(k) + "_");
////				new File(foldernames[i]);
////				if (type==ASSETSTAB) {
////					File newfile = new File(foldernames[i].replace("_" + k
////							+ "_", "_" + (k + 1) + "_"));
////					Log.d("oldname,newname",
////							foldernames[i]
////									+ ","
////									+ foldernames[i].replace("_" + k + "_", "_"
////											+ (k + 1) + "_"));
////					newfile.mkdirs();
////
////				}
////				if (type==SITETAB) {
////					File newfile = new File(foldernames[i].replace("_" + k
////							+ "_", "_" + (k + 1) + "_"));
////					Log.d("oldname,newname",
////							foldernames[i]
////									+ ","
////									+ foldernames[i].replace("_" + k + "_", "_"
////											+ (k + 1) + "_"));
////					newfile.mkdirs();
////				}
////
////				break getfolderandrenameloop;
////			}
////			if (type==SITETAB) {
////				String currenttype = null;
////
////				try {
////					currenttype = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO,
////							"sitetype" + u.s(k));
////				} catch (Throwable e) {
////
////				}
////
////				try {
////					db.updateValue(DatabaseHandler.TABLE_SITEINFO, "sitetype" + u.s(k + 1),
////							currenttype);
////				} catch (Throwable e) {
////					
////				}
////			}
////		}
////
////		for (int n = 0; n <= num; n++) {
////			File currentfile = new File(buildimagenamestring(storagenamestring,
////					k, n, null));
////			File nextfile = new File(buildimagenamestring(storagenamestring,
////					k + 1, n, null));
////			Log.d("currentfile being copied to nextfile",
////					buildimagenamestring(storagenamestring, k, n, null)
////							+ ","
////							+ buildimagenamestring(storagenamestring, k + 1, n,
////									null));
////
////			try {
////				u.copyFile(currentfile, nextfile);
////				
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				
////			}
////		}
////		if (type==SITETAB) {
////			str5[k + 1].removeAllViews();
////		}
////		if (type==ASSETSTAB) {
////			tr10[k + 1].removeAllViews();
////		}
////		addpictureviewstorow(k + 1, type);
////		addpicturesforonerow(type, k + 1);
////
////		// request focus on each view to make sure onfocuschangelisteners add
////		// changes to db
////
////		current = u.getallchildrenwithspinners(tl[k]);
////		next = u.getallchildrenwithspinners(tl[k + 1]);
////
////		for (int y = 0; y < current.size(); y++) {
////			current.get(y).requestFocus();
////		}
////		for (int y = 0; y < next.size(); y++) {
////			next.get(y).requestFocus();
////		}
//
//	}

	public void remove(final int k, int type, boolean showonscreen) {
//		ad
		performingmoveoperation = true;
		int y = 0;
		String name = null;
		if (type==SITETAB) {
			name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename" , k);
		}
		if (type==COMPONENTSTAB) {
			name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" , k);
		}
		if (type==ASSETSTAB) {
			name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" , k);
		}
		if (type==RECOMMENDATIONSTAB) {
		}
		
		String table=gettablebytype(type);
		
		db.showfulldblog(table);
		for(int num=k; num<db.countrows(table)-1; num++){
			u.log("num"+num);
			u.log("trying to rename componentname" + u.s(num)+"to componentname" + u.s(num+1));
			try{
				
				if (type==SITETAB) {
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitename"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename" , (num+1)), db.KEY_SITE_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitesizem"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizem" , (num+1)), db.KEY_SITE_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitesizeft"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizeft" , (num+1)), db.KEY_SITE_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitedescription"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitedescription" , (num+1)), db.KEY_SITE_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"sitetype"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitetype" , (num+1)), db.KEY_SITE_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_SITEINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_SITEINFO,"floorplanlink"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "floorplanlink" , (num+1)), db.KEY_SITE_ATTRIBUTES);
				}
				if (type==COMPONENTSTAB) {
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentname"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componenttype"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componenttype" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentspec"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentspec" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentlocation"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentlocation" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"componentnotes"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_COMPONENTINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_COMPONENTINFO,"floorplanlink"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "floorplanlink" , (num+1)), db.KEY_COMPONENT_ATTRIBUTES);

				}
				if (type==ASSETSTAB) {
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetname"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetquantity"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetquantity" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assettype"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assettype" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetmodel"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetmodel" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetpower"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetpower" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetlocation"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetlocation" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetserviceaream"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceaream" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetserviceareaft"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetserviceareaft" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"assetnotes"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes" , (num+1)), db.KEY_ASSET_ATTRIBUTES);
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_ASSETINFO, num, db.getcolumnnumberbytitle(DatabaseHandler.TABLE_ASSETINFO,"floorplanlink"), db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "floorplanlink" , (num+1)), db.KEY_ASSET_ATTRIBUTES);

				}
				if (type==RECOMMENDATIONSTAB) {
					recommendationscontainer.removeView(recommendationtable[r - 1]);
					r--;
				}
				db.showfulldblog(table);
			}catch(Throwable e){
				e.printStackTrace();
				u.log("surpassed limit on db");
			}
		}
		
		db.showfulldblog(table);
		
		u.log(s);
		u.log(u.s(db.countrows(table)));
		db.deleteSingleRow(table, u.s(db.countrows(table)));
		
		db.showfulldblog(table);
		removerowviews(k, type, showonscreen);
		

		

		String[] foldernames = null;
		if (type==SITETAB) {
			foldernames = u.listfolders(ProjectDirectory + "/"
					+ basedirectory[SITEINFOFOLDER]);
		}
		if (type==COMPONENTSTAB) {
			foldernames = u.listfolders(ProjectDirectory + "/"
					+ basedirectory[COMPONENTSFOLDER]);
		}
		if (type==ASSETSTAB) {
			foldernames = u.listfolders(ProjectDirectory + "/"
					+ basedirectory[ASSETSFOLDER]);
		}

		String foldernameforitem = new String();
		u.log("foldernames.length"+foldernames.length);
		getfoldernameloop: for (int i = 0; i < foldernames.length; i++) {
			u.log("foldername i"+foldernames[i]);
			u.log("name"+name);
			u.log(FilenameUtils.getName(foldernames[i]));
			if (FilenameUtils.getName((foldernames[i])).equals(name)){
				foldernameforitem = foldernames[i];
				File folder = new File(foldernameforitem);
				u.deleteDirectory(folder);
				break getfoldernameloop;
			}

		}
		if (type==SITETAB) {
			s--;
		}
		if (type==COMPONENTSTAB) {
			c--;
		}
		if (type==ASSETSTAB) {
			i--;
		}
		

	}
//	public void removecomponent(int k) {
//		BitmapFactory.Options o = new BitmapFactory.Options();
//		o.inSampleSize = PICTURESAMPLESIZE;
//		for (int l = k; l <= i; l++) {
//			try {
//				if (!(componentname[l + 1].label == null)) {
//					componentname[l].et.setText(componentname[l + 1].et
//							.getText());
//					
//					componenttype[l].sp.setSelection(componenttype[l + 1].sp
//							.getSelectedItemPosition());
//					componentlocation[l].sp.setSelection(componentlocation[l + 1].sp
//							.getSelectedItemPosition());
//					
//				
//					componentspec[l].et.setText(componentspec[l + 1].et.getText());
//					componentnotes[l].et.setText(componentnotes[l + 1].et.getText());
//
//					for (int num = 0; num <= componentpicnum[l]; num++) {
//						componentpicture[l][num].setImageBitmap(componentpicture[l + 1][num]
//								.getDrawingCache());
//						componentpicture[l + 1][num].setImageBitmap(null);
//					}
//
//					// Delete pictures to make rows equal
//					while (componentpicnum[l + 1] <= componentpicnum[l]) {
//						File file = new File(imagepaths[COMPONENTSTAB][l][assetpicnum[l]]);
//						file.delete();
//						componentpicnum[l]--;
//						// ds
//					}
//
//					// now rows are equal
//					// rename files of second row to names of files in first row
//					for (int num = 0; num <= componentpicnum[l + 1]; num++) {
//						int q = l + 1;
//						File second = new File(imagepaths[COMPONENTSTAB][q][num]);
//						File first = new File(imagepaths[COMPONENTSTAB][l][num]);
//
//						first.delete();
//						second.renameTo(first);
//
//						if (first.exists()) {
//							componentpicture[l][num].setImageBitmap(Bitmap
//									.createBitmap(BitmapFactory.decodeFile(
//											first.toString(), o)));
//						} else {
//							componentpicture[l][num].setImageDrawable(null);
//						}
//						componentpicture[l + 1][num].setImageBitmap(null);
//					}
//					// for(int num=assetpicnum[l];num<assetpicnum[l+1];num++){
//					// tr.removeViewAt(num);
//					// }
//				}
//			} catch (NullPointerException e) {
//
//			}
//
//		}
//
//		componentscontainer.removeView(componenttable[c - 1]);
//
//		c--;
//		componentcount--;
//
//	}
//	public void removeasset(int k) {
//		BitmapFactory.Options o = new BitmapFactory.Options();
//		o.inSampleSize = PICTURESAMPLESIZE;
//		for (int l = k; l <= i; l++) {
//			try {
//				if (!(assetname[l + 1].label == null)) {
//					assetname[l].et.setText(assetname[l + 1].et
//							.getText());
//					quantity[l].et.setText(quantity[l + 1].et.getText());
//					type[l].sp.setSelection(type[l + 1].sp
//							.getSelectedItemPosition());
//					location[l].sp.setSelection(location[l + 1].sp
//							.getSelectedItemPosition());
//					servicearea[l].et1
//							.setText(servicearea[l + 1].et1.getText());
//					servicearea[l].et2
//							.setText(servicearea[l + 1].et2.getText());
//					model[l].et.setText(model[l + 1].et.getText());
//					spec[l].et.setText(spec[l + 1].et.getText());
//					assetnotes[l].et.setText(assetnotes[l + 1].et.getText());
//
//					for (int num = 0; num <= assetpicnum[l]; num++) {
//						picture[l][num].setImageBitmap(picture[l + 1][num]
//								.getDrawingCache());
//						picture[l + 1][num].setImageBitmap(null);
//					}
//
//					// Delete pictures to make rows equal
//					while (assetpicnum[l + 1] <= assetpicnum[l]) {
//						File file = new File(imagepaths[ASSETSTAB][l][assetpicnum[l]]);
//						file.delete();
//						assetpicnum[l]--;
//						// ds
//					}
//
//					// now rows are equal
//					// rename files of second row to names of files in first row
//					for (int num = 0; num <= assetpicnum[l + 1]; num++) {
//						int q = l + 1;
//						File second = new File(imagepaths[ASSETSTAB][q][num]);
//						File first = new File(imagepaths[ASSETSTAB][l][num]);
//
//						first.delete();
//						second.renameTo(first);
//
//						if (first.exists()) {
//							picture[l][num].setImageBitmap(Bitmap
//									.createBitmap(BitmapFactory.decodeFile(
//											first.toString(), o)));
//						} else {
//							picture[l][num].setImageDrawable(null);
//						}
//						picture[l + 1][num].setImageBitmap(null);
//					}
//					// for(int num=assetpicnum[l];num<assetpicnum[l+1];num++){
//					// tr.removeViewAt(num);
//					// }
//				}
//			} catch (NullPointerException e) {
//
//			}
//
//		}
//
//		assetscontainer.removeView(assettable[i - 1]);
//
//		i--;
//		assetcount--;
//
//	}

	public void removerecommendation(int k) {

		for (int l = k; l <= r; l++) {
			try {
				if (!(issuenametextview[l + 1] == null)) {

					recommendationtypespinner[l]
							.setSelection(recommendationtypespinner[l + 1]
									.getSelectedItemPosition());
					recommendationareaofopportunityspinner[l]
							.setSelection(recommendationareaofopportunityspinner[l + 1]
									.getSelectedItemPosition());
					recommendationobservationsspinner[l]
							.setSelection(recommendationobservationsspinner[l + 1]
									.getSelectedItemPosition());
					recommendationrecommendationsAutoCompleteTextView[l]
							.setText(recommendationrecommendationsAutoCompleteTextView[l + 1]
									.getText());
					recommendationeaskwhAutoCompleteTextView[l]
							.setText(recommendationeaskwhAutoCompleteTextView[l + 1]
									.getText());

					recommendationeascurrencytextview1[l]
							.setText(recommendationeascurrencytextview1[l + 1]
									.getText());
					recommendationeascurrencytextview2[l]
							.setText(recommendationeascurrencytextview2[l + 1]
									.getText());

					recommendationestimatedcoiAutoCompleteTextView1[l]
							.setText(recommendationestimatedcoiAutoCompleteTextView1[l + 1]
									.getText());
					recommendationestimatedcoiAutoCompleteTextView2[l]
							.setText(recommendationestimatedcoiAutoCompleteTextView2[l + 1]
									.getText());

					recommendationestimatedroitextview[l]
							.setText(recommendationestimatedroitextview[l + 1]
									.getText());

					issuenametextview[l].setText(issuenametextview[l + 1]
							.getText());
					issuespinner[l].setSelection(issuespinner[l + 1]
							.getSelectedItemPosition());
					recommendationtextview[l]
							.setText(recommendationtextview[l + 1].getText());
					recommendationAutoCompleteTextView[l]
							.setText(recommendationAutoCompleteTextView[l + 1].getText());

				}
			} catch (NullPointerException e) {

			}
		}

		recommendationscalcstable.removeView(rctr1[r - 1]);
		recommendationscontainer.removeView(recommendationtable[r - 1]);

		r--;

	}

	public void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private int getIndexofSpinner(Spinner spinner, String myString) {
		u.log("Spinner string"+myString);
		int index = 0;

		for (int gi = 0; gi < spinner.getCount(); gi++) {

			if (spinner.getItemAtPosition(gi).toString().equals(myString)) {

				index = gi;
				break;
			}
		}
		return index;
	}

	public void checkforfirstrun() {

		folder = new File(ProjectDirectory);
		// from=printFileNames(folder, null);

		try {

			if (!db.tableexists(DatabaseHandler.TABLE_SITEINFO)) {
				db.createprojecttables();
				db.showfulldblog(db.TABLE_SITEINFO);
				db.showfulldblog(db.TABLE_COMPONENTINFO);
				db.showfulldblog(db.TABLE_ASSETINFO);
				u.log("checkingforfloorplans");
				try {
					checkforfloorplans();
				} catch (Throwable e) {
					u.log("checkforfloorplansfailed");
					e.printStackTrace();
				}
				try {
					createbasedirectories();
					u.log("XXXXXXXXXXXXXXXXX These are the documents that are being copied, do we need them?");
					for (int n = 0; n < docslist.size(); n++) {
						u.log(docslist.get(n));
					}
					u.copytemplatesfromAssets(ProjectDirectory + "/"
							+ basedirectory[GENERATEDDOCUMENTSFOLDER],
							docslist, this.getApplicationContext());

					String mcrtemplatefilename = ProjectDirectory + "/"
							+ basedirectory[GENERATEDDOCUMENTSFOLDER]
							+ "/xxx-Template.xls";
					File file = new File(mcrtemplatefilename);
					mcrfilename = mcrtemplatefilename.replace(
							"xxx-Template.xls", foldername + ".xls");
					File newfile = new File(mcrfilename);
					file.renameTo(newfile);

				} catch (IOException e) {
					// TODO Auto-generated catch block

				}
				thisisfirstrun = true;

			}
		} catch (Throwable e) {
			e.printStackTrace();
			couldnotcreatenewproject();
			
		}
		String masterlistlocation=masterfoldername;
		String masterlist=masterfoldername+"/Master_Lists.xls";
		u.log("masterlistlocation="+masterlistlocation);
		if (!new File(masterlist).exists()) {
			try {
				u.copymasterlistfromAssets(masterlistlocation,
						this.getApplicationContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				u.log("masterlist couldn't be copied");
			}

		}
		String morrisonsiplist=masterfoldername+"Morrisons_IP.xls";
		u.log("morrisonsiplist="+morrisonsiplist);
		if (!new File(morrisonsiplist).exists()) {
			try {
				u.copymorrisonsIPfromAssets(masterlistlocation,this.getApplicationContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}

		}

	}

	public void dofirstrunstuff() {
		runOnUiThread(new Runnable() {
			public void run() {
				
				if(!companyadded){
					addlocation(COMPANY);
					u.log("company added in firstrun");
				}
				if(!siteadded){
					addlocation(SITE);
					u.log("site added in firstrun");
				}
				

			}
		});
		u.log("makenewasset 3 in do on first run");

		cleardoubles();
		//new SaveAllTask(progressDialog).execute();

	}

	public void readsize(String[] tokens, int sites_read_so_far) {

		String[] meterstofeettokens = tokens[1].split(";");

		sitesize[sites_read_so_far].doub1 = u.d(meterstofeettokens[0]);
		sitesize[sites_read_so_far].et1.setText(displayNumber(secondlocale,
				Math.round(sitesize[sites_read_so_far].doub1)));
		sitesize[sites_read_so_far].doub2 = u.d(meterstofeettokens[2]);
		sitesize[sites_read_so_far].et2.setText(displayNumber(secondlocale,
				Math.round(sitesize[sites_read_so_far].doub2)));

	}

	public void readserviceareasize(String[] tokens, int sites_read_so_far) {

		String[] meterstofeettokens = tokens[1].split(";");

		servicearea[sites_read_so_far].doub1 = u.d(meterstofeettokens[0]);
		servicearea[sites_read_so_far].et1.setText(displayNumber(secondlocale,
				Math.round(sitesize[sites_read_so_far].doub1)));
		servicearea[sites_read_so_far].doub2 = u.d(meterstofeettokens[2]);
		servicearea[sites_read_so_far].et2.setText(displayNumber(secondlocale,
				Math.round(sitesize[sites_read_so_far].doub2)));

	}

	public void filloutconsumptioncurrencies() {

		kwhratiotextview.setText(u.sd(kwhratiodoub));
		currencyratio.setText(u.sd(currencyratiodoub));
		double avgkwhratio = 0;
		for (int num = 0; num <= 11; num++) {

			// conslocdoub[num]=consdoub[num]*kwhratiodoub;

			consusddoub[num] = conslocdoub[num] * currencyratiodoub;

			// consloc[num].setText(displayCurrency(locale,u.d(u.sd((double)Math.round(conslocdoub[num]*100)/100))));
			consusd[num].setText(displayCurrency(secondlocale, u.d(u
					.sd((double) Math.round(consusddoub[num] * 100) / 100))));
			if (conslocdoub[num] != 0 && consdoub[num] != 0) {
				avgkwhratio = ((conslocdoub[num] / consdoub[num]) + avgkwhratio) / 2;
			}
		}
		kwhratiodoub = avgkwhratio;
		kwhratiotextview.setText(u.sd(kwhratiodoub));
	}

	public void filloutmetersandfeet() {

		for (int num = 0; num < s; num++) {

			sitesize[num].et1.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(sitesize[num].doub1)))));
			sitesize[num].et2.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(sitesize[num].doub2)))));
		}
		for (int num = 0; num < i; num++) {

			servicearea[num].et1.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(servicearea[num].doub1)))));
			servicearea[num].et2.setText(displayNumber(secondlocale,
					u.d(u.sd((double) Math.round(servicearea[num].doub2)))));
		}
	}

	public void filloutrecscolumns() {
		for (int num = 0; num < r; num++) {

			recommendationeascurrencytextview1[num]
					.setText(displayCurrency(
							locale,
							u.d(u.sd((double) Math
									.round(recommendationeascurrencydoub1[num] * 100) / 100))));
			recommendationeascurrencytextview2[num]
					.setText(displayCurrency(
							secondlocale,
							u.d(u.sd((double) Math
									.round(recommendationeascurrencydoub2[num] * 100) / 100))));

		}
		eascurtotal1.setText(displayCurrency(locale,
				u.d(u.sd((double) Math.round(eascurtotal1doub * 100) / 100))));
		eascurtotal2.setText(displayCurrency(secondlocale,
				u.d(u.sd((double) Math.round(eascurtotal2doub * 100) / 100))));

		coitotal1.setText(displayCurrency(locale,
				u.d(u.sd((double) Math.round(coitotaldoub1 * 100) / 100))));
		coitotal2.setText(displayCurrency(secondlocale,
				u.d(u.sd((double) Math.round(coitotaldoub2 * 100) / 100))));

	}

	static public String displayNumber(Locale currentLocale, double number) {

		Integer quantity = (int) (number);
		Double amount = number;
		NumberFormat numberFormatter;
		String amountOut;

		numberFormatter = NumberFormat.getNumberInstance(currentLocale);
		numberFormatter.format(quantity);
		amountOut = numberFormatter.format(amount);
		return amountOut;
	}

	static public String displayCurrency(Locale currentLocale, double currency) {

		NumberFormat currencyFormatter;
		String currencyOut;

		currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		currencyOut = currencyFormatter.format(currency);
		return currencyOut;
	}

	static public String displayPercent(Locale currentLocale, double percent) {

		NumberFormat percentFormatter;
		String percentOut;

		percentFormatter = NumberFormat.getPercentInstance(currentLocale);
		percentOut = percentFormatter.format(percent);
		return percentOut;
	}

	static public void getcurrencylists(String[] args) {

		for (int i = 0; i < locales.length; i++) {

			try {
				Currency currency1 = Currency.getInstance((locales[i]));

				if (!(currencycodelist.contains(currency1.getCurrencyCode()))) {
					localeslist.add(locales[i]);
					currencycodelist.add(currency1.getCurrencyCode());
					currencysymbollist.add(currency1.getSymbol());
					currencylist.add("(" + currency1.getSymbol() + ")"
							+ currency1.getCurrencyCode());
				}

			} catch (IllegalArgumentException e) {
			}

		}

	}

	public void displaydoubles() {
		try {
			filloutmetersandfeet();
			filloutconsumptioncurrencies();
			calculateconsumptiontable();
			calculaterecommendationstable();
		} catch (NullPointerException e) {
			
		}

	}

	public void downloadpage(String urlToDownload) {
		try {
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(ProjectDirectory
					+ "/query.txt");

			byte data[] = new byte[1024];
			int count;
			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);

			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			
		}

	}

	public double pullnumberfrompage() {

		File file = new File(ProjectDirectory + "/query.txt");

		// Read text from file

		double num = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			String checkstring = "<input id=exchange_rate type=hidden value=";

			while ((line = br.readLine()) != null) {

				if (line.contains(checkstring)) {
					num = u.d(line.substring(
							line.lastIndexOf(checkstring)
									+ checkstring.length(),
							line.lastIndexOf(checkstring)
									+ checkstring.length() + 7));
				}

			}
			br.close();
		} catch (IOException e) {
			// You'll need to add proper error handling here
		}
		return num;

	}

	public void getratiofromwebandposttodouble() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		String currency = localcurrencydisplayspinner.getSelectedItem()
				.toString();
		String secondcurrency = secondcurrencydisplayspinner.getSelectedItem()
				.toString();
		String[] cur = new String[2];
		cur = currency.split("[\\)\\.]+");

		if (cur.length > 1) {
			currency = cur[1].substring(0, 3);
		} else {
			currency = currency.substring(0, 3);
		}

		String[] seccur = new String[2];
		seccur = secondcurrency.split("[\\)\\.]+");
		if (seccur.length > 1) {
			secondcurrency = seccur[1].substring(0, 3);
		} else {
			secondcurrency = secondcurrency.substring(0, 3);
		}

		String string_to_search = "1" + currency + "=?" + secondcurrency;

		String search = "http://www.google.com/search?hl=en&q="
				+ string_to_search;
		downloadpage(search);

		currencyratiodoub = pullnumberfrompage();
		displaydoubles();
		File file = new File(ProjectDirectory + "/query.txt");
		file.delete();
	}

	public void cleardoubles() {
		for (int num = 0; num <= 11; num++) {
			consdoub[num] = 0;
		}
		currencyratiodoub = 0;
		kwhratiodoub = 0;
		for (int num = 0; num <= r; num++) {
			recommendationeaskwhdoub[num] = 0;
		}

	}

	public static int countpics(int type, int row) {
		
		int CATEGORY = 0;
		String name="";
		//u.log("type"+type);
		
		if (type==SITETAB) {
			try {
				
				name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename" , row);
				//u.log("got!  sitename" + name);
			} catch (Throwable e) {}
			
			CATEGORY = SITEINFOFOLDER;
		}
		if (type==COMPONENTSTAB) {
			try {
				name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" , row);
				//u.log("got!  componentname" + name);
			} catch (Throwable e) {}
			
			CATEGORY = COMPONENTSFOLDER;
		}
		if (type==ASSETSTAB) {
			try {
				name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" , row);
				//u.log("got!  assetname" + name);
			} catch (Throwable e) {}
			
			CATEGORY = ASSETSFOLDER;
		}
		
		
		File dir=new File(ProjectDirectory + "/"
				+ basedirectory[CATEGORY]);
		dir.mkdirs();
		File[] foldernames=new File(ProjectDirectory + "/"
				+ basedirectory[CATEGORY]).listFiles();
		List<String> filenames=new ArrayList<String>();
		for(int i=0; i<foldernames.length; i++){
			//u.log("foldername["+i+"]="+foldernames[i].getAbsoluteFile());
		}
		getfilenamesloop: for (int i = 0; i < foldernames.length; i++) {
//			u.log("Checking foldernames[i] with name "+foldernames[i].getName()+" against the name from the et"+name+ "i="+i);
			if (foldernames[i].getName().equals(name)) {
				// filenames=printFileNames(new File(foldernames[i]));
				String yourPath = foldernames[i].getAbsolutePath();
				//u.log(yourPath);
				File directory = new File(yourPath);
				
				for (int h=0; h<directory.list().length; h++){
					//u.log("checking if "+directory.list()[h] +"is a supported image file");
					if (u.issupportedimage(directory.listFiles()[h])){
						//u.log(directory.list()[h]);
						imagepaths[type][row][h]=directory.listFiles()[h].getAbsolutePath();
						filenames.add(directory.list()[h]);
						
					}
				}
				//u.log(filenames);
				break getfilenamesloop;
			}else{
				
			}
		}

		//u.log("above are the filenames found");
		int numopics=filenames.size();
//		u.log("number of pics found is here"+numopics);
		
		return numopics;
	}
	
	public void setuparrayadapters() {

		openworksheets();
		
		
		commonmanufacturers = loadlistfromxls(masterlistsheet1,COMMONMANUFACTURES,  false, false);
		commontemplocationsammendments = loadlistfromxls(masterlistsheet1,PREPOSITIONS,  false, false);
		if(ISGROCERYSTRORE){
			loadnames = loadlistfromxls(masterlistsheet3,COMMONLOADNAMES,  false, false);
			commonlocations = loadlistfromxls(masterlistsheet3,COMMONLOCATIONS,  false, false);
			commontemplocations = loadlistfromxls(masterlistsheet3,COMMONTEMPERATURESENSORLOCATIONS,  false, false);
			correspondingtypes = loadlistfromxls(masterlistsheet3,CORRESPONDINGZONETYPE,  true, true);
		}
		if(ISDATACENTER){
			loadnames = loadlistfromxls(masterlistsheet4,COMMONLOADNAMES,  false, false);
			commonlocations = loadlistfromxls(masterlistsheet4,COMMONLOCATIONS,  false, false);
			commontemplocations = loadlistfromxls(masterlistsheet4,COMMONTEMPERATURESENSORLOCATIONS,  false, false);
			correspondingtypes = loadlistfromxls(masterlistsheet4,CORRESPONDINGZONETYPE,  true, true);
		}
		
		floornumbers= loadlistfromxls(masterlistsheet1,FLOORNUMBERS,  true, true);
		
		
		bmsnamesadapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, bmsnames);
		
		commontemplocationsadapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, commontemplocations);
		
		
		floornumbersadapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, floornumbers);
		
		commonmanufacturerssadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, commonmanufacturers);
		commonroomsadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, commonlocations);
		
		
		SpinnersHash = new HashMap<ArrayAdapter<String>, List<String>>();

		locationslist = new ArrayList<String>();
		panelslist = new ArrayList<String>();
		assetslist = new ArrayList<String>();
		componentslist = new ArrayList<String>();
		locationslist.clear();
		locationslist.add("     ");
		
		u.log("setuparrayadapters");
		u.log(locationslist);
		locationspinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, locationslist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		locationspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(locationspinneradapter, locationslist);
		smalllocationspinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, locationslist);
		smalllocationspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(smalllocationspinneradapter, locationslist);

		try {
			gr = addtwoliststringsminusduplicates(
					loadlistfromxls(masterlistsheet1,GENERICRECOMMENDATIONSSUMMARY,  true, true),
					loadrecommendationlistfromreport(3, 6));
			grdetailed = addtwoliststringsminusduplicates(
					loadlistfromxls(masterlistsheet1,GENERICRECOMMENDATIONSDETAILED,  true, true),
					loadrecommendationlistfromreport(3, 1));
		} catch (Throwable e) {
		}
		// / List<String> sitelist = loadlistfromxls(COMPANYNAME, SITE_LIST);

		List<String> masterlist = loadlistfromxls(masterlistsheet1,COMPANYNAME,  true, true);
		companynamespinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		companynamespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(companynamespinneradapter, masterlist);

		companynamespinneradapter.remove(companynamespinneradapter
				.getItem(companynamespinneradapter.getCount() - 1));

		masterlist = loadlistfromxls(masterlistsheet1,ASSETTYPES,  true, true);
		assettypespinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		assettypespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(assettypespinneradapter, masterlist);
		
		masterlist = loadlistfromxls(masterlistsheet1,COMPONENTTYPES,  true, true);
		componenttypespinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v=null;
				try{
					v = super.getView(position, convertView, parent);
				}catch(Throwable e){
					position=0;
					v = super.getView(position, convertView, parent);
				}

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		componenttypespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(componenttypespinneradapter, masterlist);
		
		

		masterlist = loadlistfromxls(masterlistsheet1,RECOMMENDATIONTYPES,  true, true);
		recommendationtypeadapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist);
		recommendationtypeadapter
				.setDropDownViewResource(R.layout.recommendationsspinnertextview);
		SpinnersHash.put(recommendationtypeadapter, masterlist);

		masterlist = loadlistfromxls(masterlistsheet1,RECOMMENDATIONSAREASOFOPPORTUNITY,
				 true, true);
		recommendationareaofopportunityadapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist);
		recommendationareaofopportunityadapter
				.setDropDownViewResource(R.layout.recommendationsspinnertextview);
		SpinnersHash.put(recommendationareaofopportunityadapter, masterlist);

		try {
			masterlist = addtwoliststringsminusduplicates(
					loadlistfromxls(masterlistsheet1,RECOMMENDATIONOBSERVATIONS,  true, true),
					loadrecommendationlistfromreport(3, 5));
		} catch (Throwable e) {

		}
		recommendationobservationsadapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist);
		recommendationobservationsadapter
				.setDropDownViewResource(R.layout.recommendationsspinnertextview);
		SpinnersHash.put(recommendationobservationsadapter, masterlist);

		try {
			masterlist = addtwoliststringsminusduplicates(
					loadlistfromxls(masterlistsheet1,RECOMMENDATIONOBSERVATIONS,  true, true),
					loadrecommendationlistfromreport(3, 0));
		} catch (Throwable e) {

		}
		issuespinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist);
		issuespinneradapter
				.setDropDownViewResource(R.layout.recommendationsspinnertextview);
		SpinnersHash.put(issuespinneradapter, masterlist);

		monthsadapter = ArrayAdapter.createFromResource(this, R.array.months,
				R.layout.spinnertextview);
		monthsadapter.setDropDownViewResource(R.layout.largespinnertextview);
		yearadapter = ArrayAdapter.createFromResource(this, R.array.year,
				R.layout.spinnertextview);
		yearadapter.setDropDownViewResource(R.layout.largespinnertextview);

		// mcr spinners
		masterlist = assetslist;
		mcrloadnamespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrloadnamespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrloadnamespinneradapter, masterlist);

		masterlist = panelslist;
		mcrpanelnamespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrpanelnamespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrpanelnamespinneradapter, masterlist);

		masterlist = loadlistfromxls(masterlistsheet1,VOLTAGE,  true, true);
		mcrvoltagespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrvoltagespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrvoltagespinneradapter, masterlist);

		masterlist = loadlistfromxls(masterlistsheet1,PH,  true, true);
		// get rid of trailing new for phases
		masterlist.remove(masterlist.size() - 1);
		mcrphspinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrphspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrphspinneradapter, masterlist);

		if(this.ISGROCERYSTRORE){
		   masterlist = loadlistfromxls(masterlistsheet3,ZONETYPE,  true, true);
		}
		if(this.ISDATACENTER){
			masterlist = loadlistfromxls(masterlistsheet4,ZONETYPE,  true, true);
		}
		
		// get rid of trailing new for Zonetypes
		masterlist.remove(masterlist.size() - 1);
		mcrtempzonetypespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrtempzonetypespinneradapter.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrtempzonetypespinneradapter, masterlist);

		masterlist = loadlistfromxls(masterlistsheet1,TRANSCONFIG,  true, true);
		mcrtransconfigspinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrtransconfigspinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrtransconfigspinneradapter, masterlist);

		
		if(this.ISGROCERYSTRORE){
			   masterlist = loadlistfromxls(masterlistsheet3,LOADTYPE,  true, true);
			}
			if(this.ISDATACENTER){
				masterlist = loadlistfromxls(masterlistsheet4,LOADTYPE,  true, true);
			}
		
			
			mcrloadtypespinneradapterlargeview = new ArrayAdapter<String>(this,
					R.layout.largespinnertextview, masterlist);
			mcrloadtypespinneradapterlargeview
					.setDropDownViewResource(R.layout.largespinnertextview);
			
		mcrloadtypespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrloadtypespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrloadtypespinneradapter, masterlist);
		
		masterlist = loadlistfromxls(masterlistsheet1,CTTYPE,  true, true);
		mcrcttypespinneradapter = new ArrayAdapter<String>(this,
				R.layout.smallspinnertextview, masterlist);
		mcrcttypespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(mcrcttypespinneradapter, masterlist);
		
		masterlist = loadlistfromxls(masterlistsheet1,ENGINEERNAME,  true, true);
		engineernamespinneradapter = new ArrayAdapter<String>(this,
				R.layout.spinnertextview, masterlist) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(0, convertView, parent);
				try{
				v = super.getView(position, convertView, parent);
				}catch(Throwable e ){
					
				}
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};
		engineernamespinneradapter
				.setDropDownViewResource(R.layout.largespinnertextview);
		SpinnersHash.put(engineernamespinneradapter, masterlist);

		engineernameslist = masterlist;
		engineeremaillist = loadlistfromxlsgivenlistlength(ENGINEEREMAIL,masterlistsheet1,
				 masterlist.size());
		engineerphonenumberlist = loadlistfromxlsgivenlistlength(
				ENGINEERPHONENUMBER, masterlistsheet1, masterlist.size());

		
		List<String> operators = new ArrayList<String>();
		operators.add("+");
		operators.add("-");
		operatorsadapter = new ArrayAdapter<String>(this,
				R.layout.largespinnertextview, operators);
		operatorsadapter
		.setDropDownViewResource(R.layout.largespinnertextview);
		
		
		refreshmeterslists();
		
		
	}

	public void WriteExcelFacilityEnergyConsumption(String filenamestring) {
		try{
		File file = new File(filenamestring);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableWorkbook copy = null;
		try {
			copy = Workbook.createWorkbook(file, workbook);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			
		}
		WritableCellFormat nf;

		if (Currency.getInstance(locale).getCurrencyCode().equals("EUR")) {
			jxl.write.NumberFormat euroPrefixCurrency = new jxl.write.NumberFormat(
					" #,##0.00" + Currency.getInstance(locale).getSymbol(),
					jxl.write.NumberFormat.COMPLEX_FORMAT);
			nf = new WritableCellFormat(euroPrefixCurrency);
		} else {
			jxl.write.NumberFormat euroPrefixCurrency = new jxl.write.NumberFormat(
					Currency.getInstance(locale).getSymbol() + " #,##0.00",
					jxl.write.NumberFormat.COMPLEX_FORMAT);
			nf = new WritableCellFormat(euroPrefixCurrency);

		}
		int snum = 0;
		WritableSheet sheet = copy.getSheet(snum);

		Currency loccurrency1 = Currency.getInstance(locale);
		String loccurrency = "(" + loccurrency1.getSymbol() + ")"
				+ loccurrency1.getCurrencyCode();

		Currency currency1 = Currency.getInstance(secondlocale);
		String currency = "(" + currency1.getSymbol() + ")"
				+ currency1.getCurrencyCode();
		

		String sitename = constablename.getText().toString();

		u.writesheetandcell(sheet, workbook, copy, snum, 'a', 2, sitename,
				"string", null, linkhash);

		u.writesheetandcell(sheet, workbook, copy, snum, 'c', 2,
				u.sd(sitesize[1].doub1), "double", null, linkhash);
		u.writesheetandcell(sheet, workbook, copy, snum, 'a', 9,
				u.sd(currencyratiodoub), "double", null, linkhash);
		int sheetnum = 0;
		for (int num = 0; num <= 11; num++) {
			sheetnum = num + 7;
			u.writesheetandcell(sheet, workbook, copy, snum, 'f', sheetnum,
					month[num].getText().toString(), "string", null, linkhash);
			u.writesheetandcell(sheet, workbook, copy, snum, 'g', sheetnum,
					u.sd(consdoub[num]), "double", null, linkhash);
			u.writesheetandcell(sheet, workbook, copy, snum, 'h', sheetnum, "G"
					+ u.s(sheetnum) + "*kwhratio", "format", nf, linkhash);
		}
		u.writesheetandcell(sheet, workbook, copy, snum, 'g', 5, sitename,
				"string", null, linkhash);
		u.writesheetandcell(sheet, workbook, copy, snum, 'h', 6, loccurrency,
				"string", null, linkhash);
		u.writesheetandcell(sheet, workbook, copy, snum, 'i', 6, currency,
				"string", null, linkhash);
		u.writesheetandcell(sheet, workbook, copy, snum, 'a', 12,
				u.sd(kwhratiodoub), "double", null, linkhash);

		try {
			copy.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			copy.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		
		}catch(Throwable e){
			
		}
	}

	public void getAssetslist() {
		docslist = new ArrayList<String>();
		prettydocslist = new ArrayList<String>();
		AssetManager aMan = this.getAssets();
		String[] filelist = null;
		try {
			filelist = aMan.list("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		
			
		String[] assets={"versiondetails.txt","Report.xls","xxx-Template.xls"};
		
		for(String asset:assets){
			docslist.add(asset);
			prettydocslist.add(asset);
		}
				
	}

	public static void deletepicturedialog(final File file, final ImageView imageview,
			final TableRow parentrow, final int num) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage("Are you sure you want to delete this image?")
				.setCancelable(false)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//getPreferences();
								deleteimage(file, imageview);
								if(typeforgetpicture==FLOORPLANTAB){
									sitefptextview[num].setText("");
									((ViewGroup)sitefptextview[num].getParent()).removeView(sitefptextview[num]);
										
								}
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						deletepicturedialog.cancel();
					}
				});
		deletepicturedialog = builder.create();

		// CustomizeDialog customizeDialog = new CustomizeDialog(this);
		deletepicturedialog.show();
		// this.finish();
	}

	public void deleteorduplicatepicturedialog(final File file,
			final ImageView imageview, final TableRow parentrow) {

		Button AddDetailsbutton = new Button(this);
		AddDetailsbutton.setText("Add Details");
		AddDetailsbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				oldcaption="";
				oldnotes="";
				// Get the text file
				u.log(file.getAbsolutePath());
				File file1 = new File(file.getAbsolutePath().replace(".jpg",".txt"));
				if (file1.exists()) {
					// File f = new
					// File(Environment.getExternalStorageDirectory(),
					// "fileOut.txt");
					// Read text from file
					
					StringBuilder detailtext = new StringBuilder();

					try {
						BufferedReader br = new BufferedReader(new FileReader(
								file1));
						String line;

						while ((line = br.readLine()) != null) {
							detailtext.append(line);
							detailtext.append('\n');
						}
					} catch (IOException e) {
						// You'll need to add proper error handling here
					}
					
					int n = detailtext.length();
					int i = 0;
					int j = 0;
					for (i = 0; i < n; i++) {
						if (detailtext.charAt(i) == '\n') {
							break;
						}

					}

					oldcaption = detailtext.substring(9, i);
					for (j = i; j < n; j++) {
						if (detailtext.charAt(j) == ':') {
							break;
						}

					}

					oldnotes = detailtext.substring(j + 1, n);
					
					

				}

				caption(file);
				deleteorduplicatepicturedialog.cancel();

			}

		});

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Image Options")
				.setCancelable(true)
				.setView(AddDetailsbutton)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								deleteorduplicatepicturedialog.dismiss();
								deletepicturedialog(file, imageview, parentrow, 1);

							}
						})
				.setNeutralButton("Duplicate",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								try {
									FileInputStream in = new FileInputStream(
											file);
									FileOutputStream out = new FileOutputStream(
											DevelopeFileName(file,null));
									byte[] buf = new byte[4096];
									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
									in.close();
									out.close();
									add1pic();
								} catch (Exception e) {
									
								}

								try {
									File file1 = new File(file.getAbsolutePath().replace(FilenameUtils.getExtension(file.getAbsolutePath()),".txt"));

									FileInputStream in = new FileInputStream(
											file1);
									FileOutputStream out = new FileOutputStream(
											DevelopeFileName(file1,"txt"));
									byte[] buf = new byte[4096];
									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
									in.close();
									out.close();
									// add1pic();
								} catch (Exception e) {
									
								}

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		deleteorduplicatepicturedialog = builder.create();

		// CustomizeDialog customizeDialog = new CustomizeDialog(this);
		deleteorduplicatepicturedialog.show();
		// this.finish();
	}
	public void deleteorduplicatefloorplandialog(final File file,
			final ImageView imageview, final TableRow parentrow, final int num) {


		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage("Would you like to delete or duplicate this floorplan?\n" +
				"**Warning deleting the floorplan also deletes the locations of any items already placed on it.")
				
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String dbtablename="floorplan";
								
								try{
									Tabs1.db.deletedbtable(dbtablename);
								}catch(Throwable e){
									e.printStackTrace();
								}
								typeforgetpicture=FLOORPLANTAB;
								
								deletepicturedialog(file, imageview, parentrow,num);

							}
						})
				.setNeutralButton("Duplicate",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								try {
									FileInputStream in = new FileInputStream(
											file);
									u.log("in and out"+file.getAbsolutePath()+"   "+file.getAbsolutePath().split(".")[0]+"(copy)"+file.getAbsolutePath().split(".")[1]);
									FileOutputStream out = new FileOutputStream(new File(file.getAbsolutePath().split(".")[0]+"(copy)"+file.getAbsolutePath().split(".")[1]));
									byte[] buf = new byte[4096];
									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
									in.close();
									out.close();
									refreshfloorplans();
								} catch (Exception e) {
									
								}

								

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		deleteorduplicatefloorplandialog = builder.create();

		// CustomizeDialog customizeDialog = new CustomizeDialog(this);
		deleteorduplicatefloorplandialog.show();
		// this.finish();
	}

	public void add1picslot(int k) {

		if (typeselected==ASSETSTAB) {
			int num = tr10[k].getChildCount() - 2;
			picture[k][num] = new ImageView(this);
			picture[k][num].setLayoutParams(lpww);
			tr10[k].addView(picture[k][num]);

		}
		if (typeselected==SITETAB) {
			int num = str5[k].getChildCount() - 2;
			sitepicture[k][num] = new ImageView(this);
			sitepicture[k][num].setLayoutParams(lpww);

			str5[k].addView(sitepicture[k][num]);

		}
	}

	public void add1pic() {
		getPreferences();

		int type = typeselected;
		int k = u.i(numberselected);

		int counted = 0;
		int[] picsforrow = null;
		
		int num = countpics(type, k)-1;
		if(num==-1){
			num=0;
		}

		ImageView[][] pic = null;
		if (type==SITETAB) {
			
			pic = sitepicture;
			counted = s;
			picsforrow = sitepicnum;

		}
		if (type==COMPONENTSTAB) {
			
			pic = componentpicture;
			counted = c;
			picsforrow = componentpicnum;

		}
		if (type==ASSETSTAB) {
			
			pic = picture;
			counted = i;
			picsforrow = assetpicnum;

		}

		pic[k][num] = new ImageView(this);
		pic[k][num].setLayoutParams(lpfw);

		TableRow.LayoutParams tr10lp = new TableRow.LayoutParams();
		tr10lp.weight = (1 / 4);

		pic[k][num].setLayoutParams(tr10lp);

		if (type==ASSETSTAB) {
			pic[k][num].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					try {
						int[] z = getsequencenumber(picture, v);

						try {
							String picturelocation = imagepaths[ASSETSTAB][z[0]][z[1]];
							lhiphotoeditororotherdialog(picturelocation,
									ASSETSTAB, z[0], z[1]);
						} catch (ActivityNotFoundException e) {
							showneedmoresoftwaredialog(
									"ASTRO File Manager/Browser",
									"com.metago.astro");
						}

					} catch (IndexOutOfBoundsException e) {

					}
				}
			});
			pic[k][num].setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View v) {

					int[] z = null;
					try {
						z = getsequencenumber(picture, v);
						
						TableRow parentrow = (TableRow) v.getParent();
						typeselected = ASSETSTAB;
						numberselected = u.s(z[0]);
						subnumberselected = u.s(z[1]);
						deleteorduplicatepicturedialog(
								new File(imagepaths[ASSETSTAB][z[0]][z[1]]), (ImageView) v,
								parentrow);

					} catch (IndexOutOfBoundsException e) {

					}

					return false;
				}

			});
		}
		
		if (type==COMPONENTSTAB) {
			pic[k][num].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					try {
						int[] z = getsequencenumber(picture, v);

						try {
							String picturelocation = imagepaths[COMPONENTSTAB][z[0]][z[1]];
							lhiphotoeditororotherdialog(picturelocation,
									COMPONENTSTAB, z[0], z[1]);
						} catch (ActivityNotFoundException e) {
							showneedmoresoftwaredialog(
									"ASTRO File Manager/Browser",
									"com.metago.astro");
						}

					} catch (IndexOutOfBoundsException e) {

					}
				}
			});
			pic[k][num].setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View v) {

					int[] z = null;
					try {
						z = getsequencenumber(picture, v);
						
						TableRow parentrow = (TableRow) v.getParent();
						typeselected = COMPONENTSTAB;
						numberselected = u.s(z[0]);
						subnumberselected = u.s(z[1]);
						deleteorduplicatepicturedialog(
								new File(imagepaths[COMPONENTSTAB][z[0]][z[1]]), (ImageView) v,
								parentrow);

					} catch (IndexOutOfBoundsException e) {

					}

					return false;
				}

			});
		}
		
		if (type==SITETAB) {
			pic[k][num].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					try {
						int[] z = getsequencenumber(sitepicture, v);

						try {
							String picturelocation = imagepaths[SITETAB][z[0]][z[1]];
							lhiphotoeditororotherdialog(picturelocation,
									SITETAB, z[0], z[1]);
						} catch (ActivityNotFoundException e) {
							showneedmoresoftwaredialog(
									"ASTRO File Manager/Browser",
									"com.metago.astro");
						}

					} catch (IndexOutOfBoundsException e) {

					}
				}
			});
			pic[k][num].setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View v) {

					int[] z = null;
					try {
						z = getsequencenumber(sitepicture, v);
						
						TableRow parentrow = (TableRow) v.getParent();
						typeselected = SITETAB;
						numberselected = u.s(z[0]);
						subnumberselected = u.s(z[1]);
						deleteorduplicatepicturedialog(
								new File(imagepaths[SITETAB][z[0]][z[1]]), (ImageView) v, parentrow);

					} catch (IndexOutOfBoundsException e) {

					}

					return false;
				}

			});
		}

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = PICTURESAMPLESIZE;
		
		try {
			Bitmap bm = BitmapFactory.decodeFile(imagepaths[type][k][num], o);

			double calcheight = (double) screenheight
					* (double) PICTUREDISPLAYPERCENTOFSCREEN / (double) 100;
			double calcwidth = (double) bm.getWidth() / (double) bm.getHeight()
					* (double) calcheight;

			int height = (int) calcheight;
			int width = (int) calcwidth;

			Bitmap resizedbitmap = null;

			resizedbitmap = Bitmap.createScaledBitmap(bm, width, height, true);
			bm.recycle();
			pic[k][num].setImageBitmap(resizedbitmap);

			if (type==COMPONENTSTAB) {
				ctr10[k].addView(pic[k][num]);
				componentpicnum[k]++;
				
			}
			if (type==ASSETSTAB) {
				tr10[k].addView(pic[k][num]);
				assetpicnum[k]++;
				
			}
			if (type==SITETAB) {
				str5[k].addView(pic[k][num]);
				sitepicnum[k]++;
			}
		} catch (Throwable e) {
			u.log("issue loading picture");
			
		}
		try {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://" + ProjectDirectory)));
		} catch (Throwable e) {

		}
		
		try{
			SetExplorerWindowtoFolder(ProjectDirectory);
		}catch(Throwable e){
			
		}
	}
	public void createvirtualmcrtablefrom(final int SOURCE) {
		// LinearLayout mcrcontainer=(LinearLayout)
		
//		if count on vmdb is greater than 0,
//		add title row and all subsuent rows
	
		
		
		int numovirtualmeters=db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST);
		int x=0;
		try{
			virtualmcrtable=(LinearLayout) findViewById(R.id.mcrtable2container);
			virtualmcrtable.setBackgroundColor(Color.parseColor(darkblue));
			virtualmcrtable.setPadding(2, 2, 2, 2);

		}catch (Throwable e){
			
		}
	
		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		
		TableLayout[] titletl=new TableLayout[numovirtualmeters+1];
		TableRow[] tr=new TableRow[numovirtualmeters+1];
		TextView[] vmnum=new TextView[numovirtualmeters+1];
		TextView[] vmname=new TextView[numovirtualmeters+1];
		TextView[] vmformula=new TextView[numovirtualmeters+1];
		TextView[] vmtype=new TextView[numovirtualmeters+1];
		ImageView[] virtualmeterdeletebutton=new ImageView[numovirtualmeters+1];
		
		titletl[x]=new TableLayout(this);
		tr[x]=new TableRow(this);
		vmnum[x]=new TextView(this);
		vmname[x]=new TextView(this);
		vmformula[x]=new TextView(this);
		vmtype[x]=new TextView(this);
		virtualmeterdeletebutton[x]=new ImageView(this);
		
		titletl[x].setLayoutParams(lpfw);
		//tr[x].setLayoutParams(lp);
		vmnum[x].setLayoutParams(lp);
		vmname[x].setLayoutParams(lp);
		vmformula[x].setLayoutParams(lp);
		vmtype[x].setLayoutParams(lp);
		virtualmeterdeletebutton[x].setLayoutParams(lp);
		
		
		vmnum[x].setText("Virtual Meter #");
		vmname[x].setText("Virtual Meter Name");
		vmformula[x].setText("Virtual Meter Formula");
		vmtype[x].setText("Virtual Meter Load Type");
		virtualmeterdeletebutton[x].setVisibility(View.INVISIBLE);
		virtualmeterdeletebutton[x].setClickable(false);
		
		
		tr[x].addView(vmnum[x]);
		tr[x].addView(vmname[x]);
		tr[x].addView(vmformula[x]);
		tr[x].addView(vmtype[x]);
		tr[x].addView(virtualmeterdeletebutton[x]);
		virtualmcrtable.addView(tr[x]);
		
		int columnItem_Number=0;
		int columnVirtual_Meter_Name=1;
		int columnFormula=2; 
		int columnLoad_Type=3;
		
		Cursor virtualmeterdb=db.getcursor(db.TABLE_MCR_VIRTUAL_METERINGLIST);
		virtualmeterdb.moveToFirst();
		for(int v=x+1; v<numovirtualmeters+1; v++){
			
			
			lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
	
			
		virtualmeterdeletebutton[v] = new ImageView(this);
		virtualmeterdeletebutton[v]
					.setImageResource(R.drawable.deleteitem48);
		virtualmeterdeletebutton[v].setLayoutParams(lp);
		virtualmeterdeletebutton[v]
					.setOnClickListener(new ImageView.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							String itemid=((TextView)((ViewGroup) v.getParent()).getChildAt(0)).getText().toString();
							db.deleteSingleRow(db.TABLE_MCR_VIRTUAL_METERINGLIST, itemid);
							try{
									virtualmcrtable.removeAllViews();
								}catch(Throwable e){
									
								}
								if(db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST)>0){
									createvirtualmcrtablefrom(SOURCE);
								}
						}
					});

			

			
			virtualmcrtablelayouts[v]=new TableLayout(this);
			virtualmcrtablelayouts[v].setLayoutParams(lpfw);
			
			tr[v]=new TableRow(this);
			vmnum[v]=new TextView(this);
			vmname[v]=new TextView(this);
			vmformula[v]=new TextView(this);
			vmtype[v]=new TextView(this);
			
		
			//tr[v].setLayoutParams(lp);
			vmnum[v].setLayoutParams(lp);
			vmname[v].setLayoutParams(lp);
			vmformula[v].setLayoutParams(lp);
			vmtype[v].setLayoutParams(lp);
			
			
			vmnum[v].setText(virtualmeterdb.getString(columnItem_Number));
			vmname[v].setText(virtualmeterdb.getString(columnVirtual_Meter_Name));
			vmformula[v].setText(virtualmeterdb.getString(columnFormula));
			vmtype[v].setText(virtualmeterdb.getString(columnLoad_Type));
			
			
			
//			vmnum[v].setText(db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Item_Number",v-1));
//			vmname[v].setText(db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Virtual_Meter_Name",v-1));
//			vmformula[v].setText(db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Formula",v-1));
//			vmtype[v].setText(db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Load_Type",v-1));
			
			
			tr[v].addView(vmnum[v]);
			tr[v].addView(vmname[v]);
			tr[v].addView(vmformula[v]);
			tr[v].addView(vmtype[v]);
			tr[v].addView(virtualmeterdeletebutton[v]);
			virtualmcrtablelayouts[v].addView(tr[v]);
			tr[v].isClickable();
			tr[v].setOnClickListener(new TableLayout.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(getCurrentFocus()!=null){
						getCurrentFocus().clearFocus();
					}
					
					TableRow tr=(TableRow)(arg0);
					TextView tv=(TextView)(tr.getChildAt(0));
					showvirtualmeterdialog(u.i(tv.getText().toString())-1);
				}});
			
			
			
			virtualmcrtable.addView(virtualmcrtablelayouts[v]);
			virtualmeterdb.moveToNext();
		}
		virtualmeterdb.close();

	}
	public void createmcrtablefrom(int SOURCE) {
		// LinearLayout mcrcontainer=(LinearLayout)

		long time=System.currentTimeMillis();
		meteringlistdb();
		Log.d("mcrtime","meteringlistdb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

		virtualmeteringlistdb();

		time=System.currentTimeMillis();
		addmcrbuttons();
		Log.d("mcrtime","addmcrbuttons() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

		
		time=System.currentTimeMillis();
		tempsensordb();
		Log.d("mcrtime","tempsensordb() "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

		
	

	}

	public void createandlinkrecommendationviewstodb(){
		genericcheck1= (CheckBox) findViewById(R.id.sitelookoutcheck1);
		genericcheck2= (CheckBox) findViewById(R.id.sitelookoutcheck2);
		genericnotes1= (AutoCompleteTextView) findViewById(R.id.sitelookoutnotes1);
		genericnotes2= (AutoCompleteTextView) findViewById(R.id.sitelookoutnotes2);
				
	}
	
	// Ibe's spot to locate and dump bom items
	
	
	public void createbom() {
		createbomcounts();
		createbomtotalcosts();
		addbomchangelisteners();
	}

	public void createbomcounts() {
		bomgatewayservercount = (TextView) findViewById(R.id.TextView023a);
		bomsamcount = (TextView) findViewById(R.id.TextView033);
		bomelccount = (TextView) findViewById(R.id.TextView023);
		bomtscount = (TextView) findViewById(R.id.TextView043);
		// For Phase See line 9792, 10661, 10680, 11826, 11845, 19083, 19210,
		// 19213, 19377
		bomCTcount = (TextView) findViewById(R.id.TextView053);
		// Goto Line 10681 and try to make a functions that returns that PH
		// number as bomCTcount
		// have table layout code here with bomCTdetail for
		bomcttablelayout = (TableLayout) findViewById(R.id.tableLayout01);

		bomtsdistance = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView073);
		bomctdistance = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView083);
		bompowerwiredistance = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView093);

		bommisccount = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView113);

	}

	public void createbomtotalcosts() {
		bomgatewayservertotalcost = (TextView) findViewById(R.id.TextView026a);
		bomsamtotalcost = (TextView) findViewById(R.id.TextView036);
		bomelctotalcost = (TextView) findViewById(R.id.TextView026);
		bomtstotalcost = (TextView) findViewById(R.id.TextView046);
		bomCTtotalcost = (TextView) findViewById(R.id.TextView056);

		bomtsdistancetotalcost = (TextView) findViewById(R.id.TextView076);
		bomctdistancetotalcost = (TextView) findViewById(R.id.TextView086);
		bompowerwiredistancetotalcost = (TextView) findViewById(R.id.TextView096);

		bommisctotalcost = (TextView) findViewById(R.id.TextView116);

		bomtotalcost = (TextView) findViewById(R.id.TextView126);
	}

	public void siterecommendationlisteners(){
	
//	recommendationsitetype
//		.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View arg0, boolean hasfocus) {
//				// TODO Auto-generated method stub
//				if (!hasfocus) {
//					db.addorupdate(db.TABLE_SITE_RECOMMENDATIONS, "recommendationsitetype",recommendationsitetype.getSelectedItem().toString());
//				  }
//			    }
//		  });
		
		genericcheck1
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck1",
							"true");
					}
				else{
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck1",
							"false");
					}
			}});
		
		genericcheck2
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck2",
							"true");
					}
				else{
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck2",
							"false");
					}
			}});

		genericnotes1
		.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (!hasfocus) {
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericnotes1",
							genericnotes1.getText().toString());
				    }
			     }
		  });
		
		genericnotes2
		.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (!hasfocus) {
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericnotes2",
							genericnotes2.getText().toString());
				    }
			     }
		  });
		
	
		
	
	
	
	}
	
	public void fillunitcosts(int companycode){
		double elccost,samcost,mahucost,trbcost,tscost,wtscost,ctcost;
		switch(companycode){
		case MORRISONS:
			elccost=1185.56;
			samcost=325.05;
			mahucost=390.89;
			trbcost=312.70;
			tscost=163.63;
			wtscost=41.83;
			ctcost=106.92;
			
			if(unitcostfill){
				bomgatewayserverunitcost.setText("0");;
				bomsamunitcost.setText(u.sd(samcost));
				bomelcunitcost.setText(u.sd(elccost));
				bomtsunitcost.setText(u.sd(tscost));
				bomCTunitcost.setText(u.sd(ctcost));
				
				bomtsdistanceunitcost.setText("0");
				bomctdistanceunitcost.setText("0");
				bompowerwiredistanceunitcost.setText("0");
				
			}else{
				bomgatewayserverunitcost.setText("0");
				bomsamunitcost.setText("0");
				bomelcunitcost.setText("0");
				bomtsunitcost.setText("0");
				bomCTunitcost.setText("0");
				
				bomtsdistanceunitcost.setText("0");
				bomctdistanceunitcost.setText("0");
				bompowerwiredistanceunitcost.setText("0");
			}
		break;
		}
	};
	public void addbomchangelisteners() {

		UnitCostFill=(TextView) findViewById(R.id.textView015);
		UnitCostFill.setOnLongClickListener(new TextView.OnLongClickListener(){

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				if(unitcostfill){
					unitcostfill=false;
				}else{
					unitcostfill=true;
				}
				
				if(ISMORRISONS){
					
					fillunitcosts(MORRISONS);
				}
				return false;
			}});
		
		bomgatewayserverunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView025a);
		bomsamunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView035);
		bomelcunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView025);
		bomtsunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView045);
		bomCTunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView055);

		bomtsdistanceunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView075);
		bomctdistanceunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView085);
		bompowerwiredistanceunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView095);

		bommisccountunitcost = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView115);

		bomgatewayserverunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bomsamunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bomelcunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bomtsunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bomCTunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });

		bomtsdistanceunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bomctdistanceunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
		bompowerwiredistanceunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });

		bommisccountunitcost
				.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });

		bomgatewayserverunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomgatewayservercount.getText()
									.toString());
							double unitcost = u.d(bomgatewayserverunitcost
									.getText().toString());
							double total = count * unitcost;
							bomgatewayservertotalcost.setText(String.format(
									"%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomgatewayservercount",
									bomgatewayservercount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomgatewayserverunitcost",
									bomgatewayserverunitcost.getText()
											.toString());
						}

					}
				});
		bomsamunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u
									.d(bomsamcount.getText().toString());
							double unitcost = u.d(bomsamunitcost.getText()
									.toString());
							double total = count * unitcost;
							bomsamtotalcost.setText(String
									.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomsamcount",
									bomsamcount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomsamunitcost",
									bomsamunitcost.getText().toString());
						}
					}
				});
		bomelcunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u
									.d(bomelccount.getText().toString());
							double unitcost = u.d(bomelcunitcost.getText()
									.toString());
							double total = count * unitcost;
							bomelctotalcost.setText(String
									.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomelccount",
									bomelccount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomelcunitcost",
									bomelcunitcost.getText().toString());
						}
					}
				});
		bomtsunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomtscount.getText().toString());
							double unitcost = u.d(bomtsunitcost.getText()
									.toString());
							double total = count * unitcost;
							bomtstotalcost.setText(String.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomtscount",
									bomtscount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomtsunitcost",
									bomtsunitcost.getText().toString());
						}
					}
				});
		bomCTunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomCTcount.getText().toString());
							double unitcost = u.d(bomCTunitcost.getText()
									.toString());
							double total = count * unitcost;
							bomCTtotalcost.setText(String.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomCTcount",
									bomCTcount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomCTunitcost",
									bomCTunitcost.getText().toString());
						}
					}
				});
		bomtsdistance
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomtsdistance.getText()
									.toString());
							double unitcost = u.d(bomtsdistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bomtsdistancetotalcost.setText(String.format(
									"%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomtsdistance",
									bomtsdistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomtsdistanceunitcost",
									bomtsdistanceunitcost.getText().toString());
						}
					}
				});

		bomtsdistanceunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomtsdistance.getText()
									.toString());
							double unitcost = u.d(bomtsdistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bomtsdistancetotalcost.setText(String.format(
									"%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomtsdistance",
									bomtsdistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomtsdistanceunitcost",
									bomtsdistanceunitcost.getText().toString());
						}
					}
				});

		bomctdistance
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomctdistance.getText()
									.toString());
							double unitcost = u.d(bomctdistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bomctdistancetotalcost.setText(String.format(
									"%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomctdistance",
									bomctdistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomctdistanceunitcost",
									bomctdistanceunitcost.getText().toString());
						}
					}
				});
		bomctdistanceunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bomctdistance.getText()
									.toString());
							double unitcost = u.d(bomctdistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bomctdistancetotalcost.setText(String.format(
									"%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bomctdistance",
									bomctdistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bomctdistanceunitcost",
									bomctdistanceunitcost.getText().toString());
						}
					}
				});
		bompowerwiredistance
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bompowerwiredistance.getText()
									.toString());
							double unitcost = u.d(bompowerwiredistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bompowerwiredistancetotalcost.setText(String
									.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bompowerwiredistance",
									bompowerwiredistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bompowerwiredistanceunitcost",
									bompowerwiredistanceunitcost.getText()
											.toString());
						}
					}
				});
		bompowerwiredistanceunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bompowerwiredistance.getText()
									.toString());
							double unitcost = u.d(bompowerwiredistanceunitcost
									.getText().toString());
							double total = count * unitcost;
							bompowerwiredistancetotalcost.setText(String
									.format("%.2f", total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bompowerwiredistance",
									bompowerwiredistance.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bompowerwiredistanceunitcost",
									bompowerwiredistanceunitcost.getText()
											.toString());
						}
					}
				});
		bommisccount
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bommisccount.getText()
									.toString());
							double unitcost = u.d(bommisccountunitcost
									.getText().toString());
							double total = count * unitcost;
							bommisctotalcost.setText(String.format("%.2f",
									total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bommisccount",
									bommisccount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bommisccountunitcost",
									bommisccountunitcost.getText().toString());
						}
					}
				});
		bommisccountunitcost
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean hasfocus) {
						// TODO Auto-generated method stub
						if (!hasfocus) {
							double count = u.d(bommisccount.getText()
									.toString());
							double unitcost = u.d(bommisccountunitcost
									.getText().toString());
							double total = count * unitcost;
							bommisctotalcost.setText(String.format("%.2f",
									total));
							getbomtotalcost();
							db.addorupdate(DatabaseHandler.TABLE_BOM, "bommisccount",
									bommisccount.getText().toString());
							db.addorupdate(DatabaseHandler.TABLE_BOM,
									"bommisccountunitcost",
									bommisccountunitcost.getText().toString());
						}
					}
				});
	}

	public static void getbomtotalcost() {
		bomtotalcost.setText(String.format(
				"%.2f",
				u.ds(u.sd(u.ds(bomgatewayservertotalcost.getText().toString())
						+ u.ds(bomsamtotalcost.getText().toString())
						+ u.ds(bomelctotalcost.getText().toString())
						+ u.ds(bomtstotalcost.getText().toString())
						+ u.ds(bomCTtotalcost.getText().toString())
						+

						u.ds(bomtsdistancetotalcost.getText().toString())
						+ u.ds(bomctdistancetotalcost.getText().toString())
						+ u.ds(bompowerwiredistancetotalcost.getText()
								.toString()) +

						u.ds(bommisctotalcost.getText().toString())))));
	}

	public void fillbomtabwdbvalues() {
		
		bomgatewayservercount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bomgatewayservercount",0));
		bomelccount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomelccount", 0));
		bomsamcount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomsamcount", 0));
		bomtscount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomtscount", 0));
		bomCTcount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomCTcount", 0));
		bomtsdistance.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomtsdistance", 0));
		bomctdistance.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomctdistance", 0));
		bompowerwiredistance.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bompowerwiredistance", 0));
		bommisccount.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bommisccount", 0));

		bomgatewayserverunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bomgatewayserverunitcost", 0));
		bomsamunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomsamunitcost", 0));
		bomelcunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomelcunitcost", 0));
		bomtsunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomtsunitcost", 0));
		bomCTunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM, "bomCTunitcost", 0));
		bomtsdistanceunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bomtsdistanceunitcost", 0));
		bomctdistanceunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bomctdistanceunitcost", 0));
		bompowerwiredistanceunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bompowerwiredistanceunitcost", 0));
		bommisccountunitcost.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
				"bommisccountunitcost", 0));

		try{
			addvaluestobom();
		}catch(Throwable e){
			
		}
		refreshbom();
		
		

	}

	public void fillsiterecommendationvalues() {
		
		
		ArrayAdapter tempadap= (ArrayAdapter)recommendationsitetype.getAdapter();
		
		int spinnerpos =0;
		try{
			spinnerpos = tempadap.getPosition(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "recommendationsitetype", 0));
		}catch(Throwable e){
			
		}
		
		recommendationsitetype.setSelection(spinnerpos);
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck1",0).equals("true")){
		genericcheck1.setChecked(true);
		}
		}catch(Throwable e){
			
		}
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericcheck2",0).equals("true")){
			genericcheck2.setChecked(true);
			}
		}catch(Throwable e){
			
		}
		
		try{
		genericnotes1.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericnotes1", 0));
		}catch(Throwable e){
			
		}
		try{
		genericnotes2.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "genericnotes2", 0));
		}catch(Throwable e){
			
		}
		
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "recommendationsitetype",0).equals("Grocery Store")){

		}}catch(Throwable e){
			
		}
		try{
		refreshrecommendation();
		}catch(Throwable e){
			
		}
		

	}
	
	public static void refreshbom() {

		bomgatewayservercount.requestFocus();
		bomelccount.requestFocus();
		bomsamcount.requestFocus();
		bomtscount.requestFocus();
		bomCTcount.requestFocus();
		bomtsdistance.requestFocus();
		bomctdistance.requestFocus();
		bompowerwiredistance.requestFocus();
		bommisccount.requestFocus();
		bomgatewayserverunitcost.requestFocus();
		bomsamunitcost.requestFocus();
		bomelcunitcost.requestFocus();
		bomtsunitcost.requestFocus();
		bomCTunitcost.requestFocus();
		bomtsdistanceunitcost.requestFocus();
		bomctdistanceunitcost.requestFocus();
		bompowerwiredistanceunitcost.requestFocus();
		bommisccountunitcost.requestFocus();

		getbomtotalcost();
	}

	public static void refreshrecommendation(){
		genericcheck1.requestFocus();
		genericcheck2.requestFocus();
		genericnotes1.requestFocus();
		genericnotes2.requestFocus();
		recommendationsitetype.requestFocus();
		
	}
	
	

	 public void addblankitemtomcrmeteringtable(String[] titles,
	 LinearLayout cont, TableLayout[] tl, final int sheetnum,
	 TableRow[][] tr, TextView[][] tvlabel, final AutoCompleteTextView[][] et,
	 TextView[][] tventry, Spinner[][] sp, ImageView[][] mcrsamdeletebutton) {
	
	 int itemnum = m[sheetnum];
	 
	 int fixedsheetofset = sheetnum;
	 tl[itemnum] = new TableLayout(this);
	 tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
	 LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
	 tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));
	
	 // tl[itemnum].setPadding(2, 2, 2, 2);
	 tr[itemnum][itemnum] = new TableRow(this);
	 tr[itemnum][itemnum].setLayoutParams(new TableLayout.LayoutParams(
	 LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
	 if ((itemnum % 2) == 0) {
	 tr[itemnum][itemnum].setBackgroundColor(Color
	 .parseColor(extremelylightblue));
	 } else {
	 tr[itemnum][itemnum].setBackgroundColor(Color
	 .parseColor(thelightestblue));
	 }
	
	 int num = 0;
	 for (num = 0; num < titles.length; num++) {
	
	  LayoutParams lp=columnlayoutparams(num);
	 
	
	 tvlabel[itemnum][num] = new TextView(this);
	 tvlabel[itemnum][num].setLayoutParams(lp);
	
	 tvlabel[itemnum][num].setText(titles[num]);
	
	 // Do different stuff for columns that aren't AutoCompleteTextView
	
	 if (num == u.cellx("A1")) {
	 // item number? textview
	
	 tventry[itemnum][num] = new TextView(this);
	 tventry[itemnum][num].setLayoutParams(lp);
	
	 tventry[itemnum][num].setText(u.s(itemnum + 1));
	
	 if (num >= maxx[fixedsheetofset]) {
	 maxx[fixedsheetofset] = num;
	 }
	 if (itemnum >= maxy[fixedsheetofset]) {
	 maxy[fixedsheetofset] = itemnum;
	 }
	
	 mcrstringarray[fixedsheetofset][itemnum][num] = u
	 .s(itemnum + 1);
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(tventry[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 // Panel Name
	
	 } else if (num == u.cellx("E1")) {
	 // spinner panel name
	 sp[itemnum][num] = new Spinner(this);
	 sp[itemnum][num].setLayoutParams(lp);
	
	 sp[itemnum][num].setAdapter(mcrpanelnamespinneradapter);
	 sp[itemnum][num]
	 .setOnItemSelectedListener(new OnItemSelectedListener() {
	 public void onItemSelected(AdapterView<?> parent,
	 View view, int position, long id) {
	
	 try {
	 String selection = ((TextView) view)
	 .getText().toString();
	 if (selection.equals("new")) {
	 // addnewvaluetolistdialog((Spinner)
	 // parent, VOLTAGE,
	 // mcrvoltagespinneradapter);
	
	 // open assets tab, and select type
	 // panel
	
	 } else {
	
	 int[] z = getsequencenumber(
	 mcrspinnerentries, parent);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
	 .getText().toString();
	
	 // Add location to next cell
	 int m = 0;
	 getassetnumberloop: for (int k = 0; k < i; k++) {
	
	 try {
	 if (assetname[k].et
	 .getText().toString()
	 .equals(selection)) {
	 m = k;
	 break getassetnumberloop;
	 } else {
	 m = -1;
	 }
	 } catch (NullPointerException e) {
	
	 }
	 }
	 if (!(m == -1)) {
	 et[itemnum][column + 1]
	 .setText(location[m].sp
	 .getSelectedItem()
	 .toString());
	 }
	 }
	 // Do Something
	
	 } catch (NullPointerException e) {
	 
	 }
	 }
	
	 public void onNothingSelected(AdapterView<?> parent) {
	 showToast("Spinner1: unselected");
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(sp[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 // Number Entries
	
	 } else if (num == u.cellx("G1")) {
	 // spinner voltage
	 sp[itemnum][num] = new Spinner(this);
	 sp[itemnum][num].setLayoutParams(lp);
	
	 sp[itemnum][num].setAdapter(mcrvoltagespinneradapter);
	 sp[itemnum][num]
	 .setOnItemSelectedListener(new OnItemSelectedListener() {
	 public void onItemSelected(AdapterView<?> parent,
	 View view, int position, long id) {
	
	 String selection = ((TextView) view).getText()
	 .toString();
	 if (selection.equals("new")) {
	 addnewvaluetolistdialog((Spinner) parent,
	 VOLTAGE, mcrvoltagespinneradapter,
	 MASTER_LIST);
	 } else {
	
	 int[] z = getsequencenumber(
	 mcrspinnerentries, parent);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
	 .getText().toString();
	 }
	 // Do Something
	
	 }
	
	 public void onNothingSelected(AdapterView<?> parent) {
	 showToast("Spinner1: unselected");
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(sp[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 } else if (num == u.cellx("H1")) {
	 sp[itemnum][num] = new Spinner(this);
	 sp[itemnum][num].setLayoutParams(lp);
	 sp[itemnum][num].setAdapter(mcrphspinneradapter);
	 sp[itemnum][num]
	 .setOnItemSelectedListener(new OnItemSelectedListener() {
	 public void onItemSelected(AdapterView<?> parent,
	 View view, int position, long id) {
	 String selection = ((TextView) view).getText()
	 .toString();
	 if (selection.equals("new")) {
	 addnewvaluetolistdialog((Spinner) parent,
	 PH, mcrphspinneradapter,
	 MASTER_LIST);
	 } else {
	 int[] z = getsequencenumber(
	 mcrspinnerentries, parent);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
	 .getText().toString();
	
	 // Make CT QTY=PH
	 mcrstringarray[sheetnum][itemnum][column + 5] =
	 mcrstringarray[sheetnum][itemnum][column];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column + 5;
	 }
	 // Do Something
	 }
	 }
	
	 public void onNothingSelected(AdapterView<?> parent) {
	 showToast("Spinner1: unselected");
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(sp[itemnum][num]);
	 }
	
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 // spinner
	
	 } else if (num == u.cellx("I1")) {
	 // spinner
	 sp[itemnum][num] = new Spinner(this);
	 sp[itemnum][num].setLayoutParams(lp);
	 sp[itemnum][num].setAdapter(mcrtransconfigspinneradapter);
	 sp[itemnum][num]
	 .setOnItemSelectedListener(new OnItemSelectedListener() {
	 public void onItemSelected(AdapterView<?> parent,
	 View view, int position, long id) {
	 String selection = ((TextView) view).getText()
	 .toString();
	 if (selection.equals("new")) {
	 addnewvaluetolistdialog((Spinner) parent,
	 TRANSCONFIG,
	 mcrtransconfigspinneradapter,
	 MASTER_LIST);
	 } else {
	 int[] z = getsequencenumber(
	 mcrspinnerentries, parent);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
	 .getText().toString();
	 // Do Something
	 }
	 }
	
	 public void onNothingSelected(AdapterView<?> parent) {
	 showToast("Spinner1: unselected");
	 }
	 });
	
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 tr[itemnum][itemnum].addView(sp[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 } else if (num == u.cellx("P1")) {
	 // spinner load type
	 sp[itemnum][num] = new Spinner(this);
	 sp[itemnum][num].setLayoutParams(lp);
	 sp[itemnum][num].setAdapter(mcrloadtypespinneradapter);
	 sp[itemnum][num]
	 .setOnItemSelectedListener(new OnItemSelectedListener() {
	 public void onItemSelected(AdapterView<?> parent,
	 View view, int position, long id) {
	 String selection = ((TextView) view).getText()
	 .toString();
	 if (selection.equals("new")) {
	 addnewvaluetolistdialog((Spinner) parent,
	 LOADTYPE,
	 mcrloadtypespinneradapter,
	 MASTER_LIST);
	 } else {
	 int[] z = getsequencenumber(
	 mcrspinnerentries, parent);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
	 .getText().toString();
	 // Do Something
	 }
	 }
	
	 public void onNothingSelected(AdapterView<?> parent) {
	 showToast("Spinner1: unselected");
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(sp[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][num], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	 } else if (num == u.cellx("J1")) {
	 et[itemnum][num] = new AutoCompleteTextView(this);
	 et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
	 et[itemnum][num].setLayoutParams(lp);
	 et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
	 | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	 et[itemnum][num]
	 .setOnFocusChangeListener(new OnFocusChangeListener() {
	
	 public void onFocusChange(View v, boolean hasFocus) {
	
	 if (hasFocus == false) {
	 int[] z = getsequencenumber(mcrentries, v);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 calculateandsetCTvalues(v, sheetnum,
	 itemnum, column);
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
	 .getText().toString();
	 }
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 // 
	
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(et[itemnum][num]);
	 }
	 } else if (num == u.cellx("B1")) {
	 et[itemnum][num] = new AutoCompleteTextView(this);
	 et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
	 | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	 et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
	 et[itemnum][num].setLayoutParams(lp);
	 et[itemnum][num]
	 .setOnFocusChangeListener(new OnFocusChangeListener() {
	
	 public void onFocusChange(View v, boolean hasFocus) {
	
	 if (hasFocus == false) {
	 int[] z = getsequencenumber(mcrentries, v);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
	 .getText().toString();
	
	 try {
	 if (mcrstringarray[sheetnum][itemnum][column]
	 .equals(mcrstringarray[sheetnum][itemnum - 1][column])) {
	 mcrstringarray[sheetnum][itemnum][column + 12] = u.s(u
	 .i(mcrstringarray[sheetnum][itemnum - 1][column + 12]) + 1);
	 } else {
	 mcrstringarray[sheetnum][itemnum][column + 12] = "1";
	 }
	 } catch (ArrayIndexOutOfBoundsException e) {
	 mcrstringarray[sheetnum][itemnum][column + 12] = "1";
	 } catch (NullPointerException e1) {
	 mcrstringarray[sheetnum][itemnum][column + 12] = "1";
	 }
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	
	 }
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 // 
	
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(et[itemnum][num]);
	 }
	
	 } else {
	
	 et[itemnum][num] = new AutoCompleteTextView(this);
	 et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
	 if ((num == u.cellx("B1")) || (num == u.cellx("M1"))
	 || (num == u.cellx("N1"))) {
	 et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
	 | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	 }
	 if (num == u.cellx("O1")) {
	 et[itemnum][num].setHint("Cable size:70mm², etc..");
	 }
	
	 et[itemnum][num].setLayoutParams(lp);
	 et[itemnum][num]
	 .setOnFocusChangeListener(new OnFocusChangeListener() {
	
	 public void onFocusChange(View v, boolean hasFocus) {
	
	 int[] z = getsequencenumber(mcrentries, v);
	 int sheetnum = z[0];
	 int itemnum = z[1];
	 int column = z[2];
	
	 if (column >= maxx[sheetnum]) {
	 maxx[sheetnum] = column;
	 }
	 if (itemnum >= maxy[sheetnum]) {
	 maxy[sheetnum] = itemnum;
	 }
	
	 mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
	 .getText().toString();
	
	 }
	 });
	
	 // tr[itemnum][num].addView(tvlabel[itemnum][num]);
	 // 
	
	 if (!((num == 3) || (num == 4) || (num == 10) || (num == 11)
	 || (num == 12) || (num == 13))) {
	 tr[itemnum][itemnum].addView(et[itemnum][num]);
	 }
	 // tl[itemnum].addView(tr[itemnum][itemnum], new
	 // TableLayout.LayoutParams(
	 // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	 }
	 }
	
	 
	 LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
	 mcrsamdeletebutton[itemnum][itemnum]=new ImageView(this);
	 mcrsamdeletebutton[itemnum][itemnum].setImageResource(R.drawable.deleteitem48);
	 mcrsamdeletebutton[itemnum][itemnum].setLayoutParams(lp);
	 tr[itemnum][itemnum].addView(mcrsamdeletebutton[itemnum][itemnum]);
	
	 tl[itemnum].addView(tr[itemnum][itemnum], new TableLayout.LayoutParams(
	 LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	 //
	
	 cont.addView(tl[itemnum]);
	 m[sheetnum]++;
	 try {
	 et[itemnum][u.cellx("B1")].requestFocus();
	 } catch (RuntimeException e) {
	
	 }
	 }


	public void addblankelcitemtotable(String[] titles, LinearLayout cont,
			TableLayout[] tl, final int sheetnum, TableRow[][] tr,
			TextView[][] tvlabel, AutoCompleteTextView[][] et, TextView[][] tventry,
			Spinner[][] sp, ImageView[][] plusbutton) {

		int itemnum = m[sheetnum];

		tl[itemnum] = new TableLayout(this);
		tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));
		tl[itemnum].setPadding(2, 2, 2, 2);

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		int num = 0;
		for (num = 0; num < titles.length; num++) {
			tr[itemnum][num] = new TableRow(this);
			tr[itemnum][num].setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			if ((num % 2) == 0) {
				tr[itemnum][num].setBackgroundColor(Color
						.parseColor(extremelylightblue));
			} else {
				tr[itemnum][num].setBackgroundColor(Color
						.parseColor(thelightestblue));
			}

			tvlabel[itemnum][num] = new TextView(this);
			tvlabel[itemnum][num].setLayoutParams(lp);
			tvlabel[itemnum][num].setTextSize(tinyfontsize);
			if (num >= 12) {
				tvlabel[itemnum][num].setPadding(20, 0, 0, 0);
			}
			tvlabel[itemnum][num].setText(titles[num]);

			et[itemnum][num] = new AutoCompleteTextView(this);
			et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
			et[itemnum][num].setLayoutParams(lp);
			et[itemnum][num].setTextSize(tinyfontsize);
			et[itemnum][num]
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {

							int[] z = getsequencenumber(mcrentries, v);
							int sheetnum = z[0];
							// convert itemnum to write on excel
							int itemnum = z[1] * 14;
							int column = z[2];

							if (column >= maxx[sheetnum]) {
								maxx[sheetnum] = column;
							}
							if (itemnum >= maxy[sheetnum]) {
								maxy[sheetnum] = itemnum;
							}

							mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
									.getText().toString();
						}
					});

			if (num == 12 && melc[sheetnum][itemnum] < 14) {

				LayoutParams lp1 = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, .9f);
				LayoutParams lp2 = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, .1f);
				et[itemnum][num].setLayoutParams(lp1);

				plusbutton[itemnum][num] = new ImageView(this);
				plusbutton[itemnum][num].setLayoutParams(lp2);
				plusbutton[itemnum][num]
						.setImageResource(R.drawable.plus_icon48);
				plusbutton[itemnum][num]
						.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								int[] z = getsequencenumber(plusbuttoniv, v);
								addblankelcsubitemtotable(mcrtabletitles[z[0]],
										mcrtable[z[0]], mcrtl[z[0]], z[0],
										mcrtr[z[0]], mcrtitles[z[0]],
										mcrentries[z[0]], mcrtventries[z[0]],
										mcrspinnerentries[z[0]],
										plusbuttoniv[z[0]], z[1]);

							}
						});

			}

			tr[itemnum][num].addView(tvlabel[itemnum][num]);
			tr[itemnum][num].addView(et[itemnum][num]);

			if (num == 12 && melc[sheetnum][itemnum] < 14) {
				tr[itemnum][num].addView(plusbutton[itemnum][num]);
			}
			tl[itemnum].addView(tr[itemnum][num], new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}

		// Add blank row after item

		/*
		 * tr[itemnum][num]=new TableRow(this);
		 * tr[itemnum][num].setLayoutParams( new TableLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 * tr[itemnum][num].setPadding(0, 0, 0, 4); tvlabel[itemnum][num] = new
		 * TextView(this); tvlabel[itemnum][num].setLayoutParams(lp);
		 * tr[itemnum][num].addView(tvlabel[itemnum][num]);
		 * 
		 * if ((num%2)==0){
		 * tr[itemnum][num].setBackgroundColor(Color.parseColor(
		 * extremelylightblue)); }else{
		 * tr[itemnum][num].setBackgroundColor(Color
		 * .parseColor(thelightestblue)); }
		 * 
		 * tl[itemnum].addView(tr[itemnum][num], new TableLayout.LayoutParams(
		 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); //
		 */

		cont.addView(tl[itemnum]);
		m[sheetnum]++;
		melc[sheetnum][itemnum]++;
	}

	public void addblankelcsubitemtotable(String[] titles, LinearLayout cont,
			TableLayout[] tl, final int sheetnum, TableRow[][] tr,
			TextView[][] tvlabel, AutoCompleteTextView[][] et, TextView[][] tventry,
			Spinner[][] sp, ImageView[][] plusbutton, final int itemnum) {

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		int num = 0;
		int offset = 0;
		for (num = 12; num < titles.length; num++) {
			// gap for remote login, which isn't on the spreadsheet

			double offsetdoub = (double) num
					+ (double) (melc[sheetnum][itemnum]) * (double) 6;
			
			offset = (int) offsetdoub;
			
			tr[itemnum][offset] = new TableRow(this);
			tr[itemnum][offset].setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			if (melc[sheetnum][itemnum] % 2 == 0) {
				if ((num % 2) == 0) {
					tr[itemnum][offset].setBackgroundColor(Color
							.parseColor(extremelylightblue));
				} else {
					tr[itemnum][offset].setBackgroundColor(Color
							.parseColor(thelightestblue));
				}
			} else {
				if ((num % 2) == 0) {
					tr[itemnum][offset].setBackgroundColor(Color
							.parseColor(lightblue));
				} else {
					tr[itemnum][offset].setBackgroundColor(Color
							.parseColor(verylightblue));
				}
			}
			tvlabel[itemnum][offset] = new TextView(this);
			tvlabel[itemnum][offset].setLayoutParams(lp);
			if (num >= 12) {
				tvlabel[itemnum][offset].setPadding(20, 0, 0, 0);
			}
			
			tvlabel[itemnum][offset].setText(titles[num]);

			et[itemnum][offset] = new AutoCompleteTextView(this);
			et[itemnum][offset].setImeOptions(EditorInfo.IME_ACTION_NEXT);
			et[itemnum][offset].setLayoutParams(lp);

			et[itemnum][offset]
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {

							int[] z = getsequencenumber(mcrentries, v);
							int sheetnum = z[0];
							int itemnum = z[1];
							int column = z[2];


							int subincrement = 0;
							if (column > 12) {
								subincrement = (int) (((double) column - (double) 12) / (double) 6);
								itemnum = itemnum * 14 + subincrement;
								column = column - (subincrement * 6);

							}

							if (column >= maxx[sheetnum]) {
								maxx[sheetnum] = column;
							}
							if (itemnum >= maxy[sheetnum]) {
								maxy[sheetnum] = itemnum;
							}

							mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
									.getText().toString();

						}
					});
			/*
			 * if(num==12){ plusbutton[itemnum][offset] = new ImageView(this);
			 * plusbutton[itemnum][offset].setLayoutParams(lp);
			 * plusbutton[itemnum
			 * ][offset].setImageResource(R.drawable.plus_icon48);
			 * plusbutton[itemnum][offset].setOnClickListener(new
			 * OnClickListener(){ public void onClick(View v) { int[]
			 * z=getsequencenumber(plusbuttoniv,v);
			 * 
			 * 
			 * } });
			 * 
			 * }
			 */

			if (!(num == 16)) {
				tr[itemnum][offset].addView(tvlabel[itemnum][offset]);
				tr[itemnum][offset].addView(et[itemnum][offset]);
				/*
				 * if(num==12){
				 * tr[itemnum][offset].addView(plusbutton[itemnum][offset]); }
				 */
				tl[itemnum].addView(tr[itemnum][offset],
						new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));

			}

		}

		// Add blank row after item

		/*
		 * tr[itemnum][offset]=new TableRow(this);
		 * tr[itemnum][offset].setLayoutParams( new TableLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 * tr[itemnum][offset].setPadding(0, 0, 0, 4); tvlabel[itemnum][offset]
		 * = new TextView(this); tvlabel[itemnum][offset].setLayoutParams(lp);
		 * tr[itemnum][num].addView(tvlabel[itemnum][offset]);
		 * 
		 * if ((num%2)==0){
		 * tr[itemnum][offset].setBackgroundColor(Color.parseColor
		 * (extremelylightblue)); }else{
		 * tr[itemnum][offset].setBackgroundColor(Color
		 * .parseColor(thelightestblue)); }
		 * 
		 * tl[itemnum].addView(tr[itemnum][offset], new
		 * TableLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT)); //
		 */

		// cont.addView(tl[itemnum]);
		melc[sheetnum][itemnum]++;
	}

	public void addblankitemtotable(String[] titles, LinearLayout cont,
			TableLayout[] tl, final int sheetnum, TableRow[][] tr,
			TextView[][] tvlabel, AutoCompleteTextView[][] et, TextView[][] tventry,
			Spinner[][] sp) {

		int itemnum = m[sheetnum];

		tl[itemnum] = new TableLayout(this);
		tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));
		tl[itemnum].setPadding(2, 2, 2, 2);

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		int num = 0;
		for (num = 0; num < titles.length; num++) {
			tr[itemnum][num] = new TableRow(this);
			tr[itemnum][num].setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			if ((num % 2) == 0) {
				tr[itemnum][num].setBackgroundColor(Color
						.parseColor(extremelylightblue));
			} else {
				tr[itemnum][num].setBackgroundColor(Color
						.parseColor(thelightestblue));
			}

			tvlabel[itemnum][num] = new TextView(this);
			tvlabel[itemnum][num].setLayoutParams(lp);
			tvlabel[itemnum][num].setTextSize(tinyfontsize);
			tvlabel[itemnum][num].setText(titles[num]);

			et[itemnum][num] = new AutoCompleteTextView(this);
			et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);

			et[itemnum][num].setLayoutParams(lp);
			et[itemnum][num].setTextSize(tinyfontsize);

			et[itemnum][num]
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						public void onFocusChange(View v, boolean hasFocus) {

							int[] z = getsequencenumber(mcrentries, v);
							int sheetnum = z[0];
							int itemnum = z[1];
							int column = z[2];

							if (column >= maxx[sheetnum]) {
								maxx[sheetnum] = column;
							}
							if (itemnum >= maxy[sheetnum]) {
								maxy[sheetnum] = itemnum;
							}

							mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
									.getText().toString();

						}
					});

			tr[itemnum][num].addView(tvlabel[itemnum][num]);
			tr[itemnum][num].addView(et[itemnum][num]);

			tl[itemnum].addView(tr[itemnum][num], new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}

		// Add blank row after item

		tr[itemnum][num] = new TableRow(this);
		tr[itemnum][num].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tr[itemnum][num].setPadding(0, 0, 0, 4);
		tvlabel[itemnum][num] = new TextView(this);
		tvlabel[itemnum][num].setLayoutParams(lp);
		tvlabel[itemnum][num].setTextSize(tinyfontsize);
		tr[itemnum][num].addView(tvlabel[itemnum][num]);

		if ((num % 2) == 0) {
			tr[itemnum][num].setBackgroundColor(Color
					.parseColor(extremelylightblue));
		} else {
			tr[itemnum][num].setBackgroundColor(Color
					.parseColor(thelightestblue));
		}

		tl[itemnum].addView(tr[itemnum][num], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//

		cont.addView(tl[itemnum]);
		m[sheetnum]++;
	}

	public int addblankitemtotablesinglecolumn(Sheet sheet, LinearLayout cont,
			int rowoftitles, TableLayout[] tl, final int itemnum,
			TableRow[][] tr, TextView[][] tv, CheckBox[] cb) {

		tl[itemnum] = new TableLayout(this);
		tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));
		tl[itemnum].setPadding(2, 2, 2, 2);

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 9f);
		LayoutParams cblp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);

		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		int leftMargin = 2;
		int topMargin = 2;
		int rightMargin = 2;
		int bottomMargin = 2;

		tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
				bottomMargin);

		lp.setMargins(2, 2, 0, 2);
		cblp.setMargins(0, 2, 2, 2);
		final int columns = 1;
		int a = 97;

		String blankcheck = "no";
		while (!blankcheck.equals("")) {
			blankcheck = u.readsheetandcell(sheet, (char) (columns + a),
					rowoftitles, "string", null);

			tr[itemnum][columns] = new TableRow(this);
			tr[itemnum][columns].setLayoutParams(tableRowParams);

			if ((rowoftitles % 2) == 0) {
				tr[itemnum][columns].setBackgroundColor(Color
						.parseColor(extremelylightblue));

			} else {
				tr[itemnum][columns].setBackgroundColor(Color
						.parseColor(thelightestblue));

			}

			tv[itemnum][columns] = new TextView(this);
			tv[itemnum][columns].setLayoutParams(lp);

			tv[itemnum][columns].setText(blankcheck);

			cb[rowoftitles - 7] = new CheckBox(this);
			cb[rowoftitles - 7].setLayoutParams(cblp);
			cb[rowoftitles - 7]
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							int number = getsequencenumber(
									mcrcheckbox[ELCCOMMISSIONINGCHECKLIST],
									buttonView)[0];

							if (isChecked) {

								mcrstringarray[ELCCOMMISSIONINGCHECKLIST][number][columns + 1] = "X";
							} else {
								mcrstringarray[ELCCOMMISSIONINGCHECKLIST][number][columns + 1] = "";
							}

							// set max x so that checks fill on excel table,
							// overshoot because im not sure, hint +2
							int Xscolumn = 2;
							maxx[ELCCOMMISSIONINGCHECKLIST] = Xscolumn + 2;

							if (maxy[ELCCOMMISSIONINGCHECKLIST] < (number + 1)) {
								maxy[ELCCOMMISSIONINGCHECKLIST] = number + 1;
							}
							
							

						}

					});

			tr[itemnum][columns].addView(tv[itemnum][columns]);
			tr[itemnum][columns].addView(cb[rowoftitles - 7]);

			tl[itemnum].addView(tr[itemnum][columns]);

			rowoftitles++;

		}
		cont.addView(tl[itemnum]);

		int incriment = itemnum + 1;
		return incriment;

	}

	public void addblankitemtomcrmeteringtabledb(String[] titles,
			LinearLayout cont, TableLayout[] tl, final int sheetnum,
			TableRow[][] tr, TextView[][] tvlabel, final AutoCompleteTextView[][] et,
			TextView[][] tventry, Spinner[][] sp,
			final ImageView[][] mcrsamdeletebutton) {

		//dont show column numbers
		//int ColumnLoadDescription=LOADDESCRIPTIONCOLUMN;
		//int ColumnPanelName=PANELNAMECOLUMN;
		int ColumnPanelCTSizeAmps=CTSIZEAMPSCOLUMN;
		int ColumnPanelCTQTY=CTQTYCOLUMN;
		int ColumnModbusAddress=MODBUSADDRESSCOLUMN;
		
		int itemnum = m[sheetnum];
		u.log("adding row to meteringlist"+itemnum);
		int fixedsheetofset = sheetnum;
		tl[itemnum] = new TableLayout(this);
		tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));

		// tl[itemnum].setPadding(2, 2, 2, 2);
		tr[itemnum][itemnum] = new TableRow(this);
		tr[itemnum][itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		if ((itemnum % 2) == 0) {
			tr[itemnum][itemnum].setBackgroundColor(Color
					.parseColor(extremelylightblue));
		} else {
			tr[itemnum][itemnum].setBackgroundColor(Color
					.parseColor(thelightestblue));
		}

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		int num = 0;
		for (num = 0; num < titles.length; num++) {

			lp=columnlayoutparams(num);

			tvlabel[itemnum][num] = new TextView(this);
			tvlabel[itemnum][num].setLayoutParams(lp);
			tvlabel[itemnum][num].setTextSize(tinyfontsize);
			tvlabel[itemnum][num].setText(titles[num]);

			// Do different stuff for columns that aren't AutoCompleteTextView

			if (num == ITEMNUMBERCOLUMN) {
				// item number? textview

				tventry[itemnum][num] = new TextView(this);
				tventry[itemnum][num].setLayoutParams(lp);
				tventry[itemnum][num].setTextSize(tinyfontsize);

				tventry[itemnum][num].setText(u.s(itemnum + 1));

				if (num >= maxx[fixedsheetofset]) {
					maxx[fixedsheetofset] = num;
				}
				if (itemnum >= maxy[fixedsheetofset]) {
					maxy[fixedsheetofset] = itemnum;
				}

				mcrstringarray[fixedsheetofset][itemnum][num] = u
						.s(itemnum + 1);
				int column = 0;
				if (MAESTROCOMMISSIONINGTABLOADED) {
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_METERINGLIST, itemnum,
							column, mcrstringarray[sheetnum][itemnum][column],
							DatabaseHandler.KEY_MCR_METERING_TITLES);
				}
				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(tventry[itemnum][num]);
				}
				// tl[itemnum].addView(tr[itemnum][num], new
				// TableLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				// Panel Name
				settextfromsource(sheetnum,tventry,itemnum,num,SOURCE);
			
			}  else if (num == PANELLOCATIONCOLUMN) {
				// spinner panel name
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);
				
				sp[itemnum][num].setAdapter(smalllocationspinneradapter);
				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {

								try {
									String selection = ((TextView) view)
											.getText().toString();

									int[] z = getsequencenumber(
											mcrspinnerentries, parent);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
									// Add location to next cell
//									int m = 0;
//									getassetnumberloop: for (int k = 0; k < i; k++) {
//
//										try {
//											if (assetname[k].et.getText()
//													.toString()
//													.equals(selection)) {
//												m = k;
//												break getassetnumberloop;
//											} else {
//												m = -1;
//											}
//										} catch (NullPointerException e) {
//
//										}
//									}
//									if (!(m == -1)) {
//										et[itemnum][column + 1]
//												.setText(location[m].sp
//														.getSelectedItem()
//														.toString());
//									}

									// Do Something

								} catch (NullPointerException e) {
									
								}
							}

							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				if (!((num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}
				// tl[itemnum].addView(tr[itemnum][num], new
				// TableLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == VOLTAGECOLUMN) {
				// spinner voltage
				
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);

				sp[itemnum][num].setAdapter(mcrvoltagespinneradapter);
				u.settexttoview(u.s(defaultvoltage), sp[itemnum][num]);

				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								
								try{
								String selection = ((TextView) view).getText()
										.toString();
								if (selection.equals("new")) {
									addnewvaluetolistdialog((Spinner) parent,
											VOLTAGE, mcrvoltagespinneradapter,
											MASTER_LIST);
								} else {

									int[] z = getsequencenumber(
											mcrspinnerentries, parent);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
								}
								// Do Something
								}catch(Throwable e){
									
								}
							}
						
							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);

				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}
				// Number Entries
				
				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == PHCOLUMN) {
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);
				sp[itemnum][num].setAdapter(mcrphspinneradapter);
				u.settexttoview(u.s(defaultphase), sp[itemnum][num]);
				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								try{
								((TextView) view).getText()
										.toString();
								// if (selection.equals("new")) {
								// addnewvaluetolistdialog((Spinner) parent,
								// PH, mcrphspinneradapter,
								// MASTER_LIST);
								// } else {
								int[] z = getsequencenumber(mcrspinnerentries,
										parent);
								int sheetnum = z[0];
								int itemnum = z[1];
								int column = z[2];

								if (column >= maxx[sheetnum]) {
									maxx[sheetnum] = column;
								}
								if (itemnum >= maxy[sheetnum]) {
									maxy[sheetnum] = itemnum;
								}

								mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
										.getText().toString();

								// Make CT QTY=PH
								mcrstringarray[sheetnum][itemnum][CTQTYCOLUMN] = mcrstringarray[sheetnum][itemnum][column];


								if (column >= maxx[sheetnum]) {
									maxx[sheetnum] = column + 5;
								}
								// Do Something
								if (MAESTROCOMMISSIONINGTABLOADED) {
									db.addorupdatemulticolumn(
											DatabaseHandler.TABLE_MCR_METERINGLIST,
											itemnum,
											column,
											mcrstringarray[sheetnum][itemnum][column],
											DatabaseHandler.KEY_MCR_METERING_TITLES);
								}
								// }
								}catch(Throwable e){
									
								}
							}

							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress ))) {
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}

				// tl[itemnum].addView(tr[itemnum][num], new
				// TableLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				// spinner
				// Number Entries
				

				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == TRANSCONFIGCOLUMN) {
				// spinner
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);
				sp[itemnum][num].setAdapter(mcrtransconfigspinneradapter);
				u.settexttoview(defaulttransconfig, sp[itemnum][num]);
				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								try{
								String selection = ((TextView) view).getText()
										.toString();
								if (selection.equals("new")) {
									addnewvaluetolistdialog((Spinner) parent,
											TRANSCONFIG,
											mcrtransconfigspinneradapter,
											MASTER_LIST);
								} else {
									int[] z = getsequencenumber(
											mcrspinnerentries, parent);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
									// Do Something
								}
								}catch(Throwable e){
									
								}
							}
						
							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
						});

				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					// tr[itemnum][num].addView(tvlabel[itemnum][num]);
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}
				// tl[itemnum].addView(tr[itemnum][num], new
				// TableLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				// Number Entries
				

				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == LOADTYPECOLUMN) {
				// spinner load type
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);
				sp[itemnum][num].setAdapter(mcrloadtypespinneradapter);
				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								try{
								String selection = ((TextView) view).getText()
										.toString();
								if (selection.equals("new")) {
									addnewvaluetolistdialog((Spinner) parent,
											LOADTYPE,
											mcrloadtypespinneradapter,
											MASTER_LIST);
								} else {
									int[] z = getsequencenumber(
											mcrspinnerentries, parent);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((TextView) view)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
									// Do Something
								}
								}catch(Throwable e){
								
							}
							}

							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}

				// Number Entries
				

				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == BREAKERSIZECOLUMN) {
				et[itemnum][num] = new AutoCompleteTextView(this);
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
				et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
				et[itemnum][num].setLayoutParams(lp);
				et[itemnum][num].setTextSize(tinyfontsize);
				et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				et[itemnum][num]
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							public void onFocusChange(View v, boolean hasFocus) {

								if (hasFocus == false) {
									int[] z = getsequencenumber(mcrentries, v);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

								 calculateandsetCTvalues(v, sheetnum,
											itemnum, column);

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
								}
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				// 

				if (!((num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(et[itemnum][num]);
				}
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
			} else if (num ==LOADSONEACHBREAKERCOLUMN ) {
				et[itemnum][num] = new AutoCompleteTextView(this);
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
				et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
				et[itemnum][num].setLayoutParams(lp);
				et[itemnum][num].setTextSize(tinyfontsize);
				et[itemnum][num]
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							public void onFocusChange(View v, boolean hasFocus) {

								if (hasFocus == false) {
									int[] z = getsequencenumber(mcrentries, v);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
											.getText().toString();
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
								}
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				// 

				if (!((num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(et[itemnum][num]);
				}
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
			}else if (num == CTSIZEPHYISICALCOLUMN) {
				// spinner load type
				sp[itemnum][num] = new Spinner(this);
				sp[itemnum][num].setLayoutParams(lp);
				sp[itemnum][num].setAdapter(mcrcttypespinneradapter);
				u.log("textchangelisteneradded");
				et[itemnum][num-2].addTextChangedListener(u.cttypewatcher(et[itemnum][num-2],sp[itemnum][num],Tabs1.ctdownsizepercent));				

				sp[itemnum][num]
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								try{
								String selection = ((TextView) view).getText()
										.toString();
								if (selection.equals("new")) {
									addnewvaluetolistdialog((Spinner) parent,
											CTTYPE,
											mcrcttypespinneradapter,
											MASTER_LIST);
								} else {
									int[] z = getsequencenumber(
											mcrspinnerentries, parent);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}
										u.log("before calling mcrarrsy");
								mcrstringarray[sheetnum][itemnum][column] = ((TextView) view).getText().toString() ;
								u.log("wantedmessage" + mcrstringarray[sheetnum][itemnum][column]);
								
									String newstring1=null,newstring2=null;
									String SPL1=null;
								//	String SPL2=null; 
									
									
									newstring1 = mcrstringarray[sheetnum][itemnum][CTSIZEPHYISICALCOLUMN] ;
									
									u.log("newstring1"+newstring1);
									newstring2= newstring1.split("-")[2];
									u.log("newstring2="+newstring2);
								//	newstring2= split(newstring1); 
									
								//	u.log("SPL1="+SPL1);
								//	newstring2 = SPL1.split("-")[1];
								
									mcrstringarray[sheetnum][itemnum][CTSIZEAMPSCOLUMN] = newstring2;
									
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
									//CTQTY =1 for RCS
									if(mcrstringarray[sheetnum][itemnum][column].contains("RCS")){
										mcrstringarray[sheetnum][itemnum][CTQTYCOLUMN]="1";
									}
									// Do Something
								}
							}catch(Throwable e){
								
							}
							}


							public void onNothingSelected(AdapterView<?> parent) {
								showToast("Spinner1: unselected");
							}
							
		
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				if (!((num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}

				settextfromsource(sheetnum,sp,itemnum,num,SOURCE);
			} else if (num == ELCNUMBERCOLUMN) {
				et[itemnum][num] = new AutoCompleteTextView(this);
				et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
				et[itemnum][num].setLayoutParams(lp);
				et[itemnum][num].setTextSize(tinyfontsize);
				et[itemnum][num]
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							public void onFocusChange(View v, boolean hasFocus) {

								if (hasFocus == false) {
									int[] z = getsequencenumber(mcrentries, v);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
											.getText().toString();

									try {
										if (mcrstringarray[sheetnum][itemnum][column]
												.equals(mcrstringarray[sheetnum][itemnum - 1][column])) {
											mcrstringarray[sheetnum][itemnum][MODBUSADDRESSCOLUMN] = u.s(u
													.i(mcrstringarray[sheetnum][itemnum - 1][MODBUSADDRESSCOLUMN]) + 1);
										} else {
											mcrstringarray[sheetnum][itemnum][MODBUSADDRESSCOLUMN] = "1";
										}
									} catch (ArrayIndexOutOfBoundsException e) {
										mcrstringarray[sheetnum][itemnum][MODBUSADDRESSCOLUMN] = "1";
									} catch (NullPointerException e1) {
										mcrstringarray[sheetnum][itemnum][MODBUSADDRESSCOLUMN] = "1";
									}

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
								}
							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				// 

				if (!( (num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(et[itemnum][num]);
				}
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
			} else {

				et[itemnum][num] = new AutoCompleteTextView(this);
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
				et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
				if ((num == ELCNUMBERCOLUMN) || (num == CTQTYCOLUMN)
						|| (num == MODBUSADDRESSCOLUMN)) {
					et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
							| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				}
				if (num == COMMENTSCOLUMN) {
					et[itemnum][num].setHint("Cable size:70mm², etc..");
				}
				if (num == LOADNAMECOLUMN) {
					List<String> loadnamestemp =new ArrayList<String>();
					loadnamestemp.clear();
					for(int ln =0;ln<loadnames.size();ln++){
						loadnamestemp.add(loadnames.get(ln));
						u.log("loadnames, "+loadnames.get(ln));
					}
//					u.log("loadnames size,"+loadnames.size());
//					u.log("loadnamestemp ,"+loadnamestemp.get(0));
//					u.log("loadnamestemp size,"+loadnamestemp.size());
					for(int l=0;l<assetnames.length;l++){
						try{
							if(! assetnames[l].equals(null)){
							loadnamestemp.add(assetnames[l]);
							u.log("loadnamestemp, "+assetnames[l]);
							}
						}catch(Throwable e){}
					}
//					u.log("loadnamestemp size, "+loadnamestemp.size());
//					u.log("loadnamestemp last, "+loadnamestemp.get(loadnamestemp.size()-1));
					ArrayAdapter loadnamesadapter = new ArrayAdapter<String>(this,
			                android.R.layout.simple_dropdown_item_1line, loadnamestemp);
					//SpinnersHash.put(loadnamesadapter, loadnames);
					et[itemnum][num].setAdapter(loadnamesadapter);
					
				}

				et[itemnum][num].setLayoutParams(lp);
				et[itemnum][num].setTextSize(tinyfontsize);
				et[itemnum][num]
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							public void onFocusChange(View v, boolean hasFocus) {
								if (hasFocus == false) {
									int[] z = getsequencenumber(mcrentries, v);
									int sheetnum = z[0];
									int itemnum = z[1];
									int column = z[2];

									if (column >= maxx[sheetnum]) {
										maxx[sheetnum] = column;
									}
									if (itemnum >= maxy[sheetnum]) {
										maxy[sheetnum] = itemnum;
									}

									mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
											.getText().toString();

									if (MAESTROCOMMISSIONINGTABLOADED) {
										db.addorupdatemulticolumn(
												DatabaseHandler.TABLE_MCR_METERINGLIST,
												itemnum,
												column,
												mcrstringarray[sheetnum][itemnum][column],
												DatabaseHandler.KEY_MCR_METERING_TITLES);
									}
								}
								if (hasFocus) {
								} else {
								}

							}
						});

				// tr[itemnum][num].addView(tvlabel[itemnum][num]);
				// 
				
				
				if (!((num == ColumnPanelCTSizeAmps) || (num == ColumnPanelCTQTY)|| (num == ColumnModbusAddress))) {
					tr[itemnum][itemnum].addView(et[itemnum][num]);
				}
				// tl[itemnum].addView(tr[itemnum][itemnum], new
				// TableLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
			}
		}

		// Add delete button
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		mcrsamdeletebutton[itemnum][itemnum] = new ImageView(this);
		mcrsamdeletebutton[itemnum][itemnum]
				.setImageResource(R.drawable.deleteitem48);
		mcrsamdeletebutton[itemnum][itemnum].setLayoutParams(lp);
		mcrsamdeletebutton[itemnum][itemnum]
				.setOnClickListener(new ImageView.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int[] z = getsequencenumber(mcrdeletebutton, v);
						int sheetnum = z[0];
						int itemnum = z[1];
						for (int a = itemnum; a < maxy[sheetnum]; a++) {
							for (int b = 0; b <= maxx[sheetnum]; b++) {
								

								try {

									mcrentries[sheetnum][a][b]
											.setText(mcrentries[sheetnum][a + 1][b]
													.getText().toString());
									mcrentries[sheetnum][a][b].requestFocus();

								} catch (Throwable e) {

								}

								try {
									if (!(b == 0)) {
										mcrtventries[sheetnum][a][b]
												.setText(mcrtventries[sheetnum][a + 1][b]
														.getText().toString());
										mcrtventries[sheetnum][a][b]
												.requestFocus();
									}
								} catch (Throwable e) {

								}

								try {
									mcrspinnerentries[sheetnum][a][b]
											.setSelection(mcrspinnerentries[sheetnum][a + 1][b]
													.getSelectedItemPosition());
									mcrspinnerentries[sheetnum][a][b]
											.requestFocus();
								} catch (Throwable e) {

								}

							}

						}
						
						//deleting last row
						int lastmcrrow=db.countrows(db.TABLE_MCR_METERINGLIST);
						u.log("lastmcrrow, "+lastmcrrow);
						
						mcrtable[sheetnum].removeView(mcrtable[sheetnum]
								.getChildAt(mcrtable[sheetnum].getChildCount() - 1));
						db.deleteSingleRow(DatabaseHandler.TABLE_MCR_METERINGLIST,
								u.s(lastmcrrow));
						maxy[sheetnum]--;
						m[sheetnum]--;

					}
				});

		tr[itemnum][itemnum].addView(mcrsamdeletebutton[itemnum][itemnum]);

		tl[itemnum].addView(tr[itemnum][itemnum], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//

		cont.addView(tl[itemnum]);
		m[sheetnum]++;
		try {
			et[itemnum][ELCNUMBERCOLUMN].requestFocus();
		} catch (Throwable e) {
			
		}
		try {
			et[itemnum][LOADSONEACHBREAKERCOLUMN].requestFocus();
		} catch (Throwable e) {
			
		}
	}
	public void virtualaddblankitemtomcrmeteringtabledb() {}

	public void addblankitemtomcrtempsensortabledb(String[] titles,
			LinearLayout cont, TableLayout[] tl, final int sheetnum,
			TableRow[][] tr, TextView[][] tvlabel, AutoCompleteTextView[][] et,
			TextView[][] tventry, Spinner[][] sp, final ImageView[][] mcrtempdeletebutton) {

		int itemnum = m[sheetnum];
		int fixedsheetofset = sheetnum;
		tl[itemnum] = new TableLayout(this);
		tl[itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tl[itemnum].setBackgroundColor(Color.parseColor(darkblue));
		// tl[itemnum].setPadding(2, 2, 2, 2);
		tr[itemnum][itemnum] = new TableRow(this);
		tr[itemnum][itemnum].setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		if ((itemnum % 2) == 0) {
			tr[itemnum][itemnum].setBackgroundColor(Color
					.parseColor(extremelylightblue));
		} else {
			tr[itemnum][itemnum].setBackgroundColor(Color
					.parseColor(thelightestblue));
		}

		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		int num = 0;
		for (num = 0; num < titles.length; num++) {

			tvlabel[itemnum][num] = new TextView(this);
			tvlabel[itemnum][num].setLayoutParams(lp);
			tvlabel[itemnum][num].setTextSize(tinyfontsize);

			tvlabel[itemnum][num].setText(titles[num]);

			// Do different stuff for columns that aren't AutoCompleteTextView
			u.log("itemnumber="+num);
		
			
			if (num == TEMPITEMNUMBERCOLUMN) {
				// textview
                u.log("num ,"+num);
				tventry[itemnum][num] = new TextView(this);
				tventry[itemnum][num].setLayoutParams(lp);
				tventry[itemnum][num].setTextSize(tinyfontsize);

				tventry[itemnum][num].setText(u.s(itemnum + 1));

				mcrstringarray[fixedsheetofset][itemnum][num] = u
						.s(itemnum + 1);

				int column = 0;
				if (MAESTROCOMMISSIONINGTABLOADED) {
					db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, itemnum, column,
							mcrstringarray[sheetnum][itemnum][column],
							DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
				}

				tr[itemnum][itemnum].addView(tventry[itemnum][num]);

				settextfromsource(sheetnum, tventry,itemnum,num,SOURCE);

			} else if(num == TEMPZONETYPECOLUMN){
				u.log("num ,"+num);
				u.log("new temperature sensor location field added");
				sp[itemnum][num] = new Spinner(this);

				sp[itemnum][num].setLayoutParams(lp);

				sp[itemnum][num].setAdapter(mcrtempzonetypespinneradapter);
				
				//et[itemnum][TEMPZONENAMECOLUMN].addTextChangedListener(u.zonetypewatcher(et[itemnum][TEMPZONENAMECOLUMN],sp[itemnum][num]));
				et[itemnum][TEMPCOMMENTSCOLUMN].addTextChangedListener(u.zonetypewatcher(et[itemnum][TEMPCOMMENTSCOLUMN],sp[itemnum][num]));
				
				String zontypestring=" ";
				try{
					u.log(correspondingtypes);
					u.log(commontemplocations);
					zontypestring=correspondingtypes.get(1);
					for(int c=0;c<commontemplocations.size();c++){
						if(et[itemnum][TEMPCOMMENTSCOLUMN].getText().toString().contains(commontemplocations.get(c))){
							zontypestring=correspondingtypes.get(c+1);
							break;
						}
					}
					
				}catch(Throwable e){
					e.printStackTrace();
				}
				
				u.log("zontypestring, "+zontypestring);
				u.settexttoview(zontypestring, sp[itemnum][num]);
				
				sp[itemnum][num].setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent,

				View view, int position, long id) {

				try{

				((TextView) view).getText().toString();

				int[] z = getsequencenumber(mcrspinnerentries,parent);

				int sheetnum = z[0];

				int itemnum = z[1];

				int column = z[2];



				if (column >= maxx[sheetnum]) {

				maxx[sheetnum] = column;

				}

				if (itemnum >= maxy[sheetnum]) {

				maxy[sheetnum] = itemnum;

				}



				mcrstringarray[sheetnum][itemnum][column] = 
						((TextView) view).getText().toString();




				if (column >= maxx[sheetnum]) {

				maxx[sheetnum] = column;

				}

				// Do Something

				if (MAESTROCOMMISSIONINGTABLOADED) {

				db.addorupdatemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST,itemnum,

				column,mcrstringarray[sheetnum][itemnum][column],

				DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);

				}

				// }

				}catch(Throwable e){


				}
				}public void onNothingSelected(AdapterView<?> parent) {
					showToast("Spinner1: unselected");
				}
				});
				tr[itemnum][itemnum].addView(sp[itemnum][num]);
				}else{
				u.log("num ,"+num);
				et[itemnum][num] = new AutoCompleteTextView(this);
				et[itemnum][num].setId(u.i(u.s(itemnum)+u.s(num)));
				et[itemnum][num].setImeOptions(EditorInfo.IME_ACTION_NEXT);
				
				if ((num == TEMPCOMMENTSCOLUMN)) {
					u.log("new temperature sensor location field added");
					
					et[itemnum][num].setAdapter(commontemplocationsadapter);
					et[itemnum][num].setThreshold(0);

				
				}
				if (num == TEMPFLOORNUMBERCOLUMN){
					new ArrayAdapter<String>(this,
			                android.R.layout.simple_dropdown_item_1line, floornumbers);
					et[itemnum][num].setAdapter(commontemplocationsadapter);
					et[itemnum][num].setDropDownAnchor(et[itemnum][num].getId());
				}
				
				
				
				if ((num == TEMPELCNUMBERCOLUMN)) {
					et[itemnum][num].setInputType(InputType.TYPE_CLASS_NUMBER
							| InputType.TYPE_NUMBER_FLAG_DECIMAL);
					
				}
				

				et[itemnum][num].setLayoutParams(lp);
				et[itemnum][num].setTextSize(tinyfontsize);
				settextfromsource(sheetnum,et,itemnum,num,SOURCE);
				et[itemnum][num]
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							public void onFocusChange(View v, boolean hasFocus) {

								int[] z = getsequencenumber(mcrentries, v);
								int sheetnum = z[0];
								int itemnum = z[1];
								int column = z[2];

								if (column >= maxx[sheetnum]) {
									maxx[sheetnum] = column;
								}
								if (itemnum >= maxy[sheetnum]) {
									maxy[sheetnum] = itemnum;
								}

								mcrstringarray[sheetnum][itemnum][column] = ((AutoCompleteTextView) v)
										.getText().toString();
								if (MAESTROCOMMISSIONINGTABLOADED) {
									db.addorupdatemulticolumn(
											DatabaseHandler.TABLE_MCR_TEMPSLIST,
											itemnum,
											column,
											mcrstringarray[sheetnum][itemnum][column],
											DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
								}
							}
						});
				if (num >= maxx[fixedsheetofset]) {
					maxx[fixedsheetofset] = num;
				}
				if (itemnum >= maxy[fixedsheetofset]) {
					maxy[fixedsheetofset] = itemnum;
				}
				tr[itemnum][itemnum].addView(et[itemnum][num]);

			}
		}

		// Add delete button
				lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
				mcrtempdeletebutton[itemnum][itemnum] = new ImageView(this);
				mcrtempdeletebutton[itemnum][itemnum]
						.setImageResource(R.drawable.deleteitem48);
				mcrtempdeletebutton[itemnum][itemnum].setLayoutParams(lp);
				mcrtempdeletebutton[itemnum][itemnum]
						.setOnClickListener(new ImageView.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								int[] z = getsequencenumber(mcrtemdeletebutton, v);
								int sheetnum = z[0];
								int itemnum = z[1];
								for (int a = itemnum; a < maxy[sheetnum]; a++) {
									for (int b = 0; b < maxx[sheetnum]; b++) {
										

										try {

											mcrentries[sheetnum][a][b]
													.setText(mcrentries[sheetnum][a + 1][b]
															.getText().toString());
											mcrentries[sheetnum][a][b].requestFocus();

										} catch (Throwable e) {

										}

										try {
											if (!(b == 0)) {
												mcrtventries[sheetnum][a][b]
														.setText(mcrtventries[sheetnum][a + 1][b]
																.getText().toString());
												mcrtventries[sheetnum][a][b]
														.requestFocus();
											}
										} catch (Throwable e) {

										}

										try {
											mcrspinnerentries[sheetnum][a][b]
													.setSelection(mcrspinnerentries[sheetnum][a + 1][b]
															.getSelectedItemPosition());
											mcrspinnerentries[sheetnum][a][b]
													.requestFocus();
										} catch (Throwable e) {

										}

									}

								}
								
								mcrtable[sheetnum].removeView(mcrtable[sheetnum]
										.getChildAt(mcrtable[sheetnum].getChildCount() - 1));

								db.deleteSingleRow(DatabaseHandler.TABLE_MCR_TEMPSLIST,
										u.s(maxy[sheetnum] + 1));
								
								maxy[sheetnum]--;
								m[sheetnum]--;

							}
						});

				tr[itemnum][itemnum].addView(mcrtempdeletebutton[itemnum][itemnum]);


		tl[itemnum].addView(tr[itemnum][itemnum], new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//

		cont.addView(tl[itemnum]);
		m[sheetnum]++;
	}

	
	public void meteringlistdb() {
		
		long time=System.currentTimeMillis();
		
		
		
		int sheetnum = METERINGLIST;

		mcrtable[sheetnum] = (LinearLayout) findViewById(R.id.mcrtable1container);
		mcrtable[sheetnum].setBackgroundColor(Color.parseColor(darkblue));
		mcrtable[sheetnum].setPadding(2, 2, 2, 2);

		mcrtabletitles[sheetnum] = DatabaseHandler.KEY_MCR_METERING_TITLES;

		TableLayout mcrtitlerow = (TableLayout) findViewById(R.id.mcrtitlerowtable);
		if(((ViewGroup)mcrtitlerow).getChildCount()>0){
			mcrtitlerow.removeAllViews();
		}
		mcrtitlerow.removeAllViews();
			
		TableRow titlerow = new TableRow(this);

		//int ColumnLoadDescription=3;
		//int ColumnPanelName=4;
		int ColumnPanelCTSizeAmps=CTSIZEAMPSCOLUMN;
		int ColumnPanelCTQTY=CTQTYCOLUMN;
		int ColumnModbusAddress=MODBUSADDRESSCOLUMN;
		
		Log.d("mcrtime","preloop "+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
		
		
		time=System.currentTimeMillis();
		for (int i = 0; i < mcrtabletitles[sheetnum].length; i++) {
			if (!( (i == ColumnPanelCTSizeAmps) || (i == ColumnPanelCTQTY) || (i == ColumnModbusAddress))) {
				TextView tv = new TextView(this);

				// Should use hashmap, but not enough time
				// make column1 tiny
				
				LayoutParams lp = columnlayoutparams(i);
				tv.setLayoutParams(lp);
				tv.setText(mcrtabletitlesneat[i]);
				tv.setTextSize(tinyfontsize);
				
				titlerow.addView(tv);
			}
		}
		Log.d("mcrtime","loop 1"+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
		time=System.currentTimeMillis();
		// Add gap for deletebutton
		TextView tv = new TextView(this);
		tv.setText(" ");
		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		tv.setLayoutParams(lp);
		titlerow.addView(tv);

		mcrtitlerow.addView(titlerow);
		int totalsamsindb=db.countrows(db.TABLE_MCR_METERINGLIST);
		int totalsitesindb=db.countrows(db.TABLE_SITEINFO);
		u.log("totalsamsindb"+totalsamsindb);	
		Log.d("mcrtime","entraloop"+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();
		
		time=System.currentTimeMillis();

		if(SOURCE==MCRDBVALUES){
			// panel location to spinners
			for(int pl=0;pl<totalsitesindb;pl++){
				try{	
					addlocationtospinners(pl,db.getvaluemulticolumnhybrid(db.TABLE_SITEINFO, "sitename", pl));
				}catch(Throwable e){
					
				}
			}
			
		
			for(int ca=0;ca<totalsamsindb;ca++){
			System.out.println("addmcr 4 in meteringlistdb (on start add more)");
			
			u.log("ca"+ca);	
			if(!(m[sheetnum]>=totalsamsindb)){
				addblankitemtomcrmeteringtabledb(mcrtabletitles[sheetnum],
					mcrtable[sheetnum], mcrtl[sheetnum], sheetnum,
					mcrtr[sheetnum], mcrtitles[sheetnum],
					mcrentries[sheetnum], mcrtventries[sheetnum],
					mcrspinnerentries[sheetnum], mcrdeletebutton[sheetnum]);
			}
		}}
		else{
			int j = 0;
			addmoreloop: while (true) {
				if(SOURCE==MCREXCELVALUES&&checkexcelformcrvalue(sheetnum,j)){
					u.log("adding MCR row");
					
					addblankitemtomcrmeteringtabledb(mcrtabletitles[sheetnum],
							mcrtable[sheetnum], mcrtl[sheetnum], sheetnum,
							mcrtr[sheetnum], mcrtitles[sheetnum],
							mcrentries[sheetnum], mcrtventries[sheetnum],
							mcrspinnerentries[sheetnum], mcrdeletebutton[sheetnum]);
	
					j++;
					
				}else{
					
					break addmoreloop;
				}
				
			}
			
			//m[sheetnum]++;
		}
		Log.d("mcrtime","loop 2"+"time:"+u.sl(time-System.currentTimeMillis())); time=System.currentTimeMillis();

	}
	public void virtualmeteringlistdb() {}
	public void addmcrbuttons(){

		reordermeteringtable = (Button) findViewById(R.id.reordermeteringtable);
		reordermeteringtable.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				refreshmcrtable(SOURCE);
			}

		});
		savetoexcel = (Button) findViewById(R.id.savetoexcel);
		savetoexcel.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				savemcrtabletoexcel();
			}

		});
		importfromexcel = (Button) findViewById(R.id.importfromexcel);
		importfromexcel.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				SOURCE=MCREXCELVALUES;
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				startActivityForResult(intent,
						GETIMPORTMCRLOCATION);
				
				
				
			}

		});
		addmcrmeteringlist = (Button) findViewById(R.id.addmcrmeteringlist);
		addmcrmeteringlist.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {

				int sheetnum = METERINGLIST;
				u.log("addmcr 1 in onclick");
				Tabs1.db.showfulldblog(Tabs1.db.TABLE_MCR_METERINGLIST);
				addblankitemtomcrmeteringtabledb(mcrtabletitles[sheetnum],
						mcrtable[sheetnum], mcrtl[sheetnum], sheetnum,
						mcrtr[sheetnum], mcrtitles[sheetnum],
						mcrentries[sheetnum], mcrtventries[sheetnum],
						mcrspinnerentries[sheetnum], mcrdeletebutton[sheetnum]);

				//
			}

		});
		addvirtualmcrmeteringlist = (Button) findViewById(R.id.addvirtualmcrmeteringlist);
		addvirtualmcrmeteringlist.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if(getCurrentFocus()!=null)getCurrentFocus().clearFocus();
				
				showvirtualmeterdialog(db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST));
			}

		});
	}
	

	public void tempsensordb() {
		int sheetnum = TEMPSENSORCOMMISSIONING;
		u.log("Sheetnum in tempsensordb "+sheetnum);
		mcrtable[sheetnum] = (LinearLayout) findViewById(R.id.mcrtempsensortablecontainer);
		mcrtable[sheetnum].setBackgroundColor(Color.parseColor(darkblue));
		mcrtable[sheetnum].setPadding(2, 2, 2, 2);

		mcrtabletitles[sheetnum] = DatabaseHandler.KEY_MCR_TEMPLIST_TITLES;

		TableLayout mcrtitlerow = (TableLayout) findViewById(R.id.mcrtemptitlerowtable);
		if(((ViewGroup)mcrtitlerow).getChildCount()>0){
			mcrtitlerow.removeAllViews();
		}
		mcrtitlerow.removeAllViews();
		TableRow titlerow = new TableRow(this);

		LayoutParams lptiny = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
		new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				4f);
		new LayoutParams(0, LayoutParams.WRAP_CONTENT,
				10f);

		for (int i = 0; i < mcrtabletitles[sheetnum].length; i++) {
			TextView tv = new TextView(this);
			tv.setLayoutParams(lptiny);
			
			tv.setText(mcrtemptabletitlesneat[i]);
			titlerow.addView(tv);
			// }
		}
		// Add gap for deletebutton
				TextView tv = new TextView(this);
				tv.setText(" ");
				LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
				tv.setLayoutParams(lp);
				titlerow.addView(tv);
		
		mcrtitlerow.addView(titlerow);
		u.log("adding sensor row");
		
		int j = 0;
		addmoreloop: while (true) {
			if((SOURCE==MCRDBVALUES&&checkdbformcrvalue(sheetnum,j))||(SOURCE==MCREXCELVALUES&&checkexcelformcrvalue(sheetnum,j))){
				u.log("adding sensor row");
				
				addblankitemtomcrtempsensortabledb(mcrtabletitles[sheetnum],
						mcrtable[sheetnum], mcrtl[sheetnum], sheetnum,
						mcrtr[sheetnum], mcrtitles[sheetnum],
						mcrentries[sheetnum], mcrtventries[sheetnum],
						mcrspinnerentries[sheetnum],mcrtemdeletebutton[sheetnum]);

				j++;
			}else{
				
				break addmoreloop;
			}
		}

		addmcrtempsensor = (Button) findViewById(R.id.addmcrtempsensor);
		addmcrtempsensor.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {

				int sheetnum = TEMPSENSORCOMMISSIONING;
				addblankitemtomcrtempsensortabledb(mcrtabletitles[sheetnum],
						mcrtable[sheetnum], mcrtl[sheetnum], sheetnum,
						mcrtr[sheetnum], mcrtitles[sheetnum],
						mcrentries[sheetnum], mcrtventries[sheetnum],
						mcrspinnerentries[sheetnum],mcrtemdeletebutton[sheetnum]);

			}

		});
	}


	

	public void destroymcrtable() {
		try {
			for (int sheet = METERINGLIST; sheet <= ELCCOMMISSIONINGCHECKLIST; sheet++) {
				mcrtable[sheet].removeAllViews();
				m[sheet] = 0;
			}
			try{
				virtualmcrtable.removeAllViews();
			}catch(Throwable e){
				
			}
			
		} catch (RuntimeException e) {
			runOnUiThread(new Runnable() {
				public void run() {
					for (int sheet = METERINGLIST; sheet <= ELCCOMMISSIONINGCHECKLIST; sheet++) {
						try{
							mcrtable[sheet].removeAllViews();
							m[sheet] = 0;
						}catch(Throwable e){
							
						}
					}
					try{
						virtualmcrtable.removeAllViews();
					}catch(Throwable e){
						
					}
				}
			});
		}
	}
	public void destroysitestab() {
		try {
			locationcontainer.removeAllViews();
			i = 0;
			//sitescount=0;
		}catch (Throwable e) {
			}
	}
	
	
	public void destroyassetstab() {
		try {
			assetscontainer.removeAllViews();
			i = 0;
			assetcount=0;
		}catch (Throwable e) {
			}
	}
	
	public void destroycomponentstsab() {
		try {
			componentscontainer.removeAllViews();
			c = 0;
			componentcount=0;
		}catch (Throwable e) {
			}
	}

	public void refreshmcrtable(int SOURCE) {
		if(SOURCE==MCRDBVALUES){
			//db.setorderassendingbycolumn(db.TABLE_MCR_METERINGLIST,db.KEY_MCR_METERING_TITLES[ELCNUMBERCOLUMN],db.KEY_MCR_METERING_TITLES[ITEMNUMBERCOLUMN]);
			//db.setorderassendingbycolumn(db.TABLE_MCR_TEMPSLIST,db.KEY_MCR_TEMPLIST_TITLES[TEMPELCNUMBERCOLUMN],db.KEY_MCR_TEMPLIST_TITLES[TEMPITEMNUMBERCOLUMN]);
			
		}
		destroymcrtable();

		if (SOURCE==MCREXCELVALUES){
			openmcrsheet();
		}
		createmcrtablefrom(SOURCE);
		
		if(db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST)>0){
			createvirtualmcrtablefrom(SOURCE);
		}
		if(SOURCE==MCREXCELVALUES){
			u.log("Workbook Closed");
			mcrworkbook.close();
			// resetting back to dbvalues
	
		}
		SOURCE=MCRDBVALUES;
	}

	public void savemcrtabletoexcel() {
		
		
		
		u.log("mcr task 2");
	//	String[][][] sortedmcr= sortingmcrstringarray(mcrstringarray);
		String tempMCRlocation=ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/"+foldername+".xls";
		
		File file = new File(tempMCRlocation);
		//delelting & making new metering list
		try{
			if(file.exists()){
				file.delete();
			}
			sortedmcr= sortingmcrstringarray(mcrstringarray); //what is in mcrstringarray? 
		
			u.copytemplatesfromAssets(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER],
					docslist, this.getApplicationContext());
	
			String mcrtemplatefilename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]
					+ "/xxx-Template.xls";
			File tempfile = new File(mcrtemplatefilename);
			mcrfilename = mcrtemplatefilename.replace(
					"xxx-Template.xls", foldername + ".xls");
			File newfile = new File(mcrfilename);
			tempfile.renameTo(newfile);
		}catch(Throwable e){
			e.printStackTrace();
		}
		file = new File(tempMCRlocation);
		u.log(tempMCRlocation);
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;
		u.log("mcr task 3");
		try {
			workbook = Workbook.getWorkbook(file, ws);
			mcrwriteworkbook = Workbook.createWorkbook(file, workbook);
			u.log("mcr task 4");
			
		// addloaddiagramtomcrstringarray();
		// addriserdiagramtomcrstringarray();

		for (int num = METERINGLIST; num <= ELCCOMMISSIONINGCHECKLIST; num++) {
			
			writeablemcrsheet[num] = mcrwriteworkbook.getSheet(num);

			// WriteTitles
			u.writecell(writeablemcrsheet[num], 0, 1, "Site: "
					+ sitename[1].et.getText().toString(), "string", null);
			u.writecell(writeablemcrsheet[num], 0, 2, "Updated by: "
					+ engineernamesp.getSelectedItem().toString(), "string",
					null);
			u.writecell(writeablemcrsheet[num], 0, 3, "Date & Time: "
					+ swdateet.getText().toString(), "string", null);

			for (int i = 0; i <= maxy[num]; i++) {
				for (int j = 0; j <= maxx[num]; j++) {
					int titlerow = 0;
					
					switch (num) {
					case METERINGLIST:
						titlerow = u.celly("A5");
						break;
					case TEMPSENSORCOMMISSIONING:
						titlerow = u.celly("A5");
						break;
					case ELCMODBUSADDRESSVALIDATION:
						titlerow = u.celly("A6");
						break;
					case SAMVALIDATIONCOMMISSIONING:
						titlerow = u.celly("A13");
						break;
					case ELCCOMMISSIONINGCHECKLIST:
						titlerow = u.celly("A6");
						break;
					}

					int x = j;
					int y = i + titlerow + 1;
					if(num==TEMPSENSORCOMMISSIONING){
						if(x==TEMPZONETYPECOLUMN){
							x++;
						}
					}
					try {
						if (!sortedmcr[num][i][j].equals(null)
								&& !sortedmcr[num][i][j].equals("")) {
							try {
								String whattowrite = sortedmcr[num][i][j];
								if (linkhash.containsKey(whattowrite)) {
									linkhash.containsKey(whattowrite);
									// u.writehyperlinkonly(writeablemcrsheet[num],
									// x, y,linkhash.get(whattowrite));
									u.writecellwlink(
											writeablemcrsheet[num],
											x,
											y,
											String.valueOf(sortedmcr[num][i][j]),
											"string", null, linkhash
													.get(whattowrite));
									
									
									
									
									
								} else {
									u.writecell(
											writeablemcrsheet[num],
											x,
											y,
											String.valueOf(whattowrite),
											"string", null);
										//asf	
								}
//						
								if(num==METERINGLIST&&x==u.cellx("C")){
									String realmetername=whattowrite;
									int FormulaColumn=2;
									int LoadtypeColumn=3;
									List<String> formulas=db.getcolumn(db.TABLE_MCR_VIRTUAL_METERINGLIST, FormulaColumn, db.KEY_VIRTUAL_MCR_METERING_TITLES);
									List<String> vrloadtypes=db.getcolumn(db.TABLE_MCR_VIRTUAL_METERINGLIST, LoadtypeColumn, db.KEY_VIRTUAL_MCR_METERING_TITLES);
									int columnsequence=0;
									for(int k=0; k<formulas.size(); k++){
										if (formulas.get(k).contains(realmetername)){
											
											String formula=formulas.get(k);
											String coeficientterm=formula.split(realmetername)[0].substring(formula.split(realmetername)[0].length()-1)+"1";
											
											//String coeficientterm=formula.split(realmetername)[0].charAt(formula.split(realmetername)[0].length()-1));
											String virtualmetername=formula.split("=")[0];
											String maestroloadtype=vrloadtypes.get(k);
											
											u.writecell(
													writeablemcrsheet[num],
													u.cellx(vrformula1column)+3*columnsequence,
													y,
													coeficientterm,
													"string", null);
											if (linkhash.containsKey(virtualmetername)) {
												u.writecellwlink(
														writeablemcrsheet[num],
														u.cellx(vrmetername1column)+3*columnsequence,
														y,
														virtualmetername,
														"string", null, linkhash
																.get(virtualmetername));
											}else{
												u.writecell(
														writeablemcrsheet[num],
														u.cellx(vrmetername1column)+3*columnsequence,
														y,
														virtualmetername,
														"string", null);
											}
											u.writecell(
													writeablemcrsheet[num],
													u.cellx(vrloadtype1column)+3*columnsequence,
													y,
													maestroloadtype,
													"string", null);
											columnsequence++;
										}
										//try to get second formula, but you might be on the last, so catch
										
									}
									
									
									
									
									
								}
							} catch (RuntimeException e) {
								
							}

						}
					} catch (RuntimeException e) {

					}
				}
			}

		}
		} catch (Throwable e) {
			e.printStackTrace();
			u.log("couldn't open workbook");
			showToastLong("Couldn't Save excel file");
			
		}
		try {
			u.log("writingworkbook");
			mcrwriteworkbook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			u.log("writingworkbook");
			mcrwriteworkbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

	}

	public void addnewvaluetoexcellists(int sheetnum, int column, String value,
			int siteormaster) {

		String listsfilename;
		if (siteormaster == SITE_LIST) {
			listsfilename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Site_Lists.xls";
		} else {
			listsfilename = masterfoldername + "Master_Lists.xls";
			
		}

		File file = new File(listsfilename);
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableWorkbook writeworkbook = null;
		try {
			writeworkbook = Workbook.createWorkbook(file, workbook);
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableSheet sheet;

		sheet = writeworkbook.getSheet(sheetnum);
		List<String> list = u.getcolumn(sheet, column, 1, true, true);
		int positionofnewitem = list.size() - 1;

		u.writecell(sheet, column, positionofnewitem, value, "string", null);

		try {
			writeworkbook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			writeworkbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

	}

	public void addnewvaluetoexcellistsgivenposition(int column, String value,
			int siteormaster, int positionofnewitem) {

		String listsfilename;
		if (siteormaster == SITE_LIST) {
			listsfilename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Site_Lists.xls";
		} else {
			listsfilename = Environment.getExternalStorageDirectory() + "/"
					+ masterfoldername + "/Master_Lists.xls";
			;
		}

		File file = new File(listsfilename);
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableWorkbook writeworkbook = null;
		try {
			writeworkbook = Workbook.createWorkbook(file, workbook);
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableSheet sheet;

		sheet = writeworkbook.getSheet(0);
		u.getcolumn(sheet, column, 1, true, true);

		u.writecell(sheet, column, positionofnewitem, value, "string", null);

		try {
			writeworkbook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			writeworkbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

	}

	public void openworksheets(){
	
	
		String filename = null;
		
	
//master		
		filename = masterfoldername + "Master_Lists.xls";
		File file = new File(filename);
		
		try {

			long size = file.length();
			int sizeint = (int) size;
			ws.setInitialFileSize(sizeint);

			masterlistworkbook = Workbook.getWorkbook(new File(filename), ws);
			
			
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		
		masterlistsheet1=null;
		masterlistsheet2=null;
		masterlistsheet3=null;
		masterlistsheet4=null;
		
		try{
		//	sitelistsheet1 = sitelistworkbook.getSheet(FIRSTSHEET);
			masterlistsheet1=masterlistworkbook.getSheet(FIRSTSHEET);
			masterlistsheet2=masterlistworkbook.getSheet(SECONDSHEET);
			masterlistsheet3=masterlistworkbook.getSheet(THIRDSHEET);
			masterlistsheet4=masterlistworkbook.getSheet(FOURTHSHEET);
			
		}catch(Throwable e){
			e.printStackTrace();
			//fix problem with FAT32, rename file then delete it, the pull new one from assets.
			
				File file1=new File(filename);
				File file2=new File(filename+"bad");
				file1.renameTo(file2);
				file2.delete();
				try {
					u.copymasterlistfromAssets(masterfoldername,
							ctx.getApplicationContext());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
	}

	public static List<String> loadlistfromxls(Sheet sheet, int columnindex, boolean withstartingblank, boolean withendingnew) {
		
		List<String> list = u.getcolumn(sheet, columnindex, 1, withstartingblank, withendingnew);
		return list;
	}

	public List<String> loadlistfromxlsgivenlistlength(int columnindex,
			Sheet sheet, int lengthwithnewandblank) {
		List<String> list = u.getcolumngivenlength(sheet, columnindex, 1, true,
				true, lengthwithnewandblank);

		return list;
	}

	public List<String> loadrecommendationlistfromreport(int sheetnum,
			int columnindex) {
		String filename = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Report.xls";
		File file = new File(filename);
		Workbook listworkbook = null;
		try {

			long size = file.length();
			int sizeint = (int) size;
			ws.setInitialFileSize(sizeint);

			listworkbook = Workbook.getWorkbook(file, ws);

			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		Sheet sheet = listworkbook.getSheet(sheetnum);
		List<String> list = new ArrayList<String>();
		try {
			list = u.getcolumn(sheet, columnindex, 1, true, true);
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		return list;
	}

	

	protected class OnResumeLoadTask extends AsyncTask<Object, Void, String> {
		ProgressDialog progressDialog;

		public OnResumeLoadTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}
			refreshmcrtable(SOURCE);

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			// progressDialog = new ProgressDialog(Tabs1.this);

			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

		}
	}

	protected class SaveMCRforLoadDiagramTask extends
			AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public SaveMCRforLoadDiagramTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}

			//savemcrtabletoexcel();

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

			startActivity(u.intent("LoadDiagramActivity"));
		}
	}

	protected class SaveMCRforRiserDiagramTask extends
			AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public SaveMCRforRiserDiagramTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}

			savemcrtabletoexcel();

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			
				startActivity(u.intent("RiserDiagramActivity"));
		}
	}
	protected class SaveMCRforMCRTask extends
	AsyncTask<Object, Void, String> {

ProgressDialog progressDialog;

public SaveMCRforMCRTask(ProgressDialog progressDialog) {
	this.progressDialog = progressDialog;
}

@Override
protected String doInBackground(Object... params) {
	try {
		Looper.prepare();
	} catch (RuntimeException e) {
	}

	savemcrtabletoexcel();

	return null;
}

// -- gets called just before thread begins
@Override
protected void onPreExecute() {
	progressDialog.show();
}

@Override
protected void onPostExecute(String result) {
	progressDialog.dismiss();
	File file = new File(MCRlocation);
	Intent intent = new Intent();
	
	
	intent.setDataAndType(Uri.fromFile(file),
			u.getMimeType(MCRlocation));

	intent.setAction(android.content.Intent.ACTION_VIEW);
	try {
		startActivity(intent);
	} catch (ActivityNotFoundException e) {
		
		showToast("No Apps Installed to Open this File");
	}


}
}
	
	protected class OnSaveConsumptionTask extends
			AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;
		String filenamestring;

		public OnSaveConsumptionTask(ProgressDialog progressDialog,
				String filenamestring) {
			this.filenamestring = filenamestring;
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {

			}
			try{
			WriteExcelFacilityEnergyConsumption(filenamestring);
			}catch(Throwable e){
				
			}
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						startActivityForResult(
								u.OpenOfficeFile(filenamestring),
								OFFICEACTIVITY);
					} catch (ActivityNotFoundException e) {
						showneedmoresoftwaredialog("Documents To Go",
								"com.dataviz.docstogo");
					}
				}
			});

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			// progressDialog = new ProgressDialog(Tabs1.this);

			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

		}

	}

	protected class SaveAllTask extends AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public SaveAllTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}
			u.databaseexport(internaldbpath,externaldbpath, ctx);
			savetask();
			//sugarsync();

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				removehourglassicons();
			} catch (Throwable e) {

			}
			if (ondrive) {
				ondrive = false;
				try {
					PackageManager pm = getPackageManager();
					Intent intent = pm
							.getLaunchIntentForPackage("com.sharpcast.sugarsync");
					startActivity(intent);
				} catch (NullPointerException e) {
					showneedmoresoftwaredialog("SugarSync",
							"com.sharpcast.sugarsync");
				}
			}
			if(saveincloud){
				saveincloud=false;
				u.databaseexport(internaldbpath,externaldbpath, ctx);
				sugarsync();
			}
		}
	}

	protected class SaveAllTaskWProgress extends
			AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public SaveAllTaskWProgress() {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}
			try {
				writereport();
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			writereportxl();
			
			savemcrtabletoexcel();
			
			WriteExcelFacilityEnergyConsumption(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]
					+ "/Facility_Energy_Consumption_Template.xls");
			// WriteExcelFacilityEnergyConsumption(getfilenamefromindex(10));
			// startActivity(u.intent("Writepreferencesfile"));
			

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

		}
	}

//	protected class MoveTask extends AsyncTask<Object, Void, String> {
//
//		ProgressDialog progressDialog;
//		int k;
//		TableLayout[] tl;
//
//		public MoveTask(ProgressDialog progressDialog, int k, TableLayout[] tl) {
//			this.progressDialog = progressDialog;
//			this.k = k;
//			this.tl = tl;
//		}
//
//		@Override
//		protected String doInBackground(Object... params) {
//			try {
//				Looper.prepare();
//			} catch (RuntimeException e) {
//			}
//			movedown(k, tl, true);
//
//			return null;
//		}
//
//		// -- gets called just before thread begins
//		@Override
//		protected void onPreExecute() {
//			progressDialog.show();
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			progressDialog.dismiss();
//		}
//	}

//	protected class DuplicateTask extends AsyncTask<Object, Void, String> {
//
//		ProgressDialog progressDialog;
//		int k;
//		TableLayout[] tl;
//
//		public DuplicateTask(ProgressDialog progressDialog, int k,
//				TableLayout[] tl) {
//			this.progressDialog = progressDialog;
//			this.k = k;
//			this.tl = tl;
//		}
//
//		@Override
//		protected String doInBackground(Object... params) {
//			try {
//				Looper.prepare();
//			} catch (RuntimeException e) {
//			}
//
//			duplicate(k, tl);
//
//			return null;
//		}
//
//		// -- gets called just before thread begins
//		@Override
//		protected void onPreExecute() {
//			progressDialog.show();
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			progressDialog.dismiss();
//		}
//	}

	protected class MOPSaveAllTask extends AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public MOPSaveAllTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {
			}
			
			u.databaseexport(internaldbpath,externaldbpath, ctx);
			savetask();

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			
			try{
				sethourglassicons();								
			}catch(Throwable e){
				
			}	
			
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				removehourglassicons();
			} catch (Throwable e) {

			}
			
			
			
			
		}
	}
	public void writereportxl() {

		int wb = REPORT;
		String filename = getfilenamefromindex(wb);

		File file = new File(filename);
		
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		WritableWorkbook writeworkbook = null;

		try {
			writeworkbook = Workbook.createWorkbook(file, workbook);
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

		WritableSheet[] writesheet = u.openallsheets(writeworkbook);

		// String wxy="A1";
		for (int h = 0; h <= writesheet.length; h++) {
			for (int i = 0; i <= wmaxy[wb][h]; i++) {
				for (int j = 0; j <= wmaxx[wb][h]; j++) {
					try {
						String whattowrite = u
								.gettextfromview(ViewHolder[wb][h][j][i]);
						if (linkhash.containsKey(whattowrite)) {
							linkhash.containsKey(whattowrite);
							u.writecellnewwhyperlink(writesheet[h],
									ViewHolder[wb][h][j][i], j, i,
									linkhash.get(whattowrite));
						} else {
							try {
								
								u.writecellnew(writesheet[h],
										ViewHolder[wb][h][j][i], j, i);
							} catch (Throwable e) {

							}
						}
					} catch (Throwable e) {
						

					}
				}
			}
		}
		try {
			writeworkbook.write();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			
		}
		try {
			writeworkbook.close();

		} catch (WriteException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	
		int wb2 = ASSETREPORT;
		String assetreport = getfilenamefromindex(wb2);
		File assetfile = new File(assetreport);
		try {
			u.copyFile(file, assetfile);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	protected class WriteReportExcelTask extends
			AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;
		String filenamestring;

		public WriteReportExcelTask(ProgressDialog progressDialog,
				String filenamestring) {
			this.filenamestring = filenamestring;
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {

			}
			writereportxl();
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						startActivityForResult(
								u.OpenOfficeFile(filenamestring),
								OFFICEACTIVITY);
					} catch (ActivityNotFoundException e) {
						showneedmoresoftwaredialog("Documents To Go",
								"com.dataviz.docstogo");
					}
				}
			});

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			// progressDialog = new ProgressDialog(Tabs1.this);

			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

		}

	}

	public String getfilenamefromindex(int index) {
		String filename = null;
		switch (index) {
		case REPORT:
			filename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Report.xls";
			break;

		case COMMISSIONING_REQUIREMENTS_TRACKER_TEMPLATE:
			filename = MCRlocation;
			break;

		case ENERGY_CONSUMPTION_TEMPLATE:
			filename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]
					+ "/Facility_Energy_Consumption_Template.xls";
			break;
		case LISTS:
			filename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Site_Lists.xls";
			break;
		case SITE_WALK_RECOMMENDATIONS_TABLE_TEMPLATE:
			filename = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]
					+ "/Site_Walk_Recommendations_Table_Template.xls";
			break;
			
		case ASSETREPORT:
			filename = ProjectDirectory + "/"
					+ basedirectory[ASSETSFOLDER]
					+ "/Report.xls";
			break;	
			
			
		}
		return filename;
	}

	public void link1cellto1view(int wb, int ws, String cell, View view) {
		String EL = cell;

		ViewHolder[wb][ws][u.cellx(EL)][u.celly(EL)] = view;
		if (u.cellx(EL) >= wmaxx[wb][ws]) {
			wmaxx[wb][ws] = u.cellx(EL);
		}
		if (u.celly(EL) >= wmaxy[wb][ws]) {
			wmaxy[wb][ws] = u.celly(EL);
		}

	}

	public void addlocationtospinners(int s, String sitenamestring) {
//		s = s + 1;
		
		try{
			u.log("trying to app panellocation, "+sitenamestring);
			if((!locationslist.contains(sitenamestring)) && (!sitenamestring.equals(null))
					 && (!sitenamestring.equals("")) && (!sitenamestring.equals("null"))){
				locationslist.add(sitenamestring);
				u.log("panellocation added, "+sitenamestring);
				try {
					smalllocationspinneradapter.notifyDataSetChanged();
					locationspinneradapter.notifyDataSetChanged();
				} catch (NullPointerException e) {

				}
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		
//		try {
//
//			locationslist.set(s, sitenamestring);
//
//		} catch (IndexOutOfBoundsException e) {
//			try {
//				locationslist.add(s, sitenamestring);
//			} catch (IndexOutOfBoundsException e1) {14326
		
//				locationslist.add(sitenamestring);
//			}
//		}

		

	}

	
	
	
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public class SiteAuditItem {

		// Associated variables
		private TextView label;
		private TextView tv;
		AutoCompleteTextView et;
		private TextView tv1;
		private AutoCompleteTextView et1;
		private TextView tv2;
		private AutoCompleteTextView et2;
		Spinner sp;
		private String string;
		private double doub = 0;
		private double doub1 = 0;
		private double doub2 = 0;
		public SiteAuditItem(TextView label, TextView tv, AutoCompleteTextView et,
				TextView tv1, AutoCompleteTextView et1, TextView tv2, AutoCompleteTextView et2,
				Spinner sp, String string, int integer, double doub,
				double doub1, double doub2) throws NullPointerException {

			this.label = label;
			this.tv = tv;
			this.et = et;
			this.tv1 = tv1;
			this.et1 = et1;
			this.tv2 = tv2;
			this.et2 = et2;
			this.sp = sp;
			this.string = string;
			this.doub = doub;
			this.doub1 = doub1;
			this.doub2 = doub2;

		}

		public SiteAuditItem(Context ctx) throws NullPointerException {
			label = new TextView(ctx);
			tv = new TextView(ctx);
			et = new AutoCompleteTextView(ctx);
			et.setSingleLine();
			et.setImeOptions(EditorInfo.IME_ACTION_NEXT);

			tv1 = new TextView(ctx);
			et1 = new AutoCompleteTextView(ctx);
			et1.setSingleLine();
			et1.setImeOptions(EditorInfo.IME_ACTION_NEXT);

			tv2 = new TextView(ctx);
			et2 = new AutoCompleteTextView(ctx);
			et2.setSingleLine();
			et2.setImeOptions(EditorInfo.IME_ACTION_NEXT);

			sp = new Spinner(ctx);

		}
	}

	private void initScreenLayout() {

		/*
		 * The following three command lines you can use depending upon the
		 * emulator device you are using.
		 */

		// 320 x 480 (Tall Display - HVGA-P) [default]
		// 320 x 240 (Short Display - QVGA-L)
		// 240 x 320 (Short Display - QVGA-P)

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels
		// +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);

		int height = dm.heightPixels;
		int width = dm.widthPixels;
		

		if (height < 400 || width < 300) {
			txtCalc.setTextSize(20);
		}

		if (width < 300) {
			btnMC.setTextSize(18);
			btnMR.setTextSize(18);
			btnMP.setTextSize(18);
			btnMM.setTextSize(18);
			btnBS.setTextSize(18);
			btnDivide.setTextSize(18);
			btnPlus.setTextSize(18);
			btnMinus.setTextSize(18);
			btnMultiply.setTextSize(18);
			btnEquals.setTextSize(18);
			btnPM.setTextSize(18);
			btnPerc.setTextSize(18);
			btnC.setTextSize(18);
			btnSqrRoot.setTextSize(18);
			btnNine.setTextSize(18);
			btnEight.setTextSize(18);
			btnSeven.setTextSize(18);
			btnSix.setTextSize(18);
			btnFive.setTextSize(18);
			btnFour.setTextSize(18);
			btnThree.setTextSize(18);
			btnTwo.setTextSize(18);
			btnOne.setTextSize(18);
			btnZero.setTextSize(18);
			btnDecimal.setTextSize(18);
		}

		btnZero.setTextColor(Color.MAGENTA);
		btnOne.setTextColor(Color.MAGENTA);
		btnTwo.setTextColor(Color.MAGENTA);
		btnThree.setTextColor(Color.MAGENTA);
		btnFour.setTextColor(Color.MAGENTA);
		btnFive.setTextColor(Color.MAGENTA);
		btnSix.setTextColor(Color.MAGENTA);
		btnSeven.setTextColor(Color.MAGENTA);
		btnEight.setTextColor(Color.MAGENTA);
		btnNine.setTextColor(Color.MAGENTA);
		btnPM.setTextColor(Color.MAGENTA);
		btnDecimal.setTextColor(Color.MAGENTA);

		btnMP.setTextColor(Color.BLUE);
		btnMM.setTextColor(Color.BLUE);
		btnMR.setTextColor(Color.BLUE);
		btnMC.setTextColor(Color.BLUE);
		btnBS.setTextColor(Color.BLUE);
		btnC.setTextColor(Color.RED);
		btnPerc.setTextColor(Color.BLACK);
		btnSqrRoot.setTextColor(Color.BLACK);
	}

	private void initControls() {
		// txtCalc = (AutoCompleteTextView) findViewById(R.id.txtCalc);
		btnZero = (Button) findViewById(R.id.btnZero);
		btnOne = (Button) findViewById(R.id.btnOne);
		btnTwo = (Button) findViewById(R.id.btnTwo);
		btnThree = (Button) findViewById(R.id.btnThree);
		btnFour = (Button) findViewById(R.id.btnFour);
		btnFive = (Button) findViewById(R.id.btnFive);
		btnSix = (Button) findViewById(R.id.btnSix);
		btnSeven = (Button) findViewById(R.id.btnSeven);
		btnEight = (Button) findViewById(R.id.btnEight);
		btnNine = (Button) findViewById(R.id.btnNine);
		btnPlus = (Button) findViewById(R.id.btnPlus);
		btnMinus = (Button) findViewById(R.id.btnMinus);
		btnMultiply = (Button) findViewById(R.id.btnMultiply);
		btnDivide = (Button) findViewById(R.id.btnDivide);
		btnEquals = (Button) findViewById(R.id.btnEquals);
		btnC = (Button) findViewById(R.id.btnC);
		btnDecimal = (Button) findViewById(R.id.btnDecimal);
		btnMC = (Button) findViewById(R.id.btnMC);
		btnMR = (Button) findViewById(R.id.btnMR);
		btnMM = (Button) findViewById(R.id.btnMM);
		btnMP = (Button) findViewById(R.id.btnMP);
		btnBS = (Button) findViewById(R.id.btnBS);
		btnPerc = (Button) findViewById(R.id.btnPerc);
		btnSqrRoot = (Button) findViewById(R.id.btnSqrRoot);
		btnPM = (Button) findViewById(R.id.btnPM);

		btnZero.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(0);
			}
		});
		btnOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(1);
			}
		});
		btnTwo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(2);
			}
		});
		btnThree.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(3);
			}
		});
		btnFour.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(4);
			}
		});
		btnFive.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(5);
			}
		});
		btnSix.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(6);
			}
		});
		btnSeven.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(7);
			}
		});
		btnEight.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(8);
			}
		});
		btnNine.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(9);
			}
		});
		btnPlus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(1);
			}
		});
		btnMinus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(2);
			}
		});
		btnMultiply.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(3);
			}
		});
		btnDivide.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(4);
			}
		});
		btnEquals.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(0);
			}
		});
		btnC.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				reset();
			}
		});
		btnDecimal.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleDecimal();
			}
		});
		btnPM.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handlePlusMinus();
			}
		});
		btnMC.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = 0;
			}
		});
		btnMR.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(memNum));
			}
		});
		btnMM.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = memNum
						- Double.parseDouble(txtCalc.getText().toString());
				operator = 0;
			}
		});
		btnMP.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = memNum
						+ Double.parseDouble(txtCalc.getText().toString());
				operator = 0;
			}
		});
		btnBS.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleBackspace();
			}
		});
		btnSqrRoot.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(Math.sqrt(Double.parseDouble(txtCalc
						.getText().toString()))));
			}
		});
		btnPerc.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(num
						* (0.01 * Double.parseDouble(txtCalc.getText()
								.toString()))));
			}
		});

		txtCalc.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int i, android.view.KeyEvent e) {
				if (e.getAction() == KeyEvent.ACTION_DOWN) {
					int keyCode = e.getKeyCode();

					// txtCalc.append("["+Integer.toString(keyCode)+"]");

					switch (keyCode) {
					case KeyEvent.KEYCODE_0:
						handleNumber(0);
						break;

					case KeyEvent.KEYCODE_1:
						handleNumber(1);
						break;

					case KeyEvent.KEYCODE_2:
						handleNumber(2);
						break;

					case KeyEvent.KEYCODE_3:
						handleNumber(3);
						break;

					case KeyEvent.KEYCODE_4:
						handleNumber(4);
						break;

					case KeyEvent.KEYCODE_5:
						handleNumber(5);
						break;

					case KeyEvent.KEYCODE_6:
						handleNumber(6);
						break;

					case KeyEvent.KEYCODE_7:
						handleNumber(7);
						break;

					case KeyEvent.KEYCODE_8:
						handleNumber(8);
						break;

					case KeyEvent.KEYCODE_9:
						handleNumber(9);
						break;

					case 43:
						handleEquals(1);
						break;

					case KeyEvent.KEYCODE_EQUALS:
						handleEquals(0);
						break;

					case KeyEvent.KEYCODE_MINUS:
						handleEquals(2);
						break;

					case KeyEvent.KEYCODE_PERIOD:
						handleDecimal();
						break;

					case KeyEvent.KEYCODE_C:
						reset();
						break;

					case KeyEvent.KEYCODE_SLASH:
						handleEquals(4);
						break;

					case KeyEvent.KEYCODE_DPAD_DOWN:
						return false;
					}
				}

				return true;
			}
		});
	}

	private void handleEquals(int newOperator) {
		if (hasChanged) {
			switch (operator) {
			case 1:
				num = num + Double.parseDouble(txtCalc.getText().toString());
				break;
			case 2:
				num = num - Double.parseDouble(txtCalc.getText().toString());
				break;
			case 3:
				num = num * Double.parseDouble(txtCalc.getText().toString());
				break;
			case 4:
				num = num / Double.parseDouble(txtCalc.getText().toString());
				break;
			}

			String txt = Double.toString(num);
			txtCalc.setText(txt);
			txtCalc.setSelection(txt.length());

			readyToClear = true;
			hasChanged = false;
		}

		operator = newOperator;
	}

	private void handleNumber(int num) {
		if (operator == 0)
			reset();

		String txt = txtCalc.getText().toString();
		if (readyToClear) {
			txt = "";
			readyToClear = false;
		} else if (txt.equals("0"))
			txt = "";

		txt = txt + Integer.toString(num);

		txtCalc.setText(txt);
		txtCalc.setSelection(txt.length());

		hasChanged = true;
	}

	private void setValue(String value) {
		if (operator == 0)
			reset();

		if (readyToClear) {
			readyToClear = false;
		}

		txtCalc.setText(value);
		txtCalc.setSelection(value.length());

		hasChanged = true;
	}

	private void handleDecimal() {
		if (operator == 0)
			reset();

		if (readyToClear) {
			txtCalc.setText("0.");
			txtCalc.setSelection(2);
			readyToClear = false;
			hasChanged = true;
		} else {
			String txt = txtCalc.getText().toString();

			if (!txt.contains(".")) {
				txtCalc.append(".");
				hasChanged = true;
			}
		}
	}

	private void handleBackspace() {
		if (!readyToClear) {
			String txt = txtCalc.getText().toString();
			if (txt.length() > 0) {
				txt = txt.substring(0, txt.length() - 1);
				if (txt.equals(""))
					txt = "0";

				txtCalc.setText(txt);
				txtCalc.setSelection(txt.length());
			}
		}
	}

	private void handlePlusMinus() {
		if (!readyToClear) {
			String txt = txtCalc.getText().toString();
			if (!txt.equals("0")) {
				if (txt.charAt(0) == '-')
					txt = txt.substring(1, txt.length());
				else
					txt = "-" + txt;

				txtCalc.setText(txt);
				txtCalc.setSelection(txt.length());
			}
		}
	}

	private void reset() {
		num = 0;
		txtCalc.setText("0");
		txtCalc.setSelection(1);
		operator = 1;
	}

	private void setupmastertabs() {

		tabHost = getTabHost();

		LayoutInflater.from(Tabs1.this).inflate(R.layout.tabs1,
				tabHost.getTabContentView(), true);

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Site Info")
				.setContent(R.id.siteinfotabview));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Components")
				.setContent(R.id.componentstabview));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Assets")
				.setContent(R.id.assetstabview));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Consumption")
				.setContent(R.id.consumptiontabview));
		tabHost.addTab(tabHost.newTabSpec("tab5")
				.setIndicator("Recommendations  (Beta)").setContent(R.id.recommendationstabview));
		tabHost.addTab(tabHost.newTabSpec("tab6")
				.setIndicator("Maestro Commissioning Requirements")
				.setContent(R.id.mcrtabview));
		tabHost.addTab(tabHost.newTabSpec("tab7").setIndicator("BOM")
				.setContent(R.id.bomtabview));
		// tabHost.addTab(tabHost.newTabSpec("tab6").setIndicator("Load Diagram").setContent(R.id.mcrtabview));
		tabHost.addTab(tabHost.newTabSpec("tab8").setIndicator("Notes")
				.setContent(R.id.notestabview));
		tabHost.addTab(tabHost.newTabSpec("tab9").setIndicator("File Explorer")
				.setContent(R.id.fileexplorertabview));
		tabHost.addTab(tabHost.newTabSpec("tab10").setIndicator("MOP")
				.setContent(R.id.moptabview));
		tabHost.addTab(tabHost.newTabSpec("tab11").setIndicator("Extras")
				.setContent(R.id.extrastabview));
	
//		tabHost.addTab(tabHost.newTabSpec("tab12")
//				.setIndicator("Progress Report").setContent(R.id.view12));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				
//				boolean COMPONENTSTABLOADED=false;
//				boolean ASSETSTABLOADED=false; 
//				boolean CONSUMPTIONTABLOADED=false; 
//				boolean RECOMMENDATIONSTABLOADED=false; 
//				boolean MAESTROCOMMISSIONINGTABLOADED=false; 
//				boolean BOMTABLOADED=false;
//				boolean FILEEXPLORERTABLOADED=false; 
//				boolean MOPTABLOADED=false; 
//				boolean EXTRASTABLOADED=false; 
//				boolean PROGRESSREPORTTABLOADED=false; 
//				boolean FLOORPLANTABLOADED=false; 
				switch (tabHost.getCurrentTab()) {
				case COMPONENTSTAB:
					if(!COMPONENTSTABLOADED){
						showToast("Loading");
						LoadComponentsTab();
						COMPONENTSTABLOADED=true;
					}
				break;
				case ASSETSTAB:
					if(!ASSETSTABLOADED){
						showToast("Loading");
						LoadAssetsTab();
						ASSETSTABLOADED=true;
					}
				break;
				case CONSUMPTIONTAB:
					if(!CONSUMPTIONTABLOADED){
						showToast("Loading");
						LoadConsumptionTab();
						CONSUMPTIONTABLOADED=true;
					}
				break;
				case RECOMMENDATIONSTAB:
					if(!RECOMMENDATIONSTABLOADED){
						showToast("Loading");
						LoadRecommendationsTab();
						RECOMMENDATIONSTABLOADED=true;
					}
				break;
				case MAESTROCOMMISSIONINGTAB:
					if(!MAESTROCOMMISSIONINGTABLOADED){
						showToast("Loading");
						setupmcrtabs();
						LoadMCRTab();
						MAESTROCOMMISSIONINGTABLOADED=true;
					}
				break;
				case BOMTAB:
					if(!BOMTABLOADED){
						createbom();
						try {
							fillbomtabwdbvalues();
						} catch (Throwable e) {
							
						}
						BOMTABLOADED=true;
					}
					addvaluestobom();
				break;
				case NOTESTAB:
					if(!NOTESTABLOADED){
						createnotestab();
						NOTESTABLOADED=true;
					}
				break;
				case FILEEXPLORERTAB:
					if(!FILEEXPLORERTABLOADED){
						setupdocstab();
						FILEEXPLORERTABLOADED=true;
					}
				break;
				case MOPTAB:
					if(!MOPTABLOADED){
						setupsubmittabbuttons();
						MOPTABLOADED=true;
					}
				break;
				case EXTRASTAB:
					if(!EXTRASTABLOADED){
						setupxtrastabbuttons();
						EXTRASTABLOADED=true;
					}
				break;
				
				default:
					break;
				}
			}
		});
	}

	public void setupmcrtabs() {
		double barheight = tabHost.getTabWidget().getChildAt(0)
				.getLayoutParams().height * 1.5;

		mTabHost = (TabHost) findViewById(R.id.mcrtabhost);

		mTabHost.setup();
		mcrview1 = (RelativeLayout) findViewById(R.id.mcrview1);
		mcrview1.setPadding(0, (int) barheight, 0, 0);
		mcrview2 = (RelativeLayout) findViewById(R.id.mcrview2);
		mcrview2.setPadding(0, (int) barheight, 0, 0);
		mcrview3 = (RelativeLayout) findViewById(R.id.mcrview3);
		mcrview3.setPadding(0, (int) barheight, 0, 0);
		mcrview4 = (RelativeLayout) findViewById(R.id.mcrview4);
		mcrview4.setPadding(0, (int) barheight, 0, 0);
		mcrview5 = (RelativeLayout) findViewById(R.id.mcrview5);
		mcrview5.setPadding(0, (int) barheight, 0, 0);

		mTabHost.addTab(mTabHost.newTabSpec("mcr_tab1")
				.setIndicator("Metering List").setContent(R.id.mcrview1));
		mTabHost.addTab(mTabHost.newTabSpec("mcr_tab2")
				.setIndicator("Temp Sensor Commissioning")
				.setContent(R.id.mcrview2));
		mTabHost.addTab(mTabHost.newTabSpec("mcr_tab3")
				.setIndicator("ELC-Modbus Address Validation (BETA)")
				.setContent(R.id.mcrview3));
		mTabHost.addTab(mTabHost.newTabSpec("mcr_tab4")
				.setIndicator("SAM Validation-Commissioning (BETA)")
				.setContent(R.id.mcrview4));
		mTabHost.addTab(mTabHost.newTabSpec("mcr_tab5")
				.setIndicator("ELC Commissioning Checklist (BETA)")
				.setContent(R.id.mcrview5));
		mTabHost.setCurrentTab(0);

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			try {
				LinearLayout relativeLayout = (LinearLayout) mTabHost
						.getTabWidget().getChildAt(i);
				TextView tabhostText = (TextView) relativeLayout.getChildAt(1);

				tabhostText.setTextSize(20.0f);
				mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (barheight);
			} catch (ClassCastException e) {
				RelativeLayout relativeLayout = (RelativeLayout) mTabHost
						.getTabWidget().getChildAt(i);
				TextView tabhostText = (TextView) relativeLayout.getChildAt(1);

				tabhostText.setTextSize(20.0f);
				mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (barheight);
			}

		}
		mTabHost.getViewTreeObserver()
				.removeOnTouchModeChangeListener(mTabHost);
		tabHost.getViewTreeObserver().removeOnTouchModeChangeListener(tabHost);
	}

	public void setuptabs() {
		setupmastertabs();
		//setupmcrtabs();
	}

	public void initializevalues() {
		linkhash = new HashMap<String, String>();
		for (int num = 0; num <= 5; num++) {
			m[num] = 0;
			maxx[num] = 0;
			maxy[num] = 0;
			for (int num2 = 0; num2 <= 10; num2++) {
				melc[num][num2] = 0;
			}
		}
		for (int num = 0; num < maxworkbooks; num++) {
			for (int numm = 0; numm < maxsheets; numm++) {
				wmaxx[num][numm] = -1;
				wmaxy[num][numm] = -1;
			}
		}
	}

	public void setupmainbuttons() {
		addbuilding = (Button) findViewById(R.id.addbuildingbutton);
		addfloor = (Button) findViewById(R.id.addfloorbutton);
		addzone = (Button) findViewById(R.id.addzonebutton);
		addroom = (Button) findViewById(R.id.addroombutton);
		addcomponent = (Button) findViewById(R.id.addcomponent);
		addasset = (Button) findViewById(R.id.addasset);
		addrecommendation = (Button) findViewById(R.id.addrecommendationbutton);
		importassets = (Button) findViewById(R.id.importassets);
		
		addbuilding.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				addlocation(BUILDING);
				tab0scrollview.post(new Runnable() {
					public void run() {
						tab0scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				sitename[s - 1].et.requestFocus();

			}
		});

		addfloor.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				addlocation(FLOOR);
				tab0scrollview.post(new Runnable() {
					public void run() {
						tab0scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				sitename[s - 1].et.requestFocus();
			}
		});

		addzone.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {

				addlocation(ZONE);
				tab0scrollview.post(new Runnable() {
					public void run() {
						tab0scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				sitename[s - 1].et.requestFocus();

			}
		});

		addroom.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {

				addlocation(ROOM);
				tab0scrollview.post(new Runnable() {
					public void run() {
						tab0scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				sitename[s - 1].et.requestFocus();

			}
		});
		addcomponent.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				u.log("makenewcomponent clicked");
				makenewcomponent();
				tab1scrollview.post(new Runnable() {
					public void run() {
						tab1scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				componentname[c - 1].et.requestFocus();

			}
		});
		addasset.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				u.log("makenewasset 4 when clicked");
				makenewasset();
				tab2scrollview.post(new Runnable() {
					public void run() {
						tab2scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				assetname[i - 1].et.requestFocus();

			}
		});
		
		importassets.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				u.log("import asset clicked");
				try {
					((Activity) ctx).getCurrentFocus().clearFocus();
				} catch (NullPointerException e) {

				}
				importassetdialogue();
				tab2scrollview.post(new Runnable() {
					public void run() {
						tab2scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				

			}
		});
		
		addrecommendation.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {

				addrecommendation();
				tab4scrollview.post(new Runnable() {
					public void run() {
						tab3scrollview.fullScroll(View.FOCUS_DOWN);
					}
				});
				recommendationnumbertextviewdetailed[r - 1].requestFocus();

			}
		});

	}

	public void setupsubmittabbuttons() {
		riserdiagrambutton = (ImageView) findViewById(R.id.riserdiagrambutton);
		mopmcrbutton = (ImageView) findViewById(R.id.mopmcrbutton);
		loaddiagrambutton = (ImageView) findViewById(R.id.loaddiagrambutton);
		mopfloorplanbutton = (ImageView) findViewById(R.id.mopfloorplanbutton);
		latexbutton = (ImageView) findViewById(R.id.latexbutton);

		savereportbutton = (ImageView) findViewById(R.id.save);
		elcconfigurationexportbutton = (ImageView) findViewById(R.id.elcconfiguration);
		uninstallbutton = (ImageView) findViewById(R.id.uninstall);
		syncreportbutton = (ImageView) findViewById(R.id.sync);
		emailreportbutton = (ImageView) findViewById(R.id.email);

		riserdiagrambutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {

				showToast("Saving Maestro Commissioning Data");

				SiteListsPath = ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Site_Lists.xls";

				OutputBitmapPath = ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Riser_Diagram.png";
				new SaveMCRforRiserDiagramTask(progressDialog).execute();

			}
		});

		mopmcrbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				u.log("mcr task 1");
				new SaveMCRforMCRTask(progressDialog).execute();
				
			}
		});

		loaddiagrambutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {

				showToast("Saving Maestro Commissioning Data");
				OutputBitmapPath = ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Load_Diagram.png";
				new SaveMCRforLoadDiagramTask(progressDialog).execute();

			}
		});

		mopfloorplanbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				showfloorplanlistmenu();

			}
		});

		latexbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				new MOPSaveAllTask(progressDialog).execute();
				latexsupport();
				moptypeselection();
				
				
				
				
				
				
			}
		});

		savereportbutton.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				
				savealldialog(false);
			}
		});

		elcconfigurationexportbutton
				.setOnClickListener(new ImageView.OnClickListener() {

					public void onClick(View arg0) {
						new SaveAllTask(progressDialog).execute();
						try {
							sethourglassicons();
						} catch (Throwable e) {

						}
						sitenumberinput();
						// getelcconfigurationvalues();
						// assigingelcconfigurationvalues(u.s(112));
						// for(int m=1;m<=maxelcconfig;m++){
						// 
						// createconfigfile(m);
						// }
					}
				});

		TableLayout projectfilesalertlayout = new TableLayout(this);
		projectfilesalertlayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		projectfilesalertlayout.setPadding(2, 2, 2, 2);
		AlertDialog.Builder projectfilesalert = new AlertDialog.Builder(this);
		projectfilesalert.setTitle("Do you want all Project files deleted  ");
		projectfilesalert.setView(projectfilesalertlayout);

		projectfilesalert.setPositiveButton("Delete All Projects",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String lhifolder = Environment
								.getExternalStorageDirectory().toString()
								+ "/lhi";
						File mainlhifile = new File(lhifolder);
						u.deleteDirectory(mainlhifile);
						Uri packageUri = Uri
								.parse("package:com.lhi.lhisiteaudit");
						Intent uninstallIntent = new Intent(
								Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
						startActivity(uninstallIntent);

					}
				}).setNegativeButton("Just Uninstall",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Uri packageUri = Uri
								.parse("package:com.lhi.lhisiteaudit");
						Intent uninstallIntent = new Intent(
								Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
						startActivity(uninstallIntent);
					}
				});

		final AlertDialog projectfilesalertdailogue = projectfilesalert
				.create();

		LayoutParams passwordfieldlayoutparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		new LayoutParams(500,
				677);

		TableLayout passwordalertlayout = new TableLayout(this);
		passwordalertlayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// mopdesignlayout.setBackgroundColor(Color.parseColor(darkblue));
		passwordalertlayout.setPadding(2, 2, 2, 2);

		TableRow passwordalerttablerow1 = new TableRow(this);
		passwordalerttablerow1.setLayoutParams(lpfw);

		final AutoCompleteTextView passwordAutoCompleteTextView = new AutoCompleteTextView(this);
		passwordAutoCompleteTextView.setLayoutParams(passwordfieldlayoutparams);

		passwordalerttablerow1.addView(passwordAutoCompleteTextView);

		passwordalertlayout.addView(passwordalerttablerow1);

		AlertDialog.Builder passwordalert = new AlertDialog.Builder(this);
		passwordalert.setTitle("Enter Password: ");
		passwordalert.setView(passwordalertlayout);

		passwordalert.setPositiveButton("CONFIRM",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (passwordAutoCompleteTextView.getText().toString()
								.equals("eLutions")) {
							projectfilesalertdailogue.show();
							passwordAutoCompleteTextView.setText("");
						}
						passwordAutoCompleteTextView.setText("");
					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		final AlertDialog passwordalertdialog = passwordalert.create();
		uninstallbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				new File(ProjectDirectory);
				// from=printFileNames(folder, null);
				passwordalertdialog.show();
			}

		});
		syncreportbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				File folder = new File(ProjectDirectory);
				// from=printFileNames(folder, null);

				from = printFileNames(folder);

				// new SaveAllTask().execute();

				String str = sitename[0].sp.getSelectedItem().toString();
				boolean containscharacters = false;
				for (int i = 0; i < str.length(); i++) {
					if (!(str.charAt(i) == ' ')) {
						containscharacters = true;

					}

				}
				if (containscharacters == true) {
					ondrive = true;
					new SaveAllTask(progressDialog).execute();
					try {
						sethourglassicons();
					} catch (Throwable e) {
						showToast("Project files Not Copied to SugarSync, Pls Do it manually");
					}
					foldersync();

					// DRIVETASKMODE = SYNCDRIVE;
					// new DriveTask(progressDialog, SYNCDRIVE).execute();
				} else {
					showToast("Company Name field has been left blank.");

				}

				// startActivityForResult(credential.newChooseAccountIntent(),
				// REQUEST_ACCOUNT_PICKER);
				// startActivity(u.intent("DriveActivity"));
			}
		});

		emailreportbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {
				new SaveAllTask(progressDialog).execute();

				zipall();
				Intent mail = new Intent(Intent.ACTION_SEND);
				mail.setType("text/html");
				mail.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "pwatson@lhi.com" });
				mail.putExtra(Intent.EXTRA_SUBJECT, "Send from Android");
				mail.putExtra(Intent.EXTRA_TEXT, "Sent from Android");
				mail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory().toString()
								+ "/" + masterfoldername + "zips" + "/"
								+ foldername + ".zip")));

				startActivity(Intent.createChooser(mail,
						"Select Email Software..."));

			}
		});
	}

	public void setupxtrastabbuttons() {
		skypebutton = (ImageView) findViewById(R.id.skypebutton);
		outlookwebaccessbutton = (ImageView) findViewById(R.id.outlookwebaccessbutton);
		companywebsitebutton = (ImageView) findViewById(R.id.companywebsitebutton);
		maestroverizonbutton = (ImageView) findViewById(R.id.maestroverizonbutton);
		googlemapsbutton = (ImageView) findViewById(R.id.googlemapsbutton);
		myscriptcalculatorbutton = (ImageView) findViewById(R.id.myscriptcalculatorbutton);

		skypebutton.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				try {
					PackageManager pm = getPackageManager();
					Intent intent = pm
							.getLaunchIntentForPackage("com.skype.raider");
					startActivity(intent);
				} catch (NullPointerException e) {
					showneedmoresoftwaredialog("Skype", "com.skype.raider");
				}
			}
		});
		outlookwebaccessbutton
				.setOnClickListener(new ImageView.OnClickListener() {

					public void onClick(View arg0) {
						String url = "https://tpamail.lhi.com/exchange";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
				});
		companywebsitebutton
				.setOnClickListener(new ImageView.OnClickListener() {

					public void onClick(View arg0) {
						String url = "http://www.lhi.com";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
				});
		maestroverizonbutton
				.setOnClickListener(new ImageView.OnClickListener() {

					public void onClick(View arg0) {
						/*
						 * String url = "http://maestro1.lhi.com/verizon";
						 * Intent i = new Intent(Intent.ACTION_VIEW);
						 * i.setData(Uri.parse(url)); startActivity(i);
						 */

						startActivity(u.intent("UploadToMaestro"));
					}
				});
		googlemapsbutton.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View arg0) {

				PackageManager pm = getPackageManager();
				Intent intent = pm
						.getLaunchIntentForPackage("com.google.android.apps.maps");
				startActivity(intent);
			}
		});
		myscriptcalculatorbutton
				.setOnClickListener(new ImageView.OnClickListener() {
					public void onClick(View arg0) {
						try {
							PackageManager pm = getPackageManager();
							Intent intent = pm
									.getLaunchIntentForPackage("com.visionobjects.calculator");
							startActivity(intent);
						} catch (NullPointerException e) {
							showneedmoresoftwaredialog("MyScript Calculator",
									"com.visionobjects.calculator");
						}
					}
				});

	}


	public void setupdocstab() {

		fileexplorerhome = (ImageView) findViewById(R.id.fileexplorerhome);
		fileexplorerup = (ImageView) findViewById(R.id.fileexplorerup);
		fileexplorerparent = (TextView) findViewById(R.id.fileexplorerparent);

		SetExplorerWindowtoFolder(ProjectDirectory);
		docsandreflistener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int selecteddocnum, long arg3) {
				try {
					File file = new File(pathfordocs.get(selecteddocnum));
					if (file.isDirectory()) {
						if (file.canRead())
							SetExplorerWindowtoFolder(pathfordocs
									.get(selecteddocnum));
						else {
							new AlertDialog.Builder(Tabs1.this)
									.setTitle(
											"[" + file.getName()
													+ "] folder can't be read!")
									.setPositiveButton(
											"OK",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
												}
											}).show();
						}
					} else {

						Intent intent = new Intent();
						
						
						intent.setDataAndType(Uri.fromFile(file),
								u.getMimeType(pathfordocs.get(selecteddocnum)));

						intent.setAction(android.content.Intent.ACTION_VIEW);
						try {
							startActivity(intent);
						} catch (ActivityNotFoundException e) {
							
							showToast("No Apps Installed to Open this File");
						}
					}
				} catch (NullPointerException e) {

				}
			}
		};
		fileexplorerlistview.setOnItemClickListener(docsandreflistener);
	}

	public void intializeviewsfromxml() {

		parentlayout = (RelativeLayout) findViewById(R.id.parentlayout);
		siteinfotabview = (RelativeLayout) findViewById(R.id.siteinfotabview);
		componentstabview = (RelativeLayout) findViewById(R.id.componentstabview);
		assetstabview=(RelativeLayout) findViewById(R.id.assetstabview);
		consumptiontabview = (RelativeLayout) findViewById(R.id.consumptiontabview);
		recommendationstabview = (RelativeLayout) findViewById(R.id.recommendationstabview);
		mcrtabview=(RelativeLayout) findViewById(R.id.mcrtabview);
		fileexplorertabview = (LinearLayout) findViewById(R.id.fileexplorertabview);
		tab0scrollview = (ScrollView) findViewById(R.id.tab0scrollview);
		tab1scrollview = (ScrollView) findViewById(R.id.tab1scrollview);
		tab2scrollview = (ScrollView) findViewById(R.id.tab2scrollview);
		tab3scrollview = (ScrollView) findViewById(R.id.tab3scrollview);
		tab4scrollview = (ScrollView) findViewById(R.id.tab4scrollview);

		locationcontainer = (LinearLayout) findViewById(R.id.locationcontainer);
		componentscontainer = (LinearLayout) findViewById(R.id.componentscontainer);
		assetscontainer = (LinearLayout) findViewById(R.id.assetscontainer);
		recommendationscontainer = (LinearLayout) findViewById(R.id.recommendationscontainer);

		recommendationscalcstable = (TableLayout) findViewById(R.id.recommendationscalcstable);
		sitetyperecommendations = (TableLayout) findViewById(R.id.sitetyperecommendations1);
		
		ImageView bg = (ImageView) findViewById(R.id.bg);
		bg.setAlpha(30);
	}

	public void intializecalculator() {
		conscalcfilm = (RelativeLayout) findViewById(R.id.conscalcfilm);
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		calc = inflater.inflate(R.layout.calculator, null);
		
		makecalculatorbutton();
	}

	public void applyfontstoeverything() {
		// *****************************************************************
		// *****************Apply Font to Everything*************************

		font = Typeface.createFromAsset(getAssets(), "verdanab.ttf");

		applyFonts(parentlayout, null, fontsize, recommendationstabview, mcrtabview,
				fileexplorerparent);

		// ************************************************************
		// ************************************************************

	}

	public void addallpictures() {
		try{
			addpictures(SITETAB);
		}catch(Throwable e){
			
		}
		
		try{
			addpictures(COMPONENTSTAB);
		}catch(Throwable e){
			
		}
		
		try{
			addpictures(ASSETSTAB);
		}catch(Throwable e){
			
		}
	}

	public void savealldialog(final boolean andexit) {
		LinearLayout valueet;
		valueet = new LinearLayout(this);
		valueet.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		valueet.setOrientation(LinearLayout.HORIZONTAL);
		valueet.setGravity(Gravity.CENTER_HORIZONTAL);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Save")
				.setMessage("Would you like to save your progress?")
				.setView(valueet).setCancelable(true)
				.setIcon(R.drawable.ic_launcher);
		
			builder.setPositiveButton("SAVE LOCAL AND TO CLOUD",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							savelocalandcloud(dialog, andexit);
						}
					})
					.setNeutralButton("SAVE LOCAL",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									savelocal(dialog, andexit);
								}
							})
					.setNegativeButton("DON'T SAVE",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									foldersync();
									dialog.cancel();
									if(andexit){
										finish();
									}

								}
							});
		
		builder.create().show();
	}

	public void getscreensize() {
		Point size = new Point();
		WindowManager w = getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			w.getDefaultDisplay().getSize(size);
			screenheight = size.y;
			screenwidth = size.x;
			
		} else {
			Display d = w.getDefaultDisplay();
			d.getWidth();
			screenheight = d.getHeight();
			screenwidth = d.getWidth();
		}
	}

	public void addpictureviewstorow(int k, int type) {
		if (type==SITETAB) {
			sitepicnum[k] = countpics(type, k);

			int[] numr = new int[maximagesperfolder];
			TableRow.LayoutParams str5lp = new TableRow.LayoutParams();
			str5lp.weight = (1 / 4);
			for (numr[k] = 0; numr[k] <= sitepicnum[k]; numr[k]++) {
				sitepicture[k][numr[k]] = new ImageView(this);
				sitepicture[k][numr[k]].setLayoutParams(lpfw);
				sitepicture[k][numr[k]].setLayoutParams(str5lp);

				str5[k].addView(sitepicture[k][numr[k]]);
				
			}
		}
		if (type==COMPONENTSTAB) {
			componentpicnum[k] = countpics(type, k);

			int[] numr = new int[maximagesperfolder];
			TableRow.LayoutParams str5lp = new TableRow.LayoutParams();
			str5lp.weight = (1 / 4);
			for (numr[k] = 0; numr[k] <= componentpicnum[k]; numr[k]++) {
				componentpicture[k][numr[k]] = new ImageView(this);
				componentpicture[k][numr[k]].setLayoutParams(lpfw);
				componentpicture[k][numr[k]].setLayoutParams(str5lp);

				tr10[k].addView(componentpicture[k][numr[k]]);
				
			}
		}
		if (type==ASSETSTAB) {
			assetpicnum[k] = countpics(type, k);

			int[] numr = new int[maximagesperfolder];
			TableRow.LayoutParams str5lp = new TableRow.LayoutParams();
			str5lp.weight = (1 / 4);
			for (numr[k] = 0; numr[k] <= assetpicnum[k]; numr[k]++) {
				picture[k][numr[k]] = new ImageView(this);
				picture[k][numr[k]].setLayoutParams(lpfw);
				picture[k][numr[k]].setLayoutParams(str5lp);

				tr10[k].addView(picture[k][numr[k]]);
				
			}
		}
	}

	public int gettype(View view) {
		int type = -1;

		List<View> locationchildren = u.getallchildrenandviewgroups(siteinfotabview);
		for (int n = 0; n < locationchildren.size(); n++) {
			if (view == locationchildren.get(n)) {
				type = SITETAB;
			}
		}

		if (type == -1) {

			List<View> assetchildren = u.getallchildrenandviewgroups(componentstabview);
			for (int n = 0; n < assetchildren.size(); n++) {
				if (view == assetchildren.get(n)) {
					type = COMPONENTSTAB;
				}
			}
		}
		if (type == -1) {

			List<View> assetchildren = u.getallchildrenandviewgroups(assetstabview);
			for (int n = 0; n < assetchildren.size(); n++) {
				if (view == assetchildren.get(n)) {
					type = ASSETSTAB;
				}
			}
		}
		if (type == -1) {

			List<View> recommendationchildren = u
					.getallchildrenandviewgroups(recommendationstabview);
			for (int n = 0; n < recommendationchildren.size(); n++) {
				if (view == recommendationchildren.get(n)) {
					type = RECOMMENDATIONSTAB;
				}
			}
		}
		return type;

	}

	public void siteauditdoubfind(View view) {
		int type = gettype(view);
		if (type==SITETAB) {
			for (int n = 0; n < s; n++) {
				

				if (sitesize[n].et1 == view) {
					sitesize[n].doub1 = u.d(((AutoCompleteTextView) view).getText()
							.toString());
				}
				if (sitesize[n].et2 == view) {
					sitesize[n].doub2 = u.d(((AutoCompleteTextView) view).getText()
							.toString());
				}

			}
		}
		if (type==ASSETSTAB) {
			for (int n = 0; n < i; n++) {
				if (servicearea[n].et1 == view) {
					servicearea[n].doub1 = u.d(((AutoCompleteTextView) view).getText()
							.toString());
				}
				if (servicearea[n].et2 == view) {
					servicearea[n].doub2 = u.d(((AutoCompleteTextView) view).getText()
							.toString());
				}
			}
		}
	}

	public void showcalcbuttoncoi(View v) {
		int[] loc = new int[2];
		int[] scrollloc = new int[2];

		v.getLocationOnScreen(loc);
		ViewParent nextparent = v.getParent();
		while (!(nextparent instanceof RelativeLayout)) {
			nextparent = nextparent.getParent();
		}
		((RelativeLayout) nextparent).getLocationInWindow(scrollloc);
		// tab2scrollview.getLocationOnScreen(scrollloc);

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);

		rllp.leftMargin = loc[0] - scrollloc[0];
		rllp.topMargin = loc[1] - scrollloc[1] + v.getHeight();
		try {
			((ViewGroup) calc.getParent()).removeView(calc);
		} catch (NullPointerException e) {

		}
		try {
			((ViewGroup) calculatorbuttoncoi.getParent())
					.removeView(calculatorbutton);
		} catch (NullPointerException e) {

		}

		try {
			((RelativeLayout) nextparent).addView(calculatorbuttoncoi, rllp);
		} catch (IllegalStateException e) {

		}

	}

	public void showcalcbutton(View v) {
		int[] loc = new int[2];
		int[] scrollloc = new int[2];

		v.getLocationOnScreen(loc);
		ViewParent nextparent = v.getParent();
		while (!(nextparent instanceof RelativeLayout)) {
			nextparent = nextparent.getParent();
		}
		((RelativeLayout) nextparent).getLocationInWindow(scrollloc);
		// tab2scrollview.getLocationOnScreen(scrollloc);

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);

		rllp.leftMargin = loc[0] - scrollloc[0];
		rllp.topMargin = loc[1] - scrollloc[1] + v.getHeight();
		try {
			((ViewGroup) calc.getParent()).removeView(calc);
		} catch (NullPointerException e) {

		}
		try {
			((ViewGroup) calculatorbutton.getParent())
					.removeView(calculatorbutton);
		} catch (NullPointerException e) {

		}

		((RelativeLayout) nextparent).addView(calculatorbutton, rllp);

		txtCalc = (AutoCompleteTextView) v;
	}

	public void removecalcorbutton(boolean hasFocus) {
		if (!(hasFocus)) {

			try {
				((ViewGroup) calculatorbuttoncoi.getParent())
						.removeView(calculatorbuttoncoi);
			} catch (NullPointerException e) {
			}
			try {
				((ViewGroup) calculatorbutton.getParent())
						.removeView(calculatorbutton);
			} catch (NullPointerException e) {
			}
			try {
				((ViewGroup) calc.getParent()).removeView(calc);
			} catch (NullPointerException e) {
			}
		}
	}


	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void showToastLong(final String toast) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_LONG).show();
			}
		});
	}


	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	public static String createnewimagename(File oldpath, int type, int row,
	Integer column, String extension) {
		String string = null;
		String columnstring;
		if(oldpath!=null){
			columnstring = oldpath.getName().replace("."+FilenameUtils.getExtension(oldpath.getName()), "");
		}else{
			columnstring = u.s(column);

		}
		if(extension==null){
			extension="jpg";
		}

if (type==COMPONENTSTAB) {
	
	string = ProjectDirectory + "/" + basedirectory[COMPONENTSFOLDER] + "/"
					+ componentname[row].et.getText().toString() +"/"+columnstring+"."+extension;
		
}else if (type==ASSETSTAB) {

	string = ProjectDirectory + "/" + basedirectory[ASSETSFOLDER] + "/"
			+ assetname[row].et.getText().toString() +"/"+columnstring+"."+extension;
} else if (type==SITETAB) {
	if (row == 0) {
		string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
				+ sitename[row].sp.getSelectedItem().toString() +"/"+ columnstring +"."+ extension;
	} else {
		string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
				+ sitename[row].et.getText().toString() +"/"+ columnstring +"."+ extension;
	}
} else if (type==CONSUMPTIONTAB) {
	try {
		string = ProjectDirectory + "/"
				+ basedirectory[CONSUMPTIONFOLDER] + "/"
				+ month[row].getText().toString() +"/" + columnstring +"."+ extension;
	} catch (Throwable e) {
		string = ProjectDirectory + "/"
				+ basedirectory[CONSUMPTIONFOLDER] + "/total_cons_"
				+ "/total_cons_" + columnstring +"."+ extension;
	}
} else {
	string = ProjectDirectory + "/" + "temp" + "_t_" + u.s(row) + "_"
			+ columnstring +"."+ extension;
}
u.log("buildimagenamestring="+string);
imagepaths[type][row][column]=string;
return string;
}



	public static String buildfoldernamestring(int type, int row) {
		String string = null;

		if (type==ASSETSTAB) {

			string = ProjectDirectory + "/" + basedirectory[ASSETSFOLDER] + "/"
					+ assetname[row].et.getText().toString();
		} else if (type==SITETAB) {
			if (row == 0) {
				string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
						+ sitename[row].sp.getSelectedItem().toString();
			} else {
				string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
						+ sitename[row].et.getText().toString();
			}
		} else if (type==CONSUMPTIONTAB) {
			try {
				string = ProjectDirectory + "/"
						+ basedirectory[CONSUMPTIONFOLDER] + "/"
						+ month[row].getText().toString();
			} catch (Throwable e) {
				string = ProjectDirectory + "/"
						+ basedirectory[CONSUMPTIONFOLDER] + "/total_cons_";
			}
		} else {
			string = ProjectDirectory + "/" + "temp" + "_t_" + u.s(row);
		}

		return string;
	}

	public String buildimagenamestringgivenname(String name, int type,
			int row, Integer column, String extension) {
		String string = null;
		String columnstring;

		if (!(extension == null) && !extension.contains(".")) {
			extension = "." + extension;
		}
		if (extension == null) {
			extension = ".jpg";
		}
		if (extension.contains("empty")) {
			extension = "";
		}

		if (column == null) {
			columnstring = "";
			extension = "";
		} else {
			columnstring = u.s(column);
		}

		if (type==ASSETSTAB) {
			string = ProjectDirectory + "/" + basedirectory[ASSETSFOLDER] + "/"
					+ name +"/"+columnstring + extension;
		} else if (type==SITETAB) {
			string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
					+ name +"/"+ columnstring + extension;
		} else {
			string = ProjectDirectory + "/" + "temp" +"/"+columnstring + extension;
		}

		return string;
	}

	public static void showneedmoresoftwaredialog(final String appname,
			final String appaddress) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Please download additional software")
				.setMessage(
						"In order to view this file you must download "
								+ appname
								+ ", or a similar app. Would you like to download it now?")
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

	public static void getappfromplay(String appName) {

		try {
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("market://details?id=" + appName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ appName)));
		}
	}

	public List<String> addtwoliststringsminusduplicates(List<String> A,
			List<String> B) {
		List<String> C = new ArrayList<String>();
		C = A;
		for (int i = 0; i < B.size(); i++) {
			if (!(C.contains(B.get(i)))) {
				C.add(B.get(i));
			}
		}
		return C;
	}

	private boolean isTabletDevice() {

		if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
			// test screen size, use reflection because isLayoutSizeAtLeast is
			// only available since 11
			Configuration con = getResources().getConfiguration();
			try {
				Method mIsLayoutSizeAtLeast = con.getClass().getMethod(
						"isLayoutSizeAtLeast", int.class);
				Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con,
						0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
				return r;
			} catch (Exception x) {
				x.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public static void refresh1pic(int type, int row, int num) {
		ImageView[][] pic = null;
		if (type==SITETAB) {
			pic = sitepicture;
		}
		if (type==ASSETSTAB) {
			pic = picture;
		}
		if (type==COMPONENTSTAB) {
			pic = componentpicture;
		}
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = PICTURESAMPLESIZE;
		Bitmap bm = BitmapFactory.decodeFile(imagepaths[type][row][num], o);

		double calcheight = (double) screenheight
				* (double) PICTUREDISPLAYPERCENTOFSCREEN / (double) 100;
		double calcwidth = (double) bm.getWidth() / (double) bm.getHeight()
				* (double) calcheight;

		int height = (int) calcheight;
		int width = (int) calcwidth;

		Bitmap resizedbitmap = null;

		resizedbitmap = Bitmap.createScaledBitmap(bm, width, height, true);
		bm.recycle();
		
		pic[row][num].setImageBitmap(resizedbitmap);

	}

	public String getAddressString(Location gpslocation) {
		addressString = null;

		if (gpslocation != null) {
			int count = 0;
			int stopcount = 20;
			while (addressString == null && count != stopcount) {
				count++;
				double currentLatitude = gpslocation.getLatitude();
				double currentLongitude = gpslocation.getLongitude();

				Geocoder gc = new Geocoder(this, Locale.getDefault());
				List<Address> addresses = null;
				try {
					addresses = gc.getFromLocation(currentLatitude,
							currentLongitude, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					showToast(e.toString());
				}
				StringBuilder sb = new StringBuilder();
				try {
					if (addresses.size() > 0) {
						Address address = addresses.get(0);

						for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
							sb.append(address.getAddressLine(i)).append("\n");

						sb.append(address.getCountryName());
					}
					addressString = sb.toString();

					lm.removeUpdates(ll);
					ll = null;
					Tabs1.gpslocation = null;
				} catch (NullPointerException e) {

				}
			}
		} else {

		}
		return addressString;
	}

	protected class GetAddressTask extends AsyncTask<Object, Void, String> {

		ProgressDialog progressDialog;

		public GetAddressTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				Looper.prepare();
			} catch (RuntimeException e) {

			}

			int i = 0;
			loop: while (true) {
				i++;
				if (gpslocation != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							// sitedescription[1].et.setText(getAddressString(gpslocation));
							try {
								sitedescription[1].et
										.append(getAddressString(gpslocation));
							} catch (Throwable e) {
								
								showToast("Restart Device");
							}
						}
					});
					break loop;
				} else if (i == 1000000000) {
					break loop;
				}
			}

			return null;
		}

		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() {
			// progressDialog = new ProgressDialog(Tabs1.this);

			progressDialog
					.setMessage("Retrieving Local Address Using GPS\nPlease Wait");
			progressDialog.show();
			startGPSlistener();
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				lm.removeUpdates(ll);
				ll = null;
				Tabs1.gpslocation = null;
			} catch (IllegalArgumentException e) {

			}
			progressDialog.dismiss();

		}
	}

	public void startGPSlistener() {
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if (location != null) {
					// ---Get current location latitude, longitude, altitude &
					// speed ---

					
					
					float speed = location.getSpeed();
					double altitude = location.getAltitude();
					Toast.makeText(
							Tabs1.this,
							"Latitude = " + location.getLatitude() + ""
									+ "Longitude = " + location.getLongitude()
									+ "Altitude = " + altitude + "Speed = "
									+ speed, Toast.LENGTH_LONG).show();
					gpslocation = location;

				}
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

		};

		// ---Get the status of GPS---
		boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// If GPS is not enable then it will be on
		if (!isGPS) {
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			try {
				sendBroadcast(intent);
			} catch (Throwable e) {

			}

		}

		// <--registers the current activity to be notified periodically by the
		// named provider. Periodically,
		// the supplied LocationListener will be called with the current
		// Location or with status updates.-->
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}

	public void addnewitemtolistwithoutdialog(int sheetnum, String string, View view,
			int listid) {
		
		int index = getIndexofSpinner(((Spinner) view), string);
		try{
			if ((!string.trim().equals("")) && index == 0) {
				ArrayAdapter<String> adapter = (ArrayAdapter<String>) ((Spinner) view)
						.getAdapter();
				addnewvaluetoexcellists(sheetnum,listid, string, MASTER_LIST);
				adapter.remove("new");
				adapter.add(string);
				adapter.add("new");
				((Spinner) view).setAdapter(adapter);
				((Spinner) view).setSelection(getIndexofSpinner(((Spinner) view),
						string));
			}
		}catch(Throwable e){
			u.log("error adding spinner item to lost without dialogue");
		}
		
	}

	public void removeinvalidcharacterdialog(final View currentview) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Tabs1.this);
		builder.setTitle("Invalid Character")
				.setMessage("Please remove any |  \\  ?  *  <  \"  :  > /")
				.setCancelable(false).setIcon(R.drawable.ic_launcher)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						currentview.requestFocus();
					}
				});
		builder.create().show();
	}

	public void fieldcantbeleftblankdialog(final View currentview) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Tabs1.this);
		builder.setTitle("Empty Item Name")
				.setMessage("Please name this item first").setCancelable(false)
				.setIcon(R.drawable.ic_launcher)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						currentview.requestFocus();
					}
				});
		builder.create().show();
	}

	public boolean hasinvalidcharacters(AutoCompleteTextView et, boolean wspace) {
		boolean hasinvalidcharactersbool = false;
		String[] chars = null;
		if (wspace) {
			chars = ReservedCharsIncludingSpace;
		} else {
			chars = ReservedChars;
		}

		for (String c : chars) {
//			u.log(c);
			if (et.getText().toString().contains(c)) {
				hasinvalidcharactersbool = true;
			}
		}
		return hasinvalidcharactersbool;
	}


	public void lhiphotoeditororotherdialog(final String picturelocation,
			int type, int row, int num) {

		eptype = type;
		eprow = row;
		epnum = num;

		Intent intent = u.openpicture(picturelocation);
		startActivityForResult(intent, EDITPICTUREACTIVITY);

	}

	public void ifspinnerdoesnthaveadd(ArrayAdapter<String> adapter,
			String string, HashMap<ArrayAdapter<String>, List<String>> SpinnerHash) {
		
		if (!string.trim().isEmpty() && string != null && !string.isEmpty()) {
			List<String> list = SpinnersHash.get(adapter);
			
			for (int i = 0; i < list.size(); i++) {
				
			}
			if (!(list.contains(string))) {
				
				list.add(string);
				adapter.notifyDataSetChanged();
			}
			

		}

	}




	

	public Bitmap generateBarCode(String data) {
		com.google.zxing.Writer c9 = new Code128Writer();
		Bitmap mBitmap = null;

		// ImageView mImageView=new ImageView(this);
		try {
			BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128, 350, 350);
			mBitmap = Bitmap.createBitmap(350, 350, Config.ARGB_8888);

			for (int i = 0; i < 350; i++) {
				for (int j = 0; j < 350; j++) {

					mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK
							: Color.WHITE);
				}
			}

		} catch (WriterException e) {
			
		}
		new Canvas(mBitmap);
		Paint p = new Paint();
		int fontsize = determineMaxTextSize(data, mBitmap.getWidth());
		Bitmap TextBitmap = Bitmap.createBitmap(mBitmap.getWidth(), fontsize
				+ fontsize, Bitmap.Config.ARGB_8888);
		Canvas textcanvas = new Canvas(TextBitmap);

		p.setTextSize(fontsize);
		p.setColor(Color.RED);

		textcanvas.drawText(data, 0, TextBitmap.getHeight()
				- ((float) 1.25 * (float) fontsize), p);
		Bitmap combinedBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight() + fontsize, Bitmap.Config.ARGB_8888);
		// 
		Canvas comboImage = new Canvas(combinedBitmap);

		comboImage.drawBitmap(mBitmap, 0f, 0f, null);
		comboImage.drawBitmap(TextBitmap, 0f, mBitmap.getHeight(), null);
		return combinedBitmap;
	}

	private int determineMaxTextSize(String str, float maxWidth) {
		int size = 0;
		Paint paint = new Paint();

		do {
			paint.setTextSize(++size);
		} while (paint.measureText(str) < maxWidth - 10);

		return size;
	} // End getMaxTextSize()

	public File DevelopeFileName(File Path, String ext) {

		getPreferences();

		new File(ProjectDirectory);
		File imageF = null;

		if (typeselected==COMPONENTSTAB) {
			
			new File(ProjectDirectory
					+ "/"
					+ basedirectory[COMPONENTSFOLDER]
					+ "/"
					+ componentname[u.i(numberselected)].et.getText()
							.toString())
					.mkdirs();

			imageF = new File(createnewimagename(Path,COMPONENTSTAB,
					u.i(numberselected), 0, null));
			int v1 = 0;
			while (imageF.exists()) {
				v1++;
				imageF = new File(createnewimagename(Path,COMPONENTSTAB,
						u.i(numberselected), v1, null));

			}
		}
		if (typeselected==ASSETSTAB) {
			
			new File(ProjectDirectory
					+ "/"
					+ basedirectory[ASSETSFOLDER]
					+ "/"
					+ assetname[u.i(numberselected)].et.getText()
							.toString())
					.mkdirs();

			imageF = new File(createnewimagename(Path,ASSETSTAB,
					u.i(numberselected), 0, null));
			int v1 = 0;
			while (imageF.exists()) {
				v1++;
				imageF = new File(createnewimagename(Path,ASSETSTAB,
						u.i(numberselected), v1, null));

			}
		}
		if (typeselected==SITETAB) {
			
			if (u.i(numberselected) == 0) {
				new File(ProjectDirectory
						+ "/"
						+ basedirectory[SITEINFOFOLDER]
						+ "/"
						+ sitename[u.i(numberselected)].sp.getSelectedItem())
						.mkdirs();
			} else {
				new File(ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER] + "/"
						+ sitename[u.i(numberselected)].et.getText().toString()).mkdirs();
			}

			imageF = new File(createnewimagename(Path,SITETAB, u.i(numberselected),
					0, null));
			int v1 = 0;
			while (imageF.exists()) {
				v1++;
				imageF = new File(createnewimagename(Path,SITETAB,
						u.i(numberselected), v1, null));

			}
		}

		if (typeselected==CONSUMPTIONTAB) {

			imageF = new File(createnewimagename(Path,CONSUMPTIONTAB,
					u.i(numberselected), 0, null));
			int v1 = 0;
			while (imageF.exists()) {
				v1++;
				imageF = new File(createnewimagename(Path,CONSUMPTIONTAB,
						u.i(numberselected), v1, null));

			}

		}
		

		File parent = imageF.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		if (!(ext == null)) {
			imageF = new File(imageF.getAbsolutePath().replace(".jpg",
					"." + ext));
			
		}
		return imageF;
	}

	public void SetTypeAndNumber(int type, int number) {
		set("typeselected", u.s(type));
		set("numberselected", u.s(number));
		getPreferences();
	}

	private void SetExplorerWindowtoFolder(String dirPath)

	{

		itemfordocs = new ArrayList<String>();
		pathfordocs = new ArrayList<String>();
		rootfordocs = ProjectDirectory;
		u.log("dir path="+dirPath);
		final File f = new File(dirPath);
		File[] files = f.listFiles();

		// }
		
		fileexplorerhome=new ImageView(this);
		fileexplorerup = new ImageView(this);
		fileexplorerparent = new TextView(this);
		
		
		fileexplorerhome = (ImageView) findViewById(R.id.fileexplorerhome);
		fileexplorerup = (ImageView) findViewById(R.id.fileexplorerup);
		fileexplorerparent = (TextView) findViewById(R.id.fileexplorerparent);
		
		fileexplorerhome.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				SetExplorerWindowtoFolder(ProjectDirectory);
			}
		});
		fileexplorerup.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View arg0) {
				SetExplorerWindowtoFolder(f.getParent());
			}
		});

		fileexplorerparent.setText(f.getName());

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			pathfordocs.add(file.getPath());
			if (file.isDirectory())
				itemfordocs.add(file.getName());
			else
				itemfordocs.add(file.getName());
		}

		fileList = new ArrayAdapter<String>(Tabs1.this,
				R.layout.androidexplorerrow, itemfordocs) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				// ((TextView) v).setTypeface(fontToSet);
				((TextView) v).setTextSize(fontsize);
				((TextView) v).setShadowLayer(6f, 4f, 4f, 0xFF00ccff);

				return v;
			}
		};

		fileexplorerlistview = (ListView) findViewById(R.id.docsandrefview);
		fileexplorerlistview.setAdapter(fileList);

	}

	public void caption(final File file) {

		File file1 = new File(file.getAbsolutePath().replace(".jpg", ".txt"));

		

		final ImageView imageview = new ImageView(this);
		imageview.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = 8;
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), o);
		imageview.setImageBitmap(bm);

		TableLayout captionEntryView = new TableLayout(this);
		captionEntryView.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));

		final TextView captionlabel = new TextView(this);
		captionlabel.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));
		captionlabel.setText("Caption:");

		final TextView Notelabel = new TextView(this);
		Notelabel.setLayoutParams(new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1f));
		Notelabel.setText("Notes:");

		final AutoCompleteTextView caption = new AutoCompleteTextView(this);
		caption.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT,
				1f));

		final AutoCompleteTextView Note = new AutoCompleteTextView(this);
		Note.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

		if (file1.exists()) {
			caption.setText(oldcaption);
			Note.setText(oldnotes);
		}

		TableRow tr1 = new TableRow(this);
		tr1.addView(imageview);

		TableRow tr2 = new TableRow(this);
		tr2.addView(captionlabel);

		TableRow tr3 = new TableRow(this);
		tr3.addView(caption);

		TableRow tr4 = new TableRow(this);
		tr4.addView(Notelabel);

		TableRow tr5 = new TableRow(this);
		tr5.addView(Note);

		captionEntryView.addView(tr1);
		captionEntryView.addView(tr2);
		captionEntryView.addView(tr3);
		captionEntryView.addView(tr4);
		captionEntryView.addView(tr5);

		AlertDialog.Builder adb = new AlertDialog.Builder(this)
				.setCancelable(false)
				.setTitle("Insert Caption")
				.setView(captionEntryView)

				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								dialog.cancel();

							}
						})
				.setPositiveButton("Insert",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String tempfilename = file.getAbsolutePath()
										.replace(".jpg", "modified.jpg");

								File file1 = new File(file.getAbsolutePath()
										.replace(".jpg", ".txt"));
								try {

									Bitmap inputbitmap = BitmapFactory
											.decodeFile(file.getAbsolutePath());
									if (file1.exists()) {

										file1.delete();
										if (!oldcaption.equals("")) {
											inputbitmap = chopoffcaption(
													inputbitmap, oldcaption);
										}
									}

									FileOutputStream out = new FileOutputStream(
											tempfilename);
									// Grap bitmap to be modified
									String capstring = caption.getText()
											.toString();
									// Do/Save modifications
									if (!capstring.equals("")) {
										EditBitmap(inputbitmap,
												caption.getText().toString())
												.compress(
														Bitmap.CompressFormat.JPEG,
														90, out);
									} else {
										inputbitmap.compress(
												Bitmap.CompressFormat.JPEG, 90,
												out);
									}

									// Grab modified bitmap
									// display modified bitmap to imageview
									// displayimageview.setImageBitmap(outputbitmap);
									// writing notes to textfile
									String inputtext = Note.getText()
											.toString();

									BufferedWriter writer = new BufferedWriter(
											new FileWriter(file1, true));

									writer.write("Caption: "
											+ caption.getText().toString()
											+ "\n" + "\n" + "\n" + "Notes:"
											+ inputtext);
									writer.newLine();
									writer.flush();
									writer.close();

									File tempfile = new File(tempfilename);
									file.delete();
									tempfile.renameTo(file);

								} catch (Exception e) {
									
								}
								refresh1pic(typeselected, u.i(numberselected),
										u.i(subnumberselected));
							}
						});
		adb.create().show();

	}

	private Bitmap EditBitmap(Bitmap InputBitmap, String text) {

		Bitmap.createBitmap(
				InputBitmap.getWidth(), InputBitmap.getHeight(),
				Bitmap.Config.ARGB_8888);
		Paint p = new Paint();

		int fontsize = determineMaxTextSize(text, InputBitmap.getWidth());
		Bitmap TextBitmap = Bitmap.createBitmap(InputBitmap.getWidth(),
				fontsize + fontsize, Bitmap.Config.ARGB_8888);
		Canvas textcanvas = new Canvas(TextBitmap);

		p.setTextSize(fontsize);
		p.setColor(Color.RED);

		textcanvas.drawText(text, 0, TextBitmap.getHeight()
				- ((float) 1.25 * (float) fontsize), p);
		Bitmap combinedBitmap = Bitmap.createBitmap(InputBitmap.getWidth(),
				InputBitmap.getHeight() + fontsize, Bitmap.Config.ARGB_8888);
		Canvas comboImage = new Canvas(combinedBitmap);

		comboImage.drawBitmap(InputBitmap, 0f, 0f, null);
		comboImage.drawBitmap(TextBitmap, 0f, InputBitmap.getHeight(), null);

		return combinedBitmap;
	}

	public void createbasedirectories() {
		// Base Directories
		for (int i = 0; i < basedirectory.length; i++) {
			new File(ProjectDirectory + "/" + basedirectory[i]).mkdirs();
		}
		// Latex Directory
		new File(ProjectDirectory + "/" + basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex").mkdirs();
		new File(ProjectDirectory + "/" + basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/ELCconfig").mkdirs();

	}

	private Bitmap chopoffcaption(Bitmap inputbitmap, String oldcaption) {
		// TODO Auto-generated method stub
		int fontsize = determineMaxTextSize(oldcaption, inputbitmap.getWidth());
		Bitmap originalBitmap = Bitmap.createBitmap(inputbitmap, 0, 0,
				inputbitmap.getWidth(), inputbitmap.getHeight() - fontsize);
		return originalBitmap;
	}

	// Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tabs1_menu, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		  case R.id.menu_settings:
			  startActivityForResult(u.intent("MainActivityPrefs"),PREFS);
			  break;
		  case R.id.menu_help:
			  showToast("Call Patrick 001-813-362-9644");
	          break;
		  case R.id.menu_refreshimages:
			  refreshfloorplans();
			  refreshpictures();
			  break;
	 
		default:
			return super.onOptionsItemSelected(item);
		}
	     return true;
	}

	public void getstringdialog(final String title, final int actionCode) {
		AlertDialog.Builder getstring = new AlertDialog.Builder(this);
		final AutoCompleteTextView nameet = new AutoCompleteTextView(this);
		nameet.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		if (typeselected==FLOORPLANTAB) {
			nameet.setThreshold(0);
			nameet.setSingleLine();
			u.log("Here are the floor count"+floornumbersadapter.getCount());
			nameet.setAdapter(floornumbersadapter);
			nameet.setHint("GroundFloor, Warehouse, Floor1,..etc (No Spaces)");
			nameet.setText(foldername);
			nameet.setSelectAllOnFocus(true);
			
		}
		getstring.setView(nameet);
		getstring.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		getstring.setTitle(title);
		getstring.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						string = nameet.getText().toString();
						
						
						
							dialog.dismiss();
							u.log(inputfloorplandirectory);
							File imageA = new File(inputfloorplandirectory+"/"+string+".jpg");
							File imageB = new File(inputfloorplandirectory+"/"+string+".gif");
							File imageC = new File(inputfloorplandirectory+"/"+string+".png");
							File imageD = new File(inputfloorplandirectory+"/"+string+".bmp");
							
							if (imageA.exists()||imageB.exists()||imageC.exists()||imageD.exists()) {
								getstringdialog(
										"Floor Plan Already Exists Please Choose Another Name",
										actionCode);

							} else {

								if (!(actionCode == GETPICFROMFILE)) {

									Intent takePictureIntent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									takePictureIntent
											.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
									takePictureIntent.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(imageA));
									startActivityForResult(takePictureIntent,
											actionCode);
								} else {
									string = nameet.getText().toString();
									Intent intent = new Intent();
									intent.setAction(Intent.ACTION_GET_CONTENT);
									intent.setType("*/*");
									startActivityForResult(intent,
											GETPICFROMFILE);
								}
							}
						
					}
				});
		getstring.create().show();
	}

	public void refreshfloorplans() {

		floorplannames.removeAllViews();
		str7[1].removeAllViews();

		TableRow.LayoutParams str5lp = new TableRow.LayoutParams();
		str5lp.weight = (1 / 4);

		
		floorplanstrings=new ArrayList<String>();
		for(File file:new File(inputfloorplandirectory).listFiles()){
			if (u.issupportedimage(file)){
				floorplanstrings.add(file.getAbsolutePath());
			}
		}

		outputfloorplanstrings=new ArrayList<String>();
		try {
			for(File file:new File(outputfloorplandirectory).listFiles()){
				if (u.issupportedimage(file)){
					outputfloorplanstrings.add(file.getAbsolutePath());
				}
			}

		} catch (Throwable e) {

		}

		for (int t = 0; t < floorplanstrings.size(); t++) {
			
		}
		FloorPlanCount = floorplanstrings.size();

		
		

		for (int floorplanpicnum = 0; floorplanpicnum < floorplanstrings.size(); floorplanpicnum++) {
			

			

			sitefpimageview[floorplanpicnum] = new ImageView(ctx);
			sitefpimageview[floorplanpicnum]
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {

							try {
								int[] z = getsequencenumber(sitefpimageview, v);
								Intent myIntent = new Intent();
								myIntent.putExtra("floorplannumber", z[0]);
								myIntent.setClassName(MainActivity.class
										.getPackage().getName(),
										MainActivity.class.getPackage()
												.getName()
												+ "."
												+ "FloorPlanActivity");

								

								// myIntent.putExtra("picturelocation",
								// Environment.getExternalStorageDirectory().toString()+"/floorplan.jpg");

							
								startActivityForResult(myIntent,FLOORPLANACTIVITY);

							} catch (IndexOutOfBoundsException e) {
								e.printStackTrace();
							}
						}
					});
			sitefpimageview[floorplanpicnum].setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(final View v) {

					int[] z = null;
					try {
						z = getsequencenumber(sitefpimageview, v);
						
						TableRow parentrow = (TableRow) v.getParent();
						File file=new File(inputfloorplandirectory).listFiles()[z[0]];
						
						deleteorduplicatefloorplandialog(file,((ImageView)v), str7[1], z[0]);

					} catch (IndexOutOfBoundsException e) {

					}

					return false;
				}

			});
			sitefpimageview[floorplanpicnum].setLayoutParams(str5lp);

			sitefptextview[floorplanpicnum] = new TextView(ctx);
			sitefptextview[floorplanpicnum].setLayoutParams(str5lp);

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inSampleSize = FLOORPLANPICTURESIZE;

			
			
			try{
				u.log(floorplanpicnum);
				u.log("trying to decode bitmap "+outputfloorplanstrings.get(floorplanpicnum));
			}catch(Throwable e){
				e.printStackTrace();
			}
			try {
				
				Bitmap bm = BitmapFactory.decodeFile(outputfloorplanstrings.get(floorplanpicnum), o);

				double calcheight = (double) screenheight
						* (double) FLOORPLANPICTUREDISPLAYPERCENTOFSCREEN
						/ (double) 100;
				double calcwidth = (double) bm.getWidth()
						/ (double) bm.getHeight() * (double) calcheight;
				
				
				int height = (int) calcheight;
				int width = (int) calcwidth;

				Bitmap resizedbitmap = null;

				resizedbitmap = Bitmap.createScaledBitmap(bm, width, height,
						true);
				bm.recycle();
				sitefpimageview[floorplanpicnum].setImageBitmap(resizedbitmap);
				
			} catch (Throwable e) {
				u.log("No outputfloorplan, trying to decode bitmap "+floorplanstrings.get(floorplanpicnum));
				Bitmap bm = BitmapFactory.decodeFile(floorplanstrings.get(floorplanpicnum), o);

				double calcheight = (double) screenheight
						* (double) FLOORPLANPICTUREDISPLAYPERCENTOFSCREEN
						/ (double) 100;
				double calcwidth = (double) bm.getWidth()
						/ (double) bm.getHeight() * (double) calcheight;

				int height = (int) calcheight;
				int width = (int) calcwidth;

				Bitmap resizedbitmap = null;

				resizedbitmap = Bitmap.createScaledBitmap(bm, width, height,
						true);
				bm.recycle();
				u.log("Setting fp to view 'sitefpimageview'"+floorplanpicnum);
				
				sitefpimageview[floorplanpicnum].setImageBitmap(resizedbitmap);
				
			}

			u.log("setting the name of the floorplan above it");
			sitefptextview[floorplanpicnum].setLayoutParams(str5lp);
			sitefptextview[floorplanpicnum]
					.setText(FilenameUtils.getBaseName(new File(floorplanstrings.get(floorplanpicnum)).getName()));

			sitefptextview[floorplanpicnum].setTextSize(fontsize);
			sitefptextview[floorplanpicnum].setShadowLayer(6f, 4f, 4f,
					0xFF00ccff);

			floorplannames.addView(sitefptextview[floorplanpicnum]);
			str7[1].addView(sitefpimageview[floorplanpicnum]);

		}
		tab0scrollview.post(new Runnable() {
			public void run() {
				tab0scrollview.fullScroll(View.FOCUS_DOWN);
			}
		});
		try{
			sitefpimageview[floorplanpicnum].requestFocus();
		}catch(Throwable e){
			
		}
	}

	public void calculateandsetCTvalues(View v, int sheetnum, int itemnum,
			int column) {

		AutoCompleteTextView et = (AutoCompleteTextView) v;
		if (!et.getText().toString().equals("")) {
			
			// /////////////////////////
			// Ibe's CT Type bom insertion
			// totalSAM
			// ////////////////////////
			mcrstringarray[sheetnum][itemnum][column + 2] = u.calculateandsetCTvalues(u.i(et.getText().toString()),ctdownsizepercent);

			if (column >= maxx[sheetnum]) {
				maxx[sheetnum] = column + 2;
			}
		}
	}

	private void showfloorplanlistmenu() {


		outputfloorplanstrings=new ArrayList<String>();
		for(File file:new File(outputfloorplandirectory).listFiles()){
			if (u.issupportedimage(file)){
				outputfloorplanstrings.add(file.getAbsolutePath());
			}
		}

		if (outputfloorplanstrings.size()>0) {
			final CharSequence[] items=new CharSequence[outputfloorplanstrings.size()];
			for (int t = 0; t < outputfloorplanstrings.size(); t++) {
				items[t]=FilenameUtils.getBaseName(new File(outputfloorplanstrings.get(t)).getName());
				u.log(items[t]);
			}


			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Make your selection");
			// builder.setItems( menuitems[type], new
			// DialogInterface.OnClickListener() {
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int menuitem) {
					// Do something with the selection
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					u.log(outputfloorplanstrings.get(menuitem));
					File file = new File(outputfloorplanstrings.get(menuitem));
					intent.setDataAndType(Uri.fromFile(file),
							u.getMimeType(file.getAbsolutePath()));
					
					startActivity(intent);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			showToast("You have not yet added any floor plans.");
		}
	}
	
	public void moptypeselection(){
		checklist = new ArrayList<String>();
		checklist.add("Executive Summary");
		checklist.add("Facilty Information");
		checklist.add("Facilty Assets");
		checklist.add("Facility & Energy Consumption Statistics Table");
		checklist.add("Bill of Materials");
		checklist.add("Optimization Strategy Development");
		checklist.add("Site Specific Energy Saving Recommendations");
		checklist.add("Metering and Monitoring Riser Architechture");
		checklist.add("Energy Metering List");
		checklist.add("Building Load Classification");
		checklist.add("Temperature Sensor List");
		checklist.add("SAM Array Reference Table");
		checklist.add("Approximate Location Layout for Installation");
		checklist.add("Life Hack Innovations Equipment Installation Information");
		checklist.add("Site's Asset' Installation Information");
		checklist.add("Notes");
		
		TableRow mopdesigntablerow0 = new TableRow(this);
		mopdesigntablerow0.setLayoutParams(lpfw);
		
		//reporttype spinner
		String MOP_TYPE_NGB_BASIC="M395 NGBailey Installation MOP (Basic)";
		String MOP_TYPE_NGB_DETAILED="M395 NGBailey Installation MOP (Detailed)";
		String MOP_TYPE_SALES="Sales MOP";
		
		
		
		
		String[] reportoptions={MOP_TYPE_NGB_BASIC,MOP_TYPE_NGB_DETAILED,MOP_TYPE_SALES};
		Spinner ReportType = new Spinner(this);
		ReportType.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT, 1f));		
		ReportType.setAdapter(new ArrayAdapter<String>(this, R.layout.spinnertextview, reportoptions));
		ReportType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int intMOP_TYPE_NGB_BASIC=0;
				int intMOP_TYPE_NGB_DETAILED=1;
				int intMOP_TYPE_SALES=2;
				u.log("Checklist size:"+ checklist.size());
				u.log("arg0 selected item position"+arg0.getSelectedItemPosition());
				mopreporttypestates=new boolean[checklist.size()];
				
				for (int s = 0; s < checklist.size(); s++) {
					mopreporttypestates[s] = true;
				}
				
				if(arg0.getSelectedItemPosition()==intMOP_TYPE_NGB_BASIC){
					for (int s = 0; s < checklist.size(); s++) {
						if ((s == 0) || (s == 3) || (s == 5) || (s==6)||(s==13)||(s==14)) {
							mopreporttypestates[s] = false;
						} else {
							mopreporttypestates[s] = true;
						}
					}
				}
				if(arg0.getSelectedItemPosition()==intMOP_TYPE_NGB_DETAILED){
					for (int s = 0; s < checklist.size(); s++) {
						if ((s == 0) || (s == 3) || (s == 5) || (s==6)||(s==13)) {
							mopreporttypestates[s] = false;
						} else {
							mopreporttypestates[s] = true;
						}
					}
				}
				if(arg0.getSelectedItemPosition()==intMOP_TYPE_SALES){
					for (int s = 0; s < checklist.size(); s++) {
						mopreporttypestates[s] = true;
					}
				}
				
	//checking for unnecessary checked items
				String[] totalassets = new File(ProjectDirectory + "/"
						+ basedirectory[ASSETSFOLDER]).list();
				if(totalassets.length==0){
					mopreporttypestates[14] = false;
				}
				
				File testfile=new File(ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Riser_Diagram.png");
				if(!testfile.exists()){
					mopreporttypestates[7] = false;
				}
				
				testfile=new File(ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Load_Diagram.png");
						if(!testfile.exists()){
							mopreporttypestates[9] = false;
						}
			   ArrayList<String> type=db.getcolumn(db.TABLE_FLOORPLAN, 5, db.KEY_FLOORPLAN_TITLES);
	           
			   if(!type.contains(u.s(FloorPlanView.TYPE_SAMARRAY))){
	        	   mopreporttypestates[11] = false;
	           }
	           
	           if(assetcount==0){
	        	   mopreporttypestates[2] = false;
	           }
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		
		TextView tv=new TextView(this);
		tv.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT, 1f));
		tv.setText("Report Type:");
		mopdesigntablerow0.addView(tv);
		mopdesigntablerow0.addView(ReportType);
		
		TableLayout mopdesignlayout = new TableLayout(this);
		mopdesignlayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mopdesignlayout.setPadding(10, 10, 10, 10);
		// mopdesignlayout.setBackgroundColor(Color.parseColor(darkblue));
		
		mopdesignlayout.addView(mopdesigntablerow0);
		
		AlertDialog.Builder selectdocumentdesign = new AlertDialog.Builder(this);
		selectdocumentdesign
				.setTitle("Please Select the Report Type");
		selectdocumentdesign.setView(mopdesignlayout);

		selectdocumentdesign.setPositiveButton("CONFIRM",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try{
							mopselection();
						}catch(Throwable e){
							showToast("You must first select a company");
						}
					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		selectdocumentdesign.create().show();
	}
	private void mopselection() {
		
		LayoutParams mopdesigntextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams mopdesignthumbnailimageviewparams = new LayoutParams(374,
				507);

		TableLayout mopdesignlayout = new TableLayout(this);
		mopdesignlayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// mopdesignlayout.setBackgroundColor(Color.parseColor(darkblue));
		mopdesignlayout.setPadding(2, 2, 2, 2);

		
		
		TableRow mopdesigntablerow1 = new TableRow(this);
		mopdesigntablerow1.setLayoutParams(lpfw);
		TableRow mopdesigntablerow2 = new TableRow(this);
		mopdesigntablerow2.setLayoutParams(lpfw);

		

		
		TextView coverpagename = new TextView(this);
		coverpagename.setLayoutParams(mopdesigntextviewparams);
		coverpagename.setText("Cover Page: ");

		if (defaultcoverpagepathset == false) {
			coverpagefilepath = ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]
					+ "/Latex/Pictures/Latex_Coverpage_0.png";
			defaultcoverpagepathset = true;

		}
		coverpagethumbnail = new ImageView(this);
		coverpagethumbnail.setLayoutParams(mopdesignthumbnailimageviewparams);
		u.log(coverpagefilepath);
		//if(u.isbluestacks()){
			coverpagethumbnail.setImageBitmap(u.getgoodmemorybitmappercentofscreen(coverpagefilepath,50,ctx));
		//}
	//		coverpagethumbnail.setImageBitmap(BitmapFactory
	//			.decodeFile(coverpagefilepath));
	//	}
		Log.d("coverpagefilepath", coverpagefilepath);
		coverpagethumbnail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				defaultcoverpagepathset = false;
				startActivityForResult(intent, GETCOVERPAGEPATH);
			}
		});

		TextView clientlogoname = new TextView(this);
		clientlogoname.setLayoutParams(mopdesigntextviewparams);
		clientlogoname.setText("Client Logo: ");
		Log.d("defaultclientlogopathset,companynameselectedboolean",
				defaultclientlogopathset + " " + companynameselectedboolean);
		String siteselectedtext;
		int compnumber = 0;
		if (sitename[0].sp.getSelectedItemPosition() > 0) {
			companynameselectedboolean = true;
			siteselectedtext = ((TextView) sitename[0].sp.getSelectedView())
					.getText().toString();
			Log.d("siteselected=", siteselectedtext);
			compnumber = sitename[0].sp.getSelectedItemPosition();

		}
		clientlogofilepath = getcompanylogopath();

		if (defaultclientlogopathset == false) {
			if (companynameselectedboolean == true) {
				// clientlogofilepath="/storage/sdcard0/lhi/Client Logos/"+companylogofilenamemop[companyindex];
				// clientlogofilepath="/storage/sdcard0/lhi/Client Logos/"+oldname+".png";
				// clientlogofilepath=ProjectDirectory + "/" +
				// basedirectory[GENERATEDDOCUMENTSFOLDER]+"/Latex/Client Logos/"+
				// oldname+".png";
				clientlogofilepath = getcompanylogopath();
				companynameselectedboolean = false;
			} else {
				// show toast saying company name not selected
			}
			defaultclientlogopathset = true;
		}
		clientlogothumbnail = new ImageView(this);
		clientlogothumbnail.setLayoutParams(mopdesignthumbnailimageviewparams);
		clientlogothumbnail.setImageBitmap(u.getgoodmemorybitmappercentofscreen(clientlogofilepath, 50, ctx));
		
		
		Log.d("clientlogofilepath", clientlogofilepath);
		clientlogothumbnail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				startActivityForResult(intent, GETCLIENTLOGOPATH);
			}
		});

		
		mopdesigntablerow1.addView(coverpagename);
		mopdesigntablerow2.addView(coverpagethumbnail);
		mopdesigntablerow1.addView(clientlogoname);
		mopdesigntablerow2.addView(clientlogothumbnail);
		
		mopdesignlayout.addView(mopdesigntablerow1);
		mopdesignlayout.addView(mopdesigntablerow2);

		AlertDialog.Builder selectdocumentdesign = new AlertDialog.Builder(this);
		selectdocumentdesign
				.setTitle("MOP Design - Select the theme and the Client Logo");
		selectdocumentdesign.setView(mopdesignlayout);

		selectdocumentdesign.setPositiveButton("CONFIRM",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						coverpagecopydest = ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]
								+ "/Latex/Pictures/Latex_Coverpage_0.png";
						clientlogocopydest = ProjectDirectory
								+ "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]
								+ "/Latex/Pictures/Latex_covercompanypicture.jpg";
						Log.d(coverpagefilepath, coverpagecopydest);
						if (!defaultcoverpagepathset) {
							File coverpagesourcefile = new File(
									coverpagefilepath);
							File coverpagedestfile = new File(coverpagecopydest);
							try {
								u.copyFile(coverpagesourcefile,
										coverpagedestfile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								
							}
						}
						File clientlogosourcefile = new File(clientlogofilepath);
						File clientlogodestfile = new File(clientlogocopydest);
						try {
							u.copyFile(clientlogosourcefile, clientlogodestfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
						showselectmopchaptersdialog();
					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		final AlertDialog mopdesignalert = selectdocumentdesign.create();
		// Editbuilder
		final ArrayList<String> editfilename = new ArrayList<String>();
		editfilename.add("Latex_ExecutiveSummary.tex");
		editfilename.add("Latex_FacilityInformation.tex");
		editfilename.add("Latex_OptimizationStrategyDevelopment.tex");

		final ArrayList<String> editchecklist = new ArrayList<String>();
		editchecklist.add("Executive Summary");
		editchecklist.add("Facilty Information");
		editchecklist.add("Optimization Strategy Development");

		final CharSequence[] edititems = new CharSequence[editchecklist.size()];
		for (int i = 0; i < editchecklist.size(); i++) {
			edititems[i] = editchecklist.get(i);
		}
		AlertDialog.Builder editbuilder = new AlertDialog.Builder(this);
		editbuilder.setTitle("Make your selection");
		// builder.setItems( menuitems[type], new
		// DialogInterface.OnClickListener() {
		editbuilder.setItems(edititems, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int menuitem) {
				// Do something with the selection
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);

				File file = new File(ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Latex/Chapters/" + editfilename.get(menuitem));
				intent.setDataAndType(Uri.fromFile(file),
						u.getMimeType(file.getAbsolutePath()));
				Log.d("file to be openned", file.getAbsolutePath());
				startActivity(intent);
			}
		});
		final AlertDialog editalert = editbuilder.create();

		// selection builder
		final CharSequence[] selections = new CharSequence[2];
		selections[0] = "Make Edits";
		selections[1] = "Create MOP";

		AlertDialog.Builder selctionbuilder = new AlertDialog.Builder(this);
		selctionbuilder.setTitle("Make your selection");
		// builder.setItems( menuitems[type], new
		// DialogInterface.OnClickListener() {
		selctionbuilder.setItems(selections,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int menuitem) {
						// Do something with the selection
						if (menuitem == 0) {
							editalert.show();
						}
						if (menuitem == 1) {
							mopdesignalert.show();
						}
					}
				});
		AlertDialog selectionalert = selctionbuilder.create();
		selectionalert.show();

	}

	public void maketexfile() {

		// replace dummy
		givevaluestolatexvariables();

		Log.d("maketexfile ran", "true");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					getAssets().open("Latex_main1.txt")));
			Log.d("Where main tex file goes", ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/Latex/"+foldername+".tex");
			File writefile = new File(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER]+ "/Latex/"+foldername+".tex");

			if (writefile.exists()) {
				writefile.delete();
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					writefile, true));

			String line;

			while ((line = br.readLine()) != null) {

				String finalline = assigingvaluestolatexvariables(line);

				Log.d("line read after replace from report", finalline);
				writer.write(finalline);
				writer.newLine();
			}

			if (execsum) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_ExecutiveSummary.tex", this);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_execsum.txt")));
				while ((line = br1.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			Boolean facilityorasset=false;
			if (facility) {
				facilityorasset=true;
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_FacilityInformation.tex", this);
				BufferedReader br2 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_facility.txt")));
				while ((line = br2.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if (facilityasset) {
				facilityorasset=true;
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_FacilityAssets.tex", this);
				BufferedReader br2 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_facilityAsset.txt")));
				while ((line = br2.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			
			if(facilityorasset){
				writer.write("\\clearpage");
				writer.newLine();
			}
			if (facilityenergy) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_FacilityEnergyConsumptionStasticsTable.tex", this);
				BufferedReader br3 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_facilityenergy.txt")));
				while ((line = br3.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if (BOM) {
				
				BufferedReader br3 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_BOM.txt")));
				while ((line = br3.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if (optimization) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_OptimizationStrategyDevelopment.tex", this);
				BufferedReader br4 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_optimization.txt")));
				while ((line = br4.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if (sitespecificrecommendations) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_Sitespecificrecommendations.tex", this);
				BufferedReader br4 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_Sitespecificrecommendations.txt")));
				while ((line = br4.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			
			
			
			if (riser) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_MeteringandMonitoringRiserArchitectures.tex", this);
				
				BufferedReader br5 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_riser.txt")));
				while ((line = br5.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
				
			}
			if (metering) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_EnergyMeteringList.tex", this);
				BufferedReader br6 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_metering.txt")));
				while ((line = br6.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if (load) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_BuildingLoadClassification.tex", this);
				BufferedReader br7 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_load.txt")));
				while ((line = br7.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
				writer.write("\\end{landscape}");
			}
			if (tempsensorlist) {
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_TemperatureSensorList.tex", this);
				BufferedReader br8 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_tempsensorlist.txt")));
				while ((line = br8.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
				}
			}
			if(SAMreferencetable){
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_SamArrayReference.tex",
						this);
				BufferedReader br8 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_SamArrayRefernce.txt")));
				while ((line = br8.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
					
				}
			}
			if (floorplan) {
				u.log("floorplan = true");
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_ApproximateLocationLayoutforInstallationEquipment.tex",
						this);
				
				
				BufferedReader br9 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_floorplan.txt")));
				while ((line = br9.readLine()) != null) {
					Log.d("line read from report", line);
					writer.write(line);
					writer.newLine();
					
				}
				for (int l = 0; l < outputfloorplanstrings.size(); l++) {
					
				
					writer.write("\\begin{center}");
					writer.newLine();
					writer.write("\\centering");
					writer.newLine();
					String temp = "\\includegraphics[width=1.25 \\textwidth]{../Floorplans/";
					Log.d("Temp String", temp);
					writer.write(temp + FilenameUtils.getBaseName((new File(outputfloorplanstrings.get(l)).getName())) + "}");
					writer.newLine();
					writer.write("\\end{center}");
					writer.newLine();
					
				}
				writer.write("\\end{landscape}");
				writer.newLine();
				writer.write("\\clearpage");
			}
			
			if(lhiassets){
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_AssetsTable.tex", this);
				
				String[] individualelcasset;
				String[] individualtempasset;
				Boolean heading=false;
				float picturewidth=125;
				float pictureheight;
			 
				elcassetstrings = new File(ProjectDirectory + "/"
						+ basedirectory[COMPONENTSFOLDER])
						.list(new FilenameFilter() {
							public boolean accept(File directory,
									String fileName) {
								return fileName.startsWith("ELC");
							}
						});
				tempsensorassetsstrings = new File(ProjectDirectory + "/"
						+ basedirectory[COMPONENTSFOLDER])
						.list(new FilenameFilter() {
							public boolean accept(File directory,
									String fileName) {
								return fileName.startsWith("Zone");
							}
						});
				if(elcassetstrings!= null ||tempsensorassetsstrings!= null ){
					BufferedReader br10 = new BufferedReader(new InputStreamReader(
							getAssets().open("Latex_aasetstable.txt")));
					while ((line = br10.readLine()) != null) {
						Log.d("line read from report", line);
						writer.write(line);
						writer.newLine();
					}
				}
				
				try {
					if (elcassetstrings!= null) {
						for(int k=0;k<elcassetstrings.length;k++){
							
							individualelcasset = new File(ProjectDirectory + "/"
									+ basedirectory[COMPONENTSFOLDER]+"/"+elcassetstrings[k])
									.list(new FilenameFilter() {
										public boolean accept(File directory,
												String fileName) {
											return fileName.endsWith(".jpg");
										}
									});
							Bitmap bitmap;
							bitmap=BitmapFactory
									.decodeFile(ProjectDirectory + "/"
											+ basedirectory[COMPONENTSFOLDER]+"/"+elcassetstrings[k]+"/"+individualelcasset[0]);
							float w1=bitmap.getWidth();
							float h1=bitmap.getHeight();
							float w2=picturewidth;
							float h2=(w2*h1)/w1;
							pictureheight=h2;
									
							String componentspath ="../../Components/"+elcassetstrings[k]+"/"+individualelcasset[0];
							int st=0;
							if(!heading){
								BufferedReader br11 = new BufferedReader(new InputStreamReader(
										getAssets().open("Latex_Assets_initial.txt")));
								while ((line = br11.readLine()) != null) {
									if(line.contains("PICTUREWIDTH")){
										line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
									}
									if(line.contains("PICTUREHEIGHT")){
										line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
									}
									if(line.contains("compenentspath")){
										line=line.replace("compenentspath",componentspath);
										
									}
									if(line.contains("assetdescription")){
										int assetname = 0;
										int notes = 8;
										String tempassetname=elcassetstrings[k]; 
										String assetnotes="null";
										//read_report_Life_Hack_Innovations_Assets();
										int tempassetindex=0;
										
										for(int temp =0;temp<maxcomponents;temp++){
											try{
											if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" ,(temp)).equals(tempassetname)){
												tempassetindex=temp;
												assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes" ,(temp));
												break;
											}}
											catch(Throwable e){
												
											}
										}
										
										String templine = line;
										if(!assetnotes.equals("null")){
										templine=templine.replace("assetdescription",assetnotes);
										}else{
											templine="";
										}
										line=line.replace("assetdescription",elcassetstrings[k]);
										writer.write(line);
										writer.newLine();
										line=templine;
									}
									Log.d("line read from report", line);
									st=1;
									heading=true;
									writer.write(line);
									writer.newLine();
								}
								
							}
							for(int j=st;j<individualelcasset.length;j++){
								
								bitmap=BitmapFactory
										.decodeFile(ProjectDirectory + "/"
												+ basedirectory[COMPONENTSFOLDER]+"/"+elcassetstrings[k]+"/"+individualelcasset[j]);
								w1=bitmap.getWidth();
								h1=bitmap.getHeight();
								w2=picturewidth;
								h2=(w2*h1)/w1;
								pictureheight=h2;
								componentspath ="../../Components/"+elcassetstrings[k]+"/"+individualelcasset[j];
								
									BufferedReader br11 = new BufferedReader(new InputStreamReader(
											getAssets().open("Latex_Assets.txt")));
									while ((line = br11.readLine()) != null) {
										if(line.contains("PICTUREWIDTH")){
											line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
										}
										if(line.contains("PICTUREHEIGHT")){
											line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
										}
										
										if(line.contains("compenentspath")){
											line=line.replace("compenentspath",componentspath);
											
										}
										if(line.contains("assetdescription")){
											int assetname = 0;
											int notes = 8;
											String tempassetname=elcassetstrings[k]; 
											String assetnotes="null";
											//read_report_Life_Hack_Innovations_Assets();
											int tempassetindex=0;
											
											for(int temp =0;temp<maxcomponents;temp++){
												try{
												if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" ,(temp)).equals(tempassetname)){
													tempassetindex=temp;
													assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes" ,(temp));
													break;
												}}
												catch(Throwable e){
													
												}
											}
											
											String templine = line;
											if(!assetnotes.equals("null")){
											templine=templine.replace("assetdescription",assetnotes);
											}else{
												templine="";
											}
											line=line.replace("assetdescription",elcassetstrings[k]);
											writer.write(line);
											writer.newLine();
											line=templine;
										}
										Log.d("line read from report", line);
										writer.write(line);
										writer.newLine();
								
								}
									
							}
						}
					}
				} catch (Throwable e) {
					System.out.println("Couldn't do image make stuff elcs latexfix");
					
				}
				try {
					if (tempsensorassetsstrings!= null) {
						Log.d("Temp Assets","Temp assets table writing started");
						
						for(int k=0;k<tempsensorassetsstrings.length;k++){
						
							individualtempasset = new File(ProjectDirectory + "/"
									+ basedirectory[COMPONENTSFOLDER]+"/"+tempsensorassetsstrings[k])
									.list(new FilenameFilter() {
										public boolean accept(File directory,
												String fileName) {
											return fileName.endsWith(".jpg");
										}
									});
							Bitmap bitmap;
							bitmap=BitmapFactory
									.decodeFile(ProjectDirectory + "/"
											+ basedirectory[COMPONENTSFOLDER]+"/"+tempsensorassetsstrings[k]+"/"+individualtempasset[0]);
							float w1=bitmap.getWidth();
							float h1=bitmap.getHeight();
							float w2=picturewidth;
							float h2=(w2*h1)/w1;
							pictureheight=h2;
							String componentspath ="../../Components/"+tempsensorassetsstrings[k]+"/"+individualtempasset[0];
							int st=0;
							if(!heading){
								BufferedReader br11 = new BufferedReader(new InputStreamReader(
										getAssets().open("Latex_Assets_initial.txt")));
								while ((line = br11.readLine()) != null) {
									
									if(line.contains("PICTUREWIDTH")){
										line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
									}
									if(line.contains("PICTUREHEIGHT")){
										line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
									}
									if(line.contains("compenentspath")){
										line=line.replace("compenentspath",componentspath);
										
									}
									if(line.contains("assetdescription")){
										int assetname = 0;
										int notes = 8;
										String tempassetname=tempsensorassetsstrings[k]; 
										String assetnotes="null";
										//read_report_Life_Hack_Innovations_Assets();
										int tempassetindex=0;
										
										for(int temp =0;temp<maxcomponents;temp++){
											try{
											if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname" ,(temp)).equals(tempassetname)){
												tempassetindex=temp;
												assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes",(temp));
												break;
											}}
											catch(Throwable e){
												
											}
										}
										
										String templine = line;
										if(!assetnotes.equals("null")){
										templine=templine.replace("assetdescription",assetnotes);
										}else{
											templine="";
										}
										line=line.replace("assetdescription",tempsensorassetsstrings[k]);
										writer.write(line);
										writer.newLine();
										line=templine;
									}
									Log.d("line read from report", line);
									st=1;
									heading=true;
									writer.write(line);
									writer.newLine();
								}
								
							}
							for(int j=st;j<individualtempasset.length;j++){
								bitmap=BitmapFactory
										.decodeFile(ProjectDirectory + "/"
												+ basedirectory[COMPONENTSFOLDER]+"/"+tempsensorassetsstrings[k]+"/"+individualtempasset[j]);
								w1=bitmap.getWidth();
								h1=bitmap.getHeight();
								w2=picturewidth;
								h2=(w2*h1)/w1;
								pictureheight=h2;
								componentspath ="../../Components/"+tempsensorassetsstrings[k]+"/"+individualtempasset[j];
								Log.d("Temp Asset", "drawing this asset"+individualtempasset[j]);
									BufferedReader br11 = new BufferedReader(new InputStreamReader(
											getAssets().open("Latex_Assets.txt")));
									while ((line = br11.readLine()) != null) {
										
										if(line.contains("PICTUREWIDTH")){
											line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
										}
										if(line.contains("PICTUREHEIGHT")){
											line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
										}
										if(line.contains("compenentspath")){
											line=line.replace("compenentspath",componentspath);
											
										}
										if(line.contains("assetdescription")){
											int assetname = 0;
											int notes = 8;
											String tempassetname=tempsensorassetsstrings[k]; 
											String assetnotes="null";
											//read_report_Life_Hack_Innovations_Assets();
											int tempassetindex=0;
											
											for(int temp =0;temp<maxcomponents;temp++){
												try{
												if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentname",(temp)).equals(tempassetname)){
													tempassetindex=temp;
													assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_COMPONENTINFO, "componentnotes",(temp));
													break;
												}}
												catch(Throwable e){
													
												}
											}
											
											String templine = line;
											if(!assetnotes.equals("null")){
											templine=templine.replace("assetdescription",assetnotes);
											}else{
												templine="";
											}
											line=line.replace("assetdescription",tempsensorassetsstrings[k]);
											writer.write(line);
											writer.newLine();
											line=templine;
										}
										Log.d("line read from report", line);
										writer.write(line);
										writer.newLine();
								
								}
									
							}
						}
					}
				} catch (Throwable e) {
					
					System.out.println("Couldn't do image make stuff temps latexfix");

				}		
				if(elcassetstrings!= null ||tempsensorassetsstrings!= null ){
					
					System.out.println("We have lhi assets latexfix");
					
						writer.write("\\end{longtable}");
						writer.newLine();
						writer.write("\\clearpage");
						writer.newLine();
					
				}
			}
			
			if(sitesassets){
				
				u.copyfilesfromasset(latexfileslocation_chaps,
						"Latex_AssetsTable2.tex", this);
				
				String[] individualasset;
				String[] allassets;
				Boolean heading=false;
				float picturewidth=125;
				float pictureheight;
			 
				
				siteassetstrings = new File(ProjectDirectory + "/"
						+ basedirectory[ASSETSFOLDER]).list();
				
				
				if(siteassetstrings[0]!= null){
					BufferedReader br10 = new BufferedReader(new InputStreamReader(
							getAssets().open("Latex_aasetstable2.txt")));
					while ((line = br10.readLine()) != null) {
						Log.d("line read from report", line);
						writer.write(line);
						writer.newLine();
					}
				}
				
				try {
					if (siteassetstrings!= null) {
						for(int k=0;k<siteassetstrings.length;k++){
						try{
							individualasset = new File(ProjectDirectory + "/"
									+ basedirectory[ASSETSFOLDER]+"/"+siteassetstrings[k]).list();
							Bitmap bitmap;
							bitmap=BitmapFactory
									.decodeFile(ProjectDirectory + "/"
											+ basedirectory[ASSETSFOLDER]+"/"+siteassetstrings[k]+"/"+individualasset[0]);
							float w1=bitmap.getWidth();
							float h1=bitmap.getHeight();
							float w2=picturewidth;
							float h2=(w2*h1)/w1;
							pictureheight=h2;
									
							String assetpath ="../../Assets/"+siteassetstrings[k]+"/"+individualasset[0];
							int st=0;
							if(!heading){
								BufferedReader br11 = new BufferedReader(new InputStreamReader(
										getAssets().open("Latex_Assets_initial.txt")));
								while ((line = br11.readLine()) != null) {
									if(line.contains("PICTUREWIDTH")){
										line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
									}
									if(line.contains("PICTUREHEIGHT")){
										line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
									}
									
									if(line.contains("compenentspath")){
										line=line.replace("compenentspath",assetpath);
										
									}
									if(line.contains("assetdescription")){
										int assetname = 0;
										int notes = 8;
										String tempassetname=siteassetstrings[k]; 
										String assetnotes="null";
										//read_report_Life_Hack_Innovations_Assets();
										int tempassetindex=0;
										
										for(int temp =0;temp<maxassets;temp++){
											try{
											if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname", (temp)).equals(tempassetname)){
												tempassetindex=temp;
												assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes",(temp));
												break;
											}}
											catch(Throwable e){
												
											}
										}
										
										String templine = line;
										if(!assetnotes.equals("null")){
										templine=templine.replace("assetdescription",assetnotes);
										}else{
											templine="";
										}
										line=line.replace("assetdescription",siteassetstrings[k]);
										writer.write(line);
										writer.newLine();
										line=templine;
									}
									Log.d("line read from report", line);
									st=1;
									heading=true;
									writer.write(line);
									writer.newLine();
								}
								
							}
							for(int j=st;j<individualasset.length;j++){
								
								bitmap=BitmapFactory
										.decodeFile(ProjectDirectory + "/"
												+ basedirectory[ASSETSFOLDER]+"/"+siteassetstrings[k]+"/"+individualasset[j]);
								w1=bitmap.getWidth();
								h1=bitmap.getHeight();
								w2=picturewidth;
								h2=(w2*h1)/w1;
								pictureheight=h2;
								assetpath ="../../Assets/"+siteassetstrings[k]+"/"+individualasset[j];
								
									BufferedReader br11 = new BufferedReader(new InputStreamReader(
											getAssets().open("Latex_Assets.txt")));
									while ((line = br11.readLine()) != null) {
										if(line.contains("PICTUREWIDTH")){
											line=line.replace("PICTUREWIDTH", String.valueOf(picturewidth)+"mm");
										}
										if(line.contains("PICTUREHEIGHT")){
											line=line.replace("PICTUREHEIGHT", String.valueOf(pictureheight)+"mm");
										}
										
										if(line.contains("compenentspath")){
											line=line.replace("compenentspath",assetpath);
											
										}
										if(line.contains("assetdescription")){
											int assetname = 0;
											int notes = 8;
											String tempassetname=siteassetstrings[k]; 
											String assetnotes="null";
											//read_report_Life_Hack_Innovations_Assets();
											int tempassetindex=0;
											
											for(int temp =0;temp<maxassets;temp++){
												try{
												if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname",(temp)).equals(tempassetname)){
													tempassetindex=temp;
													assetnotes=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes",(temp));
													break;
												}}
												catch(Throwable e){
													
												}
											}
											
											String templine = line;
											if(!assetnotes.equals("null")){
											templine=templine.replace("assetdescription",assetnotes);
											}else{
												templine="";
											}
											line=line.replace("assetdescription",siteassetstrings[k]);
											writer.write(line);
											writer.newLine();
											line=templine;
										}
										Log.d("line read from report", line);
										writer.write(line);
										writer.newLine();
								
								}
									
							}
						 }catch(Throwable e){
							u.log("Site Asset error");
						}
					 }
				  }
				} catch (Throwable e) {
					u.log("Site Asset error");
				}
				if(siteassetstrings[0]!= null){
					writer.write("\\end{longtable}");
					writer.newLine();
					writer.write("\\clearpage");
					writer.newLine();
				}
				
			}
			if(sitenotes){
				
				BufferedReader br8 = new BufferedReader(new InputStreamReader(
						getAssets().open("Latex_Notes.txt")));
				while ((line = br8.readLine()) != null) {
					Log.d("line read from report", line);
					
					writer.write(line);
					writer.newLine();
					}
				
				line=db.getValue(db.TABLE_NOTES, "notes");
				if(line.contains("\n")){
					line=line.replace("\n", "\n \\newline \n");
				
					}
					
				writer.write(line);
				writer.newLine();
			}
			
			
			execsum = false;
			facility = false;
			facilityasset=false;
			facilityenergy = false;
			BOM=false;
			floorplan = false;
			load = false;
			main1 = false;
			main2 = false;
			metering = false;
			optimization = false;
			riser = false;
			tempsensorlist = false;
			SAMreferencetable=false;
			lhiassets =false;
			sitesassets=false;
			sitespecificrecommendations=false;
			sitenotes=false;
			
			
			
			BufferedReader br9 = new BufferedReader(new InputStreamReader(
					getAssets().open("Latex_main2.txt")));
			while ((line = br9.readLine()) != null) {
				Log.d("line read from report", line);
				writer.write(line);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Throwable e) {
			showToast("There was an error righting the Tex File");
			e.printStackTrace();
			Log.d("Error Writing Main Tex File", "true");
			
		}

	}

	public void latexsupport() {

	
		
		File file1 = new File(latexfileslocation_pics);
		if (file1.exists())
			file1.delete();
		file1.mkdirs();
		File file2 = new File(latexfileslocation_chaps);
		if (file2.exists())
			file2.delete();
		file2.mkdirs();

//		u.copyfilesfromasset(latexfileslocation_pics, "Latex_CompanyLogo_0.png",
//				this);
//		u.copyfilesfromasset(latexfileslocation_pics, "Latex_CompanyLogo_1.png",
//				this);
//		u.copyfilesfromasset(latexfileslocation_pics, "Latex_CompanyLogo_2.png",
//				this);
//		u.copyfilesfromasset(latexfileslocation_pics, "Latex_CompanyLogo_3.png",
//				this);

		u.copyfilesfromasset(latexfileslocation_pics, "Latex_Coverpage_0.png",
				this);
		u.copyfilesfromasset(latexfileslocation_pics, "Latex_Coverpage_1.png",
				this);

		u.copyfilesfromasset(latexfileslocation_pics, "Latex_coverlhi.jpg",
				this);
		u.copyfilesfromasset(latexfileslocation_pics, "Latex_maestrologo.jpg",
				this);

	
	}

	

	public static void bluestacksimagequalitydialog(Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Alert")
				.setMessage(
						"Unfortunately BlueStacks currently is not supporting certain bitmap resolutions. Your final MOP images may have reduced resolution. Please transfer project to a tablet and run MOP modules again to attain optimimum resolutions.")
				.setIcon(R.drawable.ic_launcher)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).setCancelable(true);

		builder.create().show();

	}

	private void read_MCR() {

		
		
		int item = 0;
		int elcno = 1;
		int loadname = 2;
		int panellocation = 5;
		int voltage = 6;
		int ph = 7;
		int transconfig = 8;
		int breakersize = 9;
		int ctsize = 10;
		int ctphysical = 11;
		int ctqty = 12;
		int modbus = 10;
		int loadtype = 15;
		int loadsonpanel = 16;
		int maxrows = 0;
		int totalELC = 0;
		int totalCT = 0;
		int tempfloor = 1;
		int tempelc = 2;
		int tempzone = 4;
		int temploc = 6;
		
		int sheetnum = 1;
		int tempsheetnum = 2;

	

		MCCSVInputpath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/metering.csv";
		File textfile = new File(MCCSVInputpath);

		textfile.delete();
		textfile = new File(MCCSVInputpath);
		try {
			FileWriter fw = new FileWriter(textfile);
			PrintWriter pw = new PrintWriter(fw);
			Log.d("metering", "metering csv exists");
			new StringBuilder();

			String meteringinput;
			meteringinput = "Item///ELC///Load Name///Panel Location///Voltage///PH///Trans Config///Breaker Size///CT Size,Physical,Inside///CT QTY///Modbus Address///Comments///Load Type///Loads on each breaker or panel";
			
			pw.println(meteringinput + "\n");
			meteringinput = "";
			
			for (int lineno = 0; lineno < m[METERINGLIST]; lineno++) {
				String blankcolumn="";
				for (int column = ITEMNUMBERCOLUMN; column <= LOADSONEACHBREAKERCOLUMN; column++) {
					if(column!=CTSIZEAMPSCOLUMN){
						try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
								if(column==ITEMNUMBERCOLUMN){
									meteringinput = mcrstringarray[METERINGLIST][lineno][column];
									blankcolumn="";
								}else if(mcrstringarray[METERINGLIST][lineno][column].trim().equals("")){
									meteringinput = meteringinput + " ///";
									blankcolumn=""+ " ///";
								}else{
									meteringinput = meteringinput + "///"
											+ mcrstringarray[METERINGLIST][lineno][column];
									blankcolumn=""+ " ///";
								}
							}
						} catch (Throwable e) {
							meteringinput = meteringinput + " ///";
							blankcolumn=""+ " ///";
						}
					}
				}
				meteringinput = meteringinput.replaceAll("null", " ");
				if (!meteringinput
						.equals(blankcolumn)) {
					pw.println(meteringinput + "\n");
				}
				meteringinput = "";
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			
			// You'll need to add proper error handling here
		}

		
//Patricks Virtual meter code for latex		
		String Virtualmetercsvpath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_virtualmeters.csv";
		File Virtualmetercsvpathtextfile = new File(Virtualmetercsvpath);

		Virtualmetercsvpathtextfile.delete();
		Virtualmetercsvpathtextfile = new File(Virtualmetercsvpath);
		try {
			FileWriter fw = new FileWriter(Virtualmetercsvpathtextfile);
			PrintWriter pw = new PrintWriter(fw);
			Log.d("metering", "metering csv exists");
			new StringBuilder();

			String meteringinput;
			meteringinput = "Virtual Meter Number///Virtual Meter Name///Virtual Meter Formula///Virtual Meter Load Type";
			
			pw.println(meteringinput + "\n");
			meteringinput = "";
			Cursor cursor=db.getcursor(db.TABLE_MCR_VIRTUAL_METERINGLIST);
			cursor.moveToFirst();
			
			for (int j=0; j<cursor.getCount();j++){
				if(cursor.getString(0)!=null){
					pw.println(cursor.getString(0)+"///"+cursor.getString(1)+"///"+cursor.getString(2)+"///"+cursor.getString(3));
				}
				cursor.moveToNext();
			}
			cursor.close();
			
			pw.close();
			fw.close();
		} catch (IOException e) {
			
			
			// You'll need to add proper error handling here
		}
		
		

				MeteringbalmPath = ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Latex/Chapters/Latex_meteringBalm.csv";
				File textfile4 = new File(MeteringbalmPath);

				textfile4.delete();
				textfile4 = new File(MeteringbalmPath);
				try {
					FileWriter fw2 = new FileWriter(textfile4);
					PrintWriter pw2 = new PrintWriter(fw2);
					Log.d("metering", "metering Balm csv exists");

					String meteringbalminput;
					//new totals from BOM tab
					String gatewaycount="0";
					try{
					gatewaycount=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayservercount",0);
					int newmaxelc=u.i(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelccount",0));
					if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayservercount",0).equals("0")&&
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelccount",0).equals("0")&&
							newmaxelc > 0){
						gatewaycount="1";
					}}catch(Throwable e){
					gatewaycount="1";	
					}
					db.showfulldblog(DatabaseHandler.TABLE_BOM);
					meteringbalminput = "Assets///Count" + "\n" + 
							"Gateway Server///"+gatewaycount+"\n"+
							
							"ELC///"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelccount",0)+"\n"+
							"SAM///"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomsamcount",0)+"\n"+
							"Temp Sensor///"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtscount",0)+"\n"+
							"CT///"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomCTcount",0);
					
					meteringbalminput = meteringbalminput.replaceAll("null", " ");
					pw2.println(meteringbalminput + "\n");
					meteringbalminput = "";
					pw2.close();
					fw2.close();
				} catch (Throwable e1) {
					e1.printStackTrace();
					// You'll need to add proper error handling here
				}		

double totalBOMcost=0;		
//Latex BOM total Tex
		BOMtotalPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_BOM.tex";
		File textfileBOM = new File(BOMtotalPath);

		textfileBOM.delete();
		textfileBOM = new File(BOMtotalPath);
		try {
			FileWriter fw2 = new FileWriter(textfileBOM);
			PrintWriter pw2 = new PrintWriter(fw2);
			Log.d("BOM", "total BOM tex exists");
            
			String BOMtotalinput;
			BOMtotalinput = "\\section{Bill of Materials}"+"\n"+
					      "\\begin{table}[htbp]"+"\n"+
			                 " \\centering"+"\n"+
			                 "\\resizebox{\\linewidth}{!}{%"+"\n"+
					          "\\begin{tabular}{l c c r}";
			pw2.println(BOMtotalinput + "\n");
			BOMtotalinput = "";
			
			//new totals from BOM tab
			double count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayservercount",0));
			double unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayserverunitcost",0));
			double gatewaytotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+gatewaytotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelccount", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelcunitcost", 0));
			double ELCtotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+ELCtotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomsamcount", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomsamunitcost", 0));
			double SAMtotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+SAMtotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtscount", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsunitcost", 0));
			double TStotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+TStotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomCTcount", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomCTunitcost", 0));
			double CTtotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+CTtotalcost;
			
			BOMtotalinput = "\\rowcolor {lhitabletitle}"+"Hardware&Quantity&Unit Cost&Total Cost" + "\\\\ \n" + 
					"\\rowcolor {lhitablelight}"+"Gateway Server&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayservercount",0)+"&"+
					db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomgatewayserverunitcost",0)+"&"+String.format("%.2f", gatewaytotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitabledeep}"+"ELC&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelccount",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomelcunitcost",0)+"&"+String.format("%.2f", ELCtotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitablelight}"+"SAM&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomsamcount",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomsamunitcost",0)+"&"+String.format("%.2f", SAMtotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitabledeep}"+"Temp Sensor&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtscount",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsunitcost",0)+"&"+String.format("%.2f", TStotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitablelight}"+"CT&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomCTcount",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomCTunitcost",0)+"&"+String.format("%.2f", CTtotalcost)+"\\\\";
			
			
			BOMtotalinput = BOMtotalinput.replaceAll("null", " ");
			pw2.println(BOMtotalinput + "\n");
			BOMtotalinput = "";
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsdistance", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsdistanceunitcost", 0));
			double TSwiretotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+TSwiretotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomctdistance", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomctdistanceunitcost", 0));
			double CTwirietotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+CTwirietotalcost;
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bompowerwiredistance", 0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bompowerwiredistanceunitcost", 0));
			double Powerwiretotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+Powerwiretotalcost;
			
			
			BOMtotalinput = "\\rowcolor {lhitabletitle}"+"Wiring&Distance&Unit Cost&Total Cost" + "\\\\ \n" + 
					"\\rowcolor {lhitablelight}"+"Temp Sensor&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsdistance",0)+"&"+
					db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomtsdistanceunitcost",0)+"&"+String.format("%.2f", TSwiretotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitabledeep}"+"Current Transformer(CT)&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomctdistance",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bomctdistanceunitcost",0)+"&"+String.format("%.2f", CTwirietotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitablelight}"+"Power&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bompowerwiredistance",0)+"&"+
							db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bompowerwiredistanceunitcost",0)+"&"+String.format("%.2f", Powerwiretotalcost)+"\\\\";
			
			
			BOMtotalinput = BOMtotalinput.replaceAll("null", " ");
			pw2.println(BOMtotalinput + "\n");
			BOMtotalinput = "";
			
			count = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bommisccount",0));
			unitcost = u.d(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bommisccountunitcost",0));
			double bomMistotalcost = count * unitcost;
			totalBOMcost=totalBOMcost+bomMistotalcost;
			
			BOMtotalinput = "\\rowcolor {lhitabletitle}"+"Miscellaneous&Quantity&Unit Cost&Total Cost" + "\\\\ \n" + 
					"\\rowcolor {lhitablelight}"+"Miscellaneous&"+db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bommisccount",0)+"&"+
					db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,"bommisccountunitcost",0)+"&"+String.format("%.2f", bomMistotalcost)+"\\\\ \n"+
					"\\rowcolor {lhitabletitle}"+"Total&&&"+String.format("%.2f", totalBOMcost)+"\\\\";
			
			
			BOMtotalinput = BOMtotalinput.replaceAll("null", " ");
			pw2.println(BOMtotalinput + "\n");
			BOMtotalinput = "";
			
			BOMtotalinput="\\end{tabular}%"+"\n"+
						 "}"+"\n"+
			              "\\end{table}";
			pw2.println(BOMtotalinput + "\n");
			BOMtotalinput = "";
			
			pw2.close();
			fw2.close();
		} catch (Throwable e) {
			e.printStackTrace();
			// You'll need to add proper error handling here
		}

		

				
		
		
//Latex ctBOM
			CTbomPath = ProjectDirectory + "/"
						+ basedirectory[GENERATEDDOCUMENTSFOLDER]
						+ "/Latex/Chapters/Latex_ctbom.csv";
				File textfile5 = new File(CTbomPath);

				textfile5.delete();
				textfile5 = new File(CTbomPath);
				try {
					FileWriter fw2 = new FileWriter(textfile5);
					PrintWriter pw2 = new PrintWriter(fw2);
					Log.d("metering", " ctbom exists");

					String ctbominput;
					//new totals from BOM tab
				
					
					ctbominput = "CT Type///Quantity" + "\n";
					
					int typecount=bomcttablelayout.getChildCount();
					for(int h=0; h<typecount; h++){
						String type=((TextView)((TableRow)bomcttablelayout.getChildAt(h)).getChildAt(1)).getText().toString();
						String count=((TextView)((TableRow)bomcttablelayout.getChildAt(h)).getChildAt(2)).getText().toString();
						ctbominput=ctbominput+type+"///"+count+"\n";
								
					}
					ctbominput=ctbominput+"Total"+"///"+bomCTcount.getText().toString()+"\n";
					
					ctbominput = ctbominput.replaceAll("null", " ");
					pw2.println(ctbominput + "\n");
					ctbominput = "";
					pw2.close();
					fw2.close();
				} catch (Throwable e) {
					System.out.println("ctbom file didn't write");
					// You'll need to add proper error handling here
				}
		
				
				

		TempsensorPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_Tempsensors.csv";

		textfile5 = new File(TempsensorPath);

		textfile5.delete();
		textfile5 = new File(TempsensorPath);
		try {
			FileWriter fw1 = new FileWriter(textfile5);
			PrintWriter pw1 = new PrintWriter(fw1);
			Log.d("Tempsensor csv", "Tempsensor csv exists");
			new StringBuilder();

			String Tempsinput;
			Tempsinput = "Item///Floor///ELC///Zone Name///Location on FloorPlan";
			pw1.println(Tempsinput + "\n");
			Tempsinput = "";
			for (int lineno = 0; lineno < m[TEMPSENSORCOMMISSIONING]; lineno++) {
				Tempsinput = u.s(lineno) + "///"
						+ mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempfloor]
						+ "///" + mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempelc]
						+ "///" + mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempzone]
						+ "///" + mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][temploc];
				Tempsinput = Tempsinput.replaceAll("null", " ");
				if (!Tempsinput.contains("/// /// /// ///")) {
					pw1.println(Tempsinput + "\n");
				}
				Tempsinput = "";
			}
			pw1.close();
			fw1.close();
		} catch (Throwable e) {
			
			// You'll need to add proper error handling here
		}
		
		
		
		SamArrayReferencePath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_SamArrayReference.csv";

		File textfilesamarray = new File(SamArrayReferencePath);

		textfilesamarray.delete();
		textfilesamarray = new File(SamArrayReferencePath);
		
		
		try {
			FileWriter fw1 = new FileWriter(textfilesamarray);
			PrintWriter pw1 = new PrintWriter(fw1);
			Log.d("SamArrayReference", "SamArrayReference csv exists");
			String SAMarrayInput;
			SAMarrayInput = "Sam Reference///Number of SAMS///ELC Reference///Circuit Reference///DATA";
			pw1.println(SAMarrayInput + "\n");
			SAMarrayInput = "";
			
			
			ArrayList<String> type=db.getcolumn(db.TABLE_FLOORPLAN, 5, db.KEY_FLOORPLAN_TITLES);
            ArrayList<String> string= db.getcolumn(db.TABLE_FLOORPLAN, 15, db.KEY_FLOORPLAN_TITLES);
            ArrayList<String> masterelc= db.getcolumn(db.TABLE_FLOORPLAN, 9, db.KEY_FLOORPLAN_TITLES);
            System.out.println("lengthoftype,"+type.size());
            for (int y=0; y<type.size(); y++){
                 
              if(u.i(type.get(y))==FloorPlanView.TYPE_SAMARRAY){
            	 System.out.println("SAMarrayString ,"+string.get(y)); 
            	 SAMarrayInput=string.get(y).split(",")[0]+"///"+string.get(y).split(",")[1]+"///"+masterelc.get(y)+"///"+string.get(y).split(",")[2]+"///"+string.get(y).split(",")[3];
            	 SAMarrayInput = SAMarrayInput.replaceAll("null", " ");
 				if (!SAMarrayInput.contains("/// /// /// ///")) {
 					System.out.println("SAM array CSV, "+SAMarrayInput);
 					pw1.println(SAMarrayInput + "\n");
 				}
 				SAMarrayInput = ""; 
                  
               }
            }

			pw1.close();
			fw1.close();
		} catch (Throwable e) {
			System.out.println("SAM array CSV Error");
		}
		
		

	}

	public void read_reportexcel(DatabaseHandler db) throws IOException {
		Log.d("Latex Support","Read report started");

		int assetspage = 1;
		int consumptionpage = 2;
		int companyname = 6;
		int sitename = 8;
		int sitesizec = 9;
		int sitedescription = 10;
		int maxrows = 0;
		
		int assetname = 0;
		int quantity = 1;
		int type = 2;
		int manufacture = 3;
		int power = 4;
		int location = 5;
		int notes = 8;
		int start = 1;
		
		int month = 0;
		int kw = 1;
		int localcurrency = 2;
		int secondcurrency = 3;
		
		
		int comprows = 0;
		double totalkw = 0;
		double totallocalcurr = 0;
		double totalsecondcurr = 0;
		int seccondcurrencytype = 6;
		String tempconsumption = null;
		
		String Site_Name = "null";
		//String Site_Description="null";
		String sizeinmeters = "null";
		String sizeinfts = "null";
		String Company_Name = "null";
		String seccurrsymbol="null";
		String localcurrsymbol="null";
		DecimalFormat datanumberformat=new DecimalFormat("#,###,###.00");
		//intializing consumption values
		for(int in=0;in<consumptiondestinguishers;in++){
			for(int inn=0;inn<maxexcelcolumns;inn++){
				Consumptionvalues[in][inn]="0.0";
			}
		}
		
		db.showtableslog();
		db.showfulldblog(db.TABLE_SITEINFO);
		
		
		
		Company_Name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename",0);
		Site_Name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename",1);
		try{
		sizeinmeters=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizem",1);
		System.out.println("sizeinmeters,"+ sizeinmeters);
		}catch(Throwable e){
			sizeinmeters="0";
		}
		try{
		sizeinfts=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitesizeft",1);
		System.out.println("sizeinfts,"+ sizeinfts);
		}catch(Throwable e){
			sizeinfts="0";
		}
		//Site_Description = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitedescription" + u.s(1));
		

		try {
			
		
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[assetname][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetname" ,k);
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[quantity][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetquantity",(k));
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[type][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assettype",(k));
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[manufacture][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetmodel",(k));
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[power][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetpower" ,(k));
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[location][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetlocation" ,(k));;
				}catch(Throwable e){
					
				}
			}
			for (int k = 0; k < i; k++) {
				try{
				Assetvalues[notes][k] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_ASSETINFO, "assetnotes" ,(k));;
				}catch(Throwable e){
					
				}
			}
			

		} catch (Throwable e) {
			
		}

		try {
			int wb = REPORT;
			int ws1 = CONSUMPTION;

			// checkfirstrow
			String cellstart = "A2";
			String cellstop = "H2";

			int i = 0;

			for (int num = 0; num <= u.cellx(cellstop); num++) {

				String value = "";
				if (num == 0) {
					try {
						Consumptionvalues[month][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptiondate"
								,(i));
						
					} catch (Throwable e) {

					}
				}
				if (num == 1) {
					try {
						Consumptionvalues[kw][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptionkwh"
								,(i));
						if(comprows<i){
							comprows=i;
						}
						try {
							tempconsumption =Consumptionvalues[kw][i];
							if (tempconsumption.contains(",")) {
								tempconsumption = tempconsumption.replace(",", "");
							}
							totalkw = totalkw + Double.parseDouble(tempconsumption);
						} catch (Throwable e) {
							
						}
						consdoub[0] = u.d(Consumptionvalues[kw][i]);
						Log.d("consdoub[0] is being set to", Consumptionvalues[kw][i]);
					} catch (Throwable e) {

					}
				}
				
				if (num == 5) {
					try {
						localcurrsymbol = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionlocalcurrencystring",0);
						localcurrsymbol = Character.toString(localcurrsymbol.charAt(1));
						
					} catch (Throwable e) {

					}
				}
				
				
				if (num == 2) {
					try {
						Consumptionvalues[localcurrency][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionlocalcurrency" ,(i));
						if(comprows<i){
							comprows=i;
						}
						try {
							tempconsumption = Consumptionvalues[localcurrency][i];
							if (tempconsumption.contains(",")) {
								tempconsumption = tempconsumption.replace(",", "");
							}
							if (tempconsumption.contains(localcurrsymbol)) {
								tempconsumption = tempconsumption.replace(
										localcurrsymbol, "");
							}
							totallocalcurr = totallocalcurr
									+ Double.parseDouble(tempconsumption);
						} catch (Throwable e) {
							

						}
					} catch (Throwable e) {

					}
				}
				if (num == 6) {
					try {
						seccurrsymbol = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionsecondcurrencystring",0);
						
						seccurrsymbol = Character.toString(seccurrsymbol.charAt(1));
						
						
						
					} catch (Throwable e) {

					}
				if (num == 3) {
					try {
						Consumptionvalues[secondcurrency][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionsecondcurrency" ,(i));
						if(comprows<i){
							comprows=i;
						}
						
						try {
							tempconsumption = Consumptionvalues[secondcurrency][i];
							if (tempconsumption.contains(",")) {
								tempconsumption = tempconsumption.replace(",", "");
							}
							if (tempconsumption.contains(seccurrsymbol)) {
								tempconsumption = tempconsumption.replace(
										seccurrsymbol, "");
							}

							totalsecondcurr = totalsecondcurr
									+ Double.parseDouble(tempconsumption);
						} catch (Throwable e) {
							
						}	
					} catch (Throwable e) {

					}
				}
			}

			}

			int h = 1;

			loop: while (h != 0) {

				cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
				cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);
				i++;

				// ((Spinner)ViewHolder[wb][ws][u.cellx(cellstart)+5][u.celly(cellstart)]);
				h = 0;
				for (int num = 0; num <= u.cellx(cellstop); num++) {
					Log.d("Attempting consumption iteration" + i, "true");

					String value = "";

					if (num == 0) {
						try {
							Consumptionvalues[month][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptiondate"
									,(i));
							
							h++;
						} catch (Throwable e) {

						}

					}
					if (num == 1) {
						try {
							Consumptionvalues[kw][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION, "consumptionkwh"
									,(i));
							if(comprows<i){
								comprows=i;
							}
							try {
								tempconsumption =Consumptionvalues[kw][i];
								if (tempconsumption.contains(",")) {
									tempconsumption = tempconsumption.replace(",", "");
								}
								totalkw = totalkw + Double.parseDouble(tempconsumption);
							} catch (Throwable e) {
								
							}
							
							h++;
						} catch (Throwable e) {

						}

					}
					if (num == 2) {
						try {
							Consumptionvalues[localcurrency][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
									"consumptionlocalcurrency" ,(i));
							if(comprows<i){
								comprows=i;
							}
							try {
								tempconsumption = Consumptionvalues[localcurrency][i];
								if (tempconsumption.contains(",")) {
									tempconsumption = tempconsumption.replace(",", "");
								}
								if (tempconsumption.contains(localcurrsymbol)) {
									tempconsumption = tempconsumption.replace(
											localcurrsymbol, "");
								}
								totallocalcurr = totallocalcurr
										+ Double.parseDouble(tempconsumption);
							} catch (Throwable e) {
								

							}
							
							
							h++;
						} catch (Throwable e) {

						}

					}
				
					
					if (num == 3) {
						try {
							Consumptionvalues[secondcurrency][i] = db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_CONSUMPTION,
									"consumptionsecondcurrency" ,(i));
							if(comprows<i){
								comprows=i;
							}
							
							try {
								tempconsumption = Consumptionvalues[secondcurrency][i];
								if (tempconsumption.contains(",")) {
									tempconsumption = tempconsumption.replace(",", "");
								}
								if (tempconsumption.contains(seccurrsymbol)) {
									tempconsumption = tempconsumption.replace(
											seccurrsymbol, "");
								}

								totalsecondcurr = totalsecondcurr
										+ Double.parseDouble(tempconsumption);
							} catch (Throwable e) {
								
							}	
							h++;
						} catch (Throwable e) {

						}
					}	
				}

			}	
			
		
		} catch (Throwable e) {
			
		}

		ConsumptionPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_consumption1.csv";
		File textfile = new File(ConsumptionPath);

		textfile.delete();
		textfile = new File(ConsumptionPath);
		FileWriter fw = new FileWriter(textfile);
		PrintWriter pw = new PrintWriter(fw);
		try {
			
			Log.d("Consumption", "consumption csv exists");
			new StringBuilder();
			String consumptioninput;
			consumptioninput = "Site Name///" + Site_Name;
			consumptioninput = consumptioninput.replaceAll("null", " ");
			
			pw.println(consumptioninput);
			
			if(sizeinmeters.equals("0")||sizeinmeters.equals("null")){
				consumptioninput = "Total Building Area///Unknown";
			}else{
				consumptioninput = "Total Building Area///" + datanumberformat.format(Double.parseDouble(sizeinmeters)) + "(m2)" + " "
						+ datanumberformat.format(Double.parseDouble(sizeinfts)) + " (ft2)";
			}

			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw.println(consumptioninput);
			consumptioninput = "Annual Electric consumption[kWh]///"
					+ datanumberformat.format(totalkw);
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw.println(consumptioninput);
			consumptioninput = "Annual Electric consumption[£]///"
					+ u.priceWithDecimal(totalsecondcurr);
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw.println(consumptioninput);
			double cost = totalsecondcurr / totalkw;
			consumptioninput = "Cost [£/kWh]///" + u.priceWithDecimal(cost);
			consumptioninput = consumptioninput.replaceAll("null", " ");
			consumptioninput = consumptioninput.replaceAll("NaN", " ");
			pw.println(consumptioninput);

			double Ratio = totalkw / Double.parseDouble(sizeinmeters);
			consumptioninput = "Ratio [kWh/(m2.year)]///"
					+ Double.toString(Ratio);
			consumptioninput = consumptioninput.replaceAll("null", " ");
			consumptioninput = consumptioninput.replaceAll("NaN", " ");
			pw.println(consumptioninput);
			pw.close();
			fw.close();
		} catch (Throwable e) {
			pw.close();
			fw.close();
			e.printStackTrace();
			System.out.println("couldn't make consumption1 properly");
			// You'll need to add proper error handling here
		}

		ConsumptionPath2 = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_consumption2.csv";
		File textfile4 = new File(ConsumptionPath2);

		textfile4.delete();
		textfile4 = new File(ConsumptionPath2);
		FileWriter fw4 = new FileWriter(textfile4);
		PrintWriter pw4 = new PrintWriter(fw4);
		try {
			
			Log.d("metering", "metering csv exists");
			new StringBuilder();

			String consumptioninput2;
			consumptioninput2 = "Month///Cons(kWh) ///Cons(GBP)///Cons(USD)";
			pw4.println(consumptioninput2 + "\n");
			consumptioninput2 = "";
			for (int lineno = 1; lineno <= comprows; lineno++) {
				consumptioninput2 = Consumptionvalues[month][lineno - 1]
						+ "///" + datanumberformat.format(Double.parseDouble(Consumptionvalues[kw][lineno - 1])) + "///"
						+ u.priceWithDecimal(Double.parseDouble(Consumptionvalues[localcurrency][lineno - 1])) + "///"
						+ u.priceWithDecimal(Double.parseDouble(Consumptionvalues[secondcurrency][lineno - 1]));
				consumptioninput2 = consumptioninput2.replaceAll("null", " ");
				pw4.println(consumptioninput2 + "\n");
				consumptioninput2 = "";
			}
			consumptioninput2 = "Total Annual Consumption///"
					+datanumberformat.format( totalkw) + "///"
					+ u.priceWithDecimal(totalsecondcurr) + "///"
					+ u.priceWithDecimal(totallocalcurr);
			consumptioninput2 = consumptioninput2.replaceAll("null", " ");
			pw4.println(consumptioninput2 + "\n");

			pw4.close();
			fw4.close();
		} catch (Throwable e) {
			pw4.close();
			fw4.close();
			System.out.println("couldn't make consumption2 properly");
			// You'll need to add proper error handling here
		}

		GeneralPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_General.csv";
		File textfile2 = new File(GeneralPath);

		textfile2.delete();
		textfile2 = new File(GeneralPath);
		
		FileWriter fw2 = null;
		try {
			fw2 = new FileWriter(textfile2);
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		PrintWriter pw2 = new PrintWriter(fw2);
		
		try {
			
			Log.d("General", "Genral csv exists");
			new StringBuilder();
			String consumptioninput;
			consumptioninput = "Company Name///" + Company_Name;
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw2.println(consumptioninput);
			consumptioninput = "Site Name///" + Site_Name;
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw2.println(consumptioninput);
			if(sizeinmeters.equals("0")){
				consumptioninput = "Site Size///Unknown";
			}else{
				consumptioninput = "Site Size///" + datanumberformat.format(Double.parseDouble(sizeinmeters)) + "(m2)" + " "
						+ datanumberformat.format(Double.parseDouble(sizeinfts)) + " (ft2)";
			}
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw2.println(consumptioninput);
			//consumptioninput = "Site Description///" + Site_Description;
			//consumptioninput = consumptioninput.replaceAll("null", " ");
			//pw.println(consumptioninput);
			if(totalkw==0){
				consumptioninput = "Annual Electric consumption[kWh]///Unknown";
			}else{
				consumptioninput = "Annual Electric consumption[kWh]///"
						+ Double.toString(totalkw);
			}
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw2.println(consumptioninput);
			
			if(totalsecondcurr==0){
				consumptioninput = "Annual Electric consumption[£]///Unknown";
			}else{
				consumptioninput = "Annual Electric consumption[£]///"
					+ u.priceWithDecimal(totalsecondcurr);
			}
			consumptioninput = consumptioninput.replaceAll("null", " ");
			pw2.println(consumptioninput);

			pw2.close();
			fw2.close();
		} catch (Throwable e) {
			System.out.println("couldn't make General Table properly");
			pw2.close();
			try {
				fw2.close();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			// You'll need to add proper error handling here
		}

		AssetsPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_Assets.csv";

		File textfile3 = new File(AssetsPath);
   
		textfile3.delete();
		textfile3 = new File(AssetsPath);
		FileWriter fw1 = new FileWriter(textfile3);
		PrintWriter pw1 = new PrintWriter(fw1);
		try {
			
			Log.d("metering", "metering csv exists");
			new StringBuilder();

			String assetsinput;
			assetsinput = "Slno.///Asset Name///Quantity///Type///Manufature/Model///Power(kW)///Location///Notes";
			pw1.println(assetsinput + "\n");
			assetsinput = "";
			int slno=1;
			for (int lineno = 1; lineno <= i; lineno++) {
				try{
			        Boolean siteasset=true;
			        try{
						Log.d("Asset manufacture name",Assetvalues[manufacture][lineno - 1]);
			        	
			         }catch(Throwable e){
						Log.d("assetname error","error"+u.s(lineno-1));
						Assetvalues[manufacture][lineno - 1]="  ";
					}
			        try{
						if(Assetvalues[manufacture][lineno - 1].equals("Life Hack Innovations")){
							siteasset=false;
						}}
					catch(Throwable e){
						siteasset=true;
					}
					if(siteasset){
						assetsinput = u.s(slno) + "///"
								+ Assetvalues[assetname][lineno - 1] + "///"
								+ Assetvalues[quantity][lineno - 1] + "///"
								+ Assetvalues[type][lineno - 1] + "///"
								+ Assetvalues[manufacture][lineno - 1] + "///"
								+ Assetvalues[power][lineno - 1] + "///"
								+ Assetvalues[location][lineno - 1] + "///"
								+ Assetvalues[notes][lineno - 1];
						assetsinput = assetsinput.replaceAll("null", " ");
						pw1.println(assetsinput + "\n");
						slno++;
					}
						assetsinput = "";
				}catch(Throwable e){
					Log.d("siteasset","error");
					
				}
		   }
			pw1.close();
			fw1.close();
		} catch (Throwable e) {
			pw1.close();
			fw1.close();
			System.out.println("couldn't make Assets properly");
			// You'll need to add proper error handling here
		}

	
		SiterecommendationsPath = ProjectDirectory + "/"
				+ basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Latex/Chapters/Latex_Siterecommendations.csv";

		File textfile5 = new File(SiterecommendationsPath);
   
		textfile5.delete();
		textfile5 = new File(SiterecommendationsPath);
		SQLiteDatabase tempdb = db.getReadableDatabase();
	
		Cursor cursor = tempdb.query(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, null, null,
				null, null, null, null, null);
		
		cursor.moveToFirst();
		
		int numberofcolumns=cursor.getColumnCount();
		int numberofrows=cursor.getCount();
		String[] dbkeys =new String[numberofrows];
		String[] dbvalues =new String[numberofrows]; 
		
		Log.d("Columns=",u.s(numberofcolumns));
		Log.d("Rows=",u.s(numberofrows));
		String[] columnnames=cursor.getColumnNames();
		String columnnamestring="";
		for (String c:columnnames){
			columnnamestring=columnnamestring+","+c;
		}
		Log.d("Column Names",columnnamestring);
		for (int lineno = 0; lineno < numberofrows; lineno++) {
			try{	    
				   dbkeys[lineno]= cursor.getString(1);
				   dbvalues[lineno]= cursor.getString(2);
				   cursor.moveToNext();
			}catch(Throwable e){
					Log.d("siterecommendation","error");
					
				}
		   }
		cursor.close();
		;
		

		FileWriter fw3 = new FileWriter(textfile5);
		PrintWriter pw3 = new PrintWriter(fw3);
		try {
			
			Log.d("site recommendation", "Site Recommendation csv exists");
			new StringBuilder();

			String Siterecommendationsinput;
			Siterecommendationsinput = "Slno.///Recommendations///Additional Description";
			pw3.println(Siterecommendationsinput + "\n");
			Siterecommendationsinput = "";
			int slno=1;
			for (int lineno = 0; lineno < numberofrows; lineno++) {
			try{	    
				   int notesnumber=-1;
				   String Recommendationstring;
				   if(dbkeys[lineno].contains("check")){
					   for (int l = 0; l < numberofrows; l++) {
					       String tempdbvalue =  dbkeys[lineno].replace("check", "notes"); 
						   if(dbkeys[l].equals(tempdbvalue)){
							  if(!dbvalues[l].isEmpty()){
							   notesnumber=l;
							  }
						   }
					   }
					   if(dbvalues[lineno].equals("true")){
						   Recommendationstring=dbkeys[lineno].replace("check", "")+"is present on site";
						   if(!(notesnumber==-1)){
							   Siterecommendationsinput = u.s(slno)+"///"+Recommendationstring+"///"+dbvalues[notesnumber];  
							   Siterecommendationsinput = Siterecommendationsinput.replaceAll("null", " ");
							   pw3.println(Siterecommendationsinput + "\n");
							   slno++;
						   }else{
							   Siterecommendationsinput = u.s(slno)+"///"+Recommendationstring+"///"; 
							   Siterecommendationsinput = Siterecommendationsinput.replaceAll("null", " ");
							   pw3.println(Siterecommendationsinput + "\n");
							   slno++;
						   }
					   }else{
						   Recommendationstring=dbkeys[lineno].replace("check", "")+"is not present on site";
						   if(!(notesnumber==-1)){
							   Siterecommendationsinput = u.s(slno)+"///"+Recommendationstring+"///"+dbvalues[notesnumber];  
							   Siterecommendationsinput = Siterecommendationsinput.replaceAll("null", " ");
							   pw3.println(Siterecommendationsinput + "\n");
							   slno++;
						   }else{
							   
						   }
					   }
					}else{
						 int checknumber=-1;
						if(dbkeys[lineno].contains("notes")){
						 for (int l = 0; l < numberofrows; l++) {
						       String tempdbvalue =  dbkeys[lineno].replace("notes", "check"); 
							   if(dbkeys[l].equals(tempdbvalue)){
								   checknumber=l;
							   }
						   }
						if(checknumber==-1){
							   String reco=dbkeys[lineno].replace("notes", " ")+"is not present on site";
							   Siterecommendationsinput = u.s(slno)+"///"+reco+"///"+dbvalues[lineno];  
							   Siterecommendationsinput = Siterecommendationsinput.replaceAll("null", " ");
							   pw3.println(Siterecommendationsinput + "\n");
							   slno++;
						}
						}
					}
				   
				   Siterecommendationsinput = "";	
			}catch(Throwable e){
					Log.d("siterecommendation","error");
					
				}
		   }
			
			
		    pw3.close();
			fw3.close();
		} catch (Throwable e) {
			pw3.close();
			fw3.close();
			System.out.println("couldn't make Recommendations properly");
			// You'll need to add proper error handling here
		}
		
		
	}

	public void sethourglassicons() {

		hourglasslistener = hourglasslistener();

		setonehourglass(riserdiagrambutton);
		setonehourglass(mopmcrbutton);
		setonehourglass(loaddiagrambutton);
		setonehourglass(mopfloorplanbutton);
		setonehourglass(latexbutton);
		setonehourglass(savereportbutton);

		Tabs1.floorplanpicturebutton.setClickable(false);

	}

	public void removehourglassicons() {

		restoreonehourglass(Tabs1.riserdiagrambutton,
				R.drawable.riserdiagramicon);
		restoreonehourglass(Tabs1.mopmcrbutton, R.drawable.mcricon);
		restoreonehourglass(Tabs1.loaddiagrambutton,
				R.drawable.loaddiagrambutton);
		restoreonehourglass(Tabs1.mopfloorplanbutton, R.drawable.floorplanicon);
		restoreonehourglass(Tabs1.latexbutton, R.drawable.latex);
		restoreonehourglass(Tabs1.savereportbutton, R.drawable.save);

		Tabs1.floorplanpicturebutton.setClickable(true);
	}

	public void setonehourglass(final ImageView iv) {

		int resourceint = R.drawable.hourglassicon;
		BitmapFactory.decodeResource(getResources(),
				resourceint);
		iv.setImageResource(resourceint);
		iv.setClickable(false);
		iv.addOnLayoutChangeListener(hourglasslistener);

	}

	public void restoreonehourglass(ImageView iv, int drawable) {
		iv.setImageResource(drawable);
		iv.setClickable(true);
		iv.removeOnLayoutChangeListener(hourglasslistener);
		iv.setAnimation(null);
	}

	public ImageView.OnLayoutChangeListener hourglasslistener() {

		OnLayoutChangeListener olcl = new ImageView.OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {

				RotateAnimation animation = new RotateAnimation(0f, 350f,
						v.getWidth() / 2, v.getHeight() / 2);
				animation.setInterpolator(new LinearInterpolator());
				animation.setRepeatCount(Animation.INFINITE);
				animation.setDuration(700);

				((ImageView) v).startAnimation(animation);

			}
		};
		return olcl;

	}

	public void savetask() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						writereport();
						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						
					}
					writereportxl();
					
					savemcrtabletoexcel();
					
					
					WriteExcelFacilityEnergyConsumption(ProjectDirectory + "/"
							+ basedirectory[GENERATEDDOCUMENTSFOLDER]
							+ "/Facility_Energy_Consumption_Template.xls");
					// startActivity(u.intent("Writepreferencesfile"));
					
					if (u.isbluestacks()) {

						File nonbsdirectory = new File(ProjectDirectory);
						// if(u.isbluestacks()){
						// 
						// set("masterfoldername","bstfolder/BstSharedFolder/lhi");
						// }else{
						// 
						
						File movetolocation = new File(Environment
								.getExternalStorageDirectory().toString()
								+ "/bstfolder/BstSharedFolder/lhi/"
								+ foldername);
						movetolocation.mkdirs();
						try {
							u.copyDirectory(nonbsdirectory, movetolocation);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
					}
				}
			});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			
		}
	}

	public void clearingvalues() {
		Assetvalues = new String[maxassetdistinguishers][maxassets];
	}

	

	private void foldersync() {
		try {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://"
							+ Environment.getExternalStorageDirectory())));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://"
							+ Environment.getExternalStorageDirectory() + "/"
							+ masterfoldername + "")));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/"
							+ basedirectory[GENERATEDDOCUMENTSFOLDER])));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/" + basedirectory[ASSETSFOLDER])));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/" + basedirectory[COMPONENTSFOLDER])));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/"
							+ basedirectory[CONSUMPTION])));
			sendBroadcast(new Intent(
					Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/" + basedirectory[DOCUMENTSFOLDER])));
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse(ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER])));
			
			try{
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse(ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]+"/ELCconfig")));
			}catch(Throwable e){
				
			}
			
			try{
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse(ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]+"/Latex")));
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse(ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]+"/Latex/Chapters")));
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse(ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]+"/Latex/Picturess")));
				
			}catch(Throwable e){
				
			}
			

			try{
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse(ProjectDirectory + "/"
								+ basedirectory[GENERATEDDOCUMENTSFOLDER]+"/Floorplans")));
			}catch(Throwable e){
				
			}
			try{
				siteassetstrings = new File(ProjectDirectory + "/"
						+ basedirectory[ASSETSFOLDER]).list();
				for(int send=0;send<siteassetstrings.length;send++){
					try{
						sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
								Uri.parse(ProjectDirectory + "/"
								+ basedirectory[ASSETSFOLDER]+"/"+siteassetstrings[send])));
					}catch(Throwable e){
						
					}
				}
			}catch(Throwable e){
				
			}
			try{
				String[] lhiassetsstrings = new File(ProjectDirectory + "/"
						+ basedirectory[COMPONENTSFOLDER]).list();
				for(int send=0;send<lhiassetsstrings.length;send++){
					try{
						sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
								Uri.parse(ProjectDirectory + "/"
								+ basedirectory[COMPONENTSFOLDER]+"/"+lhiassetsstrings[send])));
					}catch(Throwable e){
						
					}
				}
			}catch(Throwable e){
				
			}
			
		} catch (Throwable e) {

		}
		// String path1= "file://"
		// + Environment.getExternalStorageDirectory().toString();
		// String path2 = "file://"
		// + Environment.getExternalStorageDirectory().toString()
		// + "/"+masterfoldername+"";
		// 

		// MediaScannerConnection.scanFile(this,
		// new String[] {path1}, null,
		// new MediaScannerConnection.OnScanCompletedListener() {
		// public void onScanCompleted(String path, Uri uri) {
		// 
		// 
		// }
		// });
		// MediaScannerConnection.scanFile(this,
		// new String[] {ProjectDirectory}, null,
		// new MediaScannerConnection.OnScanCompletedListener() {
		// public void onScanCompleted(String path, Uri uri) {
		// 
		// 
		// }
		// });

	}

//	public void setupprogressreporttab() {
//		sitewalkprogressbar = (ProgressBar) findViewById(R.id.sitewalkxmlprogressbar);
//		updateprogressbutton = (Button) findViewById(R.id.updateprogressxmlbutton);
//		siteinfoprogresstextview = (TextView) findViewById(R.id.siteinfoprogressxmltextview);
//		assetsprogresstextview = (TextView) findViewById(R.id.assetsprogressxmltextview);
//		consumptionprogresstextview = (TextView) findViewById(R.id.consumptionprogressxmltextview);
//		recommendationsprogresstextview = (TextView) findViewById(R.id.recommendationsprogressxmltextview);
//
//		siteinfoprogresstextview.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				tabHost.setCurrentTab(SITETAB);
//			}
//		});
//
//		assetsprogresstextview.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				tabHost.setCurrentTab(ASSETSTAB);
//			}
//		});
//
//		consumptionprogresstextview
//				.setOnClickListener(new View.OnClickListener() {
//					public void onClick(View arg0) {
//						tabHost.setCurrentTab(CONSUMPTIONTAB);
//					}
//				});
//
//		recommendationsprogresstextview
//				.setOnClickListener(new View.OnClickListener() {
//					public void onClick(View arg0) {
//						tabHost.setCurrentTab(RECOMMENDATIONSTAB);
//					}
//				});
//
//		updateprogressbutton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				updateprogressforreport();
//			}
//		});
//
//	}
//
//	public void updateprogressforreport() {
//		List<View> allviews = u.getallchildrenwithspinnerstoclear(siteinfotabview);
//		for (int l = 0; l < allviews.size(); l++) {
//			
//			allviews.get(l).setBackgroundColor(Color.TRANSPARENT);
//		}
//
//		List<View> emptyviews = u
//				.getallchildrenwithspinnersthatdonthavevalues(siteinfotabview);
//		for (int l = 0; l < emptyviews.size(); l++) {
//			
//			emptyviews.get(l).setBackgroundColor(transparentred);
//		}
//
//		Log.d("allviews.size(), emptyviews, allvies", allviews.size() + ", "
//				+ emptyviews.size() + ", " + allviews.size());
//		sitewalkprogress = (int) (((double) allviews.size() - (double) emptyviews
//				.size()) / (double) allviews.size());
//		
//
//		
//		sitewalkprogressbar.setProgress(sitewalkprogress);
//
//	}

	public String assigingvaluestolatexvariables(String input) {
		String output = input;
		
		if (output.contains("clientnamelatex")) {
			output = output.replace("clientnamelatex", clientnamelatex);
			
			
		}
		if (output.contains("headermaestrologo")) {
			output = output.replace("headermaestrologo", headermaestrologo);
		}
		if (output.contains("paperwidthlatex")) {
			output = output.replace("paperwidthlatex", paperwidthlatex);
		}
		if (output.contains("paperheightlatex")) {
			output = output.replace("paperheightlatex", paperheightlatex);
		}
		if (output.contains("coverpagewidthlatex")) {
			output = output.replace("coverpagewidthlatex", coverpagewidthlatex);
		}
		if (output.contains("coverpageheightlatex")) {
			output = output.replace("coverpageheightlatex",
					coverpageheightlatex);
		}
		if (output.contains("coverpagefilepathlatex")) {
			output = output.replace("coverpagefilepathlatex",
					coverpagefilepathlatex);
		}
		if (output.contains("footercolorlatex")) {
			output = output.replace("footercolorlatex", footercolorlatex);
		}
		if (output.contains("coverpagebackgroundcolor")) {
			output = output.replace("coverpagebackgroundcolor",
					coverpagebackgroundcolor);
		}
		if (output.contains("tabletitlecolor")) {
			output = output.replace("tabletitlecolor", tabletitlecolor);
		}
		if (output.contains("tabledeepcolor")) {
			output = output.replace("tabledeepcolor", tabledeepcolor);
		}
		if (output.contains("tablelightcolor")) {
			output = output.replace("tablelightcolor", tablelightcolor);
		}
		if (output.contains("sectionheadingcolorlatex")) {
			output = output.replace("sectionheadingcolorlatex",
					sectionheadingcolorlatex);
		}
		if (output.contains("subselectionhead2")) {
			output = output.replace("subselectionhead2", subselectionhead2);
		}

		return output;
	}

	public void setcompanylogo(String lastcompanyname, int selection) {
		try {

			try {
				deleteoldlogopic(lastcompanyname);
			} catch (Throwable e) {
				e.printStackTrace();
				u.log("Couldn't delete old picture");
			}

			typeforgetpicture = SITETAB;
			int type = typeforgetpicture;
			getPreferences();
			typeselected = SITETAB;
			numberselected = "0";
			new File(ProjectDirectory);
			File file = null;

			if (type==SITETAB) {

				file = new File(createnewimagename(null,SITETAB,
						u.i(numberselected), 0, null));
				int v1 = 0;
				while (file.exists()) {
					v1++;
					file = new File(createnewimagename(null,SITETAB,
							u.i(numberselected), v1, null));

				}
			}
			

			// make directories in path if they don't exist
			File parent = file.getParentFile();
			if (!parent.exists() && !parent.mkdirs()) {
				throw new IllegalStateException("Couldn't create dir: "
						+ parent);
			}

			String name = "Latex_CompanyLogo_" + u.s(selection) + ".png";
			

			u.copyfilefromAssets(name, file.getAbsolutePath(), ctx);
			PICTURESAMPLESIZE = 0;
			try {
				refresh1pic(type, 0, 0);
			} catch (Throwable e) {
				u.log("no logo set, adding a new one");
				add1pic();
			}
			try{
				
				PICTURESAMPLESIZE = u.i(tabimageresolutions);
				} catch( Throwable e)
				{
					e.printStackTrace();
					PICTURESAMPLESIZE=16;
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

	}

	public void sugarsync() {

		File currentprojectdirectory = new File(ProjectDirectory);
		// if(u.isbluestacks()){
		// 
		// set("masterfoldername","bstfolder/BstSharedFolder/lhi");
		// }else{
		// 
		
		String Sugarpath = mainsugarsyncdirectory+"/lhi/";
		String Newproname = foldername;
		String Newprowver;
		String engineername = engineernamesp.getSelectedItem().toString();
		if (!foldername.contains(engineername)) {
			
			Newproname = foldername + "_" + engineername;
		}
		File movetolocation = new File(Sugarpath + Newproname);
		try {
			if (!movetolocation.exists()) {
				u.copyDirectory_Cloud(currentprojectdirectory, movetolocation);
			} else {
				for (int v = 1; v <= maxversions; v++) {
					Newprowver = Newproname + "_V_" + u.s(v);
					movetolocation = new File(Sugarpath + Newprowver);
					if (!movetolocation.exists()) {
						break;
					}
				}
				u.copyDirectory_Cloud(currentprojectdirectory, movetolocation);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

	public void deleteoldlogopic(String lastcompanyname) {

		string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER];
		File basedirectory = new File(string);
		

		for (File parentfolder : basedirectory.listFiles()) {
			u.log(FilenameUtils.getBaseName(parentfolder.getName()));
			String filename=FilenameUtils.getBaseName(parentfolder.getName());
//			u.log("filename"+filename);
//			u.log(sitename[0].sp.getSelectedItem().toString());
			if (filename.contains(sitename[0].sp.getSelectedItem().toString())||filename.contains(lastcompanyname)) {
				
				for (File name : parentfolder.listFiles()) {
					name.delete();
				}
				if (parentfolder.list().length == 0) {
					parentfolder.delete();
				}
			}
		}

	}

	public class CurrencyFormatInputFilter implements InputFilter {

		Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");

		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {

			String result = dest.subSequence(0, dstart) + source.toString()
					+ dest.subSequence(dend, dest.length());

			Matcher matcher = mPattern.matcher(result);

			if (!matcher.matches())
				return dest.subSequence(dstart, dend);

			return null;
		}
	}

	public void givevaluestolatexvariables() {
		clientnamelatex = ((TextView) sitename[0].sp.getSelectedView())
				.getText().toString();
	}
	public void getelcconfigurationvalues() {

		// Get the IP File
		String MorrisonsIPPath = Environment.getExternalStorageDirectory()
				+ "/lhi" + "/Morrisons_IP.xls";
		File MorrisonsIPfile = new File(MorrisonsIPPath);
		int siteno = 0;
		int sitename = 1;
		int siteIP = 2;
		int sitesubnetmask = 3;
		int deafultgateway = 4;
		long size = MorrisonsIPfile.length();
		int sizeint = (int) size;

		Log.d("filesize", u.s(sizeint));
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(MorrisonsIPfile, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		// String[] sheets = workbook.getSheetNames();
		int worksheet = 0;
		Sheet sheet = workbook.getSheet(worksheet);

		// Fill out engineer name and sitewalk date
		// name

		try {
			int start = 0;
			ArrayList<String> itemno = u.getcol(sheet, 0, 1);
			loop: for (int start1 = 0; start1 < itemno.size(); start1++) {
				if (itemno.get(start1).equals("001")) {
					start = start1 + 1;
					break loop;
				}
			}
			Log.d("start", u.s(start));
			mcr_temp = u.getcol(sheet, siteno, start);
			for (int t = 0; t < mcr_temp.size(); t++) {
				try {
					Morrisons_Siteno[t] = mcr_temp.get(t);

				} catch (Throwable e) {
					
				}
			}
			mcr_temp = u.getcol(sheet, sitename, start);
			for (int t = 0; t < mcr_temp.size(); t++) {
				try {
					Morrisons_Sitename[t] = mcr_temp.get(t);

				} catch (Throwable e) {
					
				}
			}
			mcr_temp = u.getcol(sheet, siteIP, start);
			for (int t = 0; t < mcr_temp.size(); t++) {
				try {
					String halfip = (String) mcr_temp.get(t).subSequence(0,
							(mcr_temp.get(t).length() - 2));
					String fullip = halfip.concat("IP");
					Morrisons_IP[t] = fullip;

				} catch (Throwable e) {
					
				}
			}
			mcr_temp = u.getcol(sheet, sitesubnetmask, start);
			for (int t = 0; t < mcr_temp.size(); t++) {
				try {
					Morrisons_subnet[t] = mcr_temp.get(t);

				} catch (Throwable e) {
					
				}
			}

			mcr_temp = u.getcol(sheet, deafultgateway, start);
			for (int t = 0; t < mcr_temp.size(); t++) {
				try {
					Morrisons_defaultgateway[t] = Morrisons_IP[t].replace(
							".IP", mcr_temp.get(t));
					// Log.d("Morrisons gatewayIP",Morrisons_defaultgateway[i]);

				} catch (Throwable e) {
					
				}
			}
			
			
			maxelcconfig=0;
			int sam = 1;
			int temp = 2;
			
			String[] elcmetering = new String[maxitems];
			String[] loadnamemetering = new String[maxitems];
			String[] meteringmodbus = new String[maxitems];
			String[] elcsoftemp = new String[maxitems];
			String[] zonesoftemp = new String[maxitems];
			String[] analogoftemp = new String[maxitems];

			

			try {
				mcr_temp = db.getcolumn(db.TABLE_MCR_METERINGLIST, ELCNUMBERCOLUMN, db.KEY_MCR_METERING_TITLES);
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						elcmetering[t] = mcr_temp.get(t);
						if (maxelcconfig < u.i(mcr_temp.get(t))) {
							maxelcconfig = u.i(mcr_temp.get(t));
						}
					} catch (Throwable e) {

					}
				}

				mcr_temp =db.getcolumn(db.TABLE_MCR_METERINGLIST, LOADNAMECOLUMN, db.KEY_MCR_METERING_TITLES);
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						loadnamemetering[t] = mcr_temp.get(t);

					} catch (Throwable e) {

					}
				}
				mcr_temp = db.getcolumn(db.TABLE_MCR_METERINGLIST, MODBUSADDRESSCOLUMN, db.KEY_MCR_METERING_TITLES);
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						meteringmodbus[t] = mcr_temp.get(t);

					} catch (Throwable e) {

					}
				}

				mcr_temp =db.getcolumn(db.TABLE_MCR_TEMPSLIST, TEMPELCNUMBERCOLUMN, db.KEY_MCR_TEMPLIST_TITLES);
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						if(mcr_temp.get(t).contains(" ")){
							mcr_temp.set(t,mcr_temp.get(t).replace(" ", ""));
						}
						elcsoftemp[t] = mcr_temp.get(t);
						Log.d("maxelc","temp elc is"+elcsoftemp[t]+"/"+"max elc is"+" "+u.s(maxelcconfig));
						if (maxelcconfig < u.i(mcr_temp.get(t))) {
							maxelcconfig = u.i(mcr_temp.get(t));
						}
					} catch (Throwable e) {

					}
				}
				mcr_temp = db.getcolumn(db.TABLE_MCR_TEMPSLIST, TEMPZONENAMECOLUMN, db.KEY_MCR_TEMPLIST_TITLES);
				try{
					Double testdouble = Double.parseDouble(mcr_temp.get(0).replaceAll(" ",""));	
					testdouble =testdouble*testdouble;
				}catch(Throwable e){
				
					try{
						for(int t=0; t<6; t++){
							mcr_temp = db.getcolumn(db.TABLE_MCR_TEMPSLIST, t, db.KEY_MCR_TEMPLIST_TITLES);
							 if(u.containscaseinsensitive("on map", mcr_temp.get(0))){
								
								System.out.println("tempmap,"+t);
								mcr_temp =db.getcolumn(db.TABLE_MCR_TEMPSLIST, t, db.KEY_MCR_TEMPLIST_TITLES);
								Double testdouble = Double.parseDouble(mcr_temp.get(0).replaceAll(" ",""));	
								testdouble =testdouble*testdouble;
							}else{
								mcr_temp = db.getcolumn(db.TABLE_MCR_TEMPSLIST, TEMPZONENAMECOLUMN, db.KEY_MCR_TEMPLIST_TITLES);
							}
							
						}
						
				  }catch(Throwable p){
					  //TaskDialogs.showException(e);
				  }
				}
				
				
				
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						if(mcr_temp.get(t).contains(" ")){
							mcr_temp.set(t,mcr_temp.get(t).replace(" ", ""));
						}
						zonesoftemp[t] = mcr_temp.get(t);

					} catch (Throwable e) {

					}
				}
				mcr_temp = db.getcolumn(db.TABLE_MCR_TEMPSLIST, TEMPANALOGINPUTCOLUMN, db.KEY_MCR_TEMPLIST_TITLES);
				for (int t = 0; t < mcr_temp.size(); t++) {
					try {
						if(mcr_temp.get(t).contains(" ")){
							mcr_temp.set(t,mcr_temp.get(t).replace(" ", ""));
						}
						analogoftemp[t] = mcr_temp.get(t);

					} catch (Throwable e) {

					}
				}

			} catch (Throwable e) {

			}

			try {
				for (int t = 0; t < maxsamconfig.length; t++) {
					maxsamconfig[t] = 0;
				}

				for (int t = 0; t < elcmetering.length; t++) {

					Elcconfigurationvalues[u.i(elcmetering[t])][sam][u
							.i(meteringmodbus[t])] = loadnamemetering[t];
					Log.d("samname input for elc" + elcmetering[t], "at modbus"
							+ meteringmodbus[t] + "is " + loadnamemetering[t]);
					maxsamconfig[u.i(elcmetering[t])]++;

				}
			} catch (Throwable e) {

			}

			try {
				for (int t = 0; t < maxtempconfig.length; t++) {
					maxtempconfig[t] = 0;
				}
				for (int t = 0; t < elcsoftemp.length; t++) {
					Log.d("analog temp of zone" + zonesoftemp[t], String
							.valueOf(zonesoftemp[t].charAt(zonesoftemp[t]
									.length() - 1)));
					Elcconfigurationvalues[u.i(elcsoftemp[t])][temp][u.i(String
							.valueOf(zonesoftemp[t].charAt(zonesoftemp[t]
									.length() - 1))) - 1] = zonesoftemp[t];
					maxtempconfig[u.i(elcsoftemp[t])]++;

				}
			} catch (Throwable e) {
				
			}

		} catch (Throwable e) {

		}
	}

	public void assigingelcconfigurationvalues(String siteno) {

		int site = 0;
		int IP = 1;
		int subnet = 2;
		int gateway = 3;

		try {
			for (site = 0; site < Morrisons_Siteno.length; site++) {
				System.out.println(site);
				if (Double.parseDouble(Morrisons_Siteno[site])==Double.parseDouble(siteno)) {
					break;
				}
			}

			for (int e = 1; e <= maxelcconfig; e++) {
				Morrisons_ELC[e][IP] = Morrisons_IP[site].replace("IP",
						u.s(e + 36));
				// Log.d("Morrisons IP", Morrisons_ELC[e][IP]);
				Morrisons_ELC[e][subnet] = Morrisons_subnet[site];
				// Log.d("Morrisons subnet",Morrisons_ELC[e][subnet]);
				Morrisons_ELC[e][gateway] = Morrisons_defaultgateway[site];
				// Log.d("Morrisons gateway", Morrisons_ELC[e][gateway]);
			}

			siteexists = true;
		} catch (Throwable e) {
			showToast("Site number doesn't exists, Please enter valid 3 digit site number");
			sitenumberinput();
		}
	}

	public void createconfigfile(int elcno) {
		int IP = 1;
		int subnet = 2;
		int gateway = 3;
		int sam = 1;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					getAssets().open("config1.txt")));
			File writefile = new File(ProjectDirectory + "/"
					+ basedirectory[GENERATEDDOCUMENTSFOLDER] + "/ELCconfig/ELC"
					+ u.s(elcno) + ".XJS");

			if (writefile.exists()) {
				writefile.delete();
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					writefile, true));

			String line;

			while ((line = br.readLine()) != null) {

				if (line.contains("configIP")) {
					line = line.replace("configIP", Morrisons_ELC[elcno][IP]);
				}
				if (line.contains("configSubnet")) {
					line = line.replace("configSubnet",
							Morrisons_ELC[elcno][subnet]);
				}
				if (line.contains("configGateway")) {
					line = line.replace("configGateway",
							Morrisons_ELC[elcno][gateway]);
				}

				
				writer.write(line);
				writer.newLine();
			}

			BufferedReader br1;
			for (int r = 1; r <= maxsamconfig[elcno]; r++) {
				br1 = new BufferedReader(new InputStreamReader(getAssets()
						.open("config_sam.txt")));
				while ((line = br1.readLine()) != null) {
					if (line.contains("samname")) {
						
						line = line.replace("samname",
								Elcconfigurationvalues[elcno][sam][r]);
					}
					if (line.contains("modbusaddress")) {
						line = line.replace("modbusaddress", u.s(r));
					}

					if (!(r == (maxsamconfig[elcno]))) {
						if (line.contains("comma")) {
							line = line.replace("comma", ",");
						}
					} else {
						if (line.contains("comma")) {
							line = line.replace("comma", "");
						}
					}

					
					writer.write(line);
					writer.newLine();
				}

			}

			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					getAssets().open("config2.txt")));
			while ((line = br2.readLine()) != null) {
				if(line.contains("enabledvariable")){
					if(maxtempconfig[elcno]>0){
						line=line.replace("enabledvariable", "true");
					}else{
						line=line.replace("enabledvariable", "false");
					}
				}
				writer.write(line);
				writer.newLine();
			}
			
			if(maxtempconfig[elcno]>0){
				
				br1 = new BufferedReader(new InputStreamReader(getAssets()
						.open("config2_temp1.txt")));
				while ((line = br1.readLine()) != null) {
					writer.write(line);
					writer.newLine();
				}

			for (int r = 0; r < maxtempconfig[elcno]; r++) {
				br1 = new BufferedReader(new InputStreamReader(getAssets()
						.open("config_temp.txt")));
				while ((line = br1.readLine()) != null) {
					if (line.contains("analog")) {
						line = line.replace("analog", u.s(r));
					}
					if (!(r == (maxtempconfig[elcno] - 1))) {
						if (line.contains("comma")) {
							line = line.replace("comma", ",");
						}
					} else {
						if (line.contains("comma")) {
							line = line.replace("comma", "");
						}
					}
					
					writer.write(line);
					writer.newLine();
				}
		     }

			br1 = new BufferedReader(new InputStreamReader(getAssets()
					.open("config2_temp3.txt")));
			while ((line = br1.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
	 }
			
	
			BufferedReader br3 = new BufferedReader(new InputStreamReader(
					getAssets().open("config3.txt")));
			while ((line = br3.readLine()) != null) {
				
				writer.write(line);
				writer.newLine();
			}

			for (int r = 1; r <= maxsamconfig[elcno]; r++) {
				br1 = new BufferedReader(new InputStreamReader(getAssets()
						.open("config_samnet.txt")));
				while ((line = br1.readLine()) != null) {
					if (line.contains("modbusaddress")) {
						line = line.replace("modbusaddress", u.s(r));
					}
					if (line.contains("modbuspath")) {
						line = line.replace("modbuspath", u.s(r - 1));
					}
					if (line.contains("comma")) {
						line = line.replace("comma", ",");
					}
					
					writer.write(line);
					writer.newLine();
				}

			}
			
			if(maxtempconfig[elcno]>0){
				br1 = new BufferedReader(new InputStreamReader(getAssets()
						.open("config_temp_modbus.txt")));
				while ((line = br1.readLine()) != null) {
					if (line.contains("tempvariable")) {
						line = line.replace("tempvariable", u.s(maxsamconfig[elcno]+1));
					}
					
					writer.write(line);
					writer.newLine();
				}

			}

			BufferedReader br4 = new BufferedReader(new InputStreamReader(
					getAssets().open("config_finish.txt")));
			while ((line = br4.readLine()) != null) {
				
				writer.write(line);
				writer.newLine();
			}

			writer.flush();
			writer.close();
		} catch (Throwable e) {
			
		}

	}

	public void sitenumberinput() {

		LayoutParams sitenumberAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);

		TableLayout sitenumberlayout = new TableLayout(this);
		sitenumberlayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sitenumberlayout.setPadding(2, 2, 2, 2);

		TableRow sitenumbertablerow1 = new TableRow(this);
		sitenumbertablerow1.setLayoutParams(lpfw);

		final AutoCompleteTextView sitenumberAutoCompleteTextView = new AutoCompleteTextView(this);
		sitenumberAutoCompleteTextView.setLayoutParams(sitenumberAutoCompleteTextViewparams);
		try {
			int tempconfigno= u.i((String)foldername.subSequence(0, 3));
			sitenumberAutoCompleteTextView.setText(u.s(tempconfigno));
		}catch(Throwable e){
			if(configsiteno.length()==3){
			sitenumberAutoCompleteTextView.setText(configsiteno);
			}
		}

		
		sitenumberlayout.addView(sitenumbertablerow1);

		AlertDialog.Builder sitenumberbuilder = new AlertDialog.Builder(this);
		sitenumberbuilder.setTitle("Please Enter Site Number");
		sitenumberbuilder.setView(sitenumberlayout);
		sitenumberbuilder.setPositiveButton("OKAY",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (sitenumberAutoCompleteTextView.getText().toString().length() > 0) {
							siteexists = false;
							getelcconfigurationvalues();
							if (sitenumberAutoCompleteTextView.getText().toString()
									.length() == 3) {
								assigingelcconfigurationvalues(sitenumberAutoCompleteTextView
										.getText().toString());
								if (siteexists) {
									configfileselection();
									configsiteno = sitenumberAutoCompleteTextView.getText()
											.toString();
								}
							} else {
								showToast("Site Number should be 3 digits");
								sitenumberinput();
							}
						} else {
							showToast("You have not entered a site number yet");
							sitenumberinput();
						}

					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		final AlertDialog sitenumberalert = sitenumberbuilder.create();

		sitenumberalert.show();

	}

	public void configfileselection() {

		final ArrayList<String> elclist = new ArrayList<String>();
		for (int c = 1; c <= maxelcconfig; c++) {
			elclist.add("ELC " + u.s(c));
		}

		final CharSequence[] items = new CharSequence[elclist.size()];
		for (int i = 0; i < elclist.size(); i++) {
			items[i] = elclist.get(i);
		}

		/*
		 * LayoutParams mopdesigntextviewparams=new
		 * LayoutParams(0,LayoutParams.WRAP_CONTENT,3.5f); LayoutParams
		 * mopdesignthumbnailimageviewparams=new LayoutParams(500,677);
		 * 
		 * ScrollView elcdialoguescrollview=new ScrollView(this);
		 * 
		 * TableLayout elcdialoguetablelayout=new TableLayout(this);
		 * elcdialoguetablelayout.setLayoutParams(new LayoutParams(
		 * LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 * //mopdesignlayout.setBackgroundColor(Color.parseColor(darkblue));
		 * elcdialoguetablelayout.setPadding(2, 2, 2, 2);
		 */

		// elcdialoguetablelayout.addView(elcnumberrow1);
		// elcdialoguetablelayout.addView(elcnumberrow2);

		// elcdialoguescrollview.addView(elcdialoguetablelayout);

		AlertDialog.Builder elclistbuilder = new AlertDialog.Builder(this);
		elclistbuilder.setTitle("ELC Configuration Summary");

		// elcdialoguebuilder.setView(elcdialoguescrollview);

		elclistbuilder.setItems(items, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int menuitem) {
				// Do something with the selection

				elcconfig(menuitem);

			}
		});

		elclistbuilder.setPositiveButton("Generate All",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						for (int m = 1; m <= maxelcconfig; m++) {
							Log.d("creating config file for elc", u.s(m));
							createconfigfile(m);
						}
					}
				}).setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		final AlertDialog elclistalert = elclistbuilder.create();

		elclistalert.show();

	}

	public void elcconfig(int elcno) {
		final int IP = 1;
		final int subnet = 2;
		final int gateway = 3;
		final int elcnumber = elcno + 1;

		LayoutParams elcconfigtextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams elcconfigAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);

		TableLayout elcconfiglayout = new TableLayout(this);
		elcconfiglayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		elcconfiglayout.setPadding(2, 2, 2, 2);

		TableRow elcconfigtablerow1 = new TableRow(this);
		elcconfigtablerow1.setLayoutParams(lpfw);
		TableRow elcconfigtablerow2 = new TableRow(this);
		elcconfigtablerow2.setLayoutParams(lpfw);
		TableRow elcconfigtablerow3 = new TableRow(this);
		elcconfigtablerow3.setLayoutParams(lpfw);
		TableRow elcconfigtablerow4 = new TableRow(this);
		elcconfigtablerow4.setLayoutParams(lpfw);

		TextView sitenumbertextview = new TextView(this);
		sitenumbertextview.setLayoutParams(elcconfigtextviewparams);
		sitenumbertextview.setText("Site Number: ");

		AutoCompleteTextView sitenumberAutoCompleteTextView = new AutoCompleteTextView(this);
		sitenumberAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
		sitenumberAutoCompleteTextView.setText(configsiteno);

		TextView ipaddresstextview = new TextView(this);
		ipaddresstextview.setLayoutParams(elcconfigtextviewparams);
		ipaddresstextview.setText("IP Address: ");

		final AutoCompleteTextView ipaddressAutoCompleteTextView = new AutoCompleteTextView(this);
		ipaddressAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
		ipaddressAutoCompleteTextView.setText(Morrisons_ELC[elcnumber][IP]);

		TextView subnettextview = new TextView(this);
		subnettextview.setLayoutParams(elcconfigtextviewparams);
		subnettextview.setText("Subnet Mask: ");

		final AutoCompleteTextView subnetAutoCompleteTextView = new AutoCompleteTextView(this);
		subnetAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
		subnetAutoCompleteTextView.setText(Morrisons_ELC[elcnumber][subnet]);

		TextView gatewaytextview = new TextView(this);
		gatewaytextview.setLayoutParams(elcconfigtextviewparams);
		gatewaytextview.setText("Gateway: ");

		final AutoCompleteTextView gatewayAutoCompleteTextView = new AutoCompleteTextView(this);
		gatewayAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
		gatewayAutoCompleteTextView.setText(Morrisons_ELC[elcnumber][gateway]);

		elcconfigtablerow1.addView(sitenumbertextview);
		elcconfigtablerow1.addView(sitenumberAutoCompleteTextView);
		elcconfigtablerow2.addView(ipaddresstextview);
		elcconfigtablerow2.addView(ipaddressAutoCompleteTextView);
		elcconfigtablerow3.addView(subnettextview);
		elcconfigtablerow3.addView(subnetAutoCompleteTextView);
		elcconfigtablerow4.addView(gatewaytextview);
		elcconfigtablerow4.addView(gatewayAutoCompleteTextView);

		elcconfiglayout.addView(elcconfigtablerow1);
		elcconfiglayout.addView(elcconfigtablerow2);
		elcconfiglayout.addView(elcconfigtablerow3);
		elcconfiglayout.addView(elcconfigtablerow4);

		AlertDialog.Builder elcconfigbuilder = new AlertDialog.Builder(this);
		elcconfigbuilder.setTitle("ELC " + elcnumber + " Config");
		elcconfigbuilder.setView(elcconfiglayout);

		elcconfigbuilder
				.setPositiveButton("Config ELC" + u.s(elcnumber),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								Morrisons_ELC[elcnumber][IP] = ipaddressAutoCompleteTextView
										.getText().toString();
								Morrisons_ELC[elcnumber][subnet] = subnetAutoCompleteTextView
										.getText().toString();
								Morrisons_ELC[elcnumber][gateway] = gatewayAutoCompleteTextView
										.getText().toString();
								createconfigfile(elcnumber);
								configfileselection();
							}
						})
				.setNeutralButton("NEXT",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Morrisons_ELC[elcnumber][IP] = ipaddressAutoCompleteTextView
										.getText().toString();
								Morrisons_ELC[elcnumber][subnet] = subnetAutoCompleteTextView
										.getText().toString();
								Morrisons_ELC[elcnumber][gateway] = gatewayAutoCompleteTextView
										.getText().toString();
								showconfigsam(elcnumber);
							}
						})
				.setNegativeButton("BACK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								configfileselection();
							}
						});

		final AlertDialog elcconfigalert = elcconfigbuilder.create();

		elcconfigalert.show();

	}

	public void showconfigsam(int elcno) {
		final int elcnumber = elcno;
		int sam = 1;
		LayoutParams elcconfigtextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams elcconfigAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);

		TableLayout elcconfiglayout = new TableLayout(this);
		elcconfiglayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		elcconfiglayout.setPadding(2, 2, 2, 2);

		for (int r = 1; r <= maxsamconfig[elcnumber]; r++) {

			TableRow elcconfigtablerow1 = new TableRow(this);
			elcconfigtablerow1.setLayoutParams(lpfw);

			TextView sitenumbertextview = new TextView(this);
			sitenumbertextview.setLayoutParams(elcconfigtextviewparams);
			sitenumbertextview
					.setText(Elcconfigurationvalues[elcnumber][sam][r]);

			TextView sitenumberAutoCompleteTextView = new TextView(this);
			sitenumberAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
			sitenumberAutoCompleteTextView.setText(u.s(r));

			elcconfigtablerow1.addView(sitenumbertextview);
			elcconfigtablerow1.addView(sitenumberAutoCompleteTextView);

			elcconfiglayout.addView(elcconfigtablerow1);
		}

		AlertDialog.Builder elcconfigbuilder = new AlertDialog.Builder(this);
		elcconfigbuilder.setTitle("ELC " + elcnumber
				+ " SAM's & Modbus Address");
		elcconfigbuilder.setView(elcconfiglayout);

		elcconfigbuilder
				.setPositiveButton("Config ELC" + u.s(elcnumber),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								createconfigfile(elcnumber);
								configfileselection();
							}
						})
				.setNeutralButton("NEXT",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								showconfigtemp(elcnumber);
							}
						})
				.setNegativeButton("BACK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								elcconfig(elcnumber);
							}
						});

		final AlertDialog elcconfigalert = elcconfigbuilder.create();

		elcconfigalert.show();

	}

	public void showconfigtemp(int elcno) {
		final int elcnumber = elcno;
		int temp = 2;

		LayoutParams elcconfigtextviewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 3.5f);
		LayoutParams elcconfigAutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 7.25f);

		TableLayout elcconfiglayout = new TableLayout(this);
		elcconfiglayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		elcconfiglayout.setPadding(2, 2, 2, 2);

		for (int r = 0; r < maxtempconfig[elcnumber]; r++) {

			TableRow elcconfigtablerow1 = new TableRow(this);
			elcconfigtablerow1.setLayoutParams(lpfw);

			TextView sitenumbertextview = new TextView(this);
			sitenumbertextview.setLayoutParams(elcconfigtextviewparams);
			sitenumbertextview
					.setText(Elcconfigurationvalues[elcnumber][temp][r]);

			TextView sitenumberAutoCompleteTextView = new TextView(this);
			sitenumberAutoCompleteTextView.setLayoutParams(elcconfigAutoCompleteTextViewparams);
			sitenumberAutoCompleteTextView.setText(u.s(r));

			elcconfigtablerow1.addView(sitenumbertextview);
			elcconfigtablerow1.addView(sitenumberAutoCompleteTextView);

			elcconfiglayout.addView(elcconfigtablerow1);
		}

		AlertDialog.Builder elcconfigbuilder = new AlertDialog.Builder(this);
		elcconfigbuilder.setTitle("ELC " + elcnumber
				+ " Zone's & Analog Address");
		elcconfigbuilder.setView(elcconfiglayout);

		elcconfigbuilder
				.setPositiveButton("Config ELC" + u.s(elcnumber),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								createconfigfile(elcnumber);
								configfileselection();
							}
						})
				.setNeutralButton("ELCs Menu",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								configfileselection();
							}
						})
				.setNegativeButton("BACK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								showconfigsam(elcnumber);
							}
						});

		final AlertDialog elcconfigalert = elcconfigbuilder.create();

		elcconfigalert.show();

	}

	public static void renamepicturesandfolders(boolean hasFocus, int s, int type) {
		String oldname = null;
		String newname = null;
		switch(type){
			case ASSETSTAB:
				u.log("s value for renamepicturesandfolders "+s);
				oldname = assetname[s].string;
		
				
				assetname[s].string = assetname[s].et.getText().toString();
				
				newname = assetname[s].string;
				if (oldname == null || oldname.length() == 0) {
					oldname = newname;
				}
				break;
			case COMPONENTSTAB:
				
				oldname = componentname[s].string;
		
				
				componentname[s].string = componentname[s].et.getText().toString();
				
				newname = componentname[s].string;
				if (oldname == null || oldname.length() == 0) {
					oldname = newname;
				}
				break;
		}
		
		
		// if images exist for site
		// change them all to the new name

		
		
		countpics(type, s);

		
		
		

		if (!performingmoveoperation && hasFocus == false
				&& !(oldname.equals(newname))) {
			String[] foldernames = u.listfolders(ProjectDirectory + "/"
					+ basedirectory[ASSETSFOLDER]);
			String[] filenames = new String[0];

			String foldername = null;

			getfilenamesloop: for (int m = 0; m < foldernames.length; m++) {
				
				if (foldernames[m].contains(oldname)) {
					foldername = foldernames[m];
					filenames = printFileNames(new File(foldernames[m]));
					break getfilenamesloop;
				}

			}

			// String[] filenames=printFileNames(new
			// File(ProjectDirectory));
			// checkstring[num]
			// =buildimagenamestring(type,k,num,null);
			List<String> rowAfilenames = new ArrayList<String>();
			for (int n = 0; n < filenames.length; n++) {
				if (filenames[n].contains(oldname)) {
					rowAfilenames.add(filenames[n]);
					
					
					
					File oldfile = new File(filenames[n]);
					
					String oldfilenamewithoutpath = filenames[n].split("/")[filenames[n]
							.split("/").length - 1];
					
					String newfilenamewithoutpath = oldfilenamewithoutpath
							.replace(oldname, newname);
					
					File newfile = new File(filenames[n].replace(
							oldfilenamewithoutpath, newfilenamewithoutpath));
					oldfile.renameTo(newfile);
				}
			}

			try {
				File folder = new File(foldername);
				File newfolder = new File(foldername.replace(oldname, newname));
				folder.renameTo(newfolder);
			} catch (NullPointerException e) {
				
			}

		}
	}
	public void createcoverpage(){
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String version = pInfo.versionName;
		
		float shadowradius=1f;
		float shadowdx=1f;
		float shadowdy=1f;
		int shadowcolor=Color.parseColor(darkblue);
			
		
		Bitmap bitmap;
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		//Returns null, sizes are in the options variable
		BitmapFactory.decodeFile(coverpagefilepath, options);
		int coverwidth = options.outWidth;
		int coverheight = options.outHeight;
		
		bitmap=u.decodeSampledBitmapFromResource(coverpagefilepath,coverwidth, coverheight);
		
		Bitmap mutableBitmap =convertToMutable(bitmap);
		bitmap.recycle();
		Canvas coverpagecanvas=new Canvas(mutableBitmap);
		//coverpagecanvas.setBitmap(bitmap);
		TextPaint paint=new TextPaint();
		
		//paint.setColor(Color.parseColor(darkblue));
		paint.setColor(Color.BLACK);
		//paint.setTypeface(font);
		paint.setShadowLayer(shadowradius, shadowdx, shadowdy,
				shadowcolor);
		String SITENAME,SITEADDRESS,REPORTTYPETITLE,SURVEYORINTRO,ENGINEERNAME,ENGINEEREMAIL,ENGINEERPHONENUMBER,DATEINTRO,DATE,APPNAMEANDVERSION,REPORTVERSION;
		
		REPORTTYPETITLE="Site Survey Report / Method of Procedure (MOP)\n";
		SITENAME=sitename[1].et.getText().toString()+"\n";
		SITEADDRESS="";
		SURVEYORINTRO="Survey Conducted By:\n";
		ENGINEERNAME="          "+((TextView)engineernamesp.getSelectedView()).getText().toString()+"\n";
		ENGINEEREMAIL="          "+engineeremailtv.getText().toString()+"\n";
		ENGINEERPHONENUMBER="          "+engineerphonenumtv.getText().toString()+"\n";
		DATEINTRO="";
		DATE=swdateet.getText().toString()+"\n\n";
		//GAPFORLOGO="\n\n\n\n\n\n\n\n";
		APPNAMEANDVERSION="Conducted Using:\nLife Hack Innovations Site Audit Assistant Android App V"+version;
		REPORTVERSION="\nReport Generation Date: "+java.text.DateFormat.getDateTimeInstance().format(
				Calendar.getInstance().getTime());
		String coverpagetitle=REPORTTYPETITLE;
		String coverpagesitename=SITENAME+
							SITEADDRESS;
		String coverpagetext=
							SURVEYORINTRO+
							ENGINEERNAME+
							ENGINEEREMAIL+
							ENGINEERPHONENUMBER+
							DATEINTRO+
							DATE;
		
		String coverpagereportversion="\n\n\n\n\n\n\n\n"+REPORTVERSION;
		String coverpageversion=APPNAMEANDVERSION;
		

		
		int left = bitmap.getWidth()/10;
		int top = bitmap.getHeight()/6;
		int right = bitmap.getWidth()-left;
		int bottom = bitmap.getHeight()-top;
		Rect rect=new Rect(left, top, right, bottom);
		int y = top;
		int fontsize=55;
		paint.setTextSize(fontsize);
		StaticLayout sl;
		sl = new StaticLayout(coverpagetitle, paint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
		coverpagecanvas.translate(rect.left, y);
		sl.draw(coverpagecanvas);
		y=sl.getHeight();
		
		fontsize=45;
		paint.setTextSize(fontsize);
		sl = new StaticLayout(coverpagesitename, paint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
		coverpagecanvas.translate(0, y);
		sl.draw(coverpagecanvas);
		y=sl.getHeight();

		fontsize=30;
		paint.setTextSize(fontsize);
		sl = new StaticLayout(coverpagetext, paint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
		coverpagecanvas.translate(0, y);
		sl.draw(coverpagecanvas);
		y=sl.getHeight();
		
		Bitmap logobitmap=null;
		if(u.isbluestacks()){
			BitmapFactory.Options o = new BitmapFactory.Options();
	
			o.inSampleSize = 4;
			logobitmap=BitmapFactory.decodeFile(clientlogofilepath,o);
		}else{
			logobitmap=BitmapFactory.decodeFile(clientlogofilepath);
		}
		int imagepercentofscreenheight=18;
		double calcheight = (double) bitmap.getHeight()
				* (double) imagepercentofscreenheight
				/ (double) 100;
		double calcwidth = (double) logobitmap.getWidth()
				/ (double) logobitmap.getHeight() * (double) calcheight;
		
		if (calcwidth>bitmap.getWidth()){
			
			calcwidth = (rect.width());
			calcheight = (double) logobitmap.getHeight()
					/ (double) logobitmap.getWidth() * (double) calcwidth;
		}
		
		int height = (int) calcheight;
		int width = (int) calcwidth;
		logobitmap = Bitmap.createScaledBitmap(logobitmap, width,
				height, true);
		paint.clearShadowLayer();
		coverpagecanvas.translate(0, y);
		coverpagecanvas.drawBitmap(logobitmap, 0, 0, paint);
		
		paint.setShadowLayer(shadowradius, shadowdx, shadowdy,
				shadowcolor);
		
		y=logobitmap.getHeight();
		
		fontsize=30;
		paint.setTextSize(fontsize);
		sl = new StaticLayout(coverpagereportversion, paint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
		coverpagecanvas.translate(0, y);
		sl.draw(coverpagecanvas);
		y=sl.getHeight();
		
		fontsize=30;
		paint.setTextSize(fontsize);
		sl = new StaticLayout(coverpageversion, paint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
		coverpagecanvas.translate(0, y);
		sl.draw(coverpagecanvas);
		y=sl.getHeight();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(
					new File(ProjectDirectory + "/"
							+ basedirectory[GENERATEDDOCUMENTSFOLDER]
									+ "/Latex/Pictures/Latex_Coverpage_Final.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		}
	

		mutableBitmap.compress(
				Bitmap.CompressFormat.PNG, 
				100, 
				fos);
		try {
			
			fos.flush();
			fos.close();
			fos = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	public void read_report_Life_Hack_Innovations_Assets(){
		
		WorkbookSettings ws = new WorkbookSettings();

		Environment.getExternalStorageDirectory();
		ReportPath = ProjectDirectory + "/" + basedirectory[GENERATEDDOCUMENTSFOLDER]
				+ "/Report.xls";
		// Get the report/consumption Excel file
		File file1 = new File(ReportPath);
		
		int assetspage = 1;
		long size = file1.length();
		int sizeint = (int) size;
		int assetname = 0;
		int manufacture = 3;
		int notes = 8;
		int start = 1;


		
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(file1, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

		
		int worksheet = assetspage;
		Sheet sheet = workbook.getSheet(worksheet);

		try {
			mcr_temp = u.getcol(sheet, assetname, start);
			for (int i = 0; i < mcr_temp.size(); i++) {

				ELUTIONSAssetvalues[assetname][i] = mcr_temp.get(i);
				
			}

			mcr_temp = u.getcol(sheet, manufacture, start);
			for (int i = 0; i < mcr_temp.size(); i++) {
				if (mcr_temp.get(i) == "null") {
					mcr_temp.set(i, "");
				}
				ELUTIONSAssetvalues[manufacture][i] = mcr_temp.get(i);
			}

			mcr_temp = u.getcol(sheet, notes, start);
			for (int i = 0; i < mcr_temp.size(); i++) {

				ELUTIONSAssetvalues[notes][i] = mcr_temp.get(i);
				
			}

		} catch (IndexOutOfBoundsException e) {
			
		}
  }
	
	public void	addsitespecificrecommendations(String sitetype){
		try{
		sitetyperecommendations.removeAllViews();
		}catch(Throwable e){
			
		}
		LayoutParams boxparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1f);
		LayoutParams AutoCompleteTextViewparams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 4f);
	
 if(sitetype.equals("Grocery Store")){
		
		TableRow sitetypetable = new TableRow(this);
		sitetypetable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
        grocerycheck1 = new CheckBox(this);
        grocerycheck1.setLayoutParams(boxparams );
        grocerycheck1.setText("Night Blinds Automatic");
        
		grocerynotes1 = new AutoCompleteTextView(this);
		grocerynotes1.setLayoutParams(AutoCompleteTextViewparams);
		grocerynotes1.setHint("List what percent usage, and where automatic night blinds are");
        
		sitetypetable.addView(grocerycheck1);
		sitetypetable.addView(grocerynotes1);

		//sitetypetable.setBackgroundColor(Color.parseColor(extremelylightblue));
		sitetypetable.setBackgroundColor(Color.parseColor(thelightestblue));
		sitetypetable.setPadding(5, 5, 5, 5);

		
		TableRow sitetypetable2 = new TableRow(this);
		sitetypetable2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
       
        grocerycheck2 = new CheckBox(this);
        grocerycheck2.setLayoutParams(boxparams );
        grocerycheck2.setText("Night Blinds Manual");
        
		grocerynotes2 = new AutoCompleteTextView(this);
		grocerynotes2.setLayoutParams(AutoCompleteTextViewparams);
		grocerynotes2.setHint("Describe how often and where nightblinds are manually used");
        
        sitetypetable2.addView(grocerycheck2);
        sitetypetable2.addView(grocerynotes2);

		sitetypetable2.setBackgroundColor(Color.parseColor(extremelylightblue));
       
        sitetypetable2.setPadding(5, 5, 5, 5);
		
		
        TableRow sitetypetable3 = new TableRow(this);
        sitetypetable3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
        grocerycheck3 = new CheckBox(this);
        grocerycheck3.setLayoutParams(boxparams );
        grocerycheck3.setText("Night Blinds not available");
        
		grocerynotes3 = new AutoCompleteTextView(this);
		grocerynotes3.setLayoutParams(AutoCompleteTextViewparams);
		grocerynotes3.setHint("List where night blinds are not available");
        
        sitetypetable3.addView(grocerycheck3);
        sitetypetable3.addView(grocerynotes3);

		//sitetypetable.setBackgroundColor(Color.parseColor(extremelylightblue));
        sitetypetable3.setBackgroundColor(Color.parseColor(thelightestblue));
        sitetypetable3.setPadding(5, 5, 5, 5);
		
		
		sitetyperecommendations.addView(sitetypetable);
		sitetyperecommendations.addView(sitetypetable2);
		sitetyperecommendations.addView(sitetypetable3);
		fillsitegrocerystorevalues();
       
       }
 
 if(sitetype.equals("Office Building")){
		
		TableRow sitetypetable = new TableRow(this);
		sitetypetable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
     CheckBox sitetypecheck1 = new CheckBox(this);
     sitetypecheck1.setLayoutParams(boxparams );
     sitetypecheck1.setText("office1");
     
		AutoCompleteTextView sitetypeedit1 = new AutoCompleteTextView(this);
		sitetypeedit1.setLayoutParams(AutoCompleteTextViewparams);
     sitetypeedit1.setHint("Observations");
     
		sitetypetable.addView(sitetypecheck1);
		sitetypetable.addView(sitetypeedit1);

		//sitetypetable.setBackgroundColor(Color.parseColor(extremelylightblue));
		sitetypetable.setBackgroundColor(Color.parseColor(thelightestblue));
		sitetypetable.setPadding(5, 5, 5, 5);

		
		TableRow sitetypetable2 = new TableRow(this);
		sitetypetable2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
    
     CheckBox sitetypecheck2 = new CheckBox(this);
     sitetypecheck2.setLayoutParams(boxparams );
     sitetypecheck2.setText("office2");
     
		AutoCompleteTextView sitetypeedit2 = new AutoCompleteTextView(this);
		sitetypeedit2.setLayoutParams(AutoCompleteTextViewparams);
		sitetypeedit2.setHint("Observations2");
     
     sitetypetable2.addView(sitetypecheck2);
     sitetypetable2.addView(sitetypeedit2);

		sitetypetable2.setBackgroundColor(Color.parseColor(extremelylightblue));
    
     sitetypetable2.setPadding(5, 5, 5, 5);
		
		
     TableRow sitetypetable3 = new TableRow(this);
     sitetypetable3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
     CheckBox sitetypecheck3 = new CheckBox(this);
     sitetypecheck3.setLayoutParams(boxparams );
     sitetypecheck3.setText("office3");
     
		AutoCompleteTextView sitetypeedit3 = new AutoCompleteTextView(this);
		sitetypeedit3.setLayoutParams(AutoCompleteTextViewparams);
     sitetypeedit3.setHint("Observations");
     
     sitetypetable3.addView(sitetypecheck3);
     sitetypetable3.addView(sitetypeedit3);

		//sitetypetable.setBackgroundColor(Color.parseColor(extremelylightblue));
     sitetypetable3.setBackgroundColor(Color.parseColor(thelightestblue));
     sitetypetable3.setPadding(5, 5, 5, 5);
		
		
		sitetyperecommendations.addView(sitetypetable);
		sitetyperecommendations.addView(sitetypetable2);
		sitetyperecommendations.addView(sitetypetable3);
    
    
    }
 
	}
	public void setgrocerystorelisteners(){
		grocerycheck1
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck1",
							"true");
					}
				else{
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck1",
							"false");
					}
			}});
		
		grocerycheck2
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck2",
							"true");
					}
				else{
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck2",
							"false");
					}
			}});
		
		grocerycheck3
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck3",
							"true");
					}
				else{
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck3",
							"false");
					}
			}});

		grocerynotes1
		.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (!hasfocus) {
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes1",
							grocerynotes1.getText().toString());
				    }
			     }
		  });
		
		grocerynotes2
		.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (!hasfocus) {
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes2",
							grocerynotes2.getText().toString());
				    }
			     }
		  });
		
		grocerynotes3
		.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (!hasfocus) {
					db.addorupdate(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes3",
							grocerynotes3.getText().toString());
				    }
			     }
		  });
	}

 public void fillsitegrocerystorevalues() {
		try{
		grocerynotes1.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes1",0));
		}catch(Throwable e){
			
		}
		try{
		grocerynotes2.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes2",0));
		}catch(Throwable e){
			
		}
		try{
		grocerynotes3.setText(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerynotes3",0));
		}catch(Throwable e){
			
		}
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck1",0).equals("true")){
			grocerycheck1.setChecked(true);
			}
		}catch(Throwable e){
			
		}
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck2",0).equals("true")){
			grocerycheck2.setChecked(true);
			}
		}catch(Throwable e){
			
		}
		try{
		if(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITE_RECOMMENDATIONS, "grocerycheck3",0).equals("true")){
			grocerycheck3.setChecked(true);
			}
		}catch(Throwable e){
			
		}
		
		grocerycheck1.requestFocus();
		grocerycheck2.requestFocus();
		grocerycheck3.requestFocus();
		grocerynotes1.requestFocus();
		grocerynotes2.requestFocus();
		grocerynotes3.requestFocus();
 
 
 }
 public LayoutParams columnlayoutparams(int num){
	 float wdth = 0;
	 switch (num) {
		 case ITEMNUMBERCOLUMN:
			wdth=.5f;
		 	break;
		 case ELCNUMBERCOLUMN:
			 wdth=1;
			break;
		 case LOADNAMECOLUMN:
			 wdth=2f;
			break;
		 case PANELLOCATIONCOLUMN:
			 wdth=2f;
			break;
		 case VOLTAGECOLUMN:
			 wdth=.75f;
			break;
		 case PHCOLUMN:
			 wdth=.5f;
			 break;
		 case TRANSCONFIGCOLUMN:
			 wdth=.75f;
			break;
		 case BREAKERSIZECOLUMN:
			 wdth=1.5f;
			break;
		 case CTSIZEPHYISICALCOLUMN:
			 wdth=2f;
			break;
		 case COMMENTSCOLUMN:
			 wdth=4f;
			break;
		 case LOADTYPECOLUMN:
			 wdth=2f;
			break;
		 case LOADSONEACHBREAKERCOLUMN:
			 wdth=4f;
			break;
	 }
	 
	 
			
	LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, wdth);
	return lp;
	 
 }
 public void repopulatemcrlocations(){
	 
	 int sheetnum = METERINGLIST;
	 Spinner[][] sp=mcrspinnerentries[sheetnum];
	 int num =PANELLOCATIONCOLUMN;
 	 for (int itemnum=0; itemnum<=m[sheetnum]; itemnum++){
	 try {
			
			u.log(locationslist);
			u.log("Selection number="+getIndexofSpinner(
					sp[itemnum][num], db.getvaluemulticolumn(
							DatabaseHandler.TABLE_MCR_METERINGLIST, itemnum, num,
							DatabaseHandler.KEY_MCR_METERING_TITLES)));
			sp[itemnum][num].setSelection(getIndexofSpinner(
					sp[itemnum][num], db.getvaluemulticolumn(
							DatabaseHandler.TABLE_MCR_METERINGLIST, itemnum, num,
							DatabaseHandler.KEY_MCR_METERING_TITLES)));
			u.log("Which has a value of="+db.getvaluemulticolumn(
							DatabaseHandler.TABLE_MCR_METERINGLIST, itemnum, num,
							DatabaseHandler.KEY_MCR_METERING_TITLES));

		} catch (Throwable e) {
			
		}
	 }
 }
 
public void getcompanyvalues(){
	u.log("getcompanyvalues started");
	//morrisons
	
	LoadM395values();
	for(String store:M395storesbyCONVENTION){
		if (foldername.contains(store)){
			ISMORRISONS=true;
			ISGROCERYSTRORE=true;
			storenumber=foldername.split(" ")[0];
			setstoretypegrocery();
			u.log("store found!");
			break;
		}
	
	}
	
	//vodaphone
}
public void showselectmopchaptersdialog(){
	final CharSequence[] items = new CharSequence[checklist.size()];
	for (int i = 0; i < checklist.size(); i++) {
		items[i] = checklist.get(i);
	}
	AlertDialog.Builder mopbuilder = new AlertDialog.Builder(this);
	mopbuilder.setTitle("MOP Generator - Select Items for MOP");
	mopbuilder.setMultiChoiceItems(items, mopreporttypestates,
			new DialogInterface.OnMultiChoiceClickListener() {
				public void onClick(DialogInterface dialogInterface,
						int item, boolean state) {

				}
			});
	mopbuilder.setPositiveButton("Okay",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					SparseBooleanArray CheCked = ((AlertDialog) dialog)
							.getListView().getCheckedItemPositions();

					if (CheCked.get(0)) {
						// Toast.makeText(MainActivity.this,
						// "Executive Summary", Toast.LENGTH_SHORT).show();
						execsum = true;
					}
					if (CheCked.get(1)) {
						// makeText(MainActivity.this,
						// "Facilty Information",
						// Toast.LENGTH_SHORT).show();
						facility = true;
					}
					if (CheCked.get(2)) {
						// Toast.makeText(MainActivity.this,
						// "Facility & Energy Consumption Statistics Table",
						// Toast.LENGTH_SHORT).show();
						facilityasset = true;
					}
					if (CheCked.get(3)) {
						// Toast.makeText(MainActivity.this,
						// "Facility & Energy Consumption Statistics Table",
						// Toast.LENGTH_SHORT).show();
						facilityenergy = true;
					}
					if (CheCked.get(4)) {
						// Toast.makeText(MainActivity.this,
						// "Optimization Strategy Development",
						// Toast.LENGTH_SHORT).show();
						BOM = true;
					}
					if (CheCked.get(5)) {
						// Toast.makeText(MainActivity.this,
						// "Optimization Strategy Development",
						// Toast.LENGTH_SHORT).show();
						optimization = true;
					}
					if (CheCked.get(6)) {
						// Toast.makeText(MainActivity.this,
						// "Optimization Strategy Development",
						// Toast.LENGTH_SHORT).show();
						sitespecificrecommendations = true;
					}
					if (CheCked.get(7)) {
						// Toast.makeText(MainActivity.this,
						// "Metering and Monitoring Riser Architechture",
						// Toast.LENGTH_SHORT).show();
						riser = true;
					}
					if (CheCked.get(8)) {
						// Toast.makeText(MainActivity.this,
						// "Energy Metering List",
						// Toast.LENGTH_SHORT).show();
						metering = true;
					}
					if (CheCked.get(9)) {
						// Toast.makeText(MainActivity.this,
						// "Building Load Classification",
						// Toast.LENGTH_SHORT).show();
						load = true;
					}
					if (CheCked.get(10)) {
						// Toast.makeText(MainActivity.this,
						// "Temperature Sensor List",
						// Toast.LENGTH_SHORT).show();
						tempsensorlist = true;
					}
					if (CheCked.get(11)) {
						// Toast.makeText(MainActivity.this,
						// "Temperature Sensor List",
						// Toast.LENGTH_SHORT).show();
						SAMreferencetable = true;
					}
					if (CheCked.get(12)) {
						// Toast.makeText(MainActivity.this,
						// "Approximate Location Layout for Installation",
						// Toast.LENGTH_SHORT).show();
						try {
							outputfloorplanstrings=new ArrayList<String>();
							for(File file:new File(outputfloorplandirectory).listFiles()){
								if (u.issupportedimage(file)){
									outputfloorplanstrings.add(file.getAbsolutePath());
								}
							}
					
							if (outputfloorplanstrings.get(0) != null) {
								floorplan = true;
								Log.d("floorplan names",
										outputfloorplanstrings.get(0));
							}
						} catch (Throwable e) {

						}

					}
					
					if (CheCked.get(13)) {
						lhiassets=true;
					}
					
					if (CheCked.get(14)) {
						sitesassets=true;
					}
					if (CheCked.get(15)) {
						sitenotes=true;
					}
					maketexfile();
					createcoverpage();
					
					try{
						read_MCR();
						

					}catch(Throwable e){
						
					}
					
						try {
							read_reportexcel(db);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					
					
					
					try {
						PackageManager pm = getPackageManager();
						Intent intent = pm
								.getLaunchIntentForPackage("lah.texportal");
						startActivity(intent);
					} catch (NullPointerException e) {
						try {
							PackageManager pm = getPackageManager();
							Intent intent = pm
									.getLaunchIntentForPackage("lah.texportal.donate");
							startActivity(intent);
						} catch (NullPointerException e1) {
							showneedmoresoftwaredialog("TexPortal",
									"lah.texportal.donate");
						}
					}
				}
			});
	mopbuilder.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
	mopbuilder.create().show();
}
public void setstoretypegrocery(){
	
	
	
	commonlocations = loadlistfromxls(masterlistsheet3,COMMONLOCATIONS,  false, false);
	commontemplocations = loadlistfromxls(masterlistsheet3,COMMONTEMPERATURESENSORLOCATIONS,  false, false);
	correspondingtypes = loadlistfromxls(masterlistsheet3,CORRESPONDINGZONETYPE,  true, true);
	commontemplocationsammendments = loadlistfromxls(masterlistsheet1,PREPOSITIONS,  false, false);
	 List<String> commontemplocationshelper=new ArrayList<String>();
		int j=0;
//addprepositions
		for(j=0; j<commontemplocations.size(); j++){
			try{
				commontemplocationshelper.add(commontemplocations.get(j));
			}catch(Throwable e){
				
			}
		}
		
		for(int iteration=1; iteration<commontemplocationsammendments.size(); iteration++){
			for(j=0; j<commontemplocations.size(); j++){
				try{
				//	u.log("j="+j);
				//	u.log(iteration);
				//	int num=j+commontemplocations.size()*(iteration);
				//	u.log(commontemplocationsammendments.get(iteration-1));

					
					commontemplocationshelper.add(commontemplocationsammendments.get(iteration-1)+" "+commontemplocations.get(j));
					
				//	u.log("num="+commontemplocationshelper.get(num));
				}catch(Throwable e){
					
				}
			}
		
		}
		commontemplocations=commontemplocationshelper;
	

}
private void setstoretypedatacenter() {

	
	commonlocations = loadlistfromxls(masterlistsheet4,COMMONLOCATIONS,  false, false);
	commontemplocations = loadlistfromxls(masterlistsheet4,COMMONTEMPERATURESENSORLOCATIONS,  false, false);
	correspondingtypes = loadlistfromxls(masterlistsheet4,CORRESPONDINGZONETYPE,  true, true);
	commontemplocationsammendments = loadlistfromxls(masterlistsheet1,PREPOSITIONS,  false, false);

	 List<String> commontemplocationshelper=new ArrayList<String>();
		int j=0;
//addprepositions
		for(j=0; j<commontemplocations.size(); j++){
			try{
				commontemplocationshelper.add(commontemplocations.get(j));
			}catch(Throwable e){
				
			}
		}
		
		for(int iteration=1; iteration<commontemplocationsammendments.size(); iteration++){
			for(j=0; j<commontemplocations.size(); j++){
				try{
				//	u.log("j="+j);
				//	u.log(iteration);
				//	int num=j+commontemplocations.size()*(iteration);
				//	u.log(commontemplocationsammendments.get(iteration-1));

					
					commontemplocationshelper.add(commontemplocationsammendments.get(iteration-1)+" "+commontemplocations.get(j));
					
				//	u.log("num="+commontemplocationshelper.get(num));
				}catch(Throwable e){
					
				}
			}
		
		}
		commontemplocations=commontemplocationshelper;
	

			
}
public void savelocal(DialogInterface dialog, boolean andexit){
	new SaveAllTask(progressDialog).execute();
	try {
		sethourglassicons();
	} catch (Throwable e) {

	}
	foldersync();
	u.databaseexport(internaldbpath,externaldbpath, ctx);
	clearingvalues();
	dialog.dismiss();
	if(andexit){
		finish();
	}
}
public void savelocalandcloud(DialogInterface dialog, boolean andexit){
	saveincloud=true;
	new SaveAllTask(progressDialog).execute();
	try {
		sethourglassicons();
	} catch (Throwable e) {

	}
	
	foldersync();
	clearingvalues();
	dialog.dismiss();
	if(andexit){
		finish();
	}
}
public void LoadM395values(){

    
		String[] titles=masterdb.gettitles(masterdb.TABLE_M395);
       
 	  
 	  	
 	  	M395storenumbers=masterdb.getcolumn(masterdb.TABLE_M395, M395storenumbercolumnoncsv, titles);
       M395storenames=masterdb.getcolumn(masterdb.TABLE_M395, M395storenamecolumnoncsv, titles);
       
       M395storesbyCONVENTION=new String[M395storenames.size()];
       for (int h=0; h<M395storenames.size(); h++){
       	 M395storesbyCONVENTION[h]=M395storenumbers.get(h)+" "+M395storenames.get(h);
       }
       
	}
	public void addM395floorplan() throws IOException{
		Cursor c=masterdb.getReadableDatabase().query(false, DatabaseHandler.TABLE_M395, new String[]{"common_store_name"},"store_number="+storenumber,null,null,null,null,null);
		c.moveToFirst();
		
		String Sugarpath = mainsugarsyncdirectory+"/M395 Store Layouts PNG/"+c.getString(0);
		copypictureintoproject(typeforgetpicture, Sugarpath);
		refreshfloorplans();
	}
	public static void deleteimage(File file, ImageView imageview){
		File parentfile = file.getParentFile();
		file.delete();
		try {
			String associatedtextfile = file
					.getAbsolutePath().split(".")[file
					.getAbsolutePath().split(".").length - 1];
			
			File textfile = new File(associatedtextfile);
			textfile.delete();
		} catch (Throwable e) {
			
		}
		if (parentfile.list().length == 0) {
			parentfile.delete();
		}
		ViewGroup parentrow=(ViewGroup) imageview.getParent();
		parentrow.removeView(imageview);
		deletepicturedialog.dismiss();
	}
	public boolean isspinnercolumn(int num){
		boolean isspinner=false;
		if (num == VOLTAGECOLUMN || num == TRANSCONFIGCOLUMN || num == CTSIZEAMPSCOLUMN
				|| num == PHCOLUMN
				|| num == MODBUSADDRESSCOLUMN){
			isspinner=true;
		}
		return isspinner;
	}
	public boolean checkdbformcrvalue(int sheetnum, int j){
		boolean hasvalue=false;
		
		switch(sheetnum){
		case METERINGLIST:
			try{
				String value=db.getvaluemulticolumn(DatabaseHandler.TABLE_MCR_METERINGLIST, j, 1,
						DatabaseHandler.KEY_MCR_METERING_TITLES);
				Log.d("Write db",value);
				u.log(value);
				hasvalue=true;
			}catch(Throwable e){
				hasvalue=false;
				u.log("not in merting db");
			}
			break;
	
		case TEMPSENSORCOMMISSIONING:
			try{
				db.getvaluemulticolumn(DatabaseHandler.TABLE_MCR_TEMPSLIST, j, 0,
						DatabaseHandler.KEY_MCR_TEMPLIST_TITLES);
				hasvalue=true;
			}catch(Throwable e){
				hasvalue=false;
			}
			break;
		}
		
		
		return hasvalue;
	}
	public boolean checkexcelformcrvalue(int sheetnum,int j){
		boolean hasvalue=false;
		String titlerowstart = "A5";
		String titlerowstop = null;
		int startreferencecolumn=0;
		switch(sheetnum){
		case METERINGLIST:
			titlerowstop = "O5";
			startreferencecolumn=ELCNUMBERCOLUMN;
			break;
	
		case TEMPSENSORCOMMISSIONING:
			titlerowstop = "J5";
			startreferencecolumn=TEMPFLOORNUMBERCOLUMN;
			break;
		}
		
		u.log(titlerowstart);
		u.log(u.cellx(titlerowstart));
		u.log(u.celly(titlerowstart)+1+j);
		u.log(u.cellid(u.cellx(titlerowstop),u.celly(titlerowstop) + 1+j));
		// check first row
		String nextrowstart = u.cellid(u.cellx(titlerowstart),
				u.celly(titlerowstart) + 1+j);
		String nextrowstop = u.cellid(u.cellx(titlerowstop),
				u.celly(titlerowstop) + 1+j);
		u.log(nextrowstart+" "+nextrowstop);
		u.log("j"+j);
		u.log("sheetnum"+sheetnum);
		heldexcelrow = u.getrow(mcrsheet[sheetnum], nextrowstart, nextrowstop);
		for(int x=0; x<heldexcelrow.length; x++){
			u.log("Row read"+heldexcelrow[x]);
		}
	
		//skip ITEMNUMBERCOLUMN BECAUSE EXCEL HAS IT FILLED OUT ALREADY
		
		for (int column=startreferencecolumn; column<heldexcelrow.length; column++){
			if (heldexcelrow[column].trim().length()>0){
				hasvalue=true;
				break;
			}
		}
		return hasvalue;
	}
	public void openmcrsheet(){
		u.log("MCRlocation"+MCRlocation);
		File file = new File(MCRlocation);
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		try {

			mcrworkbook = Workbook.getWorkbook(file, ws);
			for (int num = METERINGLIST; num < mcrworkbook.getNumberOfSheets(); num++) {
				mcrsheet[num] = mcrworkbook.getSheet(num);
			}
			
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void settextfromsource(int sheetnum, View[][] v, int itemnum, int num, int SOURCE){
		String Table = null;
		String[] Titles = null;
		switch(sheetnum){
		
		case METERINGLIST:
			Table=DatabaseHandler.TABLE_MCR_METERINGLIST;
			Titles=DatabaseHandler.KEY_MCR_METERING_TITLES;
			break;
		
		case TEMPSENSORCOMMISSIONING:
			Table=DatabaseHandler.TABLE_MCR_TEMPSLIST;
			Titles=DatabaseHandler.KEY_MCR_TEMPLIST_TITLES;
			break;
		}
		
		try{
			if(SOURCE==MCRDBVALUES){
					String text=db.getvaluemulticolumn(Table, itemnum, num,Titles);
					if(!(text.trim().length()==0)){
						mcrstringarray[sheetnum][itemnum][num]=text;
						u.settexttoview(text, v[itemnum][num]);
					}
					
			}else if(SOURCE==MCREXCELVALUES){
					String text=heldexcelrow[num];
					if(!(text.trim().length()==0)){	
						mcrstringarray[sheetnum][itemnum][num]=text;
						u.settexttoview(text, v[itemnum][num]);
					}
					u.log(MAESTROCOMMISSIONINGTABLOADED);
					if (MAESTROCOMMISSIONINGTABLOADED) {
						db.addorupdatemulticolumn(
								Table,
								itemnum,
								num,
								mcrstringarray[sheetnum][itemnum][num],
								Titles);
					}
			}
		}catch(Throwable e){
			
		}
	}
	public void checkforfloorplans(){
		String Sugarpath = mainsugarsyncdirectory+"/M395 Store Layouts PNG";
		File pngfolder=new File(Sugarpath);
		File[] pngfiles=pngfolder.listFiles();
		File pngfile = null;
		boolean blankfloorplanfoundoncloud=false;
		File destinationfolder=new File(inputfloorplandirectory+"/");
		destinationfolder.mkdirs();
		u.log("inputfloorplandirectory"+inputfloorplandirectory);
		for(int h=0; h<destinationfolder.list().length; h++){
			u.log("floorplans already here="+destinationfolder.list());
		}
		
		for(int k=0; k<pngfiles.length; k++){
			u.log("Comparing file found "+pngfiles[k].getName()+" with the one we're looking for"+storenumber);

			if(pngfiles[k].getName().contains(storenumber)){
				pngfile=pngfiles[k];
				u.log(pngfile.getName());
				blankfloorplanfoundoncloud=true;
				u.log("blankfloorplanfoundoncloud"+blankfloorplanfoundoncloud);
				break;
			}
		}
		if(blankfloorplanfoundoncloud){
			destinationfolder=new File(inputfloorplandirectory+"/");
			destinationfolder.mkdirs();
			
			File destination=new File(inputfloorplandirectory+"/"+pngfile.getName());
			u.log("destination folder path"+destination.getAbsolutePath());
			
			try {
				u.copyFile(pngfile, destination);
				u.log("floorplancopied apparently");
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void comparereportwithfilestructure(int FOLDER){
		u.log("trying comparereportwithfilestructure");
		u.log("FOLDER="+FOLDER);
		try{
		int countintabs = 0;
		ArrayList<String> namesintabs=new ArrayList<String>();
		SiteAuditItem[] item = null;
		String table = null;
		String key = null;
		String[] Key = null;
		switch(FOLDER){
		case(COMPONENTSFOLDER):
			table=DatabaseHandler.TABLE_COMPONENTINFO;
			key="componentname";
			item=componentname;
			Key=Tabs1.db.KEY_COMPONENT_ATTRIBUTES;
			countintabs=db.countrows(table);
			
		break;
		case(ASSETSFOLDER):
			table=DatabaseHandler.TABLE_ASSETINFO;
			key="assetname";
			item=assetname;
			Key=Tabs1.db.KEY_ASSET_ATTRIBUTES;
			countintabs=db.countrows(table);
			
		break;
		case(SITEINFOFOLDER):
			table=DatabaseHandler.TABLE_SITEINFO;	
			key="sitename";
			item=sitename;
			Key=Tabs1.db.KEY_SITE_ATTRIBUTES;
			countintabs=db.countrows(table);
		break;
		}
		u.log(countintabs+" "+table+" "+key+" "+item+" ");
		System.out.println(Key);
		
		
		for(int y=0; y<countintabs; y++){
			try{
				int column=db.getcolumnnumberbytitle(table,key);
				u.log("adding name "+db.getvaluemulticolumn(table, y, column, Key)+"to namesintabs");
				namesintabs.add(db.getvaluemulticolumn(table, y, column, Key));
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		
		//list sites folders
		String[] foldernames;
		
		foldernames = (u.listfolders(ProjectDirectory + "/"
				+ basedirectory[FOLDER]));
		
		String basename;
		u.log("foldernames.length"+foldernames.length);
		for(int g=0; g<foldernames.length; g++){
			
			u.log("foldernames[g]"+foldernames[g]);
			basename=FilenameUtils.getBaseName(foldernames[g]);
			u.log("basename "+basename);
			u.log("nameintabs "+namesintabs);
			if(!namesintabs.contains(basename)){
				u.log("Tabs missing item"+basename);
				switch(FOLDER){
				case(COMPONENTSFOLDER):
					u.log("makenewcomponent();");
					makenewcomponent();
					u.log("Just made new component, and now setting the name of the new component to the name of the folder found");
					u.log("itemnumber "+(c-1)+" is getting name"+basename);
					item[c-1].et.setText(basename);
					
					try{
						
						String value=basename;
						int row=c-1;
						int column=db.getcolumnnumberbytitle(table,key);
						db.addorupdatemulticolumn(table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
					
					
				break;
				case(ASSETSFOLDER):
					u.log("makenewasset();");
					makenewasset();
					u.log("Just made new asset, and now setting the name of the new asset to the name of the folder found");
					u.log("itemnumber "+(i-1)+" is getting name"+basename);
					item[i-1].et.setText(basename);
					
					try{
						
						String value=basename;
						int row=i-1;
						int column=db.getcolumnnumberbytitle(table,key);
						db.addorupdatemulticolumn(table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
					
				
				break;
				case(SITEINFOFOLDER):
					u.log("addlocation(ZONE);");
					addlocation(ZONE);
					item[s-1].et.setText(basename);
					
					
					try{
						
						String value=basename;
						int row=s-1;
						int column=db.getcolumnnumberbytitle(table,key);
						db.addorupdatemulticolumn(table, row, column , value , Key);
					}catch(Throwable e){
						e.printStackTrace();
					}
					
					
				break;
				}
				
				
			}
			
		}
		}catch(Throwable e){
			u.log("couldn't run compare");
			e.printStackTrace();
		}
		
		
		
		
	}
	public void refreshpictures(){

		if(imagecapturenotprocessed()){
			processpendingimagecapture();
		}
		removeallpictures();
		
		runOnUiThread(new Runnable() {
			public void run() {
		
				comparereportwithfilestructure(SITEINFOFOLDER);
				comparereportwithfilestructure(COMPONENTSFOLDER);
				comparereportwithfilestructure(ASSETSFOLDER);
			}
		});
		addallpictures();
	}
	public void removeallpictures(){
	
		for(int k=1; k<db.countrows(db.TABLE_SITEINFO); k++){
			try{
				str5[k].removeAllViews();
			}catch(Throwable e){
				e.printStackTrace();
			}
		
		}
		for(int k=0; k<db.countrows(db.TABLE_COMPONENTINFO); k++){
			try{
				ctr10[k].removeAllViews();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		for(int k=0; k<db.countrows(db.TABLE_ASSETINFO); k++){
			try{
				tr10[k].removeAllViews();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
	}
	public void removepicturesforonerow(int tab, int row){
		switch(tab){
		case SITETAB:
			str5[row].removeAllViews();
		break;
		case COMPONENTSTAB:
			ctr10[row].removeAllViews();
		break;
		case ASSETSTAB:
			tr10[row].removeAllViews();
		break;
		}
	}
public void readexistingconsumptiontable(){
	    
		File EnergyData=new File(Environment.getExternalStorageDirectory() + "/"
				+ "lhi" + "/Morrisons_IP.xls");
		long size = EnergyData.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(EnergyData, ws);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int worksheet = 1;
		int start=2;
		int sitenum=0;
		int monthstart=4;
		int monthend=16;
		
		boolean sitematched=false;
		String[][] excelconsumptiondtata= new String[1000][1000];
		ArrayList<String> Sitenos=new ArrayList<String>();
		
		
		Sheet sheet = workbook.getSheet(worksheet);
	
		if(EnergyData.exists()){
			System.out.println("Morrisons energy Data Exists");
		}
		
		try {
			
			Sitenos = u.getcol(sheet, sitenum, start);
			System.out.println("No.of Sitenos,"+Sitenos.size());
			
			int p=0;
			for(monthstart=4;monthstart<monthend;monthstart++){
				ArrayList<String> Temp=u.getcol(sheet, monthstart, start);
				for(int k=0;k<Temp.size();k++){
					excelconsumptiondtata[p][k]=Temp.get(k);
					//System.out.println("consumptionread at,"+p+" ,"+k+", is,"+Temp.get(k));
				}
				p++;
			}
			
			
		}catch(Throwable e){
				
			}
		String sitenumber=foldername.substring(0, 3);
		int siterow=0;
		for(siterow=0;siterow<Sitenos.size();siterow++){
			try{
				Double tempd=Double.parseDouble(Sitenos.get(siterow));
				
				if(tempd==Double.parseDouble(sitenumber)){
					sitematched=true;
					break;
				}
			}catch(Throwable e){
				
			}
		}
	   if(sitematched){
		System.out.println("consumption site matched,"+siterow);
       String[] givenmonths={"June-12","July-12","August-12","September-12","October-12","November-12","December-12",
                             "January-13","January-13","Febuary-13","April-13","May-13"};
		int wb = REPORT;
		int ws = CONSUMPTION;

		// checkfirstrow
		String cellstart = "A2";
		String cellstop = "H2";

		int i = 0;

		for (int num = 0; num <= u.cellx(cellstop); num++) {

			String value = "";
			if (num == 0) {
				try {
					value = givenmonths[i];
					Log.d("given consumption date retrieved" + u.s(i), value);
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptiondate" + u.s(i),
							givenmonths[i]);
				} catch (Throwable e) {

				}
			}
			if (num == 1) {
				try {
					value = excelconsumptiondtata[i][siterow];
					Log.d("given consumption kwh retrieved" + u.s(i), value);
					u.settexttoview(value,
							ViewHolder[wb][ws][u.cellx(cellstart) + num][u
									.celly(cellstart)]);
					db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
							"consumptionkwh" + u.s(i),
							excelconsumptiondtata[i][siterow]);
					consdoub[0] = u.d(value);
					Log.d("consdoub[0] is being set to", value);
				} catch (Throwable e) {

				}
			}
			
		}
		
		int h = 1;

		loop: while (h != 0) {

			cellstart = u.cellid(u.cellx(cellstart), u.celly(cellstart) + 1);
			cellstop = u.cellid(u.cellx(cellstop), u.celly(cellstop) + 1);
			i++;

			// ((Spinner)ViewHolder[wb][ws][u.cellx(cellstart)+5][u.celly(cellstart)]);
			h = 0;
			for (int num = 0; num <= u.cellx(cellstop); num++) {
				Log.d("Attempting consumption iteration" + i, "true");

				String value = "";

				if (num == 0) {
					try {
						value = givenmonths[i];
						Log.d("consumption date retrieved" + u.s(i), value);
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptiondate" + u.s(i),
								givenmonths[i]);
						h++;
					} catch (Throwable e) {

					}

				}
				if (num == 1) {
					try {
						value = excelconsumptiondtata[i][siterow];
						Log.d("consumption kwh retrieved=", value);
						u.settexttoview(value,
								ViewHolder[wb][ws][u.cellx(cellstart) + num][u
										.celly(cellstart)]);
						db.addorupdate(DatabaseHandler.TABLE_CONSUMPTION,
								"consumptionkwh" + u.s(i),
								excelconsumptiondtata[i][siterow]);
						consdoub[u.celly(cellstart) - 1] = u.d(value);
						h++;
					} catch (Throwable e) {

					}

				}
				
			}

		}
	   }
		
	}
	public String getcompanylogopath(){
		string = ProjectDirectory + "/" + basedirectory[SITEINFOFOLDER];
		File basedirectory = new File(string);
		
		String name = null;
		for (File parentfolder : basedirectory.listFiles()) {
			u.log(FilenameUtils.getBaseName(parentfolder.getName()));
			String filename=FilenameUtils.getBaseName(parentfolder.getName());
			u.log("filename"+filename);
			u.log(sitename[0].sp.getSelectedItem().toString());
			if (filename.contains(sitename[0].sp.getSelectedItem().toString())) {
				
				name = (parentfolder.listFiles()[0]).getAbsolutePath();
				
			}
		}
		return name;
	}
	
	 OnCancelListener cancelListener=new OnCancelListener(){
		    @Override
		    public void onCancel(DialogInterface arg0){
		    	try{
		    		u.log("trying to stop loadtask");
		    		loadtask.cancel(true);
		    	}catch(Throwable e){
		    		u.log("trying to stop loadtask");
		    		e.printStackTrace();
		    	}
		        finish();
		    }
		};
		public void createnotestab(){
			EditText notes=(EditText)findViewById(R.id.sitenotes);
			db.showfulldblog(db.TABLE_NOTES);
			try{
				notes.setText(db.getValue(db.TABLE_NOTES, "notes"));
			}catch(Throwable e){
				
			}
			notes.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		        	if(NOTESTABLOADED){
		        		db.addorupdate(db.TABLE_NOTES, "notes", s.toString());
		        	}
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){
		        	
		        }
		    }); 
		}
		
		public void executegetpicfromcameraresult(int typeselected, final String numberselected, float picturestarttime, float picturestoptime){
			if ((typeselected==SITETAB)|| (typeselected==COMPONENTSTAB)||(typeselected==ASSETSTAB)) {
				u.log("");
				processpendingimagecapture();
				
				removepicturesforonerow(typeselected, u.i(numberselected));
				addpicturesforonerow(typeselected, u.i(numberselected));
				
				try {
					switch(typeselected){
					case SITETAB:
					tab0scrollview.post(new Runnable() {
						public void run() {
							tab0scrollview.scrollTo(SCROLPOSX,SCROLPOSY);
						}
					});
					break;
					case COMPONENTSTAB:
						tab1scrollview.post(new Runnable() {
							public void run() {
								tab1scrollview.scrollTo(SCROLPOSX,SCROLPOSY);							}
						});
					break;
					case ASSETSTAB:
						tab2scrollview.post(new Runnable() {
							public void run() {
								u.log("y focus value, "+ SCROLPOSY);
								tab2scrollview.scrollTo(SCROLPOSX,SCROLPOSY);
							}
						});
					break;
				}
					
						
				} catch (Throwable e) {

				}
			} else if((typeselected==FLOORPLANTAB)) {
				try {
					refreshfloorplans();
				} catch (Throwable e) {

				}
				try {
					sitefpimageview[0].requestFocus();
				} catch (Throwable e) {

				}
			}
			this.picturestarttime=-1;
			this.picturestoptime=-1;
			set("picturestarttime", u.sl(this.picturestarttime));
			set("picturestoptime", u.sl(this.picturestoptime));
		}
		public boolean imagecapturenotprocessed(){
			boolean captureattempted=false;
			boolean processed=false;
			
			if(!(picturestarttime==-1)&&!(picturestarttime==0)){
				captureattempted=true;
			}
			if(captureattempted){
				if(!(picturestoptime==-1)&&!(picturestoptime==0)){
					processed=true;
				}else{
					picturestoptime=System.currentTimeMillis();
					processed=false;
				}
			}
			return processed;
		}
		
		public static void addvaluestobom() {
		
	//Latex Metering BOM Table
			int newmaxelc=0;
			int newtotalsam=0;
			int newtotalCT=0;
			int newtotaltemp=0;
			List<String> CTtypeListwduplicates = new ArrayList<String>();
			List<String> PH = new ArrayList<String>();
			int tempelc = 2;
			

			for (int lineno = 0; lineno < m[METERINGLIST]; lineno++) {
				
				for (int column = ITEMNUMBERCOLUMN; column <= LOADSONEACHBREAKERCOLUMN; column++) {
				 if(column==ELCNUMBERCOLUMN){
					 try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
									if(newmaxelc< u.i(mcrstringarray[METERINGLIST][lineno][column])){
										newmaxelc=u.i(mcrstringarray[METERINGLIST][lineno][column]);
								}
							}
						} catch (Throwable e) {
							
						} 
				 }
				 if(column==LOADNAMECOLUMN){
					 try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
								try{
									 u.log("Modbus, "+mcrstringarray[METERINGLIST][lineno][MODBUSADDRESSCOLUMN]);
									int mod=u.i(mcrstringarray[METERINGLIST][lineno][MODBUSADDRESSCOLUMN]);
									newtotalsam++;
								}catch(Throwable e){
									e.printStackTrace();
								}
								
							}
						} catch (Throwable e) {
							
						} 
				 }
				 if(column==PHCOLUMN){
					 try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
								try{
									PH.add(mcrstringarray[METERINGLIST][lineno][column]);
								}catch(Throwable e){
									
								}
								
							}
						} catch (Throwable e) {
							
						} 
				 }
				 if(column==CTSIZEPHYISICALCOLUMN){
					 try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
								try{
									CTtypeListwduplicates.add(mcrstringarray[METERINGLIST][lineno][column]);
								}catch(Throwable e){
									
								}
								
							}
						} catch (Throwable e) {
							
						} 
				 }
				 if(column==CTQTYCOLUMN){
					 try {
							if (!mcrstringarray[METERINGLIST][lineno][column]
									.equals(null)) {
								try{
								int ctcnt=u.i(mcrstringarray[METERINGLIST][lineno][column]);
								newtotalCT=newtotalCT+ctcnt;
								}catch(Throwable e){
									
								}
								
							}
						} catch (Throwable e) {
							
						} 
				 }
			 }
		   }
			
			for (int lineno = 0; lineno < m[TEMPSENSORCOMMISSIONING]; lineno++) {
			  try {
				if(! mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempelc].equals(null)){
					
						if(newmaxelc< u.i(mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempelc])){
						
							newmaxelc=u.i(mcrstringarray[TEMPSENSORCOMMISSIONING][lineno][tempelc]);
						}
						newtotaltemp++;
				 } 
				}catch (Throwable e) {
						
				} 
				
			
			}
			
		        u.log("max elcs, "+newmaxelc);
		        u.log("max SAMS, "+newtotalsam);
		        u.log("max TotalTemps, "+newtotaltemp);
		        u.log("TotalCTs, "+newtotalCT);
			
			
			
			// Ibe's Detailed CT info rom insertion
			int bomctdetail = 0;
			int brsizecolumn = 2;

			String ctphy;
			/* Array for List with duplicates */
			
			

			TableRow.LayoutParams lpff = new TableRow.LayoutParams(
					TableRow.LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			TableRow.LayoutParams lpfw = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			TableRow.LayoutParams lpwf = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
			TableRow.LayoutParams lpww = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			Collections.sort(CTtypeListwduplicates);
			for (int h = 0; h < CTtypeListwduplicates.size(); h++) {
				//Log.d("List item with duplicates" + h, CTtypeListwduplicates.get(h));
			}

			List<String> CTtypeListwoutduplicates = new ArrayList<String>();

			CTtypeListwoutduplicates = new ArrayList<String>(new HashSet<String>(
					CTtypeListwduplicates));
			Collections.sort(CTtypeListwoutduplicates);
			int[] quantitypertype = new int[CTtypeListwoutduplicates.size()];
			int[] phpertype = new int[CTtypeListwoutduplicates.size()];
			for (int h = 0; h < CTtypeListwoutduplicates.size(); h++) {
				Log.d("List item w/o duplicate " + h, CTtypeListwoutduplicates.get(h));
				String teststring = CTtypeListwoutduplicates.get(h);
				for (int y = 0; y < CTtypeListwduplicates.size(); y++) {
					try{	
						if (CTtypeListwduplicates.get(y).equals(teststring)) {
							quantitypertype[h]++;
							if (teststring.contains("RCS")) {
								// only count one ct for ropes
								phpertype[h] = phpertype[h] + 1;
	
							} else {
								phpertype[h] = phpertype[h] + u.i(PH.get(y));
	
							}
						}
					  }catch(Throwable e1){
						 try{
						  phpertype[h] = phpertype[h] + u.i(mcrstringarray[METERINGLIST][y][CTQTYCOLUMN]);
						 }catch(Throwable e2){
							 
						 }
					   }
				}
			}

			int newctrowscount = CTtypeListwoutduplicates.size();

			// ?Create a look to check when it's three phase or 4 or 1 then use that
			// in ur CTquantity calculation below

			// If Spinner is empty, use PH as 3 by default, else use Spiner Value
			try{
				bomcttablelayout.removeAllViews();
			}catch(Throwable e){
				
			}
			for (int u = 0; u < newctrowscount; u++) {
				TableRow tr = new TableRow(ctx);
				TextView CTblank = new TextView(ctx);
				CTblank.setLayoutParams(lpww);
				CTblank.setText("          ");
				CTblank.setTextSize(22);
				CTblank.setTypeface(null, Typeface.BOLD_ITALIC);

				TextView CTblank1 = new TextView(ctx);
				CTblank1.setLayoutParams(lpww);
				CTblank1.setText("                            ");
				CTblank1.setTextSize(22);
				CTblank1.setTypeface(null, Typeface.BOLD_ITALIC);

				TextView CTblank2 = new TextView(ctx);
				CTblank2.setLayoutParams(lpww);
				CTblank2.setText("          ");
				CTblank2.setTextSize(22);
				CTblank2.setTypeface(null, Typeface.BOLD_ITALIC);

				TextView CTblank3 = new TextView(ctx);
				CTblank3.setLayoutParams(lpww);
				CTblank3.setText("          ");
				CTblank3.setTextSize(22);
				CTblank3.setTypeface(null, Typeface.BOLD_ITALIC);

				TextView CTblank4 = new TextView(ctx);
				CTblank4.setLayoutParams(lpww);
				CTblank4.setText("          ");
				CTblank4.setTextSize(22);
				CTblank4.setTypeface(null, Typeface.BOLD_ITALIC);

				TextView CTsizeTV = new TextView(ctx);
				CTsizeTV.setLayoutParams(lpww);
				CTsizeTV.setText(CTtypeListwoutduplicates.get(u));
				CTsizeTV.setTextSize(24);
				CTsizeTV.setTypeface(null, Typeface.ITALIC);

				TextView CTquantityTV = new TextView(ctx);
				CTquantityTV.setLayoutParams(lpww);
				CTquantityTV.setText(String.valueOf(phpertype[u] + " "));
				CTquantityTV.setTextSize(22);
				CTquantityTV.setTypeface(null, Typeface.BOLD_ITALIC);
				CTquantityTV.setGravity(Gravity.CENTER);

				tr.addView(CTblank);
				tr.addView(CTsizeTV);
				tr.addView(CTquantityTV);
				tr.addView(CTblank1);
				tr.addView(CTblank2);
				tr.addView(CTblank3);
				tr.addView(CTblank4);
				Tabs1.bomcttablelayout.addView(tr);

			}
			
			//bomgatewayservercount.setText(u.s(countedgwsfromfloorplan));
			bomelccount.setText(u.s(newmaxelc));
			bomsamcount.setText(u.s(newtotalsam));
			bomtscount.setText(u.s(newtotaltemp));
			bomCTcount.setText(u.s(newtotalCT));
			
			// Add Gateway Server count
			int currentgatewaycount=0;
			try{
			currentgatewaycount=u.i(db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_BOM,
					"bomgatewayservercount",0));
			}catch(Throwable e){
				currentgatewaycount=0;
			}
			if(currentgatewaycount==0){
			 bomgatewayservercount.setText("1");
			}
			
			refreshbom();
			
		}
		public void processpendingimagecapture(){
			File dcimlocationfile=new File(dcimlocationstring);
			if(picturestoptime==-1){
				picturestoptime=System.currentTimeMillis();
			}
			u.log("pictuerstarttime,picturestoptime "+picturestarttime+" "+picturestoptime);
			System.out.println(picturestarttime);
			System.out.println(picturestoptime);
			
//			databasefoldername=foldername;
//			databasefoldername=u.finddatabasefoldername(masterfoldername,foldername,ctx);
//			u.log("Foldername from databasename ,"+databasefoldername);
			db = new DatabaseHandler(Tabs1.this, foldername);
			
			try{
				for(File file:dcimlocationfile.listFiles()){
//					u.log(file.lastModified());
//					u.log(String.valueOf(file.lastModified()>picturestarttime)+" "+String.valueOf(file.lastModified()<picturestoptime));
					if(file.lastModified()>picturestarttime&&file.lastModified()<picturestoptime){
						String destinationpath = null;
						String parentstring;
						File parent;
						switch(typeselected){
							case SITETAB:
//							sitename[u.i(numberselected)].et.requestFocus();
							try{
							parentstring=ProjectDirectory + "/"+ basedirectory[SITEINFOFOLDER]+"/"+db.getvaluemulticolumnhybrid(db.TABLE_SITEINFO,"sitename",u.i(numberselected));
							}catch(Throwable e){
							parentstring=ProjectDirectory + "/"+ basedirectory[SITEINFOFOLDER]+"/"+sitename[u.i(numberselected)].et.getText().toString();
							}
//							u.log(parentstring);
							parent=new File(parentstring);
							parent.mkdirs();
							destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
						break;
						case COMPONENTSTAB:
//							componentname[u.i(numberselected)].et.requestFocus();
							try{
							parentstring=ProjectDirectory + "/"+ basedirectory[COMPONENTSFOLDER]+"/"+db.getvaluemulticolumnhybrid(db.TABLE_COMPONENTINFO,"componentname",u.i(numberselected));
							}catch(Throwable e){
								parentstring=ProjectDirectory + "/"+ basedirectory[COMPONENTSFOLDER]+"/"+componentname[u.i(numberselected)].et.getText().toString();
							}
//							u.log(parentstring);
							parent=new File(parentstring);
							parent.mkdirs();
							destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
							break;
						case ASSETSTAB:
//							assetname[u.i(numberselected)].et.requestFocus();
							db.showfulldblog(db.TABLE_ASSETINFO);
							try{
							parentstring=ProjectDirectory + "/"+ basedirectory[ASSETSFOLDER]+"/"+db.getvaluemulticolumnhybrid(db.TABLE_ASSETINFO,"assetname",u.i(numberselected));
							}catch(Throwable e){
								parentstring=ProjectDirectory + "/"+ basedirectory[ASSETSFOLDER]+"/"+assetname[u.i(numberselected)].et.getText().toString();
							}
//							u.log(parentstring);
							parent=new File(parentstring);
							parent.mkdirs();
							destinationpath=parent.getAbsolutePath()+"/"+FilenameUtils.getName(file.getAbsolutePath());
							break;
						}
						
						File destFile=new File(destinationpath);
						u.log(file.getAbsolutePath()+" "+destFile.getAbsolutePath());
						try {
							u.copyFile(file, destFile);
							file.delete();
//							u.log("copyingfile from "+file+" to "+destFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				}
			}catch(Throwable e){
				e.printStackTrace();
				showToastLong("Your images did not save properly into the project, your DCIM directory may not set properly. Please locate this directory and modify it's location in settings.");
			}
		}
		
		
		public static Bitmap convertToMutable(Bitmap imgIn) {
		    try {
		        //this is the file going to use temporally to save the bytes. 
		        // This file will not be a image, it will store the raw image data.
		        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

		        //Open an RandomAccessFile
		        //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
		        //into AndroidManifest.xml file
		        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

		        // get the width and height of the source bitmap.
		        int width = imgIn.getWidth();
		        int height = imgIn.getHeight();
		        Config type = imgIn.getConfig();

		        //Copy the byte to the file
		        //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
		        FileChannel channel = randomAccessFile.getChannel();
		        MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
		        imgIn.copyPixelsToBuffer(map);
		        //recycle the source bitmap, this will be no longer used.
		        imgIn.recycle();
		        System.gc();// try to force the bytes from the imgIn to be released

		        //Create a new bitmap to load the bitmap again. Probably the memory will be available. 
		        imgIn = Bitmap.createBitmap(width, height, type);
		        map.position(0);
		        //load it back from temporary 
		        imgIn.copyPixelsFromBuffer(map);
		        //close the temporary file and channel , then delete that also
		        channel.close();
		        randomAccessFile.close();

		        // delete the temp file
		        file.delete();

		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } 

		    return imgIn;
		}
		public void couldnotcreatenewproject(){
			
			//first run failed for some reason, delete folder, and database since they are incomplete
			for(int x=0; x<ctx.databaseList().length;x++){
	    		u.log(ctx.databaseList()[x]);
	    		if(ctx.databaseList()[x].contains(foldername)){
	    			ctx.deleteDatabase(ctx.databaseList()[x]);
	    		}
			
	    	}
			u.DeleteRecursive(new File(ProjectDirectory));
			showToast("Please restart the tablet, the file system is not responding");
			finish();
		}
		public void refreshmaintabsfromdb(){
			
			db.showfulldblog(db.TABLE_ASSETINFO);
			db.showfulldblog(db.TABLE_COMPONENTINFO);
			
			
			
			
			//s=2;
			//makeviewsforreadingsitesfromreportdb();
			if(COMPONENTSTABLOADED){
				destroycomponentstsab();
				makeviewsforreadingcomponentsfromreportdb();
				readcomponentsdb();
			}
			
			if(ASSETSTABLOADED){
				destroyassetstab();
				makeviewsforreadingassetsfromreportdb();
				readassetsdb();
			}
			
			
		}
		public void removerowviews(final int k, int type, boolean showonscreen){
			if (type==SITETAB) {
				locationcontainer.removeView(buildingtable[k]);
			}
			if (type==COMPONENTSTAB) {
				componentscontainer.removeView(componenttable[k]);
			}
			if (type==ASSETSTAB) {
				u.log("removing asset table, "+k);
				assetscontainer.removeView(assettable[k]);
			}
			if (type==RECOMMENDATIONSTAB) {
			}
		}
		public static String gettablebytype(int type){
			String table = null;
			switch(type){
			case SITETAB:
				table=db.TABLE_SITEINFO;
				break;
			case ASSETSTAB:
				table=db.TABLE_ASSETINFO;
				break;
			case COMPONENTSTAB:
				table=db.TABLE_COMPONENTINFO;
				break;
				
			}
			return table;
		}
		public String[][][] sortingmcrstringarray(String[][][] givenmcr){
			String[][][] sortedmcr2 = new String[maxsheets][maxexcelrows][maxexcelcolumns];
			for(int s=0;s<maxsheets;s++){
				for(int r=0;r<maxexcelrows;r++){
					for(int c=0;c<maxexcelcolumns;c++){
						try{
							sortedmcr2[s][r][c]=givenmcr[s][r][c];
						}catch(Throwable e){
						
						}
					}
				}
			}
			  
			int totalloads=db.countrows(db.TABLE_MCR_METERINGLIST);
//			for(int t=0; t<totalloads;t++){
//				try{
//				u.log("mcr actual array elc values, "+t+", "+givenmcr[1][t][ELCNUMBERCOLUMN]);
//				u.log("mcr actual array elc values, "+t+", "+sortedmcr2[1][t][ELCNUMBERCOLUMN]);
//				}catch(Throwable e){
//					e.printStackTrace();	
//				}
//			}
			String[] temp = null;
			int r1=0,r=0;
		
			//metering sort
				for(r1=0; r1<totalloads;r1++)
				{
					for(r=0; r<totalloads;r++)	
					{
					try{	
						

						if(	( u.i(sortedmcr2[METERINGLIST][r][ELCNUMBERCOLUMN]) ) > ( u.i(sortedmcr2[METERINGLIST][r+1][ELCNUMBERCOLUMN] ) ) )
							{
							temp = sortedmcr2[METERINGLIST][r];
							sortedmcr2[METERINGLIST][r]= sortedmcr2[METERINGLIST][r+1];;
							sortedmcr2[METERINGLIST][r+1]=temp; 
							
							//for Itemnumber
							sortedmcr2[METERINGLIST][r][ITEMNUMBERCOLUMN]=u.s(r+1); 
							sortedmcr2[METERINGLIST][r+1][ITEMNUMBERCOLUMN]=u.s(r+2);
	
						}
					}
					catch(Throwable e){
						e.printStackTrace();
					}
						
					}
				}
				
				//tempsheet sort
				int totaltempss=db.countrows(db.TABLE_MCR_TEMPSLIST);
				for(r1=0; r1<totaltempss;r1++)
				{
					for(r=0; r<totaltempss-1;r++)	
					{
					try{	
						
						if(	( u.i(sortedmcr2[TEMPSENSORCOMMISSIONING][r][TEMPELCNUMBERCOLUMN]) ) > ( u.i(sortedmcr2[TEMPSENSORCOMMISSIONING][r+1][TEMPELCNUMBERCOLUMN] ) ) )
							{
							temp = sortedmcr2[TEMPSENSORCOMMISSIONING][r];
							sortedmcr2[TEMPSENSORCOMMISSIONING][r]= sortedmcr2[TEMPSENSORCOMMISSIONING][r+1];;
							sortedmcr2[TEMPSENSORCOMMISSIONING][r+1]=temp; 
							
							//for Itemnumber
							sortedmcr2[TEMPSENSORCOMMISSIONING][r][ITEMNUMBERCOLUMN]=u.s(r+1); 
							sortedmcr2[TEMPSENSORCOMMISSIONING][r+1][ITEMNUMBERCOLUMN]=u.s(r+2);
	
						}
					}
					catch(Throwable e){
						e.printStackTrace();
					}
						
					}
				}
					
			return sortedmcr2; 
			
			
		}
		public void setvaluesbasedoncompany(){
			try{
				String Company_Name=db.getvaluemulticolumnhybrid(DatabaseHandler.TABLE_SITEINFO, "sitename",0);
				if(Company_Name.equals("Morrisons")){
					ISGROCERYSTRORE=true;
					ISMORRISONS=true;
				}
				if(Company_Name.equals("Morrisons")||Company_Name.equals("Sainsbury's")){
					ISGROCERYSTRORE=true;
					ISDATACENTER=false;
				}else{
					ISDATACENTER=true;
					ISGROCERYSTRORE=false;
				}
				
			}catch(Throwable e){
				
			}
		}
		public void LoadAssetsTab(){
			makeviewsforreadingassetsfromreportdb();
			readassetsdb();
			comparereportwithfilestructure(ASSETSFOLDER);
			addpictures(ASSETSTAB);
			
		}
		
		public void LoadConsumptionTab(){
				setupconsumptiontable();
				if(choosefolderfirst.newproject){
					choosefolderfirst.newproject=false;
					if(ISMORRISONS){
						readexistingconsumptiontable();
					}
				}else{
					readconsumptiondb();
				}
		}
		public void LoadComponentsTab(){
			makeviewsforreadingcomponentsfromreportdb();
			readcomponentsdb();
			comparereportwithfilestructure(COMPONENTSFOLDER);
			addpictures(COMPONENTSTAB);
			
		}
		
		public void LoadRecommendationsTab(){
			makeviewsforreadingrecommendationsfromreportdb();
			setuprecommendationstable();
			createandlinkrecommendationviewstodb();
			siterecommendationlisteners();
			fillsiterecommendationvalues();
			readrecommendationsdb();
		
		}
		public void LoadMCRTab(){
			refreshmcrtable(MCRDBVALUES);
			repopulatemcrlocations();
		
		}
		
		
 		public void showvirtualmeterdialog(final int row){
 			refreshmeterslists();
 			
 			Boolean newvirtualmeter;
 			u.log(row);
 			if(row>db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST)-1){
 				newvirtualmeter=true;
 			}else{
 				newvirtualmeter=false;
 			}
 	
 			String dialogtitle;
 			String vrmnamestring="";
 			if(newvirtualmeter){
 				vrmnamestring="Virtual Meter "+u.s(row);
 				
 			}else{
 				vrmnamestring=db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Virtual_Meter_Name" , row);
 				
 			}
 			dialogtitle="Virtual Meter:"+vrmnamestring;
 			
 			LinearLayout virtualmeterdialoglayout=new LinearLayout(this);
 			virtualmeterdialoglayout.setLayoutParams(lpff);
 			virtualmeterdialoglayout.setOrientation(LinearLayout.VERTICAL);
 			
 			final LinearLayout formulabar=new LinearLayout(this);
 			virtualmeterdialoglayout.setLayoutParams(lpwf);
 			formulabar.setOrientation(LinearLayout.HORIZONTAL);
 			
 			final AutoCompleteTextView vrmname=new AutoCompleteTextView(this);
 			vrmname.setLayoutParams(lpww);
 			vrmname.setTextSize(75f);
 			if(!newvirtualmeter){
 				vrmname.setText(vrmnamestring);
 			}
 			formulabar.addView(vrmname);
 			
 			
 			TextView equalsign=new TextView(this);
 			equalsign.setLayoutParams(lpww);
 			equalsign.setText("=");
 			equalsign.setTextSize(75f);
 			formulabar.addView(equalsign);
 		
 			
 		
 			//realmeternames = db.getcolumn(db.TABLE_MCR_METERINGLIST, LOADNAMECOLUMN, db.KEY_MCR_METERING_TITLES);
 			//System.out.println(realmeternames);
 			//add virtualmeters
 			int virtualloadnamecolum=1;
 			
 			
 			String[] oldvariables = null;
 			String[] oldcoefficients = null;
 			if(newvirtualmeter){
 				vrmvariablecount=2;
 			}else{
 				
 				oldvariables=getvariables(row);
 				u.log(oldvariables);
 				for(int h=0; h<oldvariables.length; h++){
 					System.out.println(oldvariables[h]);
 				}
 				
 				oldcoefficients=getcoefficients(row);
 				u.log(oldcoefficients);
 				for(int h=0; h<oldcoefficients.length; h++){
 					System.out.println(oldcoefficients[h]);
 				}
 				vrmvariablecount=oldvariables.length;
 			}
 			
 			ArrayList<Spinner> variables=new ArrayList<Spinner>();
 			ArrayList<Spinner> operatorspinners=new ArrayList<Spinner>();
 			
 			
 			u.log(vrmvariablecount);
 			for(int h=0; h<vrmvariablecount;h++){
 				try{
 				Spinner operators1=new Spinner(this);
	 			operators1.setLayoutParams(lpww);
	 			operators1.setAdapter(operatorsadapter);
	 			u.log("operator adapter set");
	 			if(!newvirtualmeter){
	 				
	 				u.settexttoview(String.valueOf(oldcoefficients[h]), operators1);
	 			}
	 			
	 			operatorspinners.add(operators1);
 				
	 			Spinner variable1=new Spinner(this);
	 			variable1.setLayoutParams(lpww);
	 			variable1.setAdapter(allmeternamesArrayAdapter);
	 			u.log("reameters adapter set");
	 			
	 			
	 			if(!newvirtualmeter){
	 				u.settexttoview(oldvariables[h], variable1);
	 			}
	 			variables.add(variable1);
	 			
	 			formulabar.addView(operatorspinners.get(h));
	 			formulabar.addView(variables.get(h));
	 			
 				}catch(Throwable e){
 					
 				}
	 		
 			}
 			virtualmeterdialoglayout.addView(formulabar);
 		
 			final Spinner loadtypespinner=new Spinner(this);
 			loadtypespinner.setLayoutParams(lpww);
 			loadtypespinner.setAdapter(mcrloadtypespinneradapter);
 			u.log("mcrloadtype adapter set");
 			
 			if(!newvirtualmeter){
 				u.settexttoview(db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST,"Load_Type" , row),loadtypespinner);
 			}
 			virtualmeterdialoglayout.addView(loadtypespinner);
 			Button addvariabletoformulabutton=new Button(this);
 			addvariabletoformulabutton.setText("Add A Meter To Equation");
 			addvariabletoformulabutton.setLayoutParams(lpww);
 			addvariabletoformulabutton.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					Spinner operators1=new Spinner(ctx);
		 			operators1.setLayoutParams(lpww);
		 			operators1.setAdapter(operatorsadapter);
		 			u.log("operators1 adapter set");
		 			
		 			
		 			Spinner variable1=new Spinner(ctx);
		 			variable1.setLayoutParams(lpww);
		 			variable1.setAdapter(allmeternamesArrayAdapter);
		 			
		 			u.log("allmeternamesArrayAdapter adapter set");
		 			
		 			
					formulabar.addView(operators1);
		 			formulabar.addView(variable1);
		 			
		 			vrmvariablecount=vrmvariablecount+1;
		 			
				}});
 			virtualmeterdialoglayout.addView(addvariabletoformulabutton);
 			
 			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
 			builder.setTitle(dialogtitle)
 			.setView(virtualmeterdialoglayout)		
 			.setMessage(
 							"Please complete the fields to create a virtual meter.")
 					.setCancelable(true)
 					.setIcon(R.drawable.ic_launcher)
 					.setPositiveButton("OK",
 							new DialogInterface.OnClickListener() {
 								public void onClick(DialogInterface dialog, int id) {
 									String tablename=db.TABLE_MCR_VIRTUAL_METERINGLIST;
 									String formula = "";
 									u.log(u.s(((ViewGroup)formulabar).getChildCount()));
 									u.log((2*vrmvariablecount)+2);
 									int formulasize=(2*vrmvariablecount)+2;
 									for(int x=0; x<formulasize; x++){
 										u.log(x);
 										formula=formula+u.gettextfromview(formulabar.getChildAt(x));
 									}
 									String loadtype=u.gettextfromview(loadtypespinner);
 									
 									u.log((Spinner)((ViewGroup)formulabar).getChildAt(((ViewGroup)formulabar).getChildCount()-1));
 									u.log(formula);
 									u.log(loadtype);
 									u.log(u.s(row));
 									u.log(vrmname.getText().toString());
 									String[] strings={vrmname.getText().toString(),formula,loadtype};
 									//"Item_Number", "Virtual_Meter_Name", "Formula", "Load_Type"
 									
 									db.addrow(tablename, strings, row+1);
 									db.showfulldblog(db.TABLE_MCR_VIRTUAL_METERINGLIST);
 									try{
 										virtualmcrtable.removeAllViews();
 									}catch(Throwable e){
 										
 									}
 									if(db.countrows(db.TABLE_MCR_VIRTUAL_METERINGLIST)>0){
 										createvirtualmcrtablefrom(SOURCE);
 									}
 									dialog.dismiss();
 								}
 							})
 					
 					.setNegativeButton("CANCEL",
 							new DialogInterface.OnClickListener() {
 								public void onClick(DialogInterface dialog, int id) {
 									dialog.cancel();
 								}
 							});
 			AlertDialog alertDialog = builder.create();
 			u.log("alertDialog created");

 		    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
 		    lp.copyFrom(alertDialog.getWindow().getAttributes());
 		    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
 		    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
 		    alertDialog.show();
 		   	alertDialog.getWindow().setAttributes(lp);
 			alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
 			
 		}
 		public String[] getvariables(int row){
 			String formula=db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST, "Formula", row);
			u.log(formula);
 			String rightsideofformula=formula.split("=")[1];
 			u.log(rightsideofformula);
 			String varsseparatedbycommas=rightsideofformula.replace("+", ",").replace("-", ",");
			u.log(varsseparatedbycommas);
			varsseparatedbycommas=varsseparatedbycommas.replaceFirst(",", "").trim();
 			String[] variables=varsseparatedbycommas.split(",");
			for(int h=0; h<variables.length; h++){
				u.log(variables[h]);
			}
 			
 			
 			return variables;
 		}
 		public String[] getcoefficients(int row){
 			String formula=db.getvaluemulticolumnhybrid(db.TABLE_MCR_VIRTUAL_METERINGLIST, "Formula", row);
 			 String rightsideofformula=formula.split("=")[1];
 			u.log(rightsideofformula);
 			
 			String vars[]=getvariables(row);
 			u.log(vars.length);
 			 for(int h=0; h<vars.length; h++){
 				 u.log(vars[h]);
 				 if(vars[h].length()>0){
 					 rightsideofformula=rightsideofformula.replace(vars[h], ",");
 				 }
 				 u.log(rightsideofformula);
 	        }
 	        
 	       
 	        u.log(formula);
 	        String[] actualcoefficients=rightsideofformula.split(",");
 	        for(int h=0; h<actualcoefficients.length; h++){
 	        	u.log(actualcoefficients[h]);
 	        }
 			return actualcoefficients;
 		}
 		public void makecalculatorbutton(){
 			calculatorbutton = new ImageView(this);
 			calculatorbutton.setImageResource(R.drawable.calculator48x48);
 			calculatorbutton.setLayoutParams(lpww);
 			calculatorbutton.setOnClickListener(new View.OnClickListener() {
 				public void onClick(View v) {
 					if (calcisopen) {
 						try {
 							((ViewGroup) calc.getParent()).removeView(calc);

 						} catch (NullPointerException e) {

 						}
 						calcisopen = false;
 					} else {

 						calcisopen = true;
 						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

 						int[] loc = new int[2];
 						int[] scrollloc = new int[2];
 						v.getLocationOnScreen(loc);
 						ViewParent nextparent = v.getParent();
 						while (!(nextparent instanceof RelativeLayout)) {
 							nextparent = nextparent.getParent();
 						}
 						((RelativeLayout) nextparent)
 								.getLocationInWindow(scrollloc);
 						// tab2scrollview.getLocationOnScreen(scrollloc);

 						RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
 								RelativeLayout.LayoutParams.WRAP_CONTENT,
 								RelativeLayout.LayoutParams.FILL_PARENT);

 						rllp.leftMargin = loc[0] - scrollloc[0];
 						rllp.topMargin = loc[1] - scrollloc[1] + v.getHeight();
 						try {
 							((ViewGroup) calc.getParent()).removeView(calc);
 						} catch (NullPointerException e) {

 						}

 						((RelativeLayout) nextparent).addView(calc, rllp);

 						initControls();
 						initScreenLayout();
 						reset();

 					}
 				}
 			});
 		}
 		public void refreshmeterslists(){
 			realmeternames=new ArrayList<String>();
 			virtualmeternames=new ArrayList<String>();
 			allmeternames=new ArrayList<String>();
 			try{
 				realmeternames = db.getcolumn(db.TABLE_MCR_METERINGLIST, LOADNAMECOLUMN, db.KEY_MCR_METERING_TITLES);
 				int Virtual_Meter_Name=1;
 				virtualmeternames=db.getcolumn(db.TABLE_MCR_VIRTUAL_METERINGLIST, Virtual_Meter_Name, db.KEY_VIRTUAL_MCR_METERING_TITLES);
 				
 				allmeternames.addAll(realmeternames);
 				allmeternames.addAll(virtualmeternames);
 			
 			}catch(Throwable e){
 				e.printStackTrace();
 			}
 			allmeternamesArrayAdapter = new ArrayAdapter<String>(this,
 					R.layout.largespinnertextview, allmeternames);
 			allmeternamesArrayAdapter
 			.setDropDownViewResource(R.layout.largespinnertextview);
 		}
 		public void importassetdialogue(){
 			
 			AlertDialog.Builder builder = new AlertDialog.Builder(this);
 			builder.setTitle("Importing Assets")
 					.setMessage("Would like to add asstes or replace assets")
 					.setIcon(R.drawable.ic_launcher)
 					.setNegativeButton("CANCEL",
 							new DialogInterface.OnClickListener() {
 								public void onClick(DialogInterface dialog, int id) {

 									dialog.cancel();
 								}
 							})
	 			.setPositiveButton("Import & Replace Assets",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									
									importandreplaceassets = true;
					  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
									Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
									    + "/lhi/");
					  intent.setDataAndType(uri, "*/*");
					  startActivityForResult(Intent.createChooser(intent, "Open folder"),GETASSETIMPORTLOCATION);	
					  
	          
								}
							})
	 			.setNeutralButton("Import & Add Assets",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									importandreplaceassets = false;
					     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
										Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
										    + "/lhi/");
						  intent.setDataAndType(uri, "*/*");
						  startActivityForResult(Intent.createChooser(intent, "Open folder"),GETASSETIMPORTLOCATION);	
								}
							}).setCancelable(true);

 			AlertDialog assetimpdialogue = builder.create();
 			assetimpdialogue.show();
 			
 			
 		}
 	public void deletingassetpics(){
		String[] foldernames = u.listfolders(ProjectDirectory + "/"
							+ basedirectory[ASSETSFOLDER]);
		String foldernameforitem = new String();
		u.log("foldernames.length"+foldernames.length);
		for (int i = 0; i < foldernames.length; i++) {
			u.log("foldername i"+foldernames[i]);
			foldernameforitem = foldernames[i];
			File folder = new File(foldernameforitem);
			u.deleteDirectory(folder);
		}
 	}
 	
 	public void assetinfotodbfromexcel(){
 		int j = 0;   
 		int k = db.countrows(db.TABLE_ASSETINFO);   
		addmoreloop: while (true) {
			if(checkexcelforassetvalue(j)){
				 for(int num =1;num<=9;num++){
					u.log("adding asset row");
					String value=heldexcelrow[num-1];
						db.addorupdatemulticolumn(
								db.TABLE_ASSETINFO,
								k,
								num,
								value,
								db.KEY_ASSET_ATTRIBUTES);
				 }
				k++; 
				j++;
			}else{
				
				break addmoreloop;
			}
			
		}
 	}
 	
 	public boolean checkexcelforassetvalue(int j){
		boolean hasvalue=false;
		String titlerowstart = "A1";
		String titlerowstop = null;
		int startreferencecolumn=0;
		titlerowstop = "I1";

		u.log(titlerowstart);
		u.log(u.cellx(titlerowstart));
		u.log(u.celly(titlerowstart)+1+j);
		u.log(u.cellid(u.cellx(titlerowstop),u.celly(titlerowstop) + 1+j));
		// check first row
		String nextrowstart = u.cellid(u.cellx(titlerowstart),
				u.celly(titlerowstart) + 1+j);
		String nextrowstop = u.cellid(u.cellx(titlerowstop),
				u.celly(titlerowstop) + 1+j);
		u.log(nextrowstart+" "+nextrowstop);
		u.log("j"+j);
		heldexcelrow = u.getrow(assetsheet, nextrowstart, nextrowstop);
		for(int x=0; x<heldexcelrow.length; x++){
			u.log("Row read"+heldexcelrow[x]);
		}
	
		//skip ITEMNUMBERCOLUMN BECAUSE EXCEL HAS IT FILLED OUT ALREADY
		
		for (int column=startreferencecolumn; column<heldexcelrow.length; column++){
			if (heldexcelrow[column].trim().length()>0){
				hasvalue=true;
				break;
			}
		}
		return hasvalue;
	}
 	public void openassetsheet(File assetexcel){
		int ASSETSSHEETNUM=2;
 		File file = assetexcel;
		long size = file.length();
		int sizeint = (int) size;
		ws.setInitialFileSize(sizeint);
		try {

			assetworkbook = Workbook.getWorkbook(file, ws);
			assetsheet = assetworkbook.getSheet(ASSETSSHEETNUM);
			
			// mcrwriteworkbook=Workbook.createWorkbook(file, mcrworkbook);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	
}
