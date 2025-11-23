# TalkGenius 測試指南

## 📋 目錄

1. [環境檢查](#1-環境檢查)
2. [後端 API 測試](#2-後端-api-測試)
3. [Android 測試](#3-android-測試)
4. [iOS 測試](#4-ios-測試)
5. [端到端測試](#5-端到端測試)
6. [常見問題排查](#6-常見問題排查)

---

## 1. 環境檢查

### 1.1 檢查 Docker 服務狀態

```bash
cd "D:\claude mode\TalkGenius\docker"
docker-compose ps
```

**預期輸出：**
```
NAME                 STATUS
talkgenius-backend   Up (healthy)
talkgenius-mysql     Up (healthy)
talkgenius-redis     Up (healthy)
```

### 1.2 檢查服務端口

```bash
# Windows
netstat -ano | findstr "8080"  # Backend
netstat -ano | findstr "3307"  # MySQL
netstat -ano | findstr "6379"  # Redis
```

### 1.3 查看後端日誌

```bash
docker-compose logs -f backend
```

**成功啟動標誌：**
```
Started TalkGeniusBackendApplication in X.XXX seconds
```

---

## 2. 後端 API 測試

### 2.1 使用 cURL 測試（推薦）

#### 健康檢查

```bash
curl http://localhost:8080/actuator/health
```

**預期響應：**
```json
{"status":"UP"}
```

#### 用戶註冊

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"test@example.com\",\"password\":\"Test1234\",\"displayName\":\"Test User\"}"
```

**預期響應：**
```json
{
  "userId": "uuid-string",
  "email": "test@example.com",
  "displayName": "Test User",
  "accessToken": "eyJhbGci...",
  "refreshToken": "eyJhbGci...",
  "expiresIn": 3600,
  "isPremium": false
}
```

**保存 accessToken 用於後續測試！**

#### 用戶登入

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"test@example.com\",\"password\":\"Test1234\"}"
```

#### AI 回覆生成（需要認證）

```bash
curl -X POST http://localhost:8080/api/v1/ai/generate-reply \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d "{\"receivedMessage\":\"Hi! How are you doing today?\",\"toneStyle\":\"Humorous\",\"conversationContext\":null}"
```

**預期響應：**
```json
{
  "conversationId": "uuid-string",
  "generatedReply": "AI 生成的回覆內容",
  "toneStyle": "Humorous",
  "timestamp": "2025-11-15T..."
}
```

### 2.2 使用 Postman 測試

1. **導入 Postman Collection**（可選創建）
2. **設置環境變量：**
   - `base_url`: `http://localhost:8080/api/v1`
   - `access_token`: 從註冊/登入響應獲取

3. **測試流程：**
   - Register → 獲取 token
   - Login → 驗證認證
   - Generate Reply → 測試 AI 功能

### 2.3 Freemium 限制測試

**免費用戶限制：** 10 次/天

```bash
# 發送 11 次請求，第 11 次應該失敗
for i in {1..11}; do
  echo "Request $i:"
  curl -X POST http://localhost:8080/api/v1/ai/generate-reply \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
    -d "{\"receivedMessage\":\"Test message $i\",\"toneStyle\":\"Gentle\"}"
  echo -e "\n---"
done
```

**第 11 次預期響應：**
```json
{
  "error": "Rate limit exceeded",
  "status": 429
}
```

---

## 3. Android 測試

### 3.1 前置準備

1. **安裝 Android Studio**
2. **安裝 Android SDK (API 34)**
3. **配置模擬器或連接真機**

### 3.2 導入專案

```bash
cd "D:\claude mode\TalkGenius\android"
# 使用 Android Studio 打開此目錄
```

### 3.3 Gradle 同步

```bash
# 在 Android Studio 中
./gradlew clean build

# 或在命令行（需要 Android SDK）
gradlew.bat assembleDebug
```

### 3.4 配置 API 端點

**檔案:** `android/app/build.gradle.kts`

```kotlin
buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080/api/v1/\"")
```

> **注意：** `10.0.2.2` 是 Android 模擬器訪問 host 的特殊 IP

### 3.5 運行應用

```bash
# 啟動模擬器
emulator -avd Pixel_5_API_34

# 安裝應用
./gradlew installDebug

# 查看日誌
adb logcat | findstr TalkGenius
```

### 3.6 功能測試清單

#### 認證流程
- [ ] 打開應用顯示登入/註冊界面
- [ ] 註冊新用戶（email + password + displayName）
- [ ] 登入已存在用戶
- [ ] 錯誤處理（錯誤密碼、重複 email）

#### Dashboard
- [ ] 顯示歡迎訊息
- [ ] "Enable Keyboard" 按鈕跳轉到系統設置
- [ ] Logout 功能正常

#### 自定義鍵盤
- [ ] 在系統設置中啟用 TalkGenius Keyboard
- [ ] 在任意應用中切換到 TalkGenius 鍵盤
- [ ] 選擇文字後點擊 "Generate AI Reply"
- [ ] 切換語氣風格（8 種）
- [ ] 生成的回覆自動插入

#### 本地存儲
- [ ] 重啟應用後保持登入狀態
- [ ] 對話歷史保存
- [ ] 離線查看歷史記錄

### 3.7 單元測試

```bash
# 運行單元測試
./gradlew test

# 運行儀器測試
./gradlew connectedAndroidTest
```

---

## 4. iOS 測試

### 4.1 前置準備

1. **安裝 Xcode 15+**
2. **安裝 CocoaPods**

```bash
sudo gem install cocoapods
```

### 4.2 安裝依賴

```bash
cd "D:\claude mode\TalkGenius\ios"
pod install
```

### 4.3 創建 Xcode 專案

**由於我們只創建了源代碼，需要手動創建 Xcode 專案：**

1. 打開 Xcode
2. Create a new Xcode project
3. 選擇 **iOS App**
4. 填寫：
   - Product Name: `TalkGenius`
   - Organization Identifier: `com.talkgenius`
   - Interface: `Storyboard`
   - Language: `Swift`

5. 添加 Keyboard Extension Target:
   - File → New → Target
   - 選擇 **Custom Keyboard Extension**
   - Product Name: `TalkGeniusKeyboard`

6. 將創建的源文件拖入對應 Target

### 4.4 配置 API 端點

**檔案:** `ios/TalkGenius/Networking/APIConfig.swift`

```swift
#if DEBUG
static let baseURL = "http://localhost:8080/api/v1"
#else
static let baseURL = "https://api.talkgenius.com/api/v1"
#endif
```

> **注意：** iOS 模擬器可以直接使用 `localhost`

### 4.5 運行應用

1. 選擇模擬器（iPhone 15 Pro 推薦）
2. 點擊 Run (Cmd + R)
3. 允許網絡權限（NSAppTransportSecurity）

### 4.6 功能測試清單

#### 認證流程
- [ ] 啟動顯示登入/註冊界面
- [ ] 切換 Login/Register Segmented Control
- [ ] 註冊新用戶
- [ ] 登入測試
- [ ] 錯誤提示正確顯示

#### Dashboard
- [ ] 顯示用戶名稱
- [ ] "Enable Keyboard" 打開系統設置
- [ ] Logout 返回登入界面

#### 自定義鍵盤
- [ ] Settings → General → Keyboard → Keyboards → Add New Keyboard
- [ ] 選擇 TalkGenius
- [ ] 啟用 "Allow Full Access"
- [ ] 在 Messages 等應用測試鍵盤
- [ ] 測試 AI 回覆生成
- [ ] 測試語氣風格選擇器

#### CoreData 存儲
- [ ] 重啟應用保持登入
- [ ] 對話歷史保存
- [ ] Keychain 正確存儲 Token

### 4.7 單元測試

```bash
# 在 Xcode 中
Cmd + U

# 或命令行
xcodebuild test -scheme TalkGenius -destination 'platform=iOS Simulator,name=iPhone 15 Pro'
```

---

## 5. 端到端測試

### 5.1 完整用戶流程測試

#### Android 流程

1. **新用戶註冊**
   - 打開應用
   - 點擊 Register
   - 輸入 `user1@test.com` / `Test1234` / `User One`
   - 驗證自動跳轉到 Dashboard

2. **啟用鍵盤**
   - 點擊 "Enable Keyboard"
   - 在系統設置啟用 TalkGenius
   - 授予必要權限

3. **使用 AI 功能**
   - 打開 WhatsApp/Messages
   - 收到訊息："Hey! Want to grab coffee?"
   - 選中文字
   - 切換到 TalkGenius 鍵盤
   - 點擊 "Generate AI Reply"
   - 選擇語氣：Flirty
   - 驗證生成回覆並插入

4. **測試上下文記憶**
   - 再次生成回覆
   - 驗證 AI 記得之前的對話

5. **登出並重新登入**
   - Logout
   - 重新登入
   - 驗證對話歷史保留

#### iOS 流程

（同 Android 流程，界面略有不同）

### 5.2 多設備測試

1. **在 Android 設備註冊**
2. **在 iOS 設備用相同帳號登入**
3. **驗證數據同步**（對話歷史、Premium 狀態）

### 5.3 Premium 功能測試

#### 升級到 Premium（手動數據庫操作）

```bash
# 連接 MySQL
docker exec -it talkgenius-mysql mysql -u talkgenius -ptalkgenius_dev_2025

# 切換數據庫
USE talkgenius;

# 升級用戶
UPDATE users SET is_premium = true WHERE email = 'test@example.com';

# 退出
EXIT;
```

#### 驗證無限制使用

```bash
# 發送 20 次請求，全部應成功
for i in {1..20}; do
  echo "Premium Request $i:"
  curl -X POST http://localhost:8080/api/v1/ai/generate-reply \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
    -d "{\"receivedMessage\":\"Test $i\",\"toneStyle\":\"Professional\"}"
done
```

---

## 6. 常見問題排查

### 6.1 後端問題

#### 問題：Backend 啟動失敗

**檢查：**
```bash
docker-compose logs backend
```

**可能原因：**
- MySQL 未準備就緒 → 等待 30 秒重試
- OpenAI API Key 缺失 → 檢查 `.env` 文件
- 端口 8080 被占用 → 關閉其他服務

**解決：**
```bash
# 重啟服務
docker-compose restart backend

# 完全重建
docker-compose down
docker-compose up -d --build
```

#### 問題：401 Unauthorized

**檢查：**
- Token 是否正確？
- Token 是否過期？（1 小時）
- Authorization header 格式：`Bearer <token>`

**解決：**
```bash
# 刷新 Token
curl -X POST http://localhost:8080/api/v1/auth/refresh \
  -H "Content-Type: application/json" \
  -d "{\"refreshToken\":\"YOUR_REFRESH_TOKEN\"}"
```

### 6.2 Android 問題

#### 問題：Gradle 同步失敗

**解決：**
```bash
# 清理緩存
./gradlew clean

# 重新同步
./gradlew build --refresh-dependencies
```

#### 問題：無法連接後端

**檢查：**
- 使用 `10.0.2.2` 而非 `localhost`
- 後端正在運行
- Cleartext traffic 已允許（已在 AndroidManifest 配置）

**測試連接：**
```bash
adb shell curl http://10.0.2.2:8080/actuator/health
```

#### 問題：Hilt 依賴注入錯誤

**檢查：**
- `@HiltAndroidApp` 註解在 Application 類
- `@AndroidEntryPoint` 註解在 Activity/Service
- Rebuild 專案

### 6.3 iOS 問題

#### 問題：Pod install 失敗

**解決：**
```bash
# 更新 CocoaPods
sudo gem install cocoapods

# 清理緩存
pod cache clean --all
pod deintegrate
pod install
```

#### 問題：Keychain 訪問被拒

**檢查：**
- 啟用 Keychain Sharing Capability
- App Groups 配置正確

#### 問題：Keyboard Extension 無法訪問網絡

**解決：**
- 在系統設置中啟用 "Allow Full Access"
- 檢查 Info.plist 中 `RequestsOpenAccess` 設為 `true`

### 6.4 通用問題

#### 問題：AI 回覆生成失敗

**檢查：**
1. OpenAI API Key 是否有效？
2. 是否有足夠的 API 額度？
3. 網絡連接正常？

**查看錯誤：**
```bash
# 後端日誌
docker-compose logs -f backend | grep ERROR
```

#### 問題：數據庫連接失敗

**檢查：**
```bash
# MySQL 健康狀態
docker-compose ps mysql

# 手動連接測試
docker exec -it talkgenius-mysql mysql -u talkgenius -ptalkgenius_dev_2025 -e "SHOW DATABASES;"
```

---

## 7. 性能測試

### 7.1 負載測試

```bash
# 安裝 Apache Bench
# Windows: 下載 Apache httpd

# 100 並發請求
ab -n 1000 -c 100 -H "Authorization: Bearer YOUR_TOKEN" \
  -p ai_request.json -T application/json \
  http://localhost:8080/api/v1/ai/generate-reply
```

**ai_request.json:**
```json
{"receivedMessage":"Test","toneStyle":"Humorous"}
```

### 7.2 內存監控

```bash
# Docker 容器資源使用
docker stats

# Android 內存分析
adb shell dumpsys meminfo com.talkgenius

# iOS 內存分析
# 使用 Xcode Instruments → Allocations
```

---

## 8. 自動化測試腳本

### 8.1 後端 API 自動化測試腳本

創建 `test_api.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/v1"

# 測試健康檢查
echo "1. Health Check..."
curl -s $BASE_URL/../actuator/health | grep -q "UP" && echo "✓ PASS" || echo "✗ FAIL"

# 測試註冊
echo "2. Register..."
RESPONSE=$(curl -s -X POST $BASE_URL/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"auto-test@example.com","password":"Test1234"}')
ACCESS_TOKEN=$(echo $RESPONSE | grep -oP '(?<="accessToken":")[^"]*')
[ -n "$ACCESS_TOKEN" ] && echo "✓ PASS" || echo "✗ FAIL"

# 測試 AI 回覆
echo "3. Generate Reply..."
curl -s -X POST $BASE_URL/ai/generate-reply \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -d '{"receivedMessage":"Test","toneStyle":"Humorous"}' \
  | grep -q "generatedReply" && echo "✓ PASS" || echo "✗ FAIL"

echo "All tests completed!"
```

運行：
```bash
bash test_api.sh
```

---

## 9. 推薦測試順序

### 階段 1: 後端驗證（30 分鐘）
1. ✅ 檢查 Docker 服務狀態
2. ✅ 測試健康端點
3. ✅ 測試用戶註冊/登入
4. ✅ 測試 AI 回覆生成
5. ✅ 測試 Freemium 限制

### 階段 2: Android 基礎測試（1 小時）
1. ✅ 導入專案並 Gradle 同步
2. ✅ 運行應用到模擬器
3. ✅ 測試認證流程
4. ✅ 測試 Dashboard

### 階段 3: Android 鍵盤測試（1 小時）
1. ✅ 啟用自定義鍵盤
2. ✅ 測試 AI 回覆生成
3. ✅ 測試語氣風格切換
4. ✅ 測試上下文記憶

### 階段 4: iOS 測試（2 小時）
1. ✅ 創建 Xcode 專案
2. ✅ 安裝 CocoaPods 依賴
3. ✅ 測試認證流程
4. ✅ 測試鍵盤擴展

### 階段 5: 端到端測試（1 小時）
1. ✅ 完整用戶流程
2. ✅ 跨平台數據同步
3. ✅ Premium 功能測試

---

## 10. 測試報告模板

```markdown
# TalkGenius 測試報告

**測試日期：** YYYY-MM-DD
**測試人員：** Your Name
**版本：** v1.0.0

## 測試環境
- 後端：✅ Running on localhost:8080
- Android：✅ Pixel 5 Emulator (API 34)
- iOS：⬜ (待測試)

## 測試結果

### 後端 API (8/8)
- [x] Health Check
- [x] Register
- [x] Login
- [x] Generate Reply
- [x] Refresh Token
- [x] Logout
- [x] Freemium Limit
- [x] Premium Unlimited

### Android (10/10)
- [x] 應用啟動
- [x] 用戶註冊
- [x] 用戶登入
- [x] Dashboard 顯示
- [x] 鍵盤啟用
- [x] AI 回覆生成
- [x] 語氣風格切換
- [x] 歷史記錄保存
- [x] Logout
- [x] 重新登入

### iOS (0/10)
- [ ] 待測試

## 發現的問題
1. 無

## 建議
1. 添加更多單元測試
2. 實現 UI 自動化測試
3. 添加性能監控

## 總結
Android 平台測試完成，功能正常。iOS 待創建 Xcode 專案後測試。
```

---

## 11. 快速測試指令參考

```bash
# ===== 後端 =====
# 啟動服務
cd docker && docker-compose up -d

# 查看日誌
docker-compose logs -f backend

# 健康檢查
curl http://localhost:8080/actuator/health

# ===== Android =====
# 構建
cd android && ./gradlew assembleDebug

# 安裝
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 查看日誌
adb logcat -s TalkGenius:V

# ===== iOS =====
# 安裝依賴
cd ios && pod install

# 打開專案
open TalkGenius.xcworkspace

# ===== 數據庫 =====
# 連接 MySQL
docker exec -it talkgenius-mysql mysql -u talkgenius -p

# 查看數據
docker exec -it talkgenius-mysql mysql -u talkgenius -ptalkgenius_dev_2025 -e "SELECT * FROM talkgenius.users;"
```

---

**祝測試順利！** 如有問題請參考常見問題排查章節。🚀
