-- TalkGenius Database Initialization Script
-- 創建時間: 2025-01-14
-- 更新時間: 2025-11-23
-- 用途: 初始化 TalkGenius AI 約會助手應用程式的資料庫

-- 設置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 使用 talkgenius 資料庫
USE talkgenius;

-- ============================================
-- 1. users (用戶表)
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY COMMENT '用戶唯一 ID (UUID)',
    email VARCHAR(255) UNIQUE NOT NULL COMMENT '用戶郵箱',
    password_hash VARCHAR(255) NOT NULL COMMENT 'bcrypt 加密的密碼',
    username VARCHAR(100) COMMENT '用戶名',
    age INT COMMENT '年齡',
    gender ENUM('male', 'female', 'other', 'prefer_not_to_say') COMMENT '性別',
    relationship_status ENUM('single', 'dating', 'in_relationship', 'married') COMMENT '感情狀態',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
    last_login TIMESTAMP COMMENT '最後登入時間',
    is_premium BOOLEAN DEFAULT FALSE COMMENT '是否為付費會員',
    premium_expires_at TIMESTAMP NULL COMMENT '付費會員到期時間',
    INDEX idx_email (email),
    INDEX idx_premium (is_premium)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用戶基本資訊表';

-- ============================================
-- 2. user_preferences (用戶偏好表)
-- ============================================
CREATE TABLE IF NOT EXISTS user_preferences (
    id VARCHAR(36) PRIMARY KEY COMMENT '偏好設置 ID (UUID)',
    user_id VARCHAR(36) NOT NULL COMMENT '關聯的用戶 ID',
    default_tone VARCHAR(50) DEFAULT 'high_eq' COMMENT '預設語氣風格',
    language VARCHAR(10) DEFAULT 'zh-TW' COMMENT '語言設置',
    save_history BOOLEAN DEFAULT TRUE COMMENT '是否保存對話歷史',
    notifications_enabled BOOLEAN DEFAULT TRUE COMMENT '是否啟用通知',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用戶個性化偏好設置表';

-- ============================================
-- 3. conversation_history (對話歷史表)
-- ============================================
CREATE TABLE IF NOT EXISTS conversation_history (
    id VARCHAR(36) PRIMARY KEY COMMENT '對話記錄 ID (UUID)',
    user_id VARCHAR(36) NOT NULL COMMENT '關聯的用戶 ID',
    match_name VARCHAR(100) NOT NULL COMMENT '配對對象名稱',
    source_app VARCHAR(50) COMMENT '來源應用 (Tinder, Bumble, etc.)',
    received_message TEXT COMMENT '收到的訊息',
    generated_reply TEXT COMMENT 'AI 生成的回覆',
    tone_style ENUM('humorous', 'high_eq', 'gentle', 'cute', 'romantic', 'professional', 'direct', 'flirty') COMMENT '語氣風格',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, created_at),
    INDEX idx_tone_style (tone_style)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='對話歷史記錄表';

-- ============================================
-- 4. ai_usage_log (AI 使用記錄表)
-- ============================================
CREATE TABLE IF NOT EXISTS ai_usage_log (
    id VARCHAR(36) PRIMARY KEY COMMENT '使用記錄 ID (UUID)',
    user_id VARCHAR(36) NOT NULL COMMENT '關聯的用戶 ID',
    operation_type VARCHAR(100) COMMENT '操作類型',
    model_used VARCHAR(100) COMMENT '使用的 AI 模型',
    tokens_used INT COMMENT 'OpenAI API 消耗的 Token 數',
    success BOOLEAN COMMENT '請求是否成功',
    error_message TEXT COMMENT '錯誤訊息 (如果失敗)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, created_at),
    INDEX idx_operation_type (operation_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI API 使用日誌表';

-- ============================================
-- 5. subscriptions (訂閱表)
-- 已修正：匹配 Subscription.java Entity
-- ============================================
CREATE TABLE IF NOT EXISTS subscriptions (
    id VARCHAR(36) PRIMARY KEY COMMENT '訂閱記錄 ID (UUID)',
    user_id VARCHAR(36) NOT NULL COMMENT '關聯的用戶 ID',
    plan_type ENUM('monthly', 'yearly') NOT NULL DEFAULT 'monthly' COMMENT '訂閱計劃類型',
    status ENUM('active', 'canceled', 'expired', 'pending') NOT NULL DEFAULT 'pending' COMMENT '訂閱狀態',
    start_date TIMESTAMP NOT NULL COMMENT '訂閱開始時間',
    end_date TIMESTAMP NOT NULL COMMENT '訂閱到期時間',
    price DECIMAL(10, 2) COMMENT '訂閱金額',
    payment_provider VARCHAR(50) COMMENT '支付供應商',
    transaction_id VARCHAR(255) COMMENT '交易 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用戶訂閱記錄表';

-- ============================================
-- 插入測試數據 (僅開發環境)
-- ============================================

-- 測試用戶 (密碼: password123, bcrypt hash)
INSERT INTO users (id, email, password_hash, username, age, gender, relationship_status, is_premium)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'test@talkgenius.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'TestUser', 25, 'male', 'single', FALSE),
    ('550e8400-e29b-41d4-a716-446655440001', 'premium@talkgenius.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PremiumUser', 28, 'female', 'dating', TRUE);

-- 測試用戶偏好
INSERT INTO user_preferences (id, user_id, default_tone, language, save_history, notifications_enabled)
VALUES
    ('660e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440000', 'humorous', 'zh-TW', TRUE, TRUE),
    ('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'romantic', 'zh-TW', TRUE, TRUE);

-- 完成初始化
SELECT 'TalkGenius Database Initialized Successfully!' AS status;
