name: "TalkGenius Master PRP - AI Dating Assistant Keyboard App"
description: |

## Purpose
建立一個完整的 AI 約會助手鍵盤應用程式,包含 Android 客戶端、iOS 客戶端和 Spring Boot 後端。此 PRP 提供完整的開發藍圖,讓多個專業團隊(Android、iOS、Java 後端、資料庫、QA、雲端基礎設施)能夠協同工作。

## Core Principles
1. **Context is King**: 包含所有必要的文檔、範例和注意事項
2. **Validation Loops**: 提供可執行的測試/檢查,讓 AI 能夠運行並修復
3. **Information Dense**: 使用來自程式碼庫的關鍵字和模式
4. **Progressive Success**: 從簡單開始,驗證,然後增強
5. **Global rules**: 遵循 CLAUDE.md 中的所有規則

---

## Goal
創建一個生產就緒的 AI 約會助手鍵盤應用程式,支援 Android 和 iOS 平台,具備:
- 自定義鍵盤與系統級整合
- 8 種 AI 語氣風格的智能回覆生成
- 情感教練功能與長期學習
- 訊息分析與上下文理解
- 用戶偏好管理與歷史記錄
- 安全的 Docker 部署環境

## Why
- **商業價值**: 解決現代約會應用中回覆困難的痛點,提升用戶溝通信心
- **技術創新**: 展示跨平台移動開發 + AI 整合的最佳實踐
- **用戶影響**: 幫助用戶建立更有意義的連結,減少社交焦慮
- **市場需求**: Freemium 商業模式,免費用戶 10 次/天,付費會員無限使用

## What
一個跨平台的 AI 驅動鍵盤應用程式,包含:

### Android 客戶端 (Kotlin + Jetpack Compose)
- 自定義鍵盤服務 (InputMethodService)
- Material Design 3 UI
- 剪貼簿管理與上下文提取
- Retrofit API 整合
- Room 本地資料庫
- JWT 認證流程

### iOS 客戶端 (Swift + SwiftUI)
- 自定義鍵盤擴展 (UIInputViewController)
- SwiftUI 現代化 UI
- Universal Clipboard 整合
- URLSession API 整合
- CoreData 本地資料庫
- Keychain 安全存儲

### 後端服務 (Spring Boot + MySQL + Redis)
- RESTful API 端點
- OpenAI GPT-4 整合 (Spring AI)
- JWT 認證與授權
- MySQL 用戶資料持久化
- Redis 會話管理與快取
- Docker 容器化部署

### Success Criteria
- [x] Android 自定義鍵盤成功啟動並接收輸入
- [x] iOS 自定義鍵盤擴展成功啟動並接收輸入
- [x] 後端 API 成功整合 OpenAI GPT-4 並返回 8 種語氣回覆
- [x] 用戶註冊/登入流程完整運作 (JWT)
- [x] 訊息分析功能正確解析上下文
- [x] 情感教練提供個性化建議
- [x] Freemium 限制正確實施 (免費 10 次/天)
- [x] Docker 部署成功運行 (MySQL + Redis + Backend)
- [x] 單元測試覆蓋率達 80%+
- [x] 整合測試通過所有關鍵流程
- [x] 安全性測試通過 (HTTPS, JWT, 密碼加密)

## All Needed Context

### Documentation & References
```yaml
# MUST READ - 將這些包含在您的上下文窗口中

# Android 開發
- url: https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method
  why: 官方 InputMethodService 自定義鍵盤開發指南
  critical: onCreateInputView(), onStartInputView(), getCurrentInputConnection()

- url: https://developer.android.com/jetpack/compose
  why: Jetpack Compose 現代 UI 開發模式

- url: https://square.github.io/retrofit/
  why: Retrofit REST API 客戶端最佳實踐

- url: https://developer.android.com/training/data-storage/room
  why: Room 資料庫 ORM 模式

# iOS 開發
- url: https://developer.apple.com/documentation/uikit/uiinputviewcontroller
  why: 官方自定義鍵盤擴展開發指南
  critical: UIInputViewController, textDocumentProxy, requestSupplementaryLexicon

- url: https://developer.apple.com/documentation/swiftui
  why: SwiftUI 聲明式 UI 開發

- url: https://github.com/KeyboardKit/KeyboardKit
  why: SwiftUI 鍵盤開發框架參考
  note: 不直接依賴,但參考其架構模式

# Spring Boot 後端
- url: https://docs.spring.io/spring-ai/reference/
  why: Spring AI 框架整合 OpenAI API
  critical: ChatClient, ChatResponse, SystemMessage, UserMessage

- url: https://spring.io/projects/spring-boot
  why: Spring Boot 3.x 核心文檔
  section: Security (Spring Security + JWT), Data JPA, Redis

- url: https://platform.openai.com/docs/api-reference/chat
  why: OpenAI Chat Completions API 規範
  critical: messages 結構, temperature, max_tokens 參數

# 資料庫設計
- file: INITIAL.md
  why: 完整資料庫 schema (users, conversations, messages, emotional_feedback, subscriptions)
  lines: 456-598

# Docker 部署
- file: INITIAL.md
  why: docker-compose.yml 和 Dockerfile 配置
  lines: 600-728

# 專案規範
- file: spec.md
  why: 完整產品需求、UI/UX、商業邏輯

- file: CLAUDE.md
  why: 開發規範、測試要求、Git 提交規範
```

### Current Codebase Structure
```bash
TalkGenius/
├── PRPs/
│   ├── templates/
│   │   └── prp_base.md
│   ├── EXAMPLE_multi_agent_prp.md
│   └── talkgenius-master.md (本文件)
├── INITIAL.md              # 專案架構與技術規範
├── CLAUDE.md              # 開發指南與規範
├── spec.md                # 產品需求文件
├── PLANNING.md            # 專案規劃文件
├── TASK.md                # 任務追蹤
├── venv_linux/            # Python 虛擬環境
└── .git/

# 注意: 這是一個全新專案 (greenfield),目前沒有任何程式碼文件
```

### Desired Codebase Structure
```bash
TalkGenius/
├── android/                          # Android 客戶端
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/talkgenius/
│   │   │   │   │   ├── keyboard/
│   │   │   │   │   │   ├── TalkGeniusKeyboard.kt          # InputMethodService 實現
│   │   │   │   │   │   ├── KeyboardView.kt                # Compose UI 鍵盤視圖
│   │   │   │   │   │   ├── KeyboardViewModel.kt           # MVVM ViewModel
│   │   │   │   │   │   └── ClipboardManager.kt            # 剪貼簿處理
│   │   │   │   │   ├── api/
│   │   │   │   │   │   ├── ApiService.kt                  # Retrofit API 介面
│   │   │   │   │   │   ├── AuthInterceptor.kt             # JWT Token 攔截器
│   │   │   │   │   │   └── models/                        # API 資料模型
│   │   │   │   │   ├── data/
│   │   │   │   │   │   ├── local/
│   │   │   │   │   │   │   ├── TalkGeniusDatabase.kt      # Room Database
│   │   │   │   │   │   │   ├── dao/                       # DAO 介面
│   │   │   │   │   │   │   └── entities/                  # Room Entities
│   │   │   │   │   │   └── repository/
│   │   │   │   │   │       ├── UserRepository.kt          # 用戶資料存取
│   │   │   │   │   │       └── MessageRepository.kt       # 訊息資料存取
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── auth/
│   │   │   │   │   │   │   ├── LoginScreen.kt             # 登入畫面
│   │   │   │   │   │   │   ├── RegisterScreen.kt          # 註冊畫面
│   │   │   │   │   │   │   └── AuthViewModel.kt           # 認證 ViewModel
│   │   │   │   │   │   ├── settings/
│   │   │   │   │   │   │   ├── SettingsScreen.kt          # 設定畫面
│   │   │   │   │   │   │   └── SettingsViewModel.kt       # 設定 ViewModel
│   │   │   │   │   │   └── MainActivity.kt                # 主 Activity
│   │   │   │   │   └── util/
│   │   │   │   │       ├── Constants.kt                   # 常數定義
│   │   │   │   │       └── Extensions.kt                  # Kotlin 擴展函數
│   │   │   │   ├── res/
│   │   │   │   │   ├── xml/
│   │   │   │   │   │   ├── method.xml                     # 鍵盤輸入法定義
│   │   │   │   │   │   └── keyboards.xml                  # 鍵盤佈局
│   │   │   │   │   ├── values/
│   │   │   │   │   │   ├── strings.xml
│   │   │   │   │   │   └── themes.xml
│   │   │   │   │   └── drawable/                          # 圖片資源
│   │   │   │   └── AndroidManifest.xml
│   │   │   └── test/                                      # 單元測試
│   │   │       └── java/com/talkgenius/
│   │   │           ├── keyboard/TalkGeniusKeyboardTest.kt
│   │   │           ├── api/ApiServiceTest.kt
│   │   │           └── repository/UserRepositoryTest.kt
│   │   ├── build.gradle.kts                               # 應用程式 Gradle 配置
│   │   └── proguard-rules.pro                             # ProGuard 規則
│   ├── build.gradle.kts                                   # 專案 Gradle 配置
│   ├── settings.gradle.kts
│   └── gradle.properties
│
├── ios/                                # iOS 客戶端
│   ├── TalkGenius/
│   │   ├── App/
│   │   │   ├── TalkGeniusApp.swift                        # SwiftUI App 入口
│   │   │   └── ContentView.swift                          # 主視圖
│   │   ├── Auth/
│   │   │   ├── LoginView.swift                            # 登入視圖
│   │   │   ├── RegisterView.swift                         # 註冊視圖
│   │   │   └── AuthViewModel.swift                        # 認證 ViewModel
│   │   ├── Settings/
│   │   │   ├── SettingsView.swift                         # 設定視圖
│   │   │   └── SettingsViewModel.swift                    # 設定 ViewModel
│   │   ├── API/
│   │   │   ├── APIService.swift                           # URLSession API 客戶端
│   │   │   ├── APIModels.swift                            # API 資料模型
│   │   │   └── AuthManager.swift                          # JWT 認證管理
│   │   ├── Data/
│   │   │   ├── CoreDataStack.swift                        # CoreData 堆疊
│   │   │   ├── Models/                                    # CoreData Models
│   │   │   └── Repositories/
│   │   │       ├── UserRepository.swift                   # 用戶資料存取
│   │   │       └── MessageRepository.swift                # 訊息資料存取
│   │   ├── Utils/
│   │   │   ├── Constants.swift                            # 常數定義
│   │   │   └── Extensions.swift                           # Swift 擴展
│   │   └── Info.plist
│   ├── TalkGeniusKeyboard/                                # 鍵盤擴展
│   │   ├── KeyboardViewController.swift                   # UIInputViewController 實現
│   │   ├── KeyboardView.swift                             # SwiftUI 鍵盤視圖
│   │   ├── KeyboardViewModel.swift                        # 鍵盤 ViewModel
│   │   ├── ClipboardManager.swift                         # 剪貼簿管理
│   │   └── Info.plist
│   ├── TalkGeniusTests/                                   # 單元測試
│   │   ├── AuthViewModelTests.swift
│   │   ├── APIServiceTests.swift
│   │   └── UserRepositoryTests.swift
│   ├── TalkGenius.xcodeproj/
│   └── Podfile (或 Package.swift)                         # 依賴管理
│
├── backend/                            # Spring Boot 後端
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/talkgenius/
│   │   │   │   ├── TalkGeniusApplication.java             # Spring Boot 主類
│   │   │   │   ├── config/
│   │   │   │   │   ├── SecurityConfig.java                # Spring Security + JWT
│   │   │   │   │   ├── OpenAIConfig.java                  # Spring AI 配置
│   │   │   │   │   ├── RedisConfig.java                   # Redis 配置
│   │   │   │   │   └── CorsConfig.java                    # CORS 設定
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java                # 認證端點
│   │   │   │   │   ├── MessageController.java             # 訊息端點
│   │   │   │   │   ├── ConversationController.java        # 對話端點
│   │   │   │   │   └── UserController.java                # 用戶端點
│   │   │   │   ├── service/
│   │   │   │   │   ├── AuthService.java                   # 認證業務邏輯
│   │   │   │   │   ├── AIReplyService.java                # AI 回覆生成
│   │   │   │   │   ├── MessageAnalysisService.java        # 訊息分析
│   │   │   │   │   ├── EmotionalCoachService.java         # 情感教練
│   │   │   │   │   └── SubscriptionService.java           # 訂閱管理
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java                # JPA Repository
│   │   │   │   │   ├── ConversationRepository.java
│   │   │   │   │   ├── MessageRepository.java
│   │   │   │   │   ├── EmotionalFeedbackRepository.java
│   │   │   │   │   └── SubscriptionRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── entity/                            # JPA Entities
│   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   ├── Conversation.java
│   │   │   │   │   │   ├── Message.java
│   │   │   │   │   │   ├── EmotionalFeedback.java
│   │   │   │   │   │   └── Subscription.java
│   │   │   │   │   └── dto/                               # Data Transfer Objects
│   │   │   │   │       ├── LoginRequest.java
│   │   │   │   │       ├── RegisterRequest.java
│   │   │   │   │       ├── GenerateReplyRequest.java
│   │   │   │   │       └── AIReplyResponse.java
│   │   │   │   ├── security/
│   │   │   │   │   ├── JwtTokenProvider.java              # JWT 生成與驗證
│   │   │   │   │   ├── JwtAuthenticationFilter.java       # JWT 過濾器
│   │   │   │   │   └── CustomUserDetailsService.java      # 用戶詳情服務
│   │   │   │   └── exception/
│   │   │   │       ├── GlobalExceptionHandler.java        # 全局異常處理
│   │   │   │       └── CustomExceptions.java              # 自定義異常
│   │   │   └── resources/
│   │   │       ├── application.yml                        # Spring Boot 配置
│   │   │       ├── application-dev.yml                    # 開發環境配置
│   │   │       └── application-prod.yml                   # 生產環境配置
│   │   └── test/
│   │       └── java/com/talkgenius/
│   │           ├── service/
│   │           │   ├── AIReplyServiceTest.java
│   │           │   ├── MessageAnalysisServiceTest.java
│   │           │   └── AuthServiceTest.java
│   │           └── controller/
│   │               ├── AuthControllerTest.java
│   │               └── MessageControllerTest.java
│   ├── pom.xml (或 build.gradle)                          # Maven/Gradle 配置
│   └── Dockerfile                                         # Docker 鏡像配置
│
├── docker/                             # Docker 配置
│   ├── docker-compose.yml                                 # 多容器編排
│   ├── mysql/
│   │   └── init.sql                                       # MySQL 初始化腳本
│   └── redis/
│       └── redis.conf                                     # Redis 配置
│
├── docs/                               # 文檔
│   ├── API.md                                             # API 文檔
│   ├── DEPLOYMENT.md                                      # 部署指南
│   └── ARCHITECTURE.md                                    # 架構文檔
│
├── .env.example                                           # 環境變數範本
├── .gitignore
└── README.md                                              # 專案 README
```

### Known Gotchas & Library Quirks
```kotlin
// CRITICAL: Android InputMethodService 注意事項
// 1. 鍵盤服務在獨立進程中運行,無法直接訪問主應用的 Context
// 2. 使用 getCurrentInputConnection() 與文本欄位交互,可能返回 null
// 3. Jetpack Compose 在鍵盤服務中需要 ComposeView 包裝
// 4. 剪貼簿訪問需要 FOREGROUND_SERVICE 權限(Android 10+)
// 5. Room 資料庫需要在應用和鍵盤擴展間共享,使用 sharedUserId

// 範例: 在鍵盤服務中使用 Compose
override fun onCreateInputView(): View {
    return ComposeView(this).apply {
        setContent {
            MaterialTheme {
                KeyboardView(viewModel = keyboardViewModel)
            }
        }
    }
}

// CRITICAL: getCurrentInputConnection() 可能為 null
val ic = currentInputConnection
ic?.commitText("text", 1) ?: run {
    Log.e(TAG, "InputConnection is null")
}
```

```swift
// CRITICAL: iOS UIInputViewController 注意事項
// 1. 鍵盤擴展有嚴格的記憶體限制(~48MB),超過會被系統終止
// 2. 無法訪問網路(需要透過 App Groups 與主應用共享資料)
// 3. textDocumentProxy 是與文本欄位交互的唯一方式
// 4. SwiftUI 需要透過 UIHostingController 嵌入
// 5. CoreData 需要使用 App Groups 共享容器

// 範例: 在鍵盤擴展中使用 SwiftUI
override func viewDidLoad() {
    super.viewDidLoad()

    let keyboardView = KeyboardView(viewModel: viewModel)
    let hostingController = UIHostingController(rootView: keyboardView)

    addChild(hostingController)
    view.addSubview(hostingController.view)
    hostingController.didMove(toParent: self)
}

// CRITICAL: App Groups 共享資料
let sharedDefaults = UserDefaults(suiteName: "group.com.talkgenius.app")
let containerURL = FileManager.default.containerURL(
    forSecurityApplicationGroupIdentifier: "group.com.talkgenius.app"
)
```

```java
// CRITICAL: Spring Boot + OpenAI 注意事項
// 1. Spring AI 0.8.0+ 需要 Spring Boot 3.2+
// 2. OpenAI API key 絕不能硬編碼,必須使用環境變數
// 3. ChatClient 是線程安全的,可以作為單例注入
// 4. GPT-4 有 token 限制,需要監控 usage
// 5. 使用 @Transactional 確保資料庫操作的原子性

// 範例: Spring AI ChatClient 配置
@Bean
public ChatClient chatClient(ChatClient.Builder builder) {
    return builder
        .defaultSystem("You are a dating assistant specialized in crafting engaging replies.")
        .build();
}

// CRITICAL: 限流處理(Freemium 10次/天)
@Service
public class AIReplyService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private SubscriptionService subscriptionService;

    public void checkDailyLimit(String userId) {
        String key = "daily_limit:" + userId;
        Integer count = redisTemplate.opsForValue().get(key);

        if (count != null && count >= 10) {
            throw new DailyLimitExceededException("Free tier limit reached");
        }

        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }
}

// CRITICAL: JWT Token 過期處理
// AccessToken: 1 hour, RefreshToken: 30 days
// 客戶端需要實現自動刷新邏輯
```

```yaml
# CRITICAL: Docker 部署注意事項
# 1. MySQL 8.0 預設使用 caching_sha2_password,需要配置
# 2. Redis 持久化需要掛載 volume,否則重啟後資料丟失
# 3. Spring Boot 需要等待 MySQL 和 Redis 啟動完成(depends_on + healthcheck)
# 4. 環境變數透過 .env 文件注入,不要提交到 Git

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: talkgenius
    volumes:
      - mysql_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  backend:
    depends_on:
      mysql:
        condition: service_healthy
      redis:
        condition: service_started
```

## Implementation Blueprint

### Data Models and Structure

#### Backend JPA Entities (完整定義)
```java
// User.java - 用戶實體
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;  // bcrypt 加密

    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;  // MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus;  // SINGLE, DATING, IN_RELATIONSHIP, MARRIED

    @Column(nullable = false)
    private Boolean isPremium = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Getters, Setters, Constructors
}

// Conversation.java - 對話實體
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String platform;  // "Tinder", "Bumble", etc.

    private String matchName;  // 對方名稱

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

// Message.java - 訊息實體
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageRole role;  // USER, MATCH, AI_SUGGESTION

    @Enumerated(EnumType.STRING)
    private ToneStyle toneStyle;  // HUMOROUS, HIGH_EQ, GENTLE, CUTE, ROMANTIC, PROFESSIONAL, DIRECT, FLIRTY

    private Boolean wasUsed = false;

    @CreatedDate
    private LocalDateTime createdAt;
}

// EmotionalFeedback.java - 情感反饋實體
@Entity
@Table(name = "emotional_feedback")
public class EmotionalFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer rating;  // 1-5 stars

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @CreatedDate
    private LocalDateTime createdAt;
}

// Subscription.java - 訂閱實體
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private SubscriptionTier tier;  // FREE, PREMIUM

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private Boolean autoRenew = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### API DTOs (Request/Response)
```java
// GenerateReplyRequest.java
public class GenerateReplyRequest {
    @NotBlank
    private String clipboardContext;  // 從剪貼簿提取的對話上下文

    @NotNull
    private ToneStyle toneStyle;  // 語氣風格

    private String additionalContext;  // 可選的額外上下文
}

// AIReplyResponse.java
public class AIReplyResponse {
    private String replyText;  // AI 生成的回覆
    private ToneStyle toneStyle;  // 使用的語氣
    private Integer tokensUsed;  // 消耗的 token 數
    private Boolean isFromCache;  // 是否來自快取
    private LocalDateTime generatedAt;
}

// MessageAnalysisResponse.java
public class MessageAnalysisResponse {
    private String sentiment;  // POSITIVE, NEUTRAL, NEGATIVE
    private Double sentimentScore;  // 情感分數 0.0-1.0
    private List<String> topics;  // 提取的主題
    private String suggestedTone;  // 建議的語氣風格
    private String analysis;  // 分析摘要
}

// EmotionalCoachResponse.java
public class EmotionalCoachResponse {
    private String advice;  // 個性化建議
    private List<String> tips;  // 實用技巧
    private String encouragement;  // 鼓勵訊息
}
```

#### Android Data Models (Kotlin)
```kotlin
// Room Entity
@Entity(tableName = "cached_replies")
data class CachedReply(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val replyText: String,
    val toneStyle: String,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + 3600000  // 1 hour
)

// API Response Model
@Serializable
data class AIReplyResponse(
    val replyText: String,
    val toneStyle: String,
    val tokensUsed: Int,
    val isFromCache: Boolean,
    val generatedAt: String
)

// UI State
data class KeyboardUiState(
    val isLoading: Boolean = false,
    val generatedReplies: List<String> = emptyList(),
    val selectedTone: ToneStyle = ToneStyle.HIGH_EQ,
    val error: String? = null
)

enum class ToneStyle {
    HUMOROUS, HIGH_EQ, GENTLE, CUTE, ROMANTIC, PROFESSIONAL, DIRECT, FLIRTY
}
```

#### iOS Data Models (Swift)
```swift
// CoreData Entity (自動生成)
extension CachedReply {
    @NSManaged var id: UUID
    @NSManaged var replyText: String
    @NSManaged var toneStyle: String
    @NSManaged var createdAt: Date
    @NSManaged var expiresAt: Date
}

// API Response Model
struct AIReplyResponse: Codable {
    let replyText: String
    let toneStyle: String
    let tokensUsed: Int
    let isFromCache: Bool
    let generatedAt: String
}

// UI State
@Observable
class KeyboardViewModel {
    var isLoading: Bool = false
    var generatedReplies: [String] = []
    var selectedTone: ToneStyle = .highEQ
    var errorMessage: String?
}

enum ToneStyle: String, CaseIterable, Codable {
    case humorous, highEQ, gentle, cute, romantic, professional, direct, flirty
}
```

### List of Tasks to be Completed

```yaml
=== PHASE 1: 基礎設施與後端核心 (Week 1-6) ===

Task 1: 環境設置與 Docker 基礎設施
CREATE docker/docker-compose.yml:
  - PATTERN: 遵循 INITIAL.md lines 600-728 的配置
  - 定義 MySQL 8.0, Redis 7, Spring Boot backend 服務
  - 配置健康檢查與依賴關係
  - 掛載持久化 volume

CREATE docker/mysql/init.sql:
  - PATTERN: 使用 INITIAL.md lines 456-598 的完整 schema
  - 創建 users, conversations, messages, emotional_feedback, subscriptions 表
  - 添加索引優化查詢效能
  - 插入測試資料(僅開發環境)

CREATE .env.example:
  - 列出所有必需的環境變數
  - 包含 MySQL, Redis, OpenAI API key, JWT secret
  - 提供預設值範例

VALIDATE:
  - 運行: docker-compose up -d
  - 檢查所有容器狀態: docker-compose ps
  - 測試 MySQL 連線: docker exec -it talkgenius-mysql mysql -u root -p
  - 測試 Redis 連線: docker exec -it talkgenius-redis redis-cli ping
  - 預期: 所有服務健康運行,MySQL 包含所有表

Task 2: Spring Boot 專案初始化與依賴配置
CREATE backend/pom.xml:
  - PATTERN: Spring Boot 3.2+, Java 17+
  - 添加依賴:
    - spring-boot-starter-web
    - spring-boot-starter-data-jpa
    - spring-boot-starter-security
    - spring-boot-starter-data-redis
    - spring-ai-openai-spring-boot-starter (0.8.0+)
    - mysql-connector-java
    - jjwt (JWT 處理)
    - lombok
    - spring-boot-starter-test

CREATE backend/src/main/resources/application.yml:
  - PATTERN: 多環境配置(dev, prod)
  - 配置 MySQL 連線池
  - 配置 Redis 連線
  - 配置 OpenAI API
  - JWT secret 從環境變數讀取

CREATE backend/src/main/java/com/talkgenius/TalkGeniusApplication.java:
  - PATTERN: 標準 Spring Boot 主類
  - @SpringBootApplication 註解

VALIDATE:
  - 運行: mvn clean install
  - 運行: mvn spring-boot:run
  - 檢查日誌無錯誤
  - 測試 /actuator/health 端點返回 200
  - 預期: 應用成功啟動並連線到 MySQL 和 Redis

Task 3: JPA Entities 與 Repository 層實現
CREATE backend/src/main/java/com/talkgenius/model/entity/:
  - MIRROR pattern from: 上方 "Data Models" 章節
  - 創建所有 5 個實體類(User, Conversation, Message, EmotionalFeedback, Subscription)
  - 使用 @Entity, @Table, @Id, @GeneratedValue 註解
  - 添加 @CreatedDate, @LastModifiedDate 審計欄位
  - 使用 Lombok @Data, @NoArgsConstructor, @AllArgsConstructor

CREATE backend/src/main/java/com/talkgenius/repository/:
  - PATTERN: 繼承 JpaRepository<Entity, ID>
  - UserRepository: 添加 findByEmail(), existsByEmail()
  - ConversationRepository: 添加 findByUserId()
  - MessageRepository: 添加 findByConversationId(), findByToneStyle()
  - EmotionalFeedbackRepository: 添加 findByUserId(), findByMessageId()
  - SubscriptionRepository: 添加 findByUserId()

VALIDATE:
  - 運行: mvn test -Dtest=*RepositoryTest
  - 創建 JPA 測試類測試 CRUD 操作
  - 檢查 Hibernate 生成的 SQL
  - 預期: 所有 Repository 方法正常運作,資料正確持久化

Task 4: Spring Security + JWT 認證系統
CREATE backend/src/main/java/com/talkgenius/security/JwtTokenProvider.java:
  - PATTERN: 使用 jjwt 庫生成與驗證 JWT
  - generateAccessToken(userId): 有效期 1 小時
  - generateRefreshToken(userId): 有效期 30 天
  - validateToken(token): 驗證簽名與過期時間
  - getUserIdFromToken(token): 提取 userId claim

CREATE backend/src/main/java/com/talkgenius/security/JwtAuthenticationFilter.java:
  - PATTERN: 繼承 OncePerRequestFilter
  - 從 Authorization header 提取 Bearer token
  - 驗證 token 並設置 SecurityContext
  - 處理 token 過期或無效的情況

CREATE backend/src/main/java/com/talkgenius/config/SecurityConfig.java:
  - PATTERN: @Configuration + @EnableWebSecurity
  - 配置 SecurityFilterChain
  - 公開端點: /api/auth/**, /actuator/**
  - 受保護端點: /api/messages/**, /api/conversations/**
  - 添加 JwtAuthenticationFilter 到過濾鏈
  - 配置 CORS

CREATE backend/src/main/java/com/talkgenius/service/AuthService.java:
  - register(RegisterRequest): 創建用戶,加密密碼(bcrypt)
  - login(LoginRequest): 驗證憑證,返回 JWT tokens
  - refreshToken(String refreshToken): 刷新 access token

CREATE backend/src/main/java/com/talkgenius/controller/AuthController.java:
  - POST /api/auth/register: 註冊新用戶
  - POST /api/auth/login: 登入並返回 tokens
  - POST /api/auth/refresh: 刷新 access token

VALIDATE:
  - 單元測試: AuthServiceTest, JwtTokenProviderTest
  - 整合測試: AuthControllerTest
  - 測試註冊流程: curl -X POST http://localhost:8080/api/auth/register -d '{"email":"test@test.com","password":"password123"}'
  - 測試登入流程: 返回 accessToken 和 refreshToken
  - 測試受保護端點: 帶 token 訪問成功,不帶 token 返回 401
  - 預期: JWT 認證流程完整運作

Task 5: OpenAI GPT-4 整合與 AI 回覆生成
CREATE backend/src/main/java/com/talkgenius/config/OpenAIConfig.java:
  - PATTERN: 使用 Spring AI ChatClient
  - 配置 ChatClient.Builder
  - 設置預設 system message
  - 配置 temperature, max_tokens

CREATE backend/src/main/java/com/talkgenius/service/AIReplyService.java:
  - CRITICAL: 8 種語氣風格的 system prompts
  - generateReply(context, toneStyle): 調用 OpenAI API
  - 限流邏輯: 免費用戶 10 次/天(Redis 計數器)
  - 快取邏輯: 相同上下文+語氣的回覆快取 1 小時(Redis)
  - 錯誤處理: API 超時, token 超限, 餘額不足

SYSTEM PROMPTS (8 種語氣):
  HUMOROUS: "You are a witty dating assistant. Create funny, lighthearted replies that make people smile..."
  HIGH_EQ: "You are an emotionally intelligent dating coach. Craft empathetic, thoughtful replies..."
  GENTLE: "You are a gentle, caring companion. Generate warm, supportive messages..."
  CUTE: "You are a playful, adorable assistant. Create sweet, charming replies..."
  ROMANTIC: "You are a romantic poet. Craft passionate, heartfelt messages..."
  PROFESSIONAL: "You are a polite, professional communicator. Generate respectful, clear messages..."
  DIRECT: "You are a straightforward, honest advisor. Create direct, transparent replies..."
  FLIRTY: "You are a confident flirt. Generate playful, subtly suggestive messages..."

CREATE backend/src/main/java/com/talkgenius/controller/MessageController.java:
  - POST /api/messages/generate: 生成 AI 回覆
  - POST /api/messages/analyze: 分析訊息情感
  - POST /api/messages/coach: 獲取情感教練建議

VALIDATE:
  - 單元測試: AIReplyServiceTest (使用 mock ChatClient)
  - 整合測試: MessageControllerTest
  - 測試每種語氣風格生成回覆
  - 測試限流: 免費用戶第 11 次請求返回 429
  - 測試快取: 相同請求第二次命中快取,無需調用 API
  - 預期: AI 回覆正確生成,限流和快取正常運作

Task 6: 訊息分析與情感教練服務
CREATE backend/src/main/java/com/talkgenius/service/MessageAnalysisService.java:
  - analyzeMessage(content): 調用 OpenAI API 分析情感
  - extractTopics(content): 提取對話主題
  - suggestTone(content): 根據上下文建議最佳語氣
  - PATTERN: 使用 GPT-4 的 JSON mode 返回結構化資料

CREATE backend/src/main/java/com/talkgenius/service/EmotionalCoachService.java:
  - provideAdvice(userId, messageId): 基於歷史反饋提供個性化建議
  - 查詢用戶的 EmotionalFeedback 歷史
  - 分析用戶偏好(最常用語氣, 評分最高的回覆)
  - 生成個性化提示與鼓勵

VALIDATE:
  - 測試訊息分析: 輸入 "I miss you", 返回 sentiment=POSITIVE, topics=["longing","affection"]
  - 測試語氣建議: 輸入嚴肅訊息,建議 PROFESSIONAL 或 GENTLE
  - 測試情感教練: 有歷史資料的用戶獲得個性化建議
  - 預期: 分析準確,建議有幫助

Task 7: Subscription 與 Freemium 限制實現
CREATE backend/src/main/java/com/talkgenius/service/SubscriptionService.java:
  - checkSubscriptionStatus(userId): 檢查用戶是否為付費會員
  - upgradeToPremium(userId): 升級到付費會員
  - checkDailyLimit(userId): 檢查免費用戶每日限制
  - PATTERN: 使用 Redis 存儲每日計數器
  - Key: "daily_limit:{userId}:{date}", TTL: 24 hours

MODIFY AIReplyService:
  - INJECT before generateReply(): subscriptionService.checkDailyLimit()
  - If limit exceeded and not premium: throw DailyLimitExceededException

CREATE backend/src/main/java/com/talkgenius/exception/DailyLimitExceededException.java:
  - 自定義異常,返回 HTTP 429

CREATE backend/src/main/java/com/talkgenius/exception/GlobalExceptionHandler.java:
  - @ControllerAdvice 全局異常處理
  - 處理 DailyLimitExceededException, AuthenticationException, ValidationException

VALIDATE:
  - 測試免費用戶限制: 第 10 次成功,第 11 次返回 429
  - 測試付費用戶: 無限制
  - 測試每日重置: 24 小時後計數器歸零
  - 預期: Freemium 邏輯正確實施

Task 8: 後端整合測試與 API 文檔
CREATE backend/src/test/java/com/talkgenius/integration/:
  - E2EFlowTest.java: 測試完整流程
    1. 註冊用戶
    2. 登入獲取 token
    3. 生成 AI 回覆(10 次)
    4. 第 11 次觸發限制
    5. 提交情感反饋
    6. 獲取教練建議

CREATE docs/API.md:
  - 列出所有端點
  - Request/Response 範例
  - 錯誤碼說明
  - 認證說明

VALIDATE:
  - 運行: mvn test
  - 檢查覆蓋率: mvn jacoco:report (目標 80%+)
  - 運行整合測試: E2EFlowTest 通過
  - 預期: 所有測試通過,覆蓋率達標

=== PHASE 2: Android 客戶端開發 (Week 7-12) ===

Task 9: Android 專案初始化與依賴配置
CREATE android/settings.gradle.kts:
  - 包含 :app module

CREATE android/app/build.gradle.kts:
  - PATTERN: Kotlin DSL, Jetpack Compose
  - Dependencies:
    - androidx.compose.ui, material3
    - androidx.lifecycle:lifecycle-viewmodel-compose
    - androidx.room:room-runtime, room-ktx
    - com.squareup.retrofit2:retrofit, converter-gson
    - androidx.datastore:datastore-preferences
    - androidx.security:security-crypto (EncryptedSharedPreferences)
  - Kotlin plugin, kapt

CREATE android/app/src/main/AndroidManifest.xml:
  - CRITICAL: 聲明 InputMethodService
  - 請求權限: INTERNET, READ_CLIPBOARD
  - 配置 sharedUserId (與主應用共享資料)

MANIFEST 範例:
```xml
<service
    android:name=".keyboard.TalkGeniusKeyboard"
    android:label="@string/keyboard_name"
    android:permission="android.permission.BIND_INPUT_METHOD">
    <intent-filter>
        <action android:name="android.view.InputMethod" />
    </intent-filter>
    <meta-data
        android:name="android.view.im"
        android:resource="@xml/method" />
</service>
```

VALIDATE:
  - 運行: ./gradlew build
  - 檢查無編譯錯誤
  - 運行: ./gradlew app:installDebug
  - 預期: APK 成功安裝到設備

Task 10: Android 自定義鍵盤服務實現
CREATE android/app/src/main/java/com/talkgenius/keyboard/TalkGeniusKeyboard.kt:
  - PATTERN: 繼承 InputMethodService
  - override onCreateInputView(): 返回 ComposeView
  - override onStartInputView(): 初始化鍵盤狀態
  - getCurrentInputConnection(): 與文本欄位交互

CREATE android/app/src/main/java/com/talkgenius/keyboard/KeyboardView.kt:
  - PATTERN: Jetpack Compose @Composable
  - Material 3 主題
  - 語氣選擇器(8 個按鈕)
  - AI 回覆顯示區域(LazyColumn)
  - 載入指示器

CREATE android/app/src/main/java/com/talkgenius/keyboard/KeyboardViewModel.kt:
  - PATTERN: ViewModel with StateFlow
  - State: KeyboardUiState
  - Functions:
    - fetchClipboardContext(): 從剪貼簿獲取對話
    - generateReplies(toneStyle): 調用 API
    - insertReply(text): 使用 getCurrentInputConnection().commitText()

CREATE android/app/src/main/java/com/talkgenius/keyboard/ClipboardManager.kt:
  - PATTERN: 單例模式
  - getLatestClipboardText(): 獲取剪貼簿內容
  - extractConversationContext(text): 解析對話上下文

VALIDATE:
  - 在設備設定中啟用 TalkGeniusKeyboard
  - 在任何文本欄位切換到自定義鍵盤
  - 測試剪貼簿讀取
  - 測試文本插入
  - 預期: 鍵盤正常顯示,可以插入文本

Task 11: Android Retrofit API 整合
CREATE android/app/src/main/java/com/talkgenius/api/ApiService.kt:
  - PATTERN: Retrofit interface
  - @POST("/api/auth/register")
  - @POST("/api/auth/login")
  - @POST("/api/messages/generate")
  - @POST("/api/messages/analyze")

CREATE android/app/src/main/java/com/talkgenius/api/AuthInterceptor.kt:
  - PATTERN: OkHttp Interceptor
  - 從 EncryptedSharedPreferences 讀取 access token
  - 添加 Authorization: Bearer {token} header
  - 處理 401 錯誤自動刷新 token

CREATE android/app/src/main/java/com/talkgenius/api/RetrofitClient.kt:
  - PATTERN: 單例模式
  - 配置 OkHttpClient
  - 添加 AuthInterceptor
  - 添加 logging interceptor (僅開發環境)

VALIDATE:
  - 單元測試: ApiServiceTest (使用 MockWebServer)
  - 測試登入請求返回正確 DTO
  - 測試 token 自動添加到 header
  - 測試 401 自動刷新 token
  - 預期: API 整合正常運作

Task 12: Android Room 資料庫與 Repository
CREATE android/app/src/main/java/com/talkgenius/data/local/TalkGeniusDatabase.kt:
  - PATTERN: @Database with entities
  - Version: 1
  - Entities: CachedReply

CREATE android/app/src/main/java/com/talkgenius/data/local/dao/CachedReplyDao.kt:
  - @Dao interface
  - @Query("SELECT * FROM cached_replies WHERE expiresAt > :currentTime")
  - @Insert, @Delete

CREATE android/app/src/main/java/com/talkgenius/data/repository/MessageRepository.kt:
  - PATTERN: Repository pattern
  - 結合 API 和本地資料庫
  - getCachedReply(): 先檢查本地快取
  - generateReply(): API 請求,成功後快取到 Room

VALIDATE:
  - 測試快取流程: 第一次調用 API,第二次從 Room 讀取
  - 測試快取過期: expiresAt 過期後重新調用 API
  - 預期: Repository 正確協調 API 和資料庫

Task 13: Android 認證與主應用 UI
CREATE android/app/src/main/java/com/talkgenius/ui/auth/LoginScreen.kt:
  - PATTERN: Jetpack Compose + ViewModel
  - Email 和 Password TextField
  - Login Button
  - 導航到 RegisterScreen

CREATE android/app/src/main/java/com/talkgenius/ui/auth/AuthViewModel.kt:
  - login(email, password): 調用 API, 存儲 token
  - register(email, password): 調用 API, 自動登入
  - logout(): 清除 token

CREATE android/app/src/main/java/com/talkgenius/ui/MainActivity.kt:
  - PATTERN: Single Activity Architecture
  - Jetpack Navigation Compose
  - 根據 token 存在與否顯示 LoginScreen 或 SettingsScreen

CREATE android/app/src/main/java/com/talkgenius/ui/settings/SettingsScreen.kt:
  - 顯示用戶資訊
  - 訂閱狀態與升級按鈕
  - 登出按鈕
  - 鍵盤啟用指引

VALIDATE:
  - 測試註冊流程: 創建新用戶
  - 測試登入流程: 成功後進入 SettingsScreen
  - 測試登出: token 清除,返回 LoginScreen
  - 預期: 認證流程完整

Task 14: Android 單元與 UI 測試
CREATE android/app/src/test/java/com/talkgenius/keyboard/KeyboardViewModelTest.kt:
  - 測試 generateReplies() 成功情況
  - 測試 API 錯誤處理
  - 測試 loading state 切換

CREATE android/app/src/test/java/com/talkgenius/repository/MessageRepositoryTest.kt:
  - 測試快取邏輯
  - 測試 API fallback

CREATE android/app/src/androidTest/java/com/talkgenius/ui/LoginScreenTest.kt:
  - PATTERN: Espresso + Compose Testing
  - 測試登入流程
  - 測試表單驗證

VALIDATE:
  - 運行: ./gradlew test
  - 運行: ./gradlew connectedAndroidTest
  - 檢查覆蓋率 80%+
  - 預期: 所有測試通過

Task 15: Android 代碼品質與打包
RUN:
  - ./gradlew ktlintCheck (Kotlin linter)
  - ./gradlew ktlintFormat (自動修復)

CREATE android/app/proguard-rules.pro:
  - 保留 Retrofit, Room, Gson 相關類
  - 混淆優化

BUILD Release APK:
  - ./gradlew assembleRelease
  - 簽名 APK (keystore)

VALIDATE:
  - APK 成功安裝到設備
  - ProGuard 後應用正常運行
  - 預期: 可分發的 Release APK

=== PHASE 3: iOS 客戶端開發 (Week 7-12, 與 Android 並行) ===

Task 16: iOS 專案初始化與依賴配置
CREATE ios/TalkGenius.xcodeproj:
  - PATTERN: Xcode 15+, Swift 5.9+
  - Targets: TalkGenius (主應用), TalkGeniusKeyboard (鍵盤擴展)
  - Minimum Deployment: iOS 16.0

CREATE ios/Podfile (或 Package.swift):
  - Dependencies:
    - Alamofire (網路請求)
    - SwiftKeychainWrapper (安全存儲)
  - CRITICAL: 鍵盤擴展不支援所有 pods,需檢查兼容性

CREATE ios/TalkGenius/Info.plist:
  - 配置 App Groups: group.com.talkgenius.app
  - 請求權限: NSUserTrackingUsageDescription

CREATE ios/TalkGeniusKeyboard/Info.plist:
  - 配置鍵盤擴展
  - RequestsOpenAccess: YES (允許網路訪問)
  - App Groups: group.com.talkgenius.app

VALIDATE:
  - 在 Xcode 中構建專案
  - 檢查無編譯錯誤
  - 運行 Simulator
  - 預期: 應用成功啟動

Task 17: iOS 自定義鍵盤擴展實現
CREATE ios/TalkGeniusKeyboard/KeyboardViewController.swift:
  - PATTERN: 繼承 UIInputViewController
  - override viewDidLoad(): 嵌入 SwiftUI KeyboardView
  - textDocumentProxy: 與文本欄位交互
  - 記憶體管理: 避免超過 48MB 限制

CREATE ios/TalkGeniusKeyboard/KeyboardView.swift:
  - PATTERN: SwiftUI @Observable
  - Tone selector (HStack with 8 buttons)
  - AI reply list (List)
  - Loading indicator (ProgressView)

CREATE ios/TalkGeniusKeyboard/KeyboardViewModel.swift:
  - @Observable class
  - @Published var state: KeyboardUiState
  - Functions:
    - fetchClipboardContext(): 從 UIPasteboard 讀取
    - generateReplies(toneStyle): 調用 API
    - insertReply(text): 使用 textDocumentProxy.insertText()

CREATE ios/TalkGeniusKeyboard/ClipboardManager.swift:
  - 單例模式
  - getLatestClipboardText(): UIPasteboard.general.string
  - extractConversationContext(text): 解析對話

VALIDATE:
  - 在設定中啟用鍵盤(Settings -> General -> Keyboard -> Keyboards -> Add New Keyboard)
  - 授予完全訪問權限(Full Access)
  - 在任何 app 切換到 TalkGenius 鍵盤
  - 測試剪貼簿讀取
  - 測試文本插入
  - 預期: 鍵盤正常顯示並運作

Task 18: iOS API 整合與認證
CREATE ios/TalkGenius/API/APIService.swift:
  - PATTERN: Singleton with URLSession
  - Functions:
    - register(email, password) async throws -> AuthResponse
    - login(email, password) async throws -> AuthResponse
    - generateReply(request) async throws -> AIReplyResponse
  - 使用 async/await

CREATE ios/TalkGenius/API/AuthManager.swift:
  - PATTERN: Singleton
  - Store tokens in Keychain (不是 UserDefaults!)
  - saveTokens(access, refresh)
  - getAccessToken() -> String?
  - refreshAccessToken() async throws

CREATE ios/TalkGenius/API/APIClient.swift:
  - PATTERN: URLSession wrapper
  - 自動添加 Authorization header
  - 處理 401 自動刷新 token
  - 錯誤處理與重試邏輯

VALIDATE:
  - 單元測試: APIServiceTests (使用 URLProtocol mock)
  - 測試登入返回正確 response
  - 測試 token 自動添加
  - 測試 401 刷新流程
  - 預期: API 整合正常

Task 19: iOS CoreData 資料庫與 Repository
CREATE ios/TalkGenius/Data/CoreDataStack.swift:
  - PATTERN: NSPersistentContainer with App Groups
  - 配置共享容器: group.com.talkgenius.app
  - lazy var context: NSManagedObjectContext

CREATE ios/TalkGenius/Data/Models/CachedReply.xcdatamodeld:
  - Entity: CachedReply
  - Attributes: id (UUID), replyText (String), toneStyle (String), createdAt (Date), expiresAt (Date)

CREATE ios/TalkGenius/Data/Repositories/MessageRepository.swift:
  - getCachedReply(for context: String, tone: ToneStyle) -> CachedReply?
  - saveReply(_ reply: AIReplyResponse)
  - deleteExpiredReplies()

VALIDATE:
  - 測試快取流程
  - 測試 App Groups 資料共享(主應用寫入,鍵盤擴展讀取)
  - 預期: CoreData 在主應用和鍵盤擴展間正常共享

Task 20: iOS 主應用 UI (SwiftUI)
CREATE ios/TalkGenius/App/TalkGeniusApp.swift:
  - @main App
  - 根據認證狀態顯示 LoginView 或 ContentView

CREATE ios/TalkGenius/Auth/LoginView.swift:
  - TextField for email
  - SecureField for password
  - Login Button
  - Navigation to RegisterView

CREATE ios/TalkGenius/Auth/AuthViewModel.swift:
  - @Observable class
  - login(email, password) async
  - register(email, password) async
  - logout()

CREATE ios/TalkGenius/Settings/SettingsView.swift:
  - 用戶資訊顯示
  - 訂閱狀態
  - 鍵盤啟用教學
  - 登出按鈕

VALIDATE:
  - 測試註冊/登入流程
  - 測試導航
  - 預期: 認證流程完整

Task 21: iOS 單元與 UI 測試
CREATE ios/TalkGeniusTests/KeyboardViewModelTests.swift:
  - 測試 generateReplies() 成功
  - 測試錯誤處理
  - 測試 state 更新

CREATE ios/TalkGeniusTests/MessageRepositoryTests.swift:
  - 測試快取邏輯
  - 測試 CoreData CRUD

CREATE ios/TalkGeniusUITests/LoginViewTests.swift:
  - PATTERN: XCTest UI Testing
  - 測試登入流程
  - 測試表單驗證

VALIDATE:
  - 運行: Cmd+U (單元測試)
  - 運行: Cmd+Shift+U (UI 測試)
  - 檢查覆蓋率 80%+
  - 預期: 所有測試通過

Task 22: iOS 代碼品質與打包
RUN:
  - SwiftLint (Swift linter)
  - 修復所有警告

BUILD Release IPA:
  - Archive in Xcode
  - Export IPA for Ad-Hoc distribution

VALIDATE:
  - 在真實設備安裝 IPA
  - 測試鍵盤功能
  - 預期: 可分發的 Release IPA

=== PHASE 4: 整合測試與部署 (Week 13-18) ===

Task 23: 端對端整合測試
CREATE tests/e2e/:
  - test_android_full_flow.py: 使用 Appium 測試 Android 完整流程
  - test_ios_full_flow.py: 使用 Appium 測試 iOS 完整流程
  - test_backend_load.py: 使用 Locust 壓力測試後端

FLOW 測試:
  1. 註冊新用戶
  2. 登入獲取 token
  3. 啟用鍵盤
  4. 複製對話到剪貼簿
  5. 在鍵盤生成回覆(8 種語氣各一次)
  6. 插入回覆到文本欄位
  7. 提交情感反饋
  8. 獲取教練建議
  9. 達到每日限制
  10. 升級到付費會員
  11. 無限制生成回覆

VALIDATE:
  - 所有 E2E 測試通過
  - 壓力測試: 100 並發用戶,響應時間 < 2s
  - 預期: 整個系統穩定運行

Task 24: 安全性測試與審計
RUN:
  - OWASP ZAP 掃描後端 API
  - 測試 SQL Injection, XSS
  - 測試 JWT token 竄改
  - 測試 HTTPS/TLS 配置
  - 測試資料加密(bcrypt, AES)

VALIDATE:
  - 無高危漏洞
  - 密碼正確加密
  - Token 無法偽造
  - HTTPS 強制執行
  - 預期: 通過安全審計

Task 25: 效能優化
BACKEND:
  - 添加資料庫索引(users.email, messages.conversation_id)
  - 配置 Redis 快取策略
  - 配置 Spring Boot Tomcat 連線池
  - 優化 N+1 查詢(使用 @EntityGraph)

ANDROID:
  - 使用 ProGuard 縮小 APK
  - 圖片壓縮與 WebP
  - 延遲載入(Lazy loading)

IOS:
  - 鍵盤擴展記憶體優化(< 40MB)
  - 圖片資產優化
  - 減少網路請求(批次 API)

VALIDATE:
  - APK < 20MB, IPA < 25MB
  - 鍵盤啟動時間 < 1s
  - API 響應時間 < 500ms (P95)
  - 預期: 效能指標達標

Task 26: CI/CD Pipeline 設置
CREATE .github/workflows/backend-ci.yml:
  - 觸發: push to main
  - Steps:
    1. Checkout code
    2. Setup Java 17
    3. Run mvn test
    4. Run security scan (OWASP Dependency-Check)
    5. Build Docker image
    6. Push to Docker Hub

CREATE .github/workflows/android-ci.yml:
  - 觸發: push to main
  - Steps:
    1. Checkout code
    2. Setup Android SDK
    3. Run ./gradlew test
    4. Run ./gradlew assembleRelease
    5. Upload APK artifact

CREATE .github/workflows/ios-ci.yml:
  - 觸發: push to main
  - Steps:
    1. Checkout code
    2. Setup Xcode
    3. Run xcodebuild test
    4. Run xcodebuild archive
    5. Upload IPA artifact

VALIDATE:
  - 所有 workflows 成功執行
  - Artifacts 正確上傳
  - 預期: CI/CD 自動化

Task 27: Docker 生產部署配置
CREATE docker/docker-compose.prod.yml:
  - 使用 .env.prod 環境變數
  - 配置 MySQL 持久化 volume
  - 配置 Redis 持久化(AOF + RDB)
  - 配置 nginx 反向代理(HTTPS)
  - 配置健康檢查與自動重啟

CREATE docker/nginx/nginx.conf:
  - 反向代理 Spring Boot backend
  - SSL/TLS 配置
  - Rate limiting (防止 DDoS)

VALIDATE:
  - 運行: docker-compose -f docker-compose.prod.yml up -d
  - 測試 HTTPS 訪問
  - 測試資料持久化(重啟容器後資料仍存在)
  - 預期: 生產環境穩定運行

Task 28: 監控與日誌系統
ADD to docker-compose.prod.yml:
  - Prometheus (指標收集)
  - Grafana (視覺化儀表板)
  - ELK Stack (Elasticsearch, Logstash, Kibana) 日誌分析

CONFIGURE Spring Boot:
  - Micrometer + Prometheus
  - 暴露 /actuator/prometheus 端點
  - 配置 Logback 輸出 JSON 格式日誌

DASHBOARD:
  - API 請求率, 響應時間, 錯誤率
  - OpenAI API 調用次數與 token 消耗
  - 資料庫連線池狀態
  - Redis 命中率

VALIDATE:
  - Grafana 儀表板正確顯示指標
  - 日誌正確索引到 Elasticsearch
  - 預期: 完整的可觀測性

Task 29: 文檔與部署指南
CREATE README.md:
  - 專案概述
  - 功能列表
  - 技術棧
  - 快速開始(Docker Compose)
  - 本地開發設置

CREATE docs/DEPLOYMENT.md:
  - 生產部署步驟
  - 環境變數配置
  - SSL 證書設置
  - 備份策略
  - 故障排除

CREATE docs/API.md:
  - 所有端點列表
  - Request/Response 範例
  - 錯誤碼
  - 認證流程

CREATE docs/ARCHITECTURE.md:
  - 系統架構圖
  - 資料流圖
  - 技術決策說明

VALIDATE:
  - 新開發者能根據文檔在 30 分鐘內啟動專案
  - 預期: 完整可用的文檔

Task 30: 最終驗收與發布
FINAL CHECKLIST:
  - [ ] 所有單元測試通過(覆蓋率 80%+)
  - [ ] 所有整合測試通過
  - [ ] 所有 E2E 測試通過
  - [ ] 安全審計通過
  - [ ] 效能測試達標
  - [ ] CI/CD Pipeline 正常運行
  - [ ] Docker 生產環境穩定
  - [ ] 監控系統正常
  - [ ] 文檔完整
  - [ ] Android APK 可安裝並運行
  - [ ] iOS IPA 可安裝並運行

DELIVERABLES:
  1. Android APK (Release, signed)
  2. iOS IPA (Ad-Hoc distribution)
  3. Docker images (talkgenius-backend, talkgenius-mysql, talkgenius-redis)
  4. docker-compose.prod.yml
  5. 完整文檔(README, API, DEPLOYMENT, ARCHITECTURE)
  6. CI/CD workflows

VALIDATE:
  - 提交所有 deliverables 給 QA 團隊
  - QA 驗收通過
  - 預期: 專案可以正式發布
```

### Per Task Pseudocode (關鍵任務)

```java
// Task 5: AI Reply Generation with 8 Tone Styles
@Service
public class AIReplyService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SubscriptionService subscriptionService;

    private static final Map<ToneStyle, String> SYSTEM_PROMPTS = Map.of(
        ToneStyle.HUMOROUS, """
            You are a witty dating assistant. Create funny, lighthearted replies that make people smile
            while maintaining respect. Use clever wordplay, gentle teasing, and humor that shows personality
            without being offensive. Keep responses concise (2-3 sentences) and natural.
            """,
        ToneStyle.HIGH_EQ, """
            You are an emotionally intelligent dating coach. Craft empathetic, thoughtful replies that
            demonstrate understanding, validate feelings, and build emotional connection. Show genuine
            interest, ask meaningful questions, and respond with warmth. Keep responses authentic and caring.
            """,
        // ... 其他 6 種語氣的 prompts
    );

    public AIReplyResponse generateReply(String userId, GenerateReplyRequest request) {
        // CRITICAL: 檢查訂閱狀態與每日限制
        subscriptionService.checkDailyLimit(userId);

        // PATTERN: 先檢查快取(相同上下文+語氣)
        String cacheKey = generateCacheKey(request.getClipboardContext(), request.getToneStyle());
        String cachedReply = redisTemplate.opsForValue().get(cacheKey);

        if (cachedReply != null) {
            return AIReplyResponse.builder()
                .replyText(cachedReply)
                .toneStyle(request.getToneStyle())
                .isFromCache(true)
                .generatedAt(LocalDateTime.now())
                .build();
        }

        // PATTERN: 調用 OpenAI API
        String systemPrompt = SYSTEM_PROMPTS.get(request.getToneStyle());
        String userPrompt = String.format("""
            Based on this dating app conversation context:

            %s

            Generate a single, engaging reply that continues the conversation naturally.
            Additional context: %s
            """,
            request.getClipboardContext(),
            request.getAdditionalContext()
        );

        ChatResponse response = chatClient.prompt()
            .system(systemPrompt)
            .user(userPrompt)
            .call()
            .chatResponse();

        String replyText = response.getResult().getOutput().getContent();
        Integer tokensUsed = response.getMetadata().getUsage().getTotalTokens();

        // CRITICAL: 快取回覆 1 小時
        redisTemplate.opsForValue().set(cacheKey, replyText, 1, TimeUnit.HOURS);

        return AIReplyResponse.builder()
            .replyText(replyText)
            .toneStyle(request.getToneStyle())
            .tokensUsed(tokensUsed)
            .isFromCache(false)
            .generatedAt(LocalDateTime.now())
            .build();
    }

    private String generateCacheKey(String context, ToneStyle tone) {
        // PATTERN: Hash context to create cache key
        String hash = DigestUtils.md5Hex(context);
        return String.format("reply:%s:%s", hash, tone.name());
    }
}
```

```kotlin
// Task 10: Android Keyboard View with Compose
@Composable
fun KeyboardView(
    viewModel: KeyboardViewModel,
    onInsertText: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // PATTERN: Tone selector (8 buttons in 2 rows)
        ToneSelector(
            selectedTone = uiState.selectedTone,
            onToneSelected = { viewModel.selectTone(it) }
        )

        Divider()

        // PATTERN: AI replies display
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                ErrorMessage(
                    message = uiState.error,
                    onRetry = { viewModel.generateReplies() }
                )
            }

            uiState.generatedReplies.isNotEmpty() -> {
                LazyColumn {
                    items(uiState.generatedReplies) { reply ->
                        ReplyCard(
                            text = reply,
                            onClick = { onInsertText(reply) }
                        )
                    }
                }
            }

            else -> {
                EmptyState(
                    onGenerateClick = { viewModel.generateReplies() }
                )
            }
        }
    }
}

@Composable
fun ToneSelector(
    selectedTone: ToneStyle,
    onToneSelected: (ToneStyle) -> Unit
) {
    // PATTERN: 2 rows x 4 columns grid
    Column {
        val tones = ToneStyle.values()
        val chunked = tones.toList().chunked(4)

        chunked.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { tone ->
                    FilterChip(
                        selected = tone == selectedTone,
                        onClick = { onToneSelected(tone) },
                        label = { Text(tone.displayName) }
                    )
                }
            }
        }
    }
}
```

```swift
// Task 17: iOS Keyboard ViewController with SwiftUI
class KeyboardViewController: UIInputViewController {

    private var viewModel: KeyboardViewModel!
    private var hostingController: UIHostingController<KeyboardView>!

    override func viewDidLoad() {
        super.viewDidLoad()

        // PATTERN: Inject textDocumentProxy into ViewModel
        viewModel = KeyboardViewModel(textProxy: textDocumentProxy)

        let keyboardView = KeyboardView(viewModel: viewModel)
        hostingController = UIHostingController(rootView: keyboardView)

        addChild(hostingController)
        view.addSubview(hostingController.view)
        hostingController.didMove(toParent: self)

        // CRITICAL: Set constraints
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            hostingController.view.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            hostingController.view.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            hostingController.view.topAnchor.constraint(equalTo: view.topAnchor),
            hostingController.view.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }
}

@Observable
class KeyboardViewModel {
    var isLoading = false
    var generatedReplies: [String] = []
    var selectedTone: ToneStyle = .highEQ
    var errorMessage: String?

    private let textProxy: UITextDocumentProxy
    private let apiService: APIService

    init(textProxy: UITextDocumentProxy) {
        self.textProxy = textProxy
        self.apiService = APIService.shared
    }

    func generateReplies() async {
        isLoading = true
        errorMessage = nil

        // PATTERN: Get clipboard context
        guard let clipboardText = UIPasteboard.general.string else {
            errorMessage = "無法讀取剪貼簿內容"
            isLoading = false
            return
        }

        let request = GenerateReplyRequest(
            clipboardContext: clipboardText,
            toneStyle: selectedTone
        )

        do {
            let response = try await apiService.generateReply(request: request)
            generatedReplies = [response.replyText]
            isLoading = false
        } catch {
            errorMessage = "生成回覆失敗: \(error.localizedDescription)"
            isLoading = false
        }
    }

    func insertReply(_ text: String) {
        // CRITICAL: Use textDocumentProxy to insert text
        textProxy.insertText(text)
    }
}
```

### Integration Points

```yaml
DATABASE MIGRATIONS:
  - Task 1: 執行 docker/mysql/init.sql 初始化所有表
  - Task 3: Spring Boot 啟動時 Hibernate 驗證 schema
  - 索引策略:
    - users.email (UNIQUE INDEX)
    - messages.conversation_id (INDEX)
    - conversations.user_id (INDEX)
    - emotional_feedback.user_id (INDEX)
    - emotional_feedback.message_id (INDEX)

ENVIRONMENT VARIABLES:
  Backend (.env):
    - MYSQL_HOST=localhost
    - MYSQL_PORT=3306
    - MYSQL_DATABASE=talkgenius
    - MYSQL_USER=root
    - MYSQL_PASSWORD=your_password
    - REDIS_HOST=localhost
    - REDIS_PORT=6379
    - OPENAI_API_KEY=sk-your-key
    - JWT_SECRET=your-secret-key-minimum-256-bits
    - JWT_ACCESS_EXPIRATION=3600  # 1 hour
    - JWT_REFRESH_EXPIRATION=2592000  # 30 days

  Android (local.properties):
    - api.base.url=http://10.0.2.2:8080/api  # Android emulator
    - api.base.url=https://api.talkgenius.com/api  # Production

  iOS (Config.plist):
    - APIBaseURL=http://localhost:8080/api  # Simulator
    - APIBaseURL=https://api.talkgenius.com/api  # Production

API CONTRACTS:
  - POST /api/auth/register
    Request: { "email": "user@example.com", "password": "password123" }
    Response: { "accessToken": "jwt...", "refreshToken": "jwt...", "user": {...} }

  - POST /api/auth/login
    Request: { "email": "user@example.com", "password": "password123" }
    Response: { "accessToken": "jwt...", "refreshToken": "jwt..." }

  - POST /api/messages/generate
    Headers: { "Authorization": "Bearer jwt..." }
    Request: { "clipboardContext": "對話內容", "toneStyle": "HIGH_EQ" }
    Response: { "replyText": "AI 生成的回覆", "toneStyle": "HIGH_EQ", "tokensUsed": 50, "isFromCache": false }

  - POST /api/messages/analyze
    Request: { "content": "訊息內容" }
    Response: { "sentiment": "POSITIVE", "sentimentScore": 0.85, "topics": ["dating","travel"], "suggestedTone": "ROMANTIC" }

DOCKER NETWORKING:
  - Network: talkgenius-network (bridge)
  - Services:
    - mysql: talkgenius-mysql, port 3306
    - redis: talkgenius-redis, port 6379
    - backend: talkgenius-backend, port 8080
  - Health Checks:
    - MySQL: mysqladmin ping -h localhost
    - Redis: redis-cli ping
    - Backend: curl http://localhost:8080/actuator/health

APP GROUPS (iOS):
  - Identifier: group.com.talkgenius.app
  - Shared Resources:
    - CoreData database
    - UserDefaults (preferences)
    - Keychain (tokens)
```

## Validation Loop

### Level 1: Syntax & Style

```bash
# Backend (Java/Spring Boot)
cd backend
mvn checkstyle:check              # 代碼風格檢查
mvn spotbugs:check                # 靜態分析
mvn compile                       # 編譯檢查

# Expected: No errors. If errors, READ and fix.

# Android (Kotlin)
cd android
./gradlew ktlintCheck             # Kotlin linter
./gradlew lintDebug               # Android lint
./gradlew compileDebugKotlin      # 編譯檢查

# Expected: No errors or warnings.

# iOS (Swift)
cd ios
swiftlint lint                    # Swift linter
xcodebuild -scheme TalkGenius -destination 'platform=iOS Simulator,name=iPhone 15' build

# Expected: Build succeeds with no warnings.
```

### Level 2: Unit Tests

```java
// Backend: AIReplyServiceTest.java
@SpringBootTest
class AIReplyServiceTest {

    @Autowired
    private AIReplyService aiReplyService;

    @MockBean
    private ChatClient chatClient;

    @Test
    void testGenerateReply_Success() {
        // GIVEN
        String userId = "test-user-id";
        GenerateReplyRequest request = new GenerateReplyRequest();
        request.setClipboardContext("Hey, how was your weekend?");
        request.setToneStyle(ToneStyle.HUMOROUS);

        ChatResponse mockResponse = mock(ChatResponse.class);
        // ... mock ChatClient behavior

        // WHEN
        AIReplyResponse response = aiReplyService.generateReply(userId, request);

        // THEN
        assertNotNull(response.getReplyText());
        assertEquals(ToneStyle.HUMOROUS, response.getToneStyle());
        assertFalse(response.getIsFromCache());
    }

    @Test
    void testGenerateReply_CacheHit() {
        // Test cache returns saved reply
    }

    @Test
    void testGenerateReply_DailyLimitExceeded() {
        // Test free user exceeds 10 requests/day
        assertThrows(DailyLimitExceededException.class, () -> {
            // ... call generateReply 11 times
        });
    }
}
```

```kotlin
// Android: KeyboardViewModelTest.kt
@ExperimentalCoroutinesApi
class KeyboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: KeyboardViewModel
    private lateinit var mockApiService: ApiService

    @Before
    fun setup() {
        mockApiService = mock()
        viewModel = KeyboardViewModel(mockApiService)
    }

    @Test
    fun `generateReplies success updates state`() = runTest {
        // GIVEN
        val mockResponse = AIReplyResponse(
            replyText = "Generated reply",
            toneStyle = "HIGH_EQ",
            tokensUsed = 50,
            isFromCache = false,
            generatedAt = "2025-01-01T00:00:00"
        )
        whenever(mockApiService.generateReply(any())).thenReturn(mockResponse)

        // WHEN
        viewModel.generateReplies()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(listOf("Generated reply"), viewModel.uiState.value.generatedReplies)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `generateReplies API error sets error state`() = runTest {
        // GIVEN
        whenever(mockApiService.generateReply(any())).thenThrow(IOException("Network error"))

        // WHEN
        viewModel.generateReplies()

        // THEN
        assertNotNull(viewModel.uiState.value.error)
        assertTrue(viewModel.uiState.value.generatedReplies.isEmpty())
    }
}
```

```swift
// iOS: KeyboardViewModelTests.swift
import XCTest
@testable import TalkGenius

final class KeyboardViewModelTests: XCTestCase {

    var viewModel: KeyboardViewModel!
    var mockAPIService: MockAPIService!

    override func setUp() {
        super.setUp()
        mockAPIService = MockAPIService()
        viewModel = KeyboardViewModel(apiService: mockAPIService, textProxy: MockTextProxy())
    }

    func testGenerateRepliesSuccess() async throws {
        // GIVEN
        let mockResponse = AIReplyResponse(
            replyText: "Generated reply",
            toneStyle: "HIGH_EQ",
            tokensUsed: 50,
            isFromCache: false,
            generatedAt: "2025-01-01T00:00:00"
        )
        mockAPIService.generateReplyResult = .success(mockResponse)

        // WHEN
        await viewModel.generateReplies()

        // THEN
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertEqual(viewModel.generatedReplies, ["Generated reply"])
        XCTAssertNil(viewModel.errorMessage)
    }

    func testGenerateRepliesNetworkError() async throws {
        // GIVEN
        mockAPIService.generateReplyResult = .failure(NSError(domain: "Network", code: -1, userInfo: nil))

        // WHEN
        await viewModel.generateReplies()

        // THEN
        XCTAssertNotNil(viewModel.errorMessage)
        XCTAssertTrue(viewModel.generatedReplies.isEmpty())
    }
}
```

```bash
# Run all unit tests
cd backend && mvn test
cd android && ./gradlew test
cd ios && xcodebuild test -scheme TalkGenius -destination 'platform=iOS Simulator,name=iPhone 15'

# If failing: Debug specific test, fix code, re-run
# NEVER mock just to pass - fix the actual logic
```

### Level 3: Integration Test

```bash
# Step 1: Start Docker environment
docker-compose up -d
# Wait for all services to be healthy
docker-compose ps

# Step 2: Test Backend API
# Register user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Password123!"}'

# Expected: {"accessToken":"jwt...","refreshToken":"jwt...","user":{...}}

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Password123!"}'

# Save access token
export TOKEN="<access-token-from-response>"

# Generate AI reply
curl -X POST http://localhost:8080/api/messages/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "clipboardContext": "Hey, how was your weekend? I went hiking!",
    "toneStyle": "HUMOROUS"
  }'

# Expected: {"replyText":"Sounds like you had a peak experience! 🏔️ My weekend was... less vertical. Did you conquer any mountains or just hills?","toneStyle":"HUMOROUS",...}

# Step 3: Test Android App
./gradlew android:installDebug
adb shell am start -n com.talkgenius/.ui.MainActivity

# Manual steps:
# 1. Register/Login in app
# 2. Enable keyboard in Settings
# 3. Open any messaging app
# 4. Copy conversation to clipboard
# 5. Switch to TalkGenius keyboard
# 6. Select tone (e.g., "幽默")
# 7. Tap "生成回覆"
# 8. Verify AI reply displays
# 9. Tap reply to insert into text field

# Step 4: Test iOS App
xcodebuild -scheme TalkGenius -destination 'platform=iOS Simulator,name=iPhone 15' run

# Manual steps (same as Android):
# 1-9 same steps in iOS Simulator

# Step 5: Test daily limit
# Generate 11 replies as free user
for i in {1..11}; do
  curl -X POST http://localhost:8080/api/messages/generate \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"clipboardContext":"Test","toneStyle":"HIGH_EQ"}'
  echo "\nRequest $i completed"
done

# Expected: First 10 succeed, 11th returns 429 Too Many Requests
```

## Final Validation Checklist

- [ ] **Backend Tests**: All unit tests pass (`mvn test`), coverage > 80%
- [ ] **Backend Integration**: E2E flow test passes (register -> login -> generate -> limit)
- [ ] **Android Tests**: All unit tests pass (`./gradlew test`), coverage > 80%
- [ ] **Android App**: APK installs, keyboard works, API integration successful
- [ ] **iOS Tests**: All unit tests pass (`xcodebuild test`), coverage > 80%
- [ ] **iOS App**: IPA installs, keyboard works, API integration successful
- [ ] **Security**: OWASP ZAP scan passes, no high/critical vulnerabilities
- [ ] **Performance**: API response time < 500ms (P95), APK < 20MB, IPA < 25MB
- [ ] **Docker**: All containers healthy, data persists after restart
- [ ] **CI/CD**: All GitHub Actions workflows pass
- [ ] **Documentation**: README complete, API docs accurate, deployment guide clear
- [ ] **Linting**: No errors from ktlintCheck, SwiftLint, Checkstyle
- [ ] **Type Safety**: No errors from mypy (if Python scripts), TypeScript (if any)
- [ ] **8 Tone Styles**: All tones generate distinct, appropriate replies
- [ ] **Freemium Logic**: Free users limited to 10/day, premium unlimited
- [ ] **Clipboard Access**: Both Android and iOS keyboards can read clipboard
- [ ] **Text Insertion**: Generated replies correctly insert into text fields
- [ ] **Error Handling**: Network errors, API errors, auth errors all handled gracefully
- [ ] **Caching**: Duplicate requests hit cache, not API
- [ ] **JWT Refresh**: Expired access tokens auto-refresh using refresh token

---

## Anti-Patterns to Avoid

- ❌ **Don't hardcode API keys** - Always use environment variables (.env, Keychain, EncryptedSharedPreferences)
- ❌ **Don't ignore memory limits on iOS keyboard** - Monitor memory usage, keep < 40MB
- ❌ **Don't use sync functions in async context** - Use `async/await` (Swift), `suspend` (Kotlin), `CompletableFuture` (Java)
- ❌ **Don't skip input validation** - Validate all user inputs, API requests (use Pydantic/Bean Validation)
- ❌ **Don't ignore getCurrentInputConnection() null** - Always check for null before using
- ❌ **Don't store JWT in UserDefaults/SharedPreferences** - Use Keychain (iOS), EncryptedSharedPreferences (Android)
- ❌ **Don't commit .env, credentials, or tokens** - Add to .gitignore
- ❌ **Don't use raw SQL** - Use JPA, Room, CoreData for type safety
- ❌ **Don't skip HTTPS in production** - Always use TLS 1.3
- ❌ **Don't ignore rate limits** - Implement exponential backoff for API retries
- ❌ **Don't create God classes** - Keep files < 500 lines, follow Single Responsibility Principle
- ❌ **Don't skip tests to "save time"** - Tests save debugging time later
- ❌ **Don't use mock data to pass tests** - Fix actual logic, not test expectations
- ❌ **Don't ignore linter warnings** - Fix all warnings before committing
- ❌ **Don't skip code reviews** - Use QA agent to review all code

---

## Confidence Score: 8/10

### High Confidence Factors:
- **清晰的需求**: spec.md 和 INITIAL.md 提供完整的產品與技術規範
- **明確的架構**: 三層架構(Android, iOS, Backend)定義清楚,職責分明
- **成熟的技術棧**:
  - Spring Boot + JPA + Redis (後端標準棧)
  - Kotlin + Jetpack Compose (Android 現代化)
  - Swift + SwiftUI (iOS 現代化)
- **完整的驗證門檻**: 每個 Phase 都有明確的測試策略與驗證標準
- **豐富的參考文檔**: 官方文檔(Android InputMethodService, iOS UIInputViewController, Spring AI)都有詳細範例
- **Docker 標準化**: 使用 docker-compose 確保環境一致性

### 不確定性因素 (-2分):
- **OpenAI API 穩定性**: GPT-4 API 可能有突發的限流或服務中斷,需要實作 fallback 機制
- **Prompt Engineering 品質**: 8 種語氣風格的 prompt 需要反覆測試與調整才能達到理想效果
- **iOS 鍵盤記憶體限制**: 48MB 限制可能在載入大型模型或圖片時觸發,需要持續監控
- **跨平台一致性**: Android 和 iOS 的鍵盤行為差異可能導致 UX 不一致,需要額外調整

### 建議:
1. **Phase 1 優先**: 先完成後端 API 並通過壓力測試,確保穩定性
2. **Parallel Development**: Android 和 iOS 可以並行開發,但需要統一 API contract
3. **Early Testing**: 每個 Task 完成後立即運行驗證測試,不要累積技術債
4. **Monitoring from Day 1**: 從開發環境就啟用 Prometheus + Grafana,及早發現效能問題
5. **User Feedback Loop**: Beta 測試時收集真實用戶對 8 種語氣的反饋,持續優化 prompts

---

## 總結

這份 PRP 提供了一個**完整的、可執行的**專案實施計劃,涵蓋:
- ✅ 30 個詳細任務,按優先級排序
- ✅ 每個任務都有明確的驗證標準
- ✅ 關鍵功能的偽代碼範例
- ✅ 三層驗證門檻(語法、單元測試、整合測試)
- ✅ 完整的技術棧與工具鏈
- ✅ Docker 部署與 CI/CD 自動化
- ✅ 8/10 信心分數,適合一次性實施

**下一步行動**:
1. 分配任務給各專業 Agent(android-engineer, ios-app-engineer, java-engineer, mysql-analyst-programmer)
2. 啟動 Phase 1: 後端基礎設施與核心 API (Task 1-8)
3. 每日 Stand-up 檢查進度,使用 TodoWrite 工具追蹤任務狀態
4. 每週 Code Review,確保品質標準

**預計時程**: 18 週完成所有三個 Phase,產出可發布的 Android APK, iOS IPA 和 Docker 生產環境。
