#!/bin/bash

### define const ###

tarNm="deployFile.tar.gz"
exDir="buildWork"
vueDeployDir="★{vueファイルアップロードディレクトリ}"
javaDeployDir="★{jarファイルアップロードディレクトリ}"


## extract
cd /tmp
sudo tar zxvf $tarNm

## vue deploy
# stop apache service
sudo systemctl stop apache2.service

# replace file
sudo rm -rf ${vueDeployDir}css ${vueDeployDir}js ${vueDeployDir}favicon.ico ${vueDeployDir}index.html
sudo cp -rf ./html/* ${vueDeployDir}

# start apache service
sudo systemctl start apache2.service


## java deploy
# stop shopping-app-backend service
sudo systemctl stop shopping_help_app_back.service

# replace file
sudo cp -rf ./shopping_help_app_back/* ${javaDeployDir}

# start shopping-app-backend service
sudo systemctl start shopping_help_app_back.service

## clean up
cd /tmp
sudo rm -rf ${tarNm} html/ shopping_help_app_back/

# release check
sudo ls -l ${vueDeployDir} ${vueDeployDir}css ${vueDeployDir}js ${javaDeployDir}
