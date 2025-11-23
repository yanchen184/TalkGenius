import { useState } from 'react'
import axios from 'axios'

interface AuthTestProps {
  onTokenChange: (token: string) => void
}

interface AuthResponse {
  success: boolean
  userId: string
  username?: string
  email?: string
  accessToken: string
  refreshToken: string
  expiresIn: number
}

export default function AuthTest({ onTokenChange }: AuthTestProps) {
  const [registerData, setRegisterData] = useState({
    email: 'test@example.com',
    password: 'Test1234!',
    username: 'TestUser'
  })

  const [loginData, setLoginData] = useState({
    email: 'test@example.com',
    password: 'Test1234!'
  })

  const [refreshToken, setRefreshToken] = useState('')
  const [response, setResponse] = useState<any>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const handleRegister = async () => {
    setLoading(true)
    setError('')
    setResponse(null)

    try {
      const res = await axios.post<AuthResponse>('/api/v1/auth/register', registerData)
      setResponse(res.data)
      if (res.data.accessToken) {
        onTokenChange(res.data.accessToken)
        setRefreshToken(res.data.refreshToken)
      }
    } catch (err: any) {
      setError(err.response?.data?.message || err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleLogin = async () => {
    setLoading(true)
    setError('')
    setResponse(null)

    try {
      const res = await axios.post<AuthResponse>('/api/v1/auth/login', loginData)
      setResponse(res.data)
      if (res.data.accessToken) {
        onTokenChange(res.data.accessToken)
        setRefreshToken(res.data.refreshToken)
      }
    } catch (err: any) {
      setError(err.response?.data?.message || err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleRefresh = async () => {
    if (!refreshToken) {
      setError('Please login first to get Refresh Token')
      return
    }

    setLoading(true)
    setError('')
    setResponse(null)

    try {
      const res = await axios.post<AuthResponse>(
        '/api/v1/auth/refresh',
        {},
        {
          headers: {
            Authorization: `Bearer ${refreshToken}`
          }
        }
      )
      setResponse(res.data)
      if (res.data.accessToken) {
        onTokenChange(res.data.accessToken)
        setRefreshToken(res.data.refreshToken)
      }
    } catch (err: any) {
      setError(err.response?.data?.message || err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div className="space-y-6">
        <div className="card">
          <h2 className="text-xl font-bold mb-4 text-gray-800">Register</h2>
          <div className="space-y-3">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                className="input"
                value={registerData.email}
                onChange={(e) => setRegisterData({ ...registerData, email: e.target.value })}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Password</label>
              <input
                type="password"
                className="input"
                value={registerData.password}
                onChange={(e) => setRegisterData({ ...registerData, password: e.target.value })}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Username</label>
              <input
                type="text"
                className="input"
                value={registerData.username}
                onChange={(e) => setRegisterData({ ...registerData, username: e.target.value })}
              />
            </div>
            <button onClick={handleRegister} disabled={loading} className="btn btn-primary w-full">
              {loading ? 'Processing...' : 'Register'}
            </button>
          </div>
        </div>

        <div className="card">
          <h2 className="text-xl font-bold mb-4 text-gray-800">Login</h2>
          <div className="space-y-3">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                className="input"
                value={loginData.email}
                onChange={(e) => setLoginData({ ...loginData, email: e.target.value })}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Password</label>
              <input
                type="password"
                className="input"
                value={loginData.password}
                onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
              />
            </div>
            <button onClick={handleLogin} disabled={loading} className="btn btn-primary w-full">
              {loading ? 'Processing...' : 'Login'}
            </button>
          </div>
        </div>

        <div className="card">
          <h2 className="text-xl font-bold mb-4 text-gray-800">Refresh Token</h2>
          <div className="space-y-3">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Refresh Token</label>
              <input
                type="text"
                className="input font-mono text-sm"
                value={refreshToken}
                onChange={(e) => setRefreshToken(e.target.value)}
                placeholder="Auto-filled from login response"
              />
            </div>
            <button onClick={handleRefresh} disabled={loading || !refreshToken} className="btn btn-primary w-full">
              {loading ? 'Processing...' : 'Refresh Token'}
            </button>
          </div>
        </div>
      </div>

      <div className="card">
        <h2 className="text-xl font-bold mb-4 text-gray-800">Response</h2>
        {error && (
          <div className="mb-4 p-4 bg-red-50 border border-red-200 rounded-lg">
            <p className="text-sm text-red-800 font-medium">Error</p>
            <p className="text-sm text-red-600 mt-1">{error}</p>
          </div>
        )}
        {response && (
          <div className="space-y-4">
            <div className="p-4 bg-green-50 border border-green-200 rounded-lg">
              <p className="text-sm text-green-800 font-medium">Success</p>
            </div>
            <div className="bg-gray-50 rounded-lg p-4 overflow-auto max-h-96">
              <pre className="text-sm text-gray-800">{JSON.stringify(response, null, 2)}</pre>
            </div>
            {response.accessToken && (
              <div>
                <p className="text-sm font-medium text-gray-700 mb-2">Access Token:</p>
                <div className="bg-gray-100 p-3 rounded border border-gray-300 break-all">
                  <code className="text-xs text-gray-700">{response.accessToken}</code>
                </div>
              </div>
            )}
            {response.refreshToken && (
              <div>
                <p className="text-sm font-medium text-gray-700 mb-2">Refresh Token:</p>
                <div className="bg-gray-100 p-3 rounded border border-gray-300 break-all">
                  <code className="text-xs text-gray-700">{response.refreshToken}</code>
                </div>
              </div>
            )}
          </div>
        )}
        {!error && !response && (
          <div className="text-center py-12 text-gray-400">
            <p>Execute API test to see response here</p>
          </div>
        )}
      </div>
    </div>
  )
}
