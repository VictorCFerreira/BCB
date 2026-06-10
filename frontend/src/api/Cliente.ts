const BASE_URL = 'http://localhost:8080'

function getToken(): string | null {
  try {
    const raw = localStorage.getItem('bcb-auth')
    if (!raw) return null
    return JSON.parse(raw)?.state?.token ?? null
  } catch {
    return null
  }
}

interface RequestOptions {
  method?: string
  body?: unknown
  headers?: Record<string, string>
}

async function request<T>(path: string, options: RequestOptions = {}): Promise<T> {
  const token = getToken()

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...options.headers,
  }

  if (token) {
    headers['Authorization'] = `Bearer ${token}`
  }

  const response = await fetch(`${BASE_URL}${path}`, {
    method: options.method ?? 'GET',
    headers,
    body: options.body ? JSON.stringify(options.body) : undefined,
  })

  if (response.status === 401) {
    localStorage.removeItem('bcb-auth')
    window.location.href = '/login'
    throw new Error('Não autorizado')
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Erro desconhecido' }))
    throw new Error(error.message ?? `Erro ${response.status}`)
  }

  if (response.status === 204 || (response.status === 201 && !response.headers.get('content-type')?.includes('application/json'))) {
    return null as T
    }
  return response.json()
}

export const api = {
  get: <T>(path: string) =>
    request<T>(path),

  post: <T>(path: string, body: unknown) =>
    request<T>(path, { method: 'POST', body }),

  put: <T>(path: string, body: unknown) =>
    request<T>(path, { method: 'PUT', body }),

  delete: <T>(path: string) =>
    request<T>(path, { method: 'DELETE' }),
}