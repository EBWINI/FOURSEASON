B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.01
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: True
#End Region
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub
Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private ViewBack As Button
	Private ScrollView1 As ScrollView
	Private su As StringUtils
	
	Private Label1 As Label
	Private EditText1 As EditText
	Private Button1 As Button
	Public price_new As Long
	Public price_new_d As Long

End Sub
Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("ViewDetails")
Try
		Dim job As HttpJob
	job.Initialize("get_requests", Me)
	Dim str As String =""& Main.server &"/request_details?order_id=" & Main.gID
	job.Download(str)
Catch
	Log(LastException.Message)
End Try
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
			If Job.JobName = "dos_ql" Then
				Dim out As OutputStream
				out = File.OpenOutput(File.DirRootExternal,Job.Tag,False )
				File.Copy2(Job.GetInputStream, out)
				File.Delete(File.DirRootExternal,Job.Tag)
				out.Close
				ParseJSON2(Job.GetString )
			End If
		Else
			Log( Job.ErrorMessage)
		End If
		Job.Release
	Catch
		Log(LastException.Message)
	End Try

End Sub
Sub ParseJSON(jsonstring As String )
	Dim intTop As Int
	Dim i As Int
	Dim totHeight As Long = 0
	Dim myHeight As Long = 40dip
	Try
		Dim parser As JSONParser
		parser.Initialize(jsonstring)
		Dim root As List = parser.NextArray
		For Each colroot As Map In root
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
		    l.Text = "رقم الطلب"
	    	intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("req_id")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l1 As Label
			l1.Initialize("")
			'l.Width = Label1.Width
			l1.TextSize = Label1.TextSize
			l1.Typeface = Label1.Typeface
			l1.Gravity = Label1.Gravity
			l1.Text = "اسم العميل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l1, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("agent")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "هاتف العميل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("phone")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "المحافظه"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("city")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "تاريخ التسليم"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("dte")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "نوع التوصيل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("delivery_type")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "المرسل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("sender")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "هاتف المرسل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("phone2")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "الوزن"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("weght")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "السعر"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("price")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "سعر التوصيل"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("price2")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "السائق"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("driver")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "الحاله"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("status")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			Dim l As Label
			l.Initialize("")
			'l.Width = Label1.Width
			l.TextSize = Label1.TextSize
			l.Typeface = Label1.Typeface
			l.Gravity = Label1.Gravity
			l.Text = "ملاحظات"
			intTop = totHeight
			ScrollView1.Panel.AddView(l, EditText1.Width ,intTop, Label1.Width ,  40dip)
			Dim et As EditText
			et.Initialize("")
			et.TextSize = EditText1.TextSize
			et.Gravity = EditText1.Gravity
			et.Width = EditText1.Width
			et.Typeface = l.Typeface
			et.Enabled=False
			et.Text = colroot.Get("notes")
			Dim p As Phone
			et.Background = p.GetResourceDrawable(17301528)
			ScrollView1.Panel.AddView(et,0dip , intTop,EditText1.Width,myHeight)
			totHeight = totHeight + myHeight
			price_new=colroot.Get("price")
			price_new_d=colroot.Get("price2")
 totHeight = totHeight + myHeight
		Next
'		Dim p As 	B4XView = xui.CreatePanel("")
'		p.SetLayoutAnimated(0,0,0,95%x, 50dip)
'		p.LoadLayout("GridRow")
'		Label1.Text 	  =  root.Get("id")
'		Label2.Text       = root.Get("name")
'		Label3.Text       =  root.Get("city")
'		ViewData.Text="عرض"
'		ViewData.Tag=root.Get("id")
'			CustomListView1.Add(p, root.Get("id"))
'		
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
Sub Activity_Resume

End Sub
Sub Activity_Pause (UserClosed As Boolean)

End Sub
Sub ViewBack_Click
		Activity.Finish
End Sub
Private Sub Button1_Click
	Dim full_price As Double=price_new+price_new_d
	Dim str As String =" سعر البضاعه هو " & price_new & " رسوم التوصيل هي " & price_new_d & " مجموع المبلغ المستحق هو " & full_price & " دينار اردني "
	
	Dim colordialog As ColorDialog
	colordialog.Initialize("dialog")
	colordialog.Color=Colors.RGB(65,170,200)
	colordialog.AnimationEnable=True
	colordialog.ContentText=str'"        الفصول الاربعه                       "
	colordialog.Title="سيتم تسليم الطلب"
	'colordialog.ContentImage=LoadBitmap(File.DirAssets,"icon.png")
	colordialog.Show("نعم","لا")
End Sub
Sub dialog_click(result As String)
	Log(result)
	If result="نعم" Then
		DateTime.DateFormat = "yyyy/MM/dd hh:mm:ss"
		Dim t As Long
		t = DateTime.DateParse(DateTime.Date(DateTime.now))
		DateTime.DateFormat = "yyyy/MM/dd hh:mm:ss"
		Dim order_date As String
		order_date = DateTime.Date(t)
		Dim str_sql1 As String 
		str_sql1="UPDATE [requests]   SET [status] = 47,[done_date]='"& order_date &"' where id =" & Main.gID & " "
		
		Try
			Dim job As HttpJob
		job.Initialize("dos_ql", Me)
		Dim str As String =""& Main.server &"/update_req?id=" & Main.gID '& "&order_date=" & order_date
		job.Download(str)	
		Catch
			Log(LastException.Message)
		End Try
		Dim full_price As Double=price_new+price_new_d
		Dim str As String =" سعر البضاعه هو " & price_new & " رسوم التوصيل هي " & price_new_d & " مجموع المبلغ المستحق هو " & full_price & " دينار اردني "
		Dim prompt As PromptDialog
		prompt.Initialize("prompt")
		prompt.DialogType=prompt.DIALOG_TYPE_SUCCESS
		prompt.Title="الفصول الاربعه"
		prompt.AnimationEnable=True
		prompt.ContentText=" تمت عملية تسليم الطلب للعميل بنجاح "'str
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
	Activity.Finish
End Sub
