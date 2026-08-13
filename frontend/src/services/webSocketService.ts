import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import type { Mensagem } from '../types'

const WS_BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'

let client: Client | null = null

export async function desconectar() {
  if (client) {
    await client.deactivate()
    client = null
  }
}

export async function conectar(
  conversaId: number,
  onMensagem: (mensagem: Mensagem) => void
) {
  await desconectar()

  client = new Client({
    webSocketFactory: () => new SockJS(`${WS_BASE_URL}/ws`),
    reconnectDelay: 5000,
    onConnect: () => {
      client?.subscribe(`/topic/conversa/${conversaId}`, msg => {
        const mensagem: Mensagem = JSON.parse(msg.body)
        onMensagem(mensagem)
      })
    },
    onStompError: frame => {
      console.error('STOMP error:', frame.headers['message'], frame.body)
    },
    onWebSocketError: event => {
      console.error('WebSocket error:', event)
    },
  })

  client.activate()
}
