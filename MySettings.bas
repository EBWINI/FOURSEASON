B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.01
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private ProgressBar1 As ProgressBar
	Private txtServer As EditText
	Private txtDatabase As EditText
	Private txtPassword As EditText
	Private txtSQLInstance As EditText
	Private txtUser As EditText
	Private txtSQL As EditText
	Private LabelM1 As Label
	Private cmdSelTable As Button

	Private txtSQLDetails As EditText
	Private ListView1 As ListView

	Private pnlListView1 As Panel
	Private lblSelTitle As Label
	Private LabelM2 As Label

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("MySettings")
	MyLoad
End Sub

Sub Activity_Resume


   
End Sub

Sub Activity_Pause (UserClosed As Boolean)

   If UserClosed = True Then
	   	MySave
	End If

End Sub


Sub cmdBack_Click
	
	' return back to main form - settings are saved in activity pause
	Activity.Finish
	
End Sub

Sub cmdTestCon_Click
	
	ProgressBar1.Visible = True
	' save all the settings
	MySave
	
	Wait For (MyConnect) Complete (Result As Boolean)

	If (Result= True) Then
		LabelM1.Text = "Connect ok"
		cmdSelTable.Visible = True
	Else
		LabelM1.Text = "Connect Fail " 
	End If
	
	LabelM2.Text = Main.jdbcUrl
	Log(Main.jdbcUrl)
	ProgressBar1.Visible = False
	
End Sub

Sub MyConnect As ResumableSub
	
	Main.mysql.InitializeAsync("mysqlWAIT", Main.driver, Main.jdbcUrl, Main.Username, Main.Password)
	Wait For mysqlWAIT_Ready (Success As Boolean)
	If Success = False Then
		Log("Check unfiltered logs for JDBC errors.")
	End If
	Return Success
	
End Sub

Sub MySave
	'
	' save all form data + controls
	' add the constring, + USER + password as keys (main form will use these to connect)
	
	Main.driver  = "net.sourceforge.jtds.jdbc.Driver"
	Main.jdbcUrl = "jdbc:jtds:sqlserver://" & txtServer.Text & _
							";DatabaseName=" & txtDatabase.Text 
							
	Main.Username = txtUser.Text
	Main.Password = txtPassword.Text
	Main.strSQLSelect = txtSQL.Text
	Main.strSQLSelectDetails = txtSQLDetails.Text
	
	StateManager.SetSetting("jdbcUrl", Main.jdbcUrl)
	StateManager.SetSetting("Username",Main.Username)
	StateManager.SetSetting("Password",Main.Password)
	StateManager.SetSetting("strSQLSelect", Main.strSQLSelect)
	StateManager.SetSetting("strSQLSelectDetails", Main.strSQLSelectDetails)

	StateManager.SaveState(Activity,"MySettings")    ' save all controls and form data
	
	StateManager.SaveSettings		' write out all above (to settings file)
	
	
End Sub

Sub MyLoad
	
	StateManager.RestoreState(Activity,"MySettings",0)
	
	
End Sub

Sub cmdSelTable_Click
	
	' assume valid connection
	' query database for all tables
	Dim strSQL As String = "SELECT TABLE_NAME FROM " & txtDatabase.Text & ".INFORMATION_SCHEMA.TABLES " & _
	                        "WHERE TABLE_TYPE = 'BASE TABLE'"
	Dim rst As JdbcResultSet
	
	ProgressBar1.Visible = True
	ListView1.Clear
	ListView1.SingleLineLayout.ItemHeight = 40dip
	Try
		Dim sf As Object = Main.mysql.ExecQueryAsync("mysqlWAIT", strSQL ,Null)
		Wait For (sf) mysqlWAIT_QueryComplete(Success As Boolean,   rst As JdbcResultSet)

		If Success Then
			Dim s As String = ""
			Do While rst.NextRow
				s = rst.GetString(rst.GetColumnName(0))
				ListView1.AddSingleLine2(s,s)
			Loop
			rst.Close
			pnlListView1.Top = 0%y
			pnlListView1.Height = 100%y
			ListView1.Height = 100%y
			lblSelTitle.Text = "Select SQL Table"
			pnlListView1.Visible = True
			
		End If
	Catch
		Success = False
		Log(LastException)
	End Try
	
ProgressBar1.Visible = False

End Sub

Sub ListView1_ItemClick (Position As Int, Value As Object)

	pnlListView1.Visible = False
	If Value <> "" Then
		Dim strFirstColumn As String
		Wait For (GetFirstColumn(Value)) Complete (strFirstColumn As String)
		
		txtSQL.Text  = "select * from requests"
'		"SELECT TOP 20 " & strFirstColumn & " FROM " & Value
'		txtSQLDetails.Text = "SELECT * FROM " & Value
	End If
	
End Sub

Sub btnselCancel_Click

	pnlListView1.Visible = False
	
End Sub



Sub GetFirstColumn (strTable As String) As ResumableSub
	
	Dim strFirstField As String = ""
	Dim rst As JdbcResultSet
	Dim strSQL As String = "SELECT * FROM " & strTable
	Dim sf As Object = Main.mysql.ExecQueryAsync("mysqlWAIT", strSQL ,Null)
	Dim i As Int
	
	Wait For (sf) mysqlWAIT_QueryComplete(Success As Boolean,   rst As JdbcResultSet)
    If Success Then
		Do While rst.NextRow
			For i  = 0 To rst.ColumnCount - 1
				If strFirstField <> "" Then strFirstField = strFirstField & ","
				strFirstField = strFirstField & rst.GetColumnName(i)
				If i = 3 Then Exit
			Next
			Exit
		Loop
		rst.Close
	Else
		strFirstField = ""
	End If
	Return strFirstField

End Sub


Sub TestSToreProc
	
	
	Dim strSQL As String = "CAll GetOrderCountByCity('Edmonton', @OrderCount);" & _
	                       "SELECT @OrderCount;"
						   
	Dim rst As JdbcResultSet
	Try
		Dim sf As Object = Main.mysql.ExecQueryAsync("mysqlWAIT", strSQL ,Null)
		Wait For (sf) mysqlWAIT_QueryComplete(Success As Boolean,   rst As JdbcResultSet)

		If Success Then
			Dim sOrderCount As Long = 0
			rst.NextRow
			sOrderCount = rst.GetLong("@OrderCount")
		End If
		rst.Close
	Catch
		Success = False
		Log(LastException)
	End Try

End Sub


