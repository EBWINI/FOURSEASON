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
	Public tmr As Timer
	Public order_date As String
	Dim date_new  As String
End Sub
Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim spinnerMap As Map
	Dim spinnerMap2 As Map
	Dim spinnerMap3 As Map
	Dim spinnerMap4 As Map
	Dim spinnerMap5 As Map
	Private Spinner1 As Spinner
	Private Spinner2 As Spinner
	Private Spinner3 As Spinner
	Private Spinner4 As Spinner
	Private Spinner5 As Spinner
	Private EditText1 As EditText
	Private EditText2 As EditText
	Private EditText3 As EditText
	Private EditText4 As EditText
	Private EditText5 As EditText
	Private EditText6 As EditText
	Private EditText7 As EditText
	Private EditText8 As EditText
	Private EditText9 As EditText
	Private EditText10 As EditText
	Private Button2 As Button
	Private Button1 As Button
	Public sender_id As Long=0
	Public good_type_ As Long=0
	Public delevery_type_ As Long=0
	Public order_type_ As Long=0
	Public city_ As Long=0
	Dim rsl As String=0
	Private WebView1 As WebView
End Sub
Sub Activity_Create(FirstTime As Boolean)
	
	Activity.LoadLayout("get_order")
	Try
	Dim job As HttpJob
	job.Initialize("get_company", Me)
		Dim str As String =""& Main.server &"/get_company"
	job.Download(str)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job2 As HttpJob
	job2.Initialize("good_type", Me)
		Dim str2 As String =""& Main.server &"/good_type"
	job2.Download(str2)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job3 As HttpJob
	job3.Initialize("delevery_type", Me)
		Dim str3 As String =""& Main.server &"/delevery_type"
	job3.Download(str3)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job4 As HttpJob
	job4.Initialize("order_type", Me)
		Dim str4 As String =""& Main.server &"/order_type"
	job4.Download(str4)
	Catch
	Log(LastException.Message)
	End Try
	Try
	Dim job5 As HttpJob
	job5.Initialize("get_city", Me)
		Dim str5 As String =""& Main.server &"/get_city"
	job5.Download(str5)
	Catch
	Log(LastException.Message)
	End Try
	Try
			Dim p As Phone
	EditText1.Background = p.GetResourceDrawable(17301528)  
	EditText2.Background = p.GetResourceDrawable(17301528)  
	EditText3.Background = p.GetResourceDrawable(17301528)  
	EditText4.Background = p.GetResourceDrawable(17301528)  
	EditText5.Background = p.GetResourceDrawable(17301528) 
	EditText6.Background = p.GetResourceDrawable(17301528)  
	EditText7.Background = p.GetResourceDrawable(17301528)  
	EditText8.Background = p.GetResourceDrawable(17301528)
	EditText9.Background = p.GetResourceDrawable(17301528)
	EditText10.Background = p.GetResourceDrawable(17301528)
	Catch
		Log(LastException.Message)
	End Try
End Sub
Sub JobDone(Job As HttpJob)
	Try
		ProgressDialogHide
		If Job.Success Then
			If Job.JobName = "get_company" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner1,spinnerMap )
			End If
			If Job.JobName = "good_type" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner2,spinnerMap2 )
			End If
			If Job.JobName = "delevery_type" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner3,spinnerMap3 )
			End If
			If Job.JobName = "order_type" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner4,spinnerMap4 )
			End If
			If Job.JobName = "get_city" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner5,spinnerMap5 )
			End If
			If Job.JobName = "dos_ql" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON(Job.GetString,Spinner5,spinnerMap5 )
			End If
		Else
			Log( Job.ErrorMessage)
		End If
		Job.Release
	Catch
		Log(LastException.Message)
	End Try

End Sub
Sub ParseJSON(jsonstring As String,sp As Spinner,spm As Map )
	Try
		Dim parser As JSONParser
	parser.Initialize(jsonstring)
	Dim root As List = parser.NextArray
		spm.Initialize
				sp.Add("اختر من القائمه")
		spm.Put("اختر من القائمه",0)
	For Each colroot As Map In root
			sp.Add( colroot.Get("name"))
			spm.Put(colroot.Get("name"),colroot.Get("id"))
	Next

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
		'lbl.Text =root.Get("cnt")
	Catch
		Log(LastException.Message)
	End Try
End Sub
Private Sub Button1_Click
	Try
		DateTime.DateFormat = "yyyy/MM/dd hh:mm:ss"
		Dim t As Long
		t = DateTime.DateParse(DateTime.Date(DateTime.now))
		DateTime.DateFormat = "yyyy/MM/dd hh:mm:ss"

		order_date = DateTime.Date(t)
'	Public sender_id As Long
'	Public good_type_ As Long
'	Public delevery_type_ As Long
'	Public order_type_ As Long
'	Public city_ As Long

		If sender_id=0 Then
			'Msgbox("اختر مرسل الطلب",Main.msg_title)
			Mymsg("اختر مرسل الطلب")
			Return
		End If
		If good_type_=0 Then
			'Msgbox("اختر نوع البضاعه",Main.msg_title)
			Mymsg("اختر نوع البضاعه")
			Return
		End If
		If delevery_type_=0 Then
			Mymsg("اختر نوع التوصيل")
			'Msgbox("اختر نوع التوصيل",Main.msg_title)
			Return
		End If
		If order_type_=0 Then
			Mymsg("اختر نوع الطلب")
			'Msgbox("اختر نوع الطلب",Main.msg_title)
			Return
		End If
		If city_=0 Then
			Mymsg("اختر المحافظه")
			'Msgbox("اختر المحافظه  ",Main.msg_title)
			Return
		End If
		If EditText1.Text="" Then
			Mymsg("تاكد من عدد الصناديق")
			Return
		End If
		If EditText2.Text="" Then
			Mymsg("تاكد من الوزن")
			Return
		End If
		If EditText3.Text="" Then
			Mymsg("تاكد من السعر")
			Return
		End If
		If EditText5.Text="" Then
			Mymsg("تاكد من اسم العميل")
			Return
		End If
		If EditText6.Text="" Then
			Mymsg("تاكد من عنوان العميل")
			Return
		End If
		If EditText7.Text="" Then
			Mymsg("تاكد من رقم هاتف")
			Return
		End If
		'**************************************************date ******************************
		If EditText4.Text="" Or EditText9.Text="" Or EditText10.Text="" Then
			Mymsg("تاكد من التاريخ")
			Return
		End If
	
		If EditText4.Text<1 Or EditText4.Text>31 Then
			Mymsg("تاكد من اليوم بالتاريخ")
			Return
		End If
		If EditText9.Text<1 Or EditText9.Text>12 Then
			Mymsg("تاكد من الشهر بالتاريخ")
			Return
		End If
		If EditText9.Text=2 And EditText4.Text >28 Then
			Mymsg("تاكد من اليوم بالتاريخ-شهر2")
			Return
		End If
		If EditText10.Text<2021 Or EditText10.Text>9999 Then
			Mymsg("تاكد من السنه بالتاريخ")
			Return
		End If
		date_new  =EditText10.Text & "/" & EditText9.Text & "/" & EditText4.Text
		'****************************************************** ******************************
'	Dim STR_SQL As String="INSERT INTO [requests] ([status],[user_id],[order_date],[sender],[order_class],[delivery_type],[order_type],[class_count],[weight],[price],[delivery_date],[temp_agent_name],[city_to],[temp_address],[receiver_phone],[note]) VALUES (54,"& Main.usrid &",'"& order_date &"',"& sender_id &","& good_type_ &","& delevery_type_ &","& order_type_ &","& EditText1.Text &","& EditText2.Text &","& EditText3.Text &",'"& date_new &"','"& EditText5.Text &"',"& city_ &",'"& EditText6.Text &"','"& EditText7.Text &"','"& EditText8.Text &"')"
'	Dim sf As Object = Main.mysql.ExecQueryAsync("mysqlWAIT", STR_SQL,Null)
	
		Dim colordialog As ColorDialog
		colordialog.Initialize("dialog")
		colordialog.Color=Colors.RGB(0,220,0)
		colordialog.AnimationEnable=True
		colordialog.ContentText= "        تم استلام الطلب بنجاح                       "
		colordialog.Title="تسليم الطلب"
		'colordialog.ContentImage=LoadBitmap(File.DirAssets,"icon.png")
		colordialog.Show("نعم","لا")
		'Msgbox("تم تسليم الطلب بنجاح","الفصول الاربعه")
	Catch
		Log(LastException)
	End Try
	
End Sub
Private Sub Spinner1_ItemClick (Position As Int, Value As Object)
Try
			sender_id = spinnerMap.Get(Value)
Catch
		sender_id =0
	Log(LastException.Message)
End Try
	 End Sub
Private Sub Spinner2_ItemClick (Position As Int, Value As Object)
	Try
		good_type_ = spinnerMap2.Get(Value)
	Catch
		good_type_ =0
		Log(LastException.Message)
	End Try
		End Sub
Sub ViewBack_Click
	Activity.Finish
End Sub
Private Sub Spinner3_ItemClick (Position As Int, Value As Object)
		Try
			delevery_type_ = spinnerMap3.Get(Value)
		Catch
		delevery_type_ =0
			Log(LastException.Message)
		End Try
	End Sub
Private Sub Spinner4_ItemClick (Position As Int, Value As Object)
	Try
			order_type_ = spinnerMap4.Get(Value)
	Catch
		order_type_ =0
		Log(LastException.Message)
	End Try
	End Sub
Private Sub Spinner5_ItemClick (Position As Int, Value As Object)
	Try
			city_ = spinnerMap5.Get(Value)
	Catch
		city_ =0
		Log(LastException.Message)
	End Try
End Sub
Private Sub Button2_Click
	Activity.Finish
End Sub
Sub Mymsg(msg As String)
Try
		Dim prompt As PromptDialog
		prompt.Initialize("prompt")
		prompt.DialogType=prompt.DIALOG_TYPE_INFO
		prompt.Title="الفصول الاربعه"
		prompt.AnimationEnable=True
		prompt.ContentText=" '"& msg &"'                      "'str
		prompt.Show("موافق","الغاء")
Catch
	Log(LastException.Message)
End Try

End Sub
Sub dialog_click(result As String)
	Log(result)
	If result="نعم" Then
		rsl=result

'		Dim str_sql1 As String
'		str_sql1=""
			Try
'			Dim job As HttpJob
'			job.Initialize("dos_ql", Me)
'			 ' temp_agent_name  ,  city_to ,  temp_address  ,  receiver_phone ,  note  
			Dim str As String =""& Main.server &"/new_order?usrid=" & Main.usrid & "&order_date=" & order_date.Replace("/","xx").Replace("\","xx").Replace(":","zz") & "&sender_id=" & sender_id & "&good_type_=" & good_type_
			str=str & "&delevery_type_=" & delevery_type_ & "&order_type_=" & order_type_ & "&class_count=" & EditText1.Text & "&weight=" & EditText2.Text & "&price=" & EditText3.Text
			str=str & "&delivery_date=" & date_new.Replace("/","xx").Replace("\","xx").Replace(":","zz") & "&temp_agent_name=" & EditText5.Text & "&city_to=" & city_ & "&temp_address=" &  EditText6.Text & "&receiver_phone=" & EditText7.Text & "&note=" & EditText7.Text
            WebView1.LoadUrl(str)
			Catch
			Log(LastException.Message)
			End Try  

		Dim prompt As PromptDialog
		prompt.Initialize("prompt")
		prompt.DialogType=prompt.DIALOG_TYPE_SUCCESS
		prompt.Title="الفصول الاربعه"
		prompt.AnimationEnable=True
		prompt.ContentText=" تمت عملية استلام الطلب من الشركه بنجاح "'str
		prompt.Show("تمت العمليه","الغاء")
	Else
		Dim prompt As PromptDialog
		prompt.Initialize("prompt")
		prompt.DialogType=prompt.DIALOG_TYPE_WARNING
		prompt.Title="الفصول الاربعه"
		prompt.AnimationEnable=True
		prompt.ContentText=" تم الغاء تسليم الطلب                         "'str
		prompt.Show("تمت العمليه","الغاء")
	End If
End Sub
Sub prompt_promptclick
	If rsl<>0 Then
		rsl=0
		Activity.Finish
	End If

End Sub
Sub Activity_Resume
	'Msgbox(Main.usrid,"")
End Sub
Sub Activity_Pause (UserClosed As Boolean)

End Sub