import { useState } from 'react'
import AuthTest from './components/AuthTest'
import AITest from './components/AITest'

function App() {
  const [activeTab, setActiveTab] = useState<'auth' | 'ai'>('auth')
  const [token, setToken] = useState<string>('')

  return (
    <div className="min-h-screen bg-gradient-to-br from-pink-50 to-purple-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <h1 className="text-2xl font-bold text-gray-900">
              ✨ TalkGenius API 測試工具
            </h1>
            <div className="flex items-center gap-4">
              <span className="text-sm text-gray-600">
                後端: http://localhost:8080
              </span>
              {token && (
                <span className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded">
                  已認證
                </span>
              )}
            </div>
          </div>
        </div>
      </header>

      {/* Navigation Tabs */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-6">
        <div className="flex gap-2 border-b border-gray-200">
          <button
            onClick={() => setActiveTab('auth')}
            className={`px-6 py-3 font-medium transition-colors ${
              activeTab === 'auth'
                ? 'text-primary border-b-2 border-primary'
                : 'text-gray-600 hover:text-gray-900'
            }`}
          >
            🔐 認證 API
          </button>
          <button
            onClick={() => setActiveTab('ai')}
            className={`px-6 py-3 font-medium transition-colors ${
              activeTab === 'ai'
                ? 'text-primary border-b-2 border-primary'
                : 'text-gray-600 hover:text-gray-900'
            }`}
          >
            🤖 AI API
          </button>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {activeTab === 'auth' && <AuthTest onTokenChange={setToken} />}
        {activeTab === 'ai' && <AITest token={token} />}
      </div>

      {/* Footer */}
      <footer className="mt-12 py-6 text-center text-sm text-gray-600">
        <p>TalkGenius API 測試工具 v1.0.0 | 建置於 {new Date().toLocaleDateString('zh-TW')}</p>
      </footer>
    </div>
  )
}

export default App
