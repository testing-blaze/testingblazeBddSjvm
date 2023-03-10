param(
  	[string] $URL,
	[string] $DocumentLocation,
	[string] $user,
	[string] $cred,
	[string] $path,
    [string] $dllPath
)

#Load SharePoint CSOM Assemblies
Add-Type -Path $dllPath"Microsoft.SharePoint.Client.dll"
Add-Type -Path $dllPath"Microsoft.SharePoint.Client.Runtime.dll"

#set variables requires to connect sharepoint and folders
$Uri = $URL
$SPMainName = $DocumentLocation
$FileToUpload=$path
$Username =$user
$userCred =$cred

write-host "URI" + $Uri
write-host "SharePointLocation" +$SPMainName
write-host "File" +$FileToUpload

  
#Setup Credentials to connect
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$Credentials = New-Object Microsoft.SharePoint.Client.SharePointOnlineCredentials($Username,(ConvertTo-SecureString $userCred -AsPlainText -Force))
  
#Set up the context
$Context = New-Object Microsoft.SharePoint.Client.ClientContext($Uri)
$Context.Credentials = $Credentials
 
$Web = $Context.Web
$Context.Load($Web)

#Check if Folder Exists
Try {
    $Folder = $Web.GetFolderByServerRelativeUrl($SPMainName)
    $Context.Load($Folder)
    $Context.ExecuteQuery()
 
    Write-host -f Green "Folder Exists!"
}
Catch {
    Write-host -f Yellow "Folder Doesn't Exist!"
    $ParentPath = Split-Path $SPMainName -Parent
    $FolderName = Split-Path $SPMainName -Leaf
    $ParentFolder=$Context.web.GetFolderByServerRelativeUrl($ParentPath)
    Write-Output "Add new folder in next statement:"$ParentFolder-$FolderName
    $item=$ParentFolder.Folders.Add($FolderName)
}

$TargetFolder = $Web.GetFolderByServerRelativeUrl($SPMainName)
$Context.Load($TargetFolder)
$Context.ExecuteQuery()
 
#Get the file from disk
$FileStream = ([System.IO.FileInfo] (Get-Item $FileToUpload)).OpenRead()
#Get File Name from source file path
$FileToUploadName = Split-path $FileToUpload -leaf
$FinalSharePointFileURL = $SPMainName+"/"+$FileToUploadName
   
#sharepoint online upload file powershell
$FileCreationInfo = New-Object Microsoft.SharePoint.Client.FileCreationInformation
$FileCreationInfo.Overwrite = $true
$FileCreationInfo.ContentStream = $FileStream
$FileCreationInfo.URL = $FinalSharePointFileURL

$FileUploaded = $TargetFolder.Files.Add($FileCreationInfo)

#powershell upload single file to sharepoint online
$Context.ExecuteQuery()

$tempsite = $Uri -split '/sites/'
$site = $tempsite[0]+$FinalSharePointFileURL
write-host "FileURL:"$site"##-#"
 
#Close file stream
$FileStream.Close()

write-host "File has been uploaded!"