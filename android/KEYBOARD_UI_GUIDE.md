# TalkGenius 鍵盤 UI 實現指南

## 📱 概述

TalkGenius 自定義鍵盤 UI 已完成實現，採用 **Jetpack Compose** 構建，提供符合 LoveAssist 設計風格的粉紅色主題界面。

---

## ✨ 功能特色

### 1. **視覺設計**
- 🎨 粉紅/珊瑚色配色方案 (`#FF6B9D`)
- 💕 "LoveAssist" 品牌標識與愛心圖標
- 📊 顯示使用進度 (30%)

### 2. **核心功能**

#### 輸入區域
- 📝 文本輸入框 - 支持手動輸入或粘貼對話內容
- 📋 粘貼按鈕 - 一鍵從剪貼簿讀取 LINE 訊息
- 提示文字：「點擊此處粘貼 TA 的對話」

#### 語氣風格選擇 (9 種)
1. 💕 **溫柔** - Gentle
2. 🌟 **高情商** - High EQ
3. 😄 **幽默** - Humorous
4. 🥰 **可愛** - Cute
5. 😘 **撤嬌** - Romantic
6. 🤖 **智能回復** - Professional
7. 💋 **曖味拉扯** - Flirty
8. 🌸 **林黛玉式** - Gentle
9. 👑 **甄嬛文學** - Professional

#### 操作按鈕
- 🗑️ **清空** - 清除輸入內容
- 🚀 **發送** - 生成 AI 回覆並插入到輸入框

#### 底部工具列
- 🌐 **語言選擇器** - 中文/English
- 🎤 **語音輸入** - 未來功能

---

## 🏗️ 架構設計

### 文件結構

```
android/app/src/main/java/com/talkgenius/
├── keyboard/
│   ├── InputMethodLifecycleService.kt  # 基礎生命週期支持
│   ├── TalkGeniusKeyboardService.kt    # 主要鍵盤服務
│   └── ui/
│       └── KeyboardView.kt             # Compose UI 組件
├── ui/
│   ├── viewmodel/
│   │   └── KeyboardViewModel.kt        # ViewModel
│   └── theme/
│       └── Theme.kt                    # 主題配置
└── data/
    └── model/
        └── ToneStyle.kt                # 語氣風格枚舉
```

### 主要組件說明

#### 1. `InputMethodLifecycleService.kt`
**目的**: 為 InputMethodService 提供 Lifecycle、ViewModel 和 SavedState 支持

```kotlin
abstract class InputMethodLifecycleService : InputMethodService(),
    LifecycleOwner,
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    // 提供 Lifecycle 管理
    private val lifecycleRegistry = LifecycleRegistry(this)

    // 提供 ViewModel 支持
    override val viewModelStore: ViewModelStore = ViewModelStore()

    // 提供狀態保存與恢復
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
}
```

**為什麼需要？**
- InputMethodService 預設不是 LifecycleOwner
- Compose 需要 LifecycleOwner 來管理組件生命週期
- ViewModel 需要 ViewModelStoreOwner 來存儲和管理狀態

#### 2. `TalkGeniusKeyboardService.kt`
**主要職責**:
- ✅ 創建和管理 Compose 鍵盤 UI
- ✅ 處理剪貼簿讀取
- ✅ 呼叫 AI API 生成回覆
- ✅ 將生成的回覆插入到輸入框

**關鍵方法**:

```kotlin
override fun onCreateInputView(): View {
    keyboardView = ComposeView(this).apply {
        // 設置 Lifecycle、ViewModel 和 SavedState
        setViewTreeLifecycleOwner(this@TalkGeniusKeyboardService)
        setViewTreeViewModelStoreOwner(this@TalkGeniusKeyboardService)
        setViewTreeSavedStateRegistryOwner(this@TalkGeniusKeyboardService)

        setContent {
            TalkGeniusTheme {
                KeyboardView(
                    onPasteClick = { handlePaste() },
                    onToneSelected = { tone -> setToneStyle(tone) },
                    onClearClick = { inputText = "" },
                    onSendClick = { handleSend() },
                    inputText = inputText,
                    onInputChange = { inputText = it },
                    isLoading = isLoading
                )
            }
        }
    }
    return keyboardView!!
}
```

**處理粘貼**:
```kotlin
private fun handlePaste() {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = clipboard.primaryClip

    if (clipData != null && clipData.itemCount > 0) {
        val text = clipData.getItemAt(0).text?.toString()
        if (!text.isNullOrBlank()) {
            inputText = text
            showToast("已粘貼文本")
        }
    }
}
```

**生成 AI 回覆**:
```kotlin
private fun handleSend() {
    if (inputText.isBlank()) {
        showToast("請先輸入或粘貼訊息")
        return
    }

    lifecycleScope.launch {
        isLoading = true
        val context = viewModel.buildConversationContext()
        viewModel.generateReply(
            receivedMessage = inputText,
            toneStyle = currentToneStyle,
            conversationContext = context
        )
    }
}
```

**插入回覆到輸入框**:
```kotlin
private fun insertGeneratedReply(reply: String) {
    val ic = currentInputConnection ?: return
    ic.commitText(reply, 1)
}
```

#### 3. `KeyboardView.kt`
**Compose UI 組件結構**:

```
KeyboardView
│
├── HeaderSection
│   └── "LoveAssist" + ❤️ + 30%
│
├── InputSection
│   ├── TextField (輸入框)
│   └── Button (粘貼按鈕)
│
├── ToneStyleGrid (3x3 格子)
│   └── ToneButton × 9
│       ├── Emoji
│       └── Label
│
├── ActionButtons
│   ├── OutlinedButton (清空)
│   └── Button (發送)
│
└── BottomToolbar
    ├── Language Selector
    └── Voice Input Icon
```

**主要 Composable 函數**:

```kotlin
@Composable
fun KeyboardView(
    onPasteClick: () -> Unit,
    onToneSelected: (ToneStyle) -> Unit,
    onClearClick: () -> Unit,
    onSendClick: () -> Unit,
    inputText: String,
    onInputChange: (String) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    // UI 實現
}
```

---

## 🎨 顏色主題

```kotlin
// 主要粉紅色
val primaryPink = Color(0xFFFF6B9D)

// 淺粉色背景
val lightPink = Color(0xFFFFF0F5)

// 文字灰色
val textGray = Color(0xFF666666)

// 背景白色
val backgroundColor = Color(0xFFFFFBFE)
```

---

## 🔄 工作流程

### 用戶使用流程

```
1. 用戶在 LINE 中複製對方訊息
   ↓
2. 切換到 TalkGenius 鍵盤
   ↓
3. 點擊「粘貼」按鈕
   ↓ (從剪貼簿讀取)
4. 訊息顯示在輸入框
   ↓
5. 選擇語氣風格 (例: 幽默 😄)
   ↓
6. 點擊「發送」按鈕
   ↓ (呼叫 AI API)
7. AI 生成回覆
   ↓ (插入到輸入框)
8. 回覆自動出現在對話中
```

### 內部數據流

```
KeyboardView (UI)
    ↓ 用戶點擊「發送」
TalkGeniusKeyboardService.handleSend()
    ↓ 調用 ViewModel
KeyboardViewModel.generateReply()
    ↓ 調用 Repository
AIRepository.generateReply()
    ↓ 調用 API Service
AIApiService.generateReply()
    ↓ HTTP POST
Backend API (/api/v1/ai/generate-reply)
    ↓ AI 處理
OpenAI GPT-4 API
    ↓ 返回回覆
AIReplyState (StateFlow)
    ↓ Observe
TalkGeniusKeyboardService (收到 state.reply)
    ↓ 調用
insertGeneratedReply(reply)
    ↓ 使用
currentInputConnection.commitText(reply, 1)
    ↓ 插入
用戶輸入框 (LINE, WhatsApp, etc.)
```

---

## 🧪 測試指南

### 前置條件
1. ✅ 後端運行在 `http://10.0.2.2:8080` (模擬器) 或實際 IP
2. ✅ 已註冊並登入 TalkGenius 帳號
3. ✅ 已在 Android 設置中啟用 TalkGenius 鍵盤

### 啟用鍵盤步驟
```
1. 設置 → 系統 → 語言與輸入
2. 虛擬鍵盤
3. 管理鍵盤
4. 啟用 "TalkGenius"
5. 在任何輸入框長按選擇鍵盤
6. 選擇 "TalkGenius"
```

### 測試場景

#### 場景 1: 基本粘貼和生成
1. 在任意 App 複製一段文字 (例: "Hey, how was your day?")
2. 切換到 TalkGenius 鍵盤
3. 點擊「粘貼」按鈕
4. 驗證文字出現在輸入框
5. 選擇「幽默 😄」語氣
6. 點擊「發送」
7. 等待 AI 生成回覆
8. 驗證回覆插入到輸入框

#### 場景 2: 清空功能
1. 在輸入框輸入文字
2. 點擊「清空」按鈕
3. 驗證輸入框已清空

#### 場景 3: 語氣切換
1. 選擇不同的語氣風格按鈕
2. 驗證 Toast 顯示 "Tone style: [選擇的語氣]"
3. 生成回覆時驗證語氣正確

#### 場景 4: Loading 狀態
1. 點擊「發送」後
2. 驗證按鈕顯示 Loading 動畫
3. 驗證按鈕被禁用 (無法重複點擊)
4. 等待回覆生成後恢復正常

#### 場景 5: 錯誤處理
1. 關閉後端或網絡
2. 嘗試生成回覆
3. 驗證顯示錯誤 Toast

---

## 🐛 常見問題排查

### 問題 1: 鍵盤 UI 不顯示
**可能原因**:
- InputMethodService 未註冊到 AndroidManifest.xml
- Compose 依賴缺失

**解決方案**:
```xml
<!-- AndroidManifest.xml -->
<service
    android:name=".keyboard.TalkGeniusKeyboardService"
    android:permission="android.permission.BIND_INPUT_METHOD"
    android:exported="true">
    <intent-filter>
        <action android:name="android.view.InputMethod" />
    </intent-filter>
    <meta-data
        android:name="android.view.im"
        android:resource="@xml/method" />
</service>
```

### 問題 2: 粘貼按鈕無反應
**可能原因**:
- 剪貼簿權限問題
- 剪貼簿為空

**解決方案**:
```kotlin
// 確保權限已聲明
<uses-permission android:name="android.permission.READ_CLIPBOARD" />

// 檢查剪貼簿內容
val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
Log.d("Keyboard", "Clipboard has data: ${clipboard.hasPrimaryClip()}")
```

### 問題 3: AI 回覆未插入
**可能原因**:
- currentInputConnection 為 null
- API 調用失敗

**解決方案**:
```kotlin
private fun insertGeneratedReply(reply: String) {
    val ic = currentInputConnection
    if (ic == null) {
        Log.e("Keyboard", "InputConnection is null!")
        showToast("無法插入文字")
        return
    }
    ic.commitText(reply, 1)
}
```

### 問題 4: Compose 狀態不更新
**可能原因**:
- Lifecycle 未正確設置
- 使用了錯誤的 State 聲明方式

**解決方案**:
```kotlin
// ✅ 正確: 使用 mutableStateOf
private var inputText by mutableStateOf("")

// ❌ 錯誤: 普通變量
private var inputText = ""

// ✅ 確保 setViewTree* 正確調用
ComposeView(this).apply {
    setViewTreeLifecycleOwner(this@TalkGeniusKeyboardService)
    setViewTreeViewModelStoreOwner(this@TalkGeniusKeyboardService)
    setViewTreeSavedStateRegistryOwner(this@TalkGeniusKeyboardService)
}
```

---

## 📊 性能優化建議

### 1. 減少重組 (Recomposition)
```kotlin
@Composable
fun ToneButton(
    label: String,
    emoji: String,
    onClick: () -> Unit,
    primaryPink: Color
) {
    // 使用 remember 和 derivedStateOf 減少重組
    val backgroundColor = remember(primaryPink) {
        Color.White
    }
}
```

### 2. LazyGrid 優化
```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    // 設置 contentPadding 提升滾動性能
    contentPadding = PaddingValues(8.dp)
) {
    items(toneStyles, key = { it.toneStyle }) { style ->
        ToneButton(...)
    }
}
```

### 3. 避免在 Composable 中進行重量級操作
```kotlin
// ❌ 錯誤: 在 Composable 中調用 API
@Composable
fun KeyboardView() {
    LaunchedEffect(Unit) {
        // API 調用應該在 ViewModel 中
        viewModel.fetchData()
    }
}
```

---

## 🚀 未來功能擴展

### 1. 語音輸入
```kotlin
// BottomToolbar 中的語音按鈕
IconButton(
    onClick = {
        // TODO: 實現語音識別功能
        // 使用 Android Speech Recognizer API
    }
) {
    Icon(imageVector = Icons.Default.Mic, ...)
}
```

### 2. 語言切換
```kotlin
// 支持多語言回覆
var selectedLanguage by remember { mutableStateOf("zh-TW") }

IconButton(
    onClick = {
        selectedLanguage = when (selectedLanguage) {
            "zh-TW" -> "en-US"
            "en-US" -> "ja-JP"
            else -> "zh-TW"
        }
    }
) {
    Text(text = selectedLanguage)
}
```

### 3. 歷史記錄
```kotlin
// 添加一個按鈕顯示最近的對話和回覆
@Composable
fun HistoryDrawer(
    conversations: List<Conversation>,
    onSelectConversation: (Conversation) -> Unit
) {
    LazyColumn {
        items(conversations) { conversation ->
            ConversationCard(conversation, onSelectConversation)
        }
    }
}
```

### 4. 自定義語氣
```kotlin
// 允許用戶創建自己的語氣風格
data class CustomTone(
    val name: String,
    val emoji: String,
    val prompt: String  // 用戶自定義的 AI Prompt
)
```

---

## 📚 參考資源

- [Android InputMethodService 官方文檔](https://developer.android.com/reference/android/inputmethodservice/InputMethodService)
- [Jetpack Compose 官方指南](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [TalkGenius API 文檔](../backend/README.md)

---

## 📞 支援

遇到問題？請聯繫:
- **Email**: bobchen184@gmail.com
- **GitHub Issues**: [TalkGenius Issues](https://github.com/yanchen184/TalkGenius/issues)

---

**文檔版本**: v1.0.0
**最後更新**: 2025-11-23
**狀態**: ✅ 已實現並可測試
