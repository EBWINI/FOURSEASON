package four.season;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class player extends  android.app.Service{
	public static class player_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (player) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, player.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static player mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return player.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "four.season", "four.season.player");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "four.season.player", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (player) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (player) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (player) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (false) {
            BA.LogInfo("** Service (player) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (player) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static int _nid = 0;
public static anywheresoftware.b4a.phone.Phone.PhoneWakeState _lock = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static b4a.example.cl_dguid _uid = null;
public static anywheresoftware.b4j.objects.SQL _mysql = null;
public static long _xx = 0L;
public static anywheresoftware.b4a.gps.GPS _g = null;
public static String _temp_info_gps = "";
public static String _lat = "";
public static String _lon = "";
public static derez.libs.Navigation _nav = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public four.season.main _main = null;
public four.season.get_order _get_order = null;
public four.season.main_page _main_page = null;
public four.season.viewdetails _viewdetails = null;
public four.season.my_profile _my_profile = null;
public four.season.main_profile _main_profile = null;
public four.season.starter _starter = null;
public four.season.b4xcollections _b4xcollections = null;
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 4;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Private nid As Int = 5";
_nid = (int) (5);
 //BA.debugLineNum = 6;BA.debugLine="Private lock As PhoneWakeState";
_lock = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 7;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 8;BA.debugLine="Dim uid As cl_dgUID";
_uid = new b4a.example.cl_dguid();
 //BA.debugLineNum = 9;BA.debugLine="Public mysql As JdbcSQL";
_mysql = new anywheresoftware.b4j.objects.SQL();
 //BA.debugLineNum = 10;BA.debugLine="Public xx As Long";
_xx = 0L;
 //BA.debugLineNum = 11;BA.debugLine="Dim g As GPS";
_g = new anywheresoftware.b4a.gps.GPS();
 //BA.debugLineNum = 12;BA.debugLine="Dim temp_info_gps As String";
_temp_info_gps = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim lat As String";
_lat = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim lon As String";
_lon = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim nav As Navigation";
_nav = new derez.libs.Navigation();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
}
