# 🎮 TalkGenius - AI Dating Assistant Keyboard App

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-blue.svg)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.6-blue.svg)](https://www.typescriptlang.org/)
[![OpenAI](https://img.shields.io/badge/OpenAI-GPT--4o--mini-412991.svg)](https://openai.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> 一個完整的全端 AI 約會助手平台，提供 8 種語氣風格的智能回覆生成，支持 Android 和 iOS 鍵盤整合。

## ✨ 主要特色

### 🤖 AI 智能回覆
- **8 種語氣風格**: Humorous, High_EQ, Gentle, Cute, Romantic, Professional, Direct, Flirty
- **真實 OpenAI 整合**: 使用 GPT-4o-mini 模型生成自然對話
- **即時生成**: 毫秒級響應時間
- **上下文感知**: 支持對話歷史和情境分析

### 🏗️ 技術架構
- **後端**: Spring Boot 3.2.1 + Spring AI + MySQL + Redis
- **前端**: React 19 + TypeScript + Vite + Tailwind CSS
- **移動端**: Android (Jetpack Compose) + iOS (SwiftUI) 鍵盤 App
- **容器化**: Docker Compose 完整編排

### 🔒 安全性
- JWT 認證系統
- 用戶配額管理（免費/Premium）
- OpenAI API Key 安全管理
- SQL 注入防護

## 🚀 快速開始

### 前置需求
- Docker & Docker Compose
- Node.js 22.12.0 LTS
- JDK 21
- Git

### 安裝步驟

1. **Clone 儲存庫**
```bash
git clone https://github.com/yanchen184/TalkGenius.git
cd TalkGenius
```

2. **設定環境變數**
```bash
cd docker
cp .env.example .env
# 編輯 .env 並填入你的 OpenAI API Key
```

3. **啟動服務**
```bash
docker-compose up -d
```

4. **啟動前端 UI**
```bash
cd ../api-test-ui
npm install
npm run dev
```

5. **訪問應用**
- Backend API: http://localhost:8080
- Frontend UI: http://localhost:3001
- API Documentation: http://localhost:8080/swagger-ui.html

## 📊 系統架構

```
┌─────────────────────────────────────────────────────────┐
│                     Mobile Apps                         │
│  ┌──────────────┐              ┌──────────────┐        │
│  │   Android    │              │     iOS      │        │
│  │   Keyboard   │              │   Keyboard   │        │
│  └──────┬───────┘              └──────┬───────┘        │
│         │                              │                │
└─────────┼──────────────────────────────┼────────────────┘
          │                              │
          └──────────────┬───────────────┘
                         │
          ┌──────────────▼───────────────┐
          │      Spring Boot Backend     │
          │    (OpenAI + Spring AI)      │
          └──────┬────────────────┬──────┘
                 │                │
     ┌───────────▼──┐      ┌─────▼─────┐
     │   MySQL 8.0  │      │  Redis 7  │
     └──────────────┘      └───────────┘
```

## 🎯 支援的語氣風格

| 語氣 | 描述 | 適用場景 |
|-----|------|---------|
| 😄 Humorous | 幽默風趣 | 輕鬆聊天 |
| 🧠 High_EQ | 高情商 | 深入交流 |
| 💕 Gentle | 溫柔體貼 | 關心安慰 |
| 🥰 Cute | 可愛俏皮 | 甜蜜互動 |
| 💖 Romantic | 浪漫深情 | 表達愛意 |
| 👔 Professional | 專業禮貌 | 正式溝通 |
| 💬 Direct | 直接坦率 | 明確表達 |
| 💋 Flirty | 魅力撩人 | 調情互動 |

## 🧪 API 測試

### 使用前端 UI (推薦)
1. 訪問 http://localhost:3001
2. 在 **Auth** 頁籤註冊/登入
3. 在 **AI Test** 頁籤測試 AI 回覆生成

### 使用 cURL
```bash
# 註冊
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "displayName": "Test User"
  }'

# 登入
TOKEN=$(curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }' | jq -r '.token')

# 生成 AI 回覆
curl -X POST http://localhost:8080/api/v1/ai/generate-reply \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "receivedMessage": "Hey, how was your day?",
    "toneStyle": "Humorous",
    "matchName": "Alice"
  }'
```

## 📁 專案結構

```
TalkGenius/
├── backend/                 # Spring Boot 後端
│   ├── src/main/java/
│   │   └── com/talkgenius/
│   │       ├── controller/  # REST API 控制器
│   │       ├── service/     # 業務邏輯
│   │       ├── model/       # JPA 實體
│   │       └── security/    # JWT 認證
│   └── pom.xml
├── api-test-ui/            # React 測試 UI
│   ├── src/
│   │   ├── components/     # React 組件
│   │   └── App.tsx
│   └── package.json
├── android/                # Android 鍵盤 App
│   └── app/src/main/
├── ios/                    # iOS 鍵盤 App
│   └── TalkGenius/
└── docker/                 # Docker 配置
    ├── docker-compose.yml
    └── mysql/init.sql
```

## 🔧 配置說明

### 環境變數 (.env)
```bash
# OpenAI API
OPENAI_API_KEY=sk-proj-YOUR_OPENAI_API_KEY_HERE

# MySQL
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_USER=talkgenius
MYSQL_PASSWORD=your_password
MYSQL_DATABASE=talkgenius

# JWT Secret
JWT_SECRET=your-jwt-secret-key-minimum-32-chars
```

### Spring AI 配置 (application.yml)
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7
          max-tokens: 500
```

## 📚 文件

- [OpenAI 整合指南](OPENAI_ENABLED.md) - 完整的 OpenAI 設定說明
- [修復記錄](FIXES_APPLIED.md) - 詳細的問題修復歷程
- [測試指南](TESTING_GUIDE.md) - 測試流程和方法
- [前端啟動指南](api-test-ui/START.md) - React UI 設置

## 🛠️ 開發工具

- **IDE**: IntelliJ IDEA / VS Code
- **API 測試**: Postman / Thunder Client
- **資料庫**: MySQL Workbench / DBeaver
- **容器**: Docker Desktop

## 🎯 Roadmap

- [x] Spring Boot 後端 API
- [x] OpenAI GPT-4o-mini 整合
- [x] React 測試 UI
- [x] JWT 認證系統
- [x] 8 種語氣風格
- [ ] Android 鍵盤完整實作
- [ ] iOS 鍵盤完整實作
- [ ] 情緒分析功能
- [ ] 對話歷史管理
- [ ] Premium 訂閱系統
- [ ] 多語言支持

## 💰 成本估算

使用 OpenAI GPT-4o-mini:
- **價格**: $0.150 / 1M input tokens, $0.600 / 1M output tokens
- **單次請求**: 約 100-200 tokens
- **預估成本**: 每 1000 次請求 ≈ $0.10-0.20 USD

## 🤝 貢獻

歡迎提交 Issue 和 Pull Request！

## 📄 授權

MIT License - 詳見 [LICENSE](LICENSE)

## 👨‍💻 作者

**Bob Chen**
- Email: bobchen184@gmail.com
- GitHub: [@yanchen184](https://github.com/yanchen184)
- Portfolio: https://yanchen184.github.io/game-portal

## 🙏 致謝

- [Spring AI](https://spring.io/projects/spring-ai) - Spring Framework 的 AI 整合
- [OpenAI](https://openai.com/) - GPT-4o-mini API
- [React](https://reactjs.org/) - 前端框架
- [Tailwind CSS](https://tailwindcss.com/) - CSS 框架

---

**🤖 Generated with [Claude Code](https://claude.com/claude-code)**

**Co-Authored-By: Claude <noreply@anthropic.com>**
