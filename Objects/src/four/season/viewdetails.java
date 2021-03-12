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

public class viewdetails extends Activity implements B4AActivity{
	public static viewdetails mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "four.season", "four.season.viewdetails");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (viewdetails).");
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
		activityBA = new BA(this, layout, processBA, "four.season", "four.season.viewdetails");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "four.season.viewdetails", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (viewdetails) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (viewdetails) Resume **");
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
		return viewdetails.class;
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
            BA.LogInfo("** Activity (viewdetails) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (viewdetails) Pause event (activity is not paused). **");
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
            viewdetails mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (viewdetails) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _viewback = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.StringUtils _su = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public static long _price_new = 0L;
public static long _price_new_d = 0L;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public four.season.main _main = null;
public four.season.get_order _get_order = null;
public four.season.main_page _main_page = null;
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
 //BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"ViewDetails\")";
mostCurrent._activity.LoadLayout("ViewDetails",mostCurrent.activityBA);
 //BA.debugLineNum = 27;BA.debugLine="Try";
try { //BA.debugLineNum = 28;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 29;BA.debugLine="job.Initialize(\"get_requests\", Me)";
_job._initialize(processBA,"get_requests",viewdetails.getObject());
 //BA.debugLineNum = 30;BA.debugLine="Dim str As String =\"\"& Main.server &\"/request_det";
_str = ""+mostCurrent._main._server /*String*/ +"/request_details?order_id="+BA.NumberToString(mostCurrent._main._gid /*long*/ );
 //BA.debugLineNum = 31;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 33;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53080200",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 402;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 399;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
double _full_price = 0;
String _str = "";
smm.smmcolordialogwrapper.colordialogwrapper _colordialog = null;
 //BA.debugLineNum = 408;BA.debugLine="Private Sub Button1_Click";
 //BA.debugLineNum = 409;BA.debugLine="Dim full_price As Double=price_new+price_new_d";
_full_price = _price_new+_price_new_d;
 //BA.debugLineNum = 410;BA.debugLine="Dim str As String =\" سعر البضاعه هو \" & price_new";
_str = " سعر البضاعه هو "+BA.NumberToString(_price_new)+" رسوم التوصيل هي "+BA.NumberToString(_price_new_d)+" مجموع المبلغ المستحق هو "+BA.NumberToString(_full_price)+" دينار اردني ";
 //BA.debugLineNum = 412;BA.debugLine="Dim colordialog As ColorDialog";
_colordialog = new smm.smmcolordialogwrapper.colordialogwrapper();
 //BA.debugLineNum = 413;BA.debugLine="colordialog.Initialize(\"dialog\")";
_colordialog.Initialize(mostCurrent.activityBA,"dialog");
 //BA.debugLineNum = 414;BA.debugLine="colordialog.Color=Colors.RGB(65,170,200)";
_colordialog.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (65),(int) (170),(int) (200)));
 //BA.debugLineNum = 415;BA.debugLine="colordialog.AnimationEnable=True";
_colordialog.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 416;BA.debugLine="colordialog.ContentText=str'\"        الفصول الارب";
_colordialog.setContentText(_str);
 //BA.debugLineNum = 417;BA.debugLine="colordialog.Title=\"سيتم تسليم الطلب\"";
_colordialog.setTitle("سيتم تسليم الطلب");
 //BA.debugLineNum = 419;BA.debugLine="colordialog.Show(\"نعم\",\"لا\")";
_colordialog.Show("نعم","لا");
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _dialog_click(String _result) throws Exception{
long _t = 0L;
String _order_date = "";
String _str_sql1 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
String _str = "";
double _full_price = 0;
smm.smmpromptdialogwrapper.promptdialogwrapper _prompt = null;
 //BA.debugLineNum = 421;BA.debugLine="Sub dialog_click(result As String)";
 //BA.debugLineNum = 422;BA.debugLine="Log(result)";
anywheresoftware.b4a.keywords.Common.LogImpl("53604481",_result,0);
 //BA.debugLineNum = 423;BA.debugLine="If result=\"نعم\" Then";
if ((_result).equals("نعم")) { 
 //BA.debugLineNum = 424;BA.debugLine="DateTime.DateFormat = \"yyyy/MM/dd hh:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy/MM/dd hh:mm:ss");
 //BA.debugLineNum = 425;BA.debugLine="Dim t As Long";
_t = 0L;
 //BA.debugLineNum = 426;BA.debugLine="t = DateTime.DateParse(DateTime.Date(DateTime.no";
_t = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 427;BA.debugLine="DateTime.DateFormat = \"yyyy/MM/dd hh:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy/MM/dd hh:mm:ss");
 //BA.debugLineNum = 428;BA.debugLine="Dim order_date As String";
_order_date = "";
 //BA.debugLineNum = 429;BA.debugLine="order_date = DateTime.Date(t)";
_order_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(_t);
 //BA.debugLineNum = 430;BA.debugLine="Dim str_sql1 As String";
_str_sql1 = "";
 //BA.debugLineNum = 431;BA.debugLine="str_sql1=\"UPDATE [requests]   SET [status] = 47,";
_str_sql1 = "UPDATE [requests]   SET [status] = 47,[done_date]='"+_order_date+"' where id ="+BA.NumberToString(mostCurrent._main._gid /*long*/ )+" ";
 //BA.debugLineNum = 433;BA.debugLine="Try";
try { //BA.debugLineNum = 434;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 435;BA.debugLine="job.Initialize(\"dos_ql\", Me)";
_job._initialize(processBA,"dos_ql",viewdetails.getObject());
 //BA.debugLineNum = 436;BA.debugLine="Dim str As String =\"\"& Main.server &\"/update_req";
_str = ""+mostCurrent._main._server /*String*/ +"/update_req?id="+BA.NumberToString(mostCurrent._main._gid /*long*/ );
 //BA.debugLineNum = 437;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); //BA.debugLineNum = 439;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53604498",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 441;BA.debugLine="Dim full_price As Double=price_new+price_new_d";
_full_price = _price_new+_price_new_d;
 //BA.debugLineNum = 442;BA.debugLine="Dim str As String =\" سعر البضاعه هو \" & price_ne";
_str = " سعر البضاعه هو "+BA.NumberToString(_price_new)+" رسوم التوصيل هي "+BA.NumberToString(_price_new_d)+" مجموع المبلغ المستحق هو "+BA.NumberToString(_full_price)+" دينار اردني ";
 //BA.debugLineNum = 443;BA.debugLine="Dim prompt As PromptDialog";
_prompt = new smm.smmpromptdialogwrapper.promptdialogwrapper();
 //BA.debugLineNum = 444;BA.debugLine="prompt.Initialize(\"prompt\")";
_prompt.Initialize(mostCurrent.activityBA,"prompt");
 //BA.debugLineNum = 445;BA.debugLine="prompt.DialogType=prompt.DIALOG_TYPE_SUCCESS";
_prompt.setDialogType(_prompt.getDIALOG_TYPE_SUCCESS());
 //BA.debugLineNum = 446;BA.debugLine="prompt.Title=\"الفصول الاربعه\"";
_prompt.setTitle("الفصول الاربعه");
 //BA.debugLineNum = 447;BA.debugLine="prompt.AnimationEnable=True";
_prompt.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 448;BA.debugLine="prompt.ContentText=\" تمت عملية تسليم الطلب للعمي";
_prompt.setContentText(" تمت عملية تسليم الطلب للعميل بنجاح ");
 //BA.debugLineNum = 449;BA.debugLine="prompt.Show(\"تمت العمليه\",\"الغاء\")";
_prompt.Show("تمت العمليه","الغاء");
 }else {
 //BA.debugLineNum = 451;BA.debugLine="Dim prompt As PromptDialog";
_prompt = new smm.smmpromptdialogwrapper.promptdialogwrapper();
 //BA.debugLineNum = 452;BA.debugLine="prompt.Initialize(\"prompt\")";
_prompt.Initialize(mostCurrent.activityBA,"prompt");
 //BA.debugLineNum = 453;BA.debugLine="prompt.DialogType=prompt.DIALOG_TYPE_WARNING";
_prompt.setDialogType(_prompt.getDIALOG_TYPE_WARNING());
 //BA.debugLineNum = 454;BA.debugLine="prompt.Title=\"الفصول الاربعه\"";
_prompt.setTitle("الفصول الاربعه");
 //BA.debugLineNum = 455;BA.debugLine="prompt.AnimationEnable=True";
_prompt.setAnimationEnable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 456;BA.debugLine="prompt.ContentText=\" تم الغاء تسليم الطلب";
_prompt.setContentText(" تم الغاء تسليم الطلب                         ");
 //BA.debugLineNum = 457;BA.debugLine="prompt.Show(\"تمت العمليه\",\"الغاء\")";
_prompt.Show("تمت العمليه","الغاء");
 };
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private ViewBack As Button";
mostCurrent._viewback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private su As StringUtils";
mostCurrent._su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 18;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Public price_new As Long";
_price_new = 0L;
 //BA.debugLineNum = 22;BA.debugLine="Public price_new_d As Long";
_price_new_d = 0L;
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 36;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 37;BA.debugLine="Try";
try { //BA.debugLineNum = 38;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 39;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 40;BA.debugLine="If Job.JobName = \"get_requests\" Then";
if ((_job._jobname).equals("get_requests")) { 
 //BA.debugLineNum = 41;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 42;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 43;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 44;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 45;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 46;BA.debugLine="ParseJSON(Job.GetString )";
_parsejson(_job._getstring());
 };
 //BA.debugLineNum = 48;BA.debugLine="If Job.JobName = \"dos_ql\" Then";
if ((_job._jobname).equals("dos_ql")) { 
 //BA.debugLineNum = 49;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 50;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 53;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 54;BA.debugLine="ParseJSON2(Job.GetString )";
_parsejson2(_job._getstring());
 };
 }else {
 //BA.debugLineNum = 57;BA.debugLine="Log( Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("53145749",_job._errormessage,0);
 };
 //BA.debugLineNum = 59;BA.debugLine="Job.Release";
_job._release();
 } 
       catch (Exception e25) {
			processBA.setLastException(e25); //BA.debugLineNum = 61;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53145753",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson(String _jsonstring) throws Exception{
int _inttop = 0;
int _i = 0;
long _totheight = 0L;
long _myheight = 0L;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.EditTextWrapper _et = null;
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
 //BA.debugLineNum = 65;BA.debugLine="Sub ParseJSON(jsonstring As String )";
 //BA.debugLineNum = 66;BA.debugLine="Dim intTop As Int";
_inttop = 0;
 //BA.debugLineNum = 67;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 68;BA.debugLine="Dim totHeight As Long = 0";
_totheight = (long) (0);
 //BA.debugLineNum = 69;BA.debugLine="Dim myHeight As Long = 40dip";
_myheight = (long) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 70;BA.debugLine="Try";
try { //BA.debugLineNum = 71;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 72;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 73;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 74;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group9 = _root;
final int groupLen9 = group9.getSize()
;int index9 = 0;
;
for (; index9 < groupLen9;index9++){
_colroot = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group9.Get(index9)));
 //BA.debugLineNum = 75;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 78;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 79;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 80;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 81;BA.debugLine="l.Text = \"رقم الطلب\"";
_l.setText(BA.ObjectToCharSequence("رقم الطلب"));
 //BA.debugLineNum = 82;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 83;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 84;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 85;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 86;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 87;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 88;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 89;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 90;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 91;BA.debugLine="et.Text = colroot.Get(\"req_id\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("req_id"))));
 //BA.debugLineNum = 92;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 93;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 94;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 95;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 96;BA.debugLine="Dim l1 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="l1.Initialize(\"\")";
_l1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 99;BA.debugLine="l1.TextSize = Label1.TextSize";
_l1.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 100;BA.debugLine="l1.Typeface = Label1.Typeface";
_l1.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 101;BA.debugLine="l1.Gravity = Label1.Gravity";
_l1.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 102;BA.debugLine="l1.Text = \"اسم العميل\"";
_l1.setText(BA.ObjectToCharSequence("اسم العميل"));
 //BA.debugLineNum = 103;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 104;BA.debugLine="ScrollView1.Panel.AddView(l1, EditText1.Width ,";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l1.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 105;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 106;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 107;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 108;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 109;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 110;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 111;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="et.Text = colroot.Get(\"agent\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("agent"))));
 //BA.debugLineNum = 113;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 114;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 115;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 116;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 117;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 118;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 120;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 121;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 122;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 123;BA.debugLine="l.Text = \"هاتف العميل\"";
_l.setText(BA.ObjectToCharSequence("هاتف العميل"));
 //BA.debugLineNum = 124;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 125;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 126;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 127;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 128;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 129;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 130;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 131;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 132;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="et.Text = colroot.Get(\"phone\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("phone"))));
 //BA.debugLineNum = 134;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 135;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 136;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 137;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 138;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 139;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 141;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 142;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 143;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 144;BA.debugLine="l.Text = \"المحافظه\"";
_l.setText(BA.ObjectToCharSequence("المحافظه"));
 //BA.debugLineNum = 145;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 146;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 147;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 148;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 149;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 150;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 151;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 152;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 153;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 154;BA.debugLine="et.Text = colroot.Get(\"city\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("city"))));
 //BA.debugLineNum = 155;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 156;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 157;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 158;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 159;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 160;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 162;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 163;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 164;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 165;BA.debugLine="l.Text = \"تاريخ التسليم\"";
_l.setText(BA.ObjectToCharSequence("تاريخ التسليم"));
 //BA.debugLineNum = 166;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 167;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 168;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 169;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 170;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 171;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 172;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 173;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 174;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 175;BA.debugLine="et.Text = colroot.Get(\"dte\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("dte"))));
 //BA.debugLineNum = 176;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 177;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 178;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 179;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 180;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 181;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 183;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 184;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 185;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 186;BA.debugLine="l.Text = \"نوع التوصيل\"";
_l.setText(BA.ObjectToCharSequence("نوع التوصيل"));
 //BA.debugLineNum = 187;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 188;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 189;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 190;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 191;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 192;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 193;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 194;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 195;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 196;BA.debugLine="et.Text = colroot.Get(\"delivery_type\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("delivery_type"))));
 //BA.debugLineNum = 197;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 198;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 199;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 200;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 201;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 202;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 204;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 205;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 206;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 207;BA.debugLine="l.Text = \"المرسل\"";
_l.setText(BA.ObjectToCharSequence("المرسل"));
 //BA.debugLineNum = 208;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 209;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 210;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 211;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 212;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 213;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 214;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 215;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 216;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="et.Text = colroot.Get(\"sender\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("sender"))));
 //BA.debugLineNum = 218;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 219;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 220;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 221;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 222;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 223;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 225;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 226;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 227;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 228;BA.debugLine="l.Text = \"هاتف المرسل\"";
_l.setText(BA.ObjectToCharSequence("هاتف المرسل"));
 //BA.debugLineNum = 229;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 230;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 231;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 232;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 233;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 234;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 235;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 236;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 237;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="et.Text = colroot.Get(\"phone2\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("phone2"))));
 //BA.debugLineNum = 239;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 240;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 241;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 242;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 243;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 244;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 246;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 247;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 248;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 249;BA.debugLine="l.Text = \"الوزن\"";
_l.setText(BA.ObjectToCharSequence("الوزن"));
 //BA.debugLineNum = 250;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 251;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 252;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 253;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 254;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 255;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 256;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 257;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 258;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="et.Text = colroot.Get(\"weght\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("weght"))));
 //BA.debugLineNum = 260;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 261;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 262;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 263;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 264;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 265;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 267;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 268;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 269;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 270;BA.debugLine="l.Text = \"السعر\"";
_l.setText(BA.ObjectToCharSequence("السعر"));
 //BA.debugLineNum = 271;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 272;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 273;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 274;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 275;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 276;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 277;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 278;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 279;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="et.Text = colroot.Get(\"price\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("price"))));
 //BA.debugLineNum = 281;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 282;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 283;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 284;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 285;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 286;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 288;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 289;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 290;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 291;BA.debugLine="l.Text = \"سعر التوصيل\"";
_l.setText(BA.ObjectToCharSequence("سعر التوصيل"));
 //BA.debugLineNum = 292;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 293;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 294;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 295;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 296;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 297;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 298;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 299;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 300;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="et.Text = colroot.Get(\"price2\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("price2"))));
 //BA.debugLineNum = 302;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 303;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 304;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 305;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 306;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 307;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 309;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 310;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 311;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 312;BA.debugLine="l.Text = \"السائق\"";
_l.setText(BA.ObjectToCharSequence("السائق"));
 //BA.debugLineNum = 313;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 314;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 315;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 316;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 317;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 318;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 319;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 320;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 321;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 322;BA.debugLine="et.Text = colroot.Get(\"driver\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("driver"))));
 //BA.debugLineNum = 323;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 324;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 325;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 326;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 327;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 328;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 330;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 331;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 332;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 333;BA.debugLine="l.Text = \"الحاله\"";
_l.setText(BA.ObjectToCharSequence("الحاله"));
 //BA.debugLineNum = 334;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 335;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 336;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 337;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 338;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 339;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 340;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 341;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 342;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 343;BA.debugLine="et.Text = colroot.Get(\"status\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("status"))));
 //BA.debugLineNum = 344;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 345;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 346;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 347;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 348;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 349;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 351;BA.debugLine="l.TextSize = Label1.TextSize";
_l.setTextSize(mostCurrent._label1.getTextSize());
 //BA.debugLineNum = 352;BA.debugLine="l.Typeface = Label1.Typeface";
_l.setTypeface(mostCurrent._label1.getTypeface());
 //BA.debugLineNum = 353;BA.debugLine="l.Gravity = Label1.Gravity";
_l.setGravity(mostCurrent._label1.getGravity());
 //BA.debugLineNum = 354;BA.debugLine="l.Text = \"ملاحظات\"";
_l.setText(BA.ObjectToCharSequence("ملاحظات"));
 //BA.debugLineNum = 355;BA.debugLine="intTop = totHeight";
_inttop = (int) (_totheight);
 //BA.debugLineNum = 356;BA.debugLine="ScrollView1.Panel.AddView(l, EditText1.Width ,i";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_l.getObject()),mostCurrent._edittext1.getWidth(),_inttop,mostCurrent._label1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 357;BA.debugLine="Dim et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 358;BA.debugLine="et.Initialize(\"\")";
_et.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 359;BA.debugLine="et.TextSize = EditText1.TextSize";
_et.setTextSize(mostCurrent._edittext1.getTextSize());
 //BA.debugLineNum = 360;BA.debugLine="et.Gravity = EditText1.Gravity";
_et.setGravity(mostCurrent._edittext1.getGravity());
 //BA.debugLineNum = 361;BA.debugLine="et.Width = EditText1.Width";
_et.setWidth(mostCurrent._edittext1.getWidth());
 //BA.debugLineNum = 362;BA.debugLine="et.Typeface = l.Typeface";
_et.setTypeface(_l.getTypeface());
 //BA.debugLineNum = 363;BA.debugLine="et.Enabled=False";
_et.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 364;BA.debugLine="et.Text = colroot.Get(\"notes\")";
_et.setText(BA.ObjectToCharSequence(_colroot.Get((Object)("notes"))));
 //BA.debugLineNum = 365;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 366;BA.debugLine="et.Background = p.GetResourceDrawable(17301528)";
_et.setBackground(_p.GetResourceDrawable((int) (17301528)));
 //BA.debugLineNum = 367;BA.debugLine="ScrollView1.Panel.AddView(et,0dip , intTop,Edit";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_et.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_inttop,mostCurrent._edittext1.getWidth(),(int) (_myheight));
 //BA.debugLineNum = 368;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 //BA.debugLineNum = 369;BA.debugLine="price_new=colroot.Get(\"price\")";
_price_new = BA.ObjectToLongNumber(_colroot.Get((Object)("price")));
 //BA.debugLineNum = 370;BA.debugLine="price_new_d=colroot.Get(\"price2\")";
_price_new_d = BA.ObjectToLongNumber(_colroot.Get((Object)("price2")));
 //BA.debugLineNum = 371;BA.debugLine="totHeight = totHeight + myHeight";
_totheight = (long) (_totheight+_myheight);
 }
};
 } 
       catch (Exception e295) {
			processBA.setLastException(e295); //BA.debugLineNum = 384;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53211583",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson2(String _jsonstring) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 387;BA.debugLine="Sub ParseJSON2(jsonstring As String )";
 //BA.debugLineNum = 388;BA.debugLine="Try";
try { //BA.debugLineNum = 389;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 390;BA.debugLine="jsonstring=jsonstring.Replace(\"[\",\"\").Replace(\"]";
_jsonstring = _jsonstring.replace("[","").replace("]","");
 //BA.debugLineNum = 391;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 392;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 393;BA.debugLine="root= parser.NextObject";
_root = _parser.NextObject();
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 396;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("53276809",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 398;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _prompt_promptclick() throws Exception{
 //BA.debugLineNum = 460;BA.debugLine="Sub prompt_promptclick";
 //BA.debugLineNum = 461;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return "";
}
public static String  _viewback_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub ViewBack_Click";
 //BA.debugLineNum = 406;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 407;BA.debugLine="End Sub";
return "";
}
}
