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

public class my_profile extends Activity implements B4AActivity{
	public static my_profile mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "four.season", "four.season.my_profile");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (my_profile).");
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
		activityBA = new BA(this, layout, processBA, "four.season", "four.season.my_profile");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "four.season.my_profile", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (my_profile) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (my_profile) Resume **");
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
		return my_profile.class;
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
            BA.LogInfo("** Activity (my_profile) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (my_profile) Pause event (activity is not paused). **");
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
            my_profile mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (my_profile) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label15 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label17 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public four.season.main _main = null;
public four.season.get_order _get_order = null;
public four.season.main_page _main_page = null;
public four.season.viewdetails _viewdetails = null;
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
anywheresoftware.b4a.samples.httputils2.httpjob _job6 = null;
String _str6 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job7 = null;
String _str7 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job8 = null;
String _str8 = "";
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"profile\")";
mostCurrent._activity.LoadLayout("profile",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="Try";
try { //BA.debugLineNum = 32;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 33;BA.debugLine="job.Initialize(\"get_driver_current\", Me)";
_job._initialize(processBA,"get_driver_current",my_profile.getObject());
 //BA.debugLineNum = 34;BA.debugLine="Dim str As String =\"\"& Main.server &\"/get_driver_";
_str = ""+mostCurrent._main._server /*String*/ +"/get_driver_current?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 35;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 37;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866632",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 39;BA.debugLine="Try";
try { //BA.debugLineNum = 40;BA.debugLine="Dim job2 As HttpJob";
_job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 41;BA.debugLine="job2.Initialize(\"get_driver_end_today\", Me)";
_job2._initialize(processBA,"get_driver_end_today",my_profile.getObject());
 //BA.debugLineNum = 42;BA.debugLine="Dim str2 As String =\"\"& Main.server &\"/get_driver";
_str2 = ""+mostCurrent._main._server /*String*/ +"/get_driver_end_today?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 43;BA.debugLine="job2.Download(str2)";
_job2._download(_str2);
 } 
       catch (Exception e16) {
			processBA.setLastException(e16); //BA.debugLineNum = 45;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866640",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 47;BA.debugLine="Try";
try { //BA.debugLineNum = 48;BA.debugLine="Dim job3 As HttpJob";
_job3 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 49;BA.debugLine="job3.Initialize(\"get_driver_end_month\", Me)";
_job3._initialize(processBA,"get_driver_end_month",my_profile.getObject());
 //BA.debugLineNum = 50;BA.debugLine="Dim str3 As String =\"\"& Main.server &\"/get_driver";
_str3 = ""+mostCurrent._main._server /*String*/ +"/get_driver_end_month?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 51;BA.debugLine="job3.Download(str3)";
_job3._download(_str3);
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 53;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866648",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 55;BA.debugLine="Try";
try { //BA.debugLineNum = 56;BA.debugLine="Dim job4 As HttpJob";
_job4 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 57;BA.debugLine="job4.Initialize(\"get_driver_pay_day\", Me)";
_job4._initialize(processBA,"get_driver_pay_day",my_profile.getObject());
 //BA.debugLineNum = 58;BA.debugLine="Dim str4 As String =\"\"& Main.server &\"/get_driver";
_str4 = ""+mostCurrent._main._server /*String*/ +"/get_driver_pay_day?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 59;BA.debugLine="job4.Download(str4)";
_job4._download(_str4);
 } 
       catch (Exception e32) {
			processBA.setLastException(e32); //BA.debugLineNum = 61;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866656",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 63;BA.debugLine="Try";
try { //BA.debugLineNum = 64;BA.debugLine="Dim job5 As HttpJob";
_job5 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 65;BA.debugLine="job5.Initialize(\"get_driver_pay_month\", Me)";
_job5._initialize(processBA,"get_driver_pay_month",my_profile.getObject());
 //BA.debugLineNum = 66;BA.debugLine="Dim str5 As String =\"\"& Main.server &\"/get_driver";
_str5 = ""+mostCurrent._main._server /*String*/ +"/get_driver_pay_month?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 67;BA.debugLine="job5.Download(str5)";
_job5._download(_str5);
 } 
       catch (Exception e40) {
			processBA.setLastException(e40); //BA.debugLineNum = 69;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866664",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 71;BA.debugLine="Try";
try { } 
       catch (Exception e44) {
			processBA.setLastException(e44); //BA.debugLineNum = 73;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866668",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 75;BA.debugLine="Try";
try { //BA.debugLineNum = 76;BA.debugLine="Dim job6 As HttpJob";
_job6 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 77;BA.debugLine="job6.Initialize(\"get_driver_pay_delev_month\", Me)";
_job6._initialize(processBA,"get_driver_pay_delev_month",my_profile.getObject());
 //BA.debugLineNum = 78;BA.debugLine="Dim str6 As String =\"\"& Main.server &\"/get_driver";
_str6 = ""+mostCurrent._main._server /*String*/ +"/get_driver_pay_delev_month?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 79;BA.debugLine="job6.Download(str6)";
_job6._download(_str6);
 } 
       catch (Exception e52) {
			processBA.setLastException(e52); //BA.debugLineNum = 81;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866676",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 83;BA.debugLine="Try";
try { //BA.debugLineNum = 84;BA.debugLine="Dim job7 As HttpJob";
_job7 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 85;BA.debugLine="job7.Initialize(\"get_driver_pay_delev_day\", Me)";
_job7._initialize(processBA,"get_driver_pay_delev_day",my_profile.getObject());
 //BA.debugLineNum = 86;BA.debugLine="Dim str7 As String =\"\"& Main.server &\"/get_driver";
_str7 = ""+mostCurrent._main._server /*String*/ +"/get_driver_pay_delev_day?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 87;BA.debugLine="job7.Download(str7)";
_job7._download(_str7);
 } 
       catch (Exception e60) {
			processBA.setLastException(e60); //BA.debugLineNum = 89;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866684",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 91;BA.debugLine="Try";
try { //BA.debugLineNum = 92;BA.debugLine="Dim job8 As HttpJob";
_job8 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 93;BA.debugLine="job8.Initialize(\"get_company_orders\", Me)";
_job8._initialize(processBA,"get_company_orders",my_profile.getObject());
 //BA.debugLineNum = 94;BA.debugLine="Dim str8 As String =\"\"& Main.server &\"/get_compan";
_str8 = ""+mostCurrent._main._server /*String*/ +"/get_company_orders?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 95;BA.debugLine="job8.Download(str8)";
_job8._download(_str8);
 } 
       catch (Exception e68) {
			processBA.setLastException(e68); //BA.debugLineNum = 97;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53866692",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 99;BA.debugLine="Panel1.Width=45%x";
mostCurrent._panel1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 100;BA.debugLine="Panel2.Width=45%x";
mostCurrent._panel2.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 101;BA.debugLine="Panel2.Left=52%x";
mostCurrent._panel2.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 102;BA.debugLine="Panel3.Width=45%x";
mostCurrent._panel3.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 103;BA.debugLine="Panel4.Width=45%x";
mostCurrent._panel4.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 104;BA.debugLine="Panel4.Left=52%x";
mostCurrent._panel4.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 105;BA.debugLine="Panel5.Width=45%x";
mostCurrent._panel5.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 106;BA.debugLine="Panel6.Width=45%x";
mostCurrent._panel6.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 107;BA.debugLine="Panel6.Left=52%x";
mostCurrent._panel6.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 108;BA.debugLine="Panel7.Width=45%x";
mostCurrent._panel7.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 109;BA.debugLine="Panel8.Width=45%x";
mostCurrent._panel8.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 110;BA.debugLine="Panel8.Left=52%x";
mostCurrent._panel8.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 215;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 213;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Label15 As Label";
mostCurrent._label15 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Panel4 As Panel";
mostCurrent._panel4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private Panel5 As Panel";
mostCurrent._panel5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Panel6 As Panel";
mostCurrent._panel6 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private Panel7 As Panel";
mostCurrent._panel7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Panel8 As Panel";
mostCurrent._panel8 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label17 As Label";
mostCurrent._label17 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 122;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 123;BA.debugLine="Try";
try { //BA.debugLineNum = 124;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 125;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 126;BA.debugLine="If Job.JobName = \"get_driver_current\" Then";
if ((_job._jobname).equals("get_driver_current")) { 
 //BA.debugLineNum = 127;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 128;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 129;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 131;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 132;BA.debugLine="ParseJSON(Job.GetString,Label2 )";
_parsejson(_job._getstring(),mostCurrent._label2);
 };
 //BA.debugLineNum = 134;BA.debugLine="If Job.JobName = \"get_driver_end_today\" Then";
if ((_job._jobname).equals("get_driver_end_today")) { 
 //BA.debugLineNum = 135;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 136;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 138;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 139;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 140;BA.debugLine="ParseJSON(Job.GetString,Label4)";
_parsejson(_job._getstring(),mostCurrent._label4);
 };
 //BA.debugLineNum = 142;BA.debugLine="If Job.JobName = \"get_driver_end_month\" Then";
if ((_job._jobname).equals("get_driver_end_month")) { 
 //BA.debugLineNum = 143;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 144;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 146;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 147;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 148;BA.debugLine="ParseJSON(Job.GetString,Label8)";
_parsejson(_job._getstring(),mostCurrent._label8);
 };
 //BA.debugLineNum = 150;BA.debugLine="If Job.JobName = \"get_driver_pay_day\" Then";
if ((_job._jobname).equals("get_driver_pay_day")) { 
 //BA.debugLineNum = 151;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 152;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 153;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 154;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 155;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 156;BA.debugLine="ParseJSON(Job.GetString,Label6)";
_parsejson(_job._getstring(),mostCurrent._label6);
 };
 //BA.debugLineNum = 158;BA.debugLine="If Job.JobName = \"get_driver_pay_month\" Then";
if ((_job._jobname).equals("get_driver_pay_month")) { 
 //BA.debugLineNum = 159;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 160;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 161;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 162;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 163;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 164;BA.debugLine="ParseJSON(Job.GetString,Label10)";
_parsejson(_job._getstring(),mostCurrent._label10);
 };
 //BA.debugLineNum = 166;BA.debugLine="If Job.JobName = \"get_driver_pay_delev_month\" T";
if ((_job._jobname).equals("get_driver_pay_delev_month")) { 
 //BA.debugLineNum = 167;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 168;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 170;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 171;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 172;BA.debugLine="ParseJSON(Job.GetString,Label15)";
_parsejson(_job._getstring(),mostCurrent._label15);
 };
 //BA.debugLineNum = 174;BA.debugLine="If Job.JobName = \"get_driver_pay_delev_day\" The";
if ((_job._jobname).equals("get_driver_pay_delev_day")) { 
 //BA.debugLineNum = 175;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 176;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 177;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 178;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 179;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 180;BA.debugLine="ParseJSON(Job.GetString,Label12)";
_parsejson(_job._getstring(),mostCurrent._label12);
 };
 //BA.debugLineNum = 182;BA.debugLine="If Job.JobName = \"get_company_orders\" Then";
if ((_job._jobname).equals("get_company_orders")) { 
 //BA.debugLineNum = 183;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 184;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 185;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 186;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 187;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 188;BA.debugLine="ParseJSON(Job.GetString,Label13)";
_parsejson(_job._getstring(),mostCurrent._label13);
 };
 }else {
 //BA.debugLineNum = 191;BA.debugLine="Log( Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("53932229",_job._errormessage,0);
 };
 //BA.debugLineNum = 193;BA.debugLine="Job.Release";
_job._release();
 } 
       catch (Exception e73) {
			processBA.setLastException(e73); //BA.debugLineNum = 195;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53932233",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson(String _jsonstring,anywheresoftware.b4a.objects.LabelWrapper _lbl) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 199;BA.debugLine="Sub ParseJSON(jsonstring As String,lbl As Label )";
 //BA.debugLineNum = 200;BA.debugLine="Try";
try { //BA.debugLineNum = 201;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 202;BA.debugLine="jsonstring=jsonstring.Replace(\"[\",\"\").Replace(\"]";
_jsonstring = _jsonstring.replace("[","").replace("]","");
 //BA.debugLineNum = 203;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 204;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 205;BA.debugLine="root= parser.NextObject";
_root = _parser.NextObject();
 //BA.debugLineNum = 206;BA.debugLine="If root.ContainsKey(\"cnt\") Then";
if (_root.ContainsKey((Object)("cnt"))) { 
 //BA.debugLineNum = 207;BA.debugLine="lbl.Text =root.Get(\"cnt\")";
_lbl.setText(BA.ObjectToCharSequence(_root.Get((Object)("cnt"))));
 };
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 210;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53997707",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
