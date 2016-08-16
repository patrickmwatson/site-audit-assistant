package com.lifehackinnovations.siteaudit;


import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class UploadToMaestro extends Activity{
	
	
   //preference variables
	public WebView webview;
	public static boolean loaded=false;
	private ValueCallback<Uri> mUploadMessage;  
	private final static int FILECHOOSER_RESULTCODE=1;  
	private static Button do_something_button;
	private static int filenumber=0;
	private List<String> fileslist;
	private static String returnMessage;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadtomaestro);
        fileslist=Tabs1.docslist;
        for(int i=0;i<fileslist.size();i++){
        	Log.d("file","file://"+Environment.getExternalStorageDirectory().toString()+"/"+fileslist.get(i));
        }
        
        do_something_button=(Button)findViewById(R.id.do_something_button);
        do_something_button.setOnClickListener(new Button.OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//webview.loadUrl("javascript:alert(document.getElementById('MAESTRO_MENU_BAR_ITEM_1').textContent)");
				
				//try{
				//	Log.d("This is the return message",returnMessage);
				//}catch(NullPointerException e){
				//	e.printStackTrace();
				//}
				//refreshscreen();
				webview.loadUrl("javascript:console.log(document.getElementById('MAESTRO_MENU_BAR_ITEM_1').textContent)");
				try{
					Log.d("This is the return message",returnMessage);
				}catch(NullPointerException e){
					e.printStackTrace();
				}

			}});
        webview = (WebView)findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());  
        
        /*
        webview.setWebChromeClient(new WebChromeClient()  
        {  
               //The undocumented magic method override  
               //Eclipse will swear at you if you try to put @Override here  
               public void openFileChooser(ValueCallback<Uri> uploadMsg) {  
                 
                mUploadMessage = uploadMsg;  
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
                i.addCategory(Intent.CATEGORY_OPENABLE);  
                i.setType("image/*");  
                UploadToMaestro.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);  
           
               }  
        }); 
        */ 
        //WebSettings wvs=webview.getSettings();
        webview.setWebChromeClient(new CustomWebChromeClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://maestro1.lhi.com/verizon");
        //wvs.setUserAgentString("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/5.0; SLCC1; .NET CLR 2.0.50727; .NET CLR 1.1.4322; .NET CLR 3.5.30729; .NET4.0C; .NET CLR 3.0.30729; Zune 4.7; .NET4.0E)");
        
        webview.setWebViewClient(new WebViewClient() {
        
        	   public void onPageFinished(WebView view, String url) {
        	        // do your stuff here
        		   Log.d("this is the url",url);
        		   
        		   login(url);
        		   
        		   //openuploadmangerscreen(url);
        		   //clickuploadbutton(url);
        		  
        	    }
        	});
       
       
    }
    @Override  
    protected void onActivityResult(int requestCode, int resultCode,  
                                     Intent intent) {  
     if(requestCode==FILECHOOSER_RESULTCODE)  
     {  
     Log.d("resultCode=",u.s(resultCode));
     
     
     if (null == mUploadMessage) return;  
               Uri result = intent == null || resultCode != RESULT_OK ? null  
                       : intent.getData();  
               mUploadMessage.onReceiveValue(result);  
               Log.d("result=",String.valueOf(result));
               mUploadMessage = null;  
                 
     }  
    }  
    public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
          public void run() {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
          }
        });
      }
    public void clickelement(String element){
    	 	 webview.loadUrl("javascript:var evt = document.createEvent('MouseEvents');");
			 webview.loadUrl("javascript:var element=document.getElementById('"+element+"');");
			 webview.loadUrl("javascript:evt.initMouseEvent('click', true, true, window,0, 0, 0, 0, 0, false, false, false, false, 0, null);");
			 webview.loadUrl("javascript:element.dispatchEvent(evt);");
    }
    protected class CustomWebChromeClient extends WebChromeClient
    {
    	@Override
    	public boolean onConsoleMessage(ConsoleMessage consoleMessage){
			returnMessage=consoleMessage.message();
    		return true;
    		
    	}
    	/*
    	@Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            returnMessage=message;
            result.confirm();
            return true;
        }
    	*/
    	public void openFileChooser(ValueCallback<Uri> uploadMsg) {  

            mUploadMessage = uploadMsg;  
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
            i.addCategory(Intent.CATEGORY_OPENABLE);  
            i.setType("*/*");  
            startActivityForResult(Intent.createChooser(i,"Image Chooser"), FILECHOOSER_RESULTCODE);  
           
            //uploadMsg.onReceiveValue(Uri.parse("file:///storage/sdcard0/lhi/vyt/00_Report.xls"));
            /*
            try{
            	uploadMsg.onReceiveValue(Uri.parse("file://"+Environment.getExternalStorageDirectory().toString()+"/"+fileslist.get(filenumber)));
            }catch(IndexOutOfBoundsException e){
            	
            }
            filenumber++;
            webview.loadUrl("javascript:document.getElementById('cmdOK').click()");
             */
    		}

           public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
               openFileChooser(uploadMsg);
           }                   

           public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
               openFileChooser(uploadMsg);
           }                
    }
    @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //do your stuff and Return true to prevent this event from being propagated further
	    	
	    		try{
	    			this.getCurrentFocus().clearFocus();
	    		}catch(NullPointerException e){
				
	    		}
	    		//locationslist.clear();
	    		
	    		//docslist.clear();
	    		//prettydocslist.clear();
	    		this.finish();
	    	
	    	return true;
	    }
	  


	    return false;
	}
    public void login(String url){
    	if(loaded==false&&url.equals("http://maestro1.lhi.com/Maestro/DesktopModules/SignIn/SignIn.aspx?noredirect=true")){
			   
			   webview.loadUrl("javascript:document.getElementById('email').value='pwatson@lhi.com';");
			   webview.loadUrl("javascript:document.getElementById('password').value='Cb6&n5-Z';");
			   webview.loadUrl("javascript:document.getElementById('LoginBtn').click()");
			   Log.d("URL=",url);
			   loaded=true;
			   showToast(url);
 		   showToast("password entered");
		   }
    }
    public void openuploadmangerscreen(String url){
    	if(url.equals("http://maestro1.lhi.com/maestro/default.aspx?alias=verizon")){
			   Log.d("Main Page URL","This was run in http://maestro1.lhi.com/Maestro/Default.aspx?alias=verizon");
			   webview.loadUrl("http://maestro1.lhi.com/maestro/RequestPages/AttachmentUploader.aspx?controlID=DynTabContent7789_AttachmentConfig_&accessID=-1699&createShortcut=true");
			   //clickelement("MAESTRO_MENU_BAR_ITEM_1");
			   //clickelement("ContextMenuItem!SYSCTXMENU1!0!3");
			   //clickelement("DynTabContent7789_attachmentConfig_cmdAttachFile");
			   
		  
		   }
    }
    public void clickuploadbutton(String url){
    	 if(url.equals("http://maestro1.lhi.com/maestro/RequestPages/AttachmentUploader.aspx?controlID=DynTabContent7789_AttachmentConfig_&accessID=-1699&createShortcut=true")||filenumber<fileslist.size()){
			   Log.d("FileNumber=",u.s(filenumber));
			   Log.d("Main Page URL","This was run in http://maestro1.lhi.com/Maestro/Default.aspx?alias=verizon");
			   
			   webview.loadUrl("javascript:document.getElementById('FileUploader').click()");
			   
			   //clickelement("MAESTRO_MENU_BAR_ITEM_1");
			   //clickelement("ContextMenuItem!SYSCTXMENU1!0!3");
			   //clickelement("DynTabContent7789_attachmentConfig_cmdAttachFile");
			   
		  
		   }
    }
    public void refreshscreen(){
    	webview.reload();
    }
}