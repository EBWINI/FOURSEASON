B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=9.01
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub GetLastID As Long

	Dim rst As JdbcResultSet
	rst = Main.mysql.ExecQuery("SELECT @@IDENTITY AS ID;")
	Dim ID As Long = 0
	If rst.NextRow Then
		ID = rst.GetLong("ID")
	End If
	rst.Close
	Return ID
	
End Sub


Sub SQLUpdate(mySQL As JdbcSQL,sTable As String,sField As String, sValues As List, lID As Long)

	Dim strSQL As String = ""
	Dim sFields As List = Regex.Split("\,",sField)
	For Each sF As String In sFields
		If strSQL <> "" Then strSQL = strSQL & ", "
		strSQL = strSQL & sF & " = ?"
	Next
	strSQL = "UPDATE " & sTable & " SET " & strSQL & " WHERE ID = " & lID
	Log(strSQL)
	mySQL.ExecNonQuery2(strSQL,sValues)
    		
End Sub
