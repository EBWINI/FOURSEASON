B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.01
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Public rp As RuntimePermissions
End Sub
Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.
	'CallSubDelayed(player, "Play")
	'DisableStrictMode
End Sub
Sub Service_Start (StartingIntent As Intent)
'	Service.StopAutomaticForeground 'Starter service can start in the foreground state in some edge cases.
End Sub
Sub goo
	CallSubDelayed(player, "Play")
End Sub
Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub
Sub Service_Destroy

End Sub
'Sub DisableStrictMode
'	Dim jo As JavaObject
'	jo.InitializeStatic("android.os.Build.VERSION")
'	If jo.GetField("SDK_INT") > 9 Then
'		Dim policy As JavaObject
'		policy = policy.InitializeNewInstance("android.os.StrictMode.ThreadPolicy.Builder", Null)
'		policy = policy.RunMethodJO("permitAll", Null).RunMethodJO("build", Null)
'		Dim sm As JavaObject
'		sm.InitializeStatic("android.os.StrictMode").RunMethod("setThreadPolicy", Array(policy))
'	End If
'End Sub