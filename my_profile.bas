B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.6
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
Sub Process_Globals

End Sub
Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private Label2 As Label
	Private Label4 As Label
	Private Label6 As Label
	Private Label8 As Label
	Private Label10 As Label
	Private Label12 As Label
	Private Label13 As Label
	Private Label15 As Label
	Private Panel1 As Panel
	Private Panel2 As Panel
	Private Panel3 As Panel
	Private Panel4 As Panel
	Private Panel5 As Panel
	Private Panel6 As Panel
	Private Panel7 As Panel
	Private Panel8 As Panel	
	Private Label17 As Label
End Sub
Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("profile")
	Try
	Dim job As HttpJob
	job.Initialize("get_driver_current", Me)
	Dim str As String =""& Main.server &"/get_driver_current?Driver_ID=" & Main.usrid
	job.Download(str)	
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job2 As HttpJob
	job2.Initialize("get_driver_end_today", Me)
	Dim str2 As String =""& Main.server &"/get_driver_end_today?Driver_ID=" & Main.usrid
	job2.Download(str2)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job3 As HttpJob
	job3.Initialize("get_driver_end_month", Me)
	Dim str3 As String =""& Main.server &"/get_driver_end_month?Driver_ID=" & Main.usrid
	job3.Download(str3)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job4 As HttpJob
	job4.Initialize("get_driver_pay_day", Me)
	Dim str4 As String =""& Main.server &"/get_driver_pay_day?Driver_ID=" & Main.usrid
	job4.Download(str4)	
	Catch
	Log(LastException.Message)
	End Try
	Try
		Dim job5 As HttpJob
	job5.Initialize("get_driver_pay_month", Me)
	Dim str5 As String =""& Main.server &"/get_driver_pay_month?Driver_ID=" & Main.usrid
	job5.Download(str5)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job6 As HttpJob
	job6.Initialize("get_driver_pay_delev_month", Me)
	Dim str6 As String =""& Main.server &"/get_driver_pay_delev_month?Driver_ID=" & Main.usrid
	job6.Download(str6)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job7 As HttpJob
	job7.Initialize("get_driver_pay_delev_day", Me)
	Dim str7 As String =""& Main.server &"/get_driver_pay_delev_day?Driver_ID=" & Main.usrid
	job7.Download(str7)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job8 As HttpJob
	job8.Initialize("get_company_orders", Me)
	Dim str8 As String =""& Main.server &"/get_company_orders?Driver_ID=" & Main.usrid
	job8.Download(str8)
	Catch
	Log(LastException.Message)
	End Try
	Panel1.Width=45%x
	Panel2.Width=45%x
	Panel2.Left=52%x
	Panel3.Width=45%x
	Panel4.Width=45%x
	Panel4.Left=52%x
	Panel5.Width=45%x
	Panel6.Width=45%x
	Panel6.Left=52%x
	Panel7.Width=45%x
	Panel8.Width=45%x
	Panel8.Left=52%x
	'MyConnect
	'Sleep(1)
	'company("SELECT count([id]) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=46",Label2)
	'company("SELECT count([id]) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and CONVERT(date, [requests].done_date) >= convert(date ,(select GETDATE())) and CONVERT(date, [requests].done_date) <= convert(date ,(select GETDATE()))",Label4)
	'company("SELECT count([id]) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and  (Month([requests].done_date) = Month((getdate())) ) AND (YEAR([requests].done_date) = YEAR((getdate())))",Label8)
	'company("SELECT isnull(sum(price),0) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and  (Month([requests].done_date) = Month((getdate())) ) AND (YEAR([requests].done_date) = YEAR((getdate())))",Label10) ' تحصيل الشهر الحالي
	'company("SELECT isnull(sum(price),0) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and CONVERT(date, [requests].done_date) >= convert(date ,(select GETDATE())) and CONVERT(date, [requests].done_date) <= convert(date ,(select GETDATE()))",Label6) ' تحصيل اليوم الحالي
	'company("SELECT isnull(sum(delivary_price),0) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and  (Month([requests].done_date) = Month((getdate())) ) AND (YEAR([requests].done_date) = YEAR((getdate())))",Label15) ' رسوم الشهر الحالي
	'company("SELECT isnull(sum(delivary_price),0) cnt  FROM  [requests] where driver_id = "& Main.usrid  &" and status=47 and CONVERT(date, [requests].done_date) >= convert(date ,(select GETDATE())) and CONVERT(date, [requests].done_date) <= convert(date ,(select GETDATE()))",Label12) ' رسوم اليوم الحالي
	'company("SELECT count([id]) cnt  FROM  [requests] where user_id = "& Main.usrid  &" and status=54",Label13)
End Sub
Sub JobDone(Job As HttpJob)
	Try
		ProgressDialogHide
		If Job.Success Then
			If Job.JobName = "get_driver_current" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label2 )
			End If
			If Job.JobName = "get_driver_end_today" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label4)
			End If
			If Job.JobName = "get_driver_end_month" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label8)
			End If
			If Job.JobName = "get_driver_pay_day" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label6)
			End If
			If Job.JobName = "get_driver_pay_month" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label10)
			End If
			If Job.JobName = "get_driver_pay_delev_month" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label15)
			End If
			If Job.JobName = "get_driver_pay_delev_day" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label12)
			End If
			If Job.JobName = "get_company_orders" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Label13)
			End If
		Else
			Log( Job.ErrorMessage)
		End If
		Job.Release
	Catch
		Log(LastException.Message)
	End Try

End Sub
Sub ParseJSON(jsonstring As String,lbl As Label )
	Try
		Dim parser As JSONParser
		jsonstring=jsonstring.Replace("[","").Replace("]","")
		parser.Initialize(jsonstring)
		Dim root As Map
	root= parser.NextObject
		If root.ContainsKey("cnt") Then
			lbl.Text =root.Get("cnt")	
		End If
		Catch
		Log(LastException.Message)
	End Try
End Sub
Sub Activity_Resume
End Sub
Sub Activity_Pause (UserClosed As Boolean)

End Sub

