@echo off
:START
for %%i in (.\build\libs\DreamHouse-bot-*-all.jar) do (
    java -jar %%i
    echo Bot disconnected.
    goto :BREAK
)
:BREAK
echo Restarting...
set timer=10

:RESTART
rem discord.com ip
ping -n 1 162.159.138.232
set error=%errorlevel%
timeout %timer%

if %error% equ 0 (
    goto :START 
) else (
    set oldTimer=timer
    set /a timer=%oldTimer% * 2
    goto :RESTART
)
pause