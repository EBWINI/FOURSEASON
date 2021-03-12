package four.season;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class get_order extends Activity implements B4AActivity{
	public static get_order mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "four.season", "four.season.get_order");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (get_order).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "four.season", "four.season.get_order");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "four.season.get_order", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (get_order) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (get_order) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return get_order.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (get_order) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (get_order) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            get_order mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (get_order) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _tmr = null;
public static String _order_date = "";
public static String _date_new = "";
public anywheresoftware.b4a.objects.collections.Map _spinnermap = null;
public anywheresoftware.b4a.objects.collections.Map _spinnermap2 = null;
public anywheresoftware.b4a.objects.collections.Map _spinnermap3 = null;
public anywheresoftware.b4a.objects.collections.Map _spinnermap4 = null;
public anywheresoftware.b4a.objects.collections.Map _spinnermap5 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner2 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner3 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner4 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext3 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext4 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext6 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext7 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext8 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext9 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext10 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public static long _sender_id = 0L;
public static long _good_type_ = 0L;
public static long _delevery_type_ = 0L;
public static long _order_type_ = 0L;
public static long _city_ = 0L;
public static String _rsl = "";
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public four.season.main _main = null;
public four.season.main_page _main_page = null;
public four.season.viewdetails _viewdetails = null;
public four.season.my_profile _my_profile = null;
public four.season.player _player = null;
public four.season.main_profile _main_profile = null;
public four.season.starter _starter = null;
public four.season.b4xcollections _b4xcollections = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
String _str = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job2 = null;
String _str2 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job3 = null;
String _str3 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job4 = null;
String _str4 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job5 = null;
String _str5 = "";
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="Activity.LoadLayout(\"get_order\")";
mostCurrent._activity.LoadLayout("get_order",mostCurrent.activityBA);
 //BA.debugLineNum = 46;BA.debugLine="Try";
try { //BA.debugLineNum = 47;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 48;BA.debugLine="job.Initialize(\"get_company\", Me)";
_job._initialize(processBA,"get_company",get_order.getObject());
 //BA.debugLineNum = 49;BA.debugLine="Dim str As String =\"\"& Main.server &\"/get_compan";
_str = ""+mostCurrent._main._server /*String*/ +"/get_company";
 //BA.debugLineNum = 50;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 52;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655369",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 54;BA.debugLine="Try";
try { //BA.debugLineNum = 55;BA.debugLine="Dim job2 As HttpJob";
_job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 56;BA.debugLine="job2.Initialize(\"good_type\", Me)";
_job2._initialize(processBA,"good_type",get_order.getObject());
 //BA.debugLineNum = 57;BA.debugLine="Dim str2 As String =\"\"& Main.server &\"/good_type";
_str2 = ""+mostCurrent._main._server /*String*/ +"/good_type";
 //BA.debugLineNum = 58;BA.debugLine="job2.Download(str2)";
_job2._download(_str2);
 } 
       catch (Exception e16) {
			processBA.setLastException(e16); //BA.debugLineNum = 60;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655377",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 62;BA.debugLine="Try";
try { //BA.debugLineNum = 63;BA.debugLine="Dim job3 As HttpJob";
_job3 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 64;BA.debugLine="job3.Initialize(\"delevery_type\", Me)";
_job3._initialize(processBA,"delevery_type",get_order.getObject());
 //BA.debugLineNum = 65;BA.debugLine="Dim str3 As String =\"\"& Main.server &\"/delevery_";
_str3 = ""+mostCurrent._main._server /*String*/ +"/delevery_type";
 //BA.debugLineNum = 66;BA.debugLine="job3.Download(str3)";
_job3._download(_str3);
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 68;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655385",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 70;BA.debugLine="Try";
try { //BA.debugLineNum = 71;BA.debugLine="Dim job4 As HttpJob";
_job4 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 72;BA.debugLine="job4.Initialize(\"order_type\", Me)";
_job4._initialize(processBA,"order_type",get_order.getObject());
 //BA.debugLineNum = 73;BA.debugLine="Dim str4 As String =\"\"& Main.server &\"/order_typ";
_str4 = ""+mostCurrent._main._server /*String*/ +"/order_type";
 //BA.debugLineNum = 74;BA.debugLine="job4.Download(str4)";
_job4._download(_str4);
 } 
       catch (Exception e32) {
			processBA.setLastException(e32); //BA.debugLineNum = 76;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655393",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 78;BA.debugLine="Try";
try { //BA.debugLineNum = 79;BA.debugLine="Dim job5 As HttpJob";
_job5 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 80;BA.debugLine="job5.Initialize(\"get_city\", Me)";
_job5._initialize(processBA,"get_city",get_order.getObject());
 //BA.debugLineNum = 81;BA.debugLine="Dim str5 As String =\"\"& Main.server &\"/get_city\"";
_str5 = ""+mostCurrent._main._server /*String*/ +"/get_city";
 //BA.debugLineNum = 82;BA.debugLine="job5.Download(str5)";
_job5._download(_str5);
 } 
       catch (Exception e40) {
			processBA.setLastException(e40); //BA.debugLineNum = 84;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655401",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 86;BA.debugLine="Try";
try { //BA.debugLineNum = 87;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 88;BA.debugLine="EditText1.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext1.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 89;BA.debugLine="EditText2.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext2.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 90;BA.debugLine="EditText3.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext3.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 91;BA.debugLine="EditText4.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext4.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 92;BA.debugLine="EditText5.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext5.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 93;BA.debugLine="EditText6.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext6.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 94;BA.debugLine="EditText7.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext7.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 95;BA.debugLine="EditText8.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext8.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 96;BA.debugLine="EditText9.Background = p.GetResourceDrawable(1730";
mostCurrent._edittext9.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 97;BA.debugLine="EditText10.Background = p.GetResourceDrawable(173";
mostCurrent._edittext10.setBackground(_p.GetResourceDrawable((int) (17301528)));
 } 
       catch (Exception e55) {
			processBA.setLastException(e55); //BA.debugLineNum = 99;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5655416",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 403;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 400;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
long _t = 0L;
smm.smmcolordialogwrapper.colordialogwrapper _colordialog = null;
 //BA.debugLineNum = 193;BA.debugLine="Private Sub Button1_Click";
 //BA.debugLineNum = 194;BA.debugLine="Try";
try { //BA.debugLineNum = 195;BA.debugLine="DateTime.DateFormat = \"yyyy/MM/dd hh:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy/MM/dd hh:mm:ss");
 //BA.debugLineNum = 196;BA.debugLine="Dim t As Long";
_t = 0L;
 //BA.debugLineNum = 197;BA.debugLine="t = DateTime.DateParse(DateTime.Date(DateTime.no";
_t = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 198;BA.debugLine="DateTime.DateFormat = \"yyyy/MM/dd hh:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy/MM/dd hh:mm:ss");
 //BA.debugLineNum = 200;BA.debugLine="order_date = DateTime.Date(t)";
_order_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(_t);
 //BA.debugLineNum = 207;BA.debugLine="If sender_id=0 Then";
if (_sender_id==0) { 
 //BA.debugLineNum = 209;BA.debugLine="Mymsg(\"اختر مرسل الطلب\")";
_mymsg("اختر مرسل الطلب");
 //BA.debugLineNum = 210;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 212;BA.debugLine="If good_type_=0 Then";
if (_good_type_==0) { 
 //BA.debugLineNum = 214;BA.debugLine="Mymsg(\"اختر نوع البضاعه\")";
_mymsg("اختر نوع البضاعه");
 //BA.debugLineNum = 215;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 217;BA.debugLine="If delevery_type_=0 Then";
if (_delevery_type_==0) { 
 //BA.debugLineNum = 218;BA.debugLine="Mymsg(\"اختر نوع التوصيل\")";
_mymsg("اختر نوع التوصيل");
 //BA.debugLineNum = 220;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 222;BA.debugLine="If order_type_=0 Then";
if (_order_type_==0) { 
 //BA.debugLineNum = 223;BA.debugLine="Mymsg(\"اختر نوع الطلب\")";
_mymsg("اختر نوع الطلب");
 //BA.debugLineNum = 225;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 227;BA.debugLine="If city_=0 Then";
if (_city_==0) { 
 //BA.debugLineNum = 228;BA.debugLine="Mymsg(\"اختر المحافظه\")";
_mymsg("اختر المحافظه");
 //BA.debugLineNum = 230;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 232;BA.debugLine="If EditText1.Text=\"\" Then";
if ((mostCurrent._edittext1.getText()).equals("")) { 
 //BA.debugLineNum = 233;BA.debugLine="Mymsg(\"تاكد من عدد الصناديق\")";
_mymsg("تاكد من عدد الصناديق");
 //BA.debugLineNum = 234;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 236;BA.debugLine="If EditText2.Text=\"\" Then";
if ((mostCurrent._edittext2.getText()).equals("")) { 
 //BA.debugLineNum = 237;BA.debugLine="Mymsg(\"تاكد من الوزن\")";
_mymsg("تاكد من الوزن");
 //BA.debugLineNum = 238;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 240;BA.debugLine="If EditText3.Text=\"\" Then";
if ((mostCurrent._edittext3.getText()).equals("")) { 
 //BA.debugLineNum = 241;BA.debugLine="Mymsg(\"تاكد من السعر\")";
_mymsg("تاكد من السعر");
 //BA.debugLineNum = 242;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 244;BA.debugLine="If EditText5.Text=\"\" Then";
if ((mostCurrent._edittext5.getText()).equals("")) { 
 //BA.debugLineNum = 245;BA.debugLine="Mymsg(\"تاكد من اسم العميل\")";
_mymsg("تاكد من اسم العميل");
 //BA.debugLineNum = 246;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 248;BA.debugLine="If EditText6.Text=\"\" Then";
if ((mostCurrent._edittext6.getText()).equals("")) { 
 //BA.debugLineNum = 249;BA.debugLine="Mymsg(\"تاكد من عنوان العميل\")";
_mymsg("تاكد من عنوان العميل");
 //BA.debugLineNum = 250;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 252;BA.debugLine="If EditText7.Text=\"\" Then";
if ((mostCurrent._edittext7.getText()).equals("")) { 
 //BA.debugLineNum = 253;BA.debugLine="Mymsg(\"تاكد من رقم هاتف\")";
_mymsg("تاكد من رقم هاتف");
 //BA.debugLineNum = 254;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 257;BA.debugLine="If EditText4.Text=\"\" Or EditText9.Text=\"\" Or Edi";
if ((mostCurrent._edittext4.getText()).equals("") || (mostCurrent._edittext9.getText()).equals("") || (mostCurrent._edittext10.getText()).equals("")) { 
 //BA.debugLineNum = 258;BA.debugLine="Mymsg(\"تاكد من التاريخ\")";
_mymsg("تاكد من التاريخ");
 //BA.debugLineNum = 259;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 262;BA.debugLine="If EditText4.Text<1 Or EditText4.Text>31 Then";
if ((double)(Double.parseDouble(mostCurrent._edittext4.getText()))<1 || (double)(Double.parseDouble(mostCurrent._edittext4.getText()))>31) { 
 //BA.debugLineNum = 263;BA.debugLine="Mymsg(\"تاكد من اليوم بالتاريخ\")";
_mymsg("تاكد من اليوم بالتاريخ");
 //BA.debugLineNum = 264;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 266;BA.debugLine="If EditText9.Text<1 Or EditText9.Text>12 Then";
if ((double)(Double.parseDouble(mostCurrent._edittext9.getText()))<1 || (double)(Double.parseDouble(mostCurrent._edittext9.getText()))>12) { 
 //BA.debugLineNum = 267;BA.debugLine="Mymsg(\"تاكد من الشهر بالتاريخ\")";
_mymsg("تاكد من الشهر بالتاريخ");
 //BA.debugLineNum = 268;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 270;BA.debugLine="If EditText9.Text=2 And EditText4.Text >28 Then";
if ((mostCurrent._edittext9.getText()).equals(BA.NumberToString(2)) && (double)(Double.parseDouble(mostCurrent._edittext4.getText()))>28) { 
 //BA.debugLineNum = 271;BA.debugLine="Mymsg(\"تاكد من اليوم بالتاريخ-شهر2\")";
_mymsg("تاكد من اليوم بالتاريخ-شهر2");
 //BA.debugLineNum = 272;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 274;BA.debugLine="If EditText10.Text<2021 Or EditText10.Text>9999";
if ((double)(Double.parseDouble(mostCurrent._edittext10.getText()))<2021 || (double)(Double.parseDouble(mostCurrent._edittext10.getText()))>9999) { 
 //BA.debugLineNum = 275;BA.debugLine="Mymsg(\"تاكد من السنه بالتاريخ\")";
_mymsg("تاكد من السنه بالتاريخ");
 //BA.debugLineNum = 276;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 278;BA.debugLine="date_new  =EditText10.Text & \"/\" & EditText9.Tex";
_date_new = mostCurrent._edittext10.getText()+"/"+mostCurrent._edittext9.getText()+"/"+mostCurrent._edittext4.getText();
 //BA.debugLineNum = 283;BA.debugLine="Dim colordialog As ColorDialog";
_colordialog = new smm.smmcolordialogwrapper.colordialogwrapper();
 //BA.debugLineNum = 284;BA.debugLine="colordialog.Initialize(\"dialog\")";
_colordialog.Initialize(mostCurrent.activityBA,"dialog");
 //BA.debugLineNum = 285;BA.debugLine="colordialog.Color=Colors.RGB(0,220,0)";
_colordialog.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (220),(int) (0)));
 //BA.debugLineNum = 286;BA.debugLine="colordialog.AnimationEnable=True";
_colordialog.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 287;BA.debugLine="colordialog.ContentText= \"        تم استلام الطل";
_colordialog.setContentText("        تم استلام الطلب بنجاح                       ");
 //BA.debugLineNum = 288;BA.debugLine="colordialog.Title=\"تسليم الطلب\"";
_colordialog.setTitle("تسليم الطلب");
 //BA.debugLineNum = 290;BA.debugLine="colordialog.Show(\"نعم\",\"لا\")";
_colordialog.Show("نعم","لا");
 } 
       catch (Exception e80) {
			processBA.setLastException(e80); //BA.debugLineNum = 293;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("5917604",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 340;BA.debugLine="Private Sub Button2_Click";
 //BA.debugLineNum = 341;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
return "";
}
public static String  _dialog_click(String _result) throws Exception{
String _str = "";
smm.smmpromptdialogwrapper.promptdialogwrapper _prompt = null;
 //BA.debugLineNum = 357;BA.debugLine="Sub dialog_click(result As String)";
 //BA.debugLineNum = 358;BA.debugLine="Log(result)";
anywheresoftware.b4a.keywords.Common.LogImpl("51507329",_result,0);
 //BA.debugLineNum = 359;BA.debugLine="If result=\"نعم\" Then";
if ((_result).equals("نعم")) { 
 //BA.debugLineNum = 360;BA.debugLine="rsl=result";
mostCurrent._rsl = _result;
 //BA.debugLineNum = 364;BA.debugLine="Try";
try { //BA.debugLineNum = 368;BA.debugLine="Dim str As String =\"\"& Main.server &\"/new_order";
_str = ""+mostCurrent._main._server /*String*/ +"/new_order?usrid="+BA.NumberToString(mostCurrent._main._usrid /*long*/ )+"&order_date="+_order_date.replace("/","xx").replace("\\","xx").replace(":","zz")+"&sender_id="+BA.NumberToString(_sender_id)+"&good_type_="+BA.NumberToString(_good_type_);
 //BA.debugLineNum = 369;BA.debugLine="str=str & \"&delevery_type_=\" & delevery_type_ &";
_str = _str+"&delevery_type_="+BA.NumberToString(_delevery_type_)+"&order_type_="+BA.NumberToString(_order_type_)+"&class_count="+mostCurrent._edittext1.getText()+"&weight="+mostCurrent._edittext2.getText()+"&price="+mostCurrent._edittext3.getText();
 //BA.debugLineNum = 370;BA.debugLine="str=str & \"&delivery_date=\" & date_new.Replace(";
_str = _str+"&delivery_date="+_date_new.replace("/","xx").replace("\\","xx").replace(":","zz")+"&temp_agent_name="+mostCurrent._edittext5.getText()+"&city_to="+BA.NumberToString(_city_)+"&temp_address="+mostCurrent._edittext6.getText()+"&receiver_phone="+mostCurrent._edittext7.getText()+"&note="+mostCurrent._edittext7.getText();
 //BA.debugLineNum = 371;BA.debugLine="WebView1.LoadUrl(str)";
mostCurrent._webview1.LoadUrl(_str);
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 373;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51507344",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 376;BA.debugLine="Dim prompt As PromptDialog";
_prompt = new smm.smmpromptdialogwrapper.promptdialogwrapper();
 //BA.debugLineNum = 377;BA.debugLine="prompt.Initialize(\"prompt\")";
_prompt.Initialize(mostCurrent.activityBA,"prompt");
 //BA.debugLineNum = 378;BA.debugLine="prompt.DialogType=prompt.DIALOG_TYPE_SUCCESS";
_prompt.setDialogType(_prompt.getDIALOG_TYPE_SUCCESS());
 //BA.debugLineNum = 379;BA.debugLine="prompt.Title=\"الفصول الاربعه\"";
_prompt.setTitle("الفصول الاربعه");
 //BA.debugLineNum = 380;BA.debugLine="prompt.AnimationEnable=True";
_prompt.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 381;BA.debugLine="prompt.ContentText=\" تمت عملية استلام الطلب من ا";
_prompt.setContentText(" تمت عملية استلام الطلب من الشركه بنجاح ");
 //BA.debugLineNum = 382;BA.debugLine="prompt.Show(\"تمت العمليه\",\"الغاء\")";
_prompt.Show("تمت العمليه","الغاء");
 }else {
 //BA.debugLineNum = 384;BA.debugLine="Dim prompt As PromptDialog";
_prompt = new smm.smmpromptdialogwrapper.promptdialogwrapper();
 //BA.debugLineNum = 385;BA.debugLine="prompt.Initialize(\"prompt\")";
_prompt.Initialize(mostCurrent.activityBA,"prompt");
 //BA.debugLineNum = 386;BA.debugLine="prompt.DialogType=prompt.DIALOG_TYPE_WARNING";
_prompt.setDialogType(_prompt.getDIALOG_TYPE_WARNING());
 //BA.debugLineNum = 387;BA.debugLine="prompt.Title=\"الفصول الاربعه\"";
_prompt.setTitle("الفصول الاربعه");
 //BA.debugLineNum = 388;BA.debugLine="prompt.AnimationEnable=True";
_prompt.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 389;BA.debugLine="prompt.ContentText=\" تم الغاء تسليم الطلب";
_prompt.setContentText(" تم الغاء تسليم الطلب                         ");
 //BA.debugLineNum = 390;BA.debugLine="prompt.Show(\"تمت العمليه\",\"الغاء\")";
_prompt.Show("تمت العمليه","الغاء");
 };
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim spinnerMap As Map";
mostCurrent._spinnermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 14;BA.debugLine="Dim spinnerMap2 As Map";
mostCurrent._spinnermap2 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 15;BA.debugLine="Dim spinnerMap3 As Map";
mostCurrent._spinnermap3 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 16;BA.debugLine="Dim spinnerMap4 As Map";
mostCurrent._spinnermap4 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 17;BA.debugLine="Dim spinnerMap5 As Map";
mostCurrent._spinnermap5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 18;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Spinner2 As Spinner";
mostCurrent._spinner2 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Spinner3 As Spinner";
mostCurrent._spinner3 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Spinner4 As Spinner";
mostCurrent._spinner4 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Spinner5 As Spinner";
mostCurrent._spinner5 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private EditText2 As EditText";
mostCurrent._edittext2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private EditText3 As EditText";
mostCurrent._edittext3 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private EditText4 As EditText";
mostCurrent._edittext4 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private EditText5 As EditText";
mostCurrent._edittext5 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private EditText6 As EditText";
mostCurrent._edittext6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private EditText7 As EditText";
mostCurrent._edittext7 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private EditText8 As EditText";
mostCurrent._edittext8 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private EditText9 As EditText";
mostCurrent._edittext9 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private EditText10 As EditText";
mostCurrent._edittext10 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Public sender_id As Long=0";
_sender_id = (long) (0);
 //BA.debugLineNum = 36;BA.debugLine="Public good_type_ As Long=0";
_good_type_ = (long) (0);
 //BA.debugLineNum = 37;BA.debugLine="Public delevery_type_ As Long=0";
_delevery_type_ = (long) (0);
 //BA.debugLineNum = 38;BA.debugLine="Public order_type_ As Long=0";
_order_type_ = (long) (0);
 //BA.debugLineNum = 39;BA.debugLine="Public city_ As Long=0";
_city_ = (long) (0);
 //BA.debugLineNum = 40;BA.debugLine="Dim rsl As String=0";
mostCurrent._rsl = BA.NumberToString(0);
 //BA.debugLineNum = 41;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 102;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 103;BA.debugLine="Try";
try { //BA.debugLineNum = 104;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 105;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 106;BA.debugLine="If Job.JobName = \"get_company\" Then";
if ((_job._jobname).equals("get_company")) { 
 //BA.debugLineNum = 107;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 108;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 110;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 111;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 112;BA.debugLine="ParseJSON(Job.GetString,Spinner1,spinnerMap )";
_parsejson(_job._getstring(),mostCurrent._spinner1,mostCurrent._spinnermap);
 };
 //BA.debugLineNum = 114;BA.debugLine="If Job.JobName = \"good_type\" Then";
if ((_job._jobname).equals("good_type")) { 
 //BA.debugLineNum = 115;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 116;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 117;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 118;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 119;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 120;BA.debugLine="ParseJSON(Job.GetString,Spinner2,spinnerMap2 )";
_parsejson(_job._getstring(),mostCurrent._spinner2,mostCurrent._spinnermap2);
 };
 //BA.debugLineNum = 122;BA.debugLine="If Job.JobName = \"delevery_type\" Then";
if ((_job._jobname).equals("delevery_type")) { 
 //BA.debugLineNum = 123;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 124;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 126;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 127;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 128;BA.debugLine="ParseJSON(Job.GetString,Spinner3,spinnerMap3 )";
_parsejson(_job._getstring(),mostCurrent._spinner3,mostCurrent._spinnermap3);
 };
 //BA.debugLineNum = 130;BA.debugLine="If Job.JobName = \"order_type\" Then";
if ((_job._jobname).equals("order_type")) { 
 //BA.debugLineNum = 131;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 132;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 134;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 135;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 136;BA.debugLine="ParseJSON(Job.GetString,Spinner4,spinnerMap4 )";
_parsejson(_job._getstring(),mostCurrent._spinner4,mostCurrent._spinnermap4);
 };
 //BA.debugLineNum = 138;BA.debugLine="If Job.JobName = \"get_city\" Then";
if ((_job._jobname).equals("get_city")) { 
 //BA.debugLineNum = 139;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 140;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 142;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 143;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 144;BA.debugLine="ParseJSON(Job.GetString,Spinner5,spinnerMap5 )";
_parsejson(_job._getstring(),mostCurrent._spinner5,mostCurrent._spinnermap5);
 };
 //BA.debugLineNum = 146;BA.debugLine="If Job.JobName = \"dos_ql\" Then";
if ((_job._jobname).equals("dos_ql")) { 
 //BA.debugLineNum = 147;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 148;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 149;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 150;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 151;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 152;BA.debugLine="ParseJSON(Job.GetString,Spinner5,spinnerMap5 )";
_parsejson(_job._getstring(),mostCurrent._spinner5,mostCurrent._spinnermap5);
 };
 }else {
 //BA.debugLineNum = 155;BA.debugLine="Log( Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("5720949",_job._errormessage,0);
 };
 //BA.debugLineNum = 157;BA.debugLine="Job.Release";
_job._release();
 } 
       catch (Exception e57) {
			processBA.setLastException(e57); //BA.debugLineNum = 159;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5720953",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _mymsg(String _msg) throws Exception{
smm.smmpromptdialogwrapper.promptdialogwrapper _prompt = null;
 //BA.debugLineNum = 343;BA.debugLine="Sub Mymsg(msg As String)";
 //BA.debugLineNum = 344;BA.debugLine="Try";
try { //BA.debugLineNum = 345;BA.debugLine="Dim prompt As PromptDialog";
_prompt = new smm.smmpromptdialogwrapper.promptdialogwrapper();
 //BA.debugLineNum = 346;BA.debugLine="prompt.Initialize(\"prompt\")";
_prompt.Initialize(mostCurrent.activityBA,"prompt");
 //BA.debugLineNum = 347;BA.debugLine="prompt.DialogType=prompt.DIALOG_TYPE_INFO";
_prompt.setDialogType(_prompt.getDIALOG_TYPE_INFO());
 //BA.debugLineNum = 348;BA.debugLine="prompt.Title=\"الفصول الاربعه\"";
_prompt.setTitle("الفصول الاربعه");
 //BA.debugLineNum = 349;BA.debugLine="prompt.AnimationEnable=True";
_prompt.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 350;BA.debugLine="prompt.ContentText=\" '\"& msg &\"'";
_prompt.setContentText(" '"+_msg+"'                      ");
 //BA.debugLineNum = 351;BA.debugLine="prompt.Show(\"موافق\",\"الغاء\")";
_prompt.Show("موافق","الغاء");
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 353;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51441802",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson(String _jsonstring,anywheresoftware.b4a.objects.SpinnerWrapper _sp,anywheresoftware.b4a.objects.collections.Map _spm) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
 //BA.debugLineNum = 163;BA.debugLine="Sub ParseJSON(jsonstring As String,sp As Spinner,s";
 //BA.debugLineNum = 164;BA.debugLine="Try";
try { //BA.debugLineNum = 165;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 166;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 167;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 168;BA.debugLine="spm.Initialize";
_spm.Initialize();
 //BA.debugLineNum = 169;BA.debugLine="sp.Add(\"اختر من القائمه\")";
_sp.Add("اختر من القائمه");
 //BA.debugLineNum = 170;BA.debugLine="spm.Put(\"اختر من القائمه\",0)";
_spm.Put((Object)("اختر من القائمه"),(Object)(0));
 //BA.debugLineNum = 171;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group8 = _root;
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_colroot = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group8.Get(index8)));
 //BA.debugLineNum = 172;BA.debugLine="sp.Add( colroot.Get(\"name\"))";
_sp.Add(BA.ObjectToString(_colroot.Get((Object)("name"))));
 //BA.debugLineNum = 173;BA.debugLine="spm.Put(colroot.Get(\"name\"),colroot.Get(\"id\"))";
_spm.Put(_colroot.Get((Object)("name")),_colroot.Get((Object)("id")));
 }
};
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 177;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5786446",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson2(String _jsonstring) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 181;BA.debugLine="Sub ParseJSON2(jsonstring As String )";
 //BA.debugLineNum = 182;BA.debugLine="Try";
try { //BA.debugLineNum = 183;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 184;BA.debugLine="jsonstring=jsonstring.Replace(\"[\",\"\").Replace(\"]";
_jsonstring = _jsonstring.replace("[","").replace("]","");
 //BA.debugLineNum = 185;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 186;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 187;BA.debugLine="root= parser.NextObject";
_root = _parser.NextObject();
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 190;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5851977",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Public tmr As Timer";
_tmr = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 7;BA.debugLine="Public order_date As String";
_order_date = "";
 //BA.debugLineNum = 8;BA.debugLine="Dim date_new  As String";
_date_new = "";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _prompt_promptclick() throws Exception{
 //BA.debugLineNum = 393;BA.debugLine="Sub prompt_promptclick";
 //BA.debugLineNum = 394;BA.debugLine="If rsl<>0 Then";
if ((mostCurrent._rsl).equals(BA.NumberToString(0)) == false) { 
 //BA.debugLineNum = 395;BA.debugLine="rsl=0";
mostCurrent._rsl = BA.NumberToString(0);
 //BA.debugLineNum = 396;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 297;BA.debugLine="Private Sub Spinner1_ItemClick (Position As Int, V";
 //BA.debugLineNum = 298;BA.debugLine="Try";
try { //BA.debugLineNum = 299;BA.debugLine="sender_id = spinnerMap.Get(Value)";
_sender_id = BA.ObjectToLongNumber(mostCurrent._spinnermap.Get(_value));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 301;BA.debugLine="sender_id =0";
_sender_id = (long) (0);
 //BA.debugLineNum = 302;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("5983045",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _spinner2_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Private Sub Spinner2_ItemClick (Position As Int, V";
 //BA.debugLineNum = 306;BA.debugLine="Try";
try { //BA.debugLineNum = 307;BA.debugLine="good_type_ = spinnerMap2.Get(Value)";
_good_type_ = BA.ObjectToLongNumber(mostCurrent._spinnermap2.Get(_value));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 309;BA.debugLine="good_type_ =0";
_good_type_ = (long) (0);
 //BA.debugLineNum = 310;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51048581",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _spinner3_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Private Sub Spinner3_ItemClick (Position As Int, V";
 //BA.debugLineNum = 317;BA.debugLine="Try";
try { //BA.debugLineNum = 318;BA.debugLine="delevery_type_ = spinnerMap3.Get(Value)";
_delevery_type_ = BA.ObjectToLongNumber(mostCurrent._spinnermap3.Get(_value));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 320;BA.debugLine="delevery_type_ =0";
_delevery_type_ = (long) (0);
 //BA.debugLineNum = 321;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51179653",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _spinner4_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 324;BA.debugLine="Private Sub Spinner4_ItemClick (Position As Int, V";
 //BA.debugLineNum = 325;BA.debugLine="Try";
try { //BA.debugLineNum = 326;BA.debugLine="order_type_ = spinnerMap4.Get(Value)";
_order_type_ = BA.ObjectToLongNumber(mostCurrent._spinnermap4.Get(_value));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 328;BA.debugLine="order_type_ =0";
_order_type_ = (long) (0);
 //BA.debugLineNum = 329;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51245189",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _spinner5_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Private Sub Spinner5_ItemClick (Position As Int, V";
 //BA.debugLineNum = 333;BA.debugLine="Try";
try { //BA.debugLineNum = 334;BA.debugLine="city_ = spinnerMap5.Get(Value)";
_city_ = BA.ObjectToLongNumber(mostCurrent._spinnermap5.Get(_value));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 336;BA.debugLine="city_ =0";
_city_ = (long) (0);
 //BA.debugLineNum = 337;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51310725",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return "";
}
public static String  _viewback_click() throws Exception{
 //BA.debugLineNum = 313;BA.debugLine="Sub ViewBack_Click";
 //BA.debugLineNum = 314;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
}
