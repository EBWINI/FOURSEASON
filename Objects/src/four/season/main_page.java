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

public class main_page extends Activity implements B4AActivity{
	public static main_page mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "four.season", "four.season.main_page");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main_page).");
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
		activityBA = new BA(this, layout, processBA, "four.season", "four.season.main_page");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "four.season.main_page", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main_page) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main_page) Resume **");
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
		return main_page.class;
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
            BA.LogInfo("** Activity (main_page) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main_page) Pause event (activity is not paused). **");
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
            main_page mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main_page) Resume **");
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
public static int _gviewtop = 0;
public static long _gid = 0L;
public static anywheresoftware.b4a.audio.SoundPoolWrapper _sp = null;
public static int _loadid1 = 0;
public static int _playid1 = 0;
public static int _loadid2 = 0;
public static int _playid2 = 0;
public static long _xx = 0L;
public static anywheresoftware.b4a.objects.Timer _tmr = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public b4a.example3.customlistview _customlistview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _viewdata = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public four.season.main _main = null;
public four.season.get_order _get_order = null;
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
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 34;BA.debugLine="Try";
try { //BA.debugLineNum = 35;BA.debugLine="SP.Initialize(4)";
_sp.Initialize((int) (4));
 //BA.debugLineNum = 36;BA.debugLine="LoadId1 = SP.Load(File.DirAssets, \"adma.mp3\")";
_loadid1 = _sp.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"adma.mp3");
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 38;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51900549",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 41;BA.debugLine="Activity.LoadLayout(\"MAIN_PAGE\")";
mostCurrent._activity.LoadLayout("MAIN_PAGE",mostCurrent.activityBA);
 //BA.debugLineNum = 43;BA.debugLine="tmr.Initialize(\"tmr\", 5000)";
_tmr.Initialize(processBA,"tmr",(long) (5000));
 //BA.debugLineNum = 44;BA.debugLine="tmr.Enabled = True";
_tmr.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 45;BA.debugLine="Try";
try { //BA.debugLineNum = 46;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 47;BA.debugLine="job.Initialize(\"get_requests\", Me)";
_job._initialize(processBA,"get_requests",main_page.getObject());
 //BA.debugLineNum = 48;BA.debugLine="Dim str As String =\"\"& Main.server &\"/get_request";
_str = ""+mostCurrent._main._server /*String*/ +"/get_requests?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 49;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e16) {
			processBA.setLastException(e16); //BA.debugLineNum = 51;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51900562",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 54;BA.debugLine="Button1.Width=48%x";
mostCurrent._button1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
 //BA.debugLineNum = 55;BA.debugLine="Button2.Width=25%x";
mostCurrent._button2.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA));
 //BA.debugLineNum = 56;BA.debugLine="Button3.Width=23%x";
mostCurrent._button3.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (23),mostCurrent.activityBA));
 //BA.debugLineNum = 57;BA.debugLine="Button3.Left=29%x";
mostCurrent._button3.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (29),mostCurrent.activityBA));
 //BA.debugLineNum = 58;BA.debugLine="Button1.Left=53%x";
mostCurrent._button1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (53),mostCurrent.activityBA));
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 101;BA.debugLine="Try";
try { } 
       catch (Exception e3) {
			processBA.setLastException(e3); //BA.debugLineNum = 104;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52162692",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 112;BA.debugLine="Try";
try { } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 115;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52162703",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 239;BA.debugLine="Private Sub Button1_Click";
 //BA.debugLineNum = 240;BA.debugLine="StartActivity(\"get_order\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("get_order"));
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Private Sub Button2_Click";
 //BA.debugLineNum = 243;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 244;BA.debugLine="StartActivity(\"MAIN\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("MAIN"));
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 246;BA.debugLine="Private Sub Button3_Click";
 //BA.debugLineNum = 247;BA.debugLine="StartActivity(\"my_profile\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("my_profile"));
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.B4XViewWrapper  _createitem(anywheresoftware.b4j.objects.SQL.ResultSetWrapper _crsr) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 228;BA.debugLine="Private Sub CreateItem(Crsr As JdbcResultSet) As B";
 //BA.debugLineNum = 229;BA.debugLine="Dim p As 	B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 230;BA.debugLine="p.SetLayoutAnimated(0,0,0,95%x, 50dip)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 231;BA.debugLine="p.LoadLayout(\"GridRow\")";
_p.LoadLayout("GridRow",mostCurrent.activityBA);
 //BA.debugLineNum = 232;BA.debugLine="If Crsr.ColumnCount >= 1 Then Label1.Text 	  = Cr";
if (_crsr.getColumnCount()>=1) { 
mostCurrent._label1.setText(BA.ObjectToCharSequence(_crsr.GetString(_crsr.GetColumnName((int) (0)))));};
 //BA.debugLineNum = 233;BA.debugLine="If Crsr.ColumnCount >= 2 Then Label2.Text       =";
if (_crsr.getColumnCount()>=2) { 
mostCurrent._label2.setText(BA.ObjectToCharSequence(_crsr.GetString(_crsr.GetColumnName((int) (1)))));};
 //BA.debugLineNum = 234;BA.debugLine="If Crsr.ColumnCount >= 3 Then Label3.Text";
if (_crsr.getColumnCount()>=3) { 
mostCurrent._label3.setText(BA.ObjectToCharSequence(_crsr.GetString(_crsr.GetColumnName((int) (2)))));};
 //BA.debugLineNum = 235;BA.debugLine="ViewData.Text=\"عرض\"";
mostCurrent._viewdata.setText(BA.ObjectToCharSequence("عرض"));
 //BA.debugLineNum = 236;BA.debugLine="ViewData.Tag      = Crsr.GetString(Crsr.GetColumn";
mostCurrent._viewdata.setTag((Object)(_crsr.GetString(_crsr.GetColumnName((int) (0)))));
 //BA.debugLineNum = 237;BA.debugLine="Return p";
if (true) return _p;
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return null;
}
public static void  _customlistview1_itemclick(int _index,String _p) throws Exception{
ResumableSub_CustomListView1_ItemClick rsub = new ResumableSub_CustomListView1_ItemClick(null,_index,_p);
rsub.resume(processBA, null);
}
public static class ResumableSub_CustomListView1_ItemClick extends BA.ResumableSub {
public ResumableSub_CustomListView1_ItemClick(four.season.main_page parent,int _index,String _p) {
this.parent = parent;
this._index = _index;
this._p = _p;
}
four.season.main_page parent;
int _index;
String _p;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 90;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 6;
this.catchState = 5;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 6;
this.catchState = 5;
 //BA.debugLineNum = 91;BA.debugLine="gID = p";
parent._gid = (long)(Double.parseDouble(_p));
 //BA.debugLineNum = 92;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 93;BA.debugLine="StartActivity(\"ViewDetails\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("ViewDetails"));
 if (true) break;

case 5:
//C
this.state = 6;
this.catchState = 0;
 //BA.debugLineNum = 95;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52097158",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 if (true) break;
if (true) break;

case 6:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private xui   As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 24;BA.debugLine="Private CustomListView1 As CustomListView";
mostCurrent._customlistview1 = new b4a.example3.customlistview();
 //BA.debugLineNum = 25;BA.debugLine="Private ViewData As Button";
mostCurrent._viewdata = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 126;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 127;BA.debugLine="Try";
try { //BA.debugLineNum = 128;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 129;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 130;BA.debugLine="If Job.JobName = \"get_requests\" Then";
if ((_job._jobname).equals("get_requests")) { 
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
 //BA.debugLineNum = 136;BA.debugLine="ParseJSON(Job.GetString )";
_parsejson(_job._getstring());
 };
 //BA.debugLineNum = 138;BA.debugLine="If Job.JobName = \"stop_noti\" Then";
if ((_job._jobname).equals("stop_noti")) { 
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
 //BA.debugLineNum = 144;BA.debugLine="ParseJSON2(Job.GetString )";
_parsejson2(_job._getstring());
 };
 //BA.debugLineNum = 147;BA.debugLine="If Job.JobName = \"check_not\" Then";
if ((_job._jobname).equals("check_not")) { 
 //BA.debugLineNum = 148;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 149;BA.debugLine="out = File.OpenOutput(File.DirRootExternal,Job";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 150;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 151;BA.debugLine="File.Delete(File.DirRootExternal,Job.Tag)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),BA.ObjectToString(_job._tag));
 //BA.debugLineNum = 152;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 153;BA.debugLine="ParseJSON3(Job.GetString )";
_parsejson3(_job._getstring());
 };
 }else {
 //BA.debugLineNum = 156;BA.debugLine="Log( Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("52359326",_job._errormessage,0);
 };
 //BA.debugLineNum = 158;BA.debugLine="Job.Release";
_job._release();
 } 
       catch (Exception e33) {
			processBA.setLastException(e33); //BA.debugLineNum = 160;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52359330",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson(String _jsonstring) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _itemid = "";
String _itemname = "";
String _itemprice = "";
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 198;BA.debugLine="Sub ParseJSON(jsonstring As String )";
 //BA.debugLineNum = 199;BA.debugLine="CustomListView1.Clear";
mostCurrent._customlistview1._clear();
 //BA.debugLineNum = 200;BA.debugLine="Try";
try { //BA.debugLineNum = 201;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 203;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 204;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 205;BA.debugLine="Try";
try { //BA.debugLineNum = 206;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group7 = _root;
final int groupLen7 = group7.getSize()
;int index7 = 0;
;
for (; index7 < groupLen7;index7++){
_colroot = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group7.Get(index7)));
 //BA.debugLineNum = 207;BA.debugLine="Dim itemid As String = colroot.Get(\"id\")";
_itemid = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 208;BA.debugLine="Dim itemname As String = colroot.Get(\"name\")";
_itemname = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 209;BA.debugLine="Dim itemprice As String = colroot.Get(\"city\")";
_itemprice = BA.ObjectToString(_colroot.Get((Object)("city")));
 //BA.debugLineNum = 210;BA.debugLine="Dim p As 	B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 211;BA.debugLine="p.SetLayoutAnimated(0,0,0,95%x, 50dip)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 212;BA.debugLine="p.LoadLayout(\"GridRow\")";
_p.LoadLayout("GridRow",mostCurrent.activityBA);
 //BA.debugLineNum = 213;BA.debugLine="Label1.Text 	  =  itemid";
mostCurrent._label1.setText(BA.ObjectToCharSequence(_itemid));
 //BA.debugLineNum = 214;BA.debugLine="Label2.Text       = itemname";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_itemname));
 //BA.debugLineNum = 215;BA.debugLine="Label3.Text       =  itemprice";
mostCurrent._label3.setText(BA.ObjectToCharSequence(_itemprice));
 //BA.debugLineNum = 216;BA.debugLine="ViewData.Text=\"عرض\"";
mostCurrent._viewdata.setText(BA.ObjectToCharSequence("عرض"));
 //BA.debugLineNum = 217;BA.debugLine="ViewData.Tag=itemid";
mostCurrent._viewdata.setTag((Object)(_itemid));
 //BA.debugLineNum = 218;BA.debugLine="Main.gID=itemid";
mostCurrent._main._gid /*long*/  = (long)(Double.parseDouble(_itemid));
 //BA.debugLineNum = 219;BA.debugLine="CustomListView1.Add(p, itemid)";
mostCurrent._customlistview1._add(_p,(Object)(_itemid));
 }
};
 } 
       catch (Exception e23) {
			processBA.setLastException(e23); //BA.debugLineNum = 222;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52555928",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 } 
       catch (Exception e26) {
			processBA.setLastException(e26); //BA.debugLineNum = 225;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52555931",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson2(String _jsonstring) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 164;BA.debugLine="Sub ParseJSON2(jsonstring As String )";
 //BA.debugLineNum = 165;BA.debugLine="Try";
try { //BA.debugLineNum = 166;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 167;BA.debugLine="jsonstring=jsonstring.Replace(\"[\",\"\").Replace(\"]";
_jsonstring = _jsonstring.replace("[","").replace("]","");
 //BA.debugLineNum = 168;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 169;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 170;BA.debugLine="root= parser.NextObject";
_root = _parser.NextObject();
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 172;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52424840",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _parsejson3(String _jsonstring) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.samples.httputils2.httpjob _job2 = null;
String _str2 = "";
 //BA.debugLineNum = 175;BA.debugLine="Sub ParseJSON3(jsonstring As String )";
 //BA.debugLineNum = 176;BA.debugLine="Try";
try { //BA.debugLineNum = 177;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 178;BA.debugLine="jsonstring=jsonstring.Replace(\"[\",\"\").Replace(\"]";
_jsonstring = _jsonstring.replace("[","").replace("]","");
 //BA.debugLineNum = 179;BA.debugLine="parser.Initialize(jsonstring)";
_parser.Initialize(_jsonstring);
 //BA.debugLineNum = 180;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 181;BA.debugLine="root= parser.NextObject";
_root = _parser.NextObject();
 //BA.debugLineNum = 182;BA.debugLine="If root.ContainsKey(\"status\") Then";
if (_root.ContainsKey((Object)("status"))) { 
 //BA.debugLineNum = 183;BA.debugLine="Msgbox(root.Get(\"status\"),\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_root.Get((Object)("status"))),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 185;BA.debugLine="xx=root.Get(\"status\")";
_xx = BA.ObjectToLongNumber(_root.Get((Object)("status")));
 //BA.debugLineNum = 186;BA.debugLine="If xx=1 Then";
if (_xx==1) { 
 //BA.debugLineNum = 187;BA.debugLine="PlayId1 = SP.Play(LoadId1, 1, 1, 1, 2, 1)";
_playid1 = _sp.Play(_loadid1,(float) (1),(float) (1),(int) (1),(int) (2),(float) (1));
 //BA.debugLineNum = 188;BA.debugLine="xx=0";
_xx = (long) (0);
 //BA.debugLineNum = 189;BA.debugLine="Dim job2 As HttpJob";
_job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 190;BA.debugLine="job2.Initialize(\"stop_noti\", Me)";
_job2._initialize(processBA,"stop_noti",main_page.getObject());
 //BA.debugLineNum = 191;BA.debugLine="Dim str2 As String =\"\"& Main.server &\"/stop_not";
_str2 = ""+mostCurrent._main._server /*String*/ +"/stop_noti?usrid="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 192;BA.debugLine="job2.Download(str2)";
_job2._download(_str2);
 };
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 195;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52490388",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private gViewTop As Int = 0		' Saves top of listv";
_gviewtop = (int) (0);
 //BA.debugLineNum = 14;BA.debugLine="Public gID As Long = 0";
_gid = (long) (0);
 //BA.debugLineNum = 17;BA.debugLine="Dim SP As SoundPool";
_sp = new anywheresoftware.b4a.audio.SoundPoolWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim LoadId1, PlayId1, LoadId2, PlayId2 As Int";
_loadid1 = 0;
_playid1 = 0;
_loadid2 = 0;
_playid2 = 0;
 //BA.debugLineNum = 19;BA.debugLine="Public xx As Long =0";
_xx = (long) (0);
 //BA.debugLineNum = 20;BA.debugLine="Public tmr As Timer";
_tmr = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _tmr_tick() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job3 = null;
String _str3 = "";
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
String _str = "";
 //BA.debugLineNum = 60;BA.debugLine="Sub tmr_Tick";
 //BA.debugLineNum = 61;BA.debugLine="Try";
try { //BA.debugLineNum = 62;BA.debugLine="Dim job3 As HttpJob";
_job3 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 63;BA.debugLine="job3.Initialize(\"check_not\", Me)";
_job3._initialize(processBA,"check_not",main_page.getObject());
 //BA.debugLineNum = 64;BA.debugLine="Dim str3 As String =\"\"& Main.server &\"/check_not?";
_str3 = ""+mostCurrent._main._server /*String*/ +"/check_not?user_id="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 65;BA.debugLine="job3.Download(str3)";
_job3._download(_str3);
 //BA.debugLineNum = 67;BA.debugLine="CustomListView1.Clear";
mostCurrent._customlistview1._clear();
 //BA.debugLineNum = 68;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 69;BA.debugLine="job.Initialize(\"get_requests\", Me)";
_job._initialize(processBA,"get_requests",main_page.getObject());
 //BA.debugLineNum = 70;BA.debugLine="Dim str As String =\"\"& Main.server &\"/get_request";
_str = ""+mostCurrent._main._server /*String*/ +"/get_requests?Driver_ID="+BA.NumberToString(mostCurrent._main._usrid /*long*/ );
 //BA.debugLineNum = 71;BA.debugLine="job.Download(str)";
_job._download(_str);
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 73;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51966093",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _viewdata_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
 //BA.debugLineNum = 79;BA.debugLine="Sub ViewData_Click";
 //BA.debugLineNum = 80;BA.debugLine="Try";
try { //BA.debugLineNum = 81;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 82;BA.debugLine="Main.gID = btn.Tag";
mostCurrent._main._gid /*long*/  = BA.ObjectToLongNumber(_btn.getTag());
 //BA.debugLineNum = 83;BA.debugLine="StartActivity(\"ViewDetails\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("ViewDetails"));
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 85;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("52031622",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
}
