# TalkGenius 專案開發指南

## 🎯 專案概述

**TalkGenius** 是一個結合 AI 技術的智能戀愛助手鍵盤應用,涉及多平台開發(Android、iOS)和後端服務(Spring Boot)。本指南為所有參與開發的團隊成員提供統一的開發規範和工作流程。

### 技術棧總覽
- **移動端**: Android (Kotlin + Jetpack Compose) / iOS (Swift + SwiftUI)
- **後端**: Spring Boot 3.x (Java 17+)
- **數據庫**: MySQL 8.0
- **緩存**: Redis 7.x
- **AI 服務**: OpenAI GPT-4 API
- **部署**: Docker + Docker Compose
- **CI/CD**: GitHub Actions

---

## 📋 專案文件結構

```
TalkGenius/
├── README.md                 # 專案說明文檔
├── CLAUDE.md                # 開發規範(本文件)
├── INITIAL.md               # 專案規格與架構文檔
├── spec.md                  # 產品規格書
├── PLANNING.md              # 專案規劃與里程碑
├── TASK.md                  # 任務追蹤清單
│
├── android/                 # Android 應用
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/talkgenius/
│   │   │   │   │   ├── keyboard/         # 自定義鍵盤
│   │   │   │   │   ├── ui/              # UI 組件
│   │   │   │   │   ├── viewmodel/       # ViewModel
│   │   │   │   │   ├── repository/      # 數據倉庫
│   │   │   │   │   ├── api/             # API 客戶端
│   │   │   │   │   └── utils/           # 工具類
│   │   │   │   └── res/                 # 資源文件
│   │   │   └── test/                    # 單元測試
│   │   ├── build.gradle.kts
│   │   └── proguard-rules.pro
│   ├── gradle/
│   └── build.gradle.kts
│
├── ios/                     # iOS 應用
│   ├── TalkGenius/
│   │   ├── App/
│   │   ├── Keyboard/                    # 鍵盤擴展
│   │   ├── Views/
│   │   ├── ViewModels/
│   │   ├── Services/
│   │   ├── Models/
│   │   └── Utils/
│   ├── TalkGeniusTests/
│   └── TalkGenius.xcodeproj
│
├── backend/                 # Spring Boot 後端
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/talkgenius/
│   │   │   │   ├── controller/          # REST 控制器
│   │   │   │   ├── service/             # 業務邏輯
│   │   │   │   ├── repository/          # 數據訪問
│   │   │   │   ├── model/               # 數據模型
│   │   │   │   ├── dto/                 # 數據傳輸對象
│   │   │   │   ├── config/              # 配置類
│   │   │   │   ├── security/            # 安全配置
│   │   │   │   ├── ai/                  # AI 集成
│   │   │   │   ├── exception/           # 異常處理
│   │   │   │   └── utils/               # 工具類
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       └── application-prod.yml
│   │   └── test/                        # 測試
│   ├── pom.xml
│   ├── Dockerfile
│   └── README.md
│
├── database/                # 數據庫相關
│   ├── init.sql            # 初始化 SQL 腳本
│   ├── migrations/         # 數據庫遷移
│   └── docs/              # 數據庫文檔
│
├── docker/                 # Docker 配置
│   ├── docker-compose.yml
│   ├── docker-compose.dev.yml
│   ├── .env.example
│   └── nginx/
│       └── nginx.conf
│
├── docs/                   # 文檔
│   ├── api/               # API 文檔
│   ├── architecture/      # 架構圖
│   ├── ui-ux/            # UI/UX 設計
│   └── deployment/       # 部署指南
│
├── tests/                  # 集成測試
│   ├── e2e/
│   └── performance/
│
└── .github/               # GitHub 配置
    ├── workflows/         # GitHub Actions
    │   ├── android-ci.yml
    │   ├── ios-ci.yml
    │   ├── backend-ci.yml
    │   └── docker-build.yml
    └── PULL_REQUEST_TEMPLATE.md
```

---

## 🔄 專案感知與上下文

### 必讀文件
開始任何開發工作前,**必須**閱讀以下文件:

1. **INITIAL.md** - 了解專案的完整技術架構、功能模塊、API 設計、數據庫 Schema
2. **PLANNING.md** - 了解當前開發階段、里程碑、優先級
3. **TASK.md** - 查看當前任務清單和已完成的工作

### 任務追蹤規範

**開始新任務前:**
1. 檢查 `TASK.md` 確認任務是否已列出
2. 如果任務未列出,添加任務描述和今天的日期
3. 標記任務狀態為 `進行中`

**完成任務後:**
1. 立即在 `TASK.md` 中標記任務為 `已完成`
2. 添加完成日期
3. 如果在開發過程中發現新的子任務,添加到 "開發中發現的任務" 區域

**範例:**
```markdown
## 任務清單

### Phase 1: MVP 開發

- [x] 設置 Android 專案基礎架構 (2025-11-10)
- [x] 實現 InputMethodService 基礎鍵盤 (2025-11-12)
- [ ] 集成 OpenAI API 客戶端 (進行中)
- [ ] 實現 AI 回覆生成端點

### 開發中發現的任務

- [ ] 添加 Redis 緩存層以優化 AI API 調用
- [ ] 實現速率限制中間件
```

---

## 🧱 代碼結構與模組化

### 通用規範

**文件大小限制:**
- ❌ **絕對不要**創建超過 **500 行**的代碼文件
- ✅ 接近 500 行時,重構並拆分為多個模組或輔助文件

**模組化組織:**
- 按功能或職責將代碼組織成清晰分離的模組
- 使用清晰、一致的 import(包內優先使用相對 import)

### Android 開發規範

**架構模式:**
- 使用 **MVVM (Model-View-ViewModel)** 架構
- 使用 **Repository Pattern** 進行數據訪問
- 使用 **Dependency Injection** (Hilt/Koin)

**Kotlin 代碼規範:**
```kotlin
// ✅ 良好的模組化範例
// keyboard/TalkGeniusKeyboard.kt (主要鍵盤服務)
class TalkGeniusKeyboard : InputMethodService() {
    // 核心鍵盤邏輯,不超過 300 行
}

// keyboard/KeyboardView.kt (UI 渲染)
@Composable
fun KeyboardView(viewModel: KeyboardViewModel) {
    // UI 組件
}

// keyboard/ClipboardMonitor.kt (剪貼簿功能)
class ClipboardMonitor(context: Context) {
    // 剪貼簿監控邏輯
}

// keyboard/AISuggestionHandler.kt (AI 建議處理)
class AISuggestionHandler(private val apiService: AIService) {
    // AI 相關邏輯
}
```

**命名慣例:**
- **文件**: PascalCase (例: `UserProfileViewModel.kt`)
- **類**: PascalCase (例: `class UserRepository`)
- **函數/變量**: camelCase (例: `fun generateReply()`, `val userName`)
- **常量**: UPPER_SNAKE_CASE (例: `const val MAX_RETRY_COUNT = 3`)

**Jetpack Compose 最佳實踐:**
```kotlin
// ✅ 推薦:可組合函數應該是無狀態的
@Composable
fun ReplyCard(
    reply: ReplyItem,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.clickable { onSelect(reply.text) }) {
        Text(text = reply.text)
    }
}

// ❌ 避免:在可組合函數內部處理業務邏輯
@Composable
fun ReplyCard(reply: ReplyItem) {
    val viewModel: ReplyViewModel = viewModel()
    // 業務邏輯應該在 ViewModel 中
}
```

### iOS 開發規範

**架構模式:**
- 使用 **MVVM** 架構
- 使用 **Combine** 進行響應式編程
- 使用 **Protocol-Oriented Programming**

**Swift 代碼規範:**
```swift
// ✅ 良好的模組化範例
// Keyboard/TalkGeniusKeyboard.swift
class TalkGeniusKeyboard: UIInputViewController {
    // 主要鍵盤邏輯
}

// Keyboard/Views/KeyboardView.swift
struct KeyboardView: View {
    @ObservedObject var viewModel: KeyboardViewModel

    var body: some View {
        // UI 組件
    }
}

// Keyboard/Services/ClipboardService.swift
class ClipboardService {
    // 剪貼簿功能
}

// Keyboard/Services/AIService.swift
class AIService {
    // AI API 調用
}
```

**命名慣例:**
- **文件**: PascalCase (例: `UserProfileViewModel.swift`)
- **類/結構體**: PascalCase (例: `class UserRepository`)
- **函數/變量**: camelCase (例: `func generateReply()`, `let userName`)
- **常量**: camelCase (例: `let maxRetryCount = 3`)

**SwiftUI 最佳實踐:**
```swift
// ✅ 推薦:視圖應該聲明式且可重用
struct ReplyCard: View {
    let reply: ReplyItem
    let onSelect: (String) -> Void

    var body: some View {
        Card {
            Text(reply.text)
        }
        .onTapGesture {
            onSelect(reply.text)
        }
    }
}

// ✅ 使用 @State, @Binding, @ObservedObject 正確管理狀態
struct ReplyListView: View {
    @StateObject private var viewModel = ReplyViewModel()

    var body: some View {
        List(viewModel.replies) { reply in
            ReplyCard(reply: reply, onSelect: viewModel.selectReply)
        }
    }
}
```

### Java Spring Boot 開發規範

**架構模式:**
- 使用 **三層架構**: Controller → Service → Repository
- 使用 **DTO Pattern** 進行數據傳輸
- 使用 **Dependency Injection** (@Autowired, Constructor Injection 優先)

**代碼組織:**
```java
// ✅ 良好的分層範例

// controller/AIController.java (不超過 200 行)
@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private final ReplyGenerationService replyService;

    @Autowired
    public AIController(ReplyGenerationService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/generate-reply")
    public ResponseEntity<ReplyResponse> generateReply(@RequestBody @Valid ReplyRequest request) {
        return ResponseEntity.ok(replyService.generateReply(request));
    }
}

// service/ReplyGenerationService.java (核心業務邏輯)
@Service
public class ReplyGenerationService {

    private final OpenAIClient openAIClient;
    private final ContextAnalyzer contextAnalyzer;
    private final RedisTemplate<String, Object> redisTemplate;

    public ReplyResponse generateReply(ReplyRequest request) {
        // 業務邏輯
        // 1. 分析上下文
        // 2. 構建 Prompt
        // 3. 調用 AI API
        // 4. 解析響應
        // 5. 緩存結果
    }
}

// service/ai/OpenAIClient.java (AI API 客戶端,單一職責)
@Component
public class OpenAIClient {

    public CompletableFuture<AIResponse> callAPI(String prompt) {
        // AI API 調用邏輯
    }
}

// service/ai/PromptBuilder.java (Prompt 構建器,單一職責)
@Component
public class PromptBuilder {

    public String buildReplyPrompt(MessageContext context, String tone) {
        // Prompt 構建邏輯
    }
}
```

**命名慣例:**
- **包**: lowercase (例: `com.talkgenius.service`)
- **類**: PascalCase (例: `class UserService`)
- **接口**: PascalCase, 通常以 I 開頭或不帶前綴 (例: `interface UserRepository`)
- **方法/變量**: camelCase (例: `generateReply()`, `userName`)
- **常量**: UPPER_SNAKE_CASE (例: `public static final int MAX_RETRY = 3`)

**Spring Boot 最佳實踐:**
```java
// ✅ 使用構造器注入(推薦)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}

// ❌ 避免字段注入
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // 不推薦
}

// ✅ 使用 @Transactional 管理事務
@Service
@Transactional
public class SubscriptionService {

    public void createSubscription(User user, Plan plan) {
        // 事務內的操作
    }
}

// ✅ 使用 DTO 而不是直接暴露 Entity
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
```

**異常處理:**
```java
// ✅ 全局異常處理器
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        // 處理驗證錯誤
    }
}

// ✅ 自定義異常
public class AIServiceException extends RuntimeException {
    public AIServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### MySQL 數據庫規範

**命名慣例:**
- **表名**: snake_case, 複數形式 (例: `users`, `conversation_history`)
- **列名**: snake_case (例: `user_id`, `created_at`)
- **索引**: `idx_表名_列名` (例: `idx_users_email`)
- **外鍵**: `fk_表名_引用表名` (例: `fk_conversations_users`)

**最佳實踐:**
```sql
-- ✅ 良好的表設計
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY COMMENT '用戶唯一標識 (UUID)',
    email VARCHAR(255) UNIQUE NOT NULL COMMENT '用戶郵箱',
    password_hash VARCHAR(255) NOT NULL COMMENT 'bcrypt 哈希密碼',
    username VARCHAR(100) COMMENT '用戶名',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',

    INDEX idx_email (email),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用戶表';

-- ✅ 使用外鍵約束
CREATE TABLE conversation_history (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    platform VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_conversations_users
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    INDEX idx_user_date (user_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ✅ 使用索引優化查詢
-- 對經常查詢的列添加索引
CREATE INDEX idx_users_premium ON users(is_premium, premium_expires_at);

-- ✅ 使用 EXPLAIN 分析查詢
EXPLAIN SELECT * FROM users WHERE email = 'user@example.com';
```

**數據遷移:**
```sql
-- database/migrations/V1__initial_schema.sql
CREATE TABLE users (...);
CREATE TABLE user_preferences (...);

-- database/migrations/V2__add_subscription_tables.sql
CREATE TABLE subscriptions (...);
```

---

## 🧪 測試與可靠性

### 測試覆蓋率要求
- **單元測試**: 最低 80% 代碼覆蓋率
- **集成測試**: 覆蓋所有 API 端點
- **E2E 測試**: 覆蓋核心用戶流程

### Android 測試

**單元測試 (JUnit + Mockito):**
```kotlin
// app/src/test/java/com/talkgenius/viewmodel/KeyboardViewModelTest.kt
class KeyboardViewModelTest {

    @Mock
    private lateinit var aiService: AIService

    @Mock
    private lateinit var clipboardMonitor: ClipboardMonitor

    private lateinit var viewModel: KeyboardViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = KeyboardViewModel(aiService, clipboardMonitor)
    }

    @Test
    fun `generateReply should return suggestions on success`() = runTest {
        // Given
        val message = "How was your day?"
        val expectedReplies = listOf("Great!", "Not bad")
        `when`(aiService.generateReply(message)).thenReturn(expectedReplies)

        // When
        viewModel.generateReply(message)

        // Then
        assertEquals(expectedReplies, viewModel.suggestions.value)
    }

    @Test
    fun `generateReply should handle error gracefully`() = runTest {
        // Given
        `when`(aiService.generateReply(any())).thenThrow(RuntimeException("API Error"))

        // When
        viewModel.generateReply("test")

        // Then
        assertTrue(viewModel.error.value?.isNotEmpty() == true)
    }
}
```

**UI 測試 (Espresso + Compose Test):**
```kotlin
// app/src/androidTest/java/com/talkgenius/ui/KeyboardUITest.kt
@RunWith(AndroidJUnit4::class)
class KeyboardUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aiButton_whenClicked_showsLoading() {
        composeTestRule.setContent {
            KeyboardView(viewModel = KeyboardViewModel())
        }

        composeTestRule.onNodeWithContentDescription("AI Button").performClick()
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }
}
```

### iOS 測試

**單元測試 (XCTest):**
```swift
// TalkGeniusTests/KeyboardViewModelTests.swift
import XCTest
@testable import TalkGenius

class KeyboardViewModelTests: XCTestCase {

    var viewModel: KeyboardViewModel!
    var mockAIService: MockAIService!

    override func setUp() {
        super.setUp()
        mockAIService = MockAIService()
        viewModel = KeyboardViewModel(aiService: mockAIService)
    }

    func testGenerateReply_Success() async throws {
        // Given
        let message = "How was your day?"
        let expectedReplies = ["Great!", "Not bad"]
        mockAIService.mockReplies = expectedReplies

        // When
        await viewModel.generateReply(for: message)

        // Then
        XCTAssertEqual(viewModel.suggestions, expectedReplies)
    }

    func testGenerateReply_HandlesError() async {
        // Given
        mockAIService.shouldFail = true

        // When
        await viewModel.generateReply(for: "test")

        // Then
        XCTAssertNotNil(viewModel.errorMessage)
    }
}
```

### Java Spring Boot 測試

**單元測試 (JUnit 5 + Mockito):**
```java
// src/test/java/com/talkgenius/service/ReplyGenerationServiceTest.java
@ExtendWith(MockitoExtension.class)
class ReplyGenerationServiceTest {

    @Mock
    private OpenAIClient openAIClient;

    @Mock
    private ContextAnalyzer contextAnalyzer;

    @InjectMocks
    private ReplyGenerationService replyService;

    @Test
    void generateReply_shouldReturnSuggestions_whenAPISucceeds() {
        // Given
        ReplyRequest request = new ReplyRequest("How was your day?", "humorous");
        AIResponse aiResponse = new AIResponse(List.of("Great!", "Not bad"));
        when(openAIClient.callAPI(anyString())).thenReturn(CompletableFuture.completedFuture(aiResponse));

        // When
        ReplyResponse response = replyService.generateReply(request);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getSuggestions().size());
        verify(openAIClient, times(1)).callAPI(anyString());
    }

    @Test
    void generateReply_shouldThrowException_whenAPIFails() {
        // Given
        when(openAIClient.callAPI(anyString())).thenThrow(new AIServiceException("API Error"));

        // When & Then
        assertThrows(AIServiceException.class, () -> {
            replyService.generateReply(new ReplyRequest("test", "humorous"));
        });
    }
}
```

**集成測試 (Spring Boot Test + TestContainers):**
```java
// src/test/java/com/talkgenius/controller/AIControllerIntegrationTest.java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class AIControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void generateReply_shouldReturn200_whenRequestIsValid() {
        // Given
        ReplyRequest request = new ReplyRequest("How are you?", "friendly");

        // When
        ResponseEntity<ReplyResponse> response = restTemplate.postForEntity(
            "/api/v1/ai/generate-reply",
            request,
            ReplyResponse.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuggestions().size() > 0);
    }
}
```

**API 測試 (RestAssured):**
```java
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class APITest {

    @Test
    void testGenerateReplyEndpoint() {
        given()
            .contentType(ContentType.JSON)
            .body(new ReplyRequest("Hello", "friendly"))
        .when()
            .post("/api/v1/ai/generate-reply")
        .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("suggestions", hasSize(greaterThan(0)));
    }
}
```

### 測試組織結構
```
tests/
├── unit/              # 單元測試
│   ├── android/
│   ├── ios/
│   └── backend/
├── integration/       # 集成測試
│   └── backend/
└── e2e/              # 端到端測試
    ├── android/
    └── ios/
```

---

## ✅ 任務完成流程

### 1. 任務開始前
- [ ] 閱讀 `INITIAL.md` 和 `PLANNING.md`
- [ ] 檢查 `TASK.md` 確認任務
- [ ] 如果任務未列出,添加到 `TASK.md`
- [ ] 標記任務狀態為 `進行中`

### 2. 開發過程中
- [ ] 遵循相應的代碼規範(Android/iOS/Java)
- [ ] 編寫單元測試(至少 3 個:正常用例、邊緣用例、錯誤用例)
- [ ] 添加必要的代碼註釋和文檔字符串
- [ ] 確保代碼文件不超過 500 行

### 3. 任務完成後
- [ ] 運行所有測試確保通過
- [ ] 檢查代碼覆蓋率(目標 > 80%)
- [ ] 更新相關文檔(如 README.md)
- [ ] 在 `TASK.md` 中標記任務為 `已完成`
- [ ] 添加完成日期

### 4. 提交代碼
- [ ] 確保代碼符合風格規範(運行 linter)
- [ ] 編寫清晰的提交訊息(遵循 Conventional Commits)
- [ ] 創建 Pull Request
- [ ] 請求代碼審查

---

## 📎 風格與慣例

### Git 提交訊息規範

使用 **Conventional Commits** 格式:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**類型(type):**
- `feat`: 新功能
- `fix`: Bug 修復
- `docs`: 文檔更新
- `style`: 代碼格式(不影響功能)
- `refactor`: 重構(既不是新功能也不是修復)
- `test`: 添加或修改測試
- `chore`: 構建過程或輔助工具變更
- `perf`: 性能優化

**範圍(scope):** android, ios, backend, database, docs, ci

**範例:**
```
feat(android): implement AI button in custom keyboard

- Added AI sparkle button to keyboard layout
- Integrated clipboard monitoring service
- Connected to reply generation API

Closes #42
```

### 代碼註釋規範

**Java (JavaDoc):**
```java
/**
 * 生成基於上下文的 AI 回覆建議
 *
 * @param request 包含用戶訊息和語氣偏好的請求對象
 * @return 包含 3-5 個回覆建議的響應對象
 * @throws AIServiceException 當 AI API 調用失敗時
 */
public ReplyResponse generateReply(ReplyRequest request) {
    // 實現邏輯
}
```

**Kotlin (KDoc):**
```kotlin
/**
 * 監控剪貼簿並檢測複製的訊息
 *
 * @param context Android 上下文
 * @param onMessageDetected 檢測到訊息時的回調函數
 */
class ClipboardMonitor(
    private val context: Context,
    private val onMessageDetected: (String) -> Unit
) {
    // 實現邏輯
}
```

**Swift (Documentation Comments):**
```swift
/// 生成基於上下文的 AI 回覆建議
///
/// - Parameters:
///   - message: 收到的訊息內容
///   - tone: 期望的語氣風格
/// - Returns: 包含多個回覆建議的陣列
/// - Throws: `AIServiceError` 當 API 調用失敗時
func generateReply(for message: String, tone: ToneStyle) async throws -> [String] {
    // 實現邏輯
}
```

### 代碼格式化工具

**Android:**
- 使用 **Ktlint** 進行 Kotlin 代碼檢查
- 配置: `.editorconfig`

**iOS:**
- 使用 **SwiftLint** 進行 Swift 代碼檢查
- 配置: `.swiftlint.yml`

**Java:**
- 使用 **Checkstyle** 或 **Google Java Format**
- 配置: `checkstyle.xml`

### 環境變量管理

**使用 `.env` 文件管理敏感配置:**

```bash
# .env.example (提交到版本控制)
MYSQL_ROOT_PASSWORD=your_password_here
MYSQL_USER=talkgenius
MYSQL_PASSWORD=your_password_here
OPENAI_API_KEY=sk-your-key-here
JWT_SECRET=your-secret-here
REDIS_HOST=localhost
REDIS_PORT=6379
```

```bash
# .env (不提交到版本控制,加入 .gitignore)
MYSQL_ROOT_PASSWORD=SecurePassword123!
MYSQL_USER=talkgenius
MYSQL_PASSWORD=DBPassword456!
OPENAI_API_KEY=sk-real-api-key
JWT_SECRET=super-secret-jwt-key-2025
REDIS_HOST=localhost
REDIS_PORT=6379
```

---

## 📚 文檔與可解釋性

### 更新 README.md 的時機
- 添加新功能時
- 依賴項變更時
- 設置步驟修改時
- 新增環境變量時

### 註釋非顯而易見的代碼
```java
// ✅ 良好的註釋 - 解釋"為什麼"
// Reason: 使用 CompletableFuture 以避免阻塞主線程,提升響應性能
CompletableFuture<AIResponse> future = openAIClient.callAPIAsync(prompt);

// ✅ 解釋複雜的業務邏輯
// Reason: 免費用戶每日限制 10 次,Premium 用戶無限制
if (!user.isPremium() && user.getTodayUsageCount() >= 10) {
    throw new QuotaExceededException("Daily free quota exceeded");
}

// ❌ 避免無意義的註釋
// Get user by ID
User user = userRepository.findById(id); // 這是顯而易見的,無需註釋
```

### API 文檔

使用 **Swagger/OpenAPI** 自動生成 API 文檔:

```java
@RestController
@RequestMapping("/api/v1/ai")
@Tag(name = "AI Services", description = "AI 回覆生成和情感顧問相關 API")
public class AIController {

    @PostMapping("/generate-reply")
    @Operation(
        summary = "生成 AI 回覆建議",
        description = "基於用戶收到的訊息和選擇的語氣,生成 3-5 個回覆建議"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "成功生成回覆"),
        @ApiResponse(responseCode = "400", description = "請求參數無效"),
        @ApiResponse(responseCode = "429", description = "超出使用配額"),
        @ApiResponse(responseCode = "500", description = "AI 服務錯誤")
    })
    public ResponseEntity<ReplyResponse> generateReply(
        @RequestBody @Valid ReplyRequest request
    ) {
        return ResponseEntity.ok(replyService.generateReply(request));
    }
}
```

訪問 Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🧠 AI 行為規則

### 不要假設缺失的上下文
- ❌ 不要假設文件路徑或模組名稱存在
- ✅ 在引用代碼或測試之前,先確認文件存在
- ✅ 如果不確定,詢問用戶

### 不要幻想庫或函數
- ❌ 不要使用未驗證的 Python/Java/Kotlin/Swift 包
- ✅ 只使用已知的、經過驗證的庫
- ✅ 查閱官方文檔確認 API

### 確認文件路徑和模組名稱
```kotlin
// ✅ 確認後使用
import com.talkgenius.keyboard.ClipboardMonitor  // 確認此文件存在

// ❌ 不確認就使用
import com.talkgenius.utils.SomeRandomUtil  // 可能不存在
```

### 不要刪除或覆蓋現有代碼
- ❌ 除非明確指示或在 `TASK.md` 中列出,否則不要刪除代碼
- ✅ 重構時保持向後兼容
- ✅ 添加新功能時保留舊功能

---

## 🔒 安全最佳實踐

### API 密鑰管理
- ❌ 絕對不要將 API 密鑰硬編碼在代碼中
- ✅ 使用環境變量或密鑰管理服務(AWS Secrets Manager, Google Secret Manager)

```java
// ❌ 錯誤示範
String apiKey = "sk-1234567890abcdef";  // 不要這樣做!

// ✅ 正確做法
@Value("${openai.api.key}")
private String apiKey;
```

### 密碼處理
```java
// ✅ 使用 bcrypt 哈希密碼
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setPasswordHash(hashedPassword);
        return userRepository.save(user);
    }
}
```

### SQL 注入防護
```java
// ✅ 使用參數化查詢(JPA/Hibernate 自動處理)
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);  // 安全
}

// ❌ 避免原生 SQL 字符串拼接
// String sql = "SELECT * FROM users WHERE email = '" + email + "'";  // 危險!
```

### XSS 防護
```java
// ✅ Spring Security 默認啟用 XSS 保護
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers()
            .contentSecurityPolicy("script-src 'self'");
        return http.build();
    }
}
```

### CORS 配置
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://app.talkgenius.com")  // 生產環境使用具體域名
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

---

## 🐳 Docker 與部署

### 本地開發環境

```bash
# 啟動所有服務(MySQL, Redis, Backend)
docker-compose up -d

# 查看日誌
docker-compose logs -f backend

# 停止所有服務
docker-compose down

# 重建並啟動
docker-compose up -d --build
```

### 環境變量配置

```bash
# 複製環境變量範例
cp .env.example .env

# 編輯 .env 文件,填入真實的配置
nano .env
```

### 數據庫初始化

```bash
# 數據庫會在首次啟動時自動執行 init.sql
# 或手動執行
docker exec -i talkgenius-mysql mysql -u root -p talkgenius < database/init.sql
```

### 健康檢查

```bash
# 檢查後端健康狀態
curl http://localhost:8080/actuator/health

# 檢查 MySQL
docker exec talkgenius-mysql mysqladmin ping -h localhost

# 檢查 Redis
docker exec talkgenius-redis redis-cli ping
```

---

## 🚀 CI/CD 流程

### GitHub Actions Workflow

**後端 CI:**
```yaml
# .github/workflows/backend-ci.yml
name: Backend CI

on:
  push:
    branches: [ main, develop ]
    paths:
      - 'backend/**'
  pull_request:
    branches: [ main, develop ]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Build with Maven
      run: mvn -B package --file backend/pom.xml

    - name: Run tests
      run: mvn test --file backend/pom.xml

    - name: Generate coverage report
      run: mvn jacoco:report --file backend/pom.xml

    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
```

**Android CI:**
```yaml
# .github/workflows/android-ci.yml
name: Android CI

on:
  push:
    branches: [ main, develop ]
    paths:
      - 'android/**'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'

    - name: Run Ktlint
      run: ./gradlew ktlintCheck
      working-directory: android

    - name: Run tests
      run: ./gradlew test
      working-directory: android

    - name: Build APK
      run: ./gradlew assembleDebug
      working-directory: android
```

---

## 📊 性能優化指南

### 數據庫優化
```sql
-- ✅ 使用索引
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_conversations_user_date ON conversation_history(user_id, created_at);

-- ✅ 使用 EXPLAIN 分析查詢
EXPLAIN SELECT * FROM users WHERE email = 'test@example.com';

-- ✅ 避免 SELECT *
SELECT id, email, username FROM users WHERE id = ?;  -- 好
SELECT * FROM users WHERE id = ?;  -- 避免
```

### Redis 緩存策略
```java
@Service
public class ReplyGenerationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ReplyResponse generateReply(ReplyRequest request) {
        String cacheKey = "reply:" + request.getMessage().hashCode();

        // 先檢查緩存
        ReplyResponse cached = (ReplyResponse) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 調用 AI API
        ReplyResponse response = callAIAPI(request);

        // 緩存結果(5 分鐘)
        redisTemplate.opsForValue().set(cacheKey, response, 5, TimeUnit.MINUTES);

        return response;
    }
}
```

### API 響應時間優化
```java
// ✅ 使用異步處理
@Async
public CompletableFuture<ReplyResponse> generateReplyAsync(ReplyRequest request) {
    return CompletableFuture.supplyAsync(() -> generateReply(request));
}

// ✅ 使用連接池
@Bean
public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setMaximumPoolSize(20);
    config.setMinimumIdle(5);
    config.setConnectionTimeout(30000);
    return new HikariDataSource(config);
}
```

---

## 🎯 代碼審查檢查清單

### 提交 Pull Request 前
- [ ] 代碼符合風格規範(運行 linter)
- [ ] 所有測試通過
- [ ] 代碼覆蓋率 > 80%
- [ ] 無安全漏洞(SQL 注入、XSS 等)
- [ ] 敏感數據已移除(API 密鑰、密碼等)
- [ ] 文檔已更新
- [ ] 提交訊息清晰且遵循規範

### 代碼審查者檢查
- [ ] 代碼邏輯正確
- [ ] 錯誤處理完善
- [ ] 性能考慮(無明顯瓶頸)
- [ ] 安全性檢查
- [ ] 可讀性和可維護性
- [ ] 測試充分且有意義

---

## 📞 團隊溝通

### 專案角色分工

| 角色 | 職責 | 主要工具 |
|------|------|---------|
| **Project Manager** | 專案規劃、進度管理、需求分析 | PLANNING.md, TASK.md |
| **System Analyst** | 系統架構設計、技術選型 | INITIAL.md, 架構圖 |
| **MySQL Analyst** | 數據庫設計、查詢優化 | MySQL Workbench, Schema 文檔 |
| **Mobile UI/UX Advisor** | 界面設計、用戶體驗 | Figma, 設計規範 |
| **Android Engineer** | Android 應用開發 | Android Studio, Kotlin |
| **iOS Engineer** | iOS 應用開發 | Xcode, Swift |
| **Java Engineer** | 後端開發、API 實現 | IntelliJ IDEA, Spring Boot |
| **QA Engineer** | 測試計劃、自動化測試 | JUnit, Espresso, XCTest |
| **Cloud Engineer** | Docker 部署、CI/CD | Docker, GitHub Actions |

### 日常溝通規範
- **Daily Standup**: 每日同步進度和阻礙
- **Code Review**: 所有代碼必須經過至少一人審查
- **Documentation**: 更新文檔與代碼同步進行
- **Issue Tracking**: 使用 GitHub Issues 追蹤 bug 和功能請求

---

## 🔧 常見問題與解決方案

### Docker 相關

**問題: MySQL 容器無法啟動**
```bash
# 檢查日誌
docker logs talkgenius-mysql

# 清理並重新啟動
docker-compose down -v
docker-compose up -d
```

**問題: 後端無法連接到 MySQL**
```bash
# 檢查網絡
docker network ls
docker network inspect talkgenius-network

# 確保環境變量正確
docker exec talkgenius-backend env | grep SPRING_DATASOURCE
```

### 測試相關

**問題: 測試覆蓋率不足**
```bash
# 生成覆蓋率報告
mvn jacoco:report
open target/site/jacoco/index.html
```

**問題: 集成測試失敗**
```bash
# 確保 TestContainers 可以訪問 Docker
docker ps
docker info
```

---

## 📝 總結

### 核心原則
1. **模組化優先**: 文件不超過 500 行,職責單一
2. **測試驅動**: 代碼覆蓋率 > 80%,先寫測試
3. **文檔同步**: 代碼和文檔同步更新
4. **安全第一**: 永不硬編碼密鑰,防範 SQL 注入和 XSS
5. **團隊協作**: 清晰的任務追蹤,規範的代碼審查

### 開發工作流程
```
1. 閱讀 INITIAL.md 和 PLANNING.md
   ↓
2. 檢查/更新 TASK.md
   ↓
3. 創建功能分支
   ↓
4. 編寫代碼 + 測試
   ↓
5. 運行 linter 和測試
   ↓
6. 更新文檔
   ↓
7. 提交 PR + 請求審查
   ↓
8. 合併到主分支
   ↓
9. 標記任務為完成
```

### 重要提醒
- ⚠️ **絕對不要**提交敏感信息(API 密鑰、密碼)到版本控制
- ⚠️ **絕對不要**創建超過 500 行的文件
- ⚠️ **絕對不要**跳過測試
- ⚠️ **絕對不要**直接推送到 main 分支

---

**文檔版本:** 1.0
**最後更新:** 2025-11-14
**狀態:** 生效中

---

## 附錄

### 有用的命令速查

**Docker:**
```bash
# 啟動所有服務
docker-compose up -d

# 查看日誌
docker-compose logs -f [service_name]

# 重啟服務
docker-compose restart [service_name]

# 停止並刪除所有容器
docker-compose down

# 進入容器
docker exec -it [container_name] bash
```

**Maven:**
```bash
# 編譯
mvn clean compile

# 運行測試
mvn test

# 打包
mvn clean package

# 跳過測試打包
mvn clean package -DskipTests
```

**Gradle (Android):**
```bash
# 編譯
./gradlew build

# 運行測試
./gradlew test

# 生成 APK
./gradlew assembleDebug

# 運行 Ktlint
./gradlew ktlintCheck
```

**Git:**
```bash
# 創建功能分支
git checkout -b feature/ai-keyboard-integration

# 提交變更
git add .
git commit -m "feat(android): implement AI keyboard"

# 推送到遠程
git push origin feature/ai-keyboard-integration

# 更新本地分支
git pull origin develop
```

### 相關資源連結

- [Spring Boot 文檔](https://spring.io/projects/spring-boot)
- [Kotlin 文檔](https://kotlinlang.org/docs/)
- [Swift 文檔](https://swift.org/documentation/)
- [Android Developers](https://developer.android.com/)
- [Apple Developer](https://developer.apple.com/)
- [Docker 文檔](https://docs.docker.com/)
- [MySQL 文檔](https://dev.mysql.com/doc/)
- [Redis 文檔](https://redis.io/documentation)
