@echo off
:: Paths
set BIN_DIR=bin
set DEPLOY_DIR=build
set JAR_NAME=project.jar

echo Cleaning up previous deployment...
if exist %DEPLOY_DIR%\%JAR_NAME% del %DEPLOY_DIR%\%JAR_NAME%

echo Creating JAR file...
jar cvf %DEPLOY_DIR%\%JAR_NAME% -C %BIN_DIR% .

echo Deployment completed successfully.
pause
