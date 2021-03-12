B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
Sub Process_Globals
'	Public mysql As JdbcSQL
'	Public driver As String = "net.sourceforge.jtds.jdbc.Driver"
'	Public jdbcUrl As String = ""
'	Public Username As String = ""
'	Public Password As String = ""
'	Public strSQLSelect As String = ""
'	Public strSQLSelectDetails As String = ""
	Private gViewTop As Int = 0		' Saves top of listview - so on rotate we can move screen
 Public gID As Long = 0
'	Public sPK As String = ""
'	Public usrid  As Long=0
	Dim SP As SoundPool
	Dim LoadId1, PlayId1, LoadId2, PlayId2 As Int
	Public xx As Long =0
Public tmr As Timer
End Sub
Sub Globals
	Private xui   As XUI
	Private CustomListView1 As CustomListView
	Private ViewData As Button
	Private Label1 As Label
	Private Label2 As Label
	Private Label3 As Label
	Private Button1 As Button
	Private Button2 As Button
	Private Button3 As Button
End Sub
Sub Activity_Create(FirstTime As Boolean)
	Try
		SP.Initialize(4)
	LoadId1 = SP.Load(File.DirAssets, "adma.mp3")	
	Catch
		Log(LastException.Message)
	End Try

	Activity.LoadLayout("MAIN_PAGE")
	
	tmr.Initialize("tmr", 5000)
	tmr.Enabled = True
	Try
		Dim job As HttpJob
	job.Initialize("get_requests", Me)
	Dim str As String =""& Main.server &"/get_requests?Driver_ID=" & Main.usrid
	job.Download(str)
	Catch
		Log(LastException.Message)
	End Try
	
	Button1.Width=48%x
	Button2.Width=25%x
	Button3.Width=23%x
	Button3.Left=29%x
	Button1.Left=53%x
	End Sub
Sub tmr_Tick
	Try
			Dim job3 As HttpJob
	job3.Initialize("check_not", Me)
	Dim str3 As String =""& Main.server &"/check_not?user_id=" & Main.usrid
	job3.Download(str3)
	
	CustomListView1.Clear
	Dim job As HttpJob
	job.Initialize("get_requests", Me)
	Dim str As String =""& Main.server &"/get_requests?Driver_ID=" & Main.usrid
	job.Download(str)
	Catch
		Log(LastException.Message)
	End Try

	

End Sub
Sub ViewData_Click
	Try
		Dim btn As Button = Sender
	Main.gID = btn.Tag
	StartActivity("ViewDetails")	
	Catch
		Log(LastException.Message)
	End Try

End Sub
Sub CustomListView1_ItemClick (Index As Int,  p As String)
Try
		gID = p
		Sleep(200)
	StartActivity("ViewDetails")
Catch
	Log(LastException.Message)
End Try
	
	
	End Sub
Sub Activity_Resume
	Try
		
	Catch
		Log(LastException.Message)
	End Try
'	WAIT FOR (LoadData) Complete (rOk As Boolean)
'	If CustomListView1.Size > 0 Then
'		If CustomListView1.FirstVisibleIndex <> gViewTop Then
'			CustomListView1.JumpToItem(gViewTop)
'		End If
'	End If
Try
	
Catch
	Log(LastException.Message)
End Try
End Sub
Sub Activity_Pause (UserClosed As Boolean)
'	If UserClosed = False Then
'		If CustomListView1.Size > 0 Then
'			gViewTop = CustomListView1.FirstVisibleIndex
'			Log("gtopo saved = " & gViewTop)
'		End If
'	End If
End Sub
Sub JobDone(Job As HttpJob)
	Try
		ProgressDialogHide
		If Job.Success Then
			If Job.JobName = "get_requests" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString )
			End If
			If Job.JobName = "stop_noti" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON2(Job.GetString )
			End If
			
			If Job.JobName = "check_not" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON3(Job.GetString )
			End If
				Else
			Log( Job.ErrorMessage)
		End If
		Job.Release
	Catch
		Log(LastException.Message)
	End Try

End Sub
Sub ParseJSON2(jsonstring As String )
	Try
		Dim parser As JSONParser
		jsonstring=jsonstring.Replace("[","").Replace("]","")
		parser.Initialize(jsonstring)
		Dim root As Map
		root= parser.NextObject
		Catch
		Log(LastException.Message)
	End Try
End Sub
Sub ParseJSON3(jsonstring As String )
	Try
		Dim parser As JSONParser
		jsonstring=jsonstring.Replace("[","").Replace("]","")
		parser.Initialize(jsonstring)
		Dim root As Map
		root= parser.NextObject
		If root.ContainsKey("status") Then
			Msgbox(root.Get("status"),"")
		End If
		xx=root.Get("status")
		If xx=1 Then
			PlayId1 = SP.Play(LoadId1, 1, 1, 1, 2, 1)
			xx=0
			Dim job2 As HttpJob
			job2.Initialize("stop_noti", Me)
			Dim str2 As String =""& Main.server &"/stop_noti?usrid=" & Main.usrid
			job2.Download(str2)
			End If
	Catch
		Log(LastException.Message)
	End Try
End Sub
Sub ParseJSON(jsonstring As String )
	CustomListView1.Clear
	Try
		Dim parser As JSONParser
		'jsonstring=jsonstring.Replace("[","").Replace("]","")
		parser.Initialize(jsonstring)
		Dim root As List = parser.NextArray
		Try
		For Each colroot As Map In root
			Dim itemid As String = colroot.Get("id")
			Dim itemname As String = colroot.Get("name")
			Dim itemprice As String = colroot.Get("city")
			Dim p As 	B4XView = xui.CreatePanel("")
			p.SetLayoutAnimated(0,0,0,95%x, 50dip)
			p.LoadLayout("GridRow")
			Label1.Text 	  =  itemid
			Label2.Text       = itemname
			Label3.Text       =  itemprice
			ViewData.Text="عرض"
			ViewData.Tag=itemid
			Main.gID=itemid
			CustomListView1.Add(p, itemid)
		Next
		Catch
			Log(LastException.Message)
		End Try
    Catch
		Log(LastException.Message)
	End Try
End Sub
Private Sub CreateItem(Crsr As JdbcResultSet) As B4XView
	Dim p As 	B4XView = xui.CreatePanel("")
	p.SetLayoutAnimated(0,0,0,95%x, 50dip)
	p.LoadLayout("GridRow")
	If Crsr.ColumnCount >= 1 Then Label1.Text 	  = Crsr.GetString(Crsr.GetColumnName(0))
	If Crsr.ColumnCount >= 2 Then Label2.Text       = Crsr.GetString(Crsr.GetColumnName(1))
    If Crsr.ColumnCount >= 3 Then Label3.Text       = Crsr.GetString(Crsr.GetColumnName(2))
		ViewData.Text="عرض"
	ViewData.Tag      = Crsr.GetString(Crsr.GetColumnName(0))
    	Return p
End Sub
Private Sub Button1_Click
StartActivity("get_order")
End Sub
Private Sub Button2_Click
	Activity.Finish
	StartActivity("MAIN")
End Sub
Private Sub Button3_Click
	StartActivity("my_profile")
End Sub