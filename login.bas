B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Public mysql As JdbcSQL
	'jtds driver
	Public driver As String = "net.sourceforge.jtds.jdbc.Driver"
	Public jdbcUrl As String = ""
	
	Public Username As String = ""
	Public Password As String = ""
	
	Public strSQLSelect As String = ""
	Public strSQLSelectDetails As String = ""
		
	Private gViewTop As Int = 0		' Saves top of listview - so on rotate we can move screen
	' to current positon before a rotate.
	Public gID As Long = 0
	Public sPK As String = ""
	Public usrid  As Long=0
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private CustomListView1 As CustomListView


	Private xui   As XUI
	
	Private Label1 As Label
	Private ButtonM1 As Button
	Private Label2 As Label
	Private Label3 As Label
	Private ProgressBar1 As ProgressBar
	Private Label4 As Label
	Private ButtonM2 As Button
	Private LabelM1 As Label
	Private ButtonM3 As Button
	Private ViewData As Button
	
	Private LabelM2 As Label
	

	Private strSQLDetails As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	MyLoad
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
Sub MyLoad
	
	jdbcUrl = "jdbc:jtds:sqlserver://192.168.1.126;DatabaseName=doortodoor"'StateManager.GetSetting("jdbcUrl")
	Username = "sa"'StateManager.getSetting("Username")
	Password = "sasa"' StateManager.getSetting("Password")
	strSQLSelect = "SELECT *  FROM  [users]"' StateManager.getSetting("strSQLSelect")
	strSQLSelectDetails =  StateManager.getSetting("strSQLSelectDetails")
	
End Sub
