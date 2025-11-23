# AI Dating Assistant Keyboard - Product Specification Document

## 1. Product Overview

### 1.1 Product Name
**TalkGenius ** 

### 1.2 Product Description
一款結合 AI 技術的智能戀愛助手鍵盤應用，幫助使用者在聊天時獲得即時的回覆建議，提升溝通品質和情感互動效果。

### 1.3 Target Audience
- 年齡層：18-35 歲
- 主要用戶：
  - 正在約會或追求對象的單身人士
  - 希望改善溝通技巧的戀愛新手
  - 需要聊天靈感的社交媒體使用者
  - 異地戀情侶

### 1.4 Core Value Proposition
- 即時 AI 回覆建議，無需切換 App
- 多種語氣風格選擇，適應不同對話情境
- 24/7 情感諮詢師功能
- 提升聊天品質，增加戀愛成功率

---

## 2. Technical Architecture

### 2.1 Platform Support
- **Primary**: Android (優先開發)
- **Secondary**: iOS (第二階段)

### 2.2 Technology Stack

#### Frontend
- **Android**:
  - Language: Kotlin
  - UI Framework: Jetpack Compose
  - Keyboard Service: InputMethodService
  - Minimum SDK: Android 8.0 (API 26)
  
- **iOS** (Future):
  - Language: Swift
  - UI Framework: SwiftUI
  - Keyboard Extension: Custom Keyboard Extension
  - Minimum iOS: 14.0

#### Backend
- **API Server**:
  - Framework: Spring Boot (Java)
  - Database: MySQL / PostgreSQL
  - Cache: Redis
  - Message Queue: RabbitMQ / Kafka (for async processing)

#### AI Integration
- **Primary AI Model**: 
  - OpenAI GPT-4 / GPT-4 Turbo API
  - Anthropic Claude API (backup option)
  
- **Local Processing** (Optional):
  - Ollama for offline basic features
  - TensorFlow Lite for on-device inference

#### Cloud Infrastructure
- **Hosting**: AWS / Google Cloud Platform / Azure
- **CDN**: CloudFlare
- **Storage**: S3 / Cloud Storage
- **Analytics**: Firebase Analytics / Mixpanel

### 2.3 System Architecture

```
┌─────────────────────────────────────────────────┐
│           User's Device (Android/iOS)           │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌──────────────────────────────────────────┐  │
│  │        Custom Keyboard Component         │  │
│  │  - Input Handler                         │  │
│  │  - UI Renderer                           │  │
│  │  - Clipboard Monitor                     │  │
│  └──────────────────────────────────────────┘  │
│                    ↕                            │
│  ┌──────────────────────────────────────────┐  │
│  │         Main App Component               │  │
│  │  - Settings Manager                      │  │
│  │  - Chat History                          │  │
│  │  - AI Companion                          │  │
│  │  - Emotional Coach                       │  │
│  └──────────────────────────────────────────┘  │
│                    ↕                            │
│  ┌──────────────────────────────────────────┐  │
│  │         Local Cache & Storage            │  │
│  │  - SQLite Database                       │  │
│  │  - Shared Preferences                    │  │
│  └──────────────────────────────────────────┘  │
│                                                 │
└────────────────────┬────────────────────────────┘
                     │ HTTPS / WebSocket
                     ↕
┌─────────────────────────────────────────────────┐
│              Backend API Server                 │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌──────────────────────────────────────────┐  │
│  │         API Gateway Layer                │  │
│  │  - Authentication (JWT)                  │  │
│  │  - Rate Limiting                         │  │
│  │  - Request Validation                    │  │
│  └──────────────────────────────────────────┘  │
│                    ↕                            │
│  ┌──────────────────────────────────────────┐  │
│  │         Business Logic Layer             │  │
│  │  - Reply Generation Service              │  │
│  │  - Tone Adjustment Service               │  │
│  │  - Chat Analysis Service                 │  │
│  │  - Emotional Coach Service               │  │
│  │  - User Management Service               │  │
│  └──────────────────────────────────────────┘  │
│                    ↕                            │
│  ┌──────────────────────────────────────────┐  │
│  │         AI Integration Layer             │  │
│  │  - OpenAI API Client                     │  │
│  │  - Prompt Engineering Module             │  │
│  │  - Response Parser                       │  │
│  │  - Context Manager                       │  │
│  └──────────────────────────────────────────┘  │
│                    ↕                            │
│  ┌──────────────────────────────────────────┐  │
│  │         Data Layer                       │  │
│  │  - MySQL Database                        │  │
│  │  - Redis Cache                           │  │
│  │  - Message Queue                         │  │
│  └──────────────────────────────────────────┘  │
│                                                 │
└─────────────────────────────────────────────────┘
```

---

## 3. Core Features Specification

### 3.1 Custom Keyboard Feature

#### 3.1.1 Keyboard UI Components
```
┌────────────────────────────────────────────┐
│         Suggestion Bar (Top)              │
│  [Suggestion 1] [Suggestion 2] [Suggestion 3] │
├────────────────────────────────────────────┤
│                                            │
│         Standard QWERTY Layout             │
│                                            │
├────────────────────────────────────────────┤
│  [AI] [Space] [Switch] [Emoji] [Settings] │
└────────────────────────────────────────────┘
```

#### 3.1.2 Keyboard Functions

**Basic Typing**
- Standard QWERTY layout
- Auto-correction
- Word prediction
- Multi-language support (繁體中文, 簡體中文, English)
- Emoji picker
- GIF support

**AI Integration Button**
- Icon: AI sparkle icon
- Position: Left side of space bar
- Function: Trigger AI reply generation
- Long press: Show tone selection menu

**AI Reply Generation Flow**
```
User copies/selects message
    ↓
Click AI button
    ↓
Show loading indicator
    ↓
Display 3-5 reply suggestions
    ↓
User selects one suggestion
    ↓
Insert into text field
    ↓
User can edit before sending
```

#### 3.1.3 Tone/Style Options

**Available Tones:**
1. **幽默風趣 (Humorous)**
   - 加入笑話、俏皮話
   - 適合輕鬆聊天

2. **高情商 (High EQ)**
   - 體貼、理解對方感受
   - 適合敏感話題

3. **溫柔體貼 (Gentle)**
   - 溫暖、關心
   - 適合安慰、關懷

4. **可愛俏皮 (Cute)**
   - 撒嬌、活潑
   - 適合親密關係

5. **文藝浪漫 (Romantic)**
   - 詩意、浪漫
   - 適合表達愛意

6. **專業禮貌 (Professional)**
   - 正式、得體
   - 適合初次認識

7. **直接坦率 (Direct)**
   - 簡潔、明確
   - 適合日常對話

8. **調情撩人 (Flirty)**
   - 暗示、曖昧
   - 適合關係升溫

#### 3.1.4 Context Detection

**Automatic Context Analysis**
- Detect conversation mood (happy, sad, angry, neutral)
- Identify question types (yes/no, open-ended, rhetorical)
- Recognize emojis and their meanings
- Understand relationship stage (strangers, dating, couple)

**Context Factors:**
- Time of day (morning greetings, goodnight messages)
- Message length (short reply vs. long conversation)
- Response time (immediate vs. delayed)
- Emoji usage frequency
- Exclamation marks and question marks

---

### 3.2 Main App Features

#### 3.2.1 Home Screen
```
┌────────────────────────────────────────┐
│  ☰  RomanceType         🔔  ⚙️         │
├────────────────────────────────────────┤
│                                        │
│  👤 User Profile Section               │
│  「Hi, [Username]」                    │
│  💎 Premium Member / Free User         │
│                                        │
├────────────────────────────────────────┤
│                                        │
│  Quick Actions:                        │
│  ┌──────────┐ ┌──────────┐           │
│  │ AI Reply │ │ Coach    │           │
│  │ 智能回覆  │ │ 情感顧問  │           │
│  └──────────┘ └──────────┘           │
│                                        │
│  ┌──────────┐ ┌──────────┐           │
│  │ History  │ │ Learning │           │
│  │ 對話記錄  │ │ 戀愛課程  │           │
│  └──────────┘ └──────────┘           │
│                                        │
├────────────────────────────────────────┤
│  Recent Conversations:                 │
│  • Chat with Sarah - 2 hours ago      │
│  • Chat with Mike - Yesterday         │
│                                        │
└────────────────────────────────────────┘
```

#### 3.2.2 AI Reply Generation (In-App)

**User Flow:**
1. User opens app
2. Click "Generate Reply" button
3. Paste or type the received message
4. Select tone/style
5. Click "Generate"
6. View 3-5 suggestions
7. Copy preferred reply
8. Return to chat app and paste

**UI Layout:**
```
┌────────────────────────────────────────┐
│  ← AI Reply Generator                  │
├────────────────────────────────────────┤
│                                        │
│  Received Message:                     │
│  ┌────────────────────────────────┐   │
│  │ [Paste or type message here]   │   │
│  │                                │   │
│  └────────────────────────────────┘   │
│                                        │
│  Select Tone:                          │
│  [😊 幽默] [🧠 高EQ] [💕 溫柔]        │
│  [🥰 可愛] [📖 文藝] [💼 專業]        │
│                                        │
│  [Generate Reply] 🪄                   │
│                                        │
├────────────────────────────────────────┤
│  Suggestions:                          │
│                                        │
│  1. [Humorous Reply]                   │
│     "哈哈，你這樣說我都不好意思了 😆"  │
│     [Copy] [Edit] [Regenerate]        │
│                                        │
│  2. [High EQ Reply]                    │
│     "謝謝你的分享，我很開心能..."      │
│     [Copy] [Edit] [Regenerate]        │
│                                        │
│  3. [Gentle Reply]                     │
│     "聽起來你今天過得不錯呢..."        │
│     [Copy] [Edit] [Regenerate]        │
│                                        │
└────────────────────────────────────────┘
```

#### 3.2.3 Emotional Coach / AI Consultant

**Features:**
- 24/7 AI chatbot for relationship advice
- Scenario-based guidance
- Personalized tips based on user profile

**Chat Interface:**
```
┌────────────────────────────────────────┐
│  ← Emotional Coach         💬  🗑️      │
├────────────────────────────────────────┤
│                                        │
│  👨‍💼 AI Coach                          │
│  ┌────────────────────────────────┐   │
│  │ Hi! I'm your emotional coach.  │   │
│  │ How can I help you today?      │   │
│  └────────────────────────────────┘   │
│                                        │
│  Common Questions:                     │
│  • How to start a conversation?       │
│  • She's not replying, what to do?    │
│  • How to ask someone out?            │
│  • Signs she/he likes me?             │
│                                        │
├────────────────────────────────────────┤
│  [Type your question...]         [Send]│
└────────────────────────────────────────┘
```

**Pre-set Scenarios:**
1. 如何開啟話題 (How to start conversations)
2. 約會邀約技巧 (Dating invitation tips)
3. 處理冷場 (Handling awkward silences)
4. 安慰對方 (Comforting techniques)
5. 表達好感 (Expressing interest)
6. 化解衝突 (Conflict resolution)
7. 維持長期關係 (Maintaining relationships)
8. 異地戀建議 (Long-distance relationship advice)

#### 3.2.4 Conversation History

**Purpose:**
- Save important chat analysis
- Track conversation patterns
- Review successful/failed attempts
- Learn from past interactions

**Data Stored:**
- Date and time
- Platform (WhatsApp, LINE, Instagram, etc.)
- Selected tone
- Generated replies
- User's final choice
- Optional: Outcome rating (Did it work? 1-5 stars)

**UI:**
```
┌────────────────────────────────────────┐
│  ← Conversation History    🔍  Filter   │
├────────────────────────────────────────┤
│                                        │
│  📅 November 14, 2025                  │
│  ┌────────────────────────────────┐   │
│  │ 💬 Chat with Sarah              │   │
│  │ Platform: WhatsApp              │   │
│  │ Tone: High EQ                   │   │
│  │ Success Rating: ⭐⭐⭐⭐⭐      │   │
│  └────────────────────────────────┘   │
│                                        │
│  📅 November 13, 2025                  │
│  ┌────────────────────────────────┐   │
│  │ 💬 Chat with Mike               │   │
│  │ Platform: Instagram             │   │
│  │ Tone: Humorous                  │   │
│  │ Success Rating: ⭐⭐⭐⭐        │   │
│  └────────────────────────────────┘   │
│                                        │
└────────────────────────────────────────┘
```

#### 3.2.5 Learning Center / Dating Tips

**Content Categories:**
1. **基礎溝通 (Communication Basics)**
   - Active listening techniques
   - Body language interpretation
   - Tone and timing

2. **約會技巧 (Dating Skills)**
   - First date ideas
   - Conversation topics
   - How to be more attractive

3. **關係維護 (Relationship Maintenance)**
   - Trust building
   - Conflict resolution
   - Keeping the spark alive

4. **心理學知識 (Psychology Knowledge)**
   - Attachment styles
   - Love languages
   - Common relationship patterns

**Content Format:**
- Articles (blog-style)
- Video tutorials
- Interactive quizzes
- Real-life case studies

#### 3.2.6 User Profile & Preferences

**Profile Information:**
- Name / Nickname
- Age
- Gender
- Relationship status
- Preferred tone styles
- Conversation style preferences

**Preferences Settings:**
```
┌────────────────────────────────────────┐
│  ← Settings                      Save  │
├────────────────────────────────────────┤
│                                        │
│  👤 Profile                            │
│  • Name: [Your Name]                   │
│  • Age: [25]                           │
│  • Gender: [Male/Female/Other]         │
│                                        │
│  🎨 Default Tone                       │
│  • Primary: [High EQ]                  │
│  • Secondary: [Humorous]               │
│                                        │
│  🔔 Notifications                      │
│  • Daily Tips: [ON/OFF]                │
│  • AI Updates: [ON/OFF]                │
│                                        │
│  🌐 Language                           │
│  • Interface: [繁體中文]               │
│  • Reply Language: [繁體中文]          │
│                                        │
│  🔒 Privacy                            │
│  • Save Chat History: [ON/OFF]         │
│  • Anonymous Mode: [ON/OFF]            │
│                                        │
│  💎 Subscription                       │
│  • Current Plan: [Free/Premium]        │
│  • Upgrade to Premium                  │
│                                        │
└────────────────────────────────────────┘
```

---

### 3.3 AI & Machine Learning Features

#### 3.3.1 AI Model Integration

**Primary Model: OpenAI GPT-4**
- API Endpoint: `https://api.openai.com/v1/chat/completions`
- Model: `gpt-4-turbo` or `gpt-4o`
- Temperature: 0.7 (for creative replies)
- Max Tokens: 150-200 per reply

**Prompt Engineering Structure:**
```
System Prompt:
You are a professional dating coach and conversation expert. 
Your goal is to help users create engaging, authentic, and 
appropriate responses in romantic conversations.

Context:
- User's relationship stage: {stage}
- Conversation mood: {mood}
- Selected tone: {tone}
- Time of day: {time}

User's received message:
"{received_message}"

Generate 3-5 reply suggestions in {language} with the {tone} 
tone. Each reply should be:
1. Natural and conversational
2. Appropriate for the relationship stage
3. Encourage further conversation
4. Show genuine interest
5. Be concise (1-3 sentences)

Format as JSON array:
[
  {"reply": "suggestion 1", "explanation": "why this works"},
  {"reply": "suggestion 2", "explanation": "why this works"},
  ...
]
```

#### 3.3.2 Context Analysis Engine

**Input Analysis:**
- Message sentiment (positive, neutral, negative)
- Question detection (yes/no, open-ended)
- Topic extraction (work, hobbies, feelings, plans)
- Emoji interpretation
- Urgency level (immediate response needed or not)

**Example Algorithm:**
```java
public class ContextAnalyzer {
    
    // Analyze received message
    public MessageContext analyzeMessage(String message) {
        MessageContext context = new MessageContext();
        
        // Sentiment analysis
        context.setSentiment(analyzeSentiment(message));
        
        // Question detection
        context.setQuestionType(detectQuestionType(message));
        
        // Topic extraction
        context.setTopics(extractTopics(message));
        
        // Emoji analysis
        context.setEmotionalTone(analyzeEmojis(message));
        
        // Urgency level
        context.setUrgency(calculateUrgency(message));
        
        return context;
    }
    
    private Sentiment analyzeSentiment(String message) {
        // Use NLP library or API call
        // Return POSITIVE, NEUTRAL, or NEGATIVE
    }
    
    private QuestionType detectQuestionType(String message) {
        if (message.contains("?")) {
            // Analyze if yes/no question or open-ended
        }
        return QuestionType.STATEMENT;
    }
    
    // More analysis methods...
}
```

#### 3.3.3 Personalization & Learning

**User Behavior Tracking:**
- Which tone styles user prefers
- Which suggestions user frequently selects
- Success rate of different reply types
- Time patterns (active hours)

**Machine Learning Component:**
- Collect anonymous usage data
- Train custom model to improve suggestions
- A/B testing different prompt strategies
- Continuous improvement based on feedback

**Personalization Features:**
- Adjust default tone based on user's choices
- Learn user's writing style
- Remember successful conversation patterns
- Suggest best time to message

---

## 4. User Experience Flow

### 4.1 Onboarding Flow

```
Launch App
    ↓
Splash Screen (2 seconds)
    ↓
Welcome Screen
    ↓
[Option 1: Login/Register]
    ↓
Profile Setup
- Name
- Age
- Gender
- Relationship status
    ↓
Keyboard Permission Request
    ↓
Tutorial (Interactive)
- How to enable keyboard
- How to use AI button
- How to select tones
    ↓
Premium Offer (Skippable)
    ↓
Main App Home Screen
```

### 4.2 Keyboard Activation Flow

```
User goes to Settings > Language & Input
    ↓
Select "RomanceType Keyboard"
    ↓
Enable the keyboard
    ↓
Grant "Full Access" permission (iOS)
    ↓
Switch to RomanceType in any chat app
    ↓
Start typing!
```

### 4.3 AI Reply Generation Flow (Keyboard)

```
User in chat app (WhatsApp, LINE, etc.)
    ↓
Receives a message from crush/partner
    ↓
Long-press and copy the message
    ↓
Switch to RomanceType keyboard
    ↓
Clipboard automatically detected
    ↓
Click AI button ✨
    ↓
Select tone (or use default)
    ↓
Loading... (1-3 seconds)
    ↓
3-5 suggestions appear
    ↓
User scrolls through suggestions
    ↓
Tap to insert into text field
    ↓
[Optional] Edit the reply
    ↓
Send!
```

### 4.4 Emotional Coach Flow

```
User opens main app
    ↓
Tap "Emotional Coach"
    ↓
Choose from pre-set scenarios OR type custom question
    ↓
AI Coach responds with advice
    ↓
User can ask follow-up questions
    ↓
[Optional] Rate the advice (helpful/not helpful)
    ↓
[Optional] Save conversation to history
```

---

## 5. Monetization Strategy

### 5.1 Freemium Model

**Free Tier:**
- 10 AI reply generations per day
- 3 tone options (Humorous, High EQ, Gentle)
- Basic keyboard functionality
- Access to Emotional Coach (5 questions/day)
- Ads (banner at bottom of app)

**Premium Tier:**
Subscription Plans:
- **Weekly**: $2.99 USD
- **Monthly**: $9.99 USD  
- **Quarterly**: $24.99 USD ($8.33/month)
- **Yearly**: $79.99 USD ($6.66/month, save 33%)

**Premium Features:**
- ✅ Unlimited AI reply generations
- ✅ All 8 tone options unlocked
- ✅ Advanced context analysis
- ✅ Conversation history (unlimited storage)
- ✅ Priority AI processing (faster responses)
- ✅ Emotional Coach (unlimited questions)
- ✅ Access to Learning Center premium content
- ✅ No ads
- ✅ Custom tone creation
- ✅ Voice-to-text integration
- ✅ Multi-language support
- ✅ Export conversation analysis

### 5.2 Additional Revenue Streams

**In-App Purchases:**
- One-time tone pack purchases ($1.99 each)
- Premium article bundles ($4.99)
- AI Tokens (100 tokens for $4.99)

**Affiliate Marketing:**
- Dating app referrals (Tinder, Bumble, Hinge)
- Relationship book recommendations
- Online dating course promotions

**B2B Opportunities:**
- White-label solution for dating apps
- Enterprise version for customer service
- API access for third-party integration

---

## 6. Technical Requirements

### 6.1 Android Keyboard Development

**Key Components:**

1. **InputMethodService**
```kotlin
class RomanceTypeKeyboard : InputMethodService() {
    
    private lateinit var keyboardView: KeyboardView
    private lateinit var aiButton: ImageButton
    
    override fun onCreateInputView(): View {
        // Create keyboard layout
        keyboardView = layoutInflater.inflate(R.layout.keyboard, null) as KeyboardView
        
        // Setup AI button
        aiButton = keyboardView.findViewById(R.id.ai_button)
        aiButton.setOnClickListener {
            handleAIButtonClick()
        }
        
        return keyboardView
    }
    
    private fun handleAIButtonClick() {
        // Get clipboard content
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip
        
        if (clipData != null && clipData.itemCount > 0) {
            val copiedText = clipData.getItemAt(0).text.toString()
            
            // Send to AI for processing
            generateReplies(copiedText)
        } else {
            showToast("Please copy a message first")
        }
    }
    
    private fun generateReplies(message: String) {
        // Show loading
        showLoadingIndicator()
        
        // Call backend API
        apiService.generateReplies(message, selectedTone)
            .enqueue(object : Callback<ReplyResponse> {
                override fun onResponse(call: Call<ReplyResponse>, response: Response<ReplyResponse>) {
                    hideLoadingIndicator()
                    if (response.isSuccessful) {
                        displaySuggestions(response.body()?.suggestions)
                    }
                }
                
                override fun onFailure(call: Call<ReplyResponse>, t: Throwable) {
                    hideLoadingIndicator()
                    showError("Failed to generate replies")
                }
            })
    }
    
    private fun displaySuggestions(suggestions: List<String>?) {
        // Update suggestion bar with replies
        suggestions?.forEach { suggestion ->
            addSuggestionChip(suggestion)
        }
    }
}
```

2. **Keyboard Layout XML**
```xml
<!-- res/layout/keyboard.xml -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:background="@color/keyboard_background">
    
    <!-- Suggestion Bar -->
    <HorizontalScrollView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:id="@+id/suggestion_bar">
        
        <LinearLayout
            android:id="@+id/suggestion_container"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:orientation="horizontal"
            android:padding="8dp"/>
    </HorizontalScrollView>
    
    <!-- Keyboard Rows -->
    <include layout="@layout/keyboard_row_1"/>
    <include layout="@layout/keyboard_row_2"/>
    <include layout="@layout/keyboard_row_3"/>
    
    <!-- Bottom Row with AI Button -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        
        <ImageButton
            android:id="@+id/ai_button"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:src="@drawable/ic_ai_sparkle"
            android:contentDescription="AI Reply"/>
        
        <Space
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"/>
        
        <Button
            android:id="@+id/space_bar"
            android:layout_width="200dp"
            android:layout_height="40dp"
            android:text="Space"/>
        
        <!-- More buttons -->
    </LinearLayout>
</LinearLayout>
```

3. **Permissions (AndroidManifest.xml)**
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <!-- Required permissions -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.READ_CLIPBOARD"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    
    <application>
        <!-- Keyboard Service -->
        <service
            android:name=".RomanceTypeKeyboard"
            android:permission="android.permission.BIND_INPUT_METHOD"
            android:exported="true">
            <intent-filter>
                <action android:name="android.view.InputMethod"/>
            </intent-filter>
            <meta-data
                android:name="android.view.im"
                android:resource="@xml/method"/>
        </service>
        
        <!-- Main App Activity -->
        <activity android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>
</manifest>
```

### 6.2 Backend API Design

**Base URL:** `https://api.romancetype.com/v1`

**Endpoints:**

1. **User Authentication**
```
POST /auth/register
POST /auth/login
POST /auth/refresh
POST /auth/logout
```

2. **AI Reply Generation**
```
POST /ai/generate-reply
Request Body:
{
  "message": "Hey, how was your day?",
  "tone": "humorous",
  "context": {
    "mood": "positive",
    "relationship_stage": "dating",
    "time_of_day": "evening"
  },
  "language": "zh-TW"
}

Response:
{
  "success": true,
  "suggestions": [
    {
      "id": "1",
      "text": "哈哈,今天過得超充實!早上差點睡過頭,不過最後還是趕上了重要會議 😆",
      "explanation": "This reply is humorous and relatable, showing personality while keeping the conversation going."
    },
    {
      "id": "2",
      "text": "還不錯啦!就是一直在想什麼時候可以再見到你 😊",
      "explanation": "This reply is flirty and shows interest, perfect for advancing the relationship."
    },
    ...
  ],
  "usage_count": 5,
  "remaining_free_quota": 5
}
```

3. **Emotional Coach Chat**
```
POST /coach/chat
Request Body:
{
  "user_id": "user123",
  "message": "She hasn't replied to my message for 2 days. What should I do?",
  "conversation_history": [...]
}

Response:
{
  "success": true,
  "reply": "I understand it's frustrating when someone doesn't reply...",
  "suggestions": [
    "Give her some space",
    "Send a light, non-pressuring follow-up",
    "Focus on other activities"
  ]
}
```

4. **User Profile**
```
GET /user/profile
PUT /user/profile
GET /user/preferences
PUT /user/preferences
```

5. **Conversation History**
```
GET /history/conversations?page=1&limit=20
POST /history/conversations
PUT /history/conversations/{id}
DELETE /history/conversations/{id}
```

6. **Subscription Management**
```
GET /subscription/status
POST /subscription/purchase
POST /subscription/cancel
```

### 6.3 Database Schema

**Users Table:**
```sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    username VARCHAR(100),
    age INT,
    gender ENUM('male', 'female', 'other', 'prefer_not_to_say'),
    relationship_status ENUM('single', 'dating', 'in_relationship', 'married'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    is_premium BOOLEAN DEFAULT FALSE,
    premium_expires_at TIMESTAMP NULL,
    INDEX idx_email (email),
    INDEX idx_premium (is_premium)
);
```

**User Preferences Table:**
```sql
CREATE TABLE user_preferences (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    default_tone VARCHAR(50) DEFAULT 'high_eq',
    language VARCHAR(10) DEFAULT 'zh-TW',
    save_history BOOLEAN DEFAULT TRUE,
    notifications_enabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

**Conversation History Table:**
```sql
CREATE TABLE conversation_history (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    platform VARCHAR(50),
    received_message TEXT,
    selected_tone VARCHAR(50),
    generated_replies JSON,
    selected_reply TEXT,
    success_rating INT CHECK (success_rating >= 1 AND success_rating <= 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, created_at)
);
```

**AI Usage Tracking Table:**
```sql
CREATE TABLE ai_usage_log (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    request_type ENUM('reply_generation', 'coach_chat', 'tone_analysis'),
    tokens_used INT,
    response_time_ms INT,
    success BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, created_at)
);
```

**Subscriptions Table:**
```sql
CREATE TABLE subscriptions (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    plan_type ENUM('weekly', 'monthly', 'quarterly', 'yearly'),
    status ENUM('active', 'cancelled', 'expired'),
    started_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    auto_renew BOOLEAN DEFAULT TRUE,
    payment_method VARCHAR(50),
    amount DECIMAL(10, 2),
    currency VARCHAR(3) DEFAULT 'USD',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_status (user_id, status)
);
```

### 6.4 Security & Privacy

**Data Protection:**
- All API calls use HTTPS/TLS 1.3
- JWT tokens for authentication (expire in 24 hours)
- Refresh tokens stored securely (expire in 30 days)
- Password hashing with bcrypt (cost factor: 12)
- Rate limiting: 100 requests per minute per user
- Input sanitization to prevent SQL injection
- XSS protection on all user inputs

**Privacy Measures:**
- Optional anonymous mode (no history saved)
- User can delete all data anytime
- Conversation history encrypted at rest
- No sharing of personal data with third parties
- GDPR compliant
- CCPA compliant

**Keyboard Security:**
- Keyboard does NOT log keystrokes
- Only processes clipboard when AI button is clicked
- No automatic data transmission
- Clear privacy policy shown during setup

---

## 7. UI/UX Design Guidelines

### 7.1 Design Principles

1. **Simple & Intuitive**
   - Minimal learning curve
   - Clear visual hierarchy
   - Consistent navigation

2. **Fast & Responsive**
   - AI replies in < 3 seconds
   - Smooth animations (60fps)
   - Optimistic UI updates

3. **Trustworthy**
   - Clear privacy statements
   - Transparent AI usage
   - Professional appearance

4. **Delightful**
   - Playful micro-interactions
   - Encouraging feedback messages
   - Celebration animations on success

### 7.2 Color Palette

**Primary Colors:**
- Primary: `#FF6B9D` (Pink - represents love/romance)
- Secondary: `#C0C0F9` (Soft Purple - represents AI/tech)
- Accent: `#FDB777` (Warm Orange - represents energy/excitement)

**Neutral Colors:**
- Background: `#FFFFFF` (White)
- Surface: `#F8F9FA` (Light Gray)
- Text Primary: `#212529` (Dark Gray)
- Text Secondary: `#6C757D` (Medium Gray)
- Border: `#DEE2E6` (Light Gray)

**Semantic Colors:**
- Success: `#28A745` (Green)
- Error: `#DC3545` (Red)
- Warning: `#FFC107` (Yellow)
- Info: `#17A2B8` (Blue)

### 7.3 Typography

**Font Family:**
- Primary (Chinese): Noto Sans TC / PingFang TC
- Primary (English): Inter / SF Pro
- Secondary: Roboto

**Font Sizes:**
- H1: 32sp (bold)
- H2: 24sp (semi-bold)
- H3: 20sp (semi-bold)
- Body: 16sp (regular)
- Caption: 14sp (regular)
- Small: 12sp (regular)

### 7.4 Iconography

**Icon Style:**
- Rounded, friendly style
- 24x24dp standard size
- Consistent stroke width (2dp)
- Use Material Icons + custom icons

**Key Icons:**
- AI Button: ✨ Sparkle/Magic wand
- Coach: 👨‍💼 Person with headset
- History: 📝 Notepad
- Settings: ⚙️ Gear
- Premium: 💎 Diamond
- Success: ✅ Checkmark
- Heart: ❤️ Love symbol

---

## 8. Development Phases & Timeline

### Phase 1: MVP (Minimum Viable Product) - 8 Weeks

**Week 1-2: Setup & Foundation**
- Project setup (Android Studio, Git repo)
- Backend infrastructure (Spring Boot project)
- Database design & setup
- OpenAI API integration testing

**Week 3-4: Core Keyboard Development**
- Basic keyboard layout
- Input method service
- Clipboard monitoring
- AI button functionality

**Week 5-6: AI Integration & Backend**
- Reply generation API
- Prompt engineering
- Response parsing
- Basic context analysis

**Week 7-8: Main App & Testing**
- Main app UI (Home, Settings, Profile)
- User authentication
- Integration testing
- Bug fixes

**MVP Features:**
- ✅ Custom keyboard with AI button
- ✅ 3 tone options (Humorous, High EQ, Gentle)
- ✅ AI reply generation (3 suggestions)
- ✅ Basic user authentication
- ✅ Free tier: 10 replies/day

### Phase 2: Enhanced Features - 6 Weeks

**Week 9-10: Emotional Coach**
- Chat interface
- Pre-set scenarios
- AI chatbot integration
- Conversation persistence

**Week 11-12: Conversation History**
- History UI
- Data storage
- Search & filter
- Success rating system

**Week 13-14: Premium Features**
- Subscription system
- Payment integration (Google Play Billing)
- All 8 tone options
- Unlimited usage for premium

**Phase 2 Features:**
- ✅ Emotional Coach (AI consultant)
- ✅ Conversation history
- ✅ 8 tone options
- ✅ Premium subscription
- ✅ Advanced context analysis

### Phase 3: Polish & Expansion - 4 Weeks

**Week 15-16: Advanced Features**
- Learning Center content
- Custom tone creation (premium)
- Voice input integration
- Multi-language support

**Week 17-18: Optimization & Launch Prep**
- Performance optimization
- UI/UX improvements
- Marketing materials
- Beta testing
- App Store submission

**Phase 3 Features:**
- ✅ Learning Center
- ✅ Voice input
- ✅ Custom tones (premium)
- ✅ Performance optimizations
- ✅ Multi-language support (EN, ZH-TW, ZH-CN)

### Phase 4: Post-Launch - Ongoing

**Month 2-3:**
- iOS version development
- User feedback implementation
- Bug fixes
- Feature improvements

**Month 4-6:**
- Advanced analytics
- A/B testing
- Marketing campaigns
- Partnership development

**Long-term:**
- ML model training with user data
- Additional languages
- Integration with dating apps
- Enterprise features

---

## 9. Success Metrics (KPIs)

### 9.1 User Acquisition
- Daily Active Users (DAU)
- Monthly Active Users (MAU)
- Download rate (app store)
- Viral coefficient (K-factor)

**Target Goals:**
- Month 1: 1,000 DAU
- Month 3: 10,000 DAU
- Month 6: 50,000 DAU
- Year 1: 200,000 DAU

### 9.2 Engagement
- Daily keyboard usage frequency
- Average replies generated per user
- Emotional Coach usage rate
- Session duration

**Target Goals:**
- Avg 5 replies/user/day
- 70% DAU use keyboard at least once
- 30% users access Emotional Coach weekly

### 9.3 Monetization
- Free to Premium conversion rate
- Monthly Recurring Revenue (MRR)
- Average Revenue Per User (ARPU)
- Customer Lifetime Value (LTV)

**Target Goals:**
- 5% free-to-premium conversion
- $10,000 MRR by Month 6
- $100,000 MRR by Year 1
- ARPU: $3-5/month

### 9.4 Retention
- Day 1 retention rate
- Day 7 retention rate
- Day 30 retention rate
- Churn rate

**Target Goals:**
- Day 1: 60%
- Day 7: 40%
- Day 30: 25%
- Monthly churn: <10%

### 9.5 Quality
- AI reply satisfaction rating (1-5 stars)
- Bug report rate
- Crash-free rate
- Average response time

**Target Goals:**
- Satisfaction: >4.2/5 stars
- Crash-free: >99.5%
- Response time: <2 seconds

---

## 10. Marketing & Go-to-Market Strategy

### 10.1 Target Markets

**Primary Markets:**
- Taiwan 🇹🇼
- Hong Kong 🇭🇰
- Singapore 🇸🇬
- Malaysia 🇲🇾

**Secondary Markets:**
- USA (English version)
- Canada
- UK
- Australia

### 10.2 Marketing Channels

**Digital Marketing:**
1. **Social Media**
   - Instagram: Relationship tips, success stories
   - TikTok: Funny dating fails, AI reply demos
   - YouTube: Tutorials, dating advice videos
   - Facebook: Community building

2. **Content Marketing**
   - Blog: Dating tips, relationship advice
   - SEO: Target keywords like "dating app helper", "AI reply generator"
   - Guest posts on dating/relationship blogs

3. **Influencer Partnerships**
   - Dating coaches
   - Relationship YouTubers
   - Lifestyle influencers

4. **Paid Advertising**
   - Google Ads (Search & Display)
   - Facebook/Instagram Ads
   - TikTok Ads
   - Dating app ad placements

**Organic Growth:**
1. **App Store Optimization (ASO)**
   - Keyword optimization
   - Compelling screenshots
   - Video preview
   - Regular updates

2. **Referral Program**
   - Give 3 extra free replies for each referral
   - Friend gets 5 bonus replies on signup

3. **PR & Media**
   - Press releases
   - Tech blog features
   - Dating podcast interviews

### 10.3 Launch Strategy

**Pre-Launch (2 weeks before):**
- Build landing page with email signup
- Create teaser videos for social media
- Reach out to influencers for early access
- Beta tester recruitment (500 users)

**Launch Day:**
- Submit to Product Hunt
- Press release distribution
- Social media announcement
- Email blast to waitlist

**Post-Launch (First month):**
- Monitor user feedback
- Quick bug fixes
- Feature requests prioritization
- Content marketing ramp-up

---

## 11. Risk Assessment & Mitigation

### 11.1 Technical Risks

**Risk 1: AI API Downtime or Rate Limits**
- **Impact:** Users can't generate replies
- **Mitigation:** 
  - Implement fallback to alternative AI provider
  - Cache common replies
  - Queue system for retry

**Risk 2: Keyboard Compatibility Issues**
- **Impact:** Keyboard doesn't work on some devices
- **Mitigation:**
  - Extensive device testing
  - Maintain device compatibility list
  - Provide troubleshooting guide

**Risk 3: Performance Issues**
- **Impact:** Slow reply generation, laggy keyboard
- **Mitigation:**
  - Optimize API calls
  - Implement caching
  - Use CDN for static assets
  - Database query optimization

### 11.2 Business Risks

**Risk 1: Low User Adoption**
- **Impact:** Not enough users to sustain business
- **Mitigation:**
  - Aggressive marketing in early stages
  - Referral program
  - Free tier generous enough to hook users

**Risk 2: High User Acquisition Cost (CAC)**
- **Impact:** Unprofitable unit economics
- **Mitigation:**
  - Focus on organic growth channels
  - Optimize ad campaigns continuously
  - Improve product virality

**Risk 3: Competition**
- **Impact:** Competitors with similar features
- **Mitigation:**
  - Unique features (Emotional Coach, Learning Center)
  - Superior UX design
  - Faster iteration and updates
  - Build loyal community

### 11.3 Legal & Privacy Risks

**Risk 1: Data Privacy Violations**
- **Impact:** Legal penalties, user trust loss
- **Mitigation:**
  - GDPR/CCPA compliance from day one
  - Clear privacy policy
  - Regular security audits
  - Data encryption

**Risk 2: Intellectual Property Issues**
- **Impact:** Lawsuits, forced shutdown
- **Mitigation:**
  - Ensure original branding and design
  - Use properly licensed assets
  - Patent search before launch

**Risk 3: AI-Generated Inappropriate Content**
- **Impact:** Negative PR, user complaints
- **Mitigation:**
  - Content filtering on AI responses
  - User report system
  - Regular review of flagged content
  - Age verification for certain features

---

## 12. Future Roadmap

### 12.1 Short-term (Next 6 months)

**Q1 2026:**
- iOS version launch
- Multi-language expansion (Japanese, Korean)
- Voice message reply feature
- Profile picture analyzer (AI suggests conversation starters based on photos)

**Q2 2026:**
- Dating app integration (Tinder, Bumble, Hinge)
- Video call coaching (real-time subtitle suggestions)
- Group chat mode
- Smart notification timing (suggest best time to reply)

### 12.2 Mid-term (6-12 months)

**Q3 2026:**
- AI Avatar feature (practice conversations with AI before real date)
- Date planner (AI suggests date ideas based on preferences)
- Compatibility analyzer (upload screenshots, AI analyzes compatibility)
- Relationship milestone tracker

**Q4 2026:**
- Voice analysis (analyze tone and emotion in voice messages)
- Long-term relationship mode (different advice for couples)
- Breakup recovery coach
- Professional networking mode (adapt AI for professional conversations)

### 12.3 Long-term (1-2 years)

**2027:**
- VR/AR dating practice scenarios
- Wearable integration (smartwatch suggestions)
- AI-powered dating profile optimizer
- Cross-platform synchronized learning (AI learns from all your conversations)
- API for third-party developers
- White-label solution for dating apps
- International expansion (Europe, Latin America, Southeast Asia)

---

## 13. Conclusion

This AI Dating Assistant Keyboard app addresses a real pain point in modern dating: the anxiety and uncertainty of crafting the perfect message. By combining:

1. **Seamless UX** - Custom keyboard integration means no app switching
2. **Powerful AI** - GPT-4 powered suggestions that actually work
3. **Personalization** - Learns user preferences over time
4. **Comprehensive Features** - Not just replies, but full relationship coaching

We create a product that users will rely on daily and be willing to pay for.

**Key Success Factors:**
- ✅ Solve a genuine problem (conversation anxiety)
- ✅ Frictionless user experience (keyboard integration)
- ✅ Clear value proposition (better conversations = better relationships)
- ✅ Sustainable business model (freemium with high conversion potential)
- ✅ Viral potential (success stories drive word-of-mouth)

**Next Steps:**
1. Validate assumptions with user research
2. Build MVP (8 weeks)
3. Beta test with 500 users
4. Iterate based on feedback
5. Launch with marketing push
6. Scale and expand features

---

## Appendix A: Tech Stack Summary

| Component | Technology |
|-----------|-----------|
| **Mobile (Android)** | Kotlin, Jetpack Compose, InputMethodService |
| **Mobile (iOS)** | Swift, SwiftUI, Custom Keyboard Extension |
| **Backend** | Spring Boot (Java), MySQL, Redis |
| **AI** | OpenAI GPT-4 API, Claude API (backup) |
| **Cloud** | AWS / GCP |
| **Analytics** | Firebase Analytics, Mixpanel |
| **Payment** | Google Play Billing, Apple In-App Purchase |
| **Monitoring** | Sentry, Datadog |
| **CI/CD** | GitHub Actions, Fastlane |

---

## Appendix B: Estimated Costs

**Development Costs (MVP):**
- Developer (8 weeks): $16,000 - $40,000
- Designer (4 weeks): $4,000 - $10,000
- Total Dev Cost: $20,000 - $50,000

**Monthly Operating Costs:**
- Server hosting (AWS): $200 - $500
- OpenAI API calls: $500 - $2,000 (depending on usage)
- Database (RDS): $100 - $300
- CDN (CloudFlare): $50 - $200
- Analytics tools: $100
- Total Monthly: $950 - $3,100

**Marketing Budget (First 6 months):**
- Paid ads: $5,000 - $15,000
- Influencer partnerships: $2,000 - $5,000
- Content creation: $1,000 - $3,000
- Total Marketing: $8,000 - $23,000

**Total First 6 Months:**
- Development: $20,000 - $50,000
- Operations (6 months): $5,700 - $18,600
- Marketing: $8,000 - $23,000
- **Grand Total: $33,700 - $91,600**

---

## Appendix C: Competitive Analysis

| Feature | RomanceType | LoveAssist | CleverType | Auri AI |
|---------|-------------|------------|------------|---------|
| Custom Keyboard | ✅ | ✅ | ✅ | ✅ |
| AI Reply Generation | ✅ | ✅ | ✅ | ✅ |
| Multiple Tones | 8 options | 5 options | 3 options | Limited |
| Emotional Coach | ✅ | ✅ | ❌ | ❌ |
| Conversation History | ✅ | Limited | ❌ | ❌ |
| Learning Center | ✅ | ❌ | ❌ | ❌ |
| Free Tier | 10/day | 5/day | 20/day | 10/day |
| Price (Monthly) | $9.99 | $9.99 | $1.99 | $9.99 |
| Context Analysis | Advanced | Basic | None | Basic |
| Privacy Focus | High | Medium | High | High |

**Our Competitive Advantages:**
1. More comprehensive feature set (Coach + Learning)
2. Better context analysis
3. Dating-specific optimization (not generic AI keyboard)
4. Community and success stories
5. Personalization through ML

---

**Document Version:** 1.0  
**Last Updated:** November 14, 2025  
**Author:** Product Specification Team  
**Status:** Draft - Ready for Development