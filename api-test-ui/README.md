# TalkGenius API 測試工具 🎮

一個現代化、互動式的 API 測試前端介面,專為 **TalkGenius** 後端 API 設計。

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![React](https://img.shields.io/badge/React-19.x-61dafb)
![TypeScript](https://img.shields.io/badge/TypeScript-5.7-3178c6)
![Vite](https://img.shields.io/badge/Vite-6.x-646cff)
![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.x-38bdf8)

---

## ✨ 功能特色

### 🔐 認證 API 測試
- **註冊**: 建立新用戶帳戶
- **登入**: 取得 Access Token 和 Refresh Token
- **Token 刷新**: 延長 Session 有效期

### 🤖 AI API 測試
- **AI 回覆生成**: 根據收到的訊息生成智能回覆
- **8 種語氣風格**: 幽默、高情商、溫柔、可愛、文藝、專業、直接、調情
- **即時回應顯示**: 清晰的 JSON 格式輸出
- **一鍵複製**: 快速複製建議回覆

---

## 🚀 快速開始

### 方法 1: 使用啟動腳本 (推薦)

```bash
# Windows
start-ui.bat

# Linux/Mac
npm run dev
```

### 方法 2: 手動啟動

```bash
# 1. 安裝依賴
npm install

# 2. 啟動開發伺服器
npm run dev

# 3. 訪問應用
# http://localhost:3000
```

---

## 📋 使用說明

詳細使用指南請參閱 [START.md](./START.md)

### 快速測試流程

1. **啟動後端**: 確保 TalkGenius 後端運行在 `http://localhost:8080`
2. **啟動前端**: 執行 `npm run dev` 或 `start-ui.bat`
3. **註冊/登入**: 在 **認證** 分頁建立帳戶或登入
4. **測試 AI**: 切換到 **AI API** 分頁,輸入訊息並生成回覆

---

## 🛠️ 技術棧

- **React 19** - UI 框架
- **TypeScript** - 類型安全
- **Vite** - 快速建置工具
- **Tailwind CSS** - 實用優先的 CSS 框架
- **Axios** - HTTP 請求庫

---

## 📁 專案結構

```
api-test-ui/
├── src/
│   ├── components/
│   │   ├── AuthTest.tsx      # 認證 API 測試
│   │   └── AITest.tsx         # AI API 測試
│   ├── App.tsx                # 主應用
│   ├── main.tsx               # 入口
│   └── index.css              # 樣式
├── public/                    # 靜態資源
├── package.json               # 依賴
├── vite.config.ts             # Vite 配置
├── tailwind.config.js         # Tailwind 配置
├── START.md                   # 詳細文檔
└── README.md                  # 本文件
```

---

## 🔗 API 端點

### 認證 API
- `POST /api/v1/auth/register` - 註冊
- `POST /api/v1/auth/login` - 登入
- `POST /api/v1/auth/refresh` - 刷新 Token

### AI API
- `POST /api/v1/ai/generate-reply` - 生成 AI 回覆

---

## 💡 提示

- **後端必須運行**: 確保後端在 `localhost:8080`
- **CORS 已配置**: Vite 代理自動處理跨域
- **即時更新**: 修改程式碼後自動熱更新
- **錯誤處理**: 所有錯誤都會顯示在 UI 上

---

## 📞 聯絡

- **Email**: bobchen184@gmail.com
- **GitHub**: https://github.com/yanchen184

---

**祝測試愉快! 🎉**

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type-aware lint rules:

```js
export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...

      // Remove tseslint.configs.recommended and replace with this
      tseslint.configs.recommendedTypeChecked,
      // Alternatively, use this for stricter rules
      tseslint.configs.strictTypeChecked,
      // Optionally, add this for stylistic rules
      tseslint.configs.stylisticTypeChecked,

      // Other configs...
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```

You can also install [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom) for React-specific lint rules:

```js
// eslint.config.js
import reactX from 'eslint-plugin-react-x'
import reactDom from 'eslint-plugin-react-dom'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...
      // Enable lint rules for React
      reactX.configs['recommended-typescript'],
      // Enable lint rules for React DOM
      reactDom.configs.recommended,
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```
