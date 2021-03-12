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
	Public mysql As JdbcSQL
	Public xx As Long
	Dim g As GPS
	Dim temp_info_gps As String
	Dim lat As String
	Dim lon As String
	Dim nav As Navigation
	End Sub
'Sub Service_Create
'	Try
'			uid.Initialize
'	g.Initialize("GPS")
'	Service.StartForeground(1, CreateNotification("تم تفعيل النظام"))
'	timer1.Initialize("timer1", 1000)
'	timer1.Enabled = True
'	lock.PartialLock
'	Catch
'		Log(LastException.Message)
'	End Try
'
'End Sub
'Sub Service_Start (StartingIntent As Intent)
'	Try
'			Service.StartForeground(2, CreateNotification("النظام جاهز لتلقي الاشعارات"))
'	Catch
'		Log(LastException.Message)
'	End Try
'
'End Sub
'Public Sub Play
'End Sub
'Sub pl_Error (Message As String)
'	Try
'	Log(Message)	
'	Catch
'		Log(LastException.Message)
'	End Try
'	
'End Sub
'Sub pl_Complete
'	Try
'	Log("complete")	
'	Catch
'		Log(LastException.Message)
'	End Try
'	
'End Sub
'Public Sub Stop
'	'Service.StopForeground(nid)
''	pl.Pause
'End Sub
'Sub CreateNotification (Body As String) As Notification
'	Try
'			Dim notification As Notification
'				notification.Initialize2(notification.IMPORTANCE_HIGH)
'	notification.OnGoingEvent=True
'	notification.Vibrate=True
'	notification.Icon = "icon"
'	notification.SetInfo("الفصول الاربعه", Body, Main)
'	Return notification
'	Catch
'		Log(LastException.Message)
'	End Try
'
'End Sub
'Sub Service_Destroy
''	pl.Pause
'	'lock.ReleasePartialLock
'End Sub
'Sub JobDone(Job As HttpJob)
'	Try
'		ProgressDialogHide
'		If Job.Success Then
'			If Job.JobName = "getjson" Then
'				Dim out As OutputStream
'				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
'				File.Copy2(Job.GetInputStream, out)
'				File.Delete(File.DirRootExternal,Job.Tag)
'				out.Close
'				'Log($"The file ${Job.Tag} is written to DirRootExternal"$)
'			
'				ParseJSON(Job.GetString)
'			
'			End If
'			If Job.JobName = "check_not2" Then
'				Dim out As OutputStream
'				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
'				File.Copy2(Job.GetInputStream, out)
'				File.Delete(File.DirRootExternal,Job.Tag)
'				out.Close
'				ParseJSON3(Job.GetString )
'			End If
'			
'		Else
'			Log( Job.ErrorMessage)
'		End If
'		Job.Release
'	Catch
'		Log(LastException.Message)
'	End Try
'
'End Sub
'
'Sub ParseJSON3(jsonstring As String )
'	Try
'		Dim parser As JSONParser
'		jsonstring=jsonstring.Replace("[","").Replace("]","")
'		parser.Initialize(jsonstring)
'		Dim root As Map
'		root= parser.NextObject
'		xx=root.Get("status2")
'		If xx=1 Then
'			Dim n As Notification
'			n.Initialize
'			n.Icon = "icon"
'			n.SetInfo(Main.msg_title," لديك طلبات جديده", "") 'Change Main to "" if this code is in the main module.
'			n.Notify(nid)
'			xx=0
'			Dim job2 As HttpJob
'			job2.Initialize("stop_noti2", Me)
'			Dim str2 As String =""& Main.server &"/stop_noti2?usrid=" & Main.usrid
'			job2.Download(str2)
'		End If
'		Catch
'		Log(LastException.Message)
'	End Try
'End Sub
'Sub ParseJSON(jsonstring As String)
'	Try
'		Dim parser As JSONParser
'		jsonstring=jsonstring.Replace("[","").Replace("]","")
'		parser.Initialize(jsonstring)
'		Dim root As Map
'		root= parser.NextObject
'		Dim names As String =root.Get("name")
'		Dim group As String =root.Get("group")
'		'Msgbox(xx,"title")
'		Dim n As Notification
'		n.Initialize
'		n.Icon = "icon"
'		If names <> 0 Then
'			n.SetInfo(group,names, "") 'Change Main to "" if this code is in the main module.
'			'n.Light = True
'			n.Notify(nid)
'			Dim phone_ As PhoneVibrate
'			phone_.Vibrate(1000)
'		End If
'	Catch
'		Log(LastException.Message)
'	End Try
'	nid  = nid +1
'	'http://www.ebwini-eco.com:8008/parking1_2/api/mapsobjects/CHCK_NOTI_BY_DEV2?DEVICE_ID=da257d9b4008e38
'End Sub
'Sub timer1_Tick
'Try
'	g.Start(1,1)
'	Dim job3 As HttpJob
'	job3.Initialize("check_not2", Me)
'	Dim str3 As String =""& Main.server &"/check_not2?user_id=" & Main.usrid
'	job3.Download(str3)
'Catch
'	Log(LastException.Message)
'End Try
'
'End Sub


'Sub GPS_LocationChanged (location1 As Location)
'	lat =location1.Latitude
'	lon=location1.Longitude
'	Dim STR_SQL As String="INSERT INTO [locations]([user_id],[lat],[lon]) VALUES   (5,'"& lat &"','"& lon &"')"
'	Dim sf As Object = Main.mysql.ExecQueryAsync("mysqlWAIT", STR_SQL,Null)
'End Sub