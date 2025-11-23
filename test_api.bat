@echo off
cls
echo ========================================
echo   TalkGenius API 測試工具啟動器
echo ========================================
echo.

REM 檢查 Node.js
where node >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo [錯誤] 未找到 Node.js，請先安裝 Node.js 20.19 或更新版本
    echo 下載連結: https://nodejs.org/
    pause
    exit /b 1
)

echo [✓] Node.js 已安裝
node --version
echo.

REM 檢查 npm
where npm >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo [錯誤] 未找到 npm
    pause
    exit /b 1
)

echo [✓] npm 已安裝
npm --version
echo.

REM 進入專案目錄
cd api-test-ui

REM 檢查 node_modules
if not exist "node_modules" (
    echo [!] 未找到 node_modules，正在安裝依賴...
    echo.
    npm install
    if %ERRORLEVEL% neq 0 (
        echo [錯誤] 依賴安裝失敗
        pause
        exit /b 1
    )
)

echo ========================================
echo   準備啟動開發伺服器...
echo ========================================
echo.
echo [提示] 應用將在 http://localhost:3000 運行
echo [提示] 確保後端在 http://localhost:8080 運行
echo [提示] 按 Ctrl+C 停止伺服器
echo.
timeout /t 2 >nul

REM 啟動開發伺服器
npm run dev

pause
