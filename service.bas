B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=10.5
@EndOfDesignText@
#Region  Service Attributes 
	 #StartAtBoot: True
#End Region
Sub Process_Globals
	Private nid As Int = 5
	Private lock As PhoneWakeState
	Private timer1 As Timer
	Dim uid As cl_dgUID
	

End Sub
Sub Service_Create
	uid.Initialize
	Service.StartForeground(1, CreateNotification("تم تفعيل النظام"))
	Service.AutomaticForegroundNotification=1
	
	timer1.Initialize("timer1", 1000)
	timer1.Enabled = True
	lock.PartialLock
End Sub
Sub Service_Start (StartingIntent As Intent)
	Service.StartForeground(2, CreateNotification("النظام جاهز لتلقي الاشعارات"))
End Sub
Public Sub Play
End Sub
Sub pl_Error (Message As String)
	Log(Message)
End Sub
Sub pl_Complete
	Log("complete")
End Sub
Public Sub Stop
	'Service.StopForeground(nid)
'	pl.Pause
End Sub
Sub CreateNotification (Body As String) As Notification
	
	Dim notification As Notification
	notification.Initialize2(notification.IMPORTANCE_HIGH)
	notification.OnGoingEvent=True
	notification.Vibrate=True
	notification.Icon = "icon"
	notification.SetInfo("مؤسسة الجبالي الصناعيه", Body, Main)
	Return notification
End Sub
Sub Service_Destroy
'	pl.Pause
	'lock.ReleasePartialLock
End Sub
Sub JobDone(Job As HttpJob)
	Try
		ProgressDialogHide
		If Job.Success Then
			If Job.JobName = "getjson" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				out.Close
				'Log($"The file ${Job.Tag} is written to DirRootExternal"$)
			
				ParseJSON(Job.GetString)
			
			End If
		Else
			Log( Job.ErrorMessage)
		End If
		Job.Release
	Catch
		Log(LastException)
	End Try

End Sub
Sub ParseJSON(jsonstring As String)
	Try
		Dim parser As JSONParser
		jsonstring=jsonstring.Replace("[","").Replace("]","")
		parser.Initialize(jsonstring)
		Dim root As Map
		root= parser.NextObject
		Dim names As String =root.Get("name")
		Dim group As String =root.Get("group")
		'Msgbox(xx,"title")
		Dim n As Notification
		n.Initialize
		n.Icon = "icon"
		If names <> 0 Then
			n.SetInfo(group,names, "") 'Change Main to "" if this code is in the main module.
			'n.Light = True
			n.Notify(nid)
			Dim phone_ As PhoneVibrate
			phone_.Vibrate(1000)
		End If
	Catch
		Log(LastException)
	End Try
	nid  = nid +1
	
End Sub
Sub timer1_Tick
	get_msg_from_server
End Sub
Sub get_msg_from_server
'		Dim j As HttpJob
'	j.Initialize("", Me)
'	j.Download("http://www.ebwini-eco.com:8008/parking1_2/api/mapsobjects/CHCK_NOTI_BY_DEV2?DEVICE_ID="&uid.GetAndroidID)
	Dim job As HttpJob
	job.Initialize("getjson", Me)
	job.Download("http://www.ebwini-eco.com:8008/parking1_2/api/mapsobjects/CHCK_NOTI_BY_DEV2?DEVICE_ID=" & uid.GetAndroidID)
End Sub
