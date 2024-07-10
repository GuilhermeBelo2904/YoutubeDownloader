@echo off
REM Recursively delete all .class files
for /r %%i in (*.class) do (
    del "%%i"
)