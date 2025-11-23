@echo off
if "%1"=="" (
  echo Usage: test_ai.bat YOUR_ACCESS_TOKEN
  echo.
  echo First run test_api.bat to get your access token
  exit /b 1
)

set TOKEN=%1

echo ===== Testing AI Reply Generation =====
echo.

echo [1/3] Testing Humorous Tone...
curl -X POST http://localhost:8080/api/v1/ai/generate-reply ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer %TOKEN%" ^
  -d "{\"receivedMessage\":\"Hi! How are you doing today?\",\"toneStyle\":\"Humorous\"}"
echo.
echo.

echo [2/3] Testing Romantic Tone...
curl -X POST http://localhost:8080/api/v1/ai/generate-reply ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer %TOKEN%" ^
  -d "{\"receivedMessage\":\"I miss you so much...\",\"toneStyle\":\"Romantic\"}"
echo.
echo.

echo [3/3] Testing Professional Tone...
curl -X POST http://localhost:8080/api/v1/ai/generate-reply ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer %TOKEN%" ^
  -d "{\"receivedMessage\":\"Can we schedule a meeting next week?\",\"toneStyle\":\"Professional\"}"
echo.
echo.

echo ===== All AI Tests Completed! =====
pause
