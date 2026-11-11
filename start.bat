@echo off
cd /d "%~dp0"
title Tabla de Tiempos

where java >nul 2>&1
if errorlevel 1 (
    echo.
    echo No se ha encontrado Java en este equipo.
    echo Descarga Java 17 o superior desde: https://adoptium.net
    echo.
    pause
    exit /b 1
)

set ABRIR_NAVEGADOR=true
java -jar tabla-tiempos.jar

echo.
echo La aplicacion se ha cerrado.
pause
