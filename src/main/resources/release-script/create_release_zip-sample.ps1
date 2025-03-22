######################################################
##													##
##  ファイル名：create_release_zip.ps1				##
##  説明：											##
##   ・vue、javaプロジェクトのビルド				##
##   ・成果物をzipファイルに圧縮し、サーバへ配置	##
##													##
######################################################

###  定数宣言 ###
param($scpUser = "★{ユーザ名}", $address = "★{adddress}", $key = "★{キーファイルパス}")
$startDir = pwd
$workDirNm = "$startDir\buildWork"
$vueDepDir = "html"
$vueBuildCompornentDir = "$workDirNm\$vueDepDir"
$javaDepDir = "shopping_help_app_back"
$javaBuildCompornentDir = "$workDirNm\$javaDepDir"
$frontPJDir = "★{frontPJのディレクトリ}"									# vueプロジェクトルート
$backendPJDir = "★{backendPJのディレクトリ}"								# javaプロジェクトルート
$buildJarNm = "shopping.help.app-0.0.1-SNAPSHOT.jar"					# jarファイル名
$tmpBranchNm = "tmp-branch"												# ビルド時の一時ブランチ名
$sleepTimeForBranchSwitch = 5											# ブランチ切り替え待ち時間
$tarNm = "deployFile.tar.gz"											# 圧縮ファイル名
$deployShell = "deploy.sh"
$distJSDir = "${frontPJDir}\dist\js"

### 1. 作業ディレクトリの作成 ###
if (Test-Path $workDirNm) {
    Remove-Item $workDirNm -Recurse -Force
}
mkdir $workDirNm
cd $workDirNm
mkdir $vueBuildCompornentDir
mkdir $javaBuildCompornentDir




### 2. 各ツールのビルド ###
## vue
# 一時ブランチ作成
cd $frontPJDir
git branch $tmpBranchNm
git checkout $tmpBranchNm
Write-Output "wait 5 second for switch branch..."
sleep $sleepTimeForBranchSwitch


# ビルド実行
npm run build

# 成果物をを本番用に変更
cd $distJSDir
$chunkFile = ls chunk*.js
Get-Content  -Encoding UTF8 ${chunkFile} `
| ForEach-Object {$_ -replace 'localhost', '★{アップロードサーバのDNS}'} `
| Out-File -Encoding UTF8 "${chunkFile}_tmp" -NoClobber
move "${chunkFile}_tmp" ${chunkFile} -Force

$appFile = ls app*.js
Get-Content  -Encoding UTF8 ${appFile} `
| ForEach-Object {$_ -replace 'localhost', '★{アップロードサーバのDNS}'} `
| Out-File -Encoding UTF8 "${appFile}_tmp" -NoClobber
move "${appFile}_tmp" ${appFile} -Force

rm *_tmp
rm *.map

# 成果物取得
cd $frontPJDir
Copy-Item -Path .\dist\* -Destination $vueBuildCompornentDir -Recurse

# 一時ブランチ削除
git checkout master
git branch --delete $tmpBranchNm


## java
# 一時ブランチ作成
cd $backendPJDir
git branch $tmpBranchNm
git checkout $tmpBranchNm
Write-Output "wait 5 second for switch branch..."
sleep $sleepTimeForBranchSwitch


# ビルド実行
.\gradlew build

# 成果物取得
Copy-Item -Path ".\build\libs\$buildJarNm" -Destination $javaBuildCompornentDir


# 一時ブランチ削除
git checkout master
git branch --delete $tmpBranchNm


### 4. アーカイブ ###
cd $workDirNm
tar -czvf $tarNm ".\$vueDepDir\*" ".\$javaDepDir\*"

### 5. サーバへ転送 ###
cd $workDirNm
scp -i $key $tarNm $scpUser@${address}:/tmp

cd $startDir
scp -i $key $deployShell $scpUser@${address}:/tmp

